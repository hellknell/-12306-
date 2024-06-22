package com.heyu.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.heyu.train.common.req.MemberTicketReq;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.generator.generator.help.Criteria;
import com.heyu.train.generator.generator.help.MyBatisWrapper;
import com.heyu.train.generator.generator.help.PageInfo;
import com.heyu.train.member.domain.Ticket;
import com.heyu.train.member.mapper.TicketMapper;
import com.heyu.train.member.req.TicketQueryReq;
import com.heyu.train.member.resp.TicketQueryResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.heyu.train.member.domain.TicketField.*;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 12:53
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {
    final TicketMapper ticketMapper;

    public void save(MemberTicketReq req) {
        DateTime now = DateTime.now();
        req.setCreateTime(now);
        req.setUpdateTime(now);
        req.setId(SnowFlask.getSnowFlaskId());
        Ticket pass = BeanUtil.copyProperties(req, Ticket.class);
        ticketMapper.insert(pass);
    }

    public PageInfo<TicketQueryResp> queryList(TicketQueryReq req) {
        MyBatisWrapper<TicketQueryResp> wrapper = new MyBatisWrapper<>();
        Criteria criteria = wrapper.select(Id, MemberId, PassengerId, PassengerName, Date, TrainCode, Row, Col, CarriageIndex, SeatType, Start, StartTime, End, EndTime).whereBuilder();
        if (ObjectUtil.isNull(req.getDate())) {
            criteria.andEq(setDate(req.getDate()));
        }
        if (StrUtil.isNotBlank(req.getCode())) {
            criteria.andEq(setTrainCode(req.getCode()));
        }
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());
        int total = ticketMapper.list(wrapper).size();
        List<Ticket> list = ticketMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        List<TicketQueryResp> resp = BeanUtil.copyToList(list, TicketQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);
    }

    public void del(Long id) {
        ticketMapper.deleteByPrimaryKey(id);
    }

}
