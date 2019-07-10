package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ZXFragment extends Fragment{

    //JSON数据对象
    public JSONObject object;
    //接受基金信息的TextView tv1为基金名称 tv2为七日年化收益率 tv3，tv4为其他可选添加信息
    private TextView tv1, tv2, tv3, tv4;

    private FundNetInfoAdapter baseAdapter;

    private ListView lv;

    //标识当前单击单元格的基金名称
    private String fundName_onclick;

    //从服务器获取的信息
    private String fundName, fundId, fundType, fundRisk, fundIncre, fundNetweigh;

    //推荐基金名称接口，用于储存推荐基金的名称
    private ArrayList<String> fundNameList = new ArrayList<>();
    //改变该条目数据对象内容，将数据显示在ListView中
    private ArrayList<FundInfoObject> fundInfoList = new ArrayList<>();

    private View view;

    //用于储存该用户自选基金Id的数组
    private ArrayList<String> favoriteFundIds = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2, container, false);
        lv = (ListView) view.findViewById(R.id.lv_favourite);
        //此用于连接数据库 得到该用户的自选信息
        //initFavConnection();
        TextView title = view.findViewById(R.id.title_tv);
        title.setText("自选基金");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initFavConnection();
    }

    private void initFavConnection(){
        favoriteFundIds.clear();
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

                }catch(IOException e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

    //建立服务器连接，获取当前基金的基本信息并生成基本信息
    public void initConnection() {
        fundInfoList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();

                for(int i = 0;i< favoriteFundIds.size();i++) {
                    String fundIdForConnect = favoriteFundIds.get(i);
                    //服务器返回地址，填入请求数据参数
                    //传入的参数为全部推荐基金的名称，若无推荐基金，则返回值传入参数为空字符串""
                    //TODO 访问显示自选基金的API
                    Request request = new Request.Builder()
                            .get()
                            .url("http://47.100.120.235:8081/basicInfo?fundId=" + fundIdForConnect).build();

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

                        //TODO 依照API给的数据修改这里的东西
                        //获取到json数据中的activity数组里的内容fundId
                        String temp_fundId = object.optString("fundId");
                        fundId = temp_fundId;

                        //获取到json数据中的activity数组里的内容fundName
                        String temp_name = object.getString("fundName");
                        fundName = temp_name;

                        //获取到json数据中的activity数组里的内容fundNetWeigh
                        String temp_fundNetWeigh = object.getString("fundNetweigh");
                        fundNetweigh = temp_fundNetWeigh;


                        //获取到json数据中的activity数组里的内容fundIncre
                        String temp_incre = object.getString("fundIncre");
                        fundIncre = temp_incre;


                        //创建基金信息对象
                        temp_fund = new FundInfoObject(fundName, fundId, fundNetweigh,fundIncre);
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
                getActivity().runOnUiThread(
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

    private void jsonJXFav(String data){
        //判断数据是空
        if(data!=null){
            try {

                JSONArray resultJsonArray = new JSONArray(removeBOM(data));
                //遍历
                for(int i=0;i<resultJsonArray.length();i++){
                    //JSON数据对象
                    object=resultJsonArray.getJSONObject(i);
                    //此处将自选的基金放入数组中
                    String favFundId = object.optString("fundId");
                    favoriteFundIds.add(favFundId);
                }
                initConnection();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    public void createList() {

        //TODO 这里应该修改改成
        //初始化ListView展示基金信息
        baseAdapter = new FundNetInfoAdapter(getContext(), R.layout.listitem_net, fundInfoList);
        lv.setAdapter(baseAdapter);

        //获取当前ListView点击的行数，并且得到该数据信息
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //TODO 这个地方应该修改成 用fundInfoArraylist来传递数据
                tv1 = view.findViewById(R.id.netListFundName);
                fundName = tv1.getText().toString();//得到数据

                tv2 = view.findViewById(R.id.netListFundId);
                fundId = tv2.getText().toString();

                tv3 = view.findViewById(R.id.netListFundNetWeight);
                fundNetweigh = tv3.getText().toString();

                tv4 = view.findViewById(R.id.netListFundIncre);
                fundIncre = tv4.getText().toString();

                Intent i = new Intent(getContext(), FundInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fundName", fundName);
                bundle.putString("fundIncre", fundIncre);
                bundle.putString("fundId", fundId);
                bundle.putString("fundNetweigh", fundNetweigh);

                i.putExtras(bundle);
                startActivity(i);

                Toast.makeText(getContext(), "" + fundName, Toast.LENGTH_SHORT).show();//显示数据
            }
        });
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


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

}
