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
    private final static int FINISHTHEORDER = 3;
    private final static int DELETETHEORDER = 4;

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
        NewThread(INITORDERINFO, "");
    }

    void onclicklistener(){
        btn_order_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewThread(UPDATEORDERINFO, "");
            }
        });

        spinner_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String i = spinner_order.getSelectedItem().toString();
                switch (i){
                    case "??????":
                        selected = "all";
                        break;
                    case "?????????":
                        selected = "booking";
                        break;
                    case "?????????":
                        selected = "using";
                        break;
                    case "?????????":
                        selected = "finish";
                }
                NewThread(UPDATEORDERINFO, "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lv_order_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(OrderShowActivity.this, position + "??????????????????", Toast.LENGTH_SHORT).show();
            }
        });


    }

    void NewThread(int msg, String order_number){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result;
                Bundle bundle;
                Message message;
                switch (msg){
                    case 1:
                    case 2:
                        //????????????????????????????????????servlet???????????????result?????????handler????????????result
                        SharedPreferences sp = Objects.requireNonNull(OrderShowActivity.this).getSharedPreferences("user", Context.MODE_PRIVATE);
                        result = HttpOrder.GetOrderInfo(sp.getString("id",""), selected);
                        bundle = new Bundle();
                        bundle.putString("result",result);
                        message = new Message();
                        message.setData(bundle);
                        message.what = msg;
                        handler.sendMessage(message);
                        break;
                    case 3:
                        result = HttpOrder.FinishOrder(order_number);
                        bundle = new Bundle();
                        bundle.putString("result",result);
                        message = new Message();
                        message.setData(bundle);
                        message.what = msg;
                        handler.sendMessage(message);
                        break;
                    case 4:
                        result = HttpOrder.DeleteOrder(order_number);
                        bundle = new Bundle();
                        bundle.putString("result",result);
                        message = new Message();
                        message.setData(bundle);
                        message.what = msg;
                        handler.sendMessage(message);
                        break;

                }

            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                //?????????listview??????
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
                    break;
                }
                //????????????json????????????
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
                    break;
                }
                // ??????????????????
                case FINISHTHEORDER:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    if (result.equals("finish_success")){
                        Toast.makeText(OrderShowActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(OrderShowActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                    }
                    NewThread(UPDATEORDERINFO, "");
                    break;
                }
                case DELETETHEORDER:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    if (result.equals("delete_success")){
                        Toast.makeText(OrderShowActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(OrderShowActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                    }
                    NewThread(UPDATEORDERINFO, "");
                }
            }
        }
    };

    //??????order???json??????
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_order_function:   //lv????????? iv_del
                final String order_number = (String) v.getTag(R.id.tag_order_number); //??????????????????????????????item ????????????setTag ?????????object????????????????????????
                final String order_status = (String) v.getTag(R.id.tag_order_status); //??????????????????????????????item ????????????setTag ?????????object????????????????????????

                AlertDialog.Builder builder = new AlertDialog.Builder(OrderShowActivity.this);

                switch (order_status){
                    case "booking":
                        builder.setTitle("??????????????????????????????????????????????????????");
                        builder.setNegativeButton("??????", (dialog, which) -> {
                        });
                        builder.setPositiveButton("??????", (dialog, which) -> { NewThread(FINISHTHEORDER, order_number);
                        });
                        break;
                    case "unlock":
                        builder.setTitle("????????????????????????????????????????????????????????????????????????????????????");
                        builder.setPositiveButton("??????", (dialog, which) -> {
                        });
                        break;
                    case "using":
                        builder.setTitle("?????????????????????????????????????????????");
                        builder.setNegativeButton("??????", (dialog, which) -> {
                        });
                        builder.setPositiveButton("??????", (dialog, which) -> { NewThread(FINISHTHEORDER, order_number);
                        });
                        break;
                    case "finish":
                        builder.setTitle("??????????????????????????????????????????????????????");
                        builder.setNegativeButton("??????", (dialog, which) -> {
                        });
                        builder.setPositiveButton("??????", (dialog, which) -> { NewThread(DELETETHEORDER, order_number);
                        });
                }
                builder.show();
                break;
        }
    }
}