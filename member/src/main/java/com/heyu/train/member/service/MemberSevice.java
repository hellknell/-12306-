package com.heyu.train.member.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.heyu.train.common.generator.help.MyBatisWrapper;
import com.heyu.train.member.domain.Member;
import com.heyu.train.member.domain.MemberField;
import com.heyu.train.member.mapper.MemberMapper;
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

    public Member serchMembers() {
        MyBatisWrapper<Member> wrapper = new MyBatisWrapper<>();
        wrapper.select(MemberField.Id,MemberField.Mobile).whereBuilder().andEq(MemberField.setId(Long.valueOf(1)));
        return memberMapper.topOne(wrapper);
    }
}
