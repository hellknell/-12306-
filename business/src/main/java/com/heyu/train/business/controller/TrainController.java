package com.heyu.train.business.controller;

import com.heyu.train.business.req.TrainQueryReq;
import com.heyu.train.business.req.TrainSaveReq;
import com.heyu.train.business.resp.TrainQueryResp;
import com.heyu.train.business.service.TrainService;
import com.heyu.train.common.resp.Result;
import com.heyu.train.generator.generator.help.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController
@Api(tags = "火车管理")
@Validated
@RequestMapping("/admin/train")
@RequiredArgsConstructor
public class TrainController {
    final TrainService trainService;

    @ApiOperation(value = "新增/保存火车")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody TrainSaveReq req) {
        trainService.save(req);
        return Result.success();
    }

    @ApiOperation("查询乘客")
    @GetMapping("/query-list")
    public Result<PageInfo<TrainQueryResp>> queryList(@Valid TrainQueryReq req) {
        return Result.success(trainService.queryList(req));

    }

    @ApiOperation("查询车次")
    @GetMapping("query-train-code")
    public Result<List<TrainQueryResp>> queryTrainCodes() {
        return Result.success(trainService.queryTrainCodes());
    }


    @ApiOperation("删除乘客")
    @DeleteMapping("/del/{id}")
    public Result<Void> queryList(@PathVariable Long id) {
        trainService.del(id);
        return Result.success();

    }
}
