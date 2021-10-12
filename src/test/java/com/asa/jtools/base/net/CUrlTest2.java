package com.asa.jtools.base.net;

import org.junit.Test;

public class CUrlTest2 {

    private static final boolean ENABLE_FIDDLER_FOR_ALL_TEST = true;

    @Test
    public void gzippedResponse() {
        String url = "https://raw.hellogithub.com/hosts";
        CUrl curl = new CUrl(url);
        String s  = curl.exec("UTF-8");

        System.out.println(s);
    }



}
