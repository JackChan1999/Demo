package com.meizu.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.meizu.common.a.c;
import com.meizu.common.widget.SlidingMenu.a;

public class CustomViewBehind extends ViewGroup {
    private static int F = 100;
    private int A;
    private float B;
    private boolean C;
    private Bitmap D;
    private View E;
    private int a;
    private CustomViewAbove b;
    private View c;
    private View d;
    private int e;
    private int f;
    private a g;
    private boolean h;
    private boolean i;
    private boolean j;
    private int k;
    private float l;
    private float m;
    private int n;
    private VelocityTracker o;
    private int p;
    private int q;
    private float r;
    private boolean s;
    private int[] t;
    private int u;
    private boolean v;
    private final Paint w;
    private float x;
    private Drawable y;
    private Drawable z;

    public CustomViewBehind(Context context) {
        this(context, null);
    }

    public CustomViewBehind(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.a = 0;
        this.i = false;
        this.k = -1;
        this.t = new int[2];
        this.w = new Paint();
        this.C = true;
        this.e = (int) TypedValue.applyDimension(1, 50.0f, getResources().getDisplayMetrics());
        setGestureAreaWidth(this.e);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        this.n = configuration.getScaledTouchSlop();
        this.p = configuration.getScaledMinimumFlingVelocity();
        this.q = configuration.getScaledMaximumFlingVelocity();
        setBackgroundColor(getResources().getColor(c.mz_slidingmenu_background_light));
    }

    public void setCustomViewAbove(CustomViewAbove customViewAbove) {
        this.b = customViewAbove;
    }

    public void setCanvasTransformer(a t) {
        this.g = t;
    }

    public void setMenuWidth(int i) {
        this.s = this.f != i;
        this.f = i;
        requestLayout();
    }

    public int getBehindWidth() {
        return this.c.getWidth();
    }

    public void setContent(View v) {
        if (this.c != null) {
            removeView(this.c);
        }
        this.c = v;
        addView(this.c);
    }

    public View getContent() {
        return this.c;
    }

    public void setSecondaryContent(View v) {
        if (this.d != null) {
            removeView(this.d);
        }
        this.d = v;
        addView(this.d);
    }

    public View getSecondaryContent() {
        return this.d;
    }

