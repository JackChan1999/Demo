package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.View.BaseSavedState;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.meizu.common.R;
import com.meizu.common.drawble.CircularAnimatedDrawable;
import com.meizu.common.drawble.CircularProgressDrawable;
import com.meizu.common.drawble.StrokeGradientDrawable;
import com.meizu.common.interpolator.PathInterpolatorCompat;

public class CircularProgressButton extends Button {
    public static final int ERROR_STATE_PROGRESS = -1;
    public static final int IDLE_STATE_PROGRESS = 0;
    private StrokeGradientDrawable background;
    private CircularAnimatedDrawable mAnimatedDrawable;
    private int mColorIndicator;
    private int mColorIndicatorBackground;
    private int mColorProgress;
    private ColorStateList mCompleteColorState;
    private StateListDrawable mCompleteStateDrawable;
    private OnAnimationEndListener mCompleteStateListener = new OnAnimationEndListener() {
        public void onAnimationEnd() {
            if (CircularProgressButton.this.mIconComplete != 0) {
                CircularProgressButton.this.setText(null);
                CircularProgressButton.this.setIcon(CircularProgressButton.this.mIconComplete);
            } else {
                CircularProgressButton.this.setText(CircularProgressButton.this.mCompleteText);
            }
            CircularProgressButton.this.mMorphingInProgress = false;
            CircularProgressButton.this.setClickable(true);
            CircularProgressButton.this.setTextColor(CircularProgressButton.this
                    .mTextColorComplete);
        }
    };
    private String mCompleteText;
    private boolean mConfigurationChanged;
    private float mCornerRadius;
    private StateListDrawable mCurrentStateDrawable;
    private ColorStateList mErrorColorState;
    private StateListDrawable mErrorStateDrawable;
    private OnAnimationEndListener mErrorStateListener = new OnAnimationEndListener() {
        public void onAnimationEnd() {
            if (CircularProgressButton.this.mIconError != 0) {
                CircularProgressButton.this.setText(null);
                CircularProgressButton.this.setIcon(CircularProgressButton.this.mIconError);
            } else {
                CircularProgressButton.this.setText(CircularProgressButton.this.mErrorText);
            }
            CircularProgressButton.this.mMorphingInProgress = false;
            CircularProgressButton.this.setClickable(true);
            CircularProgressButton.this.setTextColor(CircularProgressButton.this.mTextColorError);
        }
    };
    private String mErrorText;
    private int mIconComplete;
    private int mIconError;
    private ColorStateList mIdleColorState;
    private StateListDrawable mIdleStateDrawable;
    private OnAnimationEndListener mIdleStateListener = new OnAnimationEndListener() {
        public void onAnimationEnd() {
            CircularProgressButton.this.removeIcon();
            CircularProgressButton.this.setText(CircularProgressButton.this.mIdleText);
            CircularProgressButton.this.mMorphingInProgress = false;
            CircularProgressButton.this.setClickable(true);
            CircularProgressButton.this.setTextColor(CircularProgressButton.this.mTextColorIdle);
        }
    };
    private String mIdleText;
    private boolean mIndeterminateProgressMode;
    private boolean mIsUseTransitionAnim = true;
    private int mMaxProgress;
    private MorphingAnimation mMorphingAnimation;
    private boolean mMorphingInProgress;
    private boolean mNeedInvalidateCenterIcon;
    private int mPaddingProgress;
    private int mProgress = 0;
    private Drawable mProgressCenterIcon;
    private CircularProgressDrawable mProgressDrawable;
    private StateListDrawable mProgressStateDrawable;
    private OnAnimationEndListener mProgressStateListener = new OnAnimationEndListener() {
        public void onAnimationEnd() {
            CircularProgressButton.this.mMorphingInProgress = false;
            CircularProgressButton.this.setClickable(true);
            CircularProgressButton.this.setText(null);
            CircularProgressButton.this.requestLayout();
        }
    };
    private String mProgressText;
    private boolean mShouldShowCenterIcon = false;
    private boolean mShouldUpdateBounds = false;
    private State mState;
    private ColorStateList mStrokeColorComplete;
    private ColorStateList mStrokeColorError;
    private ColorStateList mStrokeColorIdle;
    private int mStrokeWidth;
    private ColorStateList mTextColorComplete;
    private ColorStateList mTextColorError;
    private ColorStateList mTextColorIdle;

    interface OnAnimationEndListener {
        void onAnimationEnd();
    }

    class MorphingAnimation {
        public static final int DURATION_INSTANT = 1;
        public static final int DURATION_NORMAL = 300;
        private AnimatorSet mAnimSet;
        private StrokeGradientDrawable mDrawable;
        private int mDuration;
        private int mFromColor;
        private float mFromCornerRadius;
        private int mFromStrokeColor;
        private int mFromWidth;
        private OnAnimationEndListener mListener;
        private float mPadding;
        private int mToColor;
        private float mToCornerRadius;
        private int mToStrokeColor;
        private int mToWidth;
        private TextView mView;

        public MorphingAnimation(TextView textView, StrokeGradientDrawable strokeGradientDrawable) {
            this.mView = textView;
            this.mDrawable = strokeGradientDrawable;
        }

        public void setDuration(int i) {
            this.mDuration = i;
        }

        public void setListener(OnAnimationEndListener onAnimationEndListener) {
            this.mListener = onAnimationEndListener;
        }

        public void setFromWidth(int i) {
            this.mFromWidth = i;
        }

