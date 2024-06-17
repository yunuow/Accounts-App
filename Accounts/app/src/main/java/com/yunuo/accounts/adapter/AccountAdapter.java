package com.yunuo.accounts.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunuo.accounts.R;
import com.yunuo.accounts.database.AccountSeed;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//账户适配器，即用于mainActivity中ListView所包含的item的查找
public class AccountAdapter extends BaseAdapter implements Filterable {
    Context context;
    List<AccountSeed> myDatas;
    LayoutInflater inflater;
    int year,month,day;

    private MyFilter mFilter;

    public AccountAdapter(Context context, List<AccountSeed> myDatas) {
        this.context = context;
        this.myDatas = myDatas;
        inflater = LayoutInflater.from(context);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get((Calendar.DAY_OF_MONTH));
    }

    @Override
    public Filter getFilter() {
        if (mFilter ==null){
            mFilter = new MyFilter();
        }
        return null;
    }

    @Override
    public int getCount() {
        return myDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return myDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_main_in_lv,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        AccountSeed seed = myDatas.get(position);
        holder.typeIv.setImageResource(seed.getSimageid());
        holder.typeTv.setText(seed.getTypename());
        holder.remarkTv.setText(seed.getRemark());
        if (seed.getKind()==1) {
            holder.moneyTv.setText("+ ￥ "+seed.getMoney());
        } else {
            holder.moneyTv.setText("- ￥ "+seed.getMoney());
        }
        holder.timeTv.setText(seed.getTime());
        return convertView;
    }

    public class MyFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    }

    class ViewHolder {
        ImageView typeIv;
        TextView typeTv,remarkTv,moneyTv,timeTv;
        public ViewHolder(View v) {
            typeIv = v.findViewById(R.id.item_mainlv_iv);
            typeTv = v.findViewById(R.id.item_mainlv_tv_title);
            timeTv = v.findViewById(R.id.item_mainlv_tv_time);
            remarkTv = v.findViewById(R.id.item_mainlv_tv_remark);
            moneyTv = v.findViewById(R.id.item_mainlv_tv_money);
        }
    }


}
