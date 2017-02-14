package com.meizu.flyme.appcenter.desktopplugin.presenter;

import a.a.a.c;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.downlad.f;
import com.meizu.cloud.app.downlad.f.d;
import com.meizu.cloud.app.downlad.f.e;
import com.meizu.cloud.app.downlad.f.m;
import com.meizu.cloud.app.downlad.f.n;
import com.meizu.cloud.app.downlad.g;
import com.meizu.cloud.app.request.FastJsonParseCacheRequest;
import com.meizu.cloud.app.request.FastJsonParseCacheRequest.CacheCallback;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.ParseListener;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.request.model.PluginDataResultModel;
import com.meizu.cloud.app.request.model.PluginItem;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.utils.s;
import com.meizu.flyme.appcenter.desktopplugin.view.PluginActivity;
import com.meizu.mstore.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tencent.mm.sdk.constants.ConstantsAPI.WXApp;
import java.util.ArrayList;
import java.util.List;

public class b implements com.meizu.cloud.app.downlad.f.b, d, e, CacheCallback {
    public static String c = "PluginForDataPresenter";
    protected static int d = 2;
    protected static int e = 3;
    private com.meizu.flyme.appcenter.desktopplugin.view.b a;
    private List<PluginItem> b;
    protected List<PluginItem> f;
    String g;
    FastJsonParseCacheRequest h;
    public Boolean i = Boolean.valueOf(false);
    public Bitmap j;
    public Boolean k = Boolean.valueOf(false);
    public Boolean l = Boolean.valueOf(false);
    public Boolean m = Boolean.valueOf(false);
    int n = 0;
    int o = 1;
    private List<PluginItem> p;
    private List<PluginItem> q;
    private List<PackageInfo> r;
    private Boolean s = Boolean.valueOf(true);
    private int t = 0;
    private float u;
    private float v;
    private float w;

    public interface a {
        void a();
    }

    public interface b {
        void a(PluginItem pluginItem);
    }

    public void a(com.meizu.flyme.appcenter.desktopplugin.view.b view) {
        this.a = view;
        this.f = new ArrayList();
        this.b = new ArrayList();
        this.p = new ArrayList();
        com.meizu.cloud.app.downlad.d.a(this.a.a().getApplicationContext()).a((m) this, new g(22, 3));
    }

    public void a() {
        f();
        com.meizu.cloud.app.downlad.d.a(this.a.a().getApplicationContext()).b((m) this);
        if (this.h != null) {
            this.h.cancel();
        }
    }

    private void e() {
        this.r = i.b(this.a.a(), 2);
    }

