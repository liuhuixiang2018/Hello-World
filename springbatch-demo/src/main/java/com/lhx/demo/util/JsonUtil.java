package com.lhx.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @Author:95780
 * @Date: 16:00 2020/1/9
 * @Description:
 */
public class JsonUtil {
    private static final Logger logger = LogManager.getLogger(JsonUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 忽略空bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 统一日期格式
        objectMapper.setDateFormat(new SimpleDateFormat(DateUtils.PATTERN_DEFAULT));
        // 忽略在json字符串中存在, 但在java对象中不存在对应属性的情况, 防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    public static <T> String objToStr(T obj) {
        if (null == obj) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.warn("objToStr error: ", e);
            return null;
        }
    }
    public static <T> T strToObj(String str, Class<T> clazz) {
        if (StringUtils.isBlank(str) || null == clazz) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            logger.warn("strToObj error: ", e);
            return null;
        }
    }
    public static <T> T strToObj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(str) || null == typeReference) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (Exception e) {
            logger.error("strToObj error", e);
            return null;
        }
    }

    /**
     *
     * @param src  json形式的字符串
     * @param target  目标对象
     * @param <T>
     */
    public static <T> void json2Java(String src,T target) {
        if (org.apache.commons.lang3.StringUtils.isBlank(src)) {
            return;
        }
        try {
            JSONObject jsonObject = JSONObject.parseObject(src);
            Field[] fields = target.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                try {
                    Object o = jsonObject.get(name);
                } catch (Exception e) {
                    continue;
                }
                if (jsonObject.get(name) != null && !"".equals(jsonObject.getString(name))) {
                    if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                        field.set(target, Long.parseLong(jsonObject.getString(name)));
                    } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                        field.set(target, Integer.parseInt(jsonObject.getString(name)));
                    } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                        field.set(target, Double.parseDouble(jsonObject.getString(name)));
                    } else if (field.getType().equals(Byte.class) || field.getType().equals(byte.class)) {
                        field.set(target, Byte.parseByte(jsonObject.getString(name)));
                    } else if (field.getType().equals(Short.class) || field.getType().equals(short.class)) {
                        field.set(target, Short.parseShort(jsonObject.getString(name)));
                    } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                        field.set(target, Boolean.parseBoolean(jsonObject.getString(name)));
                    } else if (field.getType().equals(String.class)) {
                        field.set(target, String.valueOf(jsonObject.getString(name)));
                    } else if (field.getType().equals(Date.class)) {
                        field.set(target, Date.parse(jsonObject.getString(name)));
                    } else if (field.getType().equals(BigDecimal.class)) {
                        field.set(target, new BigDecimal(jsonObject.getString(name)));
                    } else if (field.getType().equals(LocalDate.class)) {
                        field.set(target, LocalDate.parse(jsonObject.getString(name)));
                    } else if (field.getType().equals(LocalTime.class)) {
                        field.set(target, LocalTime.parse(jsonObject.getString(name)));
                    } else if (field.getType().equals(LocalDateTime.class)) {
                        field.set(target, LocalDateTime.parse(jsonObject.getString(name)));
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("json字符串转对象异常", e);
        }

    }

}
