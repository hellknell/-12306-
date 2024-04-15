package com.heyu.train.member.req;

import com.heyu.train.common.resp.req.PageReq;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PassengerQueryReq extends PageReq {
    private Long memberId;

    @Override
    public String toString() {
        return "PassengerQueryReq{" +
                "} " + super.toString();
    }
}
