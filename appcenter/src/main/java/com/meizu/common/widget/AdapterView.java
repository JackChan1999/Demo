package com.meizu.common.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.CapturedViewProperty;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Adapter;

public abstract class AdapterView<T extends Adapter> extends ViewGroup {
    int A;
    int B = -1;
    long C = Long.MIN_VALUE;
    boolean D = false;
    private int a;
    private View b;
    private boolean c;
    private boolean d;
    private f e;
    private Context f;
    @ExportedProperty(category = "scrolling")
    int j = 0;
    int k;
    int l;
    long m = Long.MIN_VALUE;
    long n;
    boolean o = false;
    int p;
    boolean q = false;
    e r;
    c s;
    d t;
    boolean u;
    @ExportedProperty(category = "list")
    int v = -1;
    long w = Long.MIN_VALUE;
    @ExportedProperty(category = "list")
    int x = -1;
    long y = Long.MIN_VALUE;
    @ExportedProperty(category = "list")
    int z;

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

    class b extends DataSetObserver {
        final /* synthetic */ AdapterView a;
        private Parcelable b = null;

        b(AdapterView adapterView) {
            this.a = adapterView;
        }

        public void onChanged() {
            this.a.u = true;
            this.a.A = this.a.z;
            this.a.z = this.a.getAdapter().getCount();
            if (!this.a.getAdapter().hasStableIds() || this.b == null || this.a.A != 0 || this.a.z <= 0) {
                this.a.i();
            } else {
                this.a.onRestoreInstanceState(this.b);
                this.b = null;
            }
            this.a.d();
            this.a.requestLayout();
        }

        public void onInvalidated() {
            this.a.u = true;
            if (this.a.getAdapter().hasStableIds()) {
                this.b = this.a.onSaveInstanceState();
            }
            this.a.A = this.a.z;
            this.a.z = 0;
            this.a.x = -1;
            this.a.y = Long.MIN_VALUE;
            this.a.v = -1;
            this.a.w = Long.MIN_VALUE;
            this.a.o = false;
            this.a.d();
            this.a.requestLayout();
        }
    }

    public interface c {
        void a(AdapterView<?> adapterView, View view, int i, long j);
    }

    public interface d {
        boolean a(AdapterView<?> adapterView, View view, int i, long j);
    }

    public interface e {
        void a(AdapterView<?> adapterView);

        void a(AdapterView<?> adapterView, View view, int i, long j);
    }

    private class f implements Runnable {
        final /* synthetic */ AdapterView a;

        private f(AdapterView adapterView) {
            this.a = adapterView;
        }

        public void run() {
            if (!this.a.u) {
                this.a.a();
                this.a.b();
            } else if (this.a.getAdapter() != null) {
                this.a.post(this);
            }
        }
    }

    public abstract T getAdapter();

    public abstract View getSelectedView();

    public abstract void setAdapter(T t);

    public abstract void setSelection(int i);

    public AdapterView(Context context) {
        super(context);
        this.f = context;
    }

