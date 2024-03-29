package com.heyu.train.member.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/24 19:09
 */
@Data
public class MemberSendCodeReq {
    @NotBlank(message = "手机号不能为空11")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入正确的手机格式")
    private String mobile;
}
