package com.heyu.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.heyu.train.business.domain.Station;
import com.heyu.train.business.domain.StationField;
import com.heyu.train.business.mapper.StationMapper;
import com.heyu.train.business.req.StationQueryReq;
import com.heyu.train.business.req.StationSaveReq;
import com.heyu.train.business.resp.StationQueryResp;
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
public class StationService {
    final StationMapper stationMapper;

    public void save(StationSaveReq req) {
        Station p1 = BeanUtil.copyProperties(req, Station.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(p1.getId())) {
            MyBatisWrapper<Station> wrapper = new MyBatisWrapper<>();
            wrapper.select(StationField.Id).whereBuilder().andLike(StationField.setName(req.getName()));
            Station station = stationMapper.topOne(wrapper);
            if (ObjectUtil.isNotNull(station)) {
                throw new BizException(BizExceptionEnum.USER_EXIST_ERROR);
            }

            req.setCreateTime(now);
            req.setUpdateTime(now);
            req.setId(SnowFlask.getSnowFlaskId());
            Station station1 = BeanUtil.copyProperties(req, Station.class);
            stationMapper.insertSelective(station1);
        } else {
            p1.setUpdateTime(now);
            stationMapper.updateByPrimaryKey(p1);
        }
    }

    public PageInfo
            <StationQueryResp> queryList(StationQueryReq req) {
        MyBatisWrapper<StationQueryResp> wrapper = new MyBatisWrapper<>();
        wrapper.select(StationField.Id, StationField.Name, StationField.CreateTime, StationField.UpdateTime, StationField.NamePinyin, StationField.NamePy);
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());

        int total = stationMapper.list(wrapper).size();
        List<Station> list = stationMapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(),
                req.getPageSize()));
        List
                <StationQueryResp> resp = BeanUtil.copyToList(list, StationQueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);

    }

    public void del(Long id) {
        stationMapper.deleteByPrimaryKey(id);
    }

    public List<StationQueryResp> queryStation() {
        MyBatisWrapper<Station> wrapper=new MyBatisWrapper();
        wrapper.select(StationField.Id,StationField.Name,StationField.NamePinyin,StationField.NamePy);
        List<Station> list = stationMapper.list(wrapper);
        List<StationQueryResp> stationQueryResps = BeanUtil.copyToList(list, StationQueryResp.class);
        return stationQueryResps;
    }
}