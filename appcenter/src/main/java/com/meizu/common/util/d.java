package com.meizu.common.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import com.meizu.common.a.b;
import com.meizu.common.a.e;
import com.meizu.common.a.j;
import java.util.ArrayList;

public class d {
    private Drawable a;
    private int b = 20;
    private ViewGroup c;
    private int d = 0;
    private float e;
    private float f = 0.0f;
    private ArrayList<View> g = new ArrayList();
    private ArrayList<Integer> h = new ArrayList();

    public d(Context context, ViewGroup view) {
        TypedArray array = context.obtainStyledAttributes(null, j.TabScroller, b.MeizuCommon_TabScrollerStyle, 0);
        this.a = array.getDrawable(j.TabScroller_mcTabIndicatorDrawable);
        if (this.a == null) {
            this.a = context.getResources().getDrawable(e.mz_tab_selected);
        }
        array.recycle();
        this.b = this.a.getIntrinsicHeight();
        this.c = view;
    }

    public void a(Drawable drawable) {
        if (drawable != null) {
            this.a = drawable;
            this.b = this.a.getIntrinsicHeight();
        }
    }

    public void a(int position) {
        if (this.c != null) {
            this.d = position;
            this.e = 0.0f;
            this.c.invalidate();
        }
    }

    public void a(View view) {
        if (view != null && !this.g.contains(view)) {
            this.g.add(view);
        }
    }

    public void a(int position, float positionOffset) {
        this.d = position;
        this.e = positionOffset;
        if (this.c != null) {
            this.c.invalidate();
        }
    }

    public void a(Canvas canvas) {
        int tabSize = this.g.size();
        if (tabSize != 0) {
            if (this.d >= tabSize) {
                this.d = tabSize - 1;
            } else if (this.d < 0) {
                this.d = 0;
            }
            boolean setTabLength = this.h.size() == tabSize;
            View mCurTab = (View) this.g.get(this.d);
            int curTabwidth = setTabLength ? ((Integer) this.h.get(this.d)).intValue() : mCurTab.getWidth();
            if (curTabwidth < 0 || curTabwidth > mCurTab.getWidth()) {
                curTabwidth = mCurTab.getWidth();
            }
            int height = mCurTab.getHeight();
            int center = mCurTab.getLeft() + (mCurTab.getWidth() / 2);
            View nextTab = null;
            int nextTabWidth = 0;
            float deltaWidth = 0.0f;
            if (this.e > this.f && this.d < tabSize - 1) {
                nextTab = (View) this.g.get(this.d + 1);
                nextTabWidth = setTabLength ? ((Integer) this.h.get(this.d + 1)).intValue() : nextTab.getWidth();
                if (nextTabWidth < 0 || nextTabWidth > nextTab.getWidth()) {
                    nextTabWidth = nextTab.getWidth();
                }
            } else if (this.e < this.f && this.d > 0) {
                nextTab = (View) this.g.get(this.d - 1);
                nextTabWidth = setTabLength ? ((Integer) this.h.get(this.d - 1)).intValue() : nextTab.getWidth();
                if (nextTabWidth < 0 || nextTabWidth > nextTab.getWidth()) {
                    nextTabWidth = nextTab.getWidth();
                }
            }
            if (nextTab != null) {
                deltaWidth = ((float) (nextTabWidth - curTabwidth)) * this.e;
                center = (int) (((float) center) + (((float) ((nextTab.getLeft() + (nextTab.getWidth() / 2)) - (mCurTab.getLeft() + (mCurTab.getWidth() / 2)))) * this.e));
            }
            int curWidth = (int) (((float) curTabwidth) + deltaWidth);
            this.a.setBounds(center - (curWidth / 2), height - this.b, (curWidth / 2) + center, height);
            canvas.save();
            this.a.draw(canvas);
            canvas.restore();
        }
    }
}
