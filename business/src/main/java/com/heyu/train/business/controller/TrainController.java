package com.heyu.train.business.controller;

import com.heyu.train.business.req.TrainQueryReq;
import com.heyu.train.business.req.TrainSaveReq;
import com.heyu.train.business.resp.TrainQueryResp;
import com.heyu.train.business.service.TrainService;
import com.heyu.train.common.resp.Result;
import com.heyu.train.generator.generator.help.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag( name= "火车管理")
@Validated
@RequestMapping("/admin/train")
@RequiredArgsConstructor
public class TrainController {
    final TrainService trainService;

    @Operation(summary= "新增/保存火车")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody TrainSaveReq req) {
        trainService.save(req);
        return Result.success();
    }
    @Operation(summary = "查询火车")
    @GetMapping("/query-list")
    public Result<PageInfo<TrainQueryResp>> queryList(@Valid TrainQueryReq req) {
        return Result.success(trainService.queryList(req));

    }

    @Operation(summary = "查询火车")
    @GetMapping("query-train-code")
    public Result<List<TrainQueryResp>> queryTrainCodes() {
        return Result.success(trainService.queryTrainCodes());
    }


    @Operation(summary = "删除火车")
    @DeleteMapping("/del/{id}")
    public Result<Void> queryList(@PathVariable Long id) {
        trainService.del(id);
        return Result.success();
    }
    @Operation(summary = "生成火车座位",description =  "根据车次和座位数生成座位")
    @GetMapping("gen-train-seat/{trainCode}")
    public void genTrainSeat(@PathVariable String trainCode) {
        trainService.genTrainSeat(trainCode);
    }

}
