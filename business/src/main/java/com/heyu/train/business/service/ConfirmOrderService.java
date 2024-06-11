package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.heyu.train.business.domain.*;
import com.heyu.train.business.enums.ConfirmOrderEnum;
import com.heyu.train.business.enums.SeatTypeEnum;
import com.heyu.train.business.enums.TrainSeatEnum;
import com.heyu.train.business.mapper.ConfirmOrderMapper;
import com.heyu.train.business.mapper.DailyTrainMapper;
import com.heyu.train.business.mapper.DailyTrainTicketMapper;
import com.heyu.train.business.req.ConfirmOrderDoReq;
import com.heyu.train.business.req.ConfirmOrderQueryReq;
import com.heyu.train.business.req.ConfirmOrderTicketReq;
import com.heyu.train.common.constant.BizExceptionEnum;
import com.heyu.train.common.exception.BizException;
import com.heyu.train.common.resp.ConfirmOrderQueryResp;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.generator.generator.help.MyBatisWrapper;
import com.heyu.train.generator.generator.help.PageInfo;
import context.LoginMemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Strings;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.heyu.train.business.domain.ConfirmOrderField.*;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 12:53
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmOrderService {
    final ConfirmOrderMapper confirmOrderMapper;
    final DailyTrainMapper dailyTrainMapper;
    final DailyTrainTicketMapper dailyTrainTicketMapper;

    public void save(ConfirmOrderDoReq req) {
        ConfirmOrder confirmOrder = BeanUtil.copyProperties(req, ConfirmOrder.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(confirmOrder.getId())) {
            confirmOrder.setCreateTime(now);
            confirmOrder.setUpdateTime(now);
            confirmOrder.setId(SnowFlask.getSnowFlaskId());
            ConfirmOrder pass = BeanUtil.copyProperties(req, ConfirmOrder.class);
            confirmOrderMapper.insert(pass);
        } else {
            confirmOrder.setUpdateTime(now);
            confirmOrderMapper.updateByPrimaryKey(confirmOrder);
        }

    }

    public PageInfo<ConfirmOrderQueryResp> queryList(ConfirmOrderQueryReq req) {
        MyBatisWrapper<ConfirmOrderQueryResp> wrapper = new MyBatisWrapper<>();
        wrapper.select(Id, MemberId, Date, TrainCode, Start, TrainCode, End, DailyTrainTicketId, Status);
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());
        int total = confirmOrderMapper.list(wrapper).size();
        List<ConfirmOrder> list = confirmOrderMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        List<ConfirmOrderQueryResp> resp = BeanUtil.copyToList(list, ConfirmOrderQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);
    }

    public void del(Long id) {
        confirmOrderMapper.deleteByPrimaryKey(id);
    }

    public void doConfirm(ConfirmOrderDoReq req) {
        MyBatisWrapper<DailyTrain> wrapper = new MyBatisWrapper<>();
        wrapper.select(DailyTrainField.Id).whereBuilder().andEq(DailyTrainField.setDate(req.getDate())).andEq(DailyTrainField.setCode(req.getTrainCode()));
        DailyTrain dailyTrain = dailyTrainMapper.topOne(wrapper);
        if (Objects.isNull(dailyTrain)) {
            throw new BizException(BizExceptionEnum.NO_TRAINS);
        }
        MyBatisWrapper<DailyTrainTicket> wrapper01 = new MyBatisWrapper<>();
        wrapper01.select(DailyTrainTicketField.Id).whereBuilder().andEq(DailyTrainTicketField.setDate(req.getDate())).andEq(DailyTrainTicketField.setTrainCode(req.getTrainCode())).andEq(DailyTrainTicketField.setStart(req.getStart())).andEq(DailyTrainTicketField.setEnd(req.getEnd()));
        DailyTrainTicket dailyTrainTicket = dailyTrainTicketMapper.topOne(wrapper01);
        if (Objects.isNull(dailyTrainTicket)) {
            throw new BizException(BizExceptionEnum.NO_TICKETS);
        }
        List<ConfirmOrderTicketReq> lists = req.getTickets();
        //插入订单表
        ConfirmOrder confirmOrder = BeanUtil.copyProperties(req, ConfirmOrder.class);
        DateTime now = DateTime.now();
        confirmOrder.setId(SnowFlask.getSnowFlaskId());
        confirmOrder.setUpdateTime(now);
        confirmOrder.setMemberId(LoginMemberContext.getId());
        confirmOrder.setCreateTime(now);
        confirmOrder.setStatus(ConfirmOrderEnum.INIT.getCode());
        confirmOrder.setTickets(JSONUtil.toJsonStr(req.getTickets()));
        confirmOrderMapper.insert(confirmOrder);
        //查询余票
        MyBatisWrapper<DailyTrainTicket> tikcetWrapper = new MyBatisWrapper<>();
        tikcetWrapper.select(DailyTrainTicketField.TrainCode, DailyTrainTicketField.Yw, DailyTrainTicketField.Ydz, DailyTrainTicketField.Edz, DailyTrainTicketField.Rw).whereBuilder().andEq(DailyTrainTicketField.setDate(req.getDate())).andEq(DailyTrainTicketField.setTrainCode(req.getTrainCode())).andEq(DailyTrainTicketField.setStart(req.getStart())).andEq(DailyTrainTicketField.setEnd(req.getEnd()));
        DailyTrainTicket dbTicket = dailyTrainTicketMapper.topOne(tikcetWrapper);
        log.info("ticket信息:{}", dbTicket);
        //预扣减余票
        reduceTickets(req, dbTicket);
//模拟出选座,并计算机出牌偏移量
        if (StrUtil.isNotBlank(lists.get(0).getSeat())) {
            log.info("本次有选座");
            List<String> colsByType = SeatTypeEnum.getColsByType(lists.get(0).getSeatTypeCode());
            ArrayList<String> seats = new ArrayList<>();
            for (int i = 1; i <= 2; i++) {
                for (String col : colsByType)
                    seats.add(col + i);
            }
            log.info("选座信息:{}", seats);
            ArrayList<Integer> absoluteCol = new ArrayList<>();
            for (ConfirmOrderTicketReq ticketReq : lists) {
                int i = seats.indexOf(ticketReq.getSeat());
                absoluteCol.add(i);
            }
            log.info("绝对选座:{}", absoluteCol);
            ArrayList<Integer> offset = new ArrayList<>();
            for (int i = 0; i < absoluteCol.size(); i++) {
                int j = absoluteCol.get(i) - absoluteCol.get(0);
                offset.add(j);
            }
            log.info("偏移量:{}", offset);
        } else {
            log.info("本次没有选座");
        }
    }

    private static void reduceTickets(ConfirmOrderDoReq req, DailyTrainTicket dbTicket) {
        List<ConfirmOrderTicketReq> tickets = req.getTickets();
        Integer i = null;
        Field field = null;
        for (ConfirmOrderTicketReq ticketReq : tickets) {
            String seatTypeCode = ticketReq.getSeatTypeCode();
            TrainSeatEnum seatTypeEnum = EnumUtil.getBy(TrainSeatEnum::getCode, seatTypeCode);
            StringBuffer sb = new StringBuffer(seatTypeEnum.name());
            String seatType = Strings.toLowerCase(sb.toString());
            try {
                field = DailyTrainTicket.class.getDeclaredField(seatType);
                field.setAccessible(true);
                i = (Integer) field.get(dbTicket);
                // 设置值
                i--;
                if (i < 0) {
                    throw new BizException(BizExceptionEnum.TICKETS_LIMITS);
                }
                field.set(dbTicket, i);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
