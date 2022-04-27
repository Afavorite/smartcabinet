package com.app2018212763.smartcabinet.Order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.app2018212763.smartcabinet.Bean.Box;
import com.app2018212763.smartcabinet.Bean.Order;
import com.app2018212763.smartcabinet.R;

import org.w3c.dom.Text;

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

//    @SuppressLint({"ViewHolder", "SetTextI18n"})
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,parent,false);
//        convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_order,parent,false);
//
//        TextView txt_order_number = (TextView) convertView.findViewById(R.id.text_show_order_number);
//        TextView txt_order_creator = (TextView) convertView.findViewById(R.id.text_show_order_creator);
//        TextView txt_order_box_number = (TextView) convertView.findViewById(R.id.text_show_order_box_number);
//        TextView txt_order_status = (TextView) convertView.findViewById(R.id.text_show_order_status);
//        TextView txt_order_temp = (TextView) convertView.findViewById(R.id.text_show_order_temp);
//        TextView txt_order_ster = (TextView) convertView.findViewById(R.id.text_show_order_ster);
//        TextView txt_order_start_time = (TextView) convertView.findViewById(R.id.text_show_order_start_time);
//        TextView txt_order_end_time = (TextView) convertView.findViewById(R.id.text_show_order_end_time);
//        Button btn_function = (Button) convertView.findViewById(R.id.btn_order_function);
//
//        String status, temp, ster, endtime, fuction;
//        // 状态
//        switch (mData.get(position).getOrder_status()){
//            case "using":
//            case "unlock":
//                status = "使用中";
//                break;
//            case "booking":
//                status = "预约中";
//                break;
//            default:
//                status = "已结束";
//        }
//
//        //温控
//        if (mData.get(position).getOrder_temp_switch().equals("on")){ temp = mData.get(position).getOrder_temp(); }
//        else{ temp = "未打开";}
//
//        //紫外线灯
//        if (mData.get(position).getOrder_sterilization().equals("on")){ ster = "开启"; }
//        else{ ster = "关闭"; }
//
//        //订单结束时间
//        if (mData.get(position).getOrder_status().equals("finish")){ endtime = mData.get(position).getOrder_end_time(); }
//        else endtime = "订单尚未结束";
//
//        //功能按钮
//        if (mData.get(position).getOrder_status().equals("finish")){ fuction = "删除记录"; }
//        else fuction = "结束订单";
//
//        txt_order_number.setText("订单号:"+mData.get(position).getOrder_number());
//        txt_order_creator.setText("新建者:"+mData.get(position).getOrder_creator());
//        txt_order_box_number.setText("箱柜号:"+mData.get(position).getOrder_box_number());
//        txt_order_status.setText("当前状态:"+status);
//        txt_order_temp.setText("当前温控:"+temp+"℃");
//        txt_order_ster.setText("当前杀菌:"+ster);
//        txt_order_start_time.setText("订单创建时间:"+mData.get(position).getOrder_create_time());
//        txt_order_end_time.setText("订单结束时间:"+endtime);
//        btn_function.setText(fuction);
//
//        return convertView;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item,
                    null);
            viewHolder.bt1 = (Button) convertView.findViewById(R.id.bt1);
            viewHolder.bt2 = (Button) convertView.findViewById(R.id.bt2);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bt1.setOnClickListener(this);
        viewHolder.bt2.setOnClickListener(this);
        viewHolder.bt1.setTag(position);
        viewHolder.bt2.setTag(position);
        viewHolder.tv.setText(mList.get(position));
        return convertView;
    }

    public final static class ViewHolder {
        Button bt1, bt2;
        TextView tv;
    }

    interface InnerItemOnclickListener {
        void itemClick(View v);
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener){
        this.mListener=listener;
    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }
}
