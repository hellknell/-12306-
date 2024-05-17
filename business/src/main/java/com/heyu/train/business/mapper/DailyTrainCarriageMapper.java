package com.heyu.train.business.mapper;

import com.heyu.train.business.domain.DailyTrainCarriage;
import com.heyu.train.generator.generator.help.CommonMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Mapper
public interface DailyTrainCarriageMapper extends CommonMapper<DailyTrainCarriage> {
    @Delete("delete from daily_train_carriage where train_code = #{trainCode} and date = #{date}")
    void deleteByDate(@Param("trainCode") String trainCode, @Param("date") Date date);
}