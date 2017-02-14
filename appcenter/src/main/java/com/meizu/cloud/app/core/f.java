package com.meizu.cloud.app.core;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Pair;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.f.e;
import com.meizu.cloud.app.downlad.f.g;
import com.meizu.cloud.app.downlad.f.h;
import com.meizu.cloud.app.downlad.f.j;
import com.meizu.cloud.app.downlad.f.l;
import com.meizu.cloud.app.downlad.f.n;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo.UpdateFinishRecord;
import com.meizu.cloud.app.utils.o;
import com.meizu.cloud.b.a.i;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class f {
    private static final String a = f.class.getSimpleName();
    private static f b;
    private Context c;
    private NotificationManager d;
    private a e;
    private CopyOnWriteArraySet<Pair<String, String>> f = new CopyOnWriteArraySet();
    private CopyOnWriteArraySet<p<String, String, String>> g = new CopyOnWriteArraySet();
    private CopyOnWriteArraySet<p<String, String, String>> h = new CopyOnWriteArraySet();
    private Bitmap i;
    private d j;

    class a implements com.meizu.cloud.app.downlad.f.b, com.meizu.cloud.app.downlad.f.d, e, g {
        final /* synthetic */ f a;

        a(f fVar) {
            this.a = fVar;
        }

        public void onFetchStateChange(com.meizu.cloud.app.downlad.e wrapper) {
            l anEnum = wrapper.f();
            if (anEnum instanceof n) {
                switch ((n) anEnum) {
                    case FETCHING:
                        this.a.f();
                        return;
                    case FAILURE:
                        if (this.a.j.a(1).size() == 0) {
                            this.a.d.cancel(8000);
                        } else {
                            this.a.f();
                        }
                        p three = null;
                        boolean result = false;
                        if (!(TextUtils.isEmpty(wrapper.g()) || TextUtils.isEmpty(wrapper.a(this.a.c)) || TextUtils.isEmpty(wrapper.k()))) {
                            Object string;
                            String g = wrapper.g();
                            if (wrapper.a(this.a.c) == null) {
                                string = this.a.c.getString(i.download_error);
                            } else {
                                string = this.a.c.getString(i.download_error_formatted2, new Object[]{wrapper.a(this.a.c)});
                            }
                            three = p.a(g, string, wrapper.k());
                            result = this.a.g.add(three);
                        }
                        if (result) {
                            this.a.a(three);
                            return;
                        }
                        return;
                    case CANCEL:
                        if (this.a.j.a(1).size() == 0) {
                            this.a.d.cancel(8000);
                            return;
                        }
                        this.a.a(this.a.c(), this.a.c.getString(i.removed_task, new Object[]{wrapper.k()}));
                        return;
                    default:
                        return;
                }
            }
        }

        public void onDownloadStateChanged(com.meizu.cloud.app.downlad.e wrapper) {
            l anEnum = wrapper.f();
            if (anEnum instanceof com.meizu.cloud.app.downlad.f.c) {
                switch ((com.meizu.cloud.app.downlad.f.c) anEnum) {
                    case TASK_CREATED:
                    case TASK_WAITING:
                        this.a.a(this.a.c(), null);
                        return;
                    case TASK_ERROR:
                        if (this.a.j.a(1).size() == 0) {
                            this.a.d.cancel(8000);
                        } else {
                            this.a.a(this.a.c(), null);
                        }
                        if (wrapper.y() == 1) {
                            try {
                                o.a(this.a.c);
                                return;
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        p three = null;
                        boolean result = false;
                        if (!(TextUtils.isEmpty(wrapper.g()) || TextUtils.isEmpty(wrapper.a(this.a.c)) || TextUtils.isEmpty(wrapper.k()))) {
                            Object string;
                            String g = wrapper.g();
                            if (wrapper.a(this.a.c) == null) {
                                string = this.a.c.getString(i.download_error);
                            } else {
                                string = this.a.c.getString(i.download_error_formatted2, new Object[]{wrapper.a(this.a.c)});
                            }
                            three = p.a(g, string, wrapper.k());
                            result = this.a.g.add(three);
                        }
                        if (result) {
                            this.a.a(three);
                            return;
                        }
                        return;
                    case TASK_PAUSED:
                        this.a.e();
                        return;
                    case TASK_REMOVED:
                        if (this.a.j.a(1).size() == 0) {
                            this.a.d.cancel(8000);
                            return;
                        }
                        this.a.a(this.a.c(), this.a.c.getString(i.removed_task, new Object[]{wrapper.k()}));
                        return;
                    case TASK_COMPLETED:
                        this.a.a(this.a.c(), this.a.c.getString(i.installing_formatted, new Object[]{wrapper.k()}));
                        if (this.a.j.a(1).size() == 0) {
                            this.a.d.cancel(8000);
                        }
                        this.a.d();
                        return;
                    default:
                        return;
                }
            }
        }

        public void onDownloadProgress(com.meizu.cloud.app.downlad.e wrapper) {
            this.a.a(this.a.c(), null);
        }

        public void b(com.meizu.cloud.app.downlad.e wrapper) {
            l anEnum = wrapper.f();
            if (anEnum instanceof h) {
                switch ((h) anEnum) {
                    case PATCHED_FAILURE:
                        if (this.a.j.g(1).size() == 0) {
                            this.a.d.cancel(8000);
                        }
                        p<String, String, String> three = null;
                        boolean result = false;
                        if (!(TextUtils.isEmpty(wrapper.g()) || TextUtils.isEmpty(wrapper.a(this.a.c)) || TextUtils.isEmpty(wrapper.k()))) {
                            Object string;
                            String g = wrapper.g();
                            if (wrapper.a(this.a.c) == null) {
                                string = this.a.c.getString(i.install_error);
                            } else {
                                string = this.a.c.getString(i.install_error_formatted, new Object[]{wrapper.a(this.a.c)});
                            }
                            three = p.a(g, string, wrapper.k());
                            result = this.a.h.add(three);
                        }
                        if (result) {
                            this.a.b((p) three);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }

        public void onInstallStateChange(com.meizu.cloud.app.downlad.e wrapper) {
            if (!wrapper.j().d()) {
                l anEnum = wrapper.f();
                if (anEnum instanceof com.meizu.cloud.app.downlad.f.f) {
                    String pkgName = TextUtils.isEmpty(wrapper.a()) ? wrapper.g() : wrapper.a();
                    switch ((com.meizu.cloud.app.downlad.f.f) anEnum) {
                        case INSTALL_START:
                            this.a.d();
                            return;
                        case INSTALL_SUCCESS:
                            this.a.f.add(Pair.create(pkgName, wrapper.k()));
                            this.a.e(this.a.c.getString(i.install_success_formatted, new Object[]{wrapper.k()}));
                            if (this.a.j.g(1).size() == 0) {
                                this.a.d.cancel(8000);
                            }
                            this.a.d();
                            return;
                        case INSTALL_FAILURE:
                            if (this.a.j.g(1).size() == 0) {
                                this.a.d.cancel(8000);
                            }
                            p<String, String, String> three = null;
                            boolean result = false;
                            if (!(TextUtils.isEmpty(pkgName) || TextUtils.isEmpty(wrapper.a(this.a.c)) || TextUtils.isEmpty(wrapper.k()))) {
                                Object string;
                                if (wrapper.a(this.a.c) == null) {
                                    string = this.a.c.getString(i.install_error);
                                } else {
                                    string = this.a.c.getString(i.install_error_formatted, new Object[]{wrapper.a(this.a.c)});
                                }
                                three = p.a(pkgName, string, wrapper.k());
                                result = this.a.h.add(three);
                            }
                            if (result) {
                                this.a.b((p) three);
                            }
                            this.a.d();
                            return;
                        case DELETE_SUCCESS:
                            if (!TextUtils.isEmpty(pkgName)) {
                                this.a.a(wrapper.a());
                                return;
                            }
                            return;
                        case DELETE_FAILURE:
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    class b extends BroadcastReceiver {
        final /* synthetic */ f a;

        b(f fVar) {
            this.a = fVar;
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (x.a(this.a.c)) {
                if ("app_clean_finish_notify".equals(action)) {
                    this.a.f.clear();
                } else if ("app_clean_download_error_notify".equals(action)) {
                    this.a.g.clear();
                } else if ("app_clean_install_error_notify".equals(action)) {
                    this.a.h.clear();
                }
            } else if ("game_clean_finish_notify".equals(action)) {
                this.a.f.clear();
            } else if ("game_clean_download_error_notify".equals(action)) {
                this.a.g.clear();
            } else if ("game_clean_install_error_notify".equals(action)) {
                this.a.h.clear();
            }
        }
    }

    private class c {
        public int a;
        public int b;
        public long c;
        public long d;
        public int e;
        public int f;
        final /* synthetic */ f g;

        private c(f fVar) {
            this.g = fVar;
            this.a = 0;
            this.b = 0;
            this.c = 0;
            this.d = 0;
            this.e = 0;
            this.f = 0;
        }
    }

    private f(Context context) {
        this.c = context;
        this.d = (NotificationManager) this.c.getSystemService("notification");
        this.e = new a(this);
        this.i = com.meizu.cloud.app.utils.h.a(context.getResources().getDrawable(com.meizu.cloud.b.a.e.ic_status_notification));
        IntentFilter intentFilter = new IntentFilter();
        if (x.a(this.c)) {
            intentFilter.addAction("app_clean_finish_notify");
            intentFilter.addAction("app_clean_download_error_notify");
            intentFilter.addAction("app_clean_install_error_notify");
        } else {
            intentFilter.addAction("game_clean_finish_notify");
            intentFilter.addAction("game_clean_download_error_notify");
            intentFilter.addAction("game_clean_install_error_notify");
        }
        this.c.registerReceiver(new b(this), intentFilter);
    }

    public static final synchronized f a(Context context) {
        f fVar;
        synchronized (f.class) {
            if (b == null) {
                b = new f(context.getApplicationContext());
            }
            fVar = b;
        }
        return fVar;
    }

    public void a(d taskFactory) {
        this.j = taskFactory;
        com.meizu.cloud.app.downlad.g taskProperty = new com.meizu.cloud.app.downlad.g(-1, 1);
        taskProperty.a(new p(Integer.valueOf(3), Integer.valueOf(22), n.FAILURE), new p(Integer.valueOf(3), Integer.valueOf(22), com.meizu.cloud.app.downlad.f.c.TASK_ERROR), new p(Integer.valueOf(3), Integer.valueOf(22), com.meizu.cloud.app.downlad.f.f.INSTALL_FAILURE), new p(Integer.valueOf(3), Integer.valueOf(22), h.PATCHED_FAILURE), new p(Integer.valueOf(3), Integer.valueOf(22), j.FAILURE));
        taskFactory.a(this.e, taskProperty);
    }

    public static final synchronized f a() {
        f fVar;
        synchronized (f.class) {
            fVar = b;
        }
        return fVar;
    }

    public void a(String packageName) {
        Iterator i$ = this.f.iterator();
        while (i$.hasNext()) {
            Pair<String, String> pair = (Pair) i$.next();
            if (pair.first != null && ((String) pair.first).equals(packageName)) {
                if (this.f.remove(pair)) {
                    e(null);
                    return;
                }
                return;
            }
        }
    }

    public void b(String packageName) {
        if (!c(packageName) && !d(packageName)) {
        }
    }

    public boolean c(String packageName) {
        Iterator i$ = this.g.iterator();
        while (i$.hasNext()) {
            p<String, String, String> three = (p) i$.next();
            if (three.a != null && ((String) three.a).equals(packageName)) {
                boolean success = this.g.remove(three);
                if (!success) {
                    return success;
                }
                a(null);
                return success;
            }
        }
        return false;
    }

    public boolean d(String packageName) {
        Iterator i$ = this.h.iterator();
        while (i$.hasNext()) {
            p<String, String, String> three = (p) i$.next();
            if (three.a != null && ((String) three.a).equals(packageName)) {
                boolean success = this.h.remove(three);
                if (!success) {
                    return success;
                }
                b(null);
                return success;
            }
        }
        return false;
    }

    public void a(int id) {
        this.d.cancel(id);
    }

    public void a(List<p<String, Integer, String>> appInfos) {
        int i;
        List<p<String, Integer, String>> appInfosDownload = new ArrayList();
        for (i = 0; i < appInfos.size(); i++) {
            p<String, Integer, String> appInfo = (p) appInfos.get(i);
            if (com.meizu.cloud.app.downlad.a.b((String) appInfo.a, ((Integer) appInfo.b).intValue())) {
                appInfosDownload.add(appInfos.get(i));
            }
        }
        boolean bFreeFlow = false;
        if (appInfosDownload.size() > 0) {
            appInfos.clear();
            appInfos.addAll(appInfosDownload);
            bFreeFlow = true;
        }
        int updateAllCount = appInfos.size();
        Intent contentIntent = new Intent(x.a(this.c) ? "com.meizu.flyme.appcenter.app.update" : "com.meizu.flyme.gamecenter.game.update");
        contentIntent.putExtra("perform_internal", false);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this.c, 5000, contentIntent, 134217728);
        Intent intent = new Intent("com.meizu.cloud.center.ignore.update");
        List arrayList = new ArrayList(appInfos.size());
        List<p<String, Integer, String>> stayIgnoreApps = new ArrayList();
        for (p<String, Integer, String> appInfo2 : appInfos) {
            boolean isExistMark1 = com.meizu.cloud.app.core.m.a.c(this.c, (String) appInfo2.a, "ignore_update_apps");
            boolean isExistMark2 = com.meizu.cloud.app.core.m.a.c(this.c, (String) appInfo2.a, "ignore_notify_apps");
            if (isExistMark1 || isExistMark2) {
                boolean isIgnore = isExistMark1 ? ((Integer) appInfo2.b).intValue() == com.meizu.cloud.app.core.m.a.a(this.c, (String) appInfo2.a, "ignore_update_apps") : ((Integer) appInfo2.b).intValue() == com.meizu.cloud.app.core.m.a.a(this.c, (String) appInfo2.a, "ignore_notify_apps");
                if (isIgnore) {
                    stayIgnoreApps.add(appInfo2);
                } else {
                    arrayList.add(Pair.create(appInfo2.a, appInfo2.b));
                }
            } else {
                arrayList.add(Pair.create(appInfo2.a, appInfo2.b));
            }
        }
        appInfos.removeAll(stayIgnoreApps);
        if (appInfos.size() > 0) {
            String actionName;
            String title;
            String contentText;
            int notifyAppSize = appInfos.size() - 3;
            if (notifyAppSize < 0) {
                int maxValue;
                int difValue = Math.abs(notifyAppSize);
                if (stayIgnoreApps.size() < difValue) {
                    maxValue = stayIgnoreApps.size();
                } else {
                    maxValue = difValue;
                }
                List<p<String, Integer, String>> arrayList2 = new ArrayList(maxValue);
                for (i = 0; i < maxValue; i++) {
                    appInfos.add(stayIgnoreApps.get(i));
                    arrayList2.add(stayIgnoreApps.get(i));
                }
                stayIgnoreApps.removeAll(arrayList2);
            }
            intent = new Intent("com.meizu.cloud.center.execute.update");
            intent.putExtra("initiative", true);
            intent.putExtra("free_flow", bFreeFlow);
            PendingIntent updateAllPendingIntent = PendingIntent.getBroadcast(this.c, 6000, intent, 134217728);
            if (bFreeFlow) {
                actionName = this.c.getString(i.update_now);
            } else if (appInfos.size() == 1) {
                actionName = this.c.getString(i.update);
            } else {
                actionName = this.c.getString(i.all_update);
            }
            com.meizu.cloud.app.core.g.a action = new com.meizu.cloud.app.core.g.a(0, actionName, updateAllPendingIntent);
            intent.putExtra("package_version_data", b(arrayList));
            PendingIntent ignoreUpdatePendingIntent = PendingIntent.getBroadcast(this.c, 7000, intent, 268435456);
            if (bFreeFlow) {
                title = updateAllCount == 1 ? this.c.getString(i.update_free_title) : this.c.getString(i.update_free_title_format, new Object[]{String.valueOf(updateAllCount)});
            } else if (updateAllCount == 1) {
                title = this.c.getString(i.apps_update);
            } else {
                title = this.c.getString(i.apps_update_formatted, new Object[]{String.valueOf(updateAllCount)});
            }
            String separator = ", ";
            StringBuilder sbContentTitle = new StringBuilder();
            if (bFreeFlow) {
                sbContentTitle.append(this.c.getString(i.update_notification_text));
            }
            for (p<String, Integer, String> app : appInfos) {
                sbContentTitle.append(com.meizu.cloud.app.utils.g.a((String) app.c)).append(separator);
            }
            if (stayIgnoreApps.size() > 0) {
                contentText = sbContentTitle + this.c.getString(i.etc);
            } else {
                contentText = sbContentTitle.substring(0, sbContentTitle.length() - separator.length());
            }
            Notification notification = g.a(this.c, title, contentText, this.i, com.meizu.cloud.b.a.e.mz_stat_sys_appcenter, new com.meizu.cloud.app.core.g.a[]{action});
            notification.flags = 16;
            notification.contentIntent = contentPendingIntent;
            notification.deleteIntent = ignoreUpdatePendingIntent;
            this.d.cancel(5000);
            this.d.notify(5000, notification);
        }
    }

    public static void a(Context context, long launchTime) {
        List<UpdateFinishRecord> finishRecords = r.a(context).d(context);
        ArrayList records = new ArrayList(finishRecords.size());
        for (UpdateFinishRecord record : finishRecords) {
            if (record.update_finish_time > launchTime - 259200000) {
                records.add(record.name);
            }
        }
        finishRecords.clear();
        if (records.size() > 0) {
            Drawable icon = context.getResources().getDrawable(com.meizu.cloud.b.a.e.ic_status_notification);
            Context context2 = context;
            Notification notification = g.a(context2, context.getString(i.free_update_tips, new Object[]{Integer.valueOf(records.size())}), com.meizu.cloud.app.utils.h.a(icon), com.meizu.cloud.b.a.e.mz_stat_sys_appcenter, records, null);
            notification.flags = 16;
            Intent intent = new Intent();
            intent.putExtra("perform_internal", false);
            if (x.a(context)) {
                intent.setAction("com.meizu.flyme.appcenter.app.updaterecord");
            } else if (x.b(context)) {
                intent.setAction("com.meizu.flyme.gamecenter.game.updaterecord");
            }
            notification.contentIntent = PendingIntent.getActivity(context, 13000, intent, 268435456);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            notificationManager.cancel(13000);
            notificationManager.notify(13000, notification);
            m.d.a(context, "last_launch_time", System.currentTimeMillis());
        }
    }

    private static String b(List<Pair<String, Integer>> appInfos) {
        JSONArray jsonArray = new JSONArray();
        for (Pair<String, Integer> appInfo : appInfos) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pkg", appInfo.first);
            jsonObject.put("ver", appInfo.second);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    public void b() {
        String title = this.c.getString(i.launch_appcenter_notification_title);
        String contentText = this.c.getString(i.launch_appcenter_notification_contenttext);
        Intent contentIntent = new Intent("com.meizu.setup.DOWNLOAD_NOTIFY");
        contentIntent.putExtra("perform_internal", false);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this.c, 11000, contentIntent, 134217728);
        Notification notification = g.a(this.c, title, contentText, this.i, com.meizu.cloud.b.a.e.mz_stat_sys_appcenter, null);
        notification.flags = 16;
        notification.contentIntent = contentPendingIntent;
        this.d.cancel(11000);
        this.d.notify(11000, notification);
    }

    private c c() {
        c progressEntity = new c();
        if (this.j != null) {
            List<com.meizu.cloud.app.downlad.e> downloadWrappers = this.j.a(1);
            progressEntity.a = downloadWrappers.size();
            for (com.meizu.cloud.app.downlad.e wrapper : downloadWrappers) {
                if (wrapper.f() != com.meizu.cloud.app.downlad.f.c.TASK_ERROR) {
                    progressEntity.b += wrapper.r();
                    progressEntity.c += (long) wrapper.s();
                    progressEntity.e += wrapper.o();
                }
            }
            Iterator i$ = this.j.h(1).iterator();
            while (i$.hasNext()) {
                com.meizu.cloud.app.downlad.e info = (com.meizu.cloud.app.downlad.e) i$.next();
                if (info.j().a(1) && info.f() != com.meizu.cloud.app.downlad.f.c.TASK_ERROR) {
                    progressEntity.d += info.p();
                    progressEntity.f += 100;
                }
            }
        }
        return progressEntity;
    }

    private synchronized void d() {
        List<com.meizu.cloud.app.downlad.e> installList = d.a(this.c).j(1);
        int size = installList.size();
        if (size > 0) {
            String format;
            com.meizu.cloud.app.downlad.e wrapper = (com.meizu.cloud.app.downlad.e) installList.get(0);
            Context context = this.c;
            if (size > 1) {
                format = String.format("%s   %s", new Object[]{this.c.getString(i.installing), Integer.valueOf(installList.size())});
            } else {
                format = this.c.getString(i.installing);
            }
            Notification notification = g.a(context, format, wrapper.k(), this.i, com.meizu.cloud.b.a.e.mz_stat_sys_downloading);
            notification.flags = 2;
            notification.contentIntent = PendingIntent.getActivity(this.c, 12000, f(h.a(this.c, h.PROGRESS)), 268435456);
            this.d.notify(12000, notification);
        } else if (installList.size() == 0) {
            this.d.cancel(12000);
        }
    }

    private synchronized void e() {
        com.meizu.cloud.app.downlad.e wrapper = null;
        List<com.meizu.cloud.app.downlad.e> pauseList = d.a(this.c).f(1);
        int progress = 0;
        for (com.meizu.cloud.app.downlad.e downloadWrapper : pauseList) {
            progress += downloadWrapper.o();
        }
        int allCount = this.j.h(1).size();
        if (pauseList.size() > 0 && allCount > 0) {
            String string;
            if (allCount == 1) {
                wrapper = (com.meizu.cloud.app.downlad.e) pauseList.get(0);
            }
            Context context = this.c;
            if (wrapper == null) {
                String str = "%s   %s";
                Object[] objArr = new Object[2];
                if (x.a(this.c)) {
                    string = this.c.getString(i.app_download);
                } else {
                    string = this.c.getString(i.game_download);
                }
                objArr[0] = string;
                objArr[1] = String.format("%d/%d", new Object[]{Integer.valueOf(0), Integer.valueOf(allCount)});
                string = String.format(str, objArr);
            } else {
                string = wrapper.k();
            }
            Notification notification = g.a(context, string, this.c.getString(i.waiting_for_resume), this.i, com.meizu.cloud.b.a.e.mz_stat_sys_downloading_pause, true, 100, progress);
            notification.flags = 2;
            notification.contentIntent = PendingIntent.getActivity(this.c, 8000, f(h.a(this.c, h.PROGRESS)), 268435456);
            this.d.notify(8000, notification);
        } else if (pauseList.size() == 0 && allCount == 0) {
            this.d.cancel(8000);
        }
    }

    private synchronized void f() {
        com.meizu.cloud.app.downlad.e wrapper = null;
        d factory = d.a(this.c);
        List<com.meizu.cloud.app.downlad.e> startList = factory.a(1);
        int curCount = this.j.a(1).size();
        int allCount = this.j.h(1).size();
        if (startList.size() == 0 && allCount > 0) {
            String string;
            if (allCount == 1) {
                wrapper = (com.meizu.cloud.app.downlad.e) factory.g(1).get(0);
            }
            Context context = this.c;
            if (wrapper == null) {
                String str = "%s   %s";
                Object[] objArr = new Object[2];
                if (x.a(this.c)) {
                    string = this.c.getString(i.app_download);
                } else {
                    string = this.c.getString(i.game_download);
                }
                objArr[0] = string;
                objArr[1] = String.format("%d/%d", new Object[]{Integer.valueOf(curCount), Integer.valueOf(allCount)});
                string = String.format(str, objArr);
            } else {
                string = wrapper.k();
            }
            Notification notification = g.a(context, string, this.c.getString(i.waiting_for_download), this.i, com.meizu.cloud.b.a.e.mz_stat_sys_downloading, true, 100, 0);
            notification.flags = 2;
            notification.contentIntent = PendingIntent.getActivity(this.c, 8000, f(h.a(this.c, h.PROGRESS)), 268435456);
            this.d.notify(8000, notification);
        } else if (startList.size() == 0 && allCount == 0) {
            this.d.cancel(8000);
        }
    }

    private synchronized void a(c notifyProgress, String ticker) {
        com.meizu.cloud.app.downlad.e wrapper = null;
        List<com.meizu.cloud.app.downlad.e> startList = this.j.a(1);
        int curCount = startList.size();
        int allCount = this.j.h(1).size();
        if (curCount != 0) {
            String format;
            if (curCount == 1 && allCount == 1) {
                wrapper = (com.meizu.cloud.app.downlad.e) startList.get(0);
            }
            String applicationName = x.a(this.c) ? this.c.getString(i.app_download) : this.c.getString(i.game_download);
            Context context = this.c;
            if (wrapper == null) {
                Object[] objArr = new Object[2];
                objArr[0] = applicationName;
                objArr[1] = String.format("%d/%d", new Object[]{Integer.valueOf(curCount), Integer.valueOf(allCount)});
                format = String.format("%s   %s", objArr);
            } else {
                format = wrapper.k();
            }
            Notification notification = g.a(context, format, com.meizu.cloud.app.utils.g.a((double) notifyProgress.d, this.c.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit)), com.meizu.cloud.app.utils.g.a((double) notifyProgress.b, this.c.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit)) + this.c.getResources().getStringArray(com.meizu.cloud.b.a.b.speed)[0], com.meizu.cloud.app.utils.g.c(this.c, notifyProgress.c), this.i, com.meizu.cloud.b.a.e.mz_stat_sys_downloading, true, notifyProgress.f, notifyProgress.e);
            if (!TextUtils.isEmpty(ticker)) {
                notification.tickerText = ticker;
            }
            notification.flags = 2;
            notification.contentIntent = PendingIntent.getActivity(this.c, 8000, f(h.a(this.c, h.PROGRESS)), 268435456);
            this.d.notify(8000, notification);
        }
    }

    private synchronized void e(String tickerText) {
        this.d.cancel(1000);
        if (this.f.size() > 0) {
            String string;
            Context context = this.c;
            if (this.f.size() == 1) {
                string = this.c.getString(i.install_success);
            } else {
                string = this.c.getString(i.install_success_formatted2, new Object[]{Integer.valueOf(this.f.size())});
            }
            Notification notification = g.a(context, string, this.i, com.meizu.cloud.b.a.e.mz_stat_sys_appcenter_succeed, a(this.f), tickerText);
            notification.flags = 16;
            String action = h.a(this.c, h.FINISH);
            boolean isLauncherIntent = false;
            if (this.f.size() == 1) {
                String pkgName = ((Pair) this.f.iterator().next()).first;
                Intent intent = new Intent("com.meizu.cloud.appcommon.intent.LAUNCH_APP");
                intent.putExtra("package_name", pkgName);
                notification.contentIntent = PendingIntent.getBroadcast(this.c, 1000, intent, 268435456);
                isLauncherIntent = true;
            }
            if (!isLauncherIntent) {
                notification.contentIntent = PendingIntent.getActivity(this.c, 1000, f(action), 268435456);
            }
            notification.deleteIntent = PendingIntent.getBroadcast(this.c, 1000, new Intent(action), 268435456);
            this.d.notify(1000, notification);
        }
    }

    private synchronized void a(p<String, String, String> three) {
        String str;
        Set set = this.g;
        String string = this.c.getString(i.download_error_formatted3, new Object[]{Integer.valueOf(this.g.size())});
        int i = com.meizu.cloud.b.a.e.mz_stat_sys_appcenter_error;
        if (three == null) {
            str = null;
        } else {
            str = this.c.getString(i.download_error_formatted, new Object[]{three.c});
        }
        a(9000, set, string, i, str, h.DOWNLOAD_ERROR);
    }

    private synchronized void b(p<String, String, String> three) {
        String str;
        Set set = this.h;
        String string = this.c.getString(i.install_error_formatted2, new Object[]{Integer.valueOf(this.h.size())});
        int i = com.meizu.cloud.b.a.e.mz_stat_sys_appcenter_error;
        if (three == null) {
            str = null;
        } else {
            str = this.c.getString(i.install_error_formatted3, new Object[]{three.c});
        }
        a(10000, set, string, i, str, h.INSTALL_ERROR);
    }

    private synchronized void a(int notifyId, Set<p<String, String, String>> data, String title, int smallIcon, String ticker, h actionType) {
        this.d.cancel(notifyId);
        Notification notification = null;
        if (data.size() > 1) {
            notification = g.a(this.c, title, this.i, smallIcon, b((Set) data), ticker);
        } else if (data.size() == 1) {
            p<String, String, String> lastApp = (p) data.iterator().next();
            notification = g.a(this.c, this.i, smallIcon, (String) lastApp.c, (String) lastApp.b, ticker);
        }
        if (notification != null) {
            notification.flags = 16;
            String action = h.a(this.c, actionType);
            notification.contentIntent = PendingIntent.getActivity(this.c, notifyId, f(action), 268435456);
            notification.deleteIntent = PendingIntent.getBroadcast(this.c, notifyId, new Intent(action), 268435456);
            this.d.notify(notifyId, notification);
        }
    }

    private Intent f(String actionType) {
        Intent intent = new Intent();
        intent.putExtra("perform_internal", false);
        if (x.a(this.c)) {
            intent.setAction("com.meizu.flyme.appcenter.app.download.manage");
        } else if (x.b(this.c)) {
            intent.setAction("com.meizu.flyme.gamecenter.game.download.manage");
        }
        if (!TextUtils.isEmpty(intent.getAction())) {
            intent.putExtra("Action", actionType);
        }
        return intent;
    }

    private ArrayList<String> a(Set<Pair<String, String>> pairs) {
        ArrayList<String> secondString = new ArrayList(pairs.size());
        for (Pair<String, String> pair : pairs) {
            secondString.add(pair.second);
        }
        return secondString;
    }

    private ArrayList<String> b(Set<p<String, String, String>> threes) {
        ArrayList<String> thirdString = new ArrayList(threes.size());
        for (p<String, String, String> pair : threes) {
            thirdString.add(pair.c);
        }
        return thirdString;
    }
}
