package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.heyu.train.business.domain.*;
import com.heyu.train.business.mapper.DailyTrainSeatMapper;
import com.heyu.train.business.mapper.TrainSeatMapper;
import com.heyu.train.business.req.DailyTrainSeatQueryReq;
import com.heyu.train.business.req.DailyTrainSeatSaveReq;
import com.heyu.train.business.resp.DailyTrainSeatQueryResp;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.generator.generator.help.Criteria;
import com.heyu.train.generator.generator.help.MyBatisWrapper;
import com.heyu.train.generator.generator.help.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.heyu.train.business.domain.DailyTrainSeatField.*;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 12:53
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DailyTrainSeatService {
    final DailyTrainSeatMapper dailyTrainSeatMapper;
    final TrainSeatMapper trainSeatMapper;
    final TrainStationService trainStationService;

    public void save(DailyTrainSeatSaveReq req) {
        DailyTrainSeat p1 = BeanUtil.copyProperties(req, DailyTrainSeat.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(p1.getId())) {
            req.setCreateTime(now);
            req.setUpdateTime(now);
            req.setId(SnowFlask.getSnowFlaskId());
            DailyTrainSeat pass = BeanUtil.copyProperties(req, DailyTrainSeat.class);
            dailyTrainSeatMapper.insert(pass);
        } else {
            p1.setUpdateTime(now);
            dailyTrainSeatMapper.updateByPrimaryKeySelective(p1);
        }
    }

    public PageInfo<DailyTrainSeatQueryResp> queryList(DailyTrainSeatQueryReq req) {
        MyBatisWrapper<DailyTrainSeatQueryResp> wrapper = new MyBatisWrapper<>();
        Criteria criteria = wrapper.select(Id, Date, Sell, CarriageSeatIndex, CarriageIndex, Row, Col, TrainCode, SeatType).whereBuilder();
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());
        if (StrUtil.isEmpty(req.getTrainCode()) && ObjectUtil.isNotEmpty(req.getDate())) {
            criteria.andEq(DailyTrainSeatField.setDate(req.getDate()));
        } else if (StrUtil.isNotEmpty(req.getTrainCode()) && ObjectUtil.isEmpty(req.getDate())) {
            criteria.andEq(DailyTrainSeatField.setTrainCode((req.getTrainCode())));
        } else if (StrUtil.isNotEmpty(req.getTrainCode()) && ObjectUtil.isNotEmpty(req.getDate())) {
            criteria.andEq(DailyTrainSeatField.setTrainCode(req.getTrainCode())).andEq(DailyTrainField.setDate(req.getDate()));
        }
        int total = dailyTrainSeatMapper.list(wrapper).size();
        List<DailyTrainSeat> list = dailyTrainSeatMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        List<DailyTrainSeatQueryResp> resp = BeanUtil.copyToList(list, DailyTrainSeatQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);

    }

    public void genDaily(Date date, String code) {
        dailyTrainSeatMapper.deleteByDate(date, code);
        MyBatisWrapper<TrainSeat> wrapper = new MyBatisWrapper<>();
        wrapper.select(TrainSeatField.CarriageSeatIndex, TrainSeatField.CarriageIndex, TrainSeatField.Row, TrainSeatField.Col, TrainSeatField.TrainCode, TrainSeatField.SeatType).whereBuilder().andEq(TrainSeatField.setTrainCode(code));
        List<TrainSeat> list = trainSeatMapper.list(wrapper);
        int size = trainStationService.countStations(code);
        String s = StrUtil.fillBefore("", '0', size - 1);
        DateTime now = DateTime.now();
        for (TrainSeat ts : list) {
            DailyTrainSeat dailyTrainSeat = BeanUtil.copyProperties(ts, DailyTrainSeat.class);
            dailyTrainSeat.setDate(date);
            dailyTrainSeat.setSell(s);
            dailyTrainSeat.setId(SnowFlask.getSnowFlaskId());
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        }
    }

    public void del(Long id) {
        dailyTrainSeatMapper.deleteByPrimaryKey(id);
    }

    protected Integer countBy(Date date, String trainCode, String seatType) {
        MyBatisWrapper<DailyTrainSeat> wrapper = new MyBatisWrapper<>();
        wrapper.select(Id).whereBuilder().andEq(setDate(date)).andEq(setTrainCode(trainCode)).andEq(setSeatType(seatType));
        Integer count = dailyTrainSeatMapper.count(wrapper);
        if (0L == count) {
            return -1;
        }
        return count;
    }

    public List<DailyTrainSeat> getSeatByCarriageIndex(Date date, String trainCode, Integer trainCarriageIndex) {
        MyBatisWrapper<DailyTrainSeat> wrapper = new MyBatisWrapper<>();
        wrapper.select(DailyTrainSeatField.CarriageSeatIndex, DailyTrainSeatField.CarriageIndex, DailyTrainSeatField.Row, DailyTrainSeatField.Col, DailyTrainSeatField.TrainCode, DailyTrainSeatField.SeatType, Sell).whereBuilder().andEq(setDate(date)).andEq(setTrainCode(trainCode)).andEq(setCarriageIndex(trainCarriageIndex));
        List<DailyTrainSeat> list = dailyTrainSeatMapper.list(wrapper.orderByAsc(DailyTrainSeatField.CarriageSeatIndex));
        return list;
    }

}