package com.heyu.train.common.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/6/22 15:47
 */
@Data
public class MemberTicket {
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
    private String row;
    private String col;
    private Integer CarriageIndex ;
}
