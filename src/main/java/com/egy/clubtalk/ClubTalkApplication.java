package com.egy.clubtalk;

import es.moki.ratelimitj.core.limiter.request.RequestLimitRule;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import es.moki.ratelimitj.redis.request.RedisRateLimiterFactory;
import io.lettuce.core.RedisClient;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ClubTalkApplication {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(ClubTalkApplication.class, args);
    }

    @Bean
    public RedisRateLimiterFactory getRateLimiter() {
        String redisHost = env.getProperty("rate_limiter.redis.host");
        String redisPort = env.getProperty("rate_limiter.redis.port");
        return new RedisRateLimiterFactory(RedisClient.create(String.format("redis://%s:%s/", redisHost, redisPort)));
    }
}
