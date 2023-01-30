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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.paper.healthy.R;
import com.paper.healthy.bean.Calorie;

public class CalorieFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_calorie, container, false);
        setclick(contextView);
        return contextView;
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
                AlertDialog.Builder inputDialog = new AlertDialog.Builder(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_calorie, null);
                ((TextView)view1.findViewById(R.id.a)).setText("鸡腿");
                EditText editText = view1.findViewById(R.id.num);
                TextView calorie = view1.findViewById(R.id.calorie);
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
                                int cal = Integer.valueOf(num) * 216;
                                calorie.setText(String.valueOf(cal));
                            }
                        }
                    }
                });
                inputDialog.setTitle("录入饮食").setView(view1);
                inputDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String num = editText.getText().toString();
                                if(TextUtils.isDigitsOnly(num)){
                                    Calorie calorie1 = new Calorie();
                                    calorie1.setFood("鸡腿");
                                    calorie1.setTime(TimeUtils.getNowString());
                                    calorie1.setNum(Integer.valueOf(num));
                                    calorie1.setCalorie(Integer.valueOf(num) * 216);
                                }
                            }
                        }).show();
            }
        });
    }
}