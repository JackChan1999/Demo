package com.meizu.common.interpolator;

import android.view.animation.Interpolator;

public class PathInterpolatorCompat implements Interpolator {
    private int count = 100;
    private float[] mX;
    private float[] mY;
    private float precision = 0.01f;

    public PathInterpolatorCompat(float f, float f2, float f3, float f4) {
        initPath(f, f2, f3, f4);
    }

    public PathInterpolatorCompat(float f, float f2, float f3, float f4, int i) {
        int i2 = i / 5;
        if (i2 <= this.count) {
            i2 = this.count;
        }
        this.count = i2;
        this.precision = 1.0f / ((float) this.count);
        initPath(f, f2, f3, f4);
    }

    private void initPath(float f, float f2, float f3, float f4) {
        this.mX = new float[this.count];
        this.mY = new float[this.count];
        float f5 = 0.0f;
        float f6 = (1.0f + (3.0f * f)) - (3.0f * f3);
        float f7 = (3.0f * f3) - (6.0f * f);
        float f8 = 3.0f * f;
        float f9 = (1.0f + (3.0f * f2)) - (3.0f * f4);
        float f10 = (3.0f * f4) - (6.0f * f2);
        float f11 = 3.0f * f2;
        for (int i = 0; i < this.count; i++) {
            float f12 = f5 * f5;
            float f13 = f12 * f5;
            this.mX[i] = ((f6 * f13) + (f7 * f12)) + (f8 * f5);
            this.mY[i] = ((f12 * f10) + (f13 * f9)) + (f11 * f5);
            f5 += this.precision;
        }
    }

    public float getInterpolation(float f) {
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        int i = 0;
        int length = this.mX.length - 1;
        while (length - i > 1) {
            int i2 = (i + length) / 2;
            if (f < this.mX[i2]) {
                length = i;
            } else {
                int i3 = length;
                length = i2;
                i2 = i3;
            }
            i = length;
            length = i2;
        }
        float f2 = this.mX[length] - this.mX[i];
        if (f2 == 0.0f) {
            return this.mY[i];
        }
        f2 = (f - this.mX[i]) / f2;
        float f3 = this.mY[i];
        return (f2 * (this.mY[length] - f3)) + f3;
    }
}
