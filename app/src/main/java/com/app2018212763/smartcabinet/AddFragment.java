package com.app2018212763.smartcabinet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app2018212763.smartcabinet.order.OrderAddActivity;
import com.app2018212763.smartcabinet.order.OrderDelActivity;
import com.app2018212763.smartcabinet.order.OrderShowActivity;

import java.util.Objects;

public class AddFragment extends Fragment {

    //新建组件
    private Button btn_goto_addorder;
    private Button btn_goto_showorder;
    private Button btn_goto_delorder;

    public AddFragment() {
        // Required empty public constructor
    }

    public void bindview(){
        btn_goto_addorder = (Button) getActivity().findViewById(R.id.btn_goto_addorder);
        btn_goto_showorder = (Button) getActivity().findViewById(R.id.btn_goto_showorder);
        btn_goto_delorder = (Button) getActivity().findViewById(R.id.btn_goto_delorder);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindview();
        clicklistener();
    }

    public boolean checklogin(){
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        return !sp.getString("id", "").equals("");
    }

    public void clicklistener(){
        btn_goto_addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checklogin()){
                    Intent intent = new Intent(getActivity(), OrderAddActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(),"您尚未登录",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_goto_showorder.setOnClickListener(new View.OnClickListener() {
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

        btn_goto_delorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checklogin()){
                    Intent intent = new Intent(getActivity(), OrderDelActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(),"您尚未登录",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}