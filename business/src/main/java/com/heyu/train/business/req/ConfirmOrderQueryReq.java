package com.heyu.train.business.req;

import com.heyu.train.common.resp.req.PageReq;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ConfirmOrderQueryReq extends PageReq {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    
    private String code;

    @Override
    public String toString() {
        return "ConfirmOrderQueryReq{" +
                "} " + super.toString();
    }
}
