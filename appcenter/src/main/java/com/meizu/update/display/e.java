package com.meizu.update.display;

import android.content.Context;
import com.meizu.update.UpdateInfo;
import com.meizu.update.c.d.d;
import com.meizu.update.display.a.a.a;
import com.meizu.update.h.g;
import com.meizu.update.push.b;

public class e extends a {
    private com.meizu.update.c.e e;

    public e(Context context, UpdateInfo info, com.meizu.update.c.e listener, boolean systemAlert) {
        super(context, info);
        a(systemAlert);
        this.e = listener;
    }

    public a a() {
        return new a(null, null, a.getString(d.mzuc_skip_warn_tip), a.getString(d.mzuc_ok), a.getString(d.mzuc_cancel), null, new a(this) {
            final /* synthetic */ e a;

            {
                this.a = r1;
            }

            public void a(a.a code) {
                switch (code) {
                    case POSITIVE:
                        this.a.e();
                        break;
                }
                this.a.f();
            }
        });
    }

    private void e() {
        if (this.b != null) {
            com.meizu.update.g.a.a(this.a).a(com.meizu.update.g.a.a.UpdateAlert_Ignore, this.b.mVersionName, g.b(this.a, a.getPackageName()));
            b.b(this.a, this.b.mVersionName);
        }
    }

    private void f() {
        if (this.e != null) {
            this.e.a(1, this.b);
        }
    }
}
