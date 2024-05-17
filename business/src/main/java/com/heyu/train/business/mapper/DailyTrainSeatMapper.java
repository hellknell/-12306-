package com.heyu.train.business.mapper;

import com.heyu.train.business.domain.DailyTrainSeat;
import com.heyu.train.generator.generator.help.CommonMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface DailyTrainSeatMapper extends CommonMapper<DailyTrainSeat> {
    @Delete("delete from  daily_train_seat where date = #{date} and train_code = #{trainCode}")
    int deleteByDate(@Param("date") Date date, @Param("trainCode") String trainCode);
}