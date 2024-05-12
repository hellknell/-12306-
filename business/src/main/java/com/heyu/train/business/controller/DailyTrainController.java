package com.heyu.train.business.controller;

import com.heyu.train.business.req.DailyTrainQueryReq;
import com.heyu.train.business.req.DailyTrainSaveReq;
import com.heyu.train.business.resp.DailyTrainQueryResp;
import com.heyu.train.business.service.DailyTrainService;
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
@RequestMapping("/admin/daily-train")
@RequiredArgsConstructor
public class DailyTrainController {
    final DailyTrainService dailyTrainService;

    @Operation(summary= "新增/保存乘客")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody DailyTrainSaveReq req) {
        dailyTrainService.save(req);
        return Result.success();
    }
    @Operation(summary = "查询乘客")
    @GetMapping("/query-list")
    public   Result<PageInfo<DailyTrainQueryResp>> queryList(@Valid DailyTrainQueryReq req) {
        return Result.success(dailyTrainService.queryList(req));

    }
    @Operation(summary = "删除乘客")
    @DeleteMapping("/delete/{id}")
    public   Result<Void> queryList(@PathVariable  Long id) {
        dailyTrainService.del(id);
        return  Result.success();

    }
}
