package com.heyu.train.business.req;

import com.heyu.train.common.resp.req.PageReq;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
public class DailyTrainTicketQueryReq extends PageReq implements Serializable {
    private String trainCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String start;
    private String end;

    @Override
    public String toString() {
        return "DailyTrainTicketQueryReq{" +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DailyTrainTicketQueryReq that)) return false;
        return Objects.equals(trainCode, that.trainCode) && Objects.equals(date, that.date) && Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(((DailyTrainTicketQueryReq) o).getPageNum(), that.getPageNum()) && Objects.equals(((DailyTrainTicketQueryReq) o).getPageSize(), that.getPageSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainCode, date, start, end, getPageNum(), getPageSize());
    }
}
