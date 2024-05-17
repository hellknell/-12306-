package com.heyu.train.business.mapper;

import com.heyu.train.business.domain.DailyTrain;
import com.heyu.train.generator.generator.help.CommonMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Mapper
public interface DailyTrainMapper extends CommonMapper<DailyTrain> {
    @Delete("delete from daily_train where date = #{date} and code = #{trainCode}")
    void deleteByDate(@Param("date") Date date,@Param("trainCode") String trainCode);

}