package com.google.slidingmenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/21 16:58
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class QuickIndexBar extends View {

    private static final String[] LETTERS = new String[]{
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X",
            "Y", "Z"
    };
    private int cellWidth;
    private int cellHeight;
    private Paint mPaint;
    private OnUpdateLettersListener mListener;

    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private interface OnUpdateLettersListener{
        void onUpdateLetters(String letter);
    }

    public void setOnUpdateLettersListener(OnUpdateLettersListener listener){
        mListener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellWidth = getMeasuredWidth();
        int mHeight = getMeasuredHeight();
        cellHeight = (int) (mHeight*1.0f/LETTERS.length);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i=0; i<LETTERS.length; i++){
            String text = LETTERS[i];
            int x = (int) (cellWidth/2.0f - mPaint.measureText(text)/2.0f);
            Rect bounds = new Rect();
            mPaint.getTextBounds(text,0,text.length(),bounds);
            int textHeight = bounds.height();
            int y = (int) (cellHeight/2.0f + textHeight/2.0f + i*cellHeight);
            mPaint.setColor(touchIndex == i ? Color.GRAY : Color.WHITE);
            canvas.drawText(text,x,y,mPaint);
        }
    }

    private int touchIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = -1;
        switch (MotionEventCompat.getActionMasked(event)){
            case MotionEvent.ACTION_DOWN:
                index = (int) (getY()/cellHeight);
                if (index > 0 && index < LETTERS.length){
                    if (index != touchIndex){
                        if (mListener != null){
                            mListener.onUpdateLetters(LETTERS[index]);
                        }
                        touchIndex = index;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                index = (int) (getY()/cellHeight);
                if (index > 0 && index < LETTERS.length){
                    if (index != touchIndex){
                        if (mListener != null){
                            mListener.onUpdateLetters(LETTERS[index]);
                        }
                        touchIndex = index;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                break;
        }
        invalidate();
        return true;
    }
}
