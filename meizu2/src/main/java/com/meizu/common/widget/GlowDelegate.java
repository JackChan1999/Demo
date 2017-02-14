package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.meizu.common.R;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;

public class GlowDelegate {
    private static final String TAG = "GlowDelegate";
    final float BUTTON_QUIESCENT_ALPHA = 0.7f;
    final float GLOW_MAX_SCALE_FACTOR = 1.0f;
    final float GLOW_MIN_SCALE_FACTOR = 0.72f;
    float mDrawingAlpha = 1.0f;
    float mGlowAlpha = 0.0f;
    Drawable mGlowBG;
    int mGlowHeight;
    float mGlowScale = 1.0f;
    int mGlowWidth;
    private boolean mPressed = false;
    AnimatorSet mPressedAnim;
    RectF mRect = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
    private View mView;

    public GlowDelegate(Context context, View view) {
        this.mView = view;
        this.mGlowBG = context.getResources().getDrawable(R.drawable.mz_ic_actionbar_highlight);
        if (this.mGlowBG != null) {
            setDrawingAlpha(0.7f);
            this.mGlowWidth = this.mGlowBG.getIntrinsicWidth();
            this.mGlowHeight = this.mGlowBG.getIntrinsicHeight();
        }
    }

    public void setGlowBackground(Drawable drawable) {
        this.mGlowBG = drawable;
        if (this.mGlowBG != null) {
            setDrawingAlpha(0.7f);
            this.mGlowWidth = this.mGlowBG.getIntrinsicWidth();
            this.mGlowHeight = this.mGlowBG.getIntrinsicHeight();
        }
    }

    public void onDraw(Canvas canvas) {
        if (this.mGlowBG != null) {
            canvas.save();
            int width = this.mView.getWidth();
            int height = this.mView.getHeight();
            int i = this.mGlowWidth;
            int i2 = this.mGlowHeight;
            int i3 = (i - width) / 2;
            int i4 = (i2 - height) / 2;
            canvas.scale(this.mGlowScale, this.mGlowScale, ((float) width) * FastBlurParameters.DEFAULT_LEVEL, ((float) height) * FastBlurParameters.DEFAULT_LEVEL);
            this.mGlowBG.setBounds(-i3, -i4, i - i3, i2 - i4);
            this.mGlowBG.setAlpha((int) ((this.mDrawingAlpha * this.mGlowAlpha) * 255.0f));
            this.mGlowBG.draw(canvas);
            canvas.restore();
            this.mRect.right = (float) width;
            this.mRect.bottom = (float) height;
        }
    }

    public float getDrawingAlpha() {
        if (this.mGlowBG == null) {
            return 0.0f;
        }
        return this.mDrawingAlpha;
    }

    public void setDrawingAlpha(float f) {
        if (this.mGlowBG != null) {
            this.mDrawingAlpha = f;
        }
    }

    public float getGlowAlpha() {
        if (this.mGlowBG == null) {
            return 0.0f;
        }
        return this.mGlowAlpha;
    }

    public void setGlowAlpha(float f) {
        if (this.mGlowBG != null) {
            this.mGlowAlpha = f;
            invalidate();
        }
    }

    private void invalidate() {
        this.mView.invalidate();
    }

    public float getGlowScale() {
        if (this.mGlowBG == null) {
            return 0.0f;
        }
        return this.mGlowScale;
    }

    public void setGlowScale(float f) {
        if (this.mGlowBG != null) {
            this.mGlowScale = f;
            float width = (((((float) this.mGlowWidth) * this.mGlowScale) - ((float) this.mView.getWidth())) / CircleProgressBar.BAR_WIDTH_DEF_DIP) + 1.0f;
            float height = (((((float) this.mGlowHeight) * this.mGlowScale) - ((float) this.mView.getHeight())) / CircleProgressBar.BAR_WIDTH_DEF_DIP) + 1.0f;
            invalidateGlobalRegion(this.mView, new RectF(((float) this.mView.getLeft()) - width, ((float) this.mView.getTop()) - height, width + ((float) this.mView.getRight()), height + ((float) this.mView.getBottom())));
            if (this.mView.getParent() != null) {
                ((View) this.mView.getParent()).invalidate();
            }
        }
    }

    public void setPressed(boolean z) {
        if (!(this.mGlowBG == null || z == isPressed())) {
            if (this.mPressedAnim != null && this.mPressedAnim.isRunning()) {
                this.mPressedAnim.cancel();
            }
            AnimatorSet animatorSet = new AnimatorSet();
            this.mPressedAnim = animatorSet;
            if (z) {
                if (this.mGlowScale < 1.0f) {
                    this.mGlowScale = 1.0f;
                }
                if (this.mGlowAlpha < 0.7f) {
                    this.mGlowAlpha = 0.7f;
                }
                setDrawingAlpha(1.0f);
                setGlowScale(1.0f);
                setGlowAlpha(1.0f);
            } else {
                setDrawingAlpha(1.0f);
                r1 = new Animator[2];
                r1[0] = ObjectAnimator.ofFloat(this, "glowAlpha", new float[]{0.0f});
                r1[1] = ObjectAnimator.ofFloat(this, "glowScale", new float[]{0.72f});
                animatorSet.playTogether(r1);
                animatorSet.setDuration(416);
            }
            animatorSet.start();
        }
        this.mPressed = z;
        invalidate();
    }

    public void invalidateGlobalRegion(View view, RectF rectF) {
        while (view.getParent() != null && (view.getParent() instanceof View)) {
            View view2 = (View) view.getParent();
            view2.getMatrix().mapRect(rectF);
            view2.invalidate((int) Math.floor((double) rectF.left), (int) Math.floor((double) rectF.top), (int) Math.ceil((double) rectF.right), (int) Math.ceil((double) rectF.bottom));
            view = view2;
        }
    }

    public boolean isPressed() {
        return this.mPressed;
    }

    public void jumpToCurrentState() {
        if (this.mPressedAnim != null && this.mPressedAnim.isRunning()) {
            this.mPressedAnim.cancel();
            setGlowAlpha(0.0f);
            setGlowScale(0.72f);
        }
    }
}
