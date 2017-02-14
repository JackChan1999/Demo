package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.meizu.common.R;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;

public abstract class AbsSeekBar extends ProgressBar {
    private static final int NO_ALPHA = 255;
    public static final int SEEK_BAR_SCROLL_MODE_COMMON = 0;
    public static final int SEEK_BAR_SCROLL_MODE_SLOW = 1;
    private float mDisabledAlpha;
    private float mDragTouchDownX;
    private float mDragTouchDownY = 0.0f;
    private int mHalfThumbHeight = 0;
    private int mHalfThumbWidth = 0;
    private boolean mInDragoning = false;
    private boolean mIsDragging;
    boolean mIsUserSeekable = true;
    boolean mIsVertical = false;
    private int mKeyProgressIncrement = 1;
    private int mScaledTouchSlop;
    private Drawable mThumb;
    private int mThumbOffset;
    private int mTouchDownProgress = 0;
    private float mTouchDownX;
    private float mTouchDownY;
    float mTouchProgressOffset;
    protected int mTouchScrollMode = 0;
    private int mTouchSlopSquare = 256;

    public AbsSeekBar(Context context) {
        super(context);
    }

    public AbsSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AbsSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SeekBar, i, 0);
        setThumb(obtainStyledAttributes.getDrawable(R.styleable.SeekBar_mcThumb));
        setThumbOffset(obtainStyledAttributes.getDimensionPixelOffset(R.styleable.SeekBar_mcThumbOffset, getThumbOffset()));
        obtainStyledAttributes.recycle();
        this.mDisabledAlpha = FastBlurParameters.DEFAULT_LEVEL;
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mTouchSlopSquare = this.mScaledTouchSlop * this.mScaledTouchSlop;
    }

    public void setThumb(Drawable drawable) {
        Object obj;
        if (this.mThumb == null || drawable == this.mThumb) {
            obj = null;
        } else {
            this.mThumb.setCallback(null);
            obj = 1;
        }
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.mIsVertical) {
                this.mThumbOffset = drawable.getIntrinsicHeight() / 2;
            } else {
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
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
        }
    }

    public Drawable getThumb() {
        return this.mThumb;
    }

    public int getThumbOffset() {
        return this.mThumbOffset;
    }

    public void setThumbOffset(int i) {
        this.mThumbOffset = i;
        invalidate();
    }

    public void setKeyProgressIncrement(int i) {
        if (i < 0) {
            i = -i;
        }
        this.mKeyProgressIncrement = i;
    }

    public int getKeyProgressIncrement() {
        return this.mKeyProgressIncrement;
    }

    public synchronized void setMax(int i) {
        super.setMax(i);
        if (this.mKeyProgressIncrement == 0 || getMax() / this.mKeyProgressIncrement > 20) {
            setKeyProgressIncrement(Math.max(1, Math.round(((float) getMax()) / 20.0f)));
        }
    }

    protected boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mThumb || super.verifyDrawable(drawable);
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mThumb != null) {
            this.mThumb.jumpToCurrentState();
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable != null) {
            progressDrawable.setAlpha(isEnabled() ? 255 : (int) (255.0f * this.mDisabledAlpha));
        }
        if (this.mThumb != null && this.mThumb.isStateful()) {
            this.mThumb.setState(getDrawableState());
        }
    }

    void onProgressRefresh(float f, boolean z) {
        super.onProgressRefresh(f, z);
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            setThumbPos(getWidth(), getHeight(), drawable, f, Integer.MIN_VALUE);
            invalidate();
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        updateThumbPos(i, i2);
    }

    private void updateThumbPos(int i, int i2) {
        float f = 0.0f;
        Drawable currentDrawable = getCurrentDrawable();
        Drawable drawable = this.mThumb;
        int intrinsicWidth;
        int min;
        int max;
        int i3;
        if (this.mIsVertical) {
            intrinsicWidth = drawable == null ? 0 : drawable.getIntrinsicWidth();
            min = Math.min(this.mMaxWidth, (i - getPaddingLeft()) - getPaddingRight());
            max = getMax();
            if (max > 0) {
                f = ((float) getProgress()) / ((float) max);
            }
            if (intrinsicWidth > min) {
                if (drawable != null) {
                    setThumbPos(i, i2, drawable, f, 0);
                }
                max = (intrinsicWidth - min) / 2;
                if (currentDrawable != null) {
                    currentDrawable.setBounds(max, 0, ((i - getPaddingRight()) - max) - getPaddingLeft(), (i2 - getPaddingBottom()) - getPaddingTop());
                    return;
                }
                return;
            }
            if (currentDrawable != null) {
                currentDrawable.setBounds(0, 0, (i - getPaddingRight()) - getPaddingLeft(), (i2 - getPaddingBottom()) - getPaddingTop());
            }
            i3 = (min - intrinsicWidth) / 2;
            if (drawable != null) {
                setThumbPos(i, i2, drawable, f, i3);
                return;
            }
            return;
        }
        intrinsicWidth = drawable == null ? 0 : drawable.getIntrinsicHeight();
        min = Math.min(this.mMaxHeight, (i2 - getPaddingTop()) - getPaddingBottom());
        max = getMax();
        if (max > 0) {
            f = ((float) getProgress()) / ((float) max);
        }
        if (intrinsicWidth > min) {
            if (drawable != null) {
                setThumbPos(i, i2, drawable, f, 0);
            }
            max = (intrinsicWidth - min) / 2;
            if (currentDrawable != null) {
                currentDrawable.setBounds(0, max, (i - getPaddingRight()) - getPaddingLeft(), ((i2 - getPaddingBottom()) - max) - getPaddingTop());
                return;
            }
            return;
        }
        if (currentDrawable != null) {
            currentDrawable.setBounds(0, 0, (i - getPaddingRight()) - getPaddingLeft(), (i2 - getPaddingBottom()) - getPaddingTop());
        }
        i3 = (min - intrinsicWidth) / 2;
        if (drawable != null) {
            setThumbPos(i, i2, drawable, f, i3);
        }
    }

    private void setThumbPos(int i, int i2, Drawable drawable, float f, int i3) {
        int paddingTop;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (this.mIsVertical) {
            paddingTop = ((i2 - getPaddingTop()) - getPaddingBottom()) - intrinsicHeight;
        } else {
            paddingTop = ((i - getPaddingLeft()) - getPaddingRight()) - intrinsicWidth;
        }
        paddingTop += this.mThumbOffset * 2;
        if (this.mIsVertical) {
            int i4 = (int) (((float) paddingTop) * (1.0f - f));
            if (i3 == Integer.MIN_VALUE) {
                Rect bounds = drawable.getBounds();
                i3 = bounds.left;
                paddingTop = bounds.right;
            } else {
                paddingTop = i3 + intrinsicWidth;
            }
            drawable.setBounds(i3, i4, paddingTop, i4 + intrinsicHeight);
            return;
        }
        i4 = (int) (((float) paddingTop) * f);
        if (i3 == Integer.MIN_VALUE) {
            bounds = drawable.getBounds();
            i3 = bounds.top;
            paddingTop = bounds.bottom;
        } else {
            paddingTop = i3 + intrinsicHeight;
        }
        drawable.setBounds(i4, i3, intrinsicWidth + i4, paddingTop);
    }

    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mThumb != null) {
            canvas.save();
            if (this.mIsVertical) {
                canvas.translate((float) getPaddingLeft(), (float) (getPaddingTop() - this.mThumbOffset));
                this.mThumb.draw(canvas);
                canvas.restore();
            } else {
                canvas.translate((float) (getPaddingLeft() - this.mThumbOffset), (float) getPaddingTop());
                this.mThumb.draw(canvas);
                canvas.restore();
            }
        }
    }

    protected synchronized void onMeasure(int i, int i2) {
        int i3 = 0;
        synchronized (this) {
            int max;
            Drawable currentDrawable = getCurrentDrawable();
            int intrinsicHeight = this.mThumb == null ? 0 : this.mThumb.getIntrinsicHeight();
            if (currentDrawable != null) {
                max = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, currentDrawable.getIntrinsicWidth()));
                i3 = Math.max(intrinsicHeight, Math.max(this.mMinHeight, Math.min(this.mMaxHeight, currentDrawable.getIntrinsicHeight())));
            } else {
                max = 0;
            }
            setMeasuredDimension(resolveSizeAndState(max + (getPaddingLeft() + getPaddingRight()), i, 0), resolveSizeAndState(i3 + (getPaddingTop() + getPaddingBottom()), i2, 0));
            if (getMeasuredHeight() > getMeasuredWidth()) {
                this.mIsVertical = true;
            }
        }
    }

    public boolean isInScrollingContainer() {
        ViewParent parent = getParent();
        while (parent != null && (parent instanceof ViewGroup)) {
            if (((ViewGroup) parent).shouldDelayChildPressedState()) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mIsUserSeekable || !isEnabled()) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int height;
        float f;
        float f2;
        int max;
        switch (motionEvent.getAction()) {
            case 0:
                if (!isInScrollingContainer()) {
                    setPressed(true);
                    if (this.mThumb != null) {
                        invalidate(this.mThumb.getBounds());
                    }
                    onStartTrackingTouch();
                    this.mTouchDownProgress = 0;
                    if (this.mTouchScrollMode == 1) {
                        if (this.mIsVertical) {
                            this.mDragTouchDownX = x;
                            this.mDragTouchDownY = motionEvent.getY();
                        } else {
                            this.mDragTouchDownX = motionEvent.getX();
                            this.mDragTouchDownY = y;
                        }
                        this.mInDragoning = false;
                        this.mTouchDownProgress = getProgress();
                    } else {
                        trackTouchEvent(motionEvent);
                    }
                    attemptClaimDrag();
                    break;
                }
                this.mTouchDownX = x;
                this.mTouchDownY = y;
                if (this.mTouchScrollMode == 1) {
                    onStartTrackingTouch();
                    this.mDragTouchDownX = x;
                    this.mDragTouchDownY = y;
                    this.mInDragoning = false;
                    this.mTouchDownProgress = getProgress();
                    attemptClaimDrag();
                    break;
                }
                break;
            case 1:
                if (this.mIsDragging) {
                    if (this.mIsVertical) {
                        height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                    } else {
                        height = (getWidth() - getPaddingLeft()) - getPaddingRight();
                    }
                    if (this.mTouchScrollMode == 1 && !this.mInDragoning) {
                        if (this.mIsVertical) {
                            f = y;
                        } else {
                            f = x;
                        }
                        if ((((float) height) - f) + ((float) getPaddingBottom()) < ((float) (getPosByProgress(this.mTouchDownProgress) - this.mHalfThumbWidth)) || (((float) height) - f) + ((float) getPaddingBottom()) > ((float) (getPosByProgress(this.mTouchDownProgress) + this.mHalfThumbWidth))) {
                            if (getProgressByPos((int) f) >= this.mTouchDownProgress + this.mKeyProgressIncrement) {
                                setProgress(this.mTouchDownProgress + this.mKeyProgressIncrement, true);
                            } else if (getProgressByPos((int) f) < this.mTouchDownProgress + this.mKeyProgressIncrement) {
                                setProgress(this.mTouchDownProgress - this.mKeyProgressIncrement, true);
                            }
                        }
                    } else if (this.mTouchScrollMode != 1 || !this.mInDragoning) {
                        trackTouchEvent(motionEvent);
                    } else if (height != 0) {
                        f = x - this.mDragTouchDownX;
                        y = this.mDragTouchDownY - y;
                        if (this.mIsVertical) {
                            f2 = y / ((float) height);
                        } else {
                            f2 = f / ((float) height);
                        }
                        max = getMax();
                        setProgress(getIntFromFloat(f2 * ((float) max)) + this.mTouchDownProgress, true);
                    }
                    onStopTrackingTouch();
                    setPressed(false);
                } else {
                    onStartTrackingTouch();
                    trackTouchEvent(motionEvent);
                    onStopTrackingTouch();
                }
                invalidate();
                this.mInDragoning = false;
                break;
            case 2:
                if (this.mIsDragging) {
                    if (this.mTouchScrollMode != 1) {
                        trackTouchEvent(motionEvent);
                        break;
                    }
                    if (this.mIsVertical) {
                        f = Math.abs(motionEvent.getX() - this.mDragTouchDownX);
                        f2 = Math.abs(y - this.mDragTouchDownY);
                    } else {
                        f = Math.abs(x - this.mDragTouchDownX);
                        f2 = Math.abs(motionEvent.getY() - this.mDragTouchDownY);
                    }
                    if ((f2 * f2) + (f * f) > ((float) this.mTouchSlopSquare) && !this.mInDragoning) {
                        this.mDragTouchDownX = x;
                        this.mInDragoning = true;
                    }
                    if (this.mInDragoning) {
                        if (this.mIsVertical) {
                            height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                        } else {
                            height = (getWidth() - getPaddingLeft()) - getPaddingRight();
                        }
                        if (height != 0) {
                            if (this.mIsVertical) {
                                f2 = (this.mDragTouchDownY - y) / ((float) height);
                            } else {
                                f2 = (x - this.mDragTouchDownX) / ((float) height);
                            }
                            max = getMax();
                            setProgress(getIntFromFloat(f2 * ((float) max)) + this.mTouchDownProgress, true);
                            break;
                        }
                    }
                }
                if (this.mIsVertical) {
                    f2 = Math.abs(y - this.mTouchDownY);
                } else {
                    f2 = Math.abs(x - this.mTouchDownX);
                }
                if (f2 > ((float) this.mScaledTouchSlop)) {
                    setPressed(true);
                    if (this.mThumb != null) {
                        invalidate(this.mThumb.getBounds());
                    }
                    onStartTrackingTouch();
                    trackTouchEvent(motionEvent);
                    attemptClaimDrag();
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

    private void trackTouchEvent(MotionEvent motionEvent) {
        float f = 1.0f;
        float f2 = 0.0f;
        int height;
        int paddingTop;
        int y;
        if (this.mIsVertical) {
            height = getHeight();
            paddingTop = (height - getPaddingTop()) - getPaddingBottom();
            y = (int) motionEvent.getY();
            if (y >= getPaddingTop()) {
                if (y > height - getPaddingBottom()) {
                    f = 0.0f;
                } else {
                    f = 1.0f - (((float) (y - getPaddingTop())) / ((float) paddingTop));
                    f2 = this.mTouchProgressOffset;
                }
            }
            f = (f * ((float) getMax())) + f2;
        } else {
            height = getWidth();
            paddingTop = (height - getPaddingLeft()) - getPaddingRight();
            y = (int) motionEvent.getX();
            if (y < getPaddingLeft()) {
                f = 0.0f;
            } else if (y > height - getPaddingRight()) {
                f = 0.0f;
                f2 = 1.0f;
            } else {
                f2 = ((float) (y - getPaddingLeft())) / ((float) paddingTop);
                f = this.mTouchProgressOffset;
            }
            f += f2 * ((float) getMax());
        }
        setProgress((int) f, true);
    }

    private void attemptClaimDrag() {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }

    void onStartTrackingTouch() {
        this.mIsDragging = true;
    }

    void onStopTrackingTouch() {
        this.mIsDragging = false;
    }

    void onKeyChange() {
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (isEnabled()) {
            int progress = getProgress();
            if ((i != 21 || this.mIsVertical) && !(i == 20 && this.mIsVertical)) {
                if (((i == 22 && !this.mIsVertical) || (i == 19 && this.mIsVertical)) && progress < getMax()) {
                    setProgress(progress + this.mKeyProgressIncrement, true);
                    onKeyChange();
                    return true;
                }
            } else if (progress > 0) {
                setProgress(progress - this.mKeyProgressIncrement, true);
                onKeyChange();
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    protected void setTouchMode(int i) {
        this.mTouchScrollMode = i;
        if (this.mTouchScrollMode > 1) {
            this.mTouchScrollMode = 0;
        }
    }

    private int getIntFromFloat(float f) {
        return Math.round(f);
    }

    private int getProgressByPos(int i) {
        int height;
        if (this.mIsVertical) {
            height = (getHeight() - getPaddingTop()) - getPaddingBottom();
            i = height - i;
        } else {
            height = (getWidth() - getPaddingLeft()) - getPaddingRight();
        }
        return (int) (((float) ((getMax() * i) / height)) - this.mTouchProgressOffset);
    }

    private int getPosByProgress(int i) {
        int height;
        if (this.mIsVertical) {
            height = (getHeight() - getPaddingTop()) - getPaddingBottom();
        } else {
            height = (getWidth() - getPaddingLeft()) - getPaddingRight();
        }
        int paddingLeft = getPaddingLeft();
        int paddingBottom = getPaddingBottom();
        float f = ((float) i) - this.mTouchProgressOffset;
        if (f < 0.0f) {
            if (this.mIsVertical) {
                return paddingBottom;
            }
            return paddingLeft;
        } else if (f > ((float) getMax())) {
            if (this.mIsVertical) {
                return getHeight() - getPaddingBottom();
            }
            return getWidth() - getPaddingRight();
        } else if (getMax() > 0) {
            paddingLeft += (int) (((float) height) * (f / ((float) getMax())));
            height = ((int) (((float) height) * (f / ((float) getMax())))) + paddingBottom;
            if (this.mIsVertical) {
                return height;
            }
            return paddingLeft;
        } else if (this.mIsVertical) {
            return paddingBottom;
        } else {
            return paddingLeft;
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(AbsSeekBar.class.getName());
    }
}
