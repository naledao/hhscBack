package com.example.hhcs.domain;

public class CstoreLast {
    private String id;
    private String name;
    private String pic;
    private String description;
    private String image;
    private double price;
    private double Purchase_price;
    private String business;
    private String area;
    private int status;
    private String openid;
    private String Purchase_people;
    private int activation;
    private String time;
    private String buyername;

    public CstoreLast(String id, String name, String pic, String description, String image, double price, double purchase_price, String business, String area, int status, String openid, String purchase_people, int activation, String time, String buyername) {
        this.id = id;
        this.name = name;
        this.pic = pic;
        this.description = description;
        this.image = image;
        this.price = price;
        Purchase_price = purchase_price;
        this.business = business;
        this.area = area;
        this.status = status;
        this.openid = openid;
        Purchase_people = purchase_people;
        this.activation = activation;
        this.time = time;
        this.buyername = buyername;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPurchase_price() {
        return Purchase_price;
    }

    public void setPurchase_price(double purchase_price) {
        Purchase_price = purchase_price;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPurchase_people() {
        return Purchase_people;
    }

    public void setPurchase_people(String purchase_people) {
        Purchase_people = purchase_people;
    }

    public int getActivation() {
        return activation;
    }

    public void setActivation(int activation) {
        this.activation = activation;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }
}
