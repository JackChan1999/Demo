package com.meizu.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;

public class GlowLinearLayout extends LinearLayout {
    private c a;

    public GlowLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a(context);
    }

    public GlowLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public GlowLinearLayout(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        this.a = new c(context, this);
        this.a.a(getBackground());
        setBackground(null);
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.a.a(canvas);
    }

    public float getDrawingAlpha() {
        return this.a.a();
    }

    public void setDrawingAlpha(float x) {
        this.a.a(x);
    }

    public float getGlowAlpha() {
        return this.a.a();
    }

    public void setGlowAlpha(float x) {
        this.a.b(x);
    }

    public float getGlowScale() {
        return this.a.b();
    }

    public void setGlowScale(float x) {
        this.a.c(x);
    }

    public void setPressed(boolean pressed) {
        this.a.a(pressed);
        super.setPressed(pressed);
    }

    public void setBackground(Drawable background) {
        super.setBackground(background);
        if (this.a != null && getBackground() != null) {
            this.a.a(background);
            super.setBackground(null);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(GlowLinearLayout.class.getName());
    }
}
