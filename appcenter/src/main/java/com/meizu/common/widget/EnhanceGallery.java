package com.meizu.common.widget;

import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.LongSparseArray;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.CollectionInfo;
import android.view.animation.DecelerateInterpolator;
import android.widget.Checkable;
import android.widget.SpinnerAdapter;

public class EnhanceGallery extends AbsSpinner implements OnGestureListener {
    f E;
    LongSparseArray<Integer> F;
    int G;
    int H;
    protected int I;
    private int J;
    private int K;
    private int L;
    private int M;
    private GestureDetector N;
    private int O;
    private View P;
    private c Q;
    private Runnable R;
    private boolean S;
    private View T;
    private boolean U;
    private boolean V;
    private boolean W;
    private i aA;
    private boolean aB;
    private g aC;
    private int aD;
    private boolean aa;
    private a ab;
    private boolean ac;
    private boolean ad;
    private int ae;
    private int af;
    private int ag;
    private int ah;
    private int ai;
    private int aj;
    private boolean ak;
    private int al;
    private int am;
    private int an;
    private ActionMode ao;
    private SparseBooleanArray ap;
    private d aq;
    private boolean ar;
    private int as;
    private int at;
    private int au;
    private int av;
    private int aw;
    private int ax;
    private Rect ay;
    private h az;

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    public static class a implements ContextMenuInfo {
        public View a;
        public int b;
        public long c;

        public a(View targetView, int position, long id) {
            this.a = targetView;
            this.b = position;
            this.c = id;
        }
    }

    public interface b {
        View a();

        boolean b();

        Point c();
    }

    private class c implements Runnable {
        final /* synthetic */ EnhanceGallery a;
        private h b;
        private int c;
        private int d = 0;
        private int e;

        public c(EnhanceGallery enhanceGallery) {
            this.a = enhanceGallery;
            this.b = new h(enhanceGallery.getContext());
            this.b.a(true, true);
        }

        private void b() {
            this.a.removeCallbacks(this);
        }

        public void a(int distance) {
            if (distance != 0) {
                this.a.J = 2;
                b();
                this.c = 0;
                this.b.a(new DecelerateInterpolator());
                this.b.a(0, 0, -distance, 0, this.a.L);
                this.a.postOnAnimation(this);
            }
        }

        public void a() {
            if (this.b.a(this.a.ae, 0, 0, 0, 0, 0)) {
                this.a.J = 4;
                this.d = this.a.ae;
                this.a.invalidate();
                this.a.postOnAnimation(this);
                return;
            }
            this.a.J = -1;
        }

        public void a(boolean scrollIntoSlots) {
            this.a.removeCallbacks(this);
            b(scrollIntoSlots);
        }

        private void b(boolean scrollIntoSlots) {
            this.b.a(true);
            if (scrollIntoSlots) {
                this.a.p();
            } else {
                this.a.c(0);
            }
        }

        public void run() {
            if (this.a.z == 0) {
                b(true);
                return;
            }
            h scroller = this.b;
            switch (this.a.J) {
                case 1:
                case 2:
                    this.a.S = false;
                    boolean more = scroller.c();
                    int x = scroller.b();
                    int delta = this.c - x;
                    boolean isAtEdge = this.a.b(delta);
                    if (more && !this.a.S && !isAtEdge) {
                        this.c = x;
                        this.e = delta;
                        this.a.post(this);
                        return;
                    } else if (more && !this.a.S && isAtEdge) {
                        b(false);
                        if (this.a.J == 2) {
                            this.a.J = 4;
                        } else {
                            this.a.J = 3;
                        }
                        if (this.a.aD != 2) {
                            this.a.c(2);
                        }
                        a();
                        return;
                    } else {
                        b(true);
                        return;
                    }
                case 3:
                case 4:
                    if (scroller.c()) {
                        int currX = scroller.b();
                        int deltaX = currX - this.d;
                        this.d = currX;
                        if (deltaX != 0) {
                            this.a.b(-deltaX);
                        }
                        this.a.invalidate();
                        this.a.postOnAnimation(this);
                        return;
                    }
                    b(false);
                    this.a.J = -1;
                    return;
                default:
                    this.a.J = -1;
                    if (this.a.aD != 0) {
                        this.a.c(0);
                        return;
                    }
                    return;
            }
        }
    }

    class d extends DragShadowBuilder {
        final /* synthetic */ EnhanceGallery a;
        private Drawable b;
        private Rect c;
        private int d;
        private int e;
        private boolean f;
        private Point g;
        private Drawable h;
        private Drawable i;
        private int j;

        public d(EnhanceGallery enhanceGallery, View view) {
            this(enhanceGallery, view, true, null);
        }

        public d(EnhanceGallery enhanceGallery, View view, boolean needBg, Point shadowTouchPoint) {
            this.a = enhanceGallery;
            super(view);
            this.f = true;
            this.g = null;
            this.j = -1;
            this.f = needBg;
            this.g = shadowTouchPoint;
            if (view != null) {
                if (needBg) {
                    this.b = enhanceGallery.getResources().getDrawable(enhanceGallery.as);
                    this.c = new Rect();
                    this.b.getPadding(this.c);
                    Rect padding = this.c;
                    int width = view.getWidth();
                    int height = view.getHeight();
                    this.d = (padding.left + width) + padding.right;
                    this.e = (padding.top + height) + padding.bottom;
                    this.b.setBounds(0, 0, this.d, this.e);
                    this.h = enhanceGallery.getResources().getDrawable(enhanceGallery.at);
                    this.h.setBounds(0, 0, this.d, this.e);
                    this.i = enhanceGallery.getResources().getDrawable(enhanceGallery.au);
                    this.i.setBounds(0, 0, this.d, this.e);
                } else {
                    this.d = view.getWidth();
                    this.e = view.getHeight();
                }
                enhanceGallery.av = 0;
                if (this.e > enhanceGallery.getHeight()) {
                    int[] listLocation = new int[2];
                    enhanceGallery.getLocationOnScreen(listLocation);
                    int[] dragLocation = new int[2];
                    view.getLocationOnScreen(dragLocation);
                    if (dragLocation[1] < listLocation[1]) {
                        enhanceGallery.av = listLocation[1] - dragLocation[1];
                        enhanceGallery.av = Math.min(this.e - enhanceGallery.getHeight(), enhanceGallery.av);
                    }
                    this.e = enhanceGallery.getHeight();
                }
            }
        }

        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
            shadowSize.set(this.d, this.e);
            if (this.f) {
                shadowTouchPoint.set(this.a.aw + this.c.left, (this.a.ax + this.c.top) - this.a.av);
            } else {
                shadowTouchPoint.set(this.a.aw, this.a.ax - this.a.av);
            }
        }

