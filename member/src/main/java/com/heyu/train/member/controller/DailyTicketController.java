package com.heyu.train.member.controller;

import com.heyu.train.common.feign.MemberFeign;
import com.heyu.train.common.req.DailyTrainTicketQueryReq;
import com.heyu.train.common.resp.DailyTrainTicketQueryResp;
import com.heyu.train.common.resp.PageInfo;
import com.heyu.train.common.resp.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/5/25 18:39
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/daily-ticket")
public class DailyTicketController {
    final MemberFeign memberFeign;

    @GetMapping("/query-list")
    public Result<PageInfo<DailyTrainTicketQueryResp>> queryDailyTicketList(@Valid  DailyTrainTicketQueryReq req) {
        return memberFeign.queryDailyTrainTicket(req);
    }

}
