package com.asa.jtools.switchhost.constant;

import com.asa.base.utils.io.FilenameUtils;
import com.asa.jtools.base.constant.JToolsConstant;

import java.util.Arrays;
import java.util.List;

/**
 * @author andrew_asa
 * @date 2021/10/11.
 */
public class SwitchHostConstant {

    public static final String APP_NAME = "SwitchHost";

    /**
     * 数据存放位置
     */
    public static final String DB_BASE_PATH = FilenameUtils.concat(JToolsConstant.DB_BASE_PATH, "switchhost");

    public static final String TYPE = "type";

    public static final String NAME = "name";

    public static final String URL = "url";

    public static final String FREQUENCY = "frequency";

    public static final String ROOT = "Root";

    /**
     * 网络
     */
    public static final String REMOTE = "Remote";

    /**
     * 本地
     */
    public static final String LOCAL = "Local";

    /**
     * 默认更新频率
     */
    public static final List<String> UPDATE_FREQUENCY = Arrays.asList("手动", "0.5小时", "1小时", "2小时", "3小时",
                                                                      "4小时", "5小时", "6小时", "7小时",
                                                                      "8小时", "9小时", "10小时", "11小时", "12小时");

    public static final int DEFAULT_ID_LENGTH=10;
}
