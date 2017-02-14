package com.meizu.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ListView;

public class AutoScrollListView extends ListView {
    private static final float PREFERRED_SELECTION_OFFSET_FROM_TOP = 0.33f;
    private int mRequestedScrollPosition = -1;
    private boolean mSmoothScrollRequested;

    public AutoScrollListView(Context context) {
        super(context);
    }

    public AutoScrollListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AutoScrollListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void requestPositionToScreen(int i, boolean z) {
        this.mRequestedScrollPosition = i;
        this.mSmoothScrollRequested = z;
        requestLayout();
    }

    protected void layoutChildren() {
        super.layoutChildren();
        if (this.mRequestedScrollPosition != -1) {
            int i = this.mRequestedScrollPosition;
            this.mRequestedScrollPosition = -1;
            int firstVisiblePosition = getFirstVisiblePosition() + 1;
            int lastVisiblePosition = getLastVisiblePosition();
            if (i < firstVisiblePosition || i > lastVisiblePosition) {
                int height = (int) (((float) getHeight()) * PREFERRED_SELECTION_OFFSET_FROM_TOP);
                if (this.mSmoothScrollRequested) {
                    int i2 = (lastVisiblePosition - firstVisiblePosition) * 2;
                    if (i < firstVisiblePosition) {
                        i2 += i;
                        if (i2 >= getCount()) {
                            i2 = getCount() - 1;
                        }
                        if (i2 < firstVisiblePosition) {
                            setSelection(i2);
                            super.layoutChildren();
                        }
                    } else {
                        i2 = i - i2;
                        if (i2 < 0) {
                            i2 = 0;
                        }
                        if (i2 > lastVisiblePosition) {
                            setSelection(i2);
                            super.layoutChildren();
                        }
                    }
                    smoothScrollToPositionFromTop(i, height);
                    return;
                }
                setSelectionFromTop(i, height);
                super.layoutChildren();
            }
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(AutoScrollListView.class.getName());
    }
}
