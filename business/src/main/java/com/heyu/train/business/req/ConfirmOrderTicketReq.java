package com.heyu.train.business.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * 功能:
 * 作者:何宇
 * 日期：2024/6/7 17:04
 */
@Data
public class ConfirmOrderTicketReq {
    @NotNull(message = "乘客id不能为空")
    private Long passengerId;
    @NotBlank(message = "乘客姓名不能为空")
    private String passengerName;
    @NotBlank(message = "乘客类型不能为空")
    private String passengerType;
    @NotBlank(message = "乘客身份证号不能为空")
    private String passengerIdCard;
    private String seatTypeCode;
    private String seat;
}
