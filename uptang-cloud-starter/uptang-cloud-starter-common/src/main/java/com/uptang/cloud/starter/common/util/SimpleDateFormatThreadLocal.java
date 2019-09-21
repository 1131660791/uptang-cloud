package com.uptang.cloud.starter.common.util;

import com.uptang.cloud.starter.common.Constants;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class SimpleDateFormatThreadLocal {
    private static ThreadLocal<Map<String, SimpleDateFormat>> simpleDateFormatThreadLocal = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected synchronized Map<String, SimpleDateFormat> initialValue() {
            return new HashMap<>(16);
        }
    };

    public static SimpleDateFormat get() {
        return get(Constants.DATE_FORMAT);
    }

    public static SimpleDateFormat get(String dateFormat) {
        return simpleDateFormatThreadLocal.get().computeIfAbsent(dateFormat, SimpleDateFormat::new);
    }

    public static void clear() {
        simpleDateFormatThreadLocal.remove();
    }
}
