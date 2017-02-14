package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnDrawListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BlurRectView extends FrameLayout implements OnDrawListener, OnGlobalLayoutListener {
    private int a = 6;
    private int b = 7;
    private Bitmap c;
    private View d;
    private boolean e = false;
    private boolean f = false;
    private Drawable g;
    private Rect h = new Rect();
    private ViewTreeObserver i;
    private Field j;
    private Object k;
    private Method l;
    private Object m;

    public BlurRectView(Context context) {
        super(context);
    }

    public BlurRectView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BlurRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onGlobalLayout() {
        if (!this.f) {
            this.f = a();
        }
        getGlobalVisibleRect(this.h);
    }

    public void onDraw() {
        Rect dirty = null;
        if (this.j != null) {
            try {
                dirty = (Rect) this.j.get(this.k);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
        }
        boolean isDirty = dirty == null ? false : Rect.intersects(this.h, dirty);
        if (this.f && isDirty) {
            b();
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.i == null) {
            this.i = getViewTreeObserver();
        }
        this.i.addOnGlobalLayoutListener(this);
        this.i.addOnDrawListener(this);
        getWindowBackgroundDrawable();
        try {
            this.k = View.class.getDeclaredMethod("getViewRootImpl", new Class[0]).invoke(this, new Object[0]);
            this.j = this.k.getClass().getDeclaredField("mDirty");
            this.j.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
        } catch (NoSuchFieldException e5) {
            e5.printStackTrace();
        }
        try {
            Class<?> c = Class.forName("android.graphics.MZImageProcessing");
            this.l = c.getDeclaredMethod("stackBlurMultiThreadProcessedByNative", new Class[]{Bitmap.class, Integer.TYPE});
            this.m = c.getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (ClassNotFoundException e6) {
            e6.printStackTrace();
        } catch (NoSuchMethodException e7) {
            e7.printStackTrace();
        } catch (IllegalAccessException e22) {
            e22.printStackTrace();
        } catch (IllegalArgumentException e32) {
            e32.printStackTrace();
        } catch (InvocationTargetException e42) {
            e42.printStackTrace();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.i != null) {
            this.i.removeOnGlobalLayoutListener(this);
            this.i.removeOnDrawListener(this);
        }
    }

    public void draw(Canvas canvas) {
        if (!(this.e || this.c == null || this.c.isRecycled())) {
            canvas.drawBitmap(this.c, 0.0f, 0.0f, null);
            canvas.drawBitmap(this.c, new Rect(0, 0, this.c.getWidth(), this.c.getHeight()), new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), null);
        }
        super.draw(canvas);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.c != null && !this.c.isRecycled()) {
            this.c.recycle();
            this.c = null;
            if (this.e) {
                setBackground(null);
            }
        }
    }

    private void getWindowBackgroundDrawable() {
        int[] attrs = new int[]{16842836};
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(16842836, outValue, true);
        TypedArray style = getContext().getTheme().obtainStyledAttributes(outValue.resourceId, attrs);
        this.g = style.getDrawable(0);
        style.recycle();
    }

    private boolean a() {
        ViewParent view = getParent();
        if (view instanceof FrameLayout) {
            FrameLayout parent = (FrameLayout) view;
            boolean foundme = false;
            for (int i = parent.getChildCount() - 1; i >= 0; i--) {
                View child = parent.getChildAt(i);
                if (foundme && child.getVisibility() == 0) {
                    if (new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom()).intersect(getLeft(), getTop(), getRight(), getBottom())) {
                        this.d = child;
                        return true;
                    }
                } else if (child == this) {
                    foundme = true;
                }
            }
        }
        return false;
    }

    private void b() {
        int downSampling = this.a;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        float scale = 1.0f / ((float) downSampling);
        boolean forceRefresh = false;
        if (this.c == null) {
            this.c = Bitmap.createBitmap(width / downSampling, height / downSampling, Config.ARGB_8888);
            forceRefresh = true;
        } else {
            this.c.eraseColor(0);
        }
        Canvas c = new Canvas(this.c);
        this.g.setBounds(new Rect(0, 0, width, height));
        this.g.draw(c);
        c.scale(scale, scale);
        a(c);
        try {
            this.l.invoke(this.m, new Object[]{this.c, Integer.valueOf(this.b)});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        if (forceRefresh && getBackground() == null) {
            this.e = true;
            setBackground(new BitmapDrawable(getContext().getResources(), this.c));
        }
    }

    private void a(Canvas canvas) {
        int x = (this.d.getLeft() - getLeft()) - this.d.getScrollX();
        int y = (this.d.getTop() - getTop()) - this.d.getScrollY();
        canvas.translate((float) x, (float) y);
        this.d.draw(canvas);
        canvas.translate((float) (-x), (float) (-y));
    }

    public void setBlurRadius(int radius) {
        this.b = Math.abs(radius);
    }

    public void setBlurDownScale(int downscale) {
        this.a = Math.abs(downscale);
        this.a = Math.max(1, this.a);
    }

    public void setBlurRaidusAndDownScale(int radius, int downscale) {
        setBlurRadius(radius);
        setBlurDownScale(downscale);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(BlurRectView.class.getName());
    }
}
