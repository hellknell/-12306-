package com.heyu.train.member.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/24 19:09
 */
@Data
public class MemberLoginReq {
    @Schema(name = "手机号")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入正确的手机格式")
    private String mobile;
    @Schema(name = "验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^[1-9]{4}$", message = "请输入正确的验证码格式")
    private String code;
}
