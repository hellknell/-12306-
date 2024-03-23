package com.heyu.train.member.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.heyu.train.common.constant.BizExceptionEnum;
import com.heyu.train.common.exception.BizException;
import com.heyu.train.common.generator.help.MyBatisWrapper;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.member.domain.Member;
import com.heyu.train.member.domain.MemberField;
import com.heyu.train.member.mapper.MemberMapper;
import com.heyu.train.member.req.MemberRegisterReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 12:53
 */
@Service
@RequiredArgsConstructor
public class MemberSevice {
    final MemberMapper memberMapper;
    public Long register(MemberRegisterReq req) {

        String mobile = req.getMobile();
        MyBatisWrapper<Member> wrapper = new MyBatisWrapper<>();
        wrapper.select(MemberField.Id).whereBuilder().andEq(MemberField.setMobile(mobile));

        Member dbMember = memberMapper.topOne(wrapper);
        if (ObjectUtil.isNotEmpty(dbMember)) {
            throw new BizException(BizExceptionEnum.MEMBER_MOBILE_ALREADY_EXISTS);
        }
        Member member = new Member();
        member.setMobile(mobile);
        member.setId(SnowFlask.getSnowFlaskId());
        if( memberMapper.insertSelective(member)!=-1){
            return member.getId();
        }

        return  null;


    }
}
