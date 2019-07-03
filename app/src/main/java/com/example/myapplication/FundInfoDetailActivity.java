package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FundInfoDetailActivity extends Activity {

    public JSONObject object;

    private String fundId, fundScale, fundEstablishTime, fundCompany, fundManager, fundAlloc;
    private TextView tv_scale, tv_esttime, tv_company, tv_manager, tv_alloc;

    //储存基金详细信息arraylist（数据接口）
    private ArrayList<FundDetailInfoObject> infoDetailList = new ArrayList<>();
    public ArrayList<Map<String, Object>> mainlist=new ArrayList<Map<String,Object>>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fundinfo_detail);

        fundId = getIntent().getExtras().getString("fundName");

        tv_scale = findViewById(R.id.tv_fundInfo_scale);
        tv_scale.setText(fundScale);

        tv_esttime = findViewById(R.id.tv_fundInfo_esttime);
        tv_esttime.setText(fundEstablishTime);

        tv_company =findViewById(R.id.tv_fundInfo_company);
        tv_company.setText(fundCompany);

        tv_manager = findViewById(R.id.tv_fundInfo_manager);
        tv_manager.setText(fundManager);

        tv_alloc =findViewById(R.id.tv_fundInfo_alloc);
        tv_alloc.setText(fundAlloc);

        init();
        //mainlist = (ArrayList<Map<String, Object>>)getIntent().getExtras().get("key");


    }


    //建立服务器连接，获取JSON数据
    private void init() {
        mainlist.clear();
        //lv=(ListView) findViewById(R.id.lv);
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient=new OkHttpClient();
                //服务器返回的地址
                Request request=new Request.Builder()
                        .get()
                        .url("http://47.100.120.235:8081/detailInfo?fundId="+fundId).build();
                try {
                    Response response=okHttpClient.newCall(request).execute();
                    //获取到数据
                    assert response.body() != null;
                    String date=response.body().string();
                    //把数据传入解析josn数据方法
                    jsonJX(date);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void jsonJX(String date) {
        //判断数据是空
        if(date!=null){
            try {
                //将字符串转换成jsonObject对象
                //JSONObject jsonObject = new JSONObject(date);
                //获取返回数据中flag的值
                //String resultCode = jsonObject.getString("type");
                //如果返回的值是success则正确
                //if (resultCode.equals("FeatureCollection")){
                //获取到json数据中里的activity数组内容
                JSONArray resultJsonArray = new JSONArray(date);
                //遍历
                for(int i=4241;i<resultJsonArray.length();i++){
                    object=resultJsonArray.getJSONObject(i);

                    Map<String, Object> map=new HashMap<String, Object>();

                    try {
                        //获取到json数据中的activity数组里的内容name
                        fundScale = object.getString("fundScale");
                        //获取到json数据中的activity数组里的内容startTime
                        fundEstablishTime = object.getString("fundEstablishTime");
                        fundCompany = object.getString("fundCompany");
                        fundManager = object.getString("fundManager");
                        fundAlloc = object.getString("fundAlloc");

                        String name = object.getString("1");
                        String shijian = object.getString("po");

                        //存入map
                        map.put("1", name);
                        map.put("po", shijian);
                        //ArrayList集合
                        mainlist.add(map);


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
                // }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }




    }
    //Handler运行在主线程中(UI线程中)，  它与子线程可以通过Message对象来传递数据
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //Mybaseadapter list_item=new Mybaseadapter();
                    //lv.setAdapter(list_item);
                    Toast.makeText(getApplicationContext(),"zahuishine",Toast.LENGTH_SHORT).show();
                    LineChart mLineChart = (LineChart) findViewById(R.id.lineChart);
                    //显示边界
                    mLineChart.setDrawBorders(true);
                    //设置数据
                    List<Entry> entries = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        entries.add(new Entry(i,Float.parseFloat((String) mainlist.get(i).get("po")) ) );
                    }
                    //准备好每个点对应的x轴数值
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        list.add(TimeStamp2Date((String) mainlist.get(i).get("1")));
                    }
                    XAxis xAxis = mLineChart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(list));

                    //一个LineDataSet就是一条线
                    LineDataSet lineDataSet;
                    lineDataSet = new LineDataSet(entries, "净值");
                    LineData data = new LineData(lineDataSet);
                    mLineChart.clear();
                    mLineChart.setData(data);

                    break;
            }


        }
    };

    public String TimeStamp2Date(String timestampString){
        Long timestamp = Long.parseLong(timestampString);
        //String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));
        return date;
    }
}
