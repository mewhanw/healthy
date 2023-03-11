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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.TimeUtils;
import com.paper.healthy.R;
import com.paper.healthy.bean.Calorie;
import com.paper.healthy.config.SpConfig;

import org.litepal.LitePal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 摄入食物
 * 卡路里页面
 */
public class CalorieFragment extends Fragment {

    /**
     * 列表控件
     */
    RecyclerView recyc;

    /**
     * 卡路里摄入总数
     */
    TextView food;

    // 食物对应卡路里
    private Map<String, Integer> cals = new HashMap();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_calorie, container, false);
        // 初始化数据
        initData();
        // 初始化控件
        initView(contextView);
        // 初始化点击事件
        setclick(contextView);
        return contextView;
    }

    /**
     * 初始化一些数据
     */
    private void initData() {
        // 食物份数对应 摄入卡路里 单位100g
        cals.put("鸡腿", 216);
        cals.put("饮料", 135);
        cals.put("鱼", 117);
        cals.put("青菜", 32);
        cals.put("水果", 125);
        cals.put("虾", 79);
        cals.put("红肉", 125);
        cals.put("米饭", 345);
    }

    /**
     * 初始化页面
     *
     * @param contextView
     */
    private void initView(View contextView) {
        // 列表
        recyc = contextView.findViewById(R.id.recyc);
        // 获取所有摄入食物
        List<Calorie> all = LitePal.where("name = '"+ SpConfig.getUsername()+"'").order("time desc").find(Calorie.class);
        CalorieAdapter adapter = new CalorieAdapter(all);
        recyc.setAdapter(adapter);
        // 食物
        food = contextView.findViewById(R.id.food);
        // 查询当天摄入食物总和
        Integer calorie = LitePal.where("name = '"+ SpConfig.getUsername()+"' and time like '" + TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd")) + "%'").sum(Calorie.class, "calorie", Integer.class);
        if (calorie != null) {
            food.setText(String.valueOf(calorie));
        }
    }

    /**
     * 设置点击事件
     *
     * @param contextView
     */
    private void setclick(View contextView) {
        /**
         * 鸡肉
         */
        contextView.findViewById(R.id.chicken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("鸡腿", 1, null);
            }
        });

        /**
         * 饮料
         */
        contextView.findViewById(R.id.drink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("饮料", 1, null);
            }
        });

        /**
         * 鱼
         */
        contextView.findViewById(R.id.fish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("鱼", 1, null);
            }
        });

        /**
         * 青菜
         */
        contextView.findViewById(R.id.lettuce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("青菜", 1, null);
            }
        });

        /**
         * 水果
         */
        contextView.findViewById(R.id.pear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("水果", 1, null);
            }
        });

        /**
         * 虾
         */
        contextView.findViewById(R.id.prawn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("虾", 1, null);
            }
        });

        /**
         * 红肉
         */
        contextView.findViewById(R.id.steak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("红肉", 1, null);
            }
        });

        /**
         * 米饭
         */
        contextView.findViewById(R.id.rich).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfood("米饭", 1, null);
            }
        });

        /**
         * 列表点击事件
         */
        ((CalorieAdapter) recyc.getAdapter()).setOnClickCal(new CalorieAdapter.OnClickCal() {
            @Override
            public void onClick(Calorie item) {
                // 修改
                editfood(item.getFood(), item.getNum(), item);
            }

            @Override
            public void onLongClick(Calorie item) {
                // 提示是否删除数据
                AlertDialog.Builder delDialog = new AlertDialog.Builder(getContext());
                // 设置标题
                delDialog.setTitle("删除");
                // 设置提示信息 删除数据
                delDialog.setMessage("确定删除本条数据吗？");
                // 取消按钮 用于关闭dialog
                delDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                // 删除按钮 点击删除数据库
                delDialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 删除数据库
                        item.delete();
                        // 刷新列表
                        dataList();
                    }
                });
                delDialog.show();// 展示dialog show
            }
        });


    }

    /**
     * 刷新list数据
     * 总和刷新
     */
    private void dataList() {
        // 倒序获取摄入食物数据
        List<Calorie> all = LitePal.where("name = '"+ SpConfig.getUsername()+"'").order("time desc").find(Calorie.class);
        // 设置数据源
        ((CalorieAdapter) recyc.getAdapter()).setLists(all);
        // 刷新列表
        ((CalorieAdapter) recyc.getAdapter()).notifyDataSetChanged();
        // 查询当天摄入食物总和
        Integer calorie = LitePal.where("name = '"+ SpConfig.getUsername()+"' and time like '" + TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd")) + "%'").sum(Calorie.class, "calorie", Integer.class);
        if (calorie != null) {
            // 卡路里总和
            food.setText(String.valueOf(calorie));
        }
    }

    /**
     * 新增修改食物
     * food 食物
     * fen  份数
     */
    private void editfood(String food, int fen, Calorie caloriebean) {
        int cal = cals.get(food);
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(getContext());
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_calorie, null);
        ((TextView) view1.findViewById(R.id.a)).setText(food);
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
                if (!TextUtils.isEmpty(editText.getText())) {
                    String num = editText.getText().toString();
                    if (TextUtils.isDigitsOnly(num)) {
                        int call = Integer.valueOf(num) * cal;
                        calorie.setText(String.valueOf(call));
                    }
                }
            }
        });
        String luru = "录入";
        if (caloriebean != null) {
            luru = "修改";
        }
        // 设置标题 设置自定义中间页面
        inputDialog.setTitle(luru + "饮食").setView(view1);
        // 关闭按钮 关闭dialog
        inputDialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        // 确认按钮 保存填写数据
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 输入份数数据
                        String num = editText.getText().toString();
                        // 判断是否数字
                        if (TextUtils.isDigitsOnly(num)) {
                            if (Integer.valueOf(num) <= 0) {
                                // 吐司 提示输入份数大于0
                                Toast.makeText(getContext(), "摄入份数请大于0", Toast.LENGTH_SHORT).show();
                            } else {
                                if (caloriebean == null) {
                                    // 新增一条数据保存数据库
                                    Calorie calorie1 = new Calorie();
                                    // 食物类型
                                    calorie1.setFood(food);
                                    // 摄入时间
                                    calorie1.setTime(TimeUtils.getNowString());
                                    // 摄入份数
                                    calorie1.setNum(Integer.valueOf(num));
                                    // 摄入卡路里
                                    calorie1.setCalorie(Integer.valueOf(num) * cal);
                                    calorie1.setName(SpConfig.getUsername());
                                    calorie1.save();
                                } else {
                                    // 更新数据
                                    // 个数 与 卡路里
                                    caloriebean.setNum(Integer.valueOf(num));
                                    caloriebean.setCalorie(Integer.valueOf(num) * cal);
                                    // 保存数据
                                    caloriebean.setName(SpConfig.getUsername());
                                    caloriebean.save();
                                }

                                // 刷新列表
                                dataList();
                            }
                        } else {
                            // 吐司 提示数据数字
                            Toast.makeText(getContext(), "请输入数字", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
        // show展示 dialog
    }
}