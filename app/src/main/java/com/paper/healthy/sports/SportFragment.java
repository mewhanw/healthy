package com.paper.healthy.sports;

import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.paper.healthy.R;
import com.paper.healthy.bean.Calorie;
import com.paper.healthy.bean.Sport;
import com.paper.healthy.calorie.CalorieAdapter;
import com.paper.healthy.config.SpConfig;

import org.litepal.LitePal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运动页面
 * 消耗卡路里页面
 */
public class SportFragment extends Fragment {

    /**
     * 列表控件
     */
    RecyclerView recyc;

    /**
     * 卡路里消耗总数
     */
    TextView sport;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_sports, container, false);
        // 初始化控件
        initView(contextView);
        // 设置点击事件
        setclick(contextView);
        return contextView;
    }




    /**
     * 初始化页面
     * @param contextView
     */
    private void initView(View contextView) {
        // 列表
        recyc = contextView.findViewById(R.id.recyc);
        // 获取所有运动
        List<Sport> all = LitePal.where("name = '"+ SpConfig.getUsername()+"'").order("stime desc").find(Sport.class);
        SportAdapter adapter = new SportAdapter(all);
        recyc.setAdapter(adapter);
        // 运动
        sport = contextView.findViewById(R.id.sport);
        // 查询当天运动消耗总和
        Integer sporte = LitePal.where("name = '"+ SpConfig.getUsername()+"' and stime like '" + TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd")) +"%'").sum(Sport.class, "calorie", Integer.class);
        if(sporte!=null){
            // 设置卡路里总和
            sport.setText(String.valueOf(sporte));
        }
    }


    /**
     * 设置点击事件
     * @param contextView
     */
    private void setclick(View contextView) {
        /**
         * 跑步
         */
        contextView.findViewById(R.id.paobu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSportTime("跑步");
            }
        });

        /**
         * 步行
         */
        contextView.findViewById(R.id.buhang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSportTime("步行");
            }
        });

        /**
         * 骑行
         */
        contextView.findViewById(R.id.qixing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSportTime("骑行");
            }
        });

        /**
         * 跳绳
         */
        contextView.findViewById(R.id.tiaosheng).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSportTime("跳绳");
            }
        });

        /**
         * 瑜伽
         */
        contextView.findViewById(R.id.yuqie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSportTime("瑜伽");
            }
        });

        /**
         * 动感单车
         */
        contextView.findViewById(R.id.qiche).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSportTime("动感单车");
            }
        });

        /**
         * 跑步机
         */
        contextView.findViewById(R.id.paobuji).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSportTime("跑步机");
            }
        });

        /**
         * 呼啦圈
         */
        contextView.findViewById(R.id.hulaquan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSportTime("呼啦圈");
            }
        });

        /**
         * 列表点击事件
         */
        ((SportAdapter) recyc.getAdapter()).setOnClickCal(new SportAdapter.OnClickCal() {
            @Override
            public void onClick(Sport item) {
                // 修改
//                editSport(item.getSport(),item.getTime(),item);
            }

            @Override
            public void onLongClick(Sport item) {
                // 创建dialog
                // 提示是否删除数据
                AlertDialog.Builder delDialog = new AlertDialog.Builder(getContext());
                // 设置标题
                delDialog.setTitle("删除");
                // 设置提示信息
                delDialog.setMessage("确定删除本条数据吗？");
                // 取消按钮 关闭dialog
                delDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                delDialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 删除数据库
                        item.delete();
                        // 刷新列表
                        dataList();
                    }
                });
                // 展示dialog
                delDialog.show();

            }
        });


    }

    /**
     * 跳转到运动计时页面
     * @param sport
     */
    private void goSportTime(String sport) {
        Bundle bundle = new Bundle();
        bundle.putString("sport",sport);
        // 打开计时页面  传值运动类型
        ActivityUtils.startActivityForResult(bundle,this,SportTimeActivity.class,123);
    }

    /**
     * 刷新list数据
     * 总和刷新
     */
    private void dataList() {
        // 根据时间倒序 获取运动数据集合数据库
        List<Sport> all = LitePal.where("name = '"+ SpConfig.getUsername()+"'").order("stime desc").find(Sport.class);
        // 设置新数据
        ((SportAdapter)recyc.getAdapter()).setLists(all);
        // 刷新列表页面
        ((SportAdapter)recyc.getAdapter()).notifyDataSetChanged();
        // 查询当天运动消耗总和
        Integer sporte = LitePal.where("name = '"+ SpConfig.getUsername()+"' and stime like '" + TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd")) +"%'").sum(Sport.class, "calorie", Integer.class);
        if(sporte!=null){
            // 设置当前运动总和
            sport.setText(String.valueOf(sporte));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 返回页面的时候 刷新一些列表
        dataList();
    }
}