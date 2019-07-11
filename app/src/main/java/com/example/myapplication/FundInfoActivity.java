package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.String.valueOf;

public class FundInfoActivity extends Activity {

    private JSONObject jsobject;

    private ListView lv;
    private TextView tv1;
    private TextView tv2;
    private String str;

    private ArrayList<FundInfoObject> fundInfoList = new ArrayList<>();

    private String fundName, fundIncre, fundId, fundType, fundRisk, fundNetweigh;

    private TextView tv_id, tv_type, tv_risk, tv_netweigh, title;

    //单元格跳转详细信息的控件
    private TextView tv;
    private ImageView iv;

    //用于储存该用户自选基金Id的数组
    private ArrayList<String> favoriteFundIds = new ArrayList<>();

    //用于记录自选初始状态信息,true为有,false为没有
    private boolean favFlag = true;

    //用于自选的按钮
    private Button addOrRemoveFavButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundinfo);

        title = findViewById(R.id.title_tv);
        title.setText("基金基本信息");

        //获取首页列表视图点击单元格的基金代号
        //fundName = getIntent().getExtras().getString("fundName");
        //fundIncre = getIntent().getExtras().getString("fundIncre");
        fundId = getIntent().getExtras().getString("fundId");
        addOrRemoveFavButton = (Button) findViewById(R.id.addOrRemoveFavButton);
        addOrRemoveFavButton.setText("");
        favoriteFundIds.clear();
        initConnection();
        //此用于连接数据库 得到该用户的自选信息
        initFavConnection();

        tv = findViewById(R.id.tv_jumpdetail_list);
        iv = findViewById(R.id.iv_junpdetail_list);
        //为跳转控件添加监听器，执行跳转方法
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToDetailTest();
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToDetailTest();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveFavFund();
    }

    private void initFavConnection(){

        //创建子线程发送网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();

                //服务器返回地址，填入请求数据参数
                // TODO 传入的参数为用户id 需要将Url替换成访问自选基金的服务器网址

                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType,
                        "uuid=111&fundId=*"+"&option=read&date=*");

                Request request = new Request.Builder()
                        .post(body)
                        .url("http://47.100.120.235:8081/collection").build();


                try{
                    Response response = okHttpClient.newCall(request).execute();
                    assert response.body() != null;
                    String data = response.body().string();
                    //把数据传入解析JSON数据方法
                    jsonJXFav(data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setUIButton();
                        }
                    });

                }catch(IOException e){
                    e.printStackTrace();
                }

            }
        }).start();
    }
    public void initConnection(){

        //创建子线程发送网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();

                //服务器返回地址，填入请求数据参数
                //传入的参数为全部推荐基金的名称，若无推荐基金，则返回值传入参数为空字符串""

                //先转换成Int值再转换成String消除补上的0
                String temp_Id = simplyfyId(fundId);
                Log.v("FundInfoActivity",temp_Id);
                Request request = new Request.Builder()
                        .get()
                        .url("http://47.100.120.235:8081/basicInfo?fundId="+temp_Id).build();
                try{
                    Response response = okHttpClient.newCall(request).execute();
                    assert response.body() != null;
                    String data = response.body().string();
                    //把数据传入解析JSON数据方法
                    jsonJX(data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setUIInfo();
                        }
                    });

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

                JSONArray resultJsonArray = new JSONArray(removeBOM(data));
                //遍历
                for(int i=0;i<resultJsonArray.length();i++){
                    //JSON数据对象
                    jsobject=resultJsonArray.getJSONObject(i);

                    try {

                        FundInfoObject temp_fund;

                        //获取到json数据中的activity数组里的内容fundRisk
                        String temp_risk = jsobject.optString("fundRisk");
                        fundRisk = temp_risk;
                        Log.v("FundInfoActivity",fundRisk);

                        //获取到json数据中的activity数组里的内容fundNetweigh
                        String temp_netweigh = jsobject.getString("fundNetweigh");
                        fundNetweigh = temp_netweigh;
                        Log.v("FundInfoActivity",fundNetweigh);

                        String temp_name = jsobject.getString("fundName");
                        fundName = temp_name;
                        Log.v("FundInfoActivity",fundName);

                        String temp_type = jsobject.optString("fundType");
                        fundType = temp_type;
                        Log.v("FundInfoActivity",fundType);

                        String temp_incre = jsobject.getString("fundIncre");
                        fundIncre = temp_incre;
                        Log.v("FundInfoActivity",fundIncre);

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
    private void jsonJXFav(String data){
        //判断数据是空
        if(data!=null){
            try {

                JSONArray resultJsonArray = new JSONArray(removeBOM(data));
                //遍历
                for(int i=0;i<resultJsonArray.length();i++){
                    //JSON数据对象
                    jsobject=resultJsonArray.getJSONObject(i);
                    //此处将自选的基金放入数组中
                    String favFundId = jsobject.optString("fundId");
                    favoriteFundIds.add(favFundId);

                    String date = jsobject.optString("generationDate");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    //将获取的数据显示在UI界面上
    public void setUIInfo(){
        //其他信息放置在Textview中
        tv_id = findViewById(R.id.tv_fundInfo_id);
        tv_id.setText(fundId);

        tv_type = findViewById(R.id.tv_fundInfo_type);
        tv_type.setText(fundType);

        tv_risk = findViewById(R.id.tv_fundInfo_risk);
        tv_risk.setText(fundRisk);

        tv_netweigh = findViewById(R.id.tv_fundInfo_netweigh);
        tv_netweigh.setText(fundNetweigh);

        //基金名称与七日年化信息放置在大框体中
        tv1 = findViewById(R.id.tv_big_fundName);
        tv1.setText(fundName);

        tv2 = findViewById(R.id.tv_big_fundRate);
        tv2.setText(fundIncre);

        //设置日涨幅颜色
        if(fundIncre.charAt(0) == '+') {
            tv2.setTextColor(getResources().getColor(R.color.highlighttext));
        }
        else if(fundIncre.charAt(0) == '-') {
            tv2.setTextColor(getResources().getColor(R.color.decreColor));
        }
        else
        {
            tv2.setTextColor(getResources().getColor(R.color.subtitledarkercolor));
        }
    }

    private void setUIButton(){
        //先转换成Int值再转换成String消除补上的0
        final String temp_Id = simplyfyId(fundId);
        if(favoriteFundIds.contains(temp_Id)) {
            addOrRemoveFavButton.setText("取消自选");
            addOrRemoveFavButton.setBackgroundColor(getResources().getColor(R.color.exitcolor));
            addOrRemoveFavButton.setTextColor(getResources().getColor(R.color.titlecolor));
            favFlag = true;
        }else {
            addOrRemoveFavButton.setText("加入自选");
            favFlag = false;
            addOrRemoveFavButton.setBackgroundColor(getResources().getColor(R.color.subtitledarkercolor));
            addOrRemoveFavButton.setTextColor(getResources().getColor(R.color.black));
        }
        addOrRemoveFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favoriteFundIds.contains(temp_Id)){
                    favoriteFundIds.remove(temp_Id);
                    addOrRemoveFavButton.setText("加入自选");
                    addOrRemoveFavButton.setBackgroundColor(getResources().getColor(R.color.subtitledarkercolor));
                    addOrRemoveFavButton.setTextColor(getResources().getColor(R.color.black));
                }else{
                    favoriteFundIds.add(temp_Id);
                    addOrRemoveFavButton.setText("取消自选");
                    addOrRemoveFavButton.setBackgroundColor(getResources().getColor(R.color.exitcolor));
                    addOrRemoveFavButton.setTextColor(getResources().getColor(R.color.titlecolor));
                }
            }
        });
    }

    //保存自选信息
    private void saveFavFund() {
        //先转换成Int值再转换成String消除补上的0
        final String temp_Id = simplyfyId(fundId);
        if((favFlag&&favoriteFundIds.contains(temp_Id)||(!favFlag&&!favoriteFundIds.contains(temp_Id)))) {
            return;
        }else{
            //如果原来有，则为删除
            if(favFlag&&!favoriteFundIds.contains(temp_Id)) {
                //创建子线程发送网络请求
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        OkHttpClient okHttpClient = new OkHttpClient();

                        //服务器返回地址，填入请求数据参数
                        // TODO 传入的参数为用户id,基金id，以及是删除信息

                        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                        RequestBody body = RequestBody.create(mediaType,
                                "uuid=111&fundId="+temp_Id+"&option=delete&date="+ System.currentTimeMillis());
                        Request request = new Request.Builder()
                                .post(body)
                                .url("http://47.100.120.235:8081/collection").build();
                        try{
                            Response response = okHttpClient.newCall(request).execute();
                            assert response.body() != null;
                            String data = response.body().string();

                        }catch(IOException e){
                            e.printStackTrace();
                        }

                    }
                }).start();
            }else {
                //如果原来没有，则为添加
                //创建子线程发送网络请求
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        OkHttpClient okHttpClient = new OkHttpClient();

                        //服务器返回地址，填入请求数据参数
                        // TODO 传入的参数为用户id,基金id，以及是添加信息

                        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                        RequestBody body = RequestBody.create(mediaType,
                                "uuid=111&fundId="+temp_Id+"&option=add&date="+ System.currentTimeMillis());
                        Request request = new Request.Builder()
                                .post(body)
                                .url("http://47.100.120.235:8081/collection").build();
                        try{
                            Response response = okHttpClient.newCall(request).execute();
                            assert response.body() != null;
                            String data = response.body().string();

                        }catch(IOException e){
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        }
    }
    //跳转至详细信息界面
    public void jumpToDetailTest(){
        Intent intent = new Intent(FundInfoActivity.this,FundInfoDetailActivity.class);
        Bundle bundle = new Bundle();
        //Log.v("FundInfoActivity", fundId);
        bundle.putString("fundId",simplyfyId(fundId));
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public static final String removeBOM(String data) {
        if (TextUtils.isEmpty(data)) {
            return data;
        }
        if (data.startsWith("\ufeff")) {
            return data.substring(1);
        } else {
            return data;
        }
    }

    //将自动补0的Id号去首字符0
    public String simplyfyId(String id){
        String result = valueOf(Integer.parseInt(id));
        return result;
    }

    //添加自选按钮监听
    public void addToCart(){

    }

    //统一定义单击返回按键执行操作
    public void backToMain(View view){
        onBackPressed();
    }

}
