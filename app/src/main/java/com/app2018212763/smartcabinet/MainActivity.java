package com.app2018212763.smartcabinet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //UI
    private TextView tab_menu_home;
    private TextView tab_menu_add;
    private TextView tab_menu_mine;
    private FrameLayout ly_content;

    //Fragment
    private MyFragment fg1,fg2;
    private mineFragment fg3;
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
                    fg1 = new MyFragment("第一个Fragment");
                    fTransaction.add(R.id.ly_content,fg1);
                }else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.tab_menu_add:
                setSelected();
                tab_menu_add.setSelected(true);
                if(fg2 == null){
                    fg2 = new MyFragment("第二个Fragment");
                    fTransaction.add(R.id.ly_content,fg2);
                }else{
                    fTransaction.show(fg2);
                }
                break;
            case R.id.tab_menu_mine:
                setSelected();
                tab_menu_mine.setSelected(true);
                if(fg3 == null){
                    fg3 = new mineFragment();
                    fTransaction.add(R.id.ly_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;
        }
        fTransaction.commit();
    }
}