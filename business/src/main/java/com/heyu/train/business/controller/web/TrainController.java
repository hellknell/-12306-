package com.heyu.train.business.controller.web;

import com.heyu.train.business.service.TrainService;
import com.heyu.train.common.req.TrainQueryReq;
import com.heyu.train.common.resp.PageInfo;
import com.heyu.train.common.resp.Result;
import com.heyu.train.common.resp.TrainQueryResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController(value = "Train")
@Tag(name = "火车管理")
@Validated
@RequestMapping("/train")
@RequiredArgsConstructor
public class TrainController {
    final TrainService trainService;

    @Operation(summary = "查询火车")
    @GetMapping("/query-list")
    public Result<PageInfo<TrainQueryResp>> queryList(@Valid TrainQueryReq req) {
        return Result.success(trainService.queryList(req));
    }

    @Operation(summary = "查询火车")
    @GetMapping("query-train-code")
    public Result<List<com.heyu.train.common.resp.TrainQueryResp>> queryTrainCodes() {
        return Result.success(trainService.queryTrainCodes());
    }


}
