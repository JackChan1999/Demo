package com.google.demo3.togglebutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.google.demo3.R;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/15 21:50
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class ToggleButton66 extends View {

    private Bitmap mSwitchBg;
    private Bitmap mSlideBg;

    private boolean isOpen;
    private boolean isClick;

    private int MAX_LEFT;
    private int mSlideLeft;

    public ToggleButton66(Context context) {
        this(context,null);
    }

    public ToggleButton66(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToggleButton66(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.ToggleButton66);
        for (int i=0; i<typedArray.getIndexCount(); i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.ToggleButton66_isOpen:
                    isOpen = typedArray.getBoolean(attr,false);
                    break;
                case R.styleable.ToggleButton66_slide:
                    int slideId = typedArray.getInt(attr,-1);
                    if (slideId > 0){
                        mSwitchBg = BitmapFactory.decodeResource(getResources(),slideId);
                    }
                    break;
            }
        }
        typedArray.recycle();
        invalidate();
    }

    private void initView() {
        mSwitchBg = BitmapFactory.decodeResource(getResources(), R.mipmap.switch_background);
        mSlideBg = BitmapFactory.decodeResource(getResources(), R.mipmap.slide_button);

        MAX_LEFT = mSwitchBg.getWidth() - mSlideBg.getWidth();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClick){
                    if (isOpen){
                        isOpen = false;
                        mSlideLeft = 0;
                    }else {
                        isOpen = true;
                        mSlideLeft = MAX_LEFT;
                    }
                    invalidate();
                    if (mListener != null){
                        mListener.onSwitchChangeListener(isOpen);
                    }
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mSwitchBg.getWidth(),mSwitchBg.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mSwitchBg,0,0,null);
        canvas.drawBitmap(mSlideBg,mSlideLeft,0,null);
    }

    private int startX;
    private int moveX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) event.getX();
                int dx = endX - startX;

                mSlideLeft += dx;
                moveX += Math.abs(dx);

                if (mSlideLeft > MAX_LEFT){
                    mSlideLeft = MAX_LEFT;
                }
                if (mSlideLeft < 0){
                    mSlideLeft = 0;
                }
                invalidate();
                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (moveX > 5){
                    isClick = false;
                }else {
                    isClick = true;
                }

                moveX = 0;

                if (!isClick){
                    if (mSlideLeft < MAX_LEFT/2){
                        isOpen = false;
                        mSlideLeft = 0;
                    }else {
                        isOpen = true;
                        mSlideLeft = MAX_LEFT;
                    }
                }
                invalidate();
                if (mListener != null){
                    mListener.onSwitchChangeListener(isOpen);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public interface OnSwitchChangeListener{
        void onSwitchChangeListener(boolean isOpen);
    }

    private OnSwitchChangeListener mListener;

    public void setOnSwitchChangeListener(OnSwitchChangeListener listener){
        mListener = listener;
    }
}
