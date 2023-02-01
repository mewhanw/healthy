package com.paper.healthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.ImageUtils;
import com.paper.healthy.calorie.CalorieFragment;
import com.paper.healthy.own.OwnFragment;
import com.paper.healthy.sports.SportFragment;

public class MainActivity extends FragmentActivity {
    // 下方按钮集合
    RadioGroup radioGroup;
    // fragme控件
    FrameLayout frameLayout;

    //每个页签
    // 卡路里
    CalorieFragment calorieFragment;
    //  我的
    OwnFragment ownFragment;
    // 运动
    SportFragment sportFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取控件
        // 下方按钮集合
        radioGroup = findViewById(R.id.tobar);
        // fragme控件
        frameLayout = findViewById(R.id.content);

        // 初始化每个页签
        // 卡路里
        calorieFragment = new CalorieFragment();
        //  我的
        ownFragment = new OwnFragment();
        // 运动
        sportFragment = new SportFragment();

        // 监听事件
        // 点击按钮切换不同页面
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.calorie) {
                    // 卡路里页面
                    //获取fragment管理器
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    //开启事务
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content,calorieFragment);
                    fragmentTransaction.commit();
                }
                if (radioGroup.getCheckedRadioButtonId() == R.id.sports) {
                    // 运动页面
                    //获取fragment管理器
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    //开启事务
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content,sportFragment);
                    fragmentTransaction.commit();
                }
                if (radioGroup.getCheckedRadioButtonId() == R.id.own) {
                    // 我的页面
                    //获取fragment管理器
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    //开启事务
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content,ownFragment);
                    fragmentTransaction.commit();
                }
            }
        });
        findViewById(R.id.calorie).performClick();
    }


    //    声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if (System.currentTimeMillis() - mExitTime > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}