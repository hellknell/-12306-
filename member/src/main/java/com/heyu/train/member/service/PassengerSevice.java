package com.heyu.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.heyu.train.common.constant.BizExceptionEnum;
import com.heyu.train.common.exception.BizException;
import com.heyu.train.common.generator.help.MyBatisWrapper;
import com.heyu.train.common.generator.help.PageInfo;
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

    public void save(PassengerReq req) {
        MyBatisWrapper<Passenger> wrapper = new MyBatisWrapper<>();
        wrapper.select(PassengerField.Id).whereBuilder().andEq(PassengerField.setIdCard(req.getIdCard()));
        Passenger passenger = passengerMapper.topOne(wrapper);
        if (ObjectUtil.isNotNull(passenger)) {
            throw new BizException(BizExceptionEnum.USER_EXIST_ERROR);
        }
        DateTime now = DateTime.now();
        req.setCreateTime(now);
        req.setUpdateTime(now);
        req.setId(SnowFlask.getSnowFlaskId());
        Passenger pass = BeanUtil.copyProperties(req, Passenger.class);
        passengerMapper.insert(pass);
        

    }

    public PageInfo<PassengerQueryResp> queryList(PassengerQueryReq req) {
        MyBatisWrapper<PassengerQueryResp> wrapper = new MyBatisWrapper<>();
        wrapper.select(PassengerField.MemberId, PassengerField.Name, PassengerField.Type, PassengerField.IdCard, PassengerField.CreateTime, PassengerField.UpdateTime, PassengerField.Id).whereBuilder().andEq(PassengerField.setMemberId(LoginMemberContext.getId()));
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());

        int total = passengerMapper.list(wrapper).size();
        List<Passenger> list = passengerMapper.list(wrapper.limit((req.getPageNum() - 1)*req.getPageSize(), req.getPageSize()));
        List<PassengerQueryResp> resp = BeanUtil.copyToList(list, PassengerQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);

    }
}