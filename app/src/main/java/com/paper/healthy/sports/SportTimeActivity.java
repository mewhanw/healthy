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
        initData();
        initView();
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
                finish();
            }
        });
        suspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (suspend.getText().toString().equals("开始")) {
                    if ("".equals(stime)) {
                        stime = com.blankj.utilcode.util.TimeUtils.getNowString();
                    }
                    suspend.setText("暂停");
                    task = new ThreadUtils.SimpleTask<>() {
                        @Override
                        public String doInBackground() throws Throwable {
                            timetotal += 1;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    time.setText(TimeUtils.secondConvertHourMinSecond(timetotal));
                                }
                            });
                            return null;
                        }

                        @Override
                        public void onSuccess(String result) {

                        }
                    };
                    ThreadUtils.executeByCpuAtFixRate(task, 1, TimeUnit.SECONDS);
                } else {
                    suspend.setText("开始");
                    ThreadUtils.cancel(task);
                }
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((timetotal / 60) == 0) {
                    Toast.makeText(SportTimeActivity.this, "运动时间太短，不保存数据", Toast.LENGTH_SHORT).show();
                }else {
                    Sport sport = new Sport();
                    sport.setSport(sporti);
                    sport.setStime(stime);
                    sport.setEtime(com.blankj.utilcode.util.TimeUtils.getNowString());
                    sport.setTime((int) (timetotal / 60));
                    sport.setCalorie(sport.getTime() * cals.get(sporti));
                    sport.save();
                    Toast.makeText(SportTimeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                }
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
            this.sport.setText(sporti);
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
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            this.sport.setCompoundDrawables(null, drawable, null, null);

        }
    }

    /**
     * 初始化一些数据
     */
    private void initData() {

        cals.put("跑步", 6);
        cals.put("步行", 3);
        cals.put("骑行", 10);
        cals.put("跳绳", 20);
        cals.put("瑜伽", 2);
        cals.put("动感单车", 10);
        cals.put("跑步机", 6);
        cals.put("呼啦圈", 4);

        Intent intent = getIntent();
        Bundle bundleExtra = intent.getExtras();
        sporti = bundleExtra.getString("sport");

    }

}