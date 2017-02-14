package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.CheckBox;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;

public class AnimCheckBox extends CheckBox {
    private CheckBoxAnimHelper checkBoxHelper;
    private boolean mActivated;
    int mInitVisible;
    private UpdateListener mUpdateListener;

    public interface UpdateListener {
        void getUpdateTransition(float f);
    }

    static class CheckBoxAnimHelper {
        private boolean DEBUG = false;
        private ObjectAnimator mAnimator1;
        private ObjectAnimator mAnimator2;
        private ValueAnimator mAnimator3;
        private AnimatorSet mAnimatorSet;
        private boolean mHasInit = false;
        private TimeInterpolator mInterpolator1;
        private TimeInterpolator mInterpolator2;
        private TimeInterpolator mInterpolator3;
        private TimeInterpolator mInterpolator4;
        private boolean mIsAnimation = true;
        private AnimCheckBox mTarget;
        private boolean targetActivatedState;
        private boolean targetChecekedState;

        public CheckBoxAnimHelper(AnimCheckBox animCheckBox) {
            this.mTarget = animCheckBox;
            init();
            this.mHasInit = true;
        }

        private void init() {
            if (VERSION.SDK_INT >= 21) {
                this.mInterpolator1 = new PathInterpolator(0.33f, 0.0f, 1.0f, 1.0f);
                this.mInterpolator2 = new PathInterpolator(0.0f, 0.0f, 0.01f, 1.0f);
                this.mInterpolator3 = new PathInterpolator(FastBlurParameters.DEFAULT_SCALE, 0.0f, 0.01f, 1.0f);
                this.mInterpolator4 = new PathInterpolator(0.0f, 0.0f, 0.1f, 1.0f);
            } else {
                TimeInterpolator decelerateInterpolator = new DecelerateInterpolator();
                this.mInterpolator4 = decelerateInterpolator;
                this.mInterpolator3 = decelerateInterpolator;
                this.mInterpolator2 = decelerateInterpolator;
                this.mInterpolator1 = decelerateInterpolator;
            }
            PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("scaleY", new float[]{1.0f, 0.0f});
            this.mAnimator1 = ObjectAnimator.ofPropertyValuesHolder(this.mTarget, new PropertyValuesHolder[]{ofFloat});
            this.mAnimator1.setInterpolator(this.mInterpolator1);
            this.mAnimator1.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    CheckBoxAnimHelper.this.mTarget.superSetCheck(CheckBoxAnimHelper.this.targetChecekedState);
                    CheckBoxAnimHelper.this.mTarget.superSetActivate(CheckBoxAnimHelper.this.targetActivatedState);
                    if (CheckBoxAnimHelper.this.mTarget.mInitVisible != 0) {
                        if (CheckBoxAnimHelper.this.targetChecekedState) {
                            CheckBoxAnimHelper.this.mTarget.setVisibility(0);
                        } else {
                            CheckBoxAnimHelper.this.mTarget.setVisibility(CheckBoxAnimHelper.this.mTarget.mInitVisible);
                        }
                    }
                    CheckBoxAnimHelper.this.mAnimator2.start();
                }
            });
            ofFloat = PropertyValuesHolder.ofFloat("scaleY", new float[]{0.0f, 1.0f});
            this.mAnimator2 = ObjectAnimator.ofPropertyValuesHolder(this.mTarget, new PropertyValuesHolder[]{ofFloat});
            this.mAnimator2.setInterpolator(this.mInterpolator2);
            this.mAnimator3 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            this.mAnimator3.setInterpolator(this.mInterpolator3);
            this.mAnimator3.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if (CheckBoxAnimHelper.this.mTarget.mUpdateListener != null) {
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        if (!CheckBoxAnimHelper.this.targetChecekedState) {
                            floatValue = 1.0f - floatValue;
                        }
                        CheckBoxAnimHelper.this.mTarget.mUpdateListener.getUpdateTransition(floatValue);
                    }
                }
            });
            this.mAnimator3.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    if (CheckBoxAnimHelper.this.mAnimator2.isRunning()) {
                        CheckBoxAnimHelper.this.mAnimator2.end();
                    }
                }
            });
            this.mAnimatorSet = new AnimatorSet();
            this.mAnimatorSet.playTogether(new Animator[]{this.mAnimator1, this.mAnimator3});
        }

        public void setChecked(boolean z) {
            if (this.mHasInit && this.mIsAnimation) {
                if (this.DEBUG) {
                    Log.i("xx", "setChecked checked = " + z + " targetChecekedState = " + this.targetChecekedState + " " + " " + this.mAnimatorSet.isRunning() + " " + this.mAnimator2.isRunning());
                }
                if (z != this.targetChecekedState) {
                    this.targetChecekedState = z;
                    if (z) {
                        if (this.mAnimatorSet.isRunning() || this.mAnimator2.isRunning()) {
                            this.mAnimatorSet.end();
                            this.mAnimator2.end();
                            this.targetChecekedState = false;
                            setChecked(z);
                            return;
                        }
                        this.mAnimator1.setDuration(150);
                        this.mAnimator2.setDuration(230);
                        this.mAnimator3.setDuration(380);
                        this.mAnimatorSet.start();
                        return;
                    } else if (this.mAnimatorSet.isRunning() || this.mAnimator2.isRunning()) {
                        this.mTarget.superSetCheck(z);
                        this.mAnimatorSet.end();
                        this.mAnimator2.end();
                        return;
                    } else {
                        this.mAnimator1.setDuration(0);
                        this.mAnimator2.setDuration(476);
                        this.mAnimator3.setDuration(476);
                        this.mAnimatorSet.start();
                        return;
                    }
                }
                return;
            }
            this.mTarget.superSetCheck(z);
            this.targetChecekedState = z;
        }

        public void setActivated(boolean z) {
            this.targetActivatedState = z;
            if (this.mHasInit && this.mIsAnimation) {
                if (this.DEBUG) {
                    Log.i("xx", "setActivated activated = " + z + " " + this.mTarget.isActivated() + " " + this.targetActivatedState + " targetChecekedState = " + this.targetChecekedState + " " + this.mTarget.isChecked() + " " + this.mAnimatorSet.isRunning() + " " + this.mAnimator2.isRunning());
                }
                if (z == this.mTarget.isActivated()) {
                    return;
                }
                if (!z && !this.targetChecekedState && this.mTarget.isChecked()) {
                    return;
                }
                if (this.mTarget.isChecked() && this.targetChecekedState) {
                    this.mTarget.superSetActivate(z);
                    if (!this.mAnimatorSet.isRunning() && !this.mAnimator2.isRunning()) {
                        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{0.0f, 1.0f});
                        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{0.0f, 1.0f});
                        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this.mTarget, new PropertyValuesHolder[]{ofFloat, ofFloat2});
                        ofPropertyValuesHolder.setDuration(40).setInterpolator(this.mInterpolator4);
                        ofPropertyValuesHolder.start();
                        return;
                    }
                    return;
                } else if (!z) {
                    this.mAnimatorSet.end();
                    this.mAnimator2.end();
                    this.mTarget.superSetActivate(z);
                    return;
                } else {
                    return;
                }
            }
            this.mTarget.superSetActivate(z);
        }

        public void setIsAnimation(boolean z) {
            this.mIsAnimation = z;
        }
    }

    public AnimCheckBox(Context context) {
        this(context, null);
    }

    public AnimCheckBox(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AnimCheckBox(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mInitVisible = getVisibility();
        setIsAnimation(true);
    }

    public AnimCheckBox(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mInitVisible = getVisibility();
        setIsAnimation(true);
    }

    public void setInitVisible(int i) {
        if (i == 0 || i == 4 || i == 8) {
            this.mInitVisible = i;
        }
    }

    public void setChecked(boolean z) {
        if (this.checkBoxHelper == null) {
            super.setChecked(z);
        } else {
            this.checkBoxHelper.setChecked(z);
        }
    }

    public void setActivated(boolean z) {
        if (this.mActivated != z) {
            this.mActivated = z;
            sendAccessibilityEvent(32768);
        }
        if (this.checkBoxHelper == null) {
            super.setActivated(z);
        } else {
            this.checkBoxHelper.setActivated(z);
        }
    }

    public void setIsAnimation(boolean z) {
        if (this.checkBoxHelper == null) {
            this.checkBoxHelper = new CheckBoxAnimHelper(this);
        }
        this.checkBoxHelper.setIsAnimation(z);
    }

    public void superSetCheck(boolean z) {
        super.setChecked(z);
    }

    public void superSetActivate(boolean z) {
        super.setActivated(z);
    }

    public void setUpdateListner(UpdateListener updateListener) {
        this.mUpdateListener = updateListener;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setChecked(this.mActivated);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(AnimCheckBox.class.getName());
        accessibilityNodeInfo.setChecked(this.mActivated);
    }
}
