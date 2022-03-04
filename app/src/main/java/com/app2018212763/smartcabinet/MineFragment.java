package com.app2018212763.smartcabinet;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.app2018212763.smartcabinet.http.LoginActivity;

import java.util.Objects;

public class MineFragment extends Fragment {
    private ImageView userimage;

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        userimage = view.findViewById(R.id.userimage);//头像显示
        userimage.setImageResource(R.mipmap.userimage);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = (Button) Objects.requireNonNull(getActivity()).findViewById(R.id.btn_goto_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}