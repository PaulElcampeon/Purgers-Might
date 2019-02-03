package com.purgersmight.purgersmightapp.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper(){
        return objectMapper;
    }
}
