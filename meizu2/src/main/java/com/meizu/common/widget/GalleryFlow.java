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
import cn.com.xy.sms.sdk.ui.popu.util.XySdkUtil;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.meizu.common.R;
import com.meizu.common.widget.AdapterView.AdapterContextMenuInfo;

public class GalleryFlow extends AbsSpinner implements OnGestureListener {
    private static final int AREA_0 = 0;
    private static final int AREA_1 = 1;
    private static final int AREA_2 = 2;
    private static final int AREA_3 = 3;
    private static final int AREA_4 = 4;
    private static final int AREA_5 = 5;
    private static final int AREA_COUNT = 6;
    private static final float FACTOR = 0.083333336f;
    private static final int PIC_NUMS = 5;
    private static final int PIC_NUMS_OF_SIDE = 2;
    private static final int SCROLL_TO_FLING_UNCERTAINTY_TIMEOUT = 250;
    private static final String TAG = "GalleryFlow";
    private static final boolean localLOGV = false;
    private int[][] AREA_RANGE;
    private int[] AREA_RANGE_LENGTH;
    private int mAnimationDuration;
    private int mCenterOfGalleryFlow;
    private boolean mCirculate;
    private AdapterContextMenuInfo mContextMenuInfo;
    private int mDelta_1;
    private int mDelta_2;
    private Runnable mDisableSuppressSelectionChangedRunnable;
    private int mDownTouchPosition;
    private View mDownTouchView;
    private FlingRunnable mFlingRunnable;
    private GestureDetector mGestureDetector;
    private int mGravity;
    private int mHalfWidth;
    private boolean mHasInitial;
    private int mHeight;
    private boolean mIsFirstScroll;
    private int mLeftMost;
    private int mLength;
    private Matrix mMatrix;
    private int mPicHeightMid;
    private int mPicLengthMid;
    private int mPicLengthS1;
    private int mPicLengthS2;
    private int mPicLengthS3;
    private boolean mReceivedInvokeKeyDown;
    private int mRightMost;
    private int mSelectedCenterOffset;
    private View mSelectedChild;
    private boolean mShouldCallbackDuringFling;
    private boolean mShouldCallbackOnUnselectedItemClick;
    private boolean mShouldStopFling;
    private boolean mSuppressSelectionChanged;
    private int mWidth;

    class FlingRunnable implements Runnable {
        private int mLastFlingX;
        private OverScroller mScroller;

        public FlingRunnable() {
            this.mScroller = new OverScroller(GalleryFlow.this.getContext());
            this.mScroller.setEnableMZOverScroll(true, true);
        }

        private void startCommon() {
            GalleryFlow.this.removeCallbacks(this);
        }

        public void startUsingVelocity(int i) {
            int i2 = 4200;
            if (i != 0 && Math.abs(i) >= XySdkUtil.DUOQU_BUBBLE_DATA_CACHE_SIZE) {
                int i3;
                startCommon();
                if (Math.abs(i) > 4200) {
                    if (i <= 0) {
                        i2 = -4200;
                    }
                    i3 = i2;
                } else {
                    i3 = i;
                }
                int i4 = i3 < 0 ? ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED : 0;
                this.mLastFlingX = i4;
                this.mScroller.setInterpolator(null);
                this.mScroller.fling(i4, 0, i3, 0, 0, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, 0, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
                GalleryFlow.this.post(this);
            }
        }

        public void startUsingDistance(int i) {
            if (i != 0) {
                startCommon();
                this.mLastFlingX = 0;
                this.mScroller.setInterpolator(new DecelerateInterpolator());
                this.mScroller.startScroll(0, 0, -i, 0, GalleryFlow.this.mAnimationDuration);
                GalleryFlow.this.post(this);
            }
        }

        public void stop(boolean z) {
            GalleryFlow.this.removeCallbacks(this);
            endFling(z);
        }

        private void endFling(boolean z) {
            this.mScroller.forceFinished(true);
            if (z) {
                GalleryFlow.this.scrollIntoSlots();
            }
        }

        public void run() {
            if (GalleryFlow.this.mItemCount == 0) {
                endFling(true);
                return;
            }
            GalleryFlow.this.mShouldStopFling = false;
            OverScroller overScroller = this.mScroller;
            boolean computeScrollOffset = overScroller.computeScrollOffset();
            int currX = overScroller.getCurrX();
            int i = this.mLastFlingX - currX;
            if (i > 0) {
                i = Math.min(GalleryFlow.this.AREA_RANGE_LENGTH[3], i);
            } else {
                i = Math.max(-GalleryFlow.this.AREA_RANGE_LENGTH[3], i);
            }
            if (i != 0) {
                GalleryFlow.this.trackMotionScroll(i);
            }
            if (!computeScrollOffset || GalleryFlow.this.mShouldStopFling) {
                endFling(true);
                return;
            }
            this.mLastFlingX = currX;
            GalleryFlow.this.post(this);
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

    public GalleryFlow(Context context) {
        this(context, null);
    }

    public GalleryFlow(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_GalleryFlowStyle);
    }

    public GalleryFlow(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDelta_1 = 90;
        this.mDelta_2 = 30;
        this.mAnimationDuration = 350;
        this.mFlingRunnable = new FlingRunnable();
        this.mDisableSuppressSelectionChangedRunnable = new Runnable() {
            public void run() {
                GalleryFlow.this.mSuppressSelectionChanged = false;
                GalleryFlow.this.selectionChanged();
            }
        };
        this.mShouldCallbackDuringFling = true;
        this.mShouldCallbackOnUnselectedItemClick = true;
        this.mMatrix = new Matrix();
        this.mCirculate = false;
        this.mGestureDetector = new GestureDetector(context, this);
        this.mGestureDetector.setIsLongpressEnabled(true);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.GalleryFlow, i, 0);
        this.mCirculate = obtainStyledAttributes.getBoolean(R.styleable.GalleryFlow_mcCirculate, false);
        this.mDelta_1 = getResources().getDimensionPixelSize(R.dimen.mc_galleryflow_delta_1);
        this.mDelta_2 = getResources().getDimensionPixelSize(R.dimen.mc_galleryflow_delta_2);
        initialData(obtainStyledAttributes.getDimensionPixelSize(R.styleable.GalleryFlow_mcPicSize, 300));
        obtainStyledAttributes.recycle();
        this.mHasInitial = true;
        setChildrenDrawingOrderEnabled(true);
    }

