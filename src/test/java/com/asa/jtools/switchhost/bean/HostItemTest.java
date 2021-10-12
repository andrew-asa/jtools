package com.asa.jtools.switchhost.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;

/**
 * @author andrew_asa
 * @date 2021/10/10.
 */
public class HostItemTest extends TestCase {

    public void testSetType() throws Exception{
        HostItem item = new HostItem();
        item.setType(HostItem.HostType.LOCAL);
        ObjectMapper mapper = new ObjectMapper();
        String os = mapper.writeValueAsString(item);
        System.out.println(os);
        HostItem item2 = mapper.readValue(os, HostItem.class);

        System.out.println(item2);

    }
}