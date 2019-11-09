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
            String str = object != null ? String.valueOf(object) : "";
            return str != null ? str.replaceAll("\\s*", "") : "";
        }
    },

    Long_(Long.class) {
        @Override
        public Object convert(Object number) {
            try {
                return number != null ? Long.parseLong((String) String_.convert(number)) : null;
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
            String string = (String) String_.convert(number);

            try {
                return number != null ? Integer.parseInt(string) : null;
            } catch (NumberFormatException e) {
                if (Numbers.isFloat(string)) {
                    return new BigDecimal(string).intValue();
                }

                throw e;
            }
        }
    },

    Float_(Float.class) {
        @Override
        public Object convert(Object number) {
            return number != null ? java.lang.Float.parseFloat((String) String_.convert(number)) : null;
        }
    },

    Double_(Double.class) {
        @Override
        public Object convert(Object number) {
            return number != null ? java.lang.Double.parseDouble((String) String_.convert(number)) : null;
        }
    },

    Bool(Boolean.class) {
        @Override
        public Object convert(Object bool) {
            try {
                return Boolean.parseBoolean(bool + "");
            } catch (Exception e) {
                // FIXME ignore
            }

            Long long_ = (Long) Long_.convert(bool);
            return long_ != null ? (long_ != 0 ? Boolean.TRUE : Boolean.FALSE) : Boolean.FALSE;
        }
    },

    Short_(Short.class) {
        @Override
        public Object convert(Object number) {
            try {
                return number != null ? Short.parseShort((String) String_.convert(number)) : null;
            } catch (NumberFormatException e) {
                if (Numbers.isFloat((String) String_.convert(number))) {
                    return new BigDecimal((String) String_.convert(number)).shortValueExact();
                }
                throw e;
            }
        }
    };

    /**
     * 原始类型
     */
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

        if (short.class.isAssignableFrom(clazz)
                || Short.class.isAssignableFrom(clazz)) {
            return PrimitiveResolver.Short_;
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
