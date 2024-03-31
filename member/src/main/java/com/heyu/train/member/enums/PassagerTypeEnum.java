package com.heyu.train.member.enums;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/31 13:54
 */
public enum PassagerTypeEnum {
    CHILD("1", "儿童"),
    ADULT("2", "成人"),
    STUDENTs("3", "学生");
    String code;
    String desc;

    PassagerTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
