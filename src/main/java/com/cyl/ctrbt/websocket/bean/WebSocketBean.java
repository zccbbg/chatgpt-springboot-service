package com.cyl.ctrbt.websocket.bean;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;

@Data
public class WebSocketBean {
    // websocket
    private WebSocketSession webSocketSession;
    // 客户端id
    private String clientId;
    // 最后更新时间
    private LocalDateTime lastMessageTime;
}
