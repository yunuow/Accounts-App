package com.yunuo.accounts;

import android.app.Application;

import com.yunuo.accounts.database.DataBaseManager;

//全局应用的类
public class aply extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库对象
        DataBaseManager.initDataBase(getApplicationContext());
    }
}
