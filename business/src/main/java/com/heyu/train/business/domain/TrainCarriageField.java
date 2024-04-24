package com.heyu.train.business.domain;

import com.heyu.train.generator.generator.help.DbField;
import com.heyu.train.generator.generator.help.FieldResult;
import java.util.Collections;

public class TrainCarriageField {
    public static DbField Id = new DbField("id","id","BIGINT","java.lang.Long");

    public static DbField TrainCode = new DbField("train_code","trainCode","VARCHAR","java.lang.String");

    public static DbField Index = new DbField("index","index","INTEGER","java.lang.Integer");

    public static DbField SeatType = new DbField("seat_type","seatType","CHAR","java.lang.String");

    public static DbField SeatCount = new DbField("seat_count","seatCount","INTEGER","java.lang.Integer");

    public static DbField RowCount = new DbField("row_count","rowCount","INTEGER","java.lang.Integer");

    public static DbField ColCount = new DbField("col_count","colCount","INTEGER","java.lang.Integer");

    public static DbField CreateTime = new DbField("create_time","createTime","TIMESTAMP","java.util.Date");

    public static DbField UpdateTime = new DbField("update_time","updateTime","TIMESTAMP","java.util.Date");

    public static FieldResult setId(Long id) {
        return new FieldResult(Id, Collections.singletonList(id));
    }

    public static FieldResult setTrainCode(String trainCode) {
        return new FieldResult(TrainCode, Collections.singletonList(trainCode));
    }

    public static FieldResult setIndex(Integer index) {
        return new FieldResult(Index, Collections.singletonList(index));
    }

    public static FieldResult setSeatType(String seatType) {
        return new FieldResult(SeatType, Collections.singletonList(seatType));
    }

    public static FieldResult setSeatCount(Integer seatCount) {
        return new FieldResult(SeatCount, Collections.singletonList(seatCount));
    }

    public static FieldResult setRowCount(Integer rowCount) {
        return new FieldResult(RowCount, Collections.singletonList(rowCount));
    }

    public static FieldResult setColCount(Integer colCount) {
        return new FieldResult(ColCount, Collections.singletonList(colCount));
    }

    public static FieldResult setCreateTime(java.util.Date createTime) {
        return new FieldResult(CreateTime, Collections.singletonList(createTime));
    }

    public static FieldResult setUpdateTime(java.util.Date updateTime) {
        return new FieldResult(UpdateTime, Collections.singletonList(updateTime));
    }
}