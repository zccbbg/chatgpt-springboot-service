package com.cyl.ctrbt.openai;

import com.cyl.ctrbt.openai.entity.chat.ChatCompletion;
import com.cyl.ctrbt.openai.entity.chat.Message;
import com.cyl.ctrbt.openai.listener.ConsoleStreamListener;
import com.cyl.ctrbt.util.Proxys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.Proxy;
import java.util.Arrays;

@Slf4j
@Component
public class ChatGPTStrreamUtil {
    @Value("${openai.secret_key}")
    private String token;

    private ChatGPTStream chatGPTStream;

    @Value("${proxy.ip}")
    private String proxyIp;
    @Value("${proxy.port}")
    private Integer proxyPort;

    @PostConstruct
    public void init(){
        //如果在国内访问，使用这个
        Proxy proxy = Proxys.http(proxyIp, proxyPort);

        chatGPTStream = ChatGPTStream.builder()
                .apiKey(token)
                .timeout(900)
                .proxy(proxy)
                .apiHost("https://api.openai.com/") //代理地址
                .build()
                .init();
    }
    public void chat(String userMessage,String user) {
        ConsoleStreamListener listener = new ConsoleStreamListener();
        Message message = Message.of(userMessage);
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .user(user)
                .messages(Arrays.asList(message))
                .build();
        chatGPTStream.streamChatCompletion(chatCompletion, listener);
    }
}
