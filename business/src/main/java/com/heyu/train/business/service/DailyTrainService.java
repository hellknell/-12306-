package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.heyu.train.business.domain.DailyTrain;
import com.heyu.train.business.domain.DailyTrainField;
import com.heyu.train.business.domain.Train;
import com.heyu.train.business.domain.TrainField;
import com.heyu.train.business.mapper.DailyTrainMapper;
import com.heyu.train.business.mapper.TrainMapper;
import com.heyu.train.business.req.DailyTrainQueryReq;
import com.heyu.train.business.req.DailyTrainSaveReq;
import com.heyu.train.business.resp.DailyTrainQueryResp;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.generator.generator.help.Criteria;
import com.heyu.train.generator.generator.help.MyBatisWrapper;
import com.heyu.train.generator.generator.help.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 12:53
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DailyTrainService {
    final DailyTrainMapper dailyTrainMapper;
    final DailyTrainTicketService dailyTrainTicketService;
    final TrainMapper trainMapper;
    final DailyTrainStationService dailyTrainStationService;
    private final DailyTrainCarriageService dailyTrainCarriageService;
    private final DailyTrainSeatService dailyTrainSeatService;

    public void save(DailyTrainSaveReq req) {
        DailyTrain p1 = BeanUtil.copyProperties(req, DailyTrain.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(p1.getId())) {
            req.setCreateTime(now);
            req.setUpdateTime(now);
            req.setId(SnowFlask.getSnowFlaskId());
            DailyTrain pass = BeanUtil.copyProperties(req, DailyTrain.class);
            dailyTrainMapper.insert(pass);
        } else {
            p1.setUpdateTime(now);
            dailyTrainMapper.updateByPrimaryKey(p1);
        }
    }
    public PageInfo<DailyTrainQueryResp> queryList(DailyTrainQueryReq req) {
        MyBatisWrapper<DailyTrainQueryResp> wrapper = new MyBatisWrapper<>();
        Criteria criteria = wrapper.select(DailyTrainField.Id, DailyTrainField.UpdateTime, DailyTrainField.CreateTime, DailyTrainField.End, DailyTrainField.Code, DailyTrainField.StartTime, DailyTrainField.Start, DailyTrainField.End, DailyTrainField.EndTime, DailyTrainField.Type, DailyTrainField.StartPinyin, DailyTrainField.Date, DailyTrainField.Type, DailyTrainField.EndPinyin).whereBuilder();
        if(StrUtil.isEmpty(req.getCode())&&ObjectUtil.isNotEmpty(req.getDate())){
            criteria.andEq(DailyTrainField.setDate(req.getDate()));
        } if (StrUtil.isNotEmpty(req.getCode())&&ObjectUtil.isEmpty(req.getDate())) {
                criteria.andEq(DailyTrainField.setCode(req.getCode()));
        } if(StrUtil.isNotEmpty(req.getCode())&&ObjectUtil.isNotEmpty(req.getDate())){
            criteria.andEq(DailyTrainField.setCode(req.getCode())).andEq(DailyTrainField.setDate(req.getDate()));
        }
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());
        int total = dailyTrainMapper.list(wrapper).size();
        List<DailyTrain> list = dailyTrainMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        
        List<DailyTrainQueryResp> resp = BeanUtil.copyToList(list, DailyTrainQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);
    }

    public void del(Long id) {
        dailyTrainMapper.deleteByPrimaryKey(id);
    }

    public void generate(Date date) {
        MyBatisWrapper<Train> wrapper = new MyBatisWrapper<>();
        wrapper.select(TrainField.UpdateTime, TrainField.CreateTime, TrainField.Code, TrainField.StartTime, TrainField.Start, TrainField.End, TrainField.EndTime, TrainField.Type, TrainField.StartPinyin, TrainField.EndPinyin);
        List<Train> list = trainMapper.list(wrapper);
        for (Train train : list) {
            genTrain(date, train);
        }
    }

    @Transactional
    public void genTrain(Date date, Train train) {
        dailyTrainMapper.deleteByDate(date, train.getCode());
        DailyTrain dailyTrain = BeanUtil.copyProperties(train, DailyTrain.class);
        dailyTrain.setDate(date);
        DateTime now = DateTime.now();
        dailyTrain.setId(SnowFlask.getSnowFlaskId());
        dailyTrain.setCreateTime(now);
        dailyTrain.setUpdateTime(now);
        dailyTrainMapper.insert(dailyTrain);
        //生成每日车次
        dailyTrainStationService.genDaily(date, train.getCode());
        //生成每日车厢
        dailyTrainCarriageService.genDaily(date, train.getCode());
        //生成每日车座
        dailyTrainSeatService.genDaily(date, train.getCode());
        //生成余票查询
        dailyTrainTicketService.genDaily(dailyTrain, date, train.getCode());
    }
}