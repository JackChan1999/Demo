package com.google.slidingmenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/20 11:35
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class DragLayout extends FrameLayout {

    private ViewDragHelper mHelper;
    private Status mStatus;
    private OnDragStatusChangeListener mListener;
    private ViewGroup mLeftContent;
    private ViewGroup mMainContent;
    private int mWidth;
    private int mHeight;
    private int mRange;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper = ViewDragHelper.create(this, mCallback);
    }

    //三种状态
    private enum Status {
        Close, Open, Dragging;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public Status getStatus() {
        return mStatus;
    }

    public Status updateStatus(float percent) {
        if (percent == 0f) {
            return Status.Close;
        } else if (percent == 1f) {
            return Status.Open;
        }
        return Status.Dragging;
    }

    //监听回调
    private interface OnDragStatusChangeListener {
        void onClose();

        void onOpen();

        void onDragging(float percent);
    }

    public void setOnDragStatusChangeListener(OnDragStatusChangeListener listener) {
        mListener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLeftContent = (ViewGroup) getChildAt(0);
        mMainContent = (ViewGroup) getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mRange = (int) (mWidth * 0.6);
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
    public void computeScroll() {
        if (mHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {

        //根据返回结果决定当前child是否可以拖拽
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mMainContent || child == mLeftContent;
        }

        //返回拖拽的范围，但是不对拖拽范围进行真正的限制，仅仅决定了拖拽执行速度
        @Override
        public int getViewHorizontalDragRange(View child) {
            return mRange;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mLeftContent) {
                left = fixedLeft(left);
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            int newleft = left;
            if (changedView == mLeftContent) {
                newleft = mMainContent.getLeft() + dx;
            }
            newleft = fixedLeft(newleft);
            if (changedView == mLeftContent) {
                mLeftContent.layout(left, 0, mWidth, mHeight);
                mMainContent.layout(newleft, 0, newleft + mWidth, mHeight);
            }
            dispatchDragEvent(newleft);
            invalidate();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (xvel == 0 && mMainContent.getLeft() > mRange / 2) {
                open();
            } else if (xvel > 0) {
                open();
            } else {
                close();
            }
        }
    };

    private void close() {
        close(true);
    }

    private void close(boolean isSmooth) {
        if (isSmooth){
            if (mHelper.smoothSlideViewTo(mMainContent,0,0)){
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }else {
            mMainContent.layout(0,0,mWidth,mHeight);
        }
    }

    private void open() {
        open(true);
    }

    private void open(boolean isSmooth) {
        if (isSmooth){
            if (mHelper.smoothSlideViewTo(mMainContent,mRange,0)){
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }else {
            mMainContent.layout(mRange,0,mRange+mWidth,mHeight);
        }
    }

    private void dispatchDragEvent(int newleft) {
        float percent = newleft / mRange;
        if (mListener != null) {
            mListener.onDragging(percent);
        }

        Status preStatus = mStatus;
        mStatus = updateStatus(percent);
        if (mStatus != preStatus) {
            if (mStatus == Status.Close) {
                if (mListener != null) {
                    mListener.onClose();
                }
            } else if (mStatus == Status.Open) {
                if (mListener != null) {
                    mListener.onOpen();
                }
            }

        }
        animViews(percent);
    }

    private void animViews(float percent) {
        mLeftContent.animate().x(evaluate(percent, 0.5f, 1.0f)).y(evaluate(percent, 0.5f, 1.0f))
                .translationX(evaluate(percent,-mWidth/2,0)).alpha(evaluate(percent,0.5f,1.0f));
        mMainContent.animate().x(evaluate(percent,1.0f,0.8f)).y(evaluate(percent,1.0f,0.8f));
        getBackground().setColorFilter((Integer) evaluateColor(percent, Color.BLACK, Color
                .TRANSPARENT),
                PorterDuff.Mode.SRC_OVER);
    }

    private int fixedLeft(int left) {
        if (left < 0) {
            return 0;
        } else if (left > mRange) {
            return mRange;
        }
        return left;
    }

    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }

    public Object evaluateColor(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int) ((startA + (int) (fraction * (endA - startA))) << 24) |
                (int) ((startR + (int) (fraction * (endR - startR))) << 16) |
                (int) ((startG + (int) (fraction * (endG - startG))) << 8) |
                (int) ((startB + (int) (fraction * (endB - startB))));
    }
}
