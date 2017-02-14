package com.meizu.common.widget;

public class l {
    private static float a = ((float) (Math.log(0.78d) / Math.log(0.9d)));
    private static final float[] b = new float[101];
    private static final float[] c = new float[101];
    private static float d = 8.0f;
    private static float e;

    static {
        float x_min = 0.0f;
        float y_min = 0.0f;
        for (int i = 0; i < 100; i++) {
            float x;
            float coef;
            float y;
            float alpha = ((float) i) / 100.0f;
            float x_max = 1.0f;
            while (true) {
                x = x_min + ((x_max - x_min) / 2.0f);
                coef = (3.0f * x) * (1.0f - x);
                float tx = ((((1.0f - x) * 0.175f) + (0.35000002f * x)) * coef) + ((x * x) * x);
                if (((double) Math.abs(tx - alpha)) < 1.0E-5d) {
                    break;
                } else if (tx > alpha) {
                    x_max = x;
                } else {
                    x_min = x;
                }
            }
            b[i] = ((((1.0f - x) * 0.5f) + x) * coef) + ((x * x) * x);
            float y_max = 1.0f;
            while (true) {
                y = y_min + ((y_max - y_min) / 2.0f);
                coef = (3.0f * y) * (1.0f - y);
                float dy = ((((1.0f - y) * 0.5f) + y) * coef) + ((y * y) * y);
                if (((double) Math.abs(dy - alpha)) < 1.0E-5d) {
                    break;
                } else if (dy > alpha) {
                    y_max = y;
                } else {
                    y_min = y;
                }
            }
            c[i] = ((((1.0f - y) * 0.175f) + (0.35000002f * y)) * coef) + ((y * y) * y);
        }
        float[] fArr = b;
        c[100] = 1.0f;
        fArr[100] = 1.0f;
        e = 1.0f;
        e = 1.0f / a(1.0f);
    }

    static float a(float x) {
        x *= d;
        if (x < 1.0f) {
            x -= 1.0f - ((float) Math.exp((double) (-x)));
        } else {
            x = 0.36787945f + ((1.0f - 0.36787945f) * (1.0f - ((float) Math.exp((double) (1.0f - x)))));
        }
        return x * e;
    }
}
