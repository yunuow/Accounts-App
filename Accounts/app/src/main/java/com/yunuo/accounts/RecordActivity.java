package com.yunuo.accounts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.yunuo.accounts.adapter.RecordAdapter;
import com.yunuo.accounts.fragment.InCome;
import com.yunuo.accounts.fragment.OutCome;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        
        //1.查找控件
        tabLayout = findViewById(R.id.record_tabs);
        viewPager = findViewById(R.id.record_vp);
        
        //2.设置ViewPaper加载页面
        initPager();
    }

    private void initPager() {
        //初始化ViewPaper页面的集合
        List<Fragment> fragmentList = new ArrayList<>();
        //创建收入和支出页面，放置在Fragment中
        OutCome outFrag = new OutCome(); //支出
        InCome inFrag = new InCome(); //收入
        fragmentList.add(outFrag);
        fragmentList.add(inFrag);
        
        //创建适配器
        RecordAdapter paperAdapter = new RecordAdapter(getSupportFragmentManager(),fragmentList);
        
        //设置适配器对象
        viewPager.setAdapter(paperAdapter);
        
        //将TabLayout和ViewPaper关联
        tabLayout.setupWithViewPager(viewPager);
    }

    /*点击事件*/
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_iv_back:
                finish();
                break;
        }
    }
}