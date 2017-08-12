package qa.reweyou.in.qa.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by master on 1/7/17.
 */

public class CustomViewPager extends ViewPager {
    private int pageposition = 1;

    public CustomViewPager(Context context) {
        super(context);

    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageposition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (pageposition == 1 || pageposition == 3)
            return false;
        else
            return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (pageposition == 1 || pageposition == 3)
            return true;
        else
            return super.onTouchEvent(ev);
    }

    /* @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (ev.getY() < Utils.convertpxFromDp(50) && pageposition == 2) {
                return false;
            } else return super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }*/


}
