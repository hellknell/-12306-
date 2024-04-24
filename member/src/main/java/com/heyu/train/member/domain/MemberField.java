package com.heyu.train.member.domain;

import com.heyu.train.generator.generator.help.DbField;
import com.heyu.train.generator.generator.help.FieldResult;
import java.util.Collections;

public class MemberField {
    public static DbField Id = new DbField("id","id","BIGINT","java.lang.Long");

    public static DbField Mobile = new DbField("mobile","mobile","VARCHAR","java.lang.String");

    public static FieldResult setId(Long id) {
        return new FieldResult(Id, Collections.singletonList(id));
    }

    public static FieldResult setMobile(String mobile) {
        return new FieldResult(Mobile, Collections.singletonList(mobile));
    }
}