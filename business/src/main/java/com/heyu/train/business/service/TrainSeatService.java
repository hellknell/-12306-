package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.heyu.train.business.domain.TrainSeat;
import com.heyu.train.business.domain.TrainSeatField;
import com.heyu.train.business.enums.SeatTypeEnum;
import com.heyu.train.business.mapper.TrainSeatMapper;
import com.heyu.train.business.req.TrainSeatQueryReq;
import com.heyu.train.business.req.TrainSeatSaveReq;
import com.heyu.train.business.resp.TrainSeatQueryResp;
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
public class TrainSeatService {
    final TrainSeatMapper trainSeatMapper;

    public void save(TrainSeatSaveReq req) {
        TrainSeat p1 = BeanUtil.copyProperties(req, TrainSeat.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(p1.getId())) {
//            MyBatisWrapper<TrainSeat> wrapper = new MyBatisWrapper<>();
//            wrapper.select(TrainSeatField.Id);
//            TrainSeat trainSeat = trainSeatMapper.topOne(wrapper);
//            if (ObjectUtil.isNotNull(trainSeat)) {
//                throw new BizException(BizExceptionEnum.USER_EXIST_ERROR);
//            }
            req.setCreateTime(now);
            req.setUpdateTime(now);
            req.setId(SnowFlask.getSnowFlaskId());
            TrainSeat pass = BeanUtil.copyProperties(req, TrainSeat.class);
            trainSeatMapper.insert(pass);
        } else {
            p1.setUpdateTime(now);
            trainSeatMapper.updateByPrimaryKey(p1);
        }


    }

    public PageInfo
            <TrainSeatQueryResp> queryList(TrainSeatQueryReq req) {
        MyBatisWrapper<TrainSeatQueryResp> wrapper = new MyBatisWrapper<>();
        if (ObjectUtil.isEmpty(req.getTrainCode())) {
            wrapper.select(TrainSeatField.Id, TrainSeatField.CarriageSeatIndex, TrainSeatField.CarriageIndex, TrainSeatField.Row, TrainSeatField.Col, TrainSeatField.TrainCode, TrainSeatField.SeatType);
        } else {
            wrapper.select(TrainSeatField.Id, TrainSeatField.CarriageSeatIndex, TrainSeatField.CarriageIndex, TrainSeatField.Row, TrainSeatField.Col, TrainSeatField.TrainCode, TrainSeatField.SeatType).whereBuilder().andEq(TrainSeatField.setTrainCode(req.getTrainCode()));
        }

        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());

        int total = trainSeatMapper.list(wrapper).size();
        List<TrainSeat> list = trainSeatMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(),
                req.getPageSize()));
        List<TrainSeatQueryResp> resp = BeanUtil.copyToList(list, TrainSeatQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);

    }

    public void del(Long id) {
        trainSeatMapper.deleteByPrimaryKey(id);
    }

    public List<String> querySeatCol(String type) {

        List<String> list = SeatTypeEnum.getColsByType(type);
        return list;
    }
}