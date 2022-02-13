package com.app2018212763.smartcabinet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegisterActivity extends AppCompatActivity {

    //数据库地址
    String url = "jdbc:mysql://39.107.226.190:3306/smartcabinet";
    //数据库用户名
    String userName = "root";
    //数据库密码
    String sqlpassword = "Lkw121731";

    //UI
    Connection connection = null;
    EditText reg_stu_id;
    EditText reg_stu_password;
    EditText reg_stu_confirm_password;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindViews();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        //获取输入框的数据
                        String id = reg_stu_id.getText().toString();
                        String password = reg_stu_password.getText().toString();
//                        String password_confirm = reg_stu_confirm_password.getText().toString();
                        try {
                            //1、加载驱动
                            Class.forName("com.mysql.jdbc.Driver").newInstance();
                            System.out.println("驱动加载成功！！！");
                            //2、获取与数据库的连接
                            connection = DriverManager.getConnection(url, userName, sqlpassword);
                            System.out.println("连接数据库成功！！！");
//                            Toast.makeText(RegisterActivity.this, "连接数据库成功", Toast.LENGTH_SHORT).show();
                            //3.sql添加数据语句
                            String sql = "INSERT INTO users (stu_id, password) VALUES ( ?, ?)";
                            if (!id.equals("") && !password.equals("")) {//判断输入框是否有数据
                                //4.获取用于向数据库发送sql语句的ps
                                PreparedStatement ps = connection.prepareStatement(sql);
                                //获取输入框的数据 添加到mysql数据库
                                ps.setString(1, id);
                                ps.setString(2, password);
                                ps.executeUpdate();//更新数据库
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (connection != null) {
                                try {
                                    connection.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        Looper.loop();
                    }

                }).start();
            }
        });

    }

    private void bindViews() {
        btn_register = (Button) findViewById(R.id.btn_register);
        reg_stu_id = (EditText) findViewById(R.id.reg_stu_id);
        reg_stu_password = (EditText) findViewById(R.id.reg_stu_password);
        reg_stu_confirm_password = (EditText) findViewById(R.id.reg_stu_confirm_password);
    }
}