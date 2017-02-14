package com.meizu.common.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.CompoundButton;
import com.amap.api.maps2d.model.GroundOverlayOptions;
import com.meizu.common.R;
import com.meizu.common.interpolator.PathInterpolatorCompat;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;

public class Switch extends CompoundButton {
    private static final int[] CHECKED_STATE_SET = new int[]{android.R.attr.state_checked};
    private static final int THUMB_ANIMATION_DURATION = 250;
    private static final float THUMB_STATUS_CHANGE = 0.8f;
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;
    private static int[] sSwitchStyleableId;
    private boolean isChanged;
    private int mMinFlingVelocity;
    private Layout mOffLayout;
    private Layout mOnLayout;
    private ObjectAnimator mPositionAnimator;
    private boolean mSplitTrack;
    private ValueAnimator mSwitchAnimator;
    private int mSwitchBottom;
    private int mSwitchHeight;
    private int mSwitchLeft;
    private int mSwitchMinWidth;
    private int mSwitchPadding;
    private int mSwitchRight;
    private int mSwitchTop;
    private TransformationMethod2 mSwitchTransformationMethod;
    private int mSwitchWidth;
    private final Rect mTempRect;
    private ColorStateList mTextColors;
    private TextPaint mTextPaint;
    private Drawable mThumbOffCache;
    private Drawable mThumbOffDrawable;
    private Drawable mThumbOnCache;
    private Drawable mThumbOnDrawable;
    private float mThumbPosition;
    private int mThumbWidth;
    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private Drawable mTrackCache;
    private Drawable mTrackDrawable;
    private VelocityTracker mVelocityTracker;
    private Interpolator pathInterpolator;
    public CharSequence switchOff;
    public CharSequence switchOn;

    public Switch(Context context) {
        this(context, null);
    }

