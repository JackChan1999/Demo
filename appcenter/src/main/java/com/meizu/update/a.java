package com.meizu.update;

import android.content.Context;
import com.meizu.update.h.b;
import java.lang.ref.WeakReference;
import java.util.LinkedList;

public class a {
    private static LinkedList<WeakReference<Context>> a;

    public static final synchronized void a(Context tracker) {
        synchronized (a.class) {
            b();
            if (c(tracker) == -1) {
                a.add(new WeakReference(tracker));
                b.b("add tracker : " + tracker);
            } else {
                b.b("duplicate tracker : " + tracker);
            }
        }
    }

    public static final synchronized void b(Context tracker) {
        synchronized (a.class) {
            int index = c(tracker);
            if (index != -1) {
                a.remove(index);
                b.b("rm tracker : " + tracker);
            } else {
                b.b("cant find tracker : " + tracker);
            }
            c();
        }
    }

    public static synchronized Context a() {
        Context tracker;
        synchronized (a.class) {
            if (a != null && a.size() > 0) {
                for (int i = a.size() - 1; i >= 0; i--) {
                    tracker = (Context) ((WeakReference) a.get(i)).get();
                    if (tracker != null) {
                        break;
                    }
                }
            }
            tracker = null;
        }
        return tracker;
    }

    private static void b() {
        if (a == null) {
            b.b("init com list");
            a = new LinkedList();
        }
    }

    private static void c() {
        if (a != null) {
            for (int i = a.size() - 1; i >= 0; i--) {
                if (((Context) ((WeakReference) a.get(i)).get()) == null) {
                    b.b("discard no reference list index:" + i);
                    a.remove(i);
                }
            }
            if (a.size() == 0) {
                b.b("discard com list");
                a = null;
            }
        }
    }

    private static int c(Context tracker) {
        if (a != null && a.size() > 0) {
            for (int i = a.size() - 1; i >= 0; i--) {
                if (((Context) ((WeakReference) a.get(i)).get()) == tracker) {
                    return i;
                }
            }
        }
        return -1;
    }
}
