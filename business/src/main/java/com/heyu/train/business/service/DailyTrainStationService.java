package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.heyu.train.business.domain.DailyTrainStation;
import com.heyu.train.business.domain.DailyTrainStationField;
import com.heyu.train.business.mapper.DailyTrainStationMapper;
import com.heyu.train.business.req.DailyTrainStationQueryReq;
import com.heyu.train.business.req.DailyTrainStationSaveReq;
import com.heyu.train.business.resp.DailyTrainStationQueryResp;
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
public class DailyTrainStationService {
    final DailyTrainStationMapper dailyTrainStationMapper;

    public void save(DailyTrainStationSaveReq req) {
        DailyTrainStation p1 = BeanUtil.copyProperties(req, DailyTrainStation.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(p1.getId())) {

            req.setCreateTime(now);
            req.setUpdateTime(now);
            req.setId(SnowFlask.getSnowFlaskId());
            DailyTrainStation pass = BeanUtil.copyProperties(req, DailyTrainStation.class);
            dailyTrainStationMapper.insert(pass);
        } else {
            p1.setUpdateTime(now);
            dailyTrainStationMapper.updateByPrimaryKey(p1);
        }


    }

    public PageInfo<DailyTrainStationQueryResp> queryList(DailyTrainStationQueryReq req) {
        MyBatisWrapper<DailyTrainStationQueryResp> wrapper = new MyBatisWrapper<>();
        wrapper.select(DailyTrainStationField.TrainCode, DailyTrainStationField.Id, DailyTrainStationField.Index, DailyTrainStationField.Name, DailyTrainStationField.InTime, DailyTrainStationField.StopTime, DailyTrainStationField.OutTime, DailyTrainStationField.NamePinyin, DailyTrainStationField.NamePinyin, DailyTrainStationField.Km).orderByAsc(DailyTrainStationField.TrainCode);
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());

        int total = dailyTrainStationMapper.list(wrapper).size();
        List<DailyTrainStation> list = dailyTrainStationMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        List<DailyTrainStationQueryResp> resp = BeanUtil.copyToList(list, DailyTrainStationQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);

    }
    public void del(Long id) {
        dailyTrainStationMapper.deleteByPrimaryKey(id);
    }
}