package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NetActivity extends AppCompatActivity {

    //当前界面用于显示基金的listView
    private ListView lv;

    //用于响应排序的净值按钮和涨幅按钮
    private Button byNetweigh,byIncre;

    //排序方式
    private String sortType = "increDesc";

    //用于转换数据的JSON对象
    private JSONObject object;

    //储存数据
    private String fundId, fundName,fundNetweigh,fundIncre;

    //改变该条目数据对象内容，将数据显示在ListView中
    //1,2,3,4用于存放已拿取到的数据
    private ArrayList<FundInfoObject> fundInfoList1 = new ArrayList<>();//increAs
    private ArrayList<FundInfoObject> fundInfoList2 = new ArrayList<>();//increDesc
    private ArrayList<FundInfoObject> fundInfoList3 = new ArrayList<>();//netweighAs
    private ArrayList<FundInfoObject> fundInfoList4 = new ArrayList<>();//netweighDesc

    //界面内ListView的响应器
    private FundNetInfoAdapter baseAdapter;

    //用于储存显示基金的ID们
    private ArrayList<String> fundIdList = new ArrayList<>();

    private TextView title, tv1,tv2,tv3,tv4;

    //筛选数据方式--为真则按升序排列
    private boolean increAs = false;
    private boolean netweighAs = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);

        title = findViewById(R.id.title_tv);
        title.setText("基金净值");

        //默认排序方式以及选择排序方式
        sortType = "increDesc";

        //初始化ListView
        lv = findViewById(R.id.lv_net);
        initConnection();

        //网络请求判断，已取过的数据已存在本地变量中，再次访问不需要提交网络请求
        byIncre = findViewById(R.id.netView_increSortButton);
        byIncre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (increAs == true) {
                    increAs = false;
                    sortType = "increDesc";
                    if(fundInfoList2.size() ==0)
                        initConnection();
                    else
                        createList();
                }
                else {
                    increAs = true;
                    sortType = "increAs";
                    if(fundInfoList1.size() ==0)
                        initConnection();
                    else
                        createList();
                }
            }
        });

        byNetweigh = findViewById(R.id.netView_netWeightSortButton);
        byNetweigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (netweighAs == true) {
                    netweighAs = false;
                    sortType = "netweighDesc";
                    if(fundInfoList4.size() ==0)
                        initConnection();
                    else
                        createList();
                }
                else{
                    netweighAs = true;
                    sortType = "netweighAs";
                    if(fundInfoList3.size() ==0)
                        initConnection();
                    else
                        createList();
                }
            }
        });
    }

    //建立服务器连接，获取当前基金的基本信息并生成基本信息
    public void initConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();
                //服务器返回地址，填入请求数据参数
                    Request request = new Request.Builder()
                            .get()
                            .url("http://47.100.120.235:8081/netInfo?&sortType=" + sortType).build();

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

    private void jsonJX(String data) {
        //判断数据是空
        if (data != null) {
            try {
                JSONArray resultJsonArray = new JSONArray(data);
                //遍历
                for (int i = 0; i < resultJsonArray.length(); i++) {
                    //JSON数据对象
                    object = resultJsonArray.getJSONObject(i);

                        FundInfoObject temp_fund;

                        //获取到json数据中的activity数组里的内容fundName
                        String temp_name = object.optString("fundName");
                        fundName = temp_name;

                        String temp_id = object.optString("fundId");
                        fundId = temp_id;

                        //获取到json数据中的activity数组里的内容fundNetWeigh
                        String temp_fundNetWeigh = object.optString("nowNetweigh");
                        fundNetweigh = temp_fundNetWeigh;


                        //获取到json数据中的activity数组里的内容fundIncre
                        String temp_incre = object.optString("fundIncre");
                        fundIncre = temp_incre;

                        //创建基金信息对象
                        temp_fund = new FundInfoObject(fundName,fundId, fundNetweigh,fundIncre);
                        switch (sortType){
                            case "increAs":
                                fundInfoList1.add(temp_fund);
                            case "increDesc":
                                fundInfoList2.add(temp_fund);
                            case "netweighAs":
                                fundInfoList3.add(temp_fund);
                            case "netweighDesc":
                                fundInfoList4.add(temp_fund);
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

    }

    public void createList() {

        //初始化ListView展示基金信息
        switch (sortType){
            case "increAs":
                baseAdapter = new FundNetInfoAdapter(getApplicationContext(), R.layout.listitem_net, fundInfoList1);
            case "increDesc":
                baseAdapter = new FundNetInfoAdapter(getApplicationContext(), R.layout.listitem_net, fundInfoList2);
            case "netweighAs":
                baseAdapter = new FundNetInfoAdapter(getApplicationContext(), R.layout.listitem_net, fundInfoList3);
            case "netweighDesc":
                baseAdapter = new FundNetInfoAdapter(getApplicationContext(), R.layout.listitem_net, fundInfoList4);
        }
        lv.setAdapter(baseAdapter);

        //获取当前ListView点击的行数，并且得到该数据信息
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv1 = view.findViewById(R.id.netListFundName);
                fundName = tv1.getText().toString();//得到数据

                tv2 = view.findViewById(R.id.netListFundId);
                fundId = tv2.getText().toString();

                tv3 = view.findViewById(R.id.netListFundNetWeight);
                fundNetweigh = tv3.getText().toString();

                tv4 = view.findViewById(R.id.netListFundIncre);
                fundIncre = tv4.getText().toString();

                Intent i = new Intent(NetActivity.this, FundInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fundName", fundName);
                bundle.putString("fundId", fundId);
                bundle.putString("fundIncre", fundIncre);
                bundle.putString("fundNetweigh", fundNetweigh);

                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    //统一定义单击返回按键执行操作
    public void backToMain(View view){
        onBackPressed();
    }

}