package com.yunuo.accounts.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunuo.accounts.R;
import com.yunuo.accounts.adapter.AccountAdapter;

import java.util.List;

public class Adapter extends BaseAdapter implements Filterable {
    Context context;
    List<Type> myData;

    private AccountAdapter.MyFilter mFilter;

    public int select = 0; //被点击的位置

    //构造函数
    public Adapter(Context context, List<Type> myData) {
        this.context = context;
        this.myData = myData;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    //get方法
    @Override
    public int getCount() {
        return myData.size();
    }

    @Override
    public Object getItem(int position) {
        return myData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv,parent,false);

        //查找布局中的控件
        ImageView iv = convertView.findViewById(R.id.item_recordfrag_iv);
        TextView tv = convertView.findViewById(R.id.item_recordfrag_tv);

        //获取指定位置数据源
        Type type = myData.get(position);
        tv.setText(type.getTypeName());

        //判断当前位置是否为选中位置。如果是，则显示有颜色图片，否则显示灰色
        if(select == position) {
            iv.setImageResource((type.getSimageid()));
        }else{
            iv.setImageResource(type.getImageid());
        }
        return convertView;
    }

    //此适配器不考虑复用问题，因为所有item都显示在界面上，无滑动，没有多余的convertView，所有不用复写
}
