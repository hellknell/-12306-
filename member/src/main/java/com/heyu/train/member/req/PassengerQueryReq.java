package com.heyu.train.member.req;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/4/5 20:42
 */
@Data
public class PassengerQueryReq {
    @ApiModelProperty(value = "会员ID")
    private Long memberId;

    @Max(value = 30,message = "每页最大100条")
    private int  pageSize;
    private int  pageNum;

}
