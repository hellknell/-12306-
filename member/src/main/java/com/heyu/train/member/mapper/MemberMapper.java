package com.heyu.train.member.mapper;

import com.heyu.train.generator.generator.help.CommonMapper;
import com.heyu.train.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface MemberMapper extends CommonMapper<Member> {
}