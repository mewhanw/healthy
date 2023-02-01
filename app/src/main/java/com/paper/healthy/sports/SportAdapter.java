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

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.ViewHolder> {


    private List<Sport> lists;

    public void setLists(List<Sport> lists) {
        this.lists = lists;
    }

    public SportAdapter(List<Sport> lists) {
        this.lists = lists;
    }



    private   OnClickCal onClickCal;

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
        holder.sport.setText(lists.get(position).getSport());
        holder.stime.setText(lists.get(position).getStime());
        holder.time.setText(String.valueOf(lists.get(position).getTime()));
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

        public TextView sport;
        public TextView time;
        public TextView stime;
        public TextView calorie;
        public View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            sport = itemView.findViewById(R.id.sport);
            stime = itemView.findViewById(R.id.stime);
            time = itemView.findViewById(R.id.time);
            calorie = itemView.findViewById(R.id.calorie);
        }
    }


    interface OnClickCal{
        public void onClick(Sport item);
        public void onLongClick(Sport item);
    }

}
