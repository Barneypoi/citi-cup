package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SYFragment extends Fragment {

    //JSON数据对象
    public JSONObject object;
    //接受基金信息的TextView tv1为基金名称 tv2为七日年化收益率 tv3，tv4为其他可选添加信息
    private TextView tv1, tv2, tv3, tv4;

    private FundinfoListitem_main_Adapter baseAdapter;

    private ListView lv;

    //标识当前单击单元格的基金名称
    private String fundName_onclick;

    //从服务器获取的信息
    private String fundName, fundId, fundType, fundRisk, fundIncre;

    //推荐基金名称接口，用于储存推荐基金的名称
    private ArrayList<String> fundIdList = new ArrayList<>();
    //改变该条目数据对象内容，将数据显示在ListView中
    private ArrayList<FundInfoObject> fundInfoList = new ArrayList<>();

    //字符串到JSON对象的映射
    public ArrayList<Map<String, Object>> mainlist = new ArrayList<>();

    private View view;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1, container, false);

        lv = view.findViewById(R.id.lv_mainwin);

        //block_fund_main的两个TextView
        //设置暂时未确定
        tv1 = view.findViewById(R.id.tv_big_fundName);
        tv1.setText("国富潜力组合混合A");
        tv2 = view.findViewById(R.id.tv_big_fundRate);
        tv2.setText("5.29%");


        //测试数据
        fundIdList.add("136");
        fundIdList.add("333");
        fundIdList.add("165");
        fundIdList.add("173");
        fundIdList.add("45");
        fundIdList.add("72");

        //initFundInfo();
        initConnection();

        return view;
    }

    //建立服务器连接，获取当前基金的基本信息并生成基本信息
    public void initConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();

                //获取推荐基金名称列表
                String str_fundIdList = "";
                for (int i = 0; i < fundIdList.size(); i++) {
                    if (i == 0)
                        str_fundIdList += fundIdList.get(i);
                    else
                        str_fundIdList += ("@" + fundIdList.get(i));
                }

                //服务器返回地址，填入请求数据参数
                //传入的参数为全部推荐基金的名称，若无推荐基金，则返回值传入参数为空字符串""
                Request request = new Request.Builder()
                        .get()
                        .url("http://47.100.120.235:8081/mainInfo?fundId=" + str_fundIdList).build();

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
                JSONArray resultJsonArray = new JSONArray(removeBOM(data));
                //遍历
                for (int i = 0; i < resultJsonArray.length(); i++) {
                    //JSON数据对象
                    object = resultJsonArray.getJSONObject(i);
                    try {
                        FundInfoObject temp_fund;

                        //获取到json数据中的activity数组里的内容fundId
                        String temp_name = object.getString("fundName");
                        fundName = temp_name;
                        Log.v(getActivity().toString(), fundName);

                        //获取到json数据中的activity数组里的内容fundId
                        String temp_id = object.getString("fundId");
                        fundId = temp_id;
                        Log.v(getActivity().toString(), fundId);

                        //获取到json数据中的activity数组里的内容fundType
                        String temp_type = object.optString("fundType");
                        fundType = temp_type;
                        Log.v(getActivity().toString(), fundType);

                        //获取到json数据中的activity数组里的内容fundIncre
                        String temp_incre = object.getString("fundIncre");
                        fundIncre = temp_incre;
                        Log.v(getActivity().toString(), fundIncre);

                        //获取到json数据中的activity数组里的内容fundRisk
                        String temp_risk = object.getString("fundRisk");
                        fundRisk = temp_risk;
                        Log.v(getActivity().toString(), fundRisk);


                        //创建基金信息对象
                        temp_fund = new FundInfoObject(fundName, fundIncre, fundId, fundType, fundRisk);
                        fundInfoList.add(temp_fund);

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

    public void createList() {

        //初始化ListView展示基金信息
        baseAdapter = new FundinfoListitem_main_Adapter(getContext(), R.layout.listitem_mainwin, fundInfoList);
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

                Intent i = new Intent(getContext(), FundInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fundName", fundName);
                bundle.putString("fundIncre", fundIncre);
                bundle.putString("fundId", fundId);
                //bundle.putString("fundRisk",fundRisk);
                bundle.putString("fundType", fundType);

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