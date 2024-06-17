package com.yunuo.accounts.database;

import android.content.ContentValues;
import android.content.Context;
//负责管理数据库的类，主要对于表中的内容进行操作，增删改查

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {

    private static SQLiteDatabase db;

    //初始化数据库
    public static void initDataBase(Context context) {
        //得到openHelper类对象
        DataBaseOpenHelper helper = new DataBaseOpenHelper(context);
        //得到可写的数据库对象
        db = helper.getWritableDatabase();
     }

     //读取数据库中的数据，写入内存集合
     //kind表示收入或支出的种类，支出0，收入1
    public static List<Type> gettypelist(int kind) {
        List<Type> list = new ArrayList<>();
        //读取typetable表中的数据
        String sql = "select * from typetb where kind = "+kind;
        Cursor cursor = db.rawQuery(sql,null);      //cursor可以理解为指针
        //循环读取图标内容，存储到对象中，即recordFragment的界面上
        while (cursor.moveToNext()) {
            String typename = cursor.getString(cursor.getColumnIndex("typeName"));
            int imageid = cursor.getInt(cursor.getColumnIndex("imageid"));
            int simageid = cursor.getInt(cursor.getColumnIndex("simageid"));
            int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            Type type = new Type(id,typename,imageid,simageid,kind1);
            list.add(type);
        }
        cursor.close();
        return list;
    }

    //向记账表中插入一条信息
    public static void insertToAtb(AccountSeed bean) {
        ContentValues values = new ContentValues();
        values.put("typeName",bean.getTypename());
        values.put("simageid",bean.getSimageid());
        values.put("remark",bean.getRemark());
        values.put("money",bean.getMoney());
        values.put("time",bean.getTime()); //5
        values.put("year",bean.getYear());
        values.put("month",bean.getMonth());
        values.put("day",bean.getDay());
        values.put("kind",bean.getKind());
        db.insert("accounttb",null,values);
        //Log.i("animee","Insert ok!!");     //查看数据是否插入成功
    }

    //获取记账表中每一天的所有收支情况
    public static List<AccountSeed> getTodayAccount(int year, int month) {
        List<AccountSeed> list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? order by day desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + ""});

        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typeName"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int simageid = cursor.getInt(cursor.getColumnIndex("simageid"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountSeed accountSeed = new AccountSeed(id, typename, simageid, remark, money, time, year, month, day, kind);
            list.add(accountSeed);
        }
        cursor.close();
        return list;
        //return null;
    }

    //获取今天的支出或者收入总和 以及分类kind 支出（0），收入（1）
    public static float getTodayAllMoney(int year, int month, int day, int kind) {
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and day=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        cursor.close();
        return total;
    }

    //获取本月的支出或者收入总和 kind 支出（0），收入（1）
    public static float getMonthAllMoney(int year, int month, int kind) {
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        cursor.close();
        return total;
    }

    //根据传入的id，删除accounttable表中的一条数据
    public static int deleteById(int id) {
        int i = db.delete("accounttb", "id=?", new String[]{id + ""});
        return i;
    }

    //根据备注搜索收入或者支出的情况列表
    public static List<AccountSeed> getListByRemark(String remark) {
        List<AccountSeed>list = new ArrayList<>();
        String sql = "select * from accounttb where remark like '%"+remark+"%'";
        Cursor cursor = db.rawQuery(sql, null);
        //循环遍历
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typeName"));
            String bz = cursor.getString(cursor.getColumnIndex("remark"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int simageid = cursor.getInt(cursor.getColumnIndex("simageid"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountSeed accountSeed = new AccountSeed(id, typename, simageid, bz, money, time, year, month, day, kind);
            list.add(accountSeed);
        }
        cursor.close();
        return list;
    }
}
