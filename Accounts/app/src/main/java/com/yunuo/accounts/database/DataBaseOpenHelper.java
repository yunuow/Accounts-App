package com.yunuo.accounts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.yunuo.accounts.R;

public class DataBaseOpenHelper extends SQLiteOpenHelper {
    //构造函数
    public DataBaseOpenHelper(@Nullable Context context) {
        super(context, "accounts.db",null,1);
    }


    //此方法是项目第一次运行时用于构造数据库的方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表示类型的表
        String sql = "create table typetb(id integer primary key autoincrement,typeName varchar(10),imageid integer,simageid integer,kind integer)";
        db.execSQL(sql);
        insertInto(db);

        //创建记账表
        sql = "create table accounttb(id integer primary key autoincrement,typeName varchar(10),simageid integer,remark varchar(80),money float," +
                "time varchar(60),year integer,month integer,day integer,kind integer)";
        db.execSQL(sql);
    }

    //向typetable表中插入元素
    private void insertInto(SQLiteDatabase db) {
        String sql = "insert into typetb (typeName,imageid,simageid,kind) values (?,?,?,?)";
        //支出类
        db.execSQL(sql,new Object[]{"餐饮(主食)", R.mipmap.ic_food1,R.mipmap.ic_food1_red,0});
        db.execSQL(sql,new Object[]{"餐饮(零食)", R.mipmap.ic_food2,R.mipmap.ic_food2_red,0});
        db.execSQL(sql,new Object[]{"网购", R.mipmap.ic_shopping,R.mipmap.ic_shopping_red,0});
        db.execSQL(sql,new Object[]{"服装", R.mipmap.ic_clothes,R.mipmap.ic_clothes_red,0});
        db.execSQL(sql,new Object[]{"日用品", R.mipmap.ic_ryp,R.mipmap.ic_ryp_red,0}); //5
        db.execSQL(sql,new Object[]{"娱乐", R.mipmap.ic_play,R.mipmap.ic_play_red,0});
        db.execSQL(sql,new Object[]{"交通出行", R.mipmap.ic_vehicle,R.mipmap.ic_vehicle_red,0});
        db.execSQL(sql,new Object[]{"学习支出", R.mipmap.ic_study,R.mipmap.ic_study_red,0});
        db.execSQL(sql,new Object[]{"医疗支出", R.mipmap.ic_hospital,R.mipmap.ic_hospital_red,0});
        db.execSQL(sql,new Object[]{"水电支出", R.mipmap.ic_eletric,R.mipmap.ic_eletric_red,0}); //10
        db.execSQL(sql,new Object[]{"流量费用", R.mipmap.ic_wifi,R.mipmap.ic_wifi_fs,0});
        db.execSQL(sql,new Object[]{"借出款项", R.mipmap.ic_lend,R.mipmap.ic_lend_red,0});
        db.execSQL(sql,new Object[]{"其他", R.mipmap.ic_other,R.mipmap.ic_other_red,0}); //13

        //收入类
        db.execSQL(sql,new Object[]{"薪资", R.mipmap.in_income,R.mipmap.in_income_blue,1});
        db.execSQL(sql,new Object[]{"奖金", R.mipmap.in_aword,R.mipmap.in_aword_blue,1});
        db.execSQL(sql,new Object[]{"借入款项", R.mipmap.in_borrow,R.mipmap.in_borrow_blue,1});
        db.execSQL(sql,new Object[]{"投资收入", R.mipmap.in_fund,R.mipmap.in_fund_blue,1});
        db.execSQL(sql,new Object[]{"二手交易", R.mipmap.in_recycle,R.mipmap.in_recycle_blue,1}); //5
        db.execSQL(sql,new Object[]{"意外所得", R.mipmap.in_unkown,R.mipmap.in_unkown_blue,1});
        db.execSQL(sql,new Object[]{"其他", R.mipmap.in_other,R.mipmap.in_other_blue,1}); //7

    }

    //数据库版本更新时发生改变，调用此方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*for(int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 1:
                    break;
                case 2:
                    updateMode(db);
                default:
                    break;
            }
        }*/
    }
}
