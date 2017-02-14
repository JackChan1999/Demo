package com.meizu.update.display;

import android.content.Context;
import com.meizu.update.UpdateInfo;
import com.meizu.update.c.d.d;
import com.meizu.update.service.MzUpdateComponentService;
import com.meizu.update.service.a;

public class c extends a {
    private boolean e;

    public c(Context context, UpdateInfo info, boolean isDownload) {
        super(context, info);
        this.e = isDownload;
        a(true);
    }

    public a a() {
        String msg;
        String negativeText;
        String title = a.b(this.b, this.a);
        if (this.e) {
            msg = this.a.getString(d.mzuc_download_fail);
            negativeText = this.a.getResources().getString(d.mzuc_cancel_download);
        } else {
            msg = this.a.getString(d.mzuc_install_fail);
            negativeText = this.a.getResources().getString(d.mzuc_cancel_install);
        }
        return new a(title, null, msg, this.a.getResources().getString(d.mzuc_retry), negativeText, null, new a.a(this) {
            final /* synthetic */ c a;

            {
                this.a = r1;
            }

            public void a(a.a.a code) {
                switch (code) {
                    case POSITIVE:
                        this.a.f();
                        return;
                    case NEGATIVE:
                        this.a.e();
                        return;
                    default:
                        return;
                }
            }
        });
    }

    private void e() {
    }

    private void f() {
        MzUpdateComponentService.a(this.a, this.b, null);
    }
}
