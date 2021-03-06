package com.app2018212763.smartcabinet;

import static com.app2018212763.smartcabinet.Function.Settime.comparetime;
import static com.app2018212763.smartcabinet.Function.Settime.showDatePickerDialog;
import static com.app2018212763.smartcabinet.Function.Settime.showTimePickerDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
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

import com.alibaba.fastjson.JSON;
import com.app2018212763.smartcabinet.Bean.Order;
import com.app2018212763.smartcabinet.Login.HttpLogin;
import com.app2018212763.smartcabinet.Login.LoginActivity;
import com.app2018212763.smartcabinet.Order.BoxSelectActivity;
import com.app2018212763.smartcabinet.Order.HttpOrder;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private Switch aSwitch_temp;

    private Button btn_goto_submit;

    private final static int ORDERADD_JUDGE = 1;
    //订单数据
    private String temp = "0";
    private String ster = "off";
    private String temp_switch = "off";
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
        seekbar_temp.setEnabled(false);//默认关闭
        aSwitch_ster = (Switch) getActivity().findViewById(R.id.switch_sterilization);
        aSwitch_temp = (Switch) getActivity().findViewById(R.id.switch_temp);

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
        //选择是否打开控温
        aSwitch_temp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getActivity(),"开启控温",Toast.LENGTH_SHORT).show();
                     temp_switch = "on";
                     seekbar_temp.setEnabled(true);
                }
                else {
                    Toast.makeText(getActivity(),"关闭控温",Toast.LENGTH_SHORT).show();
                    temp_switch = "off";
                    seekbar_temp.setEnabled(false);
                }

            }
        });
        //选择温度
        seekbar_temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                text_temp.setText("箱柜设置温度" + progress + "℃");
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

        //订单提交按钮
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
                        Order order = new Order();
                        order.setOrder_creator(sp.getString("id",""));
                        order.setOrder_box_number(text_order_boxnumber.getText().toString());
                        order.setOrder_use_start_time(starttime);
                        order.setOrder_use_end_time(endtime);
                        // order.setOrder_temp(temp);
                        order.setOrder_sterilization(ster);
                        order.setOrder_temp_switch(temp_switch);
                        if (temp_switch == "on"){
                            order.setOrder_temp(temp);
                        }

                        AlertDialog.Builder dialog = new AlertDialog.Builder (getActivity());//通过AlertDialog.Builder创建出一个AlertDialog的实例
                        dialog.setTitle("请确认！");//设置对话框的标题
                        dialog.setMessage("确认提交？");//设置对话框的内容
                        dialog.setCancelable(false);//设置对话框是否可以取消
                        dialog.setPositiveButton("确认", new DialogInterface. OnClickListener() {//确定按钮的点击事件
                            @Override
                            //点击确定，清空信息
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //使用下面类里的函数，连接servlet，返回一个result，使用handler处理这个result
                                        String result = HttpOrder.OrderAdd(order);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("result",result);
                                        Message message = new Message();
                                        message.setData(bundle);
                                        message.what = ORDERADD_JUDGE;
                                        handler.sendMessage(message);
                                    }
                                }).start();
                            }
                        });
                        dialog.setNegativeButton("取消", new DialogInterface. OnClickListener() {//取消按钮的点击事件
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();//显示对话框
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

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case ORDERADD_JUDGE:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (result.equals("fail")){
                            Toast.makeText(getActivity(),"提交失败！",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(),"提交成功！",Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    };
}