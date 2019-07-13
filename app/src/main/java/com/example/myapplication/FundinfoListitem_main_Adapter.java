package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

//用于在首页显示基金的Adapter
public class FundinfoListitem_main_Adapter extends ArrayAdapter<FundInfoObject> {
    private int resourceId;

    public FundinfoListitem_main_Adapter(@NonNull Context context, int resource, @NonNull List<FundInfoObject> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FundInfoObject fundinfo = getItem(position);
        //获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView tv1 = view.findViewById(R.id.tv_mainwin_fundName);
        TextView tv2 = view.findViewById(R.id.tv_mainwin_fundRate);
        TextView tv3 = view.findViewById(R.id.tv_mainwin_info1);
        TextView tv4 = view.findViewById(R.id.tv_mainwin_info2);

        tv1.setText(fundinfo.getFundName());
        tv2.setText(fundinfo.getFundIncre());
        tv3.setText(convertId(fundinfo.getFundId()));
        tv4.setText(fundinfo.getFundType());

        //设置日涨幅颜色
        if(fundinfo.getFundIncre().charAt(0) == '+') {
            tv2.setTextColor(getContext().getColor(R.color.highlighttext));
        }
        else if(fundinfo.getFundIncre().charAt(0) == '-') {
            tv2.setTextColor(getContext().getColor(R.color.decreColor));
        }
        else
        {
            tv2.setTextColor(getContext().getColor(R.color.subtitledarkercolor));
        }
        return view;
    }

    public String convertId(String orig_id){
        int orig_length = orig_id.length();
        int result_length = 6 - orig_length;
        String zero ="";
        for(int i=0;i<result_length;i++){
            zero += "0";
        }
        return zero + orig_id ;
    }

}