        public void setToWidth(int i) {
            this.mToWidth = i;
        }

        public void setFromColor(int i) {
            this.mFromColor = i;
        }

        public void setToColor(int i) {
            this.mToColor = i;
        }

        public void setFromStrokeColor(int i) {
            this.mFromStrokeColor = i;
        }

        public void setToStrokeColor(int i) {
            this.mToStrokeColor = i;
        }

        public void setFromCornerRadius(float f) {
            this.mFromCornerRadius = f;
        }

        public void setToCornerRadius(float f) {
            this.mToCornerRadius = f;
        }

        public void setPadding(float f) {
            this.mPadding = f;
        }

        public void start() {
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{this.mFromWidth, this.mToWidth});
            final GradientDrawable gradientDrawable = this.mDrawable.getGradientDrawable();
            ofInt.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int access$1400;
                    int access$14002;
                    int access$1600;
                    Integer num = (Integer) valueAnimator.getAnimatedValue();
                    if (MorphingAnimation.this.mFromWidth > MorphingAnimation.this.mToWidth) {
                        access$1400 = (MorphingAnimation.this.mFromWidth - num.intValue()) / 2;
                        access$14002 = MorphingAnimation.this.mFromWidth - access$1400;
                        access$1600 = (int) (MorphingAnimation.this.mPadding * valueAnimator
                                .getAnimatedFraction());
                    } else {
                        access$1400 = (MorphingAnimation.this.mToWidth - num.intValue()) / 2;
                        access$14002 = MorphingAnimation.this.mToWidth - access$1400;
                        access$1600 = (int) (MorphingAnimation.this.mPadding - (MorphingAnimation
                                .this.mPadding * valueAnimator.getAnimatedFraction()));
                    }
                    gradientDrawable.setBounds(access$1400 + access$1600, access$1600,
                            access$14002 - access$1600, MorphingAnimation.this.mView.getHeight()
                                    - access$1600);
                    CircularProgressButton.this.invalidate();
                }
            });
            ObjectAnimator.ofInt(gradientDrawable, "color", new int[]{this.mFromColor, this
                    .mToColor}).setEvaluator(new ArgbEvaluator());
            ObjectAnimator.ofInt(this.mDrawable, "strokeColor", new int[]{this.mFromStrokeColor,
                    this.mToStrokeColor}).setEvaluator(new ArgbEvaluator());
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(gradientDrawable, "cornerRadius", new
                    float[]{this.mFromCornerRadius, this.mToCornerRadius});
            mAnimSet = new AnimatorSet();
            mAnimSet.setInterpolator(CircularProgressButton.this.getInterpolator());
            mAnimSet.setDuration((long) this.mDuration);
            mAnimSet.playTogether(ofInt, ofFloat);
            mAnimSet.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    if (MorphingAnimation.this.mListener != null) {
                        MorphingAnimation.this.mListener.onAnimationEnd();
                    }
                }

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }
            });
            this.mAnimSet.start();
        }

        public void cancelAllAnim() {
            this.mAnimSet.end();
            this.mAnimSet.removeAllListeners();
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
        private boolean mConfigurationChanged;
        private boolean mIndeterminateProgressMode;
        private int mProgress;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            boolean z;
            boolean z2 = true;
            super(parcel);
            this.mProgress = parcel.readInt();
            if (parcel.readInt() == 1) {
                z = true;
            } else {
                z = false;
            }
            this.mIndeterminateProgressMode = z;
            if (parcel.readInt() != 1) {
                z2 = false;
            }
            this.mConfigurationChanged = z2;
        }

        public void writeToParcel(Parcel parcel, int i) {
            int i2;
            int i3 = 1;
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mProgress);
            if (this.mIndeterminateProgressMode) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            parcel.writeInt(i2);
            if (!this.mConfigurationChanged) {
                i3 = 0;
            }
            parcel.writeInt(i3);
        }
    }

    public enum State {
        PROGRESS,
        IDLE,
        COMPLETE,
        ERROR
    }

    class StateManager {
        private boolean mIsEnabled;
        private int mProgress;

        public StateManager(CircularProgressButton circularProgressButton) {
            this.mIsEnabled = circularProgressButton.isEnabled();
            this.mProgress = circularProgressButton.getProgress();
        }

        public void saveProgress(CircularProgressButton circularProgressButton) {
            this.mProgress = circularProgressButton.getProgress();
        }

        public boolean isEnabled() {
            return this.mIsEnabled;
        }

        public int getProgress() {
            return this.mProgress;
        }

        public void checkState(CircularProgressButton circularProgressButton) {
            if (circularProgressButton.getProgress() != getProgress()) {
                circularProgressButton.setProgress(circularProgressButton.getProgress());
            } else if (circularProgressButton.isEnabled() != isEnabled()) {
                circularProgressButton.setEnabled(circularProgressButton.isEnabled());
            }
        }
    }

    public CircularProgressButton(Context context) {
        super(context);
        init(context, null);
    }

    public CircularProgressButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public CircularProgressButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        initAttributes(context, attributeSet);
        this.mMaxProgress = 100;
        this.mState = State.IDLE;
        setText(this.mIdleText);
        initIdleStateDrawable();
        initCompleteStateDrawable();
        initProgressStateDrawable();
        initErrorStateDrawable();
        this.mCurrentStateDrawable = this.mIdleStateDrawable;
        setBackgroundCompat(null);
    }

    private void initErrorStateDrawable() {
        StrokeGradientDrawable createDrawable = createDrawable(getPressedColor(mErrorColorState),
                getPressedColor(this.mStrokeColorError));
        if (mErrorStateDrawable == null) {
            mErrorStateDrawable = new StateListDrawable();
            mErrorStateDrawable.setCallback(this);
        }
       mErrorStateDrawable.addState(new int[]{android.R.attr.state_pressed}, createDrawable.getGradientDrawable());
       mErrorStateDrawable.addState(StateSet.WILD_CARD, this.background.getGradientDrawable());
       mErrorStateDrawable.setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());
    }

    private void initCompleteStateDrawable() {
        StrokeGradientDrawable createDrawable = createDrawable(getPressedColor(this
                .mCompleteColorState), getPressedColor(this.mStrokeColorComplete));
        if (this.mCompleteStateDrawable == null) {
            this.mCompleteStateDrawable = new StateListDrawable();
            this.mCompleteStateDrawable.setCallback(this);
        }
        this.mCompleteStateDrawable.addState(new int[]{16842919}, createDrawable
                .getGradientDrawable());
        this.mCompleteStateDrawable.addState(StateSet.WILD_CARD, this.background
                .getGradientDrawable());
        this.mCompleteStateDrawable.setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());
    }

    private void initIdleStateDrawable() {
        int normalColor = getNormalColor(this.mIdleColorState);
        int pressedColor = getPressedColor(this.mIdleColorState);
        int focusedColor = getFocusedColor(this.mIdleColorState);
        int disabledColor = getDisabledColor(this.mIdleColorState);
        int normalColor2 = getNormalColor(this.mStrokeColorIdle);
        int pressedColor2 = getPressedColor(this.mStrokeColorIdle);
        int focusedColor2 = getFocusedColor(this.mStrokeColorIdle);
        int disabledColor2 = getDisabledColor(this.mStrokeColorIdle);
        if (this.background == null) {
            this.background = createDrawable(normalColor, normalColor2);
        }
        StrokeGradientDrawable createDrawable = createDrawable(disabledColor, disabledColor2);
        StrokeGradientDrawable createDrawable2 = createDrawable(focusedColor, focusedColor2);
        StrokeGradientDrawable createDrawable3 = createDrawable(pressedColor, pressedColor2);
        if (this.mIdleStateDrawable == null) {
            this.mIdleStateDrawable = new StateListDrawable();
            this.mIdleStateDrawable.setCallback(this);
        }
        this.mIdleStateDrawable.addState(new int[]{16842919}, createDrawable3.getGradientDrawable
                ());
        this.mIdleStateDrawable.addState(new int[]{16842908}, createDrawable2.getGradientDrawable
                ());
        this.mIdleStateDrawable.addState(new int[]{-16842910}, createDrawable.getGradientDrawable
                ());
        this.mIdleStateDrawable.addState(StateSet.WILD_CARD, this.background.getGradientDrawable());
        this.mIdleStateDrawable.setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());
    }

    private void initProgressStateDrawable() {
        if (this.mProgressStateDrawable == null) {
            this.mProgressStateDrawable = new StateListDrawable();
            this.mProgressStateDrawable.setCallback(this);
        }
        this.mProgressStateDrawable.addState(StateSet.WILD_CARD, this.background
                .getGradientDrawable());
        int abs = (Math.abs(getWidth() - getHeight()) / 2) + this.mPaddingProgress;
        this.mProgressStateDrawable.setBounds(abs, this.mPaddingProgress, (getWidth() - abs) -
                this.mPaddingProgress, getHeight() - this.mPaddingProgress);
    }

    private int getNormalColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{16842910}, 0);
    }

    private int getPressedColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{16842919}, 0);
    }

    private int getFocusedColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{16842908}, 0);
    }

    private int getDisabledColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{-16842910}, 0);
    }

    private StrokeGradientDrawable createDrawable(int i, int i2) {
        GradientDrawable gradientDrawable = (GradientDrawable) getResources().getDrawable(R
                .drawable.mc_cir_pro_btn_background).mutate();
        gradientDrawable.setColor(i);
        gradientDrawable.setCornerRadius(this.mCornerRadius);
        StrokeGradientDrawable strokeGradientDrawable = new StrokeGradientDrawable
                (gradientDrawable);
        strokeGradientDrawable.setStrokeColor(i2);
        strokeGradientDrawable.setStrokeWidth(this.mStrokeWidth);
        return strokeGradientDrawable;
    }

    protected void drawableStateChanged() {
        Rect recordBackgroundBoundIfNeed = recordBackgroundBoundIfNeed();
        setBackgroundState(this.mIdleStateDrawable, getDrawableState());
        setBackgroundState(this.mCompleteStateDrawable, getDrawableState());
        setBackgroundState(this.mErrorStateDrawable, getDrawableState());
        setBackgroundState(this.mProgressStateDrawable, getDrawableState());
        restoreBackgroundBoundIfNeed(recordBackgroundBoundIfNeed);
        super.drawableStateChanged();
    }

    private Rect recordBackgroundBoundIfNeed() {
        if (!this.mMorphingInProgress) {
            return null;
        }
        Rect rect = new Rect();
        rect.set(this.background.getGradientDrawable().getBounds());
        return rect;
    }

    private void restoreBackgroundBoundIfNeed(Rect rect) {
        if (this.mMorphingInProgress && rect != null) {
            this.background.getGradientDrawable().setBounds(rect);
        }
    }

    private void setBackgroundState(Drawable drawable, int[] iArr) {
        if (drawable != null) {
            drawable.setState(iArr);
        }
    }

    public void setPressed(boolean z) {
        if (!z || !this.mMorphingInProgress) {
            super.setPressed(z);
        }
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = getTypedArray(context, attributeSet, R.styleable
                .CircularProgressButton);
        if (typedArray != null) {
            this.mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable
                    .CircularProgressButton_mcCirButtonStrokeWidth, (int) getContext()
                    .getResources().getDimension(R.dimen.mc_cir_progress_button_stroke_width));
            this.mIdleText = typedArray.getString(R.styleable
                    .CircularProgressButton_mcCirButtonTextIdle);
            this.mCompleteText = typedArray.getString(R.styleable
                    .CircularProgressButton_mcCirButtonTextComplete);
            this.mErrorText = typedArray.getString(R.styleable
                    .CircularProgressButton_mcCirButtonTextError);
            this.mProgressText = typedArray.getString(R.styleable
                    .CircularProgressButton_mcCirButtonTextProgress);
            this.mIconComplete = typedArray.getResourceId(R.styleable
                    .CircularProgressButton_mcCirButtonIconComplete, 0);
            this.mIconError = typedArray.getResourceId(R.styleable
                    .CircularProgressButton_mcCirButtonIconError, 0);
            this.mCornerRadius = typedArray.getDimension(R.styleable
                    .CircularProgressButton_mcCirButtonCornerRadius, 0.0f);
            this.mPaddingProgress = typedArray.getDimensionPixelSize(R.styleable
                    .CircularProgressButton_mcCirButtonPaddingProgress, 0);
            int color = getColor(R.color.mc_cir_progress_button_blue);
            int color2 = getColor(R.color.mc_cir_progress_button_white);
            int color3 = getColor(R.color.mc_cir_progress_button_grey);
            int resourceId = typedArray.getResourceId(R.styleable
                    .CircularProgressButton_mcCirButtonSelectorIdle, R.color
                    .mc_cir_progress_button_blue);
            this.mIdleColorState = getResources().getColorStateList(resourceId);
            this.mStrokeColorIdle = getResources().getColorStateList(typedArray.getResourceId(R
                    .styleable.CircularProgressButton_mcCirButtonStrokeColorIdle, resourceId));
            resourceId = typedArray.getResourceId(R.styleable
                    .CircularProgressButton_mcCirButtonSelectorComplete, R.color
                    .mc_cir_progress_button_green);
            this.mCompleteColorState = getResources().getColorStateList(resourceId);
            this.mStrokeColorComplete = getResources().getColorStateList(typedArray.getResourceId
                    (R.styleable.CircularProgressButton_mcCirButtonStrokeColorComplete,
                            resourceId));
            resourceId = typedArray.getResourceId(R.styleable
                    .CircularProgressButton_mcCirButtonSelectorError, R.color
                    .mc_cir_progress_button_red);
            this.mErrorColorState = getResources().getColorStateList(resourceId);
            this.mStrokeColorError = getResources().getColorStateList(typedArray.getResourceId(R
                    .styleable.CircularProgressButton_mcCirButtonStrokeColorError, resourceId));
            this.mColorProgress = typedArray.getColor(R.styleable
                    .CircularProgressButton_mcCirButtonColorProgress, color2);
            this.mColorIndicator = typedArray.getColor(R.styleable
                    .CircularProgressButton_mcCirButtonColorIndicator, color);
            this.mColorIndicatorBackground = typedArray.getColor(R.styleable
                    .CircularProgressButton_mcCirButtonColorIndicatorBackground, color3);
            this.mTextColorError = typedArray.getColorStateList(R.styleable
                    .CircularProgressButton_mcCirButtonTextColorError);
            if (this.mTextColorError == null) {
                this.mTextColorError = getTextColors();
            }
            this.mTextColorIdle = typedArray.getColorStateList(R.styleable
                    .CircularProgressButton_mcCirButtonTextColorIdle);
            if (this.mTextColorIdle == null) {
                this.mTextColorIdle = getTextColors();
            }
            this.mTextColorComplete = typedArray.getColorStateList(R.styleable
                    .CircularProgressButton_mcCirButtonTextColorComplete);
            if (this.mTextColorComplete == null) {
                this.mTextColorComplete = getTextColors();
            }
            typedArray.recycle();
        }
    }

    protected int getColor(int i) {
        return getResources().getColor(i);
    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] iArr) {
        return context.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mState != State.PROGRESS || this.mMorphingInProgress) {
            if (this.mAnimatedDrawable != null) {
                this.mAnimatedDrawable.setAllowLoading(false);
            }
        } else if (this.mIndeterminateProgressMode) {
            drawIndeterminateProgress(canvas);
        } else {
            drawProgress(canvas);
        }
    }

    private void drawIndeterminateProgress(Canvas canvas) {
        if (this.mAnimatedDrawable == null) {
            int width = (getWidth() - getHeight()) / 2;
            this.mAnimatedDrawable = new CircularAnimatedDrawable(this.mColorIndicator, (float)
                    this.mStrokeWidth);
            int i = this.mPaddingProgress + width;
            width = (getWidth() - width) - this.mPaddingProgress;
            int height = getHeight() - this.mPaddingProgress;
            this.mAnimatedDrawable.setBounds(i, this.mPaddingProgress, width, height);
            this.mAnimatedDrawable.setCallback(this);
            this.mAnimatedDrawable.start();
            return;
        }
        this.mAnimatedDrawable.setAllowLoading(true);
        this.mAnimatedDrawable.draw(canvas);
    }

    private void drawProgress(Canvas canvas) {
        if (this.mProgressDrawable == null) {
            int width = (getWidth() - getHeight()) / 2;
            this.mProgressDrawable = new CircularProgressDrawable(getHeight() - (this
                    .mPaddingProgress * 2), this.mStrokeWidth, this.mColorIndicator);
            width += this.mPaddingProgress;
            this.mProgressDrawable.setBounds(width, this.mPaddingProgress, width, this
                    .mPaddingProgress);
        }
        if (this.mNeedInvalidateCenterIcon) {
            this.mNeedInvalidateCenterIcon = false;
            this.mProgressDrawable.setCenterIcon(this.mProgressCenterIcon);
            if (this.mProgressCenterIcon == null) {
                this.mProgressDrawable.setShowCenterIcon(this.mShouldShowCenterIcon);
            }
        }
        this.mProgressDrawable.setSweepAngle((360.0f / ((float) this.mMaxProgress)) * ((float)
                this.mProgress));
        this.mProgressDrawable.draw(canvas);
    }

    public boolean isIndeterminateProgressMode() {
        return this.mIndeterminateProgressMode;
    }

    public void setIndeterminateProgressMode(boolean z) {
        this.mIndeterminateProgressMode = z;
    }

    protected boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mAnimatedDrawable || drawable == this.mProgressStateDrawable ||
                drawable == this.mIdleStateDrawable || drawable == this.mErrorStateDrawable ||
                drawable == this.mCompleteStateDrawable || super.verifyDrawable(drawable);
    }

    private MorphingAnimation createMorphing() {
        this.mMorphingInProgress = true;
        setClickable(false);
        this.mMorphingAnimation = new MorphingAnimation(this, this.background);
        this.mMorphingAnimation.setFromCornerRadius(this.mCornerRadius);
        this.mMorphingAnimation.setToCornerRadius(this.mCornerRadius);
        this.mMorphingAnimation.setFromWidth(getWidth());
        this.mMorphingAnimation.setToWidth(getWidth());
        if (this.mConfigurationChanged || !this.mIsUseTransitionAnim) {
            this.mMorphingAnimation.setDuration(1);
        } else {
            this.mMorphingAnimation.setDuration(300);
        }
        this.mConfigurationChanged = false;
        return this.mMorphingAnimation;
    }

    private MorphingAnimation createProgressMorphing(float f, float f2, int i, int i2) {
        this.mMorphingInProgress = true;
        setClickable(false);
        this.mMorphingAnimation = new MorphingAnimation(this, this.background);
        this.mMorphingAnimation.setFromCornerRadius(f);
        this.mMorphingAnimation.setToCornerRadius(f2);
        this.mMorphingAnimation.setPadding((float) this.mPaddingProgress);
        this.mMorphingAnimation.setFromWidth(i);
        this.mMorphingAnimation.setToWidth(i2);
        if (this.mConfigurationChanged || !this.mIsUseTransitionAnim) {
            this.mMorphingAnimation.setDuration(1);
        } else {
            this.mMorphingAnimation.setDuration(300);
        }
        this.mConfigurationChanged = false;
        return this.mMorphingAnimation;
    }

    private void morphToProgress() {
        setWidth(getWidth());
        setText(this.mProgressText);
        MorphingAnimation createProgressMorphing = createProgressMorphing(this.mCornerRadius,
                (float) getHeight(), getWidth(), getHeight());
        createProgressMorphing.setFromColor(getNormalColor(this.mIdleColorState));
        createProgressMorphing.setToColor(this.mColorProgress);
        createProgressMorphing.setFromStrokeColor(getNormalColor(this.mStrokeColorIdle));
        createProgressMorphing.setToStrokeColor(this.mColorIndicatorBackground);
        createProgressMorphing.setListener(this.mProgressStateListener);
        setState(State.PROGRESS);
        this.mCurrentStateDrawable = this.mProgressStateDrawable;
        createProgressMorphing.start();
    }

    private void morphProgressToComplete() {
        MorphingAnimation createProgressMorphing = createProgressMorphing((float) getHeight(),
                this.mCornerRadius, getHeight(), getWidth());
        createProgressMorphing.setFromColor(this.mColorProgress);
        createProgressMorphing.setFromStrokeColor(this.mColorIndicator);
        createProgressMorphing.setToStrokeColor(getNormalColor(this.mStrokeColorComplete));
        createProgressMorphing.setToColor(getNormalColor(this.mCompleteColorState));
        createProgressMorphing.setListener(this.mCompleteStateListener);
        setState(State.COMPLETE);
        this.mCurrentStateDrawable = this.mCompleteStateDrawable;
        createProgressMorphing.start();
    }

    private void morphIdleToComplete() {
        MorphingAnimation createMorphing = createMorphing();
        createMorphing.setFromColor(getNormalColor(this.mIdleColorState));
        createMorphing.setFromStrokeColor(getNormalColor(this.mStrokeColorIdle));
        createMorphing.setToColor(getNormalColor(this.mCompleteColorState));
        createMorphing.setToStrokeColor(getNormalColor(this.mStrokeColorComplete));
        createMorphing.setListener(this.mCompleteStateListener);
        setState(State.COMPLETE);
        this.mCurrentStateDrawable = this.mCompleteStateDrawable;
        createMorphing.start();
    }

    private void morphCompleteToIdle() {
        MorphingAnimation createMorphing = createMorphing();
        createMorphing.setFromColor(getNormalColor(this.mCompleteColorState));
        createMorphing.setToColor(getNormalColor(this.mIdleColorState));
        createMorphing.setFromStrokeColor(getNormalColor(this.mStrokeColorComplete));
        createMorphing.setToStrokeColor(getNormalColor(this.mStrokeColorIdle));
        createMorphing.setListener(this.mIdleStateListener);
        setState(State.IDLE);
        this.mCurrentStateDrawable = this.mIdleStateDrawable;
        createMorphing.start();
    }

    private void morphErrorToIdle() {
        MorphingAnimation createMorphing = createMorphing();
        createMorphing.setFromColor(getNormalColor(this.mErrorColorState));
        createMorphing.setToColor(getNormalColor(this.mIdleColorState));
        createMorphing.setFromStrokeColor(getNormalColor(this.mStrokeColorError));
        createMorphing.setToStrokeColor(getNormalColor(this.mStrokeColorIdle));
        createMorphing.setListener(this.mIdleStateListener);
        setState(State.IDLE);
        this.mCurrentStateDrawable = this.mIdleStateDrawable;
        createMorphing.start();
    }

    private void morphIdleToError() {
        MorphingAnimation createMorphing = createMorphing();
        createMorphing.setFromColor(getNormalColor(this.mIdleColorState));
        createMorphing.setToColor(getNormalColor(this.mErrorColorState));
        createMorphing.setFromStrokeColor(getNormalColor(this.mStrokeColorIdle));
        createMorphing.setToStrokeColor(getNormalColor(this.mStrokeColorError));
        createMorphing.setListener(this.mErrorStateListener);
        setState(State.ERROR);
        this.mCurrentStateDrawable = this.mErrorStateDrawable;
        createMorphing.start();
    }

    private void morphProgressToError() {
        MorphingAnimation createProgressMorphing = createProgressMorphing((float) getHeight(),
                this.mCornerRadius, getHeight(), getWidth());
        createProgressMorphing.setFromColor(this.mColorProgress);
        createProgressMorphing.setToColor(getNormalColor(this.mErrorColorState));
        createProgressMorphing.setFromStrokeColor(this.mColorIndicator);
        createProgressMorphing.setToStrokeColor(getNormalColor(this.mStrokeColorError));
        createProgressMorphing.setListener(this.mErrorStateListener);
        setState(State.ERROR);
        this.mCurrentStateDrawable = this.mErrorStateDrawable;
        createProgressMorphing.start();
    }

    private void morphProgressToIdle() {
        MorphingAnimation createProgressMorphing = createProgressMorphing((float) getHeight(),
                this.mCornerRadius, getHeight(), getWidth());
        createProgressMorphing.setFromColor(this.mColorProgress);
        createProgressMorphing.setToColor(getNormalColor(this.mIdleColorState));
        createProgressMorphing.setFromStrokeColor(this.mColorIndicator);
        createProgressMorphing.setToStrokeColor(getNormalColor(this.mStrokeColorIdle));
        createProgressMorphing.setListener(new OnAnimationEndListener() {
            public void onAnimationEnd() {
                CircularProgressButton.this.removeIcon();
                CircularProgressButton.this.setText(CircularProgressButton.this.mIdleText);
                CircularProgressButton.this.mMorphingInProgress = false;
                CircularProgressButton.this.setClickable(true);
            }
        });
        setState(State.IDLE);
        this.mCurrentStateDrawable = this.mIdleStateDrawable;
        createProgressMorphing.start();
    }

    private void setIcon(int i) {
        Drawable drawable = getResources().getDrawable(i);
        if (drawable != null) {
            int width = (getWidth() / 2) - (drawable.getIntrinsicWidth() / 2);
            setCompoundDrawablesWithIntrinsicBounds(i, 0, 0, 0);
            setPadding(width, 0, 0, 0);
        }
    }

    protected void removeIcon() {
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        setPadding(0, 0, 0, 0);
    }

    @SuppressLint({"NewApi"})
    public void setBackgroundCompat(Drawable drawable) {
        if (VERSION.SDK_INT >= 16) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    public void setProgress(int i, boolean z) {
        this.mProgress = i;
        this.mIsUseTransitionAnim = z;
        if (!this.mMorphingInProgress && getWidth() != 0) {
            if (this.mProgress >= this.mMaxProgress) {
                if (this.mState == State.PROGRESS) {
                    morphProgressToComplete();
                } else if (this.mState == State.IDLE) {
                    morphIdleToComplete();
                }
            } else if (this.mProgress > 0) {
                if (this.mState == State.IDLE || this.mState == State.ERROR) {
                    morphToProgress();
                } else if (this.mState == State.PROGRESS) {
                    invalidate();
                }
            } else if (this.mProgress == -1) {
                if (this.mState == State.PROGRESS) {
                    morphProgressToError();
                } else if (this.mState == State.IDLE) {
                    morphIdleToError();
                }
            } else if (this.mProgress != 0) {
            } else {
                if (this.mState == State.COMPLETE) {
                    morphCompleteToIdle();
                } else if (this.mState == State.PROGRESS) {
                    morphProgressToIdle();
                } else if (this.mState == State.ERROR) {
                    morphErrorToIdle();
                }
            }
        }
    }

    public void setProgress(int i) {
        setProgress(i, true);
    }

    public int getProgress() {
        return this.mProgress;
    }

    public void setBackgroundColor(int i) {
        this.background.getGradientDrawable().setColor(i);
    }

    public void setStrokeColor(int i) {
        this.background.setStrokeColor(i);
    }

    public String getIdleText() {
        return this.mIdleText;
    }

    public String getCompleteText() {
        return this.mCompleteText;
    }

    public String getErrorText() {
        return this.mErrorText;
    }

    public void setIdleText(String str) {
        this.mIdleText = str;
    }

    public void setCompleteText(String str) {
        this.mCompleteText = str;
    }

    public void setErrorText(String str) {
        this.mErrorText = str;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            setState(this.mState, false, false);
        }
    }

    public Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        savedState.mProgress = this.mProgress;
        savedState.mIndeterminateProgressMode = this.mIndeterminateProgressMode;
        savedState.mConfigurationChanged = true;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            this.mProgress = savedState.mProgress;
            this.mIndeterminateProgressMode = savedState.mIndeterminateProgressMode;
            this.mConfigurationChanged = savedState.mConfigurationChanged;
            super.onRestoreInstanceState(savedState.getSuperState());
            setProgress(this.mProgress);
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public void setProgressCenterIcon(Drawable drawable) {
        this.mProgressCenterIcon = drawable;
        this.mNeedInvalidateCenterIcon = true;
    }

    public void setShowCenterIcon(boolean z) {
        this.mShouldShowCenterIcon = z;
        this.mNeedInvalidateCenterIcon = true;
    }

    private void ensureBackgroundBounds() {
        setBackgroundBound(State.IDLE, this.mIdleStateDrawable);
        setBackgroundBound(State.COMPLETE, this.mCompleteStateDrawable);
        setBackgroundBound(State.ERROR, this.mErrorStateDrawable);
        setBackgroundBound(this.mState, this.background.getGradientDrawable());
    }

    private void setBackgroundBound(State state, Drawable drawable) {
        if (drawable != null) {
            if (state == State.PROGRESS) {
                int abs = (Math.abs(getWidth() - getHeight()) / 2) + this.mPaddingProgress;
                drawable.setBounds(abs, this.mPaddingProgress, (getWidth() - abs) - this
                        .mPaddingProgress, getHeight() - this.mPaddingProgress);
                return;
            }
            drawable.setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());
        }
    }

    public void setState(State state, boolean z, boolean z2) {
        if (state != this.mState) {
            this.mIsUseTransitionAnim = z;
            if (!z) {
                changeBackground(state, false);
            } else if (!this.mMorphingInProgress && getWidth() != 0) {
                switch (state) {
                    case COMPLETE:
                        switch (this.mState) {
                            case PROGRESS:
                                morphProgressToComplete();
                                return;
                            case IDLE:
                                morphIdleToComplete();
                                return;
                            default:
                                return;
                        }
                    case ERROR:
                        switch (this.mState) {
                            case PROGRESS:
                                morphProgressToError();
                                return;
                            case IDLE:
                                morphIdleToError();
                                return;
                            default:
                                return;
                        }
                    case PROGRESS:
                        if (this.mState != State.PROGRESS) {
                            morphToProgress();
                            return;
                        }
                        return;
                    case IDLE:
                        switch (this.mState) {
                            case COMPLETE:
                                morphCompleteToIdle();
                                return;
                            case ERROR:
                                morphErrorToIdle();
                                return;
                            case PROGRESS:
                                morphProgressToIdle();
                                return;
                            default:
                                return;
                        }
                    default:
                        return;
                }
            }
        }
    }

    private void changeBackground(State state, boolean z) {
        if (z || state != this.mState) {
            cancelAllAnimation();
            CharSequence charSequence = "";
            int normalColor = getNormalColor(this.mIdleColorState);
            int normalColor2 = getNormalColor(this.mIdleColorState);
            ColorStateList textColors = getTextColors();
            switch (state) {
                case COMPLETE:
                    normalColor = getNormalColor(this.mCompleteColorState);
                    normalColor2 = getNormalColor(this.mStrokeColorComplete);
                    charSequence = this.mCompleteText;
                    setState(State.COMPLETE);
                    textColors = this.mTextColorComplete;
                    this.mCurrentStateDrawable = this.mCompleteStateDrawable;
                    break;
                case ERROR:
                    normalColor = getNormalColor(this.mErrorColorState);
                    normalColor2 = getNormalColor(this.mStrokeColorError);
                    charSequence = this.mErrorText;
                    setState(State.ERROR);
                    textColors = this.mTextColorError;
                    this.mCurrentStateDrawable = this.mErrorStateDrawable;
                    break;
                case PROGRESS:
                    normalColor = this.mColorProgress;
                    normalColor2 = this.mColorIndicatorBackground;
                    setState(State.PROGRESS);
                    this.mCurrentStateDrawable = this.mProgressStateDrawable;
                    break;
                case IDLE:
                    normalColor = getNormalColor(this.mIdleColorState);
                    normalColor2 = getNormalColor(this.mStrokeColorIdle);
                    charSequence = this.mIdleText;
                    setState(State.IDLE);
                    textColors = this.mTextColorIdle;
                    this.mCurrentStateDrawable = this.mIdleStateDrawable;
                    break;
            }
            GradientDrawable gradientDrawable = this.background.getGradientDrawable();
            if (state == State.PROGRESS) {
                int abs = (Math.abs(getWidth() - getHeight()) / 2) + this.mPaddingProgress;
                gradientDrawable.setBounds(abs, this.mPaddingProgress, (getWidth() - abs) - this
                        .mPaddingProgress, getHeight() - this.mPaddingProgress);
            } else {
                gradientDrawable.setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());
            }
            gradientDrawable.setColor(normalColor);
            this.background.setStrokeWidth(this.mStrokeWidth);
            this.background.setStrokeColor(normalColor2);
            setText(charSequence);
            setTextColor(textColors);
            invalidate();
        }
    }

    public void setProgressForState(int i) {
        if (this.mState == State.PROGRESS) {
            this.mProgress = i;
            invalidate();
        }
    }

    public State getState() {
        return this.mState;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAllAnimation();
    }

    public void cancelAllAnimation() {
        if (this.mMorphingAnimation != null) {
            this.mMorphingAnimation.cancelAllAnim();
        }
    }

    public void setStateColorSelector(State state, ColorStateList colorStateList, ColorStateList
            colorStateList2) {
        if (colorStateList != null && colorStateList2 != null) {
            switch (state) {
                case COMPLETE:
                    this.mCompleteColorState = colorStateList;
                    this.mStrokeColorComplete = colorStateList2;
                    break;
                case ERROR:
                    this.mErrorColorState = colorStateList;
                    this.mStrokeColorError = colorStateList2;
                    break;
                case IDLE:
                    this.mIdleColorState = colorStateList;
                    this.mStrokeColorIdle = colorStateList2;
                    break;
            }
            this.background = null;
            this.mIdleStateDrawable = null;
            this.mProgressStateDrawable = null;
            this.mCompleteStateDrawable = null;
            this.mErrorStateDrawable = null;
            initIdleStateDrawable();
            initProgressStateDrawable();
            initErrorStateDrawable();
            initCompleteStateDrawable();
            if (this.mState == state) {
                setBackgroundFromState(state);
            }
            changeBackground(this.mState, true);
            drawableStateChanged();
        }
    }

    private void setBackgroundFromState(State state) {
        switch (state) {
            case COMPLETE:
                this.mCurrentStateDrawable = this.mCompleteStateDrawable;
                return;
            case ERROR:
                this.mCurrentStateDrawable = this.mErrorStateDrawable;
                return;
            case PROGRESS:
                this.mCurrentStateDrawable = this.mProgressStateDrawable;
                return;
            case IDLE:
                this.mCurrentStateDrawable = this.mIdleStateDrawable;
                return;
            default:
                return;
        }
    }

    private void setState(State state) {
        if (this.mState != state) {
            this.mState = state;
        }
    }

    public void setStateTextColor(State state, ColorStateList colorStateList) {
        switch (state) {
            case COMPLETE:
                this.mTextColorComplete = colorStateList;
                break;
            case ERROR:
                this.mTextColorError = colorStateList;
                break;
            case IDLE:
                this.mTextColorIdle = colorStateList;
                break;
        }
        if (this.mState == state) {
            invalidate();
        }
    }

    public void draw(Canvas canvas) {
        if (this.mShouldUpdateBounds || !this.mMorphingInProgress) {
            this.mShouldUpdateBounds = false;
            ensureBackgroundBounds();
        }
        if (this.mMorphingInProgress && isPressed()) {
            super.draw(canvas);
            return;
        }
        if (this.mCurrentStateDrawable != null) {
            if ((getScrollX() | getScrollY()) == 0) {
                switch (this.mState) {
                    case COMPLETE:
                        drawStateDrawable(this.mCompleteStateDrawable, canvas);
                        break;
                    case ERROR:
                        drawStateDrawable(this.mErrorStateDrawable, canvas);
                        break;
                    case PROGRESS:
                        drawStateDrawable(this.mProgressStateDrawable, canvas);
                        break;
                    case IDLE:
                        drawStateDrawable(this.mIdleStateDrawable, canvas);
                        break;
                }
            }
            canvas.translate((float) getScrollX(), (float) getScrollY());
            this.mCurrentStateDrawable.draw(canvas);
            canvas.translate((float) (-getScrollX()), (float) (-getScrollY()));
        }
        super.draw(canvas);
    }

    private void drawStateDrawable(Drawable drawable, Canvas canvas) {
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }

    public void setStateText(State state, String str) {
        switch (state) {
            case COMPLETE:
                this.mCompleteText = str;
                break;
            case ERROR:
                this.mErrorText = str;
                break;
            case IDLE:
                this.mIdleText = str;
                break;
        }
        if (this.mState == state && !this.mMorphingInProgress) {
            setTextForState(state);
        }
    }

    private void setTextForState(State state) {
        switch (state) {
            case COMPLETE:
                setText(this.mCompleteText);
                return;
            case ERROR:
                setText(this.mErrorText);
                return;
            case IDLE:
                setText(this.mIdleText);
                return;
            default:
                return;
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mAnimatedDrawable = null;
        this.mProgressDrawable = null;
        this.mNeedInvalidateCenterIcon = true;
        this.mShouldUpdateBounds = true;
    }

    private Interpolator getInterpolator() {
        if (VERSION.SDK_INT >= 21) {
            return new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        }
        return new PathInterpolatorCompat(0.33f, 0.0f, 0.1f, 1.0f);
    }

    public void setProgressIndicatorColor(int i) {
        this.mColorIndicator = i;
        this.mAnimatedDrawable = null;
        this.mProgressDrawable = null;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(CircularProgressButton.class.getName());
    }
}
