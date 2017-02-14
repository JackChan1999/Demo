package com.meizu.update.display;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.text.TextUtils;
import com.meizu.update.UpdateInfo;
import com.meizu.update.b.b;
import com.meizu.update.c.e;
import com.meizu.update.h.g;
import com.meizu.update.h.h;
import com.meizu.update.iresponse.MzUpdateResponse;
import com.meizu.update.iresponse.a;
import com.meizu.update.service.MzUpdateComponentService;

public class d extends a {
    private String e;
    private Handler f;
    private e g;
    private ProgressDialog h;
    private a i = new a.a(this) {
        final /* synthetic */ d a;

        {
            this.a = r1;
        }

        public void a(final int code, Bundle bundle) throws RemoteException {
            this.a.a(new Runnable(this) {
                final /* synthetic */ AnonymousClass1 b;

                public void run() {
                    this.b.a.a(code);
                }
            });
        }

        public void b(int code, Bundle bundle) throws RemoteException {
        }
    };

    private void a(int code) {
        i();
        switch (code) {
            case 1:
                f();
                return;
            case 2:
                g();
                return;
            case 3:
                j();
                return;
            default:
                return;
        }
    }

    public d(Context context, e listener, UpdateInfo info, String apkPath) {
        super(context, info);
        if (TextUtils.isEmpty(apkPath)) {
            throw new IllegalArgumentException("params cant be null!");
        }
        this.g = listener;
        this.e = apkPath;
        if (this.g != null) {
            this.f = new Handler(context.getMainLooper());
            this.h = h.a(context);
            this.h.setMessage(context.getString(com.meizu.update.c.d.d.mzuc_installing));
            this.h.setCancelable(false);
            this.h.setOnCancelListener(new OnCancelListener(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public void onCancel(DialogInterface dialog) {
                }
            });
        }
    }

    public a a() {
        String title = TextUtils.isEmpty(c()) ? null : c();
        String appName = g.h(this.a);
        String msg = TextUtils.isEmpty(d()) ? String.format(this.a.getString(com.meizu.update.c.d.d.mzuc_download_finish_s), new Object[]{appName, this.b.mVersionName}) : d();
        String positiveText = this.a.getString(com.meizu.update.c.d.d.mzuc_install_immediately);
        String negativeText = null;
        if (!this.b.mNeedUpdate) {
            negativeText = this.a.getString(com.meizu.update.c.d.d.mzuc_install_later);
        }
        com.meizu.update.g.a.a(this.a).a(com.meizu.update.g.a.a.Download_Done, this.b.mVersionName);
        return new a(title, null, msg, positiveText, negativeText, null, new a.a(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void a(a.a.a code) {
                switch (code) {
                    case POSITIVE:
                        com.meizu.update.g.a.a(this.a.a).a(com.meizu.update.g.a.a.Install_Yes, this.a.b.mVersionName);
                        this.a.e();
                        return;
                    case NEGATIVE:
                        com.meizu.update.g.a.a(this.a.a).a(com.meizu.update.g.a.a.Install_No, this.a.b.mVersionName);
                        b.c(this.a.a);
                        this.a.f();
                        return;
                    case CANCELED:
                        com.meizu.update.g.a.a(this.a.a).a(com.meizu.update.g.a.a.Install_No, this.a.b.mVersionName);
                        this.a.f();
                        return;
                    default:
                        return;
                }
            }
        });
    }

    private void f() {
        if (this.g != null) {
            this.g.a(1, this.b);
        }
    }

    private void g() {
        if (this.g != null) {
            this.g.a(3, this.b);
        }
    }

    protected void e() {
        h();
        MzUpdateResponse response = null;
        if (this.g != null) {
            response = new MzUpdateResponse(this.i);
        }
        MzUpdateComponentService.a(this.a, this.b, this.e, response);
    }

    private void a(Runnable r) {
        this.f.post(r);
    }

    private void h() {
        try {
            if (this.h != null) {
                this.h.show();
            }
        } catch (Exception e) {
        }
    }

    private void i() {
        try {
            if (this.h != null && this.h.isShowing()) {
                this.h.dismiss();
            }
        } catch (Exception e) {
        }
    }

    private void j() {
        com.meizu.update.e.a.a(this.a, this.e, this.b);
        this.f.postDelayed(new Runnable(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void run() {
                h.a(this.a.a, this.a.a.getString(com.meizu.update.c.d.d.mzuc_install_cancel_tip), new OnClickListener(this) {
                    final /* synthetic */ AnonymousClass4 a;

                    {
                        this.a = r1;
                    }

                    public void onClick(DialogInterface dialog, int which) {
                        this.a.a.f();
                    }
                }, new OnCancelListener(this) {
                    final /* synthetic */ AnonymousClass4 a;

                    {
                        this.a = r1;
                    }

                    public void onCancel(DialogInterface dialog) {
                        this.a.a.f();
                    }
                });
            }
        }, 1000);
    }
}
