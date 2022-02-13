package com.app2018212763.smartcabinet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{

    //组件
    private Button btn_login;
    private Button btn_goto_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
    }

    //组件与时间绑定
    private void bindViews() {
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_goto_register = (Button) findViewById(R.id.btn_goto_register);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "登陆中", Toast.LENGTH_LONG).show();
            }
        });
        btn_goto_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}