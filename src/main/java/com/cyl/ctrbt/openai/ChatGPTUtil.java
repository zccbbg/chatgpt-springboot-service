package com.cyl.ctrbt.openai;

import com.cyl.ctrbt.openai.entity.billing.CreditGrantsResponse;
import com.cyl.ctrbt.openai.entity.chat.ChatCompletion;
import com.cyl.ctrbt.openai.entity.chat.ChatCompletionResponse;
import com.cyl.ctrbt.openai.entity.chat.Message;
import com.cyl.ctrbt.util.Proxys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.net.Proxy;
import java.util.Arrays;

@Slf4j
@Component
public class ChatGPTUtil {
    @Value("${openai.secret_key}")
    private String token;

    private ChatGPT chatGPT;

    @Value("${proxy.ip}")
    private String proxyIp;
    @Value("${proxy.port}")
    private Integer proxyPort;

    @PostConstruct
    public void init(){
        if(!StringUtils.isEmpty(proxyIp)){
            //如果在国内访问，使用这个,在application.yml里面配置
            Proxy proxy = Proxys.http(proxyIp, proxyPort);
            chatGPT = ChatGPT.builder()
                    .apiKey(token)
                    .timeout(600)
                    .proxy(proxy)
                    .apiHost("https://api.openai.com/") //代理地址
                    .build()
                    .init();
        }else{
            chatGPT = ChatGPT.builder()
                    .apiKey(token)
                    .timeout(600)
                    .apiHost("https://api.openai.com/") //代理地址
                    .build()
                    .init();
        }

    }
    public Message chat(String userMessage,String user) {
        Message message = Message.of(userMessage);

        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .user(user)
                .messages(Arrays.asList(message))
                .maxTokens(3000)
                .temperature(0.9)
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        return response.getChoices().get(0).getMessage();
    }
}
