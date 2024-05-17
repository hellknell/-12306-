package com.heyu.train.business.enums;

import java.math.BigDecimal;

public enum TrainTypeEnum {

    // 高铁
    HIGH_SPEED_RAIL("0", "高铁", new BigDecimal(1.2)),
    // 动车
    DOUCHE("1", "动车", new BigDecimal(1)),
    // 普快
    COMMON_RAIL("2", "普快", new BigDecimal(0.8));
    private String code;
    private String desc;
    private BigDecimal priceRate;
    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public BigDecimal getPriceRate() {
        return priceRate;
    }

    TrainTypeEnum(String code, String desc, BigDecimal priceRate) {
        this.code = code;
        this.desc = desc;
        this.priceRate = priceRate;
    }
}