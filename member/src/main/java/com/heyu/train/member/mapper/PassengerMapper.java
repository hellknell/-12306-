package com.heyu.train.member.mapper;

import com.heyu.train.generator.generator.help.CommonMapper;

import com.heyu.train.member.domain.Passenger;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PassengerMapper extends CommonMapper<Passenger> {
}