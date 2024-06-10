package com.heyu.train.business.controller.web;

import com.heyu.train.business.req.StationQueryReq;
import com.heyu.train.business.service.StationService;
import com.heyu.train.common.resp.PageInfo;
import com.heyu.train.common.resp.Result;
import com.heyu.train.common.resp.StationQueryResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController(value = "station")
@Tag(name = "车站管理")
@Validated
@RequestMapping("/station")
@RequiredArgsConstructor
public class StationController {
    final StationService stationService;


    @Operation(summary = "查询车站")
    @GetMapping("/query-list")
    public Result<PageInfo<com.heyu.train.common.resp.StationQueryResp>> queryList(@Valid StationQueryReq req) {
        return Result.success(stationService.queryList(req));
    }

    @Operation(summary = "查询车站")
    @GetMapping("/query-station")
    public Result<List<StationQueryResp>> queryStation() {
        return Result.success(stationService.queryStation());
    }
}
