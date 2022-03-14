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
import android.widget.TextView;
import android.widget.Toast;

import com.app2018212763.smartcabinet.order.OrderAddActivity;
import com.app2018212763.smartcabinet.order.OrderShowActivity;

import java.util.Objects;

public class AddFragment extends Fragment {

    private TextView text_order_creator;

    public AddFragment() {
        // Required empty public constructor
    }

    public void bindview(){
        text_order_creator = (TextView) getActivity().findViewById(R.id.text_order_creator);
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
        setOrdercreator();
    }

    public boolean checklogin(){
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        return !sp.getString("id", "").equals("");
    }

    public void clicklistener(){
    }

    public void setOrdercreator(){
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        text_order_creator.setText(sp.getString("id",""));
    }
}