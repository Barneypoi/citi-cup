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

//设置界面
public class SettingActivity extends AppCompatActivity {

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        title = findViewById(R.id.title_tv);
        title.setText("账户设置");
        initUserInfoList();
        initList1();
        initList2();
        initList3();


    }

    private void initUserInfoList(){
        int[] images = new int[]{R.drawable.image10};
        String[] titles1 = new String[]{"GKD"};
        String[] subtitles1 = new String[]{"99XXXXXXXX001"};
        String[] subtitles2 = new String[]{"去绑卡"};
        final List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < titles1.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("image",images[i]);
            showitem.put("titles1", titles1[i]);
            showitem.put("subtitles1", subtitles1[i]);
            showitem.put("subtitles2",subtitles2[i]);
            listitem.add(showitem);
        }

        //创建一个simpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.listitem_settinguserinfo,
                new String[]{"image","titles1", "subtitles1","subtitles2"}, new int[]{R.id.imageView5, R.id.textView7,R.id.textView8,R.id.textView9});
        final ListView listView = (ListView) findViewById(R.id.list_userinfo);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String location = "位置："+listView.getItemIdAtPosition(position);
                Map<String, Object> item = (Map<String, Object>)listView.getItemAtPosition(position);
                String l = "   内容，"+item.get("titles1");
                Toast.makeText(SettingActivity.this, l  + location, Toast.LENGTH_SHORT).show();
                jumpToUserInfo(view);
                //Toast.makeText(SettingActivity.this, "我是onclick事件显示的", Toast.LENGTH_SHORT).show();
            }

        } );
    }
    private void initList3() {
        String[] titles1 = new String[]{"密码管理","安全保护问题","手机令牌"};
        String[] subtitles1 = new String[]{"可设置微信、短信等方式","微信关注“陆金所微服务”",""};
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
        final ListView listView = (ListView) findViewById(R.id.list_test_3);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String location = "位置："+listView.getItemIdAtPosition(position);
                Map<String, Object> item = (Map<String, Object>)listView.getItemAtPosition(position);
                String l = "   内容，"+item.get("titles1");
                Toast.makeText(SettingActivity.this, l  + location, Toast.LENGTH_SHORT).show();
                //Toast.makeText(SettingActivity.this, "我是onclick事件显示的", Toast.LENGTH_SHORT).show();
            }

        } );
    }
    private void initList2() {
        String[] titles1 = new String[]{"风险承受能力评估"};
        String[] subtitles1 = new String[]{"保守型（默认）\n主动评估更准确"};
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
        final ListView listView = (ListView) findViewById(R.id.list_test_2);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String location = "位置："+listView.getItemIdAtPosition(position);
                Map<String, Object> item = (Map<String, Object>)listView.getItemAtPosition(position);
                String l = "   内容，"+item.get("titles1");
                Toast.makeText(SettingActivity.this, l  + location, Toast.LENGTH_SHORT).show();
                //Toast.makeText(SettingActivity.this, "我是onclick事件显示的", Toast.LENGTH_SHORT).show();
            }

        } );
    }

    private void initList1() {
        String[] titles1 = new String[]{"密码管理", "安全保护问题", "手机令牌"};
        String[] subtitles1 = new String[]{"包含手势密码，指纹密码等", "已设置", "未开启"};
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
        final ListView listView = (ListView) findViewById(R.id.list_test_1);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String location = "位置："+listView.getItemIdAtPosition(position);
                Map<String, Object> item = (Map<String, Object>)listView.getItemAtPosition(position);
                String l = "   内容，"+item.get("titles1");
                Toast.makeText(SettingActivity.this, l  + location, Toast.LENGTH_SHORT).show();
                //Toast.makeText(SettingActivity.this, "我是onclick事件显示的", Toast.LENGTH_SHORT).show();
            }

        } );
    }

    //跳转到设置界面
    public void jumpToUserInfo(View view) {
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
    }
    //统一定义单击返回按键执行操作
    public void backToMain(View view){
        onBackPressed();
    }

}
