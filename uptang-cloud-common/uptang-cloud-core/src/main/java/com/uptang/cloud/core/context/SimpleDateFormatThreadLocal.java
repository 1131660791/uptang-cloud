package com.uptang.cloud.core.context;

import com.uptang.cloud.core.Constants;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class SimpleDateFormatThreadLocal {
    private static final ThreadLocal<Map<String, SimpleDateFormat>> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected synchronized Map<String, SimpleDateFormat> initialValue() {
            return new HashMap<>(16);
        }
    };

    public static SimpleDateFormat get() {
        return get(Constants.DATE_FORMAT);
    }

    public static SimpleDateFormat get(String dateFormat) {
        return DATE_FORMAT_THREAD_LOCAL.get().computeIfAbsent(dateFormat, SimpleDateFormat::new);
    }

    public static void clear() {
        DATE_FORMAT_THREAD_LOCAL.remove();
    }
}
