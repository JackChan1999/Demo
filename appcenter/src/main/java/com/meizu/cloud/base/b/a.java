package com.meizu.cloud.base.b;

import a.a.a.c;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.meizu.cloud.app.c.b;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.downlad.f.k;
import com.meizu.cloud.app.downlad.g;

public abstract class a<T> extends h<T> {
    private k a = new k(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void onDownloadStateChanged(e wrapper) {
            this.a.a(wrapper, false);
        }

        public void onDownloadProgress(e wrapper) {
            this.a.a(wrapper, true);
        }

        public void onFetchStateChange(e wrapper) {
            this.a.a(wrapper, false);
        }

        public void onInstallStateChange(e wrapper) {
            this.a.a(wrapper, false);
        }

        public void b(e wrapper) {
            this.a.a(wrapper, false);
        }

        public void a(e wrapper) {
            this.a.a(wrapper, false);
        }
    };

    protected abstract void a(e eVar, boolean z);

    public abstract Fragment b();

    protected abstract void c(String str);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d.a(getActivity()).a(this.a, new g());
        c.a().a((Object) this);
    }

    public void onDestroy() {
        super.onDestroy();
        d.a(getActivity()).b(this.a);
        c.a().c(this);
    }

    public void onEventMainThread(com.meizu.cloud.app.c.a appStateChangeEvent) {
        c(appStateChangeEvent.b);
    }

    public void onEventMainThread(b appUpdateCheckEvent) {
        if (appUpdateCheckEvent.c) {
            for (String pkg : appUpdateCheckEvent.a) {
                c(pkg);
            }
        }
    }
}
