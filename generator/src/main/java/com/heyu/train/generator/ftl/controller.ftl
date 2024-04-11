package com.heyu.train.member.controller;

import com.heyu.train.common.generator.help.PageInfo;
import com.heyu.train.common.resp.${Domain}QueryResp;
import com.heyu.train.common.resp.Result;
import com.heyu.train.member.req.${Domain}QueryReq;
import com.heyu.train.member.req.${Domain}Req;
import com.heyu.train.member.service.${Domain}Service;
import com.heyu.train.member.service.${Domain}Service;
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
@RequestMapping("/${do_main}")
@RequiredArgsConstructor
public class ${Domain}Controller {
    final ${Domain}Service ${domain}Service;

    @ApiOperation(value = "新增/保存乘客")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody ${Domain}Req req) {
        req.setMemberId(LoginMemberContext.getId());
        ${domain}Service.save(req);
        return Result.success();
    }
    @ApiOperation("查询乘客")
    @GetMapping("/queryList")
    public   Result<PageInfo<${Domain}QueryResp>> queryList(@Valid ${Domain}QueryReq req) {
        req.setMemberId(LoginMemberContext.getId());
        return Result.success(${domain}Service.queryList(req));

    }
    @ApiOperation("删除乘客")
    @DeleteMapping("/del/{id}")
    public   Result<Void> queryList(@PathVariable  Long id) {
        ${domain}Service.del(id);
        return  Result.success();

    }
}
