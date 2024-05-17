package com.heyu.train.business.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.heyu.train.common.resp.req.PageReq;
import lombok.Data;

import java.util.Date;

@Data
public class DailyTrainQueryReq extends PageReq {
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    @Override
    public String toString() {
        return "DailyTrainQueryReq{" +
                "} " + super.toString();
    }
}
