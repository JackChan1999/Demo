package com.meizu.common.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnDrawListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NoClipView extends View implements OnDrawListener, OnGlobalLayoutListener {
    private static final Method c = getMethod();
    private static final Field d = getField();
    private ViewTreeObserver a;
    private Rect b = new Rect();

    public NoClipView(Context context) {
        super(context);
    }

    public NoClipView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoClipView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.a == null) {
            this.a = getViewTreeObserver();
            this.a.addOnDrawListener(this);
            this.a.addOnGlobalLayoutListener(this);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.a != null) {
            this.a.removeOnDrawListener(this);
            this.a.removeOnGlobalLayoutListener(this);
            this.a = null;
        }
    }

    public void onDraw() {
        try {
            Rect diryRect = (Rect) d.get(c.invoke(this, new Object[0]));
            if (diryRect != null && Rect.intersects(this.b, diryRect) && !diryRect.contains(this.b)) {
                diryRect.union(this.b);
            }
        } catch (Exception e) {
        }
    }

    public void onGlobalLayout() {
        getGlobalVisibleRect(this.b);
    }

    private static Method getMethod() {
        try {
            return View.class.getDeclaredMethod("getViewRootImpl", new Class[0]);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static Field getField() {
        try {
            Field field = Class.forName("android.view.ViewRootImpl").getDeclaredField("mDirty");
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            return null;
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(NoClipView.class.getName());
    }
}
