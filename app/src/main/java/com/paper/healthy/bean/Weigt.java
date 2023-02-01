package com.paper.healthy.bean;

import org.litepal.crud.LitePalSupport;

public class Weigt extends LitePalSupport {



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
}
