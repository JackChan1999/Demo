package com.meizu.cloud.app.core;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.downlad.f.f;
import com.meizu.cloud.app.utils.k;
import com.meizu.cloud.app.utils.p;
import com.meizu.cloud.b.a.i;
import com.tencent.mm.sdk.modelbase.BaseResp.ErrCode;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class d {
    private Context a;
    private p b;
    private ExecutorService c;
    private List<b> d = new ArrayList();
    private Map<String, String> e = new HashMap();
    private Handler f = new Handler(this, Looper.getMainLooper()) {
        final /* synthetic */ d a;

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (this.a.d.size() >= 1) {
                        this.a.a((b) this.a.d.get(0));
                        break;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private class a extends android.content.pm.IPackageDeleteObserver.a {
        final /* synthetic */ d a;
        private c b;

        private a(d dVar, c stateObserver) {
            this.a = dVar;
            this.b = stateObserver;
        }

        public void packageDeleted(String packageName, int returnCode) throws RemoteException {
            if (returnCode == 1) {
                x.d(this.a.a).c(packageName);
            }
            if (this.b != null) {
                this.b.a(packageName, returnCode);
            }
        }
    }

    private class b extends android.content.pm.IPackageInstallObserver.a {
        public e a;
        public c b;
        final /* synthetic */ d c;

        private b(d dVar, e installParam, c stateObserver) {
            this.c = dVar;
            this.a = installParam;
            this.b = stateObserver;
        }

        public void packageInstalled(String packageName, int returnCode) throws RemoteException {
            k.a(this.c.a, "Installer", "Installed finish: " + packageName + ", code: " + returnCode);
            if (returnCode == 1) {
                x.d(this.c.a).a(i.a(this.c.a, packageName), this.c.a.getPackageName());
            } else {
                this.c.a(packageName);
            }
            if (this.b != null) {
                this.a.a(packageName);
                this.b.a(this.a, returnCode);
            }
            synchronized (this.c.d) {
                if (this.c.d.size() > 0) {
                    this.c.d.remove(0);
                }
            }
            this.c.f.sendEmptyMessage(0);
        }
    }

    public static abstract class c {
        public void a(e downloadWrapper, int returnCode) {
        }

        public void a(String packageName, int returnCode) {
        }
    }

    public static String a(Context context, int reasonCode) {
        switch (reasonCode) {
            case -1001:
                return context.getString(i.method_invoke_failed);
            case -1000:
                return context.getString(i.update_pack_incompatible);
            case -115:
                return context.getString(i.install_aborted);
            case -114:
                return context.getString(i.no_native_libraries);
            case -113:
                return context.getString(i.no_matching_abis);
            case -112:
                return context.getString(i.duplicate_permission);
            case -111:
                return context.getString(i.user_restricted);
            case -110:
                return context.getString(i.internal_error);
            case -109:
                return context.getString(i.manifest_empty);
            case -108:
                return context.getString(i.manifest_malformed);
            case -107:
                return context.getString(i.bad_shared_user_id);
            case -106:
                return context.getString(i.bad_package_name);
            case -105:
                return context.getString(i.certificate_encoding);
            case -104:
                return context.getString(i.inconsistent_certificates);
            case -103:
                return context.getString(i.no_certificates);
            case -102:
                return context.getString(i.unexpected_exception);
            case -101:
                return context.getString(i.bad_manifest);
            case -100:
                return context.getString(i.not_apk);
            case -25:
                return context.getString(i.version_downgrade);
            case -24:
                return context.getString(i.uid_changed);
            case -23:
                return context.getString(i.package_changed);
            case -22:
                return context.getString(i.verification_failure);
            case -21:
                return context.getString(i.verification_timeout);
            case -20:
                return context.getString(i.media_unavailable);
            case -19:
                return context.getString(i.invalid_install_location);
            case -18:
                return context.getString(i.container_error);
            case -17:
                return context.getString(i.missing_feature);
            case -16:
                return context.getString(i.cpu_abi_incompatible);
            case -15:
                return context.getString(i.test_only);
            case -14:
                return context.getString(i.newer_sdk);
            case -13:
                return context.getString(i.conflicting_provider);
            case -12:
                return context.getString(i.older_sdk);
            case -11:
                return context.getString(i.dexopt);
            case -10:
                return context.getString(i.replace_couldnt_delete);
            case -9:
                return context.getString(i.missing_shared_library);
            case -8:
                return context.getString(i.share_user_incompatible);
            case -7:
                return context.getString(i.update_incompatible);
            case -6:
                return context.getString(i.no_shared_user);
            case ErrCode.ERR_UNSUPPORT /*-5*/:
                return context.getString(i.duplicate_package);
            case ErrCode.ERR_AUTH_DENIED /*-4*/:
                return context.getString(i.insufficient_storage);
            case ErrCode.ERR_SENT_FAILED /*-3*/:
                return context.getString(i.invalid_uri);
            case -2:
                return context.getString(i.invalid_apk);
            case -1:
                return context.getString(i.already_exists);
            default:
                return null;
        }
    }

    public d(Context mContext) {
        this.a = mContext;
        this.b = new p(mContext);
        this.c = Executors.newSingleThreadExecutor();
    }

    public final void a(e param, c stateObserver) {
        synchronized (this.d) {
            boolean isFound = false;
            for (b observer : this.d) {
                if (observer.a.g().equals(param.g())) {
                    isFound = true;
                }
            }
            if (!isFound) {
                this.d.add(new b(param, stateObserver));
                if (this.d.size() == 1) {
                    a((b) this.d.get(0));
                }
            }
        }
    }

    private final void a(b installObserver) {
        this.e.put(installObserver.a.g(), "");
        installObserver.a.a(f.INSTALL_START, null);
        com.meizu.cloud.app.downlad.d.a(this.a).a(null, installObserver.a);
        k.a(this.a, "Installer", "The package need to install: " + installObserver.a.g());
        if (!this.b.a(Uri.parse("file://" + installObserver.a.n()), installObserver, 2, this.a.getPackageName())) {
            installObserver.b.a(installObserver.a, -1001);
        }
    }

    public void a(String packageName) {
        this.e.remove(packageName);
    }

    public void a(final String apkPath, boolean bFored) {
        if (bFored || (!(apkPath == null || apkPath.endsWith(".apk")) || com.meizu.cloud.app.settings.a.a(this.a).e())) {
            this.c.execute(new Runnable(this) {
                final /* synthetic */ d b;

                public void run() {
                    try {
                        File file = new File(apkPath);
                        if (file.exists()) {
                            file.delete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public final void a(String packageName, c stateObserver) {
        if (!this.b.a(packageName, new a(stateObserver), 0)) {
            stateObserver.a(packageName, -1001);
        }
    }

    public static final boolean a(int errorType) {
        return errorType == -7;
    }
}
