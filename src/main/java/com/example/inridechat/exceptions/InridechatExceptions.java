package com.example.inridechat.exceptions;


public class InridechatExceptions extends Exception{
    private final int statusCode;

    public InridechatExceptions(int statusCode, String errorMessage) {
        super(errorMessage);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
