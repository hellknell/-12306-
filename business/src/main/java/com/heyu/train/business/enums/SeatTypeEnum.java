package com.heyu.train.business.enums;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum SeatTypeEnum {

    FIRST_A("A", "A", "1"),
    FIRST_C("C", "C", "1"),
    FIRST_D("D", "D", "1"),
    FIRST_E("F", "F", "1"),
    SECOND_A("A", "A", "2"),
    SECOND_B("B", "B", "2"),
    SECOND_C("C", "C", "2"),
    SECOND_D("D", "D", "2"),
    SECOND_F("F", "F", "2"),
    SOLID_UP("上铺", "上铺", "3"),
    SOLID_DOWN("下铺", "下铺", "3"),
    SOFT_UP("上铺", "上铺", "4"),
    SOFT_DOWN("下铺", "下铺", "4");
    private String code;
    private String desc;
    private String type;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getType() {
        return type;
    }

    SeatTypeEnum(String code, String desc, String type) {
        this.code = code;
        this.desc = desc;
        this.type = type;
    }

    public static List<String> getColsByType(String type) {
        List<String> list = new ArrayList<>();
        EnumSet<SeatTypeEnum> seatTypeEnums = EnumSet.allOf(SeatTypeEnum.class);
        for (SeatTypeEnum typeEnum : seatTypeEnums) {
            if (StrUtil.equals(type, typeEnum.getType())) {
                list.add(typeEnum.getCode());
            }
        }
        return list;
    }
}
