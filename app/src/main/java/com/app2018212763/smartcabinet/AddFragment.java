package com.app2018212763.smartcabinet;

import static com.app2018212763.smartcabinet.Function.Settime.showDatePickerDialog;
import static com.app2018212763.smartcabinet.Function.Settime.showTimePickerDialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddFragment extends Fragment {


    //控件
    private TextView text_order_creator;
    private TextView text_order_boxnumber;
    private TextView text_order_startdate;
    private TextView text_order_starttime;
    private TextView text_order_enddate;
    private TextView text_order_endtime;
    private TextView text_temp;
    private SeekBar seekbar_temp;
    private Switch aSwitch_ster;

    //订单数据
    private String temp;
    private String ster;

    DateFormat format= DateFormat.getDateTimeInstance();
    Calendar calendar= Calendar.getInstance(Locale.CHINA);

    public AddFragment() {
        // Required empty public constructor
    }

    public void bindview(){
        text_order_creator = (TextView) getActivity().findViewById(R.id.text_order_creator);
        text_order_boxnumber = (TextView) getActivity().findViewById(R.id.text_order_boxnumber);
        text_order_startdate = (TextView) getActivity().findViewById(R.id.text_order_startdate);
        text_order_starttime = (TextView) getActivity().findViewById(R.id.text_order_starthour);
        text_order_enddate = (TextView) getActivity().findViewById(R.id.text_order_enddate);
        text_order_endtime = (TextView) getActivity().findViewById(R.id.text_order_endhour);
        text_temp = (TextView) getActivity().findViewById(R.id.text_temp);
        seekbar_temp = (SeekBar) getActivity().findViewById(R.id.seekBar_temp);
        aSwitch_ster = (Switch) getActivity().findViewById(R.id.switch_sterilization);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindview();
        clicklistener();
        setOrdercreator();
    }

    public void clicklistener(){
        text_order_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(getActivity(),  2, text_order_startdate, calendar);
            }
        });
        text_order_starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(getActivity(),2, text_order_starttime, calendar);
            }
        });
        text_order_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(getActivity(),  2, text_order_enddate, calendar);
            }
        });
        text_order_endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(getActivity(),2, text_order_endtime, calendar);
            }
        });
        seekbar_temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                text_temp.setText("箱柜设置温度:" + progress + "  / 100 ");
                temp = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(getActivity(), "当前值"+temp, Toast.LENGTH_SHORT).show();
            }
        });
        aSwitch_ster.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
//                    Toast.makeText(getActivity(),"开启",Toast.LENGTH_SHORT).show();
                    ster = "on";
                }
                else {
//                    Toast.makeText(getActivity(),"关闭",Toast.LENGTH_SHORT).show();
                    ster = "off";
                }

            }
        });

    }

    //显示订单创建者
    public void setOrdercreator(){
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        text_order_creator.setText(sp.getString("id",""));
    }


}