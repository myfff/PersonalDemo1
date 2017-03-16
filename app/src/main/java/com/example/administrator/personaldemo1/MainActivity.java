package com.example.administrator.personaldemo1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import View.MyFragmentChild;
import View.MyFragmentChild4;
import View.MyFragmentchild2;
import View.MyfragmentChild3;
import View.NoScolllerViewpager;
//ViewPager与RadioGroup配合使用
//Viewpager与TabLayout配合使用
//Viewpager与RecyclerView配合使用（RecyclerView分情况加载布局）
//用fragment给Viewpager适配数据（ FragmentPagerAdapter ），Image给Viewpager适配数据（PagerAdapter）



public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private NoScolllerViewpager mViewpager;
    private RadioGroup rg;
    private List<Fragment> mFragmentList;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//
        setContentView(R.layout.activity_main);
        mViewpager = (NoScolllerViewpager) findViewById(R.id.vp);

        mFragmentList = new ArrayList<>();
        mFragmentList.add(new MyFragmentChild());
        mFragmentList.add(new MyFragmentchild2());
        mFragmentList.add(new MyfragmentChild3());
        mFragmentList.add(new MyFragmentChild4());

        MyFragmentPagerAdapter fragmentAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewpager.setAdapter(fragmentAdapter);//给Viewpager设置适配
         mViewpager.setCurrentItem(0);

        rg = (RadioGroup) findViewById(R.id.rg);
        rg.check(R.id.home);
        rg.setOnCheckedChangeListener(this);

        mViewpager.setOffscreenPageLimit(4);//防止频繁地销毁视图
    }

    //设置RadioGroup监听
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (R.id.home == checkedId) {
            mViewpager.setCurrentItem(0, false);
        }
        if (R.id.news == checkedId) {
            mViewpager.setCurrentItem(1, false);
        }
        if (R.id.setting == checkedId) {
            mViewpager.setCurrentItem(2, false);
        }
        if (R.id.smart == checkedId) {
            mViewpager.setCurrentItem(3, false);
        }
    }


    //Fragment的适配器
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
                super(fm);
                this.fragments = fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
