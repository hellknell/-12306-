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
@FeignClient(name = "business-service", url = "http://localhost:8002/business")
public interface MemberFeign {
    @GetMapping("/daily-train-ticket/query-list")
    Result<PageInfo<DailyTrainTicketQueryResp>> queryDailyTrainTicket(@Valid @SpringQueryMap DailyTrainTicketQueryReq req);
    @GetMapping("/station/query-station")
    Result<List<StationQueryResp>> queryList();

    @GetMapping("/train/query-train-code")
    Result<List<TrainQueryResp>> queryTrainCodes();

}

