package com.paper.healthy.bean;

import org.litepal.crud.LitePalSupport;

/**
 * 运动对象
 * 运动页面使用
 */
public class Sport extends LitePalSupport {

    // 用户名
    private String name;
    // 运动
    private String sport;

    // 开始时间
    private String stime;
    // 结束时间
    private String etime;
    // 时长
    private int time;

    //卡路里
    private int calorie;


    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
