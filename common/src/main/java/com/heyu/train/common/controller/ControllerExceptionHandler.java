package com.heyu.train.common.controller;

import com.heyu.train.common.exception.BizException;
import com.heyu.train.common.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 19:14
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e) throws Exception {
//        log.info("全局事务Id:{}", RootContext.getXID());
        log.error("系统异常，请联系管理员", e);
//        if (StrUtil.isNotBlank(RootContext.getXID())) {
//            throw new Exception(e);
//        }
        Result<Object> result = new Result<>();
        result.setMsg("系统出现异常,请联系管理员");
        result.setSuccess(false);
        return result;
    }
    @ExceptionHandler(BizException.class)
    public Result<Object> handleException(BizException e) {
        log.error("系统异常，请联系管理员", e);
        Result<Object> result = new Result<>();
        result.setMsg(e.getE().getMessage());
        result.setSuccess(false);
        result.setCode(e.getE().getCode());
        return result;
    }
    @ExceptionHandler(RuntimeException.class)
    public Result<Object> handleException(RuntimeException e){
        throw e;
    }

    @ExceptionHandler(BindException.class)
    public Result<Object> handleException(BindException e) {
        log.error("参数校验异常，请联系管理员", e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        Result<Object> result = new Result<>();
        result.setMsg(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        result.setSuccess(false);
        result.setCode("500");
        return result;
    }
}
