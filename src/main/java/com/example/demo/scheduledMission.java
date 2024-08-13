package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component  // 标记为组件，由Spring管理
public class scheduledMission {

    @Autowired  // 自动注入ModelService
    private ModelService modelService;

    @Scheduled(fixedRate = 10000)  //10000 每10秒执行一次
    public void performTask() {
        String question = "are you a human or a bot";  // 示例问题
        modelService.callModel(question);  // 传递问题到callModel方法
    }
}
