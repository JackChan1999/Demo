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
    private static final Field sDirtyField = getField();
    private static final Method sGetViewRootImplMethod = getMethod();
    private ViewTreeObserver mViewTreeObserver;
    private Rect mVisibleRect = new Rect();

    public NoClipView(Context context) {
        super(context);
    }

    public NoClipView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NoClipView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mViewTreeObserver == null) {
            this.mViewTreeObserver = getViewTreeObserver();
            this.mViewTreeObserver.addOnDrawListener(this);
            this.mViewTreeObserver.addOnGlobalLayoutListener(this);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mViewTreeObserver != null) {
            this.mViewTreeObserver.removeOnDrawListener(this);
            this.mViewTreeObserver.removeOnGlobalLayoutListener(this);
            this.mViewTreeObserver = null;
        }
    }

    public void onDraw() {
        try {
            Rect rect = (Rect) sDirtyField.get(sGetViewRootImplMethod.invoke(this, new Object[0]));
            if (rect != null && Rect.intersects(this.mVisibleRect, rect) && !rect.contains(this.mVisibleRect)) {
                rect.union(this.mVisibleRect);
            }
        } catch (Exception e) {
        }
    }

    public void onGlobalLayout() {
        getGlobalVisibleRect(this.mVisibleRect);
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
            Field declaredField = Class.forName("android.view.ViewRootImpl").getDeclaredField("mDirty");
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Exception e) {
            return null;
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(NoClipView.class.getName());
    }
}
