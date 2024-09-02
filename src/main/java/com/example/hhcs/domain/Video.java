package com.example.hhcs.domain;

public class Video {
    private String Href;
    private String NewTitle;
    private String PicSmall;
    private String Title;
    private String AID;

    public String getHref() {
        return Href;
    }

    public void setHref(String href) {
        Href = href;
    }

    public String getNewTitle() {
        return NewTitle;
    }

    public void setNewTitle(String newTitle) {
        NewTitle = newTitle;
    }

    public String getPicSmall() {
        return PicSmall;
    }

    public void setPicSmall(String picSmall) {
        PicSmall = picSmall;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAID() {
        return AID;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }
}
