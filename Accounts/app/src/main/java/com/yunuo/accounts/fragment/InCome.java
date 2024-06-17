package com.yunuo.accounts.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.yunuo.accounts.R;
import com.yunuo.accounts.database.AccountSeed;
import com.yunuo.accounts.database.DataBaseManager;
import com.yunuo.accounts.database.Type;

import java.util.List;

//Income 为 BaseFregment 的子类
public class InCome extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountSeed = new AccountSeed(); //创建对象
        accountSeed.setTypename("薪资");
        accountSeed.setSimageid(R.mipmap.in_income_blue);
    }

    @Override
    public void loadData() {
        super.loadData();
        //获取数据库中的数据源
        List<Type> inList = DataBaseManager.gettypelist(1);
        typeList.addAll(inList);
        adapter.notifyDataSetChanged();
        typeTv.setText("薪资");
        typeIv.setImageResource(R.mipmap.in_income_blue);
    }

    @Override
    public void saveAccountToDatabase() {
        accountSeed.setKind(1);
        DataBaseManager.insertToAtb(accountSeed);
    }
}