package com.heyu.train.business.mapper;

import com.heyu.train.business.domain.Station;
import com.heyu.train.generator.generator.help.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StationMapper extends CommonMapper<Station> {
}