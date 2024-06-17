package com.yunuo.accounts.database;

//表示收入或支出具体类型的类

public class Type {
    int id;
    String typeName; //类型名称
    int imageid;  //未被选中的图片id
    int simageid; //被选中的图片id
    int kind;     //收入为1，支出为0

    public Type() {
    }

    public Type(int id, String typeName, int imageid, int simageid, int kind) {
        this.id = id;
        this.typeName = typeName;
        this.imageid = imageid;
        this.simageid = simageid;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public int getSimageid() {
        return simageid;
    }

    public void setSimageid(int simageid) {
        this.simageid = simageid;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
