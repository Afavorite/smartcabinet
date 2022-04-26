package com.app2018212763.smartcabinet.Order;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.app2018212763.smartcabinet.Bean.Box;
import com.app2018212763.smartcabinet.Bean.Order;
import com.app2018212763.smartcabinet.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Objects;

public class OrderShowActivity extends AppCompatActivity {

    private Button btn_order_refresh;
    private ListView lv_order_info;
    private final static int INITORDERINFO = 1;
    private final static int UPDATEORDERINFO = 2;

    LinkedList<Order> orders = new LinkedList<>();
    OrderAdapter orderAdapter = new OrderAdapter(orders,OrderShowActivity.this);

    void Bindview(){
        btn_order_refresh = (Button) findViewById(R.id.btn_order_refresh);
        lv_order_info = (ListView) findViewById(R.id.lv_order_info);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_show);

        Bindview();
        onclicklistener();
        NewThread(INITORDERINFO);
    }

    void onclicklistener(){
        btn_order_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewThread(UPDATEORDERINFO);
            }
        });
    }

    void NewThread(int msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //使用下面类里的函数，连接servlet，返回一个result，使用handler处理这个result
                SharedPreferences sp = Objects.requireNonNull(OrderShowActivity.this).getSharedPreferences("user", Context.MODE_PRIVATE);
                String result = HttpOrder.GetOrderInfo(sp.getString("id",""), "all");
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
                            // lv_box_info.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
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
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    };

    //解析box的json数据
    private void parseOrderJson(String json){
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0;i < jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Order order = new Order();
                order.setOrder_number(jsonObject.getString("order_number"));
                orders.add(order);
            }
        }catch (Exception e){e.printStackTrace();}
    }

}