package com.asa.jtools.switchhost.constant;

import com.asa.base.utils.io.FilenameUtils;
import com.asa.jtools.base.constant.JToolsConstant;

/**
 * @author andrew_asa
 * @date 2021/10/11.
 */
public class SwitchHostConstant {

    /**
     * 数据存放位置
     */
    public static final String DB_BASE_PATH = FilenameUtils.concat(JToolsConstant.DB_BASE_PATH,"switchhost");

    /**
     * 网络
     */
    public static final String NET = "net";

    /**
     * 本地
     */
    public static final String LOCAL = "local";
}
