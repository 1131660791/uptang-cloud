package com.uptang.cloud.starter.web.json;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.google.common.base.Splitter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@JsonFilter("CustomerJsonFilter")
public class CustomerJsonFilter extends FilterProvider {
    /**
     * 按逗号把字符串拆分成列
     */
    private static final Splitter SPLITTER = Splitter.on(',').trimResults();

    /**
     * 需要保留的字段
     */
    private Map<Class<?>, Set<String>> includeMap = new HashMap<>();

    /**
     * 需要排除的字段
     */
    private Map<Class<?>, Set<String>> excludeMap = new HashMap<>();

    /**
     * 收集需要保留的字段
     *
     * @param clz    数据类型
     * @param fields 保留的字段
     */
    void include(Class<?> clz, String[] fields) {
        Set<String> existedFields = includeMap.getOrDefault(clz, new HashSet<>());
        existedFields.addAll(processFields(fields));
        includeMap.put(clz, existedFields);
    }

    /**
     * 收集需要排除的字段
     *
     * @param clz    数据类型
     * @param fields 排除的字段
     */
    void exclude(Class<?> clz, String[] fields) {
        Set<String> existedFields = excludeMap.getOrDefault(clz, new HashSet<>());
        existedFields.addAll(processFields(fields));
        excludeMap.put(clz, existedFields);
    }

    @Override
    public BeanPropertyFilter findFilter(Object filterId) {
        throw new UnsupportedOperationException("Access to deprecated filters not supported");
    }

    @Override
    public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {
        return new SimpleBeanPropertyFilter() {
            @Override
            public void serializeAsField(Object pojo, JsonGenerator generator, SerializerProvider prov, PropertyWriter writer) throws Exception {
                if (apply(pojo.getClass(), writer.getName())) {
                    writer.serializeAsField(pojo, generator, prov);
                } else if (!generator.canOmitFields()) {
                    writer.serializeAsOmittedField(pojo, generator, prov);
                }
            }
        };
    }

    /**
     * 检查字段是否需要保留
     *
     * @param type 数据类型
     * @param name 字段名称
     * @return 是否需要保留
     */
    private boolean apply(Class<?> type, String name) {
        Set<String> includes = includeMap.get(type);
        Set<String> excludes = excludeMap.get(type);
        // 对字段不区分大小写
        name = StringUtils.trimToEmpty(name).toLowerCase();
        if (includes != null && includes.contains(name)) {
            return true;
        } else if (excludes != null && !excludes.contains(name)) {
            return true;
        } else if (includes == null && excludes == null) {
            return true;
        }
        return false;
    }

    /**
     * 如果有逗号，则拆分
     *
     * @param fields 字段信息
     * @return 处理后的字段
     */
    private Set<String> processFields(String[] fields) {
        if (ArrayUtils.isEmpty(fields)) {
            return new HashSet<>(0);
        }
        return Arrays.stream(fields)
                .flatMap(field -> SPLITTER.splitToList(field).stream())
                .filter(StringUtils::isNotBlank)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }
}
