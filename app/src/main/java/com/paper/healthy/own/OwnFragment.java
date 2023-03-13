package com.paper.healthy.own;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.paper.healthy.R;
import com.paper.healthy.bean.Calorie;
import com.paper.healthy.bean.Eye;
import com.paper.healthy.bean.Weigt;
import com.paper.healthy.config.SpConfig;
import com.paper.healthy.eye.EyeActivity;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import im.dacer.androidcharts.LineView;

/**
 * 我的页面
 */
public class OwnFragment extends Fragment {

    // 表 折线图
   private LineView lineView;
   // 添加体重 新增按钮
   private ImageView add;
   // 计算bmi
   private Button submit;
   // 身高
   private EditText hei;
   // 体重
   private EditText wei;
   // 退出
   private Button exit;
   // 查看卡路里
   private Button eye;

   // 男女
   private RadioGroup group;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_own, container, false);
        // 初始化控件
        initView(contextView);
        // 添加监听
        initListenter();
        return contextView;
    }


    /**
     * 添加控件
     * @param contextView
     */
    private void initView(View contextView){
        // 表 折线图
        lineView = (LineView)contextView.findViewById(R.id.line_view);
        // 添加体重 新增按钮
        add = contextView.findViewById(R.id.addme);
        // 计算bmi
        submit = contextView.findViewById(R.id.submit);
        // 退出
        exit = contextView.findViewById(R.id.exit);
        // 查看卡路里
        eye = contextView.findViewById(R.id.eye);
        // 身高
        hei = contextView.findViewById(R.id.hei);
        // 体重
        wei = contextView.findViewById(R.id.wei);
        // 男女
        group = contextView.findViewById(R.id.group);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setLineView();
        }
    }

    /**
     * 设置表数据
     * @param
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setLineView(){
        // 根据添加时间正序排序 取10条
        List<Weigt> all = LitePal.where("name = '"+ SpConfig.getUsername()+"'").order("time desc").limit(10).find(Weigt.class);
        all.sort(new Comparator<Weigt>() {
            @Override
            public int compare(Weigt weigt1, Weigt weigt2) {
                return  weigt1.getTime().compareTo(weigt2.getTime());
            }
        });
        lineView.setDrawDotLine(false); //optional
        // 展示全部数据
        lineView.setShowPopup(LineView.SHOW_POPUPS_All); //optional
        // 底部数据
        ArrayList<String> bottom = new ArrayList<>();
        // 折线图数据
        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        ArrayList<Integer> dataListsitem = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(all)){
            for (Weigt weigt : all) {
                // 截取日月展示
                bottom.add(weigt.getTime().substring(5,10));
                dataListsitem.add(weigt.getWeight());
            }
        }else {
            // 为空设置0
            bottom.add("月-日");
            dataListsitem.add(0);
        }
        lineView.setBottomTextList(bottom);
        dataLists.add(dataListsitem);
        // 展示
        lineView.setDataList(dataLists); //or lineView.setFloatDataList(floatDataLists)
    }


    /**
     * 添加监听事件
     */
    private void initListenter() {
        // 退出
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 杀死所有进程 exit（0） 退出程序
                System.exit(0);
            }
        });
        // 计算bmi
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 身高
                String heivalue = hei.getText().toString();
                //  体重
                String weivalue = wei.getText().toString();
                // 0男 1女
                int sex = group.getCheckedRadioButtonId() == R.id.man? 0 : 1;
                // 身高体重为数字
                if(TextUtils.isDigitsOnly(heivalue) && TextUtils.isDigitsOnly(weivalue)){
                    // 身高体重大于0
                    if(Integer.valueOf(heivalue)<=0 && Integer.valueOf(weivalue)<=0){
                        // 小于0给出 吐司提示
                        Toast.makeText(getContext(),"体重身高请大于0",Toast.LENGTH_SHORT).show();
                    }else {
                        // BMI逻辑：体重/（身高平方）。单位：千克 米
                        double v = Integer.valueOf(heivalue) / 100.0;
                        double v1 = Integer.valueOf(weivalue) / (v * v);
                        // BMI的建议
                        String tuijian = "体重正常";
                        if(sex == 0){
                            // 男
                            if(v1<20){
                                tuijian = "合理的生活和饮食习惯，加强营养，经常运动锻炼身体";
                            }
                            if(v1>24){
                                tuijian = "适当的运动 坚持循序渐进原则,以有氧运动为主,即低强度、长时间进行的运动,比如快走、慢跑、长距离慢速游泳、慢骑自行车等";
                            }
                        }else {
                            if(v1<18){
                                tuijian = "合理的生活和饮食习惯，加强营养，经常运动锻炼身体";
                            }
                            if(v1>23){
                                tuijian = "适当的运动 坚持循序渐进原则,以有氧运动为主,即低强度、长时间进行的运动,比如快走、慢跑、长距离慢速游泳、慢骑自行车等";
                            }
                        }
                        // 创建dialog
                        AlertDialog.Builder delDialog = new AlertDialog.Builder(getContext());
                        // 设置标题
                        delDialog.setTitle("BMI");
                        // 设置提示信息
                        delDialog.setMessage("您的BMI是："+ String.format("%.2f", v1) +";       "+tuijian);
                        // 确认按钮 点击关闭dialog
                        delDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        // 展示dialog
                        delDialog.show();
                    }
                }else {
                    // 吐司 提示输入数字
                    Toast.makeText(getContext(),"请输入数字",Toast.LENGTH_SHORT).show();
                }

            }
        });
        // 新增体重
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建dialog
                AlertDialog.Builder inputDialog = new AlertDialog.Builder(getContext());
                // 自定义dialog中间页面 初始化化
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_weight, null);
                // num 录入体重控件
                EditText editText = view1.findViewById(R.id.num);
                // 设置dialog标题   设置自定义页面
                inputDialog.setTitle("体重").setView(view1);
                // 关闭按钮  点击关闭dialog
                inputDialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                // 确定按钮
                inputDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 获取输入的体重
                                String num = editText.getText().toString();
                                // 判断是否为数字
                                if(TextUtils.isDigitsOnly(num)){
                                    // 判断体重是否大于0
                                    if(Integer.valueOf(num)<=0){
                                        // 提示体重大于0 吐司
                                        Toast.makeText(getContext(),"体重请大于0",Toast.LENGTH_SHORT).show();
                                    }else {
                                        // 新增一条数据保存数据库
                                        Weigt weigt = new Weigt();
                                        // 设置体重
                                        weigt.setWeight(Integer.valueOf(num));
                                        // 设置体重时间
                                        weigt.setTime(TimeUtils.getNowString());
                                        weigt.setName(SpConfig.getUsername());
                                        weigt.save();
                                        // 刷新列表
                                        setLineView();
                                    }
                                }else {
                                    // 提示请输入数字 吐司
                                    Toast.makeText(getContext(),"请输入数字",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();// show展示dialog
            }
        });

        eye.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(EyeActivity.class);
            }
        });
    }

}