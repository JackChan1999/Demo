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
    private float a;
    private Interpolator b;
    private int c;
    private ValueAnimator d;
    private a e;

    public interface a {
        void a();

        void b();
    }

    public SkposSeekBar(Context context) {
        this(context, null);
    }

    public SkposSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.c = 0;
        a();
    }

    public SkposSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.c = 0;
        a();
    }

    private void a() {
        if (VERSION.SDK_INT >= 21) {
            this.b = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        } else {
            this.b = new LinearInterpolator();
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (getProgressDrawable() != null) {
            this.a = (float) (getProgressDrawable().getBounds().width() / getMax());
        }
    }

    public void setSikpProgress(int progress) {
        float endPos = ((float) getPaddingLeft()) + (((float) progress) * this.a);
        float startPos = ((float) getPaddingLeft()) + (((float) getProgress()) * this.a);
        int currentProgress = getProgress();
        if (this.d == null || !this.d.isRunning()) {
            a(currentProgress, startPos, endPos, 384);
        } else {
            this.d.cancel();
        }
    }

    private void a(final int curProgress, final float startPos, final float endPos, int duration) {
        this.d = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.d.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ SkposSeekBar d;

            public void onAnimationUpdate(ValueAnimator animation) {
                float temp = ((Float) animation.getAnimatedValue()).floatValue();
                if (endPos > startPos) {
                    this.d.c = curProgress + ((int) (((endPos - startPos) * temp) / this.d.a));
                } else {
                    this.d.c = curProgress - ((int) (((startPos - endPos) * temp) / this.d.a));
                }
                this.d.setProgress(this.d.c);
            }
        });
        this.d.setInterpolator(this.b);
        this.d.setDuration((long) duration);
        this.d.addListener(new AnimatorListenerAdapter(this) {
            final /* synthetic */ SkposSeekBar a;

            {
                this.a = r1;
            }

            public void onAnimationStart(Animator animation) {
                if (this.a.e != null) {
                    this.a.e.b();
                }
                super.onAnimationStart(animation);
            }

            public void onAnimationEnd(Animator animation) {
                if (this.a.e != null) {
                    this.a.e.a();
                }
                super.onAnimationEnd(animation);
            }
        });
        this.d.start();
    }

    public void setSkipAnimationListener(a mSkipAnimationListener) {
        this.e = mSkipAnimationListener;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(SkposSeekBar.class.getName());
    }
}
