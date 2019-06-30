package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SearchActivity extends AppCompatActivity {

    //三个ListView分别对应 热门基金 搜索历史 涨幅排行
    private ListView lv1, lv2, lv3;
    //搜索框
    private SearchView sv;
    //单个列表单元基金信息显示
    private TextView tv1;
    private TextView tv2;

    private ArrayList<FundInfoObject> fundInfoList = new ArrayList<FundInfoObject>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFundInfo();
        FundinfoListitemAdapter adapter = new FundinfoListitemAdapter(SearchActivity.this,R.layout.listitem_fundinfo,fundInfoList);
        setContentView(R.layout.activity_search);
        lv1 = findViewById(R.id.lv1_search);
        lv2 = findViewById(R.id.lv2_search);
        lv3 = findViewById(R.id.lv3_search);

        sv = findViewById(R.id.sv_searchActivity);

        lv1.setAdapter(adapter);
        lv2.setAdapter(adapter);
        lv3.setAdapter(adapter);
    }

    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    //此函数初始化列表项，将基金信息在此函数中初始化
    public void initFundInfo(){
        FundInfoObject fund1 = new FundInfoObject("华夏成长混合","000001");
        fundInfoList.add(0,fund1);
        FundInfoObject fund2 = new FundInfoObject("广发理财七天债券A","000002");
        fundInfoList.add(1,fund2);
        FundInfoObject fund3 = new FundInfoObject("广发理财七天债券B","000003");
        fundInfoList.add(2,fund3);
        FundInfoObject fund4 = new FundInfoObject("嘉实美国成长股票人民币","000004");
        fundInfoList.add(3,fund4);
    }

}

