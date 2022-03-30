package com.app2018212763.smartcabinet.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app2018212763.smartcabinet.MainActivity;
import com.app2018212763.smartcabinet.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login,register;
    private EditText id,password;
    private final static int LOGIN_JUDGE = 1;
    private int RequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener(this);
        register = (Button) findViewById(R.id.btn_goto_register);
        register.setOnClickListener(this);
        id = (EditText) findViewById(R.id.login_stu_id);
        password = (EditText) findViewById(R.id.login_stu_password);

        SharedPreferences sp = getSharedPreferences("userid",MODE_PRIVATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//注册成功从注册界面回传信息
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            id.setText(data.getStringExtra("id"));
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case LOGIN_JUDGE:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (result.equals("success")) {
                            Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                            //注册成功保留登录信息
                            SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
                            sp.edit().putString("id",id.getText().toString()).commit();

                            finish();
                            MainActivity.mainActivity.finish();//销毁旧的activity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else if (result.equals("fail")){
                            Toast.makeText(LoginActivity.this,"登录失败！",Toast.LENGTH_SHORT).show();
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //使用下面类里的函数，连接servlet，返回一个result，使用handler处理这个result
                        String result = HttpLogin.LoginByPost(id.getText().toString(),password.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("result",result);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = LOGIN_JUDGE;
                        handler.sendMessage(message);
                    }
                }).start();
            }
            break;
            case R.id.btn_goto_register:{
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent,RequestCode);
            }
            break;
        }
    }
}