package com.yunuo.accounts.fragment;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunuo.accounts.R;
import com.yunuo.accounts.database.AccountSeed;
import com.yunuo.accounts.database.Adapter;
import com.yunuo.accounts.database.Type;
import com.yunuo.accounts.dialog.RemarkDialog;
import com.yunuo.accounts.dialog.KeyBoardUtils;
import com.yunuo.accounts.dialog.TimeDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//记录页面当中的支出模

public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    KeyboardView keyboardView;
    final String TAG = "tag";

    EditText moneyEt;
    private LayoutInflater layoutInflater;
    ImageView typeIv;
    TextView typeTv,remarkTv,timeTv;
    GridView typeGv;
    private ViewGroup customView;
    private WindowManager wm;
    private DisplayMetrics metrics;

    List<Type> typeList;
    public Adapter adapter;
    AccountSeed accountSeed;  //将记账信息保存成对象形式

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountSeed = new AccountSeed(); //创建对象
        accountSeed.setTypename("餐饮(主食)");
        accountSeed.setSimageid(R.mipmap.ic_food1_red);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 给fragment填充layout
        View view = inflater.inflate(R.layout.fragment_outcome, container, false);
        initView(view);
        initTime();

        //给GridView填充数据的方法
        loadData();

        //设置GridView每一项的点击事件
        setListener();
        return view;
    }

    //获取当前时间,显示在timeTv上
    private void initTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time  = sdf.format(date);
        timeTv.setText(time);
        accountSeed.setTime(time);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //给备注添加默认日期内容，方便以后搜索
        remarkTv.setText("备注(默认):"+year+"年"+month+"月"+day+"日");
        accountSeed.setRemark(year+"."+month+"."+day);

        accountSeed.setYear(year);
        accountSeed.setMonth(month);
        accountSeed.setDay(day);
    }

    public void initPopUpView(){
        layoutInflater = null;
        customView = (ViewGroup)layoutInflater.inflate(R.layout.item_main_in_lv,null);

        metrics = new DisplayMetrics();
        metrics = getResources().getDisplayMetrics();
        //wm.getDefaultDisplay().getMetrics(metrics); //此方法已被版本弃用
    }

    //设置GridView每一项的点击事件
    private void setListener() {
        typeGv.setOnItemClickListener((parent, view, position, id) -> {
            adapter.select = position;
            adapter.notifyDataSetInvalidated(); //提示绘制发生变化

            //对于Grid上面的内容进行同步变化
            Type type = typeList.get(position);
            String typename = type.getTypeName();
            typeTv.setText(typename);
            accountSeed.setTypename(typename);

            int simageId = type.getSimageid();
            typeIv.setImageResource(simageId);

            accountSeed.setSimageid(simageId);
        });
    }

    //给GridView填充数据的方法
    public void loadData() {
        typeList = new ArrayList<>();
        adapter = new Adapter(getContext(), typeList);
        typeGv.setAdapter(adapter);
    }

    private void initView(View view) {
        keyboardView = view.findViewById(R.id.frag_record_keyboard);
        moneyEt = view.findViewById(R.id.frag_record_et_money);
        typeIv = view.findViewById(R.id.frag_record_iv);
        typeGv = view.findViewById(R.id.frag_record_gv);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        remarkTv = view.findViewById(R.id.frag_record_tv_remark);
        timeTv = view.findViewById(R.id.frag_record_tv_time);
        remarkTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);

        //让自定义的键盘显示
        KeyBoardUtils boardUtils = new KeyBoardUtils(keyboardView,moneyEt);
        boardUtils.showKeyboard();

        //设置接口，监听按钮
        //点击确定按钮
        boardUtils.setOnEnsureListener(() -> {
            //获取输入金额
            String moneyStr = moneyEt.getText().toString();
            if (TextUtils.isEmpty(moneyStr)||moneyStr.equals("0")) {
                getActivity().finish();
                return;
            }
            float money = Float.parseFloat(moneyStr);
            accountSeed.setMoney(money);
            //获取记录的信息，保存进数据库
            saveAccountToDatabase();
            //返回上级页面
            getActivity().finish();
        });
    }

    //让子类重写
    public abstract void saveAccountToDatabase();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_record_tv_time:
                showTime(); //弹出时间
                break;
            case R.id.frag_record_tv_remark:
                showRemark(); //弹出备注
                break;
        }
    }

    //显示时间对话框
    protected void showTime(){
        TimeDialog dialog = new TimeDialog(getContext());
        dialog.show();
        //设定确定按钮监听
        dialog.setOnEnsureListener((time, year, month, day) -> {
            timeTv.setText(time);
            accountSeed.setTime(time);
            accountSeed.setYear(year);
            accountSeed.setMonth(month);
            accountSeed.setDay(day);
        });
    }

    //弹出备注对话框
    public void showRemark() {
        RemarkDialog dialog = new RemarkDialog(getContext());
        dialog.show();

        //调整对话框大小
        dialog.setSize();

        dialog.setOnEnsureListener(() -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String msg = dialog.getEdit();
            if (!TextUtils.isEmpty(msg)) {
                remarkTv.setText(msg);
                String Msg = year+"."+month+"."+day+msg;
                accountSeed.setRemark(Msg);
            }
            dialog.cancel();
        });
    }
}