package com.qq.demo.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.qq.demo.R;

/**
 * Created by qijian on 16/4/18.
 */
public class IrregularWaveView extends View {

    private Paint mPaint;
    private int mItemWaveLength = 0;
    private int dx=0;
    private Rect src;
    private Rect dst;

    private Bitmap BmpSRC,BmpDST;
    private PorterDuffXfermode mXfermode;

    public IrregularWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();

        BmpDST = BitmapFactory.decodeResource(getResources(), R.mipmap.wave_bg,null);
        BmpSRC = BitmapFactory.decodeResource(getResources(),R.mipmap.circle_shape,null);
        mItemWaveLength = BmpDST.getWidth();
        dst = new Rect(0,0,BmpSRC.getWidth(),BmpSRC.getHeight());

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

        startAnim();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //先画上圆形
        canvas.drawBitmap(BmpSRC,0,0,mPaint);

        //再画上结果
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(BmpDST,src,dst,mPaint);
        mPaint.setXfermode(mXfermode);
        canvas.drawBitmap(BmpSRC,0,0,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }


    public void startAnim(){
        ValueAnimator animator = ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(4000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int)animation.getAnimatedValue();
                src = new Rect(dx,0,dx+BmpSRC.getWidth(),BmpSRC.getHeight());
                postInvalidate();
            }
        });
        animator.start();
    }
}
