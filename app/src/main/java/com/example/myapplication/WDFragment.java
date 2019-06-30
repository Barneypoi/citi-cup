package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WDFragment extends Fragment{
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my, container, false);
        initMyListView();
        return view;
    }

    //我的界面的列表按钮初始化
    private void initMyListView() {
        String[] titles1 = new String[]{"联系客服", "意见反馈", "常见问题"};
        String[] subtitles1 = new String[]{"", "", ""};
        final List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < titles1.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("titles1", titles1[i]);
            showitem.put("subtitles1", subtitles1[i]);
            listitem.add(showitem);
        }

        //创建一个simpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(view.getContext(),listitem, R.layout.listitem_setting,
                new String[]{"titles1", "subtitles1"}, new int[]{R.id.name, R.id.says});
        final ListView listView = (ListView) view.findViewById(R.id.myListView);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String location = "位置："+listView.getItemIdAtPosition(position);
                Map<String, Object> item = (Map<String, Object>)listView.getItemAtPosition(position);
                String l = "   内容，"+item.get("titles1");
                Toast.makeText(WDFragment.super.getContext(), l  + location, Toast.LENGTH_SHORT).show();
                //Toast.makeText(SettingActivity.this, "我是onclick事件显示的", Toast.LENGTH_SHORT).show();
            }

        } );
    }
}
