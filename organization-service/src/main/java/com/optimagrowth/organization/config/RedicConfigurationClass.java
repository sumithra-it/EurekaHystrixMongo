package com.optimagrowth.organization.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.cache.annotation.EnableCaching;

@Configuration
@EnableRedisRepositories
@EnableCaching
public class RedicConfigurationClass extends CachingConfigurerSupport{
	@Bean
    public JedisConnectionFactory jedisConnectionFactory()
    {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(standaloneoconfig());
        return jedisConnectionFactory;
    }
    @Bean
    public RedisTemplate<Object, Object> redisTemplate()
    {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setExposeConnection(true);
        return redisTemplate;
    }

    RedisStandaloneConfiguration standaloneoconfig() {
		RedisStandaloneConfiguration standaloneoconfig = new RedisStandaloneConfiguration("redis-cache", 6379);
		return standaloneoconfig;
	}
// CacheManager implementation that lazily builds ConcurrentMapCache instances for each getCache request. 
//    @Bean
//    public CacheManager cacheManager(RedisTemplate redisTemplate) {
//      RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//
//      // Number of seconds before expiration. Defaults to unlimited (0)
//      cacheManager.setDefaultExpiration(300);
    
    
//      return cacheManager;
//    }
    
//    config.put("testMap", new CacheConfig(24*60*1000, 12*60*1000));
//    return new RedissonSpringCacheManager(redissonClient, config);
}
