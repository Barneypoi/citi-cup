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

public class FundNetInfoAdapter extends ArrayAdapter<FundInfoObject> {
    private int resourceId;

    public FundNetInfoAdapter(@NonNull Context context, int resource, @NonNull List<FundInfoObject> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        //获取当前项的实例
        FundInfoObject fundinfo = getItem(position);

        TextView tv1 = view.findViewById(R.id.netListFundName);
        TextView tv2 = view.findViewById(R.id.netListFundId);
        TextView tv3 = view.findViewById(R.id.netListFundNetWeight);
        TextView tv4 = view.findViewById(R.id.netListFundIncre);

        tv1.setText(fundinfo.getFundName());
        tv2.setText(convertId(fundinfo.getFundId()));
        tv3.setText(fundinfo.getFundNetweigh());
        tv4.setText(fundinfo.getFundIncre());

        //设置日涨幅颜色
        if(fundinfo.getFundIncre().charAt(0) == '+') {
            tv4.setTextColor(getContext().getColor(R.color.highlighttext));
        }
        else if(fundinfo.getFundIncre().charAt(0) == '-') {
            tv4.setTextColor(getContext().getColor(R.color.decreColor));
        }
        else
        {
            tv4.setTextColor(getContext().getColor(R.color.subtitledarkercolor));
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
