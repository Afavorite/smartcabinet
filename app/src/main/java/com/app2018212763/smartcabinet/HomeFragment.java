package com.app2018212763.smartcabinet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app2018212763.smartcabinet.Login.LoginActivity;
import com.app2018212763.smartcabinet.Order.OrderShowActivity;

import java.util.Objects;

public class HomeFragment extends Fragment {

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
                Toast.makeText(getActivity(),"正在开发",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //检测是否登录
    public boolean checklogin(){
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        return !sp.getString("id", "").equals("");
    }
}