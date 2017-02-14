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

public class PinnedHeaderListView extends AutoScrollListView implements OnScrollListener, OnItemSelectedListener {
    private static final int BOTTOM = 1;
    private static final int DEFAULT_ANIMATION_DURATION = 20;
    private static final int FADING = 2;
    private static final int MAX_ALPHA = 255;
    private static final int TOP = 0;
    private PinnedHeaderAdapter mAdapter;
    private boolean mAnimating;
    private int mAnimationDuration;
    private long mAnimationTargetTime;
    private RectF mBounds;
    private Rect mClipRect;
    private Drawable mHeaderBackground;
    private int mHeaderPaddingLeft;
    private int mHeaderPaddingTop;
    private int mHeaderWidth;
    private PinnedHeader[] mHeaders;
    private OnItemSelectedListener mOnItemSelectedListener;
    private OnScrollListener mOnScrollListener;
    private int mScrollState;
    private int mSize;

    public interface PinnedHeaderAdapter {
        void configurePinnedHeaders(PinnedHeaderListView pinnedHeaderListView);

        int getPinnedHeaderCount();

        View getPinnedHeaderView(int i, View view, ViewGroup viewGroup);

        int getScrollPositionForHeader(int i);
    }

    static final class PinnedHeader {
        int alpha;
        boolean animating;
        int height;
        int sourceY;
        int state;
        long targetTime;
        boolean targetVisible;
        int targetY;
        View view;
        boolean visible;
        int y;

        private PinnedHeader() {
        }
    }

    public PinnedHeaderListView(Context context) {
        this(context, null, 16842868);
    }

