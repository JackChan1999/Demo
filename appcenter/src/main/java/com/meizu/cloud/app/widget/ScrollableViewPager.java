package com.meizu.cloud.app.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ScrollableViewPager extends ViewPager {
    private boolean a = true;

    public ScrollableViewPager(Context context) {
        super(context);
    }

    public ScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.a && super.onTouchEvent(event);
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.a && super.onInterceptTouchEvent(event);
    }

    public void setPageScrollEnabled(boolean b) {
        this.a = b;
    }
}
