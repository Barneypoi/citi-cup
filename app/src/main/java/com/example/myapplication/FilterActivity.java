package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

import static java.lang.String.valueOf;


public class FilterActivity extends AppCompatActivity {
    //下拉菜单，spinner1为基金类型,spinner2为基金风险
    private Spinner spinner1, spinner2;

    //JSON数据对象，用于在转义网络信息是作中间变量媒介
    public JSONObject object;

    //筛选基金名称接口，用于储存推荐基金的名称
    private ArrayList<String> fundNameList = new ArrayList<>();

    //改变该条目数据对象内容，将数据显示在ListView中，即将筛选的基金显示出来
    private ArrayList<FundInfoObject> fundInfoList = new ArrayList<>();

    //界面上用于显示筛选信息的listView
    private ListView lv;

    //从服务器获取的基金信息
    private String fundName, fundId, fundType, fundRisk, fundIncre, fundNetweigh;

    //用于筛选的数据，基金类型和基金风险
    private String filterFundType, filterFundRisk;

    //ListView的Adapter
    private FundNetInfoAdapter baseAdapter;

    //接受基金信息的TextView tv1为基金名称 tv2为七日年化收益率 tv3，tv4为其他可选添加信息
    private TextView tv1, tv2, tv3, tv4, title;

    //筛选基金信息的button
    private Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        //设置界面头部文字
        title = findViewById(R.id.title_tv);
        title.setText("基金筛选");

        //测试数据
        /*
        fundNameList.add("中海可转债债券A");
        fundNameList.add("嘉实中证500ETF联");
        fundNameList.add("华夏纯债债券A");
        fundNameList.add("长城核心优选混合");
        */

        //初始化获得ListView
        lv = (ListView) findViewById(R.id.lv_filter) ;

        //初始化筛选下拉单
        initSpinner();

        //筛选按钮监听事件 用于从服务器获取筛选数据
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从服务端获取数据
                initConnection();
                //调试信息
                Toast.makeText(FilterActivity.this, filterFundRisk + "    " + filterFundType, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //初始化筛选框下拉单
    public void initSpinner() {
        //初始化
        spinner1 = (Spinner) findViewById(R.id.spin_fundkind);
        spinner2 = (Spinner) findViewById(R.id.spin_riskkind);
        filterButton = (Button) findViewById(R.id.filterButton);

        // 声明两个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                FilterActivity.this, android.R.layout.simple_spinner_item,
                getData1());
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                FilterActivity.this, android.R.layout.simple_spinner_item,
                getData2());

        // 把定义好的Adapter设定到spinner中
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);

        //设定下拉框的默认值
        int fundtype_pos = adapter1.getPosition("债券型");
        spinner1.setSelection(fundtype_pos);

        int risktype_pos = adapter2.getPosition("中高风险");
        spinner2.setSelection(risktype_pos);

        //添加下拉框监听
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // 在选中之后触发
                Toast.makeText(FilterActivity.this,
                        parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                //获取选择的基金类型
                filterFundType = parent.getItemAtPosition(position).toString();
                spinner1.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 这个一直没有触发，我也不知道什么时候被触发。
                //在官方的文档上说明，为back的时候触发，但是无效，可能需要特定的场景
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // 在选中之后触发
                Toast.makeText(FilterActivity.this,
                        parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                //获取选择的基金风险
                filterFundRisk = parent.getItemAtPosition(position).toString();
                spinner2.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 这个一直没有触发，我也不知道什么时候被触发。
                //在官方的文档上说明，为back的时候触发，但是无效，可能需要特定的场景
            }
        });
    }

    public void bindSpinner(){

    }

    //建立服务器连接，获取当前基金的基本信息并生成基本信息
    public void initConnection() {
        fundInfoList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {

                //initSpinner();

                OkHttpClient okHttpClient = new OkHttpClient();
                //服务器返回地址，填入请求数据参数
                //传入的参数为全部推荐基金的名称，若无推荐基金，则返回值传入参数为空字符串""
                Request request = new Request.Builder()
                        .get()
                        .url("http://47.100.120.235:8081/filterInfo?fundType=" + filterFundType + "&fundRisk=" + filterFundRisk).build();
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

                    FundInfoObject temp_fund;

                    //获取到json数据中的activity数组里的内容fundName
                    String temp_name = object.optString("fundName");
                    fundName = temp_name;
                    //Log.v(getActivity().toString(), fundName);

                    //获取到json数据中的activity数组里的内容fundId
                    String temp_id = object.optString("fundId");
                    fundId = temp_id;
                    //Log.v(getActivity().toString(), fundId);

                    //获取到json数据中的activity数组里的内容fundIncre
                    String temp_incre = object.optString("fundIncre");
                    fundIncre = temp_incre;
                    //Log.v(getActivity().toString(), fundIncre);

                    //获取到json数据中的activity数组里的内容fundIncre
                    String temp_netweigh = object.optString("nowNetweigh");
                    fundNetweigh = temp_netweigh;
                    //.v(getActivity().toString(),fundNetweigh);

                    //创建基金信息对象
                    temp_fund = new FundInfoObject(fundName, fundId,fundNetweigh,fundIncre);
                    fundInfoList.add(temp_fund);

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

        if(fundInfoList.size() == 0)
            Toast.makeText(getApplicationContext(),"没有这种类型风险的基金",Toast.LENGTH_LONG);
        //初始化ListView展示基金信息
        baseAdapter = new FundNetInfoAdapter(getApplicationContext(), R.layout.listitem_net, fundInfoList);
        lv.setAdapter(baseAdapter);

        //获取当前ListView点击的行数，并且得到该数据信息
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 这个地方应该修改成 用fundInfoArraylist来传递数据
                tv1 = view.findViewById(R.id.netListFundName);
                fundName = tv1.getText().toString();//得到数据

                tv2 = view.findViewById(R.id.netListFundId);
                fundId = tv2.getText().toString();

                tv3 = view.findViewById(R.id.netListFundNetWeight);
                fundNetweigh = tv3.getText().toString();

                tv4 = view.findViewById(R.id.netListFundIncre);
                fundIncre = tv4.getText().toString();

                Intent i = new Intent(getApplicationContext(), FundInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fundName", fundName);
                bundle.putString("fundIncre", fundIncre);
                bundle.putString("fundId", fundId);
                bundle.putString("fundNetweigh", fundNetweigh);

                i.putExtras(bundle);
                startActivity(i);

                Toast.makeText(getApplicationContext(), "" + fundName, Toast.LENGTH_SHORT).show();//显示数据
            }
        });
    }

    //基金类型筛选下拉框的数据源
    private List<String> getData1() {
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

    //基金风险筛选下拉框的数据源
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

    public void initList() {

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
