package com.heyu.train.business.mapper;

import com.heyu.train.business.domain.TrainSeat;
import com.heyu.train.generator.generator.help.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrainSeatMapper extends CommonMapper<TrainSeat> {


    void deleteBatchs(String trainCode);
}