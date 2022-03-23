package com.app2018212763.smartcabinet.Order;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app2018212763.smartcabinet.Bean.Box;
import com.app2018212763.smartcabinet.Login.LoginActivity;
import com.app2018212763.smartcabinet.MainActivity;
import com.app2018212763.smartcabinet.Order.HttpOrder;
import com.app2018212763.smartcabinet.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BoxSelectActivity extends AppCompatActivity {

    private Button btn_box_confirm;
    private ListView lv_box_info;
    private int ResultCode = 2;
    private final static int GETBOXINFO = 3;

    void Bindview(){
        btn_box_confirm = (Button) findViewById(R.id.btn_box_confirm);
        lv_box_info = (ListView) findViewById(R.id.lv_box_info);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_select);
//        Bindview();
//        onclicklistener();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //使用下面类里的函数，连接servlet，返回一个result，使用handler处理这个result
                String result = HttpOrder.GetBoxInfo("getboxinfo");
                Bundle bundle = new Bundle();
                bundle.putString("result",result);
                Message message = new Message();
                message.setData(bundle);
                message.what = GETBOXINFO;
                handler.sendMessage(message);
            }
        }).start();
    }

//    void onclicklistener(){
//        btn_confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("box_number",editText.getText().toString());
//                setResult(ResultCode,intent);//向上一级发送数据
//                finish();
//            }
//        });
//    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case GETBOXINFO:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (!result.equals("")){
//                            tv_box_information.setText(result);
                            parseBoxJson(result);
//                            Toast.makeText(BoxSelectActivity.this,"dwdw",Toast.LENGTH_SHORT).show();
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
    private void parseBoxJson(String json){
        ArrayList<Box> boxs = new ArrayList<Box>();
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0;i < jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Box box = new Box();
                box.setBox_number(jsonObject.getString("box_number"));
                boxs.add(box);
            }
            Toast.makeText(BoxSelectActivity.this,boxs.toString(),Toast.LENGTH_SHORT).show();
        }catch (Exception e){e.printStackTrace();}
    }
}