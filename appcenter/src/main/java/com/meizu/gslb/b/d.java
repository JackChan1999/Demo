package com.meizu.gslb.b;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import com.meizu.gslb.f.a;
import com.meizu.gslb.f.b;
import com.meizu.gslb.g.c;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class d implements e {
    private static d a;
    private static List<Pair<String, String>> f;
    private Context b;
    private a c;
    private ArrayList<b> d;
    private List<g> e;

    private d(Context context, com.meizu.gslb.f.d dVar) {
        this.b = context.getApplicationContext();
        this.c = new b(this.b, dVar);
    }

    private synchronized b a(String str, boolean z) {
        b bVar;
        if (!TextUtils.isEmpty(str)) {
            Object obj;
            if (this.d != null && this.d.size() > 0) {
                Iterator it = this.d.iterator();
                while (it.hasNext()) {
                    bVar = (b) it.next();
                    if (bVar.a(str)) {
                        if (z && bVar.a(this.b)) {
                            com.meizu.gslb.e.a.b(this.b, str);
                            this.d.remove(bVar);
                            obj = 1;
                            if (obj == null) {
                                bVar = com.meizu.gslb.e.a.a(this.b, str);
                                if (bVar != null) {
                                    a(bVar);
                                }
                            }
                        }
                    }
                }
            }
            obj = null;
            if (obj == null) {
                bVar = com.meizu.gslb.e.a.a(this.b, str);
                if (bVar != null) {
                    a(bVar);
                }
            }
        }
        bVar = null;
        return bVar;
    }

    public static synchronized e a() throws IllegalArgumentException {
        e eVar;
        synchronized (d.class) {
            if (a == null) {
                throw new IllegalArgumentException("Gslb instance not init!");
            }
            eVar = a;
        }
        return eVar;
    }

    public static synchronized e a(Context context, com.meizu.gslb.f.d dVar) {
        e eVar;
        synchronized (d.class) {
            if (a == null) {
                if (context == null) {
                    throw new IllegalArgumentException("context cant be null!");
                }
                a = new d(context, dVar);
                com.meizu.gslb.g.a.c("init gslb manager: 1.0.0");
            }
            eVar = a;
        }
        return eVar;
    }

    private synchronized void a(b bVar) {
        if (this.d == null) {
            this.d = new ArrayList();
        }
        this.d.add(bVar);
    }

    private void a(String str, String str2, i.a aVar) {
        b a = a(str, false);
        if (a != null) {
            a.a(str2, aVar);
        }
    }

    public static synchronized void a(List<Pair<String, String>> list) {
        synchronized (d.class) {
            try {
                if ((!com.meizu.gslb.a.a.a(f, list) ? 1 : null) != null) {
                    com.meizu.gslb.g.a.c("custom params change!");
                    ((d) a()).e();
                } else {
                    com.meizu.gslb.g.a.c("custom params not change!");
                }
            } catch (Exception e) {
                com.meizu.gslb.g.a.d("change custom params exception or no init!");
            }
            f = list;
        }
    }

    public static synchronized a b() throws IllegalArgumentException {
        a aVar;
        synchronized (d.class) {
            if (a == null) {
                throw new IllegalArgumentException("Gslb instance not init!");
            }
            aVar = a.c;
        }
        return aVar;
    }

    private b c(String str) {
        b a = a(str, true);
        if (a != null) {
            return a;
        }
        if (!c.b(this.b)) {
            com.meizu.gslb.g.a.c("Domain load while no network: " + str);
            return null;
        } else if (g(str)) {
            f(str);
            a = new com.meizu.gslb.e.b().a(this.b, str, f);
            if (a == null) {
                return null;
            }
            a(a);
            return a;
        } else {
            com.meizu.gslb.g.a.c("Domain load too frequently: " + str);
            return null;
        }
    }

    private synchronized boolean d(String str) {
        boolean z;
        if (this.d != null) {
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                b bVar = (b) it.next();
                if (bVar.a(str)) {
                    this.d.remove(bVar);
                    z = true;
                    break;
                }
            }
        }
        z = false;
        return z;
    }

    private synchronized void e() {
        com.meizu.gslb.g.a.c("clear domain info!");
        if (this.d != null && this.d.size() > 0) {
            this.d.clear();
        }
        if (this.e != null && this.e.size() > 0) {
            this.e.clear();
        }
        com.meizu.gslb.e.a.a(this.b);
    }

    private synchronized boolean e(String str) {
        boolean z;
        if (this.e != null && this.e.size() > 0) {
            for (g gVar : this.e) {
                if (gVar.a(str)) {
                    this.e.remove(gVar);
                    z = true;
                    break;
                }
            }
        }
        z = false;
        return z;
    }

    private synchronized void f(String str) {
        if (this.e == null) {
            this.e = new ArrayList();
        }
        for (g gVar : this.e) {
            if (gVar.a(str)) {
                gVar.a(this.b);
                break;
            }
        }
        this.e.add(new g(this.b, str));
    }

    private synchronized boolean g(String str) {
        boolean b;
        if (this.e != null && this.e.size() > 0) {
            for (g gVar : this.e) {
                if (gVar.a(str)) {
                    b = gVar.b(this.b);
                    break;
                }
            }
        }
        b = true;
        return b;
    }

    public a a(String str) {
        c cVar = new c(str);
        String a = cVar.a();
        if (TextUtils.isEmpty(a)) {
            com.meizu.gslb.g.a.d("Illegal convert url:" + str);
        } else if (com.meizu.gslb.g.b.a(a)) {
            return new a(str);
        } else {
            b c = c(a);
            if (c != null) {
                return new a(str, a, cVar.a(c), c.b());
            }
            com.meizu.gslb.g.a.a("Cant find ip for domain:" + a);
        }
        return new a(str);
    }

    public boolean a(i iVar) {
        if (!iVar.f()) {
            return false;
        }
        a(iVar.b(), iVar.c(), iVar.e());
        return iVar.g();
    }

    public void b(String str) {
        com.meizu.gslb.g.a.c("Clear domain data:" + str);
        com.meizu.gslb.e.a.b(this.b, str);
        d(str);
        e(str);
    }

    public String c() {
        return this.b.getPackageName();
    }

    public boolean d() {
        return c.b(this.b);
    }
}
