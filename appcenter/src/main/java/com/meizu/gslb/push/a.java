package com.meizu.gslb.push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.gslb.b.d;
import com.meizu.gslb.b.e;
import org.json.JSONArray;
import org.json.JSONObject;

public class a {
    public static final void a(Context context, Intent intent) {
        com.meizu.gslb.g.a.b("Handle push broadcast.");
        String stringExtra = intent.getStringExtra("gslb");
        if (TextUtils.isEmpty(stringExtra)) {
            com.meizu.gslb.g.a.d("Push broadcast message empty!");
            return;
        }
        String[] a = a(stringExtra);
        if (a != null && a.length > 0) {
            a(a);
        }
    }

    protected static void a(String[] strArr) {
        try {
            e a = d.a();
            for (String b : strArr) {
                a.b(b);
            }
        } catch (IllegalArgumentException e) {
        }
    }

    protected static String[] a(String str) {
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("gslb.invalidate");
            int length = jSONArray.length();
            String[] strArr = new String[length];
            for (int i = 0; i < length; i++) {
                strArr[i] = jSONArray.getJSONObject(i).getString("name");
            }
            return strArr;
        } catch (Exception e) {
            com.meizu.gslb.g.a.c("Cant parse push json:" + str);
            return null;
        }
    }
}
