package com.heyu.train.common.resp;

import com.heyu.train.common.constant.ResultType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 18:13
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private boolean success = true;
    private T data;
    private String msg;
    private String code;

    public static <T> Result<T> success(ResultType type, T data) {
        Result<T> result = new Result<>();
        result.setCode(type.getCode());
        result.setMsg(type.getMessage());
        result.setData(data);
        return result;
    }
    public static <T> Result<T> error(ResultType type, String msg) {
        Result<T> result = new Result<>();
        result.setCode(type.getCode());
        result.setMsg(msg);
        return result;
    }
}
