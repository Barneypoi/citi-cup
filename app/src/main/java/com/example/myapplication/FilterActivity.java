package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.ArrayList;
import java.util.List;


public class FilterActivity extends AppCompatActivity {
    private Spinner spinner1, spinner2,spinner3,spinner4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);
        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        // 声明四个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                FilterActivity.this, android.R.layout.simple_spinner_item,
                getData());
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                FilterActivity.this, android.R.layout.simple_spinner_item,
                getData2());
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                FilterActivity.this, android.R.layout.simple_spinner_item,
                getData3());
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(
                FilterActivity.this, android.R.layout.simple_spinner_item,
                getData4());
        // 把定义好的Adapter设定到spinner中
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        spinner4.setAdapter(adapter4);
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // 在选中之后触发
                Toast.makeText(FilterActivity.this,
                        parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 这个一直没有触发，我也不知道什么时候被触发。
                //在官方的文档上说明，为back的时候触发，但是无效，可能需要特定的场景
            }
        });
     }

    private List<String> getData() {
        // 数据源
        List<String> dataList = new ArrayList<String>();
        dataList.add("1");
        dataList.add("2");
        dataList.add("3");
        dataList.add("4");
        return dataList;
    }

    private List<String> getData2() {
        // 数据源
        List<String> dataList = new ArrayList<String>();
        dataList.add("a");
        dataList.add("b");
        dataList.add("c");
        dataList.add("d");
        return dataList;
    }

    private List<String> getData3() {
        // 数据源
        List<String> dataList = new ArrayList<String>();
        dataList.add("q");
        dataList.add("w");
        dataList.add("e");
        dataList.add("r");
        return dataList;
    }

    private List<String> getData4() {
        // 数据源
        List<String> dataList = new ArrayList<String>();
        dataList.add("!");
        dataList.add("@");
        dataList.add("#");
        dataList.add("$");
        return dataList;
    }

    public void backToMain(View view){
        Intent intent= new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
