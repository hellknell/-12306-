package com.heyu.train.member.controller;

import com.heyu.train.common.resp.Result;
import com.heyu.train.member.req.MemberRegisterReq;
import com.heyu.train.member.req.MemberSendCodeReq;
import com.heyu.train.member.service.MemberSevice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController
@Validated
@RequiredArgsConstructor
public class MemberController {
    final MemberSevice memberSevice;
    @PostMapping("/register")
    public Result<Long> register(@Valid MemberRegisterReq req) {
        return Result.success(memberSevice.register(req));
    }
    @GetMapping("/sendCode")
    public Result<Long> sendCode(@Valid MemberSendCodeReq req) {
        memberSevice.sendCode(req);
        return Result.success();
    }
}
