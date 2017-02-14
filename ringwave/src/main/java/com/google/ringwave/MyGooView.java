package com.google.ringwave;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/23 17:55
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class MyGooView extends View {

    //圆心
    private PointF mDragCenter;
    private PointF mStickCenter;
    private PointF mInitCenter;
    //半径
    private float mDragRadius = 0;
    private float mStickRadius = 0;
    private float mStickMinRadius = 0;
    private float mStickTempRadius = mStickRadius;
    //拖拽范围
    private float farest;
    private float resetDistance;
    private boolean isOutofRange = false;
    private boolean isDisappear = false;
    private Rect mRect;
    //动画
    private ValueAnimator mAnim;
    //画笔
    private Paint mPaintRed;
    private Paint mTextPaint;
    //消息数
    private String text = "";
    //状态栏高度
    private float mStatusBarHeight;

    private OnDisappearListener mListener;

    public MyGooView(Context context) {
        this(context, null);
    }

    public MyGooView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setNumber(String number){
        text = String.valueOf(number);
    }

    public void initCenter(float x, float y){
        mDragCenter = new PointF(x, y);
        mStickCenter = new PointF(x, y);
        mInitCenter = new PointF(x, y);
        invalidate();
    }

    public MyGooView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRect = new Rect(0,0,50,50);
        mDragRadius = 10f;
        mStickRadius = 10f;
        mStickMinRadius = 3f;
        farest = 80f;
        resetDistance = 40f;

        mPaintRed = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintRed.setColor(Color.RED);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(mDragRadius*1.2f);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private ShapeDrawable drawGooView(){
        Path path = new Path();
        float distance = GeometryUtil.getDistanceBetween2Points(mDragCenter, mStickCenter);
        mStickTempRadius = getCurrentRadius(distance);
        //斜率
        float xOffset = mStickCenter.x - mDragCenter.x;
        Double linek = null;
        if (xOffset != 0){
            linek = (double) (mStickCenter.y - mDragCenter.y)/xOffset;
        }
        //直线与圆的交点
        PointF[] dragPoints = GeometryUtil.getPoints(mDragCenter, mDragRadius, linek);
        PointF[] stickPoints = GeometryUtil.getPoints(mStickCenter, mStickTempRadius, linek);
        //控制点
        PointF controlPoint = GeometryUtil.getPointByPercent(mDragCenter, mStickCenter, 0.618f);
        //path
        path.moveTo();
        path.quadTo();
        path.lineTo();
        path.quadTo();
        path.close();
        //shapeDrawable
        ShapeDrawable drawable = new ShapeDrawable(new PathShape(path, 50f,50f));
        drawable.getPaint().setColor(Color.RED);
        return drawable;
    }

    private float getCurrentRadius(float distance) {
        distance = Math.min(distance, farest);
        float fraction = 0.2f + 0.8f*distance/farest;
        return GeometryUtil.evaluate(fraction,mStickRadius, mStickMinRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(0, -mStatusBarHeight);

        if (!isDisappear){
            if (!isOutofRange){
                ShapeDrawable drawable = drawGooView();
                drawable.setBounds(mRect);
                drawable.draw(canvas);
                canvas.drawCircle(mStickCenter.x, mStickCenter.y, mStickTempRadius, mPaintRed);
            }
            canvas.drawCircle(mDragCenter.x, mDragCenter.y, mDragRadius, mTextPaint);
            canvas.drawText(text, mDragCenter.x, mDragCenter.y + mDragRadius/2f, mPaintRed);
        }

        canvas.restore();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (MotionEventCompat.getActionMasked(event)){
            case MotionEvent.ACTION_DOWN:
                if (isAnimRunning()){
                    return false;
                }
                isOutofRange = false;
                isDisappear = false;
                updateDragCenter(event.getRawX(), event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                float distance = GeometryUtil.getDistanceBetween2Points(mDragCenter, mStickCenter);
                if (distance > farest){
                    isOutofRange = true;
                    updateDragCenter(event.getRawX(), event.getRawY());
                    return false;
                }
                updateDragCenter(event.getRawX(), event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                handleActionUp();
                break;
            default:
                break;
        }
        return true;
    }

    private void handleActionUp() {
        if (isOutofRange){
            if (GeometryUtil.getDistanceBetween2Points(mDragCenter, mStickCenter) < resetDistance){
                if (mListener != null){
                    mListener.onReset(isOutofRange);
                }
            }
            disappeared();
        }else {
            mAnim = ValueAnimator.ofFloat(1.0f);
            mAnim.setInterpolator(new OvershootInterpolator(4));

            final PointF startPoint = new PointF(mDragCenter.x, mDragCenter.y);
            final PointF endPoint = new PointF(mStickCenter.x, mStickCenter.y);
            mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float fraction = animation.getAnimatedFraction();
                    PointF p = GeometryUtil.getPointByPercent(startPoint, endPoint, fraction);
                    updateDragCenter(p.x, p.y);
                }
            });

            mAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (mListener != null){
                        mListener.onReset(isOutofRange);
                    }
                }
            });

            if (GeometryUtil.getDistanceBetween2Points(mDragCenter, mStickCenter) < 10){
                mAnim.setDuration(10);
            }else {
                mAnim.setDuration(500);
            }

            mAnim.start();
        }
    }

    private void disappeared() {
        isDisappear = true;
        invalidate();
        if (mListener != null){
            mListener.onDisappear(mDragCenter);
        }
    }

    private void updateDragCenter(float x, float y) {
        mDragCenter.x = x;
        mDragCenter.y = y;
        invalidate();
    }

    private boolean isAnimRunning() {
        if (mAnim != null && mAnim.isRunning()){
            return true;
        }
        return false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Rect frame = new Rect();
        getWindowVisibleDisplayFrame(frame);
        mStatusBarHeight = frame.top;
    }

    //接口监听
    private interface OnDisappearListener{
        void onDisappear(PointF dragPoint);
        void onReset(boolean isOutofRange);
    }

    public void setOnDisappearListener(OnDisappearListener listener){
        mListener = listener;
    }

    public OnDisappearListener getDisappearListener(){
        return mListener;
    }
}
