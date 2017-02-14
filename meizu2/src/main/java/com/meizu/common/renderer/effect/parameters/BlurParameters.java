package com.meizu.common.renderer.effect.parameters;

import com.meizu.common.renderer.Utils;

public class BlurParameters extends BaseParameters {
    public static final int DEFAULT_FILTER_COLOR = 0;
    public static final float DEFAULT_INTENSITY = 1.0f;
    public static final float DEFAULT_LEVEL = 1.0f;
    public static final float DEFAULT_NORMAL_SCALE = 0.06f;
    public static final int DEFAULT_PASS_COUNT = 2;
    public static final boolean DEFAULT_PROGRESS_BLUR = false;
    public static final float DEFAULT_PROGRESS_BLUR_SCALE = 0.35f;
    public static final int DEFAULT_RADIUS = 4;
    public static final String KEY_BLUR_FILTER_COLOR = "blur_mask_filter_key";
    public static final String KEY_BLUR_INTENSITY = "blur_intensity_key";
    public static final String KEY_BLUR_LEVEL = "blur_level_key";
    public static final String KEY_BLUR_PASS_COUNT = "blur_pass_count_key";
    public static final String KEY_BLUR_PROGRESS = "blur_progress_key";
    public static final String KEY_BLUR_RADIUS = "blur_radius_key";
    public static final String KEY_BLUR_SCALE = "blur_scale_key";
    public static final int MAX_RADIUS = 30;
    public static final int RENDERMODE_ANIMATION = 2;
    public static final int RENDERMODE_CONTINUOUS = 0;
    public static final int RENDERMODE_STATIC = 1;

    public BlurParameters(BlurParameters blurParameters) {
        super(blurParameters);
    }

    public void setLevel(float f) {
        set(KEY_BLUR_LEVEL, Float.valueOf(Utils.clip(f, 0.0f, 1.0f)));
    }

    public float getLevel() {
        return getFloat(KEY_BLUR_LEVEL, 1.0f);
    }

    public void setProgressBlur(boolean z) {
        set(KEY_BLUR_PROGRESS, Boolean.valueOf(z));
    }

    public boolean getProgressBlur() {
        return getBoolean(KEY_BLUR_PROGRESS, false);
    }

    public void setFilterColor(int i) {
        set(KEY_BLUR_FILTER_COLOR, Integer.valueOf(i));
    }

    public int getFilterColor() {
        return getInt(KEY_BLUR_FILTER_COLOR, 0);
    }

    public void setRadius(int i) {
        set(KEY_BLUR_RADIUS, Integer.valueOf(i));
    }

    public int getRadius() {
        return getInt(KEY_BLUR_RADIUS, 4);
    }

    public void setScale(float f) {
        set(KEY_BLUR_SCALE, Float.valueOf(Utils.clip(f, 0.01f, 1.0f)));
    }

    public float getScale() {
        return getFloat(KEY_BLUR_SCALE, getProgressBlur() ? DEFAULT_PROGRESS_BLUR_SCALE : DEFAULT_NORMAL_SCALE);
    }

    public void setIntensity(float f) {
        set(KEY_BLUR_INTENSITY, Float.valueOf(f));
    }

    public float getIntensity() {
        return getFloat(KEY_BLUR_INTENSITY, 1.0f);
    }

    public void setPassCount(int i) {
        set(KEY_BLUR_PASS_COUNT, Integer.valueOf(i));
    }

    public int getPassCount() {
        return getInt(KEY_BLUR_PASS_COUNT, 2);
    }
}
