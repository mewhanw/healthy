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

public class CalorieAdapter extends RecyclerView.Adapter<CalorieAdapter.ViewHolder> {


    private List<Calorie> lists;

    public void setLists(List<Calorie> lists) {
        this.lists = lists;
    }

    public CalorieAdapter(List<Calorie> lists) {
        this.lists = lists;
    }



    private   OnClickCal onClickCal;

    public void setOnClickCal(OnClickCal onClickCal) {
        this.onClickCal = onClickCal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calorie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.food.setText(lists.get(position).getFood());
        holder.time.setText(lists.get(position).getTime());
        holder.num.setText(String.valueOf(lists.get(position).getNum()));
        holder.calorie.setText(String.valueOf(lists.get(position).getCalorie()));
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

    @Override
    public int getItemCount() {
        return lists.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView food;
        public TextView time;
        public TextView num;
        public TextView calorie;
        public View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            food = itemView.findViewById(R.id.food);
            time = itemView.findViewById(R.id.time);
            num = itemView.findViewById(R.id.num);
            calorie = itemView.findViewById(R.id.calorie);
        }
    }


    interface OnClickCal{
        public void onClick(Calorie item);
        public void onLongClick(Calorie item);
    }

}
