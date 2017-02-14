package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.SeekBar;

public class SkposSeekBar extends SeekBar {
    private ValueAnimator mAnimator;
    private Interpolator mInterpolator;
    private float mOffset;
    private int mProcess;
    private OnSkipAnimationListener mSkipAnimationListener;

    public interface OnSkipAnimationListener {
        void onAnimationEnd();

        void onAnimationStart();
    }

    public SkposSeekBar(Context context) {
        this(context, null);
    }

    public SkposSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mProcess = 0;
        init();
    }

    public SkposSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mProcess = 0;
        init();
    }

    private void init() {
        if (VERSION.SDK_INT >= 21) {
            this.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        } else {
            this.mInterpolator = new LinearInterpolator();
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (getProgressDrawable() != null) {
            this.mOffset = (float) (getProgressDrawable().getBounds().width() / getMax());
        }
    }

    public void setSikpProgress(int i) {
        float paddingLeft = ((float) getPaddingLeft()) + (((float) i) * this.mOffset);
        float paddingLeft2 = ((float) getPaddingLeft()) + (((float) getProgress()) * this.mOffset);
        int progress = getProgress();
        if (this.mAnimator == null || !this.mAnimator.isRunning()) {
            startAnimation(progress, paddingLeft2, paddingLeft, 384);
        } else {
            this.mAnimator.cancel();
        }
    }

    private void startAnimation(final int i, final float f, final float f2, int i2) {
        this.mAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (f2 > f) {
                    SkposSeekBar.this.mProcess = ((int) ((floatValue * (f2 - f)) / SkposSeekBar.this.mOffset)) + i;
                } else {
                    SkposSeekBar.this.mProcess = i - ((int) ((floatValue * (f - f2)) / SkposSeekBar.this.mOffset));
                }
                SkposSeekBar.this.setProgress(SkposSeekBar.this.mProcess);
            }
        });
        this.mAnimator.setInterpolator(this.mInterpolator);
        this.mAnimator.setDuration((long) i2);
        this.mAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                if (SkposSeekBar.this.mSkipAnimationListener != null) {
                    SkposSeekBar.this.mSkipAnimationListener.onAnimationStart();
                }
                super.onAnimationStart(animator);
            }

            public void onAnimationEnd(Animator animator) {
                if (SkposSeekBar.this.mSkipAnimationListener != null) {
                    SkposSeekBar.this.mSkipAnimationListener.onAnimationEnd();
                }
                super.onAnimationEnd(animator);
            }
        });
        this.mAnimator.start();
    }

    public void setSkipAnimationListener(OnSkipAnimationListener onSkipAnimationListener) {
        this.mSkipAnimationListener = onSkipAnimationListener;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(SkposSeekBar.class.getName());
    }
}
