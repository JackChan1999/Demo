package com.meizu.common.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
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

public class CustomButton extends Button {
    private static float f = 0.0f;
    private static float g = 1.0f;
    boolean a;
    boolean b;
    private float c;
    private int d;
    private ValueAnimator e;
    private Drawable h;
    private Drawable i;
    private float j;
    private Rect k;
    private Paint l;
    private Paint m;
    private Interpolator n;
    private String o;
    private String p;
    private int q;
    private int r;

    public CustomButton(Context context) {
        this(context, null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        this(context, attrs, b.MeizuCommon_CustomButtonStyle);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = true;
        this.b = true;
        this.c = 0.0f;
        this.e = null;
        this.j = 15.0f;
        TypedArray a = context.obtainStyledAttributes(attrs, j.CustomButton, defStyle, 0);
        this.h = a.getDrawable(j.CustomButton_mcBtnFocus);
        this.i = a.getDrawable(j.CustomButton_mcBtnNormalPress);
        this.j = a.getDimension(j.CustomButton_mcBtnTextSize, 15.0f);
        this.p = a.getString(j.CustomButton_mcBtnPressedText);
        this.o = a.getString(j.CustomButton_mcBtnDefaultText);
        this.q = a.getColor(j.CustomButton_mcBtnPressedTextColor, -16777216);
        this.r = a.getColor(j.CustomButton_mcBtnDefaultTextColor, -1);
        if (this.h == null) {
            this.h = getResources().getDrawable(e.mc_btn_list_default_alpha_normal);
        }
        if (this.i == null) {
            this.i = getResources().getDrawable(e.mc_btn_list_default_pressed);
        }
        a.recycle();
        a();
    }

    private void a() {
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
        this.c = alphaSign;
    }

    private float getAlphaSign() {
        return this.c;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0 && this.e != null) {
            this.e.end();
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

    protected void onDraw(Canvas canvas) {
        if (this.b) {
            if (this.k == null) {
                this.k = new Rect(0, 0, getWidth(), getHeight());
            }
            FontMetricsInt fontMetrics = this.l.getFontMetricsInt();
            this.d = (this.k.centerY() - ((fontMetrics.bottom - fontMetrics.top) / 2)) - fontMetrics.top;
            this.i.setBounds(this.k);
            this.i.setAlpha((int) ((g - this.c) * 255.0f));
            this.i.draw(canvas);
            this.h.setAlpha((int) (this.c * 255.0f));
            this.h.setBounds(this.k);
            this.h.draw(canvas);
            canvas.drawText(this.o, (float) this.k.centerX(), (float) this.d, this.l);
            this.b = false;
        } else if (this.a) {
            this.i.setBounds(this.k);
            this.i.setAlpha((int) (this.c * 255.0f));
            this.i.draw(canvas);
            this.h.setAlpha((int) ((g - this.c) * 255.0f));
            this.h.setBounds(this.k);
            this.h.draw(canvas);
            this.l.setAlpha((int) ((g - this.c) * 255.0f));
            this.m.setAlpha((int) (this.c * 77.0f));
            canvas.drawText(this.o, (float) this.k.centerX(), (float) this.d, this.l);
            canvas.drawText(this.p, (float) this.k.centerX(), (float) this.d, this.m);
        } else {
            this.h.setAlpha((int) ((g - this.c) * 255.0f));
            this.h.setBounds(this.k);
            this.h.draw(canvas);
            this.i.setBounds(this.k);
            this.i.setAlpha((int) (this.c * 255.0f));
            this.i.draw(canvas);
            this.l.setAlpha((int) ((g - this.c) * 255.0f));
            this.m.setAlpha((int) (this.c * 77.0f));
            canvas.drawText(this.p, (float) this.k.centerX(), (float) this.d, this.m);
            canvas.drawText(this.o, (float) this.k.centerX(), (float) this.d, this.l);
        }
        super.onDraw(canvas);
        canvas.restore();
    }

    public boolean performClick() {
        b();
        return super.performClick();
    }

    private void a(float begin, float end, int duration) {
        this.e = ValueAnimator.ofFloat(new float[]{begin, end});
        this.e.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ CustomButton a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.a.setAlphaSign(((Float) animation.getAnimatedValue()).floatValue());
                this.a.invalidate();
            }
        });
        this.e.setDuration((long) duration);
        this.e.setInterpolator(this.n);
        this.e.start();
    }

    private void b() {
        if (this.a) {
            this.c = 0.0f;
        } else {
            this.c = 1.0f;
        }
        this.a = !this.a;
        if (this.c > 0.0f) {
            a(this.c, f, 80);
        } else {
            a(this.c, g, 80);
        }
    }

    public void setBtnPressedText(String text) {
        String textTemp = this.p;
        this.p = text;
        if (this.m.measureText(textTemp) != this.m.measureText(this.p)) {
            requestLayout();
        }
        invalidate();
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(CustomButton.class.getName());
    }

    public String getBtnPressedText() {
        return this.p;
    }

    public void setBtnDefaultText(String text) {
        String textTemp = this.o;
        this.o = text;
        if (this.m.measureText(textTemp) != this.m.measureText(this.o)) {
            requestLayout();
        }
        invalidate();
    }

    public String getBtnDefaultText() {
        return this.o;
    }

    public void setmBtnAddedTextColor(int addedTextColor) {
        this.m.setColor(addedTextColor);
        invalidate();
    }

    public void setmBtnAddTextColor(int addTextColor) {
        this.l.setColor(addTextColor);
        invalidate();
    }

    public void setmBtnTextSize(int textSize) {
        this.l.setTextSize((float) textSize);
        this.m.setTextSize((float) textSize);
        this.b = true;
        requestLayout();
        invalidate();
    }
}
