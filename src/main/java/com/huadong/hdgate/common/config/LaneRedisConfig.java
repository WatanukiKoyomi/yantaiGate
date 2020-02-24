package com.huadong.hdgate.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * TODO
 *
 * @author chuyz
 * @date 2020/2/24 16:21
 */
@Configuration
public class LaneRedisConfig {

    @Value("${spring.laneredis.host}")
    private String host;
    @Value("${spring.laneredis.port}")
    private int port;
    @Value("${spring.laneredis.password}")
    private String password;

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Bean(name="laneRedistemplate")
    StringRedisTemplate laneRedistemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean(name="laneRedisPoolFactory")
    public JedisPool laneRedisPoolFactory()  throws Exception{
        logger.info("JedisPool注入成功！！");
        logger.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setJmxEnabled(true);
        jedisPoolConfig.setMaxTotal(20);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, 10000, password);
        return jedisPool;
    }
}
