package com.heyu.train.common.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/4/6 18:34
 */
@Data
public class PageReq {
    @NotNull(message = "页码不能为空")
    private int  pageSize;

    @NotNull(message = "页数不能为空")
    @Max(value = 100, message = "页数不能超过100")
    private int  pageNum;
}
