package com.asa.jtools.switchhost;

import com.asa.base.log.LoggerFactory;
import com.asa.jtools.switchhost.bean.HostItem;
import com.asa.jtools.switchhost.bean.HostItems;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author andrew_asa
 * @date 2021/10/11.
 */
public class SwitchHostServiceTest extends TestCase {

    public void testInit() {

        SwitchHostService service = new SwitchHostService();
        service.init();
    }

    public void testSaveHostItems() {

        SwitchHostService service = new SwitchHostService();
        Map<String, HostItem> itemMap = new HashMap<>();
        HostItem hostItem = service.createDefaultHostItem();
        itemMap.put(hostItem.getId(), hostItem);
        HostItems items = new HostItems();
        items.setItemMap(itemMap);
        service.saveHostItems(items);
    }

    public void testGetHostItems() {
        SwitchHostService service = new SwitchHostService();
        HostItems items = service.getHostItems();
        LoggerFactory.getLogger().debug("{}",items);
    }
}