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
    // private Context mContext;
    private View.OnClickListener listener;

    public OrderAdapter(LinkedList<Order> mData, View.OnClickListener listener) {
        this.mData = mData;
        // this.mContext = mContext;
        this.listener = listener;
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
//        // ??????
//        switch (mData.get(position).getOrder_status()){
//            case "using":
//            case "unlock":
//                status = "?????????";
//                break;
//            case "booking":
//                status = "?????????";
//                break;
//            default:
//                status = "?????????";
//        }
//
//        //??????
//        if (mData.get(position).getOrder_temp_switch().equals("on")){ temp = mData.get(position).getOrder_temp(); }
//        else{ temp = "?????????";}
//
//        //????????????
//        if (mData.get(position).getOrder_sterilization().equals("on")){ ster = "??????"; }
//        else{ ster = "??????"; }
//
//        //??????????????????
//        if (mData.get(position).getOrder_status().equals("finish")){ endtime = mData.get(position).getOrder_end_time(); }
//        else endtime = "??????????????????";
//
//        //????????????
//        if (mData.get(position).getOrder_status().equals("finish")){ fuction = "????????????"; }
//        else fuction = "????????????";
//
//        txt_order_number.setText("?????????:"+mData.get(position).getOrder_number());
//        txt_order_creator.setText("?????????:"+mData.get(position).getOrder_creator());
//        txt_order_box_number.setText("?????????:"+mData.get(position).getOrder_box_number());
//        txt_order_status.setText("????????????:"+status);
//        txt_order_temp.setText("????????????:"+temp+"???");
//        txt_order_ster.setText("????????????:"+ster);
//        txt_order_start_time.setText("??????????????????:"+mData.get(position).getOrder_create_time());
//        txt_order_end_time.setText("??????????????????:"+endtime);
//        btn_function.setText(fuction);
//
//        return convertView;
//    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_order, null);
            viewHolder.txt_order_number = (TextView) convertView.findViewById(R.id.text_show_order_number);
            viewHolder.txt_order_creator = (TextView) convertView.findViewById(R.id.text_show_order_creator);
            viewHolder.txt_order_box_number = (TextView) convertView.findViewById(R.id.text_show_order_box_number);
            viewHolder.txt_order_status = (TextView) convertView.findViewById(R.id.text_show_order_status);
            viewHolder.txt_order_temp = (TextView) convertView.findViewById(R.id.text_show_order_temp);
            viewHolder.txt_order_ster = (TextView) convertView.findViewById(R.id.text_show_order_ster);
            viewHolder.txt_order_start_time = (TextView) convertView.findViewById(R.id.text_show_order_start_time);
            viewHolder.txt_order_end_time = (TextView) convertView.findViewById(R.id.text_show_order_end_time);
            viewHolder.btn_function = (Button) convertView.findViewById(R.id.btn_order_function);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.btn_function.setOnClickListener(listener);
        viewHolder.btn_function.setTag(R.id.tag_order_number, mData.get(position).getOrder_number());
        viewHolder.btn_function.setTag(R.id.tag_order_status, mData.get(position).getOrder_status());

        String status, temp, ster, endtime, fuction;
        // ??????
        switch (mData.get(position).getOrder_status()){
            case "using":
            case "unlock":
                status = "?????????";
                break;
            case "booking":
                status = "?????????";
                break;
            default:
                status = "?????????";
        }

        //??????
        if (mData.get(position).getOrder_temp_switch().equals("on")){ temp = mData.get(position).getOrder_temp(); }
        else{ temp = "?????????";}

        //????????????
        if (mData.get(position).getOrder_sterilization().equals("on")){ ster = "??????"; }
        else{ ster = "??????"; }

        //??????????????????
        if (mData.get(position).getOrder_status().equals("finish")){ endtime = mData.get(position).getOrder_end_time(); }
        else endtime = "??????????????????";

        //????????????
        if (mData.get(position).getOrder_status().equals("finish")){ fuction = "????????????"; }
        else fuction = "????????????";

        viewHolder.txt_order_number.setText("?????????:"+mData.get(position).getOrder_number());
        viewHolder.txt_order_creator.setText("?????????:"+mData.get(position).getOrder_creator());
        viewHolder.txt_order_box_number.setText("?????????:"+mData.get(position).getOrder_box_number());
        viewHolder.txt_order_status.setText("????????????:"+status);
        viewHolder.txt_order_temp.setText("????????????:"+temp+"???");
        viewHolder.txt_order_ster.setText("????????????:"+ster);
        viewHolder.txt_order_start_time.setText("??????????????????:"+mData.get(position).getOrder_create_time());
        viewHolder.txt_order_end_time.setText("??????????????????:"+endtime);
        viewHolder.btn_function.setText(fuction);
        return convertView;
    }

    public final static class ViewHolder {
        TextView txt_order_number;
        TextView txt_order_creator;
        TextView txt_order_box_number;
        TextView txt_order_status;
        TextView txt_order_temp;
        TextView txt_order_ster;
        TextView txt_order_start_time;
        TextView txt_order_end_time;
        Button btn_function;
    }
}
