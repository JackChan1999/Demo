package com.meizu.flyme.appcenter.desktopplugin.presenter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.n;
import com.meizu.cloud.app.core.k;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.u;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.PluginItem;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.app.utils.s;
import com.meizu.flyme.appcenter.desktopplugin.view.b;
import java.util.ArrayList;
import java.util.List;

public class a extends b {
    public static String a = "PluginDownLoadPresenter";
    protected t b;
    private b p;
    private FastJsonRequest<AppStructItem> q;

    public void a(b view) {
        super.a(view);
        this.p = view;
        this.b = new t((FragmentActivity) this.p.a(), new u());
    }

    public void a() {
        super.a();
        if (this.q != null) {
            this.q.cancel();
        }
        this.p = null;
    }

    public void a(PluginItem pluginItem, View v, List<PluginItem> pluginItemList) {
        this.f = pluginItemList;
        if (s.c(this.p.a()).booleanValue() || !m.b(this.p.a())) {
            this.p.g();
        } else if (pluginItem.getState() != 3) {
            b(pluginItem);
        }
    }

    private String a(PluginItem pluginItem) {
        return RequestConstants.APP_CENTER_HOST + pluginItem.getUrl();
    }

    private void b(final PluginItem pluginItem) {
        if (m.a(this.p.a())) {
            if (pluginItem.getState() == 0) {
                pluginItem.setState(1);
                this.p.a(pluginItem);
            } else if (pluginItem.getState() == 1) {
                pluginItem.setState(2);
                this.p.a(pluginItem);
            } else if (pluginItem.getState() == 2) {
                pluginItem.setState(1);
                this.p.a(pluginItem);
            }
        }
        String downloadUrl = a(pluginItem);
        List params = new ArrayList();
        TypeReference typeRef = new TypeReference<ResultModel<AppStructItem>>(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }
        };
        AppStructItem appStructItem = null;
        d downloadTaskFactory = d.a(this.p.a());
        if (downloadTaskFactory != null) {
            e downloadWrapper = downloadTaskFactory.b(pluginItem.getPackage_name());
            if (downloadWrapper != null) {
                appStructItem = downloadWrapper.m();
            }
        }
        if (appStructItem != null) {
            k performAction = new k(appStructItem);
            k.a performOption = performAction.a();
            performOption.a(Boolean.valueOf(true));
            performAction.a(performOption);
            this.b.a(performAction);
            return;
        }
        if (pluginItem.getState() == 0 && m.a(this.p.a())) {
            pluginItem.setState(1);
            this.p.a(pluginItem);
        }
        this.q = new FastJsonRequest(typeRef, downloadUrl, 0, params, new n.b<ResultModel<AppStructItem>>(this) {
            final /* synthetic */ a b;

            public void a(ResultModel<AppStructItem> response) {
                AppStructItem appStructItem = null;
                if (!(response == null || response.getValue() == null)) {
                    appStructItem = (AppStructItem) response.getValue();
                }
                if (appStructItem != null) {
                    appStructItem.is_fromPlugin = true;
                    if (pluginItem.getType() == b.d) {
                        appStructItem.is_Plugin_CPD = true;
                        appStructItem.unit_id = Integer.parseInt(pluginItem.unit_id);
                        appStructItem.position_id = Integer.parseInt(pluginItem.position_id);
                        appStructItem.id = Integer.parseInt(pluginItem.id);
                        appStructItem.request_id = pluginItem.request_id;
                        appStructItem.version = pluginItem.version;
                    }
                    k performAction = new k(appStructItem);
                    k.a performOption = performAction.a();
                    performOption.a(Boolean.valueOf(true));
                    performAction.a(performOption);
                    this.b.b.a(performAction);
                }
            }
        }, new com.android.volley.n.a(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void a(com.android.volley.s error) {
            }
        });
        this.q.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.p.a()));
        com.meizu.volley.b.a(this.p.a()).a().a(this.q);
    }
}
