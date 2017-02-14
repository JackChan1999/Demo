package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ProgressTextButtonView extends FrameLayout {
    private ValueAnimator mAlphaAnimator;
    private CircularProgressButton mButton;
    private boolean mIsShowText = false;
    private TextView mText;

    public ProgressTextButtonView(Context context) {
        super(context);
    }

    public ProgressTextButtonView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new IllegalStateException("ProgressTextButtonView must has two children");
        }
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof CircularProgressButton) {
                this.mButton = (CircularProgressButton) getChildAt(i);
            } else if (getChildAt(i) instanceof TextView) {
                this.mText = (TextView) getChildAt(i);
            }
        }
        showText(this.mIsShowText, false);
    }

    public void showText(final boolean z, boolean z2) {
        if (z2) {
            if (!z || this.mText.getVisibility() != 0) {
                if (z || this.mButton.getVisibility() != 0) {
                    this.mAlphaAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                    this.mAlphaAnimator.setDuration(100);
                    this.mAlphaAnimator.addUpdateListener(new AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            if (z) {
                                ProgressTextButtonView.this.mButton.setAlpha(1.0f - floatValue);
                                ProgressTextButtonView.this.mText.setAlpha(floatValue);
                                return;
                            }
                            ProgressTextButtonView.this.mButton.setAlpha(floatValue);
                            ProgressTextButtonView.this.mText.setAlpha(1.0f - floatValue);
                        }
                    });
                    this.mAlphaAnimator.addListener(new AnimatorListener() {
                        public void onAnimationStart(Animator animator) {
                        }

                        public void onAnimationEnd(Animator animator) {
                            if (z) {
                                ProgressTextButtonView.this.mText.setVisibility(0);
                                ProgressTextButtonView.this.mButton.setVisibility(8);
                                return;
                            }
                            ProgressTextButtonView.this.mText.setVisibility(8);
                            ProgressTextButtonView.this.mButton.setVisibility(0);
                        }

                        public void onAnimationCancel(Animator animator) {
                        }

                        public void onAnimationRepeat(Animator animator) {
                        }
                    });
                    this.mAlphaAnimator.start();
                }
            }
        } else if (z) {
            this.mText.setAlpha(1.0f);
            this.mButton.setVisibility(8);
            this.mText.setVisibility(0);
        } else {
            this.mButton.setAlpha(1.0f);
            this.mButton.setVisibility(0);
            this.mText.setVisibility(8);
        }
    }

    public void cancelAllAnimation() {
        if (this.mAlphaAnimator != null) {
            this.mAlphaAnimator.cancel();
            this.mAlphaAnimator.removeAllUpdateListeners();
        }
        if (this.mButton != null) {
            this.mButton.cancelAllAnimation();
        }
    }

    public TextView getTextView() {
        return this.mText;
    }

    public CircularProgressButton getButton() {
        return this.mButton;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(ProgressTextButtonView.class.getName());
    }
}