    public void setChildrenEnabled(boolean enabled) {
        this.h = enabled;
    }

    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        if (this.g != null) {
            invalidate();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!this.b.c()) {
            return false;
        }
        if (this.b.d()) {
            this.j = false;
            return true;
        }
        int action = ev.getAction();
        if (action == 2 && this.j) {
            return true;
        }
        int x;
        int y;
        switch (action & 255) {
            case 0:
                x = (int) ev.getX();
                y = (int) ev.getY();
                this.j = false;
                float x2 = (float) ((int) ev.getX());
                this.r = x2;
                this.l = x2;
                this.m = (float) y;
                this.k = ev.getPointerId(0);
                b();
                this.o.addMovement(ev);
                break;
            case 1:
            case 3:
                this.j = false;
                this.k = -1;
                d();
                break;
            case 2:
                int activePointerId = this.k;
                if (activePointerId != -1) {
                    int pointerIndex = ev.findPointerIndex(activePointerId);
                    x = (int) ev.getX(pointerIndex);
                    y = (int) ev.getY(pointerIndex);
                    int xDiff = (int) Math.abs(this.l - ((float) x));
                    int yDiff = (int) Math.abs(this.m - ((float) y));
                    if (xDiff > this.n && xDiff - yDiff > 0 && this.b.a(((float) x) - this.l)) {
                        this.j = true;
                        this.l = (float) x;
                        this.m = (float) y;
                        c();
                        this.o.addMovement(ev);
                        if (getParent() != null) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                            break;
                        }
                    }
                }
                break;
            case 5:
                int index = ev.getActionIndex();
                this.l = (float) ((int) ev.getX(index));
                this.m = (float) ((int) ev.getY(index));
                this.k = ev.getPointerId(index);
                break;
            case 6:
                a(ev);
                this.l = (float) ((int) ev.getX(ev.findPointerIndex(this.k)));
                this.m = (float) ((int) ev.getY(ev.findPointerIndex(this.k)));
                break;
        }
        return this.j;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.b.c()) {
            return super.onTouchEvent(ev);
        }
        if (this.b.d()) {
            this.j = false;
            return false;
        }
        c();
        this.o.addMovement(ev);
        switch (ev.getAction() & 255) {
            case 0:
                if (getChildCount() != 0) {
                    float x = (float) ((int) ev.getX());
                    this.r = x;
                    this.l = x;
                    this.m = (float) ((int) ev.getY());
                    this.k = ev.getPointerId(0);
                    break;
                }
                return false;
            case 1:
            case 3:
                if (this.j) {
                    VelocityTracker velocityTracker = this.o;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.q);
                    int initialVelocity = (int) velocityTracker.getXVelocity(this.k);
                    float pageOffset = ((float) (this.b.getScrollX() - this.b.a(this.b.getCurrentItem()))) / ((float) getBehindWidth());
                    int pointerIndex = ev.findPointerIndex(this.k);
                    if (this.k != -1) {
                        this.b.a(this.b.a(pageOffset, initialVelocity, (int) (ev.getX(pointerIndex) - this.r)), true, true, initialVelocity);
                    } else {
                        this.b.a(this.b.getCurrentItem(), true, true, initialVelocity);
                    }
                    this.k = -1;
                    this.j = false;
                    d();
                    break;
                }
                break;
            case 2:
                int activePointerIndex = ev.findPointerIndex(this.k);
                int x2 = (int) ev.getX(activePointerIndex);
                int y = (int) ev.getY(activePointerIndex);
                float deltaX = this.l - ((float) x2);
                float deltaY = this.m - ((float) y);
                if (!this.j && Math.abs(deltaX) > ((float) (this.n / 2)) && Math.abs(deltaX) - Math.abs(deltaY) > 0.0f && this.b.a(((float) x2) - this.l)) {
                    ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    this.j = true;
                    deltaX = deltaX > 0.0f ? deltaX - ((float) this.n) : deltaX + ((float) this.n);
                }
                if (this.j) {
                    this.l = (float) x2;
                    this.m = (float) y;
                    float scrollX = ((float) this.b.getScrollX()) + deltaX;
                    float leftBound = (float) this.b.getLeftBound();
                    float rightBound = (float) this.b.getRightBound();
                    if (scrollX < leftBound) {
                        scrollX = leftBound;
                    } else if (scrollX > rightBound) {
                        scrollX = rightBound;
                    }
                    this.l += scrollX - ((float) ((int) scrollX));
                    this.b.scrollTo((int) scrollX, getScrollY());
                    this.b.b((int) scrollX);
                    break;
                }
                break;
            case 6:
                a(ev);
                break;
        }
        return true;
    }

    public boolean a() {
        return this.j;
    }

    public void setVisibleAlways(boolean visible) {
        this.i = visible;
    }

    private void a(MotionEvent ev) {
        int pointerIndex = ev.getActionIndex();
        if (ev.getPointerId(pointerIndex) == this.k) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            this.l = ev.getX(newPointerIndex);
            this.k = ev.getPointerId(newPointerIndex);
            d();
        }
    }

    private void b() {
        if (this.o == null) {
            this.o = VelocityTracker.obtain();
        } else {
            this.o.clear();
        }
    }

    private void c() {
        if (this.o == null) {
            this.o = VelocityTracker.obtain();
        }
    }

    private void d() {
        if (this.o != null) {
            try {
                this.o.recycle();
            } catch (IllegalStateException e) {
            }
            this.o = null;
        }
    }

    protected void dispatchDraw(Canvas canvas) {
        if (this.g != null) {
            canvas.save();
            this.g.a(canvas, this.b.getPercentOpen());
            super.dispatchDraw(canvas);
            canvas.restore();
            return;
        }
        super.dispatchDraw(canvas);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int height = b - t;
        int childWidth = Math.min(this.c.getMeasuredWidth(), r - l);
        int childHeight = Math.min(this.c.getMeasuredHeight(), height);
        this.c.layout(0, 0, childWidth, childHeight);
        if (this.d != null) {
            this.d.layout(0, 0, childWidth, childHeight);
        }
        if (this.s) {
            switch (this.b.getCurrentItem()) {
                case 0:
                    this.b.setCurrentItem(0, false, true);
                    break;
                case 2:
                    this.b.setCurrentItem(2, false, true);
                    break;
            }
            this.s = false;
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
        int contentWidth = getChildMeasureSpec(widthMeasureSpec, 0, Math.min(width, this.f));
        int contentHeight = getChildMeasureSpec(heightMeasureSpec, 0, height);
        this.c.measure(contentWidth, contentHeight);
        if (this.d != null) {
            this.d.measure(contentWidth, contentHeight);
        }
    }

    public void setMode(int mode) {
        if (mode == 0 || mode == 1) {
            if (this.c != null) {
                this.c.setVisibility(0);
            }
            if (this.d != null) {
                this.d.setVisibility(4);
            }
        }
        this.u = mode;
    }

    public int getMode() {
        return this.u;
    }

    public void setScrollScale(float scrollScale) {
        this.x = scrollScale;
    }

    public float getScrollScale() {
        return this.x;
    }

    public void setShadowDrawable(Drawable shadow) {
        this.y = shadow;
        invalidate();
    }

    public void setSecondaryShadowDrawable(Drawable shadow) {
        this.z = shadow;
        invalidate();
    }

    public void setShadowWidth(int width) {
        this.A = width;
        invalidate();
    }

    public void setFadeEnabled(boolean b) {
        this.v = b;
    }

    public void setFadeDegree(float degree) {
        if (degree > 1.0f || degree < 0.0f) {
            throw new IllegalStateException("The BehindFadeDegree must be between 0.0f and 1.0f");
        }
        this.B = degree;
    }

    public int a(int page) {
        if (page > 1) {
            page = 2;
        } else if (page < 1) {
            page = 0;
        }
        if (this.u == 0 && page > 1) {
            return 0;
        }
        if (this.u != 1 || page >= 1) {
            return page;
        }
        return 2;
    }

    public void a(View content, int x, int y) {
        int vis = 0;
        if (this.u == 0) {
            if (x >= content.getLeft() && !this.i) {
                vis = 4;
            }
            scrollTo((int) (((float) (getBehindWidth() + x)) * this.x), y);
        } else if (this.u == 1) {
            if (x <= content.getLeft() && !this.i) {
                vis = 4;
            }
            scrollTo((int) (((float) (getBehindWidth() - getWidth())) + (((float) (x - getBehindWidth())) * this.x)), y);
        } else if (this.u == 2) {
            int i;
            this.c.setVisibility(x >= content.getLeft() ? 4 : 0);
            View view = this.d;
            if (x > content.getLeft() || this.i) {
                i = 0;
            } else {
                i = 4;
            }
            view.setVisibility(i);
            if (x == 0) {
                vis = 4;
            } else {
                vis = 0;
            }
            if (x <= content.getLeft()) {
                scrollTo((int) (((float) (getBehindWidth() + x)) * this.x), y);
            } else {
                scrollTo((int) (((float) (getBehindWidth() - getWidth())) + (((float) (x - getBehindWidth())) * this.x)), y);
            }
        }
        if (vis == 4) {
            Log.v("CustomViewBehind", "behind INVISIBLE");
        }
        setVisibility(vis);
        if (vis == 0) {
            invalidate();
        }
    }

    public int a(View content, int page) {
        int widthOffset = 0;
        if (this.b != null) {
            widthOffset = this.b.getAboveOffsetLeft();
        }
        if (this.u != 0) {
            if (this.u != 1) {
                if (this.u == 2) {
                    switch (page) {
                        case 0:
                            return (content.getLeft() + widthOffset) - getBehindWidth();
                        case 2:
                            return (content.getLeft() + widthOffset) + getBehindWidth();
                        default:
                            break;
                    }
                }
            }
            switch (page) {
                case 0:
                    return content.getLeft() + widthOffset;
                case 2:
                    return (content.getLeft() + widthOffset) + getBehindWidth();
                default:
                    break;
            }
        }
        switch (page) {
            case 0:
                return (content.getLeft() + widthOffset) - getBehindWidth();
            case 2:
                return content.getLeft() + widthOffset;
        }
        return content.getLeft();
    }

    public int a(View content) {
        int widthOffset = 0;
        if (this.b != null) {
            widthOffset = this.b.getAboveOffsetLeft();
        }
        if (this.u == 0 || this.u == 2) {
            return (content.getLeft() - getBehindWidth()) + widthOffset;
        }
        if (this.u == 1) {
            return content.getLeft();
        }
        return 0;
    }

    public int b(View content) {
        if (this.u == 0) {
            return content.getLeft();
        }
        if (this.u == 1 || this.u == 2) {
            return content.getLeft() + getBehindWidth();
        }
        return 0;
    }

    public boolean b(View content, int x) {
        int left = content.getLeft();
        int right = content.getRight();
        if (this.u == 0) {
            if (x < left || x > F + left) {
                return false;
            }
            return true;
        } else if (this.u == 1) {
            if (x > right || x < right - F) {
                return false;
            }
            return true;
        } else if (this.u != 2) {
            return false;
        } else {
            if (x >= left && x <= F + left) {
                return true;
            }
            if (x > right || x < right - F) {
                return false;
            }
            return true;
        }
    }

    public void setTouchMode(int i) {
        this.a = i;
    }

    public boolean a(View content, int currPage, float x) {
        switch (this.a) {
            case 0:
                return b(content, currPage, x);
            case 1:
                return true;
            default:
                return false;
        }
    }

    public boolean b(View content, int currPage, float x) {
        if (this.u == 0 || (this.u == 2 && currPage == 0)) {
            if (x >= ((float) content.getLeft())) {
                return true;
            }
            return false;
        } else if (this.u != 1 && (this.u != 2 || currPage != 2)) {
            return false;
        } else {
            if (x > ((float) content.getRight())) {
                return false;
            }
            return true;
        }
    }

    public boolean a(float dx) {
        if (this.u == 0) {
            if (dx > 0.0f) {
                return true;
            }
            return false;
        } else if (this.u == 1) {
            if (dx >= 0.0f) {
                return false;
            }
            return true;
        } else if (this.u != 2) {
            return false;
        } else {
            return true;
        }
    }

    public boolean b(float dx) {
        if (this.u == 0) {
            if (dx < 0.0f) {
                return true;
            }
            return false;
        } else if (this.u == 1) {
            if (dx <= 0.0f) {
                return false;
            }
            return true;
        } else if (this.u != 2) {
            return false;
        } else {
            return true;
        }
    }

    public void a(View content, Canvas canvas) {
        if (this.y != null) {
            if (this.A <= 0) {
                this.A = this.y.getIntrinsicWidth();
            }
            int right = 0;
            if (this.u == 0) {
                content.getLocationInWindow(this.t);
                if (this.t[0] > 0) {
                    right = content.getLeft() + this.A;
                } else {
                    return;
                }
            } else if (this.u == 1) {
                right = content.getRight();
            } else if (this.u == 2) {
                if (this.z != null) {
                    right = content.getRight();
                    this.z.setBounds(right - this.A, 0, right, getHeight());
                    this.z.draw(canvas);
                }
                right = content.getLeft() + this.A;
            }
            this.y.setBounds(right - this.A, 0, right, getHeight());
            this.y.draw(canvas);
        }
    }

    public void a(View content, Canvas canvas, float openPercent) {
        if (this.v) {
            this.w.setColor(Color.argb((int) ((this.B * 255.0f) * Math.abs(1.0f - openPercent)), 0, 0, 0));
            int left = 0;
            int right = 0;
            if (this.u == 0) {
                left = content.getLeft() - getBehindWidth();
                right = content.getLeft();
            } else if (this.u == 1) {
                left = content.getRight();
                right = content.getRight() + getBehindWidth();
            } else if (this.u == 2) {
                Canvas canvas2 = canvas;
                canvas2.drawRect((float) (content.getLeft() - getBehindWidth()), 0.0f, (float) content.getLeft(), (float) getHeight(), this.w);
                left = content.getRight();
                right = content.getRight() + getBehindWidth();
            }
            canvas.drawRect((float) left, 0.0f, (float) right, (float) getHeight(), this.w);
        }
    }

    public void b(View content, Canvas canvas, float openPercent) {
        if (this.C && this.D != null && this.E != null && ((String) this.E.getTag()).equals("CustomViewBehindSelectedView")) {
            canvas.save();
            int offset = (int) (((float) this.D.getWidth()) * openPercent);
            int right;
            int left;
            if (this.u == 0) {
                right = content.getLeft();
                left = right - offset;
                canvas.clipRect(left, 0, right, getHeight());
                canvas.drawBitmap(this.D, (float) left, (float) getSelectorTop(), null);
            } else if (this.u == 1) {
                left = content.getRight();
                right = left + offset;
                canvas.clipRect(left, 0, right, getHeight());
                canvas.drawBitmap(this.D, (float) (right - this.D.getWidth()), (float) getSelectorTop(), null);
            }
            canvas.restore();
        }
    }

    public void setSelectorEnabled(boolean b) {
        this.C = b;
    }

    public void setSelectedView(View v) {
        if (this.E != null) {
            this.E.setTag("");
            this.E = null;
        }
        if (v != null && v.getParent() != null) {
            this.E = v;
            this.E.setTag("CustomViewBehindSelectedView");
            invalidate();
        }
    }

    private int getSelectorTop() {
        return this.E.getTop() + ((this.E.getHeight() - this.D.getHeight()) / 2);
    }

    public void setSelectorBitmap(Bitmap b) {
        this.D = b;
        refreshDrawableState();
    }

    public void setMenuVisibleAlways(boolean visibleAlways) {
        this.i = visibleAlways;
    }

    public void setGestureAreaWidth(int width) {
        F = width;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(CustomViewBehind.class.getName());
    }
}
