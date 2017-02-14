package com.google.ringwave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/24 22:43
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class MyListView extends ListView {

    private ImageView mImage;
    private int mOriginalHeight;
    private int mIntrinsicHeight;

    public MyListView(Context context) {
        super(context);
    }

    public void setParallaxImage(ImageView image){
        mImage = image;
        mOriginalHeight = image.getHeight();
        mIntrinsicHeight = image.getDrawable().getIntrinsicHeight();
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int
            scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean
            isTouchEvent) {
        if (isTouchEvent && deltaY < 0){
            if (mImage.getHeight() < mIntrinsicHeight){
                int newHeight = mImage.getHeight() + Math.abs(deltaY/3);
                mImage.getLayoutParams().height = newHeight;
                mImage.requestLayout();//重新测量和布局
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)){
            case MotionEvent.ACTION_UP:
                //执行回弹动画
                int startHeight = mImage.getHeight();
                int endHeight = mOriginalHeight;
                ResetAnimation animation = new ResetAnimation(mImage, startHeight, endHeight);
                startAnimation(animation);
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void valueAnimator(final int startHeight, final int endHeight){
        ValueAnimator animator = ValueAnimator.ofInt(1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                int newHeight = evaluate(fraction, startHeight, endHeight);
                mImage.getLayoutParams().height = newHeight;
                mImage.requestLayout();
            }
        });

        animator.setInterpolator(new OvershootInterpolator());
        animator.setDuration(500);
        animator.start();
    }

    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int)(startInt + fraction * (endValue - startInt));
    }
}
