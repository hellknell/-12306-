package com.heyu.train.business.mapper;

import com.heyu.train.business.domain.DailyTrainTicket;
import com.heyu.train.generator.generator.help.CommonMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface DailyTrainTicketMapper extends CommonMapper<DailyTrainTicket> {
    @Delete("delete from daily_train_ticket where date = #{date} and train_code = #{trainCode}")
    int deleteBatchs(@Param("date") Date date, @Param("trainCode") String trainCode);

    int updateBySell(@Param("date") Date date, @Param("trainCode") String trainCode, @Param("seatType") String seatType, @Param("minStartIndex") Integer minStartIndex, @Param("maxStartIndex") Integer maxStartIndex, @Param("minEndIndex") Integer minEndIndex, @Param("maxEndIndex") Integer maxEndIndex);
}