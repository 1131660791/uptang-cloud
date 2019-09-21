package com.uptang.cloud.starter.web.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uptang.cloud.starter.common.util.SimpleDateFormatThreadLocal;
import com.uptang.cloud.starter.web.Constants;
import com.uptang.cloud.starter.web.annotation.JsonResult;
import com.uptang.cloud.starter.web.annotation.JsonResults;
import org.apache.commons.lang3.ArrayUtils;

import java.util.stream.Stream;

/**
 * @author chuan.jiang <jiang.chuan@yanwei365.com>
 * @version 2.0.0
 * @date 2018-11-01
 */
public class CustomerJsonSerializer {
    private ObjectMapper objectMapper = new ObjectMapper();
    private CustomerJsonFilter propertyFilter = new CustomerJsonFilter();

    public void filter(Class<?> clz, boolean shortDateFormat, String[] includes, String[] excludes) {
        if (clz == null) {
            return;
        }

        // 需要包含的字段
        if (ArrayUtils.isNotEmpty(includes)) {
            propertyFilter.include(clz, includes);
        }

        // 需要排除的字段
        if (ArrayUtils.isNotEmpty(excludes)) {
            propertyFilter.exclude(clz, excludes);
        }

        // 设置时间格式
        objectMapper.setDateFormat(shortDateFormat
                ? SimpleDateFormatThreadLocal.get(Constants.DATE_FORMAT)
                : SimpleDateFormatThreadLocal.get(Constants.DATE_TIME_FORMAT));
        objectMapper.addMixIn(clz, propertyFilter.getClass());
    }

    String toJson(Object object) throws JsonProcessingException {
        objectMapper.setFilterProvider(propertyFilter);
        return objectMapper.writeValueAsString(object);
    }


    /**
     * 根据规则进行过滤
     *
     * @param rule 规则
     */
    void filter(JsonResult rule) {
        this.filter(rule.type(), rule.shortDateFormat(), rule.include(), rule.exclude());
    }

    /**
     * 根据规则进行过滤
     *
     * @param rules 规则
     */
    void filter(JsonResults rules) {
        Stream.of(rules.value()).forEach(this::filter);
    }
}
