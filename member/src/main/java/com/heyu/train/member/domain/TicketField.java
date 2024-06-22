package com.heyu.train.member.domain;


import com.heyu.train.generator.generator.help.DbField;
import com.heyu.train.generator.generator.help.FieldResult;

import java.util.Collections;

public class TicketField {
    public static DbField Id = new DbField("id","id","BIGINT","java.lang.Long");

    public static DbField MemberId = new DbField("member_id","memberId","BIGINT","java.lang.Long");

    public static DbField PassengerId = new DbField("passenger_id","passengerId","BIGINT","java.lang.Long");

    public static DbField PassengerName = new DbField("passenger_name","passengerName","VARCHAR","java.lang.String");

    public static DbField Date = new DbField("date","date","DATE","java.util.Date");

    public static DbField TrainCode = new DbField("train_code","trainCode","VARCHAR","java.lang.String");

    public static DbField Row = new DbField("row","row","CHAR","java.lang.String");

    public static DbField Col = new DbField("col","col","CHAR","java.lang.String");

    public static DbField SeatType = new DbField("seat_type","seatType","CHAR","java.lang.String");

    public static DbField Start = new DbField("start","start","VARCHAR","java.lang.String");

    public static DbField End = new DbField("end","end","VARCHAR","java.lang.String");

    public static DbField StartTime = new DbField("start_time","startTime","TIMESTAMP","java.util.Date");

    public static DbField EndTime = new DbField("end_time","endTime","TIMESTAMP","java.util.Date");

    public static DbField CarriageIndex = new DbField("carriage_index","carriageIndex","INTEGER","java.lang.Integer");

    public static DbField CreateTime = new DbField("create_time","createTime","TIMESTAMP","java.util.Date");

    public static DbField UpdateTime = new DbField("update_time","updateTime","TIMESTAMP","java.util.Date");

    public static FieldResult setId(Long id) {
        return new FieldResult(Id, Collections.singletonList(id));
    }

    public static FieldResult setMemberId(Long memberId) {
        return new FieldResult(MemberId, Collections.singletonList(memberId));
    }

    public static FieldResult setPassengerId(Long passengerId) {
        return new FieldResult(PassengerId, Collections.singletonList(passengerId));
    }

    public static FieldResult setPassengerName(String passengerName) {
        return new FieldResult(PassengerName, Collections.singletonList(passengerName));
    }

    public static FieldResult setDate(java.util.Date date) {
        return new FieldResult(Date, Collections.singletonList(date));
    }

    public static FieldResult setTrainCode(String trainCode) {
        return new FieldResult(TrainCode, Collections.singletonList(trainCode));
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

    public static FieldResult setStart(String start) {
        return new FieldResult(Start, Collections.singletonList(start));
    }

    public static FieldResult setEnd(String end) {
        return new FieldResult(End, Collections.singletonList(end));
    }

    public static FieldResult setStartTime(java.util.Date startTime) {
        return new FieldResult(StartTime, Collections.singletonList(startTime));
    }

    public static FieldResult setEndTime(java.util.Date endTime) {
        return new FieldResult(EndTime, Collections.singletonList(endTime));
    }

    public static FieldResult setCarriageIndex(Integer carriageIndex) {
        return new FieldResult(CarriageIndex, Collections.singletonList(carriageIndex));
    }

    public static FieldResult setCreateTime(java.util.Date createTime) {
        return new FieldResult(CreateTime, Collections.singletonList(createTime));
    }

    public static FieldResult setUpdateTime(java.util.Date updateTime) {
        return new FieldResult(UpdateTime, Collections.singletonList(updateTime));
    }
}