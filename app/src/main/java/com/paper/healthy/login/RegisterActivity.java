package com.paper.healthy.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.StringUtils;
import com.paper.healthy.R;
import com.paper.healthy.bean.User;

public class RegisterActivity extends AppCompatActivity {
    // 登录按钮
    private Button submit;
    // 用户框
    private EditText user;
    // 密码框
    private EditText pass;
    // 再次输入密码框
    private EditText pass2;
    // 返回
    private TextView back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //加载控件
        initView();
        // 添加点击事件
        initListenter();
    }

    /**
     * 加载控件
     */
    private void initView() {
        // 注册按钮
        submit = findViewById(R.id.submit);
        // 用户名
        user = findViewById(R.id.user);
        // 密码
        pass = findViewById(R.id.pass);
        // 再次输入密码
        pass2 = findViewById(R.id.pass2);
        // back
        back = findViewById(R.id.back);

    }
    /**
     * 添加点击事件
     */
    private void initListenter() {
        // 注册按钮点击事件
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 验证用户名不超过20位 判空
                String usertext = user.getText().toString();
                // 如果为空提示返回
                if (usertext.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,"请填写用户名",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    // 如果超过20位提示返回
                    if(usertext.length()>20){
                        Toast.makeText(RegisterActivity.this,"用户名不超过20位",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 验证密码6-12位 判空
                String passtext = pass.getText().toString();
                // 如果为空提示返回
                if (passtext.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,"请填写密码",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    // 如果不是6-12位 提示返回
                    if(passtext.length()>10 || passtext.length()<6){
                        Toast.makeText(RegisterActivity.this,"密码6-12位",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 验证俩密码是否相同
                String pass2text = pass2.getText().toString();
                // 如果为空提示返回
                if (pass2text.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    // 如果不是6-12位 提示返回
                    if(!pass2text.equals(passtext)){
                        Toast.makeText(RegisterActivity.this,"两次输入密码不同",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 保存数据库
                User user = new User();
                user.setName(usertext);
                user.setPass(passtext);
                // save() 保存到数据库
                if (user.save()) {
                    // 保存成功提示 然后关闭当前页面
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}