    private void initialData(int i) {
        this.mPicLengthMid = i / 2;
        this.mPicHeightMid = i;
        this.mLength = Math.round(((float) this.mPicLengthMid) / FACTOR);
        this.mPicLengthS1 = Math.round(((float) ((this.mLength - this.mPicLengthMid) - this.mDelta_1)) * FACTOR);
        this.mPicLengthS2 = Math.round(((float) ((((this.mLength - this.mPicLengthMid) - this.mDelta_1) - this.mPicLengthS1) - this.mDelta_2)) * FACTOR);
        this.mPicLengthS3 = this.mPicLengthS2 - 15;
        this.mHalfWidth = ((this.mPicLengthMid + this.mDelta_1) + this.mPicLengthS1) + this.mDelta_2;
        this.mWidth = ((this.mHalfWidth + this.mPicLengthS2) + 23) * 2;
        this.mHeight = this.mPicHeightMid;
        this.AREA_RANGE_LENGTH = new int[]{1, this.mPicLengthS1 + this.mDelta_2, this.mPicLengthMid + this.mDelta_1, this.mPicLengthMid + this.mDelta_1, this.mPicLengthS1 + this.mDelta_2, 1};
    }

    public void setCenterPicSize(int i) {
        if (i > 0 && i != this.mPicHeightMid) {
            initialData(i);
            requestLayout();
        }
    }

    public void setCallbackDuringFling(boolean z) {
        this.mShouldCallbackDuringFling = z;
    }

    public void setCallbackOnUnselectedItemClick(boolean z) {
        this.mShouldCallbackOnUnselectedItemClick = z;
    }

    public void setAnimationDuration(int i) {
        this.mAnimationDuration = i;
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

    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        int i = 2;
        super.setAdapter(spinnerAdapter);
        if (spinnerAdapter != null) {
            if (this.mItemCount <= 0 || 2 >= this.mItemCount) {
                i = this.mItemCount / 2;
            }
            i = getTransitionPosition(this.mCirculate, i);
            setSelectedPositionInt(i);
            setNextSelectedPositionInt(i);
            requestLayout();
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mInLayout = true;
        this.mCenterOfGalleryFlow = getCenterOfGalleryFlow();
        r0 = new int[6][];
        r0[0] = new int[]{(this.mCenterOfGalleryFlow - this.mHalfWidth) - 1, this.mCenterOfGalleryFlow - this.mHalfWidth};
        r0[1] = new int[]{this.mCenterOfGalleryFlow - this.mHalfWidth, (this.mCenterOfGalleryFlow - this.mPicLengthMid) - this.mDelta_1};
        r0[2] = new int[]{(this.mCenterOfGalleryFlow - this.mPicLengthMid) - this.mDelta_1, this.mCenterOfGalleryFlow};
        r0[3] = new int[]{this.mCenterOfGalleryFlow, (this.mCenterOfGalleryFlow + this.mPicLengthMid) + this.mDelta_1};
        r0[4] = new int[]{(this.mCenterOfGalleryFlow + this.mPicLengthMid) + this.mDelta_1, this.mCenterOfGalleryFlow + this.mHalfWidth};
        r0[5] = new int[]{this.mCenterOfGalleryFlow + this.mHalfWidth, (this.mCenterOfGalleryFlow + this.mHalfWidth) + 1};
        this.AREA_RANGE = r0;
        layout(0, false);
        this.mInLayout = false;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(resolveSizeAndState(this.mWidth, i, 0), resolveSizeAndState(this.mHeight, i2, 0));
    }

    int getChildHeight(View view) {
        return view.getMeasuredHeight();
    }

    void trackMotionScroll(int i) {
        if (getChildCount() != 0 && i != 0) {
            boolean z = i < 0;
            if (!this.mCirculate) {
                int limitedMotionScrollAmount = getLimitedMotionScrollAmount(z, i);
                if (limitedMotionScrollAmount != i) {
                    this.mFlingRunnable.endFling(false);
                    onFinishedMovement();
                }
                i = limitedMotionScrollAmount;
            }
            offsetChildrenLeftAndRight(i);
            detachOffScreenChildren(z);
            if (z) {
                fillToGalleryRightLtr();
            } else {
                fillToGalleryLeftLtr();
            }
            this.mRecycler.clear();
            setSelectionToCenterChild();
            View view = this.mSelectedChild;
            if (view != null) {
                this.mSelectedCenterOffset = ((view.getWidth() / 2) + view.getLeft()) - (getWidth() / 2);
            }
            onScrollChanged(0, 0, 0, 0);
            invalidate();
        }
    }

    private void correctRealPoint(int i) {
        if (i != -1) {
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                if (i2 != i) {
                    View childAt = getChildAt(i2);
                    childAt.offsetLeftAndRight(this.AREA_RANGE[getCurAreaNum(getCenterOfViewX(childAt))][0] - getCenterOfViewX(childAt));
                }
            }
        }
        requestLayout();
    }

