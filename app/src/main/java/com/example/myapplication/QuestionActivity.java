package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

//问卷调查界面
public class QuestionActivity extends Activity {

    RadioGroup rg1,rg2,rg3,rg4,rg5,rg6,rg7,rg8,rg9,rg10,rg11;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11;
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        initRadioGroup();
        initRadioListener();
        //notEmpty();
        initSubmit();
    }

    public void initRadioGroup(){
        rg1 = findViewById(R.id.rg_yearold);
        rg2 = findViewById(R.id.rg_sex);
        rg3 = findViewById(R.id.rg_edu);
        rg4 = findViewById(R.id.rg_district);
        rg5 = findViewById(R.id.rg_financial);
        rg6 = findViewById(R.id.rg_investRatio);
        rg7 = findViewById(R.id.rg_investYear);
        rg8 = findViewById(R.id.rg_intention);
        rg9 = findViewById(R.id.rg_experience);
        rg10 = findViewById(R.id.rg_return);
        rg11 = findViewById(R.id.rg_consideration);

    }

    public void initRadioListener(){
        rg1.setOnCheckedChangeListener(new RadioButtonListener_1());
        rg2.setOnCheckedChangeListener(new RadioButtonListener_2());
        rg3.setOnCheckedChangeListener(new RadioButtonListener_3());
        rg4.setOnCheckedChangeListener(new RadioButtonListener_4());
        rg5.setOnCheckedChangeListener(new RadioButtonListener_5());
        rg6.setOnCheckedChangeListener(new RadioButtonListener_6());
        rg7.setOnCheckedChangeListener(new RadioButtonListener_7());
        rg8.setOnCheckedChangeListener(new RadioButtonListener_8());
        rg9.setOnCheckedChangeListener(new RadioButtonListener_9());
        rg10.setOnCheckedChangeListener(new RadioButtonListener_10());
        rg11.setOnCheckedChangeListener(new RadioButtonListener_11());
    }

    public boolean notEmpty(){
        if(s1==null||s2==null||s3==null||s4==null||s5==null||s6==null||s7==null||s8==null||s9==null||s10==null||s11==null){
            return false;
        }
        else
            return true;
    }

    public void initSubmit(){
        btn = findViewById(R.id.btn_question);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //存数据库操作在此完成
                /*
                if(notEmpty()==true){
                    Toast.makeText(QuestionActivity.this,"感谢您的配合！欢迎使用citiFund！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(QuestionActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(QuestionActivity.this,"请完成问卷调查，谢谢配合！",Toast.LENGTH_SHORT).show();
                */
                Intent intent = new Intent(QuestionActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    class RadioButtonListener_1 implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = findViewById(checkedId);
            s1 = rb.getText().toString();
        }
    }
    class RadioButtonListener_2 implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = findViewById(checkedId);
            s2 = rb.getText().toString();
        }
    }
    class RadioButtonListener_3 implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = findViewById(checkedId);
            s3 = rb.getText().toString();
        }
    }
    class RadioButtonListener_4 implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = findViewById(checkedId);
            s4 = rb.getText().toString();
            Toast.makeText(QuestionActivity.this, "s1的值是" + s1, Toast.LENGTH_SHORT).show();
        }
    }
    class RadioButtonListener_5 implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = findViewById(checkedId);
            s5 = rb.getText().toString();
        }
    }
    class RadioButtonListener_6 implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = findViewById(checkedId);
            s6 = rb.getText().toString();
        }
    }
    class RadioButtonListener_7 implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = findViewById(checkedId);
            s7 = rb.getText().toString();
        }
    }
    class RadioButtonListener_8 implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = findViewById(checkedId);
            s8 = rb.getText().toString();
        }
    }
    class RadioButtonListener_9 implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = findViewById(checkedId);
            s9 = rb.getText().toString();
        }
    }
    class RadioButtonListener_10 implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = findViewById(checkedId);
            s10 = rb.getText().toString();
        }
    }
    class RadioButtonListener_11 implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = findViewById(checkedId);
            s11 = rb.getText().toString();
        }
    }

    class UserQuestion{
        private String age,sex,edu,district,financial, investRatio,investPeriod,intention, experience, profit,consider;

        public UserQuestion(String age,String sex,String edu,String district,String financial,String investRatio,String investPeriod,String intention, String experience, String profit,String consider){
            this.age = age;
            this.sex = sex;
            this.edu = edu;
            this.district = district;
            this.financial = financial;
            this.investRatio = investRatio;
            this.investPeriod = investPeriod;
            this.intention = intention ;
            this.experience = experience;
            this.profit = profit;
            this.consider = consider;
        }

    }


}
