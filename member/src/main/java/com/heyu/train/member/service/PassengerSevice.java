package com.heyu.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.member.domain.Passenger;
import com.heyu.train.member.mapper.PassengerMapper;
import com.heyu.train.member.req.PassengerReq;
import context.LoginMemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 12:53
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PassengerSevice {
    final PassengerMapper passengerMapper;

    public void save(PassengerReq req) {
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        DateTime dateTime = DateTime.now();
        passenger.setId(SnowFlask.getSnowFlaskId());
        passenger.setCreateTime(dateTime);
        passenger.setUpdateTime(dateTime);
        passenger.setMemberId(LoginMemberContext.getId());
        passengerMapper.insert(passenger);
    }


}