package com.meizu.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;

public class LimitedWHLinearLayout extends LinearLayout {
    private int mMaxHeight = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    private int mMaxWidth = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;

    public LimitedWHLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public LimitedWHLinearLayout(Context context) {
        super(context);
    }

    protected void onMeasure(int i, int i2) {
        Object obj = 1;
        super.onMeasure(i, i2);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        Object obj2 = null;
        if (measuredHeight > this.mMaxHeight) {
            i2 = MeasureSpec.makeMeasureSpec(this.mMaxHeight, 1073741824);
            obj2 = 1;
        }
        if (measuredWidth > this.mMaxWidth) {
            i = MeasureSpec.makeMeasureSpec(this.mMaxWidth, 1073741824);
        } else {
            obj = obj2;
        }
        if (obj != null) {
            super.onMeasure(i, i2);
        }
    }

    public void setMaxHeight(int i) {
        this.mMaxHeight = i;
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    public void setMaxWidth(int i) {
        this.mMaxWidth = i;
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(LimitedWHLinearLayout.class.getName());
    }
}
