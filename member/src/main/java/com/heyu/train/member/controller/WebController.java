package com.heyu.train.member.controller;

import cn.hutool.core.util.RandomUtil;
import com.heyu.train.common.resp.Result;
import com.heyu.train.member.domain.ValidateCodeCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/28 18:00
 */
@RestController
@Api(tags = "验证码")
@RequestMapping("/v1/captcha")
public class WebController {
    @ApiOperation("获取验证码")
    @GetMapping("/init")
    public Result<String> init(HttpServletRequest request) {

        String code = RandomUtil.randomString(5) + System.currentTimeMillis();
        ValidateCodeCache.setCodeCache(request.getParameter("key"), code);
        return Result.success(code);
    }

    @ApiOperation("验证验证码")
    @GetMapping("/verification")
    public Result<Object> verification(HttpServletRequest request) {
        if (ValidateCodeCache.verify(request.getParameter("key"), request.getParameter("code"))) {
            return Result.success();
        }
        return new Result<>();
    }

}