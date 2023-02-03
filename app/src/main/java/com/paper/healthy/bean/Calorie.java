package com.paper.healthy.bean;

import org.litepal.crud.LitePalSupport;

/**
 * 食物对象
 * 摄入卡路里页面使用
 */
public class Calorie extends LitePalSupport {

    // 食物
    private String food;

    // 日期
    private String time;

    // 份
    private int num;
    //卡路里
    private int calorie;


    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }
}
