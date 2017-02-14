package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import com.meizu.common.app.SlideNotice;
import com.meizu.common.widget.SlidingMenu.c;
import com.meizu.common.widget.SlidingMenu.f;
import java.util.ArrayList;
import java.util.List;

public class CustomViewAbove extends ViewGroup {
    private static int F = 650;
    private static final DecelerateInterpolator c = new DecelerateInterpolator();
    private boolean A;
    private int B;
    private int C;
    private int D;
    private float E;
    protected VelocityTracker a;
    protected int b;
    private View d;
    private int e;
    private Scroller f;
    private boolean g;
    private boolean h;
    private boolean i;
    private boolean j;
    private int k;
    private float l;
    private float m;
    private float n;
    private int o;
    private int p;
    private int q;
    private int r;
    private boolean s;
    private CustomViewBehind t;
    private a u;
    private a v;
    private c w;
    private f x;
    private List<View> y;
    private int z;

    public interface a {
        void a(int i);

        void a(int i, float f, int i2);
    }

    public static class b implements a {
        public void a(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void a(int position) {
        }
    }

    public CustomViewAbove(Context context) {
        this(context, null);
    }

    public CustomViewAbove(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.o = -1;
        this.s = true;
        this.y = new ArrayList();
        this.z = 0;
        this.b = 1;
        this.A = false;
        this.B = -1;
        this.C = 0;
        this.D = 0;
        this.E = 0.0f;
        a();
    }

    void a() {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context = getContext();
        this.f = new Scroller(context, c);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.k = configuration.getScaledPagingTouchSlop();
        this.p = configuration.getScaledMinimumFlingVelocity();
        this.q = configuration.getScaledMaximumFlingVelocity();
        a(new b(this) {
            final /* synthetic */ CustomViewAbove a;

            {
                this.a = r1;
            }

            public void a(int position) {
                if (this.a.t != null) {
                    switch (position) {
                        case 0:
                        case 2:
                            this.a.t.setChildrenEnabled(true);
                            return;
                        case 1:
                            this.a.t.setChildrenEnabled(true);
                            return;
                        default:
                            return;
                    }
                }
            }
        });
        this.r = (int) (25.0f * context.getResources().getDisplayMetrics().density);
    }

    private int getWindowBackgroud() {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(new int[]{16842836});
        int background = a.getResourceId(0, 0);
        a.recycle();
        return background;
    }

