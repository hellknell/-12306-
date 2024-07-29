package com.heyu.train.common.feign;

import com.heyu.train.common.req.DailyTrainTicketQueryReq;
import com.heyu.train.common.resp.*;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Component
@FeignClient(name = "business")
public interface MemberFeign {
    @GetMapping("/business/daily-train-ticket/query-list")
    Result<PageInfo<DailyTrainTicketQueryResp>> queryDailyTrainTicket(@Valid @SpringQueryMap DailyTrainTicketQueryReq req);

    @GetMapping("/business/station/query-station")
    Result<List<StationQueryResp>> queryList();

    @GetMapping("/business/train/query-train-code")
    Result<List<TrainQueryResp>> queryTrainCodes();

}

