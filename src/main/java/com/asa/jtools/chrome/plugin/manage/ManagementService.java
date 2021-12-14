package com.asa.jtools.chrome.plugin.manage;

import com.asa.base.utils.ListUtils;
import com.asa.base.utils.ObjectMapperUtils;
import com.asa.base.utils.StringUtils;
import com.asa.base.utils.io.FileUtils;
import com.asa.base.utils.io.FilenameUtils;
import com.asa.jtools.chrome.plugin.base.ChromeConstant;
import com.asa.jtools.chrome.plugin.base.ExtensionInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.asa.jtools.chrome.plugin.base.ChromeConstant.LOCALE_MESSAGES_FILE_NAME;

/**
 * @author andrew_asa
 * @date 2021/12/13.
 */
public class ManagementService {

    public Map<String,ExtensionInfo> scan(String path) {

        Map<String,ExtensionInfo> ret = new HashMap<>();
        if (StringUtils.isNotEmpty(path)) {
            File p = new File(path);
            if (p.exists() && p.isDirectory()) {
                for (File f : p.listFiles()) {
                    if (f.isDirectory()) {
                        ExtensionInfo info = parse(f);
                        if (info != null) {
                            String id = f.getName();
                            ret.put(id, info);
                        }
                    }
                }
            }
        }
        return ret;
    }

    public ExtensionInfo parse(String path) {

        if (StringUtils.isNotEmpty(path)) {
            return parse(new File(path));
        }
        return null;
    }

    public ExtensionInfo parse(File file) {

        if (isExtensionDir(file)) {
            // 没有版本号子目录
            if (FileUtils.existFile(file, ChromeConstant.MANIFEST_FILE_NAME)) {
                return doParse(file);
            } else {
                for (File f : file.listFiles()) {
                    if (FileUtils.existFile(f, ChromeConstant.MANIFEST_FILE_NAME)) {
                        return doParse(f);
                    }
                }
            }
        }
        return null;
    }

    private ExtensionInfo doParse(File file) {

        File mf = new File(file, ChromeConstant.MANIFEST_FILE_NAME);
        Map meta = readMap(mf);
        if (meta != null) {
            ExtensionInfo info = new ExtensionInfo(file.getAbsolutePath());
            info.setManifestMeta(meta);
            // 是否有本地化文件
            if (FileUtils.existDir(file, ChromeConstant.LOCALES_DIR_NAME)) {
                loadDefaultLocale(file, info);
            }
            return info;
        }
        return null;
    }

    private Map readMap(File file) {

        try {
            Map meta = ObjectMapperUtils.getDefaultMapper().readValue(file, Map.class);
            return meta;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadDefaultLocale(File dir, ExtensionInfo info) {

        String defaultLocale = info.getManifestStringValue(ChromeConstant.Manifest.DEFAULT_LOCALE);
        if (StringUtils.isNotEmpty(defaultLocale)) {
            String lf = FilenameUtils.concatAll(dir.getAbsolutePath(), ChromeConstant.LOCALES_DIR_NAME, defaultLocale, LOCALE_MESSAGES_FILE_NAME);
            File defaultLocalFile = new File(lf);
            if (defaultLocalFile.exists()) {
                Map meta = readMap(defaultLocalFile);
                if (meta != null) {
                    info.setDefaultLocal(meta);
                }
            }
        }
    }

    public boolean isExtensionDir(String path) {

        return StringUtils.isNotEmpty(path) && isExtensionDir(new File(path));
    }

    /**
     * 是否是插件文件夹
     *
     * @param file
     * @return
     */
    public boolean isExtensionDir(File file) {

        if (file != null && file.exists() && file.isDirectory()) {
            return FileUtils.existFile(file, ChromeConstant.MANIFEST_FILE_NAME, 1);
        }
        return false;
    }

    public List<ExtensionInfo> getAll() {

        List<ExtensionInfo> ret = new ArrayList<>();
        return ret;
    }

    public ExtensionInfo get(String id) {

        return null;
    }
}
