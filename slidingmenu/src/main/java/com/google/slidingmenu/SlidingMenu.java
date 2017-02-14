package com.google.slidingmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/19 20:40
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class SlidingMenu extends ViewGroup {


    private View mMenuView;
    private View mMainView;
    private final int MENU_VIEW = 0;
    private final int MAIN_VIEW = 1;
    private int CURRENT_VIEW = MAIN_VIEW;
    private int mMenuWidth;
    private final Scroller mScroller;
    private final int mTouchSlop;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(getContext());
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
        mMenuWidth = mMenuView.getLayoutParams().width;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMainView.measure(widthMeasureSpec,heightMeasureSpec);
        int menuWidthSpec = MeasureSpec.makeMeasureSpec(mMenuWidth, MeasureSpec.EXACTLY);
        mMenuView.measure(menuWidthSpec,heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mMainView.layout(l,t,r,b);
        mMenuView.layout(-mMenuWidth,0,0,b);
    }

    private float downX;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getX() - downX) > mTouchSlop){
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float diffX = downX - moveX;
                int scrollX = (int) (getScrollX() + diffX + 0.5f);
                if (scrollX < -mMenuWidth){
                    scrollTo(-mMenuWidth,0);
                }else if (scrollX > 0){
                    scrollTo(0,0);
                }else {
                    scrollBy((int) diffX,0);
                }
                downX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollX() < - mMenuWidth/2){
                    CURRENT_VIEW = MENU_VIEW;
                }else {
                    CURRENT_VIEW = MAIN_VIEW;
                }
                switchView();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void switchView() {
        int dx = 0;
        if (CURRENT_VIEW == MENU_VIEW){
            dx = -mMenuWidth - getScrollX();
        }else {
            dx = -getScrollX();
        }
        mScroller.startScroll(getScrollX(),0,dx,0,Math.abs(dx));
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),0);
            invalidate();
        }
    }

    public boolean isMenuShow(){
        return CURRENT_VIEW == MENU_VIEW;
    }

    public void hideMenu(){
        CURRENT_VIEW = MAIN_VIEW;
        switchView();
    }

    public void showMenu(){
        CURRENT_VIEW = MENU_VIEW;
        switchView();
    }
}
