package com.meizu.cloud.app.downlad;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.s;
import com.meizu.c.d;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.downlad.f.j;
import com.meizu.cloud.app.downlad.f.n;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.request.model.DownloadInfo;
import com.meizu.cloud.app.request.model.LicenseInfoModel;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo.Columns;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.common.app.SlideNotice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class h {
    private String a = h.class.getSimpleName();
    private Context b;

    class a implements com.android.volley.n.a {
        final /* synthetic */ h a;
        private final e b;

        a(h hVar, e wrapper) {
            this.a = hVar;
            this.b = wrapper;
        }

        public void a(s error) {
            error.printStackTrace();
            this.b.a(n.FAILURE, null);
            this.b.J();
            d.a(this.a.b).a(null, this.b);
        }
    }

    class b implements com.android.volley.n.b<ResultModel<DownloadInfo>> {
        final /* synthetic */ h a;
        private final e b;

        b(h hVar, e wrapper) {
            this.a = hVar;
            this.b = wrapper;
        }

        public void a(ResultModel<DownloadInfo> response) {
            if (response == null) {
                Log.e(this.a.a, "server response null");
                this.b.a(n.FAILURE, null);
                this.b.J();
                d.a(this.a.b).a(null, this.b);
            } else if (response.getCode() == 200) {
                if (response.getValue() != null) {
                    DownloadInfo downloadInfo = (DownloadInfo) response.getValue();
                    if (TextUtils.isEmpty(downloadInfo.download_url)) {
                        this.b.a(n.FAILURE, null);
                        this.b.J();
                        d.a(this.a.b).a(null, this.b);
                        return;
                    }
                    this.b.a(downloadInfo);
                    this.b.a(n.SUCCESS, null);
                    d.a(this.a.b).a(null, this.b);
                }
            } else if (response.getCode() == RequestConstants.CODE_APP_NOT_FOUND) {
                this.b.a(n.FAILURE, null);
                this.b.J();
                this.b.a((int) RequestConstants.CODE_APP_NOT_FOUND);
                d.a(this.a.b).a(null, this.b);
            } else if (response.getCode() == RequestConstants.CODE_APP_SIGN_ERROR) {
                this.b.a(n.FAILURE, null);
                this.b.J();
                this.b.a((int) RequestConstants.CODE_APP_SIGN_ERROR);
                this.b.c(response.getMessage());
                d.a(this.a.b).a(null, this.b);
            } else {
                Log.e(this.a.a, "server error code:" + response.getCode());
                this.b.a(n.FAILURE, null);
                this.b.J();
                d.a(this.a.b).a(null, this.b);
            }
        }
    }

    class c implements com.meizu.cloud.d.b {
        final /* synthetic */ h a;
        private e b;

        c(h hVar, e wrapper) {
            this.a = hVar;
            this.b = wrapper;
        }

        public void a(DownloadInfo downloadInfo) {
            this.b.a(true);
            this.b.a(downloadInfo);
            this.b.a(j.SUCCESS, null);
            d.a(this.a.b).a(null, this.b);
        }

        public void a(int errorCode, String errorMsg) {
            if (errorCode != SlideNotice.SHOW_ANIMATION_DURATION) {
                this.b.a(j.FAILURE, null);
                this.b.a(errorCode);
                this.b.c(errorMsg);
                d.a(this.a.b).a(null, this.b);
                return;
            }
            this.b.a(j.CANCEL, null);
            d.a(this.a.b).a(null, this.b);
        }
    }

    public h(Context context) {
        this.b = context;
    }

    public void a(FragmentActivity ui, e downloadWrapper) throws d {
        if (downloadWrapper.A()) {
            c(ui, downloadWrapper);
        } else {
            b(ui, downloadWrapper);
        }
    }

    private void b(FragmentActivity ui, e downloadWrapper) throws d {
        if (downloadWrapper.E()) {
            if (downloadWrapper.Q()) {
                e(downloadWrapper);
            } else if (!downloadWrapper.N()) {
                d(ui, downloadWrapper);
            } else if (downloadWrapper.c() != null) {
                downloadWrapper.a(j.SUCCESS, null);
                d.a(this.b).a(ui, downloadWrapper);
            } else {
                d(ui, downloadWrapper);
            }
        } else if (downloadWrapper.F()) {
            c(downloadWrapper);
        } else {
            b(downloadWrapper);
        }
    }

    private void c(FragmentActivity ui, e downloadWrapper) throws d {
        if (!downloadWrapper.A()) {
            downloadWrapper.a(n.FAILURE, null);
            d.a(this.b).a(null, downloadWrapper);
        } else if (downloadWrapper.E()) {
            if (!downloadWrapper.N()) {
                d(ui, downloadWrapper);
            } else if (downloadWrapper.c() != null) {
                downloadWrapper.a(j.SUCCESS, null);
                d.a(this.b).a(ui, downloadWrapper);
            } else {
                d(ui, downloadWrapper);
            }
        } else if (downloadWrapper.z()) {
            d(downloadWrapper);
        } else {
            b(downloadWrapper);
        }
    }

    private void b(e downloadWrapper) {
        downloadWrapper.a(n.FETCHING, null);
        d.a(this.b).a(null, downloadWrapper);
        ArrayList<com.meizu.volley.b.a> paramPairs = new ArrayList();
        String sn;
        String imei;
        HashMap<String, String> map;
        if (x.a(this.b)) {
            paramPairs.add(new com.meizu.volley.b.a("app_id", String.valueOf(downloadWrapper.i())));
            String appId = String.valueOf(downloadWrapper.i());
            sn = com.meizu.cloud.app.utils.param.a.a(this.b).c();
            imei = com.meizu.cloud.app.utils.param.a.a(this.b).d();
            map = new HashMap();
            map.put("app_id", appId);
            map.put(RequestManager.SN, sn);
            map.put(RequestManager.IMEI, imei);
            paramPairs.add(RequestManager.generateSign(map, com.meizu.cloud.app.utils.c.a.a));
        } else {
            paramPairs.add(new com.meizu.volley.b.a("game_id", String.valueOf(downloadWrapper.i())));
            String gameId = String.valueOf(downloadWrapper.i());
            sn = com.meizu.cloud.app.utils.param.a.a(this.b).c();
            imei = com.meizu.cloud.app.utils.param.a.a(this.b).d();
            map = new HashMap();
            map.put("game_id", gameId);
            map.put(RequestManager.SN, sn);
            map.put(RequestManager.IMEI, imei);
            paramPairs.add(RequestManager.generateSign(map, com.meizu.cloud.app.utils.c.a.b));
        }
        if (downloadWrapper.b.page_info != null) {
            paramPairs.add(new com.meizu.volley.b.a(Columns.CATEGORY_ID, String.valueOf(downloadWrapper.b.page_info[0])));
            paramPairs.add(new com.meizu.volley.b.a("page_id", String.valueOf(downloadWrapper.b.page_info[1])));
            paramPairs.add(new com.meizu.volley.b.a("expand", String.valueOf(downloadWrapper.b.page_info[2])));
            Log.i("page_id", "category_id:" + downloadWrapper.b.page_info[0] + ";page_id:" + downloadWrapper.b.page_info[1] + ";expend:" + downloadWrapper.b.page_info[2]);
        }
        a(downloadWrapper, paramPairs, RequestConstants.getRuntimeDomainUrl(this.b, RequestConstants.FREE_DOWNLOAD));
    }

    private void d(FragmentActivity ui, e downloadWrapper) throws d {
        if (ui != null) {
            downloadWrapper.a(j.PAYING, null);
            d.a(this.b).a(ui, downloadWrapper);
            com.meizu.cloud.d.a mzSystemPayHelper = new com.meizu.cloud.d.a(ui, downloadWrapper.i(), new c(this, downloadWrapper));
            a(mzSystemPayHelper);
            mzSystemPayHelper.a(downloadWrapper.N(), downloadWrapper.i(), downloadWrapper.O(), downloadWrapper.g(), downloadWrapper.h());
            return;
        }
        throw new d();
    }

    private void c(e downloadWrapper) {
        downloadWrapper.a(n.FETCHING, null);
        d.a(this.b).a(null, downloadWrapper);
        ArrayList<com.meizu.volley.b.a> paramPairs = new ArrayList();
        String versionId;
        String sn;
        String imei;
        HashMap<String, String> map;
        if (x.a(this.b)) {
            paramPairs.add(new com.meizu.volley.b.a("app_id", String.valueOf(downloadWrapper.i())));
            versionId = String.valueOf(downloadWrapper.d.version_id);
            sn = com.meizu.cloud.app.utils.param.a.a(this.b).c();
            imei = com.meizu.cloud.app.utils.param.a.a(this.b).d();
            map = new HashMap();
            map.put("version_id", versionId);
            map.put(RequestManager.SN, sn);
            map.put(RequestManager.IMEI, imei);
            paramPairs.add(RequestManager.generateSign(map, com.meizu.cloud.app.utils.c.a.a));
        } else {
            paramPairs.add(new com.meizu.volley.b.a("game_id", String.valueOf(downloadWrapper.i())));
            versionId = String.valueOf(downloadWrapper.d.version_id);
            sn = com.meizu.cloud.app.utils.param.a.a(this.b).c();
            imei = com.meizu.cloud.app.utils.param.a.a(this.b).d();
            map = new HashMap();
            map.put("version_id", versionId);
            map.put(RequestManager.SN, sn);
            map.put(RequestManager.IMEI, imei);
            paramPairs.add(RequestManager.generateSign(map, com.meizu.cloud.app.utils.c.a.b));
        }
        if (downloadWrapper.b.page_info != null) {
            paramPairs.add(new com.meizu.volley.b.a(Columns.CATEGORY_ID, String.valueOf(downloadWrapper.b.page_info[0])));
            paramPairs.add(new com.meizu.volley.b.a("page_id", String.valueOf(downloadWrapper.b.page_info[1])));
            paramPairs.add(new com.meizu.volley.b.a("expand", String.valueOf(downloadWrapper.b.page_info[2])));
            Log.i("page_id", "category_id:" + downloadWrapper.b.page_info[0] + ";page_id:" + downloadWrapper.b.page_info[1] + ";expend:" + downloadWrapper.b.page_info[2]);
        }
        e eVar = downloadWrapper;
        a(eVar, paramPairs, RequestConstants.getRuntimeDomainUrl(this.b, String.format(RequestConstants.VERSION_HISTORY_DOWNLOAD, new Object[]{Long.valueOf(downloadWrapper.d.version_id)})));
    }

    private void d(e downloadWrapper) {
        downloadWrapper.a(n.FETCHING, null);
        d.a(this.b).a(null, downloadWrapper);
        ArrayList<com.meizu.volley.b.a> paramPairs = new ArrayList();
        String sn;
        String imei;
        HashMap<String, String> map;
        if (x.a(this.b)) {
            paramPairs.add(new com.meizu.volley.b.a("app_id", String.valueOf(downloadWrapper.i())));
            String appId = String.valueOf(downloadWrapper.i());
            sn = com.meizu.cloud.app.utils.param.a.a(this.b).c();
            imei = com.meizu.cloud.app.utils.param.a.a(this.b).d();
            map = new HashMap();
            map.put("app_id", appId);
            map.put(RequestManager.SN, sn);
            map.put(RequestManager.IMEI, imei);
            if (downloadWrapper.z()) {
                map.put("version_code", String.valueOf(downloadWrapper.D()));
            }
            paramPairs.add(RequestManager.generateSign(map, com.meizu.cloud.app.utils.c.a.a));
        } else {
            paramPairs.add(new com.meizu.volley.b.a("game_id", String.valueOf(downloadWrapper.i())));
            String gameId = String.valueOf(downloadWrapper.i());
            sn = com.meizu.cloud.app.utils.param.a.a(this.b).c();
            imei = com.meizu.cloud.app.utils.param.a.a(this.b).d();
            map = new HashMap();
            map.put("game_id", gameId);
            map.put(RequestManager.SN, sn);
            map.put(RequestManager.IMEI, imei);
            paramPairs.add(RequestManager.generateSign(map, com.meizu.cloud.app.utils.c.a.b));
        }
        if (downloadWrapper.b.page_info != null) {
            paramPairs.add(new com.meizu.volley.b.a(Columns.CATEGORY_ID, String.valueOf(downloadWrapper.b.page_info[0])));
            paramPairs.add(new com.meizu.volley.b.a("page_id", String.valueOf(downloadWrapper.b.page_info[1])));
            paramPairs.add(new com.meizu.volley.b.a("expand", String.valueOf(downloadWrapper.b.page_info[2])));
            Log.i("page_id", "category_id:" + downloadWrapper.b.page_info[0] + ";page_id:" + downloadWrapper.b.page_info[1] + ";expend:" + downloadWrapper.b.page_info[2]);
        }
        a(downloadWrapper, paramPairs, RequestConstants.getRuntimeDomainUrl(this.b, RequestConstants.PATCH_DOWNLOAD) + downloadWrapper.i() + "/" + downloadWrapper.D() + "/");
    }

    private void e(e downloadWrapper) {
        downloadWrapper.a(n.FETCHING, null);
        d.a(this.b).a(null, downloadWrapper);
        ArrayList<com.meizu.volley.b.a> paramPairs = new ArrayList();
        String sn;
        String imei;
        HashMap<String, String> map;
        if (x.a(this.b)) {
            paramPairs.add(new com.meizu.volley.b.a("app_id", String.valueOf(downloadWrapper.i())));
            String appId = String.valueOf(downloadWrapper.i());
            sn = com.meizu.cloud.app.utils.param.a.a(this.b).c();
            imei = com.meizu.cloud.app.utils.param.a.a(this.b).d();
            map = new HashMap();
            map.put("app_id", appId);
            map.put(RequestManager.SN, sn);
            map.put(RequestManager.IMEI, imei);
            if (downloadWrapper.z()) {
                map.put("version_code", String.valueOf(downloadWrapper.D()));
            }
            paramPairs.add(RequestManager.generateSign(map, com.meizu.cloud.app.utils.c.a.a));
        } else {
            paramPairs.add(new com.meizu.volley.b.a("game_id", String.valueOf(downloadWrapper.i())));
            String gameId = String.valueOf(downloadWrapper.i());
            sn = com.meizu.cloud.app.utils.param.a.a(this.b).c();
            imei = com.meizu.cloud.app.utils.param.a.a(this.b).d();
            map = new HashMap();
            map.put("game_id", gameId);
            map.put(RequestManager.SN, sn);
            map.put(RequestManager.IMEI, imei);
            paramPairs.add(RequestManager.generateSign(map, com.meizu.cloud.app.utils.c.a.b));
        }
        if (downloadWrapper.b.page_info != null) {
            paramPairs.add(new com.meizu.volley.b.a(Columns.CATEGORY_ID, String.valueOf(downloadWrapper.b.page_info[0])));
            paramPairs.add(new com.meizu.volley.b.a("page_id", String.valueOf(downloadWrapper.b.page_info[1])));
            paramPairs.add(new com.meizu.volley.b.a("expand", String.valueOf(downloadWrapper.b.page_info[2])));
            Log.i("page_id", "category_id:" + downloadWrapper.b.page_info[0] + ";page_id:" + downloadWrapper.b.page_info[1] + ";expend:" + downloadWrapper.b.page_info[2]);
        }
        a(downloadWrapper, paramPairs, RequestConstants.getRuntimeDomainUrl(this.b, RequestConstants.TRIAL_APP));
        a(this.b, downloadWrapper.b);
    }

    private void a(e downloadWrapper, ArrayList<com.meizu.volley.b.a> paramPairs, String url) {
        l request = new FastJsonRequest(new TypeReference<ResultModel<DownloadInfo>>(this) {
            final /* synthetic */ h a;

            {
                this.a = r1;
            }
        }, 0, url, (List) paramPairs, new b(this, downloadWrapper), new a(this, downloadWrapper));
        request.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.b));
        request.setTag(downloadWrapper.g());
        com.meizu.volley.b.a(this.b).a().a(request);
    }

    public void a(e downloadWrapper) {
        com.meizu.volley.b.a(this.b).a().a(downloadWrapper.g());
        if (downloadWrapper.j().a(new g(-1, 1, 3)) && downloadWrapper.E()) {
            downloadWrapper.J();
        }
    }

    private void a(com.meizu.cloud.d.a mzSystemPayHelper) {
        ApplicationInfo info = i.a(this.b);
        if (info != null) {
            Bundle metaData = info.metaData;
            if (metaData.containsKey("dialog_confirm_style") && metaData.containsKey("dialog_edittext_style")) {
                mzSystemPayHelper.a(metaData.getInt("dialog_confirm_style"), metaData.getInt("dialog_edittext_style"));
            }
        }
    }

    private void a(final Context context, final AppStructItem appStructItem) {
        TypeReference typeReference = new TypeReference<ResultModel<LicenseInfoModel>>(this) {
            final /* synthetic */ h a;

            {
                this.a = r1;
            }
        };
        List paramPairs = new ArrayList();
        paramPairs.add(new com.meizu.volley.b.a("package_name", appStructItem.package_name));
        paramPairs.add(new com.meizu.volley.b.a("version_code", String.valueOf(appStructItem.version_code)));
        l jsonRequest = new FastJsonRequest(typeReference, 0, RequestConstants.APP_LICENSE_LOAD, paramPairs, new com.android.volley.n.b<ResultModel<LicenseInfoModel>>(this) {
            final /* synthetic */ h c;

            public void a(ResultModel<LicenseInfoModel> response) {
                if (response != null && response.getCode() == 200 && response.getValue() != null) {
                    LicenseInfoModel licenseInfoModel = (LicenseInfoModel) response.getValue();
                    if (!TextUtils.isEmpty(licenseInfoModel.license)) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("PACKAGE_NAME", appStructItem.package_name);
                        contentValues.put("LICENSE_DATA", licenseInfoModel.license);
                        context.getContentResolver().insert(Uri.parse("content://com.meizu.flyme.appcenter.licenseprovider/license"), contentValues);
                        if (licenseInfoModel.paid) {
                            Log.w(t.class.getSimpleName(), "license restore.");
                        } else {
                            Log.w(t.class.getSimpleName(), "tryout license restore.");
                        }
                    }
                }
            }
        }, new com.android.volley.n.a(this) {
            final /* synthetic */ h a;

            {
                this.a = r1;
            }

            public void a(s error) {
                error.printStackTrace();
                Log.w(t.class.getSimpleName(), "license restore failure.");
            }
        });
        jsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(context));
        com.meizu.volley.b.a(context).a().a(jsonRequest);
    }
}
