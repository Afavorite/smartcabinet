package com.app2018212763.smartcabinet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.app2018212763.smartcabinet.Bean.Order;
import com.app2018212763.smartcabinet.Order.BoxSelectActivity;
import com.app2018212763.smartcabinet.Order.HttpOrder;
import com.app2018212763.smartcabinet.Order.OrderShowActivity;

import java.util.Objects;

public class AdminActivity extends AppCompatActivity {

    private TextView edit_admin_boxselect;
    private Button btn_admin_control;
    private Button btn_admin_stopcontrol;
    private Button btn_admin_opendoor;
    private String control_order_number = null;
    private Boolean isControlling = false;

    private final static int ADMINCONTROL_JUDGE = 1;
    private final static int ADMINSTOPCONTROL_JUDGE = 2;
    private final static int UNLOCK_JUDGE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bindview();
        clicklistener();
    }

    public void bindview(){
        edit_admin_boxselect = (TextView) findViewById(R.id.edit_admin_boxselect);
        btn_admin_control = (Button) findViewById(R.id.btn_admin_control);
        btn_admin_stopcontrol = (Button) findViewById(R.id.btn_admin_stopcontrol);
        btn_admin_opendoor = (Button) findViewById(R.id.btn_admin_opendoor);

        btn_admin_control.setVisibility(View.VISIBLE);
        btn_admin_stopcontrol.setVisibility(View.GONE);
        btn_admin_opendoor.setVisibility(View.GONE);
    }

    public void clicklistener(){
        btn_admin_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = AdminActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
                Order order = new Order();
                order.setOrder_creator(sp.getString("id",""));
                order.setOrder_box_number(edit_admin_boxselect.getText().toString());
                order.setOrder_use_start_time("2000-03-12 00:00:00");
                order.setOrder_use_end_time("2000-03-12 00:00:01");
                order.setOrder_sterilization("off");
                order.setOrder_temp_switch("off");

                AlertDialog.Builder dialog = new AlertDialog.Builder (AdminActivity.this);//通过AlertDialog.Builder创建出一个AlertDialog的实例
                dialog.setTitle("请确认！");//设置对话框的标题
                dialog.setMessage("确认控制？");//设置对话框的内容
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
                                message.what = ADMINCONTROL_JUDGE;
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
                dialog.show();
            }
        });

        //停止控制按钮
        btn_admin_stopcontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder (AdminActivity.this);//通过AlertDialog.Builder创建出一个AlertDialog的实例
                dialog.setTitle("请确认！");//设置对话框的标题
                dialog.setMessage("确认控制？");//设置对话框的内容
                dialog.setCancelable(false);//设置对话框是否可以取消
                dialog.setPositiveButton("确认", new DialogInterface. OnClickListener() {//确定按钮的点击事件
                    @Override
                    //点击确定，清空信息
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                stopcontrol();
                            }
                        }).start();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface. OnClickListener() {//取消按钮的点击事件
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        });

        //开门按钮
        btn_admin_opendoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                SharedPreferences sp = Objects.requireNonNull(AdminActivity.this).getSharedPreferences("user", Context.MODE_PRIVATE);
                jsonObject.put("box", edit_admin_boxselect.getText().toString());
                jsonObject.put("user", sp.getString("id",""));
                jsonObject.toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //使用下面类里的函数，连接servlet，返回一个result，使用handler处理这个result
                        String result = HttpOrder.unlock(jsonObject.toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("result",result);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = UNLOCK_JUDGE;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case ADMINCONTROL_JUDGE:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (result.equals("fail")){
                            Toast.makeText(AdminActivity.this,"控制失败or已经控制！",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(AdminActivity.this,result,Toast.LENGTH_SHORT).show();
                            edit_admin_boxselect.setEnabled(false);
                            btn_admin_control.setVisibility(View.GONE);
                            btn_admin_stopcontrol.setVisibility(View.VISIBLE);
                            btn_admin_opendoor.setVisibility(View.VISIBLE);
                            isControlling = true;
                            control_order_number = result;
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    break;
                }
                case ADMINSTOPCONTROL_JUDGE: {
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    try {
                        if (result.equals("finish_success")) {
                            Toast.makeText(AdminActivity.this, "停止控制成功", Toast.LENGTH_SHORT).show();
                            edit_admin_boxselect.setEnabled(true);
                            btn_admin_control.setVisibility(View.VISIBLE);
                            btn_admin_stopcontrol.setVisibility(View.GONE);
                            btn_admin_opendoor.setVisibility(View.GONE);
                            isControlling = false;
                        } else {
                            Toast.makeText(AdminActivity.this, "停止失败", Toast.LENGTH_SHORT).show();
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    break;
                }
                case UNLOCK_JUDGE:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (result.equals("already unlock")) {
                            Toast.makeText(AdminActivity.this,"箱柜已经解锁",Toast.LENGTH_SHORT).show();
                        }
                        else if (result.equals("unlock")){
                            Toast.makeText(AdminActivity.this,"箱柜解锁",Toast.LENGTH_SHORT).show();
                        }
                        else if (result.equals("not exist")){
                            Toast.makeText(AdminActivity.this,"订单不存在，请检查订单内容",Toast.LENGTH_SHORT).show();
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    break;
                }

            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (isControlling){
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("提示：");
                builder.setMessage("退出将停止控制，您确定退出？");

                //设置确定按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopcontrol();
                    }
                });
                //设置取消按钮
                builder.setNegativeButton("取消",null);
                //显示提示框
                builder.show();
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    public void stopcontrol(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //使用下面类里的函数，连接servlet，返回一个result，使用handler处理这个result
                String result = HttpOrder.FinishOrder(control_order_number);
                Bundle bundle = new Bundle();
                bundle.putString("result",result);
                Message message = new Message();
                message.setData(bundle);
                message.what = ADMINSTOPCONTROL_JUDGE;
                handler.sendMessage(message);
            }
        }).start();
    }
}