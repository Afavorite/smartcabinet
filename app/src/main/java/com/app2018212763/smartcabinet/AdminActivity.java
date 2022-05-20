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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app2018212763.smartcabinet.Bean.Order;
import com.app2018212763.smartcabinet.Order.BoxSelectActivity;
import com.app2018212763.smartcabinet.Order.HttpOrder;

import java.util.Objects;

public class AdminActivity extends AppCompatActivity {

    private TextView text_admin_boxselect;
    private Button btn_admin_control;
    private Button btn_admin_stopcontrol;
    private Button btn_admin_opendoor;
    private int RequestCode = 1;

    private final static int ADMINCONTROL_JUDGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bindview();
        clicklistener();
    }

    public void bindview(){
        text_admin_boxselect = (TextView) findViewById(R.id.text_admin_boxselect);
        btn_admin_control = (Button) findViewById(R.id.btn_admin_control);
        btn_admin_stopcontrol = (Button) findViewById(R.id.btn_admin_stopcontrol);
        btn_admin_opendoor = (Button) findViewById(R.id.btn_admin_opendoor);
    }

    public void clicklistener(){
        //选择箱柜号
        text_admin_boxselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, BoxSelectActivity.class);
                startActivityForResult(intent,RequestCode);
            }
        });

        btn_admin_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = AdminActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
                Order order = new Order();
                order.setOrder_creator(sp.getString("id",""));
                order.setOrder_box_number(text_admin_boxselect.getText().toString());
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
    }

    //箱柜选择后返回数据并显示
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            text_admin_boxselect.setText(data.getStringExtra("box_number"));
        }
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
                        if (result.equals("success")) {
                            Toast.makeText(AdminActivity.this,"控制成功！",Toast.LENGTH_SHORT).show();
                        }
                        if (result.equals("fail")){
                            Toast.makeText(AdminActivity.this,"控制失败！",Toast.LENGTH_SHORT).show();
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