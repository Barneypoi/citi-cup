package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        title = findViewById(R.id.title_tv);
        title.setText("个人信息");

        initList1();
        initList2();
        initList3();
        initList4();


    }

    private void initList1(){
        int[] images = new int[]{R.drawable.image10};
        String[] titles1 = new String[]{"安全头像"};
        final List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < titles1.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("image",images[i]);
            showitem.put("titles1", titles1[i]);
            listitem.add(showitem);
        }

        //创建一个simpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.listitem_usertouxiang,
                new String[]{"image","titles1"}, new int[]{R.id.imageTouXiang, R.id.safeTouXiang});
        final ListView listView = (ListView) findViewById(R.id.list_userinfo_1);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String location = "位置："+listView.getItemIdAtPosition(position);
                Map<String, Object> item = (Map<String, Object>)listView.getItemAtPosition(position);
                String l = "   内容，"+item.get("titles1");
                Toast.makeText(UserInfoActivity.this, l  + location, Toast.LENGTH_SHORT).show();
                //Toast.makeText(SettingActivity.this, "我是onclick事件显示的", Toast.LENGTH_SHORT).show();
            }

        } );
    }

    private void initList2(){
        String[] titles1 = new String[]{"用户名","手机号码","身份证"};
        String[] subtitles1 = new String[]{"GKD","182****1963","＊架兔(************5546)"};
        final List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < titles1.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("titles1", titles1[i]);
            showitem.put("subtitles1",subtitles1[i]);
            listitem.add(showitem);
        }

        //创建一个simpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.listitem_userinfo,
                new String[]{"titles1","subtitles1"}, new int[]{R.id.key, R.id.value});
        final ListView listView = (ListView) findViewById(R.id.list_userinfo_2);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String location = "位置："+listView.getItemIdAtPosition(position);
                Map<String, Object> item = (Map<String, Object>)listView.getItemAtPosition(position);
                String l = "   内容，"+item.get("titles1");
                Toast.makeText(UserInfoActivity.this, l  + location, Toast.LENGTH_SHORT).show();
                //Toast.makeText(SettingActivity.this, "我是onclick事件显示的", Toast.LENGTH_SHORT).show();
            }

        } );
    }

    private void initList3() {
        String[] titles1 = new String[]{"我的银行卡"};
        String[] subtitles1 = new String[]{"未绑卡"};
        final List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < titles1.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("titles1", titles1[i]);
            showitem.put("subtitles1", subtitles1[i]);
            listitem.add(showitem);
        }

        //创建一个simpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.listitem_setting,
                new String[]{"titles1", "subtitles1"}, new int[]{R.id.name, R.id.says});
        final ListView listView = (ListView) findViewById(R.id.list_userinfo_3);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String location = "位置："+listView.getItemIdAtPosition(position);
                Map<String, Object> item = (Map<String, Object>)listView.getItemAtPosition(position);
                String l = "   内容，"+item.get("titles1");
                Toast.makeText(UserInfoActivity.this, l  + location, Toast.LENGTH_SHORT).show();
                jumpToAddCard(view);
                //Toast.makeText(SettingActivity.this, "我是onclick事件显示的", Toast.LENGTH_SHORT).show();
            }

        } );
    }


    private void initList4() {
        String[] titles1 = new String[]{"我的地址"};
        String[] subtitles1 = new String[]{""};
        final List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < titles1.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("titles1", titles1[i]);
            showitem.put("subtitles1", subtitles1[i]);
            listitem.add(showitem);
        }

        //创建一个simpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.listitem_setting,
                new String[]{"titles1", "subtitles1"}, new int[]{R.id.name, R.id.says});
        final ListView listView = (ListView) findViewById(R.id.list_userinfo_4);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String location = "位置："+listView.getItemIdAtPosition(position);
                Map<String, Object> item = (Map<String, Object>)listView.getItemAtPosition(position);
                String l = "   内容，"+item.get("titles1");
                Toast.makeText(UserInfoActivity.this, l  + location, Toast.LENGTH_SHORT).show();
                //Toast.makeText(SettingActivity.this, "我是onclick事件显示的", Toast.LENGTH_SHORT).show();
            }

        } );
    }

    //跳转到设置界面
    public void jumpToAddCard(View view) {
        Intent intent = new Intent(this, AddCardActivity.class);
        startActivity(intent);
    }

    //统一定义单击返回按键执行操作
    public void backToMain(View view){
        onBackPressed();
    }
}
