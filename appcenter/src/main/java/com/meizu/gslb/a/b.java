package com.meizu.gslb.a;

import android.util.Pair;
import com.meizu.gslb.b.d;
import com.meizu.gslb.b.i;
import java.util.ArrayList;
import java.util.List;

public class b {
    private List<Pair<String, String>> a;
    private long b;
    private int[] c;
    private boolean d;
    private boolean e;
    private boolean f;

    private b() {
    }

    public static b a() {
        return new b();
    }

    public b a(long j) {
        this.b = j;
        this.e = true;
        return this;
    }

    public void b() {
        if (this.d) {
            List list = null;
            if (this.a != null && this.a.size() > 0) {
                list = new ArrayList(this.a.size());
                list.addAll(this.a);
            }
            d.a(list);
        }
        if (this.f) {
            i.a(this.c);
        }
        if (this.e) {
            com.meizu.gslb.f.b.a(this.b);
        }
    }
}
