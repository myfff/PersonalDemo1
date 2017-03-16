package com.example.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        //【1】设置Navigation的事件，为侧边栏
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//结束本界面
            }
        });

        //【2】设置menu
        toolbar.inflateMenu(R.menu.menu);
        //【3】设置溢出菜单的图标(就是那个三点地方的图片)
        toolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        //【4】设置menuitem的点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.select:
                        Toast.makeText(getApplicationContext(),"你点击了选择按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case  R.id.setting:
                        Toast.makeText(getApplicationContext(),"你点击了设置按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case  R.id.add:
                        Toast.makeText(getApplicationContext(),"你点击了添加按钮",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }
}
