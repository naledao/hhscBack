package com.example.hhcs.domain;

public class HeroData {
    private String uid;
    private String name;
    private String alias;
    private String platform;
    private String photo;
    private String area;
    private String areaPower;
    private String city;
    private String cityPower;
    private String province;
    private String provincePower;
    private String guobiao;
    private String stamp;
    private String updatetime;
    private String clientIP;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaPower() {
        return areaPower;
    }

    public void setAreaPower(String areaPower) {
        this.areaPower = areaPower;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityPower() {
        return cityPower;
    }

    public void setCityPower(String cityPower) {
        this.cityPower = cityPower;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvincePower() {
        return provincePower;
    }

    public void setProvincePower(String provincePower) {
        this.provincePower = provincePower;
    }

    public String getGuobiao() {
        return guobiao;
    }

    public void setGuobiao(String guobiao) {
        this.guobiao = guobiao;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    @Override
    public String toString() {
        return "HeroData{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", platform='" + platform + '\'' +
                ", photo='" + photo + '\'' +
                ", area='" + area + '\'' +
                ", areaPower='" + areaPower + '\'' +
                ", city='" + city + '\'' +
                ", cityPower='" + cityPower + '\'' +
                ", province='" + province + '\'' +
                ", provincePower='" + provincePower + '\'' +
                ", guobiao='" + guobiao + '\'' +
                ", stamp='" + stamp + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", clientIP='" + clientIP + '\'' +
                '}';
    }
}
