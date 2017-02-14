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
    SpinnerAdapter mAdapter;
    private DataSetObserver mDataSetObserver;
    int mHeightMeasureSpec;
    final RecycleBin mRecycler;
    int mSelectionBottomPadding;
    int mSelectionLeftPadding;
    int mSelectionRightPadding;
    int mSelectionTopPadding;
    final Rect mSpinnerPadding;
    private Rect mTouchFrame;
    int mWidthMeasureSpec;

    class RecycleBin {
        private final SparseArray<View> mScrapHeap = new SparseArray();

        RecycleBin() {
        }

        public void put(int i, View view) {
            this.mScrapHeap.put(i, view);
        }

        View get(int i) {
            View view = (View) this.mScrapHeap.get(i);
            if (view != null) {
                this.mScrapHeap.delete(i);
            }
            return view;
        }

        void clear() {
            SparseArray sparseArray = this.mScrapHeap;
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                View view = (View) sparseArray.valueAt(i);
                if (view != null) {
                    AbsSpinner.this.removeDetachedView(view, true);
                }
            }
            sparseArray.clear();
        }
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int position;
        long selectedId;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.selectedId = parcel.readLong();
            this.position = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeLong(this.selectedId);
            parcel.writeInt(this.position);
        }

        public String toString() {
            return "AbsSpinner.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.selectedId + " position=" + this.position + "}";
        }
    }

    abstract void layout(int i, boolean z);

    public AbsSpinner(Context context) {
        super(context);
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mSpinnerPadding = new Rect();
        this.mRecycler = new RecycleBin();
        initAbsSpinner();
    }

    public AbsSpinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AbsSpinner(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mSpinnerPadding = new Rect();
        this.mRecycler = new RecycleBin();
        initAbsSpinner();
    }

    private void initAbsSpinner() {
        setFocusable(true);
        setWillNotDraw(false);
    }

    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        int i = -1;
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
            resetList();
        }
        this.mAdapter = spinnerAdapter;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        if (this.mAdapter != null) {
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            checkFocus();
            this.mDataSetObserver = new AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            if (this.mItemCount > 0) {
                i = 0;
            }
            setSelectedPositionInt(i);
            setNextSelectedPositionInt(i);
            if (this.mItemCount == 0) {
                checkSelectionChanged();
            }
        } else {
            checkFocus();
            resetList();
            checkSelectionChanged();
        }
        requestLayout();
    }

    void resetList() {
        this.mDataChanged = false;
        this.mNeedSync = false;
        removeAllViewsInLayout();
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        setSelectedPositionInt(-1);
        setNextSelectedPositionInt(-1);
        invalidate();
    }

    protected void onMeasure(int i, int i2) {
        int childHeight;
        int childWidth;
        boolean z;
        int mode = MeasureSpec.getMode(i);
        this.mSpinnerPadding.left = getPaddingLeft() > this.mSelectionLeftPadding ? getPaddingLeft() : this.mSelectionLeftPadding;
        this.mSpinnerPadding.top = getPaddingTop() > this.mSelectionTopPadding ? getPaddingTop() : this.mSelectionTopPadding;
        this.mSpinnerPadding.right = getPaddingRight() > this.mSelectionRightPadding ? getPaddingRight() : this.mSelectionRightPadding;
        this.mSpinnerPadding.bottom = getPaddingBottom() > this.mSelectionBottomPadding ? getPaddingBottom() : this.mSelectionBottomPadding;
        if (this.mDataChanged) {
            handleDataChanged();
        }
        int selectedItemPosition = getSelectedItemPosition();
        if (selectedItemPosition >= 0 && this.mAdapter != null && selectedItemPosition < this.mAdapter.getCount()) {
            View view = this.mRecycler.get(selectedItemPosition);
            if (view == null) {
                view = this.mAdapter.getView(selectedItemPosition, null, this);
                if (view.getImportantForAccessibility() == 0) {
                    view.setImportantForAccessibility(1);
                }
            }
            if (view != null) {
                this.mRecycler.put(selectedItemPosition, view);
            }
            if (view != null) {
                if (view.getLayoutParams() == null) {
                    this.mBlockLayoutRequests = true;
                    view.setLayoutParams(generateDefaultLayoutParams());
                    this.mBlockLayoutRequests = false;
                }
                measureChild(view, i, i2);
                childHeight = (getChildHeight(view) + this.mSpinnerPadding.top) + this.mSpinnerPadding.bottom;
                childWidth = (getChildWidth(view) + this.mSpinnerPadding.left) + this.mSpinnerPadding.right;
                z = false;
                if (z) {
                    childHeight = this.mSpinnerPadding.top + this.mSpinnerPadding.bottom;
                    if (mode == 0) {
                        childWidth = this.mSpinnerPadding.left + this.mSpinnerPadding.right;
                    }
                }
                setMeasuredDimension(resolveSizeAndState(Math.max(childWidth, getSuggestedMinimumWidth()), i, 0), resolveSizeAndState(Math.max(childHeight, getSuggestedMinimumHeight()), i2, 0));
                this.mHeightMeasureSpec = i2;
                this.mWidthMeasureSpec = i;
            }
        }
        z = true;
        childWidth = 0;
        childHeight = 0;
        if (z) {
            childHeight = this.mSpinnerPadding.top + this.mSpinnerPadding.bottom;
            if (mode == 0) {
                childWidth = this.mSpinnerPadding.left + this.mSpinnerPadding.right;
            }
        }
        setMeasuredDimension(resolveSizeAndState(Math.max(childWidth, getSuggestedMinimumWidth()), i, 0), resolveSizeAndState(Math.max(childHeight, getSuggestedMinimumHeight()), i2, 0));
        this.mHeightMeasureSpec = i2;
        this.mWidthMeasureSpec = i;
    }

    int getChildHeight(View view) {
        return view.getMeasuredHeight();
    }

    int getChildWidth(View view) {
        return view.getMeasuredWidth();
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    void recycleAllViews() {
        int childCount = getChildCount();
        RecycleBin recycleBin = this.mRecycler;
        int i = this.mFirstPosition;
        for (int i2 = 0; i2 < childCount; i2++) {
            recycleBin.put(i + i2, getChildAt(i2));
        }
    }

    public void setSelection(int i, boolean z) {
        boolean z2 = z && this.mFirstPosition <= i && i <= (this.mFirstPosition + getChildCount()) - 1;
        setSelectionInt(i, z2);
    }

    public void setSelection(int i) {
        setNextSelectedPositionInt(i);
        requestLayout();
        invalidate();
    }

    void setSelectionInt(int i, boolean z) {
        if (i != this.mOldSelectedPosition) {
            this.mBlockLayoutRequests = true;
            int i2 = i - this.mSelectedPosition;
            setNextSelectedPositionInt(i);
            layout(i2, z);
            this.mBlockLayoutRequests = false;
        }
    }

    public View getSelectedView() {
        if (this.mItemCount <= 0 || this.mSelectedPosition < 0) {
            return null;
        }
        return getChildAt(this.mSelectedPosition - this.mFirstPosition);
    }

    public void requestLayout() {
        if (!this.mBlockLayoutRequests) {
            super.requestLayout();
        }
    }

    public SpinnerAdapter getAdapter() {
        return this.mAdapter;
    }

    public int getCount() {
        return this.mItemCount;
    }

    public int pointToPosition(int i, int i2) {
        Rect rect = this.mTouchFrame;
        if (rect == null) {
            this.mTouchFrame = new Rect();
            rect = this.mTouchFrame;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt.getVisibility() == 0) {
                childAt.getHitRect(rect);
                if (rect.contains(i, i2)) {
                    return this.mFirstPosition + childCount;
                }
            }
        }
        return -1;
    }

    public Parcelable onSaveInstanceState() {
        Object savedState = new SavedState(super.onSaveInstanceState());
        savedState.selectedId = getSelectedItemId();
        if (savedState.selectedId >= 0) {
            savedState.position = getSelectedItemPosition();
        } else {
            savedState.position = -1;
        }
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.selectedId >= 0) {
            this.mDataChanged = true;
            this.mNeedSync = true;
            this.mSyncRowId = savedState.selectedId;
            this.mSyncPosition = savedState.position;
            this.mSyncMode = 0;
            requestLayout();
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(AbsSpinner.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(AbsSpinner.class.getName());
    }
}
