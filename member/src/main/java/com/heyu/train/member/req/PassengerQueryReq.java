package com.heyu.train.member.req;

import com.heyu.train.common.req.PageReq;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Max;
import lombok.Data;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/4/5 20:42
 */
@Data
public class PassengerQueryReq extends PageReq {
    @ApiModelProperty(value = "会员ID")
    private Long memberId;


}