    public AdapterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.f = context;
    }

    public AdapterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.f = context;
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    public void setOnItemClickListener(c listener) {
        this.s = listener;
    }

    public final c getOnItemClickListener() {
        return this.s;
    }

    public boolean a(View view, int position, long id) {
        if (this.s == null) {
            return false;
        }
        playSoundEffect(0);
        if (view != null) {
            view.sendAccessibilityEvent(1);
        }
        this.s.a(this, view, position, id);
        return true;
    }

    public void setOnItemLongClickListener(d listener) {
        if (!isLongClickable()) {
            setLongClickable(true);
        }
        this.t = listener;
    }

    public final d getOnItemLongClickListener() {
        return this.t;
    }

    public void setOnItemSelectedListener(e listener) {
        this.r = listener;
    }

    public final e getOnItemSelectedListener() {
        return this.r;
    }

    public void addView(View child) {
        throw new UnsupportedOperationException("addView(View) is not supported in AdapterView");
    }

    public void addView(View child, int index) {
        throw new UnsupportedOperationException("addView(View, int) is not supported in AdapterView");
    }

    public void addView(View child, LayoutParams params) {
        throw new UnsupportedOperationException("addView(View, LayoutParams) is not supported in AdapterView");
    }

    public void addView(View child, int index, LayoutParams params) {
        throw new UnsupportedOperationException("addView(View, int, LayoutParams) is not supported in AdapterView");
    }

    public void removeView(View child) {
        throw new UnsupportedOperationException("removeView(View) is not supported in AdapterView");
    }

    public void removeViewAt(int index) {
        throw new UnsupportedOperationException("removeViewAt(int) is not supported in AdapterView");
    }

    public void removeAllViews() {
        throw new UnsupportedOperationException("removeAllViews() is not supported in AdapterView");
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.a = getHeight();
    }

    @CapturedViewProperty
    public int getSelectedItemPosition() {
        return this.v;
    }

    @CapturedViewProperty
    public long getSelectedItemId() {
        return this.w;
    }

    public Object getSelectedItem() {
        T adapter = getAdapter();
        int selection = getSelectedItemPosition();
        if (adapter == null || adapter.getCount() <= 0 || selection < 0) {
            return null;
        }
        return adapter.getItem(selection);
    }

    @CapturedViewProperty
    public int getCount() {
        return this.z;
    }

    public int c(View view) {
        View listItem = view;
        while (true) {
            try {
                View v = (View) listItem.getParent();
                if (v.equals(this)) {
                    break;
                }
                listItem = v;
            } catch (ClassCastException e) {
                return -1;
            }
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).equals(listItem)) {
                return this.j + i;
            }
        }
        return -1;
    }

    public int getFirstVisiblePosition() {
        return this.j;
    }

    public int getLastVisiblePosition() {
        return (this.j + getChildCount()) - 1;
    }

    public void setEmptyView(View emptyView) {
        boolean empty = true;
        this.b = emptyView;
        if (emptyView != null && emptyView.getImportantForAccessibility() == 0) {
            emptyView.setImportantForAccessibility(1);
        }
        T adapter = getAdapter();
        if (!(adapter == null || adapter.isEmpty())) {
            empty = false;
        }
        a(empty);
    }

    public View getEmptyView() {
        return this.b;
    }

    boolean c() {
        return false;
    }

    public void setFocusable(boolean focusable) {
        boolean z = true;
        T adapter = getAdapter();
        boolean empty;
        if (adapter == null || adapter.getCount() == 0) {
            empty = true;
        } else {
            empty = false;
        }
        this.c = focusable;
        if (!focusable) {
            this.d = false;
        }
        if (!focusable || (empty && !c())) {
            z = false;
        }
        super.setFocusable(z);
    }

    public void setFocusableInTouchMode(boolean focusable) {
        boolean z = true;
        T adapter = getAdapter();
        boolean empty;
        if (adapter == null || adapter.getCount() == 0) {
            empty = true;
        } else {
            empty = false;
        }
        this.d = focusable;
        if (focusable) {
            this.c = true;
        }
        if (!focusable || (empty && !c())) {
            z = false;
        }
        super.setFocusableInTouchMode(z);
    }

    void d() {
        boolean empty;
        boolean focusable;
        boolean z;
        boolean z2 = false;
        T adapter = getAdapter();
        if (adapter == null || adapter.getCount() == 0) {
            empty = true;
        } else {
            empty = false;
        }
        if (!empty || c()) {
            focusable = true;
        } else {
            focusable = false;
        }
        if (focusable && this.d) {
            z = true;
        } else {
            z = false;
        }
        super.setFocusableInTouchMode(z);
        if (focusable && this.c) {
            z = true;
        } else {
            z = false;
        }
        super.setFocusable(z);
        if (this.b != null) {
            if (adapter == null || adapter.isEmpty()) {
                z2 = true;
            }
            a(z2);
        }
    }

    private void a(boolean empty) {
        if (c()) {
            empty = false;
        }
        if (empty) {
            if (this.b != null) {
                this.b.setVisibility(0);
                setVisibility(8);
            } else {
                setVisibility(0);
            }
            if (this.u) {
                onLayout(false, getLeft(), getTop(), getRight(), getBottom());
                return;
            }
            return;
        }
        if (this.b != null) {
            this.b.setVisibility(8);
        }
        setVisibility(0);
    }

    public long a(int position) {
        T adapter = getAdapter();
        return (adapter == null || position < 0) ? Long.MIN_VALUE : adapter.getItemId(position);
    }

    public void setOnClickListener(OnClickListener l) {
        throw new RuntimeException("Don't call setOnClickListener for an AdapterView. You probably want setOnItemClickListener instead");
    }

    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.e);
    }

    void e() {
        if (this.r == null) {
            return;
        }
        if (this.q || this.D) {
            if (this.e == null) {
                this.e = new f();
            }
            post(this.e);
            return;
        }
        a();
        b();
    }

    private void a() {
        if (this.r != null) {
            int selection = getSelectedItemPosition();
            if (selection >= 0) {
                View v = getSelectedView();
                this.r.a(this, v, selection, getAdapter().getItemId(selection));
                return;
            }
            this.r.a(this);
        }
    }

    private void b() {
        if (getSelectedItemPosition() >= 0) {
            sendAccessibilityEvent(4);
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        View selectedView = getSelectedView();
        if (selectedView != null && selectedView.getVisibility() == 0 && selectedView.dispatchPopulateAccessibilityEvent(event)) {
            return true;
        }
        return false;
    }

    public boolean onRequestSendAccessibilityEvent(View child, AccessibilityEvent event) {
        if (!super.onRequestSendAccessibilityEvent(child, event)) {
            return false;
        }
        AccessibilityEvent record = AccessibilityEvent.obtain();
        onInitializeAccessibilityEvent(record);
        child.dispatchPopulateAccessibilityEvent(record);
        event.appendRecord(record);
        return true;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(AdapterView.class.getName());
        info.setScrollable(j());
        View selectedView = getSelectedView();
        if (selectedView != null) {
            info.setEnabled(selectedView.isEnabled());
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(AdapterView.class.getName());
        event.setScrollable(j());
        View selectedView = getSelectedView();
        if (selectedView != null) {
            event.setEnabled(selectedView.isEnabled());
        }
        event.setCurrentItemIndex(getSelectedItemPosition());
        event.setFromIndex(getFirstVisiblePosition());
        event.setToIndex(getLastVisiblePosition());
        event.setItemCount(getCount());
    }

    private boolean j() {
        T adapter = getAdapter();
        if (adapter == null) {
            return false;
        }
        int itemCount = adapter.getCount();
        if (itemCount <= 0) {
            return false;
        }
        if (getFirstVisiblePosition() > 0 || getLastVisiblePosition() < itemCount - 1) {
            return true;
        }
        return false;
    }

    protected boolean canAnimate() {
        return super.canAnimate() && this.z > 0;
    }

    void f() {
        int count = this.z;
        boolean found = false;
        if (count > 0) {
            int newPos;
            if (this.o) {
                this.o = false;
                newPos = h();
                if (newPos >= 0 && c(newPos, true) == newPos) {
                    setNextSelectedPositionInt(newPos);
                    found = true;
                }
            }
            if (!found) {
                newPos = getSelectedItemPosition();
                if (newPos >= count) {
                    newPos = count - 1;
                }
                if (newPos < 0) {
                    newPos = 0;
                }
                int selectablePos = c(newPos, true);
                if (selectablePos < 0) {
                    selectablePos = c(newPos, false);
                }
                if (selectablePos >= 0) {
                    setNextSelectedPositionInt(selectablePos);
                    g();
                    found = true;
                }
            }
        }
        if (!found) {
            this.x = -1;
            this.y = Long.MIN_VALUE;
            this.v = -1;
            this.w = Long.MIN_VALUE;
            this.o = false;
            g();
        }
    }

    void g() {
        if (this.x != this.B || this.y != this.C) {
            e();
            this.B = this.x;
            this.C = this.y;
        }
    }

    int h() {
        int count = this.z;
        if (count == 0) {
            return -1;
        }
        long idToMatch = this.m;
        int seed = this.l;
        if (idToMatch == Long.MIN_VALUE) {
            return -1;
        }
        seed = Math.min(count - 1, Math.max(0, seed));
        long endTime = SystemClock.uptimeMillis() + 100;
        int first = seed;
        int last = seed;
        boolean next = false;
        T adapter = getAdapter();
        if (adapter == null) {
            return -1;
        }
        while (SystemClock.uptimeMillis() <= endTime) {
            if (adapter.getItemId(seed) != idToMatch) {
                boolean hitLast = last == count + -1;
                boolean hitFirst = first == 0;
                if (hitLast && hitFirst) {
                    break;
                } else if (hitFirst || (next && !hitLast)) {
                    last++;
                    seed = last;
                    next = false;
                } else if (hitLast || !(next || hitFirst)) {
                    first--;
                    seed = first;
                    next = true;
                }
            } else {
                return seed;
            }
        }
        return -1;
    }

    int c(int position, boolean lookDown) {
        return position;
    }

    void setSelectedPositionInt(int position) {
        this.x = position;
        this.y = a(position);
    }

    void setNextSelectedPositionInt(int position) {
        this.v = position;
        this.w = a(position);
        if (this.o && this.p == 0 && position >= 0) {
            this.l = position;
            this.m = this.w;
        }
    }

    void i() {
        if (getChildCount() > 0) {
            this.o = true;
            this.n = (long) this.a;
            View v;
            if (this.x >= 0) {
                v = getChildAt(this.x - this.j);
                this.m = this.w;
                this.l = this.v;
                if (v != null) {
                    this.k = v.getTop();
                }
                this.p = 0;
                return;
            }
            v = getChildAt(0);
            T adapter = getAdapter();
            if (this.j < 0 || this.j >= adapter.getCount()) {
                this.m = -1;
            } else {
                this.m = adapter.getItemId(this.j);
            }
            this.l = this.j;
            if (v != null) {
                this.k = v.getTop();
            }
            this.p = 1;
        }
    }
}
