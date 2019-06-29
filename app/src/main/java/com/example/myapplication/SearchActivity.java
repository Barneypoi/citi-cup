package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
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

public class SearchActivity extends AppCompatActivity {

    //三个ListView分别对应 热门基金 搜索历史 涨幅排行
    private ListView lv1, lv2, lv3;
    //搜索框
    private SearchView sv;
    //列表适配器
    private BaseAdapter baseAdapter;
    //单个列表单元基金信息显示
    private TextView tv1;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        lv1 = findViewById(R.id.lv1_search);
        lv2 = findViewById(R.id.lv2_search);
        lv3 = findViewById(R.id.lv3_search);
        sv = findViewById(R.id.sv_searchActivity);

        baseAdapter = new BaseAdapter() {

            int num = 4;
            @Override
            public int getCount() {
                return num;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater li1 = SearchActivity.this.getLayoutInflater();
                View view;

                if(convertView == null){
                    view = li1.inflate(R.layout.listitem_fundinfo,null);
                }else
                {
                    view = convertView;
                    Log.i("info","有缓存，不需要重新生成"+position);
                }

                //传入基金信息
                tv1 = findViewById(R.id.tv1_list);
                tv1.setText("OK");
                tv2 = findViewById(R.id.tv2_list);
                tv2.setText("OK");

                return view;
            }
        };
        lv1.setAdapter(baseAdapter);
        lv2.setAdapter(baseAdapter);
        lv3.setAdapter(baseAdapter);


    }

    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

