package com.paper.healthy.bean;

import org.litepal.crud.LitePalSupport;

/**
 * 体重对象
 * 体重折线图用
 */
public class Weigt extends LitePalSupport {

    // 用户名
    private String name;
    // 时间
    private String time;

    // 体重 千克
    private int weight;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
