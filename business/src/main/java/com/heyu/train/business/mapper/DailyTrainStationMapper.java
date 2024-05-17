package com.heyu.train.business.mapper;

import com.heyu.train.business.domain.DailyTrainStation;
import com.heyu.train.generator.generator.help.CommonMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface DailyTrainStationMapper extends CommonMapper<DailyTrainStation> {
   @Delete("delete from daily_train_station where train_code = #{trainCode} and date = #{date}")
    int  deleteByDate(@Param("trainCode") String trainCode, @Param("date") Date date);
}