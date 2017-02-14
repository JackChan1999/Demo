package com.meizu.update.display;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.text.TextUtils;
import com.meizu.update.UpdateInfo;
import com.meizu.update.c.d.d;
import com.meizu.update.c.e;
import com.meizu.update.h.h;
import com.meizu.update.iresponse.MzUpdateResponse;
import com.meizu.update.iresponse.a;
import com.meizu.update.push.b;
import com.meizu.update.service.MzUpdateComponentService;

public class g extends a {
    private e e;
    private Handler f;
    private ProgressDialog g;
    private boolean h;
    private boolean i;
    private boolean j;
    private a k = new a.a(this) {
        final /* synthetic */ g a;

        {
            this.a = r1;
        }

        public void a(int code, Bundle bundle) throws RemoteException {
        }

        public void b(final int code, final Bundle bundle) throws RemoteException {
            this.a.a(new Runnable(this) {
                final /* synthetic */ AnonymousClass1 c;

                public void run() {
                    this.c.a.a(code, bundle);
                }
            });
        }
    };

    private void a(int code, Bundle bundle) {
        j();
        if (this.i) {
            e();
            return;
        }
        switch (code) {
            case 0:
                new d(this.a, this.e, this.b, bundle.getString("apk_path")).e();
                return;
            case 1:
                e();
                return;
            case 2:
                f();
                return;
            default:
                return;
        }
    }

    public g(Context context, e listener, UpdateInfo info, boolean systemAlert, boolean showSkipBtn) {
        super(context, info);
        a(systemAlert);
        this.e = listener;
        this.h = showSkipBtn;
        if (this.e != null) {
            this.f = new Handler(context.getMainLooper());
            this.g = h.a(context);
            this.g.setMessage(context.getString(d.mzuc_downloading));
            this.g.setOnCancelListener(new OnCancelListener(this) {
                final /* synthetic */ g a;

                {
                    this.a = r1;
                }

                public void onCancel(DialogInterface dialog) {
                    this.a.i = true;
                    com.meizu.update.g.a.a(this.a.a).a(com.meizu.update.g.a.a.Download_Del, this.a.b.mVersionName);
                    this.a.h();
                }
            });
        }
    }

    public void b(boolean manual) {
        this.j = manual;
    }

    private void e() {
        if (this.e != null) {
            this.e.a(1, this.b);
        }
    }

    private void f() {
        if (this.e != null) {
            this.e.a(2, this.b);
        }
    }

    private void g() {
        MzUpdateResponse response = null;
        if (this.e != null) {
            response = new MzUpdateResponse(this.k);
        }
        i();
        MzUpdateComponentService.a(this.a, this.b, response);
    }

    public a a() {
        String title = TextUtils.isEmpty(c()) ? String.format(this.a.getString(d.mzuc_found_update_s), new Object[]{this.b.mVersionName}) : c();
        String subTitle = String.format(this.a.getString(d.mzuc_file_size_s), new Object[]{this.b.mSize});
        String msg = TextUtils.isEmpty(d()) ? this.b.mVersionDesc : d();
        String positiveText = this.a.getResources().getString(d.mzuc_update_immediately);
        String negativeText = null;
        String neutralText = null;
        if (!this.b.mNeedUpdate) {
            if (this.h && !this.j) {
                negativeText = this.a.getResources().getString(d.mzuc_skip_version);
            }
            if (this.j) {
                neutralText = this.a.getString(d.mzuc_cancel);
            } else {
                neutralText = this.a.getString(d.mzuc_warn_later);
            }
        }
        com.meizu.update.g.a.a(this.a).a(com.meizu.update.g.a.a.UpdateDisplay_Alert, this.b.mVersionName, com.meizu.update.h.g.b(this.a, this.a.getPackageName()), this.j);
        return new a(title, subTitle, msg, positiveText, negativeText, neutralText, new a.a(this) {
            final /* synthetic */ g a;

            {
                this.a = r1;
            }

            public void a(a.a.a code) {
                switch (code) {
                    case POSITIVE:
                        com.meizu.update.g.a.a(this.a.a).a(com.meizu.update.g.a.a.UpdateAlert_Yes, this.a.b.mVersionName, com.meizu.update.h.g.b(this.a.a, this.a.a.getPackageName()), this.a.j);
                        this.a.g();
                        return;
                    case NEGATIVE:
                        com.meizu.update.g.a.a(this.a.a).a(com.meizu.update.g.a.a.UpdateAlert_Ignore, this.a.b.mVersionName, com.meizu.update.h.g.b(this.a.a, this.a.a.getPackageName()), this.a.j);
                        b.b(this.a.a, this.a.b.mVersionName);
                        this.a.e();
                        return;
                    case CANCELED:
                        com.meizu.update.g.a.a(this.a.a).a(com.meizu.update.g.a.a.UpdateAlert_No, this.a.b.mVersionName, com.meizu.update.h.g.b(this.a.a, this.a.a.getPackageName()), this.a.j);
                        this.a.e();
                        return;
                    case NEUTRAL:
                        com.meizu.update.g.a.a(this.a.a).a(com.meizu.update.g.a.a.UpdateAlert_No, this.a.b.mVersionName, com.meizu.update.h.g.b(this.a.a, this.a.a.getPackageName()), this.a.j);
                        com.meizu.update.b.b.c(this.a.a);
                        this.a.e();
                        return;
                    default:
                        return;
                }
            }
        });
    }

    private void a(Runnable r) {
        this.f.post(r);
    }

    private void h() {
        MzUpdateComponentService.c(this.a);
    }

    private void i() {
        if (this.g != null) {
            this.g.show();
        }
    }

    private void j() {
        try {
            if (this.g != null && this.g.isShowing()) {
                this.g.dismiss();
            }
        } catch (Exception e) {
        }
    }
}
