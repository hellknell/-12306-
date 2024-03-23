package com.heyu.train.member.controller;

import com.heyu.train.member.domain.Member;
import com.heyu.train.member.service.MemberSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/21 15:12
 */
@RestController
@RequiredArgsConstructor
public class TestController {
    final MemberSevice memberSevice;
    @GetMapping("/hello")
    public Member hello(){
        return memberSevice.serchMembers();
    }
}
