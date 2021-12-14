package com.asa.jtools.chrome.plugin.base;

/**
 * @author andrew_asa
 * @date 2021/12/13.
 */
public class ChromeConstant {

    public static final String MANIFEST_FILE_NAME = "manifest.json";
    public static final String LOCALES_DIR_NAME = "_locales";
    public static final String LOCALE_MESSAGES_FILE_NAME = "messages.json";
    public static final String MESSAGE = "message";

    public static final String LOCALE_MESSAGE_PREFIX = "__MSG_";
    public static final String LOCALE_MESSAGE_SUFFIX = "__";
    public static class Manifest{
        public static final String NAME = "name";
        public static final String VERSION= "version";
        public static final String DEFAULT_LOCALE= "default_locale";
    }
}
