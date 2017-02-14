package com.meizu.cloud.app.widget;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.a.l;
import android.support.v4.view.ad;
import android.support.v4.view.ag;
import android.support.v4.view.ai;
import android.support.v4.view.as;
import android.support.v4.view.u;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import org.apache.commons.io.FileUtils;

public class ViewPager extends ViewGroup {
    private static final int[] a = new int[]{16842931};
    private static final Interpolator aA = new Interpolator() {
        public float getInterpolation(float input) {
            if (input <= 0.0f) {
                return ViewPager.az[0];
            }
            if (input >= 1.0f) {
                return ViewPager.az[ViewPager.az.length - 1];
            }
            float segment = 1.0f / ((float) (ViewPager.az.length - 1));
            int index = (int) (input / segment);
            return ViewPager.az[index] + (((ViewPager.az[index + 1] - ViewPager.az[index]) * (input - (((float) index) * segment))) / segment);
        }
    };
    private static final i av = new i();
    private static final j aw = new j();
    private static final float[] az = new float[]{0.0f, 0.0f, 0.003365234f, 0.01357806f, 0.030720964f, 0.05475371f, 0.08548926f, 0.12255032f, 0.16538717f, 0.21324258f, 0.2652047f, 0.32024413f, 0.37725833f, 0.4351431f, 0.49284747f, 0.5494277f, 0.6040792f, 0.6561299f, 0.7050707f, 0.7505254f, 0.7922336f, 0.8300537f, 0.86390066f, 0.8937803f, 0.91972214f, 0.94181687f, 0.9601534f, 0.974861f, 0.98606336f, 0.99389625f, 0.99851006f, 1.0f};
    private static final Comparator<c> c = new Comparator<c>() {
        public /* synthetic */ int compare(Object obj, Object obj2) {
            return a((c) obj, (c) obj2);
        }

        public int a(c lhs, c rhs) {
            return lhs.b - rhs.b;
        }
    };
    private static final Interpolator d = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return ((((t * t) * t) * t) * t) + 1.0f;
        }
    };
    private int A;
    private Drawable B;
    private int C;
    private int D;
    private float E = -3.4028235E38f;
    private float F = Float.MAX_VALUE;
    private int G;
    private int H;
    private boolean I;
    private boolean J;
    private boolean K;
    private int L = 1;
    private boolean M;
    private boolean N;
    private int O;
    private int P;
    private int Q;
    private float R;
    private float S;
    private float T;
    private float U;
    private int V = -1;
    private VelocityTracker W;
    private Interpolator aB;
    private int aa;
    private int ab;
    private int ac;
    private int ad;
    private Rect ae;
    private int af;
    private int ag = -1;
    private boolean ah;
    private android.support.v4.widget.g ai;
    private android.support.v4.widget.g aj;
    private boolean ak = true;
    private boolean al = false;
    private boolean am;
    private int an;
    private f ao;
    private f ap;
    private e aq;
    private g ar;
    private Method as;
    private int at;
    private LinkedList<View> au;
    private final Runnable ax = new Runnable(this) {
        final /* synthetic */ ViewPager a;

        {
            this.a = r1;
        }

        public void run() {
            this.a.setScrollState(0);
            this.a.c();
        }
    };
    private int ay = 0;
    private int b;
    private final ArrayList<c> e = new ArrayList();
    private final c f = new c();
    private final Rect g = new Rect();
    private ad h;
    private int i;
    private volatile b j = b.FLIP_MODE_DEFAULT;
    private volatile boolean k;
    private int l;
    private int m;
    private volatile boolean n;
    private int o;
    private int p;
    private int q;
    private Drawable r;
    private Drawable s;
    private int t;
    private Context u;
    private int v = -1;
    private Parcelable w = null;
    private ClassLoader x = null;
    private Scroller y;
    private h z;

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public boolean a;
        public int b;
        float c = 0.0f;
        boolean d;
        int e;
        int f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, ViewPager.a);
            this.b = a.getInteger(0, 48);
            a.recycle();
        }
    }

    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = android.support.v4.d.d.a(new android.support.v4.d.e<SavedState>() {
            public /* synthetic */ Object a(Parcel parcel, ClassLoader classLoader) {
                return b(parcel, classLoader);
            }

            public /* synthetic */ Object[] a(int i) {
                return b(i);
            }

            public SavedState b(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            public SavedState[] b(int size) {
                return new SavedState[size];
            }
        });
        int a;
        Parcelable b;
        ClassLoader c;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.a);
            out.writeParcelable(this.b, flags);
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.a + "}";
        }

        SavedState(Parcel in, ClassLoader loader) {
            super(in);
            if (loader == null) {
                loader = getClass().getClassLoader();
            }
            this.a = in.readInt();
            this.b = in.readParcelable(loader);
            this.c = loader;
        }
    }

    interface a {
    }

    public enum b {
        FLIP_MODE_DEFAULT,
        FLIP_MODE_OVERLAY
    }

    static class c {
        Object a;
        int b;
        boolean c;
        float d;
        float e;
        View f;
        int g;

        c() {
        }
    }

    class d extends android.support.v4.view.a {
        final /* synthetic */ ViewPager a;

        d(ViewPager viewPager) {
            this.a = viewPager;
        }

        public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(host, event);
            event.setClassName(ViewPager.class.getName());
            l recordCompat = l.a();
            recordCompat.a(a());
            if (event.getEventType() == 4096 && this.a.h != null) {
                recordCompat.a(this.a.h.b());
                recordCompat.b(this.a.i);
                recordCompat.c(this.a.i);
            }
        }

        public void onInitializeAccessibilityNodeInfo(View host, android.support.v4.view.a.c info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.b(ViewPager.class.getName());
            info.i(a());
            if (this.a.canScrollHorizontally(1)) {
                info.a(4096);
            }
            if (this.a.canScrollHorizontally(-1)) {
                info.a(8192);
            }
        }

        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            if (super.performAccessibilityAction(host, action, args)) {
                return true;
            }
            switch (action) {
                case 4096:
                    if (!this.a.canScrollHorizontally(1)) {
                        return false;
                    }
                    this.a.setCurrentItem(this.a.i + 1);
                    return true;
                case 8192:
                    if (!this.a.canScrollHorizontally(-1)) {
                        return false;
                    }
                    this.a.setCurrentItem(this.a.i - 1);
                    return true;
                default:
                    return false;
            }
        }

        private boolean a() {
            return this.a.h != null && this.a.h.b() > 1;
        }
    }

    interface e {
        void a(ad adVar, ad adVar2);
    }

    public interface f {
        void a(int i);

        void a(int i, float f, int i2);

        void b(int i);
    }

    public interface g {
        void a(View view, float f);
    }

    private class h extends DataSetObserver {
        final /* synthetic */ ViewPager a;

        private h(ViewPager viewPager) {
            this.a = viewPager;
        }

        public void onChanged() {
            this.a.b();
        }

        public void onInvalidated() {
            this.a.b();
        }
    }

    static class i implements Comparator<View> {
        i() {
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return a((View) obj, (View) obj2);
        }

        public int a(View lhs, View rhs) {
            LayoutParams llp = (LayoutParams) lhs.getLayoutParams();
            LayoutParams rlp = (LayoutParams) rhs.getLayoutParams();
            if (llp.a != rlp.a) {
                return llp.a ? 1 : -1;
            } else {
                return llp.e - rlp.e;
            }
        }
    }

    static class j implements Comparator<View> {
        j() {
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return a((View) obj, (View) obj2);
        }

        public int a(View lhs, View rhs) {
            LayoutParams llp = (LayoutParams) lhs.getLayoutParams();
            LayoutParams rlp = (LayoutParams) rhs.getLayoutParams();
            if (llp.a != rlp.a) {
                return llp.a ? 1 : -1;
            } else {
                return rlp.e - llp.e;
            }
        }
    }

    public ViewPager(Context context) {
        super(context);
        this.u = context;
        a();
    }

    public ViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.u = context;
        a();
    }

    void a() {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context = getContext();
        this.y = new Scroller(context, d);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        float density = context.getResources().getDisplayMetrics().density;
        this.Q = as.a(configuration);
        this.aa = (int) (400.0f * density);
        this.ab = configuration.getScaledMaximumFlingVelocity();
        this.ai = new android.support.v4.widget.g(context);
        this.aj = new android.support.v4.widget.g(context);
        this.ac = (int) (25.0f * density);
        this.ad = (int) (2.0f * density);
        this.O = (int) (16.0f * density);
        ai.a((View) this, new d(this));
        if (ai.e(this) == 0) {
            ai.c((View) this, 1);
        }
        if (b.FLIP_MODE_OVERLAY == this.j) {
            setChildrenDrawingOrderEnabledCompat(true);
        }
    }

    protected void onDetachedFromWindow() {
        removeCallbacks(this.ax);
        super.onDetachedFromWindow();
    }

    private void setScrollState(int newState) {
        if (this.ay != newState) {
            this.ay = newState;
            if (this.ar != null) {
                b(newState != 0);
            }
            if (this.ao != null) {
                this.ao.b(newState);
            }
        }
    }

    public void setAdapter(ad adapter) {
        if (this.h != null) {
            this.h.b(this.z);
            this.h.a((ViewGroup) this);
            for (int i = 0; i < this.e.size(); i++) {
                c ii = (c) this.e.get(i);
                this.h.a((ViewGroup) this, ii.b, ii.a);
            }
            this.h.b((ViewGroup) this);
            this.e.clear();
            h();
            this.i = 0;
            scrollTo(0, 0);
        }
        ad oldAdapter = this.h;
        this.h = adapter;
        this.b = 0;
        if (this.h != null) {
            if (this.z == null) {
                this.z = new h();
            }
            this.h.a(this.z);
            this.K = false;
            boolean wasFirstLayout = this.ak;
            this.ak = true;
            this.b = this.h.b();
            if (this.v >= 0) {
                this.h.a(this.w, this.x);
                a(this.v, false, true);
                this.v = -1;
                this.w = null;
                this.x = null;
            } else if (wasFirstLayout) {
                requestLayout();
            } else {
                c();
            }
        }
        if (this.aq != null && oldAdapter != adapter) {
            this.aq.a(oldAdapter, adapter);
        }
    }

    private void h() {
        int i = 0;
        while (i < getChildCount()) {
            if (!((LayoutParams) getChildAt(i).getLayoutParams()).a) {
                removeViewAt(i);
                i--;
            }
            i++;
        }
    }

    public ad getAdapter() {
        return this.h;
    }

    void setOnAdapterChangeListener(e listener) {
        this.aq = listener;
    }

    private int getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public void setCurrentItem(int item) {
        boolean z;
        this.K = false;
        if (this.ak) {
            z = false;
        } else {
            z = true;
        }
        a(item, z, false);
    }

    public void setCurrentItem(int item, int duration) {
        this.K = false;
        if (this.ak || duration <= 0) {
            a(item, false, false);
        }
        if (this.h == null || this.h.b() <= 0) {
            setScrollingCacheEnabled(false);
        } else if (this.i != item || this.e.size() == 0) {
            if (item < 0) {
                item = 0;
            } else if (item >= this.h.b()) {
                item = this.h.b() - 1;
            }
            int pageLimit = this.L;
            if (item > this.i + pageLimit || item < this.i - pageLimit) {
                for (int i = 0; i < this.e.size(); i++) {
                    ((c) this.e.get(i)).c = true;
                }
            }
            boolean dispatchSelected = this.i != item;
            a(item);
            c curInfo = b(item);
            int destX = 0;
            if (curInfo != null) {
                destX = (int) (((float) getClientWidth()) * Math.max(this.E, Math.min(curInfo.e, this.F)));
            }
            if (getChildCount() == 0) {
                setScrollingCacheEnabled(false);
                return;
            }
            int sx = getScrollX();
            int sy = getScrollY();
            int dx = destX - sx;
            int dy = 0 - sy;
            if (dx == 0 && dy == 0) {
                a(false);
                c();
                setScrollState(0);
                return;
            }
            setScrollingCacheEnabled(true);
            setScrollState(2);
            duration = Math.max(1, Math.min(duration, 5000));
            setInterpolator(aA);
            this.y.startScroll(sx, sy, dx, dy, duration);
            ai.d(this);
            if (dispatchSelected && this.ao != null) {
                this.ao.a(item);
            }
            if (dispatchSelected && this.ap != null) {
                this.ap.a(item);
            }
        } else {
            setScrollingCacheEnabled(false);
        }
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        this.K = false;
        a(item, smoothScroll, false);
    }

    public int getCurrentItem() {
        return this.i;
    }

    void a(int item, boolean smoothScroll, boolean always) {
        a(item, smoothScroll, always, 0);
    }

    void a(int item, boolean smoothScroll, boolean always, int velocity) {
        boolean dispatchSelected = true;
        if (this.h == null || this.h.b() <= 0) {
            setScrollingCacheEnabled(false);
        } else if (always || this.i != item || this.e.size() == 0) {
            if (item < 0) {
                item = 0;
            } else if (item >= this.h.b()) {
                item = this.h.b() - 1;
            }
            int pageLimit = this.L;
            if (item > this.i + pageLimit || item < this.i - pageLimit) {
                for (int i = 0; i < this.e.size(); i++) {
                    ((c) this.e.get(i)).c = true;
                }
            }
            if (this.i == item) {
                dispatchSelected = false;
            }
            if (this.ak) {
                this.i = item;
                if (dispatchSelected && this.ao != null) {
                    this.ao.a(item);
                }
                if (dispatchSelected && this.ap != null) {
                    this.ap.a(item);
                }
                requestLayout();
                return;
            }
            a(item);
            a(item, smoothScroll, velocity, dispatchSelected);
        } else {
            setScrollingCacheEnabled(false);
        }
    }

    private void a(int item, boolean smoothScroll, int velocity, boolean dispatchSelected) {
        c curInfo = b(item);
        int destX = 0;
        if (curInfo != null) {
            destX = (int) (((float) getClientWidth()) * Math.max(this.E, Math.min(curInfo.e, this.F)));
        }
        if (smoothScroll) {
            if (dispatchSelected && this.ao != null) {
                this.ao.a(item);
            }
            if (dispatchSelected && this.ap != null) {
                this.ap.a(item);
            }
            a(destX, 0, velocity);
            return;
        }
        if (dispatchSelected && this.ao != null) {
            this.ao.a(item);
        }
        if (dispatchSelected && this.ap != null) {
            this.ap.a(item);
        }
        a(false);
        scrollTo(destX, 0);
        e(destX);
    }

    public void setOnPageChangeListener(f listener) {
        this.ao = listener;
    }

    public void setPageTransformer(boolean reverseDrawingOrder, g transformer) {
        int i = 1;
        if (VERSION.SDK_INT >= 11) {
            boolean z;
            boolean hasTransformer = transformer != null;
            if (this.ar != null) {
                z = true;
            } else {
                z = false;
            }
            boolean needsPopulate = hasTransformer != z;
            this.ar = transformer;
            setChildrenDrawingOrderEnabledCompat(hasTransformer);
            if (hasTransformer) {
                if (reverseDrawingOrder) {
                    i = 2;
                }
                this.at = i;
            } else {
                this.at = 0;
            }
            if (needsPopulate) {
                c();
            }
        }
    }

    void setChildrenDrawingOrderEnabledCompat(boolean enable) {
        if (VERSION.SDK_INT >= 7) {
            if (this.as == null) {
                try {
                    this.as = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", new Class[]{Boolean.TYPE});
                } catch (NoSuchMethodException e) {
                    Log.e("ViewPager", "Can't find setChildrenDrawingOrderEnabled", e);
                }
            }
            try {
                this.as.setAccessible(true);
                this.as.invoke(this, new Object[]{Boolean.valueOf(enable)});
            } catch (Exception e2) {
                Log.e("ViewPager", "Error changing children drawing order", e2);
            }
        }
    }

    protected int getChildDrawingOrder(int childCount, int i) {
        int index;
        if (this.at == 2) {
            index = (childCount - 1) - i;
        } else {
            index = i;
        }
        return ((LayoutParams) ((View) this.au.get(index)).getLayoutParams()).f;
    }

    public int getOffscreenPageLimit() {
        return this.L;
    }

    public void setOffscreenPageLimit(int limit) {
        if (limit < 1) {
            Log.w("ViewPager", "Requested offscreen page limit " + limit + " too small; defaulting to " + 1);
            limit = 1;
        }
        if (limit != this.L) {
            this.L = limit;
            c();
        }
    }

    public void setPageMargin(int marginPixels) {
        if (b.FLIP_MODE_OVERLAY != this.j) {
            int oldMargin = this.A;
            this.A = marginPixels;
            int width = getWidth();
            a(width, width, marginPixels, oldMargin);
            requestLayout();
        }
    }

    public int getPageMargin() {
        return this.A;
    }

    public void setPageMarginDrawable(Drawable d) {
        this.B = d;
        if (d != null) {
            refreshDrawableState();
        }
        setWillNotDraw(d == null);
        invalidate();
    }

    public void setPageMarginDrawable(int resId) {
        setPageMarginDrawable(getContext().getResources().getDrawable(resId));
    }

    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.B;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable d = this.B;
        if (d != null && d.isStateful()) {
            d.setState(getDrawableState());
        }
    }

    float a(float f) {
        return (float) Math.sin((double) ((float) (((double) (f - 0.5f)) * 0.4712389167638204d)));
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
            a(false);
            c();
            setScrollState(0);
            return;
        }
        int duration;
        setScrollingCacheEnabled(true);
        setScrollState(2);
        int width = getClientWidth();
        int halfWidth = width / 2;
        float distance = ((float) halfWidth) + (((float) halfWidth) * a(Math.min(1.0f, (1.0f * ((float) Math.abs(dx))) / ((float) width))));
        velocity = Math.abs(velocity);
        if (velocity > 0) {
            duration = Math.round(1000.0f * Math.abs(distance / ((float) velocity))) * 4;
        } else {
            float pageDelta = ((float) Math.abs(dx)) / (((float) this.A) + (((float) width) * this.h.d(this.i)));
            if (b.FLIP_MODE_OVERLAY != this.j || pageDelta < 1.0f) {
                duration = (int) ((1.0f + pageDelta) * 100.0f);
            } else {
                duration = (int) ((4.0f + pageDelta) * 100.0f);
            }
        }
        this.y.startScroll(sx, sy, dx, dy, Math.min(duration, 600));
        ai.d(this);
    }

    c a(int position, int index) {
        c ii = new c();
        ii.b = position;
        ii.a = this.h.a((ViewGroup) this, position);
        ii.d = this.h.d(position);
        if (index < 0 || index >= this.e.size()) {
            ii.g = this.e.size();
            this.e.add(ii);
        } else {
            this.e.add(index, ii);
            ii.g = index;
        }
        return ii;
    }

    void b() {
        boolean needPopulate;
        int adapterCount = this.h.b();
        this.b = adapterCount;
        if (this.e.size() >= (this.L * 2) + 1 || this.e.size() >= adapterCount) {
            needPopulate = false;
        } else {
            needPopulate = true;
        }
        int newCurrItem = this.i;
        boolean isUpdating = false;
        int i = 0;
        while (i < this.e.size()) {
            c ii = (c) this.e.get(i);
            int newPos = this.h.a(ii.a);
            if (newPos != -1) {
                if (newPos == -2) {
                    this.e.remove(i);
                    i--;
                    if (!isUpdating) {
                        this.h.a((ViewGroup) this);
                        isUpdating = true;
                    }
                    this.h.a((ViewGroup) this, ii.b, ii.a);
                    needPopulate = true;
                    if (this.i == ii.b) {
                        newCurrItem = Math.max(0, Math.min(this.i, adapterCount - 1));
                        needPopulate = true;
                    }
                } else if (ii.b != newPos) {
                    if (ii.b == this.i) {
                        newCurrItem = newPos;
                    }
                    ii.b = newPos;
                    needPopulate = true;
                }
            }
            i++;
        }
        if (isUpdating) {
            this.h.b((ViewGroup) this);
        }
        Collections.sort(this.e, c);
        if (b.FLIP_MODE_OVERLAY == this.j) {
            i();
        }
        if (needPopulate) {
            int childCount = getChildCount();
            for (i = 0; i < childCount; i++) {
                LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
                if (!lp.a) {
                    lp.c = 0.0f;
                }
            }
            a(newCurrItem, false, true);
            requestLayout();
        }
    }

    private void i() {
        int childWidth = getClientWidth();
        int paddingLeft = getPaddingLeft();
        int scrollX = getScrollX();
        for (int i = 0; i < this.e.size(); i++) {
            c ii = (c) this.e.get(i);
            ii.g = i;
            if (paddingLeft + ((int) (((float) childWidth) * ii.e)) == scrollX) {
                this.l = i;
            }
            if (ii.b == this.i) {
                this.m = i;
            }
        }
    }

    void c() {
        a(this.i);
    }

    void a(int newCurrentItem) {
        c oldCurInfo = null;
        int focusDirection = 2;
        if (this.i != newCurrentItem) {
            focusDirection = this.i < newCurrentItem ? 66 : 17;
            oldCurInfo = b(this.i);
            this.i = newCurrentItem;
            if ((this.h == null || this.K || getWindowToken() == null) && b.FLIP_MODE_OVERLAY == this.j) {
                i();
            }
        }
        if (this.h == null) {
            j();
        } else if (this.K) {
            j();
        } else if (getWindowToken() != null) {
            this.h.a((ViewGroup) this);
            int pageLimit = this.L;
            int startPos = Math.max(0, this.i - pageLimit);
            int N = this.h.b();
            int endPos = Math.min(N - 1, this.i + pageLimit);
            if (N != this.b) {
                String resName;
                try {
                    resName = getResources().getResourceName(getId());
                } catch (NotFoundException e) {
                    resName = Integer.toHexString(getId());
                }
                throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.b + ", found: " + N + " Pager id: " + resName + " Pager class: " + getClass() + " Problematic adapter: " + this.h.getClass());
            }
            c ii;
            float extraWidthLeft;
            int itemIndex;
            int clientWidth;
            float leftWidthNeeded;
            int pos;
            float extraWidthRight;
            float rightWidthNeeded;
            int childCount;
            int i;
            View child;
            LayoutParams lp;
            View currentFocused;
            c curItem = null;
            int curIndex = 0;
            while (curIndex < this.e.size()) {
                ii = (c) this.e.get(curIndex);
                if (ii.b >= this.i) {
                    if (ii.b == this.i) {
                        curItem = ii;
                    }
                    if (curItem == null && N > 0) {
                        curItem = a(this.i, curIndex);
                    }
                    if (curItem != null) {
                        extraWidthLeft = 0.0f;
                        itemIndex = curIndex - 1;
                        ii = itemIndex < 0 ? (c) this.e.get(itemIndex) : null;
                        clientWidth = getClientWidth();
                        leftWidthNeeded = clientWidth > 0 ? 0.0f : (2.0f - curItem.d) + (((float) getPaddingLeft()) / ((float) clientWidth));
                        pos = this.i - 1;
                        while (pos >= 0) {
                            if (extraWidthLeft >= leftWidthNeeded || pos >= startPos) {
                                if (ii == null && pos == ii.b) {
                                    extraWidthLeft += ii.d;
                                    itemIndex--;
                                    ii = itemIndex >= 0 ? (c) this.e.get(itemIndex) : null;
                                } else {
                                    extraWidthLeft += a(pos, itemIndex + 1).d;
                                    curIndex++;
                                    ii = itemIndex < 0 ? (c) this.e.get(itemIndex) : null;
                                }
                            } else if (ii == null) {
                                break;
                            } else {
                                if (pos == ii.b && !ii.c) {
                                    this.e.remove(itemIndex);
                                    this.h.a((ViewGroup) this, pos, ii.a);
                                    itemIndex--;
                                    curIndex--;
                                    if (itemIndex >= 0) {
                                        ii = (c) this.e.get(itemIndex);
                                    } else {
                                        ii = null;
                                    }
                                }
                            }
                            pos--;
                        }
                        extraWidthRight = curItem.d;
                        itemIndex = curIndex + 1;
                        if (extraWidthRight < 2.0f) {
                            ii = itemIndex >= this.e.size() ? (c) this.e.get(itemIndex) : null;
                            rightWidthNeeded = clientWidth > 0 ? 0.0f : (((float) getPaddingRight()) / ((float) clientWidth)) + 2.0f;
                            pos = this.i + 1;
                            while (pos < N) {
                                if (extraWidthRight >= rightWidthNeeded || pos <= endPos) {
                                    if (ii == null && pos == ii.b) {
                                        extraWidthRight += ii.d;
                                        itemIndex++;
                                        ii = itemIndex < this.e.size() ? (c) this.e.get(itemIndex) : null;
                                    } else {
                                        itemIndex++;
                                        extraWidthRight += a(pos, itemIndex).d;
                                        ii = itemIndex >= this.e.size() ? (c) this.e.get(itemIndex) : null;
                                    }
                                } else if (ii == null) {
                                    break;
                                } else {
                                    if (pos == ii.b && !ii.c) {
                                        this.e.remove(itemIndex);
                                        this.h.a((ViewGroup) this, pos, ii.a);
                                        if (itemIndex < this.e.size()) {
                                            ii = (c) this.e.get(itemIndex);
                                        } else {
                                            ii = null;
                                        }
                                    }
                                }
                                pos++;
                            }
                        }
                        a(curItem, curIndex, oldCurInfo);
                    }
                    this.h.b((ViewGroup) this, this.i, curItem == null ? curItem.a : null);
                    this.h.b((ViewGroup) this);
                    childCount = getChildCount();
                    for (i = 0; i < childCount; i++) {
                        child = getChildAt(i);
                        lp = (LayoutParams) child.getLayoutParams();
                        lp.f = i;
                        if (!lp.a && lp.c == 0.0f) {
                            ii = a(child);
                            if (ii != null) {
                                lp.c = ii.d;
                                lp.e = ii.b;
                            }
                        }
                    }
                    j();
                    if (hasFocus()) {
                        currentFocused = findFocus();
                        ii = currentFocused == null ? b(currentFocused) : null;
                        if (ii == null || ii.b != this.i) {
                            for (i = 0; i < getChildCount(); i++) {
                                child = getChildAt(i);
                                ii = a(child);
                                if (ii == null && ii.b == this.i && child.requestFocus(focusDirection)) {
                                    break;
                                }
                            }
                        }
                    }
                    if (b.FLIP_MODE_OVERLAY == this.j) {
                        i();
                    }
                }
                curIndex++;
            }
            curItem = a(this.i, curIndex);
            if (curItem != null) {
                extraWidthLeft = 0.0f;
                itemIndex = curIndex - 1;
                if (itemIndex < 0) {
                }
                clientWidth = getClientWidth();
                if (clientWidth > 0) {
                }
                pos = this.i - 1;
                while (pos >= 0) {
                    if (extraWidthLeft >= leftWidthNeeded) {
                    }
                    if (ii == null) {
                    }
                    extraWidthLeft += a(pos, itemIndex + 1).d;
                    curIndex++;
                    if (itemIndex < 0) {
                    }
                    pos--;
                }
                extraWidthRight = curItem.d;
                itemIndex = curIndex + 1;
                if (extraWidthRight < 2.0f) {
                    if (itemIndex >= this.e.size()) {
                    }
                    if (clientWidth > 0) {
                    }
                    pos = this.i + 1;
                    while (pos < N) {
                        if (extraWidthRight >= rightWidthNeeded) {
                        }
                        if (ii == null) {
                        }
                        itemIndex++;
                        extraWidthRight += a(pos, itemIndex).d;
                        if (itemIndex >= this.e.size()) {
                        }
                        pos++;
                    }
                }
                a(curItem, curIndex, oldCurInfo);
            }
            if (curItem == null) {
            }
            this.h.b((ViewGroup) this, this.i, curItem == null ? curItem.a : null);
            this.h.b((ViewGroup) this);
            childCount = getChildCount();
            for (i = 0; i < childCount; i++) {
                child = getChildAt(i);
                lp = (LayoutParams) child.getLayoutParams();
                lp.f = i;
                ii = a(child);
                if (ii != null) {
                    lp.c = ii.d;
                    lp.e = ii.b;
                }
            }
            j();
            if (hasFocus()) {
                currentFocused = findFocus();
                if (currentFocused == null) {
                }
                for (i = 0; i < getChildCount(); i++) {
                    child = getChildAt(i);
                    ii = a(child);
                    if (ii == null) {
                    }
                }
            }
            if (b.FLIP_MODE_OVERLAY == this.j) {
                i();
            }
        }
    }

    private void j() {
        int childCount;
        int i;
        if (b.FLIP_MODE_OVERLAY == this.j) {
            if (this.au == null) {
                this.au = new LinkedList();
            } else {
                this.au.clear();
            }
            childCount = getChildCount();
            for (i = 0; i < childCount; i++) {
                this.au.add(getChildAt(i));
            }
            Collections.sort(this.au, aw);
        } else if (this.at != 0) {
            if (this.au == null) {
                this.au = new LinkedList();
            } else {
                this.au.clear();
            }
            childCount = getChildCount();
            for (i = 0; i < childCount; i++) {
                this.au.add(getChildAt(i));
            }
            Collections.sort(this.au, av);
        }
    }

    private void a(c curItem, int curIndex, c oldCurInfo) {
        float offset;
        int pos;
        c ii;
        int N = this.h.b();
        int width = getClientWidth();
        float marginOffset = width > 0 ? ((float) this.A) / ((float) width) : 0.0f;
        if (oldCurInfo != null) {
            int oldCurPosition = oldCurInfo.b;
            int itemIndex;
            if (oldCurPosition < curItem.b) {
                itemIndex = 0;
                offset = (oldCurInfo.e + oldCurInfo.d) + marginOffset;
                pos = oldCurPosition + 1;
                while (pos <= curItem.b && itemIndex < this.e.size()) {
                    ii = (c) this.e.get(itemIndex);
                    while (pos > ii.b && itemIndex < this.e.size() - 1) {
                        itemIndex++;
                        ii = (c) this.e.get(itemIndex);
                    }
                    while (pos < ii.b) {
                        offset += this.h.d(pos) + marginOffset;
                        pos++;
                    }
                    ii.e = offset;
                    offset += ii.d + marginOffset;
                    pos++;
                }
            } else if (oldCurPosition > curItem.b) {
                itemIndex = this.e.size() - 1;
                offset = oldCurInfo.e;
                pos = oldCurPosition - 1;
                while (pos >= curItem.b && itemIndex >= 0) {
                    ii = (c) this.e.get(itemIndex);
                    while (pos < ii.b && itemIndex > 0) {
                        itemIndex--;
                        ii = (c) this.e.get(itemIndex);
                    }
                    while (pos > ii.b) {
                        offset -= this.h.d(pos) + marginOffset;
                        pos--;
                    }
                    offset -= ii.d + marginOffset;
                    ii.e = offset;
                    pos--;
                }
            }
        }
        int itemCount = this.e.size();
        offset = curItem.e;
        pos = curItem.b - 1;
        this.E = curItem.b == 0 ? curItem.e : -3.4028235E38f;
        this.F = curItem.b == N + -1 ? (curItem.e + curItem.d) - 1.0f : Float.MAX_VALUE;
        int i = curIndex - 1;
        while (i >= 0) {
            ii = (c) this.e.get(i);
            while (pos > ii.b) {
                offset -= this.h.d(pos) + marginOffset;
                pos--;
            }
            offset -= ii.d + marginOffset;
            ii.e = offset;
            if (ii.b == 0) {
                this.E = offset;
            }
            i--;
            pos--;
        }
        offset = (curItem.e + curItem.d) + marginOffset;
        pos = curItem.b + 1;
        i = curIndex + 1;
        while (i < itemCount) {
            ii = (c) this.e.get(i);
            while (pos < ii.b) {
                offset += this.h.d(pos) + marginOffset;
                pos++;
            }
            if (ii.b == N - 1) {
                this.F = (ii.d + offset) - 1.0f;
            }
            ii.e = offset;
            offset += ii.d + marginOffset;
            i++;
            pos++;
        }
        this.al = false;
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.a = this.i;
        if (this.h != null) {
            ss.b = this.h.a();
        }
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            if (this.h != null) {
                this.h.a(ss.b, ss.c);
                a(ss.a, false, true);
                return;
            }
            this.v = ss.a;
            this.w = ss.b;
            this.x = ss.c;
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (!checkLayoutParams(params)) {
            params = generateLayoutParams(params);
        }
        LayoutParams lp = (LayoutParams) params;
        lp.a |= child instanceof a;
        if (!this.I) {
            super.addView(child, index, params);
        } else if (lp == null || !lp.a) {
            lp.d = true;
            addViewInLayout(child, index, params);
        } else {
            throw new IllegalStateException("Cannot add pager decor view during layout");
        }
    }

    public void removeView(View view) {
        if (this.I) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    c a(View child) {
        for (int i = 0; i < this.e.size(); i++) {
            c ii = (c) this.e.get(i);
            if (this.h.a(child, ii.a)) {
                return ii;
            }
        }
        return null;
    }

    c b(View child) {
        while (true) {
            View parent = child.getParent();
            if (parent == this) {
                return a(child);
            }
            if (parent != null && (parent instanceof View)) {
                child = parent;
            }
        }
        return null;
    }

    c b(int position) {
        for (int i = 0; i < this.e.size(); i++) {
            c ii = (c) this.e.get(i);
            if (ii.b == position) {
                return ii;
            }
        }
        return null;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.ak = true;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        LayoutParams lp;
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int measuredWidth = getMeasuredWidth();
        this.P = Math.min(measuredWidth / 10, this.O);
        int childWidthSize = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int childHeightSize = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int size = getChildCount();
        for (i = 0; i < size; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                lp = (LayoutParams) child.getLayoutParams();
                if (lp != null && lp.a) {
                    int hgrav = lp.b & 7;
                    int vgrav = lp.b & 112;
                    int widthMode = Integer.MIN_VALUE;
                    int heightMode = Integer.MIN_VALUE;
                    boolean consumeVertical = vgrav == 48 || vgrav == 80;
                    boolean consumeHorizontal = hgrav == 3 || hgrav == 5;
                    if (consumeVertical) {
                        widthMode = FileUtils.ONE_GB;
                    } else if (consumeHorizontal) {
                        heightMode = FileUtils.ONE_GB;
                    }
                    int widthSize = childWidthSize;
                    int heightSize = childHeightSize;
                    if (lp.width != -2) {
                        widthMode = FileUtils.ONE_GB;
                        if (lp.width != -1) {
                            widthSize = lp.width;
                        }
                    }
                    if (lp.height != -2) {
                        heightMode = FileUtils.ONE_GB;
                        if (lp.height != -1) {
                            heightSize = lp.height;
                        }
                    }
                    child.measure(MeasureSpec.makeMeasureSpec(widthSize, widthMode), MeasureSpec.makeMeasureSpec(heightSize, heightMode));
                    if (consumeVertical) {
                        childHeightSize -= child.getMeasuredHeight();
                    } else if (consumeHorizontal) {
                        childWidthSize -= child.getMeasuredWidth();
                    }
                }
            }
        }
        this.G = MeasureSpec.makeMeasureSpec(childWidthSize, FileUtils.ONE_GB);
        this.H = MeasureSpec.makeMeasureSpec(childHeightSize, FileUtils.ONE_GB);
        this.I = true;
        c();
        this.I = false;
        size = getChildCount();
        for (i = 0; i < size; i++) {
            child = getChildAt(i);
            if (child.getVisibility() != 8) {
                lp = (LayoutParams) child.getLayoutParams();
                if (lp == null || !lp.a) {
                    child.measure(MeasureSpec.makeMeasureSpec((int) (((float) childWidthSize) * lp.c), FileUtils.ONE_GB), this.H);
                }
            }
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw) {
            a(w, oldw, this.A, this.A);
        }
    }

    private void a(int width, int oldWidth, int margin, int oldMargin) {
        if (oldWidth <= 0 || this.e.isEmpty()) {
            c ii = b(this.i);
            int scrollPos = (int) (((float) ((width - getPaddingLeft()) - getPaddingRight())) * (ii != null ? Math.min(ii.e, this.F) : 0.0f));
            if (scrollPos != getScrollX()) {
                a(false);
                scrollTo(scrollPos, getScrollY());
                return;
            }
            return;
        }
        int newOffsetPixels = (int) (((float) (((width - getPaddingLeft()) - getPaddingRight()) + margin)) * (((float) getScrollX()) / ((float) (((oldWidth - getPaddingLeft()) - getPaddingRight()) + oldMargin))));
        scrollTo(newOffsetPixels, getScrollY());
        this.y.startScroll(newOffsetPixels, 0, (int) ((b(this.i).e * ((float) width)) - ((float) newOffsetPixels)), 0, this.y.getDuration() - this.y.timePassed());
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int i;
        LayoutParams lp;
        int count = getChildCount();
        int width = r - l;
        int height = b - t;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int decorCount = 0;
        for (i = 0; i < count; i++) {
            int childLeft;
            int childTop;
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                lp = (LayoutParams) child.getLayoutParams();
                if (lp.a) {
                    int vgrav = lp.b & 112;
                    switch (lp.b & 7) {
                        case 1:
                            childLeft = Math.max((width - child.getMeasuredWidth()) / 2, paddingLeft);
                            break;
                        case 3:
                            childLeft = paddingLeft;
                            paddingLeft += child.getMeasuredWidth();
                            break;
                        case 5:
                            childLeft = (width - paddingRight) - child.getMeasuredWidth();
                            paddingRight += child.getMeasuredWidth();
                            break;
                        default:
                            childLeft = paddingLeft;
                            break;
                    }
                    switch (vgrav) {
                        case 16:
                            childTop = Math.max((height - child.getMeasuredHeight()) / 2, paddingTop);
                            break;
                        case 48:
                            childTop = paddingTop;
                            paddingTop += child.getMeasuredHeight();
                            break;
                        case 80:
                            childTop = (height - paddingBottom) - child.getMeasuredHeight();
                            paddingBottom += child.getMeasuredHeight();
                            break;
                        default:
                            childTop = paddingTop;
                            break;
                    }
                    childLeft += scrollX;
                    child.layout(childLeft, childTop, child.getMeasuredWidth() + childLeft, child.getMeasuredHeight() + childTop);
                    decorCount++;
                }
            }
        }
        int childWidth = (width - paddingLeft) - paddingRight;
        for (i = 0; i < count; i++) {
            child = getChildAt(i);
            if (child.getVisibility() != 8) {
                lp = (LayoutParams) child.getLayoutParams();
                if (!lp.a) {
                    c ii = a(child);
                    if (ii != null) {
                        childLeft = paddingLeft + ((int) (((float) childWidth) * ii.e));
                        childTop = paddingTop;
                        if (lp.d) {
                            lp.d = false;
                            child.measure(MeasureSpec.makeMeasureSpec((int) (((float) childWidth) * lp.c), FileUtils.ONE_GB), MeasureSpec.makeMeasureSpec((height - paddingTop) - paddingBottom, FileUtils.ONE_GB));
                        }
                        ii.f = child;
                        child.layout(childLeft, childTop, child.getMeasuredWidth() + childLeft, child.getMeasuredHeight() + childTop);
                    }
                }
            }
        }
        this.C = paddingTop;
        this.D = height - paddingBottom;
        this.an = decorCount;
        if (b.FLIP_MODE_OVERLAY == this.j) {
            this.k = true;
            i();
            d(scrollX);
        }
        if (this.ak) {
            a(this.i, false, 0, false);
        }
        this.ak = false;
    }

    public void computeScroll() {
        if (this.y.isFinished() || !this.y.computeScrollOffset()) {
            a(true);
            return;
        }
        int oldX = getScrollX();
        int oldY = getScrollY();
        int x = this.y.getCurrX();
        int y = this.y.getCurrY();
        if (!(oldX == x && oldY == y)) {
            if (b.FLIP_MODE_OVERLAY == this.j) {
                d(x);
            }
            scrollTo(x, y);
            if (!e(x)) {
                this.y.abortAnimation();
                scrollTo(0, y);
            }
        }
        ai.d(this);
    }

    private void d(int toX) {
        if (this.e.size() != 0) {
            int paddingLeft = getPaddingLeft();
            int childWidth = getClientWidth();
            topIndex = this.l >= this.e.size() ? this.e.size() - 1 : this.l < 0 ? 0 : this.l;
            int tPageL = ((int) (((c) this.e.get(topIndex)).e * ((float) childWidth))) + paddingLeft;
            View topPage = ((c) this.e.get(topIndex)).f;
            c lItemInfo = topIndex + -1 >= 0 ? (c) this.e.get(topIndex - 1) : null;
            c rItemInfo = topIndex + 1 < this.e.size() ? (c) this.e.get(topIndex + 1) : null;
            View lPage = lItemInfo != null ? lItemInfo.f : null;
            View rPage = rItemInfo != null ? rItemInfo.f : null;
            int tSclDistance = toX - tPageL;
            int newTopPageIndex = topIndex;
            if (tSclDistance >= (this.A / 2) + childWidth) {
                newTopPageIndex = topIndex + 1;
            } else {
                if (tSclDistance <= (-((this.A / 2) + childWidth))) {
                    newTopPageIndex = topIndex - 1;
                }
            }
            if (newTopPageIndex < 0) {
                newTopPageIndex = 0;
            } else if (newTopPageIndex >= this.e.size()) {
                newTopPageIndex = this.e.size() - 1;
            }
            if (this.l != newTopPageIndex) {
                this.l = newTopPageIndex;
                topIndex = newTopPageIndex;
                tPageL = ((int) (((c) this.e.get(topIndex)).e * ((float) childWidth))) + paddingLeft;
                topPage = ((c) this.e.get(topIndex)).f;
                if (topPage != null) {
                    topPage.offsetLeftAndRight(tPageL - topPage.getLeft());
                }
                if (topIndex + 2 < this.e.size() && rPage != null) {
                    rPage.offsetLeftAndRight((((int) (((float) childWidth) * rItemInfo.e)) + paddingLeft) - rPage.getLeft());
                }
                if (topIndex - 2 >= 0 && lPage != null) {
                    lPage.offsetLeftAndRight((((int) (((float) childWidth) * lItemInfo.e)) + paddingLeft) - lPage.getLeft());
                }
                lItemInfo = topIndex + -1 >= 0 ? (c) this.e.get(topIndex - 1) : null;
                rItemInfo = topIndex + 1 < this.e.size() ? (c) this.e.get(topIndex + 1) : null;
                lPage = lItemInfo != null ? lItemInfo.f : null;
                rPage = rItemInfo != null ? rItemInfo.f : null;
                tSclDistance = toX - tPageL;
            }
            if (topPage != null) {
                this.p = topPage.getMeasuredHeight();
                this.q = topPage.getTop();
                c curInfo = (this.m < 0 || this.m >= this.e.size()) ? null : (c) this.e.get(this.m);
                int curPageL = 0;
                if (curInfo != null) {
                    curPageL = ((int) (((float) childWidth) * curInfo.e)) + paddingLeft;
                }
                int rLeft = 0;
                int rChildLeft = 0;
                if (!(rItemInfo == null || rPage == null)) {
                    rChildLeft = paddingLeft + ((int) (((float) childWidth) * rItemInfo.e));
                    rLeft = ((rChildLeft - (childWidth / 2)) - (this.A / 2)) + (tSclDistance / 2);
                }
                int lChildLeft = 0;
                if (!(lItemInfo == null || lPage == null)) {
                    lChildLeft = paddingLeft + ((int) (((float) childWidth) * lItemInfo.e));
                }
                if ((curInfo != null && toX == curPageL) || toX == tPageL) {
                    this.n = false;
                    if (rPage != null) {
                        rPage.offsetLeftAndRight(rChildLeft - rPage.getLeft());
                    }
                    if (lPage != null) {
                        lPage.offsetLeftAndRight(lChildLeft - lPage.getLeft());
                    }
                    topPage.offsetLeftAndRight(tPageL - topPage.getLeft());
                } else if (tSclDistance >= 0 && rPage != null) {
                    this.o = topPage.getRight();
                    this.t = (int) (102.0d - (((((double) tSclDistance) * 1.0d) / ((double) childWidth)) * 102.0d));
                    this.n = true;
                    rPage.offsetLeftAndRight(rLeft - rPage.getLeft());
                } else if (tSclDistance < 0 && lPage != null) {
                    this.o = lPage.getRight();
                    this.t = (int) (((((double) (-tSclDistance)) * 1.0d) / ((double) childWidth)) * 102.0d);
                    this.n = true;
                    topPage.offsetLeftAndRight(((tSclDistance / 2) + tPageL) - topPage.getLeft());
                }
            }
        }
    }

    private boolean e(int xpos) {
        if (this.e.size() == 0) {
            this.am = false;
            a(0, 0.0f, 0);
            if (this.am) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        c ii = k();
        int width = getClientWidth();
        int widthWithMargin = width + this.A;
        float marginOffset = ((float) this.A) / ((float) width);
        int currentPage = ii.b;
        float pageOffset = ((((float) xpos) / ((float) width)) - ii.e) / (ii.d + marginOffset);
        int offsetPixels = (int) (((float) widthWithMargin) * pageOffset);
        this.am = false;
        a(currentPage, pageOffset, offsetPixels);
        if (this.am) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    protected void a(int position, float offset, int offsetPixels) {
        int scrollX;
        int childCount;
        int i;
        View child;
        if (this.an > 0) {
            scrollX = getScrollX();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int width = getWidth();
            childCount = getChildCount();
            for (i = 0; i < childCount; i++) {
                child = getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.a) {
                    int childLeft;
                    switch (lp.b & 7) {
                        case 1:
                            childLeft = Math.max((width - child.getMeasuredWidth()) / 2, paddingLeft);
                            break;
                        case 3:
                            childLeft = paddingLeft;
                            paddingLeft += child.getWidth();
                            break;
                        case 5:
                            childLeft = (width - paddingRight) - child.getMeasuredWidth();
                            paddingRight += child.getMeasuredWidth();
                            break;
                        default:
                            childLeft = paddingLeft;
                            break;
                    }
                    int childOffset = (childLeft + scrollX) - child.getLeft();
                    if (childOffset != 0) {
                        child.offsetLeftAndRight(childOffset);
                    }
                }
            }
        }
        if (this.ao != null) {
            this.ao.a(position, offset, offsetPixels);
        }
        if (this.ap != null) {
            this.ap.a(position, offset, offsetPixels);
        }
        if (this.ar != null) {
            scrollX = getScrollX();
            childCount = getChildCount();
            for (i = 0; i < childCount; i++) {
                child = getChildAt(i);
                if (!((LayoutParams) child.getLayoutParams()).a) {
                    this.ar.a(child, ((float) (child.getLeft() - scrollX)) / ((float) getClientWidth()));
                }
            }
        }
        this.am = true;
    }

    private void a(boolean postEvents) {
        boolean needPopulate;
        if (this.ay == 2) {
            needPopulate = true;
        } else {
            needPopulate = false;
        }
        if (needPopulate) {
            setScrollingCacheEnabled(false);
            this.y.abortAnimation();
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = this.y.getCurrX();
            int y = this.y.getCurrY();
            if (!(oldX == x && oldY == y)) {
                scrollTo(x, y);
            }
        }
        this.K = false;
        for (int i = 0; i < this.e.size(); i++) {
            c ii = (c) this.e.get(i);
            if (ii.c) {
                needPopulate = true;
                ii.c = false;
            }
        }
        if (!needPopulate) {
            return;
        }
        if (postEvents) {
            ai.a((View) this, this.ax);
        } else {
            this.ax.run();
        }
    }

    private boolean a(float x, float dx) {
        return (x < ((float) this.P) && dx > 0.0f) || (x > ((float) (getWidth() - this.P)) && dx < 0.0f);
    }

    private void b(boolean enable) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ai.a(getChildAt(i), enable ? 2 : 0, null);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & 255;
        if (action == 3 || action == 1) {
            this.M = false;
            this.N = false;
            this.V = -1;
            if (this.W != null) {
                this.W.recycle();
                this.W = null;
            }
            return false;
        }
        if (action != 0) {
            if (this.M) {
                return true;
            }
            if (this.N) {
                return false;
            }
        }
        switch (action) {
            case 0:
                float x = ev.getX();
                this.T = x;
                this.R = x;
                x = ev.getY();
                this.U = x;
                this.S = x;
                this.V = u.b(ev, 0);
                this.N = false;
                this.y.computeScrollOffset();
                if (this.ay == 2 && Math.abs(this.y.getFinalX() - this.y.getCurrX()) > this.ad) {
                    this.y.abortAnimation();
                    this.K = false;
                    c();
                    this.M = true;
                    c(true);
                    setScrollState(1);
                    break;
                }
                a(false);
                this.M = false;
                break;
            case 2:
                int activePointerId = this.V;
                if (activePointerId != -1) {
                    int pointerIndex = u.a(ev, activePointerId);
                    if (pointerIndex == -1) {
                        Log.e("ViewPager", "Invalid pointerId=" + activePointerId + " in onInterceptTouchEvent ACTION_MOVE");
                        break;
                    }
                    float x2 = u.c(ev, pointerIndex);
                    float dx = x2 - this.R;
                    float xDiff = Math.abs(dx);
                    float y = u.d(ev, pointerIndex);
                    float yDiff = Math.abs(y - this.U);
                    if (dx != 0.0f && !a(this.R, dx) && a(this, false, (int) dx, (int) x2, (int) y)) {
                        this.R = x2;
                        this.S = y;
                        this.N = true;
                        return false;
                    } else if (xDiff >= ((float) this.ag) || yDiff >= ((float) this.ag) || !a(ev, this.ae)) {
                        if (xDiff > ((float) this.Q) && xDiff > yDiff) {
                            this.M = true;
                            c(true);
                            setScrollState(1);
                            this.R = dx > 0.0f ? this.T + ((float) this.Q) : this.T - ((float) this.Q);
                            this.S = y;
                            setScrollingCacheEnabled(true);
                        } else if (yDiff > ((float) this.Q)) {
                            this.N = true;
                        }
                        if (this.M && b(x2)) {
                            ai.d(this);
                            break;
                        }
                    } else {
                        Log.d("ViewPager", "xDiff = " + xDiff + ", yDiff = " + yDiff + ", mTouchSlopAdj = " + this.ag);
                        return false;
                    }
                }
                break;
            case 6:
                a(ev);
                break;
        }
        if (this.W == null) {
            this.W = VelocityTracker.obtain();
        }
        this.W.addMovement(ev);
        return this.M;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.ah) {
            return true;
        }
        if (ev.getAction() == 0 && ev.getEdgeFlags() != 0) {
            return false;
        }
        if (this.h == null || this.h.b() == 0) {
            return false;
        }
        if (this.W == null) {
            this.W = VelocityTracker.obtain();
        }
        this.W.addMovement(ev);
        boolean needsInvalidate = false;
        float x;
        switch (ev.getAction() & 255) {
            case 0:
                this.y.abortAnimation();
                this.K = false;
                c();
                x = ev.getX();
                this.T = x;
                this.R = x;
                x = ev.getY();
                this.U = x;
                this.S = x;
                this.V = u.b(ev, 0);
                break;
            case 1:
                if (this.M) {
                    VelocityTracker velocityTracker = this.W;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.ab);
                    int initialVelocity = (int) ag.a(velocityTracker, this.V);
                    this.K = true;
                    int width = getClientWidth();
                    int scrollX = getScrollX();
                    c ii = k();
                    int currentPage = ii.b;
                    float pageOffset = ((((float) scrollX) / ((float) width)) - ii.e) / ii.d;
                    int activePointerIndex = u.a(ev, this.V);
                    int pointerCount = u.c(ev);
                    if (activePointerIndex >= 0 && activePointerIndex < pointerCount) {
                        if (b.FLIP_MODE_OVERLAY == this.j) {
                            setInterpolator(d);
                        }
                        a(a(currentPage, pageOffset, initialVelocity, (int) (u.c(ev, activePointerIndex) - this.T)), true, true, initialVelocity);
                        this.V = -1;
                        n();
                        needsInvalidate = this.ai.b() | this.aj.b();
                        break;
                    }
                    a(currentPage, true, true);
                    this.V = -1;
                    n();
                    needsInvalidate = this.ai.b() | this.aj.b();
                    break;
                }
                break;
            case 2:
                if (!this.M) {
                    int pointerIndex = u.a(ev, this.V);
                    float x2 = u.c(ev, pointerIndex);
                    float xDiff = Math.abs(x2 - this.R);
                    float y = u.d(ev, pointerIndex);
                    float yDiff = Math.abs(y - this.S);
                    if (xDiff > ((float) this.Q) && xDiff > yDiff) {
                        this.M = true;
                        c(true);
                        if (x2 - this.T > 0.0f) {
                            x = this.T + ((float) this.Q);
                        } else {
                            x = this.T - ((float) this.Q);
                        }
                        this.R = x;
                        this.S = y;
                        setScrollState(1);
                        setScrollingCacheEnabled(true);
                        ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                if (this.M) {
                    needsInvalidate = false | b(u.c(ev, u.a(ev, this.V)));
                    break;
                }
                break;
            case 3:
                if (this.M) {
                    a(this.i, true, 0, false);
                    this.V = -1;
                    n();
                    needsInvalidate = this.ai.b() | this.aj.b();
                    break;
                }
                break;
            case 5:
                int index = u.b(ev);
                this.R = u.c(ev, index);
                this.V = u.b(ev, index);
                break;
            case 6:
                a(ev);
                this.R = u.c(ev, u.a(ev, this.V));
                break;
        }
        if (needsInvalidate) {
            ai.d(this);
        }
        return true;
    }

    private void c(boolean disallowIntercept) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    private boolean b(float x) {
        boolean needsInvalidate = false;
        float deltaX = this.R - x;
        this.R = x;
        float scrollX = ((float) getScrollX()) + deltaX;
        int width = getClientWidth();
        float leftBound = ((float) width) * this.E;
        float rightBound = ((float) width) * this.F;
        boolean leftAbsolute = true;
        boolean rightAbsolute = true;
        c firstItem = (c) this.e.get(0);
        c lastItem = (c) this.e.get(this.e.size() - 1);
        if (firstItem.b != 0) {
            leftAbsolute = false;
            leftBound = firstItem.e * ((float) width);
        }
        if (lastItem.b != this.h.b() - 1) {
            rightAbsolute = false;
            rightBound = lastItem.e * ((float) width);
        }
        if (scrollX < leftBound) {
            if (leftAbsolute) {
                needsInvalidate = this.ai.a(Math.abs(leftBound - scrollX) / ((float) width));
            }
            scrollX = leftBound;
        } else if (scrollX > rightBound) {
            if (rightAbsolute) {
                needsInvalidate = this.aj.a(Math.abs(scrollX - rightBound) / ((float) width));
            }
            scrollX = rightBound;
        }
        this.R += scrollX - ((float) ((int) scrollX));
        if (b.FLIP_MODE_OVERLAY == this.j) {
            d((int) scrollX);
        }
        scrollTo((int) scrollX, getScrollY());
        e((int) scrollX);
        return needsInvalidate;
    }

    private c k() {
        float scrollOffset;
        float marginOffset = 0.0f;
        int width = getClientWidth();
        if (width > 0) {
            scrollOffset = ((float) getScrollX()) / ((float) width);
        } else {
            scrollOffset = 0.0f;
        }
        if (width > 0) {
            marginOffset = ((float) this.A) / ((float) width);
        }
        int lastPos = -1;
        float lastOffset = 0.0f;
        float lastWidth = 0.0f;
        boolean first = true;
        c lastItem = null;
        int i = 0;
        while (i < this.e.size()) {
            c ii = (c) this.e.get(i);
            if (!(first || ii.b == lastPos + 1)) {
                ii = this.f;
                ii.e = (lastOffset + lastWidth) + marginOffset;
                ii.b = lastPos + 1;
                ii.d = this.h.d(ii.b);
                i--;
            }
            float offset = ii.e;
            float leftBound = offset;
            float rightBound = (ii.d + offset) + marginOffset;
            if (!first && scrollOffset < leftBound) {
                return lastItem;
            }
            if (scrollOffset < rightBound || i == this.e.size() - 1) {
                return ii;
            }
            first = false;
            lastPos = ii.b;
            lastOffset = offset;
            lastWidth = ii.d;
            lastItem = ii;
            i++;
        }
        return lastItem;
    }

    private int a(int currentPage, float pageOffset, int velocity, int deltaX) {
        int targetPage;
        if (Math.abs(deltaX) <= this.ac || Math.abs(velocity) <= this.aa) {
            targetPage = (int) ((((float) currentPage) + pageOffset) + (currentPage >= this.i ? 0.4f : 0.6f));
        } else {
            targetPage = velocity > 0 ? currentPage : currentPage + 1;
        }
        if (this.e.size() <= 0) {
            return targetPage;
        }
        return Math.max(((c) this.e.get(0)).b, Math.min(targetPage, ((c) this.e.get(this.e.size() - 1)).b));
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    private boolean l() {
        if (this.k) {
            this.k = false;
            if (this.r != null) {
                this.r.setBounds(0, 0, this.r.getIntrinsicWidth(), this.p);
            }
            if (this.s != null) {
                this.s.setBounds(0, 0, getClientWidth(), this.p);
            }
            if (this.r == null || this.s == null) {
                return false;
            }
            return true;
        } else if (this.r == null || this.s == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean m() {
        return l();
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (b.FLIP_MODE_OVERLAY == this.j && this.n && m()) {
            canvas.save();
            canvas.translate((float) this.o, (float) this.q);
            this.r.draw(canvas);
            this.s.setAlpha(this.t);
            this.s.draw(canvas);
            canvas.restore();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.A > 0 && this.B != null && this.e.size() > 0 && this.h != null) {
            int scrollX = getScrollX();
            int width = getWidth();
            float marginOffset = ((float) this.A) / ((float) width);
            int itemIndex = 0;
            c ii = (c) this.e.get(0);
            float offset = ii.e;
            int itemCount = this.e.size();
            int firstPos = ii.b;
            int lastPos = ((c) this.e.get(itemCount - 1)).b;
            int pos = firstPos;
            while (pos < lastPos) {
                float drawAt;
                while (pos > ii.b && itemIndex < itemCount) {
                    itemIndex++;
                    ii = (c) this.e.get(itemIndex);
                }
                if (pos == ii.b) {
                    drawAt = (ii.e + ii.d) * ((float) width);
                    offset = (ii.e + ii.d) + marginOffset;
                } else {
                    float widthFactor = this.h.d(pos);
                    drawAt = (offset + widthFactor) * ((float) width);
                    offset += widthFactor + marginOffset;
                }
                if (((float) this.A) + drawAt > ((float) scrollX)) {
                    this.B.setBounds((int) drawAt, this.C, (int) ((((float) this.A) + drawAt) + 0.5f), this.D);
                    this.B.draw(canvas);
                }
                if (drawAt <= ((float) (scrollX + width))) {
                    pos++;
                } else {
                    return;
                }
            }
        }
    }

    private void a(MotionEvent ev) {
        int pointerIndex = u.b(ev);
        if (u.b(ev, pointerIndex) == this.V) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            this.R = u.c(ev, newPointerIndex);
            this.V = u.b(ev, newPointerIndex);
            if (this.W != null) {
                this.W.clear();
            }
        }
    }

    private void n() {
        this.M = false;
        this.N = false;
        if (this.W != null) {
            this.W.recycle();
            this.W = null;
        }
    }

    private void setScrollingCacheEnabled(boolean enabled) {
        if (this.J != enabled) {
            this.J = enabled;
        }
    }

    public boolean canScrollHorizontally(int direction) {
        boolean z = true;
        if (this.h == null) {
            return false;
        }
        int width = getClientWidth();
        int scrollX = getScrollX();
        if (direction < 0) {
            if (scrollX <= ((int) (((float) width) * this.E))) {
                z = false;
            }
            return z;
        } else if (direction <= 0) {
            return false;
        } else {
            if (scrollX >= ((int) (((float) width) * this.F))) {
                z = false;
            }
            return z;
        }
    }

    protected boolean a(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int scrollX = v.getScrollX();
            int scrollY = v.getScrollY();
            for (int i = group.getChildCount() - 1; i >= 0; i--) {
                View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() && y + scrollY >= child.getTop() && y + scrollY < child.getBottom()) {
                    if (a(child, true, dx, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
                        return true;
                    }
                }
            }
        }
        return checkV && ai.a(v, -dx);
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
                if (android.support.v4.view.h.a(event)) {
                    return c(2);
                }
                if (android.support.v4.view.h.a(event, 1)) {
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
        } else if (currentFocused != null) {
            boolean isChild = false;
            for (ViewPager parent = currentFocused.getParent(); parent instanceof ViewGroup; parent = parent.getParent()) {
                if (parent == this) {
                    isChild = true;
                    break;
                }
            }
            if (!isChild) {
                StringBuilder sb = new StringBuilder();
                sb.append(currentFocused.getClass().getSimpleName());
                for (ViewParent parent2 = currentFocused.getParent(); parent2 instanceof ViewGroup; parent2 = parent2.getParent()) {
                    sb.append(" => ").append(parent2.getClass().getSimpleName());
                }
                Log.e("ViewPager", "arrowScroll tried to find focus based on non-child current focused view " + sb.toString());
                currentFocused = null;
            }
        }
        boolean handled = false;
        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);
        if (nextFocused == null || nextFocused == currentFocused) {
            if (direction == 17 || direction == 1) {
                handled = d();
            } else if (direction == 66 || direction == 2) {
                handled = e();
            }
        } else if (direction == 17) {
            handled = (currentFocused == null || a(this.g, nextFocused).left < a(this.g, currentFocused).left) ? nextFocused.requestFocus() : d();
        } else if (direction == 66) {
            handled = (currentFocused == null || a(this.g, nextFocused).left > a(this.g, currentFocused).left) ? nextFocused.requestFocus() : e();
        }
        if (handled) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
        }
        return handled;
    }

    private Rect a(Rect outRect, View child) {
        if (outRect == null) {
            outRect = new Rect();
        }
        if (child == null) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.left = child.getLeft();
            outRect.right = child.getRight();
            outRect.top = child.getTop();
            outRect.bottom = child.getBottom();
            ViewGroup parent = child.getParent();
            while ((parent instanceof ViewGroup) && parent != this) {
                ViewGroup group = parent;
                outRect.left += group.getLeft();
                outRect.right += group.getRight();
                outRect.top += group.getTop();
                outRect.bottom += group.getBottom();
                parent = group.getParent();
            }
        }
        return outRect;
    }

    boolean d() {
        if (this.i <= 0) {
            return false;
        }
        setCurrentItem(this.i - 1, true);
        return true;
    }

    boolean e() {
        if (this.h == null || this.i >= this.h.b() - 1) {
            return false;
        }
        setCurrentItem(this.i + 1, true);
        return true;
    }

    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        int focusableCount = views.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getVisibility() == 0) {
                    c ii = a(child);
                    if (ii != null && ii.b == this.i) {
                        child.addFocusables(views, direction, focusableMode);
                    }
                }
            }
        }
        if ((descendantFocusability == 262144 && focusableCount != views.size()) || !isFocusable()) {
            return;
        }
        if (((focusableMode & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && views != null) {
            views.add(this);
        }
    }

    public void addTouchables(ArrayList<View> views) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0) {
                c ii = a(child);
                if (ii != null && ii.b == this.i) {
                    child.addTouchables(views);
                }
            }
        }
    }

    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        int index;
        int increment;
        int end;
        int count = getChildCount();
        if ((direction & 2) != 0) {
            index = 0;
            increment = 1;
            end = count;
        } else {
            index = count - 1;
            increment = -1;
            end = -1;
        }
        for (int i = index; i != end; i += increment) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0) {
                c ii = a(child);
                if (ii != null && ii.b == this.i && child.requestFocus(direction, previouslyFocusedRect)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(event);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0) {
                c ii = a(child);
                if (ii != null && ii.b == this.i && child.dispatchPopulateAccessibilityEvent(event)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setFlipMode(b mode) {
        if (this.j != mode) {
            this.j = mode;
            if (b.FLIP_MODE_OVERLAY == this.j) {
                this.A = 0;
                i();
                setChildrenDrawingOrderEnabledCompat(true);
            } else {
                setChildrenDrawingOrderEnabledCompat(false);
            }
            j();
            requestLayout();
        }
    }

    public b getFlipMode() {
        return this.j;
    }

    public void setShadowResource(int shadowResid, int coverResid) {
        if (shadowResid != 0) {
            this.r = getContext().getResources().getDrawable(shadowResid);
            this.k = true;
        }
        if (coverResid != 0) {
            this.s = getContext().getResources().getDrawable(coverResid);
            this.k = true;
        }
    }

    public void setShadow(Drawable shadow, Drawable cover) {
        if (shadow != this.r) {
            this.r = shadow;
            this.k = true;
        }
        if (cover != this.s) {
            this.s = cover;
            this.k = true;
        }
    }

    public void setInterpolator(Interpolator interpolator) {
        if (!(this.y == null || this.y.isFinished())) {
            this.y.forceFinished(true);
        }
        if (interpolator != null && this.aB != interpolator) {
            this.aB = interpolator;
            this.y = new Scroller(this.u, interpolator);
        }
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return generateDefaultLayoutParams();
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return (p instanceof LayoutParams) && super.checkLayoutParams(p);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public void setRectSlopScaleInTab(int left, int top, int right, int bottom, float coef, int specTab) {
        this.ae = new Rect();
        this.ae.left = left;
        this.ae.top = top;
        this.ae.right = right;
        this.ae.bottom = bottom;
        this.ag = (int) (((float) this.Q) * coef);
        this.af = specTab;
        Log.d("ViewPager", "setRectSlopScaleInTab mSpecRect = " + this.ae + ", coef = " + coef + ", specTab = " + this.af);
    }

    private boolean a(MotionEvent event, Rect rect) {
        float x = event.getRawX();
        float y = event.getRawY();
        Log.d("ViewPager", "pointInRect x = " + x + ", y = " + y + ", rect = " + rect);
        if (rect == null || this.i != this.af || x < ((float) rect.left) || x > ((float) rect.right) || y < ((float) rect.top) || y > ((float) rect.bottom)) {
            return false;
        }
        return true;
    }
}
