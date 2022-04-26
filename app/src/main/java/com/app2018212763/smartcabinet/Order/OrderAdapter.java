package com.app2018212763.smartcabinet.Order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app2018212763.smartcabinet.Bean.Box;
import com.app2018212763.smartcabinet.Bean.Order;
import com.app2018212763.smartcabinet.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class OrderAdapter extends BaseAdapter {
    private LinkedList<Order> mData;
    private Context mContext;

    public OrderAdapter(LinkedList<Order> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,parent,false);
        TextView txt_order_number = (TextView) convertView.findViewById(android.R.id.text1);
        txt_order_number.setText(mData.get(position).getOrder_number());
        return convertView;
    }
}
