package com.heyu.train.business.controller;

import com.heyu.train.business.req.TrainSeatQueryReq;
import com.heyu.train.business.req.TrainSeatSaveReq;
import com.heyu.train.business.resp.TrainSeatQueryResp;
import com.heyu.train.business.service.TrainSeatService;
import com.heyu.train.common.resp.Result;
import com.heyu.train.generator.generator.help.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController
@Tag(name= "座位管理")
@Validated
@RequestMapping("/admin/train-seat")
@RequiredArgsConstructor
public class TrainSeatController {
    final TrainSeatService trainSeatService;

    @Operation(summary= "新增/保存车座")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody TrainSeatSaveReq req) {
        trainSeatService.save(req);
        return Result.success();
    }
    @Operation(summary = "查询车座")
    @GetMapping("/query-list")
    public   Result<PageInfo<TrainSeatQueryResp>> queryList(@Validated TrainSeatQueryReq req) {
        return Result.success(trainSeatService.queryList(req));
    }
    @Operation(summary = "删除车座")
    @DeleteMapping("/del/{id}")
    public   Result<Void> queryList(@PathVariable  Long id) {
        trainSeatService.del(id);
        return  Result.success();

    }
    @Operation(summary = "查询列号")
    @GetMapping("/query-seat-col/{type}")
    public   Result<List<String>> querySeatCol(@NotBlank @PathVariable String type) {
        return Result.success(trainSeatService.querySeatCol(type));

    }

}
