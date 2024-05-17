package com.heyu.train.business.req;

import com.heyu.train.common.resp.req.PageReq;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DailyTrainTicketQueryReq extends PageReq {
    private String trainCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Override
    public String toString() {
        return "DailyTrainTicketQueryReq{" +
                "} " + super.toString();
    }
}
