package com.app2018212763.smartcabinet.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BoxSelectActivity extends AppCompatActivity {

    private Button btn_box_confirm;
    private Button btn_box_refresh;
    private ListView lv_box_info;
    private int ResultCode = 2;
    private final static int INITBOXINFO = 3;
    private final static int UPDATEBOXINFO = 4;
    private String choose = "";


    LinkedList<Box> boxs = new LinkedList<>();
    BoxAdapter boxAdapter = new BoxAdapter(boxs,BoxSelectActivity.this);

    void Bindview(){
        btn_box_confirm = (Button) findViewById(R.id.btn_box_confirm);
        btn_box_refresh = (Button) findViewById(R.id.btn_box_refresh);
        lv_box_info = (ListView) findViewById(R.id.lv_box_info);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_select);
        Bindview();

        onclicklistener();
        NewThread(INITBOXINFO);
    }

    void onclicklistener(){
        btn_box_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boxs.isEmpty() && !choose.equals("")){
                    Intent intent = new Intent();
                    intent.putExtra("box_number",choose);
                    setResult(ResultCode,intent);//向上一级发送箱柜选择结果数据
                    finish();
                }
                else{
                    Toast.makeText(BoxSelectActivity.this, "您尚未选择", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_box_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewThread(UPDATEBOXINFO);
            }
        });
        lv_box_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choose = boxs.get(lv_box_info.getCheckedItemPosition()).box_number;
            }
        });
    }

    void NewThread(int msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //使用下面类里的函数，连接servlet，返回一个result，使用handler处理这个result
                String result = HttpOrder.GetBoxInfo("getboxinfo");
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
                case INITBOXINFO:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (!result.equals("")){
                            parseBoxJson(result);
                            lv_box_info.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                            lv_box_info.setAdapter(boxAdapter);
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                //重新获取json更新列表
                case UPDATEBOXINFO:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (!result.equals("")){
                            boxs.clear();
                            parseBoxJson(result);
                            boxAdapter.notifyDataSetChanged();
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
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0;i < jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Box box = new Box();
                box.setBox_number(jsonObject.getString("box_number"));
                boxs.add(box);
            }
        }catch (Exception e){e.printStackTrace();}
    }
}