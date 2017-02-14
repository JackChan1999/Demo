package com.meizu.volley.c;

import com.android.volley.a;
import com.android.volley.p;
import com.android.volley.s;

public class b implements p {
    private boolean a;
    private int b;
    private int c;
    private final int d;
    private final float e;
    private a f;

    public b(a tokenLoader) {
        this(20000, 0, 0.0f, tokenLoader);
    }

    public b(int initialTimeoutMs, int maxNumRetries, float backoffMultiplier, a tokenLoader) {
        this.a = false;
        this.b = initialTimeoutMs;
        this.d = maxNumRetries;
        this.e = backoffMultiplier;
        this.f = tokenLoader;
    }

    public int a() {
        return this.b;
    }

    public int b() {
        return this.c;
    }

    public void a(s error) throws s {
        if (!(error instanceof a)) {
            this.c++;
            this.b = (int) (((float) this.b) + (((float) this.b) * this.e));
            if (!c()) {
                throw error;
            }
        } else if (this.a) {
            throw error;
        } else {
            this.a = true;
            if (!this.f.reLoadToken()) {
                throw error;
            }
        }
    }

    protected boolean c() {
        return this.c <= this.d;
    }
}
