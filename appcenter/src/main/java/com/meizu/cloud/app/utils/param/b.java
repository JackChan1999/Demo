package com.meizu.cloud.app.utils.param;

import android.os.Build.VERSION;
import android.text.TextUtils;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.volley.b.a;
import com.meizu.volley.d;
import java.util.ArrayList;
import java.util.List;

public class b implements d {
    private String a = null;

    protected b() {
        a();
    }

    private void a() {
        if (TextUtils.isEmpty(this.a)) {
            this.a = "4.0";
            try {
                this.a = (String) com.meizu.cloud.c.b.a.b.a("android.content.res.flymetheme.FlymeThemeUtils", "FLYME_THEME_OS");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    public List<a> b() {
        List<a> paramPairs = new ArrayList();
        paramPairs.add(new a(RequestManager.OS, String.valueOf(VERSION.SDK_INT)));
        paramPairs.add(new a(RequestManager.MZOS, this.a));
        paramPairs.add(new a(RequestManager.SCREEN_SIZE, com.meizu.cloud.app.utils.d.c()));
        paramPairs.add(new a(RequestManager.LANGUAGE, com.meizu.cloud.app.utils.d.d()));
        return paramPairs;
    }

    public boolean g() {
        return false;
    }
}
