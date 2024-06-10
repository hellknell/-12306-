package com.heyu.train.common.req;

import com.heyu.train.common.resp.req.PageReq;
import lombok.Data;

@Data
public class DailyTrainTicketQueryReq extends PageReq {

    private String trainCode;
    private String date;
    private String start;
    private String end;

    @Override
    public String toString() {
        return "DailyTrainTicketQueryReq{" +
                "} " + super.toString();
    }
}

