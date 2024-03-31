package com.heyu.train.member.controller;

import com.heyu.train.common.resp.Result;
import com.heyu.train.member.req.PassengerReq;
import com.heyu.train.member.service.PassengerSevice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController
@Validated
@RequestMapping("/passenger")
@RequiredArgsConstructor
public class PassengerController {
    final PassengerSevice passengerSevice;
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody PassengerReq req) {
        passengerSevice.save(req);
        return Result.success();
    }
}
