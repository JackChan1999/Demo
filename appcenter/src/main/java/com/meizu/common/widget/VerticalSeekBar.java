package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import com.meizu.common.a.b;
import com.meizu.common.a.j;

public class VerticalSeekBar extends AbsSeekBar {
    private a i;

    public interface a {
        void a(VerticalSeekBar verticalSeekBar);

        void a(VerticalSeekBar verticalSeekBar, int i, boolean z);

        void b(VerticalSeekBar verticalSeekBar);
    }

    public VerticalSeekBar(Context context) {
        this(context, null);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, b.MeizuCommon_VerticalSeekBarStyle);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, j.SeekBar, defStyle, 0);
        setBreakPoint(a.getInt(j.SeekBar_mcBreakPoint, 0));
        a.recycle();
        setIsVertical(true);
        setTouchMode(1);
    }

    void a(float scale, boolean fromUser) {
        super.a(scale, fromUser);
        if (this.i != null) {
            this.i.a(this, getProgress(), fromUser);
        }
    }

    public void setOnSeekBarChangeListener(a l) {
        this.i = l;
    }

    void b() {
        super.b();
        if (this.i != null) {
            this.i.a(this);
        }
    }

    void c() {
        super.c();
        if (this.i != null) {
            this.i.b(this);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(VerticalSeekBar.class.getName());
    }
}
