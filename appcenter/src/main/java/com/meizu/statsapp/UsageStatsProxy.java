package com.meizu.statsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.meizu.statsapp.util.Utils;
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
    private c f;
    private SharedPreferences g;
    private String h;

    static class Event implements Parcelable {
        public static final Creator<Event> CREATOR = new Creator<Event>() {
            public /* synthetic */ Object createFromParcel(Parcel x0) {
                return a(x0);
            }

            public /* synthetic */ Object[] newArray(int x0) {
                return a(x0);
            }

            public Event a(Parcel source) {
                return new Event(source);
            }

            public Event[] a(int size) {
                return new Event[size];
            }
        };
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

        public Event(String name, int type, long time, String sessionid, String packageName, String page, Object properties, String flymeVersion, String packageVersion) {
            this.b = name;
            this.c = type;
            this.d = time;
            this.e = sessionid;
            this.f = packageName;
            this.g = page;
            this.h = properties;
            this.k = flymeVersion;
            this.l = packageVersion;
        }

        public String a() {
            return this.b;
        }

        public void a(String name) {
            this.b = name;
        }

        public int b() {
            return this.c;
        }

        public void a(int type) {
            this.c = type;
        }

        public long c() {
            return this.d;
        }

        public void a(long time) {
            this.d = time;
        }

        public String d() {
            return this.e;
        }

        public void b(String sessionid) {
            this.e = sessionid;
        }

        public String e() {
            return this.f;
        }

        public void c(String packageName) {
            this.f = packageName;
        }

        public String f() {
            return this.g;
        }

        public void d(String page) {
            this.g = page;
        }

        public Object g() {
            return this.h;
        }

        public String h() {
            if (this.h == null) {
                return "";
            }
            String result = "";
            try {
                if (!(this.h instanceof Map) || ((Map) this.h).size() <= 0) {
                    return this.h.toString();
                }
                JSONStringer stringer = new JSONStringer();
                stringer.object();
                for (Entry<String, String> entry : ((Map) this.h).entrySet()) {
                    stringer.key((String) entry.getKey()).value(entry.getValue());
                }
                stringer.endObject();
                return stringer.toString();
            } catch (JSONException e) {
                e.printStackTrace();
                return result;
            } catch (Exception e2) {
                e2.printStackTrace();
                return result;
            }
        }

        public void a(Object properties) {
            this.h = properties;
        }

        public void b(int id) {
            this.a = id;
        }

        public int i() {
            return this.a;
        }

        public void e(String network) {
            this.i = network;
        }

        public String j() {
            return this.i;
        }

        public void b(long channelNum) {
            this.j = channelNum;
        }

        public long k() {
            return this.j;
        }

        public void f(String flymeVersion) {
            this.k = flymeVersion;
        }

        public String l() {
            return this.k;
        }

        public void g(String packageVersion) {
            this.l = packageVersion;
        }

        public String m() {
            return this.l;
        }

        public String toString() {
            return "{name=" + this.b + ", type=" + this.c + ", package=" + this.f + ", page=" + this.g + ", properties=" + this.h + ", packageName=" + this.f + ", packageVersion=" + this.l + "}";
        }

        public Event(Parcel source) {
            if (source != null) {
                this.a = source.readInt();
                this.b = source.readString();
                this.c = source.readInt();
                this.d = source.readLong();
                this.e = source.readString();
                this.f = source.readString();
                this.g = source.readString();
                String propertiesStr = source.readString();
                if (!Utils.isEmpty(propertiesStr)) {
                    try {
                        this.h = new JSONObject(propertiesStr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                this.i = source.readString();
                this.j = source.readLong();
                this.k = source.readString();
                this.l = source.readString();
            }
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            if (dest != null) {
                dest.writeInt(this.a);
                dest.writeString(this.b);
                dest.writeInt(this.c);
                dest.writeLong(this.d);
                dest.writeString(this.e == null ? "" : this.e);
                dest.writeString(this.f == null ? "" : this.f);
                dest.writeString(this.g == null ? "" : this.g);
                dest.writeString(this.h == null ? "" : h());
                dest.writeString(this.i);
                dest.writeLong(this.j);
                dest.writeString(this.k);
                dest.writeString(this.l);
            }
        }
    }

    private UsageStatsProxy(Context context, boolean online, boolean upload) {
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
            this.e = online;
            this.f = new c(this.c, this.e, upload);
            this.h = Utils.getPackageVersion(this.d, this.c);
        }
    }

    public static UsageStatsProxy a(Context context, boolean online) {
        if (a == null) {
            synchronized (b) {
                if (a == null) {
                    a = new UsageStatsProxy(context, online, true);
                }
            }
        }
        return a;
    }

    public void a(String pageName) {
        if (this.f != null) {
            if (this.e || this.f.a()) {
                this.f.a(this.d, true, pageName, System.currentTimeMillis());
            }
        }
    }

    public void b(String pageName) {
        if (this.f != null) {
            if (this.e || this.f.a()) {
                this.f.a(this.d, false, pageName, System.currentTimeMillis());
            }
        }
    }

    public void a(String eventName, String pageName, Map<String, String> properties) {
        if (this.f != null) {
            if ((this.e || this.f.a()) && !c(eventName)) {
                this.f.onEvent(new Event(eventName, 1, System.currentTimeMillis(), null, this.d, pageName, properties, Build.DISPLAY, this.h));
            }
        }
    }

    public void a(String logName, Map<String, String> properties) {
        if (this.f != null) {
            if ((this.e || this.f.a()) && !c(logName)) {
                this.f.onEvent(new Event(logName, 3, System.currentTimeMillis(), null, this.d, null, properties, Build.DISPLAY, this.h));
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
