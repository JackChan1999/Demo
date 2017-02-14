package com.meizu.statsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.meizu.statsapp.util.Utils;
import defpackage.aoa;
import defpackage.aog;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class UsageStatsProxy {
    private static volatile UsageStatsProxy a;
    private static Object b = new Object();
    private Context c;
    private String d;
    private boolean e;
    private aoa f;
    private SharedPreferences g;
    private String h;

    public static class Event implements Parcelable {
        public static final Creator<Event> CREATOR = new aog();
        private int a;
        private String b;
        private int c;
        private long d;
        private String e;
        private String f;
        private String g;
        private Object h;
        private String i;
        private long j;
        private String k;
        private String l;

        public Event(String str, int i, long j, String str2, String str3, String str4, Object obj, String str5, String str6) {
            this.b = str;
            this.c = i;
            this.d = j;
            this.e = str2;
            this.f = str3;
            this.g = str4;
            this.h = obj;
            this.k = str5;
            this.l = str6;
        }

        public String a() {
            return this.b;
        }

        public void a(String str) {
            this.b = str;
        }

        public int b() {
            return this.c;
        }

        public void a(int i) {
            this.c = i;
        }

        public long c() {
            return this.d;
        }

        public void a(long j) {
            this.d = j;
        }

        public String d() {
            return this.e;
        }

        public void b(String str) {
            this.e = str;
        }

        public String e() {
            return this.f;
        }

        public void c(String str) {
            this.f = str;
        }

        public String f() {
            return this.g;
        }

        public void d(String str) {
            this.g = str;
        }

        public Object g() {
            return this.h;
        }

        public String h() {
            if (this.h == null) {
                return "";
            }
            String str = "";
            try {
                if (!(this.h instanceof Map) || ((Map) this.h).size() <= 0) {
                    return this.h.toString();
                }
                JSONStringer jSONStringer = new JSONStringer();
                jSONStringer.object();
                for (Entry entry : ((Map) this.h).entrySet()) {
                    jSONStringer.key((String) entry.getKey()).value(entry.getValue());
                }
                jSONStringer.endObject();
                return jSONStringer.toString();
            } catch (JSONException e) {
                e.printStackTrace();
                return str;
            } catch (Exception e2) {
                e2.printStackTrace();
                return str;
            }
        }

        public void a(Object obj) {
            this.h = obj;
        }

        public void b(int i) {
            this.a = i;
        }

        public int i() {
            return this.a;
        }

        public void e(String str) {
            this.i = str;
        }

        public String j() {
            return this.i;
        }

        public void b(long j) {
            this.j = j;
        }

        public long k() {
            return this.j;
        }

        public void f(String str) {
            this.k = str;
        }

        public String l() {
            return this.k;
        }

        public void g(String str) {
            this.l = str;
        }

        public String m() {
            return this.l;
        }

        public String toString() {
            return "{name=" + this.b + ", type=" + this.c + ", package=" + this.f + ", page=" + this.g + ", properties=" + this.h + ", packageName=" + this.f + ", packageVersion=" + this.l + "}";
        }

        public Event(Parcel parcel) {
            if (parcel != null) {
                this.a = parcel.readInt();
                this.b = parcel.readString();
                this.c = parcel.readInt();
                this.d = parcel.readLong();
                this.e = parcel.readString();
                this.f = parcel.readString();
                this.g = parcel.readString();
                String readString = parcel.readString();
                if (!Utils.isEmpty(readString)) {
                    try {
                        this.h = new JSONObject(readString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                this.i = parcel.readString();
                this.j = parcel.readLong();
                this.k = parcel.readString();
                this.l = parcel.readString();
            }
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            if (parcel != null) {
                parcel.writeInt(this.a);
                parcel.writeString(this.b);
                parcel.writeInt(this.c);
                parcel.writeLong(this.d);
                parcel.writeString(this.e == null ? "" : this.e);
                parcel.writeString(this.f == null ? "" : this.f);
                parcel.writeString(this.g == null ? "" : this.g);
                parcel.writeString(this.h == null ? "" : h());
                parcel.writeString(this.i);
                parcel.writeLong(this.j);
                parcel.writeString(this.k);
                parcel.writeString(this.l);
            }
        }
    }

    private UsageStatsProxy(Context context, boolean z, boolean z2) {
        if (context == null) {
            throw new IllegalArgumentException("The context is null.");
        } else if (VERSION.SDK_INT > 15) {
            try {
                this.c = context.getApplicationContext();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.c == null) {
                this.c = context;
            }
            this.g = this.c.getSharedPreferences("com.meizu.stats", 0);
            this.d = this.c.getPackageName();
            if ("android".equals(this.d)) {
                throw new IllegalArgumentException("This sdk cannot be used in system process.");
            }
            this.e = z;
            this.f = new aoa(this.c, this.e, z2);
            this.h = Utils.getPackageVersion(this.d, this.c);
        }
    }

    public static UsageStatsProxy a(Context context, boolean z) {
        if (a == null) {
            synchronized (b) {
                if (a == null) {
                    a = new UsageStatsProxy(context, z, true);
                }
            }
        }
        return a;
    }

    private static UsageStatsProxy a(Context context, boolean z, boolean z2) {
        if (a == null) {
            synchronized (b) {
                if (a == null) {
                    a = new UsageStatsProxy(context, z, z2);
                }
            }
        }
        return a;
    }

    public static UsageStatsProxy a(Context context) {
        return a(context, false, true);
    }

    public void a(String str) {
        if (this.f != null) {
            if (this.e || this.f.a()) {
                this.f.a(this.d, true, str, System.currentTimeMillis());
            }
        }
    }

    public void b(String str) {
        if (this.f != null) {
            if (this.e || this.f.a()) {
                this.f.a(this.d, false, str, System.currentTimeMillis());
            }
        }
    }

    public void a(String str, String str2, String str3) {
        if (this.f != null) {
            if ((this.e || this.f.a()) && !c(str)) {
                Object obj;
                if (c(str3)) {
                    obj = null;
                } else {
                    obj = new HashMap();
                    obj.put("value", str3);
                }
                this.f.a(new Event(str, 1, System.currentTimeMillis(), null, this.d, str2, obj, Build.DISPLAY, this.h));
            }
        }
    }

    public void a(String str, String str2, Map<String, String> map) {
        if (this.f != null) {
            if ((this.e || this.f.a()) && !c(str)) {
                this.f.a(new Event(str, 1, System.currentTimeMillis(), null, this.d, str2, map, Build.DISPLAY, this.h));
            }
        }
    }

    public void a(String str, Map<String, String> map) {
        if (this.f != null) {
            if ((this.e || this.f.a()) && !c(str)) {
                this.f.a(new Event(str, 3, System.currentTimeMillis(), null, this.d, null, map, Build.DISPLAY, this.h));
            }
        }
    }

    private static boolean c(String str) {
        if (str == null || str.length() < 1) {
            return true;
        }
        return false;
    }
}
