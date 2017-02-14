package com.meizu.flyme.appcenter;

import android.os.Handler;
import android.os.Looper;
import com.meizu.cloud.base.app.BaseApplication;
import com.meizu.cloud.download.app.NetworkStatusManager;
import com.meizu.cloud.download.app.NetworkStatusManager.a;
import com.meizu.flyme.appcenter.recommend.b;

public class AppCenterApplication extends BaseApplication {
    private static boolean b = false;

    public void onCreate() {
        super.onCreate();
    }

    public static void c() {
        NetworkStatusManager networkStatusManager = NetworkStatusManager.a();
        if (networkStatusManager != null && !b) {
            b = true;
            networkStatusManager.a(new a() {
                public void a(int networkType) {
                    NetworkStatusManager manager = NetworkStatusManager.a();
                    if (manager != null && manager.a(true)) {
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable(this) {
                            final /* synthetic */ AnonymousClass1 a;

                            {
                                this.a = r1;
                            }

                            public void run() {
                                b.a(BaseApplication.a()).a(false);
                            }
                        }, 3000);
                    }
                }
            });
        }
    }
}