    private void a(View view, int background) {
        int i;
        int i2 = 1;
        if (view.getBackground() == null) {
            i = 1;
        } else {
            i = 0;
        }
        if (view == null) {
            i2 = 0;
        }
        if ((i2 & i) != 0) {
            view.setBackgroundResource(background);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(CustomViewAbove.class.getName());
    }

    public void setCurrentItem(int item) {
        a(item, true, false);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        a(item, smoothScroll, false);
    }

    public void setCurrentItem(int item, boolean smoothScroll, boolean always) {
        a(item, smoothScroll, always);
    }

    public int getCurrentItem() {
        return this.e;
    }

    void a(int item, boolean smoothScroll, boolean always) {
        a(item, smoothScroll, always, 0);
    }

    void a(int item, boolean smoothScroll, boolean always, int velocity) {
        if (always || this.e != item) {
            boolean dispatchSelected;
            item = this.t.a(item);
            if (this.e != item) {
                dispatchSelected = true;
            } else {
                dispatchSelected = false;
            }
            this.e = item;
            int destX = a(this.e);
            if (dispatchSelected && this.u != null) {
                this.u.a(item);
            }
            if (dispatchSelected && this.v != null) {
                this.v.a(item);
            }
            if (smoothScroll) {
                a(destX, 0, velocity);
                return;
            }
            g();
            scrollTo(destX, 0);
            return;
        }
        setScrollingCacheEnabled(false);
    }

    public void setOnPageChangeListener(a listener) {
        this.u = listener;
    }

    public void setOnOpenedListener(f l) {
        this.x = l;
    }

    public void setOnClosedListener(c l) {
        this.w = l;
    }

    a a(a listener) {
        a oldListener = this.v;
        this.v = listener;
        return oldListener;
    }

    public int a(int page) {
        switch (page) {
            case 0:
            case 2:
                return this.t.a(this.d, page);
            case 1:
                return this.d.getLeft();
            default:
                return 0;
        }
    }

    public int getLeftBound() {
        return this.t.a(this.d);
    }

    public int getRightBound() {
        return this.t.b(this.d);
    }

    public int getContentLeft() {
        return this.d.getLeft() + this.d.getPaddingLeft();
    }

    public boolean b() {
        return this.e == 0 || this.e == 2;
    }

    private boolean a(MotionEvent ev) {
        Rect rect = new Rect();
        for (View v : this.y) {
            v.getHitRect(rect);
            if (rect.contains((int) ev.getX(), (int) ev.getY())) {
                return true;
            }
        }
        return false;
    }

    private boolean b(MotionEvent ev) {
        Rect rect = new Rect();
        this.d.getHitRect(rect);
        if (rect.contains((int) ev.getX(), (int) ev.getY())) {
            return true;
        }
        return false;
    }

    public int getBehindWidth() {
        if (this.t == null) {
            return 0;
        }
        return this.t.getBehindWidth();
    }

    public boolean c() {
        return this.s;
    }

    public void setSlidingEnabled(boolean b) {
        this.s = b;
    }

    void a(int x, int y, int velocity) {
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        int sx = getScrollX();
        int sy = getScrollY();
        int dx = x - sx;
        int dy = y - sy;
        if (dx == 0 && dy == 0) {
            g();
            if (b()) {
                if (this.x != null) {
                    this.x.a();
                    return;
                }
                return;
            } else if (this.w != null) {
                this.w.a();
                return;
            } else {
                return;
            }
        }
        setScrollingCacheEnabled(true);
        this.h = true;
        int duration = Math.min((int) ((2.0f + (((float) Math.abs(dx)) / ((float) getBehindWidth()))) * 100.0f), SlideNotice.SHOW_ANIMATION_DURATION);
        if (this.t.getVisibility() != 0) {
            this.t.setVisibility(0);
        }
        this.f.startScroll(sx, sy, dx, dy, duration);
        invalidate();
    }

    public void setContent(View v) {
        if (this.d != null) {
            removeView(this.d);
        }
        this.d = v;
        addView(this.d, -1, -1);
        a(this.d, getWindowBackgroud());
    }

    public View getContent() {
        return this.d;
    }

    public void setCustomViewBehind(CustomViewBehind cvb) {
        this.t = cvb;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
        this.d.measure(getChildMeasureSpec(widthMeasureSpec, 0, width), getChildMeasureSpec(heightMeasureSpec, 0, height));
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw) {
            g();
            scrollTo(a(this.e), getScrollY());
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.d.layout(0, 0, this.d.getMeasuredWidth(), this.d.getMeasuredHeight());
    }

    public void setAboveOffsetLeft(int i) {
        this.t.setVisibleAlways(i > 0);
        this.z = i;
        requestLayout();
    }

    public int getAboveOffsetLeft() {
        return this.z;
    }

    public void computeScroll() {
        if (this.f.isFinished() || !this.f.computeScrollOffset()) {
            g();
            return;
        }
        int oldX = getScrollX();
        int oldY = getScrollY();
        int x = this.f.getCurrX();
        int y = this.f.getCurrY();
        if (!(oldX == x && oldY == y)) {
            scrollTo(x, y);
            b(x);
        }
        invalidate();
    }

    public void b(int xpos) {
        int widthWithMargin = getWidth();
        int offsetPixels = xpos % widthWithMargin;
        a(xpos / widthWithMargin, ((float) offsetPixels) / ((float) widthWithMargin), offsetPixels);
    }

    protected void a(int position, float offset, int offsetPixels) {
        if (this.u != null) {
            this.u.a(position, offset, offsetPixels);
        }
        if (this.v != null) {
            this.v.a(position, offset, offsetPixels);
        }
    }

    private void g() {
        if (this.h) {
            setScrollingCacheEnabled(false);
            this.f.abortAnimation();
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = this.f.getCurrX();
            int y = this.f.getCurrY();
            if (!(oldX == x && oldY == y)) {
                scrollTo(x, y);
            }
            if (b()) {
                if (this.x != null) {
                    this.x.a();
                }
            } else if (this.w != null) {
                this.w.a();
            }
        }
        this.h = false;
    }

    public void setTouchMode(int i) {
        this.b = i;
    }

    public int getTouchMode() {
        return this.b;
    }

    private boolean c(MotionEvent ev) {
        int x = (int) (ev.getX() + this.E);
        if (b()) {
            return this.t.a(this.d, this.e, (float) x);
        }
        switch (this.b) {
            case 0:
                return this.t.b(this.d, x);
            case 1:
                if (a(ev) || !b(ev)) {
                    return false;
                }
                return true;
            case 2:
                return false;
            default:
                return false;
        }
    }

    public boolean a(float dx) {
        if (b()) {
            return this.t.b(dx);
        }
        return this.t.a(dx);
    }

    private int a(MotionEvent ev, int id) {
        int activePointerIndex = ev.findPointerIndex(id);
        if (activePointerIndex == -1) {
            this.o = -1;
        }
        return activePointerIndex;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.s) {
            if (this.t.a()) {
                this.i = false;
                return true;
            }
            int action = ev.getAction() & 255;
            if (action == 3 || action == 1 || (action != 0 && this.j)) {
                i();
                return false;
            }
            if (this.a == null) {
                this.a = VelocityTracker.obtain();
            }
            this.a.addMovement(ev);
            switch (action) {
                case 0:
                    this.o = (VERSION.SDK_INT >= 8 ? 65280 : 65280) & ev.getAction();
                    float x = ev.getX(this.o);
                    this.l = x;
                    this.m = x;
                    this.n = ev.getY(this.o);
                    if (c(ev)) {
                        this.i = false;
                        this.j = false;
                        if (b() && this.t.b(this.d, this.e, ev.getX() + this.E)) {
                            this.A = true;
                        }
                    } else {
                        this.j = true;
                    }
                    this.C = 0;
                    this.D = 0;
                    break;
                case 2:
                    if (this.b == 0 && !b()) {
                        VelocityTracker velocityTracker = this.a;
                        velocityTracker.computeCurrentVelocity(1000, (float) this.q);
                        int velocity = (int) velocityTracker.getXVelocity(this.o);
                        if (velocity > 0) {
                            this.C += velocity;
                            this.D++;
                        }
                    }
                    int activePointerId = this.o;
                    if (activePointerId != -1) {
                        int pointerIndex = a(ev, activePointerId);
                        if (pointerIndex != -1) {
                            float x2 = ev.getX(pointerIndex);
                            float dx = x2 - this.m;
                            float xDiff = Math.abs(dx);
                            float yDiff = Math.abs(ev.getY(pointerIndex) - this.n);
                            if (xDiff <= ((float) this.k) || xDiff <= yDiff || !a(dx)) {
                                if (yDiff > ((float) this.k)) {
                                    this.j = true;
                                    break;
                                }
                            } else if (this.b != 0 || this.D <= 0 || this.C / this.D <= F) {
                                h();
                                this.m = x2;
                                setScrollingCacheEnabled(true);
                                break;
                            } else {
                                return false;
                            }
                        }
                    }
                    break;
                case 6:
                    this.C = 0;
                    this.D = 0;
                    d(ev);
                    break;
            }
            if (this.i || this.A) {
                return true;
            }
            return false;
        } else if (b()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.s) {
            if (!this.i && !c(ev)) {
                return false;
            }
            if (this.t.a()) {
                this.i = false;
                return false;
            }
            int action = ev.getAction();
            if (this.a == null) {
                this.a = VelocityTracker.obtain();
            }
            this.a.addMovement(ev);
            int activePointerIndex;
            int pointerIndex;
            switch (action & 255) {
                case 0:
                    g();
                    float x = ev.getX();
                    this.l = x;
                    this.m = x;
                    this.o = ev.getPointerId(0);
                    this.C = 0;
                    this.D = 0;
                    break;
                case 1:
                    this.C = 0;
                    this.D = 0;
                    if (!this.i) {
                        if (this.A && this.t.b(this.d, this.e, ev.getX() + this.E)) {
                            setCurrentItem(1);
                            i();
                            break;
                        }
                    }
                    VelocityTracker velocityTracker = this.a;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.q);
                    int initialVelocity = (int) velocityTracker.getXVelocity(this.o);
                    float pageOffset = ((float) (getScrollX() - a(this.e))) / ((float) getBehindWidth());
                    activePointerIndex = a(ev, this.o);
                    if (this.o != -1) {
                        a(a(pageOffset, initialVelocity, (int) (ev.getX(activePointerIndex) - this.l)), true, true, initialVelocity);
                    } else {
                        a(this.e, true, true, initialVelocity);
                    }
                    this.o = -1;
                    i();
                    break;
                case 2:
                    float x2;
                    if (!this.i) {
                        if (this.o != -1) {
                            pointerIndex = a(ev, this.o);
                            if (pointerIndex != -1) {
                                x2 = ev.getX(pointerIndex);
                                float dx = x2 - this.m;
                                float xDiff = Math.abs(dx);
                                float yDiff = Math.abs(ev.getY(pointerIndex) - this.n);
                                if ((xDiff <= ((float) this.k) && (!this.A || xDiff <= ((float) (this.k / 4)))) || xDiff <= yDiff || !a(dx)) {
                                    return false;
                                }
                                h();
                                this.m = x2;
                                setScrollingCacheEnabled(true);
                            }
                        }
                    }
                    if (this.i) {
                        activePointerIndex = a(ev, this.o);
                        if (this.o != -1) {
                            x2 = ev.getX(activePointerIndex);
                            float deltaX = this.m - x2;
                            this.m = x2;
                            float scrollX = ((float) getScrollX()) + deltaX;
                            float leftBound = (float) getLeftBound();
                            float rightBound = (float) getRightBound();
                            if (scrollX < leftBound) {
                                scrollX = leftBound;
                            } else if (scrollX > rightBound) {
                                scrollX = rightBound;
                            }
                            this.m += scrollX - ((float) ((int) scrollX));
                            scrollTo((int) scrollX, getScrollY());
                            b((int) scrollX);
                            break;
                        }
                    }
                    break;
                case 3:
                    if (this.i) {
                        a(this.e, true, true);
                        this.o = -1;
                        i();
                        break;
                    }
                    break;
                case 5:
                    int index = ev.getActionIndex();
                    this.m = ev.getX(index);
                    this.o = ev.getPointerId(index);
                    break;
                case 6:
                    d(ev);
                    pointerIndex = a(ev, this.o);
                    if (this.o != -1) {
                        this.m = ev.getX(pointerIndex);
                        break;
                    }
                    break;
            }
            return true;
        } else if (c(ev)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean d() {
        return this.i;
    }

    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        this.E = (float) x;
        this.t.a(this.d, x, y);
    }

