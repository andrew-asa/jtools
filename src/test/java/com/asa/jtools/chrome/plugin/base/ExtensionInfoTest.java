package com.asa.jtools.chrome.plugin.base;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author andrew_asa
 * @date 2021/12/14.
 */
public class ExtensionInfoTest extends TestCase {

    public void testGetLocalMessageKey() {

        ExtensionInfo info = new ExtensionInfo();
        Assert.assertEquals(info.getLocalMessageKey("__MSG_appDescription__"), "appDescription");
    }

    public void testGetManifestStringValue() {

        Map<String, Object> manifest = new HashMap<>();
        manifest.put("name", "__MSG_appName__");
        Map<String, Object> local = new HashMap<>();
        Map<String,Object> lv = new HashMap<>();
        lv.put(ChromeConstant.MESSAGE, "appName");
        local.put("appName", lv);
        ExtensionInfo info = new ExtensionInfo();
        info.setManifestMeta(manifest);
        info.setDefaultLocal(local);
        Assert.assertEquals(info.getManifestStringValue("name"), "appName");
    }
}