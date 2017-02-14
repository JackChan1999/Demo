package com.meizu.common.a;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;
import com.meizu.cloud.app.drawable.CircularAnimatedDrawable;

public class b extends Drawable implements Animatable {
    private final long a = 1760;
    private final RectF b = new RectF();
    private Paint c;
    private float d;
    private boolean e;
    private Animator f = null;
    private float g;
    private float h;
    private boolean i = true;

    public b(int color, float borderWidth) {
        this.d = borderWidth;
        this.c = new Paint();
        this.c.setAntiAlias(true);
        this.c.setStyle(Style.STROKE);
        this.c.setStrokeWidth(borderWidth);
        this.c.setColor(color);
        this.c.setStrokeCap(Cap.ROUND);
        this.f = a();
    }

    public void draw(Canvas canvas) {
        canvas.drawArc(this.b, this.g, this.h, false, this.c);
    }

    public void setAlpha(int alpha) {
        this.c.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.c.setColorFilter(cf);
    }

    public int getOpacity() {
        return -2;
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.b.left = (((float) bounds.left) + (this.d / 2.0f)) + 0.5f;
        this.b.right = (((float) bounds.right) - (this.d / 2.0f)) - 0.5f;
        this.b.top = (((float) bounds.top) + (this.d / 2.0f)) + 0.5f;
        this.b.bottom = (((float) bounds.bottom) - (this.d / 2.0f)) - 0.5f;
    }

    public void start() {
        if (!isRunning()) {
            this.e = true;
            this.f.start();
            invalidateSelf();
        }
    }

    public void stop() {
        if (isRunning()) {
            this.e = false;
            this.f.cancel();
            invalidateSelf();
        }
    }

    public boolean isRunning() {
        return this.e;
    }

    private Animator a() {
        Keyframe key1 = Keyframe.ofFloat(0.0f, -90.0f);
        Keyframe key2 = Keyframe.ofFloat(0.5f, 330.0f);
        Keyframe key3 = Keyframe.ofFloat(1.0f, 630.0f);
        PropertyValuesHolder pvhStart = PropertyValuesHolder.ofKeyframe(CircularAnimatedDrawable.START_ANGLE_PROPERTY, new Keyframe[]{key1, key2, key3});
        PropertyValuesHolder pvhSweep = PropertyValuesHolder.ofFloat(CircularAnimatedDrawable.SWEEP_ANGLE_PROPERTY, new float[]{0.0f, -120.0f, 0.0f});
        ObjectAnimator loadingAnim = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{pvhStart, pvhSweep});
        loadingAnim.setDuration(1760);
        loadingAnim.setInterpolator(new LinearInterpolator());
        loadingAnim.setRepeatCount(-1);
        return loadingAnim;
    }

    public void a(boolean allow) {
        this.i = allow;
    }
}
