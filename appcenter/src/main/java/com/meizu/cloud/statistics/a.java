package com.meizu.cloud.statistics;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.n.b;
import com.android.volley.s;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structitem.Row1Col2AppItem;
import com.meizu.cloud.app.block.structitem.Row1Col3AppVerItem;
import com.meizu.cloud.app.block.structitem.Row1Col4AppVerItem;
import com.meizu.cloud.app.block.structitem.SingleRowAppItem;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.model.PluginItem;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import java.util.ArrayList;
import java.util.List;

public class a {
    private static a b;
    private Context a;

    protected a(Context context) {
        this.a = context;
    }

    public static synchronized a a(Context context) {
        a aVar;
        synchronized (a.class) {
            if (b == null) {
                b = new a(context.getApplicationContext());
            }
            aVar = b;
        }
        return aVar;
    }

    private void d(AppStructItem appStructItem) {
        if (!x.b(this.a)) {
            l exposureRequest = new FastJsonRequest(new TypeReference<ResultModel<Object>>(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }
            }, "https://t-e.flyme.cn/track/public/exposure", f(appStructItem), new b<ResultModel<Object>>(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void a(ResultModel<Object> response) {
                    if (response != null && response.getCode() == 200) {
                        Log.i("CpdAppAdManager", "cpd exposure send succeed !");
                    }
                }
            }, new com.android.volley.n.a(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void a(s error) {
                }
            });
            exposureRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.a));
            com.meizu.volley.b.a(this.a).a().a(exposureRequest);
        }
    }

    private void e(AppStructItem appStructItem) {
        if (!x.b(this.a)) {
            l detailRequest = new FastJsonRequest(new TypeReference<ResultModel<Object>>(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }
            }, "https://t-e.flyme.cn/track/public/detail", f(appStructItem), new b<ResultModel<Object>>(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void a(ResultModel<Object> response) {
                    if (response != null && response.getCode() == 200) {
                        Log.i("CpdAppAdManager", "cpd detail send succeed !");
                    }
                }
            }, new com.android.volley.n.a(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void a(s error) {
                }
            });
            detailRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.a));
            com.meizu.volley.b.a(this.a).a().a(detailRequest);
        }
    }

    private void b(AppStructItem appStructItem, final int opType) {
        if (!x.b(this.a)) {
            List<com.meizu.volley.b.a> installParams = f(appStructItem);
            installParams.add(new com.meizu.volley.b.a("opType", String.valueOf(opType)));
            l installRequest = new FastJsonRequest(new TypeReference<ResultModel<Object>>(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }
            }, "https://t-e.flyme.cn/track/public/install", installParams, new b<ResultModel<Object>>(this) {
                final /* synthetic */ a b;

                public void a(ResultModel<Object> response) {
                    if (response != null && response.getCode() == 200) {
                        Log.i("CpdAppAdManager", "  cpd install send succeed !op_type: " + opType);
                    }
                }
            }, new com.android.volley.n.a(this) {
                final /* synthetic */ a a;

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

    private List<com.meizu.volley.b.a> f(AppStructItem appStructItem) {
        List<com.meizu.volley.b.a> params = new ArrayList();
        params.add(new com.meizu.volley.b.a("positionId", String.valueOf(appStructItem.position_id)));
        params.add(new com.meizu.volley.b.a("unitId", String.valueOf(appStructItem.unit_id)));
        params.add(new com.meizu.volley.b.a("appId", String.valueOf(appStructItem.id)));
        params.add(new com.meizu.volley.b.a("ts", String.valueOf(System.currentTimeMillis())));
        params.add(new com.meizu.volley.b.a("requestId", appStructItem.request_id));
        params.add(new com.meizu.volley.b.a("version", appStructItem.version));
        if (!TextUtils.isEmpty(appStructItem.kw) || appStructItem.kw_id > 0) {
            params.add(new com.meizu.volley.b.a("kw", appStructItem.kw));
            params.add(new com.meizu.volley.b.a("kwId", String.valueOf(appStructItem.kw_id)));
            params.add(new com.meizu.volley.b.a("trackerType", String.valueOf(appStructItem.tracker_type)));
        }
        return params;
    }

    public void a(AbsBlockItem absBlockItem) {
        if (absBlockItem instanceof SingleRowAppItem) {
            SingleRowAppItem singleRowAppItem = (SingleRowAppItem) absBlockItem;
            if (singleRowAppItem.app != null) {
                a(singleRowAppItem.app);
            }
        } else if (absBlockItem instanceof Row1Col2AppItem) {
            Row1Col2AppItem row1Col2AppItem = (Row1Col2AppItem) absBlockItem;
            if (row1Col2AppItem.app1 != null) {
                a(row1Col2AppItem.app1);
            }
            if (row1Col2AppItem.app2 != null) {
                a(row1Col2AppItem.app2);
            }
        } else if (absBlockItem instanceof Row1Col3AppVerItem) {
            Row1Col3AppVerItem row1Col3AppVerItem = (Row1Col3AppVerItem) absBlockItem;
            if (row1Col3AppVerItem.mAppStructItem1 != null) {
                a(row1Col3AppVerItem.mAppStructItem1);
            }
            if (row1Col3AppVerItem.mAppStructItem2 != null) {
                a(row1Col3AppVerItem.mAppStructItem2);
            }
            if (row1Col3AppVerItem.mAppStructItem3 != null) {
                a(row1Col3AppVerItem.mAppStructItem3);
            }
        } else if (absBlockItem instanceof Row1Col4AppVerItem) {
            Row1Col4AppVerItem row1Col4AppVerItem = (Row1Col4AppVerItem) absBlockItem;
            if (row1Col4AppVerItem.mAppStructItem1 != null) {
                a(row1Col4AppVerItem.mAppStructItem1);
            }
            if (row1Col4AppVerItem.mAppStructItem2 != null) {
                a(row1Col4AppVerItem.mAppStructItem2);
            }
            if (row1Col4AppVerItem.mAppStructItem3 != null) {
                a(row1Col4AppVerItem.mAppStructItem3);
            }
            if (row1Col4AppVerItem.mAppStructItem4 != null) {
                a(row1Col4AppVerItem.mAppStructItem4);
            }
        }
    }

    public void a(AppStructItem appStructItem) {
        if (c(appStructItem) && !appStructItem.is_cpd_exposured) {
            d(appStructItem);
            appStructItem.is_cpd_exposured = true;
        }
    }

    public void b(AppStructItem appStructItem) {
        if (c(appStructItem)) {
            e(appStructItem);
        }
    }

    public void a(AppStructItem appStructItem, int opType) {
        if (c(appStructItem)) {
            b(appStructItem, opType);
        }
    }

    public static boolean c(AppStructItem appStructItem) {
        return appStructItem.unit_id > 0 && !TextUtils.isEmpty(appStructItem.request_id);
    }

    public void a(PluginItem pluginItem) {
        if (!x.b(this.a)) {
            l exposureRequest = new FastJsonRequest(new TypeReference<ResultModel<Object>>(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }
            }, "https://t-e.flyme.cn/track/public/exposure", b(pluginItem), new b<ResultModel<Object>>(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void a(ResultModel<Object> response) {
                    if (response != null && response.getCode() == 200) {
                        Log.i("CpdAppAdManager", "plugin cpd exposure send succeed !");
                    }
                }
            }, new com.android.volley.n.a(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void a(s error) {
                }
            });
            exposureRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.a));
            com.meizu.volley.b.a(this.a).a().a(exposureRequest);
        }
    }

    private List<com.meizu.volley.b.a> b(PluginItem pluginItem) {
        List<com.meizu.volley.b.a> params = new ArrayList();
        params.add(new com.meizu.volley.b.a("positionId", String.valueOf(pluginItem.position_id)));
        params.add(new com.meizu.volley.b.a("unitId", String.valueOf(pluginItem.unit_id)));
        params.add(new com.meizu.volley.b.a("appId", String.valueOf(pluginItem.id)));
        params.add(new com.meizu.volley.b.a("ts", String.valueOf(System.currentTimeMillis())));
        params.add(new com.meizu.volley.b.a("requestId", pluginItem.request_id));
        params.add(new com.meizu.volley.b.a("version", pluginItem.version));
        return params;
    }
}
