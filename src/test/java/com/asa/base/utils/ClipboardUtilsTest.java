package com.asa.base.utils;

import junit.framework.TestCase;
import org.junit.Ignore;

/**
 * @author andrew_asa
 * @date 2022/6/12.
 */
public class ClipboardUtilsTest extends TestCase {

    @Ignore
    public void testSaveImageFromClipboardNoException() {
        ClipboardUtils.saveImageFromClipboardNoException("./1.png");
    }
}