package com.asa.base.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author andrew_asa
 * @date 2021/12/15.
 */
public class ObjectMapperUtils {

    public static ObjectMapper default_mapper = new ObjectMapper();

    public static ObjectMapper getDefaultMapper() {

        return default_mapper;
    }

    public static Map readMap(String file) {

        return readMap(new File(file));
    }

    public static Map readMap(File file) {

        try {
            Map meta = ObjectMapperUtils.getDefaultMapper().readValue(file, Map.class);
            return meta;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
