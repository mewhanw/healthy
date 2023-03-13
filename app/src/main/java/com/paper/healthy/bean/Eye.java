package com.paper.healthy.bean;

import org.litepal.crud.LitePalSupport;

/**
 * 食物对象
 * 摄入卡路里页面使用
 */
public class Eye {

    // 日期
    private String time;
    // 消耗
    private int sport;
    // 摄入
    private int calorie;
    // 差值
    private int dif;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSport() {
        return sport;
    }

    public void setSport(int sport) {
        this.sport = sport;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getDif() {
        return dif;
    }

    public void setDif(int dif) {
        this.dif = dif;
    }
}
