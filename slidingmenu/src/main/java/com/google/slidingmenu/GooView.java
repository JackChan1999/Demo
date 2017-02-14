package com.google.slidingmenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/21 22:11
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class GooView extends View {

    private Paint mPaint;

    public GooView(Context context) {
        this(context, null);
    }

    public GooView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GooView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    //拖拽圆，固定园，贝塞尔曲线（控制点）

    PointF[] mStickPoints = new PointF[]{
            new PointF(250f,250f),
            new PointF(250f,350f)
    };

    PointF[] mDragPoints = new PointF[]{
        new PointF(50f,250f),
        new PointF(50f,350f)
    };

    PointF mControlPoint = new PointF(150f, 300f);//控制点
    PointF mDragCenter = new PointF(80f,80f);//拖拽园圆心
    float mDragRadius = 14f;//拖拽园半径

    PointF mStickCenter = new PointF(150f, 150f);
    float mStickRadius = 12f;

    private int statusBarHeight;

    private float farestDistance = 80f;

    private boolean isOutOfRange;
    private boolean isDisappear;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}
