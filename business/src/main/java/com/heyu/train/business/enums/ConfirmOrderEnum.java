package com.heyu.train.business.enums;

public enum ConfirmOrderEnum {

    INIT("I", "初始化"),
    PENDING("P", "处理中"),
    SUCCESS("S", "成功"),
    FAILURE("F", "失败"),
    EMPTY("E", "没有订单"),
    CANCEL("C", "取消");

    private String code;
    private String desc;

    ConfirmOrderEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "ConfirmOrderEnum{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
