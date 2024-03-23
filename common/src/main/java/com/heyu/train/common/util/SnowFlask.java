package com.heyu.train.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/24 0:39
 */
public class SnowFlask {
    private static   Long dataCenterId=1L;
    private static   Long workerId=1L;
    public static   long getSnowFlaskId(){
        return IdUtil.getSnowflake(dataCenterId, workerId).nextId();
    }
    public static  String getSnowFlaskStr(){
        return IdUtil.getSnowflake(dataCenterId, workerId).nextIdStr();
    }
}
