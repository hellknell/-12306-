package com.heyu.train.member.controller;

import com.heyu.train.common.resp.Result;
import com.heyu.train.generator.generator.help.PageInfo;
import com.heyu.train.member.req.PassengerQueryReq;
import com.heyu.train.member.req.PassengerSaveReq;
import com.heyu.train.member.resp.PassengerQueryResp;
import com.heyu.train.member.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController
@Tag(name = "乘车人管理")
@Validated
@RequestMapping("/passenger")
@RequiredArgsConstructor
public class PassengerController {
    final PassengerService passengerService;
    @Operation(summary = "新增/保存乘客")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody PassengerSaveReq req) {
        passengerService.save(req);
        return Result.success();
    }
    @Operation(summary = "查询会员乘客")
    @GetMapping("/query-passenger-mine")
    public Result<List<PassengerQueryResp>> queryPassenger() {
        List<PassengerQueryResp> passengerQueryResps = passengerService.queryPassenger();
        return Result.success(passengerQueryResps);
    }

    @Operation(summary = "查询乘客")
    @GetMapping("/query-list")
    public Result<PageInfo<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req) {
        return Result.success(passengerService.queryList(req));
    }
    @Operation(summary = "删除乘客")
    @DeleteMapping("/del/{id}")
    public Result<Void> queryList(@PathVariable Long id) {
        passengerService.del(id);
        return Result.success();
    }
}
