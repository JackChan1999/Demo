package com.meizu.common.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View.BaseSavedState;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CustomPicker extends FrameLayout {
    private int[] a;
    private ScrollTextView[] b;
    private int c;
    private int d;
    private int e;
    private TextView[] f;
    private a g;

    private static class SavedState extends BaseSavedState {
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
        private final int[] a;

        private SavedState(Parcelable superState, int... currentItems) {
            super(superState);
            this.a = (int[]) currentItems.clone();
        }

        private SavedState(Parcel in) {
            super(in);
            this.a = in.createIntArray();
        }

        public int[] a() {
            return this.a;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeIntArray(this.a);
        }
    }

    public interface a {
    }

    public CustomPicker(Context context) {
        this(context, null);
    }

    public CustomPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.b = new ScrollTextView[3];
        this.d = 0;
        this.e = -1;
    }

    public void a(int... currentItems) {
        if (currentItems != null) {
            int i = 0;
            while (i < currentItems.length && i < this.c) {
                this.a[i] = currentItems[i];
                this.b[i].b(currentItems[i]);
                i++;
            }
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (int i = 0; i < this.c; i++) {
            this.b[i].setEnabled(enabled);
        }
    }

    public void setOnCurrentItemChangedListener(a onItemChangedListener) {
        this.g = onItemChangedListener;
    }

    public int[] getCurrentItems() {
        return this.a;
    }

    public void setCurrentItem(int columnIndex, int currentItem) {
        if (columnIndex >= 0 && columnIndex < this.c) {
            this.a[columnIndex] = currentItem;
            a(this.a);
        }
    }

    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this.a);
    }

    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        a(ss.a());
    }

    public void setTextColor(int selectedColor, int normalColor, int unitColor) {
        for (int i = 0; i < this.c; i++) {
            this.b[i].setTextColor(selectedColor, normalColor);
            this.f[i].setTextColor(unitColor);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(CustomPicker.class.getName());
    }
}
