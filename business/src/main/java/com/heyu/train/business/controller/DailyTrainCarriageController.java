package com.heyu.train.business.controller;

import com.heyu.train.business.req.DailyTrainCarriageQueryReq;
import com.heyu.train.business.req.DailyTrainCarriageSaveReq;
import com.heyu.train.business.resp.DailyTrainCarriageQueryResp;
import com.heyu.train.business.service.DailyTrainCarriageService;
import com.heyu.train.common.resp.Result;
import com.heyu.train.generator.generator.help.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController
@Tag(name = "每日车厢管理")
@Validated
@RequestMapping("/admin/daily-train-carriage")
@RequiredArgsConstructor
public class DailyTrainCarriageController {
    final DailyTrainCarriageService dailyTrainCarriageService;

    @Operation(summary = "删除车厢")
    @DeleteMapping("/del/{id}")
    public Result<Void> queryList(@PathVariable Long id) {
        dailyTrainCarriageService.del(id);
        return Result.success();
    }

    @Operation(summary = "查询车厢")
    @GetMapping("/query-list")
    public Result<PageInfo<DailyTrainCarriageQueryResp>> queryList(@Valid DailyTrainCarriageQueryReq req) {
        return Result.success(dailyTrainCarriageService.queryList(req));
    }

    @Operation(summary = "新增/保存每日车厢")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody DailyTrainCarriageSaveReq req) {
        dailyTrainCarriageService.save(req);
        return Result.success();
    }
}
