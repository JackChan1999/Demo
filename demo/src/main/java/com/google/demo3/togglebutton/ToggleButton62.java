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
 * 创建日期 ：2016/5/16 10:33
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class ToggleButton62 extends View {

    private final static int STATE_NONE = 0;
    private final static int STATE_DOWN = 1;
    private final static int STATE_MOVE = 2;
    private final static int STATE_UP = 3;
    private int mState = STATE_NONE;

    private float mCurrentX;

    private Bitmap mSwitchBg;
    private Bitmap mSlideBg;

    private boolean isOpen;

    private OnSwitchChangeListener mListener;


    public ToggleButton62(Context context) {
        this(context, null);
    }

    public ToggleButton62(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToggleButton62(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSwitchBackground(int resId) {
        mSwitchBg = BitmapFactory.decodeResource(getResources(), resId);
    }

    public void setSlideBackground(int resId) {
        mSlideBg = BitmapFactory.decodeResource(getResources(), resId);
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
        if (mSwitchBg != null) {
            canvas.drawBitmap(mSwitchBg, 0, 0, null);
        }

        if (mSlideBg == null) {
            return;
        }

        int slideWidth = mSlideBg.getWidth();
        int switchWidth = mSwitchBg.getWidth();
        int maxLeft = slideWidth - switchWidth;
        float left = mCurrentX - slideWidth/2f;

        switch (mState) {
            case STATE_DOWN:
            case STATE_MOVE:
                if (!isOpen){
                    //关闭时，点击滑块的左侧，滑块不动
                    if (mCurrentX < slideWidth/2f){
                        canvas.drawBitmap(mSlideBg,0,0,null);
                    }else {
                        //关闭时，点击滑块的右侧，滑块的中线和按下的X坐标对齐
                        if (left > maxLeft){
                            left = maxLeft;
                        }
                        canvas.drawBitmap(mSlideBg,left,0,null);
                    }
                }else {
                    if (mCurrentX < switchWidth - slideWidth/2f){
                        //点击左侧，滑块中线和按下的x坐标对齐
                        if (left < 0){
                            left = 0;
                        }
                        canvas.drawBitmap(mSlideBg,left,0,null);
                    }else {
                        //点击右侧，滑块不动
                        canvas.drawBitmap(mSlideBg,maxLeft,0,null);
                    }
                }

                break;
            case STATE_NONE:
            case STATE_UP:
                if (isOpen){
                    canvas.drawBitmap(mSlideBg,maxLeft,0,null);
                }else {
                    canvas.drawBitmap(mSlideBg,0,0,null);
                }
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCurrentX = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mState = STATE_DOWN;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                mState = STATE_MOVE;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mState = STATE_UP;
                int switchWidth = mSwitchBg.getWidth();
                if (mCurrentX < switchWidth / 2f && isOpen) {
                    isOpen = false;
                    if (mListener != null) {
                        mListener.onSwitchChange(isOpen);
                    }
                } else if (mCurrentX >= switchWidth / 2f && !isOpen) {
                    isOpen = true;
                    if (mListener != null) {
                        mListener.onSwitchChange(isOpen);
                    }
                }
                invalidate();
                break;
        }
        return true;
    }

    public interface OnSwitchChangeListener {
        void onSwitchChange(boolean isOpen);
    }

    public void setOnSwitchChangeListener(OnSwitchChangeListener listener) {
        mListener = listener;
    }
}

