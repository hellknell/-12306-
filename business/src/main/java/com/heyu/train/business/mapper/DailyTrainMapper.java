package com.heyu.train.business.mapper;

import com.heyu.train.business.domain.DailyTrain;
import com.heyu.train.generator.generator.help.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DailyTrainMapper extends CommonMapper<DailyTrain> {
}