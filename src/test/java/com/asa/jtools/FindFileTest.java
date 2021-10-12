package com.asa.jtools;

import com.asa.jtools.bin.FindFile;
import junit.framework.TestCase;
import org.junit.Assert;

/**
 * @author andrew_asa
 * @date 2021/8/5.
 */
public class FindFileTest extends TestCase {

    public void testMatches() {

        Assert.assertTrue(FindFile.matches("a.txt", "a"));
        Assert.assertTrue(FindFile.matches("a.txt", ".txt"));
        Assert.assertFalse(FindFile.matches("a.txt", ".doc"));
        Assert.assertTrue(FindFile.matches("中文文档.txt", "中文"));
        Assert.assertTrue(FindFile.matches("中文文档.txt", ".txt"));
    }
}