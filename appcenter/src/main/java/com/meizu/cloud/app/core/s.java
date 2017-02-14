package com.meizu.cloud.app.core;

public class s {
    public boolean a;
    public boolean b;
    public boolean c;
    public boolean d;
    public boolean e;

    public s a(boolean isCheckDelta) {
        this.a = isCheckDelta;
        return this;
    }

    public s b(boolean isShowNotify) {
        this.b = isShowNotify;
        return this;
    }

    public s c(boolean isInitiative) {
        this.c = isInitiative;
        return this;
    }

    public s d(boolean isManual) {
        this.d = isManual;
        return this;
    }

    public s e(boolean isBatch) {
        this.e = isBatch;
        return this;
    }
}
