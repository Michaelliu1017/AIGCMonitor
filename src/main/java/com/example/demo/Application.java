package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication  // 标识这是一个Spring Boot应用
@EnableScheduling      // 启用Spring的定时任务功能
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // 启动Spring应用
    }
}
