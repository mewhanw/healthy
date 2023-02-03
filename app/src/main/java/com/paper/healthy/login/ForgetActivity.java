
package com.paper.healthy.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.paper.healthy.R;
import com.paper.healthy.bean.User;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.LitePalBase;
import org.litepal.LitePalDB;

import java.util.List;

/**
 * 忘记密码页面
 */
public class ForgetActivity extends AppCompatActivity {
    // 登录按钮
    private Button submit;
    // 用户框
    private EditText user;
    // 密码框
    private EditText pass;
    // 返回
    private TextView back;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
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
        // back
        back = findViewById(R.id.back);

    }
    /**
     * 添加点击事件
     */
    private void initListenter() {
        // 登录按钮点击事件
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 查询用户名是否存在
                List<User> users = LitePal.where("name = ? ", user.getText().toString()).find(User.class);
                if(users!=null && users.size()>0){
                    // 验证密码6-12位
// 验证密码6-12位 判空
                    String passtext = pass.getText().toString();
                    // 如果为空提示返回
                    if (passtext.isEmpty()) {
                        Toast.makeText(ForgetActivity.this,"请填写密码",Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        // 如果不是6-12位 提示返回
                        if(passtext.length()>10 || passtext.length()<6){
                            Toast.makeText(ForgetActivity.this,"密码6-12位",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    // 保存密码
                    for (User user1 : users) {
                        user1.save();
                    }
                    // 返回登录页面
                    finish();
                }else {
                    Toast.makeText(ForgetActivity.this,"用户未注册",Toast.LENGTH_SHORT).show();
                }

            }
        });
        // 返回按钮 关闭页面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}