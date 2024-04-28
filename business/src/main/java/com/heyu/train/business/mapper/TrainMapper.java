package com.heyu.train.business.mapper;

import com.heyu.train.business.domain.Train;
import com.heyu.train.generator.generator.help.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TrainMapper extends CommonMapper<Train> {
    @Select("SELECT code FROM train where code like '%${code}%' order by code asc")
    List<String> queryTrainCodes(String code);
}