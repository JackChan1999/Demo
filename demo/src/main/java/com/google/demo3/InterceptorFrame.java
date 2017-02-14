package com.google.demo3;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/15 09:21
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class InterceptorFrame extends FrameLayout {

    public static final int ORIENTATION_UP = 0x1;
    public static final int ORIENTATION_DOWN = 0x2;
    public static final int ORIENTATION_LEFT = 0x4;
    public static final int ORIENTATION_RIGHT = 0x8;
    public static final int ORIENTATION_ALL = 0x10;

    private List<View> mInterceptView;
    private HashMap<View, Integer> mViewAndOrientation;
    private int mTouchSlop;
    private View mTarget;
    private int mLastX;
    private int mLastY;

    public InterceptorFrame(Context context) {
        super(context);
        init();
    }

    private void init() {
        mInterceptView = new LinkedList<>();
        mViewAndOrientation = new HashMap<>();
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    private void addInterceptView(View view, int orientation) {
        if (!mInterceptView.contains(view)) {
            mInterceptView.add(view);
            mViewAndOrientation.put(view, orientation);
        }
    }

    private void remove(View view) {
        mInterceptView.remove(view);
        mViewAndOrientation.remove(view);
    }

    private boolean isTouchInView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int motionX = (int) ev.getRawX();
        int motionY = (int) ev.getRawY();
        return motionX >= location[0] && motionX <= (location[0] + view.getWidth()) && motionY >=
                location[1] && motionY <= (location[1] + view.getHeight());
    }

    private View isTouchInterceptView(int orientation, MotionEvent ev) {
        for (View view : mInterceptView) {
            if (isTouchInView(view, ev) && (mViewAndOrientation.get(view) & orientation) ==
                    orientation && view.dispatchTouchEvent(ev)) {
                return view;
            }
        }
        return null;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        if (mTarget != null) {
            boolean flag = mTarget.dispatchTouchEvent(ev);
            if (flag && action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
                mTarget = null;
            }
            return flag;
        }

        int x = (int) ev.getX();
        int y = (int) ev.getY();
        View view = null;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                view = isTouchInterceptView(ORIENTATION_ALL, ev);
                break;
            case MotionEvent.ACTION_MOVE:
                int xDiff = Math.abs(x - mLastX);
                int yDiff = Math.abs(y - mLastY);
                if (xDiff > mTouchSlop && xDiff > yDiff) {
                    view = isTouchInterceptView((x - mLastX) > 0 ? ORIENTATION_RIGHT :
                            ORIENTATION_LEFT, ev);
                } else if (yDiff > mTouchSlop && yDiff > xDiff) {
                    view = isTouchInterceptView((y - mLastX) > 0 ? ORIENTATION_DOWN :
                            ORIENTATION_UP, ev);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mTarget = null;
                break;
        }
        if (view != null) {
            mTarget = view;
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }
}
