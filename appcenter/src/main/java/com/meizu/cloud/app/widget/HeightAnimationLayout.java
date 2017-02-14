package com.meizu.cloud.app.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.LinearLayout;
import org.apache.commons.io.FileUtils;

public class HeightAnimationLayout extends LinearLayout {
    private AnimatorSet a;
    private int b;
    private final long c = 300;
    private final long d = 200;
    private boolean e;
    private boolean f;

    public HeightAnimationLayout(Context context) {
        super(context);
    }

    public HeightAnimationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeightAnimationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.b == 0 || this.b < getMeasuredHeight()) {
            this.b = getMeasuredHeight();
            LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = 0;
            setLayoutParams(layoutParams);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.e) {
            if (this.b == 0) {
                onMeasure(MeasureSpec.getMode(FileUtils.ONE_GB), MeasureSpec.getMode(FileUtils.ONE_GB));
            }
            this.e = false;
            ValueAnimator heightAnimator = a(0, this.b, true);
            ObjectAnimator alphaAnimator = a(1.0f, 1.0f);
            this.a = new AnimatorSet();
            this.a.play(heightAnimator).with(alphaAnimator);
            this.a.start();
            Log.i("HeightAnimationLayout", "onLayout" + this.b);
        }
        super.onLayout(changed, l, t, r, b);
    }

    public void a() {
        setVisibility(0);
        this.e = true;
    }

    public void a(final View relativeView) {
        if (!this.f) {
            this.f = true;
            ValueAnimator heightAnimator = a(this.b, 0, false);
            ObjectAnimator alphaAnimator = a(1.0f, 0.0f);
            this.a = new AnimatorSet();
            this.a.play(heightAnimator).with(alphaAnimator);
            this.a.addListener(new AnimatorListenerAdapter(this) {
                final /* synthetic */ HeightAnimationLayout b;

                public void onAnimationEnd(Animator animation) {
                    this.b.setVisibility(8);
                    relativeView.setVisibility(0);
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(relativeView, "alpha", new float[]{0.0f, 1.0f});
                    objectAnimator.setInterpolator(new LinearInterpolator());
                    objectAnimator.setDuration(83);
                    objectAnimator.start();
                    this.b.f = false;
                }
            });
            this.a.start();
            relativeView.setVisibility(4);
        }
    }

    private ObjectAnimator a(float fromAlpha, float toAlpha) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "alpha", new float[]{fromAlpha, toAlpha});
        objectAnimator.setInterpolator(new PathInterpolator(0.8f, 0.0f, 0.3f, 1.0f));
        if (this.b <= 500) {
            objectAnimator.setDuration(200);
        } else {
            long duration = 200 * ((long) (this.b / 500));
            if (duration > 400) {
                duration = 400;
            }
            objectAnimator.setDuration(duration);
        }
        return objectAnimator;
    }

    private ValueAnimator a(int start, int end, boolean expanding) {
        PathInterpolator pathInterpolator;
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{start, end});
        animator.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ HeightAnimationLayout a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                LayoutParams layoutParams = this.a.getLayoutParams();
                layoutParams.height = value;
                this.a.setLayoutParams(layoutParams);
            }
        });
        if (expanding) {
            pathInterpolator = new PathInterpolator(0.4f, 0.0f, 0.1f, 1.0f);
        } else {
            pathInterpolator = new PathInterpolator(0.5f, 0.0f, 0.2f, 1.0f);
        }
        animator.setInterpolator(pathInterpolator);
        animator.setDuration(getDuration());
        return animator;
    }

    private final long getDuration() {
        if (this.b <= 500) {
            return 250;
        }
        long duration = 300 * ((long) (this.b / 500));
        return duration > 500 ? 500 : duration;
    }
}
