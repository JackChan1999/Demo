package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.meizu.common.R;

public class PraiseView extends View {
    private ObjectAnimator mAnimator;
    private Drawable mCanDrawable;
    public Stage mCurrentStage;
    public onPraiseCallBack mPraCallBack;
    private Drawable mPraDrawable;

    class AnimInterpolator implements Interpolator {
        private AnimInterpolator() {
        }

        public float getInterpolation(float f) {
            return (float) (1.0d - Math.pow((double) (1.0f - f), 2.0d));
        }
    }

    public enum Stage {
        PRAISED,
        CANCEL
    }

    public interface onPraiseCallBack {
        void cancelAnimEnd();

        void praAlphAnimEnd();

        void praAnimEnd();
    }

    public PraiseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        this.mCurrentStage = Stage.CANCEL;
        this.mCanDrawable = getResources().getDrawable(R.drawable.mz_praise_white);
        this.mPraDrawable = getResources().getDrawable(R.drawable.mz_praise_red);
        setBackgroundDrawable(this.mCurrentStage == Stage.PRAISED ? this.mPraDrawable : this.mCanDrawable);
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(this.mCanDrawable.getIntrinsicWidth(), this.mCanDrawable.getIntrinsicHeight());
    }

    private void startAnim() {
        if (this.mAnimator != null) {
            this.mAnimator.end();
            this.mAnimator.removeAllListeners();
        }
        this.mAnimator = ObjectAnimator.ofFloat(this, "alpha", new float[]{1.0f, 0.0f});
        this.mAnimator.setDuration(166);
        this.mAnimator.setInterpolator(getInterpolator(0.33f, 0.67f));
        this.mAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                PraiseView.this.setBackgroundDrawable(PraiseView.this.mPraDrawable);
                if (PraiseView.this.mPraCallBack != null) {
                    PraiseView.this.mPraCallBack.praAlphAnimEnd();
                }
            }
        });
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("alpha", new float[]{0.0f, 1.0f});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat("scaleX", new float[]{1.0f, 1.3f});
        PropertyValuesHolder ofFloat3 = PropertyValuesHolder.ofFloat("scaleY", new float[]{1.0f, 1.3f});
        setPivotX(0.523f * ((float) this.mPraDrawable.getIntrinsicWidth()));
        setPivotY(0.649f * ((float) this.mPraDrawable.getIntrinsicHeight()));
        Animator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofFloat, ofFloat2, ofFloat3});
        ofPropertyValuesHolder.setDuration(166);
        ofPropertyValuesHolder.setInterpolator(getInterpolator(0.33f, 0.45f));
        ofFloat2 = PropertyValuesHolder.ofFloat("scaleX", new float[]{1.3f, 0.95f});
        ofFloat3 = PropertyValuesHolder.ofFloat("scaleY", new float[]{1.3f, 0.95f});
        Animator ofPropertyValuesHolder2 = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofFloat2, ofFloat3});
        ofPropertyValuesHolder2.setDuration(250);
        ofPropertyValuesHolder2.setInterpolator(getInterpolator(0.38f, 0.63f));
        ofFloat3 = PropertyValuesHolder.ofFloat("scaleX", new float[]{0.95f, 1.0f});
        PropertyValuesHolder ofFloat4 = PropertyValuesHolder.ofFloat("scaleY", new float[]{0.95f, 1.0f});
        Animator ofPropertyValuesHolder3 = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofFloat3, ofFloat4});
        ofPropertyValuesHolder3.setDuration(166);
        ofPropertyValuesHolder3.setInterpolator(getInterpolator(0.33f, 0.67f));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.mAnimator).before(ofPropertyValuesHolder);
        animatorSet.play(ofPropertyValuesHolder).before(ofPropertyValuesHolder2);
        animatorSet.play(ofPropertyValuesHolder2).before(ofPropertyValuesHolder3);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                if (PraiseView.this.mPraCallBack != null) {
                    PraiseView.this.mPraCallBack.praAnimEnd();
                }
            }
        });
    }

    private void cancelAnim() {
        if (this.mAnimator != null) {
            this.mAnimator.end();
            this.mAnimator.removeAllListeners();
        }
        setBackgroundDrawable(this.mCanDrawable);
        this.mAnimator = ObjectAnimator.ofFloat(this, "alpha", new float[]{0.0f, 1.0f});
        this.mAnimator.setDuration(166);
        this.mAnimator.setInterpolator(getInterpolator(0.33f, 0.67f));
        this.mAnimator.start();
        this.mAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                if (PraiseView.this.mPraCallBack != null) {
                    PraiseView.this.mPraCallBack.cancelAnimEnd();
                }
            }
        });
    }

    @TargetApi(21)
    private Interpolator getInterpolator(float f, float f2) {
        if (VERSION.SDK_INT >= 21) {
            return new PathInterpolator(f, 0.0f, 1.0f - f2, 1.0f);
        }
        return new AnimInterpolator();
    }

    public void setState(Stage stage) {
        this.mCurrentStage = stage;
        switch (this.mCurrentStage) {
            case PRAISED:
                setBackgroundDrawable(this.mPraDrawable);
                cancelAnim();
                this.mCurrentStage = Stage.CANCEL;
                return;
            case CANCEL:
                setBackgroundDrawable(this.mCanDrawable);
                startAnim();
                this.mCurrentStage = Stage.PRAISED;
                return;
            default:
                return;
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (accessibilityEvent.getEventType() != 128) {
            return;
        }
        if (this.mCurrentStage == Stage.PRAISED) {
            setContentDescription(getResources().getString(R.string.mc_praised_message));
        } else {
            setContentDescription(getResources().getString(R.string.mc_cancel_praised_message));
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(PraiseView.class.getName());
    }

    public void setBackgroundResId(int i, int i2) {
        this.mPraDrawable = getResources().getDrawable(i);
        this.mCanDrawable = getResources().getDrawable(i2);
        setBackgroundDrawable(this.mCurrentStage == Stage.PRAISED ? this.mPraDrawable : this.mCanDrawable);
    }

    public Stage getState() {
        return this.mCurrentStage;
    }

    public void setPraiseCallBack(onPraiseCallBack com_meizu_common_widget_PraiseView_onPraiseCallBack) {
        this.mPraCallBack = com_meizu_common_widget_PraiseView_onPraiseCallBack;
    }
}
