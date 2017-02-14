package com.meizu.flyme.appcenter.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.n;
import com.android.volley.s;
import com.meizu.cloud.app.core.g;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.LicenseInfoModel;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.utils.c;
import com.meizu.cloud.app.utils.j;
import com.meizu.cloud.base.app.BaseApplication;
import com.meizu.mstore.R;
import com.meizu.mstore.license.LicenseResult;
import com.meizu.mstore.license.a.a;
import com.meizu.mstore.license.b;
import java.util.ArrayList;
import java.util.List;

public class LicensingService extends Service {
    private Thread a = null;
    private final a b = new a(this) {
        final /* synthetic */ LicensingService a;

        {
            this.a = r1;
        }

        public LicenseResult a(String packageName) throws RemoteException {
            LicenseResult result = new b(this.a, "LicenseFile", c.d).a(packageName, null, 0, null);
            this.a.b(packageName, result, false, 0);
            return result;
        }

        public LicenseResult a(String packageName, int version) throws RemoteException {
            throw new RemoteException("Current Version Not Support");
        }

        public LicenseResult a(String packageName, int version, String themePath) throws RemoteException {
            throw new RemoteException("Current Version Not Support");
        }
    };

    public IBinder onBind(Intent intent) {
        return this.b;
    }

    public boolean onUnbind(Intent intent) {
        return false;
    }

    public void onCreate() {
        super.onCreate();
        BaseApplication.a((Context) this);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void a(final String packageName, LicenseResult result, final boolean bTheme, int themeVersion) {
        boolean bFound = false;
        int versionCode = 0;
        if (bTheme) {
            versionCode = themeVersion;
            bFound = true;
        } else {
            try {
                PackageInfo pi = getPackageManager().getPackageInfo(packageName, 0);
                if (pi != null) {
                    versionCode = pi.versionCode;
                    bFound = true;
                }
            } catch (Exception e) {
                j.b("LicensingService", "load license file exception!");
                if (c.b) {
                    e.printStackTrace();
                    return;
                }
                return;
            }
        }
        if (bFound) {
            TypeReference typeReference = new TypeReference<ResultModel<LicenseInfoModel>>(this) {
                final /* synthetic */ LicensingService a;

                {
                    this.a = r1;
                }
            };
            List paramPairs = new ArrayList();
            paramPairs.add(new com.meizu.volley.b.a("package_name", packageName));
            paramPairs.add(new com.meizu.volley.b.a("version_code", String.valueOf(versionCode)));
            l jsonRequest = new FastJsonRequest(typeReference, 0, RequestConstants.APP_LICENSE_LOAD, paramPairs, new n.b<ResultModel<LicenseInfoModel>>(this) {
                final /* synthetic */ LicensingService c;

                public void a(ResultModel<LicenseInfoModel> response) {
                    if (response == null || response.getCode() != 200 || response.getValue() == null) {
                        this.c.a(packageName, this.c.getString(R.string.license_restore_fail_message), this.c.getString(R.string.license_restore_failed));
                        return;
                    }
                    LicenseInfoModel licenseInfoModel = (LicenseInfoModel) response.getValue();
                    if (TextUtils.isEmpty(licenseInfoModel.license)) {
                        this.c.a(packageName, this.c.getString(R.string.license_restore_fail_message), this.c.getString(R.string.license_restore_failed));
                        return;
                    }
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("PACKAGE_NAME", packageName);
                    contentValues.put("LICENSE_DATA", licenseInfoModel.license);
                    this.c.getBaseContext().getContentResolver().insert(Uri.parse("content://com.meizu.flyme.appcenter.licenseprovider/license"), contentValues);
                    if (!bTheme) {
                        this.c.a(packageName, this.c.getString(R.string.license_restore_success_message), this.c.getString(R.string.license_restore_successful));
                    }
                    if (licenseInfoModel.paid) {
                        Log.w("LicensingService", "license restore.");
                    } else {
                        Log.w("LicensingService", "tryout license restore.");
                    }
                }
            }, new n.a(this) {
                final /* synthetic */ LicensingService b;

                public void a(s error) {
                    error.printStackTrace();
                    this.b.a(packageName, this.b.getString(R.string.license_restore_fail_message), this.b.getString(R.string.license_restore_failed));
                }
            });
            jsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(getBaseContext()));
            com.meizu.volley.b.a(getBaseContext()).a().a(jsonRequest);
            return;
        }
        j.c("LicensingService", "cant find package, just return");
    }

    private void a(String packageName, String message, String ticker) {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            if (pi != null) {
                a(a(packageName), pi.applicationInfo.loadLabel(pm).toString(), message, ticker);
            }
        } catch (NameNotFoundException e) {
            j.b("LicensingService", "application not found exception!");
            if (c.b) {
                e.printStackTrace();
            }
        }
    }

    public void a(int id, String appName, String contentText, String ticker) {
        Notification notification = g.a((Context) this, i.b(getApplicationContext(), getPackageName()), (int) R.drawable.mz_stat_sys_appcenter, appName, contentText, ticker);
        notification.flags = 16;
        notification.contentIntent = PendingIntent.getActivity(this, 0, new Intent(), 268435456);
        ((NotificationManager) getSystemService("notification")).notify(id, notification);
    }

    private int a(String packageName) {
        int id = 0;
        if (!TextUtils.isEmpty(packageName)) {
            for (char c : packageName.toCharArray()) {
                id += c;
            }
        }
        return id;
    }

    private void b(String packageName, LicenseResult result, boolean bTheme, int themeVersion) {
        if (result.a() != 1) {
            if (c.b) {
                Log.w("LicensingService", "no license or not legal, download license!");
            }
            if (this.a == null || !this.a.isAlive()) {
                final String str = packageName;
                final LicenseResult licenseResult = result;
                final boolean z = bTheme;
                final int i = themeVersion;
                this.a = new Thread(new Runnable(this) {
                    final /* synthetic */ LicensingService e;

                    public void run() {
                        this.e.a(str, licenseResult, z, i);
                    }
                });
                this.a.start();
            } else if (c.b) {
                Log.w("LicensingService", "loading license, just return");
            }
        }
    }
}
