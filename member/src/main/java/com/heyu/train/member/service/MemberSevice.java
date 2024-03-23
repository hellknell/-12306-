package com.heyu.train.member.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import com.heyu.train.common.constant.ResultType;
import com.heyu.train.common.generator.help.MyBatisWrapper;
import com.heyu.train.common.resp.Result;
import com.heyu.train.member.domain.Member;
import com.heyu.train.member.domain.MemberField;
import com.heyu.train.member.mapper.MemberMapper;
import com.heyu.train.member.req.MemberRegisterReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 12:53
 */
@Service
@RequiredArgsConstructor
public class MemberSevice {
    final MemberMapper memberMapper;

    public Result<Long> register(MemberRegisterReq req) {

        String mobile = req.getMobile();
        MyBatisWrapper<Member> wrapper = new MyBatisWrapper<>();
        wrapper.select(MemberField.Id).whereBuilder().andEq(MemberField.setMobile(mobile));

        List<Member> lists= memberMapper.list(wrapper);
        if(CollectionUtil.isNotEmpty(lists)){
                throw  new RuntimeException("手机号已存在");
        }
        Member member = new Member();
        member.setMobile(mobile);
       if(memberMapper.insert(member)!=-1){
          return Result.success(ResultType.SUCCESS,member.getId());

       }

       throw  new RuntimeException("注册失败");


    }
}
