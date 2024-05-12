package com.heyu.train.business.domain;

import com.heyu.train.generator.generator.help.DbField;
import com.heyu.train.generator.generator.help.FieldResult;
import java.util.Collections;

public class DailyTrainStationField {
    public static DbField Id = new DbField("id","id","BIGINT","java.lang.Long");

    public static DbField Date = new DbField("date","date","DATE","java.util.Date");

    public static DbField TrainCode = new DbField("train_code","trainCode","VARCHAR","java.lang.String");

    public static DbField Index = new DbField("index","index","INTEGER","java.lang.Integer");

    public static DbField Name = new DbField("name","name","VARCHAR","java.lang.String");

    public static DbField NamePinyin = new DbField("name_pinyin","namePinyin","VARCHAR","java.lang.String");

    public static DbField InTime = new DbField("in_time","inTime","TIME","java.util.Date");

    public static DbField OutTime = new DbField("out_time","outTime","TIME","java.util.Date");

    public static DbField StopTime = new DbField("stop_time","stopTime","TIME","java.util.Date");

    public static DbField Km = new DbField("km","km","DECIMAL","java.math.BigDecimal");

    public static DbField CreateTime = new DbField("create_time","createTime","TIMESTAMP","java.util.Date");

    public static DbField UpdateTime = new DbField("update_time","updateTime","TIMESTAMP","java.util.Date");

    public static FieldResult setId(Long id) {
        return new FieldResult(Id, Collections.singletonList(id));
    }

    public static FieldResult setDate(java.util.Date date) {
        return new FieldResult(Date, Collections.singletonList(date));
    }

    public static FieldResult setTrainCode(String trainCode) {
        return new FieldResult(TrainCode, Collections.singletonList(trainCode));
    }

    public static FieldResult setIndex(Integer index) {
        return new FieldResult(Index, Collections.singletonList(index));
    }

    public static FieldResult setName(String name) {
        return new FieldResult(Name, Collections.singletonList(name));
    }

    public static FieldResult setNamePinyin(String namePinyin) {
        return new FieldResult(NamePinyin, Collections.singletonList(namePinyin));
    }

    public static FieldResult setInTime(java.util.Date inTime) {
        return new FieldResult(InTime, Collections.singletonList(inTime));
    }

    public static FieldResult setOutTime(java.util.Date outTime) {
        return new FieldResult(OutTime, Collections.singletonList(outTime));
    }

    public static FieldResult setStopTime(java.util.Date stopTime) {
        return new FieldResult(StopTime, Collections.singletonList(stopTime));
    }

    public static FieldResult setKm(java.math.BigDecimal km) {
        return new FieldResult(Km, Collections.singletonList(km));
    }

    public static FieldResult setCreateTime(java.util.Date createTime) {
        return new FieldResult(CreateTime, Collections.singletonList(createTime));
    }

    public static FieldResult setUpdateTime(java.util.Date updateTime) {
        return new FieldResult(UpdateTime, Collections.singletonList(updateTime));
    }
}