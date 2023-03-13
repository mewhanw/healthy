package com.paper.healthy.eye;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ServiceUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.paper.healthy.R;
import com.paper.healthy.bean.Calorie;
import com.paper.healthy.bean.Eye;
import com.paper.healthy.bean.Sport;
import com.paper.healthy.calorie.CalorieAdapter;
import com.paper.healthy.config.SpConfig;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

/**
 * 摄入食物
 * 卡路里页面
 */
public class EyeActivity extends FragmentActivity {

    /**
     * 列表控件
     */
    RecyclerView recyc;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye);

        // 初始化控件
        initView();
    }




    /**
     * 初始化页面
     *
     * @param
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initView() {
        // 列表
        recyc = findViewById(R.id.recyc);
        // 获取所有运动
        List<Sport> sports = LitePal.where("name = '"+ SpConfig.getUsername()+"'").order("stime desc").find(Sport.class);
        // 获取所有摄入食物
        List<Calorie> calories = LitePal.where("name = '"+ SpConfig.getUsername()+"'").order("time desc").find(Calorie.class);
        Map<String, List<Sport>> sportmap = sports.stream().collect(Collectors.groupingBy(Sport::getStime2));
        Map<String, List<Calorie>> caloriesmap = calories.stream().collect(Collectors.groupingBy(Calorie::getTime2));
        Map<String,Eye> all = new HashMap();

        for (String sdate : sportmap.keySet()) {
            Boolean xitong = false;
            for (String cdate : caloriesmap.keySet()) {
                if (sdate.equals(cdate)) {
                    xitong = true;
                    var e = new Eye();
                    e.setTime(cdate);
                    Integer collect = caloriesmap.get(cdate).stream().collect(Collectors.summingInt(Calorie::getCalorie));
                    e.setCalorie(collect);
                    Integer collect1 = sportmap.get(sdate).stream().collect(Collectors.summingInt(Sport::getCalorie));
                    e.setSport(collect1);
                    e.setDif(collect - collect1);
                    all.put(cdate,e);
                }
            }
            if(!xitong){
                var e = new Eye();
                e.setTime(sdate);
                Integer collect1 = sportmap.get(sdate).stream().collect(Collectors.summingInt(Sport::getCalorie));
                e.setSport(collect1);
                e.setCalorie(0);
                e.setDif(0-collect1);
                all.put(sdate,e);
            }

        }
        for (String cdate : caloriesmap.keySet()) {
            if( all.get(cdate) == null){
                var e = new Eye();
                e.setTime(cdate);
                Integer collect = caloriesmap.get(cdate).stream().collect(Collectors.summingInt(Calorie::getCalorie));
                e.setCalorie(collect);
                e.setDif(collect);
                e.setSport(0);
                all.put(cdate,e);
            }
        }
        EyeAdapter adapter = new EyeAdapter( all.values().stream().collect(Collectors.toList()));
        recyc.setAdapter(adapter);

    }




}