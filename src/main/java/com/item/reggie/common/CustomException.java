package com.item.reggie.common;

/**
 * @author Li Guotng
 * @create 2022-07-10 15:34
 * 自定义业务异常
 */
public class CustomException extends RuntimeException{

    public CustomException(String message) {
        super(message);
    }
}