    int getLimitedMotionScrollAmount(boolean z, int i) {
        View childAt = getChildAt((z ? this.mItemCount - 1 : 0) - this.mFirstPosition);
        if (childAt == null) {
            return i;
        }
        int centerOfViewX = getCenterOfViewX(childAt);
        int centerOfGalleryFlow = getCenterOfGalleryFlow();
        if (centerOfViewX == centerOfGalleryFlow) {
            return 0;
        }
        centerOfViewX = centerOfGalleryFlow - centerOfViewX;
        return z ? Math.max(centerOfViewX, i) : Math.min(centerOfViewX, i);
    }

    private void offsetChildrenLeftAndRight(int i) {
        if (i != 0) {
            int childCount;
            float f;
            int i2 = i < 0 ? 1 : 0;
            int childCount2 = getChildCount() / 2;
            int i3 = -1;
            int centerOfGalleryFlow = getCenterOfGalleryFlow();
            int i4;
            int centerOfViewX;
            View childAt;
            float f2;
            View childAt2;
            int round;
            if (i2 != 0) {
                i4 = childCount2;
                childCount2 = Integer.MAX_VALUE;
                childCount = getChildCount() - 1;
                while (childCount >= 0) {
                    centerOfViewX = getCenterOfViewX(getChildAt(childCount)) - centerOfGalleryFlow;
                    if (centerOfViewX < 0 || centerOfViewX >= r5) {
                        break;
                    }
                    i4 = childCount;
                    childCount--;
                    childCount2 = centerOfViewX;
                }
                childAt = getChildAt(i4);
                if (getCurAreaNum(getCenterOfViewX(childAt) + i) == 3) {
                    f2 = ((float) i) / (((float) this.AREA_RANGE_LENGTH[3]) * 1.0f);
                    for (childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                        childAt2 = getChildAt(childCount);
                        i4 = getCurAreaNum(getCenterOfViewX(childAt2));
                        if (childCount != getChildCount() - 1 || i4 != 5) {
                            round = Math.round(((float) this.AREA_RANGE_LENGTH[i4]) * f2);
                            childAt2.offsetLeftAndRight(round);
                            if (getCurAreaNum(getCenterOfViewX(childAt2)) != i4) {
                                childAt2.offsetLeftAndRight(-round);
                            }
                            if (getCenterOfViewX(childAt2) == centerOfGalleryFlow) {
                                i3 = childCount;
                            }
                        }
                    }
                    childCount = i3;
                    f = f2;
                } else {
                    f2 = ((float) ((getCenterOfViewX(childAt) + i) - centerOfGalleryFlow)) / (((float) this.AREA_RANGE_LENGTH[2]) * 1.0f);
                    childCount = -1;
                    for (i3 = 0; i3 <= getChildCount() - 1; i3++) {
                        childAt2 = getChildAt(i3);
                        i4 = getCurAreaNum(getCenterOfViewX(childAt2));
                        if (i4 == 1) {
                            childAt2.offsetLeftAndRight(this.AREA_RANGE[1][0] - getCenterOfViewX(childAt2));
                        } else if (i3 == getChildCount() - 1 && i4 == 5) {
                        } else {
                            childAt2.offsetLeftAndRight((this.AREA_RANGE[i4][0] - getCenterOfViewX(childAt2)) + Math.round(((float) this.AREA_RANGE_LENGTH[i4 - 1]) * f2));
                        }
                        if (getCenterOfViewX(childAt2) == centerOfGalleryFlow) {
                            childCount = i3;
                        }
                    }
                    f = f2;
                }
            } else {
                i4 = childCount2;
                childCount2 = Integer.MAX_VALUE;
                childCount = 0;
                while (childCount <= getChildCount() - 1) {
                    centerOfViewX = centerOfGalleryFlow - getCenterOfViewX(getChildAt(childCount));
                    if (centerOfViewX < 0 || centerOfViewX >= r5) {
                        break;
                    }
                    i4 = childCount;
                    childCount++;
                    childCount2 = centerOfViewX;
                }
                childAt = getChildAt(i4);
                if (getCenterOfViewX(childAt) == centerOfGalleryFlow || getCurAreaNum(getCenterOfViewX(childAt) + i) == 2) {
                    f2 = ((float) i) / (((float) this.AREA_RANGE_LENGTH[2]) * 1.0f);
                    childCount = -1;
                    i3 = 0;
                    while (i3 <= getChildCount() - 1) {
                        childAt2 = getChildAt(i3);
                        i4 = getCurAreaNum(getCenterOfViewX(childAt2));
                        if (i4 != 1 || i3 != 0) {
                            round = Math.round(((float) this.AREA_RANGE_LENGTH[i4]) * f2);
                            childAt2.offsetLeftAndRight(round);
                            if (getCurAreaNum(getCenterOfViewX(childAt2)) != i4) {
                                childAt2.offsetLeftAndRight(-round);
                            }
                            if (getCenterOfViewX(childAt2) == centerOfGalleryFlow) {
                                childCount = i3;
                            }
                        }
                        i3++;
                    }
                    f = f2;
                } else {
                    float centerOfViewX2 = ((float) ((getCenterOfViewX(childAt) + i) - centerOfGalleryFlow)) / (((float) this.AREA_RANGE_LENGTH[3]) * 1.0f);
                    childCount2 = getChildCount() - 1;
                    while (childCount2 >= 0) {
                        childAt2 = getChildAt(childCount2);
                        i4 = getCurAreaNum(getCenterOfViewX(childAt2));
                        if (i4 == 4) {
                            childAt2.offsetLeftAndRight(this.AREA_RANGE[5][0] - getCenterOfViewX(childAt2));
                        } else if (!((childCount2 == 0 && i4 == 1) || (childCount2 == getChildCount() - 1 && i4 == 5))) {
                            childAt2.offsetLeftAndRight((this.AREA_RANGE[i4][1] - getCenterOfViewX(childAt2)) + Math.round(((float) this.AREA_RANGE_LENGTH[i4 + 1]) * centerOfViewX2));
                        }
                        childCount2--;
                    }
                    float f3 = centerOfViewX2;
                    childCount = -1;
                    f = f3;
                }
            }
            View childAt3;
            if (i2 != 0) {
                childAt3 = getChildAt(getChildCount() - 1);
                if (getCurAreaNum(getCenterOfViewX(childAt3)) == 5 && getCurAreaNum(getCenterOfViewX(getChildAt(getChildCount() - 2))) == 3) {
                    childAt3.offsetLeftAndRight(Math.round(((float) this.AREA_RANGE_LENGTH[4]) * f) + (this.AREA_RANGE[5][0] - getCenterOfViewX(childAt3)));
                }
            } else {
                childAt3 = getChildAt(0);
                if (getCurAreaNum(getCenterOfViewX(childAt3)) == 1 && getCurAreaNum(getCenterOfViewX(getChildAt(1))) == 2) {
                    int centerOfViewX3 = (getCenterOfViewX(childAt3) - this.AREA_RANGE[1][0]) + Math.round(((float) this.AREA_RANGE_LENGTH[1]) * f);
                    childAt3.offsetLeftAndRight(Math.round(((float) this.AREA_RANGE_LENGTH[1]) * f));
                }
            }
            if (childCount != -1) {
                correctRealPoint(childCount);
            }
        }
    }

