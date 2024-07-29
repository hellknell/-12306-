package com.heyu.train.business.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heyu.train.common.resp.req.PageReq;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
public class DailyTrainQueryReq extends PageReq implements Serializable {
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DailyTrainQueryReq that = (DailyTrainQueryReq) o;
        return Objects.equals(code, that.code) && Objects.equals(date, that.date) && Objects.equals(getPageSize(), that.getPageSize())
                && Objects.equals(getPageNum(), that.getPageNum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code, date, getPageNum(), getPageSize());
    }

    @Override
    public String toString() {
        return "DailyTrainQueryReq{" +
                "} " + super.toString();
    }
}
