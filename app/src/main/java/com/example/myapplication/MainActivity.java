package com.example.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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
                selectTab(0);//当点击的是微信的Tab就选中微信的Tab
                break;
            case R.id.id_tab_frd:
                selectTab(1);
                break;
            case R.id.id_tab_address:
                selectTab(2);
                break;
        }

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
            //当选中点击的是微信的Tab时
            case 0:
                //如果微信对应的Fragment没有实例化，则进行实例化，并显示出来
                if (mFragSY == null) {
                    mFragSY = new WeixinFragment();
                    transaction.add(R.id.id_content, mFragSY);
                } else {
                    //如果微信对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mFragSY);
                }
                break;
            case 1:
                if (mFragFrd == null) {
                    mFragFrd = new FrdFragment();
                    transaction.add(R.id.id_content, mFragFrd);
                } else {
                    transaction.show(mFragFrd);
                }
                break;
            case 2:
                if (mFragAddress == null) {
                    mFragAddress = new AddressFragment();
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
}
