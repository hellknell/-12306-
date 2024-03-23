package com.heyu.train.common.controller;

import com.heyu.train.common.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 19:14
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result<Object>  handleException(Exception e){
        log.error("系统异常，请联系管理员",e);
        Result<Object> result = new Result<>();
        result.setMsg(e.getMessage());
        return result;
    }


}
