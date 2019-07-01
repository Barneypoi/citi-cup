package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SYFragment extends Fragment {

    //接受基金信息的TextView tv1为基金名称 tv2为七日年化收益率 tv3，tv4为其他可选添加信息
    private TextView tv1;
    private TextView tv2;
    private FundinfoListitem_main_Adapter baseAdapter;
    private ListView lv;
    private String str;

    //改变该条目数据对象内容，将数据显示在ListView中
    private ArrayList<FundInfoObject> fundInfoList = new ArrayList<>();

    private View view;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1, container, false);

        lv = view.findViewById(R.id.lv_mainwin);

        tv1 = view.findViewById(R.id.tv_big_fundName);
        tv1.setText("国富潜力组合混合A");

        tv2 = view.findViewById(R.id.tv_big_fundRate);
        tv2.setText("5.29%");


        initFundInfo();
        FundinfoListitem_main_Adapter baseAdapter = new FundinfoListitem_main_Adapter(getContext() ,R.layout.listitem_mainwin,fundInfoList);
        lv.setAdapter(baseAdapter);

        return view;
    }

    public void initFundInfo(){
        FundInfoObject fund1 = new FundInfoObject("博时鑫瑞混合A","1.41%","002558","管理人：前海开源基金");
        fundInfoList.add(fund1);
        FundInfoObject fund2 = new FundInfoObject("安信新回报混合A","1.91%","002770","管理人：安信基金");
        fundInfoList.add(fund2);
        FundInfoObject fund3 = new FundInfoObject("广发理财七天债券B","1.67%","270007","管理人：华安基金");
        fundInfoList.add(fund3);
        FundInfoObject fund4 = new FundInfoObject("嘉实美国成长股票","1.94%","001071","管理人：华安基金");
        fundInfoList.add(fund4);
    }

}