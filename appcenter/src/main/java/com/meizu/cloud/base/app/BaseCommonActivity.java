package com.meizu.cloud.base.app;

import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.utils.i;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.download.app.NetworkStatusManager;
import com.meizu.common.app.SlideNotice;
import java.util.ArrayList;
import java.util.List;

public class BaseCommonActivity extends BaseActivity {
    protected List<b> k;
    protected com.meizu.cloud.download.app.NetworkStatusManager.a l = new com.meizu.cloud.download.app.NetworkStatusManager.a(this) {
        final /* synthetic */ BaseCommonActivity a;

        {
            this.a = r1;
        }

        public void a(int networkType) {
            if (networkType != 0) {
                this.a.m();
            }
        }
    };
    private SlideNotice m;
    private a n;
    private c o;
    private d p;

    public interface d {
        boolean onWebViewBackPressed();
    }

    public interface a {
        boolean d();
    }

    public interface b {
        void b();
    }

    public interface c {
    }

    public void a(a backPressedListener) {
        if (this.n == null || !this.n.equals(backPressedListener)) {
            this.n = backPressedListener;
        }
    }

    public void a(d webViewBackPressedListener) {
        if (this.p == null || !this.p.equals(webViewBackPressedListener)) {
            this.p = webViewBackPressedListener;
        }
    }

    public void k() {
        this.p = null;
    }

    public void a(b onSearchIconClickListener) {
        if (onSearchIconClickListener != null) {
            if (this.k == null) {
                this.k = new ArrayList();
            } else {
                for (b listener : this.k) {
                    if (listener == onSearchIconClickListener) {
                        return;
                    }
                }
            }
            this.k.add(onSearchIconClickListener);
        }
    }

    public void b(b onSearchIconClickListener) {
        if (this.k != null && this.k.size() > 0) {
            for (b listener : this.k) {
                if (listener == onSearchIconClickListener) {
                    this.k.remove(listener);
                    return;
                }
            }
        }
    }

    public void onBackPressed() {
        if (this.p == null || !this.p.onWebViewBackPressed()) {
            boolean res = false;
            if (this.n != null) {
                res = this.n.d();
            }
            if (!res) {
                super.onBackPressed();
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }

    protected void onResume() {
        super.onResume();
        NetworkStatusManager.a().a(this.l);
        if (m.b(this)) {
            m();
        }
    }

    protected void onPause() {
        super.onPause();
        NetworkStatusManager.a().b(this.l);
    }

    protected void onDestroy() {
        this.n = null;
        this.o = null;
        i.a(this);
        super.onDestroy();
    }

    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.e("AsyncExecuteActivity", "onTrimMemory level: " + level);
        if (level == 5 || level == 10 || level == 15 || level == 20 || level == 60) {
            h.a((Context) this);
            System.gc();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.meizu.cloud.b.a.h.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void l() {
        if (!isDestroyed()) {
            if (this.m == null) {
                this.m = com.meizu.cloud.app.utils.a.a(this);
            } else if (!this.m.isShowing()) {
                this.m = com.meizu.cloud.app.utils.a.a(this);
            }
        }
    }

    public void m() {
        if (!isDestroyed() && this.m != null && this.m.isShowing()) {
            this.m.slideToCancel();
        }
    }
}
