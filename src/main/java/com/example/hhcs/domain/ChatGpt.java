package com.example.hhcs.domain;


import com.theokanning.openai.completion.chat.ChatMessage;

import java.util.List;

public class ChatGpt {
    private String password;
    private String openid;
    private String question;
    private List<ChatMessage> list_message;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ChatMessage> getList_message() {
        return list_message;
    }

    public void setList_message(List<ChatMessage> list_message) {
        this.list_message = list_message;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
