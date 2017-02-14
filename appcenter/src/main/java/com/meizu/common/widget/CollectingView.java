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
import com.meizu.common.a.e;
import com.meizu.common.a.h;

public class CollectingView extends View {
    public b a;
    public c b;
    private Drawable c;
    private Drawable d;
    private ObjectAnimator e;

    private class a implements Interpolator {
        final /* synthetic */ CollectingView a;

        private a(CollectingView collectingView) {
            this.a = collectingView;
        }

        public float getInterpolation(float input) {
            return (float) (1.0d - Math.pow((double) (1.0f - input), 2.0d));
        }
    }

    public interface b {
        void a();

        void b();

        void c();

        void d();
    }

    public enum c {
        COLLECTED,
        CANCEL
    }

    public CollectingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a();
    }

    private void a() {
        this.b = c.CANCEL;
        this.d = getResources().getDrawable(e.mz_collect_white);
        this.c = getResources().getDrawable(e.mz_collect_red);
        setBackgroundDrawable(this.b == c.COLLECTED ? this.c : this.d);
    }

    private void b() {
        if (this.e != null) {
            this.e.end();
            this.e.removeAllListeners();
        }
        setBackgroundDrawable(this.c);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("rotationY", new float[]{-270.0f, 0.0f});
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", new float[]{0.0f, 1.0f});
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", new float[]{0.0f, 1.0f});
        setPivotX(0.5f * ((float) this.c.getIntrinsicWidth()));
        setPivotY((float) this.c.getIntrinsicHeight());
        this.e = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{pvhX, pvhY, pvhZ});
        this.e.setDuration(640);
        this.e.setInterpolator(getInterpolator());
        this.e.addListener(new AnimatorListenerAdapter(this) {
            final /* synthetic */ CollectingView a;

            {
                this.a = r1;
            }

            public void onAnimationStart(Animator animation) {
                if (this.a.a != null) {
                    this.a.a.a();
                }
            }

            public void onAnimationEnd(Animator animation) {
                if (this.a.a != null) {
                    this.a.a.c();
                }
            }
        });
        this.e.start();
    }

    private void c() {
        if (this.e != null) {
            this.e.end();
            this.e.removeAllListeners();
        }
        setBackgroundDrawable(this.d);
        if (this.a != null) {
            this.a.b();
            this.a.d();
        }
    }

    @TargetApi(21)
    private Interpolator getInterpolator() {
        if (VERSION.SDK_INT >= 21) {
            return new PathInterpolator(0.0f, 0.0f, 0.1f, 1.0f);
        }
        return new a();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(this.d.getIntrinsicWidth(), this.d.getIntrinsicHeight());
    }

    public void setBackgroundResId(int collectDrawableId, int cancleDrawableId) {
        this.d = getResources().getDrawable(cancleDrawableId);
        this.c = getResources().getDrawable(collectDrawableId);
        setBackgroundDrawable(this.b == c.COLLECTED ? this.c : this.d);
    }

    public void setState(c stage) {
        this.b = stage;
        switch (this.b) {
            case COLLECTED:
                c();
                this.b = c.CANCEL;
                return;
            case CANCEL:
                b();
                this.b = c.COLLECTED;
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
        if (this.b == c.COLLECTED) {
            setContentDescription(getResources().getString(h.mc_collected_message));
        } else {
            setContentDescription(getResources().getString(h.mc_cancel_collected_message));
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(CollectingView.class.getName());
    }

    public c getState() {
        return this.b;
    }

    public void setCollectCallBack(b callBack) {
        this.a = callBack;
    }

    public b getCollectCallBack() {
        return this.a;
    }
}
