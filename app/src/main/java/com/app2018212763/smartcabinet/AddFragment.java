package com.app2018212763.smartcabinet;

import static com.app2018212763.smartcabinet.Function.Settime.comparetime;
import static com.app2018212763.smartcabinet.Function.Settime.showDatePickerDialog;
import static com.app2018212763.smartcabinet.Function.Settime.showTimePickerDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app2018212763.smartcabinet.Bean.Order;
import com.app2018212763.smartcabinet.Order.BoxSelectActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.SimpleTimeZone;

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

    private Button btn_goto_submit;

    //订单数据
    private String temp;
    private String ster = "off";
    private int RequestCode = 1;

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

        btn_goto_submit = (Button) getActivity().findViewById(R.id.btn_goto_submit);
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
        //选择箱柜号
        text_order_boxnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BoxSelectActivity.class);
                startActivityForResult(intent,RequestCode);
            }
        });

        //选择开始日期
        text_order_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(getActivity(),  2, text_order_startdate, calendar);
            }
        });

        //选择开始时间
        text_order_starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(getActivity(),2, text_order_starttime, calendar);
            }
        });
        //选择结束日期
        text_order_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(getActivity(),  2, text_order_enddate, calendar);
            }
        });

        //选择结束时间
        text_order_endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(getActivity(),2, text_order_endtime, calendar);
            }
        });
        //选择温度
        seekbar_temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                text_temp.setText("箱柜设置温度" + progress + "  / 100 ");
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
        //选择是否打开杀菌功能
        aSwitch_ster.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getActivity(),"杀菌开启",Toast.LENGTH_SHORT).show();
                    ster = "on";
                }
                else {
                    Toast.makeText(getActivity(),"杀菌关闭",Toast.LENGTH_SHORT).show();
                    ster = "off";
                }

            }
        });

        btn_goto_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
                String starttime = text_order_startdate.getText().toString() + " " + text_order_starttime.getText().toString();
                String endtime = text_order_enddate.getText().toString() + " " + text_order_endtime.getText().toString();

                if (!TextUtils.isEmpty(text_order_boxnumber.getText())
                        && !TextUtils.isEmpty(text_order_startdate.getText())
                        && !TextUtils.isEmpty(text_order_starttime.getText())
                        && !TextUtils.isEmpty(text_order_enddate.getText())
                        && !TextUtils.isEmpty(text_order_endtime.getText())
                        && !TextUtils.isEmpty(temp)){
                    //检查时间关系是否正确
                    if (comparetime(starttime,endtime)){
                        //实例一个Order，写入信息
                        Order neworder = new Order();
                    }
                    else {
                        Toast.makeText(getActivity(),"时间有误",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(),"未填写完毕",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //显示订单创建者
    public void setOrdercreator(){
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        text_order_creator.setText(sp.getString("id",""));
    }

    //箱柜选择后返回数据并显示
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            text_order_boxnumber.setText(data.getStringExtra("box_number"));
        }
    }
}