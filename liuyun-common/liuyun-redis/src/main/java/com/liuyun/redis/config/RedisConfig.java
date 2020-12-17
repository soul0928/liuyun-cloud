package com.liuyun.redis.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisConfig
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/11 13:42
 **/
@Slf4j
@Configuration
@EnableCaching
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisProperties redisProperties;

    public RedisConfig() {
        log.info("INIT Redis Config ... ");
    }

    /**
     * 自定义缓存key的生成策略
     *
     * @return org.springframework.cache.interceptor.KeyGenerator
     * @author wangdong
     * @date 2020/12/11 1:52 下午
     **/
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * 缓存配置管理器
     *
     * @param lettuceConnectionFactory {@link LettuceConnectionFactory}
     * @return org.springframework.cache.CacheManager
     * @author wangdong
     * @date 2020/12/11 1:53 下午
     **/
    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        //以锁写入的方式创建RedisCacheWriter对象
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(lettuceConnectionFactory);
        //        设置CacheManager的Value序列化方式为JdkSerializationRedisSerializer,
        //        但其实RedisCacheConfiguration默认就是使用
        //        StringRedisSerializer序列化key，
        //        JdkSerializationRedisSerializer序列化value,
        //        所以以下注释代码就是默认实现，没必要写，直接注释掉
        // RedisSerializationContext.SerializationPair pair = RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer(this.getClass().getClassLoader()));
        // RedisCacheConfiguration redis = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);

        // 创建默认缓存配置对象
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        return new RedisCacheManager(writer, config);
    }

    /**
     * 获取缓存操作助手对象 及其序列化方式
     *
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.String, java.lang.Object>
     * @author wangdong
     * @date 2020/12/11 1:57 下午
     **/
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        // 不适用默认的dateTime进行序列化,使用JSR310的LocalDateTimeSerializer
        om.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        // 这是序列化LocalDateTIme和LocalDate的必要配置,由Jackson-data-JSR310实现
        om.registerModule(new JavaTimeModule());
        // 必须配置
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();

        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        // key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        // value序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        // Hash value序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisProperties.Cluster cluster = redisProperties.getCluster();
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(cluster.getNodes());
        redisClusterConfiguration.setMaxRedirects(cluster.getMaxRedirects());
        redisClusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        RedisProperties.Pool pool = redisProperties.getLettuce().getPool();
        //连接池配置
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(pool.getMaxIdle());
        genericObjectPoolConfig.setMinIdle(pool.getMinIdle());
        genericObjectPoolConfig.setMaxTotal(pool.getMaxActive());
        genericObjectPoolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());

        //redis客户端配置
        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder().
                commandTimeout(redisProperties.getTimeout())
                .poolConfig(genericObjectPoolConfig)
                .build()
        ;

        //根据配置和客户端配置创建连接
        LettuceConnectionFactory lettuceConnectionFactory = new
                LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);
        log.info("Redis 连接池 加载完成 Nodes -> [{}]", cluster.getNodes());
        return lettuceConnectionFactory;
    }
}
