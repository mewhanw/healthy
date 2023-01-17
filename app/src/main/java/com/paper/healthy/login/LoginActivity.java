package com.paper.healthy.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.paper.healthy.MainActivity;
import com.paper.healthy.R;
import com.paper.healthy.bean.User;

import org.litepal.LitePal;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    // 登录按钮
    private Button submit;
    // 用户框
    private EditText user;
    // 密码框
    private EditText pass;
    // 忘记密码按钮
    private TextView forget;
    // 注册按钮
    private TextView register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //加载控件
        initView();
        // 添加点击事件
        initListenter();
    }

    /**
     * 加载控件
     */
    private void initView() {
        // 提交按钮
        submit = findViewById(R.id.submit);
        // 用户名
        user = findViewById(R.id.user);
        // 密码
        pass = findViewById(R.id.pass);
        // 忘记密码按钮
        forget = findViewById(R.id.forget);
        // 注册按钮
        register = findViewById(R.id.register);
    }
    /**
     * 添加点击事件
     */
    private void initListenter() {
        // 登录按钮点击事件
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 验证用户名不超过20位 判空
                String usertext = user.getText().toString();
                // 如果为空提示返回
                if (usertext.isEmpty()) {
                    Toast.makeText(LoginActivity.this,"请填写用户名",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    // 如果超过20位提示返回
                    if(usertext.length()>20){
                        Toast.makeText(LoginActivity.this,"用户名不超过20位",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 验证密码6-12位 判空
                String passtext = pass.getText().toString();
                // 如果为空提示返回
                if (passtext.isEmpty()) {
                    Toast.makeText(LoginActivity.this,"请填写密码",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    // 如果不是6-12位 提示返回
                    if(passtext.length()>10 || passtext.length()<6){
                        Toast.makeText(LoginActivity.this,"密码6-12位",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 验证用户是否存在
                List<User> users = LitePal.where("name = ? and pass = ?",usertext,  passtext).find(User.class);
                // 跳转主页面
                if (users!=null && users.size()>0){
                    // 跳转主页面
                    ActivityUtils.startActivity(MainActivity.class);
                    // 关闭登陆页面
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"用户名或者密码不正确",Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 忘记密码按钮点击事件
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转忘记密码页面
                ActivityUtils.startActivity(ForgetActivity.class);
            }
        });
        // 注册按钮点击事件
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转注册页面
                ActivityUtils.startActivity(RegisterActivity.class);
            }
        });
    }


}