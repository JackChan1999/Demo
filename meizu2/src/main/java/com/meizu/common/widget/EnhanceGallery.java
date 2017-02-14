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
import com.meizu.common.R;

public class EnhanceGallery extends AbsSpinner implements OnGestureListener {
    private static final int CHECK_POSITION_SEARCH_DISTANCE = 20;
    public static final int CHOICE_MODE_MULTIPLE = 2;
    public static final int CHOICE_MODE_NONE = 0;
    public static final int CHOICE_MODE_SINGLE = 1;
    private static final int MIN_FLING_VELOCITY = 1500;
    private static final int SCROLL_TO_FLING_UNCERTAINTY_TIMEOUT = 250;
    static final int TOUCH_MODE_DOWN = 0;
    static final int TOUCH_MODE_FLING = 2;
    static final int TOUCH_MODE_OVERFLING = 4;
    static final int TOUCH_MODE_OVERSCROLL = 3;
    static final int TOUCH_MODE_REST = -1;
    static final int TOUCH_MODE_SCROLL = 1;
    private boolean mAccDelegateStates;
    private SpinnerItemAccessibilityDelegate mAccessibilityDelegate;
    private int mAnimationDuration;
    private boolean mChangeChildAlphaWhenDragView;
    private SparseBooleanArray mCheckStates;
    LongSparseArray<Integer> mCheckedIdStates;
    private int mCheckedItemCount;
    private int mChildWidth;
    private ActionMode mChoiceActionMode;
    private int mChoiceMode;
    private AdapterContextMenuInfo mContextMenuInfo;
    private int mCurrentOverScrollDistance;
    private int mDefaultMaxOverScrollDistance;
    private int mDeltaLength;
    private Runnable mDisableSuppressSelectionChangedRunnable;
    private int mDownFirstPosition;
    private int mDownLastPosition;
    private int mDownTouchPosition;
    private View mDownTouchView;
    protected int mDragAndDropPosition;
    private boolean mDragEnable;
    private int mDragOffsetX;
    private int mDragOffsetY;
    private int mDragScrollY;
    private int mDragViewBackground;
    private int mDragViewBackgroundDelete;
    private int mDragViewBackgroundFilter;
    private FlingRunnable mFlingRunnable;
    private GestureDetector mGestureDetector;
    private int mGravity;
    private boolean mIsFirstScroll;
    private boolean mIsRtl;
    private int mLastScrollState;
    private int mMaxOverScrollDistance;
    int mMotionX;
    int mMotionY;
    MultiChoiceModeWrapper mMultiChoiceModeCallback;
    private OnScrollListener mOnScrollListener;
    private PerformClick mPerformClick;
    private boolean mScrollEnableWhenLessContent;
    private View mSelectedChild;
    private ListViewDragShadowBuilder mShadowBuilder;
    private boolean mShouldCallbackDuringFling;
    private boolean mShouldCallbackOnUnselectedItemClick;
    private boolean mShouldStopFling;
    private int mSpacing;
    private boolean mSuppressSelectionChanged;
    private Rect mTouchFrame;
    private int mTouchMode;

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

    public interface DragShadowItem {
        View getDragView();

        Point getDragViewShowPosition();

        boolean needBackground();
    }

    class FlingRunnable implements Runnable {
        private int mLastDelta;
        private int mLastFlingX;
        private int mLastOverFlingX = 0;
        private OverScroller mScroller;

        public FlingRunnable() {
            this.mScroller = new OverScroller(EnhanceGallery.this.getContext());
            this.mScroller.setEnableMZOverScroll(true, true);
        }

        private void startCommon() {
            EnhanceGallery.this.removeCallbacks(this);
        }

        public void startUsingDistance(int i) {
            if (i != 0) {
                EnhanceGallery.this.mTouchMode = 2;
                startCommon();
                this.mLastFlingX = 0;
                this.mScroller.setInterpolator(new DecelerateInterpolator());
                this.mScroller.startScroll(0, 0, -i, 0, EnhanceGallery.this.mAnimationDuration);
                EnhanceGallery.this.postOnAnimation(this);
            }
        }

        public void startSpringback() {
            if (this.mScroller.springBack(EnhanceGallery.this.mCurrentOverScrollDistance, 0, 0, 0, 0, 0)) {
                EnhanceGallery.this.mTouchMode = 4;
                this.mLastOverFlingX = EnhanceGallery.this.mCurrentOverScrollDistance;
                EnhanceGallery.this.invalidate();
                EnhanceGallery.this.postOnAnimation(this);
                return;
            }
            EnhanceGallery.this.mTouchMode = -1;
        }

        public void stop(boolean z) {
            EnhanceGallery.this.removeCallbacks(this);
            endFling(z);
        }

        private void endFling(boolean z) {
            this.mScroller.forceFinished(true);
            if (z) {
                EnhanceGallery.this.scrollIntoSlots();
            } else {
                EnhanceGallery.this.reportScrollStateChange(0);
            }
        }

        public void run() {
            if (EnhanceGallery.this.mItemCount == 0) {
                endFling(true);
                return;
            }
            OverScroller overScroller = this.mScroller;
            int currX;
            switch (EnhanceGallery.this.mTouchMode) {
                case 1:
                case 2:
                    EnhanceGallery.this.mShouldStopFling = false;
                    boolean computeScrollOffset = overScroller.computeScrollOffset();
                    currX = overScroller.getCurrX();
                    int i = this.mLastFlingX - currX;
                    boolean trackMotionScroll = EnhanceGallery.this.trackMotionScroll(i);
                    if (computeScrollOffset && !EnhanceGallery.this.mShouldStopFling && !trackMotionScroll) {
                        this.mLastFlingX = currX;
                        this.mLastDelta = i;
                        EnhanceGallery.this.post(this);
                        return;
                    } else if (computeScrollOffset && !EnhanceGallery.this.mShouldStopFling && trackMotionScroll) {
                        endFling(false);
                        if (EnhanceGallery.this.mTouchMode == 2) {
                            EnhanceGallery.this.mTouchMode = 4;
                        } else {
                            EnhanceGallery.this.mTouchMode = 3;
                        }
                        if (EnhanceGallery.this.mLastScrollState != 2) {
                            EnhanceGallery.this.reportScrollStateChange(2);
                        }
                        startSpringback();
                        return;
                    } else {
                        endFling(true);
                        return;
                    }
                case 3:
                case 4:
                    if (overScroller.computeScrollOffset()) {
                        currX = overScroller.getCurrX();
                        int i2 = currX - this.mLastOverFlingX;
                        this.mLastOverFlingX = currX;
                        if (i2 != 0) {
                            EnhanceGallery.this.trackMotionScroll(-i2);
                        }
                        EnhanceGallery.this.invalidate();
                        EnhanceGallery.this.postOnAnimation(this);
                        return;
                    }
                    endFling(false);
                    EnhanceGallery.this.mTouchMode = -1;
                    return;
                default:
                    EnhanceGallery.this.mTouchMode = -1;
                    if (EnhanceGallery.this.mLastScrollState != 0) {
                        EnhanceGallery.this.reportScrollStateChange(0);
                        return;
                    }
                    return;
            }
        }
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    class ListViewDragShadowBuilder extends DragShadowBuilder {
        private static final int STATE_ENTER_NORMAL = 0;
        private static final int STATE_ENTER_WARNING = 1;
        private static final int STATE_IDLE = -1;
        private Drawable mBackground;
        private Rect mBackgroundPadding;
        private int mHeight;
        private Drawable mHightLightNormal;
        private Drawable mHightLightWarning;
        private boolean mNeedBackground;
        private Point mShowPoint;
        private int mState;
        private int mWidth;

        public ListViewDragShadowBuilder(EnhanceGallery enhanceGallery, View view) {
            this(view, true, null);
        }

        public ListViewDragShadowBuilder(View view, boolean z, Point point) {
            super(view);
            this.mNeedBackground = true;
            this.mShowPoint = null;
            this.mState = -1;
            this.mNeedBackground = z;
            this.mShowPoint = point;
            if (view != null) {
                if (z) {
                    this.mBackground = EnhanceGallery.this.getResources().getDrawable(EnhanceGallery.this.mDragViewBackground);
                    this.mBackgroundPadding = new Rect();
                    this.mBackground.getPadding(this.mBackgroundPadding);
                    Rect rect = this.mBackgroundPadding;
                    int width = view.getWidth();
                    int height = view.getHeight();
                    this.mWidth = (width + rect.left) + rect.right;
                    this.mHeight = rect.bottom + (rect.top + height);
                    this.mBackground.setBounds(0, 0, this.mWidth, this.mHeight);
                    this.mHightLightNormal = EnhanceGallery.this.getResources().getDrawable(EnhanceGallery.this.mDragViewBackgroundFilter);
                    this.mHightLightNormal.setBounds(0, 0, this.mWidth, this.mHeight);
                    this.mHightLightWarning = EnhanceGallery.this.getResources().getDrawable(EnhanceGallery.this.mDragViewBackgroundDelete);
                    this.mHightLightWarning.setBounds(0, 0, this.mWidth, this.mHeight);
                } else {
                    this.mWidth = view.getWidth();
                    this.mHeight = view.getHeight();
                }
                EnhanceGallery.this.mDragScrollY = 0;
                if (this.mHeight > EnhanceGallery.this.getHeight()) {
                    int[] iArr = new int[2];
                    EnhanceGallery.this.getLocationOnScreen(iArr);
                    int[] iArr2 = new int[2];
                    view.getLocationOnScreen(iArr2);
                    if (iArr2[1] < iArr[1]) {
                        EnhanceGallery.this.mDragScrollY = iArr[1] - iArr2[1];
                        EnhanceGallery.this.mDragScrollY = Math.min(this.mHeight - EnhanceGallery.this.getHeight(), EnhanceGallery.this.mDragScrollY);
                    }
                    this.mHeight = EnhanceGallery.this.getHeight();
                }
            }
        }

        public void onProvideShadowMetrics(Point point, Point point2) {
            super.onProvideShadowMetrics(point, point2);
            point.set(this.mWidth, this.mHeight);
            if (this.mNeedBackground) {
                point2.set(EnhanceGallery.this.mDragOffsetX + this.mBackgroundPadding.left, (EnhanceGallery.this.mDragOffsetY + this.mBackgroundPadding.top) - EnhanceGallery.this.mDragScrollY);
            } else {
                point2.set(EnhanceGallery.this.mDragOffsetX, EnhanceGallery.this.mDragOffsetY - EnhanceGallery.this.mDragScrollY);
            }
        }

        public void onDrawShadow(Canvas canvas) {
            if (this.mNeedBackground) {
                if (this.mState == 0) {
                    this.mHightLightNormal.draw(canvas);
                } else if (this.mState == 1) {
                    this.mHightLightWarning.draw(canvas);
                } else {
                    this.mBackground.draw(canvas);
                }
                canvas.save();
                canvas.translate((float) this.mBackgroundPadding.left, (float) (this.mBackgroundPadding.top - EnhanceGallery.this.mDragScrollY));
                super.onDrawShadow(canvas);
                canvas.restore();
            } else if (EnhanceGallery.this.mDragScrollY != 0) {
                canvas.save();
                canvas.translate(0.0f, (float) (-EnhanceGallery.this.mDragScrollY));
                super.onDrawShadow(canvas);
                canvas.restore();
            } else {
                super.onDrawShadow(canvas);
            }
        }

        public void setDragingState(int i) {
            this.mState = i;
        }

        public int getDragingState() {
            return this.mState;
        }
    }

