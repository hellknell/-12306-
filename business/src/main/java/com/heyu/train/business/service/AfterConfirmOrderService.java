package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONUtil;
import com.heyu.train.business.domain.ConfirmOrder;
import com.heyu.train.business.domain.ConfirmOrderTicket;
import com.heyu.train.business.domain.DailyTrainSeat;
import com.heyu.train.business.domain.DailyTrainTicket;
import com.heyu.train.business.enums.ConfirmOrderEnum;
import com.heyu.train.business.mapper.ConfirmOrderMapper;
import com.heyu.train.business.mapper.DailyTrainSeatMapper;
import com.heyu.train.business.mapper.DailyTrainTicketMapper;
import com.heyu.train.business.req.ConfirmOrderTicketReq;
import com.heyu.train.common.feign.BusinessFeign;
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
    final BusinessFeign businessFeign;
    final ConfirmOrderMapper confirmOrderMapper;

    @Transactional
    public void afterConfirm(DailyTrainTicket dailyTrainTicket, List<DailyTrainSeat> seats, List<ConfirmOrderTicketReq> tickets, ConfirmOrder confirmOrder) {
        DateTime now = DateTime.now();
        List<ConfirmOrderTicket> lists = new ArrayList<>();
        for (int i = 0; i < seats.size(); i++) {
            ConfirmOrderTicketReq confirmOrderTicketReq = tickets.get(i);
            DailyTrainSeat seat = seats.get(i);
            DailyTrainSeat newSeat = new DailyTrainSeat();
            newSeat.setId(seat.getId());
            newSeat.setUpdateTime(now);
            newSeat.setSell(seat.getSell());
            dailyTrainSeatMapper.updateByPrimaryKeySelective(newSeat);
            //预扣减相关的车票库存
            Integer startIndex = dailyTrainTicket.getStartIndex();
            Integer endIndex = dailyTrainTicket.getEndIndex();
            Integer minStartindex = startIndex;
            Integer maxStartindex = endIndex - 1;
            Integer minEndindex = startIndex + 1;
            Integer maxEndIndex = seat.getSell().length() + 1;
            char[] sellArray = seat.getSell().toCharArray();
            for (int j = startIndex; j >= 0; j--) {
                if (j == 0) {
                    minStartindex = 0;
                    break;
                }
                char c = sellArray[j];
                if (c == '1') {
                    minStartindex = j + 1;
                    break;
                }
            }
            for (int j = endIndex; j < seat.getSell().length(); j++) {
                char c = sellArray[j];
                if (c == '1') {
                    maxEndIndex = j;
                    break;
                }
            }
            dailyTrainTicketMapper.updateBySell(dailyTrainTicket.getDate(), dailyTrainTicket.getTrainCode(), seat.getSeatType(), minStartindex, maxStartindex, minEndindex, maxEndIndex);
            MemberTicketReq ticket = new MemberTicketReq();
            ticket.setDate(dailyTrainTicket.getDate());
            ticket.setMemberId(LoginMemberContext.getId());
            ticket.setPassengerId(tickets.get(i).getPassengerId());
            ticket.setPassengerName(tickets.get(i).getPassengerName());
            ticket.setTrainCode(dailyTrainTicket.getTrainCode());
            ticket.setSeatType(seat.getSeatType());
            ticket.setCol(seat.getCol());
            ticket.setRow(seat.getRow());
            ticket.setStart(dailyTrainTicket.getStart());
            ticket.setStartTime(dailyTrainTicket.getStartTime());
            ticket.setEnd(dailyTrainTicket.getEnd());
            ticket.setEndTime(dailyTrainTicket.getEndTime());
            ticket.setCarriageIndex(seat.getCarriageIndex());
            businessFeign.saveTicket(ticket);
            ConfirmOrderTicket confirmOrderTicket1 = BeanUtil.copyProperties(confirmOrderTicketReq, ConfirmOrderTicket.class);
            confirmOrderTicket1.setRow(seat.getRow());
            confirmOrderTicket1.setCol(seat.getCol());
            confirmOrderTicket1.setCarriageIndex(seat.getCarriageIndex());
            lists.add(confirmOrderTicket1);
        }
        ConfirmOrder dbConfirmOrder = new ConfirmOrder();
        dbConfirmOrder.setId(confirmOrder.getId());
        dbConfirmOrder.setStatus(ConfirmOrderEnum.SUCCESS.getCode());
        dbConfirmOrder.setUpdateTime(now);
        log.info(JSONUtil.toJsonStr(lists));
        dbConfirmOrder.setTickets(JSONUtil.toJsonStr(lists));
        confirmOrderMapper.updateByPrimaryKeySelective(dbConfirmOrder);
    }
}
