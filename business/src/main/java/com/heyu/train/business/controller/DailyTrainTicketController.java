package com.heyu.train.business.controller;

import com.heyu.train.business.req.DailyTrainTicketQueryReq;
import com.heyu.train.business.req.DailyTrainTicketSaveReq;
import com.heyu.train.business.resp.DailyTrainTicketQueryResp;
import com.heyu.train.business.service.DailyTrainTicketService;
import com.heyu.train.common.resp.Result;
import com.heyu.train.generator.generator.help.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController
@Tag(name= "乘客管理")
@Validated
@RequestMapping("/admin/daily-train-ticket")
@RequiredArgsConstructor
public class DailyTrainTicketController {
    final DailyTrainTicketService dailyTrainTicketService;

    @Operation(summary = "新增/保存每日车票")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody DailyTrainTicketSaveReq req) {
        dailyTrainTicketService.save(req);
        return Result.success();
    }
    @Operation(summary = "查询每日车票")
    @GetMapping("/query-list")
    public   Result<PageInfo<DailyTrainTicketQueryResp>> queryList(@Valid DailyTrainTicketQueryReq req) {
        return Result.success(dailyTrainTicketService.queryList(req));

    }
    @Operation(summary = "删除每日车票")
    @DeleteMapping("/delete/{id}")
    public   Result<Void> queryList(@PathVariable  Long id) {
        dailyTrainTicketService.del(id);
        return  Result.success();
    }



}
