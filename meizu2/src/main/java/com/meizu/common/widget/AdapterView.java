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
import android.view.accessibility.AccessibilityRecord;
import android.widget.Adapter;

public abstract class AdapterView<T extends Adapter> extends ViewGroup {
    public static final int INVALID_POSITION = -1;
    public static final long INVALID_ROW_ID = Long.MIN_VALUE;
    public static final int ITEM_VIEW_TYPE_HEADER_OR_FOOTER = -2;
    public static final int ITEM_VIEW_TYPE_IGNORE = -1;
    static final int SYNC_FIRST_POSITION = 1;
    static final int SYNC_MAX_DURATION_MILLIS = 100;
    static final int SYNC_SELECTED_POSITION = 0;
    boolean mBlockLayoutRequests = false;
    private Context mContext;
    boolean mDataChanged;
    private boolean mDesiredFocusableInTouchModeState;
    private boolean mDesiredFocusableState;
    private View mEmptyView;
    @ExportedProperty(category = "scrolling")
    int mFirstPosition = 0;
    boolean mInLayout = false;
    @ExportedProperty(category = "list")
    int mItemCount;
    private int mLayoutHeight;
    boolean mNeedSync = false;
    @ExportedProperty(category = "list")
    int mNextSelectedPosition = -1;
    long mNextSelectedRowId = Long.MIN_VALUE;
    int mOldItemCount;
    int mOldSelectedPosition = -1;
    long mOldSelectedRowId = Long.MIN_VALUE;
    OnItemClickListener mOnItemClickListener;
    OnItemLongClickListener mOnItemLongClickListener;
    OnItemSelectedListener mOnItemSelectedListener;
    @ExportedProperty(category = "list")
    int mSelectedPosition = -1;
    long mSelectedRowId = Long.MIN_VALUE;
    private SelectionNotifier mSelectionNotifier;
    int mSpecificTop;
    long mSyncHeight;
    int mSyncMode;
    int mSyncPosition;
    long mSyncRowId = Long.MIN_VALUE;

    public static class AdapterContextMenuInfo implements ContextMenuInfo {
        public long id;
        public int position;
        public View targetView;

        public AdapterContextMenuInfo(View view, int i, long j) {
            this.targetView = view;
            this.position = i;
            this.id = j;
        }
    }

    class AdapterDataSetObserver extends DataSetObserver {
        private Parcelable mInstanceState = null;

        AdapterDataSetObserver() {
        }

        public void onChanged() {
            AdapterView.this.mDataChanged = true;
            AdapterView.this.mOldItemCount = AdapterView.this.mItemCount;
            AdapterView.this.mItemCount = AdapterView.this.getAdapter().getCount();
            if (!AdapterView.this.getAdapter().hasStableIds() || this.mInstanceState == null || AdapterView.this.mOldItemCount != 0 || AdapterView.this.mItemCount <= 0) {
                AdapterView.this.rememberSyncState();
            } else {
                AdapterView.this.onRestoreInstanceState(this.mInstanceState);
                this.mInstanceState = null;
            }
            AdapterView.this.checkFocus();
            AdapterView.this.requestLayout();
        }

        public void onInvalidated() {
            AdapterView.this.mDataChanged = true;
            if (AdapterView.this.getAdapter().hasStableIds()) {
                this.mInstanceState = AdapterView.this.onSaveInstanceState();
            }
            AdapterView.this.mOldItemCount = AdapterView.this.mItemCount;
            AdapterView.this.mItemCount = 0;
            AdapterView.this.mSelectedPosition = -1;
            AdapterView.this.mSelectedRowId = Long.MIN_VALUE;
            AdapterView.this.mNextSelectedPosition = -1;
            AdapterView.this.mNextSelectedRowId = Long.MIN_VALUE;
            AdapterView.this.mNeedSync = false;
            AdapterView.this.checkFocus();
            AdapterView.this.requestLayout();
        }

        public void clearSavedState() {
            this.mInstanceState = null;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(AdapterView<?> adapterView, View view, int i, long j);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(AdapterView<?> adapterView, View view, int i, long j);

        void onNothingSelected(AdapterView<?> adapterView);
    }

    class SelectionNotifier implements Runnable {
        private SelectionNotifier() {
        }

        public void run() {
            if (!AdapterView.this.mDataChanged) {
                AdapterView.this.fireOnSelected();
                AdapterView.this.performAccessibilityActionsOnSelected();
            } else if (AdapterView.this.getAdapter() != null) {
                AdapterView.this.post(this);
            }
        }
    }

    public abstract T getAdapter();

    public abstract View getSelectedView();

    public abstract void setAdapter(T t);

    public abstract void setSelection(int i);

    public AdapterView(Context context) {
        super(context);
        this.mContext = context;
    }

