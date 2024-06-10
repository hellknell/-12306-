package com.heyu.train.common.req;

import com.heyu.train.common.resp.req.PageReq;
import lombok.Data;

@Data
public class TrainQueryReq extends PageReq {
    private String trainCode;
    @Override
    public String toString() {
        return "TrainQueryReq{" +
                "} " + super.toString();
    }
}
