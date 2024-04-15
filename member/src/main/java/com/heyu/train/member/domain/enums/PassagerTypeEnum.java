package com.heyu.train.member.domain.enums;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/31 13:54
 */
public enum PassagerTypeEnum {
    CHILD("1", "儿童"),
    ADULT("2", "成人"),
    STUDENTs("3", "学生");
    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    PassagerTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
