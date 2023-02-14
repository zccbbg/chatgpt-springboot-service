package com.cyl.ctrbt.websocket;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.cyl.ctrbt.chatgpt.ChatGptUtil;
import com.cyl.ctrbt.websocket.bean.WebSocketBean;
import com.theokanning.openai.completion.CompletionChoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MyWebsocketHandler extends AbstractWebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final Map<String, WebSocketBean> webSocketBeanMap;
    private static final AtomicInteger clientIdMaker;   //仅用用于标识客户端编号

    static {
        webSocketBeanMap = new ConcurrentHashMap<>();
        clientIdMaker = new AtomicInteger(0);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //当WebSocket连接正式建立后，将该Session加入到Map中进行管理
        WebSocketBean webSocketBean = new WebSocketBean();
        webSocketBean.setWebSocketSession(session);
        webSocketBean.setClientId(UUID.fastUUID().toString());
        webSocketBeanMap.put(session.getId(), webSocketBean);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //当连接关闭后，从Map中移除session实例
        webSocketBeanMap.remove(session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("session {}", session.getId(), exception);
        //传输过程中出现了错误
        if (session.isOpen()) {
            session.close();
        }
        webSocketBeanMap.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //处理接收到的消息
        logger.info("Received message from client[ID:" + webSocketBeanMap.get(session.getId()).getClientId() +
                "]; Content is [" + message.getPayload() + "].");
        TextMessage textMessage;
        try {
            List<CompletionChoice> list = ChatGptUtil.sendComplete(message.getPayload());
            textMessage = new TextMessage(JSONUtil.toJsonStr(list));
            session.sendMessage(textMessage);
        } catch (Exception e) {
            textMessage = new TextMessage(JSONUtil.toJsonStr(e.getMessage()));
        }
        session.sendMessage(textMessage);

    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }
}