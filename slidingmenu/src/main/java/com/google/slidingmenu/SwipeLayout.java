package com.google.slidingmenu;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/21 09:59
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class SwipeLayout extends FrameLayout {

    private ViewDragHelper mHelper;
    private View mBackView;
    private View mFrontView;
    private int mWidth;
    private int mHeight;
    private int mRange;
    private Status mStatus;
    private OnSwipelayoutListener mListener;

    public SwipeLayout(Context context) {
        super(context);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper = ViewDragHelper.create(this, mCallback);
    }

    //三种状态
    private enum Status {
        Open, Close, Dragging;
    }

    public Status updateStatus() {
        int left = mFrontView.getLeft();
        if (left == -mRange){
            return Status.Open;
        }else if (left ==0){
            return Status.Close;
        }
        return Status.Dragging;
    }

    //监听接口
    private interface OnSwipelayoutListener {
        void Open(SwipeLayout swipeLayout);

        void Close(SwipeLayout swipeLayout);

        void Dragging(SwipeLayout swipeLayout);

        void onStartOpen(SwipeLayout swipeLayout);

        void onStartClose(SwipeLayout swipeLayout);
    }

    public void setOnSwipelayoutListener(OnSwipelayoutListener listener) {
        mListener = listener;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        layoutContent(false);
    }

    private void layoutContent(boolean isOpen) {
        Rect frontRect = computeFrontViewRect(isOpen);
        mFrontView.layout(frontRect.left, frontRect.top, frontRect.right, frontRect.bottom);
        Rect backRect = computeBackViewRect(frontRect);
        mBackView.layout(backRect.left, backRect.top, backRect.right, backRect.bottom);
    }

    private Rect computeBackViewRect(Rect frontRect) {
        int left = frontRect.right;
        return new Rect(left, 0, left + mRange, mHeight);
    }

    private Rect computeFrontViewRect(boolean isOpen) {
        int left = 0;
        if (isOpen) {
            left = -mRange;
        }
        return new Rect(left, 0, left + mWidth, mHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackView = getChildAt(0);
        mFrontView = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = mFrontView.getMeasuredWidth();
        mHeight = mFrontView.getMeasuredHeight();
        mRange = mBackView.getMeasuredWidth();
    }

    @Override
    public void computeScroll() {
        if (mHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mBackView || child == mFrontView;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mRange;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mFrontView) {
                if (left < -mRange) {
                    left = -mRange;
                } else if (left > 0) {
                    left = 0;
                }
            } else if (child == mBackView) {
                if (left > mWidth){
                    left = mWidth;
                }else if (left < mWidth - mRange){
                    left = mWidth - mRange;
                }
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == mFrontView){
                mBackView.offsetLeftAndRight(dx);
            }else if (changedView == mBackView){
                mFrontView.offsetLeftAndRight(dx);
            }
            dispatchSwipeEvent();
            invalidate();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }
    };

    private void dispatchSwipeEvent() {
        if (mListener != null){
            mListener.Dragging(this);
        }
        Status preStatus = mStatus;
        mStatus = updateStatus();
        if (mStatus != preStatus && mListener != null){
            if (mStatus == Status.Close){
                mListener.Close(this);
            }else if (mStatus == Status.Open){
                mListener.Open(this);
            }else if (mStatus == Status.Dragging){
                if (preStatus == Status.Close){
                    mListener.onStartOpen(this);
                }else if (preStatus == Status.Open){
                    mListener.onStartClose(this);
                }
            }
        }
    }
}
