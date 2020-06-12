package com.adnanbk.coronatracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class CoronaTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoronaTrackerApplication.class, args);
    }


    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("stats");
    }

}
