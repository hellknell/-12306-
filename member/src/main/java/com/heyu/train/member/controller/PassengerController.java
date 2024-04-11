package com.heyu.train.member.controller;

import com.heyu.train.common.generator.help.PageInfo;
import com.heyu.train.common.resp.PassengerQueryResp;
import com.heyu.train.common.resp.Result;
import com.heyu.train.member.req.PassengerQueryReq;
import com.heyu.train.member.req.PassengerReq;
import com.heyu.train.member.service.PassengerSevice;
import context.LoginMemberContext;
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
@RequestMapping("/passenger")
@RequiredArgsConstructor
public class PassengerController {
    final PassengerSevice passengerSevice;

    @ApiOperation(value = "新增/保存乘客")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody PassengerReq req) {
        req.setMemberId(LoginMemberContext.getId());
        passengerSevice.save(req);
        return Result.success();
    }
    @ApiOperation("查询乘客")
    @GetMapping("/queryList")
    public   Result<PageInfo<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req) {
        req.setMemberId(LoginMemberContext.getId());
        return Result.success(passengerSevice.queryList(req));

    }
    @ApiOperation("删除乘客")
    @DeleteMapping("/del/{id}")
    public   Result<Void> queryList(@PathVariable  Long id) {
        passengerSevice.del(id);
        return  Result.success();

    }
}
