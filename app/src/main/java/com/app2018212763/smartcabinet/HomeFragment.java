package com.app2018212763.smartcabinet;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.app2018212763.smartcabinet.Login.LoginActivity;
import com.app2018212763.smartcabinet.Order.HttpOrder;
import com.app2018212763.smartcabinet.Order.OrderShowActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Objects;

public class HomeFragment extends Fragment {
    private final static int UNLOCK_JUDGE = 1;

    private Button btn_qrcode_scanner;
    private Button btn_myorder;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindview();
        //读取登录信息,查看是否登录
        clicklistener();
    }

    public void bindview(){
        btn_qrcode_scanner = (Button) getActivity().findViewById(R.id.btn_qrcode_scanner);
        btn_myorder = (Button) getActivity().findViewById(R.id.btn_myorder);
    }

    public void clicklistener() {
        btn_myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checklogin()){
                    Intent intent = new Intent(getActivity(), OrderShowActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(),"您尚未登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_qrcode_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checklogin()){
//                    view -> scan();
                    // 创建IntentIntegrator对象
                    IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                    // 开始扫描
                    intentIntegrator.initiateScan();
                }
                else {
                    Toast.makeText(getActivity(),"您尚未登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //检测是否登录
    public boolean checklogin(){
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        return !sp.getString("id", "").equals("");
    }

//    private void scan() {
//        // 创建IntentIntegrator对象
//        IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
//        // 开始扫描
//        intentIntegrator.initiateScan();
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 获取二维码解析结果并处理
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getActivity(), "取消扫描", Toast.LENGTH_LONG).show();
            } else {
                //扫描到内容
                JSONObject jsonObject = new JSONObject();
                SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
                jsonObject.put("box", intentResult.getContents());
                jsonObject.put("user", sp.getString("id",""));
                jsonObject.toString();
                // Toast.makeText(getActivity(), jsonObject.toString(), Toast.LENGTH_LONG).show();

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
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case UNLOCK_JUDGE:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (result.equals("already unlock")) {
                            Toast.makeText(getActivity(),"箱柜已经解锁",Toast.LENGTH_SHORT).show();
                        }
                        else if (result.equals("unlock")){
                            Toast.makeText(getActivity(),"箱柜解锁",Toast.LENGTH_SHORT).show();
                        }
                        else if (result.equals("not exist")){
                            Toast.makeText(getActivity(),"订单不存在，请检查订单内容",Toast.LENGTH_SHORT).show();
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