package com.heyu.train.business.controller;

import com.heyu.train.business.req.StationQueryReq;
import com.heyu.train.business.req.StationSaveReq;
import com.heyu.train.business.resp.StationQueryResp;
import com.heyu.train.business.service.StationService;
import com.heyu.train.common.resp.Result;
import com.heyu.train.generator.generator.help.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "车站管理")
@Validated
@RequestMapping("/admin/station")
@RequiredArgsConstructor
public class StationController {
    final StationService stationService;

    @ApiOperation(value = "新增/保存车站")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody StationSaveReq req) {
        stationService.save(req);
        return Result.success();
    }
    @ApiOperation("查询车站")
    @GetMapping("/query-list")
    public Result<PageInfo<StationQueryResp>> queryList(@Valid StationQueryReq req) {
        return Result.success(stationService.queryList(req));
    }
    @ApiOperation("删除车站")
    @DeleteMapping("/del/{id}")
    public Result<Void> queryList(@PathVariable Long id) {
        stationService.del(id);
        return Result.success();
    }

    @ApiOperation("查询车站")
    @GetMapping("/query-station")
    public Result<List<StationQueryResp>> queryStation() {
        return Result.success(stationService.queryStation());
    }
}
