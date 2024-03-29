package com.heyu.train.member.domain;

import lombok.Data;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/28 18:05
 */
@Data
public class KeyCache {
    private String key;
    public String code;
    private long TimeStamp;

}
