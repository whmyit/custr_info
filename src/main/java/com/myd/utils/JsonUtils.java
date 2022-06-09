package com.myd.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.Writer;


/**
 * Json转换工具类
 */
public class JsonUtils {

    /**
     * ObjectMapper
     */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 不可实例化
     */
    private JsonUtils() {
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param value 对象
     * @return JSOn字符串
     */
    public static String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json      JSON字符串
     * @param valueType 对象类型
     * @return 对象
     */
    public static <T> T toObject(String json, Class<T> valueType) {
        Assert.hasText(json);
        Assert.notNull(valueType);
        try {
            return mapper.readValue(json, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 将JSON字符串转换为对象
     *
     * @param json     JSON字符串
     * @param javaType 对象类型
     * @return 对象
     */
    public static <T> T toObject(String json, JavaType javaType) {
        Assert.hasText(json);
        Assert.notNull(javaType);
        try {
            return mapper.readValue(json, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象转换为JSON流
     *
     * @param writer writer
     * @param value  对象
     */
    public static void writeValue(Writer writer, Object value) {
        try {
            mapper.writeValue(writer, value);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断json中是否存在某一key值
     *
     * @param json
     * @param key
     * @return
     */
    public static boolean checkShipperCode(String json, String key) {
        JSONObject obj = null;
        boolean bool = false;
        try {
            obj = JSONObject.parseObject(json);
            if (obj.containsKey(key)) {
                bool = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    public static boolean checkValue(Object value) {
        JSONObject obj = null;
        boolean bool = false;
        if (value != null && !"".equals(String.valueOf(value)) && !"null".equals(String.valueOf(value))) {
            bool = true;
        }
        return bool;
    }
    public static boolean checkValue1(JSONObject resJsonObj, String value) {
        JSONObject obj = null;
        boolean bool = true;
        JSONObject dataJson =resJsonObj.getJSONObject(value);
        if(dataJson == null || dataJson.isEmpty()  || "null".equals(dataJson)){
            return false;

        }
        return bool;
    }


    public static String getKey(String json, String key) {
        JSONObject obj = JSONObject.parseObject(json);
        return String.valueOf(obj.get(key));
    }

}
