package com.meizu.cloud.app.widget;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class b extends Animation {
    private final float[] a;
    private final float[] b;
    private final float[] c;
    private final float[] d;
    private final float[] e;
    private final float[] f;
    private final float g;
    private final float h;
    private final boolean i;
    private final boolean j;
    private Camera k;
    private int l;
    private int m;
    private int n;
    private int o;
    private boolean p;
    private boolean q;
    private boolean r;

    public b(float[] translateX, float[] translateY, float[] translateZ, float[] alpha, float[] degrees, float centerX, float centerY, boolean isYAxis, boolean reverse) {
        this(translateX, translateY, translateZ, null, alpha, degrees, centerX, centerY, isYAxis, reverse);
    }

    public b(float[] translateX, float[] translateY, float[] translateZ, float[] scale, float[] alpha, float[] degrees, float centerX, float centerY, boolean isYAxis, boolean reverse) {
        this.p = false;
        this.q = false;
        this.r = false;
        this.b = translateX;
        this.c = translateY;
        this.d = translateZ;
        this.a = scale;
        this.e = alpha;
        this.f = degrees;
        this.g = centerX;
        this.h = centerY;
        this.i = reverse;
        this.j = isYAxis;
    }

    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.k = new Camera();
        this.l = width;
        this.m = height;
    }

    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (this.i) {
            interpolatedTime = 1.0f - interpolatedTime;
        }
        float translateX = a.a(this.b, interpolatedTime, 0.0f);
        if (!this.p) {
            translateX *= (float) this.n;
        }
        float translateY = a.a(this.c, interpolatedTime, 0.0f);
        if (!this.q) {
            translateY *= (float) this.o;
        }
        float translateZ = a.a(this.d, interpolatedTime, 0.0f);
        if (!this.r) {
            translateZ *= 1000.0f;
        }
        float alpha = a.a(this.e, interpolatedTime, 1.0f);
        float degree = a.a(this.f, interpolatedTime, 0.0f);
        float centerX = this.g;
        if (!this.p) {
            centerX *= (float) this.l;
        }
        float centerY = this.h;
        if (!this.q) {
            centerY *= (float) this.m;
        }
        Camera camera = this.k;
        Matrix matrix = t.getMatrix();
        camera.save();
        if (!(translateX == 0.0f && translateY == 0.0f && translateZ == 0.0f)) {
            camera.translate(translateX, -translateY, translateZ);
        }
        if (degree != 0.0f) {
            if (this.j) {
                camera.rotateY(degree);
            } else {
                camera.rotateX(degree);
            }
        }
        camera.getMatrix(matrix);
        camera.restore();
        float scale = 1.0f;
        if (this.a != null) {
            scale = a.a(this.a, interpolatedTime, 1.0f);
        }
        if (scale != 1.0f) {
            matrix.preScale(scale, scale);
        }
        if (!(degree == 0.0f && scale == 1.0f)) {
            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
        t.setAlpha(alpha);
    }
}
