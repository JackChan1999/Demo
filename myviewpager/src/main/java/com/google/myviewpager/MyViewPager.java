package com.google.myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/17 19:26
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class MyViewPager extends ViewGroup {


    private GestureDetector mDetector;
    private Scroller mScroller;

    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mDetector = new GestureDetector(getContext(),new GestureDetector
                .SimpleOnGestureListener(){
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float
                    distanceY) {
                scrollBy((int) distanceX,0);
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });

        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i=0; i<getChildCount(); i++){
            getChildAt(i).measure(widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i=0; i<getChildCount(); i++){
            getChildAt(i).layout(i*getWidth(),0,(i+1)*getWidth(),getHeight());
        }
    }


    int startX;
    int startY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDetector.onTouchEvent(event);
                startX = (int) event.getX();
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (event.getX() - startX);
                int dy = (int) (event.getY() - startY);
                if (Math.abs(dx) > Math.abs(dy)){
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                int pos = (getScrollX() + getWidth()/2)/getWidth();
                if (pos > getChildCount() -1){
                    pos = getChildCount() -1;
                }
                setCurrentItem(pos);

                break;
        }
        return super.onTouchEvent(event);
    }

    private void setCurrentItem(int pos) {
        int distance = pos*getWidth() - getScrollX();
        mScroller.startScroll(getScrollX(),0,distance,0,Math.abs(distance));
        invalidate();
        if (mListener != null){
            mListener.onPagerSelected(pos);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),0);
            invalidate();
        }
    }

    private OnPagerChangeListener mListener;

    public interface OnPagerChangeListener{
        void onPagerSelected(int pos);
    }

    public void setOnPagerChangeListener(OnPagerChangeListener listener){
        mListener = listener;
    }
}
