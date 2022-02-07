package com.asa.jtools.chrome.plugin.base;

import com.asa.base.utils.MapUtils;
import com.asa.base.utils.StringUtils;

import java.util.Map;

/**
 * @author andrew_asa
 * @date 2021/12/13.
 */
public class ExtensionInfo {

    /**
     * manifest 元信息
     */
    private Map manifestMeta;

    /**
     * 本地化元信息
     */
    private Map defaultLocal;

    private String extensionDir;

    public ExtensionInfo() {

    }

    public ExtensionInfo(String extensionDir) {

        this.extensionDir = extensionDir;
    }

    public Map getManifestMeta() {

        return manifestMeta;
    }

    public void setManifestMeta(Map manifestMeta) {

        this.manifestMeta = manifestMeta;
    }

    public String getManifestStringValue(String key) {

        return getLocalMessage(MapUtils.getString(manifestMeta, key));
    }

    private String getLocalMessage(String v) {

        String lk = getLocalMessageKey(v);
        if (StringUtils.isNotEmpty(lk)) {
            Object lv  = MapUtils.get(defaultLocal, lk);
            if (lv!=null && lv instanceof Map) {
                String tv = MapUtils.getString((Map) lv, ChromeConstant.MESSAGE);
                if (StringUtils.isNotEmpty(tv)) {
                    return tv;
                }
            }
        }
        return v;
    }

    public String getLocalMessageKey(String v) {

        if (isLocalMessage(v)) {
            return v.substring(ChromeConstant.LOCALE_MESSAGE_PREFIX.length(), v.length() - ChromeConstant.LOCALE_MESSAGE_SUFFIX.length());
        }
        return v;
    }

    private boolean isLocalMessage(String v) {

        return StringUtils.startsWith(v, ChromeConstant.LOCALE_MESSAGE_PREFIX) && StringUtils.endWith(v, ChromeConstant.LOCALE_MESSAGE_SUFFIX);
    }

    public Map getDefaultLocal() {

        return defaultLocal;
    }

    public void setDefaultLocal(Map defaultLocal) {

        this.defaultLocal = defaultLocal;
    }

    public String getName() {

        return getLocalMessage(ChromeConstant.Manifest.NAME);
    }
}