    public Switch(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_Switch);
    }

    public Switch(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mTempRect = new Rect();
        this.mTextPaint = new TextPaint(1);
        Resources resources = getResources();
        this.mTextPaint.density = resources.getDisplayMetrics().density;
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.Switch, i, 0);
        this.mThumbOffDrawable = a.getDrawable(R.styleable.Switch_mcThumbOff);
        if (this.mThumbOffDrawable == null) {
            this.mThumbOffDrawable = resources.getDrawable(R.drawable.mc_switch_anim_thumb_off_selector);
        }
        if (this.mThumbOffDrawable != null) {
            this.mThumbOffDrawable.setCallback(this);
        }
        this.mThumbOnDrawable = a.getDrawable(R.styleable.Switch_mcThumbOn);
        if (this.mThumbOnDrawable == null) {
            this.mThumbOnDrawable = resources.getDrawable(R.drawable.mc_switch_anim_thumb_on_selector);
        }
        if (this.mThumbOnDrawable != null) {
            this.mThumbOnDrawable.setCallback(this);
        }
        this.mTrackDrawable = a.getDrawable(R.styleable.Switch_mcTrack);
        if (this.mTrackDrawable == null) {
            this.mTrackDrawable = resources.getDrawable(R.drawable.mc_switch_bg_default);
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(this);
        }
        this.mSwitchMinWidth = a.getDimensionPixelSize(R.styleable.Switch_mcSwitchMinWidth, 0);
        this.mSwitchPadding = a.getDimensionPixelSize(R.styleable.Switch_mcSwitchPadding, 0);
        this.mSplitTrack = false;
        a.recycle();
        sSwitchStyleableId = new int[]{16843044, 16843045};
        TypedArray array = context.obtainStyledAttributes(attributeSet, sSwitchStyleableId, 16843839, 0);
        this.switchOn = array.getText(0);
        this.switchOff = array.getText(1);
        array.recycle();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        refreshDrawableState();
        setChecked(isChecked());
    }

    public void setSwitchPadding(int i) {
        this.mSwitchPadding = i;
        requestLayout();
    }

    public int getSwitchPadding() {
        return this.mSwitchPadding;
    }

    public void setSwitchMinWidth(int i) {
        this.mSwitchMinWidth = i;
        requestLayout();
    }

    public int getSwitchMinWidth() {
        return this.mSwitchMinWidth;
    }

    public void setTrackDrawable(Drawable drawable) {
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(null);
        }
        this.mTrackDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public void setTrackResource(int i) {
        setTrackDrawable(getContext().getResources().getDrawable(i));
    }

    public Drawable getTrackDrawable() {
        return this.mTrackDrawable;
    }

    public void setThumbDrawable(Drawable drawable) {
        if (this.mThumbOnDrawable != null) {
            this.mThumbOnDrawable.setCallback(null);
        }
        this.mThumbOnDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public void setThumbResource(int i) {
        setThumbDrawable(getContext().getResources().getDrawable(i));
    }

    public Drawable getThumbDrawable() {
        return this.mThumbOnDrawable;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int intrinsicWidth;
        int intrinsicHeight;
        int i3 = 0;
        Rect rect = this.mTempRect;
        if (this.mThumbOnDrawable != null) {
            this.mThumbOnDrawable.getPadding(rect);
            intrinsicWidth = (this.mThumbOnDrawable.getIntrinsicWidth() - rect.left) - rect.right;
            intrinsicHeight = this.mThumbOnDrawable.getIntrinsicHeight();
        } else {
            intrinsicHeight = 0;
            intrinsicWidth = 0;
        }
        this.mThumbWidth = intrinsicWidth;
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.getPadding(rect);
            i3 = this.mTrackDrawable.getIntrinsicHeight();
        } else {
            rect.setEmpty();
        }
        int i4 = rect.left;
        intrinsicWidth = rect.right;
        if (this.mThumbOffDrawable != null) {
            Insets insets = Insets.NONE;
            i4 = Math.max(i4, insets.left);
            intrinsicWidth = Math.max(intrinsicWidth, insets.right);
        }
        intrinsicWidth = Math.max(this.mSwitchMinWidth, intrinsicWidth + (i4 + (this.mThumbWidth * 2)));
        intrinsicHeight = Math.max(i3, intrinsicHeight);
        this.mSwitchWidth = intrinsicWidth;
        this.mSwitchHeight = intrinsicHeight;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredHeight() < intrinsicHeight) {
            setMeasuredDimension(getMeasuredWidthAndState(), intrinsicHeight);
        }
    }

    @Override
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
    }

    private Layout makeLayout(CharSequence charSequence) {
        CharSequence transformation = this.mSwitchTransformationMethod != null ? this.mSwitchTransformationMethod.getTransformation(charSequence, this) : charSequence;
        this.mTextPaint = new TextPaint(1);
        this.mTextPaint.density = getResources().getDisplayMetrics().density;
        return new StaticLayout(transformation, this.mTextPaint, (int) Math.ceil((double) Layout.getDesiredWidth(transformation, this.mTextPaint)), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
    }

    private boolean hitThumb(float f, float f2) {
        return f > ((float) this.mSwitchLeft) && f < ((float) this.mSwitchRight) && f2 > ((float) this.mSwitchTop) && f2 < ((float) this.mSwitchBottom);
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        super.setCompoundDrawablesWithIntrinsicBounds(i, i2, i3, i4);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mVelocityTracker.addMovement(motionEvent);
        float x;
        float y;
        switch (motionEvent.getActionMasked()) {
            case 0:
                x = motionEvent.getX();
                y = motionEvent.getY();
                if (isEnabled() && hitThumb(x, y)) {
                    this.mTouchMode = 1;
                    this.mTouchX = x;
                    this.mTouchY = y;
                    break;
                }
            case 1:
            case 3:
                if (this.mTouchMode != 2) {
                    this.mTouchMode = 0;
                    this.mVelocityTracker.clear();
                    break;
                }
                stopDrag(motionEvent);
                super.onTouchEvent(motionEvent);
                return true;
            case 2:
                switch (this.mTouchMode) {
                    case 0:
                        break;
                    case 1:
                        x = motionEvent.getX();
                        y = motionEvent.getY();
                        if (Math.abs(x - this.mTouchX) > ((float) this.mTouchSlop) || Math.abs(y - this.mTouchY) > ((float) this.mTouchSlop)) {
                            this.mTouchMode = 2;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            this.mTouchX = x;
                            this.mTouchY = y;
                            return true;
                        }
                    case 2:
                        float x2 = motionEvent.getX();
                        int thumbScrollRange = getThumbScrollRange();
                        float f = x2 - this.mTouchX;
                        x = thumbScrollRange != 0 ? f / ((float) thumbScrollRange) : f > 0.0f ? 1.0f : GroundOverlayOptions.NO_DIMENSION;
                        if (isLayoutRtl()) {
                            x = -x;
                        }
                        x = MathUtils.constrain(x + this.mThumbPosition, 0.0f, 1.0f);
                        if (x != this.mThumbPosition) {
                            this.mTouchX = x2;
                            setThumbPosition(x);
                        }
                        return true;
                    default:
                        break;
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    private void cancelSuperTouch(MotionEvent motionEvent) {
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        super.onTouchEvent(obtain);
        obtain.recycle();
    }

    private void stopDrag(MotionEvent motionEvent) {
        boolean z = true;
        this.mTouchMode = 0;
        boolean z2 = motionEvent.getAction() == 1 && isEnabled();
        if (z2) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float xVelocity = this.mVelocityTracker.getXVelocity();
            if (Math.abs(xVelocity) <= ((float) this.mMinFlingVelocity)) {
                z = getTargetCheckedState();
            } else if (isLayoutRtl()) {
                if (xVelocity >= 0.0f) {
                    z = false;
                }
            } else if (xVelocity <= 0.0f) {
                z = false;
            }
        } else {
            z = isChecked();
        }
        setChecked(z);
        cancelSuperTouch(motionEvent);
    }

    private void animateThumbToCheckedState(boolean z) {
        float f = z ? 1.0f : 0.0f;
        if (this.mSwitchAnimator != null) {
            this.mSwitchAnimator.removeAllUpdateListeners();
        }
        this.mSwitchAnimator = ValueAnimator.ofFloat(new float[]{this.mThumbPosition, f});
        if (this.pathInterpolator == null) {
            if (VERSION.SDK_INT >= 21) {
                this.pathInterpolator = new PathInterpolator(0.33f, 0.0f, 0.33f, 1.0f);
            } else {
                this.pathInterpolator = new PathInterpolatorCompat(0.33f, 0.0f, 0.33f, 1.0f);
            }
        }
        this.mSwitchAnimator.setInterpolator(this.pathInterpolator);
        this.mSwitchAnimator.setDuration(250);
        this.mSwitchAnimator.start();
        this.mSwitchAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Switch.this.setThumbPosition(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
    }

    private void cancelPositionAnimator() {
        if (this.mSwitchAnimator != null) {
            this.mSwitchAnimator.cancel();
        }
    }

    private boolean getTargetCheckedState() {
        return this.mThumbPosition > FastBlurParameters.DEFAULT_LEVEL;
    }

    private void setThumbPosition(float f) {
        this.mThumbPosition = f;
        invalidate();
    }

    public void toggle() {
        setChecked(!isChecked());
    }

    public void setChecked(boolean z) {
        setChecked(z, true);
    }

    public void setChecked(boolean z, boolean z2) {
        super.setChecked(z);
        boolean isChecked = isChecked();
        if (z2 && VERSION.SDK_INT >= 19 && isAttachedToWindow() && isLaidOut()) {
            animateThumbToCheckedState(isChecked);
            return;
        }
        cancelPositionAnimator();
        setThumbPosition(isChecked ? 1.0f : 0.0f);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int max;
        int paddingLeft;
        int paddingTop;
        int i5 = 0;
        super.onLayout(changed, left, top, right, bottom);
        if (this.mThumbOffDrawable != null) {
            Rect rect = this.mTempRect;
            if (this.mTrackDrawable != null) {
                this.mTrackDrawable.getPadding(rect);
            } else {
                rect.setEmpty();
            }
            Insets insets = Insets.NONE;
            max = Math.max(0, insets.left - rect.left);
            i5 = Math.max(0, insets.right - rect.right);
        } else {
            max = 0;
        }
        if (isLayoutRtl()) {
            paddingLeft = getPaddingLeft() + max;
            max = ((this.mSwitchWidth + paddingLeft) - max) - i5;
            i5 = paddingLeft;
        } else {
            paddingLeft = (getWidth() - getPaddingRight()) - i5;
            i5 += max + (paddingLeft - this.mSwitchWidth);
            max = paddingLeft;
        }
        switch (getGravity() & 112) {
            case 16:
                paddingTop = (((getPaddingTop() + getHeight()) - getPaddingBottom()) / 2) - (this.mSwitchHeight / 2);
                paddingLeft = this.mSwitchHeight + paddingTop;
                break;
            case 80:
                paddingLeft = getHeight() - getPaddingBottom();
                paddingTop = paddingLeft - this.mSwitchHeight;
                break;
            default:
                paddingTop = getPaddingTop();
                paddingLeft = this.mSwitchHeight + paddingTop;
                break;
        }
        this.mSwitchLeft = i5;
        this.mSwitchTop = paddingTop;
        this.mSwitchBottom = paddingLeft;
        this.mSwitchRight = max;
    }

    public void draw(Canvas canvas) {
        Insets insets;
        int i;
        Rect rect = this.mTempRect;
        int i2 = this.mSwitchLeft;
        int i3 = this.mSwitchTop;
        int i4 = this.mSwitchRight;
        int i5 = this.mSwitchBottom;
        int thumbOffset = i2 + getThumbOffset();
        if (this.mThumbOffDrawable != null) {
            insets = Insets.NONE;
        } else {
            insets = Insets.NONE;
        }
        if (this.mTrackDrawable != null) {
            int i6;
            int i7;
            this.mTrackDrawable.getPadding(rect);
            int i8 = thumbOffset + rect.left;
            if (insets != Insets.NONE) {
                if (insets.left > rect.left) {
                    thumbOffset = (insets.left - rect.left) + i2;
                } else {
                    thumbOffset = i2;
                }
                if (insets.top > rect.top) {
                    i6 = (insets.top - rect.top) + i3;
                } else {
                    i6 = i3;
                }
                if (insets.right > rect.right) {
                    i7 = i4 - (insets.right - rect.right);
                } else {
                    i7 = i4;
                }
                i = insets.bottom > rect.bottom ? i5 - (insets.bottom - rect.bottom) : i5;
            } else {
                i = i5;
                i7 = i4;
                i6 = i3;
                thumbOffset = i2;
            }
            this.mTrackDrawable.setBounds(thumbOffset, i6, i7, i);
            i = i8;
        } else {
            i = thumbOffset;
        }
        if (this.mThumbOffDrawable != null && !isThumbOn()) {
            this.mThumbOffDrawable.getPadding(rect);
            if (isLayoutRtl()) {
                i = i4 - getThumbOffset();
                thumbOffset = (i - this.mThumbOffDrawable.getIntrinsicWidth()) - ((int) (((float) (this.mThumbOnDrawable.getIntrinsicWidth() - this.mThumbOffDrawable.getIntrinsicWidth())) * (1.0f - getThumbValue())));
            } else {
                thumbOffset = i;
                i = (this.mThumbOffDrawable.getIntrinsicWidth() + i) + ((int) (((float) (this.mThumbOnDrawable.getIntrinsicWidth() - this.mThumbOffDrawable.getIntrinsicWidth())) * (1.0f - getThumbValue())));
            }
            this.mThumbOffDrawable.setBounds(thumbOffset, i3, i, i5);
        } else if (this.mThumbOnDrawable != null && isThumbOn()) {
            this.mThumbOnDrawable.getPadding(rect);
            if (isLayoutRtl()) {
                thumbOffset = i2 - rect.left;
                i = (this.mThumbWidth + thumbOffset) + rect.right;
            } else {
                i = i4 - rect.right;
                thumbOffset = (i - this.mThumbWidth) - rect.left;
            }
            i2 = (int) ((((float) (i - thumbOffset)) * (1.0f - getThumbValue())) * 0.7f);
            this.mThumbOnDrawable.setBounds(thumbOffset + i2, i3 + i2, i - i2, i5 - i2);
        }
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = this.mTempRect;
        Drawable drawable = this.mTrackDrawable;
        if (drawable != null) {
            drawable.getPadding(rect);
        } else {
            rect.setEmpty();
        }
        Drawable drawable2 = this.mThumbOffDrawable;
        if (drawable != null) {
            if (!this.mSplitTrack || drawable2 == null) {
                drawable.draw(canvas);
            } else {
                Insets insets = Insets.NONE;
                drawable2.copyBounds(rect);
                rect.left += insets.left;
                rect.right -= insets.right;
                int save = canvas.save();
                canvas.clipRect(rect, Op.DIFFERENCE);
                drawable.draw(canvas);
                canvas.restoreToCount(save);
            }
        }
        int save2 = canvas.save();
        drawable = this.mThumbOnDrawable;
        if (drawable2 == null || isThumbOn()) {
            drawable.draw(canvas);
        } else {
            drawable2.mutate();
            drawable2.draw(canvas);
        }
        canvas.restoreToCount(save2);
    }

    public int getCompoundPaddingLeft() {
        if (!isLayoutRtl()) {
            return super.getCompoundPaddingLeft();
        }
        int compoundPaddingLeft = super.getCompoundPaddingLeft() + this.mSwitchWidth;
        if (TextUtils.isEmpty(getText())) {
            return compoundPaddingLeft;
        }
        return compoundPaddingLeft + this.mSwitchPadding;
    }

    public int getCompoundPaddingRight() {
        if (isLayoutRtl()) {
            return super.getCompoundPaddingRight();
        }
        int compoundPaddingRight = super.getCompoundPaddingRight() + this.mSwitchWidth;
        if (TextUtils.isEmpty(getText())) {
            return compoundPaddingRight;
        }
        return compoundPaddingRight + this.mSwitchPadding;
    }

    public boolean isLayoutRtl() {
        if (VERSION.SDK_INT < 17) {
            return false;
        }
        if (getLayoutDirection() == 1) {
            return true;
        }
        return false;
    }

    private int getThumbOffset() {
        if (isThumbOn()) {
            return getThumbScrollRange();
        }
        return (int) ((getThumbValue() * ((float) getThumbScrollRange())) + FastBlurParameters.DEFAULT_LEVEL);
    }

    private float getThumbValue() {
        if (isThumbOn()) {
            return (this.mThumbPosition - THUMB_STATUS_CHANGE) / 0.19999999f;
        }
        return this.mThumbPosition / THUMB_STATUS_CHANGE;
    }

    private boolean isThumbOn() {
        return this.mThumbPosition > THUMB_STATUS_CHANGE;
    }

    private int getThumbScrollRange() {
        if (this.mTrackDrawable == null) {
            return 0;
        }
        Insets insets;
        Rect rect = this.mTempRect;
        this.mTrackDrawable.getPadding(rect);
        if (this.mThumbOffDrawable != null) {
            insets = Insets.NONE;
        } else {
            insets = Insets.NONE;
        }
        return ((((this.mSwitchWidth - this.mThumbWidth) - rect.left) - rect.right) - insets.left) - insets.right;
    }

    protected int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (isChecked()) {
            mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        if (this.mThumbOffDrawable != null) {
            this.mThumbOffDrawable.setState(drawableState);
        }
        if (this.mThumbOnDrawable != null) {
            this.mThumbOnDrawable.setState(drawableState);
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setState(drawableState);
        }
        invalidate();
    }

    @Override
    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mThumbOffDrawable || drawable == this.mTrackDrawable;
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mThumbOffDrawable != null) {
            this.mThumbOffDrawable.jumpToCurrentState();
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.jumpToCurrentState();
        }
        if (this.mSwitchAnimator != null && this.mSwitchAnimator.isRunning()) {
            this.mSwitchAnimator.end();
            this.mSwitchAnimator = null;
        }
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(Switch.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(Switch.class.getName());
        CharSequence charSequence = isChecked() ? this.switchOn : this.switchOff;
        if (!TextUtils.isEmpty(charSequence)) {
            CharSequence text = accessibilityNodeInfo.getText();
            if (TextUtils.isEmpty(text)) {
                accessibilityNodeInfo.setText(charSequence);
                return;
            }
            CharSequence stringBuilder = new StringBuilder();
            stringBuilder.append(text).append(' ').append(charSequence);
            accessibilityNodeInfo.setText(stringBuilder);
        }
    }

    public void setStyleWhite() {
        Resources resources = getResources();
        this.mThumbOnCache = this.mThumbOnDrawable;
        this.mThumbOffCache = this.mThumbOffDrawable;
        this.mTrackCache = this.mTrackDrawable;
        this.mThumbOnDrawable = resources.getDrawable(R.drawable.mc_switch_anim_thumb_on_selector_color_white);
        this.mThumbOffDrawable = resources.getDrawable(R.drawable.mc_switch_anim_thumb_off_selector_color_white);
        this.mTrackDrawable = resources.getDrawable(R.drawable.mc_switch_anim_track_color_white);
        if (this.mThumbOnDrawable == null || this.mThumbOffDrawable == null || this.mTrackDrawable == null) {
            setStyleDefault();
            return;
        }
        this.mThumbOnDrawable.setCallback(this);
        this.mThumbOffDrawable.setCallback(this);
        this.mTrackDrawable.setCallback(this);
        invalidate();
        this.isChanged = true;
    }

    public void setStyleDefault() {
        if (this.isChanged) {
            this.mThumbOnDrawable = this.mThumbOnCache;
            this.mThumbOffDrawable = this.mThumbOffCache;
            this.mTrackDrawable = this.mTrackCache;
            this.mThumbOnDrawable.setCallback(this);
            this.mThumbOffDrawable.setCallback(this);
            this.mTrackDrawable.setCallback(this);
            invalidate();
        }
    }
}
