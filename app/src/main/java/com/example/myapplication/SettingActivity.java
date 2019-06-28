package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {
    private String[] names = new String[]{"密码管理", "安全保护问题", "手机令牌"};
    private String[] says = new String[]{"包含手势密码，指纹密码等", "已设置", "未开启"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);


//        //要显示的数据
//        String[] strs = {"基神","B神","翔神","曹神","J神"};
//        //创建ArrayAdapter
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                (this,android.R.layout.simple_expandable_list_item_1,strs);
//        //获取ListView对象，通过调用setAdapter方法为ListView设置Adapter设置适配器
//        ListView list_test = (ListView) findViewById(R.id.list_test);
//        list_test.setAdapter(adapter);

        final List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("name", names[i]);
            showitem.put("says", says[i]);
            listitem.add(showitem);
        }

        //创建一个simpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.base_list_item,
                new String[]{"name", "says"}, new int[]{R.id.name, R.id.says});
        final ListView listView = (ListView) findViewById(R.id.list_test);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String location = "位置："+listView.getItemIdAtPosition(position);
                Map<String, Object> item = (Map<String, Object>)listView.getItemAtPosition(position);
                String l = "   内容，"+item.get("name");
                Toast.makeText(SettingActivity.this, l  + location, Toast.LENGTH_SHORT).show();
                //Toast.makeText(SettingActivity.this, "我是onclick事件显示的", Toast.LENGTH_SHORT).show();
                }

        } );
    }

    public void backToMain(View view){
        Intent intent= new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}
