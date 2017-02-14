package com.meizu.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout.LayoutParams;

public class TabTopLayout extends TabLayout {
    private static final String a = TabTopLayout.class.getSimpleName();
    private int b = 2;
    private int c = 0;
    private int d = 20;
    private int e = 0;
    private int f = 1;
    private View g;
    private View h;
    private boolean i = false;
    private View j;
    private a k;

    public interface a {
    }

    public TabTopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.g = new View(context);
        this.g.setBackgroundColor(-4013374);
        addView(this.g);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        a();
    }

    public void setHeaderHeight(int height) {
        this.e = height;
        if (this.e == 0) {
            Log.w(a, "mHeaderHeight is 0");
        }
    }

    public void setOnHeaderScrollListener(a onHeaderScrollListener) {
        this.k = onHeaderScrollListener;
    }

    public void setHeaderView(View headerView) {
        this.h = headerView;
    }

    public void setParentLayoutView(View view) {
        this.j = view;
    }

    protected void setHeaderView(int reId) {
        this.h = findViewById(reId);
    }

    public void setDividerHeight(int height) {
        this.f = height;
        a();
    }

    public void setDividerParam(int margin, int height, int scale, int color) {
        this.d = margin;
        if (height > 0) {
            this.f = height;
        }
        if (scale > 0) {
            this.b = scale;
        }
        if (color > 0) {
            this.g.setBackgroundColor(color);
        }
        this.c = this.d * this.b;
        a(this.c);
    }

    public void setDividerColor(int color) {
        this.g.setBackgroundColor(color);
    }

    private void a() {
        if (this.g != null) {
            removeView(this.g);
            LayoutParams params = new LayoutParams(-1, this.f);
            params.setMargins(this.c / this.b, getHeight() - this.f, this.c / this.b, 0);
            addView(this.g, params);
            Log.v(a, "drawDivider");
        }
    }

    private int getHeaderHeight() {
        if (this.h != null) {
            return this.h.getHeight();
        }
        return this.e;
    }

    public void a(int margin) {
        margin /= this.b;
        LayoutParams params = (LayoutParams) this.g.getLayoutParams();
        if (params != null) {
            params.height = this.f;
            params.setMargins(margin, params.topMargin, margin, params.bottomMargin);
            this.g.setLayoutParams(params);
            this.g.invalidate();
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(TabTopLayout.class.getName());
    }
}
