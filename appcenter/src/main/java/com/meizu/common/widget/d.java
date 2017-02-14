package com.meizu.common.widget;

class d {
    public static final d a = new d(0, 0, 0, 0);
    public final int b;
    public final int c;
    public final int d;
    public final int e;

    private d(int left, int top, int right, int bottom) {
        this.b = left;
        this.c = top;
        this.d = right;
        this.e = bottom;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        d insets = (d) o;
        if (this.e != insets.e) {
            return false;
        }
        if (this.b != insets.b) {
            return false;
        }
        if (this.d != insets.d) {
            return false;
        }
        if (this.c != insets.c) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((this.b * 31) + this.c) * 31) + this.d) * 31) + this.e;
    }

    public String toString() {
        return "Insets{left=" + this.b + ", top=" + this.c + ", right=" + this.d + ", bottom=" + this.e + '}';
    }
}
