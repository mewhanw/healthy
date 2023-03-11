package com.paper.healthy.config;

import com.blankj.utilcode.util.SPUtils;

public class SpConfig {
    // 用户姓名
    private static String USERNAME = "username";
    public static String getUsername() {
        return SPUtils.getInstance().getString(USERNAME,"");
    }
    public static void setUsername(String username) {
        SPUtils.getInstance().put(USERNAME,username);

    }


}
