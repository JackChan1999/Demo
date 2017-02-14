package com.meizu.cloud.app.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import flyme.support.v7.widget.FadeViewPager;

public class ChildViewPager extends FadeViewPager {
    private int a;
    private PointF b;
    private boolean c;
    private PointF d;

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.a = 0;
        this.b = new PointF();
        this.c = true;
        this.d = new PointF();
        this.a = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public ChildViewPager(Context context) {
        super(context);
        this.a = 0;
        this.b = new PointF();
        this.c = true;
        this.d = new PointF();
        this.a = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        getParent().requestDisallowInterceptTouchEvent(true);
        if (action == 0) {
            this.d.x = event.getX();
            this.d.y = event.getY();
            this.c = true;
        } else if (action == 2 && Math.abs(event.getY() - this.d.y) > ((float) this.a) && this.c) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.onInterceptTouchEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int oldX = getScrollX();
        boolean handle = super.onTouchEvent(event);
        int newX = getScrollX();
        for (int i = 0; i < getChildCount(); i++) {
            View itemView = getChildAt(i);
            if ((itemView.getTag() instanceof Integer) && ((Integer) itemView.getTag()).intValue() == getCurrentItem()) {
                switch (event.getAction()) {
                    case 0:
                        this.c = true;
                        this.b.x = event.getX();
                        this.b.y = event.getY();
                        break;
                    case 2:
                        float dx = Math.abs(event.getX() - this.b.x);
                        float dy = Math.abs(event.getY() - this.b.y);
                        if (dx <= dy || dx <= ((float) this.a) || !this.c) {
                            if (dy > ((float) this.a) && this.c) {
                                break;
                            }
                        }
                        this.c = false;
                        event.setAction(3);
                        itemView.dispatchTouchEvent(event);
                        break;
                        break;
                }
                if (this.c) {
                    itemView.dispatchTouchEvent(event);
                }
                return handle;
            }
        }
        return handle;
    }
}
