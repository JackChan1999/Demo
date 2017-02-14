package com.meizu.cloud.app.core;

import android.content.Context;
import android.util.Log;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.n.a;
import com.android.volley.n.b;
import com.android.volley.s;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.ResultModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class o {
    private static final String a = o.class.getSimpleName();
    private Context b;
    private l<?> c;
    private n d;
    private b<ResultModel<String>> e = new b<ResultModel<String>>(this) {
        final /* synthetic */ o a;

        {
            this.a = r1;
        }

        public void a(ResultModel<String> response) {
            boolean succes = response != null && response.getCode() == 200;
            if (succes) {
                if (this.a.d == null || !this.a.d.a()) {
                    Log.e(o.a, "submit end while history illegal!");
                } else {
                    this.a.d.d();
                }
            }
            this.a.c = null;
            this.a.d = null;
        }
    };
    private a f = new a(this) {
        final /* synthetic */ o a;

        {
            this.a = r1;
        }

        public void a(s error) {
            error.printStackTrace();
            this.a.c = null;
            this.a.d = null;
        }
    };
    private Timer g;
    private TimerTask h;

    public o(Context context) {
        this.b = context;
    }

    private void d() {
        if (this.c == null || this.c.isCanceled()) {
            n history = new n(this.b);
            if (history.a()) {
                int submitType = history.c();
                String submitStr = history.b();
                this.d = history;
                List<com.meizu.volley.b.a> paramPairs = new ArrayList();
                paramPairs.add(new com.meizu.volley.b.a("optype", String.valueOf(submitType)));
                paramPairs.add(new com.meizu.volley.b.a("apps", submitStr));
                l request = new com.meizu.volley.a.b(this.b, new TypeReference<ResultModel<String>>(this) {
                    final /* synthetic */ o a;

                    {
                        this.a = r1;
                    }
                }, RequestConstants.getRuntimeDomainUrl(this.b, RequestConstants.SUBMIT_HISTORY), paramPairs, this.e, this.f);
                request.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.b));
                this.c = request;
                com.meizu.volley.b.a(this.b).a().a(request);
                return;
            }
            return;
        }
        Log.w(a, "uploadDeviceAppInfos while processing, delay");
        a();
    }

    public void a() {
        if (this.h != null) {
            this.h.cancel();
            this.h = null;
        }
        if (this.g != null) {
            this.g.cancel();
            this.g.purge();
            this.g = null;
        }
        this.h = new TimerTask(this) {
            final /* synthetic */ o a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.d();
            }
        };
        this.g = new Timer();
        this.g.schedule(this.h, 10000);
    }

    public synchronized void b() {
        a();
    }
}
