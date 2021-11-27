package com.learn.springboot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 完成对redis的整合的一些配置
 *
 * 注解 + 方法替代配置文件
 */
@Configuration
public class RedisConfig {


    /**
     * 1.创建JedisPoolConfig对象，在该对象中完成连接池配置
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);//最大空闲数
        jedisPoolConfig.setMinIdle(5);
        jedisPoolConfig.setMaxTotal(20);//最大连接数
        return jedisPoolConfig;
    }


    /**
     * 2.创建JedisConnectionFactory 配置redis连接信息
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig config){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        //关联连接池的配置对象
        factory.setPoolConfig(config);
        //配置连接redis的信息
        factory.setHostName("127.0.0.1");
        factory.setPort(6378);
        factory.setDatabase(0);
        return factory;
    }

    /**
     * 3.创建redisTemplate用于执行redis操作的方法
     * 【可以把上述两个方法都整合在这个方法里】
     *
     * redis存储的都是String，对象，list等都要序列化存储，json形式。
     * @return
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(JedisConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //关联
        template.setConnectionFactory(factory);
        //设置序列化器
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }

}