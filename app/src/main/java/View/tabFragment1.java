package View;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.personaldemo1.MyRecyclerAdater;
import com.example.administrator.personaldemo1.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
//recyclerview中加入头布局，头布局为一个viewpager我们做相应的图片适配

public class tabFragment1 extends Fragment {
    private static final String TAG = "tabFragment1";
    private RecyclerView mRcyclerView;
    //在此里面给Viewpager适配数据（加3个）
    private MyRecyclerAdater myRecyclerAdater;
    private List<String> mAdaterData = new ArrayList<>();
    private List<ImageView> mViewpagerData = new ArrayList<>();
    private boolean isrunning = false;//做轮询
    private  Handler mHandler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabfragment11, container, false);
        mRcyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        initData();
        myRecyclerAdater = new MyRecyclerAdater(mAdaterData);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcyclerView.setLayoutManager(manager);
        mRcyclerView.setAdapter(myRecyclerAdater);

        return view;//返回自己的布局

    }

    /**
     */
    private void initData() {

        //初始RecyclerView要用的化数据
        for (int i = 0; i < 40; i++) {
            mAdaterData.add("item" + i);
        }
        //初始化Viewpager要用的数据
        int[] imageIdres = new int[]{R.drawable.f3, R.drawable.f2, R.drawable.f1};
        /*for (int i = 0; i < imageIdres.length; i++) {
            ImageView image = new ImageView(getActivity());
            image.setImageResource(imageIdres[i]);
            mViewpagerData.add(image);
        }*/

        //以下做法可以让imageView的大小适配Viewpager
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(getActivity());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(imageIdres[i]);
            mViewpagerData.add(imageView);
        }
    }


    /**
     * Created by Administrator on 2017/3/8.
     */

    //注意里面用的是父类
    public class MyRecyclerAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<String> mData = new ArrayList<>();
        public static final int HEAD_TYPE = 0;
        public static final int BODY_TYPE = 1;

        /**
         * 构造函数
         *
         * @param mData
         */
        public MyRecyclerAdater(List<String> mData) {
            this.mData = mData;
        }
        //item布局
        //找到控件，根据传进来的item找控件
        class myBodyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            public myBodyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.tv);
            }
        }

        //头布局的Viewholder
        class MyHeadViewPagerHolder extends RecyclerView.ViewHolder {
            ViewPager viewPager;
            CirclePageIndicator indicator;
            public MyHeadViewPagerHolder(final View itemView) {
                super(itemView);
                viewPager = (ViewPager) itemView.findViewById(R.id.vp);
                indicator = (CirclePageIndicator) itemView.findViewById(R.id.indicator);


                //做viewpager的轮番
                if (mHandler==null) {
                    mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);

                            int currentItem = viewPager.getCurrentItem();
                            currentItem++;
                            if (currentItem ==mViewpagerData.size()) {//循环到最后 一个跳到第一页
                                currentItem = 0;
                            }
                            viewPager.setCurrentItem(currentItem);
                            mHandler.sendEmptyMessageDelayed(0, 3000);//持续发送延时3秒的信息，行程内循环
                        }
                    };
                    //保证内循环逻辑只执行一次
                    mHandler.sendEmptyMessageDelayed(0, 3000);//发送延时3秒的信息
                    viewPager.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    //停止广告自动轮番
                                    //删除Hander所有的信息
                                    mHandler.removeCallbacksAndMessages(null);
                                    break;
                                case MotionEvent.ACTION_UP:
                                    mHandler.sendEmptyMessageDelayed(0, 3000);
                                    break;
                                case MotionEvent.ACTION_CANCEL:
                                    //取消时间，当按下Viewpager后直接滑动listView，导致抬起事件无法响应，所以我们也要在此处做信息发送
                                    mHandler.sendEmptyMessageDelayed(0, 3000);
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });
                }
            }
        }

        /**
         * 当position==0；加载头布局
         * 通过position来判断我们到底是要加载那个布局
         *
         * @param position
         * @return
         */
        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return HEAD_TYPE;
            } else {
                return BODY_TYPE;
            }
        }

        /**
         * 创建布局视图
         *
         * @param parent
         * @param viewType
         * @return 用的父类
         */
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == HEAD_TYPE) {
                //加载头布局
                View view = View.inflate(parent.getContext(), R.layout.recyclerhead, null);
                MyHeadViewPagerHolder holder = new MyHeadViewPagerHolder(view);
                return holder;
            }
            if (viewType == BODY_TYPE) {
                View view = View.inflate(parent.getContext(), R.layout.itemrecycler, null);
                myBodyViewHolder holder = new myBodyViewHolder(view);
                return holder;
            }
            return null;
        }

        /**
         * 绑定数据,将数据填到具体的View
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyHeadViewPagerHolder) {
                ((MyHeadViewPagerHolder) holder).viewPager.setAdapter(new MyViewpagerAdater(mViewpagerData));
                ((MyHeadViewPagerHolder) holder).indicator.onPageSelected(0);//默认选择0
                //将Viewpager和指示器绑定
                ((MyHeadViewPagerHolder) holder).indicator.setViewPager(((MyHeadViewPagerHolder) holder).viewPager);
                ((MyHeadViewPagerHolder) holder).indicator.setSnap(true);


            }
            if (holder instanceof myBodyViewHolder) {
                Log.i(TAG, "onBindViewHolder: " + mData.toString() + "我就是个好人");
                ((myBodyViewHolder) holder).textView.setText(mData.get(position - 1));//position位1时。我们要数组中的0数据
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }



    //写Viewpager的适配器

    class MyViewpagerAdater extends PagerAdapter {
        private List<ImageView> data;

        public MyViewpagerAdater(List<ImageView> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (View) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = data.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(data.get(position));
        }
    }

    //当销毁的时候，将轮询关闭
    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
