package com.paper.healthy.sports;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ThreadUtils;
import com.paper.healthy.R;
import com.paper.healthy.bean.Sport;
import com.paper.healthy.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 运动计时页面
 */
public class SportTimeActivity extends AppCompatActivity {


    private Map<String, Integer> cals = new HashMap();

    // 运动
    private TextView sport;
    // 时间
    private TextView time;
    // 暂停
    private Button suspend;
    // 结束
    private Button finish;

    // 返回
    private View back;
    // 运动名称
    private String sporti = "";
    // 运动开始时间
    private String stime = "";
    // 累计毫秒
    private long timetotal = 0;


    /**
     * 时间进程
     */
    ThreadUtils.SimpleTask<String> task = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_time);
        // 初始化数据
        initData();
        // 初始化控件
        initView();
        // 添加监听事件
        initListenter();

    }

    /**
     * 监听事件
     * 返回按钮
     */
    private void initListenter() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击返回按钮 关闭当前页面
                finish();
            }
        });
        // 开始暂停按钮
        suspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (suspend.getText().toString().equals("开始")) {
                    if ("".equals(stime)) {
                        // 记录开始时间
                        stime = com.blankj.utilcode.util.TimeUtils.getNowString();
                    }
                    // 开始计时
                    suspend.setText("暂停");
                    // 一秒刷新一下时间 刷新页面
                    task = new ThreadUtils.SimpleTask<>() {
                        @Override
                        public String doInBackground() throws Throwable {
                            timetotal += 1;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // 页面展示时间加一秒
                                    time.setText(TimeUtils.secondConvertHourMinSecond(timetotal));
                                }
                            });
                            return null;
                        }

                        @Override
                        public void onSuccess(String result) {

                        }
                    };
                    // 任务一秒走一次
                    ThreadUtils.executeByCpuAtFixRate(task, 1, TimeUnit.SECONDS);
                } else {
                    // 结束计时
                    suspend.setText("开始");
                    ThreadUtils.cancel(task);
                }
            }
        });
        // 结束运动保存数据
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((timetotal / 60) == 0) {
                    // 运动时间太短 不保存数据
                    Toast.makeText(SportTimeActivity.this, "运动时间太短，不保存数据", Toast.LENGTH_SHORT).show();
                }else {
                    // 创建保存数据对象
                    Sport sport = new Sport();
                    // 运动类型
                    sport.setSport(sporti);
                    // 运动开始时间
                    sport.setStime(stime);
                    // 运动结束时间
                    sport.setEtime(com.blankj.utilcode.util.TimeUtils.getNowString());
                    // 运动时长
                    sport.setTime((int) (timetotal / 60));
                    // 消耗卡路里
                    sport.setCalorie(sport.getTime() * cals.get(sporti));
                    // 保存数据
                    sport.save();
                    // 吐司 提示保存成功
                    Toast.makeText(SportTimeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                }
                // 关闭当前页面
                finish();
            }
        });
    }

    /**
     * 控件初始化
     */
    private void initView() {
        // 运动
        sport = findViewById(R.id.sport);
        // 时间
        time = findViewById(R.id.time);
        // 暂停
        suspend = findViewById(R.id.suspend);
        // 结束
        finish = findViewById(R.id.finish);
        // 返回
        back = findViewById(R.id.back);

        if (sporti != null) {
            // 设置运动名称
            this.sport.setText(sporti);
            // 根据名称设置运动图片
            Drawable drawable = getDrawable(R.drawable.ic_paobu);
            switch (sporti) {
                case "跑步":
                    drawable = getDrawable(R.drawable.ic_paobu);
                    break;
                case "步行":
                    drawable = getDrawable(R.drawable.ic_buhang);
                    break;
                case "骑行":
                    drawable = getDrawable(R.drawable.ic_zihangche);
                    break;
                case "跳绳":
                    drawable = getDrawable(R.drawable.ic_tiaosheng);
                    break;
                case "瑜伽":
                    drawable = getDrawable(R.drawable.ic_yuqie);
                    break;
                case "动感单车":
                    drawable = getDrawable(R.drawable.ic_qiche);
                    break;
                case "呼啦圈":
                    drawable = getDrawable(R.drawable.ic_hulaquan);
                    break;

            }
            // 设置下图片大小
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            // 设置图片 （Textview上图片）
            this.sport.setCompoundDrawables(null, drawable, null, null);

        }
    }

    /**
     * 初始化一些数据
     */
    private void initData() {

        // 设置每个运动 消耗的卡路里基数  每分钟
        cals.put("跑步", 6);
        cals.put("步行", 3);
        cals.put("骑行", 10);
        cals.put("跳绳", 20);
        cals.put("瑜伽", 2);
        cals.put("动感单车", 10);
        cals.put("跑步机", 6);
        cals.put("呼啦圈", 4);

        // 获取上个类传过来的运动类型
        Intent intent = getIntent();
        Bundle bundleExtra = intent.getExtras();
        sporti = bundleExtra.getString("sport");

    }

}