package com.cyl.ctrbt.chatgpt;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;

import java.util.Arrays;
import java.util.List;

public class ChatGptUtil {
    private static  String token = System.getenv("OPENAI_TOKEN");
    private static OpenAiService service = new OpenAiService(token);
    public static List<CompletionChoice> sendComplete(String prompt) {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(prompt)
                .temperature(1d)
                .maxTokens(100)
                .topP(1d)
                .frequencyPenalty(0d)
                .presencePenalty(0d)
                .stop(Arrays.asList("\n"))
                .build();

        return service.createCompletion(completionRequest).getChoices();
    }
}
