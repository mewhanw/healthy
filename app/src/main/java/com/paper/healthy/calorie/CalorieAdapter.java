package com.paper.healthy.calorie;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paper.healthy.R;
import com.paper.healthy.bean.Calorie;

import java.util.List;

/**
 * 摄入食物列表用
 */
public class CalorieAdapter extends RecyclerView.Adapter<CalorieAdapter.ViewHolder> {

    // 数据源
    private List<Calorie> lists;
    // 设置数据源
    public void setLists(List<Calorie> lists) {
        this.lists = lists;
    }
    // 构造函数
    public CalorieAdapter(List<Calorie> lists) {
        this.lists = lists;
    }


    // 监听接口
    private   OnClickCal onClickCal;
    // 设置监听接口
    public void setOnClickCal(OnClickCal onClickCal) {
        this.onClickCal = onClickCal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 页面初始化
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calorie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // 设置食物
        holder.food.setText(lists.get(position).getFood());
        // 设置时间
        holder.time.setText(lists.get(position).getTime());
        // 设置摄入量
        holder.num.setText(String.valueOf(lists.get(position).getNum()));
        // 设置摄入卡路里
        holder.calorie.setText(String.valueOf(lists.get(position).getCalorie()));
        // 设置item点击事件  点击 与长按
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickCal!=null){
                    onClickCal.onClick(lists.get(position));
                }
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                if(onClickCal!=null){
                    onClickCal.onLongClick(lists.get(position));
                }
                return true;
            }
        });
    }
    // 数据个数
    @Override
    public int getItemCount() {
        return lists.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 食物控件
        public TextView food;
        // 时间控件
        public TextView time;
        // 摄入份数控件
        public TextView num;
        // 摄入卡路里控件
        public TextView calorie;
        // item 一条 总控件
        public View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // item 一条 总控件
            view = itemView;
            // 食物控件
            food = itemView.findViewById(R.id.food);
            // 时间控件
            time = itemView.findViewById(R.id.time);
            // 摄入份数控件
            num = itemView.findViewById(R.id.num);
            // 摄入卡路里控件
            calorie = itemView.findViewById(R.id.calorie);
        }
    }


    // 点击长按接口
    interface OnClickCal{
        public void onClick(Calorie item);
        public void onLongClick(Calorie item);
    }

}
