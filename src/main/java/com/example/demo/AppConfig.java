package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration  // 标记该类为配置类
public class AppConfig {

    @Bean // 定义一个Spring管理的Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(); // 实例化RestTemplate，用于发起REST请求
    }
}
