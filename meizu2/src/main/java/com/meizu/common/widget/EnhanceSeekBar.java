package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.meizu.common.R;

public class EnhanceSeekBar extends View {
    private static final int CIRCLE_RADIUS = 6;
    private static final int MIN_HEIGHT = 20;
    private static final int MIN_WIDTH = 64;
    private static final int STROKE_WIDTH = 4;
    private static final String TAG = "EnhanceSeekBar";
    private static final int TEXT_HEIGHT = 50;
    private static final int TEXT_SIZE = 40;
    private boolean hasMoved;
    private int mAuraRadis;
    private Drawable mAuraThumb;
    private DrawableXYHolder mDrawableXYHolder;
    private XYHolder mEndXY;
    private int mHalfThumbHeight;
    private int mHalfThumbWidth;
    private float mInitialThumbX;
    private float mInitialTouchX;
    private Interpolator mInterpolator;
    private boolean mIsDrag;
    private boolean mIsDragging;
    private boolean mIsInItemPositon;
    private CharSequence[] mItems;
    private int mMax;
    private ObjectAnimator mObjectAnimator;
    private OnEnhanceSeekBarChangeListener mOnEnhanceSeekBarChangeListener;
    private Paint mPaint;
    private int mPaintColor;
    private int mProgress;
    private int mScaledTouchSlop;
    private XYHolder mStartXY;
    private Drawable mThumb;
    private int mThumbOffset;
    private int mTouchDownProgress;
    private XYEvaluator mXYEvaluator;

    class DrawableXYHolder {
        private Drawable mDrawable;

        public DrawableXYHolder(Drawable drawable) {
            this.mDrawable = drawable;
        }

        public void setDrawable(Drawable drawable) {
            this.mDrawable = drawable;
        }

        public void setXY(XYHolder xYHolder) {
            if (this.mDrawable != null) {
                this.mDrawable.setBounds((int) xYHolder.getX(), (int) xYHolder.getY(), (int) (xYHolder.getX() + ((float) this.mDrawable.getIntrinsicWidth())), (int) (xYHolder.getY() + ((float) this.mDrawable.getIntrinsicHeight())));
                EnhanceSeekBar.this.invalidate();
            }
        }

        public XYHolder getXY() {
            if (this.mDrawable == null) {
                return null;
            }
            Rect bounds = this.mDrawable.getBounds();
            return new XYHolder((float) bounds.left, (float) bounds.top);
        }
    }

    public interface OnEnhanceSeekBarChangeListener {
        void onProgressChanged(EnhanceSeekBar enhanceSeekBar, int i);

        void onProgressDragging(EnhanceSeekBar enhanceSeekBar, int i);

        void onStartTrackingTouch(EnhanceSeekBar enhanceSeekBar);

