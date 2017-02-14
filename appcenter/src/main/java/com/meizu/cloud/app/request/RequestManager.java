package com.meizu.cloud.app.request;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.meizu.c.f;
import com.meizu.cloud.a.c;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.app.utils.n;
import com.meizu.e.RequeseParams;
import com.meizu.e.b;
import com.meizu.e.k;
import com.meizu.volley.b.a;
import com.meizu.volley.e;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

public class RequestManager {
    public static final String CUSTOM_ICON = "custom_icon";
    public static final String DEVICE_MODEL = "device_model";
    public static final String FIRMWARE = "firmware";
    public static final String HEAD_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String IMEI = "imei";
    public static final String LANGUAGE = "language";
    public static final String LOCALE = "locale";
    public static final String MPV = "mpv";
    public static final String MZOS = "mzos";
    public static final String NET = "net";
    public static final String OS = "os";
    public static final String SCREEN_SIZE = "screen_size";
    public static final String SN = "sn";
    public static final String TIME = "time";
    public static final String UID = "uid";
    public static final String V = "v";
    public static final String VALUE_APPS_ALI = "apps_ali";
    public static final String VALUE_APPS_FLYME5 = "appsv5";
    public static final String VALUE_GAMES_ALI = "games_ali";
    public static final String VALUE_GAMES_FLYME5 = "gamesv5";
    public static final String VC = "vc";
    public static final String VERSIONCODE = "version_code";
    private static RequestManager sInstance;
    private Context mContext;
    private b mHttpClient = new b(this.mContext);

    private RequestManager(Context context) {
        this.mContext = context;
    }

