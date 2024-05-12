package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.heyu.train.business.domain.DailyTrainTicket;
import com.heyu.train.business.domain.DailyTrainTicketField;
import com.heyu.train.business.mapper.DailyTrainTicketMapper;
import com.heyu.train.business.req.DailyTrainTicketQueryReq;
import com.heyu.train.business.req.DailyTrainTicketSaveReq;
import com.heyu.train.business.resp.DailyTrainTicketQueryResp;
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
public class DailyTrainTicketService {
    final DailyTrainTicketMapper dailyTrainTicketMapper;

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
        wrapper.select(DailyTrainTicketField.Id,DailyTrainTicketField.Date,DailyTrainTicketField.End,DailyTrainTicketField.Edz,DailyTrainTicketField.EdzPrice,DailyTrainTicketField.EndIndex,DailyTrainTicketField.Rw,DailyTrainTicketField.RwPrice,DailyTrainTicketField.Start,DailyTrainTicketField.StartIndex,DailyTrainTicketField.Ydz,DailyTrainTicketField.YdzPrice,DailyTrainTicketField.Yw,DailyTrainTicketField.YwPrice,DailyTrainTicketField.Rw,DailyTrainTicketField.RwPrice);
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());
        int total = dailyTrainTicketMapper.list(wrapper).size();
        List<DailyTrainTicket> list = dailyTrainTicketMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        List<DailyTrainTicketQueryResp> resp = BeanUtil.copyToList(list, DailyTrainTicketQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);
    }

    public void del(Long id) {
        dailyTrainTicketMapper.deleteByPrimaryKey(id);
    }
}