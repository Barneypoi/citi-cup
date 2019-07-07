package com.example.myapplication;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.math.BigDecimal;

public class DetailsMarkerView extends MarkerView {

    private TextView mTvMonth;
    private TextView mTvChart1;
    public static String a;

    public DetailsMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mTvMonth = findViewById(R.id.textView);
        mTvChart1 = findViewById(R.id.textView2);

    }

    //每次重绘，会调用此方法刷新数据
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        try {
            //信息
            if (e.getY() == 0) {
                mTvChart1.setText("暂无数据");
            } else {
                mTvMonth.setText(concat(e.getX(),"日期："));
                mTvChart1.setText(concat(e.getY(), "净值："));
            }
            long b =86400000*(long)e.getX()+Long.parseLong(a);
            String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(b));
            mTvMonth.setText(date);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        super.refreshContent(e, highlight);
    }

    //布局的偏移量。就是布局显示在圆点的那个位置
    // -(width / 2) 布局水平居中
    //-(height) 布局显示在圆点上方
    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    public String concat(float money, String values) {
        return values + new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
    }
}
