package com.meizu.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import org.apache.commons.io.FileUtils;

public class PinnedHeaderListView extends AutoScrollListView implements OnScrollListener, OnItemSelectedListener {
    private b a;
    private int b;
    private a[] c;
    private RectF d;
    private Rect e;
    private OnScrollListener f;
    private OnItemSelectedListener g;
    private int h;
    private int i;
    private boolean j;
    private long k;
    private int l;
    private int m;
    private int n;
    private Drawable o;

    private static final class a {
        View a;
        boolean b;
        int c;
        int d;
        int e;
        int f;
        boolean g;
        boolean h;
        int i;
        int j;
        long k;

        private a() {
        }
    }

    public interface b {
        int a();

        View a(int i, View view, ViewGroup viewGroup);

        void a(PinnedHeaderListView pinnedHeaderListView);
    }

    public PinnedHeaderListView(Context context) {
        this(context, null, 16842868);
    }

    public PinnedHeaderListView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842868);
    }

    public PinnedHeaderListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.d = new RectF();
        this.e = new Rect();
        this.i = 20;
        this.o = null;
        super.setOnScrollListener(this);
        super.setOnItemSelectedListener(this);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.l = 0;
        this.m = r - l;
    }

    public void setPinnedHeaderAnimationDuration(int duration) {
        this.i = duration;
    }

    public void setAdapter(ListAdapter adapter) {
        this.a = (b) adapter;
        super.setAdapter(adapter);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.f = onScrollListener;
        super.setOnScrollListener(this);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.g = listener;
        super.setOnItemSelectedListener(this);
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.a != null) {
            int count = this.a.a();
            if (count != this.b) {
                this.b = count;
                if (this.c == null) {
                    this.c = new a[this.b];
                } else if (this.c.length < this.b) {
                    a[] headers = this.c;
                    this.c = new a[this.b];
                    System.arraycopy(headers, 0, this.c, 0, headers.length);
                }
            }
            int i = 0;
            while (i < this.b) {
                if (this.c[i] == null) {
                    this.c[i] = new a();
                }
                this.c[i].a = this.a.a(i, this.c[i].a, this);
                if (!(this.o == null || this.c[i].a == null)) {
                    this.c[i].a.setBackground(this.o);
                }
                i++;
            }
            this.k = System.currentTimeMillis() + ((long) this.i);
            this.a.a(this);
            a();
        }
        if (this.f != null) {
            this.f.onScroll(this, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    protected float getTopFadingEdgeStrength() {
        return this.b > 0 ? 0.0f : super.getTopFadingEdgeStrength();
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.h = scrollState;
        if (this.f != null) {
            this.f.onScrollStateChanged(this, scrollState);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int windowTop = 0;
        int windowBottom = getHeight();
        for (int i = 0; i < this.b; i++) {
            a header = this.c[i];
            if (header.b) {
                if (header.f == 0) {
                    windowTop = header.c + header.d;
                } else if (header.f == 1) {
                    windowBottom = header.c;
                    break;
                }
            }
        }
        View selectedView = getSelectedView();
        if (selectedView != null) {
            if (selectedView.getTop() < windowTop) {
                setSelectionFromTop(position, windowTop);
            } else if (selectedView.getBottom() > windowBottom) {
                setSelectionFromTop(position, windowBottom - selectedView.getHeight());
            }
        }
        if (this.g != null) {
            this.g.onItemSelected(parent, view, position, id);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        if (this.g != null) {
            this.g.onNothingSelected(parent);
        }
    }

    public int a(int viewIndex) {
        b(viewIndex);
        return this.c[viewIndex].a.getHeight();
    }

    public void setHeaderPinnedAtTop(int viewIndex, int y, boolean animate) {
        b(viewIndex);
        a header = this.c[viewIndex];
        header.b = true;
        header.c = y;
        header.f = 0;
        header.g = false;
    }

    public void setHeaderPinnedAtBottom(int viewIndex, int y, boolean animate) {
        b(viewIndex);
        a header = this.c[viewIndex];
        header.f = 1;
        if (header.g) {
            header.k = this.k;
            header.i = header.c;
            header.j = y;
        } else if (!animate || (header.c == y && header.b)) {
            header.b = true;
            header.c = y;
        } else {
            if (header.b) {
                header.i = header.c;
            } else {
                header.b = true;
                header.i = header.d + y;
            }
            header.g = true;
            header.h = true;
            header.k = this.k;
            header.j = y;
        }
    }

    public void setFadingHeader(int viewIndex, int position, boolean fade) {
        b(viewIndex);
        if (getChildAt(position - getFirstVisiblePosition()) != null) {
            a header = this.c[viewIndex];
            header.b = true;
            header.f = 2;
            header.e = 255;
            header.g = false;
            header.c = getTotalTopPinnedHeaderHeight();
        }
    }

    public void setHeaderInvisible(int viewIndex, boolean animate) {
        a header = this.c[viewIndex];
        if (header.b && ((animate || header.g) && header.f == 1)) {
            header.i = header.c;
            if (!header.g) {
                header.b = true;
                header.j = getBottom() + header.d;
            }
            header.g = true;
            header.k = this.k;
            header.h = false;
            return;
        }
        header.b = false;
    }

    private void b(int viewIndex) {
        View view = this.c[viewIndex].a;
        if (view.isLayoutRequested()) {
            int heightSpec;
            int widthSpec = MeasureSpec.makeMeasureSpec(this.m, FileUtils.ONE_GB);
            LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null || layoutParams.height <= 0) {
                heightSpec = MeasureSpec.makeMeasureSpec(0, 0);
            } else {
                heightSpec = MeasureSpec.makeMeasureSpec(layoutParams.height, FileUtils.ONE_GB);
            }
            view.measure(widthSpec, heightSpec);
            int height = view.getMeasuredHeight();
            this.c[viewIndex].d = height;
            view.layout(0, 0, this.m, height);
        }
    }

    public int getTotalTopPinnedHeaderHeight() {
        int i = this.b;
        while (true) {
            i--;
            if (i < 0) {
                return 0;
            }
            a header = this.c[i];
            if (header.b && header.f == 0) {
                return header.c + header.d;
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    private void a() {
        this.j = false;
        for (int i = 0; i < this.b; i++) {
            if (this.c[i].g) {
                this.j = true;
                invalidate();
                return;
            }
        }
    }

    protected void dispatchDraw(Canvas canvas) {
        int i;
        long currentTime = this.j ? System.currentTimeMillis() : 0;
        int top = 0;
        int bottom = getBottom();
        boolean hasVisibleHeaders = false;
        for (i = 0; i < this.b; i++) {
            a header = this.c[i];
            if (header.b) {
                hasVisibleHeaders = true;
                if (header.f == 1 && header.c < bottom) {
                    bottom = header.c;
                } else if (header.f == 0 || header.f == 2) {
                    int newTop = header.c + header.d;
                    if (newTop > top) {
                        top = newTop;
                    }
                }
            }
        }
        if (hasVisibleHeaders) {
            canvas.save();
            this.e.set(0, 0, getWidth(), bottom);
            canvas.clipRect(this.e);
        }
        super.dispatchDraw(canvas);
        if (hasVisibleHeaders) {
            canvas.restore();
            i = this.b;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                header = this.c[i];
                if (header.b && (header.f == 0 || header.f == 2)) {
                    a(canvas, header, currentTime);
                }
            }
            for (i = 0; i < this.b; i++) {
                header = this.c[i];
                if (header.b && header.f == 1) {
                    a(canvas, header, currentTime);
                }
            }
        }
        a();
    }

    private void a(Canvas canvas, a header, long currentTime) {
        if (header.g) {
            int timeLeft = (int) (header.k - currentTime);
            if (timeLeft <= 0) {
                header.c = header.j;
                header.b = header.h;
                header.g = false;
            } else {
                header.c = header.j + (((header.i - header.j) * timeLeft) / this.i);
            }
        }
        if (header.b) {
            View view = header.a;
            int saveCount = canvas.save();
            if (header.f == 0 || header.f == 2) {
                canvas.translate((float) this.l, (float) (header.c + this.n));
            } else {
                canvas.translate((float) this.l, (float) header.c);
            }
            if (header.f == 2) {
                this.d.set(0.0f, 0.0f, (float) this.m, (float) view.getHeight());
                canvas.saveLayerAlpha(this.d, header.e, 31);
            }
            view.draw(canvas);
            canvas.restoreToCount(saveCount);
        }
    }

    public void setSelection(int position) {
        if (this.a instanceof i) {
            i indexerListAdapter = this.a;
            if (!indexerListAdapter.b(position - getHeaderViewsCount()).a && indexerListAdapter.d() && this.b > 0) {
                super.setSelectionFromTop(position, a(0));
                return;
            }
        }
        super.setSelection(position);
    }

    public int getCurrentOverScrollDistance() {
        if (getFirstVisiblePosition() != 0 || getChildCount() <= 0) {
            return 0;
        }
        return getPaddingTop() - getChildAt(0).getTop();
    }

    public void setHeaderPaddingTop(int padding) {
        if (padding >= 0) {
            this.n = padding;
        }
    }

    public int getHeaderPaddingTop() {
        return this.n;
    }

    public void setHeaderBackground(Drawable drawable) {
        if (drawable != null && drawable != this.o) {
            if (this.o != null) {
                this.o.setCallback(null);
                unscheduleDrawable(this.o);
            }
            this.o = drawable;
            this.o.setCallback(this);
            requestLayout();
            invalidate();
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(PinnedHeaderListView.class.getName());
    }
}
