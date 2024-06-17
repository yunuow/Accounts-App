package com.yunuo.accounts.fragment;

import com.yunuo.accounts.R;
import com.yunuo.accounts.database.DataBaseManager;
import com.yunuo.accounts.database.Type;

import java.util.List;

//Outcome 为 BaseFregment 的子类
public class OutCome extends BaseFragment {

    //重写
    @Override
    public void loadData() {
        super.loadData();
        //获取数据库中的数据源
        List<Type> outList = DataBaseManager.gettypelist(0);
        typeList.addAll(outList);
        adapter.notifyDataSetChanged();
        typeTv.setText("餐饮(主食)");
        typeIv.setImageResource(R.mipmap.ic_food1_red);
    }

    @Override
    public void saveAccountToDatabase() {
        accountSeed.setKind(0);
        DataBaseManager.insertToAtb(accountSeed);
    }
}
