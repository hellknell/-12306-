package com.heyu.train.business.controller.web;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.heyu.train.business.req.ConfirmOrderDoReq;
import com.heyu.train.business.service.ConfirmOrderService;
import com.heyu.train.common.constant.BizExceptionEnum;
import com.heyu.train.common.resp.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Tag(name = "确认订单")
public class ConfirmOrderDoController {
    final ConfirmOrderService confirmOrderService;

    @SentinelResource(value = "confirmOrderDo", blockHandler = "blockHandler")
    @Operation(summary = "提交订单")
    @PostMapping("/do")
    public Result<Void> login(@RequestBody @Valid ConfirmOrderDoReq req) throws InterruptedException {
        confirmOrderService.doConfirm(req);
        return Result.success();
    }

    public static Result blockHandler(ConfirmOrderDoReq req, BlockException e) {
        log.info("请求被限流:{}", req);
        Result<Object> result = new Result<>();
        result.setSuccess(false);
        result.setMsg(BizExceptionEnum.FLOW_EXCEPTION.getMessage());
        return result;
    }
}
