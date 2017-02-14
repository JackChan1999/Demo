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
    private CircularProgressButton a;
    private TextView b;
    private boolean c = false;
    private ValueAnimator d;

    public ProgressTextButtonView(Context context) {
        super(context);
    }

    public ProgressTextButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new IllegalStateException("ProgressTextButtonView must has two children");
        }
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof CircularProgressButton) {
                this.a = (CircularProgressButton) getChildAt(i);
            } else if (getChildAt(i) instanceof TextView) {
                this.b = (TextView) getChildAt(i);
            }
        }
        a(this.c, false);
    }

    public void a(final boolean show, boolean useAnim) {
        if (useAnim) {
            if (!show || this.b.getVisibility() != 0) {
                if (show || this.a.getVisibility() != 0) {
                    this.d = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                    this.d.setDuration(100);
                    this.d.addUpdateListener(new AnimatorUpdateListener(this) {
                        final /* synthetic */ ProgressTextButtonView b;

                        public void onAnimationUpdate(ValueAnimator animation) {
                            float scale = ((Float) animation.getAnimatedValue()).floatValue();
                            if (show) {
                                this.b.a.setAlpha(1.0f - scale);
                                this.b.b.setAlpha(scale);
                                return;
                            }
                            this.b.a.setAlpha(scale);
                            this.b.b.setAlpha(1.0f - scale);
                        }
                    });
                    this.d.addListener(new AnimatorListener(this) {
                        final /* synthetic */ ProgressTextButtonView b;

                        public void onAnimationStart(Animator animation) {
                        }

                        public void onAnimationEnd(Animator animation) {
                            if (show) {
                                this.b.b.setVisibility(0);
                                this.b.a.setVisibility(8);
                                return;
                            }
                            this.b.b.setVisibility(8);
                            this.b.a.setVisibility(0);
                        }

                        public void onAnimationCancel(Animator animation) {
                        }

                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                    this.d.start();
                }
            }
        } else if (show) {
            this.b.setAlpha(1.0f);
            this.a.setVisibility(8);
            this.b.setVisibility(0);
        } else {
            this.a.setAlpha(1.0f);
            this.a.setVisibility(0);
            this.b.setVisibility(8);
        }
    }

    public TextView getTextView() {
        return this.b;
    }

    public CircularProgressButton getButton() {
        return this.a;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ProgressTextButtonView.class.getName());
    }
}
