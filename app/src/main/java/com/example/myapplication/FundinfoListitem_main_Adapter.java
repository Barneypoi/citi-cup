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
        tv2.setText(fundinfo.getFundInfo());
        tv3.setText(fundinfo.getFundOtherInfo1());
        tv4.setText(fundinfo.getFundOtherInfo2());
        return view;
    }
}
