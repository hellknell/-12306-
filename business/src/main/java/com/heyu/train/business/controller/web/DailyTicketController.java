package com.heyu.train.business.controller.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heyu.train.business.req.DailyTrainTicketQueryReq;
import com.heyu.train.business.service.DailyTrainTicketService;
import com.heyu.train.common.resp.DailyTrainTicketQueryResp;
import com.heyu.train.common.resp.PageInfo;
import com.heyu.train.common.resp.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController
@Tag(name = "乘客管理")
@Validated
@RequestMapping("/daily-train-ticket")
@RequiredArgsConstructor
public class DailyTicketController {
    final DailyTrainTicketService dailyTrainTicketService;
    ObjectMapper mapper = new ObjectMapper();
    @Operation(summary = "查询每日车票")
    @GetMapping(value = "/query-list")
    public Result<PageInfo<DailyTrainTicketQueryResp>> queryList(@Valid DailyTrainTicketQueryReq req) {
        DailyTrainTicketQueryReq dailyTrainTicketQueryReq = mapper.convertValue(req, DailyTrainTicketQueryReq.class);
        return Result.success(dailyTrainTicketService.queryList(dailyTrainTicketQueryReq));

    }

}
