package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import java.util.ArrayList;

public class FundInfoDetailActivity extends Activity {

    private ListView lv;
    //ListView适配器
    private FundDetailInfoListitemAdapter adapter;
    //储存基金详细信息arraylist（数据接口）
    private ArrayList<FundDetailInfoObject> infoDetailList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundinfo_detail);

        lv = findViewById(R.id.lv_fundInfo_detail);
        //实例化adapter
        adapter = new FundDetailInfoListitemAdapter(FundInfoDetailActivity.this ,R.layout.listitem_src_fundinfo ,infoDetailList);
        //为ListView添加adapter
        lv.setAdapter(adapter);
    }

    public void initFundInfo(){
        //默认展示八条信息(根据需求更改)
        //字母处改为该信息名称 数字处为信息具体内容
        FundDetailInfoObject fund1 = new FundDetailInfoObject("A","1");
        infoDetailList.add(fund1);
        FundDetailInfoObject fund2 = new FundDetailInfoObject("B","2");
        infoDetailList.add(fund2);
        FundDetailInfoObject fund3 = new FundDetailInfoObject("C","3");
        infoDetailList.add(fund3);
        FundDetailInfoObject fund4 = new FundDetailInfoObject("D","4");
        infoDetailList.add(fund4);
        FundDetailInfoObject fund5 = new FundDetailInfoObject("E","5");
        infoDetailList.add(fund5);
        FundDetailInfoObject fund6 = new FundDetailInfoObject("F","6");
        infoDetailList.add(fund6);
        FundDetailInfoObject fund7 = new FundDetailInfoObject("G","7");
        infoDetailList.add(fund7);
        FundDetailInfoObject fund8 = new FundDetailInfoObject("H","8");
        infoDetailList.add(fund8);
    }
}
