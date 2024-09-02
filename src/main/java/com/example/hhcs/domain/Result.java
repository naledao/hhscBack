package com.example.hhcs.domain;

import java.util.List;

public class Result {
    private int code;
    private String message;
    private WxReturn wxReturn;
    private User user;
    private List<Object> goodlist;
    private List<Object> goodlist_end;
    private List<Object> goodlist_ing;
    private int warehouseid;
    private int imageid;
    private Object object;
    private String Simageid;
    int sum;
    int csum;

    public int getCsum() {
        return csum;
    }

    public void setCsum(int csum) {
        this.csum = csum;
    }

    public Result(int code, int sum, int csum, Object object)
    {
        this.code=code;
        this.sum=sum;
        this.object=object;
        this.csum=csum;
    }
    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Result(int code, List<Object> goodlist, List<Object> goodlist_end, List<Object> goodlist_ing) {
        this.code = code;
        this.goodlist = goodlist;
        this.goodlist_end = goodlist_end;
        this.goodlist_ing = goodlist_ing;
    }

    public List<Object> getGoodlist_ing() {
        return goodlist_ing;
    }

    public void setGoodlist_ing(List<Object> goodlist_ing) {
        this.goodlist_ing = goodlist_ing;
    }

    public Result(int code, List<Object> goodlist, List<Object> goodlist_end) {
        this.code = code;
        this.goodlist = goodlist;
        this.goodlist_end = goodlist_end;
    }


    public List<Object> getGoodlist_end() {
        return goodlist_end;
    }

    public void setGoodlist_end(List<Object> goodlist_end) {
        this.goodlist_end = goodlist_end;
    }

    public String getSimageid() {
        return Simageid;
    }
    public void setSimageid(String simageid) {
        Simageid = simageid;
    }

    public Result(int code, int warehouseid, String Simageid) {
        this.code = code;
        this.warehouseid = warehouseid;
        this.Simageid=Simageid;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(int warehouseid) {
        this.warehouseid = warehouseid;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public List<Object> getGoodlist() {
        return goodlist;
    }

    public void setGoodlist(List<Object> goodlist) {
        this.goodlist = goodlist;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WxReturn getWxReturn() {
        return wxReturn;
    }

    public void setWxReturn(WxReturn wxReturn) {
        this.wxReturn = wxReturn;
    }

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

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public Result(int code,WxReturn wxReturn){
        this.code=code;
        this.wxReturn=wxReturn;
    }
    public Result(int code,WxReturn wxReturn,User user){
        this.code=code;
        this.wxReturn=wxReturn;
        this.user=user;
    }

    public Result(int code, List<Object> goodlist) {
        this.code = code;
        this.goodlist = goodlist;
    }

    public Result(int code, int warehouseid, int imageid) {
        this.code = code;
        this.warehouseid = warehouseid;
        this.imageid = imageid;
    }

    public Result(int code, Object object) {
        this.code = code;
        this.object = object;
    }
}