    public AdapterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    public AdapterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public final OnItemClickListener getOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    public boolean performItemClick(View view, int i, long j) {
        if (this.mOnItemClickListener == null) {
            return false;
        }
        playSoundEffect(0);
        if (view != null) {
            view.sendAccessibilityEvent(1);
        }
        this.mOnItemClickListener.onItemClick(this, view, i, j);
        return true;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        if (!isLongClickable()) {
            setLongClickable(true);
        }
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public final OnItemLongClickListener getOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }

    public final OnItemSelectedListener getOnItemSelectedListener() {
        return this.mOnItemSelectedListener;
    }

    public void addView(View view) {
        throw new UnsupportedOperationException("addView(View) is not supported in AdapterView");
    }

    public void addView(View view, int i) {
        throw new UnsupportedOperationException("addView(View, int) is not supported in AdapterView");
    }

    public void addView(View view, LayoutParams layoutParams) {
        throw new UnsupportedOperationException("addView(View, LayoutParams) is not supported in AdapterView");
    }

    public void addView(View view, int i, LayoutParams layoutParams) {
        throw new UnsupportedOperationException("addView(View, int, LayoutParams) is not supported in AdapterView");
    }

    public void removeView(View view) {
        throw new UnsupportedOperationException("removeView(View) is not supported in AdapterView");
    }

    public void removeViewAt(int i) {
        throw new UnsupportedOperationException("removeViewAt(int) is not supported in AdapterView");
    }

    public void removeAllViews() {
        throw new UnsupportedOperationException("removeAllViews() is not supported in AdapterView");
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mLayoutHeight = getHeight();
    }

    @CapturedViewProperty
    public int getSelectedItemPosition() {
        return this.mNextSelectedPosition;
    }

    @CapturedViewProperty
    public long getSelectedItemId() {
        return this.mNextSelectedRowId;
    }

    public Object getSelectedItem() {
        Adapter adapter = getAdapter();
        int selectedItemPosition = getSelectedItemPosition();
        if (adapter == null || adapter.getCount() <= 0 || selectedItemPosition < 0) {
            return null;
        }
        return adapter.getItem(selectedItemPosition);
    }

    @CapturedViewProperty
    public int getCount() {
        return this.mItemCount;
    }

