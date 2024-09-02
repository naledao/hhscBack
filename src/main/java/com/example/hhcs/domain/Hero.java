package com.example.hhcs.domain;

public class Hero {
    private int code;
    private HeroData data;
    private String msg;
    private String docs;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HeroData getData() {
        return data;
    }

    public void setData(HeroData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", docs='" + docs + '\'' +
                '}';
    }
}
