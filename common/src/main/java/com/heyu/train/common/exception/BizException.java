package com.heyu.train.common.exception;

import com.heyu.train.common.constant.BizExceptionEnum;

public class BizException extends RuntimeException {

    private BizExceptionEnum e;

    public BizException(BizExceptionEnum bizExceptionEnum) {
        this.e = bizExceptionEnum;
    }

    public BizExceptionEnum getE() {
        return e;
    }

    public void setE(BizExceptionEnum e) {
        this.e = e;
    }

    /**
     * 不写入堆栈信息,提高性能
     *
     * @return
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
