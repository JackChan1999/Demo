package com.meizu.cloud.app.drawable;

import android.graphics.drawable.GradientDrawable;

public class StrokeGradientDrawable {
    private GradientDrawable mGradientDrawable;
    private int mStrokeColor;
    private int mStrokeWidth;

    public StrokeGradientDrawable(GradientDrawable drawable) {
        this.mGradientDrawable = drawable;
    }

    public int getStrokeWidth() {
        return this.mStrokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.mStrokeWidth = strokeWidth;
        this.mGradientDrawable.setStroke(strokeWidth, getStrokeColor());
    }

    public int getStrokeColor() {
        return this.mStrokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.mStrokeColor = strokeColor;
        this.mGradientDrawable.setStroke(getStrokeWidth(), strokeColor);
    }

    public GradientDrawable getGradientDrawable() {
        return this.mGradientDrawable;
    }
}
