package com.heyu.train.member.req;

import com.heyu.train.common.resp.req.PageReq;
import lombok.Data;

@Data
public class StationQueryReq extends PageReq {
    private String trainCode;

    @Override
    public String toString() {
        return "StationQueryReq{" +
                "} " + super.toString();
    }
}