    public interface MultiChoiceModeListener extends Callback {
        void onItemCheckedStateChanged(ActionMode actionMode, int i, long j, boolean z);
    }

    class MultiChoiceModeWrapper implements MultiChoiceModeListener {
        private MultiChoiceModeListener mWrapped;

        MultiChoiceModeWrapper() {
        }

        public void setWrapped(MultiChoiceModeListener multiChoiceModeListener) {
            this.mWrapped = multiChoiceModeListener;
        }

        public boolean hasWrappedCallback() {
            return this.mWrapped != null;
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            if (!this.mWrapped.onCreateActionMode(actionMode, menu)) {
                return false;
            }
            if (EnhanceGallery.this.mChoiceMode == 2) {
                EnhanceGallery.this.setLongClickable(true);
                return true;
            }
            EnhanceGallery.this.setLongClickable(false);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        public void onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            EnhanceGallery.this.mChoiceActionMode = null;
            EnhanceGallery.this.clearChoices();
            EnhanceGallery.this.invalidateViews();
            EnhanceGallery.this.setLongClickable(true);
        }

        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long j, boolean z) {
            this.mWrapped.onItemCheckedStateChanged(actionMode, i, j, z);
            if (EnhanceGallery.this.getCheckedItemCount() == 0) {
                actionMode.finish();
            }
        }
    }

    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        void onScroll(EnhanceGallery enhanceGallery, int i, int i2, int i3);

