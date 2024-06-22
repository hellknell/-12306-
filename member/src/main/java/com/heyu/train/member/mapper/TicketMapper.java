package com.heyu.train.member.mapper;


import com.heyu.train.generator.generator.help.CommonMapper;
import com.heyu.train.member.domain.Ticket;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TicketMapper extends CommonMapper<Ticket> {
}