package com.example.hhcs.domain;

public class BstoreLast {
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
    private String keynm;
    private String buyername;

    public BstoreLast(int id, String name, String pic, String description, int image, double price, double purchase_price, String business, String area, int status, String openid, String purchase_people, String keynm, String buyername) {
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
        this.keynm = keynm;
        this.buyername = buyername;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getKeynm() {
        return keynm;
    }

    public void setKeynm(String keynm) {
        this.keynm = keynm;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }
}
