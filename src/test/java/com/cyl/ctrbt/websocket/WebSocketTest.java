package com.cyl.ctrbt.websocket;

import org.junit.Test;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class WebSocketTest {
    private static final AtomicInteger count = new AtomicInteger(0);

    @Test
    public void test() {
        URI uri = URI.create("ws://127.0.0.1:8081/websocket");  //注意协议号为ws
        MyWebSocketClient client = new MyWebSocketClient(uri);
        client.addHeader("token", "token-123456");              //这里为header添加了token，实现简单的校验
        try {
            client.connectBlocking();   //在连接成功之前会一直阻塞

            while (true) {
                if (client.isOpen()) {
                    client.send("先有鸡还是先有蛋？");
                }
                Thread.sleep(1000000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
