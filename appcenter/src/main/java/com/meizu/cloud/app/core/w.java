package com.meizu.cloud.app.core;

import android.content.Context;
import android.content.res.ColorStateList;
import com.meizu.cloud.app.utils.b;

public class w {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    private boolean g;

    public void a(boolean isCustom) {
        this.g = isCustom;
    }

    public ColorStateList a(Context context) {
        if (this.a == -1) {
            return null;
        }
        if (this.g) {
            return b.b(context, this.a);
        }
        return b.a(context, this.a);
    }

    public ColorStateList b(Context context) {
        if (this.a == -1) {
            return null;
        }
        if (this.g) {
            return b.b(context, this.a);
        }
        return b.a(context, this.a);
    }

    public int c(Context context) {
        if (this.a == -1) {
            return -1;
        }
        if (this.g) {
            return this.a;
        }
        return context.getResources().getColor(this.a);
    }

    public ColorStateList d(Context context) {
        return b.c(context, 17170445);
    }

    public ColorStateList e(Context context) {
        if (this.a == -1) {
            return null;
        }
        if (this.g) {
            return b.b(context, this.e);
        }
        return b.a(context, this.e);
    }

    public ColorStateList f(Context context) {
        if (this.g) {
            return b.d(context, this.c);
        }
        return b.c(context, this.c);
    }

    public ColorStateList g(Context context) {
        if (this.g) {
            return b.d(context, this.f);
        }
        return b.c(context, this.f);
    }
}
