package com.asa.base.utils;

import junit.framework.TestCase;

/**
 * @author andrew_asa
 * @date 2022/6/13.
 */
public class DateUtilsTest extends TestCase {

    public void testGetYY_MM_DD_HH_MM_SS() {

        System.out.println(DateUtils.formatDate(DateUtils.YYMMDDHHMMSS));
    }
}