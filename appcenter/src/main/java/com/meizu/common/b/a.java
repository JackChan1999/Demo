package com.meizu.common.b;

import android.view.animation.Interpolator;

public class a implements Interpolator {
    private int a = 100;
    private float b = 0.01f;
    private float[] c;
    private float[] d;

    public a(float controlX1, float controlY1, float controlX2, float controlY2) {
        a(controlX1, controlY1, controlX2, controlY2);
    }

    private void a(float x1, float y1, float x2, float y2) {
        this.c = new float[this.a];
        this.d = new float[this.a];
        float t = 0.0f;
        float ax = (1.0f + (3.0f * x1)) - (3.0f * x2);
        float bx = (3.0f * x2) - (6.0f * x1);
        float cx = 3.0f * x1;
        float ay = (1.0f + (3.0f * y1)) - (3.0f * y2);
        float by = (3.0f * y2) - (6.0f * y1);
        float cy = 3.0f * y1;
        for (int i = 0; i < this.a; i++) {
            float tt = t * t;
            float ttt = tt * t;
            this.c[i] = ((ax * ttt) + (bx * tt)) + (cx * t);
            this.d[i] = ((ay * ttt) + (by * tt)) + (cy * t);
            t += this.b;
        }
    }

    public float getInterpolation(float t) {
        if (t <= 0.0f) {
            return 0.0f;
        }
        if (t >= 1.0f) {
            return 1.0f;
        }
        int startIndex = 0;
        int endIndex = this.c.length - 1;
        while (endIndex - startIndex > 1) {
            int midIndex = (startIndex + endIndex) / 2;
            if (t < this.c[midIndex]) {
                endIndex = midIndex;
            } else {
                startIndex = midIndex;
            }
        }
        float xRange = this.c[endIndex] - this.c[startIndex];
        if (xRange == 0.0f) {
            return this.d[startIndex];
        }
        float fraction = (t - this.c[startIndex]) / xRange;
        float startY = this.d[startIndex];
        return ((this.d[endIndex] - startY) * fraction) + startY;
    }
}
