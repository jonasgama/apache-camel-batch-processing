package com.example.customconfigapi.cache

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import org.springframework.data.redis.connection.RedisConnectionFactory
import java.time.Duration
import java.util.*
import java.util.Collections.singletonMap


@Configuration
@EnableCaching
class CacheConfig {


    @Bean
    fun redisCacheManagerBuilderCustomizer(): RedisCacheManagerBuilderCustomizer? {
        return RedisCacheManagerBuilderCustomizer {
            builder: RedisCacheManagerBuilder ->
            val configurationMap: MutableMap<String, RedisCacheConfiguration> = HashMap()
            configurationMap["configs"] = defaultCacheConfig().entryTtl(Duration.ofSeconds(20))
            builder.withInitialCacheConfigurations(configurationMap)
        }
    }


    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory?): RedisCacheManager? {
        val config: RedisCacheConfiguration = defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1))
                .disableCachingNullValues()

        return RedisCacheManager.builder(connectionFactory!!)
                .withInitialCacheConfigurations(singletonMap("configs", config))
                .transactionAware()
                .build()
    }

}