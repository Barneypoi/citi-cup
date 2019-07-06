package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NetActivity extends AppCompatActivity {

    //当前界面用于显示基金的listView
    private ListView lv;

    //用于响应排序的净值按钮和涨幅按钮
    private Button netWeightSortButton,increSortButton;
    //用于转换数据的JSON对象
    private JSONObject object;

    //储存数据
    private String fundName,fundNetweigh,fundIncre;

    //改变该条目数据对象内容，将数据显示在ListView中
    private ArrayList<FundInfoObject> fundInfoList = new ArrayList<>();

    //界面内ListView的响应器
    private NetListitemAdapter baseAdapter;

    //推荐基金名称接口，用于储存推荐基金的名称
    private ArrayList<String> fundNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);

        //测试数据
//        fundNameList.add("中海可转债债券A");
//        fundNameList.add("嘉实中证500ETF联");
//        fundNameList.add("华夏纯债债券A");
//        fundNameList.add("长城核心优选混合");


        lv = (ListView) findViewById(R.id.lv_net);
        netWeightSortButton = (Button) findViewById(R.id.netView_netWeightSortButton);
        netWeightSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(fundInfoList, new Comparator<FundInfoObject>() {
                    @Override
                    public int compare(FundInfoObject o1, FundInfoObject o2) {
                        FundInfoObject fund1 = (FundInfoObject) o1;
                        FundInfoObject fund2 = (FundInfoObject) o2;
                        if(Float.parseFloat(fund1.getFundNetweigh())> Float.parseFloat(fund2.getFundNetweigh()))
                            return 1;
                        else if (Float.parseFloat(fund1.getFundNetweigh()) == Float.parseFloat(fund2.getFundNetweigh()))
                            return 0;
                        else
                            return -1;
                    }
                });
            }
        });
        increSortButton = (Button) findViewById(R.id.netView_increSortButton);
        increSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(fundInfoList, new Comparator<FundInfoObject>() {
                    @Override
                    public int compare(FundInfoObject o1, FundInfoObject o2) {
                        FundInfoObject fund1 = (FundInfoObject) o1;
                        FundInfoObject fund2 = (FundInfoObject) o2;
                        float fund1Incre = fund1.getFundIncreFloat();
                        float fund2Incre = fund2.getFundIncreFloat();
                        if(fund1Incre > fund2Incre)
                            return 1;
                        else if (fund1Incre == fund2Incre)
                            return 0;
                        else
                            return -1;
                    }
                });
            }
        });

        initConnection();

    }

    //建立服务器连接，获取当前基金的基本信息并生成基本信息
    public void initConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();
                //服务器返回地址，填入请求数据参数
                //想要获得所有的基金
                Request request = new Request.Builder()
                        .get()
                        .url("http://47.100.120.235:8081/basicInfo?fundId=3").build();

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
                //createList();
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
                    try {
                        FundInfoObject temp_fund;

                        //获取到json数据中的activity数组里的内容fundName
                        String temp_name = object.getString("fundName");
                        fundName = temp_name;

                        //获取到json数据中的activity数组里的内容fundNetWeigh
                        String temp_fundNetWeigh = object.getString("fundNetWeight");
                        fundNetweigh = temp_fundNetWeigh;


                        //获取到json数据中的activity数组里的内容fundIncre
                        String temp_incre = object.getString("fundIncre");
                        fundIncre = temp_incre;


                        //创建基金信息对象
                        temp_fund = new FundInfoObject(fundName, fundNetweigh,fundIncre ,1);
                        fundInfoList.add(temp_fund);

                        //存入map
                        /*map.put("fundId", fundId);
                        map.put("fundType",fundType);
                        map.put("fundIncre",fundIncre);
                        map.put("fundRisk",fundRisk);
                        //ArrayList集合
                        mainlist.add(map);*/

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
                //通过handler可以通过子线程与UI线程进行信息传递
                Message message = new Message();
                //传递的信息
                message.what = 1;
                handler.sendMessage(message);
                // }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public void createList() {

        //初始化ListView展示基金信息
        baseAdapter = new NetListitemAdapter(getApplicationContext(), R.layout.listitem_net, fundInfoList);
        lv.setAdapter(baseAdapter);

        //获取当前ListView点击的行数，并且得到该数据信息
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                tv1 = view.findViewById(R.id.tv_mainwin_fundName);
//                fundName = tv1.getText().toString();//得到数据
//
//                tv2 = view.findViewById(R.id.tv_mainwin_fundRate);
//                fundIncre = tv2.getText().toString();
//
//                tv3 = view.findViewById(R.id.tv_mainwin_info1);
//                fundId = tv3.getText().toString();
//
//                tv4 = view.findViewById(R.id.tv_mainwin_info2);
//                fundType = tv4.getText().toString();
//
//                Intent i = new Intent(getContext(), FundInfoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("fundName", fundName);
//                bundle.putString("fundIncre", fundIncre);
//                bundle.putString("fundId", fundId);
//                bundle.putString("fundType", fundType);
//                bundle.putString("fundNetweigh", fundNetweigh);
//
//                i.putExtras(bundle);
//                startActivity(i);
//
//                Toast.makeText(getContext(), "" + fundName, Toast.LENGTH_SHORT).show();//显示数据
//            }
//        });
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public void backToMain(View view){
        Intent intent= new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}