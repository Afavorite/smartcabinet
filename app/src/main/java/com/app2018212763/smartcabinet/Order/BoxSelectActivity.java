package com.app2018212763.smartcabinet.Order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app2018212763.smartcabinet.R;

public class BoxSelectActivity extends AppCompatActivity {

    private Button btn_confirm;
    private EditText editText;
    private int ResultCode = 2;

    void Bindview(){
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        editText = (EditText) findViewById(R.id.editText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_select);
        Bindview();
        onclicklistener();
    }

    void onclicklistener(){
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("box_number",editText.getText().toString());
                setResult(ResultCode,intent);//向上一级发送数据
                finish();
            }
        });
    }
}