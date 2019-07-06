package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FilterActivity extends AppCompatActivity {
    private Spinner spinner1, spinner2,spinner3,spinner4;

    //JSON数据对象
    public JSONObject object;
    //筛选基金名称接口，用于储存推荐基金的名称
    private ArrayList<String> fundNameList = new ArrayList<>();

    //改变该条目数据对象内容，将数据显示在ListView中，即将筛选的基金显示出来
    private ArrayList<FundInfoObject> fundInfoList = new ArrayList<>();
    //界面上用于显示筛选信息的listView
    private ListView lv;
    //从服务器获取的信息
    private String fundName, fundId, fundType, fundRisk, fundIncre, fundNetweigh;

    //用于筛选的数据
    private String filterFundType,filterFundRisk;

    //ListView的Adapter
    private FundinfoListitem_main_Adapter baseAdapter;

    //接受基金信息的TextView tv1为基金名称 tv2为七日年化收益率 tv3，tv4为其他可选添加信息
    private TextView tv1, tv2, tv3, tv4;

    //筛选基金信息的button
    private Button filterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        filterButton = (Button) findViewById(R.id.filterButton);
        //筛选按钮监听事件 用于从服务器获取筛选数据
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fundInfoList.clear();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        OkHttpClient okHttpClient = new OkHttpClient();

                        //服务器返回地址，填入请求数据参数
                        //传入的参数为全部推荐基金的名称，若无推荐基金，则返回值传入参数为空字符串""
                        Request request = new Request.Builder()
                                .get()
                                .url("http://47.100.120.235:8081/filter?fundType=" + filterFundType +"@"+filterFundRisk).build();

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
        });
        // 声明四个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                FilterActivity.this, android.R.layout.simple_spinner_item,
                getData());
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                FilterActivity.this, android.R.layout.simple_spinner_item,
                getData2());
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                FilterActivity.this, android.R.layout.simple_spinner_item,
                getData3());
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(
                FilterActivity.this, android.R.layout.simple_spinner_item,
                getData4());
        // 把定义好的Adapter设定到spinner中
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        spinner4.setAdapter(adapter4);
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // 在选中之后触发
                Toast.makeText(FilterActivity.this,
                        parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                filterFundType = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 这个一直没有触发，我也不知道什么时候被触发。
                //在官方的文档上说明，为back的时候触发，但是无效，可能需要特定的场景
            }
        });
        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // 在选中之后触发
                Toast.makeText(FilterActivity.this,
                        parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                filterFundRisk = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 这个一直没有触发，我也不知道什么时候被触发。
                //在官方的文档上说明，为back的时候触发，但是无效，可能需要特定的场景
            }
        });

        //测试数据
        fundNameList.add("中海可转债债券A");
        fundNameList.add("嘉实中证500ETF联");
        fundNameList.add("华夏纯债债券A");
        fundNameList.add("长城核心优选混合");

        //initFundInfo();
        initConnection();
     }



    //建立服务器连接，获取当前基金的基本信息并生成基本信息
    public void initConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();

                //获取推荐基金名称列表
                String str_fundNameList = "";
                for (int i = 0; i < fundNameList.size(); i++) {
                    if (i == 0)
                        str_fundNameList += fundNameList.get(i);
                    else
                        str_fundNameList += ("@" + fundNameList.get(i));
                }

                //服务器返回地址，填入请求数据参数
                //传入的参数为全部推荐基金的名称，若无推荐基金，则返回值传入参数为空字符串""
                Request request = new Request.Builder()
                        .get()
                        .url("http://47.100.120.235:8081/mainInfo?fundName=" + str_fundNameList).build();

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

                        //获取到json数据中的activity数组里的内容fundId
                        String temp_name = object.getString("fundName");
                        fundName = temp_name;
                        //Log.v(getActivity().toString(), fundName);

                        //获取到json数据中的activity数组里的内容fundId
                        String temp_id = object.getString("fundId");
                        fundId = temp_id;
                        //Log.v(getActivity().toString(), fundId);

                        //获取到json数据中的activity数组里的内容fundType
                        String temp_type = object.optString("fundType");
                        fundType = temp_type;
                        //Log.v(getActivity().toString(), fundType);

                        //获取到json数据中的activity数组里的内容fundIncre
                        String temp_incre = object.getString("fundIncre");
                        fundIncre = temp_incre;
                        //Log.v(getActivity().toString(), fundIncre);

                        //获取到json数据中的activity数组里的内容fundRisk
                        String temp_risk = object.getString("fundRisk");
                        fundRisk = temp_risk;
                        //Log.v(getActivity().toString(), fundRisk);

                        /*String temp_netweigh = object.getString("fundNetweigh");
                        fundNetweigh = temp_netweigh;
                        Log.v(getActivity().toString(),fundNetweigh);
                        */

                        //创建基金信息对象
                        temp_fund = new FundInfoObject(fundName, fundIncre, convertId(fundId), fundType);
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
        baseAdapter = new FundinfoListitem_main_Adapter(getApplicationContext(), R.layout.listitem_mainwin, fundInfoList);
        lv.setAdapter(baseAdapter);

        //获取当前ListView点击的行数，并且得到该数据信息
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv1 = view.findViewById(R.id.tv_mainwin_fundName);
                fundName = tv1.getText().toString();//得到数据

                tv2 = view.findViewById(R.id.tv_mainwin_fundRate);
                fundIncre = tv2.getText().toString();

                tv3 = view.findViewById(R.id.tv_mainwin_info1);
                fundId = tv3.getText().toString();

                tv4 = view.findViewById(R.id.tv_mainwin_info2);
                fundType = tv4.getText().toString();

                Intent i = new Intent(getApplicationContext(), FundInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fundName", fundName);
                bundle.putString("fundIncre", fundIncre);
                bundle.putString("fundId", fundId);
                bundle.putString("fundType", fundType);
                bundle.putString("fundNetweigh", fundNetweigh);

                i.putExtras(bundle);
                startActivity(i);

                Toast.makeText(getApplicationContext(), "" + fundName, Toast.LENGTH_SHORT).show();//显示数据
            }
        });
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private List<String> getData() {
        // 数据源
        List<String> dataList = new ArrayList<String>();
        dataList.add("1");
        dataList.add("2");
        dataList.add("3");
        dataList.add("4");
        return dataList;
    }

    private List<String> getData2() {
        // 数据源
        List<String> dataList = new ArrayList<String>();
        dataList.add("a");
        dataList.add("b");
        dataList.add("c");
        dataList.add("d");
        return dataList;
    }

    private List<String> getData3() {
        // 数据源
        List<String> dataList = new ArrayList<String>();
        dataList.add("q");
        dataList.add("w");
        dataList.add("e");
        dataList.add("r");
        return dataList;
    }

    private List<String> getData4() {
        // 数据源
        List<String> dataList = new ArrayList<String>();
        dataList.add("!");
        dataList.add("@");
        dataList.add("#");
        dataList.add("$");
        return dataList;
    }

    public String convertId(String orig_id){
        int orig_length = orig_id.length();
        int result_length = 6 - orig_length;
        String zero ="";
        for(int i=0;i<result_length;i++){
            zero += "0";
        }
        return zero + orig_id ;
    }


    public void backToMain(View view){
        Intent intent= new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
