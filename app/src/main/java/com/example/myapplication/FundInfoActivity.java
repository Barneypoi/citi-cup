package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FundInfoActivity extends Activity {

    private ListView lv;
    private TextView tv1;
    private TextView tv2;
    private String str;

    private ArrayList<FundInfoObject> fundInfoList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundinfo);

        initFundInfo();
        FundinfoListitemAdapter adapter = new FundinfoListitemAdapter(FundInfoActivity.this,R.layout.listitem_src_fundinfo,fundInfoList);
        lv = findViewById(R.id.lv_fundinfo);
        lv.setAdapter(adapter);


        //获取当前ListView点击的行数，并且得到该数据
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                tv1 = view.findViewById(R.id.tv1_list);//找到Textviewname
                str = tv1.getText().toString();//得到数据
                Toast.makeText(FundInfoActivity.this, "" + str, Toast.LENGTH_SHORT).show();//显示数据
            }
        });



    }

    //此函数初始化列表项，将基金信息在此函数中初始化
    public void initFundInfo(){
        //缺乏鲁棒性，若名字过长，导致基金条数不显示
        FundInfoObject fund1 = new FundInfoObject("华夏成长混合","000001");
        fundInfoList.add(fund1);
        FundInfoObject fund2 = new FundInfoObject("广发理财七天债券A","000002");
        fundInfoList.add(fund2);
        FundInfoObject fund3 = new FundInfoObject("广发理财七天债券B","000003");
        fundInfoList.add(fund3);
        FundInfoObject fund4 = new FundInfoObject("嘉实美国成长股票","000004");
        fundInfoList.add(fund4);
        FundInfoObject fund5 = new FundInfoObject("广发理财七天债券B","000003");
        fundInfoList.add(fund5);
        FundInfoObject fund6 = new FundInfoObject("嘉实美国成长股票","000004");
        fundInfoList.add(fund6);
    }

    //跳转至详细信息界面
    public void jumpToDetailTest(View view){
        Intent intent = new Intent(FundInfoActivity.this,FundInfoDetailActivity.class);
        startActivity(intent);
    }

}
