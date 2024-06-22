package com.heyu.train.member.controller;

import com.heyu.train.common.feign.MemberFeign;
import com.heyu.train.common.resp.Result;
import com.heyu.train.common.resp.StationQueryResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController(value = "station")
@Tag(name = "车站管理")
@Validated
@RequestMapping("/station")
@RequiredArgsConstructor
public class StationController {
    final MemberFeign memberFeign;
    @Operation(summary = "查询车站")
    @GetMapping("/query-station")
    public Result<List<StationQueryResp>> queryStation() {
        return memberFeign.queryList();
    }
}
