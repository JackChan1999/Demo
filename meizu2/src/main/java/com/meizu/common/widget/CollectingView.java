package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;

public class CollectingView extends View {
    private ObjectAnimator mAnimator;
    private Drawable mCancelDrawable;
    public OnCollectCallBack mCollectCallBack;
    private Drawable mCollectDrawable;
    public Stage mCurrentStage;

    class AnimInterpolator implements Interpolator {
        private AnimInterpolator() {
        }

        public float getInterpolation(float f) {
            return (float) (1.0d - Math.pow((double) (1.0f - f), 2.0d));
        }
    }

    public interface OnCollectCallBack {
        void cancleEndAnim();

        void cancleStartAnim();

        void collectEndAnim();

        void collectStartAnim();
    }

    public enum Stage {
        COLLECTED,
        CANCEL
    }

    public CollectingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        this.mCurrentStage = Stage.CANCEL;
        this.mCancelDrawable = getResources().getDrawable(R.drawable.mz_collect_white);
        this.mCollectDrawable = getResources().getDrawable(R.drawable.mz_collect_red);
        setBackgroundDrawable(this.mCurrentStage == Stage.COLLECTED ? this.mCollectDrawable : this.mCancelDrawable);
    }

    private void startAnim() {
        if (this.mAnimator != null) {
            this.mAnimator.end();
            this.mAnimator.removeAllListeners();
        }
        setBackgroundDrawable(this.mCollectDrawable);
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("rotationY", new float[]{-270.0f, 0.0f});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat("scaleX", new float[]{0.0f, 1.0f});
        PropertyValuesHolder ofFloat3 = PropertyValuesHolder.ofFloat("scaleY", new float[]{0.0f, 1.0f});
        setPivotX(FastBlurParameters.DEFAULT_LEVEL * ((float) this.mCollectDrawable.getIntrinsicWidth()));
        setPivotY((float) this.mCollectDrawable.getIntrinsicHeight());
        this.mAnimator = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofFloat, ofFloat2, ofFloat3});
        this.mAnimator.setDuration(640);
        this.mAnimator.setInterpolator(getInterpolator());
        this.mAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                if (CollectingView.this.mCollectCallBack != null) {
                    CollectingView.this.mCollectCallBack.collectStartAnim();
                }
            }

            public void onAnimationEnd(Animator animator) {
                if (CollectingView.this.mCollectCallBack != null) {
                    CollectingView.this.mCollectCallBack.collectEndAnim();
                }
            }
        });
        this.mAnimator.start();
    }

    private void cancelAnim() {
        if (this.mAnimator != null) {
            this.mAnimator.end();
            this.mAnimator.removeAllListeners();
        }
        setBackgroundDrawable(this.mCancelDrawable);
        if (this.mCollectCallBack != null) {
            this.mCollectCallBack.cancleStartAnim();
            this.mCollectCallBack.cancleEndAnim();
        }
    }

    @TargetApi(21)
    private Interpolator getInterpolator() {
        if (VERSION.SDK_INT >= 21) {
            return new PathInterpolator(0.0f, 0.0f, 0.1f, 1.0f);
        }
        return new AnimInterpolator();
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(this.mCancelDrawable.getIntrinsicWidth(), this.mCancelDrawable.getIntrinsicHeight());
    }

    public void setBackgroundResId(int i, int i2) {
        this.mCancelDrawable = getResources().getDrawable(i2);
        this.mCollectDrawable = getResources().getDrawable(i);
        setBackgroundDrawable(this.mCurrentStage == Stage.COLLECTED ? this.mCollectDrawable : this.mCancelDrawable);
    }

    public void setState(Stage stage) {
        this.mCurrentStage = stage;
        switch (this.mCurrentStage) {
            case COLLECTED:
                cancelAnim();
                this.mCurrentStage = Stage.CANCEL;
                return;
            case CANCEL:
                startAnim();
                this.mCurrentStage = Stage.COLLECTED;
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
        if (this.mCurrentStage == Stage.COLLECTED) {
            setContentDescription(getResources().getString(R.string.mc_collected_message));
        } else {
            setContentDescription(getResources().getString(R.string.mc_cancel_collected_message));
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(CollectingView.class.getName());
    }

    public Stage getState() {
        return this.mCurrentStage;
    }

    public void setCollectCallBack(OnCollectCallBack onCollectCallBack) {
        this.mCollectCallBack = onCollectCallBack;
    }

    public OnCollectCallBack getCollectCallBack() {
        return this.mCollectCallBack;
    }
}
