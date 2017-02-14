package com.meizu.cloud.base.app;

import a.a.a.c;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.alibaba.fastjson.TypeReference;
import com.meizu.cloud.app.c.e;
import com.meizu.cloud.app.core.k;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.u;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.RecommendAppStructItem;
import com.meizu.cloud.app.request.structitem.RecommendRowItem;
import com.meizu.cloud.app.widget.LoadDataView;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.a.d;
import com.meizu.cloud.statistics.b;
import com.meizu.e.RequeseParams;

import flyme.support.v7.widget.LinearLayoutManager;
import flyme.support.v7.widget.MzRecyclerView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseWizardActivityV2 extends FragmentActivity implements OnClickListener {
    protected MzRecyclerView j;
    protected a k;
    protected RelativeLayout l;
    protected LoadDataView m;
    protected String n;
    protected t o;

    public static abstract class a extends d<RecommendRowItem> {
        protected Context a;

        public a(Context context) {
            this.a = context;
        }

        public int a() {
            int count = 0;
            if (d() > 0) {
                for (RecommendRowItem rowItem : e()) {
                    if (!(rowItem == null || rowItem.data == null)) {
                        count += rowItem.data.size();
                    }
                }
            }
            return count;
        }

        public ArrayList<RecommendAppStructItem> c() {
            ArrayList<RecommendAppStructItem> result = new ArrayList();
            if (d() > 0) {
                for (RecommendRowItem rowItem : e()) {
                    if (!(rowItem == null || rowItem.data == null)) {
                        Iterator i$ = rowItem.data.iterator();
                        while (i$.hasNext()) {
                            RecommendAppStructItem structItem = (RecommendAppStructItem) i$.next();
                            if (structItem.isChecked) {
                                result.add(structItem);
                            }
                        }
                    }
                }
            }
            return result;
        }
    }

    protected abstract void h();

    protected abstract String k();

    protected abstract a l();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.o = new t(this, new u());
        m();
        a(k());
        BaseApplication.a(this, true, null);
        this.n = "new_user_guide";
        this.o.a(this.n);
        c.a().a((Object) this);
    }

    private void m() {
        setContentView(g.wizard_activity_layout2);
        this.m = (LoadDataView) findViewById(f.load_data_view);
        this.j = (MzRecyclerView) findViewById(f.recyclerView);
        this.j.setVisibility(4);
        this.j.setHasFixedSize(true);
        this.j.setLayoutManager(new LinearLayoutManager(this));
        this.j.setSelector(new ColorDrawable(0));
        this.j.setClipChildren(false);
        this.j.setClipToPadding(false);
        this.l = (RelativeLayout) findViewById(f.ll_wizard_bottom_bar);
        this.l.setVisibility(4);
        Button skipButton = (Button) findViewById(f.btnSkip);
        Button installButton = (Button) findViewById(f.btnInstall);
        installButton.setTextColor(getResources().getColor(com.meizu.cloud.b.a.c.theme_color));
        if (getIntent() != null && "com.meizu.mstore.wizard.activity".equals(getIntent().getAction())) {
            skipButton.setText(getString(i.skip2));
        }
        skipButton.setOnClickListener(this);
        installButton.setOnClickListener(this);
        this.l.setBackground(com.meizu.common.util.c.a(this));
        h();
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        com.meizu.cloud.app.utils.u.a((FragmentActivity) this, false);
    }

    protected void onDestroy() {
        super.onDestroy();
        com.meizu.cloud.app.utils.u.a((FragmentActivity) this, true);
        c.a().c(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == f.btnSkip) {
            getSharedPreferences("setting", 0).edit().putBoolean("show_user_guide_5.0", false).apply();
            b.a().a("skip_new_user_guide", this.n, null);
            finish();
        } else if (id == f.btnInstall) {
            getSharedPreferences("setting", 0).edit().putBoolean("show_user_guide_5.0", false).apply();
            b.a().a("install_new_user_guide", this.n, null);
            g();
        }
    }

    protected void g() {
        if (this.k != null) {
            ArrayList<RecommendAppStructItem> checkedItems = this.k.c();
            if (checkedItems != null) {
                if (checkedItems.size() == this.k.a()) {
                    b.a().a("install_all_new_user_guide", this.n, null);
                }
                Iterator i$ = checkedItems.iterator();
                while (i$.hasNext()) {
                    RecommendAppStructItem appStructItem = (RecommendAppStructItem) i$.next();
                    appStructItem.page_info = new int[]{0, 16, 0};
                    appStructItem.install_page = this.n;
                }
                k performAction = new k((AppStructItem[]) checkedItems.toArray(new RecommendAppStructItem[checkedItems.size()]));
                performAction.a(new k.a().c(true));
                this.o.a(performAction);
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

    protected void a(List<RecommendRowItem> list) {
        final ArrayList<RecommendRowItem> rowItemArrayList = new ArrayList();
        if (list != null && list.size() > 0) {
            for (RecommendRowItem rowItem : list) {
                if (rowItem.data != null) {
                    int useful = 0;
                    for (int i = rowItem.data.size() - 1; i >= 0; i--) {
                        RecommendAppStructItem structItem = (RecommendAppStructItem) rowItem.data.get(i);
                        if (structItem == null || com.meizu.cloud.app.core.i.a((Context) this, structItem.package_name) != null) {
                            rowItem.data.remove(i);
                        } else {
                            useful++;
                        }
                    }
                    if (useful >= 3) {
                        rowItemArrayList.add(rowItem);
                    }
                }
            }
        }
        runOnUiThread(new Runnable(this) {
            final /* synthetic */ BaseWizardActivityV2 b;

            public void run() {
                if (!this.b.isDestroyed()) {
                    if (rowItemArrayList == null || rowItemArrayList.size() < 3) {
                        this.b.i();
                        return;
                    }
                    this.b.m.b();
                    this.b.m.c();
                    this.b.l.setVisibility(0);
                    this.b.b(true);
                    this.b.j.setVisibility(0);
                    this.b.k = this.b.l();
                    this.b.j.setAdapter(this.b.k);
                    this.b.k.b(rowItemArrayList);
                }
            }
        });
    }

    protected void i() {
        finish();
    }

    protected void j() {
        if (!isDestroyed()) {
            b(false);
            this.m.b();
            this.m.a(getString(i.server_error), getResources().getDrawable(com.meizu.cloud.b.a.e.mz_ic_empty_view_refresh, null), new OnClickListener(this) {
                final /* synthetic */ BaseWizardActivityV2 a;

                {
                    this.a = r1;
                }

                public void onClick(View v) {
                    this.a.a(this.a.k());
                }
            });
        }
    }

    protected void b(boolean enabled) {
        Button installButton = (Button) findViewById(f.btnInstall);
        installButton.setTextColor(enabled ? getResources().getColor(com.meizu.cloud.b.a.c.theme_color) : getResources().getColor(com.meizu.cloud.b.a.c.info_gray_color));
        installButton.setEnabled(enabled);
    }

    protected void a(final String url) {
        this.m.a(getString(i.loading_text));
        this.m.c();
        this.l.postDelayed(new Runnable(this) {
            final /* synthetic */ BaseWizardActivityV2 a;

            {
                this.a = r1;
            }

            public void run() {
                if (!this.a.isDestroyed() && this.a.k == null) {
                    this.a.l.setVisibility(0);
                    this.a.b(false);
                }
            }
        }, 3000);
        new Thread(new Runnable(this) {
            final /* synthetic */ BaseWizardActivityV2 b;

            public void run() {
                Process.setThreadPriority(10);
                long start = System.nanoTime();
                List<PackageInfo> appInfos = com.meizu.cloud.app.core.i.b(this.b, 5);
                int app_count = 0;
                if (appInfos != null) {
                    app_count = appInfos.size();
                }
                Log.d(Application.class.getSimpleName(), "checkShowUserGuide getApps time=" + (System.nanoTime() - start));
                RequeseParams[] parameters = new RequeseParams[2];
                String str = "app_count";
                if (app_count > 50) {
                    app_count = 50;
                } else if (app_count < 10) {
                    app_count += 9;
                }
                parameters[0] = new RequeseParams(str, app_count);
                parameters[1] = new RequeseParams("version", "v1");
                String str2 = RequestManager.getInstance(this.b).requestGet(url, parameters);
                if (TextUtils.isEmpty(str2)) {
                    this.b.runOnUiThread(new Runnable(this) {
                        final /* synthetic */ AnonymousClass4 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            this.a.b.j();
                        }
                    });
                    return;
                }
                ResultModel<List<RecommendRowItem>> response = JSONUtils.parseResultModel(str2, new TypeReference<ResultModel<List<RecommendRowItem>>>(this) {
                    final /* synthetic */ AnonymousClass4 a;

                    {
                        this.a = r1;
                    }
                });
                if (response == null) {
                    this.b.runOnUiThread(new Runnable(this) {
                        final /* synthetic */ AnonymousClass4 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            this.a.b.j();
                        }
                    });
                    return;
                }
                this.b.getSharedPreferences("setting", 0).edit().putBoolean("show_user_guide_5.0", false).apply();
                if (response.getCode() == 200) {
                    this.b.a((List) response.getValue());
                } else {
                    this.b.runOnUiThread(new Runnable(this) {
                        final /* synthetic */ AnonymousClass4 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            this.a.b.j();
                        }
                    });
                }
            }
        }).start();
    }

    protected void onStart() {
        super.onStart();
        b.a().a("new_user_guide");
    }

    protected void onStop() {
        super.onStop();
        b.a().a("new_user_guide", null);
    }
}
