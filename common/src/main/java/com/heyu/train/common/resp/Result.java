package com.heyu.train.common.resp;

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

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode("200");
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }
//    public static <T> Result<T> error(String msg) {
//        Result<T> result = new Result<>();
//        result.setCode();
//        result.setMsg(msg);
//        return result;
//    }
}
