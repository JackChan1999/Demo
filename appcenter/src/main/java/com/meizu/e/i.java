package com.meizu.e;

import java.io.Serializable;
import javax.crypto.spec.SecretKeySpec;

public class i implements Serializable {
    private static final long serialVersionUID = 1;
    private String a;
    private String b;
    private transient SecretKeySpec c;

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof i)) {
            return false;
        }
        i that = (i) o;
        if (this.c == null ? that.c != null : !this.c.equals(that.c)) {
            return false;
        }
        if (!this.a.equals(that.a)) {
            return false;
        }
        if (this.b.equals(that.b)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + (this.c != null ? this.c.hashCode() : 0);
    }

    public String toString() {
        return "OAuthToken{token='" + this.a + '\'' + ", tokenSecret='" + this.b + '\'' + ", secretKeySpec=" + this.c + '}';
    }
}
