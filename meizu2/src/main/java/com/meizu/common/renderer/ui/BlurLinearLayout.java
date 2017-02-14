package com.meizu.common.renderer.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.meizu.common.renderer.drawable.GLBlurDrawable;

public class BlurLinearLayout extends LinearLayout {
    private GLBlurDrawable mBlurBackgound = new GLBlurDrawable();

    public BlurLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setBackground(this.mBlurBackgound);
    }

    public GLBlurDrawable getBlurDrawable() {
        return this.mBlurBackgound;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mBlurBackgound.recycle();
    }

    protected boolean onSetAlpha(int i) {
        float f = ((float) i) / 255.0f;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            getChildAt(i2).setAlpha(f);
        }
        this.mBlurBackgound.setAlpha(i);
        return true;
    }
}
