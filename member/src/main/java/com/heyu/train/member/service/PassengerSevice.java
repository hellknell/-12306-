package com.heyu.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.heyu.train.common.generator.help.MyBatisWrapper;
import com.heyu.train.common.resp.PassengerQueryResp;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.member.domain.Passenger;
import com.heyu.train.member.domain.PassengerField;
import com.heyu.train.member.mapper.PassengerMapper;
import com.heyu.train.member.req.PassengerQueryReq;
import com.heyu.train.member.req.PassengerReq;
import context.LoginMemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void save(PassengerReq req) { }
    public List<PassengerQueryResp> queryList(PassengerQueryReq req) {
        MyBatisWrapper<PassengerQueryResp> wrapper = new MyBatisWrapper<>();
        wrapper.select(PassengerField.MemberId,PassengerField.Name,PassengerField.Type,PassengerField.IdCard).whereBuilder().andEq(PassengerField.setMemberId(LoginMemberContext.getId()));
        List<Passenger> list = passengerMapper.list(wrapper);
        List<PassengerQueryResp> resp = BeanUtil.copyToList(list, PassengerQueryResp.class);
        return resp;


    }
}