package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONUtil;
import com.heyu.train.business.domain.ConfirmOrder;
import com.heyu.train.business.domain.DailyTrainSeat;
import com.heyu.train.business.domain.DailyTrainTicket;
import com.heyu.train.business.enums.ConfirmOrderEnum;
import com.heyu.train.business.mapper.ConfirmOrderMapper;
import com.heyu.train.business.mapper.DailyTrainSeatMapper;
import com.heyu.train.business.mapper.DailyTrainTicketMapper;
import com.heyu.train.business.req.ConfirmOrderTicketReq;
import com.heyu.train.common.feign.BusinessFeign;
import com.heyu.train.common.req.MemberTicket;
import com.heyu.train.common.req.MemberTicketReq;
import context.LoginMemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AfterConfirmOrderService {
    final DailyTrainSeatMapper dailyTrainSeatMapper;
    final DailyTrainTicketMapper dailyTrainTicketMapper;
    final ConfirmOrderMapper confirmOrderMapper;
    final BusinessFeign businessFeign;

    @Transactional
    public void afterConfirm(List<DailyTrainSeat> seats, DailyTrainTicket dailyTrainTicket, List<ConfirmOrderTicketReq> tickets, ConfirmOrder confirmOrder) {
        DateTime now = DateTime.now();
        List<MemberTicket> list = new ArrayList<>();
        for (int j = 0; j < seats.size(); j++) {
            DailyTrainSeat seat = seats.get(j);
            DailyTrainSeat newSeat = new DailyTrainSeat();
            newSeat.setId(seat.getId());
            newSeat.setUpdateTime(now);
            newSeat.setSell(seat.getSell());
            dailyTrainSeatMapper.updateByPrimaryKeySelective(newSeat);
            //影响到其他的余票库存
            Integer startIndex = dailyTrainTicket.getStartIndex();
            Integer endIndex = dailyTrainTicket.getEndIndex();
            char[] sellArray = seat.getSell().toCharArray();
            Integer minStartIndex = 0;
            Integer maxStartIndex = endIndex - 1;
            Integer minEndIndex = startIndex + 1;
            Integer maxEndIndex = seat.getSell().length() + 1;
            for (int i = startIndex; i >= 0; j--) {
                if (i == 0) {
                    minStartIndex = 0;
                }
                char c1 = sellArray[i];
                if (c1 == '1') {
                    minStartIndex = i + 1;
                }
            }
            for (int m = endIndex; m < sellArray.length; j++) {
                char c2 = sellArray[m];
                if (c2 == '1') {
                    maxEndIndex = m;
                }
            }
            log.info("minStartIndex:{},maxStartIndex:{},minEndIndex:{},minEndIndex:{}", minStartIndex, maxStartIndex, minEndIndex, maxEndIndex);
            dailyTrainTicketMapper.updateBySell(dailyTrainTicket.getDate(), dailyTrainTicket.getTrainCode(), seat.getSeatType(), minStartIndex, maxStartIndex, minEndIndex, maxEndIndex);
            log.info("更新余票成功");
            MemberTicket memberTicket1 = BeanUtil.copyProperties(tickets.get(j), MemberTicket.class);
            memberTicket1.setRow(seat.getRow());
            memberTicket1.setCol(seat.getCol());
            memberTicket1.setCarriageIndex(seat.getCarriageSeatIndex());
            list.add(memberTicket1);
            MemberTicketReq ticket = new MemberTicketReq();
            ticket.setCarriageIndex(seat.getCarriageSeatIndex());
            ticket.setStart(dailyTrainTicket.getStart());
            ticket.setEnd(dailyTrainTicket.getEnd());
            ticket.setStartTime(dailyTrainTicket.getStartTime());
            ticket.setEndTime(dailyTrainTicket.getEndTime());
            ticket.setRow(seat.getRow());
            ticket.setCol(seat.getCol());
            ticket.setSeatType(seat.getSeatType());
            ticket.setTrainCode(dailyTrainTicket.getTrainCode());
            ticket.setDate(dailyTrainTicket.getDate());
            ticket.setPassengerId(tickets.get(j).getPassengerId());
            ticket.setPassengerName(tickets.get(j).getPassengerName());
            ticket.setMemberId(LoginMemberContext.getId());
            businessFeign.saveTicket(ticket);
        }
        ConfirmOrder confirmOrder1 = new ConfirmOrder();
        confirmOrder1.setStatus(ConfirmOrderEnum.SUCCESS.getCode());
        confirmOrder1.setId(confirmOrder.getId());
        confirmOrder1.setUpdateTime(now);
        confirmOrder1.setTickets(JSONUtil.toJsonStr(list));
        confirmOrderMapper.updateByPrimaryKeySelective(confirmOrder1);
    }
}
