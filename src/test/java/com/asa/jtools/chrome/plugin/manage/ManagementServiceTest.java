package com.asa.jtools.chrome.plugin.manage;

import com.asa.base.utils.io.FilenameUtils;
import com.asa.jtools.chrome.plugin.base.ChromeConstant;
import com.asa.jtools.chrome.plugin.base.ExtensionInfo;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Map;

/**
 * @author andrew_asa
 * @date 2021/12/13.
 */
public class ManagementServiceTest extends TestCase {

    String path;

    String pluginsRootDir;

    public void setUp() {

        path = this.getClass().getResource("./").getPath();
        pluginsRootDir = FilenameUtils.concat(path, "testplugins");
    }

    public void testScan() {

        ManagementService service = new ManagementService();
        Map<String,ExtensionInfo> infoMap =  service.scan(pluginsRootDir);
        ExtensionInfo info = infoMap.get("abc");
        Assert.assertNotNull(info);
        Assert.assertEquals(info.getManifestStringValue("name"),"BaiduExporter");
    }

    public void testParse() {
        String plugin1 = FilenameUtils.concat(pluginsRootDir, "abc");
        ManagementService service = new ManagementService();
        ExtensionInfo extensionInfo = service.parse(plugin1);
        Assert.assertEquals(extensionInfo.getManifestStringValue("name"),"BaiduExporter");
    }

    public void testTestParse() {

    }

    public void testIsExtensionDir() {

    }

    public void testTestIsExtensionDir() {

        String plugin1 = FilenameUtils.concat(pluginsRootDir, "abc");
        String plugin2 = FilenameUtils.concat(pluginsRootDir, "efg");
        String plugin3 = FilenameUtils.concat(pluginsRootDir, "aaaa");
        ManagementService service = new ManagementService();
        Assert.assertTrue(service.isExtensionDir(plugin1));
        Assert.assertTrue(service.isExtensionDir(plugin2));
        Assert.assertFalse(service.isExtensionDir(plugin3));
    }

    public void testGetAll() {

    }

    public void testGet() {

    }
}