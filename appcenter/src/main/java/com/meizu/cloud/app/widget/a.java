package com.meizu.cloud.app.widget;

public class a {
    public static float a(float[] values, float time, float def) {
        float value = def;
        if (values == null || values.length <= 0) {
            return value;
        }
        float segment = 1.0f / ((float) (values.length - 1));
        int index = (int) (time / segment);
        if (index >= values.length - 1) {
            return values[values.length - 1];
        }
        return values[index] + (((values[index + 1] - values[index]) * (time - (((float) index) * segment))) / segment);
    }
}
