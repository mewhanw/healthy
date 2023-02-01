package com.paper.healthy.calorie;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.paper.healthy.R;
import com.paper.healthy.bean.Calorie;

import org.litepal.LitePal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalorieFragment extends Fragment {

    /**
     * 列表控件
     */
    RecyclerView recyc;

    /**
     * 卡路里摄入总数
     */
    TextView food;

    private Map<String,Integer> cals = new HashMap();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_calorie, container, false);
        initData();
        initView(contextView);
        setclick(contextView);
        return contextView;
    }

    /**
     * 初始化一些数据
     */
    private void initData() {
        cals.put("鸡腿",216);
        cals.put("饮料",135);
        cals.put("鱼",117);
        cals.put("青菜",32);
        cals.put("水果",125);
        cals.put("虾",79);
        cals.put("红肉",125);
        cals.put("米饭",345);
    }

    /**
     * 初始化页面
     * @param contextView
     */
    private void initView(View contextView) {
        // 列表
        recyc = contextView.findViewById(R.id.recyc);
        // 获取所有摄入食物
        List<Calorie> all = LitePal.order("time desc").find(Calorie.class);
        CalorieAdapter adapter = new CalorieAdapter(all);
        recyc.setAdapter(adapter);
        // 食物
        food = contextView.findViewById(R.id.food);
        // 查询当天摄入食物总和
        Integer calorie = LitePal.where("time like '" + TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd")) +"%'").sum(Calorie.class, "calorie", Integer.class);
        if(calorie!=null){
            food.setText(String.valueOf(calorie));
        }
    }

    /**
     * 设置点击事件
     * @param contextView
     */
    private void setclick(View contextView) {
        /**
         * 鸡肉
         */
        contextView.findViewById(R.id.chicken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("鸡腿",1,null);
            }
        });

        /**
         * 饮料
         */
        contextView.findViewById(R.id.drink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("饮料",1,null);
            }
        });

        /**
         * 鱼
         */
        contextView.findViewById(R.id.fish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("鱼",1,null);
            }
        });

        /**
         * 青菜
         */
        contextView.findViewById(R.id.lettuce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("青菜",1,null);
            }
        });

        /**
         * 水果
         */
        contextView.findViewById(R.id.pear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("水果",1,null);
            }
        });

        /**
         * 虾
         */
        contextView.findViewById(R.id.prawn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("虾",1,null);
            }
        });

        /**
         * 红肉
         */
        contextView.findViewById(R.id.steak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("红肉",1,null);
            }
        });

        /**
         * 米饭
         */
        contextView.findViewById(R.id.rich).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("米饭",1,null);
            }
        });

        /**
         * 列表点击事件
         */
        ((CalorieAdapter) recyc.getAdapter()).setOnClickCal(new CalorieAdapter.OnClickCal() {
            @Override
            public void onClick(Calorie item) {
                // 修改
                editfood(item.getFood(),item.getNum(),item);
            }

            @Override
            public void onLongClick(Calorie item) {
                // 提示是否删除数据
                AlertDialog.Builder delDialog = new AlertDialog.Builder(getContext());
                delDialog.setTitle("删除");
                delDialog.setMessage("确定删除本条数据吗？");
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
                delDialog.show();

            }
        });


    }

    /**
     * 刷新list数据
     * 总和刷新
     */
    private void dataList() {
        List<Calorie> all = LitePal.findAll(Calorie.class);
        ((CalorieAdapter)recyc.getAdapter()).setLists(all);
        ((CalorieAdapter)recyc.getAdapter()).notifyDataSetChanged();
        // 查询当天摄入食物总和
        Integer calorie = LitePal.where("time like '" + TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd")) +"%'").sum(Calorie.class, "calorie", Integer.class);
        if(calorie!=null){
            food.setText(String.valueOf(calorie));
        }
    }

    /**
     * 新增修改食物
     * food 食物
     * fen  份数
     */
    private void editfood(String food,int fen,Calorie caloriebean) {
        int cal = cals.get(food);
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(getContext());
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_calorie, null);
        ((TextView)view1.findViewById(R.id.a)).setText(food);
        // num 份数控件   calorie是卡路里控件
        EditText editText = view1.findViewById(R.id.num);
        TextView calorie = view1.findViewById(R.id.calorie);
        // 默认一份
        editText.setText(String.valueOf(fen));
        calorie.setText(String.valueOf(cal));
        // 监听当份数改变的时候 卡路里自动变化
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editText.getText())){
                    String num = editText.getText().toString();
                    if(TextUtils.isDigitsOnly(num)){
                        int call = Integer.valueOf(num) * cal;
                        calorie.setText(String.valueOf(call));
                    }
                }
            }
        });
        String luru = "录入";
        if(caloriebean!=null){
            luru = "修改";
        }
        inputDialog.setTitle(luru+"饮食").setView(view1);
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
                                Toast.makeText(getContext(),"摄入份数请大于0",Toast.LENGTH_SHORT).show();
                            }else {
                                if(caloriebean==null){
                                    // 新增一条数据保存数据库
                                    Calorie calorie1 = new Calorie();
                                    calorie1.setFood(food);
                                    calorie1.setTime(TimeUtils.getNowString());
                                    calorie1.setNum(Integer.valueOf(num));
                                    calorie1.setCalorie(Integer.valueOf(num) * cal);
                                    calorie1.save();
                                }else {
                                    // 更新数据
                                    caloriebean.setNum(Integer.valueOf(num));
                                    caloriebean.setCalorie(Integer.valueOf(num) * cal);
                                    caloriebean.save();
                                }

                                // 刷新列表
                                dataList();
                            }
                        }else {
                            Toast.makeText(getContext(),"请输入数字",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
    }
}