package com.heyu.train.member.req;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
@Data
public class TicketSaveReq {

    /**
    * 车票id
    */
    private Long id;

    /**
    * 会员id
    */
    @NotNull(message = "【会员id】不能为空")
    private Long memberId;

    /**
    * 乘客id
    */
    @NotNull(message = "【乘客id】不能为空")
    private Long passengerId;

    /**
    * 乘客姓名
    */
    private String passengerName;

    /**
    * 日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;

    /**
    * 车次编号
    */
    private String trainCode;

    /**
    * 排号
    */
    private String row;

    /**
    * 列号
    */
    private String col;

    /**
    * 座位类型
    */
    private String seatType;

    /**
    * 出发站
    */
    @NotBlank(message = "【出发站】不能为空")
    private String start;

    /**
    * 到达站
    */
    private String end;

    /**
    * 出发时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    /**
    * 到达时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    /**
    * 车厢号
    */
    private Integer carriageIndex;

    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
    * 新增时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", memberId=").append(memberId);
    sb.append(", passengerId=").append(passengerId);
    sb.append(", passengerName=").append(passengerName);
    sb.append(", date=").append(date);
    sb.append(", trainCode=").append(trainCode);
    sb.append(", row=").append(row);
    sb.append(", col=").append(col);
    sb.append(", seatType=").append(seatType);
    sb.append(", start=").append(start);
    sb.append(", end=").append(end);
    sb.append(", startTime=").append(startTime);
    sb.append(", endTime=").append(endTime);
    sb.append(", carriageIndex=").append(carriageIndex);
    sb.append(", createTime=").append(createTime);
    sb.append(", updateTime=").append(updateTime);
sb.append("]");
return sb.toString();
}
}
