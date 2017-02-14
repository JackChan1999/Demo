package com.meizu.flyme.appcenter.desktopplugin.view.customview;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.meizu.mstore.R;

public class IconBadgeView extends ImageView {
    public Boolean a;
    private int b;
    private Paint mPaint;
    private ValueAnimator mAnimator;
    private Bitmap mBitmap;
    private float f;

    public IconBadgeView(Context context) {
        this(context, null);
    }

    public IconBadgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.f = 0.0f;
        this.a = Boolean.valueOf(false);
        this.b = 0;
        setBackgroundResource(R.drawable.round_badgeview_background);
        setImageResource(R.drawable.plugin_badgeview_downloading);
        this.mBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.plugin_badgeview_download_anim, null)).getBitmap();
        this.mPaint = new Paint();
        this.mPaint.setFilterBitmap(false);
        this.mPaint.setStyle(Style.FILL);
        c();
    }

    public IconBadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.f = 0.0f;
        this.a = Boolean.valueOf(false);
    }

    private void c() {
        this.mAnimator = ValueAnimator.ofFloat(new float[]{-50.0f, -1.0f});
        this.mAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ IconBadgeView a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.a.f = ((Float) animation.getAnimatedValue()).floatValue();
                this.a.invalidate();
            }
        });
        this.mAnimator.addListener(new AnimatorListener(this) {
            final /* synthetic */ IconBadgeView a;

            {
                this.a = r1;
            }

            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                if (this.a.a.booleanValue()) {
                    this.a.mAnimator.cancel();
                }
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        this.mAnimator.setRepeatCount(-1);
        this.mAnimator.setDuration(1000);
    }

    protected void onDraw(Canvas canvas) {
        switch (this.b) {
            case 0:
                super.onDraw(canvas);
                return;
            case 1:
                canvas.drawBitmap(this.mBitmap, 0.0f, this.f, this.mPaint);
                return;
            case 2:
                super.onDraw(canvas);
                return;
            case 4:
                super.onDraw(canvas);
                return;
            default:
                return;
        }
    }

    public int getState() {
        return this.b;
    }

    public void setState(int state) {
        Boolean stateNotChanged = Boolean.valueOf(state == this.b);
        this.b = state;
        if (state == 1) {
            if (!stateNotChanged.booleanValue()) {
                this.mAnimator.start();
            }
        } else if (state == 2) {
            setImageResource(17170445);
            this.mAnimator.cancel();
            setImageResource(R.drawable.plugin_badgeview_pause);
        } else if (state == 4) {
            setImageResource(R.drawable.plugin_badgeview_finish);
            this.mAnimator.cancel();
        } else if (state == 0) {
            setImageResource(R.drawable.plugin_badgeview_downloading);
            this.mAnimator.cancel();
        } else if (this.b == 3) {
            setImageResource(17170445);
            this.mAnimator.cancel();
        }
    }

    public void a() {
        this.mAnimator.cancel();
        this.mAnimator.removeAllUpdateListeners();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAnimator.cancel();
        this.mAnimator.removeAllUpdateListeners();
        if (getAnimation() != null) {
            getAnimation().cancel();
        }
        clearAnimation();
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (getVisibility() != View.VISIBLE) {
            this.mAnimator.cancel();
            this.mAnimator.removeAllUpdateListeners();
        }
    }

    public void cancel() {
        this.mAnimator.cancel();
    }
}
