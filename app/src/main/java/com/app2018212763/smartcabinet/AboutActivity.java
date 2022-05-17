package com.app2018212763.smartcabinet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AboutActivity extends AppCompatActivity {

    private TextView text_about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        bindview();
        text_about.setText("智能储物app\n遇到困难请联系管理员\nlai_kaiwen@qq.com");
    }

    public void bindview(){
        text_about = (TextView) findViewById(R.id.text_about);
    }
}