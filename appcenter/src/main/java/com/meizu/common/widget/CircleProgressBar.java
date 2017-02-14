package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.meizu.common.a.j;

public class CircleProgressBar extends View {
    private int a;
    private int b;
    private int c;
    private float d;
    private int e;
    private int f;
    private Paint g;
    private Paint h;
    private Paint i;
    private RectF j;
    private int k;
    private int l;
    private String m;
    private int n;
    private boolean o;
    private boolean p;

    public CircleProgressBar(Context context) {
        super(context, null);
        this.c = 0;
        this.g = new Paint();
        this.h = new Paint();
        this.i = new Paint();
        this.j = new RectF();
        this.k = 0;
        this.m = "0%";
        this.n = 0;
        this.o = false;
        this.p = true;
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.c = 0;
        this.g = new Paint();
        this.h = new Paint();
        this.i = new Paint();
        this.j = new RectF();
        this.k = 0;
        this.m = "0%";
        this.n = 0;
        this.o = false;
        this.p = true;
        TypedArray a = context.obtainStyledAttributes(attrs, j.CircleProgressBar, defStyle, 0);
        this.e = a.getColor(j.CircleProgressBar_mcCircleBarColor, -436207617);
        this.f = a.getColor(j.CircleProgressBar_mcCircleBarRimColor, 889192447);
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        this.d = (float) a.getDimensionPixelSize(j.CircleProgressBar_mcCircleBarWidth, (int) (dm.density * 2.0f));
        this.k = a.getDimensionPixelSize(j.CircleProgressBar_mcCenterTextSize, (int) (dm.density * 14.0f));
        this.l = a.getColor(j.CircleProgressBar_mcCenterTextColor, -1);
        setMax(a.getInt(j.CircleProgressBar_mcCircleBarMax, 0));
        setProgress(a.getInt(j.CircleProgressBar_mcCircleBarProgress, 0));
        this.p = a.getBoolean(j.CircleProgressBar_mcCircleIsShowProgress, this.p);
        a.recycle();
        a();
    }

    private void a() {
        b();
        c();
        this.c = a(this.b, true);
        this.n = a(this.b, false);
        this.m = String.valueOf(this.n) + "%";
    }

    protected void onDraw(Canvas canvas) {
        if (this.o) {
            b();
            this.o = false;
        }
        canvas.drawArc(this.j, 360.0f, 360.0f, false, this.h);
        canvas.drawArc(this.j, -90.0f, (float) this.c, false, this.g);
        float verticalTextOffset = ((this.i.descent() - this.i.ascent()) / 2.0f) - this.i.descent();
        float horizontalTextOffset = this.i.measureText(this.m) / 2.0f;
        if (this.p) {
            canvas.drawText(this.m, ((float) (getWidth() / 2)) - horizontalTextOffset, ((float) (getHeight() / 2)) + verticalTextOffset, this.i);
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.o = true;
    }

    private void b() {
        if (this.j == null) {
            this.j = new RectF();
        }
        this.j.left = ((float) getPaddingLeft()) + this.d;
        this.j.top = ((float) getPaddingTop()) + this.d;
        this.j.right = ((float) (getWidth() - getPaddingRight())) - this.d;
        this.j.bottom = ((float) (getHeight() - getPaddingBottom())) - this.d;
    }

    private void c() {
        if (this.g == null) {
            this.g = new Paint();
        }
        this.g.setColor(this.e);
        this.g.setAntiAlias(true);
        this.g.setStyle(Style.STROKE);
        this.g.setStrokeWidth(this.d);
        this.g.setStrokeJoin(Join.ROUND);
        if (this.h == null) {
            this.h = new Paint();
        }
        this.h.setColor(this.f);
        this.h.setAntiAlias(true);
        this.h.setStyle(Style.STROKE);
        this.h.setStrokeWidth(this.d);
        if (this.i == null) {
            this.i = new Paint();
        }
        this.i.setTextSize((float) this.k);
        this.i.setColor(this.l);
        this.i.setAntiAlias(true);
    }

    private int a(int progress, boolean isCircle) {
        int reference = isCircle ? 360 : 100;
        if (this.a <= 0) {
            return 0;
        }
        if (progress >= this.a) {
            return reference;
        }
        return (int) (((float) reference) * (((float) progress) / ((float) this.a)));
    }

    public void setProgressStatus(boolean isShowProgress) {
        this.p = isShowProgress;
    }

    public void setMax(int max) {
        if (max < 0) {
            max = 0;
        }
        if (max != this.a) {
            this.a = max;
            if (this.b > max) {
                this.b = max;
            }
            postInvalidate();
        }
    }

    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        }
        if (progress > this.a) {
            progress = this.a;
        }
        if (progress != this.b) {
            this.b = progress;
            this.c = a(this.b, true);
            this.n = a(this.b, false);
            this.m = String.valueOf(this.n) + "%";
            postInvalidate();
        }
    }

    public void setCircleBarColor(int barColor) {
        if (this.e != barColor) {
            this.e = barColor;
            this.g.setColor(this.e);
            postInvalidate();
        }
    }

    public void setCircleRimColor(int rimColor) {
        if (this.f != rimColor) {
            this.f = rimColor;
            this.h.setColor(this.f);
            postInvalidate();
        }
    }

    public void setCircleBarWidth(float barWidth) {
        if (((double) Math.abs(this.d - barWidth)) >= 1.0E-6d) {
            if (barWidth < 0.0f) {
                this.d = 0.0f;
            } else {
                this.d = barWidth;
            }
            this.g.setStrokeWidth(this.d);
            this.h.setStrokeWidth(this.d);
            this.o = true;
            postInvalidate();
        }
    }

    public int getMax() {
        return this.a < 0 ? 0 : this.a;
    }

    public int getProgress() {
        return this.b < 0 ? 0 : this.b;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(CircleProgressBar.class.getName());
    }
}
