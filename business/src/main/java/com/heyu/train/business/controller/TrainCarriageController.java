package com.heyu.train.business.controller;

import com.heyu.train.business.req.TrainCarriageQueryReq;
import com.heyu.train.business.req.TrainCarriageSaveReq;
import com.heyu.train.business.resp.TrainCarriageQueryResp;
import com.heyu.train.business.service.TrainCarriageService;
import com.heyu.train.common.resp.Result;
import com.heyu.train.generator.generator.help.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "车厢管理")
@Validated
@RequestMapping("/admin/train-carriage")
@RequiredArgsConstructor
public class TrainCarriageController {
    final TrainCarriageService trainCarriageService;

    @ApiOperation(value = "新增/保存乘客")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody TrainCarriageSaveReq req) {
        trainCarriageService.save(req);
        return Result.success();
    }

    @ApiOperation("查询车厢")
    @GetMapping("/query-list")
    public Result<PageInfo<TrainCarriageQueryResp>> queryList(@Valid TrainCarriageQueryReq req) {
        return Result.success(trainCarriageService.queryList(req));
    }

    @ApiOperation("删除车厢")
    @DeleteMapping("/del/{id}")
    public Result<Void> queryList(@PathVariable Long id) {
        trainCarriageService.del(id);
        return Result.success();

    }
}
