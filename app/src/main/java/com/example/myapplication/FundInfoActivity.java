package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
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

public class FundInfoActivity extends Activity {

    private JSONObject jsobject;

    private ListView lv;
    private TextView tv1;
    private TextView tv2;
    private String str;

    private ArrayList<FundInfoObject> fundInfoList = new ArrayList<>();

    private String fundName, fundIncre, fundId, fundType, fundRisk, fundNetweigh;

    private TextView tv_id, tv_type, tv_risk, tv_netweigh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundinfo);

        //获取首页列表视图点击单元格的基金信息
        fundName = getIntent().getExtras().getString("fundName");
        fundIncre = getIntent().getExtras().getString("fundIncre");
        fundId = getIntent().getExtras().getString("fundId");
        fundType = getIntent().getExtras().getString("fundType");


        //initFundInfo();
        initConnection();

        //基金名称与七日年化信息放置在大框体中
        tv1 = findViewById(R.id.tv_big_fundName);
        tv1.setText(fundName);
        tv2 = findViewById(R.id.tv_big_fundRate);
        tv2.setText(fundIncre);

        //其他信息放置在Textview中
        tv_id = findViewById(R.id.tv_fundInfo_id);
        tv_id.setText(fundId);

        tv_type = findViewById(R.id.tv_fundInfo_type);
        tv_type.setText(fundType);

        tv_risk = findViewById(R.id.tv_fundInfo_risk);
        tv_risk.setText(fundRisk);

        tv_netweigh = findViewById(R.id.tv_fundInfo_netweigh);
        tv_netweigh.setText(fundNetweigh);

    }

    //跳转至详细信息界面
    public void jumpToDetailTest(View view){
        Intent intent = new Intent(FundInfoActivity.this,FundInfoDetailActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("fundId",fundId);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void initConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();

                //服务器返回地址，填入请求数据参数
                //传入的参数为全部推荐基金的名称，若无推荐基金，则返回值传入参数为空字符串""
                Request request = new Request.Builder()
                        .get()
                        .url("http://47.100.120.235:8081/basicInfo?fundId="+fundId).build();
                try{
                    Response response = okHttpClient.newCall(request).execute();
                    assert response.body() != null;
                    String data = response.body().string();
                    //把数据传入解析JSON数据方法
                    jsonJX(data);

                }catch(IOException e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void jsonJX(String data){
        //判断数据是空
        if(data!=null){
            try {

                JSONArray resultJsonArray = new JSONArray(data);
                //遍历
                for(int i=0;i<resultJsonArray.length();i++){
                    //JSON数据对象
                    jsobject=resultJsonArray.getJSONObject(i);

                    try {

                        FundInfoObject temp_fund;

                        //获取到json数据中的activity数组里的内容fundRisk
                        String temp_risk = jsobject.getString("fundRisk");
                        fundRisk = temp_risk;

                        //获取到json数据中的activity数组里的内容fundNetweigh
                        String temp_netweigh = jsobject.getString("fundNetweigh");
                        fundNetweigh = temp_netweigh;

                        //TODO将基金信息显示在列表中

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void backToMainWin(View view){
        Intent intent= new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
