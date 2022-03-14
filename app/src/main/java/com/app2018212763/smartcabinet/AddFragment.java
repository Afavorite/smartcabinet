package com.app2018212763.smartcabinet;

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
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.app2018212763.smartcabinet.Login.LoginActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddFragment extends Fragment {

    private TextView text_order_creator;
    private TextView text_order_boxnumber;
    private TextView text_order_startdate;
    private TextView text_order_starttime;
    private TextView text_order_enddate;
    private TextView text_order_endtime;

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
                showDatePickerDialog(getActivity(),  0, text_order_startdate, calendar);
            }
        });
        text_order_starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(getActivity(),0, text_order_starttime, calendar);
            }
        });
        text_order_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(getActivity(),  0, text_order_enddate, calendar);
            }
        });
        text_order_endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(getActivity(),0, text_order_endtime, calendar);
            }
        });

    }

    public void setOrdercreator(){
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        text_order_creator.setText(sp.getString("id",""));
    }

    /**
     * 日期选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                tv.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 时间选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showTimePickerDialog(Activity activity,int themeResId, final TextView tv, Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog( activity,themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv.setText(hourOfDay + "时" + minute  + "分");
                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
    }


}