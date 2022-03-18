package com.app2018212763.smartcabinet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app2018212763.smartcabinet.Login.LoginActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //UI
    private TextView tab_menu_home;
    private TextView tab_menu_add;
    private TextView tab_menu_mine;
    private FrameLayout ly_content;

    //Fragment
    private HomeFragment fg1;
    private AddFragment fg2;
    private MineFragment fg3;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
        bindViews();
        tab_menu_home.performClick();   //模拟一次点击，既进去后选择第一项
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        tab_menu_add = findViewById(R.id.tab_menu_add);
        tab_menu_home = findViewById(R.id.tab_menu_home);
        tab_menu_mine = findViewById(R.id.tab_menu_mine);
        ly_content = findViewById(R.id.ly_content);

        tab_menu_add.setOnClickListener(this);
        tab_menu_home.setOnClickListener(this);
        tab_menu_mine.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        tab_menu_add.setSelected(false);
        tab_menu_home.setSelected(false);
        tab_menu_mine.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.tab_menu_home:
                setSelected();
                tab_menu_home.setSelected(true);
                if(fg1 == null){
                    fg1 = new HomeFragment();
                    fTransaction.add(R.id.ly_content,fg1);
                }else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.tab_menu_add:
                if (checklogin()){
                    setSelected();
                    tab_menu_add.setSelected(true);
                    if(fg2 == null){
                        fg2 = new AddFragment();
                        fTransaction.add(R.id.ly_content,fg2);
                    }else{
                        fTransaction.show(fg2);
                    }
                }
                else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder (MainActivity.this);//通过AlertDialog.Builder创建出一个AlertDialog的实例

                    dialog.setTitle("您尚未登录！");//设置对话框的标题
                    dialog.setMessage("现在要前往登录界面么");//设置对话框的内容
                    dialog.setCancelable(false);//设置对话框是否可以取消
                    dialog.setPositiveButton("确认", new DialogInterface. OnClickListener() {//确定按钮的点击事件
                        @Override
                        //点击确定，清空信息
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface. OnClickListener() {//取消按钮的点击事件
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tab_menu_home.performClick();
                        }
                    });
                    dialog.show();//显示对话框
                }
                break;
            case R.id.tab_menu_mine:
                setSelected();
                tab_menu_mine.setSelected(true);
                if(fg3 == null){
                    fg3 = new MineFragment();
                    fTransaction.add(R.id.ly_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;
        }
        fTransaction.commit();
    }

    public boolean checklogin(){
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        return !sp.getString("id", "").equals("");
    }
}