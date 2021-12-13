package com.asa.jtools.alfred;

import com.asa.base.utils.ObjectMapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author andrew_asa
 * @date 2021/11/14.
 */
public class JToolsAlfredAssistTest extends TestCase {

    @Ignore
    @Test
    public void test() throws Exception{
        AlfredItems items = new AlfredItems();
        items.addItem(new AlfredItem("uid","args","title","subTitle"));
        items.addItem(new AlfredItem("uid","args","title","subTitle"));
        System.out.println(ObjectMapperUtils.getDefaultMapper().writerWithDefaultPrettyPrinter().writeValueAsString(items));
        String content = "{\n" +
                "  \"items\" : [ {\n" +
                "    \"uid\" : \"jtools_build jar\",\n" +
                "    \"args\" : \"jtools_build jar\",\n" +
                "    \"valid\" : true,\n" +
                "    \"autocomplete\" : true,\n" +
                "    \"title\" : \"jtools_build jar\",\n" +
                "    \"subtitle\" : \"enter to exec\",\n" +
                "    \"icon\" : \"icon.png\"\n" +
                "  },{\n" +
                "    \"uid\" : \"jtools_build dep\",\n" +
                "    \"args\" : \"jtools_build dep\",\n" +
                "    \"valid\" : true,\n" +
                "    \"autocomplete\" : true,\n" +
                "    \"title\" : \"jtools_build dep\",\n" +
                "    \"subtitle\" : \"enter to exec\",\n" +
                "    \"icon\" : \"icon.png\"\n" +
                "  } ]\n" +
                "}";
        AlfredItems i2 = ObjectMapperUtils.getDefaultMapper().readValue(content, AlfredItems.class);
        System.out.println(i2);
    }

    public void testMain() {

    }

    public void testGetBuiltInCmdAlfredItems() {

    }

    @Test
    @Ignore
    public void testGetCustomAlfredItems() {
        
    }

    public void testCreateOptions() {

    }
}