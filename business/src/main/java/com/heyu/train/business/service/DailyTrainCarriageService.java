package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.heyu.train.business.domain.DailyTrainCarriage;
import com.heyu.train.business.domain.DailyTrainCarriageField;
import com.heyu.train.business.mapper.DailyTrainCarriageMapper;
import com.heyu.train.business.req.DailyTrainCarriageQueryReq;
import com.heyu.train.business.req.DailyTrainCarriageSaveReq;
import com.heyu.train.business.resp.DailyTrainCarriageQueryResp;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.generator.generator.help.MyBatisWrapper;
import com.heyu.train.generator.generator.help.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class DailyTrainCarriageService {
    final DailyTrainCarriageMapper dailyTrainCarriageMapper;

    public void save(DailyTrainCarriageSaveReq req) {
        DailyTrainCarriage p1 = BeanUtil.copyProperties(req, DailyTrainCarriage.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(p1.getId())) {
            req.setCreateTime(now);
            req.setUpdateTime(now);
            req.setId(SnowFlask.getSnowFlaskId());
            DailyTrainCarriage pass = BeanUtil.copyProperties(req, DailyTrainCarriage.class);
            dailyTrainCarriageMapper.insert(pass);
        } else {
            p1.setUpdateTime(now);
            dailyTrainCarriageMapper.updateByPrimaryKey(p1);
        }


    }

    public PageInfo<DailyTrainCarriageQueryResp> queryList(DailyTrainCarriageQueryReq req) {
        MyBatisWrapper<DailyTrainCarriageQueryResp> wrapper = new MyBatisWrapper<>();
        wrapper.select(DailyTrainCarriageField.Id,DailyTrainCarriageField.Index, DailyTrainCarriageField.TrainCode, DailyTrainCarriageField.ColCount, DailyTrainCarriageField.RowCount, DailyTrainCarriageField.SeatCount,  DailyTrainCarriageField.SeatType);
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());

        int total = dailyTrainCarriageMapper.list(wrapper).size();
        List<DailyTrainCarriage> list = dailyTrainCarriageMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        List<DailyTrainCarriageQueryResp> resp = BeanUtil.copyToList(list, DailyTrainCarriageQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);

    }

    public void del(Long id) {
        dailyTrainCarriageMapper.deleteByPrimaryKey(id);
    }
}