    private float getCurLengthFromX(int i) {
        int abs = Math.abs(getCenterOfGalleryFlow() - i);
        if (abs > this.mHalfWidth) {
            return (float) (this.mPicLengthS2 * 2);
        }
        return (((float) (this.mLength - abs)) * FACTOR) * CircleProgressBar.BAR_WIDTH_DEF_DIP;
    }

    protected boolean drawChild(Canvas canvas, View view, long j) {
        Rect rect = new Rect();
        scaleView(view);
        int save = canvas.save();
        canvas.concat(this.mMatrix);
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.restoreToCount(save);
        rect.setEmpty();
        view.getHitRect(rect);
        return drawChild;
    }

    private void scaleView(View view) {
        float curLengthFromX;
        int i;
        int centerOfViewX = getCenterOfViewX(view);
        int centerOfViewY = getCenterOfViewY(view);
        this.mMatrix.reset();
        View childAt;
        float centerOfViewX2;
        if (view == getChildAt(0)) {
            childAt = getChildAt(1);
            if (childAt == null || getCurAreaNum(getCenterOfViewX(childAt)) != 1) {
                curLengthFromX = getCurLengthFromX(getCenterOfViewX(view));
                i = 0;
            } else {
                centerOfViewX2 = ((float) (getCenterOfViewX(childAt) - this.AREA_RANGE[1][0])) / (((float) this.AREA_RANGE_LENGTH[1]) * 1.0f);
                curLengthFromX = (BitmapDescriptorFactory.HUE_ORANGE * centerOfViewX2) + ((float) (this.mPicLengthS3 * 2));
                i = Math.round(BitmapDescriptorFactory.HUE_CYAN - (centerOfViewX2 * 102.0f));
            }
        } else if (view == getChildAt(getChildCount() - 1)) {
            childAt = getChildAt(getChildCount() - 2);
            if (childAt == null || getCurAreaNum(getCenterOfViewX(childAt)) != 4) {
                curLengthFromX = getCurLengthFromX(getCenterOfViewX(view));
                i = 0;
            } else {
                centerOfViewX2 = ((float) (this.AREA_RANGE[5][0] - getCenterOfViewX(childAt))) / (((float) this.AREA_RANGE_LENGTH[4]) * 1.0f);
                curLengthFromX = (BitmapDescriptorFactory.HUE_ORANGE * centerOfViewX2) + ((float) (this.mPicLengthS3 * 2));
                i = Math.round(BitmapDescriptorFactory.HUE_CYAN - (centerOfViewX2 * 102.0f));
            }
        } else {
            curLengthFromX = getCurLengthFromX(getCenterOfViewX(view));
            i = 0;
        }
        this.mMatrix.postScale(curLengthFromX / ((float) this.mPicHeightMid), curLengthFromX / ((float) this.mPicHeightMid));
        this.mMatrix.preTranslate((float) (-centerOfViewX), (float) (-centerOfViewY));
        this.mMatrix.postTranslate((float) centerOfViewX, (float) centerOfViewY);
        TextView textView = (TextView) view.findViewById(R.id.mc_galleryflow_album_title);
        centerOfViewX = Math.abs(centerOfViewX - getCenterOfGalleryFlow());
        if (textView != null) {
            if (centerOfViewX < this.AREA_RANGE_LENGTH[2]) {
                textView.setVisibility(0);
                textView.setAlpha(1.0f - (((float) centerOfViewX) / (((float) this.AREA_RANGE_LENGTH[2]) * 1.0f)));
            } else {
                textView.setVisibility(8);
            }
        }
        if (view instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) view;
            Drawable foreground = frameLayout.getForeground();
            if (i == 0) {
                i = Math.round((110.0f * ((float) centerOfViewX)) / (((float) this.mHalfWidth) * 1.0f));
            }
            if (foreground != null) {
                foreground.setAlpha(i);
                frameLayout.setForeground(foreground);
            }
        }
    }

