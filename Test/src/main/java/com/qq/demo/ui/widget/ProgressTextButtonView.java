package com.qq.demo.ui.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ProgressTextButtonView extends FrameLayout {
    private ValueAnimator mAlphaAnimator;
    private CircularProgressButton mButton;
    private TextView mText;
    private boolean mIsShowText = false;

    public ProgressTextButtonView(Context context) {
        super(context);
    }

    public ProgressTextButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new IllegalStateException("ProgressTextButtonView must has two children");
        }
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof CircularProgressButton) {
                mButton = (CircularProgressButton) getChildAt(i);
            } else if (getChildAt(i) instanceof TextView) {
                mText = (TextView) getChildAt(i);
            }
        }
        showText(mIsShowText, false);
    }

    public void showText(final boolean show, boolean useAnim) {
        if (useAnim) {
            if (!show || mText.getVisibility() != View.VISIBLE) {
                if (show || mButton.getVisibility() != View.VISIBLE) {
                    mAlphaAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                    mAlphaAnimator.setDuration(100);
                    mAlphaAnimator.addUpdateListener(new AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float scale = (Float) animation.getAnimatedValue();
                            if (show) {
                                ProgressTextButtonView.this.mButton.setAlpha(1.0f - scale);
                                ProgressTextButtonView.this.mText.setAlpha(scale);
                                return;
                            }
                            ProgressTextButtonView.this.mButton.setAlpha(scale);
                            ProgressTextButtonView.this.mText.setAlpha(1.0f - scale);
                        }
                    });
                    mAlphaAnimator.addListener(new AnimatorListener() {
                        public void onAnimationStart(Animator animation) {
                        }

                        public void onAnimationEnd(Animator animation) {
                            if (show) {
                                ProgressTextButtonView.this.mText.setVisibility(View.VISIBLE);
                                ProgressTextButtonView.this.mButton.setVisibility(View.GONE);
                                return;
                            }
                            ProgressTextButtonView.this.mText.setVisibility(View.GONE);
                            ProgressTextButtonView.this.mButton.setVisibility(View.VISIBLE);
                        }

                        public void onAnimationCancel(Animator animation) {
                        }

                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                    mAlphaAnimator.start();
                }
            }
        } else if (show) {
            mText.setAlpha(1.0f);
            mButton.setVisibility(View.GONE);
            mText.setVisibility(View.VISIBLE);
        } else {
            mButton.setAlpha(1.0f);
            mButton.setVisibility(View.VISIBLE);
            mText.setVisibility(View.GONE);
        }
    }

    public void cancelAllAnimation() {
        if (mAlphaAnimator != null) {
            mAlphaAnimator.cancel();
            mAlphaAnimator.removeAllUpdateListeners();
        }
        if (mButton != null) {
            mButton.cancelAllAnimation();
        }
    }

    public TextView getTextView() {
        return mText;
    }

    public CircularProgressButton getButton() {
        return mButton;
    }
}
