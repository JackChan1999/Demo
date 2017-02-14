package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewDebug.ExportedProperty;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.RemoteViews.RemoteView;
import com.meizu.common.R;

@RemoteView
public class ProgressBar extends View {
    private static final int MAX_LEVEL = 10000;
    private AlphaAnimation mAnimation;
    private int mBehavior;
    private int mBreakPoint;
    private Drawable mCurrentDrawable;
    private int mDuration;
    private boolean mHasAnimation;
    private boolean mInDrawing;
    private boolean mIndeterminate;
    private Drawable mIndeterminateDrawable;
    private Interpolator mInterpolator;
    private boolean mIsVertical;
    private int mMax;
    int mMaxHeight;
    int mMaxWidth;
    int mMinHeight;
    int mMinWidth;
    private boolean mNoInvalidate;
    private boolean mOnlyIndeterminate;
    private int mProgress;
    private Drawable mProgressDrawable;
    private boolean mRefreshIsPosted;
    private RefreshProgressRunnable mRefreshProgressRunnable;
    Bitmap mSampleTile;
    private int mSecondaryProgress;
    private boolean mShouldStartAnimationDrawable;
    private Transformation mTransformation;
    private long mUiThreadId;

    class RefreshProgressRunnable implements Runnable {
        private boolean mFromUser;
        private int mId;
        private int mProgress;

        RefreshProgressRunnable(int i, int i2, boolean z) {
            this.mId = i;
            this.mProgress = i2;
            this.mFromUser = z;
        }

        public void run() {
            ProgressBar.this.doRefreshProgress(this.mId, this.mProgress, this.mFromUser, true);
            ProgressBar.this.mRefreshProgressRunnable = this;
        }

