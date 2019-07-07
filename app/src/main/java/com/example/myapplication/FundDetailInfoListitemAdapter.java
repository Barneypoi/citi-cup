package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class FundDetailInfoListitemAdapter extends ArrayAdapter<FundDetailInfoObject> {
    private int resourceId;
    public FundDetailInfoListitemAdapter(@NonNull Context context, int resource, @NonNull List<FundDetailInfoObject> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FundDetailInfoObject funddetinfo = getItem(position);
        //获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        /*
        TextView tv1 = view.findViewById(R.id.tv1_list);
        TextView tv2 = view.findViewById(R.id.tv2_list);

        tv1.setText(funddetinfo.getInfoName());
        tv2.setText(funddetinfo.getInfoContent());*/
        return view;
    }
}
