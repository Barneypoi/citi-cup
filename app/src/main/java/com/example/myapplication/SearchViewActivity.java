package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.String.valueOf;

//用于显示搜索结果的界面
public class SearchViewActivity extends Activity {

    JSONObject object;
    String fundNameorId, fundName, fundId, onclick_fundName, onclick_fundId;
    ArrayList<FundInfoObject> fundInfoList = new ArrayList<>();
    TextView title;
    ListView lv;
    FundinfoListitemAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //初始化活动界面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchview);
        title = findViewById(R.id.title_tv);
        title.setText("搜索结果");

        adapter = new FundinfoListitemAdapter(SearchViewActivity.this,R.layout.listitem_funditem, fundInfoList);

        //获取用户输入字符串，执行网络请求
        fundNameorId = getIntent().getExtras().getString("fundNameorId");
        searchInfo(fundNameorId);

    }

    public void searchInfo(String type_in){

        //新建线程进行网络请求操作
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();

                //服务器返回地址，填入请求数据参数
                //传入的参数为全部推荐基金的名称，若无推荐基金，则返回值传入参数为空字符串""
                //搜索信息后台方法提供模糊查询
                Request request = new Request.Builder()
                        .get()
                        .url("http://47.100.120.235:8081/srcInfo?fundNameorId=" + fundNameorId).build();

                try {
                    Response response = null;
                    response = okHttpClient.newCall(request).execute();
                    assert response.body() != null;
                    String data = null;
                    data = response.body().string();
                    //把数据传入解析JSON数据方法
                    jsonJX(data);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    public void jsonJX(String data) {
        if (data != null) {
            try {
                JSONArray resultJsonArray = new JSONArray(data);
                //遍历
                for (int i = 0; i < resultJsonArray.length(); i++) {
                    //JSON数据对象
                    object = resultJsonArray.getJSONObject(i);
                    try {
                        FundInfoObject temp_fund;

                        //获取到json数据中的activity数组里的内容fundId
                        String temp_name = object.getString("fundName");
                        fundName = temp_name;

                        //获取到json数据中的activity数组里的内容fundId
                        String temp_id = object.getString("fundId");
                        fundId = temp_id;

                        //创建基金信息对象
                        temp_fund = new FundInfoObject(fundName, fundId);
                        fundInfoList.add(temp_fund);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                //执行完数据线程再执行UI线程防止引用空对象
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                createList();
                            }
                        }
                );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            //查询数据为空的反馈
            Toast.makeText(SearchViewActivity.this,"您输入的基金代号/名称不存在！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SearchViewActivity.this,SearchActivity.class);
            startActivity(intent);


        }
    }

    //为获得的基金对象创建ListView
    private void createList(){
        lv = findViewById(R.id.lv_src_view);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //获取单击事件的TextView内容
                TextView t1 = findViewById(R.id.tv1_src_list);
                onclick_fundName = t1.getText().toString();

                TextView t2 = findViewById(R.id.tv2_src_list);
                onclick_fundId = t2.getText().toString();

                //跳转至基金基本信息
                Intent intent = new Intent(SearchViewActivity.this,FundInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fundName", onclick_fundName);
                bundle.putString("fundId", simplyfyId(onclick_fundId));
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

    //将自动补0的Id号去首字符0
    public String simplyfyId(String id){
        String result = valueOf(Integer.parseInt(id));
        return result;
    }

    //统一定义单击返回按键执行操作
    public void backToMain(View view){
        onBackPressed();
    }
}