        void onScrollStateChanged(EnhanceGallery enhanceGallery, int i);
    }

    class WindowRunnnable {
        private int mOriginalAttachCount;

        private WindowRunnnable() {
        }

        public void rememberWindowAttachCount() {
            this.mOriginalAttachCount = EnhanceGallery.this.getWindowAttachCount();
        }

        public boolean sameWindow() {
            return EnhanceGallery.this.hasWindowFocus() && EnhanceGallery.this.getWindowAttachCount() == this.mOriginalAttachCount;
        }
    }

    class PerformClick extends WindowRunnnable implements Runnable {
        int mClickMotionPosition;

        private PerformClick() {
            super();
        }

        public void run() {
            if (!EnhanceGallery.this.mDataChanged) {
                SpinnerAdapter adapter = EnhanceGallery.this.getAdapter();
                int i = this.mClickMotionPosition;
                if (adapter != null && EnhanceGallery.this.mItemCount > 0 && i != -1 && i < adapter.getCount() && sameWindow()) {
                    View childAt = EnhanceGallery.this.getChildAt(i - EnhanceGallery.this.mFirstPosition);
                    if (childAt != null) {
                        EnhanceGallery.this.performItemClicks(childAt, i, adapter.getItemId(i));
                    }
                }
            }
        }
    }

    class SpinnerItemAccessibilityDelegate extends AccessibilityDelegate {
        SpinnerItemAccessibilityDelegate() {
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            EnhanceGallery.this.onInitializeAccessibilityNodeInfoForItem(view, EnhanceGallery.this.getPositionForView(view), accessibilityNodeInfo);
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            return false;
        }
    }

    public EnhanceGallery(Context context) {
        this(context, null);
    }

    public EnhanceGallery(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_EnhanceGalleryStyle);
    }

    public EnhanceGallery(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTouchMode = -1;
        this.mSpacing = 0;
        this.mAnimationDuration = SCROLL_TO_FLING_UNCERTAINTY_TIMEOUT;
        this.mFlingRunnable = new FlingRunnable();
        this.mDisableSuppressSelectionChangedRunnable = new Runnable() {
            public void run() {
                EnhanceGallery.this.mSuppressSelectionChanged = false;
                EnhanceGallery.this.selectionChanged();
            }
        };
        this.mShouldCallbackDuringFling = true;
        this.mShouldCallbackOnUnselectedItemClick = true;
        this.mDragEnable = false;
        this.mIsRtl = false;
        this.mScrollEnableWhenLessContent = false;
        this.mChoiceMode = 0;
        this.mChangeChildAlphaWhenDragView = false;
        this.mDragViewBackground = R.drawable.mz_list_selector_background_long_pressed;
        this.mDragViewBackgroundFilter = R.drawable.mz_list_selector_background_filter;
        this.mDragViewBackgroundDelete = R.drawable.mz_list_selector_background_delete;
        this.mDragScrollY = 0;
        this.mDragAndDropPosition = -1;
        this.mDragOffsetX = 0;
        this.mDragOffsetY = 0;
        this.mAccDelegateStates = false;
        this.mLastScrollState = 0;
        this.mGestureDetector = new GestureDetector(context, this);
        this.mGestureDetector.setIsLongpressEnabled(true);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EnhanceGallery, i, 0);
        setSpacing(obtainStyledAttributes.getDimensionPixelSize(R.styleable.EnhanceGallery_mcSpacing, 10));
        this.mDefaultMaxOverScrollDistance = getResources().getDimensionPixelSize(R.dimen.mc_enhancegallery_max_overscroll_distance);
        this.mMaxOverScrollDistance = obtainStyledAttributes.getDimensionPixelSize(R.styleable.EnhanceGallery_mcMaxOverScrollDistance, this.mDefaultMaxOverScrollDistance);
        this.mScrollEnableWhenLessContent = obtainStyledAttributes.getBoolean(R.styleable.EnhanceGallery_mcScrollEnableWhenLessContent, false);
        obtainStyledAttributes.recycle();
    }

    public void setCallbackDuringFling(boolean z) {
        this.mShouldCallbackDuringFling = z;
    }

    public void setMaxOverScrollDistance(int i) {
        if (i < 0) {
            this.mMaxOverScrollDistance = this.mDefaultMaxOverScrollDistance;
        } else {
            this.mMaxOverScrollDistance = i;
        }
    }

    public void setCallbackOnUnselectedItemClick(boolean z) {
        this.mShouldCallbackOnUnselectedItemClick = z;
    }

    public void setDragEnable(boolean z) {
        this.mDragEnable = z;
    }

    public void setAnimationDuration(int i) {
        this.mAnimationDuration = i;
    }

    public void setSpacing(int i) {
        this.mSpacing = i;
    }

    public void setScrollEnableWhenLessContent(boolean z) {
        this.mScrollEnableWhenLessContent = z;
    }

    protected int computeHorizontalScrollExtent() {
        return 1;
    }

    protected int computeHorizontalScrollOffset() {
        return this.mSelectedPosition;
    }

    protected int computeHorizontalScrollRange() {
        return this.mItemCount;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mInLayout = true;
        layout(0, false);
        this.mInLayout = false;
    }

    int getChildHeight(View view) {
        return view.getMeasuredHeight();
    }

    boolean trackMotionScroll(int i) {
        boolean z = true;
        int childCount = getChildCount();
        if (childCount == 0 || i == 0) {
            return false;
        }
        boolean z2;
        int i2;
        boolean z3 = i < 0;
        boolean z4;
        if (this.mIsRtl) {
            if (this.mFirstPosition != 0 || getChildAt(0).getRight() < (getWidth() - getPaddingRight()) - this.mSpacing || i > 0) {
                z4 = false;
            } else {
                z4 = true;
            }
            if (this.mFirstPosition + childCount != this.mItemCount || getChildAt(childCount - 1).getLeft() < getPaddingLeft() || i < 0) {
                z2 = false;
            } else {
                z2 = true;
            }
        } else {
            if (this.mFirstPosition != 0 || getChildAt(0).getLeft() < getPaddingLeft() + this.mSpacing || i < 0) {
                z4 = false;
            } else {
                z4 = true;
            }
            if (this.mFirstPosition + childCount != this.mItemCount || getChildAt(childCount - 1).getRight() > getWidth() - getPaddingRight() || i > 0) {
                z2 = false;
            } else {
                z2 = true;
            }
            boolean z5 = z2;
            z2 = z4;
            z4 = z5;
        }
        if (z2 || r3) {
            i2 = true;
        } else {
            i2 = 0;
        }
        offsetChildrenLeftAndRight(i);
        if (i2 == 0) {
            detachOffScreenChildren(z3);
            if (z3) {
                fillToGalleryRight();
            } else {
                fillToGalleryLeft();
            }
            this.mRecycler.clear();
            setSelectionView();
        }
        this.mCurrentOverScrollDistance = 0;
        int childCount2 = getChildCount();
        int width;
        if (this.mIsRtl) {
            i2 = getChildAt(0).getRight();
            childCount = getChildAt(childCount2 - 1).getLeft();
            width = (getWidth() - getPaddingRight()) - this.mSpacing;
            if (this.mFirstPosition != 0 || i2 >= width) {
                if (childCount2 + this.mFirstPosition == this.mItemCount && childCount > getPaddingLeft()) {
                    this.mCurrentOverScrollDistance = (getPaddingLeft() + this.mSpacing) - childCount;
                }
                z = false;
            } else {
                this.mCurrentOverScrollDistance = width - i2;
            }
        } else {
            i2 = getChildAt(0).getLeft();
            childCount = getChildAt(childCount2 - 1).getRight();
            width = getPaddingLeft() + this.mSpacing;
            int width2 = getWidth() - getPaddingRight();
            if (this.mFirstPosition != 0 || i2 <= width) {
                if (childCount2 + this.mFirstPosition == this.mItemCount && childCount < width2) {
                    this.mCurrentOverScrollDistance = (width2 - childCount) - this.mSpacing;
                }
                z = false;
            } else {
                this.mCurrentOverScrollDistance = width - i2;
            }
        }
        invokeOnItemScrollListener();
        onScrollChanged(0, 0, 0, 0);
        invalidate();
        return z;
    }

    private void offsetChildrenLeftAndRight(int i) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            getChildAt(childCount).offsetLeftAndRight(i);
        }
    }

    private int getCenterOfEnhanceGallery() {
        return (((getWidth() - getPaddingLeft()) - getPaddingRight()) / 2) + getPaddingLeft();
    }

    private static int getCenterOfView(View view) {
        return view.getLeft() + (view.getWidth() / 2);
    }

    private void detachOffScreenChildren(boolean z) {
        int paddingLeft;
        int i = 0;
        int childCount = getChildCount();
        int i2 = this.mFirstPosition;
        int i3;
        int i4;
        int i5;
        int i6;
        if (z) {
            if (this.mIsRtl) {
                paddingLeft = getPaddingLeft();
            } else {
                paddingLeft = getPaddingLeft() + this.mSpacing;
            }
            i3 = 0;
            i4 = 0;
            i5 = 0;
            while (i3 < childCount - 1) {
                if (this.mIsRtl) {
                    i6 = (childCount - 1) - i3;
                } else {
                    i6 = i3;
                }
                if ((this.mIsRtl ? getChildAt(i6 - 1) : getChildAt(i6 + 1)).getLeft() > paddingLeft) {
                    break;
                }
                i4++;
                this.mRecycler.put(i2 + i6, getChildAt(i6));
                i3++;
                i5 = i6;
            }
            if (this.mIsRtl) {
                paddingLeft = i5;
            } else {
                paddingLeft = 0;
            }
            i = paddingLeft;
            paddingLeft = i4;
        } else {
            if (this.mIsRtl) {
                paddingLeft = (getWidth() - getPaddingRight()) - this.mSpacing;
            } else {
                paddingLeft = getWidth() - getPaddingRight();
            }
            i3 = childCount - 1;
            i5 = 0;
            i4 = 0;
            while (i3 >= 1) {
                if (this.mIsRtl) {
                    i6 = (childCount - 1) - i3;
                } else {
                    i6 = i3;
                }
                if ((this.mIsRtl ? getChildAt(i6 + 1) : getChildAt(i6 - 1)).getRight() < paddingLeft) {
                    break;
                }
                i5++;
                this.mRecycler.put(i2 + i6, getChildAt(i6));
                i3--;
                i4 = i6;
            }
            if (this.mIsRtl) {
                paddingLeft = i5;
            } else {
                paddingLeft = i5;
                i = i4;
            }
        }
        detachViewsFromParent(i, paddingLeft);
        if (z != this.mIsRtl) {
            this.mFirstPosition = paddingLeft + this.mFirstPosition;
        }
    }

    private void scrollIntoSlots() {
        int childCount = getChildCount();
        if (childCount != 0 && this.mSelectedChild != null) {
            int left;
            View childAt = getChildAt(0);
            View childAt2 = getChildAt(childCount - 1);
            int width;
            int paddingLeft;
            if (this.mIsRtl) {
                width = (getWidth() - getPaddingRight()) - this.mSpacing;
                paddingLeft = getPaddingLeft();
                if (this.mTouchMode == 2 && this.mFirstPosition + childCount == this.mItemCount) {
                    left = (this.mSpacing + paddingLeft) - childAt2.getLeft();
                } else {
                    if (childAt.getRight() != width) {
                        if (getCenterOfView(childAt) >= width) {
                            left = width - getChildAt(1).getRight();
                        } else {
                            left = width - childAt.getRight();
                        }
                        if (childCount + this.mFirstPosition == this.mItemCount && childAt2.getLeft() + r0 > paddingLeft) {
                            left = (paddingLeft - childAt2.getLeft()) + this.mSpacing;
                        }
                    }
                    left = 0;
                }
            } else {
                width = getPaddingLeft() + this.mSpacing;
                paddingLeft = getWidth() - getPaddingRight();
                if (this.mTouchMode == 2 && this.mFirstPosition + childCount == this.mItemCount) {
                    left = (paddingLeft - childAt2.getRight()) - this.mSpacing;
                } else {
                    if (childAt.getLeft() != width) {
                        if (getCenterOfView(childAt) < width) {
                            left = width - getChildAt(1).getLeft();
                        } else {
                            left = width - childAt.getLeft();
                        }
                        if (childCount + this.mFirstPosition == this.mItemCount && childAt2.getRight() + r0 != paddingLeft - this.mSpacing) {
                            left = (paddingLeft - childAt2.getRight()) - this.mSpacing;
                        }
                    }
                    left = 0;
                }
            }
            if (left != 0) {
                if (this.mLastScrollState != 2) {
                    reportScrollStateChange(2);
                }
                this.mFlingRunnable.startUsingDistance(left);
                return;
            }
            if (this.mLastScrollState != 0) {
                reportScrollStateChange(0);
            }
            onFinishedMovement();
            this.mTouchMode = -1;
        } else if (this.mLastScrollState != 0) {
            reportScrollStateChange(0);
        }
    }

    private void onFinishedMovement() {
        if (this.mSuppressSelectionChanged) {
            this.mSuppressSelectionChanged = false;
            super.selectionChanged();
        }
        invalidate();
    }

    void selectionChanged() {
        if (!this.mSuppressSelectionChanged) {
            super.selectionChanged();
        }
    }

    private void setSelectionView() {
        if (this.mSelectedChild != null) {
            int i = this.mFirstPosition;
            if (i != this.mSelectedPosition) {
                setSelectedPositionInt(i);
                setNextSelectedPositionInt(i);
                checkSelectionChanged();
            }
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        postDelayed(new Runnable() {
            public void run() {
                View childAt = EnhanceGallery.this.getChildAt(EnhanceGallery.this.getChildCount() - 1);
                int i = 0;
                if (EnhanceGallery.this.mIsRtl) {
                    if (childAt != null && childAt.getLeft() > EnhanceGallery.this.getPaddingLeft()) {
                        i = EnhanceGallery.this.getPaddingLeft() - childAt.getLeft();
                    }
                } else if (childAt != null && childAt.getRight() < EnhanceGallery.this.getWidth() - EnhanceGallery.this.getPaddingRight()) {
                    i = (EnhanceGallery.this.getWidth() - EnhanceGallery.this.getPaddingRight()) - childAt.getRight();
                }
                EnhanceGallery.this.mTouchMode = -1;
                if (!(EnhanceGallery.this.mLastScrollState == 2 || i == 0)) {
                    EnhanceGallery.this.reportScrollStateChange(2);
                }
                EnhanceGallery.this.mFlingRunnable.startUsingDistance(i);
            }
        }, 200);
    }

    void layout(int i, boolean z) {
        boolean z2 = true;
        if (VERSION.SDK_INT >= 17) {
            if (getLayoutDirection() != 1) {
                z2 = false;
            }
            this.mIsRtl = z2;
        }
        if (this.mDataChanged) {
            handleDataChanged();
        }
        if (this.mDataChanged && this.mChoiceMode == 2 && this.mAdapter != null && this.mAdapter.hasStableIds()) {
            confirmCheckedPositionsById();
        }
        if (this.mItemCount == 0) {
            invokeOnItemScrollListener();
            resetList();
            return;
        }
        if (this.mNextSelectedPosition >= 0) {
            setSelectedPositionInt(this.mNextSelectedPosition);
        }
        recycleAllViews();
        detachAllViewsFromParent();
        this.mFirstPosition = this.mSelectedPosition;
        layoutChildren();
        this.mRecycler.clear();
        invalidate();
        checkSelectionChanged();
        this.mDataChanged = false;
        this.mNeedSync = false;
        setNextSelectedPositionInt(this.mSelectedPosition);
        updateSelectedItemMetadata();
        this.mDeltaLength = 0;
        View childAt = getChildAt(0);
        if (childAt != null) {
            this.mChildWidth = childAt.getWidth();
            this.mDeltaLength = ((getWidth() - getPaddingLeft()) - getPaddingRight()) - (this.mItemCount * (this.mChildWidth + this.mSpacing));
            int childCount;
            if (this.mDeltaLength <= 0 || this.mScrollEnableWhenLessContent) {
                if (this.mDeltaLength <= 0) {
                    childCount = getChildCount();
                    int paddingLeft;
                    if (this.mIsRtl) {
                        paddingLeft = getPaddingLeft() + this.mSpacing;
                        if (this.mFirstPosition + childCount == this.mItemCount && getChildAt(childCount - 1).getLeft() != paddingLeft) {
                            trackMotionScroll(paddingLeft - getChildAt(childCount - 1).getLeft());
                            scrollIntoSlots();
                        }
                    } else {
                        paddingLeft = (getWidth() - getPaddingRight()) - this.mSpacing;
                        if (this.mFirstPosition + childCount == this.mItemCount && getChildAt(childCount - 1).getRight() != paddingLeft) {
                            trackMotionScroll(paddingLeft - getChildAt(childCount - 1).getRight());
                            scrollIntoSlots();
                        }
                    }
                }
            } else if (this.mFirstPosition != 0 && this.mSelectedPosition < this.mItemCount) {
                if (this.mIsRtl) {
                    childCount = (-this.mSelectedPosition) * (this.mChildWidth + this.mSpacing);
                } else {
                    childCount = this.mSelectedPosition * (this.mChildWidth + this.mSpacing);
                }
                trackMotionScroll(childCount);
                scrollIntoSlots();
            }
        }
        invokeOnItemScrollListener();
    }

    private void layoutChildren() {
        int i = this.mSpacing;
        int paddingLeft = getPaddingLeft();
        int right = (getRight() - getLeft()) - getPaddingRight();
        int i2 = this.mItemCount;
        int i3;
        int i4;
        if (this.mIsRtl) {
            i3 = this.mFirstPosition;
            i4 = right - this.mSpacing;
            while (i4 > paddingLeft && i3 < i2) {
                i4 = makeAndAddView(i3, i3 - this.mSelectedPosition, i4, false).getLeft() - i;
                i3++;
            }
            return;
        }
        i3 = this.mFirstPosition;
        i4 = paddingLeft + i;
        while (i4 < right && i3 < i2) {
            i4 = makeAndAddView(i3, i3 - this.mSelectedPosition, i4, true).getRight() + i;
            i3++;
        }
    }

    private void fillToGalleryLeft() {
        if (this.mIsRtl) {
            fillToGalleryLeftRtl();
        } else {
            fillToGalleryLeftLtr();
        }
    }

    private void fillToGalleryLeftRtl() {
        int i;
        int i2 = this.mSpacing;
        int paddingLeft = getPaddingLeft();
        int childCount = getChildCount();
        View childAt = getChildAt(childCount - 1);
        if (childAt != null) {
            i = this.mFirstPosition + childCount;
            childCount = childAt.getLeft() - i2;
        } else {
            i = this.mItemCount - 1;
            this.mFirstPosition = i;
            childCount = (getRight() - getLeft()) - getPaddingRight();
            this.mShouldStopFling = true;
        }
        while (childCount > paddingLeft && i < this.mItemCount) {
            childCount = makeAndAddView(i, i - this.mSelectedPosition, childCount, false).getLeft() - i2;
            i++;
        }
    }

    private void fillToGalleryLeftLtr() {
        int i;
        int left;
        int i2 = this.mSpacing;
        int paddingLeft = getPaddingLeft();
        View childAt = getChildAt(0);
        if (childAt != null) {
            i = this.mFirstPosition - 1;
            left = childAt.getLeft() - i2;
        } else {
            left = (getRight() - getLeft()) - getPaddingRight();
            this.mShouldStopFling = true;
            i = 0;
        }
        while (left > paddingLeft && i >= 0) {
            childAt = makeAndAddView(i, i - this.mSelectedPosition, left, false);
            this.mFirstPosition = i;
            left = childAt.getLeft() - i2;
            i--;
        }
    }

    private void fillToGalleryRight() {
        if (this.mIsRtl) {
            fillToGalleryRightRtl();
        } else {
            fillToGalleryRightLtr();
        }
    }

    private void fillToGalleryRightRtl() {
        int right;
        int i = 0;
        int i2 = this.mSpacing;
        int right2 = (getRight() - getLeft()) - getPaddingRight();
        View childAt = getChildAt(0);
        if (childAt != null) {
            i = this.mFirstPosition - 1;
            right = childAt.getRight() + i2;
        } else {
            right = getPaddingLeft();
            this.mShouldStopFling = true;
        }
        while (right < right2 && i >= 0) {
            childAt = makeAndAddView(i, i - this.mSelectedPosition, right, true);
            this.mFirstPosition = i;
            right = childAt.getRight() + i2;
            i--;
        }
    }

    private void fillToGalleryRightLtr() {
        int i;
        int i2 = this.mSpacing;
        int right = (getRight() - getLeft()) - getPaddingRight();
        int childCount = getChildCount();
        int i3 = this.mItemCount;
        View childAt = getChildAt(childCount - 1);
        if (childAt != null) {
            i = this.mFirstPosition + childCount;
            childCount = childAt.getRight() + i2;
        } else {
            i = this.mItemCount - 1;
            this.mFirstPosition = i;
            childCount = getPaddingLeft();
            this.mShouldStopFling = true;
        }
        while (childCount < right && i < i3) {
            childCount = makeAndAddView(i, i - this.mSelectedPosition, childCount, true).getRight() + i2;
            i++;
        }
    }

    private View makeAndAddView(int i, int i2, int i3, boolean z) {
        View view;
        if (!this.mDataChanged) {
            view = this.mRecycler.get(i);
            if (view != null) {
                if (!this.mAccDelegateStates) {
                    if (view.getImportantForAccessibility() == 0) {
                        view.setImportantForAccessibility(1);
                    }
                    if (((AccessibilityManager) getContext().getSystemService("accessibility")).isEnabled()) {
                        if (this.mAccessibilityDelegate == null) {
                            this.mAccessibilityDelegate = new SpinnerItemAccessibilityDelegate();
                        }
                        if (view.getAccessibilityNodeProvider() == null) {
                            view.setAccessibilityDelegate(this.mAccessibilityDelegate);
                        }
                    }
                    this.mAccDelegateStates = true;
                }
                setUpChild(view, i, i2, i3, z);
                return view;
            }
        }
        view = this.mAdapter.getView(i, null, this);
        if (view.getImportantForAccessibility() == 0) {
            view.setImportantForAccessibility(1);
        }
        if (((AccessibilityManager) getContext().getSystemService("accessibility")).isEnabled()) {
            if (this.mAccessibilityDelegate == null) {
                this.mAccessibilityDelegate = new SpinnerItemAccessibilityDelegate();
            }
            if (view.getAccessibilityNodeProvider() == null) {
                view.setAccessibilityDelegate(this.mAccessibilityDelegate);
            }
        }
        setUpChild(view, i, i2, i3, z);
        return view;
    }

    private void setUpChild(View view, int i, int i2, int i3, boolean z) {
        int i4;
        boolean z2 = false;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = (LayoutParams) generateDefaultLayoutParams();
        } else if (layoutParams instanceof LayoutParams) {
            r0 = (LayoutParams) layoutParams;
        } else {
            r0 = (LayoutParams) generateLayoutParams(layoutParams);
        }
        if (z != this.mIsRtl) {
            i4 = -1;
        } else {
            i4 = 0;
        }
        addViewInLayout(view, i4, layoutParams);
        if (this.mChoiceMode == 1) {
            if (i2 == 0) {
                z2 = true;
            }
            view.setSelected(z2);
        }
        view.measure(ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mSpinnerPadding.left + this.mSpinnerPadding.right, layoutParams.width), ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mSpinnerPadding.top + this.mSpinnerPadding.bottom, layoutParams.height));
        i4 = calculateTop(view, true);
        int measuredHeight = i4 + view.getMeasuredHeight();
        int measuredWidth = view.getMeasuredWidth();
        if (z) {
            int i5 = measuredWidth + i3;
            measuredWidth = i3;
            i3 = i5;
        } else {
            measuredWidth = i3 - measuredWidth;
        }
        view.layout(measuredWidth, i4, i3, measuredHeight);
        if (!(this.mChoiceMode == 0 || this.mCheckStates == null)) {
            if (view instanceof Checkable) {
                ((Checkable) view).setChecked(this.mCheckStates.get(i));
            } else if (getContext().getApplicationInfo().targetSdkVersion >= 11) {
                view.setActivated(this.mCheckStates.get(i));
            }
        }
        if (this.mChoiceMode == 2 && this.mDragEnable) {
            view.setOnDragListener(new OnDragListener() {
                public boolean onDrag(View view, DragEvent dragEvent) {
                    if (EnhanceGallery.this.mDragAndDropPosition == -1) {
                        return false;
                    }
                    View childAt;
                    View dragView;
                    switch (dragEvent.getAction()) {
                        case 1:
                            return true;
                        case 2:
                            return true;
                        case 3:
                            return false;
                        case 4:
                            childAt = EnhanceGallery.this.getChildAt(EnhanceGallery.this.mDragAndDropPosition - EnhanceGallery.this.mFirstPosition);
                            if (childAt != null) {
                                if (childAt instanceof DragShadowItem) {
                                    dragView = ((DragShadowItem) childAt).getDragView();
                                    if (dragView != null) {
                                        dragView.setAlpha(1.0f);
                                    }
                                    if (EnhanceGallery.this.mChangeChildAlphaWhenDragView) {
                                        childAt.setAlpha(1.0f);
                                    }
                                } else {
                                    childAt.setAlpha(1.0f);
                                }
                            }
                            if (!dragEvent.getResult()) {
                                EnhanceGallery.this.setItemChecked(EnhanceGallery.this.mDragAndDropPosition, true);
                            } else if (childAt != null) {
                                dragView = childAt.findViewById(16908289);
                                if (dragView != null && (dragView instanceof Checkable)) {
                                    ((Checkable) dragView).setChecked(false);
                                }
                                EnhanceGallery.this.invalidateViews();
                            }
                            EnhanceGallery.this.mDragAndDropPosition = -1;
                            if (EnhanceGallery.this.getCheckedItemCount() <= 0 && EnhanceGallery.this.mChoiceActionMode != null) {
                                EnhanceGallery.this.mChoiceActionMode.finish();
                                break;
                            }
                        case 5:
                            return true;
                        case 6:
                            return true;
                        case 100:
                            childAt = EnhanceGallery.this.getChildAt(EnhanceGallery.this.mDragAndDropPosition - EnhanceGallery.this.mFirstPosition);
                            if (childAt != null) {
                                if (childAt instanceof DragShadowItem) {
                                    dragView = ((DragShadowItem) childAt).getDragView();
                                    if (dragView != null) {
                                        dragView.setAlpha(1.0f);
                                    }
                                    if (EnhanceGallery.this.mChangeChildAlphaWhenDragView) {
                                        childAt.setAlpha(1.0f);
                                    }
                                } else {
                                    childAt.setAlpha(1.0f);
                                }
                                dragView = childAt.findViewById(16908289);
                                if (dragView != null && (dragView instanceof Checkable)) {
                                    ((Checkable) dragView).setChecked(false);
                                }
                            }
                            EnhanceGallery.this.requestLayout();
                            if (EnhanceGallery.this.getCheckedItemCount() <= 0 && EnhanceGallery.this.mChoiceActionMode != null) {
                                EnhanceGallery.this.mChoiceActionMode.finish();
                            }
                            EnhanceGallery.this.mDragAndDropPosition = -1;
                            break;
                    }
                    return true;
                }
            });
        }
    }

    private int calculateTop(View view, boolean z) {
        int measuredHeight = z ? getMeasuredHeight() : getHeight();
        int measuredHeight2 = z ? view.getMeasuredHeight() : view.getHeight();
        switch (this.mGravity) {
            case 16:
                return ((((measuredHeight - this.mSpinnerPadding.bottom) - this.mSpinnerPadding.top) - measuredHeight2) / 2) + this.mSpinnerPadding.top;
            case 48:
                return this.mSpinnerPadding.top;
            case 80:
                return (measuredHeight - this.mSpinnerPadding.bottom) - measuredHeight2;
            default:
                return 0;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = this.mGestureDetector.onTouchEvent(motionEvent);
        int action = motionEvent.getAction();
        if (action == 1) {
            onUp();
        } else if (action == 3) {
            onCancel();
        }
        return onTouchEvent;
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        if (this.mDownTouchPosition >= 0 && this.mChoiceMode != 0) {
            if (this.mPerformClick == null) {
                this.mPerformClick = new PerformClick();
            }
            Runnable runnable = this.mPerformClick;
            runnable.mClickMotionPosition = this.mDownTouchPosition;
            runnable.rememberWindowAttachCount();
            post(runnable);
        } else if (this.mShouldCallbackOnUnselectedItemClick || this.mDownTouchPosition == this.mSelectedPosition) {
            performItemClick(this.mDownTouchView, this.mDownTouchPosition, this.mAdapter.getItemId(this.mDownTouchPosition));
        }
        return true;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.mDeltaLength > 0 && !this.mScrollEnableWhenLessContent) {
            return false;
        }
        if (!this.mShouldCallbackDuringFling) {
            removeCallbacks(this.mDisableSuppressSelectionChangedRunnable);
            if (!this.mSuppressSelectionChanged) {
                this.mSuppressSelectionChanged = true;
            }
        }
        int childCount = getChildCount();
        switch (this.mTouchMode) {
            case 1:
                if (Math.abs(f) >= 1500.0f) {
                    int right;
                    this.mTouchMode = 2;
                    int floor = ((int) Math.floor((double) (((getWidth() - getPaddingLeft()) - getPaddingRight()) / (this.mChildWidth + this.mSpacing)))) * (this.mChildWidth + this.mSpacing);
                    View childAt;
                    View childAt2;
                    if (f > 0.0f) {
                        if (this.mIsRtl) {
                            childAt = getChildAt(this.mDownLastPosition - this.mFirstPosition);
                            childCount = (getWidth() - getPaddingRight()) - this.mSpacing;
                            if (childAt != null) {
                                right = childCount - childAt.getRight();
                            } else {
                                right = childCount - getChildAt(getChildCount() - 1).getRight();
                            }
                        } else {
                            childAt2 = getChildAt(this.mDownFirstPosition - this.mFirstPosition);
                            if (childAt2 != null) {
                                right = floor - ((childAt2.getLeft() - getPaddingLeft()) - this.mSpacing);
                            } else {
                                right = ((getPaddingLeft() + this.mSpacing) - getChildAt(0).getLeft()) + floor;
                            }
                        }
                    } else if (this.mIsRtl) {
                        childAt2 = getChildAt(this.mDownFirstPosition - this.mFirstPosition);
                        int width = (getWidth() - getPaddingRight()) - this.mSpacing;
                        if (childAt2 != null) {
                            right = -(floor - (width - childAt2.getRight()));
                        } else {
                            right = -(floor - (width - getChildAt(0).getRight()));
                        }
                    } else {
                        childAt = getChildAt(this.mDownLastPosition - this.mFirstPosition);
                        if (childAt != null) {
                            right = (getPaddingLeft() + this.mSpacing) - childAt.getLeft();
                        } else {
                            right = (getPaddingLeft() + this.mSpacing) - getChildAt(childCount - 1).getLeft();
                        }
                    }
                    reportScrollStateChange(2);
                    this.mFlingRunnable.startUsingDistance(right);
                    break;
                }
                return false;
            case 3:
                this.mTouchMode = 4;
                break;
        }
        return true;
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.mDeltaLength > 0 && !this.mScrollEnableWhenLessContent) {
            return false;
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        if (this.mShouldCallbackDuringFling) {
            if (this.mSuppressSelectionChanged) {
                this.mSuppressSelectionChanged = false;
            }
        } else if (this.mIsFirstScroll) {
            if (!this.mSuppressSelectionChanged) {
                this.mSuppressSelectionChanged = true;
            }
            postDelayed(this.mDisableSuppressSelectionChangedRunnable, 250);
        }
        if (this.mIsFirstScroll) {
            reportScrollStateChange(1);
        }
        this.mTouchMode = 1;
        getChildCount();
        int i = (int) f;
        if (this.mMaxOverScrollDistance > getWidth()) {
            this.mMaxOverScrollDistance = this.mDefaultMaxOverScrollDistance;
        }
        if (!(this.mCurrentOverScrollDistance == 0 || this.mMaxOverScrollDistance == 0)) {
            this.mTouchMode = 3;
            if (Math.abs(this.mCurrentOverScrollDistance) >= this.mMaxOverScrollDistance) {
                i = 0;
            } else {
                i = (int) (((float) i) * (1.0f - ((((float) Math.abs(this.mCurrentOverScrollDistance)) * 1.0f) / ((float) this.mMaxOverScrollDistance))));
            }
        }
        if (i != 0) {
            trackMotionScroll(i * -1);
        }
        this.mIsFirstScroll = false;
        return true;
    }

    public boolean onDown(MotionEvent motionEvent) {
        if (this.mTouchMode == 2 || this.mTouchMode == 4) {
            this.mTouchMode = 1;
            reportScrollStateChange(1);
        } else {
            this.mTouchMode = 0;
        }
        this.mFlingRunnable.stop(false);
        this.mDownTouchPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
        if (this.mDownTouchPosition >= 0) {
            this.mDownTouchView = getChildAt(this.mDownTouchPosition - this.mFirstPosition);
            this.mDownTouchView.setPressed(true);
        }
        this.mMotionX = (int) motionEvent.getX();
        this.mMotionY = (int) motionEvent.getY();
        this.mDownFirstPosition = this.mFirstPosition;
        this.mDownLastPosition = (this.mFirstPosition + getChildCount()) - 1;
        this.mIsFirstScroll = true;
        return true;
    }

    void onUp() {
        switch (this.mTouchMode) {
            case 0:
                scrollIntoSlots();
                break;
            case 1:
                scrollIntoSlots();
                break;
            case 3:
            case 4:
                if (this.mCurrentOverScrollDistance != 0) {
                    if (this.mLastScrollState != 2) {
                        reportScrollStateChange(2);
                    }
                    this.mFlingRunnable.startSpringback();
                    break;
                }
                break;
        }
        dispatchUnpress();
    }

    void onCancel() {
        onUp();
    }

    public void onLongPress(MotionEvent motionEvent) {
        if (this.mDownTouchPosition >= 0) {
            if (this.mChoiceMode == 2) {
                View childAt = getChildAt(this.mDownTouchPosition - this.mFirstPosition);
                if (childAt != null) {
                    boolean z;
                    int i = this.mDownTouchPosition;
                    long itemId = this.mAdapter.getItemId(this.mDownTouchPosition);
                    if (this.mDataChanged) {
                        z = false;
                    } else {
                        z = performLongPress(childAt, i, itemId);
                    }
                    if (z) {
                        this.mTouchMode = -1;
                        setPressed(false);
                        childAt.setPressed(false);
                    }
                }
            }
            performHapticFeedback(0);
            dispatchLongPress(this.mDownTouchView, this.mDownTouchPosition, getItemIdAtPosition(this.mDownTouchPosition));
        }
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    private void dispatchPress(View view) {
        if (view != null) {
            view.setPressed(true);
        }
        setPressed(true);
    }

    private void dispatchUnpress() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            getChildAt(childCount).setPressed(false);
        }
        setPressed(false);
    }

    public void dispatchSetSelected(boolean z) {
    }

    protected void dispatchSetPressed(boolean z) {
        if (this.mSelectedChild != null) {
            this.mSelectedChild.setPressed(z);
        }
    }

    protected ContextMenuInfo getContextMenuInfo() {
        return this.mContextMenuInfo;
    }

    public boolean showContextMenuForChild(View view) {
        int positionForView = getPositionForView(view);
        if (positionForView < 0) {
            return false;
        }
        return dispatchLongPress(view, positionForView, this.mAdapter.getItemId(positionForView));
    }

    public boolean showContextMenu() {
        if (!isPressed() || this.mSelectedPosition < 0) {
            return false;
        }
        return dispatchLongPress(getChildAt(this.mSelectedPosition - this.mFirstPosition), this.mSelectedPosition, this.mSelectedRowId);
    }

    private boolean dispatchLongPress(View view, int i, long j) {
        boolean onItemLongClick;
        if (this.mOnItemLongClickListener != null) {
            onItemLongClick = this.mOnItemLongClickListener.onItemLongClick(this, this.mDownTouchView, this.mDownTouchPosition, j);
        } else {
            onItemLongClick = false;
        }
        if (!onItemLongClick) {
            this.mContextMenuInfo = new AdapterContextMenuInfo(view, i, j);
            onItemLongClick = super.showContextMenuForChild(this);
        }
        if (onItemLongClick) {
            performHapticFeedback(0);
        }
        return onItemLongClick;
    }

    void setSelectedPositionInt(int i) {
        super.setSelectedPositionInt(i);
        updateSelectedItemMetadata();
    }

    private void updateSelectedItemMetadata() {
        View view = this.mSelectedChild;
        View childAt = getChildAt(this.mSelectedPosition - this.mFirstPosition);
        this.mSelectedChild = childAt;
        if (childAt != null && this.mChoiceMode == 1) {
            childAt.setSelected(true);
            childAt.setFocusable(true);
            if (hasFocus()) {
                childAt.requestFocus();
            }
            if (view != null && view != childAt) {
                view.setSelected(false);
                view.setFocusable(false);
            }
        }
    }

    public void setGravity(int i) {
        if (this.mGravity != i) {
            this.mGravity = i;
            requestLayout();
        }
    }

    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (z && this.mSelectedChild != null && this.mChoiceMode == 1) {
            this.mSelectedChild.requestFocus(i);
            this.mSelectedChild.setSelected(true);
        }
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
        invokeOnItemScrollListener();
    }

    void invokeOnItemScrollListener() {
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(this, this.mFirstPosition, getChildCount(), this.mItemCount);
        }
    }

    void reportScrollStateChange(int i) {
        if (i != this.mLastScrollState) {
            this.mLastScrollState = i;
            if (this.mOnScrollListener != null) {
                this.mOnScrollListener.onScrollStateChanged(this, i);
            }
        }
    }

    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        super.setAdapter(spinnerAdapter);
        if (spinnerAdapter != null && this.mChoiceMode != 0) {
            if (this.mCheckStates == null) {
                this.mCheckStates = new SparseBooleanArray();
            }
            if (spinnerAdapter.hasStableIds() && this.mCheckedIdStates == null) {
                this.mCheckedIdStates = new LongSparseArray();
            }
            clearChoices();
        }
    }

    public void setChoiceMode(int i) {
        this.mChoiceMode = i;
        if (this.mChoiceActionMode != null) {
            this.mChoiceActionMode.finish();
            this.mChoiceActionMode = null;
        }
        if (this.mChoiceMode != 0) {
            if (this.mCheckStates == null) {
                this.mCheckStates = new SparseBooleanArray();
            }
            if (this.mCheckedIdStates == null && this.mAdapter != null && this.mAdapter.hasStableIds()) {
                this.mCheckedIdStates = new LongSparseArray();
            }
            if (this.mChoiceMode == 2) {
                clearChoices();
                setLongClickable(true);
            }
        }
    }

    public void clearChoices() {
        if (this.mCheckStates != null) {
            this.mCheckStates.clear();
        }
        if (this.mCheckedIdStates != null) {
            this.mCheckedIdStates.clear();
        }
        this.mCheckedItemCount = 0;
    }

    public void setMultiChoiceModeListener(MultiChoiceModeListener multiChoiceModeListener) {
        if (this.mMultiChoiceModeCallback == null) {
            this.mMultiChoiceModeCallback = new MultiChoiceModeWrapper();
        }
        this.mMultiChoiceModeCallback.setWrapped(multiChoiceModeListener);
    }

    public void invalidateViews() {
        this.mDataChanged = true;
        this.mItemCount = this.mAdapter.getCount();
        requestLayout();
        invalidate();
    }

    public int getCheckedItemCount() {
        return this.mCheckedItemCount;
    }

    public SparseBooleanArray getCheckedItemPositions() {
        if (this.mChoiceMode != 0) {
            return this.mCheckStates;
        }
        return null;
    }

    public boolean isItemChecked(int i) {
        if (this.mChoiceMode == 0 || this.mCheckStates == null) {
            return false;
        }
        return this.mCheckStates.get(i);
    }

    public long[] getCheckedItemIds() {
        int i = 0;
        if (this.mChoiceMode == 0 || this.mCheckedIdStates == null || this.mAdapter == null) {
            return new long[0];
        }
        LongSparseArray longSparseArray = this.mCheckedIdStates;
        int size = longSparseArray.size();
        long[] jArr = new long[size];
        while (i < size) {
            jArr[i] = longSparseArray.keyAt(i);
            i++;
        }
        return jArr;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    boolean performLongPress(View r10, int r11, long r12) {
        /*
        r9 = this;
        r8 = 0;
        r7 = 0;
        r2 = 1;
        r6 = 0;
        r0 = r9.mChoiceMode;
        r1 = 2;
        if (r0 != r1) goto L_0x00db;
    L_0x0009:
        r0 = r9.mChoiceActionMode;
        if (r0 != 0) goto L_0x001b;
    L_0x000d:
        r0 = r9.mChoiceActionMode;
        if (r0 != 0) goto L_0x00cf;
    L_0x0011:
        r0 = r9.mMultiChoiceModeCallback;
        r0 = r9.startActionMode(r0);
        r9.mChoiceActionMode = r0;
        if (r0 == 0) goto L_0x00cf;
    L_0x001b:
        r9.mDragAndDropPosition = r11;
        r0 = 16908289; // 0x1020001 float:2.3877232E-38 double:8.3538047E-317;
        r1 = r10.findViewById(r0);
        if (r1 == 0) goto L_0x0030;
    L_0x0026:
        r0 = r1 instanceof android.widget.Checkable;
        if (r0 == 0) goto L_0x0030;
    L_0x002a:
        r0 = r1;
        r0 = (android.widget.Checkable) r0;
        r0.setChecked(r2);
    L_0x0030:
        r0 = r9.mTouchFrame;
        if (r0 != 0) goto L_0x003d;
    L_0x0034:
        r0 = new android.graphics.Rect;
        r0.<init>();
        r9.mTouchFrame = r0;
        r0 = r9.mTouchFrame;
    L_0x003d:
        r10.getHitRect(r0);
        r3 = r9.mMotionX;
        r4 = r0.left;
        r3 = r3 - r4;
        r3 = java.lang.Math.max(r6, r3);
        r9.mDragOffsetX = r3;
        r3 = r9.mMotionY;
        r0 = r0.top;
        r0 = r3 - r0;
        r0 = java.lang.Math.max(r6, r0);
        r9.mDragOffsetY = r0;
        r10.setActivated(r6);
        r10.jumpDrawablesToCurrentState();
        r0 = r9.mDragEnable;
        if (r0 == 0) goto L_0x00d5;
    L_0x0061:
        r0 = r10 instanceof com.meizu.common.widget.EnhanceGallery.DragShadowItem;
        if (r0 == 0) goto L_0x00ad;
    L_0x0065:
        r0 = r10;
        r0 = (com.meizu.common.widget.EnhanceGallery.DragShadowItem) r0;
        r3 = new com.meizu.common.widget.EnhanceGallery$ListViewDragShadowBuilder;
        r4 = r0.getDragView();
        r5 = r0.needBackground();
        r0 = r0.getDragViewShowPosition();
        r3.<init>(r4, r5, r0);
        r9.mShadowBuilder = r3;
    L_0x007b:
        r0 = r9.mShadowBuilder;
        r0 = r9.startDragNow(r8, r0, r9, r6);
        if (r0 != 0) goto L_0x00b5;
    L_0x0083:
        if (r1 == 0) goto L_0x008e;
    L_0x0085:
        r0 = r1 instanceof android.widget.Checkable;
        if (r0 == 0) goto L_0x008e;
    L_0x0089:
        r1 = (android.widget.Checkable) r1;
        r1.setChecked(r6);
    L_0x008e:
        r0 = r9.mChoiceActionMode;
        r0.finish();
        r0 = -1;
        r9.mDragAndDropPosition = r0;
        r0 = r9.mPerformClick;
        if (r0 != 0) goto L_0x00a1;
    L_0x009a:
        r0 = new com.meizu.common.widget.EnhanceGallery$PerformClick;
        r0.<init>();
        r9.mPerformClick = r0;
    L_0x00a1:
        r0 = r9.mPerformClick;
        r0.mClickMotionPosition = r11;
        r0.rememberWindowAttachCount();
        r9.post(r0);
        r0 = r2;
    L_0x00ac:
        return r0;
    L_0x00ad:
        r0 = new com.meizu.common.widget.EnhanceGallery$ListViewDragShadowBuilder;
        r0.<init>(r9, r10);
        r9.mShadowBuilder = r0;
        goto L_0x007b;
    L_0x00b5:
        r9.performHapticFeedback(r6);
        r0 = r10 instanceof com.meizu.common.widget.EnhanceGallery.DragShadowItem;
        if (r0 == 0) goto L_0x00d1;
    L_0x00bc:
        r0 = r10;
        r0 = (com.meizu.common.widget.EnhanceGallery.DragShadowItem) r0;
        r0 = r0.getDragView();
        if (r0 == 0) goto L_0x00c8;
    L_0x00c5:
        r0.setAlpha(r7);
    L_0x00c8:
        r0 = r9.mChangeChildAlphaWhenDragView;
        if (r0 == 0) goto L_0x00cf;
    L_0x00cc:
        r10.setAlpha(r7);
    L_0x00cf:
        r0 = r2;
        goto L_0x00ac;
    L_0x00d1:
        r10.setAlpha(r7);
        goto L_0x00cf;
    L_0x00d5:
        r0 = r9.mDragAndDropPosition;
        r9.setItemChecked(r0, r2);
        goto L_0x00cf;
    L_0x00db:
        r0 = r9.mOnItemLongClickListener;
        if (r0 == 0) goto L_0x00fd;
    L_0x00df:
        r0 = r9.mOnItemLongClickListener;
        r1 = r9;
        r2 = r10;
        r3 = r11;
        r4 = r12;
        r0 = r0.onItemLongClick(r1, r2, r3, r4);
    L_0x00e9:
        if (r0 != 0) goto L_0x00f7;
    L_0x00eb:
        r0 = r9.createContextMenuInfo(r10, r11, r12);
        r0 = (com.meizu.common.widget.EnhanceGallery.AdapterContextMenuInfo) r0;
        r9.mContextMenuInfo = r0;
        r0 = super.showContextMenuForChild(r9);
    L_0x00f7:
        if (r0 == 0) goto L_0x00ac;
    L_0x00f9:
        r9.performHapticFeedback(r6);
        goto L_0x00ac;
    L_0x00fd:
        r0 = r6;
        goto L_0x00e9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.common.widget.EnhanceGallery.performLongPress(android.view.View, int, long):boolean");
    }

    boolean startDragNow(ClipData clipData, DragShadowBuilder dragShadowBuilder, Object obj, int i) {
        return false;
    }

    public void setDragItemBackgroundResources(int[] iArr) {
        if (iArr != null) {
            if (iArr.length > 0) {
                this.mDragViewBackground = iArr[0];
            }
            if (iArr.length > 1) {
                this.mDragViewBackgroundFilter = iArr[1];
            }
            if (iArr.length > 2) {
                this.mDragViewBackgroundDelete = iArr[2];
            }
        }
    }

    public void changeChildAlphaWhenDragView(boolean z) {
        this.mChangeChildAlphaWhenDragView = z;
    }

    ContextMenuInfo createContextMenuInfo(View view, int i, long j) {
        return new AdapterContextMenuInfo(view, i, j);
    }

    public boolean performItemClicks(View view, int i, long j) {
        boolean z;
        if (this.mChoiceMode != 0) {
            boolean z2;
            if (this.mChoiceMode == 2 && this.mChoiceActionMode != null) {
                boolean z3 = !this.mCheckStates.get(i, false);
                this.mCheckStates.put(i, z3);
                if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                    if (z3) {
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(i), Integer.valueOf(i));
                    } else {
                        this.mCheckedIdStates.delete(this.mAdapter.getItemId(i));
                    }
                }
                if (z3) {
                    this.mCheckedItemCount++;
                } else {
                    this.mCheckedItemCount--;
                }
                if (this.mChoiceActionMode == null || this.mMultiChoiceModeCallback == null) {
                    z = true;
                } else {
                    this.mMultiChoiceModeCallback.onItemCheckedStateChanged(this.mChoiceActionMode, i, j, z3);
                    z = false;
                }
                z2 = true;
            } else if (this.mChoiceMode == 1) {
                if (!this.mCheckStates.get(i, false)) {
                    this.mCheckStates.clear();
                    this.mCheckStates.put(i, true);
                    if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                        this.mCheckedIdStates.clear();
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(i), Integer.valueOf(i));
                    }
                    this.mCheckedItemCount = 1;
                } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                    this.mCheckedItemCount = 0;
                }
                z2 = true;
                z = true;
            } else {
                z2 = false;
                z = true;
            }
            if (z2) {
                updateOnScreenCheckedViews();
            }
        } else {
            z = true;
        }
        if (!z || this.mOnItemClickListener == null) {
            return false;
        }
        if (view != null) {
            view.sendAccessibilityEvent(1);
        }
        this.mOnItemClickListener.onItemClick(this, view, i, j);
        return true;
    }

    public void setItemChecked(int i, boolean z) {
        if (this.mChoiceMode != 0) {
            if (z && this.mChoiceActionMode == null && this.mChoiceMode == 2) {
                if (this.mMultiChoiceModeCallback == null || !this.mMultiChoiceModeCallback.hasWrappedCallback()) {
                    throw new IllegalStateException("StaggeredGridView: attempted to start selection mode for CHOICE_MODE_MULTIPLE_MODAL but no choice mode callback was supplied. Call setMultiChoiceModeListener to set a callback.");
                }
                this.mChoiceActionMode = startActionMode(this.mMultiChoiceModeCallback);
            }
            boolean z2;
            if (this.mChoiceMode == 2) {
                z2 = this.mCheckStates.get(i);
                this.mCheckStates.put(i, z);
                if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                    if (z) {
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(i), Integer.valueOf(i));
                    } else {
                        this.mCheckedIdStates.delete(this.mAdapter.getItemId(i));
                    }
                }
                if (z2 != z) {
                    if (z) {
                        this.mCheckedItemCount++;
                    } else {
                        this.mCheckedItemCount--;
                    }
                }
                if (this.mChoiceActionMode != null) {
                    this.mMultiChoiceModeCallback.onItemCheckedStateChanged(this.mChoiceActionMode, i, this.mAdapter.getItemId(i), z);
                }
            } else {
                z2 = this.mCheckedIdStates != null && this.mAdapter.hasStableIds();
                if (z || isItemChecked(i)) {
                    this.mCheckStates.clear();
                    if (z2) {
                        this.mCheckedIdStates.clear();
                    }
                }
                if (z) {
                    this.mCheckStates.put(i, true);
                    if (z2) {
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(i), Integer.valueOf(i));
                    }
                    this.mCheckedItemCount = 1;
                } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                    this.mCheckedItemCount = 0;
                }
            }
            if (!this.mInLayout) {
                invalidateViews();
            }
        }
    }

    void confirmCheckedPositionsById() {
        this.mCheckStates.clear();
        int i = 0;
        boolean z = false;
        while (i < this.mCheckedIdStates.size()) {
            int max;
            boolean z2;
            long keyAt = this.mCheckedIdStates.keyAt(i);
            int intValue = ((Integer) this.mCheckedIdStates.valueAt(i)).intValue();
            long j = -1;
            if (intValue < this.mItemCount) {
                j = this.mAdapter.getItemId(intValue);
            }
            if (intValue >= this.mItemCount || keyAt != r8) {
                boolean z3;
                int min = Math.min(intValue + 20, this.mItemCount);
                for (max = Math.max(0, intValue - 20); max < min; max++) {
                    if (keyAt == this.mAdapter.getItemId(max)) {
                        this.mCheckStates.put(max, true);
                        this.mCheckedIdStates.setValueAt(i, Integer.valueOf(max));
                        z3 = true;
                        break;
                    }
                }
                z3 = false;
                if (!z3) {
                    this.mCheckedIdStates.delete(keyAt);
                    max = i - 1;
                    this.mCheckedItemCount--;
                    if (!(this.mChoiceActionMode == null || this.mMultiChoiceModeCallback == null)) {
                        this.mMultiChoiceModeCallback.onItemCheckedStateChanged(this.mChoiceActionMode, intValue, keyAt, false);
                    }
                    i = max;
                    z = true;
                }
                max = i;
                z2 = z;
            } else {
                this.mCheckStates.put(intValue, true);
                max = i;
                z2 = z;
            }
            z = z2;
            i = max + 1;
        }
        if (z && this.mChoiceActionMode != null) {
            this.mChoiceActionMode.invalidate();
        }
    }

    private void updateOnScreenCheckedViews() {
        int i;
        int i2 = 0;
        int i3 = this.mFirstPosition;
        int childCount = getChildCount();
        if (getContext().getApplicationInfo().targetSdkVersion >= 11) {
            i = 1;
        } else {
            i = 0;
        }
        while (i2 < childCount) {
            View childAt = getChildAt(i2);
            int i4 = i3 + i2;
            if (childAt instanceof Checkable) {
                ((Checkable) childAt).setChecked(this.mCheckStates.get(i4));
            } else if (i != 0) {
                childAt.setActivated(this.mCheckStates.get(i4));
            }
            i2++;
        }
    }

    public void onInitializeAccessibilityNodeInfoForItem(View view, int i, AccessibilityNodeInfo accessibilityNodeInfo) {
        SpinnerAdapter adapter = getAdapter();
        if (i != -1 && adapter != null) {
            if (i == getSelectedItemPosition()) {
                accessibilityNodeInfo.setSelected(true);
                accessibilityNodeInfo.addAction(8);
            } else {
                accessibilityNodeInfo.addAction(4);
            }
            if (isFocusable()) {
                accessibilityNodeInfo.addAction(64);
                accessibilityNodeInfo.setFocusable(true);
            }
            if (isClickable()) {
                accessibilityNodeInfo.addAction(16);
                accessibilityNodeInfo.setClickable(true);
            }
            if (isLongClickable()) {
                accessibilityNodeInfo.addAction(32);
                accessibilityNodeInfo.setLongClickable(true);
            }
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setCollectionInfo(CollectionInfo.obtain(1, getCount(), false, 1));
        accessibilityNodeInfo.setClassName(EnhanceGallery.class.getName());
    }
}
