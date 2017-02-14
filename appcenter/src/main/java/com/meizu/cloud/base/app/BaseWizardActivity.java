package com.meizu.cloud.base.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.meizu.cloud.app.c.e;
import com.meizu.cloud.app.core.k;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.u;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.RecommendAppStructItem;
import com.meizu.cloud.app.widget.LoadDataView;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.a.b;
import com.meizu.e.RequeseParams;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseWizardActivity extends FragmentActivity implements OnClickListener {
    protected GridView j;
    protected a k;
    protected String l;
    protected t m;
    private RelativeLayout n;
    private LoadDataView o;

    public abstract class a extends b<RecommendAppStructItem> implements OnItemClickListener {
        boolean[] a;

        public ArrayList<RecommendAppStructItem> a() {
            ArrayList<RecommendAppStructItem> list = new ArrayList();
            int count = this.a.length;
            for (int i = 0; i < count; i++) {
                if (this.a[i]) {
                    list.add((RecommendAppStructItem) getItem(i));
                }
            }
            return list;
        }
    }

    protected abstract a a(List<RecommendAppStructItem> list);

    protected abstract void g();

    protected abstract String i();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.m = new t(this, new u());
        setContentView(g.wizard_activity_layout);
        this.o = (LoadDataView) findViewById(f.load_data_view);
        this.j = (GridView) findViewById(f.list);
        this.j.setVisibility(4);
        this.n = (RelativeLayout) findViewById(f.ll_wizard_bottom_bar);
        this.n.setVisibility(4);
        Button skipButton = (Button) findViewById(f.btnSkip);
        Button installButton = (Button) findViewById(f.btnInstall);
        installButton.setTextColor(getResources().getColor(c.theme_color));
        if (getIntent() != null && "com.meizu.mstore.wizard.activity".equals(getIntent().getAction())) {
            skipButton.setText(getString(i.skip2));
        }
        skipButton.setOnClickListener(this);
        installButton.setOnClickListener(this);
        this.n.setBackground(com.meizu.common.util.c.a(this));
        g();
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        com.meizu.cloud.app.utils.u.a((FragmentActivity) this, false);
        this.j.setOverScrollMode(1);
        a(i());
        BaseApplication.a(this, true, null);
        this.l = "new_user_guide";
        this.m.a(this.l);
        a.a.a.c.a().a((Object) this);
    }

    protected void onDestroy() {
        super.onDestroy();
        com.meizu.cloud.app.utils.u.a((FragmentActivity) this, true);
        a.a.a.c.a().c(this);
    }

    protected void onResume() {
        super.onResume();
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == f.btnSkip) {
            getSharedPreferences("setting", 0).edit().putBoolean("show_user_guide_5.0", false).apply();
            com.meizu.cloud.statistics.b.a().a("skip_new_user_guide", this.l, null);
            finish();
        } else if (id == f.btnInstall) {
            getSharedPreferences("setting", 0).edit().putBoolean("show_user_guide_5.0", false).apply();
            com.meizu.cloud.statistics.b.a().a("install_new_user_guide", this.l, null);
            if (this.k != null) {
                ArrayList<RecommendAppStructItem> checkedItems = this.k.a();
                if (checkedItems != null) {
                    if (checkedItems.size() == this.k.a.length) {
                        com.meizu.cloud.statistics.b.a().a("install_all_new_user_guide", this.l, null);
                    }
                    Iterator i$ = checkedItems.iterator();
                    while (i$.hasNext()) {
                        RecommendAppStructItem appStructItem = (RecommendAppStructItem) i$.next();
                        appStructItem.page_info = new int[]{0, 16, 0};
                        appStructItem.install_page = this.l;
                    }
                    k performAction = new k((AppStructItem[]) checkedItems.toArray(new RecommendAppStructItem[checkedItems.size()]));
                    performAction.a(new k.a().c(true));
                    this.m.a(performAction);
                }
            }
        }
    }

    public void onEventMainThread(e itemChioceEvent) {
        finish();
    }

    public void finish() {
        if (getIntent() != null && "com.meizu.mstore.wizard.activity".equals(getIntent().getAction())) {
            com.meizu.cloud.app.core.f.a((Context) this).b();
        }
        setResult(-1);
        super.finish();
    }

    protected void a(ArrayList<RecommendAppStructItem> list) {
        if (!isDestroyed()) {
            if (list == null || list.size() <= 0) {
                finish();
                return;
            }
            this.o.b();
            this.o.c();
            this.n.setVisibility(0);
            b(true);
            this.j.setVisibility(0);
            this.k = a((List) list);
            this.j.setAdapter(this.k);
            this.j.setOnItemClickListener(this.k);
        }
    }

    protected void h() {
        if (!isDestroyed()) {
            b(false);
            this.o.b();
            this.o.a(getString(i.server_error), getResources().getDrawable(com.meizu.cloud.b.a.e.mz_ic_empty_view_refresh, null), new OnClickListener(this) {
                final /* synthetic */ BaseWizardActivity a;

                {
                    this.a = r1;
                }

                public void onClick(View v) {
                    this.a.a(this.a.i());
                }
            });
        }
    }

    private void b(boolean enabled) {
        Button installButton = (Button) findViewById(f.btnInstall);
        installButton.setTextColor(enabled ? getResources().getColor(c.theme_color) : getResources().getColor(c.info_gray_color));
        installButton.setEnabled(enabled);
    }

    protected void a(final String url) {
        this.o.a(getString(i.loading_text));
        this.o.c();
        this.n.postDelayed(new Runnable(this) {
            final /* synthetic */ BaseWizardActivity a;

            {
                this.a = r1;
            }

            public void run() {
                if (!this.a.isDestroyed() && this.a.k == null) {
                    this.a.n.setVisibility(0);
                    this.a.b(false);
                }
            }
        }, 3000);
        new Thread(new Runnable(this) {
            final /* synthetic */ BaseWizardActivity b;

            public void run() {
                Process.setThreadPriority(10);
                TypeReference<ResultModel<JSONArray>> anonymousClass1 = new TypeReference<ResultModel<JSONArray>>(this) {
                    final /* synthetic */ AnonymousClass3 a;

                    {
                        this.a = r1;
                    }
                };
                long start = System.nanoTime();
                List<PackageInfo> appInfos = com.meizu.cloud.app.core.i.b(this.b, 5);
                int app_count = 0;
                if (appInfos != null) {
                    app_count = appInfos.size();
                }
                Log.d(Application.class.getSimpleName(), "checkShowUserGuide getApps time=" + (System.nanoTime() - start));
                RequeseParams[] parameters = new RequeseParams[1];
                String str = "app_count";
                if (app_count > 50) {
                    app_count = 50;
                } else if (app_count < 10) {
                    app_count += 9;
                }
                parameters[0] = new RequeseParams(str, app_count);
                String str2 = RequestManager.getInstance(this.b).requestGet(url, parameters);
                if (TextUtils.isEmpty(str2)) {
                    this.b.runOnUiThread(new Runnable(this) {
                        final /* synthetic */ AnonymousClass3 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            this.a.b.h();
                        }
                    });
                    return;
                }
                ResultModel<JSONArray> response = JSONUtils.parseResultModel(str2, anonymousClass1);
                if (response == null) {
                    this.b.runOnUiThread(new Runnable(this) {
                        final /* synthetic */ AnonymousClass3 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            this.a.b.h();
                        }
                    });
                    return;
                }
                this.b.getSharedPreferences("setting", 0).edit().putBoolean("show_user_guide_5.0", false).apply();
                if (response.getCode() == 200) {
                    JSONArray jsonArray = (JSONArray) response.getValue();
                    if (jsonArray == null || jsonArray.size() <= 0) {
                        this.b.a(null);
                        return;
                    }
                    final ArrayList<RecommendAppStructItem> list = new ArrayList();
                    int size = jsonArray.size();
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            RecommendAppStructItem appStructItem = (RecommendAppStructItem) AbstractStrcutItem.createFromJson(RecommendAppStructItem.class, jsonArray.getJSONObject(i));
                            if (appStructItem != null && appStructItem.price == 0.0d && com.meizu.cloud.app.core.i.a(this.b, appStructItem.package_name) == null) {
                                list.add(appStructItem);
                                if (list.size() >= 9) {
                                    break;
                                }
                            }
                        }
                    }
                    this.b.runOnUiThread(new Runnable(this) {
                        final /* synthetic */ AnonymousClass3 b;

                        public void run() {
                            this.b.b.a(list);
                        }
                    });
                    return;
                }
                this.b.runOnUiThread(new Runnable(this) {
                    final /* synthetic */ AnonymousClass3 a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        this.a.b.h();
                    }
                });
            }
        }).start();
    }

    protected void onStart() {
        super.onStart();
        com.meizu.cloud.statistics.b.a().a("new_user_guide");
    }

    protected void onStop() {
        super.onStop();
        com.meizu.cloud.statistics.b.a().a("new_user_guide", null);
    }
}
