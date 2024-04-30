package com.heyu.train.business.req;

import com.heyu.train.common.resp.req.PageReq;
import lombok.Data;
@Data
public class TrainSeatQueryReq extends PageReq {
private String trainCode;
@Override
public String toString() {
return "TrainSeatQueryReq{" +
"} " + super.toString();
}
}
