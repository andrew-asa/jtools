package com.asa.jtools.base.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author andrew_asa
 * @date 2021/10/11.
 */
public class ObjectMapperUtils {

    private static ObjectMapper DEFAULT_MAPPER = new ObjectMapper();

    static {
        DEFAULT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getDefaultMapper() {

        return DEFAULT_MAPPER;
    }
}
