package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.heyu.train.business.domain.*;
import com.heyu.train.business.mapper.DailyTrainStationMapper;
import com.heyu.train.business.mapper.TrainStationMapper;
import com.heyu.train.business.req.DailyTrainStationQueryReq;
import com.heyu.train.business.req.DailyTrainStationSaveReq;
import com.heyu.train.business.resp.DailyTrainStationQueryResp;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.generator.generator.help.Criteria;
import com.heyu.train.generator.generator.help.MyBatisWrapper;
import com.heyu.train.generator.generator.help.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class DailyTrainStationService {
  private   final DailyTrainStationMapper dailyTrainStationMapper;
    private final TrainStationMapper trainStationMapper;
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
        Criteria criteria = wrapper.select(DailyTrainStationField.TrainCode, DailyTrainTicketField.Date, DailyTrainStationField.Id, DailyTrainStationField.Index, DailyTrainStationField.Name, DailyTrainStationField.InTime, DailyTrainStationField.StopTime, DailyTrainStationField.OutTime, DailyTrainStationField.NamePinyin, DailyTrainStationField.NamePinyin, DailyTrainStationField.Km).orderByAsc(DailyTrainStationField.TrainCode).whereBuilder();
        if(StrUtil.isEmpty(req.getTrainCode())&&ObjectUtil.isNotEmpty(req.getDate())){
            criteria.andEq(DailyTrainStationField.setDate(req.getDate()));
        } else if (StrUtil.isNotEmpty(req.getTrainCode())&&ObjectUtil.isEmpty(req.getDate())) {
            criteria.andEq(DailyTrainStationField.setTrainCode((req.getTrainCode())));
        } else if(StrUtil.isNotEmpty(req.getTrainCode())&&ObjectUtil.isNotEmpty(req.getDate())){
            criteria.andEq(DailyTrainStationField.setTrainCode(req.getTrainCode())).andEq(DailyTrainField.setDate(req.getDate()));
        }
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());

        int total = dailyTrainStationMapper.list(wrapper).size();
        List<DailyTrainStation> list = dailyTrainStationMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        List<DailyTrainStationQueryResp> resp = BeanUtil.copyToList(list, DailyTrainStationQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);

    }
    public void del(Long id) {
        dailyTrainStationMapper.deleteByPrimaryKey(id);
    }
    public  void genDaily(Date date,String trainCode){
        dailyTrainStationMapper.deleteByDate(trainCode,date);
        MyBatisWrapper<TrainStation> wrapper = new MyBatisWrapper<>();
      wrapper.select(TrainStationField.TrainCode,TrainStationField.Name,TrainStationField.Km,TrainStationField.NamePinyin,TrainStationField.Index,TrainStationField.OutTime,TrainStationField.StopTime,TrainStationField.InTime).whereBuilder().andEq(TrainStationField.setTrainCode(trainCode));
        List<TrainStation> list = trainStationMapper.list(wrapper);
        DateTime now = DateTime.now();
        for(TrainStation ts:list){
           DailyTrainStation dailyTrainStation =  BeanUtil.copyProperties(ts,DailyTrainStation.class);
           dailyTrainStation.setDate(date);
           dailyTrainStation.setId(SnowFlask.getSnowFlaskId());
           dailyTrainStation.setCreateTime(now);
           dailyTrainStation.setUpdateTime(now);
           dailyTrainStationMapper.insert(dailyTrainStation);
        }

        log.info("生成每日站点信息成功!!{}", DateUtil.formatDate(date));
    }
}