    int a(float pageOffset, int velocity, int deltaX) {
        int targetPage = this.e;
        if (Math.abs(deltaX) <= this.r || Math.abs(velocity) <= this.p) {
            return Math.round(((float) this.e) + pageOffset);
        }
        if (velocity > 0 && deltaX > 0) {
            return targetPage - 1;
        }
        if (velocity >= 0 || deltaX >= 0) {
            return targetPage;
        }
        return targetPage + 1;
    }

    protected float getPercentOpen() {
        return Math.abs(this.E - ((float) this.d.getLeft())) / ((float) getBehindWidth());
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.t.a(this.d, canvas);
        this.t.a(this.d, canvas, getPercentOpen());
        this.t.b(this.d, canvas, getPercentOpen());
    }

    private void d(MotionEvent ev) {
        int pointerIndex = ev.getActionIndex();
        if (ev.getPointerId(pointerIndex) == this.o) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            this.m = ev.getX(newPointerIndex);
            this.o = ev.getPointerId(newPointerIndex);
            if (this.a != null) {
                this.a.clear();
            }
        }
    }

    private void h() {
        this.i = true;
        this.A = false;
    }

    private void i() {
        this.A = false;
        this.i = false;
        this.j = false;
        this.o = -1;
        if (this.a != null) {
            try {
                this.a.recycle();
            } catch (IllegalStateException e) {
            }
            this.a = null;
        }
    }

