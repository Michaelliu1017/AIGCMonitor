package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication  // spring Boot应用标识
@EnableScheduling      // 启用Spring 定时任务功能
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // 启动 Spring
    }
}
