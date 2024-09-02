package com.example.hhcs.domain;

public class LastResult {
    private int code;
    private Object all;
    private Object ing;
    private Object end;
    private String msg;


    public LastResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public LastResult(int code, Object all, Object ing, Object end) {
        this.code = code;
        this.all = all;
        this.ing = ing;
        this.end = end;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getAll() {
        return all;
    }

    public void setAll(Object all) {
        this.all = all;
    }

    public Object getIng() {
        return ing;
    }

    public void setIng(Object ing) {
        this.ing = ing;
    }

    public Object getEnd() {
        return end;
    }

    public void setEnd(Object end) {
        this.end = end;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
