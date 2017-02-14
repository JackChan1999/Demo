package com.meizu.statsapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.statsapp.a.a.d;
import com.meizu.statsapp.util.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class e {
    private static volatile e a;
    private static Object b = new Object();
    private Context c;
    private boolean d;
    private volatile boolean e;
    private d f;
    private HandlerThread g = new HandlerThread("StatsUploadThread");
    private b h;
    private SharedPreferences i;
    private Map<String, Map<String, List<Event>>> j = new HashMap();
    private int k;
    private UsageStatsReceiver l;
    private boolean m;
    private int n;
    private long o;
    private long p;
    private boolean q = false;
    private volatile boolean r = false;
    private int s = 0;
    private long t = 0;
    private boolean u = false;

    private class a extends BroadcastReceiver {
        final /* synthetic */ e a;

        private a(e eVar) {
            this.a = eVar;
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.ACTION_POWER_CONNECTED".equals(intent.getAction())) {
                if (!this.a.r) {
                    this.a.r = true;
                }
                this.a.h.removeMessages(5);
                this.a.h.sendEmptyMessage(5);
                UsageStatusLog.d("UsageStatsUploader", "ACTION_POWER_CONNECTED, mPowerConnecting=" + this.a.r);
            } else if ("android.intent.action.ACTION_POWER_DISCONNECTED".equals(intent.getAction())) {
                if (this.a.r) {
                    this.a.r = false;
                }
                UsageStatusLog.d("UsageStatsUploader", "ACTION_POWER_DISCONNECTED, mPowerConnecting=" + this.a.r);
            } else if ("android.intent.action.TIME_SET".equals(intent.getAction())) {
                this.a.h.sendEmptyMessage(4);
            }
        }
    }

    private class b extends Handler {
        final /* synthetic */ e a;

        public b(e eVar, Looper looper) {
            this.a = eVar;
            super(looper);
        }

        public void handleMessage(Message msg) {
            try {
                long now;
                switch (msg.what) {
                    case 1:
                        this.a.j();
                        try {
                            this.a.h.removeMessages(1);
                            if (this.a.f.a() > 0) {
                                this.a.f.b();
                                boolean uploaded = false;
                                if (Utils.isWiFiWorking(this.a.c)) {
                                    UsageStatusLog.d("UsageStatsUploader", "upload by wifi");
                                    if (this.a.a(this.a.e(), true) && null == null) {
                                        uploaded = true;
                                    }
                                } else if (!Utils.isWiFiWorking(this.a.c) && Utils.isNetworkWorking(this.a.c)) {
                                    UsageStatusLog.d("UsageStatsUploader", "upload by mobile");
                                    if (this.a.d) {
                                        if (this.a.a(this.a.e(), false) && null == null) {
                                            uploaded = true;
                                        }
                                    } else {
                                        if (this.a.a(this.a.f(), false) && null == null) {
                                            uploaded = true;
                                            this.a.c(false);
                                        }
                                    }
                                }
                                if (uploaded && !this.a.d) {
                                    now = System.currentTimeMillis();
                                    this.a.b(now);
                                    this.a.a(now);
                                }
                                if (this.a.d && this.a.u) {
                                    if (!uploaded) {
                                        this.a.s = this.a.s * 2;
                                        this.a.i.edit().putInt("ratio", this.a.s).apply();
                                    }
                                    this.a.k = 0;
                                    this.a.u = false;
                                }
                            }
                            this.a.j.clear();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (this.a.d) {
                            this.a.h.sendEmptyMessageDelayed(1, 1800000);
                            return;
                        }
                        return;
                    case 2:
                        UsageStatusLog.d("UsageStatsUploader", "UPLOAD_TIME_ALARM");
                        this.a.b();
                        this.a.a(System.currentTimeMillis());
                        return;
                    case 3:
                        if (this.a.i()) {
                            if (!this.a.m) {
                                this.a.m = true;
                                this.a.b();
                            }
                        } else if (this.a.m) {
                            this.a.m = false;
                        }
                        UsageStatusLog.d("UsageStatsUploader", "NETWORK_STATE_CHANGED, mNetworkConnected=" + this.a.m);
                        return;
                    case 4:
                        now = System.currentTimeMillis();
                        if (now < this.a.p) {
                            this.a.b();
                            this.a.a(now);
                            return;
                        }
                        return;
                    case 5:
                        this.a.b();
                        return;
                    default:
                        return;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            e2.printStackTrace();
        }
    }

    private e(Context context, boolean online, boolean upload) {
        IntentFilter filter;
        this.c = context;
        this.d = online;
        this.e = upload;
        this.n = this.d ? b.c : b.a;
        this.f = d.a(this.c, this.d);
        this.i = this.c.getSharedPreferences("com.meizu.stats", 0);
        this.o = this.i.getLong("last_upload_time", 0);
        this.q = this.i.getBoolean("today_upload_flag", false);
        this.s = this.i.getInt("ratio", 1);
        this.t = this.i.getLong("online_flow_sum", 0);
        if (!this.d) {
            UsageStatusLog.initLog();
        }
        this.g.start();
        this.h = new b(this, this.g.getLooper());
        if (this.d) {
            this.l = new UsageStatsReceiver();
            filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            try {
                this.c.unregisterReceiver(this.l);
            } catch (Exception e) {
            }
            try {
                this.c.registerReceiver(this.l, filter);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.h.post(new Runnable(this) {
            final /* synthetic */ e a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.f.b();
            }
        });
        if (!this.d) {
            try {
                IntentFilter batteryfilter = new IntentFilter();
                batteryfilter.addAction("android.intent.action.BATTERY_CHANGED");
                Intent result = this.c.registerReceiver(null, batteryfilter);
                if (!(result == null || result.getIntExtra("plugged", 0) == 0)) {
                    this.r = true;
                }
                filter = new IntentFilter();
                filter.addAction("android.intent.action.TIME_SET");
                filter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
                filter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
                this.c.registerReceiver(new a(), filter);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            a(System.currentTimeMillis());
            b();
        }
    }

    public static e a(Context context, boolean online, boolean upload) {
        if (a == null) {
            synchronized (b) {
                if (a == null) {
                    a = new e(context, online, upload);
                }
            }
        }
        return a;
    }

    private JSONObject e() {
        Exception e;
        Throwable th;
        String flymeVersion = null;
        Cursor cursor = this.f.a(this.n);
        if (cursor == null) {
            return null;
        }
        Map<String, List<Event>> sessions;
        List<Event> eventList;
        UsageStatusLog.d("UsageStatsUploader", "parcelEvents, count=" + cursor.getCount());
        this.j.clear();
        List<Event> eventList2 = null;
        Map<String, List<Event>> sessions2 = null;
        while (cursor.moveToNext()) {
            try {
                Event event = d.a(cursor);
                if (event != null) {
                    if (flymeVersion == null) {
                        flymeVersion = event.l();
                    }
                    String packageName = event.e();
                    String packageVersion = TextUtils.isEmpty(event.m()) ? Utils.getPackageVersion(packageName, this.c) : event.m();
                    String sessionid = event.d();
                    packageName = packageName + "*" + packageVersion;
                    if (this.j.containsKey(packageName)) {
                        sessions = (Map) this.j.get(packageName);
                        if (sessions.containsKey(sessionid)) {
                            eventList = (List) sessions.get(sessionid);
                            eventList.add(event);
                            eventList2 = eventList;
                            sessions2 = sessions;
                        } else {
                            eventList = new ArrayList();
                            eventList.add(event);
                            sessions.put(sessionid, eventList);
                            eventList2 = eventList;
                            sessions2 = sessions;
                        }
                    } else {
                        sessions = new HashMap();
                        try {
                            eventList = new ArrayList();
                        } catch (Exception e2) {
                            e = e2;
                            eventList = eventList2;
                        } catch (Throwable th2) {
                            th = th2;
                            eventList = eventList2;
                        }
                        try {
                            eventList.add(event);
                            sessions.put(sessionid, eventList);
                            this.j.put(packageName, sessions);
                            eventList2 = eventList;
                            sessions2 = sessions;
                        } catch (Exception e3) {
                            e = e3;
                        }
                    }
                }
            } catch (Exception e4) {
                e = e4;
                eventList = eventList2;
                sessions = sessions2;
            } catch (Throwable th3) {
                th = th3;
                eventList = eventList2;
                sessions = sessions2;
            }
        }
        cursor.close();
        eventList = eventList2;
        sessions = sessions2;
        return a(flymeVersion);
        try {
            e.printStackTrace();
            cursor.close();
            return a(flymeVersion);
        } catch (Throwable th4) {
            th = th4;
            cursor.close();
            throw th;
        }
    }

    private JSONObject f() {
        Exception e;
        Throwable th;
        String flymeVersion = null;
        Cursor cursor = this.f.a(this.n, System.currentTimeMillis() - 86400000);
        if (cursor == null) {
            return null;
        }
        Map<String, List<Event>> sessions;
        List<Event> eventList;
        UsageStatusLog.d("UsageStatsUploader", "parcelSimpleEvents, count=" + cursor.getCount());
        this.j.clear();
        List<Event> eventList2 = null;
        Map<String, List<Event>> sessions2 = null;
        while (cursor.moveToNext() && this.j.size() < b.b) {
            try {
                Event event = d.a(cursor);
                if (event != null) {
                    if (flymeVersion == null) {
                        flymeVersion = event.l();
                    }
                    String packageName = event.e();
                    String sessionid = event.d();
                    if (this.j.containsKey(packageName)) {
                        sessions = (Map) this.j.get(packageName);
                        if (sessions.containsKey(sessionid)) {
                            eventList = (List) sessions.get(sessionid);
                            eventList.add(event);
                            eventList2 = eventList;
                            sessions2 = sessions;
                        } else {
                            sessions2 = sessions;
                        }
                    } else {
                        sessions = new HashMap();
                        try {
                            eventList = new ArrayList();
                        } catch (Exception e2) {
                            e = e2;
                            eventList = eventList2;
                        } catch (Throwable th2) {
                            th = th2;
                            eventList = eventList2;
                        }
                        try {
                            eventList.add(event);
                            sessions.put(sessionid, eventList);
                            this.j.put(packageName, sessions);
                            eventList2 = eventList;
                            sessions2 = sessions;
                        } catch (Exception e3) {
                            e = e3;
                        }
                    }
                }
            } catch (Exception e4) {
                e = e4;
                eventList = eventList2;
                sessions = sessions2;
            } catch (Throwable th3) {
                th = th3;
                eventList = eventList2;
                sessions = sessions2;
            }
        }
        cursor.close();
        eventList = eventList2;
        sessions = sessions2;
        JSONObject data = a(flymeVersion);
        while (data != null && ((long) data.toString().getBytes().length) > 10240) {
            UsageStatusLog.d("UsageStatsUploader", "data.size=" + data.toString().getBytes().length);
            Iterator<String> it = this.j.keySet().iterator();
            if (it == null) {
                this.j.remove(it.next());
                data = a(flymeVersion);
            } else {
                data = null;
            }
        }
        return data;
        try {
            e.printStackTrace();
            cursor.close();
            JSONObject data2 = a(flymeVersion);
            while (data2 != null) {
                UsageStatusLog.d("UsageStatsUploader", "data.size=" + data2.toString().getBytes().length);
                Iterator<String> it2 = this.j.keySet().iterator();
                if (it2 == null) {
                    data2 = null;
                } else {
                    this.j.remove(it2.next());
                    data2 = a(flymeVersion);
                }
            }
            return data2;
        } catch (Throwable th4) {
            th = th4;
            cursor.close();
            throw th;
        }
    }

    private JSONObject a(String flymeVersion) {
        if (this.j.size() < 1) {
            return null;
        }
        JSONObject uploadData = new JSONObject();
        try {
            uploadData.put("ver", "2.0");
            a(uploadData, flymeVersion);
            JSONArray apps = g();
            if (apps == null) {
                return null;
            }
            uploadData.put("apps", apps);
            return uploadData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void a(JSONObject data) throws JSONException {
        data.put(RequestManager.IMEI, Utils.getIMEI(this.c));
        data.put("mac", Utils.getMACAddress(this.c));
        data.put(RequestManager.SN, Utils.getSN());
        data.put("version", "2.0");
        data.put("nonce", Utils.bytesToHexString(Utils.getMD5((Utils.getIMEI(this.c) + "U@M##I$$$D").getBytes())));
    }

    private void a(JSONObject data, String flymeVersion) throws JSONException {
        data.put("device", Build.MODEL);
        data.put("os_version", VERSION.RELEASE);
        data.put(RequestManager.IMEI, Utils.getIMEI(this.c));
        data.put("country", Utils.getCountry(this.c));
        data.put("operator", Utils.getOperater(this.c));
        data.put("root", Utils.isRoot(this.c));
        data.put(RequestManager.SN, Utils.getSN());
        data.put("flyme_uid", Utils.getFlymeUid(this.c));
        String str = "flyme_ver";
        if (TextUtils.isEmpty(flymeVersion)) {
            flymeVersion = Build.DISPLAY;
        }
        data.put(str, flymeVersion);
        data.put("mac_address", Utils.getMACAddress(this.c));
        data.put("product_model", Utils.getProductModel());
        data.put("build_mask", Utils.getBuildMask());
        data.put("sre", Utils.getSre(this.c));
        data.put("lla", Utils.getLocationLanguage(this.c));
        data.put("umid", UsageStatsManagerServer.a);
        data.put("ter_type", Utils.getDeviceType());
        data.put("os_type", Utils.getOSType());
        data.put("brand", Utils.getBrand());
    }

    private JSONArray g() throws JSONException {
        JSONArray apps = new JSONArray();
        for (String packageName : this.j.keySet()) {
            String version;
            JSONObject app = new JSONObject();
            int splitIndex = packageName.indexOf("*");
            String name = -1 == splitIndex ? packageName : packageName.substring(0, splitIndex);
            app.put("package", name);
            if ("com.meizu.uxip.log".equals(packageName)) {
                version = "1.0";
            } else {
                version = -1 == splitIndex ? Utils.getPackageVersion(name, this.c) : packageName.substring(splitIndex + 1);
            }
            app.put("sdk_ver", "2.5");
            app.put("version", version);
            JSONArray sessions = b(packageName);
            if (sessions != null) {
                app.put("sessions", sessions);
                apps.put(app);
            }
        }
        return apps.length() > 0 ? apps : null;
    }

    private JSONArray b(String packageName) throws JSONException {
        long channel = 0;
        Map<String, List<Event>> sessionMap = (Map) this.j.get(packageName);
        if (sessionMap == null || sessionMap.size() < 1) {
            return null;
        }
        JSONArray sessions = new JSONArray();
        for (Entry<String, List<Event>> sessionMapEntry : sessionMap.entrySet()) {
            List<Event> eventList = (List) sessionMapEntry.getValue();
            if (eventList != null && eventList.size() >= 1) {
                JSONObject session = new JSONObject();
                session.put("sid", sessionMapEntry.getKey());
                JSONArray events = new JSONArray();
                for (Event event : eventList) {
                    if (0 == channel) {
                        channel = event.k();
                    }
                    JSONObject eventJson = new JSONObject();
                    eventJson.put("network", event.j());
                    eventJson.put("type", a(event.b()));
                    eventJson.put("name", event.a());
                    if (2 != event.b()) {
                        eventJson.put(RequestManager.TIME, event.c());
                        eventJson.put("page", event.f());
                        eventJson.put("value", event.g());
                    } else if (event.g() != null) {
                        try {
                            eventJson.put("launch", ((JSONObject) event.g()).get("start_time"));
                            eventJson.put("terminate", ((JSONObject) event.g()).get("stop_time"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    events.put(eventJson);
                }
                session.put("channel_id", channel);
                session.put("events", events);
                sessions.put(session);
            }
        }
        return sessions.length() <= 0 ? null : sessions;
    }

    private String a(int type) {
        switch (type) {
            case 1:
                return "action_x";
            case 2:
                return "page";
            case 3:
                return "log";
            default:
                return "";
        }
    }

    void a() {
        this.k++;
        UsageStatusLog.d("UsageStatsUploader", "eventComein, mEventCome=" + this.k + " mRatio=" + this.s);
        if (this.k == b.f * this.s) {
            this.u = true;
            b();
        }
    }

    private com.meizu.statsapp.a.a.b[] a(byte[] pushData) {
        String md5 = Utils.bytesToHexString(Utils.getMD5(pushData));
        int tsValue = (int) (System.currentTimeMillis() / 1000);
        String sign = Utils.bytesToHexString(Utils.getMD5(String.format("key=OjUiuYe80AUYnbgBNT6&nonce=%s&ts=%s&md5=%s", new Object[]{String.valueOf(new Random().nextInt() + tsValue), String.valueOf(tsValue), md5}).getBytes()));
        com.meizu.statsapp.a.a.b[] partArray = new com.meizu.statsapp.a.a.b[5];
        partArray[0] = new d("nonce", nonce);
        partArray[1] = new d("ts", ts);
        partArray[2] = new d("md5", md5);
        partArray[3] = new d("sign", sign);
        partArray[4] = new com.meizu.statsapp.a.a.a("collect", "collect", pushData, null, "UTF-8");
        return partArray;
    }

    private boolean a(JSONObject data, boolean isWifi) {
        if (data == null) {
            return false;
        }
        if (!isWifi) {
            if (this.d && !c(data)) {
                return false;
            }
            if (!(this.d || b(data))) {
                return false;
            }
        }
        if (TextUtils.isEmpty(UsageStatsManagerServer.a) || "00000000000000000000000000000000000".equals(UsageStatsManagerServer.a)) {
            c();
            try {
                data.put("umid", UsageStatsManagerServer.a);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String dataStr = data.toString();
        if (TextUtils.isEmpty(dataStr)) {
            return false;
        }
        UsageStatusLog.d("UsageStatsUploader", "uploadEvent data=" + data);
        com.meizu.statsapp.a.a.b[] uploadData = a(dataStr.getBytes());
        if (uploadData == null) {
            return false;
        }
        for (int i = 0; i < 3 && i(); i++) {
            if (c(com.meizu.statsapp.util.a.a("https://uxip.meizu.com/api/upload", uploadData))) {
                UsageStatusLog.d("UsageStatsUploader", "uploadEvents, upload successfully.");
                h();
                return true;
            }
        }
        return false;
    }

    private boolean c(String result) {
        if (TextUtils.isEmpty(result)) {
            return false;
        }
        try {
            if (200 == new JSONObject(result).getInt("code")) {
                return true;
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void h() {
        for (Entry<String, Map<String, List<Event>>> packageSessionEntry : this.j.entrySet()) {
            for (Entry<String, List<Event>> sessionMapEntry : ((Map) packageSessionEntry.getValue()).entrySet()) {
                this.f.a((Collection) sessionMapEntry.getValue());
            }
        }
        if (this.d) {
            this.k = this.f.a();
            UsageStatusLog.d("UsageStatsUploader", "upload successful! update mEventCome=" + this.k);
        }
    }

    boolean a(Event event, boolean isWifi) {
        if (!this.e || event == null) {
            return false;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("ver", "2.0");
            a(data, event.l());
            data.put("package", event.e());
            data.put("version", TextUtils.isEmpty(event.m()) ? Utils.getPackageVersion(event.e(), this.c) : event.m());
            data.put("sid", event.d());
            JSONObject eventJson = new JSONObject();
            eventJson.put("type", a(event.b()));
            eventJson.put("name", event.a());
            eventJson.put(RequestManager.TIME, event.c());
            eventJson.put("page", event.f());
            String propertiesJson = event.h();
            if (Utils.isEmpty(propertiesJson)) {
                eventJson.put("value", new JSONObject());
            } else {
                eventJson.put("value", new JSONObject(propertiesJson));
            }
            data.put("event", eventJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (data.length() < 1) {
            return false;
        }
        byte[] pushData = data.toString().getBytes();
        if (pushData == null) {
            return false;
        }
        if (!isWifi && !c(data)) {
            return false;
        }
        if (TextUtils.isEmpty(UsageStatsManagerServer.a) || "00000000000000000000000000000000000".equals(UsageStatsManagerServer.a)) {
            c();
            try {
                data.put("umid", UsageStatsManagerServer.a);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        int tsValue = (int) (System.currentTimeMillis() / 1000);
        String sign = Utils.bytesToHexString(Utils.getMD5(String.format("key=OjUiuYe80AUYnbgBNT6&nonce=%s&ts=%s&md5=%s", new Object[]{String.valueOf(new Random().nextInt() + tsValue), String.valueOf(tsValue), Utils.bytesToHexString(Utils.getMD5(pushData))}).getBytes()));
        Map params = new HashMap();
        params.put("nonce", nonce);
        params.put("ts", ts);
        params.put("md5", md5);
        params.put("sign", sign);
        params.put("uxip_data", data.toString());
        return c(com.meizu.statsapp.util.a.a("https://uxip.meizu.com/api/report/realtime", params));
    }

    static void a(boolean isAlarm) {
        if (a == null) {
            return;
        }
        if (isAlarm) {
            a.b(2);
        } else {
            a.b(3);
        }
    }

    private void b(int what) {
        this.h.sendEmptyMessage(what);
    }

    void b(boolean upload) {
        if (this.e != upload) {
            this.e = upload;
            UsageStatusLog.d("UsageStatsUploader", "setUploaded, mUpload=" + this.e);
        }
        if (this.e) {
            b();
        }
    }

    void b() {
        if (!this.d || this.e) {
            long intervalTime = Math.abs(System.currentTimeMillis() - this.o);
            UsageStatusLog.d("UsageStatsUploader", "checkUpload, mOnline=" + this.d + ", intervalTime=" + (intervalTime / 3600000) + "h, mPowerConnecting=" + this.r + ", mLastUploadTime=" + this.o);
            if (intervalTime >= 86400000) {
                c(true);
            }
            if (this.d || 0 == this.o || intervalTime >= 86400000 || (this.r && intervalTime >= 28800000)) {
                this.h.removeMessages(1);
                this.h.sendEmptyMessage(1);
                return;
            }
            return;
        }
        UsageStatusLog.d("UsageStatsUploader", "checkUpload, mUpload=" + this.e);
    }

    private void a(long time) {
        PendingIntent pIntent = PendingIntent.getBroadcast(this.c, 1, new Intent("com.meizu.usagestats.check_upload"), 134217728);
        AlarmManager alarmManager = (AlarmManager) this.c.getSystemService("alarm");
        if (alarmManager != null) {
            alarmManager.cancel(pIntent);
            alarmManager.set(1, 28800000 + time, pIntent);
            this.p = time;
        }
    }

    private void b(long time) {
        this.o = time;
        this.i.edit().putLong("last_upload_time", this.o).apply();
    }

    private void c(boolean isAllow) {
        this.q = isAllow;
        this.i.edit().putBoolean("today_upload_flag", this.q).apply();
    }

    private boolean i() {
        if (Utils.isNetworkWorking(this.c)) {
            return true;
        }
        return false;
    }

    private boolean b(JSONObject data) {
        if (data == null || ((long) data.toString().getBytes().length) >= 10240 || !this.q) {
            return false;
        }
        UsageStatusLog.d("UsageStatsUploader", "offline this data.size=" + data.toString().getBytes().length);
        return true;
    }

    private boolean c(JSONObject data) {
        if (data == null) {
            return false;
        }
        long tempSum = this.t + ((long) data.toString().getBytes().length);
        UsageStatusLog.d("UsageStatsUploader", "mOnlineDayFlowSum=" + this.t + " online this data.size=" + data.toString().getBytes().length);
        if (tempSum >= 1048576) {
            return false;
        }
        this.t = tempSum;
        this.i.edit().putLong("online_flow_sum", this.t).apply();
        return true;
    }

    public void c() {
        try {
            JSONObject uploadData = new JSONObject();
            a(uploadData);
            String response = com.meizu.statsapp.util.a.a("https://umid.orion.meizu.com/umid/generator", uploadData);
            if (response != null) {
                JSONObject result = new JSONObject(response);
                if (200 == result.getInt("code")) {
                    JSONObject value = result.getJSONObject("value");
                    if (value != null) {
                        String umid = value.getString("umid");
                        String mac = value.getString("mac");
                        if (umid != null) {
                            UsageStatsManagerServer.a = umid;
                            this.i.edit().putString(b.g, UsageStatsManagerServer.a).apply();
                        }
                        if (mac != null) {
                            this.i.edit().putString(b.h, mac).apply();
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void d() {
        this.h.post(new Runnable(this) {
            final /* synthetic */ e a;

            {
                this.a = r1;
            }

            public void run() {
                try {
                    JSONObject uploadData = new JSONObject();
                    this.a.a(uploadData);
                    String response = com.meizu.statsapp.util.a.a("https://umid.orion.meizu.com/umid/generator", uploadData);
                    if (response != null) {
                        JSONObject result = new JSONObject(response);
                        if (200 == result.getInt("code")) {
                            JSONObject value = result.getJSONObject("value");
                            if (value != null) {
                                String umid = value.getString("umid");
                                if (umid != null) {
                                    UsageStatsManagerServer.a = umid;
                                    this.a.i.edit().putString(b.g, UsageStatsManagerServer.a).apply();
                                }
                                String mac = value.getString("mac");
                                if (mac != null) {
                                    this.a.i.edit().putString(b.h, mac).apply();
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void j() {
        if (this.d) {
            long cur = System.currentTimeMillis();
            long last = this.i.getLong("online_last_upload_time", 0);
            if (0 == last) {
                this.i.edit().putLong("online_last_upload_time", cur).apply();
            } else if (Math.abs(cur - last) >= 86400000) {
                this.i.edit().putLong("online_last_upload_time", cur).apply();
                k();
            }
        }
    }

    private void k() {
        this.s = 1;
        this.t = 0;
        this.i.edit().putInt("ratio", this.s).apply();
        this.i.edit().putLong("online_flow_sum", this.t).apply();
    }
}
