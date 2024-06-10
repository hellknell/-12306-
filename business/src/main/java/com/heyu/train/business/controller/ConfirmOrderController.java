package com.heyu.train.business.controller;

import com.heyu.train.business.req.ConfirmOrderDoReq;
import com.heyu.train.business.req.ConfirmOrderQueryReq;
import com.heyu.train.business.service.ConfirmOrderService;
import com.heyu.train.common.resp.ConfirmOrderQueryResp;
import com.heyu.train.common.resp.Result;
import com.heyu.train.generator.generator.help.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController
@Tag(name = "订单管理")
@RequestMapping("/admin/confirm-order")
@RequiredArgsConstructor
public class ConfirmOrderController {
    final ConfirmOrderService confirmOrderService;

    @Operation(summary = "新增/保存订单")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody ConfirmOrderDoReq req) {
        confirmOrderService.save(req);
        return Result.success();
    }

    @Operation(summary = "查询订单")
    @GetMapping("/query-list")
    public Result<PageInfo<ConfirmOrderQueryResp>> queryList(@Valid ConfirmOrderQueryReq req) {
        return Result.success(confirmOrderService.queryList(req));
    }

    @Operation(summary = "删除订单")
    @DeleteMapping("/del/{id}")
    public Result<Void> queryList(@PathVariable Long id) {
        confirmOrderService.del(id);
        return Result.success();
    }
}
