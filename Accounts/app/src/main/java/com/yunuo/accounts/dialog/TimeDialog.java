package com.yunuo.accounts.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.yunuo.accounts.R;

//弹出时间对话框
public class TimeDialog extends Dialog implements View.OnClickListener{
    EditText hourEt,minuteEt;
    DatePicker datePicker;
    Button ensureBtn,cancelBtn;

    public interface OnEnsureListener{
        public void onEnsure(String time,int year,int month,int day);
    }

    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public TimeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);
        hourEt = findViewById(R.id.dialog_time_et_hour);
        minuteEt = findViewById(R.id.dialog_time_et_minute);
        datePicker = findViewById(R.id.dialog_time_dp);
        ensureBtn = findViewById(R.id.dialog_time_btn_ensure);
        cancelBtn = findViewById(R.id.dialog_time_btn_cancel);
        ensureBtn.setOnClickListener(this);  //添加点击监听事件
        cancelBtn.setOnClickListener(this);
        hideHeader();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_time_btn_cancel:
                cancel();
                break;
            case R.id.dialog_time_btn_ensure:
                int year = datePicker.getYear(); //选择年份
                int month = datePicker.getMonth() + 1;
                int day = datePicker.getDayOfMonth();
                String monthStr = String.valueOf(month);
                if(month < 10) {
                    monthStr = "0" + month;
                }
                String dayStr = String.valueOf(day);
                if(day < 10) {
                    dayStr = "0" + day;
                }

                //获取输入的小时和分钟
                String hstr = hourEt.getText().toString();
                String mstr = minuteEt.getText().toString();
                int hour = 0;
                if(!TextUtils.isEmpty(hstr)) {
                    hour = Integer.parseInt(hstr);
                    hour %= 24;
                }
                int minute = 0;
                if(!TextUtils.isEmpty(mstr)) {
                    minute = Integer.parseInt(mstr);
                    minute %= 60;
                }

                hstr=String.valueOf(hour);
                mstr=String.valueOf(minute);
                if (hour<10){
                    hstr="0"+hour;
                }
                if (minute<10){
                    mstr="0"+minute;
                }
                String tFormat = year+"年"+monthStr+"月"+dayStr+"日 "+hstr+":"+mstr;
                if (onEnsureListener!=null) {
                    onEnsureListener.onEnsure(tFormat,year,month,day);
                }
                cancel();
                break;
        }
    }

    //隐藏DataPicker的头布局
    private void hideHeader() {
        ViewGroup rootView = (ViewGroup) datePicker.getChildAt(0);
        if(rootView == null){
            return;
        }
        View headerView = rootView.getChildAt(0);
        if(headerView == null) {
            return;
        }
        //5.0+
//        int headerId = getContext().getResources().getIdentifier("day_picker_selector_layout","id","android");
//        if(headerId == headerView.getId()) {
//            headerView.setVisibility(View.GONE);
//            ViewGroup.LayoutParams layoutParamsRoot = rootView.getLayoutParams();
//            layoutParamsRoot.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//            rootView.setLayoutParams(layoutParamsRoot);
//
//            ViewGroup animator = (ViewGroup) rootView.getChildAt(1);
//            ViewGroup.LayoutParams layoutParamsAnimator = animator.getLayoutParams();
//            layoutParamsAnimator.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//            animator.setLayoutParams(layoutParamsAnimator);
//
//            View child = animator.getChildAt(0);
//            ViewGroup.LayoutParams layoutParamsChild = child.getLayoutParams();
//            layoutParamsChild.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//            child.setLayoutParams(layoutParamsChild);
//
//            return;
//        }

        //6.0+
        int headerId = getContext().getResources().getIdentifier("date_picker_header","id","android");
        if (headerId == headerView.getId()) {
            headerView.setVisibility(View.GONE);
        }
    }
}
