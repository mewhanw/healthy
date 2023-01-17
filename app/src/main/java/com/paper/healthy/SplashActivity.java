package com.paper.healthy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.paper.healthy.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 欢迎页面 设置版本号（build.gradle中 defaultConfig下的versionName）
        // findViewById获取页面的控件
        // setText给TextView控件设置文字
        // AppUtils.getAppVersionName()封装的方法获取版本名
        ((TextView)findViewById(R.id.version)).setText(AppUtils.getAppVersionName());
        // 开启子线程一秒后跳转页面(new Thread(){}.start();)
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    // 睡眠一秒等待
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // runOnUiThread()切换到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 如果未登录跳转登录页面
                        ActivityUtils.startActivity(LoginActivity.class);
                        // 登录跳转主页面

                        // 关闭欢迎页
                        finish();
                    }
                });
            }
        }.start();
    }
}