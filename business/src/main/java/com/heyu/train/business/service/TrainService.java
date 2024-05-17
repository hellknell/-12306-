package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.heyu.train.business.domain.*;
import com.heyu.train.business.enums.SeatTypeEnum;
import com.heyu.train.business.mapper.TrainMapper;
import com.heyu.train.business.mapper.TrainSeatMapper;
import com.heyu.train.business.req.TrainQueryReq;
import com.heyu.train.business.req.TrainSaveReq;
import com.heyu.train.business.resp.TrainQueryResp;
import com.heyu.train.common.constant.BizExceptionEnum;
import com.heyu.train.common.exception.BizException;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.generator.generator.help.MyBatisWrapper;
import com.heyu.train.generator.generator.help.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 12:53
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TrainService {
    final TrainMapper trainMapper;
    final TrainCarriageService trainCarriageService;
    final TrainSeatMapper trainSeatMapper;

    public void save(TrainSaveReq req) {
        Train p1 = BeanUtil.copyProperties(req, Train.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(p1.getId())) {
            MyBatisWrapper<Train> wrapper = new MyBatisWrapper<>();
            wrapper.select(TrainField.Id).whereBuilder().andEq(TrainField.setCode(req.getCode()));
            Train train = trainMapper.topOne(wrapper);
            if (ObjectUtil.isNotNull(train)) {
                throw new BizException(BizExceptionEnum.TRAIN_EXIST_ERROR);
            }
            req.setCreateTime(now);
            req.setUpdateTime(now);
            req.setId(SnowFlask.getSnowFlaskId());
            Train train1 = BeanUtil.copyProperties(req, Train.class);
            trainMapper.insert(train1);
        } else {
            p1.setUpdateTime(now);
            trainMapper.updateByPrimaryKey(p1);
        }


    }

    public PageInfo<TrainQueryResp> queryList(TrainQueryReq req) {
        MyBatisWrapper<Train> wrapper = new MyBatisWrapper<>();
        if (ObjectUtil.isEmpty(req.getTrainCode())) {
            wrapper.select(TrainField.Id, TrainField.UpdateTime, TrainField.CreateTime, TrainField.End, TrainField.Code, TrainField.StartTime, TrainField.Start, TrainField.End, TrainField.EndTime, TrainField.Type, TrainField.StartPinyin, TrainField.EndPinyin);
        } else {
            wrapper.select(TrainField.Id, TrainField.UpdateTime, TrainField.CreateTime, TrainField.End, TrainField.Code, TrainField.StartTime, TrainField.Start, TrainField.End, TrainField.EndTime, TrainField.Type, TrainField.StartPinyin, TrainField.EndPinyin).whereBuilder().andEq(TrainField.setCode(req.getTrainCode()));
        }

        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());
        int total = trainMapper.list(wrapper).size();
        List<Train> list = trainMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        List<TrainQueryResp> resp = BeanUtil.copyToList(list, TrainQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);

    }

    public void del(Long id) {
        trainMapper.deleteByPrimaryKey(id);
    }

    public List<TrainQueryResp> queryTrainCodes() {
        MyBatisWrapper<Train> wrapper = new MyBatisWrapper<>();
        wrapper.select(TrainField.Id, TrainField.UpdateTime, TrainField.CreateTime, TrainField.End, TrainField.Code, TrainField.StartTime, TrainField.Start, TrainField.End, TrainField.EndTime, TrainField.Type, TrainField.StartPinyin, TrainField.EndPinyin);
        List<Train> list = trainMapper.list(wrapper);
        List<TrainQueryResp> resp = BeanUtil.copyToList(list, TrainQueryResp.class);
        return resp;

    }

    @Transactional
    public void genTrainSeat(String trainCode) {
        trainSeatMapper.deleteBatchs(trainCode);
        List<TrainCarriage> trainCarriages = trainCarriageService.selectByTrainCode(trainCode);
        for (TrainCarriage trainCarriage : trainCarriages) {
            int index = 1;
            Integer rowCount = trainCarriage.getRowCount();
            List<String> colsByType = SeatTypeEnum.getColsByType(trainCarriage.getSeatType());
            DateTime now = DateTime.now();
            for (int i = 1; i < rowCount; i++) {
                for (String col : colsByType) {
                    TrainSeat trainSeat = new TrainSeat();
                    trainSeat.setTrainCode(trainCode);
                    trainSeat.setCarriageIndex(trainCarriage.getIndex());
                    trainSeat.setId(SnowFlask.getSnowFlaskId());
                    trainSeat.setCarriageSeatIndex(index++);
                    trainSeat.setRow(StrUtil.fillBefore(String.valueOf(i), '0', 2));
                    trainSeat.setCol(col);
                    trainSeat.setSeatType(trainCarriage.getSeatType());
                    trainSeat.setCreateTime(now);
                    trainSeat.setUpdateTime(now);
                    trainSeatMapper.insert(trainSeat);
                }

            }


        }

    }
}