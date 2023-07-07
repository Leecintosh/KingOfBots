package com.kob.matchingSystem;

import com.kob.matchingSystem.service.impl.MatchingServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MatchingSystemApplication {
    public static void main(String[] args) {
        MatchingServiceImpl.matchingPool.start(); // start the matching pool thread
        SpringApplication.run(MatchingSystemApplication.class, args);
    }
}