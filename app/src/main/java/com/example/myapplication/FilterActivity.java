package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    //下拉菜单
    private Spinner spinner1, spinner2;

    //JSON数据对象
    public JSONObject object;
    //筛选基金名称接口，用于储存推荐基金的名称
    private ArrayList<String> fundNameList = new ArrayList<>();

    //改变该条目数据对象内容，将数据显示在ListView中，即将筛选的基金显示出来
    private ArrayList<FundInfoObject> fundInfoList = new ArrayList<>();
    //界面上用于显示筛选信息的listView
    private ListView lv;
    //从服务器获取的信息
    private String fundName, fundId, fundType, fundRisk, fundIncre, fundNetweigh, sortType;

    //用于筛选的数据
    private String filterFundType,filterFundRisk;

    //ListView的Adapter
    private FundinfoListitem_main_Adapter baseAdapter;

    //接受基金信息的TextView tv1为基金名称 tv2为七日年化收益率 tv3，tv4为其他可选添加信息
    private TextView tv1, tv2, tv3, tv4, title;

    //筛选基金信息的button
    private Button filterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        title = findViewById(R.id.title_tv);
        title.setText("基金筛选");

        spinner1 = (Spinner) findViewById(R.id.spin_fundkind);
        spinner2 = (Spinner) findViewById(R.id.spin_riskkind);
        filterButton = (Button) findViewById(R.id.filterButton);

        // 声明四个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                FilterActivity.this, android.R.layout.simple_spinner_item,
                getData());
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                FilterActivity.this, android.R.layout.simple_spinner_item,
                getData2());

        // 把定义好的Adapter设定到spinner中
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter2);

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
        //筛选按钮监听事件 用于从服务器获取筛选数据
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 识别用户点击筛选方式

                fundInfoList.clear();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        OkHttpClient okHttpClient = new OkHttpClient();

                        //服务器返回地址，填入请求数据参数
                        //传入的参数为全部推荐基金的名称，若无推荐基金，则返回值传入参数为空字符串""
                        Request request = new Request.Builder()
                                .get()
                                .url("http://47.100.120.235:8081/filter?fundType=" + filterFundType +"&fundRisk=" + filterFundRisk+ "&sortType=" + sortType).build();
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
                        temp_fund = new FundInfoObject(fundName, fundIncre, convertId(fundId), 1);
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

    private List<String> getData() {
        // 数据源
        List<String> dataList = new ArrayList<String>();
        dataList.add("股票型");
        dataList.add("股票指数");
        dataList.add("固定收益");
        dataList.add("混合型");
        dataList.add("货币型");
        dataList.add("理财型");
        dataList.add("联接基金");
        dataList.add("其他创新");
        dataList.add("债券型");
        dataList.add("债券指数");
        dataList.add("混合-FOF");
        dataList.add("ETF-场内");
        dataList.add("QDII");
        dataList.add("QDII-ETF");
        dataList.add("QDII-指数");
        dataList.add("定开债券");
        dataList.add("分级杠杆");

        return dataList;
    }

    private List<String> getData2() {
        // 数据源
        List<String> dataList = new ArrayList<String>();
        dataList.add("低风险");
        dataList.add("中低风险");
        dataList.add("中风险");
        dataList.add("中高风险");
        dataList.add("高风险");
        return dataList;
    }

    public void initList(){

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

}
