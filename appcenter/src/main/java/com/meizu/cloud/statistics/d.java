package com.meizu.cloud.statistics;

import android.content.Context;
import android.util.Log;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.n.a;
import com.android.volley.n.b;
import com.android.volley.s;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import java.util.ArrayList;
import java.util.List;

public class d {
    private static d b;
    private Context a;

    private d(Context context) {
        this.a = context;
    }

    public static synchronized d a(Context context) {
        d dVar;
        synchronized (d.class) {
            if (b == null) {
                b = new d(context.getApplicationContext());
            }
            dVar = b;
        }
        return dVar;
    }

    public void a(AppStructItem appStructItem) {
        if (x.a(this.a) && appStructItem != null && appStructItem.mTrackAdInfo != null) {
            l installRequest = new FastJsonRequest(new TypeReference<ResultModel<Object>>(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }
            }, RequestConstants.TRACK_AD_CLICK_INSTALL, b(appStructItem), new b<ResultModel<Object>>(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public void a(ResultModel<Object> response) {
                    if (response != null && response.getCode() == 200) {
                        Log.i("TrackAdManager", "  cpd install send succeed !");
                    }
                }
            }, new a(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public void a(s error) {
                }
            });
            installRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.a));
            com.meizu.volley.b.a(this.a).a().a(installRequest);
        }
    }

    private List<com.meizu.volley.b.a> b(AppStructItem appStructItem) {
        List<com.meizu.volley.b.a> params = new ArrayList();
        if (!(appStructItem == null || appStructItem.mTrackAdInfo == null)) {
            params.add(new com.meizu.volley.b.a("position_id", String.valueOf(appStructItem.mTrackAdInfo.a)));
            params.add(new com.meizu.volley.b.a("platform_id", String.valueOf(appStructItem.mTrackAdInfo.e)));
            params.add(new com.meizu.volley.b.a("unit_id", String.valueOf(appStructItem.mTrackAdInfo.b)));
            params.add(new com.meizu.volley.b.a("plan_id", String.valueOf(appStructItem.mTrackAdInfo.c)));
            params.add(new com.meizu.volley.b.a("ts", String.valueOf(System.currentTimeMillis())));
            params.add(new com.meizu.volley.b.a("request_id", appStructItem.mTrackAdInfo.d));
            params.add(new com.meizu.volley.b.a("platform_id", String.valueOf(appStructItem.mTrackAdInfo.f)));
            params.add(new com.meizu.volley.b.a(RequestManager.SN, com.meizu.cloud.app.utils.d.b(this.a)));
            params.add(new com.meizu.volley.b.a(RequestManager.IMEI, com.meizu.cloud.app.utils.d.a(this.a)));
            params.add(new com.meizu.volley.b.a(RequestManager.MZOS, String.valueOf(appStructItem.mTrackAdInfo.h)));
            params.add(new com.meizu.volley.b.a("content_id", String.valueOf(appStructItem.mTrackAdInfo.i)));
            params.add(new com.meizu.volley.b.a("content_type", String.valueOf(appStructItem.mTrackAdInfo.j)));
            params.add(new com.meizu.volley.b.a("display_type", String.valueOf(appStructItem.mTrackAdInfo.k)));
            params.add(new com.meizu.volley.b.a("version", String.valueOf(appStructItem.mTrackAdInfo.g)));
        }
        return params;
    }
}
