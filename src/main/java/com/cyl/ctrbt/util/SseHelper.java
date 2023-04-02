package com.cyl.ctrbt.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@UtilityClass
public class SseHelper {


    public void complete(SseEmitter sseEmitter) {

        try {
            sseEmitter.complete();
        } catch (Exception e) {

        }
    }

    public void send(SseEmitter sseEmitter, Object data) {

        try {
            sseEmitter.send(data);
        } catch (Exception e) {

        }
    }
}
