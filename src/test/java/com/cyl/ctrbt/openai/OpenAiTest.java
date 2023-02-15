package com.cyl.ctrbt.openai;

import com.cyl.ctrbt.ChatgptRobotBackApplication;
import com.theokanning.openai.completion.CompletionChoice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ChatgptRobotBackApplication.class)
@ActiveProfiles("dev")
public class OpenAiTest {
    @Autowired
    private OpenAiUtil openAiUtil;

    @Test
    public void testOpenAi(){
        List<CompletionChoice> completionChoices = openAiUtil.sendComplete("单身狗如何过情人节？");
        completionChoices.forEach(
                choice ->{
                    System.out.println("gpt3："+choice.getText());
                }
        );
    }
}
