package com.google.sweepview;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/18 21:41
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class SweepView extends ViewGroup {

    private View mContentView;
    private View mDeleteView;
    private int mDeleteWidth;
    private ViewDragHelper mHelper;
    private boolean isOpen;

    private OnSweepChangeListener mListener;

    public SweepView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mHelper = ViewDragHelper.create(this, null);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
        mDeleteView = getChildAt(1);
        mDeleteWidth = mDeleteView.getLayoutParams().width;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mContentView.measure(widthMeasureSpec, heightMeasureSpec);
        int deleteWidthSpec = MeasureSpec.makeMeasureSpec(mDeleteWidth, MeasureSpec.EXACTLY);
        mDeleteView.measure(deleteWidthSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mContentView.layout(0, 0, mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight
                ());
        mDeleteView.layout(mContentView.getMeasuredWidth(), 0, mContentView.getMeasuredWidth() +
                mDeleteWidth, mDeleteView.getMeasuredHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.processTouchEvent(event);
        return true;
    }

    class ViewDragCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mContentView || child == mDeleteView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int contentWidth = mContentView.getMeasuredWidth();
            if (child == mContentView) {
                if (left < 0 && left < -mDeleteWidth) {
                    return -mDeleteWidth;
                } else if (left > 0) {
                    return 0;
                }
            } else if (child == mDeleteView) {
                if (left < contentWidth - mDeleteWidth) {
                    return contentWidth - mDeleteWidth;
                } else if (left > contentWidth) {
                    return contentWidth;
                }
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            ViewCompat.postInvalidateOnAnimation(SweepView.this);
            int contentWidth = mContentView.getMeasuredWidth();
            if (changedView == mContentView) {
                mDeleteView.layout(contentWidth + left, 0, contentWidth + left + mDeleteWidth,
                        mDeleteView.getMeasuredHeight());
            } else if (changedView == mDeleteView) {
                mContentView.layout(left - contentWidth, 0, left, mContentView.getMeasuredHeight());
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int left = mContentView.getLeft();
            if (-left > mDeleteWidth / 2) {
                open();
            } else {
                close();
            }
        }
    }

    private void close() {
        isOpen = false;
        if (mListener != null) {
            mListener.onSweepChange(this, isOpen);
        }

        mHelper.smoothSlideViewTo(mContentView, 0, 0);
        mHelper.smoothSlideViewTo(mDeleteView, mContentView.getMeasuredWidth(), 0);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void open() {
        isOpen = true;
        if (mListener != null) {
            mListener.onSweepChange(this, isOpen);
        }

        mHelper.smoothSlideViewTo(mContentView, -mDeleteWidth, 0);
        mHelper.smoothSlideViewTo(mDeleteView, mContentView.getMeasuredWidth() - mDeleteWidth,
                mContentView.getMeasuredWidth());
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public void computeScroll() {
        if (mHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public interface OnSweepChangeListener {
        void onSweepChange(SweepView sweepView, boolean isOpen);
    }

    public void setOnSweepChangeListener(OnSweepChangeListener listener) {
        mListener = listener;
    }

}

