package com.heyu.train.business.controller.web;

import com.heyu.train.business.req.ConfirmOrderDoReq;
import com.heyu.train.business.service.ConfirmOrderService;
import com.heyu.train.common.resp.Result;
import io.swagger.v3.oas.annotations.Operation;
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
 * 日期：2024/6/8 1:23
 */
@RestController
@RequestMapping("/confirm-order")
@RequiredArgsConstructor
@Validated
public class ConfirmOrderDoController {
    final ConfirmOrderService confirmOrderService;
    @Operation(summary = "提交订单")
    @PostMapping("/do")
    public Result<Void> login(@RequestBody @Valid ConfirmOrderDoReq req ) throws NoSuchFieldException, IllegalAccessException {
        confirmOrderService.doConfirm(req);
        return Result.success();
    }
}
