package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//用于基金搜索的界面
public class SearchActivity extends Activity implements AdapterView.OnItemClickListener {

    private SearchView sv;
    //三个ListView分别对应 热门基金 搜索历史 涨幅排行
    private ListView lv1, lv2;
    //单个列表单元基金信息显示
    private TextView tv1,tv2;
    //标题
    private TextView title;
    private ArrayList<FundInfoObject> fundInfoList1 = new ArrayList<>() ;
    private ArrayList<FundInfoObject> fundInfoList2 = new ArrayList<>() ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        title = findViewById(R.id.title_tv);
        title.setText("基金搜索");

        initSearchView();
        initFundInfo();
        initList();
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

                Intent intent = new Intent(SearchActivity.this,SearchViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fundNameorId",fundNameorId);
                intent.putExtras(bundle);
                startActivity(intent);
                Log.v("SearchActivity","Submit");

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }


    public void initFundInfo(){
        FundInfoObject fund1 = new FundInfoObject("富国国有企业债债券A/B","139");
        fundInfoList1.add(fund1);
        FundInfoObject fund2 = new FundInfoObject("大成景旭纯债债券A","152");
        fundInfoList1.add(fund2);
        FundInfoObject fund3 = new FundInfoObject("易方达裕丰回报债券","171");
        fundInfoList1.add(fund3);
        FundInfoObject fund4 = new FundInfoObject("汇添富高息债债券A","174");
        fundInfoList1.add(fund4);
        FundInfoObject fund21 = new FundInfoObject("易方达投资级信用债债券C","206");
        fundInfoList2.add(fund21);
        FundInfoObject fund22 = new FundInfoObject("嘉实丰益纯债定期债券","116");
        fundInfoList2.add(fund22);
        FundInfoObject fund23 = new FundInfoObject("汇添富实业债债券A","122");
        fundInfoList2.add(fund23);
        FundInfoObject fund24 = new FundInfoObject("大成景安短融债券A","128");
        fundInfoList2.add(fund24);
    }

    public void initList(){
        //List适配器
        FundinfoListitemAdapter adapter1 = new FundinfoListitemAdapter(getApplicationContext(),R.layout.listitem_funditem,fundInfoList1);
        FundinfoListitemAdapter adapter2 = new FundinfoListitemAdapter(getApplicationContext(),R.layout.listitem_funditem,fundInfoList2);
        //setContentView(R.layout.activity_search);
        lv1 = findViewById(R.id.lv1_search);
        lv2 = findViewById(R.id.lv2_search);

        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);

        //添加点击事件监听器
        lv1.setOnItemClickListener(SearchActivity.this);
//        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                tv1 = view.findViewById(R.id.tv1_src_list);
//                String fundName = tv1.getText().toString();
//                //fundName = fundInfoList1.get(position).getFundName();
//
//                tv2 = findViewById(R.id.tv2_src_list);
//                String fundId = tv2.getText().toString();
//                //fundId  = fundInfoList1.get(position).getFundId();
//
//                Intent intent = new Intent(SearchActivity.this,FundInfoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("fundName", fundName);
//                bundle.putString("fundId", fundId);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
        //添加点击事件监听器
        lv2.setOnItemClickListener(SearchActivity.this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        tv1 = view.findViewById(R.id.tv1_src_list);
        String fundName = tv1.getText().toString();

        tv2 = view.findViewById(R.id.tv2_src_list);
        String fundId = tv2.getText().toString();

        Intent intent = new Intent(SearchActivity.this,FundInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("fundName", fundName);
        bundle.putString("fundId", fundId);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    //统一定义单击返回按键执行操作
    public void backToMain(View view){
        onBackPressed();
    }
}
