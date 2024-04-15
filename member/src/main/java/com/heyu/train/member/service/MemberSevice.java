package com.heyu.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heyu.train.common.constant.BizExceptionEnum;
import com.heyu.train.common.exception.BizException;
import com.heyu.train.generator.generator.help.MyBatisWrapper;
import com.heyu.train.common.util.JwtUtil;
import com.heyu.train.common.util.SnowFlask;
import com.heyu.train.member.domain.Member;
import com.heyu.train.member.domain.MemberField;
import com.heyu.train.member.dto.MemberDTO;
import com.heyu.train.member.mapper.MemberMapper;
import com.heyu.train.member.req.MemberLoginReq;
import com.heyu.train.member.req.MemberRegisterReq;
import com.heyu.train.member.req.MemberSendCodeReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    final ObjectMapper objectMapper;

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
        return null;
    }

    public void sendCode(MemberSendCodeReq req) {
        String mobile = req.getMobile();
        Member m = selectMembers(mobile);
        if (ObjectUtil.isEmpty(m)) {
            log.info("用户手机号不存在,插入一条记录");
            Member member = new Member();
            member.setId(SnowFlask.getSnowFlaskId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        }
        log.info("用户存在,发送验证码");
        // 发送验证码
        String code = "8888";
        log.info("发送验证码成功,验证码为:{}", code);
        // 保存验证码到redis
        log.info("保存验证码到redis");
        log.info("对接短信平台");

    }

    private Member selectMembers(String mobile) {
        MyBatisWrapper<Member> wrapper = new MyBatisWrapper<>();
        wrapper.select(MemberField.Id, MemberField.Mobile).whereBuilder().andEq(MemberField.setMobile(mobile));
        return memberMapper.topOne(wrapper);

    }

    public MemberDTO login(MemberLoginReq req) {
        String code = req.getCode();
        Member m = selectMembers(req.getMobile());
        if (ObjectUtil.isEmpty(m)) {
            throw new BizException(BizExceptionEnum.MEMBER_NO_EXISTS);
        }

        if (!"8888".equals(code)) {

            throw new BizException(BizExceptionEnum.CODE_ERROR);
        }
        MemberDTO memberDTO = BeanUtil.copyProperties(m, MemberDTO.class);
        String token = JwtUtil.creatToken(m.getMobile(), m.getId());
        memberDTO.setToken(token);
        return memberDTO;


    }


    public Integer count() {
        MyBatisWrapper<Member> wrapper = new MyBatisWrapper<>();
        return memberMapper.count(wrapper);
    }
}