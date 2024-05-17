package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.heyu.train.business.domain.*;
import com.heyu.train.business.enums.TrainSeatEnum;
import com.heyu.train.business.enums.TrainTypeEnum;
import com.heyu.train.business.mapper.DailyTrainTicketMapper;
import com.heyu.train.business.mapper.TrainStationMapper;
import com.heyu.train.business.req.DailyTrainTicketQueryReq;
import com.heyu.train.business.req.DailyTrainTicketSaveReq;
import com.heyu.train.business.resp.DailyTrainTicketQueryResp;
import com.heyu.train.common.constant.BizExceptionEnum;
import com.heyu.train.common.exception.BizException;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.generator.generator.help.Criteria;
import com.heyu.train.generator.generator.help.MyBatisWrapper;
import com.heyu.train.generator.generator.help.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class DailyTrainTicketService {
    private final DailyTrainTicketMapper dailyTrainTicketMapper;
    private final TrainStationMapper trainStationMapper;
    private final DailyTrainSeatService dailyTrainSeatService;

    public void save(DailyTrainTicketSaveReq req) {
        DailyTrainTicket p1 = BeanUtil.copyProperties(req, DailyTrainTicket.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(p1.getId())) {
            req.setCreateTime(now);
            req.setUpdateTime(now);
            req.setId(SnowFlask.getSnowFlaskId());
            DailyTrainTicket pass = BeanUtil.copyProperties(req, DailyTrainTicket.class);
            dailyTrainTicketMapper.insert(pass);
        } else {
            p1.setUpdateTime(now);
            dailyTrainTicketMapper.updateByPrimaryKey(p1);
        }
    }

    public PageInfo<DailyTrainTicketQueryResp> queryList(DailyTrainTicketQueryReq req) {
        MyBatisWrapper<DailyTrainTicketQueryResp> wrapper = new MyBatisWrapper<>();
        Criteria criteria = wrapper.select(DailyTrainTicketField.Id, DailyTrainTicketField.StartIndex, DailyTrainTicketField.EndIndex, DailyTrainTicketField.StartTime, DailyTrainTicketField.EndTime, DailyTrainTicketField.Date, DailyTrainTicketField.End, DailyTrainTicketField.Edz, DailyTrainTicketField.EdzPrice, DailyTrainTicketField.EndIndex, DailyTrainTicketField.Rw, DailyTrainTicketField.RwPrice, DailyTrainTicketField.Start, DailyTrainTicketField.StartIndex, DailyTrainTicketField.Ydz, DailyTrainTicketField.EndPinyin, DailyTrainTicketField.StartPinyin, DailyTrainTicketField.TrainCode, DailyTrainTicketField.StartIndex, DailyTrainTicketField.YdzPrice, DailyTrainTicketField.Yw, DailyTrainTicketField.YwPrice, DailyTrainTicketField.Rw, DailyTrainTicketField.RwPrice).whereBuilder();
        if (StrUtil.isEmpty(req.getTrainCode()) && ObjectUtil.isNotEmpty(req.getDate())) {
            criteria.andEq(DailyTrainStationField.setDate(req.getDate()));
        } else if (StrUtil.isNotEmpty(req.getTrainCode()) && ObjectUtil.isEmpty(req.getDate())) {
            criteria.andEq(DailyTrainStationField.setTrainCode((req.getTrainCode())));
        } else if (StrUtil.isNotEmpty(req.getTrainCode()) && ObjectUtil.isNotEmpty(req.getDate())) {
            criteria.andEq(DailyTrainStationField.setTrainCode(req.getTrainCode())).andEq(DailyTrainField.setDate(req.getDate()));
        }


        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());
        int total = dailyTrainTicketMapper.list(wrapper).size();
        List<DailyTrainTicket> list = dailyTrainTicketMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        List<DailyTrainTicketQueryResp> resp = BeanUtil.copyToList(list, DailyTrainTicketQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);
    }

    public void del(Long id) {
        dailyTrainTicketMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void genDaily(DailyTrain dailyTrain, Date date, String trainCode) {
        log.info("生成日期{},车次{}的余票信息查询开始", DateUtil.format(date, "yyyy-MM-dd"), trainCode);
        dailyTrainTicketMapper.deleteBatchs(date, trainCode);
        //查询车次所经过的所有车站
        MyBatisWrapper<TrainStation> wrapper = new MyBatisWrapper<>();
        wrapper.select(TrainStationField.Id, TrainStationField.Km, TrainStationField.TrainCode, TrainStationField.OutTime, TrainStationField.InTime, TrainStationField.Index, TrainStationField.Name, TrainStationField.NamePinyin).whereBuilder().andEq(TrainStationField.setTrainCode(trainCode));
        wrapper.orderByAsc(TrainStationField.TrainCode, TrainStationField.Index);
        List<TrainStation> stationLists = trainStationMapper.list(wrapper);
        if (CollUtil.isEmpty(stationLists)) {
            throw new BizException(BizExceptionEnum.NO_DAILY_TRAIN);
        }
        DateTime now = DateTime.now();
        String type = dailyTrain.getType();

        BigDecimal priceRate = EnumUtil.getFieldBy(TrainTypeEnum::getPriceRate, TrainTypeEnum::getCode, type);
        log.info("车次{}的票面价格系数为{}", trainCode, priceRate);
        BigDecimal ydz = priceRate.multiply(TrainSeatEnum.FIRST.getPrice());
        BigDecimal edz = priceRate.multiply(TrainSeatEnum.SECOND.getPrice());
        BigDecimal yw = priceRate.multiply(TrainSeatEnum.SOLID.getPrice());
        BigDecimal rw = priceRate.multiply(TrainSeatEnum.SOFT.getPrice());
        Integer ydzCount = dailyTrainSeatService.countBy(date, trainCode, TrainSeatEnum.FIRST.getCode());
        Integer edzCount = dailyTrainSeatService.countBy(date, trainCode, TrainSeatEnum.SECOND.getCode());
        Integer ywCount = dailyTrainSeatService.countBy(date, trainCode, TrainSeatEnum.SOLID.getCode());
        Integer rwCount = dailyTrainSeatService.countBy(date, trainCode, TrainSeatEnum.SOFT.getCode());
        //遍历所有途经车站
        for (int i = 0; i < stationLists.size(); i++) {
            BigDecimal sumKm = BigDecimal.ZERO;
            TrainStation trainStationStart = stationLists.get(i);
            for (int j = i + 1; j < stationLists.size(); j++) {
                log.info("{}", trainStationStart.getKm());
                sumKm = sumKm.add(trainStationStart.getKm());
                log.info("{}", sumKm);
                TrainStation trainStationEnd = stationLists.get(j);
                DailyTrainTicket dailyTrainTicket = new DailyTrainTicket();
                dailyTrainTicket.setId(SnowFlask.getSnowFlaskId());
                dailyTrainTicket.setDate(date);
                dailyTrainTicket.setEdz(edzCount);
                dailyTrainTicket.setEdzPrice(edz.multiply(sumKm).setScale(2, RoundingMode.HALF_UP));
                dailyTrainTicket.setYdz(ydzCount);
                dailyTrainTicket.setYdzPrice(sumKm.multiply(ydz).setScale(2, RoundingMode.HALF_UP));
                dailyTrainTicket.setYw(ywCount);
                dailyTrainTicket.setYwPrice(sumKm.multiply(yw).setScale(2, RoundingMode.HALF_UP));
                dailyTrainTicket.setRw(rwCount);
                dailyTrainTicket.setRwPrice(sumKm.multiply(rw).setScale(2, RoundingMode.HALF_UP));
                dailyTrainTicket.setTrainCode(trainCode);
                dailyTrainTicket.setStart(trainStationStart.getName());
                dailyTrainTicket.setStartPinyin(trainStationStart.getNamePinyin());
                dailyTrainTicket.setEnd(trainStationEnd.getName());
                dailyTrainTicket.setEndPinyin(trainStationEnd.getNamePinyin());
                dailyTrainTicket.setStartIndex(trainStationStart.getIndex());
                dailyTrainTicket.setEndIndex(trainStationEnd.getIndex());
                dailyTrainTicket.setStartTime(trainStationStart.getOutTime());
                dailyTrainTicket.setEndTime(trainStationEnd.getInTime());
                dailyTrainTicket.setCreateTime(now);
                dailyTrainTicket.setUpdateTime(now);
                dailyTrainTicketMapper.insert(dailyTrainTicket);
            }
        }

    }
}