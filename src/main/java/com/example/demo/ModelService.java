package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ModelService {

    @Autowired
    private RestTemplate restTemplate;
    
    //Total Model Num: 2
    int count=0;
    int caseNum=0;

    public void callModel(String question) {
    	
    	//Initial output
    	if(count==0) {
    		//
    		System.out.println("[URL MONITOR ACTIVATED]");
    		sendMessageToGroup("* * 模型监测已启动 * *");
    	}
    	System.out.println("\n[CALLED]");
    	if(caseNum>1) {caseNum=0;}
    	System.out.println(" CASE: "+caseNum);
    
        //Time record
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = now.format(formatter);

        
        String baseUrl = "http://modelhub.4pd.io/learnware/models/openai/4pd/api/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer 4a69fbf662204f7fb8ea5846779c4c2a");

        Map<String, Object> body = new HashMap<>();
        
        //Url selection
        String cup ="";
        switch(caseNum){
        case 0:
        	cup="public/qwen2-72b-instruct-awq@main";
        	break;
        case 1:
        	cup="public/qwen2-72b-instruct-gptq-int4@main";
        	break;
        }
   
        
        
        body.put("model",cup );
        body.put("messages", new Object[]{new HashMap<String, String>() {{
            put("role", "user");
            put("content", question);
        }}});
        body.put("temperature", 0.01);
        body.put("max_tokens", 4096);
        body.put("top_p", 1);
        body.put("stop", null);
        body.put("timeout", 1000);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("* * SUCCESS * * [" + formattedTime + "]"+" [Model: "+cup+"] "+"("+count+")");
               // sendMessageToGroup("* 调用成功 * [" + formattedTime +"] [ Model ]:"+cup);
            } else {
                System.out.println("* * FAIL * * [" + formattedTime + "]"+" [Model: "+cup+"] "+"("+count+")");
                sendMessageToGroup("[注意] 调用失败 [" + formattedTime + " 模型已下线:"+cup+"]");
            }
        } catch (RestClientException e) {
            System.out.println("FAIL[Exception]");
            System.out.println("Failed to call model: " + e.getMessage() + " [" + formattedTime + "]");
            sendMessageToGroup("[注意]调用失败(Exception) [" + formattedTime +"] [ Model]"+cup);
        }
        count++;
        caseNum++;
    }

    private void sendMessageToGroup(String message) {
        String url = "https://www.qingzhouim.com/api/msg/groupmsgsend?access_token=dbb599d0619e9179c519f25fbfff6bca9";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> msg = new HashMap<>();
        msg.put("message", new HashMap<String, Object>() {{
            put("header", new HashMap<String, Object>() {{
                put("toid", new int[]{1627615});
            }});
            put("body", new Object[]{new HashMap<String, String>() {{
                put("content", message);
                put("type", "TEXT");
            }}});
        }});
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(msg, headers);
        restTemplate.postForEntity(url, entity, String.class);
    }
}
