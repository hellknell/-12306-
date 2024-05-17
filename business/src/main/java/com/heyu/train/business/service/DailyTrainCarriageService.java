package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.heyu.train.business.domain.*;
import com.heyu.train.business.mapper.DailyTrainCarriageMapper;
import com.heyu.train.business.mapper.TrainCarriageMapper;
import com.heyu.train.business.req.DailyTrainCarriageQueryReq;
import com.heyu.train.business.req.DailyTrainCarriageSaveReq;
import com.heyu.train.business.resp.DailyTrainCarriageQueryResp;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.generator.generator.help.Criteria;
import com.heyu.train.generator.generator.help.MyBatisWrapper;
import com.heyu.train.generator.generator.help.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class DailyTrainCarriageService {
    final DailyTrainCarriageMapper dailyTrainCarriageMapper;
    private final TrainCarriageMapper trainCarriageMapper;

    public void save(DailyTrainCarriageSaveReq req) {
        DailyTrainCarriage p1 = BeanUtil.copyProperties(req, DailyTrainCarriage.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(p1.getId())) {
            req.setCreateTime(now);
            req.setUpdateTime(now);
            req.setId(SnowFlask.getSnowFlaskId());
            DailyTrainCarriage pass = BeanUtil.copyProperties(req, DailyTrainCarriage.class);
            dailyTrainCarriageMapper.insert(pass);
        } else {
            p1.setUpdateTime(now);
            dailyTrainCarriageMapper.updateByPrimaryKey(p1);
        }


    }

    public PageInfo<DailyTrainCarriageQueryResp> queryList(DailyTrainCarriageQueryReq req) {
        MyBatisWrapper<DailyTrainCarriageQueryResp> wrapper = new MyBatisWrapper<>();
        Criteria criteria = wrapper.select(DailyTrainCarriageField.Id, DailyTrainCarriageField.Index, DailyTrainStationField.Date, DailyTrainCarriageField.TrainCode, DailyTrainCarriageField.ColCount, DailyTrainCarriageField.RowCount, DailyTrainCarriageField.SeatCount, DailyTrainCarriageField.SeatType).whereBuilder();
        if (StrUtil.isEmpty(req.getTrainCode()) && ObjectUtil.isNotEmpty(req.getDate())) {
            criteria.andEq(DailyTrainCarriageField.setDate(req.getDate()));
        } else if (StrUtil.isNotEmpty(req.getTrainCode()) && ObjectUtil.isEmpty(req.getDate())) {
            criteria.andEq(DailyTrainCarriageField.setTrainCode(req.getTrainCode()));
        } else if (StrUtil.isNotEmpty(req.getTrainCode()) && ObjectUtil.isNotEmpty(req.getDate())) {
            criteria.andEq(DailyTrainCarriageField.setTrainCode(req.getTrainCode())).andEq(DailyTrainCarriageField.setDate(req.getDate()));
        }
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());
        int total = dailyTrainCarriageMapper.list(wrapper).size();
        List<DailyTrainCarriage> list = dailyTrainCarriageMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        List<DailyTrainCarriageQueryResp> resp = BeanUtil.copyToList(list, DailyTrainCarriageQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);
    }

    public void del(Long id) {
        dailyTrainCarriageMapper.deleteByPrimaryKey(id);
    }

    public void genDaily(Date date, String trainCode) {

        dailyTrainCarriageMapper.deleteByDate(trainCode, date);
        MyBatisWrapper<TrainCarriage> wrapper = new MyBatisWrapper<>();
        wrapper.select(TrainCarriageField.Id, TrainCarriageField.Index, TrainCarriageField.TrainCode, TrainCarriageField.ColCount, TrainCarriageField.RowCount, TrainCarriageField.SeatCount, TrainCarriageField.SeatType).whereBuilder().andEq(TrainStationField.setTrainCode(trainCode));
        List<TrainCarriage> list = trainCarriageMapper.list(wrapper);
        DateTime now = DateTime.now();
        for (TrainCarriage trainCarriage : list) {
            DailyTrainCarriage dailyTrainCarriage = BeanUtil.copyProperties(trainCarriage, DailyTrainCarriage.class);
            dailyTrainCarriage.setId(SnowFlask.getSnowFlaskId());
            dailyTrainCarriage.setDate(date);
            dailyTrainCarriage.setCreateTime(now);
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriageMapper.insert(dailyTrainCarriage);
        }

    }
}