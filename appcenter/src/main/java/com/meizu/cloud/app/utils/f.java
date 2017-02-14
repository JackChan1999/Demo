package com.meizu.cloud.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.k;
import com.android.volley.n;
import com.android.volley.s;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo.Columns;
import com.meizu.volley.b;
import com.meizu.volley.b.a;
import com.meizu.volley.c.c;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class f {
    static long[] a = new long[]{2142137411500896221L, -6806118518315004405L, -4880637255783253524L, 3518313652420932814L, -1056543911091379739L};
    static long[] b = new long[]{2734268813764827088L, -5400011204852214929L, 1121111549966670701L, -1713923716866280748L, 8777579849408178177L};

    private static void b(Context context, String packageName, int versionCode) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FEEDBACK_DOWNLOAD_FAILED_APPS", 0);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putInt(packageName, versionCode).apply();
        }
    }

    public static void a(Context context, String packageName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FEEDBACK_DOWNLOAD_FAILED_APPS", 0);
        if (sharedPreferences != null && sharedPreferences.contains(packageName)) {
            sharedPreferences.edit().remove(packageName).apply();
        }
    }

    public static void a(final Context context, String url, String imei, String sn, String appKey, ArrayList<Integer> apps, long[] codes, e wrapper) {
        ArrayList<a> paramPairs = new ArrayList();
        String deviceModel = com.meizu.cloud.app.utils.param.a.a(context).e();
        HashMap<String, String> map = new HashMap();
        map.put(RequestManager.DEVICE_MODEL, deviceModel);
        map.put(RequestManager.SN, sn);
        map.put(RequestManager.IMEI, imei);
        Iterator i$ = apps.iterator();
        while (i$.hasNext()) {
            paramPairs.add(new a(appKey, String.valueOf((Integer) i$.next())));
        }
        int installType = 1;
        if (wrapper.j().f() == 8) {
            installType = 2;
        } else if (wrapper.j().f() == 20) {
            installType = 3;
        }
        map.put(appKey, apps.toString());
        paramPairs.add(RequestManager.generateSign(map, codes));
        paramPairs.addAll(com.meizu.cloud.app.utils.param.a.a(context).b());
        if (wrapper.M() != null) {
            paramPairs.add(new a(Columns.CATEGORY_ID, String.valueOf(wrapper.M()[0])));
            paramPairs.add(new a("page_id", String.valueOf(wrapper.M()[1])));
            paramPairs.add(new a("expand", String.valueOf(wrapper.M()[2])));
            Log.i("page_id", "category_id:" + wrapper.M()[0] + ";page_id:" + wrapper.M()[1] + ";expend:" + wrapper.M()[2]);
        }
        paramPairs.add(new a("install_type", String.valueOf(installType)));
        paramPairs.add(new a("version_codes", String.valueOf(wrapper.F() ? wrapper.G() : wrapper.h())));
        final e eVar = wrapper;
        final e eVar2 = wrapper;
        final Context context2 = context;
        b.a(context).a().a(new c<ResultModel<JSONObject>>(1, url, paramPairs, new n.b<ResultModel<JSONObject>>() {
            public void a(ResultModel<JSONObject> resultModel) {
                Log.w("FeedbackUtils", "install ok");
            }
        }, new n.a() {
            public void a(s error) {
                if (eVar != null) {
                    f.b(context, eVar.g(), eVar.h());
                }
            }
        }) {
            protected /* synthetic */ Object parseResponse(String str) throws k {
                return a(str);
            }

            protected ResultModel<JSONObject> a(String response) throws k {
                ResultModel<JSONObject> resultModel = JSONUtils.parseResultModel(response, new TypeReference<ResultModel<JSONObject>>(this) {
                    final /* synthetic */ AnonymousClass3 a;

                    {
                        this.a = r1;
                    }
                });
                Log.d(f.class.getSimpleName(), "response=" + response);
                if (resultModel == null || resultModel.getCode() != 200) {
                    if (eVar2 != null) {
                        f.b(context2, eVar2.g(), eVar2.h());
                    }
                } else if (eVar2 != null) {
                    f.a(context2, eVar2.g());
                }
                return resultModel;
            }
        });
    }

    public static void a(Context context, long pushId) {
        long[] key;
        HashMap<String, String> map = new HashMap();
        long curTime = System.currentTimeMillis();
        String imei = d.a(context);
        String sn = d.b(context);
        if (x.b(context)) {
            key = b;
        } else {
            key = a;
        }
        map.put(RequestManager.IMEI, imei);
        map.put(RequestManager.SN, sn);
        map.put(RequestManager.TIME, String.valueOf(curTime));
        TypeReference reference = new TypeReference<ResultModel<JSONObject>>() {
        };
        List paramPairs = new ArrayList();
        paramPairs.add(new a("pushId", String.valueOf(pushId)));
        paramPairs.add(new a(RequestManager.IMEI, imei));
        paramPairs.add(new a(RequestManager.SN, sn));
        paramPairs.add(new a(RequestManager.TIME, String.valueOf(curTime)));
        paramPairs.add(RequestManager.generateSign(map, key));
        b.a(context).a().a(new FastJsonRequest(reference, 0, RequestConstants.getRuntimeDomainUrl(context, RequestConstants.FEEDBACK_PUSH_MSG), paramPairs, new n.b() {
            public void a(Object response) {
            }
        }, new n.a() {
            public void a(s error) {
            }
        }));
    }
}
