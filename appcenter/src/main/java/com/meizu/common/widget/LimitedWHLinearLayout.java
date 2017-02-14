package com.meizu.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import org.apache.commons.io.FileUtils;

public class LimitedWHLinearLayout extends LinearLayout {
    private int a = Integer.MAX_VALUE;
    private int b = Integer.MAX_VALUE;

    public LimitedWHLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LimitedWHLinearLayout(Context context) {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        boolean measure = false;
        if (height > this.a) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(this.a, FileUtils.ONE_GB);
            measure = true;
        }
        if (width > this.b) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(this.b, FileUtils.ONE_GB);
            measure = true;
        }
        if (measure) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setMaxHeight(int max) {
        this.a = max;
    }

    public int getMaxHeight() {
        return this.a;
    }

    public void setMaxWidth(int max) {
        this.b = max;
    }

    public int getMaxWidth() {
        return this.b;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(LimitedWHLinearLayout.class.getName());
    }
}
