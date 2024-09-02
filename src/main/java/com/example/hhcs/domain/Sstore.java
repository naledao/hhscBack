package com.example.hhcs.domain;

public class Sstore {
    private int id;
    private String name;
    private String pic;
    private String description;
    private int image;
    private double price;
    private double Purchase_price;
    private String business;
    private String area;
    private int status;
    private String openid;
    private String Purchase_people;



    public String getPurchase_people() {
        return Purchase_people;
    }

    public void setPurchase_people(String purchase_people) {
        Purchase_people = purchase_people;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
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
}
