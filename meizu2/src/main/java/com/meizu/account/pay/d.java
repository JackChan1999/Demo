package com.meizu.account.pay;

import android.content.Intent;
import android.os.Bundle;
import com.meizu.account.pay.a.e;

final class d extends e {
    private /* synthetic */ c a;

    d(c cVar) {
        this.a = cVar;
    }

    public final void a(int i, String str) {
        this.a.a.a(new f(this, i, str));
    }

    public final void a(Bundle bundle) {
        this.a.a.a(new e(this, bundle));
    }

    public final void b(Bundle bundle) {
        if (bundle.containsKey("intent")) {
            a.a(this.a.a, (Intent) bundle.getParcelable("intent"));
        }
    }
}
