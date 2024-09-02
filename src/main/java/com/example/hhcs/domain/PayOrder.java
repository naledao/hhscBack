package com.example.hhcs.domain;

public class PayOrder {
    private String payid;
    private String cloundid;
    private String secret;
    private String openid;

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getCloundid() {
        return cloundid;
    }

    public void setCloundid(String cloundid) {
        this.cloundid = cloundid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
