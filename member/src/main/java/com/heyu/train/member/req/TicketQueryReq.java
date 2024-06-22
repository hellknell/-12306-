package com.heyu.train.member.req;

import com.heyu.train.common.resp.req.PageReq;
import lombok.Data;

import java.util.Date;

@Data
public class TicketQueryReq extends PageReq {
    private String code;
    private Date date;

@Override
public String toString() {
return "TicketQueryReq{" +
"} " + super.toString();
}
}
