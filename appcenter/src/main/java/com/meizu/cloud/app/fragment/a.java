package com.meizu.cloud.app.fragment;

import android.support.v4.app.o;
import android.support.v7.app.ActionBar.b;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import com.alibaba.fastjson.TypeReference;
import com.meizu.cloud.app.request.FastJsonParseCacheRequest;
import com.meizu.cloud.app.request.FastJsonParseCacheRequest.CacheCallback;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.ParseListener;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.PageInfo;
import com.meizu.cloud.app.request.model.PageInfo.PageType;
import com.meizu.cloud.app.request.model.PagesResultModel;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.utils.s;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.b.m;
import java.util.ArrayList;
import java.util.List;

public abstract class a extends m<ResultModel<PagesResultModel<PageInfo>>> implements CacheCallback, ParseListener<ResultModel<PagesResultModel<PageInfo>>> {
    protected List<PageInfo> a;
    protected String b;
    protected boolean c = true;
    protected MenuItem d;
    private final int k = 10;

    protected abstract String a();

    public /* synthetic */ Object onParseCache() {
        return b_();
    }

    protected /* synthetic */ Object onParseFirstData(String str) {
        return a(str);
    }

    public /* synthetic */ Object onParseResponse(String str) {
        return b(str);
    }

    protected /* synthetic */ boolean onResponse(Object obj) {
        return a((ResultModel) obj);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (this.d == null) {
            this.d = menu.findItem(f.search_menu);
        }
        if (this.d == null) {
            return;
        }
        if (this.h == null || this.h.length == 0) {
            this.d.setVisible(false);
        }
    }

    public void b(b tab, o ft) {
        if (this.e != null && tab.a() == 0) {
            this.e.setOffscreenPageLimit(3);
        }
        super.b(tab, ft);
    }

    protected boolean loadData() {
        showProgress();
        if (this.mbInitLoad || com.meizu.cloud.app.utils.m.b(getActivity())) {
            return super.loadData();
        }
        return loadData(s.a(getActivity(), a(), 0, 10));
    }

    protected void onRequestData() {
        List<com.meizu.volley.b.a> paramPairs = new ArrayList();
        paramPairs.add(new com.meizu.volley.b.a("start", String.valueOf(0)));
        paramPairs.add(new com.meizu.volley.b.a("max", String.valueOf(10)));
        this.mLoadRequest = new FastJsonParseCacheRequest(a(), paramPairs, this, new com.meizu.cloud.base.b.f.b(this), new com.meizu.cloud.base.b.f.a(this));
        ((FastJsonParseCacheRequest) this.mLoadRequest).setCacheListener(this);
        String ifSinceModified = s.a(getActivity(), a());
        if (!TextUtils.isEmpty(ifSinceModified)) {
            this.mLoadRequest.addHeader(RequestConstants.IF_MODIFIED_SINCE, ifSinceModified);
        }
        this.mLoadRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(getActivity()));
        com.meizu.volley.b.a(getActivity()).a().a(this.mLoadRequest);
    }

    public ResultModel<PagesResultModel<PageInfo>> b_() {
        String json = "";
        if (this.mRunning) {
            json = s.a(getActivity(), a(), 0, 10);
            if (TextUtils.isEmpty(json)) {
                s.a(getActivity(), a(), "");
            }
        }
        return d(json);
    }

    public void onCacheDateReceived(String date) {
        if (this.mRunning) {
            s.a(getActivity(), a(), date);
        }
    }

    protected ResultModel<PagesResultModel<PageInfo>> a(String json) {
        return d(json);
    }

    public ResultModel<PagesResultModel<PageInfo>> b(String json) {
        if (!TextUtils.isEmpty(json) && this.mRunning) {
            s.a(getActivity(), a(), json, 0, 10);
        }
        return d(json);
    }

    private ResultModel<PagesResultModel<PageInfo>> d(String json) {
        ResultModel<PagesResultModel<PageInfo>> resultModel = JSONUtils.parseResultModel(json, new TypeReference<ResultModel<PagesResultModel<PageInfo>>>(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }
        });
        boolean success = (resultModel == null || resultModel.getCode() != 200 || resultModel.getValue() == null || ((PagesResultModel) resultModel.getValue()).nav == null || ((PagesResultModel) resultModel.getValue()).nav.size() <= 0) ? false : true;
        if (!success && this.mRunning) {
            s.a(getActivity(), a(), "");
        }
        return resultModel;
    }

    protected boolean a(ResultModel<PagesResultModel<PageInfo>> resultModel) {
        if (resultModel == null || resultModel.getCode() != 200 || resultModel.getValue() == null) {
            String message;
            if (resultModel != null) {
                message = resultModel.getMessage();
            } else {
                message = null;
            }
            c(message);
            return false;
        }
        this.a = ((PagesResultModel) resultModel.getValue()).nav;
        this.b = ((PagesResultModel) resultModel.getValue()).blocks;
        this.c = ((PagesResultModel) resultModel.getValue()).more;
        this.mbMore = false;
        if (this.a != null) {
            int i = this.a.size() - 1;
            while (i >= 0) {
                boolean bFound = false;
                for (PageType pageType : PageType.values()) {
                    if (pageType.getType().equals(((PageInfo) this.a.get(i)).type)) {
                        bFound = true;
                        break;
                    }
                }
                if (!bFound || PageType.MINE.getType().equals(((PageInfo) this.a.get(i)).type)) {
                    this.a.remove(i);
                }
                i--;
            }
            PageInfo mine = new PageInfo();
            mine.name = "我的";
            mine.type = PageType.MINE.getType();
            mine.page_type = PageType.MINE.getType();
            this.a.add(mine);
            List<String> names = new ArrayList();
            for (i = 0; i < this.a.size(); i++) {
                names.add(((PageInfo) this.a.get(i)).name);
            }
            this.h = (String[]) names.toArray(new String[names.size()]);
        }
        c(null);
        return true;
    }

    protected void onErrorResponse(com.android.volley.s error) {
        c(getString(i.server_error));
    }

    protected void c() {
        if (!this.mbInitLoad) {
            loadData();
        }
    }

    protected void c(String errorMsg) {
        hideProgress();
        if (this.mRunning && getActivity() != null) {
            if (errorMsg != null) {
                a(null);
                return;
            }
            if (isResumed()) {
                getActionBar().c(false);
                if (!(this.d == null || this.h == null || this.h.length == 0)) {
                    this.d.setVisible(true);
                }
            }
            a(this.h);
        }
    }
}
