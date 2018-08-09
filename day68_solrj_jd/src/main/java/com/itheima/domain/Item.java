package com.itheima.domain;

/**
 * @Author: yangfei
 * @Date: 2018/8/6 20:45
 * @Description:
 */

public class Item {
    private String pid;
    private String picture;
    private String name;
    private Float price;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
