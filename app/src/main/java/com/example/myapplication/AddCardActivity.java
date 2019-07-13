package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class AddCardActivity extends AppCompatActivity {
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card);
        title = findViewById(R.id.title_tv);
        title.setText("我的银行卡");

    }
    //头部按钮相应事件：统一定义单击返回按键执行操作
    public void backToMain(View view){
        onBackPressed();
    }

}
