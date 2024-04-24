package com.heyu.train.member.domain;

import com.heyu.train.generator.generator.help.DbField;
import com.heyu.train.generator.generator.help.FieldResult;
import java.util.Collections;

public class PassengerField {
    public static DbField Id = new DbField("id","id","BIGINT","java.lang.Long");

    public static DbField MemberId = new DbField("member_id","memberId","BIGINT","java.lang.Long");

    public static DbField Name = new DbField("name","name","VARCHAR","java.lang.String");

    public static DbField IdCard = new DbField("id_card","idCard","VARCHAR","java.lang.String");

    public static DbField Type = new DbField("type","type","CHAR","java.lang.String");

    public static DbField CreateTime = new DbField("create_time","createTime","TIMESTAMP","java.util.Date");

    public static DbField UpdateTime = new DbField("update_time","updateTime","TIMESTAMP","java.util.Date");

    public static FieldResult setId(Long id) {
        return new FieldResult(Id, Collections.singletonList(id));
    }

    public static FieldResult setMemberId(Long memberId) {
        return new FieldResult(MemberId, Collections.singletonList(memberId));
    }

    public static FieldResult setName(String name) {
        return new FieldResult(Name, Collections.singletonList(name));
    }

    public static FieldResult setIdCard(String idCard) {
        return new FieldResult(IdCard, Collections.singletonList(idCard));
    }

    public static FieldResult setType(String type) {
        return new FieldResult(Type, Collections.singletonList(type));
    }

    public static FieldResult setCreateTime(java.util.Date createTime) {
        return new FieldResult(CreateTime, Collections.singletonList(createTime));
    }

    public static FieldResult setUpdateTime(java.util.Date updateTime) {
        return new FieldResult(UpdateTime, Collections.singletonList(updateTime));
    }
}