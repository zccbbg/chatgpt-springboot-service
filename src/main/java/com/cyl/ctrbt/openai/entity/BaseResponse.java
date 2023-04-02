package com.cyl.ctrbt.openai.entity;

import lombok.Data;

import java.util.List;

/**
 * @author plexpt
 */
@Data
public class BaseResponse<T> {
    private String object;
    private List<T> data;
    private Error error;


    @Data
    public class Error {
        private String message;
        private String type;
        private String param;
        private String code;
    }
}
