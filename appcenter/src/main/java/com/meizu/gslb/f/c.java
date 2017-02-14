package com.meizu.gslb.f;

import android.os.SystemClock;
import android.text.TextUtils;
import com.meizu.gslb.b.i;
import com.meizu.gslb.g.a;
import java.util.HashMap;

public class c {
    private HashMap<String, Long> a;

    c() {
    }

    public synchronized long a(String str) {
        Long l;
        if (this.a == null) {
            this.a = new HashMap();
        }
        l = (Long) this.a.get(str);
        return l == null ? 0 : l.longValue();
    }

    public synchronized void a(String str, long j) {
        if (this.a == null) {
            this.a = new HashMap();
        }
        this.a.put(str, Long.valueOf(j));
    }

    boolean a(i iVar, Exception exception) {
        if (iVar.f()) {
            return true;
        }
        String b = iVar.b();
        if (TextUtils.isEmpty(b)) {
            return false;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long a = a(b);
        if (a == 0 || elapsedRealtime - a >= 60000) {
            a(b, elapsedRealtime);
            return true;
        }
        a.a("skip write usage:" + (elapsedRealtime - a) + "," + (exception != null ? exception.getMessage() : null));
        return false;
    }
}
