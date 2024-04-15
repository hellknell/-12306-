package com.heyu.train.${module}.controller;
import com.heyu.train.generator.generator.help.PageInfo;
import com.heyu.train.${module}.resp.${Domain}QueryResp;
import com.heyu.train.common.resp.Result;
import com.heyu.train.${module}.req.${Domain}QueryReq;
import com.heyu.train.${module}.req.${Domain}SaveReq;
import com.heyu.train.${module}.service.${Domain}Service;
import com.heyu.train.${module}.service.${Domain}Service;
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
    public Result<Void> login(@Valid @RequestBody ${Domain}SaveReq req) {
        ${domain}Service.save(req);
        return Result.success();
    }
    @ApiOperation("查询乘客")
    @GetMapping("/query-list")
    public   Result<PageInfo<${Domain}QueryResp>> queryList(@Valid ${Domain}QueryReq req) {
        return Result.success(${domain}Service.queryList(req));

    }
    @ApiOperation("删除乘客")
    @DeleteMapping("/del/{id}")
    public   Result<Void> queryList(@PathVariable  Long id) {
        ${domain}Service.del(id);
        return  Result.success();

    }
}
