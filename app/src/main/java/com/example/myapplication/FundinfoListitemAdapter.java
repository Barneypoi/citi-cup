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

public class FundinfoListitemAdapter extends ArrayAdapter<FundInfoObject> {
    private int resourceId;

    public FundinfoListitemAdapter(@NonNull Context context, int resource, @NonNull List<FundInfoObject> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FundInfoObject fundinfo = getItem(position);
        //获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        //listitem_fundinfo
        TextView tv1 = view.findViewById(R.id.tv1_src_list);
        TextView tv2 = view.findViewById(R.id.tv2_src_list);

        tv1.setText(fundinfo.getFundName());
        tv2.setText(convertId(fundinfo.getFundId()));
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
