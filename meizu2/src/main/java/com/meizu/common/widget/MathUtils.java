package com.meizu.common.widget;

final class MathUtils {
    private MathUtils() {
    }

    public static float abs(float f) {
        return f > 0.0f ? f : -f;
    }

    public static float constrain(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        return f > f3 ? f3 : f;
    }

    public static float log(float f) {
        return (float) Math.log((double) f);
    }

    public static float max(float f, float f2) {
        return f > f2 ? f : f2;
    }

    public static float max(int i, int i2) {
        return i > i2 ? (float) i : (float) i2;
    }

    public static float max(float f, float f2, float f3) {
        return f > f2 ? f > f3 ? f : f3 : f2 > f3 ? f2 : f3;
    }

    public static float max(int i, int i2, int i3) {
        if (i > i2) {
            if (i <= i3) {
                i = i3;
            }
            return (float) i;
        }
        if (i2 <= i3) {
            i2 = i3;
        }
        return (float) i2;
    }

    public static float min(float f, float f2) {
        return f < f2 ? f : f2;
    }

    public static float min(int i, int i2) {
        return i < i2 ? (float) i : (float) i2;
    }

    public static float min(float f, float f2, float f3) {
        return f < f2 ? f < f3 ? f : f3 : f2 < f3 ? f2 : f3;
    }

    public static float min(int i, int i2, int i3) {
        if (i < i2) {
            if (i >= i3) {
                i = i3;
            }
            return (float) i;
        }
        if (i2 >= i3) {
            i2 = i3;
        }
        return (float) i2;
    }
}
