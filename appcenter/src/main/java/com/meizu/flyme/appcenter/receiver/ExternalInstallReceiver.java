package com.meizu.flyme.appcenter.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.n;
import com.android.volley.n.b;
import com.android.volley.s;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.downlad.f.c;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.AppStructDetailsItem;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.utils.g;
import com.meizu.cloud.app.utils.k;
import com.meizu.volley.b.a;
import java.util.ArrayList;
import java.util.List;

public class ExternalInstallReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if ("com.meizu.flyme.appcenter.intent.EXTERNAL_INSTALL".equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            if (bundle != null && !TextUtils.isEmpty(bundle.getString("source", ""))) {
                String pkgName = bundle.getString("package_name", "");
                long appId = bundle.getLong("app_id", 0);
                if (appId > 0) {
                    a(context, pkgName, appId);
                } else {
                    b(context, pkgName);
                }
            }
        }
    }

    private boolean a(Context context, String pkgName) {
        d downloadFactory = d.a(context.getApplicationContext());
        for (e downloadWrapper : downloadFactory.e()) {
            if (downloadWrapper.g().equals(pkgName)) {
                if (downloadWrapper.f() == c.TASK_PAUSED) {
                    downloadFactory.j(pkgName);
                    return true;
                } else if (downloadFactory.f(pkgName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean b(final Context context, final String pkgName) {
        if (TextUtils.isEmpty(pkgName) || x.d(context).a(pkgName)) {
            return false;
        }
        if (a(context, pkgName)) {
            return true;
        }
        TypeReference tr = new TypeReference<ResultModel<JSONObject>>(this) {
            final /* synthetic */ ExternalInstallReceiver a;

            {
                this.a = r1;
            }
        };
        List paramPairs = new ArrayList();
        paramPairs.add(new a("package_name", pkgName));
        l getAppUrlRequest = new FastJsonRequest(tr, RequestConstants.getRuntimeDomainUrl(context, RequestConstants.GET_EXTERNAL_APP_URL), 0, paramPairs, new b<ResultModel<JSONObject>>(this) {
            final /* synthetic */ ExternalInstallReceiver c;

            public void a(ResultModel<JSONObject> response) {
                if (response == null || response.getCode() != 200 || response.getValue() == null) {
                    if (response == null || response.getCode() != RequestConstants.CODE_APP_NOT_FOUND) {
                        k.a(context, toString(), "get detailUrl fail");
                    } else {
                        k.a(context, toString(), "app not found");
                    }
                } else if (((JSONObject) response.getValue()).containsKey("redirect_url")) {
                    String detailUrl = ((JSONObject) response.getValue()).getString("redirect_url");
                    if (TextUtils.isEmpty(detailUrl)) {
                        k.a(context, toString(), "detailUrl is null");
                        return;
                    }
                    this.c.a(context, pkgName, g.d(detailUrl));
                } else {
                    k.a(context, toString(), "detailUrl is null");
                }
            }
        }, new n.a(this) {
            final /* synthetic */ ExternalInstallReceiver b;

            public void a(s error) {
                k.a(context, toString(), "get detail error: ");
            }
        });
        getAppUrlRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(context));
        com.meizu.volley.b.a(context).a().a(getAppUrlRequest);
        return true;
    }

    private boolean a(final Context context, String pkgName, long appId) {
        if (TextUtils.isEmpty(pkgName) || x.d(context).a(pkgName)) {
            return false;
        }
        if (a(context, pkgName)) {
            return true;
        }
        l mDetailRequest = new FastJsonRequest(new TypeReference<ResultModel<AppStructDetailsItem>>(this) {
            final /* synthetic */ ExternalInstallReceiver a;

            {
                this.a = r1;
            }
        }, 0, RequestConstants.getRuntimeDomainUrl(context, "/public/detail/") + appId, null, new b<ResultModel<AppStructDetailsItem>>(this) {
            final /* synthetic */ ExternalInstallReceiver b;

            public void a(ResultModel<AppStructDetailsItem> response) {
                if (response == null || response.getValue() == null) {
                    k.a(context, toString(), "get detail fail");
                    return;
                }
                AppStructItem item = (AppStructDetailsItem) response.getValue();
                if (item != null && item.price == 0.0d) {
                    d downloadFactory = d.a(context.getApplicationContext());
                    downloadFactory.a(null, downloadFactory.a(item, new com.meizu.cloud.app.downlad.g(2, 1)));
                }
            }
        }, new n.a(this) {
            final /* synthetic */ ExternalInstallReceiver b;

            public void a(s error) {
                k.a(context, toString(), "get detail error: ");
            }
        });
        mDetailRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(context));
        com.meizu.volley.b.a(context).a().a(mDetailRequest);
        return true;
    }
}
