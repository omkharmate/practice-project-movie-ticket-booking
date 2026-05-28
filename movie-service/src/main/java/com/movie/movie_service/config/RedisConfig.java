package com.movie.movie_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;

import org.springframework.data.redis.connection.RedisConnectionFactory;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public CacheManager cacheManager(
            RedisConnectionFactory connectionFactory) {

        // Jackson object mapper for JSON serialization/deserialization
        ObjectMapper objectMapper = new ObjectMapper();

        // Support Java 8 date/time types like LocalDate and LocalDateTime
        objectMapper.registerModule(new JavaTimeModule());

        // Store dates in readable format instead of timestamp arrays
        objectMapper.disable(
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
        );

        // Store type information in Redis to avoid LinkedHashMap casting issues
        objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL
        );

        // JSON serializer used by Redis
        GenericJackson2JsonRedisSerializer serializer =
                new GenericJackson2JsonRedisSerializer(objectMapper);

        // Redis cache configuration
        RedisCacheConfiguration config =
                RedisCacheConfiguration.defaultCacheConfig()

                        // Cache expires automatically after 10 minutes
                        .entryTtl(Duration.ofMinutes(10))

                        // Use JSON serializer for cache values
                        .serializeValuesWith(
                                RedisSerializationContext
                                        .SerializationPair
                                        .fromSerializer(serializer)
                        );

        // Create Redis cache manager with custom configuration
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }

}
