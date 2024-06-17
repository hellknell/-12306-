package com.heyu.train.business.service;

import cn.hutool.core.date.DateTime;
import com.heyu.train.business.domain.DailyTrainSeat;
import com.heyu.train.business.mapper.DailyTrainSeatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AfterConfirmOrderService {
    final DailyTrainSeatMapper dailyTrainSeatMapper;

    //    final TransactionTemplate transactionTemplate;
    @Transactional
    public void afterConfirm(List<DailyTrainSeat> seats) {
        DateTime now = DateTime.now();
        for (DailyTrainSeat seat : seats) {
            DailyTrainSeat newSeat = new DailyTrainSeat();
            newSeat.setId(seat.getId());
            newSeat.setUpdateTime(now);
            newSeat.setSell(seat.getSell());
            dailyTrainSeatMapper.updateByPrimaryKeySelective(newSeat);
        }

    }
}
