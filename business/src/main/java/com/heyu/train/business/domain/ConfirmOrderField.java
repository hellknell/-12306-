package com.heyu.train.business.domain;

import com.heyu.train.generator.generator.help.DbField;
import com.heyu.train.generator.generator.help.FieldResult;


import java.util.Collections;

public class ConfirmOrderField {
    public static DbField Id = new DbField("id","id","BIGINT","java.lang.Long");

    public static DbField MemberId = new DbField("member_id","memberId","BIGINT","java.lang.Long");

    public static DbField Date = new DbField("date","date","DATE","java.util.Date");

    public static DbField TrainCode = new DbField("train_code","trainCode","VARCHAR","java.lang.String");

    public static DbField Start = new DbField("start","start","VARCHAR","java.lang.String");

    public static DbField End = new DbField("end","end","VARCHAR","java.lang.String");

    public static DbField DailyTrainTicketId = new DbField("daily_train_ticket_id","dailyTrainTicketId","BIGINT","java.lang.Long");

    public static DbField Status = new DbField("status","status","CHAR","java.lang.String");

    public static DbField CreateTime = new DbField("create_time","createTime","TIMESTAMP","java.util.Date");

    public static DbField UpdateTime = new DbField("update_time","updateTime","TIMESTAMP","java.util.Date");

    public static DbField Tickets = new DbField("tickets","tickets","LONGVARCHAR","java.lang.String");

    public static FieldResult setId(Long id) {
        return new FieldResult(Id, Collections.singletonList(id));
    }

    public static FieldResult setMemberId(Long memberId) {
        return new FieldResult(MemberId, Collections.singletonList(memberId));
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

    public static FieldResult setEnd(String end) {
        return new FieldResult(End, Collections.singletonList(end));
    }

    public static FieldResult setDailyTrainTicketId(Long dailyTrainTicketId) {
        return new FieldResult(DailyTrainTicketId, Collections.singletonList(dailyTrainTicketId));
    }

    public static FieldResult setStatus(String status) {
        return new FieldResult(Status, Collections.singletonList(status));
    }

    public static FieldResult setCreateTime(java.util.Date createTime) {
        return new FieldResult(CreateTime, Collections.singletonList(createTime));
    }

    public static FieldResult setUpdateTime(java.util.Date updateTime) {
        return new FieldResult(UpdateTime, Collections.singletonList(updateTime));
    }

    public static FieldResult setTickets(String tickets) {
        return new FieldResult(Tickets, Collections.singletonList(tickets));
    }
}