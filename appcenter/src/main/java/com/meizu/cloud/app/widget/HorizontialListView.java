package com.meizu.cloud.app.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.Scroller;
import java.util.LinkedList;
import java.util.Queue;

public class HorizontialListView extends AdapterView<ListAdapter> {
    public boolean a = true;
    protected ListAdapter b;
    protected int c;
    protected int d;
    protected Scroller e;
    float f = 0.0f;
    float g = 0.0f;
    float h = 0.0f;
    float i = 0.0f;
    private int j = -1;
    private int k = 0;
    private int l = Integer.MAX_VALUE;
    private int m = 0;
    private GestureDetector n;
    private Queue<View> o = new LinkedList();
    private OnItemSelectedListener p;
    private OnItemClickListener q;
    private boolean r = false;
    private DataSetObserver s = new DataSetObserver(this) {
        final /* synthetic */ HorizontialListView a;

        {
            this.a = r1;
        }

        public void onChanged() {
            synchronized (this.a) {
                this.a.r = true;
            }
            this.a.invalidate();
            this.a.requestLayout();
        }

        public void onInvalidated() {
            this.a.b();
            this.a.invalidate();
            this.a.requestLayout();
        }
    };
    private OnGestureListener t = new SimpleOnGestureListener(this) {
        final /* synthetic */ HorizontialListView a;

        {
            this.a = r1;
        }

        public boolean onDown(MotionEvent e) {
            return this.a.a(e);
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return this.a.a(e1, e2, velocityX, velocityY);
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            synchronized (this.a) {
                HorizontialListView horizontialListView = this.a;
                horizontialListView.d += (int) distanceX;
            }
            this.a.requestLayout();
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            return a(e);
        }

        public boolean onDoubleTap(MotionEvent e) {
            return a(e);
        }

        private boolean a(MotionEvent e) {
            Rect viewRect = new Rect();
            for (int i = 0; i < this.a.getChildCount(); i++) {
                View child = this.a.getChildAt(i);
                viewRect.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
                if (viewRect.contains((int) e.getX(), (int) e.getY())) {
                    if (this.a.q != null) {
                        this.a.q.onItemClick(this.a, child, (this.a.j + 1) + i, this.a.b.getItemId((this.a.j + 1) + i));
                    }
                    if (this.a.p != null) {
                        this.a.p.onItemSelected(this.a, child, (this.a.j + 1) + i, this.a.b.getItemId((this.a.j + 1) + i));
                    }
                    return true;
                }
            }
            return true;
        }
    };

    public HorizontialListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a();
    }

    private synchronized void a() {
        this.j = -1;
        this.k = 0;
        this.m = 0;
        this.c = 0;
        this.d = 0;
        this.l = Integer.MAX_VALUE;
        this.e = new Scroller(getContext());
        this.n = new GestureDetector(getContext(), this.t);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.p = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.q = listener;
    }

    public ListAdapter getAdapter() {
        return this.b;
    }

    public View getSelectedView() {
        return null;
    }

    public void setAdapter(ListAdapter adapter) {
        if (this.b != null) {
            this.b.unregisterDataSetObserver(this.s);
        }
        this.b = adapter;
        this.b.registerDataSetObserver(this.s);
        b();
    }

    private synchronized void b() {
        a();
        removeAllViewsInLayout();
        requestLayout();
    }

    public void setSelection(int position) {
    }

    private void a(View child, int viewPos) {
        LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(-1, -1);
        }
        addViewInLayout(child, viewPos, params, true);
        child.measure(MeasureSpec.makeMeasureSpec(getWidth(), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(getHeight(), Integer.MIN_VALUE));
    }

    protected synchronized void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.b != null) {
            if (this.r) {
                int oldCurrentX = this.c;
                a();
                removeAllViewsInLayout();
                this.d = oldCurrentX;
                this.r = false;
            }
            if (this.e.computeScrollOffset()) {
                this.d = this.e.getCurrX();
            }
            if (this.d < 0) {
                this.d = 0;
                this.e.forceFinished(true);
            }
            if (this.d > this.l) {
                this.d = this.l;
                this.e.forceFinished(true);
            }
            int dx = this.c - this.d;
            b(dx);
            a(dx);
            c(dx);
            this.c = this.d;
            if (!this.e.isFinished()) {
                post(new Runnable(this) {
                    final /* synthetic */ HorizontialListView a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        this.a.requestLayout();
                    }
                });
            }
        }
    }

    private void a(int dx) {
        int edge = 0;
        View child = getChildAt(getChildCount() - 1);
        if (child != null) {
            edge = child.getRight();
        }
        a(edge, dx);
        edge = 0;
        child = getChildAt(0);
        if (child != null) {
            edge = child.getLeft();
        }
        b(edge, dx);
    }

    private void a(int rightEdge, int dx) {
        while (rightEdge + dx < getWidth() && this.k < this.b.getCount()) {
            View child = this.b.getView(this.k, (View) this.o.poll(), this);
            a(child, -1);
            rightEdge += child.getMeasuredWidth();
            if (this.k == this.b.getCount() - 1) {
                this.l = (this.c + rightEdge) - getWidth();
                if (this.l < 0) {
                    this.l = 0;
                }
            }
            this.k++;
        }
    }

    private void b(int leftEdge, int dx) {
        while (leftEdge + dx > 0 && this.j >= 0) {
            View child = this.b.getView(this.j, (View) this.o.poll(), this);
            a(child, 0);
            leftEdge -= child.getMeasuredWidth();
            this.j--;
            this.m -= child.getMeasuredWidth();
        }
    }

    private void b(int dx) {
        View child = getChildAt(0);
        while (child != null && child.getRight() + dx <= 0) {
            this.m += child.getMeasuredWidth();
            this.o.offer(child);
            removeViewInLayout(child);
            this.j++;
            child = getChildAt(0);
        }
        child = getChildAt(getChildCount() - 1);
        while (child != null && child.getLeft() + dx >= getWidth()) {
            this.o.offer(child);
            removeViewInLayout(child);
            this.k--;
            child = getChildAt(getChildCount() - 1);
        }
    }

    private void c(int dx) {
        if (getChildCount() > 0) {
            this.m += dx;
            int left = this.m;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                int childWidth = child.getMeasuredWidth();
                child.layout(left, 0, left + childWidth, child.getMeasuredHeight());
                left += childWidth;
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            this.f = ev.getX();
            this.i = ev.getY();
        }
        requestDisallowInterceptTouchEvent(true);
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 2) {
            this.g = event.getX();
            this.h = event.getY();
            if (Math.abs(this.h - this.i) > Math.abs(this.g - this.f)) {
                requestDisallowInterceptTouchEvent(false);
                return true;
            } else if (this.g - this.f >= 0.0f || this.d != this.l) {
                requestDisallowInterceptTouchEvent(true);
            } else {
                requestDisallowInterceptTouchEvent(false);
                return true;
            }
        }
        return this.n.onTouchEvent(event);
    }

    protected boolean a(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        synchronized (this) {
            this.e.fling(this.d, 0, (int) (-velocityX), 0, 0, this.l, 0, 0);
        }
        requestLayout();
        return true;
    }

    protected boolean a(MotionEvent e) {
        this.e.forceFinished(true);
        return true;
    }

    public void setHorizontialMaxX() {
        if (this.l <= 0) {
            this.l = Integer.MAX_VALUE;
        }
    }
}
