package com.example.hhcs.domain;

import com.theokanning.openai.completion.chat.ChatMessage;

import java.util.List;

public class ChatGptResult {
    private int code;
    private String message;
    private List<ChatMessage> list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ChatMessage> getList() {
        return list;
    }

    public void setList(List<ChatMessage> list) {
        this.list = list;
    }
}
