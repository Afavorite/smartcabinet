package com.app2018212763.smartcabinet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app2018212763.smartcabinet.Order.BoxSelectActivity;

public class AdminActivity extends AppCompatActivity {

    private TextView text_admin_boxselect;
    private Button btn_admin_control;
    private Button btn_admin_stopcontrol;
    private Button btn_admin_opendoor;
    private int RequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bindview();
        clicklistener();
    }

    public void bindview(){
        text_admin_boxselect = (TextView) findViewById(R.id.text_admin_boxselect);
        btn_admin_control = (Button) findViewById(R.id.btn_admin_control);
        btn_admin_stopcontrol = (Button) findViewById(R.id.btn_admin_stopcontrol);
        btn_admin_opendoor = (Button) findViewById(R.id.btn_admin_opendoor);
    }

    public void clicklistener(){
        //选择箱柜号
        text_admin_boxselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, BoxSelectActivity.class);
                startActivityForResult(intent,RequestCode);
            }
        });
    }

    //箱柜选择后返回数据并显示
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            text_admin_boxselect.setText(data.getStringExtra("box_number"));
        }
    }
}