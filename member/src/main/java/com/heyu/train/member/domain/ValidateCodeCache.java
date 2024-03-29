package com.heyu.train.member.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能:
 * 作者:何宇
 * 日期：2023/11/24 23:07
 */
@Component
@Slf4j
public class ValidateCodeCache {

    private static final List<KeyCache> keyCache = new ArrayList<>();

    public static void setCodeCache(String key, String code) {
        KeyCache cache = new KeyCache();
        cache.setKey(key);
        cache.setCode(code);
        cache.setTimeStamp(System.currentTimeMillis());
        keyCache.add(cache);
    }

    public static boolean verify(String key, String code) {
        boolean ret = keyCache.stream().anyMatch(cache -> cache.getKey().equals(key) && cache.getCode().equals(code));
        return ret;
    }

    //        定时清缓存
    @Scheduled(fixedRate = 30000) //每30秒清一次
    public static void task() {
        log.info("===========开始扫描,验证码长度:" + keyCache.size() + "===================");
        List<KeyCache> codeList = keyCache.stream().filter(cache -> {
            long time = cache.getTimeStamp();
            long duration = System.currentTimeMillis() - time;
            return duration >  1000 * 60;
        }).collect(Collectors.toList());
        keyCache.removeAll(codeList);
        if (keyCache.size() > 100) {
            keyCache.clear();
        }
        log.info("===========扫描结束,验证码长度:" + keyCache.size() + "===================");
    }

}
