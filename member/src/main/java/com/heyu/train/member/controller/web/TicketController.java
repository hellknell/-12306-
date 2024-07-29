package com.heyu.train.member.controller.web;

import com.heyu.train.common.req.MemberTicketReq;
import com.heyu.train.common.resp.Result;
import com.heyu.train.generator.generator.help.PageInfo;
import com.heyu.train.member.req.TicketQueryReq;
import com.heyu.train.member.resp.TicketQueryResp;
import com.heyu.train.member.service.TicketService;
import context.LoginMemberContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController(value = "TicketWebController")
@Tag(name = "订单管理")
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    final TicketService ticketService;
    @Operation(summary = "新增/保存车票")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody MemberTicketReq req) throws Exception {
        ticketService.save(req);
        return Result.success();
    }
    @Operation(summary = "查询车票")
    @GetMapping("/query-list")
    public Result<PageInfo<TicketQueryResp>> queryList(@Valid TicketQueryReq req) {

        return Result.success(ticketService.queryList(req));
    }
    @Operation(summary = "删除车票")
    @DeleteMapping("/del/{id}")
    public Result<Void> queryList(@PathVariable Long id) {
        ticketService.del(id);
        return Result.success();
    }
}
