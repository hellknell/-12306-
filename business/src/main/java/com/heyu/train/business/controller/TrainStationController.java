package com.heyu.train.business.controller;
import com.heyu.train.generator.generator.help.PageInfo;
import com.heyu.train.business.resp.TrainStationQueryResp;
import com.heyu.train.common.resp.Result;
import com.heyu.train.business.req.TrainStationQueryReq;
import com.heyu.train.business.req.TrainStationSaveReq;
import com.heyu.train.business.service.TrainStationService;
import com.heyu.train.business.service.TrainStationService;
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
@Api(tags = "乘客管理")
@Validated
@RequestMapping("/admin/train-station")
@RequiredArgsConstructor
public class TrainStationController {
    final TrainStationService trainStationService;

    @ApiOperation(value = "新增/保存乘客")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody TrainStationSaveReq req) {
        trainStationService.save(req);
        return Result.success();
    }
    @ApiOperation("查询乘客")
    @GetMapping("/query-list")
    public   Result<PageInfo<TrainStationQueryResp>> queryList(@Valid TrainStationQueryReq req) {
        return Result.success(trainStationService.queryList(req));
    }
    @ApiOperation("删除乘客")
    @DeleteMapping("/del/{id}")
    public   Result<Void> queryList(@PathVariable  Long id) {
        trainStationService.del(id);
        return  Result.success();

    }
}
