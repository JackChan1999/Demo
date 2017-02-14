package com.meizu.common.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.SpinnerAdapter;

public abstract class AbsSpinner extends AdapterView<SpinnerAdapter> {
    private DataSetObserver E;
    private Rect F;
    SpinnerAdapter a;
    int b;
    int c;
    int d;
    int e;
    int f;
    int g;
    final Rect h;
    final a i;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public /* synthetic */ Object createFromParcel(Parcel x0) {
                return a(x0);
            }

            public /* synthetic */ Object[] newArray(int x0) {
                return a(x0);
            }

            public SavedState a(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] a(int size) {
                return new SavedState[size];
            }
        };
        long a;
        int b;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.a = in.readLong();
            this.b = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeLong(this.a);
            out.writeInt(this.b);
        }

        public String toString() {
            return "AbsSpinner.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.a + " position=" + this.b + "}";
        }
    }

    class a {
        final /* synthetic */ AbsSpinner a;
        private final SparseArray<View> b = new SparseArray();

        a(AbsSpinner absSpinner) {
            this.a = absSpinner;
        }

        public void a(int position, View v) {
            this.b.put(position, v);
        }

        View a(int position) {
            View result = (View) this.b.get(position);
            if (result != null) {
                this.b.delete(position);
            }
            return result;
        }

        void a() {
            SparseArray<View> scrapHeap = this.b;
            int count = scrapHeap.size();
            for (int i = 0; i < count; i++) {
                View view = (View) scrapHeap.valueAt(i);
                if (view != null) {
                    this.a.removeDetachedView(view, true);
                }
            }
            scrapHeap.clear();
        }
    }

    abstract void b(int i, boolean z);

    public AbsSpinner(Context context) {
        super(context);
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.h = new Rect();
        this.i = new a(this);
        j();
    }

    public AbsSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.h = new Rect();
        this.i = new a(this);
        j();
    }

    private void j() {
        setFocusable(true);
        setWillNotDraw(false);
    }

    public void setAdapter(SpinnerAdapter adapter) {
        int position = -1;
        if (this.a != null) {
            this.a.unregisterDataSetObserver(this.E);
            a();
        }
        this.a = adapter;
        this.B = -1;
        this.C = Long.MIN_VALUE;
        if (this.a != null) {
            this.A = this.z;
            this.z = this.a.getCount();
            d();
            this.E = new b(this);
            this.a.registerDataSetObserver(this.E);
            if (this.z > 0) {
                position = 0;
            }
            setSelectedPositionInt(position);
            setNextSelectedPositionInt(position);
            if (this.z == 0) {
                g();
            }
        } else {
            d();
            a();
            g();
        }
        requestLayout();
    }

    void a() {
        this.u = false;
        this.o = false;
        removeAllViewsInLayout();
        this.B = -1;
        this.C = Long.MIN_VALUE;
        setSelectedPositionInt(-1);
        setNextSelectedPositionInt(-1);
        invalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        this.h.left = getPaddingLeft() > this.d ? getPaddingLeft() : this.d;
        this.h.top = getPaddingTop() > this.e ? getPaddingTop() : this.e;
        this.h.right = getPaddingRight() > this.f ? getPaddingRight() : this.f;
        this.h.bottom = getPaddingBottom() > this.g ? getPaddingBottom() : this.g;
        if (this.u) {
            f();
        }
        int preferredHeight = 0;
        int preferredWidth = 0;
        boolean needsMeasuring = true;
        int selectedPosition = getSelectedItemPosition();
        if (selectedPosition >= 0 && this.a != null && selectedPosition < this.a.getCount()) {
            View view = this.i.a(selectedPosition);
            if (view == null) {
                view = this.a.getView(selectedPosition, null, this);
                if (view.getImportantForAccessibility() == 0) {
                    view.setImportantForAccessibility(1);
                }
            }
            if (view != null) {
                this.i.a(selectedPosition, view);
            }
            if (view != null) {
                if (view.getLayoutParams() == null) {
                    this.D = true;
                    view.setLayoutParams(generateDefaultLayoutParams());
                    this.D = false;
                }
                measureChild(view, widthMeasureSpec, heightMeasureSpec);
                preferredHeight = (a(view) + this.h.top) + this.h.bottom;
                preferredWidth = (b(view) + this.h.left) + this.h.right;
                needsMeasuring = false;
            }
        }
        if (needsMeasuring) {
            preferredHeight = this.h.top + this.h.bottom;
            if (widthMode == 0) {
                preferredWidth = this.h.left + this.h.right;
            }
        }
        setMeasuredDimension(resolveSizeAndState(Math.max(preferredWidth, getSuggestedMinimumWidth()), widthMeasureSpec, 0), resolveSizeAndState(Math.max(preferredHeight, getSuggestedMinimumHeight()), heightMeasureSpec, 0));
        this.b = heightMeasureSpec;
        this.c = widthMeasureSpec;
    }

    int a(View child) {
        return child.getMeasuredHeight();
    }

    int b(View child) {
        return child.getMeasuredWidth();
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    void b() {
        int childCount = getChildCount();
        a recycleBin = this.i;
        int position = this.j;
        for (int i = 0; i < childCount; i++) {
            recycleBin.a(position + i, getChildAt(i));
        }
    }

    public void setSelection(int position, boolean animate) {
        boolean shouldAnimate = animate && this.j <= position && position <= (this.j + getChildCount()) - 1;
        a(position, shouldAnimate);
    }

    public void setSelection(int position) {
        setNextSelectedPositionInt(position);
        requestLayout();
        invalidate();
    }

    void a(int position, boolean animate) {
        if (position != this.B) {
            this.D = true;
            int delta = position - this.x;
            setNextSelectedPositionInt(position);
            b(delta, animate);
            this.D = false;
        }
    }

    public View getSelectedView() {
        if (this.z <= 0 || this.x < 0) {
            return null;
        }
        return getChildAt(this.x - this.j);
    }

    public void requestLayout() {
        if (!this.D) {
            super.requestLayout();
        }
    }

    public SpinnerAdapter getAdapter() {
        return this.a;
    }

    public int getCount() {
        return this.z;
    }

    public int a(int x, int y) {
        Rect frame = this.F;
        if (frame == null) {
            this.F = new Rect();
            frame = this.F;
        }
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return this.j + i;
                }
            }
        }
        return -1;
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.a = getSelectedItemId();
        if (ss.a >= 0) {
            ss.b = getSelectedItemPosition();
        } else {
            ss.b = -1;
        }
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        if (ss.a >= 0) {
            this.u = true;
            this.o = true;
            this.m = ss.a;
            this.l = ss.b;
            this.p = 0;
            requestLayout();
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(AbsSpinner.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(AbsSpinner.class.getName());
    }
}
