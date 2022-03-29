package com.app2018212763.smartcabinet.Function;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Settime {

    /**
     * 日期选择
     * @param activity
     * @param themeResId 主题
     * @param tv 对应textview
     * @param calendar 日期选择器
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                String flag1 = "",flag2 = "";
                if (monthOfYear < 10){ flag1 = "0";}
                if (dayOfMonth < 10){ flag2 = "0";}
                tv.setText(year + "-" + flag1 + (monthOfYear + 1) + "-" + flag2 + dayOfMonth);
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
                        String flag1 = "",flag2 = "";
                        if (hourOfDay < 10){ flag1 = "0";}
                        if (minute < 10){ flag2 = "0";}
                        tv.setText(flag1 + hourOfDay + ":" + flag2 + minute + ":00");
                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
    }

    public static boolean comparetime(String starttime, String endtime){
        SimpleDateFormat cTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean flag = false;
        try {
            Date stime = cTime.parse(starttime);
            Date etime = cTime.parse(endtime);
            if (etime.getTime() > stime.getTime()){
                flag = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
