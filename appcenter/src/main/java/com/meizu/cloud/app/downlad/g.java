package com.meizu.cloud.app.downlad;

import com.google.gson.a.a;
import com.google.gson.a.b;
import com.meizu.cloud.app.core.p;
import com.meizu.cloud.app.downlad.f.l;

public class g {
    @a
    @b(a = "Propertys")
    private int[] a;
    @a
    @b(a = "Origin")
    private int b;
    @a
    @b(a = "IsAutoInstall")
    private boolean c;
    @a
    @b(a = "IsTrack")
    private boolean d;
    @a
    @b(a = "IsUISkip")
    private boolean e;
    @a
    @b(a = "RetainErrorInfo")
    private boolean f;
    private p<Integer, Integer, l>[] g;
    @a
    @b(a = "NoteNetWork")
    private boolean h;
    @a
    private boolean i;

    public g() {
        this(-1, 1);
    }

    public g(int property) {
        this(-1, property);
    }

    public g(int origin, int... property) {
        this.c = true;
        this.d = true;
        this.e = false;
        this.f = true;
        this.i = false;
        this.b = origin;
        this.a = property;
    }

    public int[] a() {
        return this.a;
    }

    public boolean b() {
        return this.c;
    }

    public g a(boolean isAutoInstall) {
        this.c = isAutoInstall;
        return this;
    }

    public boolean c() {
        return this.d;
    }

    public g b(boolean isTrack) {
        this.d = isTrack;
        return this;
    }

    public boolean d() {
        return this.e;
    }

    public g c(boolean isUISkip) {
        this.e = isUISkip;
        return this;
    }

    public boolean a(int taskProperty, int origin, l anEnum) {
        if (this.g != null) {
            for (p<Integer, Integer, l> enumPair : this.g) {
                if (((Integer) enumPair.a).intValue() == taskProperty && enumPair.c == anEnum) {
                    return true;
                }
            }
        }
        return false;
    }

    public g a(p<Integer, Integer, l>... considerState) {
        this.g = considerState;
        return this;
    }

    public boolean e() {
        return this.f;
    }

    public g d(boolean retainErrorInfo) {
        this.f = retainErrorInfo;
        return this;
    }

    public boolean a(int taskProperty) {
        for (int property : this.a) {
            if (property == taskProperty) {
                return true;
            }
        }
        return false;
    }

    public boolean a(g taskProperty) {
        if (taskProperty == null) {
            return false;
        }
        if (this == taskProperty) {
            return true;
        }
        for (int property : taskProperty.a) {
            if (a(property)) {
                return true;
            }
        }
        return false;
    }

    public int f() {
        return this.b;
    }

    public boolean g() {
        return this.h;
    }

    public void e(boolean noteNetWork) {
        this.h = noteNetWork;
    }
}
