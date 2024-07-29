package com.heyu.train.common.feign;

import com.heyu.train.common.req.MemberTicketReq;
import com.heyu.train.common.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Component
@FeignClient(name = "member")
public interface BusinessFeign {

    @PostMapping("/member/ticket/save")
    Result<Void> saveTicket(@RequestBody MemberTicketReq req);
}