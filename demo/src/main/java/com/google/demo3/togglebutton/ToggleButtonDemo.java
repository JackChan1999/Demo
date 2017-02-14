package com.google.demo3.togglebutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/16 18:55
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class ToggleButtonDemo extends View {

    private Bitmap mSwitchBg;
    private Bitmap mSlideBg;

    private int mCurrentX;

    private boolean isSliding = false;

    private ToggleState mToggleState = ToggleState.Open;

    private OnToggleStateChangeListener mListener;

    private enum ToggleState {
        Open, Close
    }

    public ToggleButtonDemo(Context context) {
        this(context, null);
    }

    public ToggleButtonDemo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToggleButtonDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSwitchBackground(int resId) {
        mSwitchBg = BitmapFactory.decodeResource(getResources(), resId);
    }

    public void setSlideBackground(int resId) {
        mSlideBg = BitmapFactory.decodeResource(getResources(), resId);
    }

    public void setToggleState(ToggleState state) {
        mToggleState = state;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mSwitchBg != null) {
            setMeasuredDimension(mSwitchBg.getWidth(), mSwitchBg.getHeight());
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mSwitchBg != null){
            canvas.drawBitmap(mSwitchBg,0,0,null);
        }

        if (mSlideBg == null){
            return;
        }
        int slideWidth = mSlideBg.getWidth();
        int switchWidth = mSwitchBg.getWidth();
        int maxLeft = switchWidth - slideWidth;
        int left = mCurrentX - slideWidth/2;

        if (isSliding){
            if (left < 0) left = 0;
            if (left > maxLeft) left = maxLeft;
            canvas.drawBitmap(mSlideBg,left,0,null);
        }else {
            if (mToggleState == ToggleState.Open){
                canvas.drawBitmap(mSlideBg,maxLeft,0,null);
            }else if (mToggleState == ToggleState.Close){
                canvas.drawBitmap(mSlideBg,0,0,null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCurrentX = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isSliding = true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                isSliding = false;
                if (mCurrentX < mSwitchBg.getWidth() / 2 && mToggleState != ToggleState.Close) {
                    mToggleState = ToggleState.Close;
                    if (mListener != null) {
                        mListener.onToggleStateChange(mToggleState);
                    }
                } else if (mCurrentX >= mSwitchBg.getWidth() / 2 && mToggleState != ToggleState.Open) {
                    mToggleState = ToggleState.Open;
                    if (mListener != null) {
                        mListener.onToggleStateChange(mToggleState);
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    public interface OnToggleStateChangeListener {
        void onToggleStateChange(ToggleState state);
    }

    public void setOnToggleStateChangeListener(OnToggleStateChangeListener listener) {
        mListener = listener;
    }
}
