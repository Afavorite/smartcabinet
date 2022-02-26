package com.app2018212763.smartcabinet.http;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app2018212763.smartcabinet.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private int ResultCode = 2;
    private final static int REGISTER_JUDGE = 2;
    private Button register;
    private EditText id,psw_1,psw_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = (Button) findViewById(R.id.btn_register);
        register.setOnClickListener(this);
        id = (EditText) findViewById(R.id.reg_stu_id);
        psw_1 = (EditText) findViewById(R.id.reg_stu_password);
        psw_2 = (EditText) findViewById(R.id.reg_stu_confirm_password);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case REGISTER_JUDGE:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (result.equals("success")) {
                            Intent intent = new Intent();
                            intent.putExtra("id",id.getText().toString());
                            intent.putExtra("password",psw_1.getText().toString());
                            setResult(ResultCode,intent);//向上一级发送数据
                            finish();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this,"注册失败！",Toast.LENGTH_LONG).show();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:{
                if (psw_1.getText().toString().length() < 8 || psw_1.getText().toString().length() > 20 ){
                    Toast.makeText(RegisterActivity.this,"密码格式不正确",Toast.LENGTH_LONG).show();
                }
                else if( ! psw_1.getText().toString().equals(psw_2.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"两次密码不一致！",Toast.LENGTH_LONG).show();
                }
                else if (id.length() != 10){
                    Toast.makeText(RegisterActivity.this,"学号格式不正确",Toast.LENGTH_LONG).show();
                }

                else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = HttpLogin.RegisterByPost(id.getText().toString(),
                                    psw_1.getText().toString());
                            Bundle bundle = new Bundle();
                            bundle.putString("result",result);
                            Message msg = new Message();
                            msg.what = REGISTER_JUDGE;
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }).start();
                }
            }
            break;
        }
    }
}