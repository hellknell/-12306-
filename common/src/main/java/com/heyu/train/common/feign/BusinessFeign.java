package com.heyu.train.common.feign;

import com.heyu.train.common.req.MemberTicketReq;
import com.heyu.train.common.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Component
@FeignClient(name = "member-service", url = "http://localhost:8001/member")
public interface BusinessFeign {

    @PostMapping("/ticket/save")
    Result<Void> saveTicket(@RequestBody MemberTicketReq req);
}