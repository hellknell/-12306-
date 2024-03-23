package com.heyu.train.common.generator.help;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonMapper<T>  {
    int deleteByPrimaryKey(Long id);

    int insert(T row);

    int insertSelective(T row);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T row);

    int updateByPrimaryKey(T row);

    List<T> list(MyBatisWrapper example);

    T topOne(MyBatisWrapper example);

    Integer count(MyBatisWrapper example);

    int updateField(@Param("example") MyBatisWrapper example);

    int updateFieldBatch(List<MyBatisWrapper> list);

    T get(MyBatisWrapper example);
    
    int updateBatchSelective(@Param("list") List<T> list);
}