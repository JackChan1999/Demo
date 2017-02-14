package com.meizu.gslb.e;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import com.meizu.gslb.g.a;
import com.meizu.gslb.g.c;
import com.meizu.gslb.g.f;
import java.util.ArrayList;
import java.util.List;

public class b {
    private String a(Context context) {
        return c.a(context) ? "wifi" : f.a(context);
    }

    public com.meizu.gslb.b.b a(Context context, String str, List<Pair<String, String>> list) {
        a.a("Load ips for domain:" + str);
        try {
            String a = a(context);
            List arrayList = new ArrayList();
            arrayList.add(new Pair("version", "2.0"));
            arrayList.add(new Pair("sim_card_sp", a));
            arrayList.add(new Pair("name", str));
            if (list != null && list.size() > 0) {
                for (Pair add : list) {
                    arrayList.add(add);
                }
            }
            com.meizu.gslb.c.a.a a2 = com.meizu.gslb.c.a.a("https://servicecut.meizu.com/interface/locate", arrayList, new String[]{"secrete"});
            if (a2 == null || TextUtils.isEmpty(a2.a)) {
                a.d("Proxy response is null!");
                return null;
            }
            String str2 = a2.b != null ? (String) a2.b.get("secrete") : null;
            a.a("Proxy info: " + a2.a);
            com.meizu.gslb.b.b bVar = new com.meizu.gslb.b.b(context, a2.a, str, str2);
            a.a(context, str, bVar.c(), a2.a, str2);
            return bVar;
        } catch (Exception e) {
            a.d("Load proxy exception!");
            e.printStackTrace();
        }
    }
}
