package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.heyu.train.business.domain.DailyTrain;
import com.heyu.train.business.domain.DailyTrainField;
import com.heyu.train.business.domain.TrainField;
import com.heyu.train.business.mapper.DailyTrainMapper;
import com.heyu.train.business.req.DailyTrainQueryReq;
import com.heyu.train.business.req.DailyTrainSaveReq;
import com.heyu.train.business.resp.DailyTrainQueryResp;
import com.heyu.train.common.constant.BizExceptionEnum;
import com.heyu.train.common.exception.BizException;
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
public class DailyTrainService {
    final DailyTrainMapper dailyTrainMapper;

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

    public PageInfo
            <DailyTrainQueryResp> queryList(DailyTrainQueryReq req) {
        MyBatisWrapper<DailyTrainQueryResp> wrapper = new MyBatisWrapper<>();
        wrapper.select(DailyTrainField.Id,DailyTrainField.UpdateTime,DailyTrainField.CreateTime,DailyTrainField.End,DailyTrainField.Code,DailyTrainField.StartTime,DailyTrainField.Start,DailyTrainField.End,DailyTrainField.EndTime,DailyTrainField.Type,DailyTrainField.StartPinyin,DailyTrainField.EndPinyin);
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());
        int total = dailyTrainMapper.list(wrapper).size();
        List<DailyTrain> list = dailyTrainMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(),
                req.getPageSize()));
        List
                <DailyTrainQueryResp> resp = BeanUtil.copyToList(list, DailyTrainQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);

    }

    public void del(Long id) {
        dailyTrainMapper.deleteByPrimaryKey(id);
    }
}