        void onStopTrackingTouch(EnhanceSeekBar enhanceSeekBar);
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

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.progress = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.progress);
        }
    }

    class XYEvaluator implements TypeEvaluator {
        private XYEvaluator() {
        }

        public Object evaluate(float f, Object obj, Object obj2) {
            XYHolder xYHolder = (XYHolder) obj;
            XYHolder xYHolder2 = (XYHolder) obj2;
            return new XYHolder(xYHolder.getX() + ((xYHolder2.getX() - xYHolder.getX()) * f), xYHolder.getY() + ((xYHolder2.getY() - xYHolder.getY()) * f));
        }
    }

    class XYHolder {
        private float mX;
        private float mY;

        public XYHolder() {
            this.mY = 0.0f;
            this.mX = 0.0f;
        }

        public XYHolder(float f, float f2) {
            this.mX = f;
            this.mY = f2;
        }

        public float getX() {
            return this.mX;
        }

        public void setXY(float f, float f2) {
            this.mX = f;
            this.mY = f2;
        }

        public void setX(float f) {
            this.mX = f;
        }

        public float getY() {
            return this.mY;
        }

        public void setY(float f) {
            this.mY = f;
        }
    }

    public EnhanceSeekBar(Context context) {
        this(context, null);
    }

    public EnhanceSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_EnhanceSeekBarStyle);
    }

    public EnhanceSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsDrag = false;
        this.mTouchDownProgress = 0;
        this.mHalfThumbWidth = 0;
        this.mHalfThumbHeight = 0;
        this.mStartXY = new XYHolder();
        this.mEndXY = new XYHolder();
        this.mXYEvaluator = new XYEvaluator();
        this.mDrawableXYHolder = new DrawableXYHolder();
        this.hasMoved = false;
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EnhanceSeekBar, i, 0);
        setItems(obtainStyledAttributes.getTextArray(R.styleable.EnhanceSeekBar_mcEItems));
        setProgress(obtainStyledAttributes.getInt(R.styleable.EnhanceSeekBar_mcEProgress, 0));
        setItemsCount(obtainStyledAttributes.getInt(R.styleable.EnhanceSeekBar_mcEItemsCount, 1));
        Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.EnhanceSeekBar_mcEThumb);
        if (drawable == null) {
            drawable = context.getResources().getDrawable(R.drawable.mz_scrubber_control_selector);
        }
        this.mAuraThumb = obtainStyledAttributes.getDrawable(R.styleable.EnhanceSeekBar_mcAuraEnhanceThumbDrawble);
        this.mAuraRadis = (int) obtainStyledAttributes.getDimension(R.styleable.EnhanceSeekBar_mcAuraEnhanceDistance, 10.0f);
        setThumb(drawable);
        obtainStyledAttributes.recycle();
        this.mPaint = new Paint();
        this.mPaintColor = getResources().getColor(R.color.mc_enhance_seekbar_circle_color);
        this.mPaint.setColor(this.mPaintColor);
        this.mPaint.setStrokeWidth(4.0f);
        if (VERSION.SDK_INT >= 21) {
            this.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        } else {
            this.mInterpolator = new AccelerateInterpolator();
        }
    }

    public void setOnEnhanceSeekBarChangeListener(OnEnhanceSeekBarChangeListener onEnhanceSeekBarChangeListener) {
        this.mOnEnhanceSeekBarChangeListener = onEnhanceSeekBarChangeListener;
    }

    public void setItems(CharSequence[] charSequenceArr) {
        if (charSequenceArr == null || charSequenceArr.length == 0) {
            this.mItems = null;
            setMax(0);
            return;
        }
        int length = charSequenceArr.length;
        this.mItems = new CharSequence[length];
        System.arraycopy(charSequenceArr, 0, this.mItems, 0, length);
        setMax(length - 1);
    }

    public void setItemsCount(int i) {
        if (this.mItems != null && this.mItems.length < i) {
            setMax(this.mItems.length - 1);
        } else if (i < 1) {
            setMax(0);
        } else {
            setMax(i - 1);
        }
    }

    public void setThumb(Drawable drawable) {
        Object obj;
        if (drawable == null) {
            drawable = getResources().getDrawable(R.drawable.mz_scrubber_control_selector);
        }
        if (this.mThumb == null || drawable == this.mThumb) {
            obj = null;
        } else {
            this.mThumb.setCallback(null);
            this.mThumb.getIntrinsicWidth();
            obj = 1;
        }
        if (drawable != null) {
            if (obj != null) {
                drawable.setCallback(this);
                this.mThumbOffset = drawable.getIntrinsicWidth() / 2;
            } else {
                drawable.setCallback(this);
                this.mThumbOffset = drawable.getIntrinsicWidth() / 2;
            }
            if (!(obj == null || (drawable.getIntrinsicWidth() == this.mThumb.getIntrinsicWidth() && drawable.getIntrinsicHeight() == this.mThumb.getIntrinsicHeight()))) {
                requestLayout();
            }
            this.mHalfThumbWidth = drawable.getIntrinsicWidth() / 2;
            this.mHalfThumbHeight = drawable.getIntrinsicHeight() / 2;
        }
        this.mThumb = drawable;
        invalidate();
        if (obj != null) {
            updateThumbPos(getWidth(), getHeight());
            if (drawable != null && drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
        }
    }

    public Drawable getThumb() {
        return this.mThumb;
    }

    private void setThumbOffset(int i) {
        this.mThumbOffset = i;
        invalidate();
    }

    public synchronized void setProgress(int i) {
        setProgress(i, false);
    }

    synchronized void setProgress(int i, boolean z) {
        int i2;
        if (i < 0) {
            i2 = 0;
        } else {
            i2 = i;
        }
        if (i2 > this.mMax) {
            i2 = this.mMax;
        }
        if (!(i2 == this.mProgress && this.mIsInItemPositon)) {
            this.mProgress = i2;
            if (!z) {
                onProgressRefresh(this.mMax > 0 ? ((float) this.mProgress) / ((float) this.mMax) : 0.0f);
            } else if (this.mOnEnhanceSeekBarChangeListener != null && this.mIsDragging) {
                this.mOnEnhanceSeekBarChangeListener.onProgressDragging(this, getProgress());
            }
        }
    }

    public synchronized int getProgress() {
        return this.mProgress;
    }

    public synchronized int getItemsCount() {
        return this.mItems != null ? this.mItems.length : this.mMax;
    }

    private synchronized int getMax() {
        return this.mMax;
    }

    private synchronized void setMax(int i) {
        if (i < 0) {
            i = 0;
        }
        if (i != this.mMax) {
            this.mMax = i;
            if (this.mProgress > i) {
                this.mProgress = i;
            }
            onProgressRefresh(this.mMax > 0 ? ((float) this.mProgress) / ((float) this.mMax) : 0.0f);
        }
    }

    private void onProgressRefresh(float f) {
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            setThumbPos(getWidth(), drawable, f, Integer.MIN_VALUE);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mThumb != null && this.mThumb.isStateful()) {
            this.mThumb.setState(getDrawableState());
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        updateThumbPos(i, i2);
    }

    private void updateThumbPos(int i, int i2) {
        Drawable drawable = this.mThumb;
        int max = getMax();
        float progress = max > 0 ? ((float) getProgress()) / ((float) max) : 0.0f;
        if (drawable != null) {
            setThumbPos(i, drawable, progress, 0);
        }
    }

    private void setThumbPos(int i, Drawable drawable, float f, int i2) {
        int i3;
        int i4;
        boolean z;
        int paddingLeft = ((i - getPaddingLeft()) - getPaddingRight()) - (this.mAuraRadis * 2);
        int intrinsicWidth = drawable.getIntrinsicWidth();
        paddingLeft -= intrinsicWidth;
        int round = paddingLeft - Math.round((1.0f - f) * ((float) paddingLeft));
        if (i2 == Integer.MIN_VALUE) {
            Rect bounds = drawable.getBounds();
            i3 = bounds.top;
            i4 = bounds.bottom;
            z = true;
        } else {
            i4 = drawable.getIntrinsicHeight() + i2;
            i3 = i2;
            z = false;
        }
        if (this.mObjectAnimator != null) {
            if (!z) {
                this.mObjectAnimator.cancel();
                this.mObjectAnimator = null;
            } else if (this.mObjectAnimator.isStarted()) {
                z = false;
            }
        }
        if (z) {
            paddingLeft = drawable.getBounds().left;
            if (paddingLeft == round) {
                this.mIsInItemPositon = true;
                if (i2 == Integer.MIN_VALUE && this.mOnEnhanceSeekBarChangeListener != null) {
                    this.mOnEnhanceSeekBarChangeListener.onProgressChanged(this, getProgress());
                    return;
                }
                return;
            }
            this.mStartXY.setXY((float) paddingLeft, (float) i3);
            this.mEndXY.setXY((float) round, (float) i3);
            this.mDrawableXYHolder.setDrawable(drawable);
            this.mObjectAnimator = ObjectAnimator.ofObject(this.mDrawableXYHolder, "xY", this.mXYEvaluator, new Object[]{this.mStartXY, this.mEndXY});
            this.mObjectAnimator.setDuration(256);
            this.mObjectAnimator.setInterpolator(this.mInterpolator);
            this.mObjectAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    if (EnhanceSeekBar.this.mOnEnhanceSeekBarChangeListener != null) {
                        EnhanceSeekBar.this.mOnEnhanceSeekBarChangeListener.onProgressChanged(EnhanceSeekBar.this, EnhanceSeekBar.this.getProgress());
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (EnhanceSeekBar.this.mOnEnhanceSeekBarChangeListener != null) {
                        EnhanceSeekBar.this.mOnEnhanceSeekBarChangeListener.onProgressChanged(EnhanceSeekBar.this, EnhanceSeekBar.this.getProgress());
                    }
                }
            });
            this.mObjectAnimator.start();
        } else {
            this.mThumb.setBounds(round, i3, round + intrinsicWidth, i4);
            invalidate();
        }
        this.mIsInItemPositon = true;
    }

    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mThumb != null) {
            int i;
            int i2;
            canvas.save();
            if (this.mItems != null) {
                canvas.translate((float) ((getPaddingLeft() + this.mHalfThumbWidth) + this.mAuraRadis), (float) ((getPaddingTop() + 50) + this.mHalfThumbHeight));
            } else {
                canvas.translate((float) ((getPaddingLeft() + this.mHalfThumbWidth) + this.mAuraRadis), (float) (getPaddingTop() + this.mHalfThumbHeight));
            }
            float width = (float) ((((getWidth() - getPaddingLeft()) - getPaddingRight()) - (this.mHalfThumbWidth * 2)) - (this.mAuraRadis * 2));
            int max = getMax();
            float f = max > 0 ? width / ((float) max) : 0.0f;
            this.mPaint.setColor(this.mPaintColor);
            this.mPaint.setAntiAlias(true);
            canvas.drawLine(0.0f, 0.0f, width, 0.0f, this.mPaint);
            for (i = 0; i <= max; i++) {
                canvas.drawCircle(((float) i) * f, 0.0f, 6.0f, this.mPaint);
            }
            this.mPaint.setTextSize(40.0f);
            for (i2 = 0; i2 <= max; i2++) {
                if (getProgress() == i2) {
                    this.mPaint.setColor(-12303292);
                } else {
                    this.mPaint.setColor(this.mPaintColor);
                }
                if (this.mItems != null) {
                    String str = (String) this.mItems[i2];
                    canvas.drawText(str, (((float) i2) * f) - (this.mPaint.measureText(str) / CircleProgressBar.BAR_WIDTH_DEF_DIP), (float) (-this.mHalfThumbHeight), this.mPaint);
                }
            }
            canvas.restore();
            canvas.save();
            if (this.mItems != null) {
                canvas.translate((float) (getPaddingLeft() + this.mAuraRadis), (float) (getPaddingTop() + 50));
            } else {
                canvas.translate((float) (getPaddingLeft() + this.mAuraRadis), (float) getPaddingTop());
            }
            if (this.mIsDrag && this.mAuraThumb != null) {
                i = this.mThumb.getBounds().centerX();
                i2 = this.mThumb.getBounds().centerY();
                int intrinsicWidth = this.mThumb.getIntrinsicWidth() / 2;
                this.mAuraThumb.setBounds((i - intrinsicWidth) - this.mAuraRadis, (i2 - intrinsicWidth) - this.mAuraRadis, (i + intrinsicWidth) + this.mAuraRadis, (i2 + intrinsicWidth) + this.mAuraRadis);
                this.mAuraThumb.setAlpha(204);
                this.mAuraThumb.draw(canvas);
            }
            this.mThumb.draw(canvas);
            canvas.restore();
        }
    }

    protected synchronized void onMeasure(int i, int i2) {
        int i3 = 0;
        synchronized (this) {
            int intrinsicHeight = this.mThumb == null ? 0 : this.mThumb.getIntrinsicHeight();
            int i4 = 20;
            if (this.mItems != null) {
                i4 = 70;
            }
            int paddingLeft = (getPaddingLeft() + 64) + getPaddingRight();
            if (intrinsicHeight != 0) {
                if (this.mItems != null) {
                    i3 = 50;
                }
                i4 = Math.max(i3 + intrinsicHeight, i4);
            }
            setMeasuredDimension(resolveSizeAndState(Math.max(paddingLeft, MeasureSpec.getSize(i)), i, 0), resolveSizeAndState(i4 + ((getPaddingTop() + getPaddingBottom()) + this.mAuraRadis), i2, 0));
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled() || getMax() == 0) {
            return false;
        }
        float x = motionEvent.getX();
        switch (motionEvent.getAction()) {
            case 0:
                this.mInitialTouchX = x;
                if (this.mThumb != null) {
                    this.mInitialThumbX = (float) this.mThumb.getBounds().left;
                }
                this.mTouchDownProgress = Math.round((((x - ((float) getPaddingLeft())) - ((float) this.mAuraRadis)) / ((float) ((((getWidth() - getPaddingLeft()) - getPaddingRight()) - (this.mHalfThumbWidth * 2)) - (this.mAuraRadis * 2)))) * ((float) getMax()));
                if (isPointInside((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    setPressed(true);
                    if (this.mThumb != null) {
                        invalidate(this.mThumb.getBounds());
                    }
                    onStartTrackingTouch();
                }
                attemptClaimDrag();
                this.hasMoved = false;
                break;
            case 1:
                if (!this.hasMoved) {
                    trackTapUpTouchEvent(motionEvent);
                    break;
                }
                this.mIsDrag = false;
                if (this.mAuraThumb != null) {
                    invalidate(this.mAuraThumb.getBounds());
                }
                if (!this.mIsDragging) {
                    setProgress(this.mTouchDownProgress, false);
                    break;
                }
                trackTouchEvent(motionEvent);
                onStopTrackingTouch();
                setPressed(false);
                break;
            case 2:
                if (this.mIsDragging) {
                    this.mIsInItemPositon = false;
                    flingThumb(motionEvent);
                    attemptClaimDrag();
                }
                if (Math.abs(motionEvent.getX() - this.mInitialTouchX) <= ((float) this.mScaledTouchSlop)) {
                    this.hasMoved = false;
                    break;
                }
                this.hasMoved = true;
                this.mIsDrag = true;
                if (this.mAuraThumb != null) {
                    invalidate(this.mAuraThumb.getBounds());
                    break;
                }
                break;
            case 3:
                if (this.mIsDragging) {
                    onStopTrackingTouch();
                    setPressed(false);
                }
                invalidate();
                break;
        }
        return true;
    }

    private boolean isPointInside(int i, int i2) {
        return true;
    }

    private void flingThumb(MotionEvent motionEvent) {
        int width = getWidth();
        int paddingLeft = (((width - getPaddingLeft()) - getPaddingRight()) - (this.mHalfThumbWidth * 2)) - (this.mAuraRadis * 2);
        int x = (int) motionEvent.getX();
        Rect bounds = this.mThumb.getBounds();
        x = (int) ((((float) x) + this.mInitialThumbX) - this.mInitialTouchX);
        if (x < 0) {
            x = 0;
        } else if (x > ((width - getPaddingRight()) - (this.mHalfThumbWidth * 2)) - (this.mAuraRadis * 2)) {
            x = ((width - getPaddingRight()) - (this.mHalfThumbWidth * 2)) - (this.mAuraRadis * 2);
        }
        setProgress(Math.round((((float) ((x - getPaddingLeft()) - this.mAuraRadis)) / ((float) paddingLeft)) * ((float) getMax())), true);
        this.mThumb.setBounds(x, bounds.top, this.mThumb.getIntrinsicWidth() + x, bounds.bottom);
        invalidate();
    }

    private void trackTouchEvent(MotionEvent motionEvent) {
        float f;
        int width = getWidth();
        int paddingLeft = (((width - getPaddingLeft()) - getPaddingRight()) - (this.mHalfThumbWidth * 2)) - (this.mAuraRadis * 2);
        int x = (int) ((((float) ((int) motionEvent.getX())) + this.mInitialThumbX) - this.mInitialTouchX);
        if (x < 0) {
            f = 0.0f;
        } else if (x > (width - getPaddingRight()) - (this.mHalfThumbWidth * 2)) {
            f = 1.0f;
        } else {
            f = ((float) ((x - getPaddingLeft()) - this.mAuraRadis)) / ((float) paddingLeft);
        }
        setProgress(Math.round((f * ((float) getMax())) + 0.0f), false);
    }

    private void trackTapUpTouchEvent(MotionEvent motionEvent) {
        float round = (float) Math.round((((((float) ((int) motionEvent.getX())) - ((float) getPaddingLeft())) - ((float) this.mAuraRadis)) / ((float) ((((getWidth() - getPaddingLeft()) - getPaddingRight()) - (this.mHalfThumbWidth * 2)) - (this.mAuraRadis * 2)))) * ((float) getMax()));
        float f = round / ((float) this.mMax);
        setProgress((int) round, true);
        setThumbPos(getWidth(), this.mThumb, f, Integer.MIN_VALUE);
    }

    private void attemptClaimDrag() {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }

    void onStartTrackingTouch() {
        this.mIsDragging = true;
        if (this.mOnEnhanceSeekBarChangeListener != null) {
            this.mOnEnhanceSeekBarChangeListener.onStartTrackingTouch(this);
        }
    }

    void onStopTrackingTouch() {
        this.mIsDragging = false;
        if (this.mOnEnhanceSeekBarChangeListener != null) {
            this.mOnEnhanceSeekBarChangeListener.onStopTrackingTouch(this);
        }
    }

    public Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        savedState.progress = this.mProgress;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setProgress(savedState.progress, true);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(EnhanceSeekBar.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(EnhanceSeekBar.class.getName());
        if (isEnabled()) {
            int progress = getProgress();
            if (progress > 0) {
                accessibilityNodeInfo.addAction(8192);
            }
            if (progress < getMax()) {
                accessibilityNodeInfo.addAction(4096);
            }
        }
    }

    public boolean performAccessibilityAction(int i, Bundle bundle) {
        if (super.performAccessibilityAction(i, bundle)) {
            return true;
        }
        if (!isEnabled()) {
            return false;
        }
        int progress = getProgress();
        int max = Math.max(1, Math.round(((float) getMax()) / 5.0f));
        switch (i) {
            case 4096:
                if (progress >= getMax()) {
                    return false;
                }
                setProgress(progress + max, false);
                return true;
            case 8192:
                if (progress <= 0) {
                    return false;
                }
                setProgress(progress - max, false);
                return true;
            default:
                return false;
        }
    }

    public void setPaintColor(int i) {
        this.mPaintColor = i;
    }
}
