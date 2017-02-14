package com.meizu.account.pay;

import android.os.AsyncTask;
import android.os.RemoteException;
import com.meizu.account.pay.a.g;

final class c extends AsyncTask {
    private /* synthetic */ a a;

    c(a aVar) {
        this.a = aVar;
    }

    private Void a() {
        g gVar = (g) this.a.b.a();
        if (gVar != null) {
            try {
                this.a.a(gVar, new d(this));
            } catch (RemoteException e) {
                e.printStackTrace();
                this.a.a(new g(this));
            }
        } else {
            this.a.a(new h(this));
        }
        return null;
    }

    protected final /* synthetic */ Object doInBackground(Object... objArr) {
        return a();
    }

    protected final /* synthetic */ void onPostExecute(Object obj) {
        this.a.b.b();
    }
}
