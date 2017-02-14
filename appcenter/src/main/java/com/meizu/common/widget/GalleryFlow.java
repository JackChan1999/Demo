package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.meizu.common.a.b;
import com.meizu.common.a.d;
import com.meizu.common.a.f;
import com.meizu.common.a.j;
import com.meizu.common.app.SlideNotice;

public class GalleryFlow extends AbsSpinner implements OnGestureListener {
    private int E;
    private int F;
    private int G;
    private int H;
    private int I;
    private int J;
    private int K;
    private int L;
    private int M;
    private int N;
    private int O;
    private int P;
    private int[][] Q;
    private int[] R;
    private int S;
    private int T;
    private int U;
    private int V;
    private GestureDetector W;
    private int aa;
    private View ab;
    private a ac;
    private Runnable ad;
    private boolean ae;
    private View af;
    private boolean ag;
    private boolean ah;
    private boolean ai;
    private boolean aj;
    private AdapterView.a ak;
    private boolean al;
    private int am;
    private Matrix an;
    private boolean ao;
    private boolean ap;

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

    private class a implements Runnable {
        final /* synthetic */ GalleryFlow a;
        private h b;
        private int c;

        public a(GalleryFlow galleryFlow) {
            this.a = galleryFlow;
            this.b = new h(galleryFlow.getContext());
            this.b.a(true, true);
        }

        private void a() {
            this.a.removeCallbacks(this);
        }

        public void a(int initialVelocity) {
            if (initialVelocity != 0 && Math.abs(initialVelocity) >= 200) {
                int initialX;
                a();
                if (Math.abs(initialVelocity) > 4200) {
                    initialVelocity = initialVelocity > 0 ? 4200 : -4200;
                }
                if (initialVelocity < 0) {
                    initialX = Integer.MAX_VALUE;
                } else {
                    initialX = 0;
                }
                this.c = initialX;
                this.b.a(null);
                this.b.a(initialX, 0, initialVelocity, 0, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
                this.a.post(this);
            }
        }

        public void b(int distance) {
            if (distance != 0) {
                a();
                this.c = 0;
                this.b.a(new DecelerateInterpolator());
                this.b.a(0, 0, -distance, 0, this.a.S);
                this.a.post(this);
            }
        }

        public void a(boolean scrollIntoSlots) {
            this.a.removeCallbacks(this);
            b(scrollIntoSlots);
        }

        private void b(boolean scrollIntoSlots) {
            this.b.a(true);
            if (scrollIntoSlots) {
                this.a.n();
            }
        }

        public void run() {
            if (this.a.z == 0) {
                b(true);
                return;
            }
            this.a.ae = false;
            h scroller = this.b;
            boolean more = scroller.c();
            int x = scroller.b();
            int delta = this.c - x;
            if (delta > 0) {
                delta = Math.min(this.a.R[3], delta);
            } else {
                delta = Math.max(-this.a.R[3], delta);
            }
            if (delta != 0) {
                this.a.b(delta);
            }
            if (!more || this.a.ae) {
                b(true);
                return;
            }
            this.c = x;
            this.a.post(this);
        }
    }

    public GalleryFlow(Context context) {
        this(context, null);
    }

    public GalleryFlow(Context context, AttributeSet attrs) {
        this(context, attrs, b.MeizuCommon_GalleryFlowStyle);
    }

