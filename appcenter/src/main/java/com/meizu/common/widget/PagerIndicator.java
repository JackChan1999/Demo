package com.meizu.common.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.meizu.common.a.b;
import com.meizu.common.a.c;
import com.meizu.common.a.d;
import com.meizu.common.a.j;

public class PagerIndicator extends View {
    private int a;
    private int b;
    private int c;
    private float d;
    private Paint e;
    private boolean f;
    private float g;
    private float h;
    private float i;
    private int j;
    private Paint k;
    private int l;
    private int m;
    private int n;

    public PagerIndicator(Context context) {
        this(context, null);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, b.MeizuCommon_PagerIndicator);
    }

    public PagerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, j.PagerIndicator, defStyle, 0);
        Resources res = getResources();
        float defaultRadius = res.getDimension(d.mc_pager_indicator_radius);
        float defaultEnlargeRadius = res.getDimension(d.mc_pager_indicator_enlarge_radius);
        float defaultDistance = res.getDimension(d.mc_pager_indicator_distance);
        int defaultFillColor = res.getColor(c.mc_pager_indicator_fill_color);
        int defaultHighlightColor = res.getColor(c.mc_pager_indicator_highlight_color);
        this.g = a.getDimension(j.PagerIndicator_mcRadius, defaultRadius);
        this.h = a.getDimension(j.PagerIndicator_mcEnlargeRadius, defaultEnlargeRadius);
        this.i = a.getDimension(j.PagerIndicator_mcDistance, defaultDistance);
        this.l = a.getColor(j.PagerIndicator_mcFillColor, defaultFillColor);
        this.m = a.getColor(j.PagerIndicator_mcHighlightColor, defaultHighlightColor);
        this.n = a.getColor(j.PagerIndicator_mcStrokeColor, defaultHighlightColor);
        this.j = a.getInteger(j.PagerIndicator_mcGravity, 17);
        a.recycle();
        this.e = new Paint(1);
        this.e.setStyle(Style.FILL);
        this.e.setColor(this.l);
        this.k = new Paint(1);
        this.k.setStyle(Style.STROKE);
        this.k.setColor(this.n);
    }

    public void setSnap(boolean snap) {
        this.f = snap;
    }

    public void setCirclePosition(int curPosition) {
        this.a = curPosition;
        this.b = curPosition;
        invalidate();
    }

    public void setCirclePosOffset(float positionOffset, int position) {
        this.a = position;
        this.d = positionOffset;
        invalidate();
    }

    public void setPagerCount(int count) {
        if (this.c != count) {
            this.c = count;
            requestLayout();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.c != 0) {
            int i;
            float nextX;
            int count = this.c;
            int longSize = getWidth();
            int curPosition = this.a;
            float space = this.i;
            float dY = this.h;
            float leftMost = (((float) longSize) / 2.0f) - ((((float) (count - 1)) * space) / 2.0f);
            float rightMost = ((((float) (count - 1)) * space) + leftMost) + (2.0f * this.g);
            this.e.setColor(this.l);
            int iLoop = 0;
            while (iLoop < count) {
                if (!(curPosition == iLoop || ((curPosition == count - 1 && iLoop == 0) || iLoop == curPosition + 1))) {
                    canvas.drawCircle(leftMost + (((float) iLoop) * space), dY, this.g, this.e);
                }
                iLoop++;
            }
            if (this.f) {
                i = this.b;
            } else {
                i = curPosition;
            }
            float curX = leftMost + (((float) i) * space);
            if (curPosition == count - 1) {
                nextX = leftMost;
            } else {
                nextX = curX + space;
            }
            int curColor = a(this.l, this.m, this.d, -1);
            float curRadius = a(this.g, this.h, this.d, -1);
            this.e.setColor(curColor);
            canvas.drawCircle(curX, dY, curRadius, this.e);
            int nextColor = a(this.l, this.m, this.d, 1);
            float nextRadius = a(this.g, this.h, this.d, 1);
            this.e.setColor(nextColor);
            canvas.drawCircle(nextX, dY, nextRadius, this.e);
        }
    }

    private int a(int startColor, int endColor, float offset, int direction) {
        int gradualRed;
        int gradualGreen;
        int gradualBlue;
        int gradualAlpha;
        int startRed = Color.red(startColor);
        int startGreen = Color.green(startColor);
        int startBlue = Color.blue(startColor);
        int startAlpha = Color.alpha(startColor);
        int endRed = Color.red(endColor);
        int endGreen = Color.green(endColor);
        int endBlue = Color.blue(endColor);
        int endAlpha = Color.alpha(endColor);
        if (direction < 0) {
            gradualRed = Math.round(((float) endRed) - (((float) (endRed - startRed)) * offset));
            gradualGreen = Math.round(((float) endGreen) - (((float) (endGreen - startGreen)) * offset));
            gradualBlue = Math.round(((float) endBlue) - (((float) (endBlue - startBlue)) * offset));
            gradualAlpha = Math.round(((float) endAlpha) - (((float) (endAlpha - startAlpha)) * offset));
        } else {
            gradualRed = Math.round(((float) startRed) + (((float) (endRed - startRed)) * offset));
            gradualGreen = Math.round(((float) startGreen) + (((float) (endGreen - startGreen)) * offset));
            gradualBlue = Math.round(((float) startBlue) + (((float) (endBlue - startBlue)) * offset));
            gradualAlpha = Math.round(((float) startAlpha) + (((float) (endAlpha - startAlpha)) * offset));
        }
        return Color.argb(gradualAlpha, gradualRed, gradualGreen, gradualBlue);
    }

    private float a(float startRadius, float endRadius, float offset, int direction) {
        if (direction < 0) {
            return endRadius - ((endRadius - startRadius) * offset);
        }
        return ((endRadius - startRadius) * offset) + startRadius;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveSizeAndState(((int) ((((float) this.c) * this.i) + (this.g * 2.0f))) + (getPaddingLeft() + getPaddingRight()), widthMeasureSpec, 0), resolveSizeAndState(((int) Math.max(this.g * 2.0f, this.h * 2.0f)) + (getPaddingTop() + getPaddingBottom()), heightMeasureSpec, 0));
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(PagerIndicator.class.getName());
    }
}
