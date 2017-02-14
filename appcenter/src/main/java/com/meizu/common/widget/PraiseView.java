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
import com.meizu.common.a.e;
import com.meizu.common.a.h;

public class PraiseView extends View {
    public c a;
    public b b;
    private Drawable c;
    private Drawable d;
    private ObjectAnimator e;

    private class a implements Interpolator {
        final /* synthetic */ PraiseView a;

        private a(PraiseView praiseView) {
            this.a = praiseView;
        }

        public float getInterpolation(float input) {
            return (float) (1.0d - Math.pow((double) (1.0f - input), 2.0d));
        }
    }

    public enum b {
        PRAISED,
        CANCEL
    }

    public interface c {
        void a();

        void b();

        void c();
    }

    public PraiseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a();
    }

    private void a() {
        this.b = b.CANCEL;
        this.d = getResources().getDrawable(e.mz_praise_white);
        this.c = getResources().getDrawable(e.mz_praise_red);
        setBackgroundDrawable(this.b == b.PRAISED ? this.c : this.d);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(this.d.getIntrinsicWidth(), this.d.getIntrinsicHeight());
    }

    private void b() {
        if (this.e != null) {
            this.e.end();
            this.e.removeAllListeners();
        }
        this.e = ObjectAnimator.ofFloat(this, "alpha", new float[]{1.0f, 0.0f});
        this.e.setDuration(166);
        this.e.setInterpolator(a(0.33f, 0.67f));
        this.e.addListener(new AnimatorListenerAdapter(this) {
            final /* synthetic */ PraiseView a;

            {
                this.a = r1;
            }

            public void onAnimationEnd(Animator animation) {
                this.a.setBackgroundDrawable(this.a.c);
                if (this.a.a != null) {
                    this.a.a.a();
                }
            }
        });
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", new float[]{0.0f, 1.0f});
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", new float[]{1.0f, 1.3f});
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", new float[]{1.0f, 1.3f});
        setPivotX(0.523f * ((float) this.c.getIntrinsicWidth()));
        setPivotY(0.649f * ((float) this.c.getIntrinsicHeight()));
        ObjectAnimator animatorScaleBig = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{pvhX, pvhY, pvhZ});
        animatorScaleBig.setDuration(166);
        animatorScaleBig.setInterpolator(a(0.33f, 0.45f));
        PropertyValuesHolder scaleXAnim = PropertyValuesHolder.ofFloat("scaleX", new float[]{1.3f, 0.95f});
        PropertyValuesHolder scaleYAnim = PropertyValuesHolder.ofFloat("scaleY", new float[]{1.3f, 0.95f});
        ObjectAnimator animatorScaleSmall = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{scaleXAnim, scaleYAnim});
        animatorScaleSmall.setDuration(250);
        animatorScaleSmall.setInterpolator(a(0.38f, 0.63f));
        PropertyValuesHolder recoverX = PropertyValuesHolder.ofFloat("scaleX", new float[]{0.95f, 1.0f});
        PropertyValuesHolder recoverY = PropertyValuesHolder.ofFloat("scaleY", new float[]{0.95f, 1.0f});
        ObjectAnimator animatorRecoverA = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{recoverX, recoverY});
        animatorRecoverA.setDuration(166);
        animatorRecoverA.setInterpolator(a(0.33f, 0.67f));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.e).before(animatorScaleBig);
        animatorSet.play(animatorScaleBig).before(animatorScaleSmall);
        animatorSet.play(animatorScaleSmall).before(animatorRecoverA);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter(this) {
            final /* synthetic */ PraiseView a;

            {
                this.a = r1;
            }

            public void onAnimationEnd(Animator animation) {
                if (this.a.a != null) {
                    this.a.a.b();
                }
            }
        });
    }

    private void c() {
        if (this.e != null) {
            this.e.end();
            this.e.removeAllListeners();
        }
        setBackgroundDrawable(this.d);
        this.e = ObjectAnimator.ofFloat(this, "alpha", new float[]{0.0f, 1.0f});
        this.e.setDuration(166);
        this.e.setInterpolator(a(0.33f, 0.67f));
        this.e.start();
        this.e.addListener(new AnimatorListenerAdapter(this) {
            final /* synthetic */ PraiseView a;

            {
                this.a = r1;
            }

            public void onAnimationEnd(Animator animation) {
                if (this.a.a != null) {
                    this.a.a.c();
                }
            }
        });
    }

    @TargetApi(21)
    private Interpolator a(float controlX1, float controlX2) {
        if (VERSION.SDK_INT >= 21) {
            return new PathInterpolator(controlX1, 0.0f, 1.0f - controlX2, 1.0f);
        }
        return new a();
    }

    public void setState(b stage) {
        this.b = stage;
        switch (this.b) {
            case PRAISED:
                setBackgroundDrawable(this.c);
                c();
                this.b = b.CANCEL;
                return;
            case CANCEL:
                setBackgroundDrawable(this.d);
                b();
                this.b = b.PRAISED;
                return;
            default:
                return;
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        if (event.getEventType() != 128) {
            return;
        }
        if (this.b == b.PRAISED) {
            setContentDescription(getResources().getString(h.mc_praised_message));
        } else {
            setContentDescription(getResources().getString(h.mc_cancel_praised_message));
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(PraiseView.class.getName());
    }

    public void setBackgroundResId(int PraDrawableId, int CanDrawableId) {
        this.c = getResources().getDrawable(PraDrawableId);
        this.d = getResources().getDrawable(CanDrawableId);
        setBackgroundDrawable(this.b == b.PRAISED ? this.c : this.d);
    }

    public b getState() {
        return this.b;
    }

    public void setPraiseCallBack(c callBack) {
        this.a = callBack;
    }
}
