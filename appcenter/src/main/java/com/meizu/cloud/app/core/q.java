package com.meizu.cloud.app.core;

import a.a.a.c;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.job.JobInfo.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.s;
import com.meizu.cloud.app.downlad.AppDownloadService;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.downlad.g;
import com.meizu.cloud.app.jobscheduler.JobSchedulerService;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.DownloadInfo;
import com.meizu.cloud.app.request.model.GameEntryInfo;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.utils.k;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.download.service.DownloadService;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.update.UpdateInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;

public class q {
    private static final String a = q.class.getSimpleName();
    private static q d;
    private Context b;
    private r c;
    private JobSchedulerService e;
    private Timer f;
    private TimerTask g;
    private CopyOnWriteArraySet<String> h = new CopyOnWriteArraySet();
    private Thread i;
    private long j;

    abstract class a<T> implements com.android.volley.n.b<T> {
        s b;
        final /* synthetic */ q c;

        public a(q qVar, s updateProperty) {
            this.c = qVar;
            this.b = updateProperty;
        }
    }

    class b implements com.android.volley.n.a {
        final /* synthetic */ q a;
        private s b;

        public b(q qVar, s updateProperty) {
            this.a = qVar;
            this.b = updateProperty;
        }

        public void a(s error) {
            k.b(this.a.b, q.a, "onErrorResponse");
            error.printStackTrace();
            c.a().d(new com.meizu.cloud.app.c.b(this.b, false, new String[0]));
        }
    }

    private void a(ResultModel<List<ServerUpdateAppInfo<GameEntryInfo>>> response, s updateProperty) {
        if (response == null || response.getCode() != 200 || response.getValue() == null) {
            c.a().d(new com.meizu.cloud.app.c.b(updateProperty, false, new String[0]));
            if (response != null) {
                k.b(this.b, a, "server response : " + response.getCode() + " , " + response.getValue());
                return;
            } else {
                k.b(this.b, a, "server response : null");
                return;
            }
        }
        List<ServerUpdateAppInfo> list = (List) response.getValue();
        List<ServerUpdateAppInfo> stayRemoveList = new ArrayList();
        if (x.b(this.b)) {
            for (ServerUpdateAppInfo appInfo : list) {
                if (!appInfo.isGame()) {
                    stayRemoveList.add(appInfo);
                }
            }
        }
        for (ServerUpdateAppInfo<GameEntryInfo> updateAppInfo : list) {
            boolean isRemove = true;
            for (Pair<String, Integer> pair : x.d(this.b).a()) {
                if (updateAppInfo.package_name.equals(pair.first)) {
                    isRemove = false;
                }
            }
            if (isRemove) {
                stayRemoveList.add(updateAppInfo);
            }
        }
        list.removeAll(stayRemoveList);
        if (list.size() > 0) {
            if (!updateProperty.e) {
                for (ServerUpdateAppInfo updateAppInfo2 : list) {
                    this.c.a(updateAppInfo2);
                }
            } else if (updateProperty.a) {
                this.c.a((List) list, true);
            }
            this.c.a(this.b, (List) list);
            ArrayList<String> pkgs = new ArrayList(list.size());
            for (ServerUpdateAppInfo updateAppInfo22 : list) {
                if (updateAppInfo22.existUpdate()) {
                    pkgs.add(updateAppInfo22.package_name);
                }
            }
            c.a().d(new com.meizu.cloud.app.c.b(updateProperty, true, (String[]) pkgs.toArray(new String[pkgs.size()])));
            k.b(this.b, a, "update property is initiative:" + (!updateProperty.c));
            if (updateProperty.c) {
                for (ServerUpdateAppInfo updateAppInfo222 : list) {
                    if (updateAppInfo222.existUpdate()) {
                        if (updateAppInfo222.price <= 0.0d) {
                            e downloadWrapper = d.a(this.b).a(updateAppInfo222, new g(8, 1));
                            int[] iArr = new int[3];
                            downloadWrapper.m().page_info = new int[]{0, 18, 0};
                            d.a(this.b).a(null, downloadWrapper);
                        } else {
                            Log.w(a, "can not start update : " + updateAppInfo222.package_name + " is a fee app");
                        }
                    }
                }
            } else {
                k.b(this.b, a, "setting auto download:" + com.meizu.cloud.app.settings.a.a(this.b).c());
                k.b(this.b, a, "setting auto install:" + com.meizu.cloud.app.settings.a.a(this.b).d());
                if (!com.meizu.cloud.app.settings.a.a(this.b).d()) {
                    List threeList = new ArrayList();
                    for (ServerUpdateAppInfo updateAppInfo2222 : list) {
                        if (updateAppInfo2222.existUpdate()) {
                            if (x.a(this.b)) {
                                threeList.add(p.a(updateAppInfo2222.package_name, Integer.valueOf(updateAppInfo2222.version_code), updateAppInfo2222.name));
                            } else if (updateAppInfo2222.isGame()) {
                                threeList.add(p.a(updateAppInfo2222.package_name, Integer.valueOf(updateAppInfo2222.version_code), updateAppInfo2222.name));
                            }
                        }
                    }
                    k.b(this.b, a, "update property is show notify :" + updateProperty.b + "&" + list.size());
                    if (updateProperty.b && list.size() > 0) {
                        k.b(this.b, a, "setting notify is :" + com.meizu.cloud.app.settings.a.a(this.b).a());
                        if (com.meizu.cloud.app.settings.a.a(this.b).a()) {
                            if (System.currentTimeMillis() - m.d.a(this.b, "last_check_update_time") >= 25200000) {
                                k.b(this.b, a, "send notify at " + com.meizu.common.util.a.a(this.b, System.currentTimeMillis(), 0));
                                f.a(this.b).a(threeList);
                            }
                        }
                    }
                }
            }
        } else {
            if (updateProperty.e) {
                this.c.a(true);
            }
            c.a().d(new com.meizu.cloud.app.c.b(updateProperty, true, new String[0]));
        }
        if (updateProperty.b || updateProperty.d) {
            m.d.a(this.b, "last_check_update_time", System.currentTimeMillis());
        }
    }

