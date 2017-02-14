package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.LinearInterpolator;
import com.meizu.cloud.app.drawable.CircularAnimatedDrawable;
import com.meizu.common.a.b;
import com.meizu.common.a.j;

@SuppressLint({"NewApi"})
public class LoadingView extends View {
    private Paint a;
    private Paint b;
    private Paint c;
    private Context d;
    private Animator e;
    private float f;
    private float g;
    private final long h;
    private RectF i;
    private int j;
    private float k;
    private float l;
    private float m;
    private float n;
    private int o;
    private int p;
    private int q;
    private int r;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, b.MeizuCommon_LoadingViewStyle);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.h = 1760;
        this.i = null;
        this.j = 0;
        this.r = 1;
        this.d = context;
        this.a = new Paint(1);
        this.a.setAntiAlias(true);
        this.a.setColor(-1);
        this.a.setAntiAlias(true);
        this.a.setTextAlign(Align.CENTER);
        this.a.setTextSize(36.0f);
        TypedArray b = this.d.obtainStyledAttributes(j.MZTheme);
        this.o = b.getInt(j.MZTheme_mzThemeColor, -16711936);
        b.recycle();
        TypedArray a = context.obtainStyledAttributes(attrs, j.LoadingView, defStyleAttr, 0);
        this.k = a.getDimension(j.LoadingView_mcLoadingRadius, 30.0f);
        this.l = a.getDimension(j.LoadingView_mcRingWidth, 4.5f);
        this.p = a.getColor(j.LoadingView_mcLBackground, this.o);
        this.q = a.getColor(j.LoadingView_mcLForeground, this.o);
        this.r = a.getInt(j.LoadingView_mcLoadingState, 1);
        a.recycle();
        this.b = new Paint(1);
        this.b.setAntiAlias(true);
        this.b.setColor(this.q);
        this.b.setStyle(Style.STROKE);
        this.b.setStrokeCap(Cap.ROUND);
        this.c = new Paint(1);
        this.c.setAntiAlias(true);
        this.c.setColor(this.p);
        this.c.setStyle(Style.STROKE);
        this.b.setStrokeWidth(this.l - ((float) this.j));
        this.c.setStrokeWidth(this.l - ((float) this.j));
        c();
    }

    public LoadingView(Context context, float radius, float ringWidth) {
        this(context, null);
        this.k = radius;
        this.l = ringWidth;
        c();
    }

    private void c() {
        this.m = (((getX() + ((float) getPaddingLeft())) + this.k) + ((float) (this.j * 2))) + this.l;
        this.n = (((getY() + ((float) getPaddingTop())) + this.k) + ((float) (this.j * 2))) + this.l;
        this.i = new RectF();
        this.i.left = ((this.m - this.k) - ((float) (this.j / 2))) - (this.l / 2.0f);
        this.i.top = ((this.n - this.k) - ((float) (this.j / 2))) - (this.l / 2.0f);
        this.i.right = ((this.m + this.k) + ((float) (this.j / 2))) + (this.l / 2.0f);
        this.i.bottom = ((this.n + this.k) + ((float) (this.j / 2))) + (this.l / 2.0f);
    }

    protected void onDraw(Canvas canvas) {
        canvas.translate((((float) (getWidth() / 2)) - this.k) - this.l, (((float) (getHeight() / 2)) - this.k) - this.l);
        if (this.q == this.p) {
            this.c.setAlpha(26);
        }
        if (this.r == 1) {
            a(canvas);
        } else {
            super.onDraw(canvas);
        }
    }

    private void a(Canvas canvas) {
        canvas.drawArc(this.i, -90.0f, 360.0f, false, this.c);
        canvas.drawArc(this.i, this.f, this.g, false, this.b);
    }

    public void a() {
        d();
    }

    public void b() {
        if (this.e != null) {
            this.e.cancel();
            this.e = null;
        }
    }

    private void d() {
        if (this.e == null || !this.e.isRunning()) {
            this.r = 1;
            this.e = e();
            this.e.start();
        }
    }

    private Animator e() {
        Keyframe key1 = Keyframe.ofFloat(0.0f, -90.0f);
        Keyframe key2 = Keyframe.ofFloat(0.5f, 330.0f);
        Keyframe key3 = Keyframe.ofFloat(1.0f, 630.0f);
        PropertyValuesHolder pvhStart = PropertyValuesHolder.ofKeyframe(CircularAnimatedDrawable.START_ANGLE_PROPERTY, new Keyframe[]{key1, key2, key3});
        PropertyValuesHolder pvhSweep = PropertyValuesHolder.ofFloat(CircularAnimatedDrawable.SWEEP_ANGLE_PROPERTY, new float[]{0.0f, -120.0f, 0.0f});
        ObjectAnimator loadingAnim = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{pvhStart, pvhSweep});
        loadingAnim.setDuration(1760);
        loadingAnim.setInterpolator(new LinearInterpolator());
        loadingAnim.setRepeatCount(-1);
        return loadingAnim;
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == 0) {
            d();
        } else if ((visibility == 4 || visibility == 8) && this.e != null) {
            this.e.cancel();
            this.e = null;
        }
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (1 == this.r) {
            if (visibility != 0) {
                if (this.e != null) {
                    this.e.cancel();
                    this.e = null;
                }
            } else if (isShown()) {
                d();
            }
        }
    }

    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (1 == this.r) {
            if (visibility != 0) {
                if (this.e != null) {
                    this.e.cancel();
                    this.e = null;
                }
            } else if (isShown()) {
                d();
            }
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int dw = (int) (2.0f * ((this.k + this.l) + 1.0f));
        int dh = dw;
        setMeasuredDimension(resolveSizeAndState(dw + (getPaddingLeft() + getPaddingRight()), widthMeasureSpec, 0), resolveSizeAndState(dh + (getPaddingTop() + getPaddingBottom()), heightMeasureSpec, 0));
    }

    public void setBarColor(int barColor) {
        if (this.b != null && this.b.getColor() != barColor) {
            this.b.setColor(barColor);
            this.q = barColor;
            postInvalidate();
        }
    }

    public int getBarColor() {
        return this.q;
    }

    public void setBarBackgroundColor(int backgroundColor) {
        if (this.c != null && this.c.getColor() != backgroundColor) {
            this.c.setColor(backgroundColor);
            this.p = backgroundColor;
            postInvalidate();
        }
    }

    public int getBarBackgroundColor() {
        return this.p;
    }

    public float getSweepAngle() {
        return this.g;
    }

    public void setSweepAngle(float sweepAngle) {
        this.g = sweepAngle;
        invalidate();
    }

    public float getStartAngle() {
        return this.f;
    }

    public void setStartAngle(float startAngle) {
        this.f = startAngle;
        invalidate();
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(LoadingView.class.getName());
    }
}
