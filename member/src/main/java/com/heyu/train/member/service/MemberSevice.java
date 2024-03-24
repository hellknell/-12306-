package com.heyu.train.member.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.heyu.train.common.constant.BizExceptionEnum;
import com.heyu.train.common.exception.BizException;
import com.heyu.train.common.generator.help.MyBatisWrapper;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.member.domain.Member;
import com.heyu.train.member.domain.MemberField;
import com.heyu.train.member.mapper.MemberMapper;
import com.heyu.train.member.req.MemberRegisterReq;
import com.heyu.train.member.req.MemberSendCodeReq;
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
        if (memberMapper.insertSelective(member) != -1) {
            return member.getId();
        }
return  null;
    }
    public void sendCode(MemberSendCodeReq req) {
        String mobile = req.getMobile();
        MyBatisWrapper<Member> wrapper = new MyBatisWrapper<>();
        wrapper.select(MemberField.Id).whereBuilder().andEq(MemberField.setMobile(mobile));
        List<Member> list = memberMapper.list(wrapper);
        if(CollUtil.isEmpty(list)){
            log.info("用户手机号不存在,插入一条记录");
            Member member = new Member();
            member.setId(SnowFlask.getSnowFlaskId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        }
        log.info("用户存在,发送验证码");
        // 发送验证码
        String code = RandomUtil.randomString(4);
        log.info("发送验证码成功,验证码为:{}", code);
        // 保存验证码到redis
        log.info("保存验证码到redis");
        log.info("对接短信平台");

    }


}