        public void onDrawShadow(Canvas canvas) {
            if (this.f) {
                if (this.j == 0) {
                    this.h.draw(canvas);
                } else if (this.j == 1) {
                    this.i.draw(canvas);
                } else {
                    this.b.draw(canvas);
                }
                canvas.save();
                canvas.translate((float) this.c.left, (float) (this.c.top - this.a.av));
                super.onDrawShadow(canvas);
                canvas.restore();
            } else if (this.a.av != 0) {
                canvas.save();
                canvas.translate(0.0f, (float) (-this.a.av));
                super.onDrawShadow(canvas);
                canvas.restore();
            } else {
                super.onDrawShadow(canvas);
            }
        }
    }

    public interface e extends Callback {
        void a(ActionMode actionMode, int i, long j, boolean z);
    }

    class f implements e {
        final /* synthetic */ EnhanceGallery a;
        private e b;

        f(EnhanceGallery enhanceGallery) {
            this.a = enhanceGallery;
        }

        public void a(e wrapped) {
            this.b = wrapped;
        }

        public boolean a() {
            return this.b != null;
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            if (!this.b.onCreateActionMode(mode, menu)) {
                return false;
            }
            if (this.a.am == 2) {
                this.a.setLongClickable(true);
                return true;
            }
            this.a.setLongClickable(false);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return this.b.onPrepareActionMode(mode, menu);
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return this.b.onActionItemClicked(mode, item);
        }

        public void onDestroyActionMode(ActionMode mode) {
            this.b.onDestroyActionMode(mode);
            this.a.ao = null;
            this.a.m();
            this.a.n();
            this.a.setLongClickable(true);
        }

        public void a(ActionMode mode, int position, long id, boolean checked) {
            this.b.a(mode, position, id, checked);
            if (this.a.getCheckedItemCount() == 0) {
                mode.finish();
            }
        }
    }

    public interface g {
        void a(EnhanceGallery enhanceGallery, int i);

        void a(EnhanceGallery enhanceGallery, int i, int i2, int i3);
    }

    private class j {
        private int a;
        final /* synthetic */ EnhanceGallery c;

        private j(EnhanceGallery enhanceGallery) {
            this.c = enhanceGallery;
        }

        public void a() {
            this.a = this.c.getWindowAttachCount();
        }

        public boolean b() {
            return this.c.hasWindowFocus() && this.c.getWindowAttachCount() == this.a;
        }
    }

    private class h extends j implements Runnable {
        int a;
        final /* synthetic */ EnhanceGallery b;

        private h(EnhanceGallery enhanceGallery) {
            this.b = enhanceGallery;
            super();
        }

        public void run() {
            if (!this.b.u) {
                SpinnerAdapter adapter = this.b.getAdapter();
                int motionPosition = this.a;
                if (adapter != null && this.b.z > 0 && motionPosition != -1 && motionPosition < adapter.getCount() && b()) {
                    View view = this.b.getChildAt(motionPosition - this.b.j);
                    if (view != null) {
                        this.b.d(view, motionPosition, adapter.getItemId(motionPosition));
                    }
                }
            }
        }
    }

    class i extends AccessibilityDelegate {
        final /* synthetic */ EnhanceGallery a;

        i(EnhanceGallery enhanceGallery) {
            this.a = enhanceGallery;
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            this.a.a(host, this.a.c(host), info);
        }

        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            if (super.performAccessibilityAction(host, action, args)) {
                return true;
            }
            return false;
        }
    }

    public EnhanceGallery(Context context) {
        this(context, null);
    }

    public EnhanceGallery(Context context, AttributeSet attrs) {
        this(context, attrs, com.meizu.common.a.b.MeizuCommon_EnhanceGalleryStyle);
    }

    public EnhanceGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.J = -1;
        this.K = 0;
        this.L = 250;
        this.Q = new c(this);
        this.R = new Runnable(this) {
            final /* synthetic */ EnhanceGallery a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.W = false;
                this.a.e();
            }
        };
        this.U = true;
        this.V = true;
        this.aa = false;
        this.ad = false;
        this.ak = false;
        this.am = 0;
        this.ar = false;
        this.as = com.meizu.common.a.e.mz_list_selector_background_long_pressed;
        this.at = com.meizu.common.a.e.mz_list_selector_background_filter;
        this.au = com.meizu.common.a.e.mz_list_selector_background_delete;
        this.av = 0;
        this.I = -1;
        this.aw = 0;
        this.ax = 0;
        this.aB = false;
        this.aD = 0;
        this.N = new GestureDetector(context, this);
        this.N.setIsLongpressEnabled(true);
        TypedArray a = context.obtainStyledAttributes(attrs, com.meizu.common.a.j.EnhanceGallery, defStyle, 0);
        setSpacing(a.getDimensionPixelSize(com.meizu.common.a.j.EnhanceGallery_mcSpacing, 10));
        this.ag = getResources().getDimensionPixelSize(com.meizu.common.a.d.mc_enhancegallery_max_overscroll_distance);
        this.af = a.getDimensionPixelSize(com.meizu.common.a.j.EnhanceGallery_mcMaxOverScrollDistance, this.ag);
        this.ak = a.getBoolean(com.meizu.common.a.j.EnhanceGallery_mcScrollEnableWhenLessContent, false);
        a.recycle();
    }

    public void setCallbackDuringFling(boolean shouldCallback) {
        this.U = shouldCallback;
    }

    public void setMaxOverScrollDistance(int distance) {
        if (distance < 0) {
            this.af = this.ag;
        } else {
            this.af = distance;
        }
    }

    public void setCallbackOnUnselectedItemClick(boolean shouldCallback) {
        this.V = shouldCallback;
    }

    public void setDragEnable(boolean flag) {
        this.aa = flag;
    }

    public void setAnimationDuration(int animationDurationMillis) {
        this.L = animationDurationMillis;
    }

    public void setSpacing(int spacing) {
        this.K = spacing;
    }

    public void setScrollEnableWhenLessContent(boolean enable) {
        this.ak = enable;
    }

    protected int computeHorizontalScrollExtent() {
        return 1;
    }

    protected int computeHorizontalScrollOffset() {
        return this.x;
    }

    protected int computeHorizontalScrollRange() {
        return this.z;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.q = true;
        b(0, false);
        this.q = false;
    }

    int a(View child) {
        return child.getMeasuredHeight();
    }

    boolean b(int deltaX) {
        int childCount = getChildCount();
        if (childCount == 0 || deltaX == 0) {
            return false;
        }
        boolean cannotScrollRight;
        boolean toLeft = deltaX < 0;
        boolean isAtEdge = false;
        boolean dontRecycle = false;
        boolean cannotScrollLeft;
        if (this.ad) {
            cannotScrollLeft = this.j == 0 && getChildAt(0).getRight() >= (getWidth() - getPaddingRight()) - this.K && deltaX <= 0;
            cannotScrollRight = this.j + childCount == this.z && getChildAt(childCount - 1).getLeft() >= getPaddingLeft() && deltaX >= 0;
        } else {
            cannotScrollRight = this.j == 0 && getChildAt(0).getLeft() >= getPaddingLeft() + this.K && deltaX >= 0;
            cannotScrollLeft = this.j + childCount == this.z && getChildAt(childCount - 1).getRight() <= getWidth() - getPaddingRight() && deltaX <= 0;
        }
        if (cannotScrollRight || cannotScrollLeft) {
            dontRecycle = true;
        }
        e(deltaX);
        if (!dontRecycle) {
            a(toLeft);
            if (toLeft) {
                w();
            } else {
                t();
            }
            this.i.a();
            r();
        }
        this.ae = 0;
        childCount = getChildCount();
        int first;
        int last;
        int end;
        if (this.ad) {
            first = getChildAt(0).getRight();
            last = getChildAt(childCount - 1).getLeft();
            end = (getWidth() - getPaddingRight()) - this.K;
            if (this.j == 0 && first < end) {
                this.ae = end - first;
                isAtEdge = true;
            } else if (this.j + childCount == this.z && last > getPaddingLeft()) {
                this.ae = (getPaddingLeft() + this.K) - last;
                isAtEdge = true;
            }
        } else {
            first = getChildAt(0).getLeft();
            last = getChildAt(childCount - 1).getRight();
            int start = getPaddingLeft() + this.K;
            end = getWidth() - getPaddingRight();
            if (this.j == 0 && first > start) {
                this.ae = start - first;
                isAtEdge = true;
            } else if (this.j + childCount == this.z && last < end) {
                this.ae = (end - last) - this.K;
                isAtEdge = true;
            }
        }
        l();
        onScrollChanged(0, 0, 0, 0);
        invalidate();
        return isAtEdge;
    }

    private void e(int offset) {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            getChildAt(i).offsetLeftAndRight(offset);
        }
    }

    private int getCenterOfEnhanceGallery() {
        return (((getWidth() - getPaddingLeft()) - getPaddingRight()) / 2) + getPaddingLeft();
    }

    private static int d(View view) {
        return view.getLeft() + (view.getWidth() / 2);
    }

    private void a(boolean toLeft) {
        int numChildren = getChildCount();
        int firstPosition = this.j;
        int start = 0;
        int count = 0;
        int i;
        int n;
        if (toLeft) {
            int galleryLeft;
            if (this.ad) {
                galleryLeft = getPaddingLeft();
            } else {
                galleryLeft = getPaddingLeft() + this.K;
            }
            for (i = 0; i < numChildren - 1; i++) {
                if (this.ad) {
                    n = (numChildren - 1) - i;
                } else {
                    n = i;
                }
                if ((this.ad ? getChildAt(n - 1) : getChildAt(n + 1)).getLeft() > galleryLeft) {
                    break;
                }
                start = n;
                count++;
                this.i.a(firstPosition + n, getChildAt(n));
            }
            if (!this.ad) {
                start = 0;
            }
        } else {
            int galleryRight;
            if (this.ad) {
                galleryRight = (getWidth() - getPaddingRight()) - this.K;
            } else {
                galleryRight = getWidth() - getPaddingRight();
            }
            for (i = numChildren - 1; i >= 1; i--) {
                if (this.ad) {
                    n = (numChildren - 1) - i;
                } else {
                    n = i;
                }
                if ((this.ad ? getChildAt(n + 1) : getChildAt(n - 1)).getRight() < galleryRight) {
                    break;
                }
                start = n;
                count++;
                this.i.a(firstPosition + n, getChildAt(n));
            }
            if (this.ad) {
                start = 0;
            }
        }
        detachViewsFromParent(start, count);
        if (toLeft != this.ad) {
            this.j += count;
        }
    }

    private void p() {
        int scrollAmount = 0;
        int childCount = getChildCount();
        if (childCount != 0 && this.T != null) {
            View startView = getChildAt(0);
            View endView = getChildAt(childCount - 1);
            int start;
            int end;
            if (this.ad) {
                start = (getWidth() - getPaddingRight()) - this.K;
                end = getPaddingLeft();
                if (this.J == 2 && this.j + childCount == this.z) {
                    scrollAmount = (this.K + end) - endView.getLeft();
                } else if (startView.getRight() != start) {
                    if (d(startView) >= start) {
                        scrollAmount = start - getChildAt(1).getRight();
                    } else {
                        scrollAmount = start - startView.getRight();
                    }
                    if (this.j + childCount == this.z && endView.getLeft() + scrollAmount > end) {
                        scrollAmount = (end - endView.getLeft()) + this.K;
                    }
                }
            } else {
                start = getPaddingLeft() + this.K;
                end = getWidth() - getPaddingRight();
                if (this.J == 2 && this.j + childCount == this.z) {
                    scrollAmount = (end - endView.getRight()) - this.K;
                } else if (startView.getLeft() != start) {
                    if (d(startView) < start) {
                        scrollAmount = start - getChildAt(1).getLeft();
                    } else {
                        scrollAmount = start - startView.getLeft();
                    }
                    if (this.j + childCount == this.z && endView.getRight() + scrollAmount != end - this.K) {
                        scrollAmount = (end - endView.getRight()) - this.K;
                    }
                }
            }
            if (scrollAmount != 0) {
                if (this.aD != 2) {
                    c(2);
                }
                this.Q.a(scrollAmount);
                return;
            }
            if (this.aD != 0) {
                c(0);
            }
            q();
            this.J = -1;
        } else if (this.aD != 0) {
            c(0);
        }
    }

    private void q() {
        if (this.W) {
            this.W = false;
            super.e();
        }
        invalidate();
    }

    void e() {
        if (!this.W) {
            super.e();
        }
    }

    private void r() {
        if (this.T != null) {
            int newPos = this.j;
            if (newPos != this.x) {
                setSelectedPositionInt(newPos);
                setNextSelectedPositionInt(newPos);
                g();
            }
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        postDelayed(new Runnable(this) {
            final /* synthetic */ EnhanceGallery a;

            {
                this.a = r1;
            }

            public void run() {
                View lastView = this.a.getChildAt(this.a.getChildCount() - 1);
                int scrollAmount = 0;
                if (this.a.ad) {
                    if (lastView != null && lastView.getLeft() > this.a.getPaddingLeft()) {
                        scrollAmount = this.a.getPaddingLeft() - lastView.getLeft();
                    }
                } else if (lastView != null && lastView.getRight() < this.a.getWidth() - this.a.getPaddingRight()) {
                    scrollAmount = (this.a.getWidth() - this.a.getPaddingRight()) - lastView.getRight();
                }
                this.a.J = -1;
                if (!(this.a.aD == 2 || scrollAmount == 0)) {
                    this.a.c(2);
                }
                this.a.Q.a(scrollAmount);
            }
        }, 200);
    }

    void b(int delta, boolean animate) {
        boolean z = true;
        if (VERSION.SDK_INT >= 17) {
            if (getLayoutDirection() != 1) {
                z = false;
            }
            this.ad = z;
        }
        if (this.u) {
            f();
        }
        if (this.u && this.am == 2 && this.a != null && this.a.hasStableIds()) {
            o();
        }
        if (this.z == 0) {
            l();
            a();
            return;
        }
        if (this.v >= 0) {
            setSelectedPositionInt(this.v);
        }
        b();
        detachAllViewsFromParent();
        this.j = this.x;
        s();
        this.i.a();
        invalidate();
        g();
        this.u = false;
        this.o = false;
        setNextSelectedPositionInt(this.x);
        A();
        this.al = 0;
        View view = getChildAt(0);
        if (view != null) {
            this.ah = view.getWidth();
            this.al = ((getWidth() - getPaddingLeft()) - getPaddingRight()) - (this.z * (this.ah + this.K));
            if (this.al <= 0 || this.ak) {
                if (this.al <= 0) {
                    int childCount = getChildCount();
                    if (this.ad) {
                        int mostLeft = getPaddingLeft() + this.K;
                        if (this.j + childCount == this.z && getChildAt(childCount - 1).getLeft() != mostLeft) {
                            b(mostLeft - getChildAt(childCount - 1).getLeft());
                            p();
                        }
                    } else {
                        int mostRight = (getWidth() - getPaddingRight()) - this.K;
                        if (this.j + childCount == this.z && getChildAt(childCount - 1).getRight() != mostRight) {
                            b(mostRight - getChildAt(childCount - 1).getRight());
                            p();
                        }
                    }
                }
            } else if (this.j != 0 && this.x < this.z) {
                int distance;
                if (this.ad) {
                    distance = (-this.x) * (this.ah + this.K);
                } else {
                    distance = this.x * (this.ah + this.K);
                }
                b(distance);
                p();
            }
        }
        l();
    }

    private void s() {
        int itemSpacing = this.K;
        int galleryLeft = getPaddingLeft();
        int galleryRight = (getRight() - getLeft()) - getPaddingRight();
        int numItems = this.z;
        int curPosition;
        if (this.ad) {
            curPosition = this.j;
            int curRightEdge = galleryRight - this.K;
            while (curRightEdge > galleryLeft && curPosition < numItems) {
                curRightEdge = a(curPosition, curPosition - this.x, curRightEdge, false).getLeft() - itemSpacing;
                curPosition++;
            }
            return;
        }
        curPosition = this.j;
        int curLeftEdge = galleryLeft + itemSpacing;
        while (curLeftEdge < galleryRight && curPosition < numItems) {
            curLeftEdge = a(curPosition, curPosition - this.x, curLeftEdge, true).getRight() + itemSpacing;
            curPosition++;
        }
    }

    private void t() {
        if (this.ad) {
            u();
        } else {
            v();
        }
    }

    private void u() {
        int curPosition;
        int curRightEdge;
        int itemSpacing = this.K;
        int galleryLeft = getPaddingLeft();
        int numChildren = getChildCount();
        View prevIterationView = getChildAt(numChildren - 1);
        if (prevIterationView != null) {
            curPosition = this.j + numChildren;
            curRightEdge = prevIterationView.getLeft() - itemSpacing;
        } else {
            curPosition = this.z - 1;
            this.j = curPosition;
            curRightEdge = (getRight() - getLeft()) - getPaddingRight();
            this.S = true;
        }
        while (curRightEdge > galleryLeft && curPosition < this.z) {
            curRightEdge = a(curPosition, curPosition - this.x, curRightEdge, false).getLeft() - itemSpacing;
            curPosition++;
        }
    }

    private void v() {
        int curPosition;
        int curRightEdge;
        int itemSpacing = this.K;
        int galleryLeft = getPaddingLeft();
        View prevIterationView = getChildAt(0);
        if (prevIterationView != null) {
            curPosition = this.j - 1;
            curRightEdge = prevIterationView.getLeft() - itemSpacing;
        } else {
            curPosition = 0;
            curRightEdge = (getRight() - getLeft()) - getPaddingRight();
            this.S = true;
        }
        while (curRightEdge > galleryLeft && curPosition >= 0) {
            prevIterationView = a(curPosition, curPosition - this.x, curRightEdge, false);
            this.j = curPosition;
            curRightEdge = prevIterationView.getLeft() - itemSpacing;
            curPosition--;
        }
    }

    private void w() {
        if (this.ad) {
            x();
        } else {
            y();
        }
    }

    private void x() {
        int curPosition;
        int curLeftEdge;
        int itemSpacing = this.K;
        int galleryRight = (getRight() - getLeft()) - getPaddingRight();
        View prevIterationView = getChildAt(0);
        if (prevIterationView != null) {
            curPosition = this.j - 1;
            curLeftEdge = prevIterationView.getRight() + itemSpacing;
        } else {
            curPosition = 0;
            curLeftEdge = getPaddingLeft();
            this.S = true;
        }
        while (curLeftEdge < galleryRight && curPosition >= 0) {
            prevIterationView = a(curPosition, curPosition - this.x, curLeftEdge, true);
            this.j = curPosition;
            curLeftEdge = prevIterationView.getRight() + itemSpacing;
            curPosition--;
        }
    }

    private void y() {
        int curPosition;
        int curLeftEdge;
        int itemSpacing = this.K;
        int galleryRight = (getRight() - getLeft()) - getPaddingRight();
        int numChildren = getChildCount();
        int numItems = this.z;
        View prevIterationView = getChildAt(numChildren - 1);
        if (prevIterationView != null) {
            curPosition = this.j + numChildren;
            curLeftEdge = prevIterationView.getRight() + itemSpacing;
        } else {
            curPosition = this.z - 1;
            this.j = curPosition;
            curLeftEdge = getPaddingLeft();
            this.S = true;
        }
        while (curLeftEdge < galleryRight && curPosition < numItems) {
            curLeftEdge = a(curPosition, curPosition - this.x, curLeftEdge, true).getRight() + itemSpacing;
            curPosition++;
        }
    }

    private View a(int position, int offset, int x, boolean fromLeft) {
        View child;
        if (!this.u) {
            child = this.i.a(position);
            if (child != null) {
                if (!this.aB) {
                    if (child.getImportantForAccessibility() == 0) {
                        child.setImportantForAccessibility(1);
                    }
                    if (((AccessibilityManager) getContext().getSystemService("accessibility")).isEnabled()) {
                        if (this.aA == null) {
                            this.aA = new i(this);
                        }
                        if (child.getAccessibilityNodeProvider() == null) {
                            child.setAccessibilityDelegate(this.aA);
                        }
                    }
                    this.aB = true;
                }
                a(child, position, offset, x, fromLeft);
                return child;
            }
        }
        child = this.a.getView(position, null, this);
        if (child.getImportantForAccessibility() == 0) {
            child.setImportantForAccessibility(1);
        }
        if (((AccessibilityManager) getContext().getSystemService("accessibility")).isEnabled()) {
            if (this.aA == null) {
                this.aA = new i(this);
            }
            if (child.getAccessibilityNodeProvider() == null) {
                child.setAccessibilityDelegate(this.aA);
            }
        }
        a(child, position, offset, x, fromLeft);
        return child;
    }

    private void a(View child, int position, int offset, int x, boolean fromLeft) {
        LayoutParams lp;
        int childLeft;
        int childRight;
        ViewGroup.LayoutParams vlp = child.getLayoutParams();
        if (vlp == null) {
            lp = (LayoutParams) generateDefaultLayoutParams();
        } else if (vlp instanceof LayoutParams) {
            lp = (LayoutParams) vlp;
        } else {
            lp = (LayoutParams) generateLayoutParams(vlp);
        }
        addViewInLayout(child, fromLeft != this.ad ? -1 : 0, lp);
        if (this.am == 1) {
            child.setSelected(offset == 0);
        }
        child.measure(ViewGroup.getChildMeasureSpec(this.c, this.h.left + this.h.right, lp.width), ViewGroup.getChildMeasureSpec(this.b, this.h.top + this.h.bottom, lp.height));
        int childTop = a(child, true);
        int childBottom = childTop + child.getMeasuredHeight();
        int width = child.getMeasuredWidth();
        if (fromLeft) {
            childLeft = x;
            childRight = childLeft + width;
        } else {
            childLeft = x - width;
            childRight = x;
        }
        child.layout(childLeft, childTop, childRight, childBottom);
        if (!(this.am == 0 || this.ap == null)) {
            if (child instanceof Checkable) {
                ((Checkable) child).setChecked(this.ap.get(position));
            } else if (getContext().getApplicationInfo().targetSdkVersion >= 11) {
                child.setActivated(this.ap.get(position));
            }
        }
        if (this.am == 2 && this.aa) {
            child.setOnDragListener(new OnDragListener(this) {
                final /* synthetic */ EnhanceGallery a;

                {
                    this.a = r1;
                }

                public boolean onDrag(View item, DragEvent event) {
                    if (this.a.I == -1) {
                        return false;
                    }
                    View child;
                    View v;
                    View checkbox;
                    switch (event.getAction()) {
                        case 1:
                            return true;
                        case 2:
                            return true;
                        case 3:
                            return false;
                        case 4:
                            child = this.a.getChildAt(this.a.I - this.a.j);
                            if (child != null) {
                                if (child instanceof b) {
                                    v = ((b) child).a();
                                    if (v != null) {
                                        v.setAlpha(1.0f);
                                    }
                                    if (this.a.ar) {
                                        child.setAlpha(1.0f);
                                    }
                                } else {
                                    child.setAlpha(1.0f);
                                }
                            }
                            if (!event.getResult()) {
                                this.a.setItemChecked(this.a.I, true);
                            } else if (child != null) {
                                checkbox = child.findViewById(16908289);
                                if (checkbox != null && (checkbox instanceof Checkable)) {
                                    ((Checkable) checkbox).setChecked(false);
                                }
                                this.a.n();
                            }
                            this.a.I = -1;
                            if (this.a.getCheckedItemCount() <= 0 && this.a.ao != null) {
                                this.a.ao.finish();
                                break;
                            }
                        case 5:
                            return true;
                        case 6:
                            return true;
                        case 100:
                            child = this.a.getChildAt(this.a.I - this.a.j);
                            if (child != null) {
                                if (child instanceof b) {
                                    v = ((b) child).a();
                                    if (v != null) {
                                        v.setAlpha(1.0f);
                                    }
                                    if (this.a.ar) {
                                        child.setAlpha(1.0f);
                                    }
                                } else {
                                    child.setAlpha(1.0f);
                                }
                                checkbox = child.findViewById(16908289);
                                if (checkbox != null && (checkbox instanceof Checkable)) {
                                    ((Checkable) checkbox).setChecked(false);
                                }
                            }
                            this.a.requestLayout();
                            if (this.a.getCheckedItemCount() <= 0 && this.a.ao != null) {
                                this.a.ao.finish();
                            }
                            this.a.I = -1;
                            break;
                    }
                    return true;
                }
            });
        }
    }

    private int a(View child, boolean duringLayout) {
        int myHeight = duringLayout ? getMeasuredHeight() : getHeight();
        int childHeight = duringLayout ? child.getMeasuredHeight() : child.getHeight();
        switch (this.M) {
            case 16:
                return this.h.top + ((((myHeight - this.h.bottom) - this.h.top) - childHeight) / 2);
            case 48:
                return this.h.top;
            case 80:
                return (myHeight - this.h.bottom) - childHeight;
            default:
                return 0;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean retValue = this.N.onTouchEvent(event);
        int action = event.getAction();
        if (action == 1) {
            j();
        } else if (action == 3) {
            k();
        }
        return retValue;
    }

    public boolean onSingleTapUp(MotionEvent e) {
        if (this.O >= 0 && this.am != 0) {
            if (this.az == null) {
                this.az = new h();
            }
            h performClick = this.az;
            performClick.a = this.O;
            performClick.a();
            post(performClick);
        } else if (this.V || this.O == this.x) {
            a(this.P, this.O, this.a.getItemId(this.O));
        }
        return true;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (this.al > 0 && !this.ak) {
            return false;
        }
        if (!this.U) {
            removeCallbacks(this.R);
            if (!this.W) {
                this.W = true;
            }
        }
        int childCount = getChildCount();
        switch (this.J) {
            case 1:
                if (Math.abs(velocityX) >= 1500.0f) {
                    int distance;
                    this.J = 2;
                    int delta = ((int) Math.floor((double) (((getWidth() - getPaddingLeft()) - getPaddingRight()) / (this.ah + this.K)))) * (this.ah + this.K);
                    View view;
                    int startEdge;
                    if (velocityX > 0.0f) {
                        if (this.ad) {
                            view = getChildAt(this.aj - this.j);
                            startEdge = (getWidth() - getPaddingRight()) - this.K;
                            if (view != null) {
                                distance = startEdge - view.getRight();
                            } else {
                                distance = startEdge - getChildAt(getChildCount() - 1).getRight();
                            }
                        } else {
                            view = getChildAt(this.ai - this.j);
                            if (view != null) {
                                distance = delta - ((view.getLeft() - getPaddingLeft()) - this.K);
                            } else {
                                distance = ((getPaddingLeft() + this.K) - getChildAt(0).getLeft()) + delta;
                            }
                        }
                    } else if (this.ad) {
                        view = getChildAt(this.ai - this.j);
                        startEdge = (getWidth() - getPaddingRight()) - this.K;
                        if (view != null) {
                            distance = -(delta - (startEdge - view.getRight()));
                        } else {
                            distance = -(delta - (startEdge - getChildAt(0).getRight()));
                        }
                    } else {
                        view = getChildAt(this.aj - this.j);
                        if (view != null) {
                            distance = (getPaddingLeft() + this.K) - view.getLeft();
                        } else {
                            distance = (getPaddingLeft() + this.K) - getChildAt(childCount - 1).getLeft();
                        }
                    }
                    c(2);
                    this.Q.a(distance);
                    break;
                }
                return false;
            case 3:
                this.J = 4;
                break;
        }
        return true;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (this.al > 0 && !this.ak) {
            return false;
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        if (this.U) {
            if (this.W) {
                this.W = false;
            }
        } else if (this.ac) {
            if (!this.W) {
                this.W = true;
            }
            postDelayed(this.R, 250);
        }
        if (this.ac) {
            c(1);
        }
        this.J = 1;
        int childCount = getChildCount();
        int incrementalDeltaX = (int) distanceX;
        if (this.af > getWidth()) {
            this.af = this.ag;
        }
        if (!(this.ae == 0 || this.af == 0)) {
            this.J = 3;
            if (Math.abs(this.ae) >= this.af) {
                incrementalDeltaX = 0;
            } else {
                incrementalDeltaX = (int) (((float) incrementalDeltaX) * (1.0f - ((((float) Math.abs(this.ae)) * 1.0f) / ((float) this.af))));
            }
        }
        if (incrementalDeltaX != 0) {
            b(incrementalDeltaX * -1);
        }
        this.ac = false;
        return true;
    }

    public boolean onDown(MotionEvent e) {
        if (this.J == 2 || this.J == 4) {
            this.J = 1;
            c(1);
        } else {
            this.J = 0;
        }
        this.Q.a(false);
        this.O = a((int) e.getX(), (int) e.getY());
        if (this.O >= 0) {
            this.P = getChildAt(this.O - this.j);
            this.P.setPressed(true);
        }
        this.G = (int) e.getX();
        this.H = (int) e.getY();
        this.ai = this.j;
        this.aj = (this.j + getChildCount()) - 1;
        this.ac = true;
        return true;
    }

    void j() {
        switch (this.J) {
            case 0:
                p();
                break;
            case 1:
                p();
                break;
            case 3:
            case 4:
                if (this.ae != 0) {
                    if (this.aD != 2) {
                        c(2);
                    }
                    this.Q.a();
                    break;
                }
                break;
        }
        z();
    }

    void k() {
        j();
    }

    public void onLongPress(MotionEvent e) {
        if (this.O >= 0) {
            if (this.am == 2) {
                View child = getChildAt(this.O - this.j);
                if (child != null) {
                    int longPressPosition = this.O;
                    long longPressId = this.a.getItemId(this.O);
                    boolean handled = false;
                    if (true && !this.u) {
                        handled = b(child, longPressPosition, longPressId);
                    }
                    if (handled) {
                        this.J = -1;
                        setPressed(false);
                        child.setPressed(false);
                    }
                }
            }
            performHapticFeedback(0);
            e(this.P, this.O, a(this.O));
        }
    }

    public void onShowPress(MotionEvent e) {
    }

    private void z() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            getChildAt(i).setPressed(false);
        }
        setPressed(false);
    }

    public void dispatchSetSelected(boolean selected) {
    }

    protected void dispatchSetPressed(boolean pressed) {
        if (this.T != null) {
            this.T.setPressed(pressed);
        }
    }

    protected ContextMenuInfo getContextMenuInfo() {
        return this.ab;
    }

    public boolean showContextMenuForChild(View originalView) {
        int longPressPosition = c(originalView);
        if (longPressPosition < 0) {
            return false;
        }
        return e(originalView, longPressPosition, this.a.getItemId(longPressPosition));
    }

    public boolean showContextMenu() {
        if (!isPressed() || this.x < 0) {
            return false;
        }
        return e(getChildAt(this.x - this.j), this.x, this.y);
    }

    private boolean e(View view, int position, long id) {
        boolean handled = false;
        if (this.t != null) {
            handled = this.t.a(this, this.P, this.O, id);
        }
        if (!handled) {
            this.ab = new a(view, position, id);
            handled = super.showContextMenuForChild(this);
        }
        if (handled) {
            performHapticFeedback(0);
        }
        return handled;
    }

    void setSelectedPositionInt(int position) {
        super.setSelectedPositionInt(position);
        A();
    }

    private void A() {
        View oldSelectedChild = this.T;
        View child = getChildAt(this.x - this.j);
        this.T = child;
        if (child != null && this.am == 1) {
            child.setSelected(true);
            child.setFocusable(true);
            if (hasFocus()) {
                child.requestFocus();
            }
            if (oldSelectedChild != null && oldSelectedChild != child) {
                oldSelectedChild.setSelected(false);
                oldSelectedChild.setFocusable(false);
            }
        }
    }

    public void setGravity(int gravity) {
        if (this.M != gravity) {
            this.M = gravity;
            requestLayout();
        }
    }

    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus && this.T != null && this.am == 1) {
            this.T.requestFocus(direction);
            this.T.setSelected(true);
        }
    }

    public void setOnScrollListener(g l) {
        this.aC = l;
        l();
    }

    void l() {
        if (this.aC != null) {
            this.aC.a(this, this.j, getChildCount(), this.z);
        }
    }

    void c(int newState) {
        if (newState != this.aD) {
            this.aD = newState;
            if (this.aC != null) {
                this.aC.a(this, newState);
            }
        }
    }

    public void setAdapter(SpinnerAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null && this.am != 0) {
            if (this.ap == null) {
                this.ap = new SparseBooleanArray();
            }
            if (adapter.hasStableIds() && this.F == null) {
                this.F = new LongSparseArray();
            }
            m();
        }
    }

    public void setChoiceMode(int choiceMode) {
        this.am = choiceMode;
        if (this.ao != null) {
            this.ao.finish();
            this.ao = null;
        }
        if (this.am != 0) {
            if (this.ap == null) {
                this.ap = new SparseBooleanArray();
            }
            if (this.F == null && this.a != null && this.a.hasStableIds()) {
                this.F = new LongSparseArray();
            }
            if (this.am == 2) {
                m();
                setLongClickable(true);
            }
        }
    }

    public void m() {
        if (this.ap != null) {
            this.ap.clear();
        }
        if (this.F != null) {
            this.F.clear();
        }
        this.an = 0;
    }

    public void setMultiChoiceModeListener(e listener) {
        if (this.E == null) {
            this.E = new f(this);
        }
        this.E.a(listener);
    }

    public void n() {
        this.u = true;
        this.z = this.a.getCount();
        requestLayout();
        invalidate();
    }

    public int getCheckedItemCount() {
        return this.an;
    }

    public SparseBooleanArray getCheckedItemPositions() {
        if (this.am != 0) {
            return this.ap;
        }
        return null;
    }

    public boolean d(int position) {
        if (this.am == 0 || this.ap == null) {
            return false;
        }
        return this.ap.get(position);
    }

    public long[] getCheckedItemIds() {
        if (this.am == 0 || this.F == null || this.a == null) {
            return new long[0];
        }
        LongSparseArray<Integer> idStates = this.F;
        int count = idStates.size();
        long[] ids = new long[count];
        for (int i = 0; i < count; i++) {
            ids[i] = idStates.keyAt(i);
        }
        return ids;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    boolean b(View r14, int r15, long r16) {
        /*
        r13 = this;
        r0 = r13.am;
        r1 = 2;
        if (r0 != r1) goto L_0x00e3;
    L_0x0005:
        r0 = r13.ao;
        if (r0 != 0) goto L_0x0017;
    L_0x0009:
        r0 = r13.ao;
        if (r0 != 0) goto L_0x00d5;
    L_0x000d:
        r0 = r13.E;
        r0 = r13.startActionMode(r0);
        r13.ao = r0;
        if (r0 == 0) goto L_0x00d5;
    L_0x0017:
        r13.I = r15;
        r0 = 16908289; // 0x1020001 float:2.3877232E-38 double:8.3538047E-317;
        r6 = r14.findViewById(r0);
        if (r6 == 0) goto L_0x002d;
    L_0x0022:
        r0 = r6 instanceof android.widget.Checkable;
        if (r0 == 0) goto L_0x002d;
    L_0x0026:
        r0 = r6;
        r0 = (android.widget.Checkable) r0;
        r1 = 1;
        r0.setChecked(r1);
    L_0x002d:
        r7 = r13.ay;
        if (r7 != 0) goto L_0x003a;
    L_0x0031:
        r0 = new android.graphics.Rect;
        r0.<init>();
        r13.ay = r0;
        r7 = r13.ay;
    L_0x003a:
        r14.getHitRect(r7);
        r0 = 0;
        r1 = r13.G;
        r2 = r7.left;
        r1 = r1 - r2;
        r0 = java.lang.Math.max(r0, r1);
        r13.aw = r0;
        r0 = 0;
        r1 = r13.H;
        r2 = r7.top;
        r1 = r1 - r2;
        r0 = java.lang.Math.max(r0, r1);
        r13.ax = r0;
        r0 = 0;
        r14.setActivated(r0);
        r14.jumpDrawablesToCurrentState();
        r0 = r13.aa;
        if (r0 == 0) goto L_0x00dc;
    L_0x0060:
        r0 = r14 instanceof com.meizu.common.widget.EnhanceGallery.cancel;
        if (r0 == 0) goto L_0x00b0;
    L_0x0064:
        r9 = r14;
        r9 = (com.meizu.common.widget.EnhanceGallery.cancel) r9;
        r0 = new com.meizu.common.widget.EnhanceGallery$d;
        r1 = r9.a();
        r2 = r9.cancel();
        r3 = r9.c();
        r0.<init>(r13, r1, r2, r3);
        r13.aq = r0;
    L_0x007a:
        r0 = 0;
        r1 = r13.aq;
        r2 = 0;
        r0 = r13.a(r0, r1, r13, r2);
        if (r0 != 0) goto L_0x00b8;
    L_0x0084:
        if (r6 == 0) goto L_0x0090;
    L_0x0086:
        r0 = r6 instanceof android.widget.Checkable;
        if (r0 == 0) goto L_0x0090;
    L_0x008a:
        r6 = (android.widget.Checkable) r6;
        r0 = 0;
        r6.setChecked(r0);
    L_0x0090:
        r0 = r13.ao;
        r0.finish();
        r0 = -1;
        r13.I = r0;
        r0 = r13.az;
        if (r0 != 0) goto L_0x00a4;
    L_0x009c:
        r0 = new com.meizu.common.widget.EnhanceGallery$h;
        r1 = 0;
        r0.<init>();
        r13.az = r0;
    L_0x00a4:
        r10 = r13.az;
        r10.a = r15;
        r10.a();
        r13.post(r10);
        r8 = 1;
    L_0x00af:
        return r8;
    L_0x00b0:
        r0 = new com.meizu.common.widget.EnhanceGallery$d;
        r0.<init>(r13, r14);
        r13.aq = r0;
        goto L_0x007a;
    L_0x00b8:
        r0 = 0;
        r13.performHapticFeedback(r0);
        r0 = r14 instanceof com.meizu.common.widget.EnhanceGallery.cancel;
        if (r0 == 0) goto L_0x00d7;
    L_0x00c0:
        r0 = r14;
        r0 = (com.meizu.common.widget.EnhanceGallery.cancel) r0;
        r11 = r0.a();
        if (r11 == 0) goto L_0x00cd;
    L_0x00c9:
        r0 = 0;
        r11.setAlpha(r0);
    L_0x00cd:
        r0 = r13.ar;
        if (r0 == 0) goto L_0x00d5;
    L_0x00d1:
        r0 = 0;
        r14.setAlpha(r0);
    L_0x00d5:
        r8 = 1;
        goto L_0x00af;
    L_0x00d7:
        r0 = 0;
        r14.setAlpha(r0);
        goto L_0x00d5;
    L_0x00dc:
        r0 = r13.I;
        r1 = 1;
        r13.setItemChecked(r0, r1);
        goto L_0x00d5;
    L_0x00e3:
        r8 = 0;
        r0 = r13.t;
        if (r0 == 0) goto L_0x00f3;
    L_0x00e8:
        r0 = r13.t;
        r1 = r13;
        r2 = r14;
        r3 = r15;
        r4 = r16;
        r8 = r0.a(r1, r2, r3, r4);
    L_0x00f3:
        if (r8 != 0) goto L_0x0101;
    L_0x00f5:
        r0 = r13.c(r14, r15, r16);
        r0 = (com.meizu.common.widget.EnhanceGallery.a) r0;
        r13.ab = r0;
        r8 = super.showContextMenuForChild(r13);
    L_0x0101:
        if (r8 == 0) goto L_0x00af;
    L_0x0103:
        r0 = 0;
        r13.performHapticFeedback(r0);
        goto L_0x00af;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.common.widget.EnhanceGallery.cancel(android.view.View, int, long):boolean");
    }

    boolean a(ClipData data, DragShadowBuilder shadowBuilder, Object myLocalState, int flags) {
        return false;
    }

    public void setDragItemBackgroundResources(int[] bgres) {
        if (bgres != null) {
            if (bgres.length > 0) {
                this.as = bgres[0];
            }
            if (bgres.length > 1) {
                this.at = bgres[1];
            }
            if (bgres.length > 2) {
                this.au = bgres[2];
            }
        }
    }

    ContextMenuInfo c(View view, int position, long id) {
        return new a(view, position, id);
    }

    public boolean d(View view, int position, long id) {
        boolean dispatchItemClick = true;
        if (this.am != 0) {
            boolean checkedStateChanged = false;
            boolean checked;
            if (this.am == 2 && this.ao != null) {
                if (this.ap.get(position, false)) {
                    checked = false;
                } else {
                    checked = true;
                }
                this.ap.put(position, checked);
                if (this.F != null && this.a.hasStableIds()) {
                    if (checked) {
                        this.F.put(this.a.getItemId(position), Integer.valueOf(position));
                    } else {
                        this.F.delete(this.a.getItemId(position));
                    }
                }
                if (checked) {
                    this.an++;
                } else {
                    this.an--;
                }
                if (!(this.ao == null || this.E == null)) {
                    this.E.a(this.ao, position, id, checked);
                    dispatchItemClick = false;
                }
                checkedStateChanged = true;
            } else if (this.am == 1) {
                if (this.ap.get(position, false)) {
                    checked = false;
                } else {
                    checked = true;
                }
                if (checked) {
                    this.ap.clear();
                    this.ap.put(position, true);
                    if (this.F != null && this.a.hasStableIds()) {
                        this.F.clear();
                        this.F.put(this.a.getItemId(position), Integer.valueOf(position));
                    }
                    this.an = 1;
                } else if (this.ap.size() == 0 || !this.ap.valueAt(0)) {
                    this.an = 0;
                }
                checkedStateChanged = true;
            }
            if (checkedStateChanged) {
                B();
            }
        }
        if (!dispatchItemClick || this.s == null) {
            return false;
        }
        if (view != null) {
            view.sendAccessibilityEvent(1);
        }
        this.s.a(this, view, position, id);
        return true;
    }

    public void setItemChecked(int position, boolean value) {
        if (this.am != 0) {
            if (value && this.ao == null && this.am == 2) {
                if (this.E == null || !this.E.a()) {
                    throw new IllegalStateException("StaggeredGridView: attempted to start selection mode for CHOICE_MODE_MULTIPLE_MODAL but no choice mode callback was supplied. Call setMultiChoiceModeListener to set a callback.");
                }
                this.ao = startActionMode(this.E);
            }
            if (this.am == 2) {
                boolean oldValue = this.ap.get(position);
                this.ap.put(position, value);
                if (this.F != null && this.a.hasStableIds()) {
                    if (value) {
                        this.F.put(this.a.getItemId(position), Integer.valueOf(position));
                    } else {
                        this.F.delete(this.a.getItemId(position));
                    }
                }
                if (oldValue != value) {
                    if (value) {
                        this.an++;
                    } else {
                        this.an--;
                    }
                }
                if (this.ao != null) {
                    this.E.a(this.ao, position, this.a.getItemId(position), value);
                }
            } else {
                boolean updateIds;
                if (this.F == null || !this.a.hasStableIds()) {
                    updateIds = false;
                } else {
                    updateIds = true;
                }
                if (value || d(position)) {
                    this.ap.clear();
                    if (updateIds) {
                        this.F.clear();
                    }
                }
                if (value) {
                    this.ap.put(position, true);
                    if (updateIds) {
                        this.F.put(this.a.getItemId(position), Integer.valueOf(position));
                    }
                    this.an = 1;
                } else if (this.ap.size() == 0 || !this.ap.valueAt(0)) {
                    this.an = 0;
                }
            }
            if (!this.q) {
                n();
            }
        }
    }

    void o() {
        this.ap.clear();
        boolean checkedCountChanged = false;
        int checkedIndex = 0;
        while (checkedIndex < this.F.size()) {
            long id = this.F.keyAt(checkedIndex);
            int lastPos = ((Integer) this.F.valueAt(checkedIndex)).intValue();
            long lastPosId = -1;
            if (lastPos < this.z) {
                lastPosId = this.a.getItemId(lastPos);
            }
            if (lastPos >= this.z || id != lastPosId) {
                int start = Math.max(0, lastPos - 20);
                int end = Math.min(lastPos + 20, this.z);
                boolean found = false;
                for (int searchPos = start; searchPos < end; searchPos++) {
                    if (id == this.a.getItemId(searchPos)) {
                        found = true;
                        this.ap.put(searchPos, true);
                        this.F.setValueAt(checkedIndex, Integer.valueOf(searchPos));
                        break;
                    }
                }
                if (!found) {
                    this.F.delete(id);
                    checkedIndex--;
                    this.an--;
                    checkedCountChanged = true;
                    if (!(this.ao == null || this.E == null)) {
                        this.E.a(this.ao, lastPos, id, false);
                    }
                }
            } else {
                this.ap.put(lastPos, true);
            }
            checkedIndex++;
        }
        if (checkedCountChanged && this.ao != null) {
            this.ao.invalidate();
        }
    }

    private void B() {
        int firstPos = this.j;
        int count = getChildCount();
        boolean useActivated = getContext().getApplicationInfo().targetSdkVersion >= 11;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int position = firstPos + i;
            if (child instanceof Checkable) {
                ((Checkable) child).setChecked(this.ap.get(position));
            } else if (useActivated) {
                child.setActivated(this.ap.get(position));
            }
        }
    }

    public void a(View view, int position, AccessibilityNodeInfo info) {
        SpinnerAdapter adapter = getAdapter();
        if (position != -1 && adapter != null) {
            if (position == getSelectedItemPosition()) {
                info.setSelected(true);
                info.addAction(8);
            } else {
                info.addAction(4);
            }
            if (isFocusable()) {
                info.addAction(64);
                info.setFocusable(true);
            }
            if (isClickable()) {
                info.addAction(16);
                info.setClickable(true);
            }
            if (isLongClickable()) {
                info.addAction(32);
                info.setLongClickable(true);
            }
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setCollectionInfo(CollectionInfo.obtain(1, getCount(), false, 1));
        info.setClassName(EnhanceGallery.class.getName());
    }
}