    private int getCenterOfGalleryFlow() {
        return (((getWidth() - getPaddingLeft()) - getPaddingRight()) / 2) + getPaddingLeft();
    }

    private static int getCenterOfViewX(View view) {
        return view.getLeft() + (view.getWidth() / 2);
    }

    private static int getCenterOfViewY(View view) {
        return view.getTop() + (view.getHeight() / 2);
    }

    private void detachOffScreenChildren(boolean z) {
        int i;
        int i2 = 0;
        int childCount = getChildCount();
        int i3 = this.mFirstPosition;
        int i4;
        View childAt;
        int i5;
        if (z) {
            i4 = 0;
            i = 0;
            while (i4 < childCount - 1) {
                childAt = getChildAt(i4);
                View childAt2 = getChildAt(i4 + 1);
                if (getCenterOfViewX(childAt) != this.AREA_RANGE[1][0] || getCenterOfViewX(childAt2) != this.AREA_RANGE[1][0]) {
                    break;
                }
                i5 = i + 1;
                this.mRecycler.put(i3 + i4, childAt);
                i4++;
                i = i5;
            }
        } else {
            i4 = 0;
            i = 0;
            for (i5 = childCount - 1; i5 >= 0; i5--) {
                View childAt3 = getChildAt(i5);
                childAt = getChildAt(i5 - 1);
                if (getCenterOfViewX(childAt3) != this.AREA_RANGE[5][0] || getCenterOfViewX(childAt) != this.AREA_RANGE[5][0]) {
                    break;
                }
                i4 = i + 1;
                this.mRecycler.put(i3 + i5, childAt3);
                i = i4;
                i4 = i5;
            }
            i2 = i4;
        }
        detachViewsFromParent(i2, i);
        if (z) {
            this.mFirstPosition = i + this.mFirstPosition;
            this.mFirstPosition = getTransitionPosition(this.mCirculate, this.mFirstPosition);
        }
    }

    private void scrollIntoSlots() {
        if (getChildCount() != 0 && this.mSelectedChild != null) {
            int centerOfGalleryFlow = getCenterOfGalleryFlow() - getCenterOfViewX(this.mSelectedChild);
            if (centerOfGalleryFlow != 0) {
                this.mFlingRunnable.startUsingDistance(centerOfGalleryFlow);
            } else {
                onFinishedMovement();
            }
        }
    }

    private void onFinishedMovement() {
        if (this.mSuppressSelectionChanged) {
            this.mSuppressSelectionChanged = false;
            super.selectionChanged();
        }
        this.mSelectedCenterOffset = 0;
        invalidate();
    }

    void selectionChanged() {
        if (!this.mSuppressSelectionChanged) {
            super.selectionChanged();
        }
    }

    private void setSelectionToCenterChild() {
        View view = this.mSelectedChild;
        if (this.mSelectedChild != null) {
            int centerOfGalleryFlow = getCenterOfGalleryFlow();
            if (view.getLeft() > centerOfGalleryFlow || view.getRight() < centerOfGalleryFlow) {
                int i = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                int i2 = 0;
                int childCount = getChildCount() - 1;
                while (childCount >= 0) {
                    View childAt = getChildAt(childCount);
                    if (childAt.getLeft() <= centerOfGalleryFlow && childAt.getRight() >= centerOfGalleryFlow) {
                        break;
                    }
                    int min = Math.min(Math.abs(childAt.getLeft() - centerOfGalleryFlow), Math.abs(childAt.getRight() - centerOfGalleryFlow));
                    if (min < i) {
                        i2 = childCount;
                    } else {
                        min = i;
                    }
                    childCount--;
                    i = min;
                }
                childCount = i2;
                i2 = this.mFirstPosition + childCount;
                if (i2 != this.mSelectedPosition) {
                    i2 = getTransitionPosition(this.mCirculate, i2);
                    setSelectedPositionInt(i2);
                    setNextSelectedPositionInt(i2);
                    checkSelectionChanged();
                }
            }
        }
    }

