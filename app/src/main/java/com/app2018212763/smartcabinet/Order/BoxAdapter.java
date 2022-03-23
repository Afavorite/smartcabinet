package com.app2018212763.smartcabinet.Order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app2018212763.smartcabinet.Bean.Box;
import com.app2018212763.smartcabinet.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class BoxAdapter extends BaseAdapter {
    private LinkedList<Box> mData;
    private Context mContext;

    public BoxAdapter(LinkedList<Box> mData, Context mContext) {
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
        convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_single_choice,parent,false);
        TextView txt_box_number = (TextView) convertView.findViewById(android.R.id.text1);
        txt_box_number.setText(mData.get(position).getBox_number());
        return convertView;
    }
}
