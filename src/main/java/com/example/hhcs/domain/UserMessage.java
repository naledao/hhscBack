package com.example.hhcs.domain;

public class UserMessage {
    private String code;
    private String name;
    private String head;

    @Override
    public String toString() {
        return "{code:"+code+",name:"+name+",head:"+head+"}";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