    public GalleryFlow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.E = 90;
        this.F = 30;
        this.S = 350;
        this.ac = new a(this);
        this.ad = new Runnable(this) {
            final /* synthetic */ GalleryFlow a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.ai = false;
                this.a.e();
            }
        };
        this.ag = true;
        this.ah = true;
        this.an = new Matrix();
        this.ao = false;
        this.W = new GestureDetector(context, this);
        this.W.setIsLongpressEnabled(true);
        TypedArray a = context.obtainStyledAttributes(attrs, j.GalleryFlow, defStyle, 0);
        this.ao = a.getBoolean(j.GalleryFlow_mcCirculate, false);
        this.E = getResources().getDimensionPixelSize(d.mc_galleryflow_delta_1);
        this.F = getResources().getDimensionPixelSize(d.mc_galleryflow_delta_2);
        c(a.getDimensionPixelSize(j.GalleryFlow_mcPicSize, SlideNotice.SHOW_ANIMATION_DURATION));
        a.recycle();
        this.ap = true;
        setChildrenDrawingOrderEnabled(true);
    }

    private void c(int length) {
        this.G = length / 2;
        this.H = length;
        this.I = Math.round(((float) this.G) / 0.083333336f);
        this.J = Math.round(((float) ((this.I - this.G) - this.E)) * 0.083333336f);
        this.K = Math.round(((float) ((((this.I - this.G) - this.E) - this.J) - this.F)) * 0.083333336f);
        this.L = this.K - 15;
        this.M = ((this.G + this.E) + this.J) + this.F;
        this.N = ((this.M + this.K) + 23) * 2;
        this.O = this.H;
        this.R = new int[]{1, this.J + this.F, this.G + this.E, this.G + this.E, this.J + this.F, 1};
    }

    public void setCenterPicSize(int length) {
        if (length > 0 && length != this.H) {
            c(length);
            requestLayout();
        }
    }

    public void setCallbackDuringFling(boolean shouldCallback) {
        this.ag = shouldCallback;
    }

    public void setCallbackOnUnselectedItemClick(boolean shouldCallback) {
        this.ah = shouldCallback;
    }

    public void setAnimationDuration(int animationDurationMillis) {
        this.S = animationDurationMillis;
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

    public void setAdapter(SpinnerAdapter adapter) {
        int position = 2;
        super.setAdapter(adapter);
        if (adapter != null) {
            if (this.z <= 0 || 2 >= this.z) {
                position = this.z / 2;
            }
            position = b(this.ao, position);
            setSelectedPositionInt(position);
            setNextSelectedPositionInt(position);
            requestLayout();
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.q = true;
        this.P = getCenterOfGalleryFlow();
        r0 = new int[6][];
        r0[0] = new int[]{(this.P - this.M) - 1, this.P - this.M};
        r0[1] = new int[]{this.P - this.M, (this.P - this.G) - this.E};
        r0[2] = new int[]{(this.P - this.G) - this.E, this.P};
        r0[3] = new int[]{this.P, (this.P + this.G) + this.E};
        r0[4] = new int[]{(this.P + this.G) + this.E, this.P + this.M};
        r0[5] = new int[]{this.P + this.M, (this.P + this.M) + 1};
        this.Q = r0;
        b(0, false);
        this.q = false;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(resolveSizeAndState(this.N, widthMeasureSpec, 0), resolveSizeAndState(this.O, heightMeasureSpec, 0));
    }

    int a(View child) {
        return child.getMeasuredHeight();
    }

    void b(int deltaX) {
        if (getChildCount() != 0 && deltaX != 0) {
            boolean toLeft;
            if (deltaX < 0) {
                toLeft = true;
            } else {
                toLeft = false;
            }
            int limitedDeltaX = deltaX;
            if (!this.ao) {
                limitedDeltaX = a(toLeft, deltaX);
                if (limitedDeltaX != deltaX) {
                    this.ac.b(false);
                    o();
                }
            }
            e(limitedDeltaX);
            a(toLeft);
            if (toLeft) {
                r();
            } else {
                q();
            }
            this.i.a();
            p();
            View selChild = this.af;
            if (selChild != null) {
                int galleryCenter = getWidth() / 2;
                this.am = (selChild.getLeft() + (selChild.getWidth() / 2)) - galleryCenter;
            }
            onScrollChanged(0, 0, 0, 0);
            invalidate();
        }
    }

    private void d(int index) {
        int indexOfCenterView = index;
        if (indexOfCenterView != -1) {
            for (int i = 0; i < getChildCount(); i++) {
                if (i != indexOfCenterView) {
                    View view = getChildAt(i);
                    view.offsetLeftAndRight(this.Q[g(e(view))][0] - e(view));
                }
            }
        }
        requestLayout();
    }

    int a(boolean motionToLeft, int deltaX) {
        int extremeItemPosition;
        if (motionToLeft) {
            extremeItemPosition = this.z - 1;
        } else {
            extremeItemPosition = 0;
        }
        View extremeChild = getChildAt(extremeItemPosition - this.j);
        if (extremeChild == null) {
            return deltaX;
        }
        int extremeChildCenter = e(extremeChild);
        int centerOfView = getCenterOfGalleryFlow();
        if (extremeChildCenter == centerOfView) {
            return 0;
        }
        int centerDifference = centerOfView - extremeChildCenter;
        return motionToLeft ? Math.max(centerDifference, deltaX) : Math.min(centerDifference, deltaX);
    }

    private void e(int offset) {
        if (offset != 0) {
            float scale;
            View view;
            boolean toLeft = offset < 0;
            int mostCloseCenterViewIndex = getChildCount() / 2;
            int closestEdgeDistance = Integer.MAX_VALUE;
            int indexOfCenterView = -1;
            int centerOfView = getCenterOfGalleryFlow();
            int i;
            int delta;
            View mostCloseCenterView;
            int areaNum;
            int offers;
            if (toLeft) {
                for (i = getChildCount() - 1; i >= 0; i--) {
                    delta = e(getChildAt(i)) - centerOfView;
                    if (delta < 0 || delta >= closestEdgeDistance) {
                        break;
                    }
                    mostCloseCenterViewIndex = i;
                    closestEdgeDistance = delta;
                }
                mostCloseCenterView = getChildAt(mostCloseCenterViewIndex);
                if (g(e(mostCloseCenterView) + offset) == 3) {
                    scale = ((float) offset) / (((float) this.R[3]) * 1.0f);
                    for (i = getChildCount() - 1; i >= 0; i--) {
                        view = getChildAt(i);
                        areaNum = g(e(view));
                        if (i != getChildCount() - 1 || areaNum != 5) {
                            offers = Math.round(((float) this.R[areaNum]) * scale);
                            view.offsetLeftAndRight(offers);
                            if (g(e(view)) != areaNum) {
                                view.offsetLeftAndRight(-offers);
                            }
                            if (e(view) == centerOfView) {
                                indexOfCenterView = i;
                            }
                        }
                    }
                } else {
                    scale = ((float) ((e(mostCloseCenterView) + offset) - centerOfView)) / (((float) this.R[2]) * 1.0f);
                    for (i = 0; i <= getChildCount() - 1; i++) {
                        view = getChildAt(i);
                        areaNum = g(e(view));
                        if (areaNum == 1) {
                            view.offsetLeftAndRight(this.Q[1][0] - e(view));
                        } else if (i == getChildCount() - 1 && areaNum == 5) {
                        } else {
                            view.offsetLeftAndRight(Math.round(((float) this.R[areaNum - 1]) * scale) + (this.Q[areaNum][0] - e(view)));
                        }
                        if (e(view) == centerOfView) {
                            indexOfCenterView = i;
                        }
                    }
                }
            } else {
                for (i = 0; i <= getChildCount() - 1; i++) {
                    delta = centerOfView - e(getChildAt(i));
                    if (delta < 0 || delta >= closestEdgeDistance) {
                        break;
                    }
                    mostCloseCenterViewIndex = i;
                    closestEdgeDistance = delta;
                }
                mostCloseCenterView = getChildAt(mostCloseCenterViewIndex);
                if (e(mostCloseCenterView) == centerOfView || g(e(mostCloseCenterView) + offset) == 2) {
                    scale = ((float) offset) / (((float) this.R[2]) * 1.0f);
                    i = 0;
                    while (i <= getChildCount() - 1) {
                        view = getChildAt(i);
                        areaNum = g(e(view));
                        if (areaNum != 1 || i != 0) {
                            offers = Math.round(((float) this.R[areaNum]) * scale);
                            view.offsetLeftAndRight(offers);
                            if (g(e(view)) != areaNum) {
                                view.offsetLeftAndRight(-offers);
                            }
                            if (e(view) == centerOfView) {
                                indexOfCenterView = i;
                            }
                        }
                        i++;
                    }
                } else {
                    scale = ((float) ((e(mostCloseCenterView) + offset) - centerOfView)) / (((float) this.R[3]) * 1.0f);
                    i = getChildCount() - 1;
                    while (i >= 0) {
                        view = getChildAt(i);
                        areaNum = g(e(view));
                        if (areaNum == 4) {
                            view.offsetLeftAndRight(this.Q[5][0] - e(view));
                        } else if (!((i == 0 && areaNum == 1) || (i == getChildCount() - 1 && areaNum == 5))) {
                            view.offsetLeftAndRight(Math.round(((float) this.R[areaNum + 1]) * scale) + (this.Q[areaNum][1] - e(view)));
                        }
                        i--;
                    }
                }
            }
            if (toLeft) {
                view = getChildAt(getChildCount() - 1);
                if (g(e(view)) == 5 && g(e(getChildAt(getChildCount() - 2))) == 3) {
                    view.offsetLeftAndRight(Math.round(((float) this.R[4]) * scale) + (this.Q[5][0] - e(view)));
                }
            } else {
                view = getChildAt(0);
                if (g(e(view)) == 1 && g(e(getChildAt(1))) == 2) {
                    int lent = Math.round(((float) this.R[1]) * scale) + (e(view) - this.Q[1][0]);
                    view.offsetLeftAndRight(Math.round(((float) this.R[1]) * scale));
                }
            }
            if (indexOfCenterView != -1) {
                d(indexOfCenterView);
            }
        }
    }

    private float f(int x) {
        int delta = Math.abs(getCenterOfGalleryFlow() - x);
        if (delta > this.M) {
            return (float) (this.K * 2);
        }
        return (((float) (this.I - delta)) * 0.083333336f) * 2.0f;
    }

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Rect frame = new Rect();
        d(child);
        int saveCount = canvas.save();
        canvas.concat(this.an);
        boolean ret = super.drawChild(canvas, child, drawingTime);
        canvas.restoreToCount(saveCount);
        frame.setEmpty();
        child.getHitRect(frame);
        return ret;
    }

    private void d(View view) {
        float width;
        int alpha = 0;
        int centerX = e(view);
        int centerY = f(view);
        this.an.reset();
        View view2;
        float scales;
        if (view == getChildAt(0)) {
            view2 = getChildAt(1);
            if (view2 == null || g(e(view2)) != 1) {
                width = f(e(view));
            } else {
                scales = ((float) (e(view2) - this.Q[1][0])) / (((float) this.R[1]) * 1.0f);
                width = (30.0f * scales) + ((float) (this.L * 2));
                alpha = Math.round(180.0f - (102.0f * scales));
            }
        } else if (view == getChildAt(getChildCount() - 1)) {
            view2 = getChildAt(getChildCount() - 2);
            if (view2 == null || g(e(view2)) != 4) {
                width = f(e(view));
            } else {
                scales = ((float) (this.Q[5][0] - e(view2))) / (((float) this.R[4]) * 1.0f);
                width = (30.0f * scales) + ((float) (this.L * 2));
                alpha = Math.round(180.0f - (102.0f * scales));
            }
        } else {
            width = f(e(view));
        }
        this.an.postScale(width / ((float) this.H), width / ((float) this.H));
        this.an.preTranslate((float) (-centerX), (float) (-centerY));
        this.an.postTranslate((float) centerX, (float) centerY);
        TextView textView = (TextView) view.findViewById(f.mc_galleryflow_album_title);
        int delta = Math.abs(centerX - getCenterOfGalleryFlow());
        if (textView != null) {
            if (delta < this.R[2]) {
                textView.setVisibility(0);
                textView.setAlpha(1.0f - (((float) delta) / (((float) this.R[2]) * 1.0f)));
            } else {
                textView.setVisibility(8);
            }
        }
        if (view instanceof FrameLayout) {
            FrameLayout fl = (FrameLayout) view;
            Drawable db = fl.getForeground();
            if (alpha == 0) {
                alpha = Math.round((110.0f * ((float) delta)) / (((float) this.M) * 1.0f));
            }
            if (db != null) {
                db.setAlpha(alpha);
                fl.setForeground(db);
            }
        }
    }

    private int getCenterOfGalleryFlow() {
        return (((getWidth() - getPaddingLeft()) - getPaddingRight()) / 2) + getPaddingLeft();
    }

    private static int e(View view) {
        return view.getLeft() + (view.getWidth() / 2);
    }

    private static int f(View view) {
        return view.getTop() + (view.getHeight() / 2);
    }

    private void a(boolean toLeft) {
        int numChildren = getChildCount();
        int firstPosition = this.j;
        int start = 0;
        int count = 0;
        int i;
        int n;
        View child1;
        View child2;
        if (!toLeft) {
            for (i = numChildren - 1; i >= 0; i--) {
                n = i;
                child1 = getChildAt(n);
                child2 = getChildAt(n - 1);
                if (e(child1) != this.Q[5][0] || e(child2) != this.Q[5][0]) {
                    break;
                }
                start = n;
                count++;
                this.i.a(firstPosition + n, child1);
            }
        } else {
            for (i = 0; i < numChildren - 1; i++) {
                n = i;
                child1 = getChildAt(n);
                child2 = getChildAt(n + 1);
                if (e(child1) != this.Q[1][0] || e(child2) != this.Q[1][0]) {
                    break;
                }
                start = n;
                count++;
                this.i.a(firstPosition + n, child1);
            }
            start = 0;
        }
        detachViewsFromParent(start, count);
        if (toLeft) {
            this.j += count;
            this.j = b(this.ao, this.j);
        }
    }

    private void n() {
        if (getChildCount() != 0 && this.af != null) {
            int scrollAmount = getCenterOfGalleryFlow() - e(this.af);
            if (scrollAmount != 0) {
                this.ac.b(scrollAmount);
            } else {
                o();
            }
        }
    }

    private void o() {
        if (this.ai) {
            this.ai = false;
            super.e();
        }
        this.am = 0;
        invalidate();
    }

    void e() {
        if (!this.ai) {
            super.e();
        }
    }

    private void p() {
        View selView = this.af;
        if (this.af != null) {
            int galleryCenter = getCenterOfGalleryFlow();
            if (selView.getLeft() > galleryCenter || selView.getRight() < galleryCenter) {
                int closestEdgeDistance = Integer.MAX_VALUE;
                int newSelectedChildIndex = 0;
                for (int i = getChildCount() - 1; i >= 0; i--) {
                    View child = getChildAt(i);
                    if (child.getLeft() <= galleryCenter && child.getRight() >= galleryCenter) {
                        newSelectedChildIndex = i;
                        break;
                    }
                    int childClosestEdgeDistance = Math.min(Math.abs(child.getLeft() - galleryCenter), Math.abs(child.getRight() - galleryCenter));
                    if (childClosestEdgeDistance < closestEdgeDistance) {
                        closestEdgeDistance = childClosestEdgeDistance;
                        newSelectedChildIndex = i;
                    }
                }
                int newPos = this.j + newSelectedChildIndex;
                if (newPos != this.x) {
                    newPos = b(this.ao, newPos);
                    setSelectedPositionInt(newPos);
                    setNextSelectedPositionInt(newPos);
                    g();
                }
            }
        }
    }

    void b(int delta, boolean animate) {
        if (this.u) {
            f();
        }
        if (this.z == 0) {
            a();
            return;
        }
        if (this.v >= 0) {
            this.v = b(this.ao, this.v);
            setSelectedPositionInt(this.v);
        }
        b();
        detachAllViewsFromParent();
        this.U = 0;
        this.T = 0;
        this.j = this.x;
        View sel = a(this.x, 0, 0, true);
        sel.offsetLeftAndRight((getCenterOfGalleryFlow() - e(sel)) + this.am);
        r();
        q();
        this.i.a();
        invalidate();
        g();
        this.u = false;
        this.o = false;
        this.x = b(this.ao, this.x);
        setNextSelectedPositionInt(this.x);
        t();
    }

    private void q() {
        View prevIterationView = getChildAt(0);
        int curCenter;
        if (prevIterationView != null) {
            int curPosition = this.j - 1;
            int areaNum = g(e(prevIterationView));
            float scale = ((float) (e(prevIterationView) - this.Q[areaNum][0])) / (((float) this.R[areaNum]) * 1.0f);
            int i = areaNum - 1;
            while (i >= 0) {
                if (this.ao || curPosition >= 0) {
                    if (i != 0) {
                        curCenter = this.Q[i][0] + Math.round(((float) this.R[i]) * scale);
                    } else if (e(prevIterationView) > this.Q[1][0]) {
                        curCenter = this.Q[1][0];
                    } else {
                        i--;
                    }
                    prevIterationView = a(curPosition, curPosition - this.x, Math.round((float) (this.G + curCenter)), false);
                    this.j = curPosition;
                    curPosition--;
                    i--;
                } else {
                    return;
                }
            }
            return;
        }
        curCenter = (getRight() - getLeft()) - getPaddingRight();
        this.ae = true;
    }

    private void r() {
        int numChildren = getChildCount();
        View prevIterationView = getChildAt(numChildren - 1);
        int curCenter;
        if (prevIterationView != null) {
            int curPosition = this.j + numChildren;
            int areaNum = g(e(prevIterationView));
            float scale = ((float) (e(prevIterationView) - this.Q[areaNum][0])) / (((float) this.R[areaNum]) * 1.0f);
            int i = areaNum + 1;
            while (i < 6) {
                if (this.ao || curPosition < this.z) {
                    if (i == 5) {
                        curCenter = this.Q[i][0];
                    } else {
                        curCenter = this.Q[i][0] + Math.round(((float) this.R[i]) * scale);
                    }
                    prevIterationView = a(curPosition, curPosition - this.x, Math.round((float) (curCenter - this.G)), true);
                    curPosition++;
                    i++;
                } else {
                    return;
                }
            }
            return;
        }
        this.j = this.z - 1;
        curCenter = getPaddingLeft();
        this.ae = true;
    }

    private int g(int center) {
        int i = 0;
        while (i < 6) {
            if (center >= this.Q[i][0] && center < this.Q[i][1]) {
                return i;
            }
            i++;
        }
        return 0;
    }

    private View a(int position, int offset, int x, boolean fromLeft) {
        View child;
        if (!this.u) {
            child = this.i.a(position);
            if (child != null) {
                int childLeft = child.getLeft();
                this.U = Math.max(this.U, child.getMeasuredWidth() + childLeft);
                this.T = Math.min(this.T, childLeft);
                a(child, offset, x, fromLeft);
                return child;
            }
        }
        child = this.a.getView(b(this.ao, position), null, this);
        a(child, offset, x, fromLeft);
        return child;
    }

    private int b(boolean isCirculate, int position) {
        if (!isCirculate) {
            return position;
        }
        if (isCirculate && this.z != 0) {
            position = position > 0 ? position % this.z : (this.z + (position % this.z)) % this.z;
        }
        return position;
    }

    private void a(View child, int offset, int x, boolean fromLeft) {
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
        int i = this.H;
        lp.width = i;
        lp.height = i;
        child.setLayoutParams(lp);
        addViewInLayout(child, fromLeft ? -1 : 0, lp);
        child.setSelected(offset == 0);
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
    }

    private int a(View child, boolean duringLayout) {
        int myHeight = duringLayout ? getMeasuredHeight() : getHeight();
        int childHeight = duringLayout ? child.getMeasuredHeight() : child.getHeight();
        switch (this.V) {
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
        boolean retValue = this.W.onTouchEvent(event);
        int action = event.getAction();
        if (action == 1) {
            j();
        } else if (action == 3) {
            k();
        }
        return retValue;
    }

    public boolean onSingleTapUp(MotionEvent e) {
        if (this.ab == null) {
            return false;
        }
        h(this.aa - this.j);
        if (this.ah || this.aa == this.x) {
            int downTouchPosition = b(this.ao, this.aa);
            a(this.ab, downTouchPosition, this.a.getItemId(downTouchPosition));
        }
        return true;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!this.ag) {
            removeCallbacks(this.ad);
            if (!this.ai) {
                this.ai = true;
            }
        }
        this.ac.a((int) (-velocityX));
        return true;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        getParent().requestDisallowInterceptTouchEvent(true);
        if (this.ag) {
            if (this.ai) {
                this.ai = false;
            }
        } else if (this.al) {
            if (!this.ai) {
                this.ai = true;
            }
            postDelayed(this.ad, 250);
        }
        if (((double) (Math.abs(distanceX) - ((float) this.R[3]))) > 1.0E-4d) {
            distanceX = distanceX > 0.0f ? (float) this.R[3] : (float) (-this.R[3]);
        }
        b(((int) distanceX) * -1);
        this.al = false;
        return true;
    }

    public boolean onDown(MotionEvent e) {
        this.ac.a(false);
        this.aa = a((int) e.getX(), (int) e.getY());
        this.ab = getChildAt(b(this.ao, this.aa - this.j));
        if (this.ab != null) {
            this.ab.setPressed(true);
        }
        this.al = true;
        return true;
    }

    public int a(int x, int y) {
        Rect frame = new Rect();
        int count = getChildCount();
        int i;
        View child;
        int centerX;
        int centerY;
        int halfLength;
        if (x >= getCenterOfGalleryFlow()) {
            for (i = 0; i < count; i++) {
                child = getChildAt(i);
                if (child.getVisibility() == 0) {
                    centerX = e(child);
                    centerY = f(child);
                    halfLength = ((int) f(centerX)) / 2;
                    frame.set(centerX - halfLength, centerY - halfLength, centerX + halfLength, centerY + halfLength);
                    if (frame.contains(x, y)) {
                        return this.j + i;
                    }
                }
            }
        } else {
            for (i = count - 1; i >= 0; i--) {
                child = getChildAt(i);
                if (child.getVisibility() == 0) {
                    centerX = e(child);
                    centerY = f(child);
                    halfLength = ((int) f(centerX)) / 2;
                    frame.set(centerX - halfLength, centerY - halfLength, centerX + halfLength, centerY + halfLength);
                    if (frame.contains(x, y)) {
                        return this.j + i;
                    }
                }
            }
        }
        return -1;
    }

    void j() {
        if (this.ac.b.a()) {
            n();
        }
        s();
    }

    void k() {
        j();
    }

    public void onLongPress(MotionEvent e) {
    }

    public void onShowPress(MotionEvent e) {
    }

    private void g(View child) {
        if (child != null) {
            child.setPressed(true);
        }
        setPressed(true);
    }

    private void s() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            getChildAt(i).setPressed(false);
        }
        setPressed(false);
    }

    public void dispatchSetSelected(boolean selected) {
    }

    protected void dispatchSetPressed(boolean pressed) {
        if (this.af != null) {
            this.af.setPressed(pressed);
        }
    }

    protected ContextMenuInfo getContextMenuInfo() {
        return this.ak;
    }

    public boolean showContextMenuForChild(View originalView) {
        int longPressPosition = c(originalView);
        if (longPressPosition < 0) {
            return false;
        }
        return b(originalView, longPressPosition, this.a.getItemId(longPressPosition));
    }

    public boolean showContextMenu() {
        if (!isPressed()) {
            return false;
        }
        return b(getChildAt(b(this.ao, this.x - this.j)), this.x, this.y);
    }

    private boolean b(View view, int position, long id) {
        boolean handled = false;
        if (this.t != null) {
            handled = this.t.a(this, this.ab, b(this.ao, this.aa), id);
        }
        if (!handled) {
            this.ak = new AdapterView.a(view, position, id);
            handled = super.showContextMenuForChild(this);
        }
        if (handled) {
            performHapticFeedback(0);
        }
        return handled;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return event.dispatch(this, null, null);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 21:
                if (l()) {
                    playSoundEffect(1);
                    return true;
                }
                break;
            case 22:
                if (m()) {
                    playSoundEffect(3);
                    return true;
                }
                break;
            case 23:
            case 66:
                this.aj = true;
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 23:
            case 66:
                if (this.aj && this.z > 0) {
                    g(this.af);
                    postDelayed(new Runnable(this) {
                        final /* synthetic */ GalleryFlow a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            this.a.s();
                        }
                    }, (long) ViewConfiguration.getPressedStateDuration());
                    int selectedIndex = this.x - this.j;
                    int selectedPosition = b(this.ao, this.x);
                    a(getChildAt(selectedIndex), selectedPosition, this.a.getItemId(selectedPosition));
                }
                this.aj = false;
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    boolean l() {
        if (this.z <= 0) {
            return false;
        }
        h((this.x - this.j) - 1);
        return true;
    }

    boolean m() {
        if (this.z <= 0) {
            return false;
        }
        h((this.x - this.j) + 1);
        return true;
    }

    private boolean h(int childPosition) {
        View child = getChildAt(b(this.ao, childPosition));
        if (child == null) {
            return false;
        }
        int childCount = getChildCount();
        this.ac.b((3 - g(e(child))) * this.R[2]);
        return true;
    }

    void setSelectedPositionInt(int position) {
        super.setSelectedPositionInt(position);
        t();
    }

    private void t() {
        View oldSelectedChild = this.af;
        View child = getChildAt(b(this.ao, this.x - this.j));
        this.af = child;
        if (child != null) {
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
        if (this.V != gravity) {
            this.V = gravity;
            requestLayout();
        }
    }

    protected int getChildDrawingOrder(int childCount, int i) {
        int selectedIndex = b(this.ao, this.x - this.j);
        if (selectedIndex < 0) {
            return i;
        }
        if (i == childCount - 1) {
            return selectedIndex;
        }
        if (i >= selectedIndex) {
            return (childCount - 1) - (i - selectedIndex);
        }
        return i;
    }

    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus && this.af != null) {
            this.af.requestFocus(direction);
            this.af.setSelected(true);
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(GalleryFlow.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        boolean z = true;
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(GalleryFlow.class.getName());
        if (this.z <= 1) {
            z = false;
        }
        info.setScrollable(z);
        if (isEnabled()) {
            if (this.z > 0 && this.x < this.z - 1) {
                info.addAction(4096);
            }
            if (isEnabled() && this.z > 0 && this.x > 0) {
                info.addAction(8192);
            }
        }
    }

    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if (super.performAccessibilityAction(action, arguments)) {
            return true;
        }
        switch (action) {
            case 4096:
                if (!isEnabled() || this.z <= 0 || this.x >= this.z - 1) {
                    return false;
                }
                return h((this.x - this.j) + 1);
            case 8192:
                if (!isEnabled() || this.z <= 0 || this.x <= 0) {
                    return false;
                }
                return h((this.x - this.j) - 1);
            default:
                return false;
        }
    }
}
