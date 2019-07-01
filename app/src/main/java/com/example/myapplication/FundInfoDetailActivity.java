package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ListView;
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
    private ListView lv;
    //ListView适配器
    private FundDetailInfoListitemAdapter adapter;
    //储存基金详细信息arraylist（数据接口）
    private ArrayList<FundDetailInfoObject> infoDetailList = new ArrayList<>();
    public ArrayList<Map<String, Object>> mainlist=new ArrayList<Map<String,Object>>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundinfo_detail);

        init();

        initFundInfo();
        lv = findViewById(R.id.lv_fundInfo_detail);
        //实例化adapter
        adapter = new FundDetailInfoListitemAdapter(FundInfoDetailActivity.this ,R.layout.listitem_src_fundinfo ,infoDetailList);
        //为ListView添加adapter
        lv.setAdapter(adapter);

        //mainlist = (ArrayList<Map<String, Object>>)getIntent().getExtras().get("key");

    }

    public void initFundInfo(){
        //默认展示八条信息(根据需求更改)
        //字母处改为该信息名称 数字处为信息具体内容
        FundDetailInfoObject fund1 = new FundDetailInfoObject("A","1");
        infoDetailList.add(fund1);
        FundDetailInfoObject fund2 = new FundDetailInfoObject("B","2");
        infoDetailList.add(fund2);
        FundDetailInfoObject fund3 = new FundDetailInfoObject("C","3");
        infoDetailList.add(fund3);
        FundDetailInfoObject fund4 = new FundDetailInfoObject("D","4");
        infoDetailList.add(fund4);
        FundDetailInfoObject fund5 = new FundDetailInfoObject("E","5");
        infoDetailList.add(fund5);
        FundDetailInfoObject fund6 = new FundDetailInfoObject("F","6");
        infoDetailList.add(fund6);
        FundDetailInfoObject fund7 = new FundDetailInfoObject("G","7");
        infoDetailList.add(fund7);
        FundDetailInfoObject fund8 = new FundDetailInfoObject("H","8");
        infoDetailList.add(fund8);
    }


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
                        .url("http://47.100.120.235:8081/check?value1=1").build();
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
        }).start();;

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
                        String name = object.getString("1");
                        //获取到json数据中的activity数组里的内容startTime
                        String shijian=object.getString("po");
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
