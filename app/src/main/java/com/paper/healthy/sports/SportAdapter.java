package com.paper.healthy.sports;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paper.healthy.R;
import com.paper.healthy.bean.Calorie;
import com.paper.healthy.bean.Sport;

import java.util.List;

/**
 * 运动列表页面
 */
public class SportAdapter extends RecyclerView.Adapter<SportAdapter.ViewHolder> {


    // 数据源
    private List<Sport> lists;
    // 外部设置数据源方法
    public void setLists(List<Sport> lists) {
        this.lists = lists;
    }

    // 构造函数
    public SportAdapter(List<Sport> lists) {
        this.lists = lists;
    }

    // 点击事件接口
    private   OnClickCal onClickCal;

    // 设置监听事件
    public void setOnClickCal(OnClickCal onClickCal) {
        this.onClickCal = onClickCal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // 运动类型
        holder.sport.setText(lists.get(position).getSport());
        // 运动时间
        holder.stime.setText(lists.get(position).getStime());
        // 运动时长
        holder.time.setText(String.valueOf(lists.get(position).getTime()));
        // 消耗卡路里
        holder.calorie.setText(String.valueOf(lists.get(position).getCalorie()));
        // 设置监听事件  点击与 长按
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

    /**
     * 数据个数
     * @return
     */
    @Override
    public int getItemCount() {
        return lists.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 运动类型控件
        public TextView sport;
        // 运动时长控件
        public TextView time;
        // 运动开始时间控件
        public TextView stime;
        // 消耗卡路里控件
        public TextView calorie;
        // 单条主页面
        public View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 单条主页面
            view = itemView;
            // 运动类型控件
            sport = itemView.findViewById(R.id.sport);
            // 运动开始时间控件
            stime = itemView.findViewById(R.id.stime);
            // 运动时长控件
            time = itemView.findViewById(R.id.time);
            // 消耗卡路里控件
            calorie = itemView.findViewById(R.id.calorie);
        }
    }


    /**
     * 点击事件 接口
     */
    interface OnClickCal{
        // 点击
        public void onClick(Sport item);
        // 长按
        public void onLongClick(Sport item);
    }

}