    void layout(int i, boolean z) {
        if (this.mDataChanged) {
            handleDataChanged();
        }
        if (this.mItemCount == 0) {
            resetList();
            return;
        }
        if (this.mNextSelectedPosition >= 0) {
            this.mNextSelectedPosition = getTransitionPosition(this.mCirculate, this.mNextSelectedPosition);
            setSelectedPositionInt(this.mNextSelectedPosition);
        }
        recycleAllViews();
        detachAllViewsFromParent();
        this.mRightMost = 0;
        this.mLeftMost = 0;
        this.mFirstPosition = this.mSelectedPosition;
        View makeAndAddView = makeAndAddView(this.mSelectedPosition, 0, 0, true);
        makeAndAddView.offsetLeftAndRight((getCenterOfGalleryFlow() - getCenterOfViewX(makeAndAddView)) + this.mSelectedCenterOffset);
        fillToGalleryRightLtr();
        fillToGalleryLeftLtr();
        this.mRecycler.clear();
        invalidate();
        checkSelectionChanged();
        this.mDataChanged = false;
        this.mNeedSync = false;
        this.mSelectedPosition = getTransitionPosition(this.mCirculate, this.mSelectedPosition);
        setNextSelectedPositionInt(this.mSelectedPosition);
        updateSelectedItemMetadata();
    }

    private void fillToGalleryLeftLtr() {
        View childAt = getChildAt(0);
        int curAreaNum;
        if (childAt != null) {
            int i = this.mFirstPosition - 1;
            curAreaNum = getCurAreaNum(getCenterOfViewX(childAt));
            float centerOfViewX = ((float) (getCenterOfViewX(childAt) - this.AREA_RANGE[curAreaNum][0])) / (((float) this.AREA_RANGE_LENGTH[curAreaNum]) * 1.0f);
            int i2 = curAreaNum - 1;
            View view = childAt;
            while (i2 >= 0) {
                if (this.mCirculate || i >= 0) {
                    int i3;
                    View view2;
                    if (i2 != 0) {
                        curAreaNum = this.AREA_RANGE[i2][0] + Math.round(((float) this.AREA_RANGE_LENGTH[i2]) * centerOfViewX);
                    } else if (getCenterOfViewX(view) > this.AREA_RANGE[1][0]) {
                        curAreaNum = this.AREA_RANGE[1][0];
                    } else {
                        i3 = i;
                        view2 = view;
                        curAreaNum = i3;
                        i2--;
                        i3 = curAreaNum;
                        view = view2;
                        i = i3;
                    }
                    childAt = makeAndAddView(i, i - this.mSelectedPosition, Math.round((float) (curAreaNum + this.mPicLengthMid)), false);
                    this.mFirstPosition = i;
                    curAreaNum = i - 1;
                    view2 = childAt;
                    i2--;
                    i3 = curAreaNum;
                    view = view2;
                    i = i3;
                } else {
                    return;
                }
            }
            return;
        }
        curAreaNum = (getRight() - getLeft()) - getPaddingRight();
        this.mShouldStopFling = true;
    }

    private void fillToGalleryRightLtr() {
        int childCount = getChildCount();
        View childAt = getChildAt(childCount - 1);
        if (childAt != null) {
            int i = this.mFirstPosition + childCount;
            childCount = getCurAreaNum(getCenterOfViewX(childAt));
            float centerOfViewX = ((float) (getCenterOfViewX(childAt) - this.AREA_RANGE[childCount][0])) / (((float) this.AREA_RANGE_LENGTH[childCount]) * 1.0f);
            int i2 = i;
            i = childCount + 1;
            while (i < 6) {
                if (this.mCirculate || i2 < this.mItemCount) {
                    if (i == 5) {
                        childCount = this.AREA_RANGE[i][0];
                    } else {
                        childCount = this.AREA_RANGE[i][0] + Math.round(((float) this.AREA_RANGE_LENGTH[i]) * centerOfViewX);
                    }
                    makeAndAddView(i2, i2 - this.mSelectedPosition, Math.round((float) (childCount - this.mPicLengthMid)), true);
                    i2++;
                    i++;
                } else {
                    return;
                }
            }
            return;
        }
        this.mFirstPosition = this.mItemCount - 1;
        getPaddingLeft();
        this.mShouldStopFling = true;
    }

    private int getCurAreaNum(int i) {
        int i2 = 0;
        while (i2 < 6) {
            if (i >= this.AREA_RANGE[i2][0] && i < this.AREA_RANGE[i2][1]) {
                return i2;
            }
            i2++;
        }
        return 0;
    }

