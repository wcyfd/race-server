package com.randioo.race_server.util;

import java.io.IOException;

import com.google.gson.Gson;

public class ObjectUtils {
    public static String serialize(Object entity) {
        Gson gson = new Gson();
        String value = gson.toJson(entity);
        return value;
    }

    public static <T> T unserialize(Class<T> clazz, String source) {
        Gson gson = new Gson();
        try {
            return gson.getAdapter(clazz).fromJson(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