    private void setScrollingCacheEnabled(boolean enabled) {
        if (this.g != enabled) {
            this.g = enabled;
        }
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event) || a(event);
    }

    public boolean a(KeyEvent event) {
        if (event.getAction() != 0) {
            return false;
        }
        switch (event.getKeyCode()) {
            case 21:
                return c(17);
            case 22:
                return c(66);
            case 61:
                if (VERSION.SDK_INT < 11) {
                    return false;
                }
                if (event.hasNoModifiers()) {
                    return c(2);
                }
                if (event.hasModifiers(1)) {
                    return c(1);
                }
                return false;
            default:
                return false;
        }
    }

    public boolean c(int direction) {
        View currentFocused = findFocus();
        if (currentFocused == this) {
            currentFocused = null;
        }
        boolean handled = false;
        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);
        if (nextFocused == null || nextFocused == currentFocused) {
            if (direction == 17 || direction == 1) {
                handled = e();
            } else if (direction == 66 || direction == 2) {
                handled = f();
            }
        } else if (direction == 17) {
            handled = nextFocused.requestFocus();
        } else if (direction == 66) {
            handled = (currentFocused == null || nextFocused.getLeft() > currentFocused.getLeft()) ? nextFocused.requestFocus() : f();
        }
        if (handled) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
        }
        return handled;
    }

    boolean e() {
        if (this.e <= 0) {
            return false;
        }
        setCurrentItem(this.e - 1, true);
        return true;
    }

    boolean f() {
        if (this.e >= 1) {
            return false;
        }
        setCurrentItem(this.e + 1, true);
        return true;
    }

    public void setCriticalVelocity(int velocity) {
        F = velocity;
    }
}
