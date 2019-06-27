package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FundInfoActivity extends Activity {

    private ListView lv;
    private TextView tv1;
    private TextView tv2;
    private BaseAdapter baseAdapter;
    private String str;

    //改变该条目数据对象内容，将数据显示在ListView中
    private List<Integer> dataList = new ArrayList<Integer>();//实体类

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundinfo);

        lv.findViewById(R.id.lv_fundinfo);

        for (int i = 0; i < 5; i++) {
            Integer integer = new Integer(i);//给实体类赋值
            dataList.add(integer);
        }

        baseAdapter = new BaseAdapter() {
            //获取条目数目,测试数据为5
            int number = 5;
            @Override
            public int getCount() {
                return number;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = FundInfoActivity.this.getLayoutInflater();
                View view;

                if(convertView == null){
                    view = inflater.inflate(R.layout.listitem,null);
                }else{
                    view = convertView;
                    Log.i("info","有缓存，不需要重新生成"+position);
                }

                tv1.findViewById(R.id.tv1_list);
                tv1.setText(dataList.get(position));

                tv2.findViewById(R.id.tv2_list);
                tv2.setText(dataList.get(position));
                return view;
            }
            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

        };
        lv.setAdapter(baseAdapter);


        //获取当前ListView点击的行数，并且得到该数据
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                tv1 = view.findViewById(R.id.tv1_list);//找到Textviewname
                str = tv1.getText().toString();//得到数据
                Toast.makeText(FundInfoActivity.this, "" + str, Toast.LENGTH_SHORT).show();//显示数据
            }
        });

    }
}
