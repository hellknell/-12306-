package com.heyu.train.business.enums;

import java.math.BigDecimal;

public enum TrainSeatEnum {
    //    N元/公里
    FIRST("1", "一等座", new BigDecimal("0.4")),
    SECOND("2", "二等座", new BigDecimal("0.3")),
    SOLID("3", "硬卧", new BigDecimal("0.5")),
    SOFT("4", "软卧", new BigDecimal("0.6"));
    private String code;
    private String desc;
    private BigDecimal price;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    TrainSeatEnum(String code, String desc, BigDecimal price) {
        this.code = code;
        this.desc = desc;
        this.price = price;
    }
}
