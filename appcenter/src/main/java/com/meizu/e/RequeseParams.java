package com.meizu.e;

import java.io.Serializable;

public class RequeseParams implements Serializable, Comparable<RequeseParams> {
    private static final long serialVersionUID = -8708108746980739212L;
    String name;
    String value;

    public /* synthetic */ int compareTo(Object obj) {
        return a((RequeseParams) obj);
    }

    public RequeseParams(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public RequeseParams(String name, int value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public String a() {
        return this.name;
    }

    public String b() {
        return this.value;
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + this.value.hashCode();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RequeseParams)) {
            return false;
        }
        RequeseParams that = (RequeseParams) obj;
        if (!(this.name.equals(that.name) && this.value.equals(that.value))) {
            z = false;
        }
        return z;
    }

    public int a(RequeseParams another) {
        int compared = this.name.compareTo(another.name);
        if (compared == 0) {
            return this.value.compareTo(another.value);
        }
        return compared;
    }
}
