package com.heyu.train.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis Template 配置
 **/
@ConditionalOnProperty(prefix = "sys", name = "redis-template-config", havingValue = "true")
@Configuration
@Slf4j
public class RedisTemplateDefaultConfig<T> {
    /**
     * redisTemplate相关配置
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, T> redisTemplate(RedisConnectionFactory factory) {
        log.info("RedisTemplateConfig init start ...");
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        RedisTemplate<String,T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(objectJackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(objectJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(objectJackson2JsonRedisSerializer);
        redisTemplate.setConnectionFactory(factory);
        log.info("RedisTemplateConfig init end");
        return redisTemplate;
    }
}