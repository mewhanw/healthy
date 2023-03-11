package com.paper.healthy.bean;

import org.litepal.crud.LitePalSupport;

/**
 * 用户对象
 * 注册 改密码 登陆使用
 */
public class User extends LitePalSupport {

    // 用户名
    private String name;
    // 密码
    private String pass;

    //生日
    private String brithday;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }
}
