package com.heyu.train.business.enums;

public enum TrainTypeEnum {

    // 高铁
    HIGH_SPEED_RAIL("0", "高铁"),
    // 动车
    DOUCHE("1", "动车"),
    // 普快
    COMMON_RAIL("2", "普快");
    private String code;
    private String desc;

    TrainTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;

    }
}