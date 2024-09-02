package com.example.hhcs.domain;

public class ChatGptMessageYoN {
    private String role;
    private int yorn;
    private String message;
    private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getYorn() {
        return yorn;
    }

    public void setYorn(int yorn) {
        this.yorn = yorn;
    }
}
