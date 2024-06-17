package com.yunuo.accounts.database;

//描述记录一条数据的相关内容类
public class AccountSeed {
    int id;
    String typeName; //类型
    int simageid; // 被选中类型图片
    String remark; //备注内容
    float money; //价格
    String time; //时间字符串
    int year;
    int month;
    int day;
    int kind; //表示数据的类型，值只有0和1，收入(1)or支出(0)

    public AccountSeed() {
    }

    //构造函数
    public AccountSeed(int id, String typename, int simageid, String remark, float money, String time, int year, int month, int day, int kind) {
        this.id = id;
        this.typeName = typename;
        this.simageid = simageid;
        this.remark = remark;
        this.money = money;
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
        this.kind = kind;
    }

    //get和set方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typeName;
    }

    public void setTypename(String typename) {
        this.typeName = typename;
    }

    public int getSimageid() {
        return simageid;
    }

    public void setSimageid(int simageid) {
        this.simageid = simageid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
