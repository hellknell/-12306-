package com.heyu.train.member.controller;

import com.heyu.train.common.resp.Result;
import com.heyu.train.member.req.MemberRegisterReq;
import com.heyu.train.member.service.MemberSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController
@RequiredArgsConstructor
public class MemberController {
    final MemberSevice memberSevice;
    @PostMapping("/register")
    public Result<Long> register(MemberRegisterReq req) {
        return memberSevice.register(req);
    }
}
