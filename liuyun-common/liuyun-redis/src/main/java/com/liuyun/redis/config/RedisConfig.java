package com.liuyun.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
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
    @Primary
    @Bean(name = "cacheManager")
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
     * redisTemplate 配置
     *
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.String, java.lang.Object>
     * @author wangdong
     * @date 2020/12/11 1:57 下午
     **/
    @Bean(value = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {

        // 设置序列化
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = jackson2JsonRedisSerializer();

        RedisSerializer<?> stringSerializer = new StringRedisSerializer();

        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        // key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        // value序列化
        redisTemplate.setValueSerializer(objectJackson2JsonRedisSerializer);
        // Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        // Hash value序列化
        redisTemplate.setHashValueSerializer(objectJackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * redisTemplate 配置 (使用默认 value 序列化方式) JdkSerializationRedisSerializer
     *
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.String, java.lang.Object>
     * @author wangdong
     * @date 2020/12/11 1:57 下午
     **/
    @Bean(value = "redisTemplate2")
    public RedisTemplate<String, Object> redisTemplate2() {
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        // key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        // Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 此项必须配置，否则会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX

        // objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }


    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setPort(redisProperties.getPort());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
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
                LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
        log.info("Redis 连接池 加载完成 HOST -> [{}] PORT -> [{}]", redisProperties.getHost(), redisProperties.getPort());
        return lettuceConnectionFactory;
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setPassword(redisProperties.getPassword());
                //.setDatabase(redisProperties.getDatabase())
                //.setTimeout(Math.toIntExact(redisProperties.getTimeout().getSeconds()))
                //.setConnectTimeout(Math.toIntExact(redisProperties.getTimeout().getSeconds()))
                //.setConnectionMinimumIdleSize(5);
        // 监控锁的看门狗超时，单位：毫秒
        // 加锁请求中未明确使用 leaseTimeout 参数的情况下 生效
        config.setLockWatchdogTimeout(30000L);
        RedissonClient redissonClient = Redisson.create(config);
        log.info("RedissonClient 加载完成 HOST -> [{}] PORT -> [{}]", redisProperties.getHost(), redisProperties.getPort());
        return redissonClient;
    }

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://123.57.73.216:6379")
                .setPassword("liuyun");
        // 监控锁的看门狗超时，单位：毫秒
        // 加锁请求中未明确使用 leaseTimeout 参数的情况下 生效
        config.setLockWatchdogTimeout(30000L);
        RedissonClient redissonClient = Redisson.create(config);
        log.info("RedissonClient 加载完成 redissonClient -> [{}] ", redissonClient);

    }
}
