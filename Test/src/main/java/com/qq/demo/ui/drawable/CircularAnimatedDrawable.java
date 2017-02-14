package com.qq.demo.ui.drawable;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
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

import com.qq.demo.ui.widget.CircleProgressBar;

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

    public static final float HUE_ROSE = 330.0f;

    public CircularAnimatedDrawable(int i, float f) {
        this.mBorderWidth = f;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth(f);
        this.mPaint.setColor(i);
        this.mPaint.setStrokeCap(Cap.ROUND);
        this.mLoadingAnimator = createLoadingAnimator();
    }

    public void draw(Canvas canvas) {
        canvas.drawArc(this.fBounds, this.mStartAngle, this.mSweepAngle, false, this.mPaint);
    }

    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public int getOpacity() {
        return -2;
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.fBounds.left = (((float) rect.left) + (this.mBorderWidth / CircleProgressBar.BAR_WIDTH_DEF_DIP)) + 0.5f;
        this.fBounds.right = (((float) rect.right) - (this.mBorderWidth / CircleProgressBar.BAR_WIDTH_DEF_DIP)) - 0.5f;
        this.fBounds.top = (((float) rect.top) + (this.mBorderWidth / CircleProgressBar.BAR_WIDTH_DEF_DIP)) + 0.5f;
        this.fBounds.bottom = (((float) rect.bottom) - (this.mBorderWidth / CircleProgressBar.BAR_WIDTH_DEF_DIP)) - 0.5f;
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
        Keyframe ofFloat = Keyframe.ofFloat(0.0f, -90.0f);
        Keyframe ofFloat2 = Keyframe.ofFloat(0.5f, HUE_ROSE);
        Keyframe ofFloat3 = Keyframe.ofFloat(1.0f, 630.0f);
        PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe("startAngle", new Keyframe[]{ofFloat, ofFloat2, ofFloat3});
        PropertyValuesHolder ofFloat4 = PropertyValuesHolder.ofFloat("sweepAngle", new float[]{0.0f, -120.0f, 0.0f});
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofKeyframe, ofFloat4});
        ofPropertyValuesHolder.setDuration(1760);
        ofPropertyValuesHolder.setInterpolator(new LinearInterpolator());
        ofPropertyValuesHolder.setRepeatCount(ValueAnimator.INFINITE);
        return ofPropertyValuesHolder;
    }

    public float getSweepAngle() {
        return this.mSweepAngle;
    }

    public void setSweepAngle(float f) {
        this.mSweepAngle = f;
        if (this.mAllowLoading) {
            invalidateSelf();
        }
    }

    public float getStartAngle() {
        return this.mStartAngle;
    }

    public void setStartAngle(float f) {
        this.mStartAngle = f;
        if (this.mAllowLoading) {
            invalidateSelf();
        }
    }

    public void setAllowLoading(boolean z) {
        this.mAllowLoading = z;
    }
}
