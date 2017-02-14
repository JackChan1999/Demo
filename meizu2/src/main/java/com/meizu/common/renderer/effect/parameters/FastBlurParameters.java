package com.meizu.common.renderer.effect.parameters;

public class FastBlurParameters extends BlurParameters {
    public static final float DEFAULT_LEVEL = 0.5f;
    public static final int DEFAULT_PASS_COUNT = 3;
    public static final float DEFAULT_SCALE = 0.4f;

    public FastBlurParameters(FastBlurParameters fastBlurParameters) {
        super(fastBlurParameters);
    }

    public float getLevel() {
        return getFloat(BlurParameters.KEY_BLUR_LEVEL, DEFAULT_LEVEL);
    }

    public int getRadius() {
        return getInt(BlurParameters.KEY_BLUR_RADIUS, 30);
    }

    public float getScale() {
        return getFloat(BlurParameters.KEY_BLUR_SCALE, DEFAULT_SCALE);
    }

    public int getPassCount() {
        return getInt(BlurParameters.KEY_BLUR_PASS_COUNT, 3);
    }
}
