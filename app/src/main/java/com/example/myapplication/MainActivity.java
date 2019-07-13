package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

//有下边栏切换的Activity
public class MainActivity extends FragmentActivity implements OnClickListener {
    //声明Tab的布局文件
    private LinearLayout mTabSY;
    private LinearLayout mTabZX;
    private LinearLayout mTabWD;


    //声明Tab分别对应的Fragment
    private Fragment mFragSY;
    private Fragment mFragFrd;
    private Fragment mFragAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews();//初始化控件
        initEvents();//初始化事件

        selectTab(0);//默认选中第一个Tab
        ImageButton imageButton1 = (ImageButton)findViewById(R.id.id_tab_SY_img);
        imageButton1.setImageResource(R.mipmap.home_selected);
        TextView textView1 = (TextView)findViewById(R.id.id_tab_SY_text);
        textView1.setTextColor(getResources().getColor(R.color.main));

    }

    private void initEvents() {
        //初始化Tab的点击事件
        mTabSY.setOnClickListener(this);
        mTabZX.setOnClickListener(this);
        mTabWD.setOnClickListener(this);
    }

    private void initViews() {
        //初始化Tab的布局文件
        mTabSY = (LinearLayout) findViewById(R.id.id_tab_SY);
        mTabZX = (LinearLayout) findViewById(R.id.id_tab_frd);
        mTabWD = (LinearLayout) findViewById(R.id.id_tab_address);
        
    }

    //处理Tab的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab_SY:
                selectTab(0);//当点击的是首页的Tab就选中微信的Tab
                resetBottomImageAndText();
                ImageButton imageButton1 = (ImageButton)findViewById(R.id.id_tab_SY_img);
                imageButton1.setImageResource(R.mipmap.home_selected);
                TextView textView1 = (TextView)findViewById(R.id.id_tab_SY_text);
                textView1.setTextColor(getResources().getColor(R.color.main));
                break;
            case R.id.id_tab_frd:
                selectTab(1);
                resetBottomImageAndText();
                ImageButton imageButton2 = (ImageButton)findViewById(R.id.id_tab_frd_img);
                imageButton2.setImageResource(R.mipmap.fund_selected);
                TextView textView2 = (TextView)findViewById(R.id.id_tab_frd_text);
                textView2.setTextColor(getResources().getColor(R.color.main));
                break;
            case R.id.id_tab_address:
                selectTab(2);
                resetBottomImageAndText();
                ImageButton imageButton3 = (ImageButton)findViewById(R.id.id_tab_address_img);
                imageButton3.setImageResource(R.mipmap.mine_selected);
                TextView textView3 = (TextView)findViewById(R.id.id_tab_address_text);
                textView3.setTextColor(getResources().getColor(R.color.main));
                break;
        }

    }

    private void resetBottomImageAndText()
    {
        ImageButton imageButton1 = (ImageButton)findViewById(R.id.id_tab_SY_img);
        ImageButton imageButton2 = (ImageButton)findViewById(R.id.id_tab_frd_img);
        ImageButton imageButton3 = (ImageButton)findViewById(R.id.id_tab_address_img);
        imageButton1.setImageResource(R.mipmap.home_unselected);
        imageButton2.setImageResource(R.mipmap.fund_unselected);
        imageButton3.setImageResource(R.mipmap.mine_unselected);
        TextView textView1 = (TextView)findViewById(R.id.id_tab_SY_text);
        textView1.setTextColor(getResources().getColor(R.color.black));
        TextView textView2 = (TextView)findViewById(R.id.id_tab_frd_text);
        textView2.setTextColor(getResources().getColor(R.color.black));
        TextView textView3 = (TextView)findViewById(R.id.id_tab_address_text);
        textView3.setTextColor(getResources().getColor(R.color.black));
    }
    //进行选中Tab的处理
    private void selectTab(int i) {
        //获取FragmentManager对象
        FragmentManager manager = getSupportFragmentManager();
        //获取FragmentTransaction对象
        FragmentTransaction transaction = manager.beginTransaction();
        //先隐藏所有的Fragment
        hideFragments(transaction);
        switch (i) {
            //当选中点击的是首页的Tab时
            case 0:
                //如果对应的Fragment没有实例化，则进行实例化，并显示出来
                if (mFragSY == null) {
                    mFragSY = new SYFragment();
                    transaction.add(R.id.id_content, mFragSY);
                } else {
                    //如果对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mFragSY);
                }
                break;
            case 1:
                if (mFragFrd == null) {
                    mFragFrd = new ZXFragment();
                    transaction.add(R.id.id_content, mFragFrd);
                } else {
                    transaction.show(mFragFrd);
                }
                break;
            case 2:
                if (mFragAddress == null) {
                    mFragAddress = new WDFragment();
                    transaction.add(R.id.id_content, mFragAddress);
                } else {
                    transaction.show(mFragAddress);
                }
                break;
        }
        //不要忘记提交事务
        transaction.commit();
    }


    //将Fragment隐藏
    private void hideFragments(FragmentTransaction transaction) {
        if (mFragSY != null) {
            transaction.hide(mFragSY);
        }
        if (mFragFrd != null) {
            transaction.hide(mFragFrd);
        }
        if (mFragAddress != null) {
            transaction.hide(mFragAddress);
        }
    }


    //跳转到搜索页面
    public void jumpToSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
    //跳转到筛选界面
    public void jumpToFilter(View view) {
        Intent intent = new Intent(this, FilterActivity.class);
        startActivity(intent);
    }
    //跳转到回报
    public void jumpToReturn(View view) {
//        Intent intent = new Intent(this, ReturnActivity.class);
//        startActivity(intent);
    }
    //跳转到净值
    public void jumpToNet(View view) {
        Intent intent = new Intent(this, NetActivity.class);
        startActivity(intent);
    }
    //跳转到设置界面
    public void jumpToSetting(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    //统一定义单击返回按键执行操作
    public void backToMain(View view){
        onBackPressed();
    }
}
