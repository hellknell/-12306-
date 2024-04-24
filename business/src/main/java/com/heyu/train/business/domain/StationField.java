package com.heyu.train.business.domain;

import com.heyu.train.generator.generator.help.DbField;
import com.heyu.train.generator.generator.help.FieldResult;
import java.util.Collections;

public class StationField {
    public static DbField Id = new DbField("id","id","BIGINT","java.lang.Long");

    public static DbField Name = new DbField("name","name","VARCHAR","java.lang.String");

    public static DbField NamePinyin = new DbField("name_pinyin","namePinyin","VARCHAR","java.lang.String");

    public static DbField NamePy = new DbField("name_py","namePy","VARCHAR","java.lang.String");

    public static DbField CreateTime = new DbField("create_time","createTime","TIMESTAMP","java.util.Date");

    public static DbField UpdateTime = new DbField("update_time","updateTime","TIMESTAMP","java.util.Date");

    public static FieldResult setId(Long id) {
        return new FieldResult(Id, Collections.singletonList(id));
    }

    public static FieldResult setName(String name) {
        return new FieldResult(Name, Collections.singletonList(name));
    }

    public static FieldResult setNamePinyin(String namePinyin) {
        return new FieldResult(NamePinyin, Collections.singletonList(namePinyin));
    }

    public static FieldResult setNamePy(String namePy) {
        return new FieldResult(NamePy, Collections.singletonList(namePy));
    }

    public static FieldResult setCreateTime(java.util.Date createTime) {
        return new FieldResult(CreateTime, Collections.singletonList(createTime));
    }

    public static FieldResult setUpdateTime(java.util.Date updateTime) {
        return new FieldResult(UpdateTime, Collections.singletonList(updateTime));
    }
}