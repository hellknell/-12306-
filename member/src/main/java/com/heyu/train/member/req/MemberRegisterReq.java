package com.heyu.train.member.req;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:34
 */
@Data
public class MemberRegisterReq {
    @ApiModelProperty(value = "手机号",required = true)
    @NotBlank(message = "手机号不能为空")
    private  String mobile;
}