    public PinnedHeaderListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842868);
    }

    public PinnedHeaderListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBounds = new RectF();
        this.mClipRect = new Rect();
        this.mAnimationDuration = 20;
        this.mHeaderBackground = null;
        super.setOnScrollListener(this);
        super.setOnItemSelectedListener(this);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mHeaderPaddingLeft = 0;
        this.mHeaderWidth = i3 - i;
    }

    public void setPinnedHeaderAnimationDuration(int i) {
        this.mAnimationDuration = i;
    }

    public void setAdapter(ListAdapter listAdapter) {
        this.mAdapter = (PinnedHeaderAdapter) listAdapter;
        super.setAdapter(listAdapter);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
        super.setOnScrollListener(this);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
        super.setOnItemSelectedListener(this);
    }

    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        int i4 = 0;
        if (this.mAdapter != null) {
            int pinnedHeaderCount = this.mAdapter.getPinnedHeaderCount();
            if (pinnedHeaderCount != this.mSize) {
                this.mSize = pinnedHeaderCount;
                if (this.mHeaders == null) {
                    this.mHeaders = new PinnedHeader[this.mSize];
                } else if (this.mHeaders.length < this.mSize) {
                    Object obj = this.mHeaders;
                    this.mHeaders = new PinnedHeader[this.mSize];
                    System.arraycopy(obj, 0, this.mHeaders, 0, obj.length);
                }
            }
            while (i4 < this.mSize) {
                if (this.mHeaders[i4] == null) {
                    this.mHeaders[i4] = new PinnedHeader();
                }
                this.mHeaders[i4].view = this.mAdapter.getPinnedHeaderView(i4, this.mHeaders[i4].view, this);
                if (!(this.mHeaderBackground == null || this.mHeaders[i4].view == null)) {
                    this.mHeaders[i4].view.setBackground(this.mHeaderBackground);
                }
                i4++;
            }
            this.mAnimationTargetTime = System.currentTimeMillis() + ((long) this.mAnimationDuration);
            this.mAdapter.configurePinnedHeaders(this);
            invalidateIfAnimating();
        }
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(this, i, i2, i3);
        }
    }

    protected float getTopFadingEdgeStrength() {
        return this.mSize > 0 ? 0.0f : super.getTopFadingEdgeStrength();
    }

    public void onScrollStateChanged(AbsListView absListView, int i) {
        this.mScrollState = i;
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScrollStateChanged(this, i);
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        int i2 = 0;
        int height = getHeight();
        int i3 = 0;
        while (i2 < this.mSize) {
            PinnedHeader pinnedHeader = this.mHeaders[i2];
            if (pinnedHeader.visible) {
                if (pinnedHeader.state == 0) {
                    i3 = pinnedHeader.y + pinnedHeader.height;
                } else if (pinnedHeader.state == 1) {
                    i2 = pinnedHeader.y;
                    break;
                }
            }
            i2++;
        }
        i2 = height;
        View selectedView = getSelectedView();
        if (selectedView != null) {
            if (selectedView.getTop() < i3) {
                setSelectionFromTop(i, i3);
            } else if (selectedView.getBottom() > i2) {
                setSelectionFromTop(i, i2 - selectedView.getHeight());
            }
        }
        if (this.mOnItemSelectedListener != null) {
            this.mOnItemSelectedListener.onItemSelected(adapterView, view, i, j);
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        if (this.mOnItemSelectedListener != null) {
            this.mOnItemSelectedListener.onNothingSelected(adapterView);
        }
    }

    public int getPinnedHeaderHeight(int i) {
        ensurePinnedHeaderLayout(i);
        return this.mHeaders[i].view.getHeight();
    }

    public void setHeaderPinnedAtTop(int i, int i2, boolean z) {
        ensurePinnedHeaderLayout(i);
        PinnedHeader pinnedHeader = this.mHeaders[i];
        pinnedHeader.visible = true;
        pinnedHeader.y = i2;
        pinnedHeader.state = 0;
        pinnedHeader.animating = false;
    }

    public void setHeaderPinnedAtBottom(int i, int i2, boolean z) {
        ensurePinnedHeaderLayout(i);
        PinnedHeader pinnedHeader = this.mHeaders[i];
        pinnedHeader.state = 1;
        if (pinnedHeader.animating) {
            pinnedHeader.targetTime = this.mAnimationTargetTime;
            pinnedHeader.sourceY = pinnedHeader.y;
            pinnedHeader.targetY = i2;
        } else if (!z || (pinnedHeader.y == i2 && pinnedHeader.visible)) {
            pinnedHeader.visible = true;
            pinnedHeader.y = i2;
        } else {
            if (pinnedHeader.visible) {
                pinnedHeader.sourceY = pinnedHeader.y;
            } else {
                pinnedHeader.visible = true;
                pinnedHeader.sourceY = pinnedHeader.height + i2;
            }
            pinnedHeader.animating = true;
            pinnedHeader.targetVisible = true;
            pinnedHeader.targetTime = this.mAnimationTargetTime;
            pinnedHeader.targetY = i2;
        }
    }

    public void setFadingHeader(int i, int i2, boolean z) {
        ensurePinnedHeaderLayout(i);
        if (getChildAt(i2 - getFirstVisiblePosition()) != null) {
            PinnedHeader pinnedHeader = this.mHeaders[i];
            pinnedHeader.visible = true;
            pinnedHeader.state = 2;
            pinnedHeader.alpha = 255;
            pinnedHeader.animating = false;
            pinnedHeader.y = getTotalTopPinnedHeaderHeight();
        }
    }

    public void setHeaderInvisible(int i, boolean z) {
        PinnedHeader pinnedHeader = this.mHeaders[i];
        if (pinnedHeader.visible && ((z || pinnedHeader.animating) && pinnedHeader.state == 1)) {
            pinnedHeader.sourceY = pinnedHeader.y;
            if (!pinnedHeader.animating) {
                pinnedHeader.visible = true;
                pinnedHeader.targetY = getBottom() + pinnedHeader.height;
            }
            pinnedHeader.animating = true;
            pinnedHeader.targetTime = this.mAnimationTargetTime;
            pinnedHeader.targetVisible = false;
            return;
        }
        pinnedHeader.visible = false;
    }

    private void ensurePinnedHeaderLayout(int i) {
        View view = this.mHeaders[i].view;
        if (view.isLayoutRequested()) {
            int makeMeasureSpec;
            int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(this.mHeaderWidth, 1073741824);
            LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null || layoutParams.height <= 0) {
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
            } else {
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824);
            }
            view.measure(makeMeasureSpec2, makeMeasureSpec);
            makeMeasureSpec = view.getMeasuredHeight();
            this.mHeaders[i].height = makeMeasureSpec;
            view.layout(0, 0, this.mHeaderWidth, makeMeasureSpec);
        }
    }

    public int getTotalTopPinnedHeaderHeight() {
        int i = this.mSize;
        while (true) {
            i--;
            if (i < 0) {
                return 0;
            }
            PinnedHeader pinnedHeader = this.mHeaders[i];
            if (pinnedHeader.visible && pinnedHeader.state == 0) {
                return pinnedHeader.y + pinnedHeader.height;
            }
        }
    }

    public int getPositionAt(int i) {
        int childCount = getChildCount();
        if (childCount <= 0) {
            return 0;
        }
        int i2;
        if (isStackFromBottom()) {
            for (i2 = childCount - 1; i2 >= 0; i2--) {
                if (i >= getChildAt(i2).getTop()) {
                    return getFirstVisiblePosition() + i2;
                }
            }
            return 0;
        }
        for (i2 = 0; i2 < childCount; i2++) {
            if (i <= getChildAt(i2).getBottom()) {
                return getFirstVisiblePosition() + i2;
            }
        }
        return 0;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }

    private boolean smoothScrollToPartition(int i) {
        int i2 = 0;
        int scrollPositionForHeader = this.mAdapter.getScrollPositionForHeader(i);
        if (scrollPositionForHeader == -1) {
            return false;
        }
        for (int i3 = 0; i3 < i; i3++) {
            PinnedHeader pinnedHeader = this.mHeaders[i3];
            if (pinnedHeader.visible) {
                i2 += pinnedHeader.height;
            }
        }
        smoothScrollToPositionFromTop(getHeaderViewsCount() + scrollPositionForHeader, i2);
        return true;
    }

    private void invalidateIfAnimating() {
        int i = 0;
        this.mAnimating = false;
        while (i < this.mSize) {
            if (this.mHeaders[i].animating) {
                this.mAnimating = true;
                invalidate();
                return;
            }
            i++;
        }
    }

    protected void dispatchDraw(Canvas canvas) {
        int i;
        int i2 = 0;
        long currentTimeMillis = this.mAnimating ? System.currentTimeMillis() : 0;
        int i3 = 0;
        int bottom = getBottom();
        int i4 = 0;
        for (i = 0; i < this.mSize; i++) {
            PinnedHeader pinnedHeader = this.mHeaders[i];
            if (pinnedHeader.visible) {
                if (pinnedHeader.state != 1 || pinnedHeader.y >= bottom) {
                    if (pinnedHeader.state == 0 || pinnedHeader.state == 2) {
                        i3 = pinnedHeader.y + pinnedHeader.height;
                        if (i3 > i4) {
                            i4 = i3;
                            i3 = 1;
                        }
                    }
                    i3 = 1;
                } else {
                    bottom = pinnedHeader.y;
                    i3 = 1;
                }
            }
        }
        if (i3 != 0) {
            canvas.save();
            this.mClipRect.set(0, 0, getWidth(), bottom);
            canvas.clipRect(this.mClipRect);
        }
        super.dispatchDraw(canvas);
        if (i3 != 0) {
            canvas.restore();
            i = this.mSize;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                PinnedHeader pinnedHeader2 = this.mHeaders[i];
                if (pinnedHeader2.visible && (pinnedHeader2.state == 0 || pinnedHeader2.state == 2)) {
                    drawHeader(canvas, pinnedHeader2, currentTimeMillis);
                }
            }
            while (i2 < this.mSize) {
                PinnedHeader pinnedHeader3 = this.mHeaders[i2];
                if (pinnedHeader3.visible && pinnedHeader3.state == 1) {
                    drawHeader(canvas, pinnedHeader3, currentTimeMillis);
                }
                i2++;
            }
        }
        invalidateIfAnimating();
    }

    private void drawHeader(Canvas canvas, PinnedHeader pinnedHeader, long j) {
        if (pinnedHeader.animating) {
            int i = (int) (pinnedHeader.targetTime - j);
            if (i <= 0) {
                pinnedHeader.y = pinnedHeader.targetY;
                pinnedHeader.visible = pinnedHeader.targetVisible;
                pinnedHeader.animating = false;
            } else {
                pinnedHeader.y = ((i * (pinnedHeader.sourceY - pinnedHeader.targetY)) / this.mAnimationDuration) + pinnedHeader.targetY;
            }
        }
        if (pinnedHeader.visible) {
            View view = pinnedHeader.view;
            int save = canvas.save();
            if (pinnedHeader.state == 0 || pinnedHeader.state == 2) {
                canvas.translate((float) this.mHeaderPaddingLeft, (float) (pinnedHeader.y + this.mHeaderPaddingTop));
            } else {
                canvas.translate((float) this.mHeaderPaddingLeft, (float) pinnedHeader.y);
            }
            if (pinnedHeader.state == 2) {
                this.mBounds.set(0.0f, 0.0f, (float) this.mHeaderWidth, (float) view.getHeight());
                canvas.saveLayerAlpha(this.mBounds, pinnedHeader.alpha, 31);
            }
            view.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    public void setSelection(int i) {
        if (this.mAdapter instanceof PinnedHeaderIndexerListAdapter) {
            PinnedHeaderIndexerListAdapter pinnedHeaderIndexerListAdapter = (PinnedHeaderIndexerListAdapter) this.mAdapter;
            if (!pinnedHeaderIndexerListAdapter.getItemPlacementInSection(i - getHeaderViewsCount()).firstInSection && pinnedHeaderIndexerListAdapter.isSectionHeaderDisplayEnabled() && this.mSize > 0) {
                super.setSelectionFromTop(i, getPinnedHeaderHeight(0));
                return;
            }
        }
        super.setSelection(i);
    }

    public int getCurrentOverScrollDistance() {
        if (getFirstVisiblePosition() != 0 || getChildCount() <= 0) {
            return 0;
        }
        return getPaddingTop() - getChildAt(0).getTop();
    }

    public void setHeaderPaddingTop(int i) {
        if (i >= 0) {
            this.mHeaderPaddingTop = i;
        }
    }

    public int getHeaderPaddingTop() {
        return this.mHeaderPaddingTop;
    }

    public void setHeaderBackground(Drawable drawable) {
        if (drawable != null && drawable != this.mHeaderBackground) {
            if (this.mHeaderBackground != null) {
                this.mHeaderBackground.setCallback(null);
                unscheduleDrawable(this.mHeaderBackground);
            }
            this.mHeaderBackground = drawable;
            this.mHeaderBackground.setCallback(this);
            requestLayout();
            invalidate();
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(PinnedHeaderListView.class.getName());
    }
}
