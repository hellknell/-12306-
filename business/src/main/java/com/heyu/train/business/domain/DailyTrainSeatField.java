package com.heyu.train.business.domain;

import com.heyu.train.generator.generator.help.DbField;
import com.heyu.train.generator.generator.help.FieldResult;
import java.util.Collections;

public class DailyTrainSeatField {
    public static DbField Id = new DbField("id","id","BIGINT","java.lang.Long");

    public static DbField Date = new DbField("date","date","DATE","java.util.Date");

    public static DbField TrainCode = new DbField("train_code","trainCode","VARCHAR","java.lang.String");

    public static DbField CarriageIndex = new DbField("carriage_index","carriageIndex","INTEGER","java.lang.Integer");

    public static DbField Row = new DbField("row","row","CHAR","java.lang.String");

    public static DbField Col = new DbField("col","col","CHAR","java.lang.String");

    public static DbField SeatType = new DbField("seat_type","seatType","CHAR","java.lang.String");

    public static DbField CarriageSeatIndex = new DbField("carriage_seat_index","carriageSeatIndex","INTEGER","java.lang.Integer");

    public static DbField Sell = new DbField("sell","sell","VARCHAR","java.lang.String");

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

    public static FieldResult setCarriageIndex(Integer carriageIndex) {
        return new FieldResult(CarriageIndex, Collections.singletonList(carriageIndex));
    }

    public static FieldResult setRow(String row) {
        return new FieldResult(Row, Collections.singletonList(row));
    }

    public static FieldResult setCol(String col) {
        return new FieldResult(Col, Collections.singletonList(col));
    }

    public static FieldResult setSeatType(String seatType) {
        return new FieldResult(SeatType, Collections.singletonList(seatType));
    }

    public static FieldResult setCarriageSeatIndex(Integer carriageSeatIndex) {
        return new FieldResult(CarriageSeatIndex, Collections.singletonList(carriageSeatIndex));
    }

    public static FieldResult setSell(String sell) {
        return new FieldResult(Sell, Collections.singletonList(sell));
    }

    public static FieldResult setCreateTime(java.util.Date createTime) {
        return new FieldResult(CreateTime, Collections.singletonList(createTime));
    }

    public static FieldResult setUpdateTime(java.util.Date updateTime) {
        return new FieldResult(UpdateTime, Collections.singletonList(updateTime));
    }
}