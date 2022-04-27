package com.app2018212763.smartcabinet.Order;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.app2018212763.smartcabinet.Bean.Box;
import com.app2018212763.smartcabinet.Bean.Order;
import com.app2018212763.smartcabinet.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Objects;

public class OrderShowActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_order_refresh;
    private ListView lv_order_info;
    private Spinner spinner_order;
    private final static int INITORDERINFO = 1;
    private final static int UPDATEORDERINFO = 2;

    String selected;

    LinkedList<Order> orders = new LinkedList<>();
    OrderAdapter orderAdapter = new OrderAdapter(orders,OrderShowActivity.this);

    void Bindview(){
        btn_order_refresh = (Button) findViewById(R.id.btn_order_refresh);
        lv_order_info = (ListView) findViewById(R.id.lv_order_info);
        spinner_order = (Spinner) findViewById(R.id.spinner_order);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_show);

        Bindview();
        onclicklistener();
        selected = "all";
        NewThread(INITORDERINFO);
    }

    void onclicklistener(){
        btn_order_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewThread(UPDATEORDERINFO);
            }
        });

        spinner_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String i = spinner_order.getSelectedItem().toString();
                switch (i){
                    case "所有":
                        selected = "all";
                        break;
                    case "已预定":
                        selected = "booking";
                        break;
                    case "使用中":
                        selected = "using";
                        break;
                    case "已结束":
                        selected = "finish";
                }
                NewThread(UPDATEORDERINFO);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lv_order_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(OrderShowActivity.this, position + "号位置被点击", Toast.LENGTH_SHORT).show();
            }
        });


    }

    void NewThread(int msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //使用下面类里的函数，连接servlet，返回一个result，使用handler处理这个result
                SharedPreferences sp = Objects.requireNonNull(OrderShowActivity.this).getSharedPreferences("user", Context.MODE_PRIVATE);
                String result = HttpOrder.GetOrderInfo(sp.getString("id",""), selected);
                Bundle bundle = new Bundle();
                bundle.putString("result",result);
                Message message = new Message();
                message.setData(bundle);
                message.what = msg;
                handler.sendMessage(message);
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                //初始化listview显示
                case INITORDERINFO:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (!result.equals("")){
                            parseOrderJson(result);
                            lv_order_info.setAdapter(orderAdapter);
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                //重新获取json更新列表
                case UPDATEORDERINFO:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (!result.equals("")){
                            orders.clear();
                            parseOrderJson(result);
                            orderAdapter.notifyDataSetChanged();
//                            Toast.makeText(OrderShowActivity.this,result,Toast.LENGTH_SHORT).show();
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    };

    //解析order的json数据
    private void parseOrderJson(String json){
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0;i < jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Order order = new Order();
                order.setOrder_number(jsonObject.getString("order_number"));
                order.setOrder_creator(jsonObject.getString("order_creator"));
                order.setOrder_box_number(jsonObject.getString("order_box_number"));
                order.setOrder_temp_switch(jsonObject.getString("order_temp_switch"));
                if (order.getOrder_temp_switch().equals("on")){
                    order.setOrder_temp(jsonObject.getString("order_temp"));
                }
                order.setOrder_sterilization(jsonObject.getString("order_sterilization"));
                order.setOrder_status(jsonObject.getString("order_status"));
                order.setOrder_create_time(jsonObject.getString("order_create_time"));
                if (order.getOrder_status().equals("finish")){
                    order.setOrder_end_time(jsonObject.getString("order_end_time"));
                }
                orders.add(order);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_order_function:   //lv条目中 iv_del
                final int position = (int) v.getTag(); //获取被点击的控件所在item 的位置，setTag 存储的object，所以此处要强转

                //点击删除按钮之后，给出dialog提示
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderShowActivity.this);
                builder.setTitle( position + "号位置的删除按钮被点击，确认删除?");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                break;
        }
    }
}