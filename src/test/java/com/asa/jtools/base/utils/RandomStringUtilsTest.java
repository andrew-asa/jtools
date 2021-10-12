package com.asa.jtools.base.utils;

import junit.framework.TestCase;

/**
 * @author andrew_asa
 * @date 2021/10/12.
 */
public class RandomStringUtilsTest extends TestCase {

    public void testGetRandomString() {

        System.out.println(RandomStringUtils.randomAlphabetic(10));
    }
}