    public int getPositionForView(View view) {
        while (true) {
            try {
                View view2 = (View) view.getParent();
                if (view2.equals(this)) {
                    break;
                }
                view = view2;
            } catch (ClassCastException e) {
                return -1;
            }
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).equals(view)) {
                return i + this.mFirstPosition;
            }
        }
        return -1;
    }

    public int getFirstVisiblePosition() {
        return this.mFirstPosition;
    }

    public int getLastVisiblePosition() {
        return (this.mFirstPosition + getChildCount()) - 1;
    }

    public void setEmptyView(View view) {
        boolean z = true;
        this.mEmptyView = view;
        if (view != null && view.getImportantForAccessibility() == 0) {
            view.setImportantForAccessibility(1);
        }
        Adapter adapter = getAdapter();
        if (!(adapter == null || adapter.isEmpty())) {
            z = false;
        }
        updateEmptyStatus(z);
    }

    public View getEmptyView() {
        return this.mEmptyView;
    }

    boolean isInFilterMode() {
        return false;
    }

    public void setFocusable(boolean z) {
        boolean z2 = true;
        Adapter adapter = getAdapter();
        boolean z3 = adapter == null || adapter.getCount() == 0;
        this.mDesiredFocusableState = z;
        if (!z) {
            this.mDesiredFocusableInTouchModeState = false;
        }
        if (!z || (z3 && !isInFilterMode())) {
            z2 = false;
        }
        super.setFocusable(z2);
    }

    public void setFocusableInTouchMode(boolean z) {
        boolean z2 = true;
        Adapter adapter = getAdapter();
        boolean z3 = adapter == null || adapter.getCount() == 0;
        this.mDesiredFocusableInTouchModeState = z;
        if (z) {
            this.mDesiredFocusableState = true;
        }
        if (!z || (z3 && !isInFilterMode())) {
            z2 = false;
        }
        super.setFocusableInTouchMode(z2);
    }

    void checkFocus() {
        boolean z;
        boolean z2 = false;
        Adapter adapter = getAdapter();
        boolean z3 = adapter == null || adapter.getCount() == 0;
        if (!z3 || isInFilterMode()) {
            z = true;
        } else {
            z = false;
        }
        if (z && this.mDesiredFocusableInTouchModeState) {
            z3 = true;
        } else {
            z3 = false;
        }
        super.setFocusableInTouchMode(z3);
        if (z && this.mDesiredFocusableState) {
            z3 = true;
        } else {
            z3 = false;
        }
        super.setFocusable(z3);
        if (this.mEmptyView != null) {
            if (adapter == null || adapter.isEmpty()) {
                z2 = true;
            }
            updateEmptyStatus(z2);
        }
    }

    private void updateEmptyStatus(boolean z) {
        if (isInFilterMode()) {
            z = false;
        }
        if (z) {
            if (this.mEmptyView != null) {
                this.mEmptyView.setVisibility(0);
                setVisibility(8);
            } else {
                setVisibility(0);
            }
            if (this.mDataChanged) {
                onLayout(false, getLeft(), getTop(), getRight(), getBottom());
                return;
            }
            return;
        }
        if (this.mEmptyView != null) {
            this.mEmptyView.setVisibility(8);
        }
        setVisibility(0);
    }

    public Object getItemAtPosition(int i) {
        Adapter adapter = getAdapter();
        return (adapter == null || i < 0) ? null : adapter.getItem(i);
    }

    public long getItemIdAtPosition(int i) {
        Adapter adapter = getAdapter();
        return (adapter == null || i < 0) ? Long.MIN_VALUE : adapter.getItemId(i);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        throw new RuntimeException("Don't call setOnClickListener for an AdapterView. You probably want setOnItemClickListener instead");
    }

    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mSelectionNotifier);
    }

    void selectionChanged() {
        if (this.mOnItemSelectedListener == null) {
            return;
        }
        if (this.mInLayout || this.mBlockLayoutRequests) {
            if (this.mSelectionNotifier == null) {
                this.mSelectionNotifier = new SelectionNotifier();
            }
            post(this.mSelectionNotifier);
            return;
        }
        fireOnSelected();
        performAccessibilityActionsOnSelected();
    }

    private void fireOnSelected() {
        if (this.mOnItemSelectedListener != null) {
            int selectedItemPosition = getSelectedItemPosition();
            if (selectedItemPosition >= 0) {
                View selectedView = getSelectedView();
                this.mOnItemSelectedListener.onItemSelected(this, selectedView, selectedItemPosition, getAdapter().getItemId(selectedItemPosition));
                return;
            }
            this.mOnItemSelectedListener.onNothingSelected(this);
        }
    }

    private void performAccessibilityActionsOnSelected() {
        if (getSelectedItemPosition() >= 0) {
            sendAccessibilityEvent(4);
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        View selectedView = getSelectedView();
        if (selectedView != null && selectedView.getVisibility() == 0 && selectedView.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
            return true;
        }
        return false;
    }

    public boolean onRequestSendAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        if (!super.onRequestSendAccessibilityEvent(view, accessibilityEvent)) {
            return false;
        }
        AccessibilityRecord obtain = AccessibilityEvent.obtain();
        onInitializeAccessibilityEvent(obtain);
        view.dispatchPopulateAccessibilityEvent(obtain);
        accessibilityEvent.appendRecord(obtain);
        return true;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(AdapterView.class.getName());
        accessibilityNodeInfo.setScrollable(isScrollableForAccessibility());
        View selectedView = getSelectedView();
        if (selectedView != null) {
            accessibilityNodeInfo.setEnabled(selectedView.isEnabled());
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(AdapterView.class.getName());
        accessibilityEvent.setScrollable(isScrollableForAccessibility());
        View selectedView = getSelectedView();
        if (selectedView != null) {
            accessibilityEvent.setEnabled(selectedView.isEnabled());
        }
        accessibilityEvent.setCurrentItemIndex(getSelectedItemPosition());
        accessibilityEvent.setFromIndex(getFirstVisiblePosition());
        accessibilityEvent.setToIndex(getLastVisiblePosition());
        accessibilityEvent.setItemCount(getCount());
    }

    private boolean isScrollableForAccessibility() {
        Adapter adapter = getAdapter();
        if (adapter == null) {
            return false;
        }
        int count = adapter.getCount();
        if (count <= 0) {
            return false;
        }
        if (getFirstVisiblePosition() > 0 || getLastVisiblePosition() < count - 1) {
            return true;
        }
        return false;
    }

    protected boolean canAnimate() {
        return super.canAnimate() && this.mItemCount > 0;
    }

    void handleDataChanged() {
        boolean z;
        int i = this.mItemCount;
        if (i > 0) {
            int findSyncPosition;
            boolean z2;
            if (this.mNeedSync) {
                this.mNeedSync = false;
                findSyncPosition = findSyncPosition();
                if (findSyncPosition >= 0 && lookForSelectablePosition(findSyncPosition, true) == findSyncPosition) {
                    setNextSelectedPositionInt(findSyncPosition);
                    z2 = true;
                    if (!z2) {
                        findSyncPosition = getSelectedItemPosition();
                        if (findSyncPosition >= i) {
                            findSyncPosition = i - 1;
                        }
                        if (findSyncPosition < 0) {
                            findSyncPosition = 0;
                        }
                        i = lookForSelectablePosition(findSyncPosition, true);
                        if (i >= 0) {
                            findSyncPosition = lookForSelectablePosition(findSyncPosition, false);
                        } else {
                            findSyncPosition = i;
                        }
                        if (findSyncPosition >= 0) {
                            setNextSelectedPositionInt(findSyncPosition);
                            checkSelectionChanged();
                            z = true;
                        }
                    }
                    z = z2;
                }
            }
            z2 = false;
            if (z2) {
                findSyncPosition = getSelectedItemPosition();
                if (findSyncPosition >= i) {
                    findSyncPosition = i - 1;
                }
                if (findSyncPosition < 0) {
                    findSyncPosition = 0;
                }
                i = lookForSelectablePosition(findSyncPosition, true);
                if (i >= 0) {
                    findSyncPosition = i;
                } else {
                    findSyncPosition = lookForSelectablePosition(findSyncPosition, false);
                }
                if (findSyncPosition >= 0) {
                    setNextSelectedPositionInt(findSyncPosition);
                    checkSelectionChanged();
                    z = true;
                }
            }
            z = z2;
        } else {
            z = false;
        }
        if (!z) {
            this.mSelectedPosition = -1;
            this.mSelectedRowId = Long.MIN_VALUE;
            this.mNextSelectedPosition = -1;
            this.mNextSelectedRowId = Long.MIN_VALUE;
            this.mNeedSync = false;
            checkSelectionChanged();
        }
    }

    void checkSelectionChanged() {
        if (this.mSelectedPosition != this.mOldSelectedPosition || this.mSelectedRowId != this.mOldSelectedRowId) {
            selectionChanged();
            this.mOldSelectedPosition = this.mSelectedPosition;
            this.mOldSelectedRowId = this.mSelectedRowId;
        }
    }

    int findSyncPosition() {
        int i = this.mItemCount;
        if (i == 0) {
            return -1;
        }
        long j = this.mSyncRowId;
        int i2 = this.mSyncPosition;
        if (j == Long.MIN_VALUE) {
            return -1;
        }
        int min = Math.min(i - 1, Math.max(0, i2));
        long uptimeMillis = SystemClock.uptimeMillis() + 100;
        Object obj = null;
        Adapter adapter = getAdapter();
        if (adapter == null) {
            return -1;
        }
        int i3 = min;
        int i4 = min;
        while (SystemClock.uptimeMillis() <= uptimeMillis) {
            if (adapter.getItemId(i4) != j) {
                Object obj2 = min == i + -1 ? 1 : null;
                Object obj3 = i3 == 0 ? 1 : null;
                if (obj2 != null && obj3 != null) {
                    break;
                } else if (obj3 != null || (r0 != null && obj2 == null)) {
                    min++;
                    obj = null;
                    i4 = min;
                } else if (obj2 != null || (r0 == null && obj3 == null)) {
                    i3--;
                    obj = 1;
                    i4 = i3;
                }
            } else {
                return i4;
            }
        }
        return -1;
    }

    int lookForSelectablePosition(int i, boolean z) {
        return i;
    }

    void setSelectedPositionInt(int i) {
        this.mSelectedPosition = i;
        this.mSelectedRowId = getItemIdAtPosition(i);
    }

    void setNextSelectedPositionInt(int i) {
        this.mNextSelectedPosition = i;
        this.mNextSelectedRowId = getItemIdAtPosition(i);
        if (this.mNeedSync && this.mSyncMode == 0 && i >= 0) {
            this.mSyncPosition = i;
            this.mSyncRowId = this.mNextSelectedRowId;
        }
    }

    void rememberSyncState() {
        if (getChildCount() > 0) {
            this.mNeedSync = true;
            this.mSyncHeight = (long) this.mLayoutHeight;
            View childAt;
            if (this.mSelectedPosition >= 0) {
                childAt = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                this.mSyncRowId = this.mNextSelectedRowId;
                this.mSyncPosition = this.mNextSelectedPosition;
                if (childAt != null) {
                    this.mSpecificTop = childAt.getTop();
                }
                this.mSyncMode = 0;
                return;
            }
            childAt = getChildAt(0);
            Adapter adapter = getAdapter();
            if (this.mFirstPosition < 0 || this.mFirstPosition >= adapter.getCount()) {
                this.mSyncRowId = -1;
            } else {
                this.mSyncRowId = adapter.getItemId(this.mFirstPosition);
            }
            this.mSyncPosition = this.mFirstPosition;
            if (childAt != null) {
                this.mSpecificTop = childAt.getTop();
            }
            this.mSyncMode = 1;
        }
    }
}
