package com.meizu.update.display;

import android.content.Context;
import com.meizu.update.UpdateInfo;
import com.meizu.update.c.d.d;
import com.meizu.update.service.MzUpdateComponentService;
import com.meizu.update.service.a;

public class b extends a {
    public b(Context context, UpdateInfo info) {
        super(context, info);
        a(true);
    }

    public a a() {
        return new a(this.a.getString(d.mzuc_downloading), null, a.b(this.b, this.a) + " , " + this.b.mSize, this.a.getResources().getString(d.mzuc_delete), this.a.getResources().getString(d.mzuc_cancel), null, new a.a(this) {
            final /* synthetic */ b a;

            {
                this.a = r1;
            }

            public void a(a.a.a code) {
                switch (code) {
                    case POSITIVE:
                        com.meizu.update.g.a.a(this.a.a).a(com.meizu.update.g.a.a.Download_Del, this.a.b.mVersionName);
                        this.a.e();
                        return;
                    default:
                        return;
                }
            }
        });
    }

    private void e() {
        MzUpdateComponentService.c(this.a);
    }
}
