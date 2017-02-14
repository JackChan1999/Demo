package com.meizu.common.a;

import android.graphics.drawable.GradientDrawable;

public class d {
    private int a;
    private int b;
    private GradientDrawable c;

    public d(GradientDrawable drawable) {
        this.c = drawable;
    }

    public int a() {
        return this.a;
    }

    public void a(int strokeWidth) {
        this.a = strokeWidth;
        this.c.setStroke(strokeWidth, b());
    }

    public int b() {
        return this.b;
    }

    public void b(int strokeColor) {
        this.b = strokeColor;
        this.c.setStroke(a(), strokeColor);
    }

    public GradientDrawable c() {
        return this.c;
    }
}
