package com.meizu.common.widget;

final class e {
    public static float a(float amount, float low, float high) {
        if (amount < low) {
            return low;
        }
        return amount > high ? high : amount;
    }
}
