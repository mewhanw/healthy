package com.paper.healthy.own;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.paper.healthy.R;
import com.paper.healthy.bean.Calorie;
import com.paper.healthy.bean.Weigt;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import im.dacer.androidcharts.LineView;

public class OwnFragment extends Fragment {

    // 表
   private LineView lineView;
   // 添加体重
   private ImageView add;
   // 计算bmi
   private Button submit;
   // 身高
   private EditText hei;
   // 体重
   private EditText wei;
   // 退出
   private Button exit;

   // 男女
   private RadioGroup group;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_own, container, false);

        initView(contextView);
        initListenter();
        return contextView;
    }


    /**
     * 添加控件
     * @param contextView
     */
    private void initView(View contextView){
        lineView = (LineView)contextView.findViewById(R.id.line_view);
        add = contextView.findViewById(R.id.addme);
        submit = contextView.findViewById(R.id.submit);
        exit = contextView.findViewById(R.id.exit);
        hei = contextView.findViewById(R.id.hei);
        wei = contextView.findViewById(R.id.wei);
        group = contextView.findViewById(R.id.group);
        setLineView();
    }

    /**
     * 设置表数据
     * @param
     */
    public void setLineView(){
        List<Weigt> all = LitePal.limit(10).find(Weigt.class);
        lineView.setDrawDotLine(false); //optional
        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
        ArrayList<String> bottom = new ArrayList<>();
        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        ArrayList<Integer> dataListsitem = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(all)){
            for (Weigt weigt : all) {
                bottom.add(weigt.getTime().substring(5,9));
                dataListsitem.add(weigt.getWeight());
            }
        }else {
            bottom.add("月-日");
            dataListsitem.add(0);
        }
        lineView.setBottomTextList(bottom);
        dataLists.add(dataListsitem);
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
                System.exit(0);
            }
        });
        // 计算bmi
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                String heivalue = hei.getText().toString();
                String weivalue = wei.getText().toString();
                // 0男 1女
                int sex = group.getCheckedRadioButtonId() == R.id.man? 0 : 1;
                if(TextUtils.isDigitsOnly(heivalue) && TextUtils.isDigitsOnly(weivalue)){
                    if(Integer.valueOf(heivalue)<=0 && Integer.valueOf(weivalue)<=0){
                        Toast.makeText(getContext(),"体重身高请大于0",Toast.LENGTH_SHORT).show();
                    }else {
                        double v = Integer.valueOf(heivalue) / 100.0;
                        double v1 = Integer.valueOf(weivalue) / (v * v);
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
                        AlertDialog.Builder delDialog = new AlertDialog.Builder(getContext());
                        delDialog.setTitle("BMI");
                        delDialog.setMessage("您的BMI是："+ String.format("%.2f", v1) +";       "+tuijian);
                        delDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        delDialog.show();
                    }
                }else {
                    Toast.makeText(getContext(),"请输入数字",Toast.LENGTH_SHORT).show();
                }

            }
        });
        // 新增体重
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder inputDialog = new AlertDialog.Builder(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_weight, null);
                // num 份数控件
                EditText editText = view1.findViewById(R.id.num);
                inputDialog.setTitle("体重").setView(view1);
                inputDialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                inputDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String num = editText.getText().toString();
                                if(TextUtils.isDigitsOnly(num)){
                                    if(Integer.valueOf(num)<=0){
                                        Toast.makeText(getContext(),"体重请大于0",Toast.LENGTH_SHORT).show();
                                    }else {
                                        // 新增一条数据保存数据库
                                        Weigt weigt = new Weigt();
                                        weigt.setWeight(Integer.valueOf(num));
                                        weigt.setTime(TimeUtils.getNowString());
                                        weigt.save();
                                        // 刷新列表
                                        setLineView();
                                    }
                                }else {
                                    Toast.makeText(getContext(),"请输入数字",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
            }
        });
    }

}