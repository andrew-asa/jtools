package com.asa.base.utils;

import com.asa.base.log.LoggerFactory;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author andrew_asa
 * @date 2021/10/25.
 */
public class ClassScanUtilsTest extends TestCase {

    public void testGetClass4Annotation() throws Exception{

        Set<Class<?>> classes = ClassScanUtils.getClasses("com.asa.jtools.bin");
        List<String> names = new ArrayList<String>();
        for (Class c : classes) {
            names.add(c.getSimpleName());
        }
        LoggerFactory.getLogger().debug("{}",names);
    }
}