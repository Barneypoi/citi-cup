package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
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

    private String fundId, fundScale, fundEstablishTime, fundCompany, fundManager, stockRatio, bondRatio, cashRatio, netWeigh;
    private TextView tv_scale, tv_esttime, tv_company, tv_manager, tv_stock, tv_bond, tv_cash, title;

    //储存基金详细信息arraylist（数据接口）
    //private ArrayList<FundDetailInfoObject> infoDetailList = new ArrayList<>();
    public ArrayList<Map<String, Object>> mainlist=new ArrayList<Map<String,Object>>();
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundinfo_detail);

        title = findViewById(R.id.title_tv);
        title.setText("基金详细信息");

        context = this;

        fundId = getIntent().getExtras().getString("fundId");
        Log.v("FundInfoDetailActivity", fundId);

        init();

    }


    //建立服务器连接，获取JSON数据
    private void init() {
        //mainlist.clear();
        //lv=(ListView) findViewById(R.id.lv);
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient=new OkHttpClient();

                //先转换成Int值再转换成String消除补上的0
                String temp_Id = String.valueOf(Integer.parseInt(fundId));
                //服务器返回的地址
                Request request=new Request.Builder()
                        .get()
                        .url("http://47.100.120.235:8081/detailInfo?fundId="+temp_Id).build();
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
                for(int i=0;i<resultJsonArray.length();i++){
                    object=resultJsonArray.getJSONObject(i);

                    //获取到json数据中的activity数组里的内容name
                    fundScale = object.optString("fundScale");
                    //获取到json数据中的activity数组里的内容establishTime
                    fundEstablishTime = object.optString("fundEstablishTime");
                    fundCompany = object.optString("funCompany");
                    fundManager = object.optString("fundManager");
                    stockRatio = object.optString("stockRatio");
                    bondRatio = object.optString("bondRatio");
                    cashRatio = object.optString("cashRatio");

                    JSONArray netWeighJSList = object.getJSONArray("netWeigh");

                    //for(int tmp = 0; tmp< netWeighJSList.length(); tmp++)
                    for(int tmp = netWeighJSList.length() - 1; tmp>0; tmp--){
                        JSONObject tmpJSObject = netWeighJSList.getJSONObject(tmp);
                        Map<String, Object> map = new HashMap<String, Object>();
                        String time = tmpJSObject.getString("1");
                        String netWeigh = tmpJSObject.getString("po");


                        //存入map
                        map.put("1", time);
                        map.put("po", netWeigh);
                        //ArrayList集合
                        mainlist.add(map);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setUIView();
                        }
                    });

                }

                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);

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
                    Toast.makeText(getApplicationContext(),"显示详细基金信息",Toast.LENGTH_SHORT).show();
                    LineChart mLineChart = (LineChart) findViewById(R.id.lineChart);
                    //显示边界
                    mLineChart.setDrawBorders(true);
                    //设置数据
                    List<Entry> entries = new ArrayList<>();
                    for (int i = 0; i < mainlist.size(); i++) {
                        entries.add(new Entry(i,Float.parseFloat((String) mainlist.get(i).get("po")) ) );
                    }
                    //准备好每个点对应的x轴数值
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < mainlist.size(); i++) {
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

                    DetailsMarkerView detailsMarkerView = new DetailsMarkerView(context,R.layout.detail);
                    //一定要设置这个玩意，不然到点击到最边缘的时候不会自动调整布局
                    detailsMarkerView.setChartView(mLineChart);
                    mLineChart.setMarker(detailsMarkerView);
                    Description desc= new Description();
                    desc.setText("");
                    mLineChart.setDescription(desc);
                    break;
            }


        }
    };

    public void setUIView(){

        tv_scale = findViewById(R.id.tv_fundInfo_scale);
        tv_scale.setText(fundScale);

        tv_esttime = findViewById(R.id.tv_fundInfo_esttime);
        tv_esttime.setText(fundEstablishTime);

        tv_company =findViewById(R.id.tv_fundInfo_company);
        tv_company.setText(fundCompany);

        tv_manager = findViewById(R.id.tv_fundInfo_manager);
        tv_manager.setText(fundManager);

        tv_stock =findViewById(R.id.tv_fundInfo_stock);
        tv_stock.setText(stockRatio);

        tv_bond = findViewById(R.id.tv_fundInfo_bond);
        tv_bond.setText(bondRatio);

        tv_cash = findViewById(R.id.tv_fundInfo_cash);
        tv_cash.setText(cashRatio);
    }

    public String TimeStamp2Date(String timestampString){
        Long timestamp = Long.parseLong(timestampString);
        //String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));
        return date;
    }
}
