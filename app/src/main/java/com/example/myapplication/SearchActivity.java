package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    private SearchView sv;
    //三个ListView分别对应 热门基金 搜索历史 涨幅排行
    private ListView lv1, lv2, lv3;
    //单个列表单元基金信息显示
    private TextView tv1;
    private TextView tv2;
    private ArrayList<FundInfoObject> fundInfoList1 = new ArrayList<FundInfoObject>() ;
    private ArrayList<FundInfoObject> fundInfoList2 = new ArrayList<FundInfoObject>() ;
    private ArrayList<FundInfoObject> fundInfoList3 = new ArrayList<FundInfoObject>() ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initSearchView();

    }

    private void initSearchView(){
        sv = findViewById(R.id.sv_searchActivity);
        sv.setIconifiedByDefault(true);
        sv.setInputType(InputType.TYPE_CLASS_TEXT);
        sv.setQueryHint("基金名称/基金代号");
        sv.setSubmitButtonEnabled(true);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String fundNameorId = query;
                Toast.makeText(SearchActivity.this,"您输入的基金代号/名称: "+ fundNameorId, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }


}
