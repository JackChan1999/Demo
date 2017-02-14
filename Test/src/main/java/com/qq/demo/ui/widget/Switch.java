package com.qq.demo.ui.widget;

import android.content.Context;
import android.widget.CompoundButton;

public class Switch extends CompoundButton {
    public Switch(Context context) {
        super(context);
    }
   /* private static final int[] CHECKED_STATE_SET = new int[]{16842912};
    private static final int THUMB_ANIMATION_DURATION = 250;
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;

    private boolean isChanged;
    private boolean mSplitTrack;

    private final Rect mTempRect;
    private Layout mOffLayout;
    private Layout mOnLayout;
    private ObjectAnimator mPositionAnimator;
    private ValueAnimator mSwitchAnimator;
    private Transformation mSwitchTransformationMethod;
    private ColorStateList mTextColors;
    private TextPaint mTextPaint;
    private Drawable mThumbOffCache;
    private Drawable mThumbOffDrawable;
    private Drawable mThumbOnCache;
    private Drawable mThumbOnDrawable;
    private Drawable mTrackCache;
    private Drawable mTrackDrawable;
    private VelocityTracker mVelocityTracker;
    private Interpolator pathInterpolator;

    private int mMinFlingVelocity;
    private int mSwitchBottom;
    private int mSwitchHeight;
    private int mSwitchLeft;
    private int mSwitchMinWidth;
    private int mSwitchPadding;
    private int mSwitchRight;
    private int mSwitchTop;
    private int mSwitchWidth;
    private int mThumbWidth;
    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private float mThumbPosition;




    public Switch(Context context) {
        this(context, null);
    }

    public Switch(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.mySwitch);
    }

    public Switch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mTempRect = new Rect();
        this.mTextPaint = new TextPaint(1);
        Resources res = getResources();
        this.mTextPaint.density = res.getDisplayMetrics().density;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Switch, defStyleAttr, 0);
        this.mThumbOffDrawable = a.getDrawable(R.styleable.Switch_mcThumbOff);
        if (this.mThumbOffDrawable == null) {
            this.mThumbOffDrawable = res.getDrawable(R.drawable.mc_switch_anim_thumb_off_selector);
        }
        if (this.mThumbOffDrawable != null) {
            this.mThumbOffDrawable.setCallback(this);
        }
        this.mThumbOnDrawable = a.getDrawable(R.styleable.Switch_mcThumbOn);
        if (this.mThumbOnDrawable == null) {
            this.mThumbOnDrawable = res.getDrawable(R.drawable.mc_switch_anim_thumb_on_selector);
        }
        if (this.mThumbOnDrawable != null) {
            this.mThumbOnDrawable.setCallback(this);
        }
        this.mTrackDrawable = a.getDrawable(R.styleable.Switch_mcTrack);
        if (this.mTrackDrawable == null) {
            this.mTrackDrawable = res.getDrawable(R.drawable.mc_switch_bg_default);
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(this);
        }
        this.mSwitchMinWidth = a.getDimensionPixelSize(R.styleable.Switch_mcSwitchMinWidth, 0);
        this.mSwitchPadding = a.getDimensionPixelSize(R.styleable.Switch_mcSwitchPadding, 0);
        this.mSplitTrack = false;
        a.recycle();
        ViewConfiguration config = ViewConfiguration.get(context);
        this.mTouchSlop = config.getScaledTouchSlop();
        this.mMinFlingVelocity = config.getScaledMinimumFlingVelocity();
        refreshDrawableState();
        setChecked(isChecked());
    }

    public void setSwitchPadding(int pixels) {
        this.mSwitchPadding = pixels;
        requestLayout();
    }

    public int getSwitchPadding() {
        return this.mSwitchPadding;
    }

    public void setSwitchMinWidth(int pixels) {
        this.mSwitchMinWidth = pixels;
        requestLayout();
    }

    public int getSwitchMinWidth() {
        return this.mSwitchMinWidth;
    }

    public void setTrackDrawable(Drawable track) {
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(null);
        }
        this.mTrackDrawable = track;
        if (track != null) {
            track.setCallback(this);
        }
        requestLayout();
    }

    public void setTrackResource(int resId) {
        setTrackDrawable(getContext().getResources().getDrawable(resId));
    }

    public Drawable getTrackDrawable() {
        return this.mTrackDrawable;
    }

    public void setThumbDrawable(Drawable thumb) {
        if (this.mThumbOnDrawable != null) {
            this.mThumbOnDrawable.setCallback(null);
        }
        this.mThumbOnDrawable = thumb;
        if (thumb != null) {
            thumb.setCallback(this);
        }
        requestLayout();
    }

    public void setThumbResource(int resId) {
        setThumbDrawable(getContext().getResources().getDrawable(resId));
    }

    public Drawable getThumbDrawable() {
        return this.mThumbOnDrawable;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int thumbWidth;
        int thumbHeight;
        int trackHeight;
        Rect padding = this.mTempRect;
        if (this.mThumbOnDrawable != null) {
            this.mThumbOnDrawable.getPadding(padding);
            thumbWidth = (this.mThumbOnDrawable.getIntrinsicWidth() - padding.left) - padding.right;
            thumbHeight = this.mThumbOnDrawable.getIntrinsicHeight();
        } else {
            thumbWidth = 0;
            thumbHeight = 0;
        }
        this.mThumbWidth = thumbWidth;
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.getPadding(padding);
            trackHeight = this.mTrackDrawable.getIntrinsicHeight();
        } else {
            padding.setEmpty();
            trackHeight = 0;
        }
        int paddingLeft = padding.left;
        int paddingRight = padding.right;
        if (this.mThumbOffDrawable != null) {
            Insets inset = Insets.NONE;
            paddingLeft = Math.max(paddingLeft, inset.left);
            paddingRight = Math.max(paddingRight, inset.right);
        }
        int switchWidth = Math.max(this.mSwitchMinWidth, ((this.mThumbWidth * 2) + paddingLeft) + paddingRight);
        int switchHeight = Math.max(trackHeight, thumbHeight);
        this.mSwitchWidth = switchWidth;
        this.mSwitchHeight = switchHeight;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredHeight() < switchHeight) {
            setMeasuredDimension(getMeasuredWidthAndState(), switchHeight);
        }
    }

    @Override
    public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
        super.onPopulateAccessibilityEvent(event);
    }

    private Layout makeLayout(CharSequence text) {
        CharSequence transformed;
        if (this.mSwitchTransformationMethod != null) {
            transformed = this.mSwitchTransformationMethod.getTransformation(text, this);
        } else {
            transformed = text;
        }
        this.mTextPaint = new TextPaint(1);
        this.mTextPaint.density = getResources().getDisplayMetrics().density;
        return new StaticLayout(transformed, this.mTextPaint, (int) Math.ceil((double) Layout.getDesiredWidth(transformed, this.mTextPaint)), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
    }

    private boolean hitThumb(float x, float y) {
        return x > ((float) this.mSwitchLeft) && x < ((float) this.mSwitchRight) && y > ((float) this.mSwitchTop) && y < ((float) this.mSwitchBottom);
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        this.mVelocityTracker.addMovement(ev);
        float x;
        float y;
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                x = ev.getX();
                y = ev.getY();
                if (isEnabled() && hitThumb(x, y)) {
                    this.mTouchMode = 1;
                    this.mTouchX = x;
                    this.mTouchY = y;
                    break;
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (this.mTouchMode != 2) {
                    this.mTouchMode = 0;
                    this.mVelocityTracker.clear();
                    break;
                }
                stopDrag(ev);
                super.onTouchEvent(ev);
                return true;
            case MotionEvent.ACTION_MOVE:
                switch (this.mTouchMode) {
                    case 0:
                        break;
                    case 1:
                        x = ev.getX();
                        y = ev.getY();
                        if (Math.abs(x - this.mTouchX) > ((float) this.mTouchSlop) || Math.abs(y - this.mTouchY) > ((float) this.mTouchSlop)) {
                            this.mTouchMode = 2;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            this.mTouchX = x;
                            this.mTouchY = y;
                            return true;
                        }
                    case 2:
                        float dPos;
                        x = ev.getX();
                        int thumbScrollRange = getThumbScrollRange();
                        float thumbScrollOffset = x - this.mTouchX;
                        if (thumbScrollRange != 0) {
                            dPos = thumbScrollOffset / ((float) thumbScrollRange);
                        } else {
                            dPos = thumbScrollOffset > 0.0f ? 1.0f : -1.0f;
                        }
                        if (isLayoutRtl()) {
                            dPos = -dPos;
                        }
                        float newPos = MathUtils.constrain(this.mThumbPosition + dPos, 0.0f, 1.0f);
                        if (newPos != this.mThumbPosition) {
                            this.mTouchX = x;
                            setThumbPosition(newPos);
                        }
                        return true;
                    default:
                        break;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void cancelSuperTouch(MotionEvent ev) {
        MotionEvent cancel = MotionEvent.obtain(ev);
        cancel.setAction(3);
        super.onTouchEvent(cancel);
        cancel.recycle();
    }

    private void stopDrag(MotionEvent ev) {
        boolean commitChange;
        boolean newState = true;
        this.mTouchMode = 0;
        if (ev.getAction() == 1 && isEnabled()) {
            commitChange = true;
        } else {
            commitChange = false;
        }
        if (commitChange) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float xvel = this.mVelocityTracker.getXVelocity();
            if (Math.abs(xvel) <= ((float) this.mMinFlingVelocity)) {
                newState = getTargetCheckedState();
            } else if (isLayoutRtl()) {
                if (xvel >= 0.0f) {
                    newState = false;
                }
            } else if (xvel <= 0.0f) {
                newState = false;
            }
        } else {
            newState = isChecked();
        }
        setChecked(newState);
        cancelSuperTouch(ev);
    }

    private void animateThumbToCheckedState(boolean newCheckedState) {
        float targetPosition;
        if (newCheckedState) {
            targetPosition = 1.0f;
        } else {
            targetPosition = 0.0f;
        }
        if (this.mSwitchAnimator != null) {
            this.mSwitchAnimator.removeAllUpdateListeners();
        }
        this.mSwitchAnimator = ValueAnimator.ofFloat(new float[]{this.mThumbPosition, targetPosition});
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
            public void onAnimationUpdate(ValueAnimator animation) {
                Switch.this.setThumbPosition(((Float) animation.getAnimatedValue()).floatValue());
            }
        });
    }

    private void cancelPositionAnimator() {
        if (this.mSwitchAnimator != null) {
            this.mSwitchAnimator.cancel();
        }
    }

    private boolean getTargetCheckedState() {
        return this.mThumbPosition > 0.5f;
    }

    private void setThumbPosition(float position) {
        this.mThumbPosition = position;
        invalidate();
    }

    public void toggle() {
        setChecked(!isChecked());
    }

    public void setChecked(boolean checked) {
        setChecked(checked, true);
    }

    public void setChecked(boolean checked, boolean useAnim) {
        super.setChecked(checked);
        checked = isChecked();
        if (useAnim && VERSION.SDK_INT >= 19 && isAttachedToWindow() && isLaidOut()) {
            animateThumbToCheckedState(checked);
            return;
        }
        cancelPositionAnimator();
        setThumbPosition(checked ? 1.0f : 0.0f);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int switchLeft;
        int switchRight;
        int switchTop;
        int switchBottom;
        super.onLayout(changed, left, top, right, bottom);
        int opticalInsetLeft = 0;
        int opticalInsetRight = 0;
        if (this.mThumbOffDrawable != null) {
            Rect trackPadding = this.mTempRect;
            if (this.mTrackDrawable != null) {
                this.mTrackDrawable.getPadding(trackPadding);
            } else {
                trackPadding.setEmpty();
            }
            Insets insets = Insets.NONE;
            opticalInsetLeft = Math.max(0, insets.left - trackPadding.left);
            opticalInsetRight = Math.max(0, insets.right - trackPadding.right);
        }
        if (isLayoutRtl()) {
            switchLeft = getPaddingLeft() + opticalInsetLeft;
            switchRight = ((this.mSwitchWidth + switchLeft) - opticalInsetLeft) - opticalInsetRight;
        } else {
            switchRight = (getWidth() - getPaddingRight()) - opticalInsetRight;
            switchLeft = ((switchRight - this.mSwitchWidth) + opticalInsetLeft) + opticalInsetRight;
        }
        switch (getGravity() & com.meizu.common.R.styleable.Theme_mzColorActionBarTextPrimary) {
            case 16:
                switchTop = (((getPaddingTop() + getHeight()) - getPaddingBottom()) / 2) - (this.mSwitchHeight / 2);
                switchBottom = switchTop + this.mSwitchHeight;
                break;
            case com.meizu.common.R.styleable.Theme_panelMenuListWidth *//*80*//*:
                switchBottom = getHeight() - getPaddingBottom();
                switchTop = switchBottom - this.mSwitchHeight;
                break;
            default:
                switchTop = getPaddingTop();
                switchBottom = switchTop + this.mSwitchHeight;
                break;
        }
        this.mSwitchLeft = switchLeft;
        this.mSwitchTop = switchTop;
        this.mSwitchBottom = switchBottom;
        this.mSwitchRight = switchRight;
    }

    public void draw(Canvas c) {
        Insets thumbInsets;
        Rect padding = this.mTempRect;
        int switchLeft = this.mSwitchLeft;
        int switchTop = this.mSwitchTop;
        int switchRight = this.mSwitchRight;
        int switchBottom = this.mSwitchBottom;
        int thumbInitialLeft = switchLeft + getThumbOffset();
        if (this.mThumbOffDrawable != null) {
            thumbInsets = Insets.NONE;
        } else {
            thumbInsets = Insets.NONE;
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.getPadding(padding);
            thumbInitialLeft += padding.left;
            int trackLeft = switchLeft;
            int trackTop = switchTop;
            int trackRight = switchRight;
            int trackBottom = switchBottom;
            if (thumbInsets != Insets.NONE) {
                if (thumbInsets.left > padding.left) {
                    trackLeft += thumbInsets.left - padding.left;
                }
                if (thumbInsets.top > padding.top) {
                    trackTop += thumbInsets.top - padding.top;
                }
                if (thumbInsets.right > padding.right) {
                    trackRight -= thumbInsets.right - padding.right;
                }
                if (thumbInsets.bottom > padding.bottom) {
                    trackBottom -= thumbInsets.bottom - padding.bottom;
                }
            }
            this.mTrackDrawable.setBounds(trackLeft, trackTop, trackRight, trackBottom);
        }
        if (this.mThumbOffDrawable != null) {
            this.mThumbOffDrawable.getPadding(padding);
            this.mThumbOffDrawable.setBounds((((int) (1.25f * ((this.mThumbPosition * ((float) getThumbScrollRange())) + 0.5f))) + switchLeft) - padding.left, switchTop, (((((int) (1.25f * ((this.mThumbPosition * ((float) getThumbScrollRange())) + 0.5f))) + switchLeft) + this.mThumbOffDrawable.getIntrinsicWidth()) + ((int) (((float) (this.mThumbOnDrawable.getIntrinsicWidth() - this.mThumbOffDrawable.getIntrinsicWidth())) * (0.8f - this.mThumbPosition)))) + padding.right, switchBottom);
            getBackground();
        }
        if (this.mThumbOnDrawable != null) {
            this.mThumbOnDrawable.getPadding(padding);
            int thumbRight = (this.mThumbWidth + thumbInitialLeft) - padding.right;
            int thumbLeft = (thumbRight - this.mThumbWidth) - padding.left;
            int xx = (int) ((((float) (thumbRight - thumbLeft)) * (1.0f - this.mThumbPosition)) * 3.5f);
            this.mThumbOnDrawable.setBounds(thumbLeft + xx, switchTop + xx, thumbRight - xx, switchBottom - xx);
        }
        super.draw(c);
    }

    protected void onDraw(Canvas canvas) {
        int saveCount;
        super.onDraw(canvas);
        Rect padding = this.mTempRect;
        Drawable trackDrawable = this.mTrackDrawable;
        if (trackDrawable != null) {
            trackDrawable.getPadding(padding);
        } else {
            padding.setEmpty();
        }
        int switchTop = this.mSwitchTop;
        int switchInnerTop = switchTop + padding.top;
        int switchInnerBottom = this.mSwitchBottom - padding.bottom;
        Drawable thumbOffDrawable = this.mThumbOffDrawable;
        if (trackDrawable != null) {
            if (!this.mSplitTrack || thumbOffDrawable == null) {
                trackDrawable.draw(canvas);
            } else {
                Insets insets = Insets.NONE;
                thumbOffDrawable.copyBounds(padding);
                padding.left += insets.left;
                padding.right -= insets.right;
                saveCount = canvas.save();
                canvas.clipRect(padding, Op.DIFFERENCE);
                trackDrawable.draw(canvas);
                canvas.restoreToCount(saveCount);
            }
        }
        saveCount = canvas.save();
        Drawable thumbOnDrawable = this.mThumbOnDrawable;
        if (thumbOffDrawable == null || this.mThumbPosition >= 0.8f) {
            thumbOnDrawable.draw(canvas);
        } else {
            thumbOffDrawable.mutate();
            thumbOffDrawable.draw(canvas);
        }
        canvas.restoreToCount(saveCount);
    }

    public int getCompoundPaddingLeft() {
        if (!isLayoutRtl()) {
            return super.getCompoundPaddingLeft();
        }
        int padding = super.getCompoundPaddingLeft() + this.mSwitchWidth;
        if (TextUtils.isEmpty(getText())) {
            return padding;
        }
        return padding + this.mSwitchPadding;
    }

    public int getCompoundPaddingRight() {
        if (isLayoutRtl()) {
            return super.getCompoundPaddingRight();
        }
        int padding = super.getCompoundPaddingRight() + this.mSwitchWidth;
        if (TextUtils.isEmpty(getText())) {
            return padding;
        }
        return padding + this.mSwitchPadding;
    }

    public boolean isLayoutRtl() {
        return false;
    }

    private int getThumbOffset() {
        float thumbPosition;
        if (isLayoutRtl()) {
            thumbPosition = 1.0f - this.mThumbPosition;
        } else {
            thumbPosition = this.mThumbPosition;
        }
        return (int) ((((float) getThumbScrollRange()) * thumbPosition) + 0.5f);
    }

    private int getThumbScrollRange() {
        if (this.mTrackDrawable == null) {
            return 0;
        }
        Insets insets;
        Rect padding = this.mTempRect;
        this.mTrackDrawable.getPadding(padding);
        if (this.mThumbOffDrawable != null) {
            insets = Insets.NONE;
        } else {
            insets = Insets.NONE;
        }
        return ((((this.mSwitchWidth - this.mThumbWidth) - padding.left) - padding.right) - insets.left) - insets.right;
    }

    protected int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] myDrawableState = getDrawableState();
        if (this.mThumbOffDrawable != null) {
            this.mThumbOffDrawable.setState(myDrawableState);
        }
        if (this.mThumbOnDrawable != null) {
            this.mThumbOnDrawable.setState(myDrawableState);
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setState(myDrawableState);
        }
        invalidate();
    }

    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.mThumbOffDrawable || who == this.mTrackDrawable;
    }

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
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(Switch.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(Switch.class.getName());
    }

    public void setStyleWhite() {
        Resources res = getResources();
        this.mThumbOnCache = this.mThumbOnDrawable;
        this.mThumbOffCache = this.mThumbOffDrawable;
        this.mTrackCache = this.mTrackDrawable;
        this.mThumbOnDrawable = res.getDrawable(R.drawable.mc_switch_anim_thumb_on_selector_color_white);
        this.mThumbOffDrawable = res.getDrawable(R.drawable.mc_switch_anim_thumb_off_selector_color_white);
        this.mTrackDrawable = res.getDrawable(R.drawable.mc_switch_anim_track_color_white);
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
    }*/
}