    public static final synchronized q a(Context context) {
        q qVar;
        synchronized (q.class) {
            if (d == null) {
                qVar = new q(context.getApplicationContext());
                d = qVar;
            } else {
                qVar = d;
            }
        }
        return qVar;
    }

    private q(Context context) {
        this.b = context.getApplicationContext();
        this.c = r.a(this.b);
        this.e = JobSchedulerService.a(new Runnable(this) {
            final /* synthetic */ q a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.e = JobSchedulerService.a(null);
            }
        });
    }

    public final void a() {
        if (this.e == null) {
            this.e = JobSchedulerService.a(new Runnable(this) {
                final /* synthetic */ q a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.e = JobSchedulerService.a(null);
                    this.a.e();
                }
            });
        } else {
            e();
        }
    }

    private void e() {
        if (this.e != null) {
            k.b(this.b, a, "schedule check update Job");
            this.e.a(new Builder(JobSchedulerService.a[1], new ComponentName(this.b, JobSchedulerService.class)).setPeriodic(25200000).setRequiredNetworkType(1).build(), new Runnable(this) {
                final /* synthetic */ q a;

                {
                    this.a = r1;
                }

                public void run() {
                    k.b(this.a.b, q.a, "run check update in jobSchedulerService");
                    this.a.a(new s().a(true).b(com.meizu.cloud.app.settings.a.a(this.a.b).a()));
                    if (x.a(this.a.b)) {
                        Intent intent2 = new Intent();
                        intent2.setAction("com.meizu.cloud.appcommon.intent.UPDATE_PLUGIN_ICON");
                        this.a.b.sendBroadcast(intent2);
                    }
                }
            });
        }
    }

    private synchronized void b(String... pkgNames) {
        if (this.g != null) {
            this.g.cancel();
            this.g = null;
        }
        if (this.f != null) {
            this.f.cancel();
            this.f.purge();
            this.f = null;
        }
        for (String pkgName : pkgNames) {
            this.h.add(pkgName);
        }
        this.g = new TimerTask(this) {
            final /* synthetic */ q a;

            {
                this.a = r1;
            }

            public void run() {
                List<PackageInfo> appInfos = new ArrayList(this.a.h.size());
                Iterator<String> iterator = this.a.h.iterator();
                while (iterator.hasNext()) {
                    PackageInfo packageInfo = i.a(this.a.b, (String) iterator.next());
                    if (packageInfo != null) {
                        appInfos.add(packageInfo);
                    }
                }
                this.a.h.clear();
                if (appInfos.size() > 0) {
                    String param = this.a.a((PackageInfo[]) appInfos.toArray(new PackageInfo[appInfos.size()]));
                    if (!TextUtils.isEmpty(param)) {
                        s updateProperty = new s();
                        this.a.a(this.a.a(param), new a<ResultModel<List<ServerUpdateAppInfo<GameEntryInfo>>>>(this, updateProperty) {
                            final /* synthetic */ AnonymousClass4 a;

                            public void a(ResultModel<List<ServerUpdateAppInfo<GameEntryInfo>>> response) {
                                this.a.a.a((ResultModel) response, this.b);
                            }
                        }, updateProperty);
                    }
                }
            }
        };
        this.f = new Timer();
        this.f.schedule(this.g, 15000);
    }

    private synchronized void a(final int time) {
        if (this.g != null) {
            this.g.cancel();
            this.g = null;
        }
        if (this.f != null) {
            this.f.cancel();
            this.f.purge();
            this.f = null;
        }
        this.g = new TimerTask(this) {
            final /* synthetic */ q b;

            public void run() {
                k.b(this.b.b, q.a, "update check excute :" + time);
                this.b.a(new s().a(true).e(true));
            }
        };
        this.f = new Timer();
        this.f.schedule(this.g, (long) (time * 1000));
    }

    public final synchronized void a(String... pkgNames) {
        if (pkgNames.length > 0) {
            b(pkgNames);
        }
    }

    public boolean b() {
        List<RunningAppProcessInfo> appProcesses = ((ActivityManager) this.b.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        String packageName = this.b.getPackageName();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == 100 && appProcess.processName.equals(packageName)) {
                k.b(this.b, a, packageName + "---> isRunningForeGround");
                return true;
            }
        }
        k.b(this.b, a, packageName + "---> isRunningBackGround");
        return false;
    }

    public void c() {
        if (!b()) {
            long lastLaunch = m.d.a(this.b, "last_launch_time");
            int min = 0;
            int max = 1800;
            long lasttime = System.currentTimeMillis() - lastLaunch;
            if (lastLaunch == 0) {
                max = 600;
            } else if (lasttime >= 25200000) {
                min = 600;
                max = 900;
            } else if (lasttime >= 14400000) {
                min = 900;
                max = 1200;
            } else {
                min = 1200;
            }
            a((new Random().nextInt(max) % ((max - min) + 1)) + min);
        } else if (this.j == 0) {
            k.b(this.b, a, "check app update first time");
            a(new s().a(false).e(true));
        } else if (System.currentTimeMillis() - this.j > 300000) {
            k.b(this.b, a, "check app update after 5 minutes");
            a(new s().a(true).e(true));
        }
    }

    public final synchronized void a(final s updateProperty) {
        if (this.i == null || !this.i.isAlive()) {
            if (x.a(this.b) && updateProperty.a) {
                long launchTime = m.d.a(this.b, "last_launch_time");
                if (launchTime != 0 && System.currentTimeMillis() - launchTime >= 259200000) {
                    f.a(this.b, launchTime);
                }
            }
            this.i = new Thread(new Runnable(this) {
                final /* synthetic */ q b;

                public void run() {
                    Process.setThreadPriority(10);
                    updateProperty.e(true);
                    for (String pkgName : m.c.a(this.b.b)) {
                        q.a(this.b.b, pkgName);
                    }
                    if (updateProperty.c) {
                        f.a(this.b.b).a(5000);
                    }
                    List<PackageInfo> appInfos = i.b(this.b.b, 5);
                    if (appInfos.size() == 0) {
                        this.b.c.a(true);
                        c.a().d(new com.meizu.cloud.app.c.b(updateProperty, true, new String[0]));
                        m.d.a(this.b.b, "last_check_update_time", System.currentTimeMillis());
                        return;
                    }
                    a<ResultModel<List<ServerUpdateAppInfo<GameEntryInfo>>>> listener = new a<ResultModel<List<ServerUpdateAppInfo<GameEntryInfo>>>>(this, updateProperty) {
                        final /* synthetic */ AnonymousClass6 a;

                        public void a(ResultModel<List<ServerUpdateAppInfo<GameEntryInfo>>> response) {
                            this.a.b.a((ResultModel) response, this.b);
                            if (!this.b.a) {
                                k.b(this.a.b.b, q.a, "check app update in UpdateCheckListener");
                                this.a.b.a(this.b.a(true));
                            }
                            this.a.b.j = System.currentTimeMillis();
                        }
                    };
                    String param = this.b.a(updateProperty.a, (PackageInfo[]) appInfos.toArray(new PackageInfo[appInfos.size()]));
                    if (!TextUtils.isEmpty(param)) {
                        this.b.a(this.b.a(param), (com.android.volley.n.b) listener, updateProperty);
                    }
                }
            });
            this.i.start();
        }
    }

    private List<com.meizu.volley.b.a> a(String paramValue) {
        com.meizu.volley.b.a paramPair = new com.meizu.volley.b.a("apps", paramValue);
        List<com.meizu.volley.b.a> params = new ArrayList();
        params.add(paramPair);
        return params;
    }

    private void a(List<com.meizu.volley.b.a> params, com.android.volley.n.b responseListener, s updateProperty) {
        l request = new FastJsonRequest(new TypeReference<ResultModel<List<ServerUpdateAppInfo<GameEntryInfo>>>>(this) {
            final /* synthetic */ q a;

            {
                this.a = r1;
            }
        }, RequestConstants.getRuntimeDomainUrl(this.b, RequestConstants.CHECK_UPDATE), params, responseListener, new b(this, updateProperty));
        request.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.b));
        com.meizu.volley.b.a(this.b).a().a(request);
    }

    private String a(boolean isCheckDelta, PackageInfo... appInfos) {
        JSONArray jsonArray = m.b.a(this.b);
        List<PackageInfo> changeApps = new ArrayList(appInfos.length);
        for (PackageInfo appInfo : appInfos) {
            if (!TextUtils.isEmpty(appInfo.packageName)) {
                boolean isFound = false;
                boolean isNeedLoadMd5 = false;
                int index = 0;
                int j = 0;
                while (j < jsonArray.size()) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    String pkgName = jsonObject.getString("package_name");
                    int verCode = jsonObject.getInteger("version_code").intValue();
                    if (appInfo.packageName.equals(pkgName)) {
                        if (appInfo.versionCode != verCode) {
                            index = j;
                            isNeedLoadMd5 = true;
                            changeApps.add(appInfo);
                        }
                        isFound = true;
                        if (isNeedLoadMd5) {
                            jsonArray.remove(index);
                        }
                        if (!isFound) {
                            changeApps.add(appInfo);
                        }
                    } else {
                        j++;
                    }
                }
                if (isNeedLoadMd5) {
                    jsonArray.remove(index);
                }
                if (!isFound) {
                    changeApps.add(appInfo);
                }
            }
        }
        for (PackageInfo appInfo2 : changeApps) {
            jsonObject = new JSONObject();
            jsonObject.put("package_name", (Object) appInfo2.packageName);
            jsonObject.put("version_code", (Object) Integer.valueOf(appInfo2.versionCode));
            if (isCheckDelta) {
                byte[] md5 = com.meizu.e.e.b(appInfo2.applicationInfo.sourceDir);
                if (md5 != null) {
                    jsonObject.put("version_md5", (Object) com.meizu.e.e.a(md5));
                }
            }
            jsonArray.add(jsonObject);
        }
        String json = jsonArray.toJSONString();
        Log.i(q.class.getSimpleName(), json);
        if (jsonArray.size() == 0) {
            return null;
        }
        if (!isCheckDelta) {
            return json;
        }
        m.b.a(this.b, json);
        return json;
    }

    private String a(PackageInfo... appInfos) {
        JSONArray jsonArray = new JSONArray(appInfos.length);
        for (PackageInfo appInfo : appInfos) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("package_name", appInfo.packageName);
            jsonObject.put("version_code", Integer.valueOf(appInfo.versionCode));
            byte[] md5 = com.meizu.e.e.b(appInfo.applicationInfo.sourceDir);
            if (md5 != null) {
                jsonObject.put("version_md5", com.meizu.e.e.a(md5));
            }
            jsonArray.add(jsonObject);
        }
        Log.i(q.class.getSimpleName(), jsonArray.toJSONString());
        if (jsonArray.size() == 0) {
            return null;
        }
        m.b.a(this.b, jsonArray);
        return jsonArray.toJSONString();
    }

    public static synchronized void a(final Context context, final String pkgName) {
        synchronized (q.class) {
            new Thread(new Runnable() {
                public void run() {
                    Process.setThreadPriority(10);
                    k.b(context, q.a, "checking system app update ==> " + pkgName);
                    UpdateInfo updateInfo = com.meizu.update.c.a(context, pkgName);
                    if (updateInfo == null) {
                        k.b(context, q.a, "update info is null ==> " + pkgName);
                    } else {
                        k.b(context, q.a, "exist update ==> " + pkgName + " : " + updateInfo.mExistsUpdate);
                    }
                    if (updateInfo != null && updateInfo.mExistsUpdate) {
                        boolean isStart;
                        if (!updateInfo.mNoteNetWork || m.a(context)) {
                            isStart = true;
                        } else {
                            isStart = false;
                        }
                        if (isStart) {
                            final AppStructItem structItem = new AppStructItem();
                            structItem.package_name = pkgName;
                            structItem.software_file = updateInfo.mUpdateUrl;
                            final DownloadInfo downloadInfo = new DownloadInfo();
                            downloadInfo.package_name = pkgName;
                            downloadInfo.download_url = updateInfo.mUpdateUrl;
                            downloadInfo.version_code = 0;
                            downloadInfo.digest = updateInfo.mDigest;
                            downloadInfo.size = updateInfo.mSizeByte;
                            downloadInfo.verify_mode = updateInfo.mVerifyMode;
                            if (DownloadService.a(null) == null) {
                                k.b(context, q.a, "downlaod service is null.");
                                DownloadService.a(context.getApplicationContext(), AppDownloadService.class);
                                DownloadService.b(new Runnable(this) {
                                    final /* synthetic */ AnonymousClass8 c;

                                    public void run() {
                                        g taskProperty = new g(0);
                                        taskProperty.b(false);
                                        d.a(context).a(null, d.a(context).a(structItem, downloadInfo, taskProperty));
                                        k.b(context, q.a, "update task start ==> " + pkgName);
                                    }
                                });
                                DownloadService.e();
                                return;
                            }
                            g taskProperty = new g(0);
                            taskProperty.b(false);
                            taskProperty.e(updateInfo.mNoteNetWork);
                            d.a(context).a(null, d.a(context).a(structItem, downloadInfo, taskProperty));
                            k.b(context, q.a, "update task start ==> " + pkgName);
                        }
                    }
                }
            }).start();
        }
    }
}
