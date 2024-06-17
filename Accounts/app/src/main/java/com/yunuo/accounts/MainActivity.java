package com.yunuo.accounts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yunuo.accounts.adapter.AccountAdapter;
import com.yunuo.accounts.database.AccountSeed;
import com.yunuo.accounts.database.DataBaseManager;
import com.yunuo.accounts.dialog.BudgetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ListView todayLv; //展示今日收支情况的ListView
    ImageView searchIv;
    Button editBtn;
    List<AccountSeed> myDatas; //声明数据源
    AccountAdapter adapter;
    int year,month,day;

    //头布局相关的控件
    View headerView;
    TextView topOutTv,topInTv,topBudgetTv,topConTv,topReminderTv;

    //设置预算金额，采用共享数据的方式
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTime();
        initView();
        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE);

        //添加ListView的头布局
        addHeaderView();

        myDatas = new ArrayList<>();
        //设置适配器，加载每一条数据
        adapter = new AccountAdapter(this, myDatas);
        todayLv.setAdapter(adapter);
    }

    //初始化自带的View方法
    private void initView() {
        todayLv = findViewById(R.id.main_lv);
        editBtn = findViewById(R.id.main_btn_edit);
        searchIv = findViewById(R.id.main_search);
        editBtn.setOnClickListener(this);
        searchIv.setOnClickListener(this);
        setLongClickListener();
    }

    //设置ListView的长按事件
    private void setLongClickListener() {
        todayLv.setOnItemLongClickListener((parent, view, position, id) -> {
            if(position == 0) {
                //点击了头布局
                return false;
            }
            int pos = position - 1;
            AccountSeed clickBean = myDatas.get(pos); //获取准备删除的信息

            //弹出提示用户是否确认删除的对话框
            showDeleteReminder(clickBean);
            return false;
        });
    }

    //弹出是否删除某一条记录的提示
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
                        setTopShow();   //改变头布局中显示的内容
                    }
                }).create().show();
    }

    //在ListView上添加头布局
    private void addHeaderView() {
        //将布局转换成View对象
        headerView = getLayoutInflater().inflate(R.layout.item_main_in_lv_top, null);
        todayLv.addHeaderView(headerView); //加入头布局

        //查找头布局所用到的控件
        topOutTv = headerView.findViewById(R.id.item_main_top_out);
        topInTv = headerView.findViewById(R.id.item_main_top_in);
        topBudgetTv = headerView.findViewById(R.id.item_main_top_budget);
        topConTv = headerView.findViewById(R.id.item_main_top_day);
        topReminderTv = headerView.findViewById(R.id.item_main_top_reminder);

        topBudgetTv.setOnClickListener(this);
        headerView.setOnClickListener(this);
    }

    //获取今日的具体时间
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get((Calendar.DAY_OF_MONTH));
    }

    //当返回到MainActivity会经历Onresume（）进程。这时候实时更新主页面内容
    @Override
    protected void onResume() {
        super.onResume();
        loadDataBase();
        setTopShow();
    }

    //设置头布局当中文本内容的显示
    private void setTopShow() {
        //获取今日支出和收入总金额，显示在view当中(主页面ListView上方，TopView下方）
        float incomeOneDay = DataBaseManager.getTodayAllMoney(year, month, day, 1);
        float outcomeOneDay = DataBaseManager.getTodayAllMoney(year, month, day, 0);
        String infoOneDay = "今日 "+month+"月"+day+"日 支出 ￥"+outcomeOneDay+"  收入 ￥"+incomeOneDay;
        topConTv.setText(infoOneDay);

        //获取本月收入和支出总金额
        float incomeOneMonth = DataBaseManager.getMonthAllMoney(year, month, 1);
        float outcomeOneMonth = DataBaseManager.getMonthAllMoney(year, month, 0);
        topInTv.setText("￥"+incomeOneMonth);
        topOutTv.setText("￥"+outcomeOneMonth);

        //设置显示运算剩余
        float budget = preferences.getFloat("budget", 0);//预算
        if (budget == 0) {
            topBudgetTv.setText("￥ 0");
        }else{
            float rest = budget-outcomeOneMonth;
            topBudgetTv.setText("￥"+rest);
            if (rest < 0) {
                topReminderTv.setText("是不是还没月末就又把钱全花完了？");
            } else if (rest < (budget / 3)) {
                topReminderTv.setText("快要吃土了，请精打细算哦！");
            } else if (rest < ((2 * budget) / 3)) {
                topReminderTv.setText("花了不少钱了哦！");
            } else {
                topReminderTv.setText("勤俭是一种美德，总之就是不要乱花钱知道了吗？");
            }
        }

    }

    private void loadDataBase() {
        List<AccountSeed> list = DataBaseManager.getTodayAccount(year, month);
        myDatas.clear();
        myDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_search:
                Intent intent2 = new Intent(this,SearchActivity.class); //跳转到编辑界面
                startActivity(intent2);
                break;
            case R.id.main_btn_edit:
                Intent intent1 = new Intent(this,RecordActivity.class); //跳转到编辑界面
                startActivity(intent1);
                break;
            case R.id.item_main_top_budget:
                showBudget();
                break;
        }
    }

    //显示预算设置的对话框
    private void showBudget() {
        BudgetDialog dialog = new BudgetDialog(this);
        dialog.show();
        dialog.setSize();

        dialog.setOnEnsureListener(money -> {

            //将预算金额写入到共享参数中进行存储
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat("budget",money);
            editor.commit();

            //计算剩余金额
            float outcome_month = DataBaseManager.getMonthAllMoney(year, month, 0);

            float rest = money - outcome_month; //剩余金额的计算
            topBudgetTv.setText("￥ " + rest);
            if (rest < 0) {
                topReminderTv.setText("是不是还没月末就又把钱全花完了？");
            } else if (rest < (money / 3)) {
                topReminderTv.setText("快要吃土了，请精打细算哦！");
            } else if (rest < ((2 * money) / 3)) {
                topReminderTv.setText("还不错，继续保持！");
            } else {
                topReminderTv.setText("勤俭是一种美德，总之就是不要乱花钱知道了吗？");
            }
        });
    }
}