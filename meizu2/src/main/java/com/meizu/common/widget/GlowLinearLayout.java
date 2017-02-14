package com.meizu.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;

public class GlowLinearLayout extends LinearLayout {
    private GlowDelegate mDelegate;

    public GlowLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    public GlowLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public GlowLinearLayout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        this.mDelegate = new GlowDelegate(context, this);
        this.mDelegate.setGlowBackground(getBackground());
        setBackground(null);
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.mDelegate.onDraw(canvas);
    }

    public float getDrawingAlpha() {
        return this.mDelegate.getGlowAlpha();
    }

    public void setDrawingAlpha(float f) {
        this.mDelegate.setDrawingAlpha(f);
    }

    public float getGlowAlpha() {
        return this.mDelegate.getGlowAlpha();
    }

    public void setGlowAlpha(float f) {
        this.mDelegate.setGlowAlpha(f);
    }

    public float getGlowScale() {
        return this.mDelegate.getGlowScale();
    }

    public void setGlowScale(float f) {
        this.mDelegate.setGlowScale(f);
    }

    public void setPressed(boolean z) {
        this.mDelegate.setPressed(z);
        super.setPressed(z);
    }

    public void setBackground(Drawable drawable) {
        super.setBackground(drawable);
        if (this.mDelegate != null && getBackground() != null) {
            this.mDelegate.setGlowBackground(drawable);
            super.setBackground(null);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(GlowLinearLayout.class.getName());
    }
}
