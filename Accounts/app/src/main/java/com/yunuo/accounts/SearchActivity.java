package com.yunuo.accounts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yunuo.accounts.adapter.AccountAdapter;
import com.yunuo.accounts.database.AccountSeed;
import com.yunuo.accounts.database.DataBaseManager;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ListView searchLv;
    EditText searchEt;
    TextView emptyTv;

    List<AccountSeed> myDatas; //数据源
    AccountAdapter adapter;    //适配器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        myDatas = new ArrayList<>();
        adapter = new AccountAdapter(this,myDatas);
        searchLv.setAdapter(adapter);
        searchLv.setEmptyView(emptyTv); //设置当ListView为空时，页面控件的显示（即默认显示）
    }

    private void initView() {
        searchEt = findViewById(R.id.search_et);
        searchLv = findViewById(R.id.search_lv);
        emptyTv = findViewById(R.id.search_tv_empty);
        setLongClickListener();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_iv_back:
                finish();
                break;
            case R.id.search_iv_sh:     //执行搜索操作
                String msg = searchEt.getText().toString().trim();

                //判断搜索内容是否为空，执行不同步骤
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(this,"输入内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                //搜索
                List<AccountSeed> list = DataBaseManager.getListByRemark(msg);
                myDatas.clear();
                myDatas.addAll(list);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    //设置ListView的长按事件
    private void setLongClickListener() {
        searchLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AccountSeed clickBean = myDatas.get(position); //获取准备删除的信息
                //弹出提示用户是否确认删除的对话框
                showDeleteReminder(clickBean);
                return false;
            }
        });
    }

    //弹出是否删除某一条记录的方法
    private void showDeleteReminder(final AccountSeed clickBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("确定删除此条记录？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //执行删除操作
                        DataBaseManager.deleteById(clickBean.getId());
                        myDatas.remove(clickBean); //实时刷新，移除集合中对象
                        adapter.notifyDataSetChanged();  //提示适配器更新数据
                    }
                }).create().show();
    }
}