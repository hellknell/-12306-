package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.heyu.train.business.domain.DailyTrainSeat;
import com.heyu.train.business.domain.DailyTrainSeatField;
import com.heyu.train.business.mapper.DailyTrainSeatMapper;
import com.heyu.train.business.req.DailyTrainSeatQueryReq;
import com.heyu.train.business.req.DailyTrainSeatSaveReq;
import com.heyu.train.business.resp.DailyTrainSeatQueryResp;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.generator.generator.help.MyBatisWrapper;
import com.heyu.train.generator.generator.help.PageInfo;
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
public class DailyTrainSeatService {
    final DailyTrainSeatMapper dailyTrainSeatMapper;

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
            dailyTrainSeatMapper.updateByPrimaryKey(p1);
        }


    }

    public PageInfo<DailyTrainSeatQueryResp> queryList(DailyTrainSeatQueryReq req) {
        MyBatisWrapper<DailyTrainSeatQueryResp> wrapper = new MyBatisWrapper<>();
        wrapper.select(DailyTrainSeatField.Id, DailyTrainSeatField.CarriageSeatIndex, DailyTrainSeatField.CarriageIndex, DailyTrainSeatField.Row, DailyTrainSeatField.Col, DailyTrainSeatField.TrainCode, DailyTrainSeatField.SeatType);
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());

        int total = dailyTrainSeatMapper.list(wrapper).size();
        List<DailyTrainSeat> list = dailyTrainSeatMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        List<DailyTrainSeatQueryResp> resp = BeanUtil.copyToList(list, DailyTrainSeatQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);

    }

    public void del(Long id) {
        dailyTrainSeatMapper.deleteByPrimaryKey(id);
    }
}