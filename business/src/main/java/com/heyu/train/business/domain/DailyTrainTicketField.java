package com.heyu.train.business.domain;

import com.heyu.train.generator.generator.help.DbField;
import com.heyu.train.generator.generator.help.FieldResult;
import java.util.Collections;
public class DailyTrainTicketField {
    public static DbField Id = new DbField("id","id","BIGINT","java.lang.Long");

    public static DbField Date = new DbField("date","date","DATE","java.util.Date");

    public static DbField TrainCode = new DbField("train_code","trainCode","VARCHAR","java.lang.String");

    public static DbField Start = new DbField("start","start","VARCHAR","java.lang.String");

    public static DbField StartPinyin = new DbField("start_pinyin","startPinyin","VARCHAR","java.lang.String");

    public static DbField StartTime = new DbField("start_time","startTime","TIME","java.util.Date");

    public static DbField StartIndex = new DbField("start_index","startIndex","INTEGER","java.lang.Integer");

    public static DbField End = new DbField("end","end","VARCHAR","java.lang.String");

    public static DbField EndPinyin = new DbField("end_pinyin","endPinyin","VARCHAR","java.lang.String");

    public static DbField EndTime = new DbField("end_time","endTime","TIME","java.util.Date");

    public static DbField EndIndex = new DbField("end_index","endIndex","INTEGER","java.lang.Integer");

    public static DbField Ydz = new DbField("ydz","ydz","INTEGER","java.lang.Integer");

    public static DbField YdzPrice = new DbField("ydz_price","ydzPrice","DECIMAL","java.math.BigDecimal");

    public static DbField Edz = new DbField("edz","edz","INTEGER","java.lang.Integer");

    public static DbField EdzPrice = new DbField("edz_price","edzPrice","DECIMAL","java.math.BigDecimal");

    public static DbField Rw = new DbField("rw","rw","INTEGER","java.lang.Integer");

    public static DbField RwPrice = new DbField("rw_price","rwPrice","DECIMAL","java.math.BigDecimal");

    public static DbField Yw = new DbField("yw","yw","INTEGER","java.lang.Integer");

    public static DbField YwPrice = new DbField("yw_price","ywPrice","DECIMAL","java.math.BigDecimal");

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

    public static FieldResult setStart(String start) {
        return new FieldResult(Start, Collections.singletonList(start));
    }

    public static FieldResult setStartPinyin(String startPinyin) {
        return new FieldResult(StartPinyin, Collections.singletonList(startPinyin));
    }

    public static FieldResult setStartTime(java.util.Date startTime) {
        return new FieldResult(StartTime, Collections.singletonList(startTime));
    }

    public static FieldResult setStartIndex(Integer startIndex) {
        return new FieldResult(StartIndex, Collections.singletonList(startIndex));
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

    public static FieldResult setEndIndex(Integer endIndex) {
        return new FieldResult(EndIndex, Collections.singletonList(endIndex));
    }

    public static FieldResult setYdz(Integer ydz) {
        return new FieldResult(Ydz, Collections.singletonList(ydz));
    }

    public static FieldResult setYdzPrice(java.math.BigDecimal ydzPrice) {
        return new FieldResult(YdzPrice, Collections.singletonList(ydzPrice));
    }

    public static FieldResult setEdz(Integer edz) {
        return new FieldResult(Edz, Collections.singletonList(edz));
    }

    public static FieldResult setEdzPrice(java.math.BigDecimal edzPrice) {
        return new FieldResult(EdzPrice, Collections.singletonList(edzPrice));
    }

    public static FieldResult setRw(Integer rw) {
        return new FieldResult(Rw, Collections.singletonList(rw));
    }

    public static FieldResult setRwPrice(java.math.BigDecimal rwPrice) {
        return new FieldResult(RwPrice, Collections.singletonList(rwPrice));
    }

    public static FieldResult setYw(Integer yw) {
        return new FieldResult(Yw, Collections.singletonList(yw));
    }

    public static FieldResult setYwPrice(java.math.BigDecimal ywPrice) {
        return new FieldResult(YwPrice, Collections.singletonList(ywPrice));
    }

    public static FieldResult setCreateTime(java.util.Date createTime) {
        return new FieldResult(CreateTime, Collections.singletonList(createTime));
    }

    public static FieldResult setUpdateTime(java.util.Date updateTime) {
        return new FieldResult(UpdateTime, Collections.singletonList(updateTime));
    }
}