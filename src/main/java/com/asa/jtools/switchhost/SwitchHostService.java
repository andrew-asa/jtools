package com.asa.jtools.switchhost;

import com.asa.base.log.LoggerFactory;
import com.asa.base.utils.StringUtils;
import com.asa.base.utils.io.FileUtils;
import com.asa.base.utils.io.FilenameUtils;
import com.asa.jtools.base.utils.ObjectMapperUtils;
import com.asa.jtools.switchhost.bean.HostItem;
import com.asa.jtools.switchhost.bean.HostItems;
import com.asa.jtools.switchhost.bean.SwitchHostSettings;
import com.asa.jtools.switchhost.constant.SwitchHostConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author andrew_asa
 * @date 2021/10/10.
 */
@Component
public class SwitchHostService {

    /**
     * db 路径
     */
    public static final String DB_PATH = FilenameUtils.concat(SwitchHostConstant.DB_BASE_PATH, "hosts.db");

    /**
     * 配置文件路径
     */
    public static final String SETTING_PATH = FilenameUtils.concat(SwitchHostConstant.DB_BASE_PATH, "switch_host.setting");

    /**
     * hosts文件存放位置
     */
    public static final String HOST_DIR = FilenameUtils.concat(SwitchHostConstant.DB_BASE_PATH, "hosts");

    /**
     * 默认的hosts文件id
     */
    public static final String DEFAULT_HOSTS_ID = "default";

    /**
     * 系统hosts文件路径
     */
    public static final String SYSTEM_HOSTS_PATH = "/etc/hosts";

    private HostItems hostItems;

    private SwitchHostSettings settings;

    public SwitchHostService() {

    }

    @PostConstruct
    public void init() {

        try {
            FileUtils.forceMkdir(HOST_DIR);
        } catch (Exception e) {
            LoggerFactory.getLogger().error("无法创建文件夹[{}],程序退出", SwitchHostConstant.DB_BASE_PATH);
            System.exit(-1);
        }
        try {
            boolean init = loadSettings();
            HostItems hostItems = getHostItems();
            // 不存在先进行刷新
            if (hostItems == null || !hostItems.existItem() || !init) {
                LoggerFactory.getLogger().debug("系统初始化");
                backupSystemHosts();
            } else {
                LoggerFactory.getLogger().debug("系统已经初始化过");
            }
            // 同时需要检查配置文件
        } catch (Exception e) {
            LoggerFactory.getLogger().error("无法备份[{}],程序退出", SYSTEM_HOSTS_PATH);
            System.exit(-1);
        }
        LoggerFactory.getLogger().debug("SwitchHost 启动，数据存放位置为[{}]", SwitchHostConstant.DB_BASE_PATH);
    }

    /**
     * 备份系统自带的hosts文件，并生成对应的数据结构
     */
    public void backupSystemHosts() throws IOException {

        Map<String, HostItem> itemMap = new HashMap<>();
        HostItem defaultHostItem = createDefaultHostItem();
        itemMap.put(defaultHostItem.getId(), defaultHostItem);
        HostItems items = new HostItems();
        items.setItemMap(itemMap);
        saveHostFile(SYSTEM_HOSTS_PATH, defaultHostItem.getId());
        saveHostItems(items);
        //    配置文件默认
        settings = new SwitchHostSettings();
        settings.setCurrentHostId(DEFAULT_HOSTS_ID);
        saveSettings(settings);
    }

    private boolean loadSettings() {

        getSettings();
        return settings != null;
    }

    public File getSettingFile() {

        return new File(SETTING_PATH);
    }

    public SwitchHostSettings getSettings() {

        if (settings != null) {
            return settings;
        }
        settings = getObject(SwitchHostSettings.class, getSettingFile());
        return settings;
    }

    /**
     * 获取所有的hosts映射关系。
     *
     * @return
     */
    public HostItems getHostItems() {

        if (hostItems != null && !hostItems.existItem()) {
            return hostItems;
        }
        hostItems = getObject(HostItems.class, getDBFile());
        return hostItems;
    }

    public <T> T getObject(Class<T> valueType, File file) {

        ObjectMapper objectMapper = ObjectMapperUtils.getDefaultMapper();
        try {
            return objectMapper.readValue(file, valueType);
        } catch (Exception e) {
            LoggerFactory.getLogger().error("error read {}", file.getAbsolutePath());
        }
        return null;
    }

    public void saveHostFile(String srcFilePath, String id) throws IOException {

        saveHostFile(new File(srcFilePath), id);
    }

    public void saveHostFile(File src, String id) throws IOException {

        FileUtils.copyFile(src, new File(FilenameUtils.concat(HOST_DIR, id)));
    }

    public File getDBFile() {

        return new File(DB_PATH);
    }


    /**
     * 保存hosts映射文件
     *
     * @param items
     */
    public void saveHostItems(HostItems items) {

        saveObject(items, getDBFile());
    }

    /**
     * 保存配置文件
     *
     * @param items
     */
    public void saveSettings(SwitchHostSettings items) {

        saveObject(items, getSettingFile());
    }

    public void saveObject(Object obj, File file) {

        ObjectMapper objectMapper = ObjectMapperUtils.getDefaultMapper();
        try {
            objectMapper.writeValue(file, obj);
        } catch (IOException e) {
            LoggerFactory.getLogger().info("error save {}", file.getAbsolutePath());
        }
    }

    /**
     * 生成系统默认hosts配置
     *
     * @return
     */
    public HostItem createDefaultHostItem() {

        HostItem defaultHosts = new HostItem();
        defaultHosts.setType(HostItem.HostType.LOCAL);
        defaultHosts.setName(DEFAULT_HOSTS_ID);
        defaultHosts.setId(DEFAULT_HOSTS_ID);
        defaultHosts.setPath(FilenameUtils.concat(SwitchHostService.HOST_DIR, DEFAULT_HOSTS_ID));
        return defaultHosts;
    }

    public List<HostItem> getItems(HostItem.HostType type) {

        HostItems hostItems = getHostItems();
        if (hostItems != null) {
            return hostItems.getItems(type);
        }
        return new ArrayList<>();
    }

    public String getCurrentHostsId() {
        settings = getSettings();
        if (settings != null) {
            return settings.getCurrentHostId();
        }
        return StringUtils.EMPTY;
    }
}
