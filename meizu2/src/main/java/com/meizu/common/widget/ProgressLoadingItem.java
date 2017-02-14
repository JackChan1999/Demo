package com.meizu.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;

public class ProgressLoadingItem extends GlowLinearLayout {
    public ProgressLoadingItem(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ProgressLoadingItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ProgressLoadingItem(Context context) {
        super(context);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(ProgressLoadingItem.class.getName());
    }
}
