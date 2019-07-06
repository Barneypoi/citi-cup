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

public class NetListitemAdapter extends ArrayAdapter<FundInfoObject> {
    private int resourceId;

    public NetListitemAdapter(@NonNull Context context, int resource, @NonNull List<FundInfoObject> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FundInfoObject fundinfo = getItem(position);
        //获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView tv1 = view.findViewById(R.id.netListFundName);
        TextView tv2 = view.findViewById(R.id.netListFundNetWeight);
        TextView tv3 = view.findViewById(R.id.netListFundIncre);

        tv1.setText(fundinfo.getFundName());
        tv2.setText(fundinfo.getFundNetweigh());
        tv3.setText(fundinfo.getFundIncre());
        return view;
    }
}