    public void b() {
        this.u = this.a.a().getResources().getDimension(R.dimen.plugin_desktopicon_firsticon_marginleftortop);
        this.v = this.a.a().getResources().getDimension(R.dimen.plugin_desktopicon_icon_interval);
        this.w = this.a.a().getResources().getDimension(R.dimen.plugin_desktopicon_icon_widthorheight);
        BitmapDrawable bd;
        if (this.f.size() == 0) {
            bd = (BitmapDrawable) this.a.a().getResources().getDrawable(R.drawable.plugin_noapp_ic, null);
            if (bd != null) {
                this.j = bd.getBitmap();
                c.a().d(new com.meizu.flyme.appcenter.desktopplugin.presenter.a.b());
                return;
            }
            return;
        }
        final Bitmap errorBitmap = ((BitmapDrawable) this.a.a().getResources().getDrawable(R.drawable.plugin_appdefault_ic_noflyme, null)).getBitmap();
        bd = (BitmapDrawable) this.a.a().getResources().getDrawable(R.drawable.desktop_icon_bg, null);
        if (bd != null) {
            Bitmap bitmapBg = bd.getBitmap();
            ColorFilter colorFilter = new PorterDuffColorFilter(this.a.k(), Mode.SRC_ATOP);
            Paint paint = new Paint();
            paint.setColorFilter(colorFilter);
            this.j = Bitmap.createBitmap(bitmapBg.getWidth(), bitmapBg.getHeight(), Config.ARGB_8888);
            final Canvas canvas = new Canvas(this.j);
            canvas.drawBitmap(bitmapBg, 0.0f, 0.0f, paint);
            final int iconNumber = this.f.size() < 9 ? this.f.size() : 9;
            for (int i = 0; i < iconNumber; i++) {
                final ImageView bitmapImageView = new ImageView(this.a.a().getApplicationContext());
                final int finalI = i;
                if (s.c(this.a.a()).booleanValue()) {
                    Picasso.with(this.a.a().getApplicationContext()).load(((PluginItem) this.f.get(i)).getDrawabeId()).error((int) R.drawable.plugin_appdefault_ic_noflyme).tag(PluginActivity.class).placeholder((int) R.drawable.plugin_appdefault_ic_noflyme).into(bitmapImageView, new Callback(this) {
                        final /* synthetic */ b f;

                        public void onSuccess() {
                            if (this.f.a.a() != null) {
                                Bitmap bitmap = ((BitmapDrawable) bitmapImageView.getDrawable()).getBitmap();
                                if (bitmap == null) {
                                    bitmap = ((BitmapDrawable) this.f.a.a().getApplicationContext().getResources().getDrawable(R.drawable.plugin_appdefault_ic_noflyme, null)).getBitmap();
                                }
                                canvas.drawBitmap(b.a(this.f.a.a().getApplicationContext(), bitmap), (this.f.u + (((float) (finalI % 3)) * this.f.w)) + (((float) (finalI % 3)) * this.f.v), (this.f.u + (((float) (finalI / 3)) * this.f.w)) + (((float) (finalI / 3)) * this.f.v), null);
                                this.f.t = this.f.t + 1;
                                if (this.f.t == iconNumber) {
                                    c.a().d(new com.meizu.flyme.appcenter.desktopplugin.presenter.a.b());
                                    this.f.t = 0;
                                }
                            }
                        }

                        public void onError() {
                            if (this.f.a.a() != null) {
                                canvas.drawBitmap(b.a(this.f.a.a().getApplicationContext(), errorBitmap), (this.f.u + (((float) (finalI % 3)) * this.f.w)) + (((float) (finalI % 3)) * this.f.v), (this.f.u + (((float) (finalI / 3)) * this.f.w)) + (((float) (finalI / 3)) * this.f.v), null);
                                this.f.t = this.f.t + 1;
                                if (this.f.t == iconNumber) {
                                    c.a().d(new com.meizu.flyme.appcenter.desktopplugin.presenter.a.b());
                                    this.f.t = 0;
                                }
                            }
                        }
                    });
                } else if (((PluginItem) this.f.get(i)).isIconError()) {
                    canvas.drawBitmap(a(this.a.a().getApplicationContext(), errorBitmap), (this.u + (((float) (finalI % 3)) * this.w)) + (((float) (finalI % 3)) * this.v), (this.u + (((float) (finalI / 3)) * this.w)) + (((float) (finalI / 3)) * this.v), null);
                    this.t++;
                    if (this.t == iconNumber) {
                        c.a().d(new com.meizu.flyme.appcenter.desktopplugin.presenter.a.b());
                        this.t = 0;
                    }
                } else {
                    Picasso.with(this.a.a().getApplicationContext()).load(((PluginItem) this.f.get(i)).getIcon()).tag(PluginActivity.class).into(bitmapImageView, new Callback(this) {
                        final /* synthetic */ b f;

                        public void onSuccess() {
                            if (this.f.a.a() != null) {
                                Bitmap bitmap = ((BitmapDrawable) bitmapImageView.getDrawable()).getBitmap();
                                if (bitmap == null) {
                                    bitmap = ((BitmapDrawable) this.f.a.a().getApplicationContext().getResources().getDrawable(R.drawable.plugin_appdefault_ic_noflyme, null)).getBitmap();
                                }
                                canvas.drawBitmap(b.a(this.f.a.a().getApplicationContext(), bitmap), (this.f.u + (((float) (finalI % 3)) * this.f.w)) + (((float) (finalI % 3)) * this.f.v), (this.f.u + (((float) (finalI / 3)) * this.f.w)) + (((float) (finalI / 3)) * this.f.v), null);
                                this.f.t = this.f.t + 1;
                                if (this.f.t == iconNumber) {
                                    c.a().d(new com.meizu.flyme.appcenter.desktopplugin.presenter.a.b());
                                    this.f.t = 0;
                                }
                            }
                        }

                        public void onError() {
                            if (this.f.a.a() != null) {
                                canvas.drawBitmap(b.a(this.f.a.a().getApplicationContext(), errorBitmap), (this.f.u + (((float) (finalI % 3)) * this.f.w)) + (((float) (finalI % 3)) * this.f.v), (this.f.u + (((float) (finalI / 3)) * this.f.w)) + (((float) (finalI / 3)) * this.f.v), null);
                                this.f.t = this.f.t + 1;
                                if (this.f.t == iconNumber) {
                                    c.a().d(new com.meizu.flyme.appcenter.desktopplugin.presenter.a.b());
                                    this.f.t = 0;
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    public static Bitmap a(Context context, Bitmap bitmap) {
        int smallIconWidthOrHeight = (int) context.getResources().getDimension(R.dimen.plugin_desktopicon_icon_widthorheight);
        return Bitmap.createScaledBitmap(bitmap, smallIconWidthOrHeight, smallIconWidthOrHeight, true);
    }

    private void f() {
        if (this.f != null && this.l.booleanValue()) {
            int i;
            for (i = 0; i < this.f.size(); i++) {
                if (((PluginItem) this.f.get(i)).getState() == 3) {
                    this.f.remove(this.f.get(i));
                }
            }
            for (i = 0; i < this.f.size(); i++) {
                ((PluginItem) this.f.get(i)).setState(0);
            }
            s.d(this.a.a(), JSON.toJSONString(this.f));
        }
    }

    public List<PluginItem> c() {
        int i;
        String lastListStr = s.b(this.a.a());
        TypeReference<List<PluginItem>> typeRef = new TypeReference<List<PluginItem>>(this) {
            final /* synthetic */ b a;

            {
                this.a = r1;
            }
        };
        List<PluginItem> saveList = (List) JSON.parseObject(lastListStr, new TypeReference<List<PluginItem>>(this) {
            final /* synthetic */ b a;

            {
                this.a = r1;
            }
        }, new Feature[0]);
        for (i = 0; i < saveList.size(); i++) {
            ((PluginItem) saveList.get(i)).isFromCache = Boolean.valueOf(true);
        }
        e();
        List<PluginItem> temp = new ArrayList();
        temp.addAll(saveList);
        for (int j = 0; j < temp.size(); j++) {
            for (i = 0; i < this.r.size(); i++) {
                if (((PluginItem) temp.get(j)).getPackage_name().equals(((PackageInfo) this.r.get(i)).packageName)) {
                    saveList.remove((PluginItem) temp.get(j));
                }
            }
        }
        temp.clear();
        return saveList;
    }

    private void a(final a loadDataCallBack) {
        this.g = "http://api-app.meizu.com/apps/public/collection/detail/desktop/5000";
        List params = new ArrayList();
        params.add(new com.meizu.volley.b.a("start", String.valueOf(this.n)));
        params.add(new com.meizu.volley.b.a("max", String.valueOf(18)));
        params.add(new com.meizu.volley.b.a("mp", RequestManager.VALUE_APPS_FLYME5));
        e();
        TypeReference<ResultModel<PluginDataResultModel<PluginItem>>> typeRef = new TypeReference<ResultModel<PluginDataResultModel<PluginItem>>>(this) {
            final /* synthetic */ b a;

            {
                this.a = r1;
            }
        };
        this.h = new FastJsonParseCacheRequest(this.g, 0, params, new ParseListener(this) {
            final /* synthetic */ b a;

            {
                this.a = r1;
            }

            public Object onParseResponse(String json) {
                if (!TextUtils.isEmpty(json)) {
                    s.a(this.a.a.a(), this.a.g, json, this.a.n, 9);
                }
                return this.a.a(json);
            }
        }, new com.android.volley.n.b(this) {
            final /* synthetic */ b b;

            public void a(Object response) {
                if (this.b.a.a() != null) {
                    ResultModel<PluginDataResultModel<PluginItem>> resultModel = (ResultModel) response;
                    if (resultModel != null && resultModel.getCode() == 200) {
                        List<PluginItem> temp;
                        int i;
                        int j;
                        if (!(resultModel.getValue() == null || ((PluginDataResultModel) resultModel.getValue()).cpd_data == null)) {
                            temp = new ArrayList();
                            temp = ((PluginDataResultModel) resultModel.getValue()).cpd_data;
                            this.b.e();
                            for (i = 0; i < this.b.r.size(); i++) {
                                for (j = 0; j < temp.size(); j++) {
                                    if (((PluginItem) temp.get(j)).getPackage_name().equals(((PackageInfo) this.b.r.get(i)).packageName)) {
                                        temp.remove((PluginItem) temp.get(j));
                                    }
                                }
                            }
                            for (i = 0; i < this.b.f.size(); i++) {
                                for (j = 0; j < temp.size(); j++) {
                                    if (((PluginItem) temp.get(j)).getPackage_name().equals(((PluginItem) this.b.f.get(i)).package_name)) {
                                        temp.remove((PluginItem) temp.get(j));
                                    }
                                }
                            }
                            this.b.p.addAll(temp);
                        }
                        if (!(resultModel.getValue() == null || ((PluginDataResultModel) resultModel.getValue()).data == null)) {
                            ArrayList arrayList = new ArrayList();
                            temp = ((PluginDataResultModel) resultModel.getValue()).data;
                            this.b.e();
                            for (i = 0; i < this.b.r.size(); i++) {
                                for (j = 0; j < temp.size(); j++) {
                                    if (((PluginItem) temp.get(j)).getPackage_name().equals(((PackageInfo) this.b.r.get(i)).packageName)) {
                                        PluginItem tempPluginItem = (PluginItem) temp.get(j);
                                        if (tempPluginItem.type != b.d || this.b.p.size() <= 0) {
                                            temp.remove(tempPluginItem);
                                        } else {
                                            PluginItem tobeAdd = (PluginItem) this.b.p.get(0);
                                            this.b.p.remove(tobeAdd);
                                            tobeAdd.setPosition_id(tempPluginItem.getPosition_id());
                                            tobeAdd.setVersion(tempPluginItem.getVersion());
                                            tobeAdd.setType(b.d);
                                            temp.set(temp.indexOf(tempPluginItem), tobeAdd);
                                        }
                                    }
                                }
                            }
                            for (i = 0; i < this.b.f.size(); i++) {
                                for (j = 0; j < temp.size(); j++) {
                                    if (((PluginItem) temp.get(j)).getPackage_name().equals(((PluginItem) this.b.f.get(i)).package_name)) {
                                        temp.remove((PluginItem) temp.get(j));
                                    }
                                }
                            }
                            this.b.b.addAll(temp);
                        }
                        if (resultModel.getValue() != null) {
                            this.b.s = Boolean.valueOf(((PluginDataResultModel) resultModel.getValue()).more);
                            loadDataCallBack.a();
                        } else if (this.b.b.size() == 0) {
                            this.b.i = Boolean.valueOf(true);
                        }
                    } else if (resultModel == null || resultModel.getCode() != 200) {
                        c.a().d(new com.meizu.flyme.appcenter.desktopplugin.presenter.a.a(this.b.g()));
                    }
                }
            }
        }, new com.android.volley.n.a(this) {
            final /* synthetic */ b a;

            {
                this.a = r1;
            }

            public void a(com.android.volley.s error) {
                this.a.i = Boolean.valueOf(true);
                c.a().d(new com.meizu.flyme.appcenter.desktopplugin.presenter.a.a(this.a.g()));
            }
        });
        com.meizu.cloud.app.utils.param.a.a(this.a.a()).a();
        this.h.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.a.a()));
        this.h.setShouldCache(true);
        this.h.setCacheListener(this);
        com.meizu.volley.b.a(this.a.a()).a().a(this.h);
        this.n += 18;
    }

    private ResultModel<PluginDataResultModel<PluginItem>> a(String json) {
        ResultModel<PluginDataResultModel<PluginItem>> resultModel = JSONUtils.parseResultModel(json, new TypeReference<ResultModel<PluginDataResultModel<PluginItem>>>(this) {
            final /* synthetic */ b a;

            {
                this.a = r1;
            }
        });
        boolean success = (resultModel == null || resultModel.getCode() != 200 || resultModel.getValue() == null || ((PluginDataResultModel) resultModel.getValue()).data == null) ? false : true;
        if (success) {
            s.a(this.a.a(), this.g, "");
        }
        return resultModel;
    }

    public void a(final Boolean showNetworkFirst) {
        if (com.meizu.cloud.app.utils.m.b(this.a.a())) {
            int showCount;
            boolean isFirstopen = s.c(this.a.a()).booleanValue();
            if (isFirstopen) {
                this.f.clear();
            }
            if (showNetworkFirst.booleanValue()) {
                List temp;
                showCount = 12;
                if (isFirstopen) {
                    temp = g();
                } else {
                    temp = c();
                }
                if (temp != null) {
                    c.a().d(new com.meizu.flyme.appcenter.desktopplugin.presenter.a.a(temp));
                }
            } else {
                showCount = 9;
            }
            if (this.b.size() >= showCount) {
                for (int i = 0; i < showCount; i++) {
                    PluginItem toBeAdd = (PluginItem) this.b.get(0);
                    this.f.add(toBeAdd);
                    this.b.remove(toBeAdd);
                }
                a(showNetworkFirst.booleanValue());
                c.a().d(new com.meizu.flyme.appcenter.desktopplugin.presenter.a.a(this.f));
                s.a(this.a.a(), Boolean.valueOf(false));
            } else if (this.s.booleanValue()) {
                this.o++;
                a(new a(this) {
                    final /* synthetic */ b b;

                    public void a() {
                        this.b.a(showNetworkFirst);
                    }
                });
            } else {
                this.f.addAll(this.b);
                this.b.clear();
                a(showNetworkFirst.booleanValue());
                c.a().d(new com.meizu.flyme.appcenter.desktopplugin.presenter.a.a(this.f));
                this.i = Boolean.valueOf(true);
                s.a(this.a.a(), Boolean.valueOf(false));
            }
        } else if (s.c(this.a.a()).booleanValue()) {
            this.q = g();
            this.f = this.q;
            c.a().d(new com.meizu.flyme.appcenter.desktopplugin.presenter.a.a(this.f));
        } else {
            this.f = c();
            this.l = Boolean.valueOf(true);
            c.a().d(new com.meizu.flyme.appcenter.desktopplugin.presenter.a.a(this.f));
        }
    }

    public void a(PluginItem pluginItem, Boolean isDelete, b updateOneItemCallBack) {
        if (pluginItem.getType() != d) {
            b(pluginItem, isDelete, updateOneItemCallBack);
        } else if (this.p.size() > 0) {
            PluginItem tobeAdd = (PluginItem) this.p.get(0);
            this.p.remove(tobeAdd);
            tobeAdd.setPosition_id(pluginItem.getPosition_id());
            tobeAdd.setVersion(pluginItem.getVersion());
            tobeAdd.setType(d);
            this.f.set(this.f.indexOf(pluginItem), tobeAdd);
            updateOneItemCallBack.a(tobeAdd);
        } else {
            b(pluginItem, isDelete, updateOneItemCallBack);
        }
    }

    public void a(boolean updateView) {
        List<com.meizu.cloud.app.downlad.e> downloadWrapperList = com.meizu.cloud.app.downlad.d.a(this.a.a()).e();
        int i = 0;
        while (i < downloadWrapperList.size()) {
            for (int j = this.f.size() - 1; j >= 0; j--) {
                if (((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).g().equals(((PluginItem) this.f.get(j)).getPackage_name())) {
                    if (((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).f() == f.c.TASK_PAUSED) {
                        ((PluginItem) this.f.get(j)).setProgerss((float) ((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).o());
                        ((PluginItem) this.f.get(j)).setState(2);
                    } else if (((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).f() == f.c.TASK_CREATED) {
                        ((PluginItem) this.f.get(j)).setProgerss((float) ((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).o());
                        ((PluginItem) this.f.get(j)).setState(1);
                    } else if (((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).f() == f.c.TASK_WAITING) {
                        ((PluginItem) this.f.get(j)).setProgerss((float) ((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).o());
                        ((PluginItem) this.f.get(j)).setState(1);
                    } else if (((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).f() == f.c.TASK_ERROR) {
                        ((PluginItem) this.f.get(j)).setState(0);
                    } else if (((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).f() == f.c.TASK_STARTED) {
                        ((PluginItem) this.f.get(j)).setProgerss((float) ((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).o());
                        ((PluginItem) this.f.get(j)).setState(1);
                    } else if (((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).f() == f.c.TASK_COMPLETED) {
                        ((PluginItem) this.f.get(j)).setProgerss((float) ((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).o());
                        ((PluginItem) this.f.get(j)).setState(3);
                    } else if (((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).f() == f.f.INSTALL_START) {
                        ((PluginItem) this.f.get(j)).setState(3);
                    } else if (((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).f() == f.f.INSTALL_FAILURE) {
                        ((PluginItem) this.f.get(j)).setState(0);
                    } else if (((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).f() == f.f.INSTALL_SUCCESS) {
                    }
                    if (updateView && ((com.meizu.cloud.app.downlad.e) downloadWrapperList.get(i)).f() != f.f.INSTALL_SUCCESS) {
                        this.a.a((PluginItem) this.f.get(j));
                    }
                }
            }
            i++;
        }
    }

    private void b(final PluginItem pluginItem, final Boolean isDelete, final b updateOneItemCallBack) {
        if (this.b.size() > 0) {
            PluginItem tobeAdd = (PluginItem) this.b.get(0);
            this.b.remove(tobeAdd);
            int pos = this.f.indexOf(pluginItem);
            if (pos != -1) {
                this.f.set(pos, tobeAdd);
                updateOneItemCallBack.a(tobeAdd);
            }
        } else if (this.s.booleanValue()) {
            a(new a(this) {
                final /* synthetic */ b d;

                public void a() {
                    this.d.a(pluginItem, isDelete, updateOneItemCallBack);
                }
            });
        } else if (isDelete.booleanValue()) {
            this.f.remove(pluginItem);
            updateOneItemCallBack.a(null);
        } else {
            updateOneItemCallBack.a(null);
        }
    }

    private PluginItem b(String packageName) {
        for (int i = 0; i < this.f.size(); i++) {
            if (((PluginItem) this.f.get(i)).getPackage_name().equals(packageName)) {
                return (PluginItem) this.f.get(i);
            }
        }
        return null;
    }

    private List<PluginItem> g() {
        List<PluginItem> pluginItemList = new ArrayList();
        PluginItem pluginItem1 = new PluginItem();
        pluginItem1.setState(0);
        pluginItem1.setName("微信");
        pluginItem1.setPackage_name(WXApp.WXAPP_PACKAGE_NAME);
        pluginItem1.setDrawabeId(R.drawable.plugin_nonetwork1);
        PluginItem pluginItem2 = new PluginItem();
        pluginItem2.setState(0);
        pluginItem2.setName("QQ");
        pluginItem2.setPackage_name("com.tencent.mobileqq");
        pluginItem2.setDrawabeId(R.drawable.plugin_nonetwork2);
        PluginItem pluginItem3 = new PluginItem();
        pluginItem3.setState(0);
        pluginItem3.setName("手机淘宝");
        pluginItem3.setPackage_name("com.taobao.taobao");
        pluginItem3.setDrawabeId(R.drawable.plugin_nonetwork3);
        PluginItem pluginItem4 = new PluginItem();
        pluginItem4.setState(0);
        pluginItem4.setName("爱奇艺");
        pluginItem4.setPackage_name("com.qiyi.video");
        pluginItem4.setDrawabeId(R.drawable.plugin_nonetwork4);
        PluginItem pluginItem5 = new PluginItem();
        pluginItem5.setState(0);
        pluginItem5.setName("支付宝");
        pluginItem5.setPackage_name("com.eg.android.AlipayGphone");
        pluginItem5.setDrawabeId(R.drawable.plugin_nonetwork5);
        PluginItem pluginItem6 = new PluginItem();
        pluginItem6.setState(0);
        pluginItem6.setName("腾讯新闻");
        pluginItem6.setPackage_name("com.tencent.news");
        pluginItem6.setDrawabeId(R.drawable.plugin_nonetwork6);
        PluginItem pluginItem7 = new PluginItem();
        pluginItem7.setState(0);
        pluginItem7.setName("糯米网");
        pluginItem7.setPackage_name("com.nuomi");
        pluginItem7.setDrawabeId(R.drawable.plugin_nonetwork7);
        PluginItem pluginItem8 = new PluginItem();
        pluginItem8.setState(0);
        pluginItem8.setName("携程旅行");
        pluginItem8.setPackage_name("ctrip.android.view");
        pluginItem8.setDrawabeId(R.drawable.plugin_nonetwork8);
        PluginItem pluginItem9 = new PluginItem();
        pluginItem9.setState(0);
        pluginItem9.setName("高德地图");
        pluginItem9.setPackage_name("com.autonavi.minimap");
        pluginItem9.setDrawabeId(R.drawable.plugin_nonetwork9);
        pluginItemList.add(pluginItem1);
        pluginItemList.add(pluginItem2);
        pluginItemList.add(pluginItem3);
        pluginItemList.add(pluginItem4);
        pluginItemList.add(pluginItem5);
        pluginItemList.add(pluginItem6);
        pluginItemList.add(pluginItem7);
        pluginItemList.add(pluginItem8);
        pluginItemList.add(pluginItem9);
        e();
        for (int i = 0; i < this.r.size(); i++) {
            for (int j = 0; j < pluginItemList.size(); j++) {
                if (((PluginItem) pluginItemList.get(j)).getPackage_name().equals(((PackageInfo) this.r.get(i)).packageName)) {
                    pluginItemList.remove((PluginItem) pluginItemList.get(j));
                }
            }
        }
        return pluginItemList;
    }

    public List<PluginItem> d() {
        return this.f;
    }

    public void onDownloadStateChanged(com.meizu.cloud.app.downlad.e wrapper) {
        int location = this.f.indexOf(b(wrapper.g()));
        if (location != -1) {
            PluginItem pluginItem = (PluginItem) this.f.get(location);
            if (wrapper.f() == f.c.TASK_ERROR) {
                if (pluginItem != null) {
                    pluginItem.setState(0);
                    this.a.a(pluginItem);
                }
            } else if (wrapper.f() == f.c.TASK_PAUSED) {
                if (pluginItem != null) {
                    pluginItem.setProgerss((float) wrapper.o());
                    pluginItem.setState(2);
                    this.a.a(pluginItem);
                }
            } else if (wrapper.f() == f.c.TASK_RESUME) {
                if (pluginItem != null) {
                    pluginItem.setProgerss((float) wrapper.o());
                    pluginItem.setState(1);
                    this.a.a(pluginItem);
                }
            } else if (wrapper.f() == f.c.TASK_COMPLETED) {
                if (pluginItem != null) {
                    pluginItem.setState(3);
                    this.a.a(pluginItem);
                }
            } else if (wrapper.f() == f.c.TASK_WAITING) {
                if (pluginItem != null) {
                    pluginItem.setProgerss((float) wrapper.o());
                    pluginItem.setState(1);
                    this.a.a(pluginItem);
                }
            } else if (wrapper.f() == f.c.TASK_STARTED) {
                if (pluginItem != null) {
                    pluginItem.setProgerss((float) wrapper.o());
                    pluginItem.setState(1);
                    this.a.a(pluginItem);
                }
            } else if (wrapper.f() == f.c.TASK_CREATED && pluginItem != null) {
                pluginItem.setState(1);
                this.a.a(pluginItem);
            }
        }
    }

    public void onDownloadProgress(com.meizu.cloud.app.downlad.e wrapper) {
        PluginItem pluginItem = b(wrapper.g());
        if (this.f.indexOf(pluginItem) != -1 && pluginItem != null) {
            pluginItem.setProgerss((float) wrapper.o());
            pluginItem.setState(1);
            this.a.a(pluginItem);
        }
    }

    public void onInstallStateChange(com.meizu.cloud.app.downlad.e wrapper) {
        PluginItem pluginItem = b(wrapper.g());
        if (wrapper.f() == f.f.INSTALL_START) {
            if (pluginItem != null) {
                pluginItem.setProgerss((float) wrapper.o());
                pluginItem.setState(3);
                this.a.a(pluginItem);
            }
        } else if (wrapper.f() == f.f.INSTALL_SUCCESS) {
            if (pluginItem != null) {
                View v2 = this.a.b(pluginItem);
                pluginItem.setProgerss((float) wrapper.o());
                pluginItem.setState(4);
                this.a.a(pluginItem);
                final View finalV = v2;
                a(pluginItem, Boolean.valueOf(false), new b(this) {
                    final /* synthetic */ b b;

                    public void a(PluginItem updatePluginItem) {
                        if (updatePluginItem != null && finalV != null) {
                            this.b.a.a(finalV, updatePluginItem);
                        }
                    }
                });
            }
        } else if (wrapper.f() == f.f.INSTALL_FAILURE && pluginItem != null) {
            pluginItem.setState(0);
            this.a.a(pluginItem);
        }
    }

    public void onFetchStateChange(com.meizu.cloud.app.downlad.e wrapper) {
        PluginItem pluginItem = b(wrapper.g());
        if (wrapper.f() == n.FETCHING) {
            if (pluginItem != null) {
                pluginItem.setState(1);
                this.a.a(pluginItem);
            }
        } else if (wrapper.f() == n.FAILURE) {
            if (pluginItem != null) {
                pluginItem.setState(0);
                this.a.a(pluginItem);
            }
        } else if (wrapper.f() == n.SUCCESS) {
            if (pluginItem != null) {
                pluginItem.setState(1);
                this.a.a(pluginItem);
            }
        } else if (wrapper.f() == n.CANCEL && pluginItem != null) {
            pluginItem.setState(0);
            this.a.a(pluginItem);
        }
    }

    public Object onParseCache() {
        return a(s.a(this.a.a(), this.g, this.n, 9));
    }

    public void onCacheDateReceived(String date) {
    }
}
