package com.heyu.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.heyu.train.common.constant.BizExceptionEnum;
import com.heyu.train.common.exception.BizException;
import com.heyu.train.common.generator.help.MyBatisWrapper;
import com.heyu.train.common.generator.help.PageInfo;
import com.heyu.train.common.resp.${Domain}QueryResp;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.member.domain.${Domain};
import com.heyu.train.member.domain.${Domain}Field;
import com.heyu.train.member.mapper.${Domain}Mapper;
import com.heyu.train.member.req.${Domain}QueryReq;
import com.heyu.train.member.req.${Domain}Req;
import context.LoginMemberContext;
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
public class ${Domain}Service {
    final ${Domain}Mapper ${domain}Mapper;

    public void save(${Domain}Req req) {
        ${Domain} p1 = BeanUtil.copyProperties(req, ${Domain}.class);
        DateTime now = DateTime.now();
        if (ObjectUtil.isNull(p1.getId()) ) {
            MyBatisWrapper<${Domain}> wrapper = new MyBatisWrapper<>();
            wrapper.select(${Domain}Field.Id).whereBuilder().andEq(${Domain}Field.setIdCard(req.getIdCard()));
            ${Domain} ${domain} = ${domain}Mapper.topOne(wrapper);
            if (ObjectUtil.isNotNull(${domain})) {
                throw new BizException(BizExceptionEnum.USER_EXIST_ERROR);
            }

            req.setCreateTime(now);
            req.setUpdateTime(now);
            req.setId(SnowFlask.getSnowFlaskId());
            ${Domain} pass = BeanUtil.copyProperties(req, ${Domain}.class);
            ${domain}Mapper.insert(pass);
        }else{
            p1.setUpdateTime(now);
            ${domain}Mapper.updateByPrimaryKey(p1);
        }


    }

    public PageInfo<${Domain}QueryResp> queryList(${Domain}QueryReq req) {
        MyBatisWrapper<${Domain}QueryResp> wrapper = new MyBatisWrapper<>();
        wrapper.select(${Domain}Field.MemberId, ${Domain}Field.Name, ${Domain}Field.Type, ${Domain}Field.IdCard, ${Domain}Field.CreateTime, ${Domain}Field.UpdateTime, ${Domain}Field.Id).whereBuilder().andEq(${Domain}Field.setMemberId(LoginMemberContext.getId()));
        log.info("pageSize:{}----pageNum:{},", req.getPageSize(), req.getPageNum());

        int total = ${domain}Mapper.list(wrapper).size();
        List<${Domain}> list = ${domain}Mapper.list(wrapper.limit((req.getPageNum() - 1) * req.getPageSize(), req.getPageSize()));
        List<${Domain}QueryResp> resp = BeanUtil.copyToList(list, ${Domain}QueryResp.class);
        return new PageInfo<>(req.getPageNum(), req.getPageSize(), total, resp);

    }

    public void del(Long id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}