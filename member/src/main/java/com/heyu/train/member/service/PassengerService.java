package com.heyu.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.heyu.train.common.constant.BizExceptionEnum;
import com.heyu.train.common.exception.BizException;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.generator.generator.help.MyBatisWrapper;
import com.heyu.train.generator.generator.help.PageInfo;
import com.heyu.train.member.domain.Passenger;
import com.heyu.train.member.domain.PassengerField;
import com.heyu.train.member.mapper.PassengerMapper;
import com.heyu.train.member.req.PassengerQueryReq;
import com.heyu.train.member.req.PassengerSaveReq;
import com.heyu.train.member.resp.PassengerQueryResp;
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
public class PassengerService {
    final PassengerMapper passengerMapper;

    public void save(PassengerSaveReq req) {
        Long member_id = LoginMemberContext.getId();
        Passenger p1 = BeanUtil.copyProperties(req, Passenger.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(p1.getId())) {
            MyBatisWrapper<Passenger> wrapper = new MyBatisWrapper<>();
            wrapper.select(PassengerField.Id).whereBuilder().andEq(PassengerField.setMemberId(member_id));
            List<Passenger> list = passengerMapper.list(wrapper);
            if (list.size() >= 50) {
                throw new BizException(BizExceptionEnum.PASSENGER_COUNT_LIMIT);
            }
            req.setMemberId(member_id);
            req.setCreateTime(now);
            req.setUpdateTime(now);
            req.setId(SnowFlask.getSnowFlaskId());
            Passenger pass = BeanUtil.copyProperties(req, Passenger.class);
            passengerMapper.insert(pass);
        } else {
            p1.setUpdateTime(now);
            passengerMapper.updateByPrimaryKey(p1);
        }


    }

    public PageInfo<PassengerQueryResp> queryList(PassengerQueryReq req) {
        MyBatisWrapper<PassengerQueryResp> wrapper = new MyBatisWrapper<>();
        wrapper.select(PassengerField.Id, PassengerField.MemberId, PassengerField.Name, PassengerField.Type, PassengerField.IdCard);
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());
        int total = passengerMapper.list(wrapper).size();
        List<Passenger> list = passengerMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(),
                req.getPageSize()));
        List<PassengerQueryResp> resp = BeanUtil.copyToList(list, PassengerQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);
    }


    public void del(Long id) {
        passengerMapper.deleteByPrimaryKey(id);
    }


    public List<PassengerQueryResp> queryPassenger() {
        Long id = LoginMemberContext.getId();
        MyBatisWrapper<Passenger> wrapper = new MyBatisWrapper<>();
        wrapper.select(PassengerField.Id, PassengerField.MemberId, PassengerField.Name, PassengerField.Type, PassengerField.IdCard).whereBuilder().andEq(PassengerField.setMemberId(id));
        List<Passenger> lis = passengerMapper.list(wrapper);
        List<PassengerQueryResp> passengerQueryResps = BeanUtil.copyToList(lis, PassengerQueryResp.class);
        return passengerQueryResps;

    }
}