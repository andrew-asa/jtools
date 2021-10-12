package com.asa.jtools.base.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author andrew_asa
 * @date 2021/10/11.
 */
public class ObjectMapperUtils {

    private static ObjectMapper DEFAULT_MAPPER = new ObjectMapper();

    public static ObjectMapper getDefaultMapper() {

        return DEFAULT_MAPPER;
    }
}
