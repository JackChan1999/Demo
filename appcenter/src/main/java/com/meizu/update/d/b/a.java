package com.meizu.update.d.b;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import com.meizu.update.h.b;
import com.meizu.update.h.f;
import com.meizu.update.h.g;
import java.util.ArrayList;
import java.util.List;

public class a {
    private static b a;

    public b a(Context context) {
        if (a != null && !a.a(context)) {
            return a;
        }
        a = new a().b(context);
        return a;
    }

    public static void a() {
        a = null;
    }

    private b b(Context context) {
        try {
            String simType = c(context);
            List<Pair<String, String>> params = new ArrayList();
            params.add(new Pair("sim_card_sp", simType));
            params.add(new Pair("rule_id", "15"));
            String res = a("http://servicecut.meizu.com/interface/locate", params);
            if (TextUtils.isEmpty(res)) {
                b.d("Proxy response is null!");
                return null;
            }
            b.a("Proxy info: " + res);
            return new b(res, context);
        } catch (Exception ignore) {
            b.d("Load proxy exception!");
            ignore.printStackTrace();
        }
    }

    private String c(Context context) {
        if (g.j(context)) {
            return "wifi";
        }
        return g.k(context);
    }

    protected String a(String url, List<Pair<String, String>> params) {
        return f.a(url, params);
    }
}
