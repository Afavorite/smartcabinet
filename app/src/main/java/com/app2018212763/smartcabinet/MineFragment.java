package com.app2018212763.smartcabinet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app2018212763.smartcabinet.Login.LoginActivity;
import com.app2018212763.smartcabinet.Order.OrderShowActivity;

import java.util.Objects;

public class MineFragment extends Fragment {
    private ImageView showuserimage;
    private TextView showuserid;

    private Button btn_goto_login;
    private Button btn_logout;
    private Button btn_admin;
    private Button btn_about;
//    private Button btn_goto_showorder;

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    //绑定组件
    public void bindview(){
        showuserimage = (ImageView) Objects.requireNonNull(getActivity()).findViewById(R.id.showuserimage);
        showuserid = (TextView) getActivity().findViewById(R.id.showuserid);
        btn_goto_login = (Button) Objects.requireNonNull(getActivity()).findViewById(R.id.btn_goto_login);
        btn_logout = (Button) getActivity().findViewById(R.id.btn_logout);
        btn_admin = (Button) getActivity().findViewById(R.id.btn_admin);
        btn_about = (Button) getActivity().findViewById(R.id.btn_about);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindview();
        //头像显示
        showuserimage.setImageResource(R.mipmap.userimage);
        //读取登录信息,查看是否登录
        if (checklogin()) {
            SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
            showuserid.setText("欢迎:"+sp.getString("id",""));
            btn_goto_login.setVisibility(View.GONE);
            if (sp.getString("id","").equals("1000000001")){
                visibilitychange("isadmin");
            }
            else { visibilitychange("notadmin");}
        }
        else{
            showuserid.setText("您尚未登录");
            visibilitychange("notlogin");
        }
        clicklistener();

    }

    //检测是否登录
    public boolean checklogin(){
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
        return !sp.getString("id", "").equals("");
    }

    //根据登录状态设置控件的可见性
    public void visibilitychange(String status){
        switch (status){
            case "notlogin":
                btn_goto_login.setVisibility(View.VISIBLE);
                btn_logout.setVisibility(View.GONE);
                btn_admin.setVisibility(View.GONE);
                break;
            case "notadmin":
                btn_goto_login.setVisibility(View.GONE);
                btn_logout.setVisibility(View.VISIBLE);
                btn_admin.setVisibility(View.GONE);
                break;
            case "isadmin":
                btn_goto_login.setVisibility(View.GONE);
                btn_logout.setVisibility(View.VISIBLE);
                btn_admin.setVisibility(View.VISIBLE);
                break;
        }
    }

    //按键检测
    public void clicklistener(){
        btn_goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checklogin()){
                    Toast.makeText(getActivity(),"您已登录",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checklogin()){
                    AlertDialog.Builder dialog = new AlertDialog.Builder (getActivity());//通过AlertDialog.Builder创建出一个AlertDialog的实例

                    dialog.setTitle("警告！");//设置对话框的标题
                    dialog.setMessage("确认注销");//设置对话框的内容
                    dialog.setCancelable(false);//设置对话框是否可以取消
                    dialog.setPositiveButton("确认", new DialogInterface. OnClickListener() {//确定按钮的点击事件
                        @Override
                        //点击确定，清空信息
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
                            sp.edit().putString("id","").apply();
                            showuserid.setText("您尚未登录");
                            visibilitychange("notlogin");

                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface. OnClickListener() {//取消按钮的点击事件
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();//显示对话框
                }
                else{
                    Toast.makeText(getActivity(),"您尚未登录",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });

        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checklogin()){
                    SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
                    if (sp.getString("id", "").equals("1000000001")){
                        Intent intent = new Intent(getActivity(), AdminActivity.class);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(getActivity(),"您尚未登录",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}