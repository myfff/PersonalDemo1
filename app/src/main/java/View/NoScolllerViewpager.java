package View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/3/8.
 */

public class NoScolllerViewpager extends ViewPager {
    /*
   /*注意
   * onInterceptTouchEvent()是ViewGroup的一个方法，目的是在系统向该ViewGroup及其各个childView触发onTouchEvent()之前对相关事件进行一次拦截.

down事件首先会传递到onInterceptTouchEvent()方法

1，如果该ViewGroup的onInterceptTouchEvent()在接收到down事件处理完成之后return false，那么后续的move, up等事件将继续会先传递给该ViewGroup，之后才和down事件一样传递给最终的目标view的onTouchEvent()处理。
2，如果该ViewGroup的onInterceptTouchEvent()在接收到down事件处理完成之后return true，那么后续的move, up等事件将不再传递给onInterceptTouchEvent()，而是和down事件一样传递给该ViewGroup的onTouchEvent()处理，注意，目标view将接收不到任何事件。
3，如果最终需要处理事件的view的onTouchEvent()返回了false，那么该事件将被传递至其上一层次的view的onTouchEvent()处理。

4，如果最终需要处理事件的view 的onTouchEvent()返回了true，那么后续事件将可以继续传递给该view的onTouchEvent()处理。
*/

    public NoScolllerViewpager(Context context) {
        super(context);
    }


    //当初始化一个对象的时候需要将此attr传进去
    public NoScolllerViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;//此层并未进行事件的拦截，事件向下传
    }


    /**
     * 其实这里不管我们返回真假，此层Vieewpager都不会滑动，但又为了要向下传递事件，我们只能返回true，表示消费了此事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //，因为我们还向下传递事件呢，不返回给上层处理（不能反悔true），
        // 但我们又想让此层Viewpager不滑动，我们只能返回继承ViewPager重写此方法不做任何逻辑处理
        return true;//返回true表示处理事件
    }
}
