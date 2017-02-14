package com.meizu.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;

public class ProgressLoadingItem extends GlowLinearLayout {
    public ProgressLoadingItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ProgressLoadingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressLoadingItem(Context context) {
        super(context);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ProgressLoadingItem.class.getName());
    }
}