    private View makeAndAddView(int i, int i2, int i3, boolean z) {
        View view;
        if (!this.mDataChanged) {
            view = this.mRecycler.get(i);
            if (view != null) {
                int left = view.getLeft();
                this.mRightMost = Math.max(this.mRightMost, view.getMeasuredWidth() + left);
                this.mLeftMost = Math.min(this.mLeftMost, left);
                setUpChild(view, i2, i3, z);
                return view;
            }
        }
        view = this.mAdapter.getView(getTransitionPosition(this.mCirculate, i), null, this);
        setUpChild(view, i2, i3, z);
        return view;
    }

    private int getTransitionPosition(boolean z, int i) {
        if (!z || !z || this.mItemCount == 0) {
            return i;
        }
        return i > 0 ? i % this.mItemCount : (this.mItemCount + (i % this.mItemCount)) % this.mItemCount;
    }

    private void setUpChild(View view, int i, int i2, boolean z) {
        boolean z2 = false;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = (LayoutParams) generateDefaultLayoutParams();
        } else if (layoutParams instanceof LayoutParams) {
            r0 = (LayoutParams) layoutParams;
        } else {
            r0 = (LayoutParams) generateLayoutParams(layoutParams);
        }
        int i3 = this.mPicHeightMid;
        layoutParams.width = i3;
        layoutParams.height = i3;
        view.setLayoutParams(layoutParams);
        if (z) {
            i3 = -1;
        } else {
            i3 = 0;
        }
        addViewInLayout(view, i3, layoutParams);
        if (i == 0) {
            z2 = true;
        }
        view.setSelected(z2);
        view.measure(ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mSpinnerPadding.left + this.mSpinnerPadding.right, layoutParams.width), ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mSpinnerPadding.top + this.mSpinnerPadding.bottom, layoutParams.height));
        i3 = calculateTop(view, true);
        int measuredHeight = i3 + view.getMeasuredHeight();
        int measuredWidth = view.getMeasuredWidth();
        if (z) {
            int i4 = measuredWidth + i2;
            measuredWidth = i2;
            i2 = i4;
        } else {
            measuredWidth = i2 - measuredWidth;
        }
        view.layout(measuredWidth, i3, i2, measuredHeight);
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
        if (this.mDownTouchView == null) {
            return false;
        }
        scrollToChild(this.mDownTouchPosition - this.mFirstPosition);
        if (this.mShouldCallbackOnUnselectedItemClick || this.mDownTouchPosition == this.mSelectedPosition) {
            int transitionPosition = getTransitionPosition(this.mCirculate, this.mDownTouchPosition);
            performItemClick(this.mDownTouchView, transitionPosition, this.mAdapter.getItemId(transitionPosition));
        }
        return true;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (!this.mShouldCallbackDuringFling) {
            removeCallbacks(this.mDisableSuppressSelectionChangedRunnable);
            if (!this.mSuppressSelectionChanged) {
                this.mSuppressSelectionChanged = true;
            }
        }
        this.mFlingRunnable.startUsingVelocity((int) (-f));
        return true;
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
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
        float f3 = ((double) (Math.abs(f) - ((float) this.AREA_RANGE_LENGTH[3]))) > 1.0E-4d ? f > 0.0f ? (float) this.AREA_RANGE_LENGTH[3] : (float) (-this.AREA_RANGE_LENGTH[3]) : f;
        trackMotionScroll(((int) f3) * -1);
        this.mIsFirstScroll = false;
        return true;
    }

    public boolean onDown(MotionEvent motionEvent) {
        this.mFlingRunnable.stop(false);
        this.mDownTouchPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
        this.mDownTouchView = getChildAt(getTransitionPosition(this.mCirculate, this.mDownTouchPosition - this.mFirstPosition));
        if (this.mDownTouchView != null) {
            this.mDownTouchView.setPressed(true);
        }
        this.mIsFirstScroll = true;
        return true;
    }

    public int pointToPosition(int i, int i2) {
        Rect rect = new Rect();
        int childCount = getChildCount();
        int i3;
        int centerOfViewX;
        int centerOfViewY;
        if (i >= getCenterOfGalleryFlow()) {
            for (i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getVisibility() == 0) {
                    centerOfViewX = getCenterOfViewX(childAt);
                    centerOfViewY = getCenterOfViewY(childAt);
                    int curLengthFromX = ((int) getCurLengthFromX(centerOfViewX)) / 2;
                    rect.set(centerOfViewX - curLengthFromX, centerOfViewY - curLengthFromX, centerOfViewX + curLengthFromX, centerOfViewY + curLengthFromX);
                    if (rect.contains(i, i2)) {
                        return i3 + this.mFirstPosition;
                    }
                }
            }
        } else {
            for (i3 = childCount - 1; i3 >= 0; i3--) {
                View childAt2 = getChildAt(i3);
                if (childAt2.getVisibility() == 0) {
                    centerOfViewY = getCenterOfViewX(childAt2);
                    childCount = getCenterOfViewY(childAt2);
                    centerOfViewX = ((int) getCurLengthFromX(centerOfViewY)) / 2;
                    rect.set(centerOfViewY - centerOfViewX, childCount - centerOfViewX, centerOfViewY + centerOfViewX, childCount + centerOfViewX);
                    if (rect.contains(i, i2)) {
                        return i3 + this.mFirstPosition;
                    }
                }
            }
        }
        return -1;
    }

    void onUp() {
        if (this.mFlingRunnable.mScroller.isFinished()) {
            scrollIntoSlots();
        }
        dispatchUnpress();
    }

    void onCancel() {
        onUp();
    }

    public void onLongPress(MotionEvent motionEvent) {
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
        if (!isPressed()) {
            return false;
        }
        return dispatchLongPress(getChildAt(getTransitionPosition(this.mCirculate, this.mSelectedPosition - this.mFirstPosition)), this.mSelectedPosition, this.mSelectedRowId);
    }

    private boolean dispatchLongPress(View view, int i, long j) {
        boolean onItemLongClick;
        if (this.mOnItemLongClickListener != null) {
            onItemLongClick = this.mOnItemLongClickListener.onItemLongClick(this, this.mDownTouchView, getTransitionPosition(this.mCirculate, this.mDownTouchPosition), j);
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

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return keyEvent.dispatch(this, null, null);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        switch (i) {
            case 21:
                if (movePrevious()) {
                    playSoundEffect(1);
                    return true;
                }
                break;
            case 22:
                if (moveNext()) {
                    playSoundEffect(3);
                    return true;
                }
                break;
            case 23:
            case 66:
                this.mReceivedInvokeKeyDown = true;
                break;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 23:
            case 66:
                if (this.mReceivedInvokeKeyDown && this.mItemCount > 0) {
                    dispatchPress(this.mSelectedChild);
                    postDelayed(new Runnable() {
                        public void run() {
                            GalleryFlow.this.dispatchUnpress();
                        }
                    }, (long) ViewConfiguration.getPressedStateDuration());
                    int i2 = this.mSelectedPosition - this.mFirstPosition;
                    int transitionPosition = getTransitionPosition(this.mCirculate, this.mSelectedPosition);
                    performItemClick(getChildAt(i2), transitionPosition, this.mAdapter.getItemId(transitionPosition));
                }
                this.mReceivedInvokeKeyDown = false;
                return true;
            default:
                return super.onKeyUp(i, keyEvent);
        }
    }

    boolean movePrevious() {
        if (this.mItemCount <= 0) {
            return false;
        }
        scrollToChild((this.mSelectedPosition - this.mFirstPosition) - 1);
        return true;
    }

    boolean moveNext() {
        if (this.mItemCount <= 0) {
            return false;
        }
        scrollToChild((this.mSelectedPosition - this.mFirstPosition) + 1);
        return true;
    }

    private boolean scrollToChild(int i) {
        View childAt = getChildAt(getTransitionPosition(this.mCirculate, i));
        if (childAt == null) {
            return false;
        }
        getChildCount();
        this.mFlingRunnable.startUsingDistance((3 - getCurAreaNum(getCenterOfViewX(childAt))) * this.AREA_RANGE_LENGTH[2]);
        return true;
    }

    void setSelectedPositionInt(int i) {
        super.setSelectedPositionInt(i);
        updateSelectedItemMetadata();
    }

    private void updateSelectedItemMetadata() {
        View view = this.mSelectedChild;
        View childAt = getChildAt(getTransitionPosition(this.mCirculate, this.mSelectedPosition - this.mFirstPosition));
        this.mSelectedChild = childAt;
        if (childAt != null) {
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

    protected int getChildDrawingOrder(int i, int i2) {
        int transitionPosition = getTransitionPosition(this.mCirculate, this.mSelectedPosition - this.mFirstPosition);
        if (transitionPosition < 0) {
            return i2;
        }
        if (i2 == i - 1) {
            return transitionPosition;
        }
        if (i2 >= transitionPosition) {
            return (i - 1) - (i2 - transitionPosition);
        }
        return i2;
    }

    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (z && this.mSelectedChild != null) {
            this.mSelectedChild.requestFocus(i);
            this.mSelectedChild.setSelected(true);
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(GalleryFlow.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        boolean z = true;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(GalleryFlow.class.getName());
        if (this.mItemCount <= 1) {
            z = false;
        }
        accessibilityNodeInfo.setScrollable(z);
        if (isEnabled()) {
            if (this.mItemCount > 0 && this.mSelectedPosition < this.mItemCount - 1) {
                accessibilityNodeInfo.addAction(4096);
            }
            if (isEnabled() && this.mItemCount > 0 && this.mSelectedPosition > 0) {
                accessibilityNodeInfo.addAction(8192);
            }
        }
    }

    public boolean performAccessibilityAction(int i, Bundle bundle) {
        if (super.performAccessibilityAction(i, bundle)) {
            return true;
        }
        switch (i) {
            case 4096:
                if (!isEnabled() || this.mItemCount <= 0 || this.mSelectedPosition >= this.mItemCount - 1) {
                    return false;
                }
                return scrollToChild((this.mSelectedPosition - this.mFirstPosition) + 1);
            case 8192:
                if (!isEnabled() || this.mItemCount <= 0 || this.mSelectedPosition <= 0) {
                    return false;
                }
                return scrollToChild((this.mSelectedPosition - this.mFirstPosition) - 1);
            default:
                return false;
        }
    }
}
