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
    private TextView tv3;
    private TextView tv4;
    private BaseAdapter baseAdapter;
    private ListView lv;
    private String str;

    //改变该条目数据对象内容，将数据显示在ListView中
    private List<Integer> dataList = new ArrayList<Integer>();//实体类
    private View view;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1, container, false);

        lv = view.findViewById(R.id.lv_mainwin);
        for (int i = 0; i < 5; i++) {
            Integer integer = new Integer(i);//给实体类赋值
            dataList.add(integer);
        }

        baseAdapter = new BaseAdapter() {
            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            //获取数据条数 测试数据为5
            int num = 5;

            @Override
            public int getCount() {
                return num;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater sub_inflater = inflater;
                View sub_view;
                if (convertView == null) {
                    sub_view = sub_inflater.inflate(R.layout.listitem_mainwin, null);
                } else {
                    sub_view = convertView;
                    Log.i("info", "有缓存，不需要重新生成" + position);
                }

                tv1 = sub_view.findViewById(R.id.tv_mainwin_fundName);
                tv1.setText("FundName");

                tv2 = sub_view.findViewById(R.id.tv_mainwin_fundRate);
                tv2.setText("5.2%");

                tv3 = sub_view.findViewById(R.id.tv_mainwin_info1);
                tv4 = sub_view.findViewById(R.id.tv_mainwin_info2);

                return sub_view;
            }
        };

        lv.setAdapter(baseAdapter);

        return view;
    }

}