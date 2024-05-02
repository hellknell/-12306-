package com.heyu.train.business.controller;

import com.heyu.train.business.req.TrainStationQueryReq;
import com.heyu.train.business.req.TrainStationSaveReq;
import com.heyu.train.business.resp.TrainStationQueryResp;
import com.heyu.train.business.service.TrainStationService;
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
@Tag(name = "火车车次管理")
@Validated
@RequestMapping("/admin/train-station")
@RequiredArgsConstructor
public class TrainStationController {
    final TrainStationService trainStationService;

    @Operation(summary= "新增/保存火车车次")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody TrainStationSaveReq req) {
        trainStationService.save(req);
        return Result.success();
    }
    @Operation(summary = "查询火车车次")
    @GetMapping("/query-list")
    public   Result<PageInfo<TrainStationQueryResp>> queryList(@Valid TrainStationQueryReq req) {
        return Result.success(trainStationService.queryList(req));
    }
    @Operation(summary = "删除火车车次")
    @DeleteMapping("/del/{id}")
    public   Result<Void> queryList(@PathVariable  Long id) {
        trainStationService.del(id);
        return  Result.success();

    }
}