        public void setup(int i, int i2, boolean z) {
            this.mId = i;
            this.mProgress = i2;
            this.mFromUser = z;
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
        int progress;
        int secondaryProgress;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.progress = parcel.readInt();
            this.secondaryProgress = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.progress);
            parcel.writeInt(this.secondaryProgress);
        }
    }

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_ProgressBarStyle);
    }

    public ProgressBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ProgressBar(Context context, AttributeSet attributeSet, int i, int i2) {
        boolean z = false;
        super(context, attributeSet, i);
        this.mBreakPoint = 0;
        this.mIsVertical = false;
        this.mUiThreadId = Thread.currentThread().getId();
        initProgressBar();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ProgressBar, i, i2);
        this.mNoInvalidate = true;
        Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.ProgressBar_mcProgressDrawable);
        if (drawable != null) {
            setProgressDrawable(tileify(drawable, false));
        }
        this.mDuration = obtainStyledAttributes.getInt(R.styleable.ProgressBar_mcIndeterminateDuration, this.mDuration);
        this.mMinWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ProgressBar_mcMinWidth, this.mMinWidth);
        this.mMaxWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ProgressBar_mcMaxWidth, this.mMaxWidth);
        this.mMinHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ProgressBar_mcMinHeight, this.mMinHeight);
        this.mMaxHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ProgressBar_mcMaxHeight, this.mMaxHeight);
        this.mBehavior = obtainStyledAttributes.getInt(R.styleable.ProgressBar_mcIndeterminateBehavior, this.mBehavior);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.ProgressBar_mcInterpolator, 17432587);
        if (resourceId > 0) {
            setInterpolator(context, resourceId);
        }
        setMax(obtainStyledAttributes.getInt(R.styleable.ProgressBar_mcMax, this.mMax));
        setProgress(obtainStyledAttributes.getInt(R.styleable.ProgressBar_mcProgress, this.mProgress));
        setSecondaryProgress(obtainStyledAttributes.getInt(R.styleable.ProgressBar_mcSecondaryProgress, this.mSecondaryProgress));
        drawable = obtainStyledAttributes.getDrawable(R.styleable.ProgressBar_mcIndeterminateDrawable);
        if (drawable != null) {
            setIndeterminateDrawable(tileifyIndeterminate(drawable));
        }
        this.mOnlyIndeterminate = obtainStyledAttributes.getBoolean(R.styleable.ProgressBar_mcIndeterminateOnly, this.mOnlyIndeterminate);
        this.mNoInvalidate = false;
        if (this.mOnlyIndeterminate || obtainStyledAttributes.getBoolean(R.styleable.ProgressBar_mcIndeterminate, this.mIndeterminate)) {
            z = true;
        }
        setIndeterminate(z);
        obtainStyledAttributes.recycle();
    }

    private Drawable tileify(Drawable drawable, boolean z) {
        int i = 0;
        if (!(drawable instanceof LayerDrawable)) {
            return drawable;
        }
        LayerDrawable layerDrawable = (LayerDrawable) drawable;
        int numberOfLayers = layerDrawable.getNumberOfLayers();
        Drawable[] drawableArr = new Drawable[numberOfLayers];
        for (int i2 = 0; i2 < numberOfLayers; i2++) {
            boolean z2;
            int id = layerDrawable.getId(i2);
            Drawable drawable2 = layerDrawable.getDrawable(i2);
            if (id == 16908301 || id == 16908303) {
                z2 = true;
            } else {
                z2 = false;
            }
            drawableArr[i2] = tileify(drawable2, z2);
        }
        Drawable layerDrawable2 = new LayerDrawable(drawableArr);
        while (i < numberOfLayers) {
            layerDrawable2.setId(i, layerDrawable.getId(i));
            i++;
        }
        return layerDrawable2;
    }

    Shape getDrawableShape() {
        return new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null);
    }

    private Drawable tileifyIndeterminate(Drawable drawable) {
        if (!(drawable instanceof AnimationDrawable)) {
            return drawable;
        }
        AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
        int numberOfFrames = animationDrawable.getNumberOfFrames();
        Drawable animationDrawable2 = new AnimationDrawable();
        animationDrawable2.setOneShot(animationDrawable.isOneShot());
        for (int i = 0; i < numberOfFrames; i++) {
            Drawable tileify = tileify(animationDrawable.getFrame(i), true);
            tileify.setLevel(10000);
            animationDrawable2.addFrame(tileify, animationDrawable.getDuration(i));
        }
        animationDrawable2.setLevel(10000);
        return animationDrawable2;
    }

    private void initProgressBar() {
        this.mMax = 100;
        this.mProgress = 0;
        this.mSecondaryProgress = 0;
        this.mIndeterminate = false;
        this.mOnlyIndeterminate = false;
        this.mDuration = 4000;
        this.mBehavior = 1;
        this.mMinWidth = 24;
        this.mMaxWidth = 48;
        this.mMinHeight = 24;
        this.mMaxHeight = 48;
    }

    @ExportedProperty(category = "progress")
    public synchronized boolean isIndeterminate() {
        return this.mIndeterminate;
    }

    public synchronized void setIndeterminate(boolean z) {
        if (!((this.mOnlyIndeterminate && this.mIndeterminate) || z == this.mIndeterminate)) {
            this.mIndeterminate = z;
            if (z) {
                this.mCurrentDrawable = this.mIndeterminateDrawable;
                startAnimation();
            } else {
                this.mCurrentDrawable = this.mProgressDrawable;
                stopAnimation();
            }
        }
    }

    public Drawable getIndeterminateDrawable() {
        return this.mIndeterminateDrawable;
    }

    public void setIndeterminateDrawable(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(this);
        }
        this.mIndeterminateDrawable = drawable;
        if (this.mIndeterminate) {
            this.mCurrentDrawable = drawable;
            postInvalidate();
        }
    }

    public Drawable getProgressDrawable() {
        return this.mProgressDrawable;
    }

    public void setProgressDrawable(Drawable drawable) {
        boolean z;
        if (this.mProgressDrawable == null || drawable == this.mProgressDrawable) {
            z = false;
        } else {
            this.mProgressDrawable.setCallback(null);
            z = true;
        }
        if (drawable != null) {
            drawable.setCallback(this);
            int minimumHeight = drawable.getMinimumHeight();
            if (this.mMaxHeight < minimumHeight) {
                this.mMaxHeight = minimumHeight;
                requestLayout();
            }
        }
        this.mProgressDrawable = drawable;
        if (!this.mIndeterminate) {
            this.mCurrentDrawable = drawable;
            postInvalidate();
        }
        if (z) {
            updateDrawableBounds(getWidth(), getHeight());
            updateDrawableState();
            doRefreshProgress(16908301, this.mProgress, false, false);
            doRefreshProgress(16908303, this.mSecondaryProgress, false, false);
        }
    }

    Drawable getCurrentDrawable() {
        return this.mCurrentDrawable;
    }

    protected boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mProgressDrawable || drawable == this.mIndeterminateDrawable || super.verifyDrawable(drawable);
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mProgressDrawable != null) {
            this.mProgressDrawable.jumpToCurrentState();
        }
        if (this.mIndeterminateDrawable != null) {
            this.mIndeterminateDrawable.jumpToCurrentState();
        }
    }

    public void postInvalidate() {
        if (!this.mNoInvalidate) {
            super.postInvalidate();
        }
    }

    private synchronized void doRefreshProgress(int i, int i2, boolean z, boolean z2) {
        float f;
        if (this.mMax > 0) {
            f = ((float) i2) / ((float) this.mMax);
        } else {
            f = 0.0f;
        }
        Drawable drawable = this.mCurrentDrawable;
        if (drawable != null) {
            Drawable drawable2 = null;
            if (drawable instanceof LayerDrawable) {
                drawable2 = ((LayerDrawable) drawable).findDrawableByLayerId(i);
            }
            int i3 = (int) (10000.0f * f);
            if (drawable2 != null) {
                drawable = drawable2;
            }
            drawable.setLevel(i3);
        } else {
            invalidate();
        }
        if (z2 && i == 16908301) {
            onProgressRefresh(f, z);
        }
    }

    void onProgressRefresh(float f, boolean z) {
    }

    private synchronized void refreshProgress(int i, int i2, boolean z) {
        if (this.mUiThreadId == Thread.currentThread().getId()) {
            doRefreshProgress(i, i2, z, true);
        } else {
            Runnable runnable;
            if (this.mRefreshProgressRunnable != null) {
                runnable = this.mRefreshProgressRunnable;
                this.mRefreshProgressRunnable = null;
                runnable.setup(i, i2, z);
            } else {
                runnable = new RefreshProgressRunnable(i, i2, z);
            }
            post(runnable);
        }
    }

    public synchronized void setProgress(int i) {
        setProgress(i, false);
    }

    synchronized void setProgress(int i, boolean z) {
        if (!this.mIndeterminate) {
            int i2;
            if (i < 0) {
                i2 = 0;
            } else {
                i2 = i;
            }
            if (i2 > this.mMax) {
                i2 = this.mMax;
            }
            if (i2 != this.mProgress) {
                this.mProgress = i2;
                refreshProgress(16908301, this.mProgress, z);
            }
        }
    }

    public synchronized void setSecondaryProgress(int i) {
        int i2 = 0;
        synchronized (this) {
            if (!this.mIndeterminate) {
                if (i >= 0) {
                    i2 = i;
                }
                if (i2 > this.mMax) {
                    i2 = this.mMax;
                }
                if (i2 != this.mSecondaryProgress) {
                    this.mSecondaryProgress = i2;
                    refreshProgress(16908303, this.mSecondaryProgress, false);
                }
            }
        }
    }

    @ExportedProperty(category = "progress")
    public synchronized int getProgress() {
        return this.mIndeterminate ? 0 : this.mProgress;
    }

    @ExportedProperty(category = "progress")
    public synchronized int getSecondaryProgress() {
        return this.mIndeterminate ? 0 : this.mSecondaryProgress;
    }

    @ExportedProperty(category = "progress")
    public synchronized int getMax() {
        return this.mMax;
    }

    public synchronized void setMax(int i) {
        if (i < 0) {
            i = 0;
        }
        if (i != this.mMax) {
            this.mMax = i;
            postInvalidate();
            if (this.mProgress > i) {
                this.mProgress = i;
            }
            refreshProgress(16908301, this.mProgress, false);
        }
    }

    public final synchronized void incrementProgressBy(int i) {
        setProgress(this.mProgress + i);
    }

    public final synchronized void incrementSecondaryProgressBy(int i) {
        setSecondaryProgress(this.mSecondaryProgress + i);
    }

    void startAnimation() {
        if (getVisibility() == 0) {
            if (this.mIndeterminateDrawable instanceof Animatable) {
                this.mShouldStartAnimationDrawable = true;
                this.mAnimation = null;
            } else {
                if (this.mInterpolator == null) {
                    this.mInterpolator = new LinearInterpolator();
                }
                this.mTransformation = new Transformation();
                this.mAnimation = new AlphaAnimation(0.0f, 1.0f);
                this.mAnimation.setRepeatMode(this.mBehavior);
                this.mAnimation.setRepeatCount(-1);
                this.mAnimation.setDuration((long) this.mDuration);
                this.mAnimation.setInterpolator(this.mInterpolator);
                this.mAnimation.setStartTime(-1);
            }
            postInvalidate();
        }
    }

    void stopAnimation() {
        this.mAnimation = null;
        this.mTransformation = null;
        if (this.mIndeterminateDrawable instanceof Animatable) {
            ((Animatable) this.mIndeterminateDrawable).stop();
            this.mShouldStartAnimationDrawable = false;
        }
        postInvalidate();
    }

    public void setInterpolator(Context context, int i) {
        setInterpolator(AnimationUtils.loadInterpolator(context, i));
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    public void setVisibility(int i) {
        if (getVisibility() != i) {
            super.setVisibility(i);
            if (!this.mIndeterminate) {
                return;
            }
            if (i == 8 || i == 4) {
                stopAnimation();
            } else {
                startAnimation();
            }
        }
    }

    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (!this.mIndeterminate) {
            return;
        }
        if (i == 8 || i == 4) {
            stopAnimation();
        } else {
            startAnimation();
        }
    }

    public void invalidateDrawable(Drawable drawable) {
        if (!this.mInDrawing) {
            if (verifyDrawable(drawable)) {
                Rect bounds = drawable.getBounds();
                int scrollX = getScrollX() + getPaddingLeft();
                int scrollY = getScrollY() + getPaddingTop();
                invalidate(bounds.left + scrollX, bounds.top + scrollY, scrollX + bounds.right, bounds.bottom + scrollY);
                return;
            }
            super.invalidateDrawable(drawable);
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        updateDrawableBounds(i, i2);
    }

    private void updateDrawableBounds(int i, int i2) {
        int i3;
        int paddingRight = (i - getPaddingRight()) - getPaddingLeft();
        int paddingBottom = (i2 - getPaddingBottom()) - getPaddingTop();
        if (this.mIndeterminateDrawable != null) {
            int i4;
            if (this.mOnlyIndeterminate && !(this.mIndeterminateDrawable instanceof AnimationDrawable)) {
                float intrinsicWidth = ((float) this.mIndeterminateDrawable.getIntrinsicWidth()) / ((float) this.mIndeterminateDrawable.getIntrinsicHeight());
                float f = ((float) i) / ((float) i2);
                if (intrinsicWidth != f) {
                    if (f > intrinsicWidth) {
                        paddingRight = (int) (intrinsicWidth * ((float) i2));
                        i4 = (i - paddingRight) / 2;
                        i3 = paddingRight + i4;
                        paddingRight = paddingBottom;
                        paddingBottom = 0;
                    } else {
                        paddingBottom = (int) ((1.0f / intrinsicWidth) * ((float) i));
                        i4 = (i2 - paddingBottom) / 2;
                        i3 = paddingRight;
                        paddingRight = paddingBottom + i4;
                        paddingBottom = i4;
                        i4 = 0;
                    }
                    this.mIndeterminateDrawable.setBounds(i4, paddingBottom, i3, paddingRight);
                }
            }
            i4 = 0;
            i3 = paddingRight;
            paddingRight = paddingBottom;
            paddingBottom = 0;
            this.mIndeterminateDrawable.setBounds(i4, paddingBottom, i3, paddingRight);
        } else {
            i3 = paddingRight;
            paddingRight = paddingBottom;
        }
        if (this.mProgressDrawable != null) {
            this.mProgressDrawable.setBounds(0, 0, i3, paddingRight);
        }
    }

    public void setBreakPoint(int i) {
        this.mBreakPoint = i;
        invalidate();
    }

    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = this.mCurrentDrawable;
        if (drawable != null) {
            Rect rect;
            Rect rect2;
            int height = this.mIsVertical ? (getHeight() - getPaddingTop()) - getPaddingBottom() : (getWidth() - getPaddingLeft()) - getPaddingRight();
            int max = (this.mBreakPoint * height) / getMax();
            if (this.mIsVertical) {
                rect = new Rect(0, 0, getWidth(), height - max);
                rect2 = new Rect(0, (height - max) + 5, getWidth(), height);
            } else {
                rect = new Rect(0, 0, max, getHeight());
                rect2 = new Rect(max + 5, 0, height, getHeight());
            }
            canvas.save();
            canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
            long drawingTime = getDrawingTime();
            if (this.mHasAnimation) {
                this.mAnimation.getTransformation(drawingTime, this.mTransformation);
                float alpha = this.mTransformation.getAlpha();
                try {
                    this.mInDrawing = true;
                    boolean z = (int) (alpha * 10000.0f);
                    drawable.setLevel(z);
                    this.mInDrawing = z;
                    postInvalidate();
                } finally {
                    rect2 = null;
                    this.mInDrawing = false;
                }
            }
            if (this.mBreakPoint == getMax() || this.mBreakPoint == 0) {
                drawable.draw(canvas);
                canvas.restore();
            } else {
                canvas.clipRect(rect);
                drawable.draw(canvas);
                canvas.restore();
                canvas.save();
                canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
                canvas.clipRect(rect2);
                drawable.draw(canvas);
                canvas.restore();
            }
            if (this.mShouldStartAnimationDrawable && (drawable instanceof Animatable)) {
                ((Animatable) drawable).start();
                this.mShouldStartAnimationDrawable = false;
            }
        }
    }

    protected synchronized void onMeasure(int i, int i2) {
        int i3 = 0;
        synchronized (this) {
            int max;
            Drawable drawable = this.mCurrentDrawable;
            if (drawable != null) {
                max = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, drawable.getIntrinsicWidth()));
                i3 = Math.max(this.mMinHeight, Math.min(this.mMaxHeight, drawable.getIntrinsicHeight()));
            } else {
                max = 0;
            }
            updateDrawableState();
            setMeasuredDimension(resolveSizeAndState(max + (getPaddingLeft() + getPaddingRight()), i, 0), resolveSizeAndState(i3 + (getPaddingTop() + getPaddingBottom()), i2, 0));
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateDrawableState();
    }

    private void updateDrawableState() {
        int[] drawableState = getDrawableState();
        if (this.mProgressDrawable != null && this.mProgressDrawable.isStateful()) {
            this.mProgressDrawable.setState(drawableState);
        }
        if (this.mIndeterminateDrawable != null && this.mIndeterminateDrawable.isStateful()) {
            this.mIndeterminateDrawable.setState(drawableState);
        }
    }

    public Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        savedState.progress = this.mProgress;
        savedState.secondaryProgress = this.mSecondaryProgress;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setProgress(savedState.progress);
        setSecondaryProgress(savedState.secondaryProgress);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mIndeterminate) {
            startAnimation();
        }
    }

    protected void onDetachedFromWindow() {
        if (this.mIndeterminate) {
            stopAnimation();
        }
        if (this.mRefreshProgressRunnable != null) {
            removeCallbacks(this.mRefreshProgressRunnable);
        }
        if (this.mRefreshProgressRunnable != null && this.mRefreshIsPosted) {
            removeCallbacks(this.mRefreshProgressRunnable);
        }
        super.onDetachedFromWindow();
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(ProgressBar.class.getName());
        accessibilityEvent.setItemCount(this.mMax);
        accessibilityEvent.setCurrentItemIndex(this.mProgress);
    }

    public void setProgressDrawableResource(int i) {
        Drawable progressDrawable = getProgressDrawable();
        Drawable drawable = getContext().getResources().getDrawable(i);
        drawable.setBounds(progressDrawable.copyBounds());
        setProgressDrawable(drawable);
        if (this.mProgress > 0) {
            incrementProgressBy(-1);
            incrementProgressBy(1);
        }
    }

    protected boolean isIsVertical() {
        return this.mIsVertical;
    }

    protected void setIsVertical(boolean z) {
        this.mIsVertical = z;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(ProgressBar.class.getName());
    }
}
