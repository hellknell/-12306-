package com.heyu.train.business.domain;

import com.heyu.train.generator.generator.help.DbField;
import com.heyu.train.generator.generator.help.FieldResult;
import java.util.Collections;

public class TrainField {
    public static DbField Id = new DbField("id","id","BIGINT","java.lang.Long");

    public static DbField Code = new DbField("code","code","VARCHAR","java.lang.String");

    public static DbField Type = new DbField("type","type","CHAR","java.lang.String");

    public static DbField Start = new DbField("start","start","VARCHAR","java.lang.String");

    public static DbField StartPinyin = new DbField("start_pinyin","startPinyin","VARCHAR","java.lang.String");

    public static DbField StartTime = new DbField("start_time","startTime","TIME","java.util.Date");

    public static DbField End = new DbField("end","end","VARCHAR","java.lang.String");

    public static DbField EndPinyin = new DbField("end_pinyin","endPinyin","VARCHAR","java.lang.String");

    public static DbField EndTime = new DbField("end_time","endTime","TIME","java.util.Date");

    public static DbField CreateTime = new DbField("create_time","createTime","TIMESTAMP","java.util.Date");

    public static DbField UpdateTime = new DbField("update_time","updateTime","TIMESTAMP","java.util.Date");

    public static FieldResult setId(Long id) {
        return new FieldResult(Id, Collections.singletonList(id));
    }

    public static FieldResult setCode(String code) {
        return new FieldResult(Code, Collections.singletonList(code));
    }

    public static FieldResult setType(String type) {
        return new FieldResult(Type, Collections.singletonList(type));
    }

    public static FieldResult setStart(String start) {
        return new FieldResult(Start, Collections.singletonList(start));
    }

    public static FieldResult setStartPinyin(String startPinyin) {
        return new FieldResult(StartPinyin, Collections.singletonList(startPinyin));
    }

    public static FieldResult setStartTime(java.util.Date startTime) {
        return new FieldResult(StartTime, Collections.singletonList(startTime));
    }

    public static FieldResult setEnd(String end) {
        return new FieldResult(End, Collections.singletonList(end));
    }

    public static FieldResult setEndPinyin(String endPinyin) {
        return new FieldResult(EndPinyin, Collections.singletonList(endPinyin));
    }

    public static FieldResult setEndTime(java.util.Date endTime) {
        return new FieldResult(EndTime, Collections.singletonList(endTime));
    }

    public static FieldResult setCreateTime(java.util.Date createTime) {
        return new FieldResult(CreateTime, Collections.singletonList(createTime));
    }

    public static FieldResult setUpdateTime(java.util.Date updateTime) {
        return new FieldResult(UpdateTime, Collections.singletonList(updateTime));
    }
}