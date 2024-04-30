package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.heyu.train.business.domain.Train;
import com.heyu.train.business.domain.TrainCarriage;
import com.heyu.train.business.domain.TrainCarriageField;
import com.heyu.train.business.domain.TrainStationField;
import com.heyu.train.business.mapper.TrainCarriageMapper;
import com.heyu.train.business.req.TrainCarriageQueryReq;
import com.heyu.train.business.req.TrainCarriageSaveReq;
import com.heyu.train.business.resp.TrainCarriageQueryResp;
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
public class TrainCarriageService {
    final TrainCarriageMapper trainCarriageMapper;
    public void save(TrainCarriageSaveReq req) {
        TrainCarriage p1 = BeanUtil.copyProperties(req, TrainCarriage.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(p1.getId())) {
//            MyBatisWrapper<TrainCarriage> wrapper = new MyBatisWrapper<>();
//            wrapper.select(TrainCarriageField.Id).whereBuilder().andEq(TrainCarriageField.setIndex(req.getIndex()));
//            TrainCarriage trainCarriage = trainCarriageMapper.topOne(wrapper);
//            if (ObjectUtil.isNotNull(trainCarriage)) {
//                throw new BizException(BizExceptionEnum.TRAIN_CARRIAGE_EXIST_ERROR);
//            }
            req.setCreateTime(now);
            req.setUpdateTime(now);
            req.setId(SnowFlask.getSnowFlaskId());
            TrainCarriage pass = BeanUtil.copyProperties(req, TrainCarriage.class);
            trainCarriageMapper.insert(pass);
        } else {
            p1.setUpdateTime(now);
            trainCarriageMapper.updateByPrimaryKey(p1);
        }


    }

    public PageInfo<TrainCarriageQueryResp> queryList(TrainCarriageQueryReq req) {
        MyBatisWrapper<TrainCarriageQueryResp> wrapper = new MyBatisWrapper<>();
        if(ObjectUtil.isEmpty(req.getTrainCode())){
            wrapper.select(TrainCarriageField.Id,TrainCarriageField.Index, TrainCarriageField.TrainCode, TrainCarriageField.ColCount, TrainCarriageField.RowCount, TrainCarriageField.SeatCount,  TrainCarriageField.SeatType);
        }else{
            wrapper.select(TrainCarriageField.Id,TrainCarriageField.Index, TrainCarriageField.TrainCode, TrainCarriageField.ColCount, TrainCarriageField.RowCount, TrainCarriageField.SeatCount,  TrainCarriageField.SeatType).whereBuilder().andEq(TrainCarriageField.setTrainCode(req.getTrainCode()));
        }
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());
        int total = trainCarriageMapper.list(wrapper).size();
        List<TrainCarriage> list = trainCarriageMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        List<TrainCarriageQueryResp> resp = BeanUtil.copyToList(list, TrainCarriageQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);
    }

    public void del(Long id) {
        trainCarriageMapper.deleteByPrimaryKey(id);
    }
}