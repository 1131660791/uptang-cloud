package com.uptang.cloud.score.handler;

import com.uptang.cloud.score.util.Numbers;

import java.math.BigDecimal;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-09 17:57
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public enum PrimitiveResolver {

    /**
     * 原生Java基础类型
     */
    String_(String.class) {
        @Override
        public Object convert(Object object) {
            return object != null ? String.valueOf(object) : "";
        }
    },

    Long_(Long.class) {
        @Override
        public Object convert(Object number) {
            try {
                return number != null ? Long.parseLong(number + "") : null;
            } catch (NumberFormatException e) {
                if (Numbers.isFloat(number + "")) {
                    return new BigDecimal(number + "").shortValueExact();
                }
                throw e;
            }
        }
    },

    Int_(Integer.class) {
        @Override
        public Object convert(Object number) {
            try {
                return number != null ? Integer.parseInt(number + "") : null;
            } catch (NumberFormatException e) {
                if (Numbers.isFloat(number + "")) {
                    return new BigDecimal(number + "").intValue();
                }

                throw e;
            }
        }
    },

    Float_(Float.class) {
        @Override
        public Object convert(Object number) {
            return number != null ? java.lang.Float.parseFloat(number + "") : null;
        }
    },

    Double_(Double.class) {
        @Override
        public Object convert(Object number) {
            return number != null ? java.lang.Double.parseDouble(number + "") : null;
        }
    },

    Bool(Boolean.class) {
        @Override
        public Object convert(Object bool) {
            try {
                return Boolean.parseBoolean(bool + "");
            } catch (Exception e) {
            }

            Long long_ = (Long) Long_.convert(bool);
            return long_ != null ? (long_ != 0 ? Boolean.TRUE : Boolean.FALSE) : Boolean.FALSE;
        }
    },

    Short_(Short.class) {
        @Override
        public Object convert(Object number) {
            try {
                return number != null ? Short.parseShort(number + "") : null;
            } catch (NumberFormatException e) {
                if (Numbers.isFloat(number + "")) {
                    return new BigDecimal(number + "").shortValueExact();
                }
                throw e;
            }
        }
    };

    private final Class<?> clazz;

    PrimitiveResolver(Class<?> clazz) {
        this.clazz = clazz;
    }

    public abstract Object convert(Object object);

    public static PrimitiveResolver getConverter(Class<?> clazz) {
        if (String.class.isAssignableFrom(clazz)) {
            return PrimitiveResolver.String_;
        }

        if (int.class.isAssignableFrom(clazz)
                || Integer.class.isAssignableFrom(clazz)) {
            return PrimitiveResolver.Int_;
        }

        if (float.class.isAssignableFrom(clazz)
                || Float.class.isAssignableFrom(clazz)) {
            return PrimitiveResolver.Float_;
        }

        if (double.class.isAssignableFrom(clazz)
                || Double.class.isAssignableFrom(clazz)) {
            return PrimitiveResolver.Double_;
        }

        if (boolean.class.isAssignableFrom(clazz)
                || Boolean.class.isAssignableFrom(clazz)) {
            return PrimitiveResolver.Bool;
        }

        return PrimitiveResolver.String_;
    }
}