    public static RequestManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RequestManager(context.getApplicationContext());
        }
        return sInstance;
    }

    public String getHome(String url, int start, int max) {
        return requestGet(url, new RequeseParams[]{new RequeseParams("start", start), new RequeseParams("max", max)});
    }

    public String getCommonList(String url, int start, int max) {
        return requestGet(url, new RequeseParams[]{new RequeseParams("start", start), new RequeseParams("max", max)});
    }

    public String getCommentList(Context context, String url, int app_id, int start, int max) {
        RequeseParams[] params = new RequeseParams[3];
        params[0] = new RequeseParams("start", start);
        params[1] = new RequeseParams("max", max);
        return requestGet(url, params);
    }

    public String getVersionList(String url, int app_id) {
        return requestGet(url, new RequeseParams[]{new RequeseParams("app_id", app_id)});
    }

    public String getSearchHot(String url) {
        return getSearchHot(url, -1, -1);
    }

    public String getSearchHot(String url, int start, int max) {
        if (start < 0 || max <= 0) {
            return requestGet(url, null);
        }
        return requestGet(url, new RequeseParams[]{new RequeseParams("start", start), new RequeseParams("max", max)});
    }

    public String search(String url, String key) {
        return requestGet(url, new RequeseParams[]{new RequeseParams("q", key)});
    }

    public String search(String url, String key, String searchId, String sessionId) {
        return requestGet(url, new RequeseParams[]{new RequeseParams("q", key), new RequeseParams("searchId", searchId), new RequeseParams("sessionId", sessionId), new RequeseParams("isYunos", String.valueOf(d.h()))});
    }

    public String getCategory(String url) {
        return requestGet(url, null);
    }

    public String requestGet(String url, RequeseParams[] params) {
        return request(url, 0, params);
    }

    public String requestPost(String url, RequeseParams[] params) {
        return request(url, 1, params);
    }

    private String request(String url, int method, RequeseParams[] params) {
        if (params == null) {
            params = new RequeseParams[0];
        }
        List<HashMap<String,String>> postParams = new ArrayList();
        for (int i = 0; i < params.length; i++) {
            postParams.add(new a(params[i].a(), params[i].b()));


        }
        try {
            com.android.volley.toolbox.j<String> requestFuture = com.android.volley.toolbox.j.a();
            l request = new e(new TypeReference<String>() {
            }, method, url, postParams, requestFuture, requestFuture);
            request.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.mContext));
            requestFuture.a(com.meizu.volley.b.a(this.mContext).a().a(request));
            return (String) requestFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e2) {
            e2.printStackTrace();
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
        return null;
    }

    public String requestADStatistics(String url, RequeseParams[] params) {
        if (params == null) {
            params = new RequeseParams[0];
        }
        List<a> postParams = new ArrayList();
        for (int i = 0; i < params.length; i++) {
            postParams.add(new a(params[i].a(), params[i].b()));
        }
        postParams.add(new a(IMEI, d.a(this.mContext)));
        postParams.add(new a(SN, d.b(this.mContext)));
        postParams.add(new a(DEVICE_MODEL, d.e()));
        postParams.add(new a("mac", d.g(this.mContext)));
        String uid = c.c(this.mContext);

        if (!TextUtils.isEmpty(uid)) {
            postParams.add(new a(UID, uid));
        }
        postParams.add(new a("mzos_full", d.h(this.mContext)));
        postParams.add(new a(SCREEN_SIZE, d.c()));
        postParams.add(new a("storage", d.i(this.mContext)));
        postParams.add(new a("root", d.f()));
        postParams.add(new a(NET, m.c(this.mContext)));
        postParams.add(new a("operator", d.j(this.mContext)));
        postParams.add(new a(V, i.e(this.mContext, this.mContext.getPackageName())));
        postParams.add(new a(VC, String.valueOf(i.f(this.mContext, this.mContext.getPackageName()))));
        try {
            com.android.volley.toolbox.j<String> requestFuture = com.android.volley.toolbox.j.a();
            requestFuture.a(com.meizu.volley.b.a(this.mContext).a().a(new e(new TypeReference<String>() {
            }, 1, url, postParams, requestFuture, requestFuture)));
            return (String) requestFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e2) {
            e2.printStackTrace();
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
        return null;
    }

    public String requestPostWithDeviceInfo(String url, RequeseParams[] params) {
        String str = null;
        if (params == null) {
            params = new RequeseParams[0];
        }
        RequeseParams[] postParams = new RequeseParams[(params.length + 6)];
        for (int i = 0; i < params.length; i++) {
            postParams[i] = new RequeseParams(params[i].a(), params[i].b());
        }
        postParams[params.length] = new RequeseParams(OS, 18);
        postParams[params.length + 1] = new RequeseParams(MZOS, "4.0");
        postParams[params.length + 2] = new RequeseParams(SCREEN_SIZE, "1800*1080");
        postParams[params.length + 3] = new RequeseParams(LANGUAGE, "zh_CN");
        postParams[params.length + 4] = new RequeseParams(IMEI, d.a(this.mContext));
        postParams[params.length + 5] = new RequeseParams(SN, d.b(this.mContext));
        try {
            k response = this.mHttpClient.a(url, postParams, null, false);
            if (response != null) {
                str = response.toString();
            }
        } catch (f e) {
            e.printStackTrace();
        } catch (com.meizu.c.e e2) {
            e2.printStackTrace();
        }
        return str;
    }

    public static a generateSign(HashMap<String, String> paramPairs, long[] codes) {
        if (paramPairs == null || paramPairs.isEmpty()) {
            return null;
        }
        List<String> sortCache = new ArrayList();
        for (Entry<String, String> en : paramPairs.entrySet()) {
            sortCache.add(((String) en.getKey()) + "=" + ((String) en.getValue()));
        }
        Collections.sort(sortCache);
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String p : sortCache) {
            if (first) {
                first = false;
            } else {
                sb.append('&');
            }
            sb.append(p);
        }
        sb.append(':').append(new n(codes).toString());
        return new a("sign", com.meizu.cloud.app.utils.l.a(sb.toString(), "UTF-8"));
    }
}
