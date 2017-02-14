package com.meizu.cloud.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.meizu.common.util.d;

public class TabScrollerLayout extends LinearLayout {
    private d a;

    public TabScrollerLayout(Context context) {
        this(context, null);
    }

    public TabScrollerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabScrollerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = new d(context, this);
    }

    public d getTabScroller() {
        return this.a;
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.a.a(canvas);
    }
}
