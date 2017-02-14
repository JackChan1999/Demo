package com.meizu.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ListView;

public class AutoScrollListView extends ListView {
    private int a = -1;
    private boolean b;

    public AutoScrollListView(Context context) {
        super(context);
    }

    public AutoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void layoutChildren() {
        super.layoutChildren();
        if (this.a != -1) {
            int position = this.a;
            this.a = -1;
            int firstPosition = getFirstVisiblePosition() + 1;
            int lastPosition = getLastVisiblePosition();
            if (position < firstPosition || position > lastPosition) {
                int offset = (int) (((float) getHeight()) * 0.33f);
                if (this.b) {
                    int twoScreens = (lastPosition - firstPosition) * 2;
                    int preliminaryPosition;
                    if (position < firstPosition) {
                        preliminaryPosition = position + twoScreens;
                        if (preliminaryPosition >= getCount()) {
                            preliminaryPosition = getCount() - 1;
                        }
                        if (preliminaryPosition < firstPosition) {
                            setSelection(preliminaryPosition);
                            super.layoutChildren();
                        }
                    } else {
                        preliminaryPosition = position - twoScreens;
                        if (preliminaryPosition < 0) {
                            preliminaryPosition = 0;
                        }
                        if (preliminaryPosition > lastPosition) {
                            setSelection(preliminaryPosition);
                            super.layoutChildren();
                        }
                    }
                    smoothScrollToPositionFromTop(position, offset);
                    return;
                }
                setSelectionFromTop(position, offset);
                super.layoutChildren();
            }
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(AutoScrollListView.class.getName());
    }
}
