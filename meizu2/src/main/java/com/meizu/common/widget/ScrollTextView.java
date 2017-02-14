package com.meizu.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.GroundOverlayOptions;
import com.meizu.common.R;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class ScrollTextView extends View {
    private static final int DEF_VISIBLE_ITEMS = 5;
    private static final int DEF_YSCROLL_END = Integer.MAX_VALUE;
    private static String TAG = "ScrollTextView";
    boolean isCyclic;
    private boolean isScrollingPerformed;
    private Paint mBitmapPaint;
    private List<OnScrollTextViewChangedListener> mChangingListeners;
    private List<OnScrollTextViewClickedListener> mClickingListeners;
    private int mCurrentItem;
    private IDataAdapter mDataInterface;
    private float mFadingHeight;
    private Matrix mFadingMatrix;
    private Paint mFadingPaint;
    private Shader mFadingShader;
    private int mFirstItem;
    private float mFontMetricsCenterY;
    private boolean mIsBitmapChanged;
    private boolean mIsDrawFading;
    private float mNormalFontMetricsCenterY;
    private float mNormalItemHeight;
    private int mNormalTextColor;
    private float mNormalTextSize;
    private int mOffsetX;
    private int mOffsetY;
    private VisibleItemsRange mRange;
    private ScrollingListener mScrollingListener;
    private List<OnScrollTextViewScrollListener> mScrollingListeners;
    private int mScrollingOffset;
    private float mSelectFontMetricsCenterY;
    private float mSelectItemHeight;
    private int mSelectTextColor;
    private float mSelectTextSize;
    private Paint mTextPaint;
    private Bitmap mTmpBitmap;
    private Canvas mTmpCanvas;
    private ScrollTextViewAdapter mViewAdapter;
    private int mVisibleItems;
    private ScrollTextViewScroller mWheelScroller;

    public interface OnScrollTextViewScrollListener {
        void onScrollingFinished(ScrollTextView scrollTextView);

        void onScrollingStarted(ScrollTextView scrollTextView);
    }

    public interface IDataAdapter {
        String getItemText(int i);

        void onChanged(View view, int i, int i2);
    }

    interface ScrollingListener {
        void onFinished();

        void onJustify();

        void onScroll(int i);

        void onStarted();
    }

    static class AnimationHandler extends Handler {
        private final WeakReference<ScrollTextViewScroller> mScrollTextViewScroller;

        public AnimationHandler(ScrollTextViewScroller scrollTextViewScroller) {
            this.mScrollTextViewScroller = new WeakReference(scrollTextViewScroller);
        }

        public void handleMessage(Message message) {
            ScrollTextViewScroller scrollTextViewScroller = (ScrollTextViewScroller) this.mScrollTextViewScroller.get();
            if (scrollTextViewScroller != null) {
                scrollTextViewScroller.scroller.computeScrollOffset();
                int currY = scrollTextViewScroller.scroller.getCurrY();
                int access$600 = scrollTextViewScroller.lastScrollY - currY;
                scrollTextViewScroller.lastScrollY = currY;
                if (access$600 != 0) {
                    scrollTextViewScroller.listener.onScroll(access$600);
                }
                if (Math.abs(currY - scrollTextViewScroller.scroller.getFinalY()) < 1) {
                    scrollTextViewScroller.scroller.getFinalY();
                    scrollTextViewScroller.scroller.forceFinished(true);
                }
                if (!scrollTextViewScroller.scroller.isFinished()) {
                    scrollTextViewScroller.animationHandler.sendEmptyMessage(message.what);
                } else if (message.what == 0) {
                    scrollTextViewScroller.justify();
                } else {
                    scrollTextViewScroller.finishScrolling();
                }
            }
        }
    }

    public interface OnScrollTextViewChangedListener {
        void onChanged(ScrollTextView scrollTextView, int i, int i2);
    }

    public interface OnScrollTextViewClickedListener {
        void onItemClicked(ScrollTextView scrollTextView, int i);
    }

    class ScrollTextViewAdapter {
        public static final int DEFAULT_MAX_VALUE = 9;
        private static final int DEFAULT_MIN_VALUE = 0;
        private int count;
        private int validEnd;
        private int validStart;

        public ScrollTextViewAdapter(ScrollTextView scrollTextView) {
            this(10, 0, 9);
        }

        public ScrollTextViewAdapter(ScrollTextView scrollTextView, int i, int i2) {
            this((i2 - i) + 1, i, i2);
        }

        public ScrollTextViewAdapter(int i, int i2, int i3) {
            this.validStart = 0;
            this.validEnd = 0;
            this.count = 0;
            update(i, i2, i3);
        }

        public String getItemText(int i) {
            if (i < 0 || i >= getItemsCount() || ScrollTextView.this.mDataInterface == null) {
                return null;
            }
            return ScrollTextView.this.mDataInterface.getItemText(i);
        }

        public void setItemCount(int i) {
            this.count = i;
        }

        public int getItemsCount() {
            return this.count;
        }

        public ScrollTextViewAdapter update(int i, int i2, int i3) {
            this.validStart = i2;
            this.validEnd = i3;
            this.count = i;
            return this;
        }

        public int getValidStart() {
            return this.validStart;
        }

        public int getValidEnd() {
            return this.validEnd;
        }
    }

    class ScrollTextViewScroller {
        public static final int MIN_DELTA_FOR_SCROLLING = 1;
        private static final int SCROLLING_DURATION = 400;
        private final int MESSAGE_JUSTIFY = 1;
        private final int MESSAGE_SCROLL = 0;
        private Handler animationHandler = new AnimationHandler(this);
        private Context context;
        private GestureDetector gestureDetector;
        private SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                return true;
            }

            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                ScrollTextViewScroller.this.lastScrollY = 0;
                int access$700 = ScrollTextView.this.getYScrollEnd();
                int i = (int) (-f2);
                ScrollTextViewScroller.this.scroller.fling(0, ScrollTextViewScroller.this.lastScrollY, 0, i, 0, 0, ScrollTextView.this.getYScrollStart(), access$700);
                ScrollTextViewScroller.this.setNextMessage(0);
                return true;
            }
        };
        private boolean isScrollingPerformed;
        private int lastScrollY;
        private float lastTouchedY;
        private ScrollingListener listener;
        private Scroller scroller;

        public ScrollTextViewScroller(Context context, ScrollingListener scrollingListener) {
            this.gestureDetector = new GestureDetector(context, this.gestureListener);
            this.gestureDetector.setIsLongpressEnabled(false);
            this.scroller = new Scroller(context);
            this.listener = scrollingListener;
            this.context = context;
        }

        public void setInterpolator(Interpolator interpolator) {
            this.scroller.forceFinished(true);
            this.scroller = new Scroller(this.context, interpolator);
        }

        public void scroll(int i, int i2) {
            this.scroller.forceFinished(true);
            this.lastScrollY = 0;
            this.scroller.startScroll(0, 0, 0, i, i2 != 0 ? i2 : 400);
            setNextMessage(0);
            startScrolling();
        }

        public void stopScrolling() {
            this.scroller.forceFinished(true);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case 0:
                    this.lastTouchedY = motionEvent.getY();
                    this.scroller.forceFinished(true);
                    clearMessages();
                    break;
                case 2:
                    int y = (int) (motionEvent.getY() - this.lastTouchedY);
                    if (y != 0) {
                        startScrolling();
                        this.listener.onScroll(y);
                        this.lastTouchedY = motionEvent.getY();
                        break;
                    }
                    break;
            }
            if (!this.gestureDetector.onTouchEvent(motionEvent) && motionEvent.getAction() == 1) {
                justify();
            }
            return true;
        }

        private void setNextMessage(int i) {
            clearMessages();
            this.animationHandler.sendEmptyMessage(i);
        }

        private void clearMessages() {
            this.animationHandler.removeMessages(0);
            this.animationHandler.removeMessages(1);
        }

        private void justify() {
            this.listener.onJustify();
            setNextMessage(1);
        }

        private void startScrolling() {
            if (!this.isScrollingPerformed) {
                this.isScrollingPerformed = true;
                this.listener.onStarted();
            }
        }

        void finishScrolling() {
            if (this.isScrollingPerformed) {
                this.listener.onFinished();
                this.isScrollingPerformed = false;
            }
        }
    }

    class VisibleItemsRange {
        private int count;
        private int first;

        public VisibleItemsRange(ScrollTextView scrollTextView) {
            this(0, 0);
        }

        public VisibleItemsRange(int i, int i2) {
            this.first = i;
            this.count = i2;
        }

        public int getFirst() {
            return this.first;
        }

        public int getLast() {
            return (getFirst() + getCount()) - 1;
        }

        public int getCount() {
            return this.count;
        }

        public VisibleItemsRange update(int i, int i2) {
            this.first = i;
            this.count = i2;
            return this;
        }
    }

    public ScrollTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTextPaint = new Paint(1);
        this.mCurrentItem = 0;
        this.mVisibleItems = 5;
        this.mSelectItemHeight = 0.0f;
        this.mNormalItemHeight = 0.0f;
        this.isCyclic = false;
        this.mIsDrawFading = true;
        this.mChangingListeners = new LinkedList();
        this.mScrollingListeners = new LinkedList();
        this.mClickingListeners = new LinkedList();
        this.mScrollingListener = new ScrollingListener() {
            public void onStarted() {
                ScrollTextView.this.isScrollingPerformed = true;
                ScrollTextView.this.notifyScrollingListenersAboutStart();
            }

            public void onScroll(int i) {
                ScrollTextView.this.doScroll(i);
                int height = ScrollTextView.this.getHeight();
                if (ScrollTextView.this.mScrollingOffset > height) {
                    ScrollTextView.this.mScrollingOffset = height;
                    ScrollTextView.this.mWheelScroller.stopScrolling();
                } else if (ScrollTextView.this.mScrollingOffset < (-height)) {
                    ScrollTextView.this.mScrollingOffset = -height;
                    ScrollTextView.this.mWheelScroller.stopScrolling();
                }
            }

            public void onFinished() {
                if (ScrollTextView.this.isScrollingPerformed) {
                    ScrollTextView.this.notifyScrollingListenersAboutEnd();
                    ScrollTextView.this.isScrollingPerformed = false;
                }
                ScrollTextView.this.mScrollingOffset = 0;
                ScrollTextView.this.invalidate();
            }

            public void onJustify() {
                if (!ScrollTextView.this.isCyclic && ScrollTextView.this.getCurrentItem() < ScrollTextView.this.mViewAdapter.getValidStart()) {
                    ScrollTextView.this.scroll(ScrollTextView.this.mViewAdapter.getValidStart() - ScrollTextView.this.getCurrentItem(), 0);
                } else if (!ScrollTextView.this.isCyclic && ScrollTextView.this.getCurrentItem() > ScrollTextView.this.mViewAdapter.getValidEnd()) {
                    ScrollTextView.this.scroll(ScrollTextView.this.mViewAdapter.getValidEnd() - ScrollTextView.this.getCurrentItem(), 0);
                } else if (Math.abs(ScrollTextView.this.mScrollingOffset) > 1) {
                    ScrollTextView.this.mWheelScroller.scroll(ScrollTextView.this.mScrollingOffset, 0);
                }
            }
        };
        this.mTmpCanvas = new Canvas();
        this.mIsBitmapChanged = false;
        initData(context);
    }

    public ScrollTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScrollTextView(Context context) {
        super(context);
        this.mTextPaint = new Paint(1);
        this.mCurrentItem = 0;
        this.mVisibleItems = 5;
        this.mSelectItemHeight = 0.0f;
        this.mNormalItemHeight = 0.0f;
        this.isCyclic = false;
        this.mIsDrawFading = true;
        this.mChangingListeners = new LinkedList();
        this.mScrollingListeners = new LinkedList();
        this.mClickingListeners = new LinkedList();
        this.mScrollingListener = /* anonymous class already generated */;
        this.mTmpCanvas = new Canvas();
        this.mIsBitmapChanged = false;
        initData(context);
    }

    private void initData(Context context) {
        this.mWheelScroller = new ScrollTextViewScroller(getContext(), this.mScrollingListener);
        this.mSelectTextSize = context.getResources().getDimension(R.dimen.mc_picker_selected_number_size);
        this.mNormalTextSize = context.getResources().getDimension(R.dimen.mc_picker_normal_number_size);
        this.mSelectItemHeight = context.getResources().getDimension(R.dimen.mc_picker_scroll_select_item_height);
        this.mNormalItemHeight = context.getResources().getDimension(R.dimen.mc_picker_scroll_normal_item_height);
        this.mSelectTextColor = context.getResources().getColor(R.color.mc_picker_selected_color);
        this.mNormalTextColor = context.getResources().getColor(R.color.mc_picker_unselected_color);
        this.mViewAdapter = new ScrollTextViewAdapter(this);
        this.mFadingMatrix = new Matrix();
        this.mFadingShader = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, ViewCompat.MEASURED_STATE_MASK, 0, TileMode.CLAMP);
        this.mFadingPaint = new Paint();
        this.mFadingPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        this.mFadingPaint.setShader(this.mFadingShader);
        this.mFadingHeight = context.getResources().getDimension(R.dimen.mc_picker_fading_height);
        this.mRange = new VisibleItemsRange(this);
        this.mBitmapPaint = new Paint();
        this.mBitmapPaint.setAntiAlias(true);
        this.mBitmapPaint.setColor(-3355444);
        this.mTextPaint.setTextAlign(Align.CENTER);
        computeFontMetrics();
    }

    public void refreshCount(int i) {
        refreshData(i, this.mCurrentItem, 0, i - 1);
    }

    public void refreshCurrent(int i) {
        refreshData(this.mViewAdapter.getItemsCount(), i, this.mViewAdapter.getValidStart(), this.mViewAdapter.getValidEnd());
    }

    public void refreshCountAndCurrent(int i, int i2) {
        refreshData(i, i2, 0, i - 1);
    }

    void refreshData(int i, int i2, int i3, int i4) {
        stopScrolling();
        if (i >= 0) {
            setViewAdapter(this.mViewAdapter.update(i, i3, i4));
            int i5 = this.mCurrentItem;
            if (i2 != this.mCurrentItem) {
                this.mCurrentItem = Math.max(i2, i3);
                if (this.mCurrentItem > i4 || this.mCurrentItem >= i) {
                    this.mCurrentItem = Math.min(i4, i);
                }
            }
            if (!(i5 == this.mCurrentItem || this.mDataInterface == null)) {
                this.mDataInterface.onChanged(this, i5, this.mCurrentItem);
            }
            invalidate();
        }
    }

    public void setData(IDataAdapter iDataAdapter, int i, int i2, int i3) {
        setData(iDataAdapter, GroundOverlayOptions.NO_DIMENSION, i, i2, i3, 0, i2 - 1, true);
    }

    public void setData(IDataAdapter iDataAdapter, float f, int i, int i2, int i3, int i4, int i5, boolean z) {
        setIDataAdapter(iDataAdapter);
        this.mVisibleItems = i3;
        this.isCyclic = z;
        if (f == GroundOverlayOptions.NO_DIMENSION) {
            this.mOffsetY = getResources().getDimensionPixelSize(R.dimen.mc_picker_offset_y);
        } else {
            this.mOffsetY = (int) (getContext().getResources().getDisplayMetrics().density * f);
        }
        if (i2 < this.mVisibleItems || i5 + 1 < i2 || i4 > 0) {
            this.isCyclic = false;
        }
        refreshData(i2, i, i4, i5);
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mWheelScroller.setInterpolator(interpolator);
    }

    public int getVisibleItems() {
        return this.mVisibleItems;
    }

    public void setVisibleItems(int i) {
        this.mVisibleItems = i;
    }

    public ScrollTextViewAdapter getViewAdapter() {
        return this.mViewAdapter;
    }

    private void setViewAdapter(ScrollTextViewAdapter scrollTextViewAdapter) {
        this.mViewAdapter = scrollTextViewAdapter;
        invalidate();
    }

    public void addChangingListener(OnScrollTextViewChangedListener onScrollTextViewChangedListener) {
        this.mChangingListeners.add(onScrollTextViewChangedListener);
    }

    public void removeChangingListener(OnScrollTextViewChangedListener onScrollTextViewChangedListener) {
        this.mChangingListeners.remove(onScrollTextViewChangedListener);
    }

    protected void notifyChangingListeners(int i, int i2) {
        for (OnScrollTextViewChangedListener onChanged : this.mChangingListeners) {
            onChanged.onChanged(this, i, i2);
        }
    }

    public void addScrollingListener(OnScrollTextViewScrollListener onScrollTextViewScrollListener) {
        this.mScrollingListeners.add(onScrollTextViewScrollListener);
    }

    public void removeScrollingListener(OnScrollTextViewScrollListener onScrollTextViewScrollListener) {
        this.mScrollingListeners.remove(onScrollTextViewScrollListener);
    }

    protected void notifyScrollingListenersAboutStart() {
        for (OnScrollTextViewScrollListener onScrollingStarted : this.mScrollingListeners) {
            onScrollingStarted.onScrollingStarted(this);
        }
    }

    protected void notifyScrollingListenersAboutEnd() {
        if (this.mDataInterface != null) {
            this.mDataInterface.onChanged(this, 0, getCurrentItem());
        }
        for (OnScrollTextViewScrollListener onScrollingFinished : this.mScrollingListeners) {
            onScrollingFinished.onScrollingFinished(this);
        }
    }

    public void addClickingListener(OnScrollTextViewClickedListener onScrollTextViewClickedListener) {
        this.mClickingListeners.add(onScrollTextViewClickedListener);
    }

    public void removeClickingListener(OnScrollTextViewClickedListener onScrollTextViewClickedListener) {
        this.mClickingListeners.remove(onScrollTextViewClickedListener);
    }

    protected void notifyClickListenersAboutClick(int i) {
        setCurrentItem(i, true);
        for (OnScrollTextViewClickedListener onItemClicked : this.mClickingListeners) {
            onItemClicked.onItemClicked(this, i);
        }
    }

    public int getCurrentItem() {
        return this.mCurrentItem;
    }

    public void setCurrentItem(int i, boolean z) {
        if (this.mViewAdapter != null && this.mViewAdapter.getItemsCount() != 0) {
            int itemsCount = this.mViewAdapter.getItemsCount();
            if (i < 0 || i >= itemsCount) {
                if (this.isCyclic) {
                    while (i < 0) {
                        i += itemsCount;
                    }
                    i %= itemsCount;
                } else {
                    return;
                }
            }
            if (i == this.mCurrentItem) {
                return;
            }
            if (z) {
                int i2 = i - this.mCurrentItem;
                if (this.isCyclic) {
                    itemsCount = (itemsCount + Math.min(i, this.mCurrentItem)) - Math.max(i, this.mCurrentItem);
                    if (itemsCount < Math.abs(i2)) {
                        if (i2 >= 0) {
                            itemsCount = -itemsCount;
                        }
                        scroll(itemsCount, 0);
                        return;
                    }
                }
                itemsCount = i2;
                scroll(itemsCount, 0);
                return;
            }
            this.mScrollingOffset = 0;
            itemsCount = this.mCurrentItem;
            this.mCurrentItem = i;
            notifyChangingListeners(itemsCount, this.mCurrentItem);
            invalidate();
        }
    }

    public void setCurrentItem(int i) {
        setCurrentItem(i, false);
    }

    public boolean isCyclic() {
        return this.isCyclic;
    }

    public void setCyclic(boolean z) {
        this.isCyclic = z;
        invalidate();
    }

    private int getItemHeight() {
        return (int) this.mNormalItemHeight;
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(i, (int) ((((float) (this.mVisibleItems - 1)) * this.mNormalItemHeight) + this.mSelectItemHeight));
    }

    public void setHorizontalOffset(int i) {
        this.mOffsetX = i;
    }

    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = getBitmap(getWidth(), getHeight());
        if (this.mIsBitmapChanged) {
            this.mTmpCanvas.setBitmap(bitmap);
        }
        if (this.mViewAdapter != null && this.mViewAdapter.getItemsCount() > 0) {
            rebuildItems();
            drawItems(this.mTmpCanvas);
        }
        if (this.mIsDrawFading) {
            drawVerticalFading(this.mTmpCanvas);
        }
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.mBitmapPaint);
    }

    private Bitmap getBitmap(int i, int i2) {
        if (this.mTmpBitmap == null) {
            this.mTmpBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
            this.mIsBitmapChanged = true;
        } else if (this.mTmpBitmap.getWidth() == i && this.mTmpBitmap.getHeight() == i2) {
            this.mIsBitmapChanged = false;
        } else {
            this.mTmpBitmap.recycle();
            this.mTmpBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
            this.mIsBitmapChanged = true;
        }
        this.mTmpBitmap.eraseColor(0);
        return this.mTmpBitmap;
    }

    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (4 == i && this.mTmpBitmap != null) {
            this.mTmpBitmap = null;
        }
    }

    private void drawVerticalFading(Canvas canvas) {
        this.mFadingMatrix.setScale(1.0f, this.mFadingHeight);
        this.mFadingShader.setLocalMatrix(this.mFadingMatrix);
        canvas.drawRect(0.0f, 0.0f, (float) getWidth(), this.mFadingHeight, this.mFadingPaint);
        this.mFadingMatrix.setScale(1.0f, this.mFadingHeight);
        this.mFadingMatrix.postRotate(BitmapDescriptorFactory.HUE_CYAN);
        this.mFadingMatrix.postTranslate(0.0f, (float) getHeight());
        this.mFadingShader.setLocalMatrix(this.mFadingMatrix);
        canvas.drawRect(0.0f, ((float) getHeight()) - this.mFadingHeight, (float) getWidth(), (float) getHeight(), this.mFadingPaint);
    }

    private void drawItems(Canvas canvas) {
        int i;
        float itemHeight = (float) (((-(((this.mCurrentItem - this.mFirstItem) * getItemHeight()) + ((((int) this.mSelectItemHeight) - getHeight()) / 2))) + this.mScrollingOffset) - getItemHeight());
        canvas.translate((float) this.mOffsetX, itemHeight);
        if (this.mScrollingOffset > 0) {
            i = this.mScrollingOffset;
        } else {
            i = getItemHeight() + this.mScrollingOffset;
        }
        float itemHeight2 = (((float) i) * 1.0f) / ((float) getItemHeight());
        for (i = 0; i < this.mRange.getCount(); i++) {
            float configTextView = configTextView(i, itemHeight2);
            canvas.translate(0.0f, configTextView);
            itemHeight += configTextView;
            canvas.drawText(getItemText(i), (float) (getWidth() / 2), (this.mNormalItemHeight / CircleProgressBar.BAR_WIDTH_DEF_DIP) - this.mFontMetricsCenterY, this.mTextPaint);
        }
        canvas.translate((float) (-this.mOffsetX), -itemHeight);
    }

    private String getItemText(int i) {
        int i2 = i + this.mFirstItem;
        String itemText = this.mViewAdapter.getItemText(i2);
        if (i2 < 0) {
            itemText = this.isCyclic ? this.mViewAdapter.getItemText(this.mViewAdapter.getItemsCount() + i2) : "";
        } else if (i2 >= this.mViewAdapter.getItemsCount()) {
            itemText = this.isCyclic ? this.mViewAdapter.getItemText(i2 - this.mViewAdapter.getItemsCount()) : "";
        }
        if (itemText == null) {
            return "";
        }
        return itemText;
    }

    private float configTextView(int i, float f) {
        float f2;
        float itemHeight = (float) getItemHeight();
        int i2 = (int) (this.mSelectItemHeight - this.mNormalItemHeight);
        int i3 = this.mVisibleItems / 2;
        if (i == i3) {
            f2 = ((((float) i2) * f) / CircleProgressBar.BAR_WIDTH_DEF_DIP) + itemHeight;
        } else if (i == i3 + 1) {
            f2 = ((float) (i2 / 2)) + itemHeight;
            f = 1.0f - f;
        } else if (i == i3 + 2) {
            f = 0.0f;
            f2 = itemHeight + ((((float) i2) * (1.0f - f)) / CircleProgressBar.BAR_WIDTH_DEF_DIP);
        } else {
            f = 0.0f;
            f2 = itemHeight;
        }
        computeTextSizeAndColor(i, f);
        return f2;
    }

    private void computeTextSizeAndColor(int i, float f) {
        int i2 = this.mSelectTextColor;
        int i3 = this.mNormalTextColor;
        int alpha = Color.alpha(i2);
        int red = Color.red(i2);
        int green = Color.green(i2);
        i2 = Color.blue(i2);
        int alpha2 = Color.alpha(i3);
        int red2 = Color.red(i3);
        int green2 = Color.green(i3);
        i3 = Color.blue(i3);
        this.mTextPaint.setColor(Color.argb(((int) (((float) (alpha - alpha2)) * f)) + alpha2, ((int) (((float) (red - red2)) * f)) + red2, ((int) (((float) (green - green2)) * f)) + green2, ((int) (((float) (i2 - i3)) * f)) + i3));
        this.mTextPaint.setTextSize(this.mNormalTextSize + ((this.mSelectTextSize - this.mNormalTextSize) * f));
        this.mFontMetricsCenterY = this.mNormalFontMetricsCenterY + ((this.mSelectFontMetricsCenterY - this.mNormalFontMetricsCenterY) * f);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled() || getViewAdapter() == null) {
            return true;
        }
        switch (motionEvent.getAction()) {
            case 1:
                if (!this.isScrollingPerformed) {
                    int y = ((int) motionEvent.getY()) - (getHeight() / 2);
                    if (y < 0) {
                        y = (int) (((float) y) + ((this.mSelectItemHeight / CircleProgressBar.BAR_WIDTH_DEF_DIP) - ((float) getItemHeight())));
                    } else {
                        y = (int) (((float) y) - ((this.mSelectItemHeight / CircleProgressBar.BAR_WIDTH_DEF_DIP) - ((float) getItemHeight())));
                    }
                    y /= getItemHeight();
                    if (y != 0 && isValidItemIndex(this.mCurrentItem + y)) {
                        notifyClickListenersAboutClick(y + this.mCurrentItem);
                        break;
                    }
                }
                break;
            case 2:
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                }
                break;
        }
        return this.mWheelScroller.onTouchEvent(motionEvent);
    }

    private int getYScrollEnd() {
        if (this.isCyclic) {
            return Integer.MAX_VALUE;
        }
        return this.mScrollingOffset + ((int) (((float) (getScrollEndItem() - getCurrentItem())) * this.mNormalItemHeight));
    }

    private int getYScrollStart() {
        if (this.isCyclic) {
            return -2147483647;
        }
        return this.mScrollingOffset + ((int) (((float) (getScrollStartItem() - getCurrentItem())) * this.mNormalItemHeight));
    }

    private int getScrollEndItem() {
        int itemsCount = this.mViewAdapter.getItemsCount();
        if (this.isCyclic) {
            return 0;
        }
        if (itemsCount <= this.mVisibleItems) {
            return itemsCount - 1;
        }
        return (itemsCount - 1) - (this.mVisibleItems / 2);
    }

    private int getScrollStartItem() {
        int itemsCount = this.mViewAdapter.getItemsCount();
        if (!this.isCyclic && itemsCount > this.mVisibleItems) {
            return this.mVisibleItems / 2;
        }
        return 0;
    }

    private void doScroll(int i) {
        this.mScrollingOffset += i;
        int itemHeight = getItemHeight();
        int i2 = this.mScrollingOffset / itemHeight;
        int i3 = this.mCurrentItem - i2;
        int itemsCount = this.mViewAdapter.getItemsCount();
        int i4 = this.mScrollingOffset % itemHeight;
        if (Math.abs(i4) <= itemHeight / 2) {
            i4 = 0;
        }
        if (this.isCyclic && itemsCount > 0) {
            if (i4 > 0) {
                i4 = i3 - 1;
                i3 = i2 + 1;
            } else if (i4 < 0) {
                i4 = i3 + 1;
                i3 = i2 - 1;
            } else {
                i4 = i3;
                i3 = i2;
            }
            while (i4 < 0) {
                i4 += itemsCount;
            }
            i4 %= itemsCount;
        } else if (i3 < getScrollStartItem()) {
            i3 = this.mCurrentItem - getScrollStartItem();
            i4 = getScrollStartItem();
            this.mScrollingOffset = 0;
        } else if (i3 > getScrollEndItem()) {
            i3 = this.mCurrentItem - getScrollEndItem();
            i4 = getScrollEndItem();
            this.mScrollingOffset = 0;
        } else if (i3 > getScrollStartItem() && i4 > 0) {
            i4 = i3 - 1;
            i3 = i2 + 1;
            Log.i(TAG, "pos > 0 && fixPos > 0");
        } else if (i3 >= getScrollEndItem() || i4 >= 0) {
            if (i3 == getScrollEndItem()) {
                if (this.mScrollingOffset < 0) {
                    this.mScrollingOffset = 0;
                    i4 = i3;
                    i3 = i2;
                }
            } else if (i3 == getScrollStartItem() && this.mScrollingOffset > 0) {
                this.mScrollingOffset = 0;
            }
            i4 = i3;
            i3 = i2;
        } else {
            i4 = i3 + 1;
            i3 = i2 - 1;
        }
        i2 = this.mScrollingOffset;
        if (i4 != this.mCurrentItem) {
            setCurrentItem(i4, false);
        } else {
            invalidate();
        }
        this.mScrollingOffset = i2 - (i3 * itemHeight);
        if (this.mScrollingOffset > getHeight() && getHeight() != 0) {
            this.mScrollingOffset = (this.mScrollingOffset % getHeight()) + getHeight();
        }
    }

    public void scroll(int i, int i2) {
        this.mWheelScroller.scroll((getItemHeight() * i) + this.mScrollingOffset, i2);
    }

    private VisibleItemsRange getItemsRange() {
        if (getItemHeight() == 0) {
            return null;
        }
        int i = this.mCurrentItem;
        int i2 = 1;
        while ((i2 + 2) * getItemHeight() < getHeight()) {
            i--;
            i2 += 2;
        }
        if (this.mScrollingOffset != 0) {
            if (this.mScrollingOffset > 0) {
                i--;
            }
            int itemHeight = this.mScrollingOffset / getItemHeight();
            i -= itemHeight;
            i2 = (int) (Math.asin((double) itemHeight) + ((double) (i2 + 1)));
        }
        return this.mRange.update(i, i2);
    }

    private boolean rebuildItems() {
        this.mRange = getItemsRange();
        if (this.mFirstItem <= this.mRange.getFirst() || this.mFirstItem > this.mRange.getLast()) {
            this.mFirstItem = this.mRange.getFirst();
        } else {
            for (int i = this.mFirstItem - 1; i >= this.mRange.getFirst(); i--) {
                this.mFirstItem = i;
            }
        }
        return false;
    }

    private void computeFontMetrics() {
        this.mTextPaint.setTextSize(this.mSelectTextSize);
        FontMetricsInt fontMetricsInt = this.mTextPaint.getFontMetricsInt();
        this.mSelectFontMetricsCenterY = (float) ((fontMetricsInt.top + fontMetricsInt.bottom) / 2);
        this.mTextPaint.setTextSize(this.mNormalTextSize);
        fontMetricsInt = this.mTextPaint.getFontMetricsInt();
        this.mNormalFontMetricsCenterY = (float) ((fontMetricsInt.top + fontMetricsInt.bottom) / 2);
    }

    private boolean isValidItemIndex(int i) {
        return this.mViewAdapter != null && this.mViewAdapter.getItemsCount() > 0 && (this.isCyclic || (i >= 0 && i < this.mViewAdapter.getItemsCount()));
    }

    public void stopScrolling() {
        if (this.mWheelScroller != null) {
            this.mWheelScroller.stopScrolling();
        }
    }

    public void setTextColor(int i, int i2) {
        if (this.mSelectTextColor != i || this.mNormalTextColor != i2) {
            this.mSelectTextColor = i;
            this.mNormalTextColor = i2;
            invalidate();
        }
    }

    public void setTextPreference(float f, float f2, float f3, float f4) {
        if (this.mSelectItemHeight != f3 || this.mNormalItemHeight != f4 || this.mSelectTextSize != f || this.mNormalTextSize != f2) {
            this.mSelectItemHeight = f3;
            this.mNormalItemHeight = f4;
            this.mSelectTextSize = f;
            this.mNormalTextSize = f2;
            computeFontMetrics();
            invalidate();
        }
    }

    public void setTextSize(float f, float f2) {
        setTextPreference(f, f2, this.mSelectItemHeight, this.mNormalItemHeight);
    }

    public void setItemHeight(float f, float f2) {
        setTextPreference(this.mSelectTextSize, this.mNormalTextSize, f, f2);
    }

    public void setSelectItemHeight(float f) {
        setTextPreference(this.mSelectTextSize, this.mNormalTextSize, f, this.mNormalItemHeight);
    }

    public void setNormalItemHeight(float f) {
        setTextPreference(this.mSelectTextSize, this.mNormalTextSize, this.mSelectItemHeight, f);
    }

    public void setIDataAdapter(IDataAdapter iDataAdapter) {
        this.mDataInterface = iDataAdapter;
    }

    public IDataAdapter getIDataAdapter() {
        return this.mDataInterface;
    }

    public int getItemsCount() {
        return this.mViewAdapter.getItemsCount();
    }

    public void setTypeface(Typeface typeface) {
        this.mTextPaint.setTypeface(typeface);
        computeFontMetrics();
        invalidate();
    }

    public void setIsDrawFading(boolean z) {
        this.mIsDrawFading = z;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(ScrollTextView.class.getName());
    }
}
