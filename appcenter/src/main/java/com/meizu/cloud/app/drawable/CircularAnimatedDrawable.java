package com.meizu.cloud.app.drawable;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;

public class CircularAnimatedDrawable extends Drawable implements Animatable {
    public static final String START_ANGLE_PROPERTY = "startAngle";
    public static final String SWEEP_ANGLE_PROPERTY = "sweepAngle";
    private final long LOADING_ANIM_DURATION = 1760;
    private final RectF fBounds = new RectF();
    private boolean mAllowLoading = true;
    private float mBorderWidth;
    private Animator mLoadingAnimator = null;
    private Paint mPaint;
    private boolean mRunning;
    private float mStartAngle;
    private float mSweepAngle;

    public CircularAnimatedDrawable(int color, float borderWidth) {
        this.mBorderWidth = borderWidth;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth(borderWidth);
        this.mPaint.setColor(color);
        this.mPaint.setStrokeCap(Cap.ROUND);
        this.mLoadingAnimator = createLoadingAnimator();
    }

    public void draw(Canvas canvas) {
        canvas.drawArc(this.fBounds, this.mStartAngle, this.mSweepAngle, false, this.mPaint);
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
    }

    public int getOpacity() {
        return -2;
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.fBounds.left = (((float) bounds.left) + (this.mBorderWidth / 2.0f)) + 0.5f;
        this.fBounds.right = (((float) bounds.right) - (this.mBorderWidth / 2.0f)) - 0.5f;
        this.fBounds.top = (((float) bounds.top) + (this.mBorderWidth / 2.0f)) + 0.5f;
        this.fBounds.bottom = (((float) bounds.bottom) - (this.mBorderWidth / 2.0f)) - 0.5f;
    }

    public void start() {
        if (!isRunning()) {
            this.mRunning = true;
            this.mLoadingAnimator.start();
            invalidateSelf();
        }
    }

    public void stop() {
        if (isRunning()) {
            this.mRunning = false;
            this.mLoadingAnimator.cancel();
            invalidateSelf();
        }
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    private Animator createLoadingAnimator() {
        Keyframe key1 = Keyframe.ofFloat(0.0f, -90.0f);
        Keyframe key2 = Keyframe.ofFloat(0.5f, 330.0f);
        Keyframe key3 = Keyframe.ofFloat(1.0f, 630.0f);
        PropertyValuesHolder pvhStart = PropertyValuesHolder.ofKeyframe(START_ANGLE_PROPERTY, new Keyframe[]{key1, key2, key3});
        PropertyValuesHolder pvhSweep = PropertyValuesHolder.ofFloat(SWEEP_ANGLE_PROPERTY, new float[]{0.0f, -120.0f, 0.0f});
        ObjectAnimator loadingAnim = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{pvhStart, pvhSweep});
        loadingAnim.setDuration(1760);
        loadingAnim.setInterpolator(new LinearInterpolator());
        loadingAnim.setRepeatCount(-1);
        return loadingAnim;
    }

    public float getSweepAngle() {
        return this.mSweepAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        this.mSweepAngle = sweepAngle;
        if (this.mAllowLoading) {
            invalidateSelf();
        }
    }

    public float getStartAngle() {
        return this.mStartAngle;
    }

    public void setStartAngle(float startAngle) {
        this.mStartAngle = startAngle;
        if (this.mAllowLoading) {
            invalidateSelf();
        }
    }

    public void setAllowLoading(boolean allow) {
        this.mAllowLoading = allow;
    }

    public void setIndicatorColor(int color) {
        if (this.mPaint != null) {
            this.mPaint.setColor(color);
        }
    }

    public void setStrokeWidth(int width) {
        if (width > 0 && this.mBorderWidth != ((float) width)) {
            this.mBorderWidth = (float) width;
            onBoundsChange(getBounds());
            if (this.mPaint != null) {
                this.mPaint.setStrokeWidth(this.mBorderWidth);
            }
        }
    }
}
