package com.paper.healthy.eye;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paper.healthy.R;
import com.paper.healthy.bean.Eye;

import java.util.List;

/**
 * 摄入食物列表用
 */
public class EyeAdapter extends RecyclerView.Adapter<EyeAdapter.ViewHolder> {

    // 数据源
    private List<Eye> lists;
    // 设置数据源
    public void setLists(List<Eye> lists) {
        this.lists = lists;
    }
    // 构造函数
    public EyeAdapter(List<Eye> lists) {
        this.lists = lists;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 页面初始化
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eye, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // 日期
        holder.time.setText(lists.get(position).getTime());
        // 摄入
        holder.sport.setText(String.valueOf(lists.get(position).getSport()));
        // 消耗
        holder.calorie.setText(String.valueOf(lists.get(position).getCalorie()));
        // 差值
        holder.dif.setText(String.valueOf(lists.get(position).getDif()));

    }
    // 数据个数
    @Override
    public int getItemCount() {
        return lists.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 摄入
        public TextView sport;
        // 日期
        public TextView time;
        // 消耗
        public TextView calorie;
        // 差值
        public TextView dif;
        // item 一条 总控件
        public View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // item 一条 总控件
            view = itemView;
            // 摄入
            sport = itemView.findViewById(R.id.sport);
            // 日期
            time = itemView.findViewById(R.id.time);
            // 消耗
            calorie = itemView.findViewById(R.id.calorie);
            // 差值
            dif = itemView.findViewById(R.id.dif);
        }
    }


    // 点击长按接口


}
