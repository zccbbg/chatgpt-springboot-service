package com.cyl.ctrbt.openai.entity.chat;

import com.cyl.ctrbt.openai.entity.billing.Usage;
import lombok.Data;

import java.util.List;

/**
 * chat答案类
 *
 * @author plexpt
 */
@Data
public class ChatCompletionResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChatChoice> choices;
    private Usage usage;
}
