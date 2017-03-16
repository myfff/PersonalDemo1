package View;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.personaldemo1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
//tablayout与Viewpager绑定使用

public class MyFragmentChild extends Fragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Fragment> fragments;
    private TabLayout.Tab one;//tabLayout中所加的tab
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;//数目应保持和我们要给Viewpager适配多少内容相等
    private MyTabFragmeny mFragmentAdater;//viewpager的适配器
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 将myfragment Layout文件转化为View
        //返回转化来的View
        View view = inflater.inflate(R.layout.fragment1, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout);//绑定TabLayout和Viewpager
        initToolbar(view);
        initData();
        return view;
    }

    private void initToolbar(View view) {
        //控制drawableleft中图片的大小
        EditText editText=(EditText)view.findViewById(R.id.et_setting);
        //控制登录用户名图标大小
        Drawable drawable1 = getResources().getDrawable(R.drawable.seach);
        drawable1.setBounds(0, 0, 70, 70);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        editText.setCompoundDrawables(drawable1, null, null, null);//只放左边



        Toolbar toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        //【1】设置Navigation的事件，为侧边栏
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //退出程序
                getActivity().finish();
            }
        });

        //【2】设置menu
        toolbar.inflateMenu(R.menu.menu);
        //【3】设置溢出菜单的图标(就是那个三点地方的图片)
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.setting1));
        //【4】设置menuitem的点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.delete:
                        Toast.makeText(getActivity(),"你点击了删除按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case  R.id.setting:
                        Toast.makeText(getActivity(),"你点击了设置按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case  R.id.add:
                        Toast.makeText(getActivity(),"你点击了添加按钮",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        fragments = new ArrayList<>();
        //加页签
        fragments.add(new tabFragment1());
        fragments.add(new tabFragment2());
        fragments.add(new TabFragment3());
        fragments.add(new tabFragment4());
        mFragmentAdater = new MyTabFragmeny(getFragmentManager(), fragments);
        mViewPager.setAdapter(mFragmentAdater);
        mViewPager.setOffscreenPageLimit(4);

        //将tablayout与viewpaager绑定
        mTabLayout.setupWithViewPager(mViewPager);

        //设置tabTitle
        mTabLayout.setTag(one);
        mTabLayout.setTag(two);
        mTabLayout.setTag(three);
        mTabLayout.setTag(four);
    }

    //嵌套fragment
    class MyTabFragmeny extends FragmentPagerAdapter {
        private String[] mTitles = new String[]{"娱乐", "新闻", "体育", "民生"};
        private List<Fragment> fragments;

        public MyTabFragmeny(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        /**
         * @return 返回页签标题的个数
         */
        @Override
        public int getCount() {
         //   return mTitles.length;
            //都可以，因为数目是相等的
            return  fragments.size();

        }

        /**
         *  当有页签的时候，此方法一定要实现，用来设置title的标题
         *
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

}
