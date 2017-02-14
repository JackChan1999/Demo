package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.meizu.common.a.e;

public class c {
    Drawable a;
    int b;
    int c;
    float d = 0.0f;
    float e = 1.0f;
    float f = 1.0f;
    RectF g = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
    AnimatorSet h;
    final float i = 1.0f;
    final float j = 0.7f;
    final float k = 0.72f;
    private View l;
    private boolean m = false;

    public c(Context context, View delegateView) {
        this.l = delegateView;
        this.a = context.getResources().getDrawable(e.mz_ic_actionbar_highlight);
        if (this.a != null) {
            a(0.7f);
            this.b = this.a.getIntrinsicWidth();
            this.c = this.a.getIntrinsicHeight();
        }
    }

    public void a(Drawable drawable) {
        this.a = drawable;
        if (this.a != null) {
            a(0.7f);
            this.b = this.a.getIntrinsicWidth();
            this.c = this.a.getIntrinsicHeight();
        }
    }

    public void a(Canvas canvas) {
        if (this.a != null) {
            canvas.save();
            int w = this.l.getWidth();
            int h = this.l.getHeight();
            int drawW = this.b;
            int drawH = this.c;
            int marginWidth = (drawW - w) / 2;
            int marginHeight = (drawH - h) / 2;
            canvas.scale(this.e, this.e, ((float) w) * 0.5f, ((float) h) * 0.5f);
            this.a.setBounds(-marginWidth, -marginHeight, drawW - marginWidth, drawH - marginHeight);
            this.a.setAlpha((int) ((this.f * this.d) * 255.0f));
            this.a.draw(canvas);
            canvas.restore();
            this.g.right = (float) w;
            this.g.bottom = (float) h;
        }
    }

    public void a(float x) {
        if (this.a != null) {
            this.f = x;
        }
    }

    public float a() {
        if (this.a == null) {
            return 0.0f;
        }
        return this.d;
    }

    public void b(float x) {
        if (this.a != null) {
            this.d = x;
            d();
        }
    }

    private void d() {
        this.l.invalidate();
    }

    public float b() {
        if (this.a == null) {
            return 0.0f;
        }
        return this.e;
    }

    public void c(float x) {
        if (this.a != null) {
            this.e = x;
            float rx = (((((float) this.b) * this.e) - ((float) this.l.getWidth())) / 2.0f) + 1.0f;
            float ry = (((((float) this.c) * this.e) - ((float) this.l.getHeight())) / 2.0f) + 1.0f;
            a(this.l, new RectF(((float) this.l.getLeft()) - rx, ((float) this.l.getTop()) - ry, ((float) this.l.getRight()) + rx, ((float) this.l.getBottom()) + ry));
            if (this.l.getParent() != null) {
                ((View) this.l.getParent()).invalidate();
            }
        }
    }

    public void a(boolean pressed) {
        if (!(this.a == null || pressed == c())) {
            if (this.h != null && this.h.isRunning()) {
                this.h.cancel();
            }
            AnimatorSet as = new AnimatorSet();
            this.h = as;
            if (pressed) {
                if (this.e < 1.0f) {
                    this.e = 1.0f;
                }
                if (this.d < 0.7f) {
                    this.d = 0.7f;
                }
                a(1.0f);
                c(1.0f);
                b(1.0f);
            } else {
                a(1.0f);
                r1 = new Animator[2];
                r1[0] = ObjectAnimator.ofFloat(this, "glowAlpha", new float[]{0.0f});
                r1[1] = ObjectAnimator.ofFloat(this, "glowScale", new float[]{0.72f});
                as.playTogether(r1);
                as.setDuration(416);
            }
            as.start();
        }
        this.m = pressed;
        d();
    }

    public void a(View view, RectF childBounds) {
        while (view.getParent() != null && (view.getParent() instanceof View)) {
            view = (View) view.getParent();
            view.getMatrix().mapRect(childBounds);
            view.invalidate((int) Math.floor((double) childBounds.left), (int) Math.floor((double) childBounds.top), (int) Math.ceil((double) childBounds.right), (int) Math.ceil((double) childBounds.bottom));
        }
    }

    public boolean c() {
        return this.m;
    }
}
