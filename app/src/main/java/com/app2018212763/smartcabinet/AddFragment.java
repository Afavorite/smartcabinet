package com.app2018212763.smartcabinet;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app2018212763.smartcabinet.Login.LoginActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddFragment extends Fragment {

    private TextView text_order_creator;
    private TextView text_order_boxnumber;
    private TextView text_order_startdate;
    private TextView text_order_starttime;
    private TextView text_order_enddate;
    private TextView text_order_endtime;

    DateFormat format= DateFormat.getDateTimeInstance();
    Calendar calendar= Calendar.getInstance(Locale.CHINA);

    public AddFragment() {
        // Required empty public constructor
    }

    public void bindview(){
        text_order_creator = (TextView) getActivity().findViewById(R.id.text_order_creator);
        text_order_boxnumber = (TextView) getActivity().findViewById(R.id.text_order_boxnumber);
        text_order_startdate = (TextView) getActivity().findViewById(R.id.text_order_startdate);
        text_order_starttime = (TextView) getActivity().findViewById(R.id.text_order_starthour);
        text_order_enddate = (TextView) getActivity().findViewById(R.id.text_order_enddate);
        text_order_endtime = (TextView) getActivity().findViewById(R.id.text_order_endhour);
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
        text_order_starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setOrdercreator(){
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        text_order_creator.setText(sp.getString("id",""));
    }

}