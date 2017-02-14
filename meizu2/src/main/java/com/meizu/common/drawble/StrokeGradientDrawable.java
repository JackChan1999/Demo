package com.meizu.common.drawble;

import android.graphics.drawable.GradientDrawable;

public class StrokeGradientDrawable {
    private GradientDrawable mGradientDrawable;
    private int mStrokeColor;
    private int mStrokeWidth;

    public StrokeGradientDrawable(GradientDrawable gradientDrawable) {
        this.mGradientDrawable = gradientDrawable;
    }

    public int getStrokeWidth() {
        return this.mStrokeWidth;
    }

    public void setStrokeWidth(int i) {
        this.mStrokeWidth = i;
        this.mGradientDrawable.setStroke(i, getStrokeColor());
    }

    public int getStrokeColor() {
        return this.mStrokeColor;
    }

    public void setStrokeColor(int i) {
        this.mStrokeColor = i;
        this.mGradientDrawable.setStroke(getStrokeWidth(), i);
    }

    public GradientDrawable getGradientDrawable() {
        return this.mGradientDrawable;
    }
}
