package com.meizu.cloud.app.settings;

import android.content.Context;
import android.content.SharedPreferences;
import com.meizu.cloud.app.request.model.BaseServerAppInfo.Columns;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.app.utils.r;
import java.util.ArrayList;
import java.util.List;

public class a {
    private static final String a = a.class.getSimpleName();
    private static a c = null;
    private Context b = null;
    private SharedPreferences d;
    private List<a> e;

    public interface a {
        void a(String str, Object obj);
    }

    private a(Context context) {
        this.b = context.getApplicationContext();
        this.d = context.getSharedPreferences("my_settings", 0);
        this.e = new ArrayList();
    }

    public static a a(Context context) {
        if (c == null) {
            c = new a(context);
        }
        return c;
    }

    public boolean a() {
        SharedPreferences sharedPreferences = this.d;
        String str = "update_notify";
        boolean z = (r.a().booleanValue() || d.g()) ? false : true;
        return sharedPreferences.getBoolean(str, z);
    }

    public void a(boolean bOpen) {
        this.d.edit().putBoolean("update_notify", bOpen).apply();
        if (this.e.size() > 0) {
            for (a onSettingChangeListener : this.e) {
                onSettingChangeListener.a("update_notify", Boolean.valueOf(bOpen));
            }
        }
    }

    public boolean b() {
        SharedPreferences sharedPreferences = this.d;
        String str = "recommend_notify";
        boolean z = (r.a().booleanValue() || d.g()) ? false : true;
        return sharedPreferences.getBoolean(str, z);
    }

    public void b(boolean bOpen) {
        this.d.edit().putBoolean("recommend_notify", bOpen).apply();
    }

    public boolean c() {
        return this.d.getBoolean("auto_downlad", !d.g());
    }

    public void c(boolean bOpen) {
        this.d.edit().putBoolean("auto_downlad", bOpen).apply();
        if (this.e.size() > 0) {
            for (a onSettingChangeListener : this.e) {
                onSettingChangeListener.a("auto_downlad", Boolean.valueOf(bOpen));
            }
        }
    }

    public boolean d() {
        return this.d.getBoolean(Columns.AUTO_INSTALL, true) && c();
    }

    public void d(boolean bOpen) {
        this.d.edit().putBoolean(Columns.AUTO_INSTALL, bOpen).apply();
        if (this.e.size() > 0) {
            for (a onSettingChangeListener : this.e) {
                onSettingChangeListener.a(Columns.AUTO_INSTALL, Boolean.valueOf(bOpen));
            }
        }
    }

    public boolean e() {
        return this.d.getBoolean("delete_apk", true);
    }

    public void e(boolean bOpen) {
        this.d.edit().putBoolean("delete_apk", bOpen).apply();
        if (this.e.size() > 0) {
            for (a onSettingChangeListener : this.e) {
                onSettingChangeListener.a("delete_apk", Boolean.valueOf(bOpen));
            }
        }
    }

    public boolean f() {
        return this.d.getBoolean("filter_app", this.b.getPackageName().equals("com.meizu.mstore"));
    }

    public void f(boolean bOpen) {
        this.d.edit().putBoolean("filter_app", bOpen).apply();
    }

    public boolean g() {
        return this.d.getBoolean("plugin_swicher", this.b.getPackageName().equals("com.meizu.mstore"));
    }

    public void g(boolean bOpen) {
        this.d.edit().putBoolean("plugin_swicher", bOpen).apply();
    }
}
