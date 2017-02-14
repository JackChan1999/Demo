package com.meizu.cloud.app.core;

import java.util.Objects;

public class p<F, S, T> {
    public final F a;
    public final S b;
    public final T c;

    public p(F first, S second, T third) {
        this.a = first;
        this.b = second;
        this.c = third;
    }

    public boolean equals(Object o) {
        if (!(o instanceof p)) {
            return false;
        }
        p<?, ?, ?> p = (p) o;
        if (Objects.equals(p.a, this.a) && Objects.equals(p.b, this.b) && Objects.equals(p.c, this.c)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (this.a == null ? 0 : this.a.hashCode()) ^ (this.b == null ? 0 : this.b.hashCode());
        if (this.c != null) {
            i = this.c.hashCode();
        }
        return hashCode ^ i;
    }

    public static <A, B, C> p<A, B, C> a(A a, B b, C c) {
        return new p(a, b, c);
    }
}
