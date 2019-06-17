package com.dhj.demo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * json、object转换工具类
 * <p>
 * 处理接口传参的转换
 *
 * @author denghaijing
 */
public class GsonUtil {

    /**
     * Object对象转json字符串
     *
     * @param object Object对象
     * @return json字符串
     */
    public static String object2Json(Object object) {
        if (object == null) {
            return null;
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    /**
     * json字符串转object对象
     *
     * @param json json字符串
     * @param type 对象类型
     * @param <T>  泛型
     * @return object对象
     */
    public static <T> T json2Object(String json, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    /**
     * json字符串数组 转成 对象集合
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonArr2ObjectList(String json, Class clazz) {
        Type type = new ParameterizedTypeImpl(clazz);
        List<T> list =  new Gson().fromJson(json, type);
        return list;
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
