package com.cyl.ctrbt.openai;

import com.cyl.ctrbt.ChatgptRobotBackApplication;
import com.cyl.ctrbt.openai.entity.chat.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ChatgptRobotBackApplication.class)
@ActiveProfiles("dev")
public class OpenAiTest {
    @Autowired
    private ChatGPTUtil chatGPTUtil;

    @Test
    public void testChatGPT(){
        Message message = chatGPTUtil.chat("单身狗如何过情人节？", "单身狗");
        System.out.println(message.getContent());
    }
}
