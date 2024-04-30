package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.heyu.train.business.domain.TrainStation;
import com.heyu.train.business.domain.TrainStationField;
import com.heyu.train.business.mapper.TrainStationMapper;
import com.heyu.train.business.req.TrainStationQueryReq;
import com.heyu.train.business.req.TrainStationSaveReq;
import com.heyu.train.business.resp.TrainStationQueryResp;
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
public class TrainStationService {
    final TrainStationMapper trainStationMapper;

    public void save(TrainStationSaveReq req) {
        TrainStation p1 = BeanUtil.copyProperties(req, TrainStation.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(p1.getId())) {
//            MyBatisWrapper<TrainStation> wrapper = new MyBatisWrapper<>();
//            wrapper.select(TrainStationField.Id).whereBuilder().andEq(TrainStationField.setId(req.getId()));
//            TrainStation trainStation = trainStationMapper.topOne(wrapper);
//            if (ObjectUtil.isNotNull(trainStation)) {
//                throw new BizException(BizExceptionEnum.TRAIN_STATION_EXIST_ERROR);
//            }
            req.setCreateTime(now);
            req.setUpdateTime(now);
            req.setId(SnowFlask.getSnowFlaskId());
            TrainStation train_staion = BeanUtil.copyProperties(req, TrainStation.class);
            trainStationMapper.insert(train_staion);
        } else {
            p1.setUpdateTime(now);
            trainStationMapper.updateByPrimaryKey(p1);
        }
    }

    public PageInfo<TrainStationQueryResp> queryList(TrainStationQueryReq req) {
        MyBatisWrapper<TrainStationQueryResp> wrapper = new MyBatisWrapper<>();
        if(ObjectUtil.isEmpty(req.getTrainCode())){
            wrapper.select(TrainStationField.TrainCode, TrainStationField.Id, TrainStationField.Index, TrainStationField.Name, TrainStationField.InTime, TrainStationField.StopTime, TrainStationField.OutTime, TrainStationField.NamePinyin, TrainStationField.NamePinyin, TrainStationField.Km).orderByAsc(TrainStationField.TrainCode);
        }else {
            wrapper.select(TrainStationField.TrainCode, TrainStationField.Id, TrainStationField.Index, TrainStationField.Name, TrainStationField.InTime, TrainStationField.StopTime, TrainStationField.OutTime, TrainStationField.NamePinyin, TrainStationField.NamePinyin, TrainStationField.Km).orderByAsc(TrainStationField.TrainCode).whereBuilder().andEq(TrainStationField.setTrainCode(req.getTrainCode()));
        }
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());
        int total = trainStationMapper.list(wrapper).size();
        List<TrainStation> list = trainStationMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(),
                req.getPageSize()));
        List<TrainStationQueryResp> resp = BeanUtil.copyToList(list, TrainStationQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);
    }

    public void del(Long id) {
        trainStationMapper.deleteByPrimaryKey(id);
    }
}