package com.meizu.common.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import com.meizu.common.a.b;
import com.meizu.common.a.e;
import com.meizu.common.a.j;
import org.apache.commons.io.FileUtils;

public class SubscribeButton extends Button {
    private static float e = 0.0f;
    private static float f = 1.0f;
    boolean a;
    private float b;
    private int c;
    private ValueAnimator d;
    private Drawable g;
    private Drawable h;
    private int i;
    private float j;
    private Rect k;
    private Paint l;
    private Paint m;
    private Interpolator n;
    private String o;
    private String p;
    private int q;
    private int r;
    private boolean s;

    public SubscribeButton(Context context) {
        this(context, null);
    }

    public SubscribeButton(Context context, AttributeSet attrs) {
        this(context, attrs, b.MeizuCommon_SubscribeButtonStyle);
    }

    public SubscribeButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = false;
        this.b = 0.0f;
        this.d = null;
        this.j = 15.0f;
        this.s = false;
        TypedArray a = context.obtainStyledAttributes(attrs, j.SubscribeButton, defStyle, 0);
        this.g = a.getDrawable(j.SubscribeButton_mcBtnNormalBg);
        this.h = a.getDrawable(j.SubscribeButton_mcBtnBeAddedBg);
        this.j = a.getDimension(j.SubscribeButton_mcBtnSubTextSize, 15.0f);
        this.p = a.getString(j.SubscribeButton_mcBtnBeAddedText);
        this.o = a.getString(j.SubscribeButton_mcBtnNormalText);
        this.q = a.getColor(j.SubscribeButton_mcBtnBeAddedTextColor, -16777216);
        this.r = a.getColor(j.SubscribeButton_mcBtnNormalTextColor, -1);
        this.i = a.getInteger(j.SubscribeButton_mcBtnAnimDuration, 80);
        if (this.g == null) {
            this.g = getResources().getDrawable(e.mc_btn_list_default_alpha_normal);
        }
        if (this.h == null) {
            this.h = getResources().getDrawable(e.mc_btn_list_default_pressed);
        }
        a.recycle();
        b();
    }

    @TargetApi(21)
    private void b() {
        Paint paint = new Paint();
        paint.setTextAlign(Align.CENTER);
        paint.setAntiAlias(true);
        paint.setTextSize(this.j);
        this.l = new Paint(paint);
        this.l.setColor(this.r);
        this.m = new Paint(paint);
        this.m.setColor(this.q);
        if (VERSION.SDK_INT >= 21) {
            this.n = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        } else {
            this.n = new AccelerateInterpolator();
        }
    }

    private void setAlphaSign(float alphaSign) {
        this.b = alphaSign;
    }

    private float getAlphaSign() {
        return this.b;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0 && this.d != null) {
            this.d.end();
        }
        return super.onTouchEvent(event);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height = 0;
        FontMetrics textMetrics = this.l.getFontMetrics();
        int heightTemp = (int) ((((float) getPaddingBottom()) + (textMetrics.bottom - textMetrics.top)) + ((float) getPaddingTop()));
        int widthTemp = (int) (Math.max(this.l.measureText(this.o), this.m.measureText(this.p)) + 20.0f);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (measureWidthMode == Integer.MIN_VALUE) {
            width = widthTemp;
        } else if (measureWidthMode == FileUtils.ONE_GB) {
            width = measureWidth;
        } else {
            width = measureWidth;
        }
        if (measureHeightMode == Integer.MIN_VALUE) {
            height = heightTemp;
        } else if (measureHeightMode == FileUtils.ONE_GB) {
            height = measureHeight;
        } else if (measureWidthMode == 0) {
            height = measureHeight;
        }
        setMeasuredDimension(width, height);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.k = new Rect(0, 0, w, h);
        FontMetricsInt fontMetrics = this.l.getFontMetricsInt();
        this.c = (this.k.centerY() - ((fontMetrics.bottom - fontMetrics.top) / 2)) - fontMetrics.top;
        this.h.setBounds(this.k);
        this.g.setBounds(this.k);
    }

    protected void onDraw(Canvas canvas) {
        float inverAlphaSign = f - this.b;
        this.l.setAlpha((int) (inverAlphaSign * 255.0f));
        this.m.setAlpha((int) (this.b * 77.0f));
        if (this.a) {
            this.h.setAlpha((int) (this.b * 255.0f));
            this.h.draw(canvas);
            this.g.setAlpha((int) (inverAlphaSign * 255.0f));
            this.g.draw(canvas);
        } else {
            this.g.setAlpha((int) (inverAlphaSign * 255.0f));
            this.g.draw(canvas);
            this.h.setAlpha((int) (this.b * 255.0f));
            this.h.draw(canvas);
        }
        canvas.drawText(this.o, (float) this.k.centerX(), (float) this.c, this.l);
        canvas.drawText(this.p, (float) this.k.centerX(), (float) this.c, this.m);
        super.onDraw(canvas);
    }

    public boolean performClick() {
        if (!this.s) {
            a();
        }
        return super.performClick();
    }

    private void a(float begin, float end, int duration) {
        this.d = ValueAnimator.ofFloat(new float[]{begin, end});
        this.d.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ SubscribeButton a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.a.setAlphaSign(((Float) animation.getAnimatedValue()).floatValue());
                this.a.invalidate();
            }
        });
        this.d.setDuration((long) duration);
        this.d.setInterpolator(this.n);
        this.d.start();
    }

    public void a() {
        this.a = !this.a;
        if (this.b > 0.0f) {
            a(this.b, e, this.i);
        } else {
            a(this.b, f, this.i);
        }
    }

    public void setBtnBeAddedText(String text) {
        String textTemp = this.p;
        this.p = text;
        if (this.m.measureText(textTemp) != this.m.measureText(this.p)) {
            requestLayout();
        }
        invalidate();
    }

    public String getBtnBeAddedText() {
        return this.p;
    }

    public void setBtnNormalText(String text) {
        String textTemp = this.o;
        this.o = text;
        if (this.m.measureText(textTemp) != this.m.measureText(this.o)) {
            requestLayout();
        }
        invalidate();
    }

    public String getBtnNormalText() {
        return this.o;
    }

    public void setBtnBeAddedTextColor(int beaddedTextColor) {
        this.m.setColor(beaddedTextColor);
        invalidate();
    }

    public int getBtnBeAddedTextColor() {
        return this.m.getColor();
    }

    public void setBtnNormalTextColor(int addTextColor) {
        this.l.setColor(addTextColor);
        invalidate();
    }

    public int getBtnNormalTextColor() {
        return this.l.getColor();
    }

    public void setBtnSubTextSize(int textSize) {
        this.l.setTextSize((float) textSize);
        this.m.setTextSize((float) textSize);
        if (this.k != null) {
            FontMetricsInt fontMetrics = this.l.getFontMetricsInt();
            this.c = (this.k.centerY() - ((fontMetrics.bottom - fontMetrics.top) / 2)) - fontMetrics.top;
        }
        invalidate();
    }

    public void setAnimDuration(int duration) {
        this.i = duration;
    }

    public void setSelectedable(boolean selectedable) {
        if (this.a != selectedable) {
            this.a = selectedable;
            if (this.a) {
                setAlphaSign(1.0f);
                a(e, this.b, 0);
                return;
            }
            setAlphaSign(0.0f);
            a(f, this.b, 0);
        }
    }

    public boolean getSelectedState() {
        return this.a;
    }

    public void setManuaStartAnim(boolean manuaStartAnim) {
        this.s = manuaStartAnim;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(SubscribeButton.class.getName());
    }
}
