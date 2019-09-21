package com.uptang.cloud.pojo.enums;

import java.math.BigDecimal;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public enum CurrencyUnitEnum {

    /**
     * 元
     */
    YUAN {
        @Override
        public BigDecimal toJiao(double price) {
            return new BigDecimal(price).multiply(BigDecimal.valueOf(10)).setScale(1, BigDecimal.ROUND_HALF_UP);
        }

        @Override
        public BigDecimal toFen(double price) {
            return new BigDecimal(price).multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
        }
    },

    /**
     * 角
     */
    JIAO {
        @Override
        public BigDecimal toYuan(double price) {
            return new BigDecimal(price).divide(BigDecimal.valueOf(10), SCALE, BigDecimal.ROUND_HALF_UP).setScale(1, BigDecimal.ROUND_HALF_UP);
        }

        @Override
        public BigDecimal toFen(double price) {
            return new BigDecimal(price).multiply(BigDecimal.valueOf(10)).setScale(0, BigDecimal.ROUND_HALF_UP);
        }
    },

    /**
     * 分
     */
    FEN {
        @Override
        public BigDecimal toYuan(double price) {
            return new BigDecimal(price).divide(BigDecimal.valueOf(100), SCALE, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        @Override
        public BigDecimal toJiao(double price) {
            return new BigDecimal(price).divide(BigDecimal.valueOf(10), SCALE, BigDecimal.ROUND_HALF_UP).setScale(1, BigDecimal.ROUND_HALF_UP);
        }
    };

    static final int SCALE = 10;

    public BigDecimal toYuan(double price) {
        throw new AbstractMethodError();
    }

    public BigDecimal toJiao(double price) {
        throw new AbstractMethodError();
    }

    public BigDecimal toFen(double price) {
        throw new AbstractMethodError();
    }

}
