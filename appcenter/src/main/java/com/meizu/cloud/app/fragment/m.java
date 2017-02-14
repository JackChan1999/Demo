package com.meizu.cloud.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.alibaba.fastjson.TypeReference;
import com.meizu.cloud.app.request.FastJsonParseCacheRequest;
import com.meizu.cloud.app.request.FastJsonParseCacheRequest.CacheCallback;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.ParseListener;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.PageInfo.PageType;
import com.meizu.cloud.app.request.model.PagesResultModel;
import com.meizu.cloud.app.request.model.RankPageInfo;
import com.meizu.cloud.app.request.model.RankPageInfo.RankPageType;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.utils.s;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.base.b.f.b;
import com.meizu.cloud.base.b.i;
import java.util.ArrayList;
import java.util.List;

public abstract class m extends i<ResultModel<PagesResultModel<RankPageInfo>>> implements CacheCallback, ParseListener<ResultModel<PagesResultModel<RankPageInfo>>> {
    protected String a;
    private String i;

    public class a extends com.meizu.cloud.base.b.i.a<RankPageInfo> {
        final /* synthetic */ m a;

        public a(m mVar) {
            this.a = mVar;
            super(mVar);
        }

        public com.meizu.cloud.base.b.i.a.a a(int position) {
            RankPageInfo pageInfo = (RankPageInfo) b(position);
            if (pageInfo != null) {
                Bundle bundle = new Bundle();
                if (position == 0 && this.a.i != null) {
                    bundle.putString("json_string", this.a.i);
                }
                bundle.putString("url", this.a.d() + pageInfo.url);
                bundle.putInt("page_id", 2);
                if (pageInfo.type.equals(PageType.RANK.getType())) {
                    Fragment fragment = this.a.c();
                    bundle.putString("rank_page_type", pageInfo.page_type);
                    bundle.putString("pager_name", this.a.getArguments().getString("pager_name", ""));
                    int margin = this.a.getResources().getDimensionPixelSize(d.multi_tab_margin);
                    bundle.putInt("extra_padding_top", (margin * 2) + this.a.getResources().getDimensionPixelSize(d.multi_tab_button_height));
                    fragment.setArguments(bundle);
                    return new com.meizu.cloud.base.b.i.a.a(this, fragment, pageInfo.name, pageInfo.url);
                }
            }
            return null;
        }
    }

    protected abstract Fragment c();

    protected abstract String d();

    public /* synthetic */ Object onParseCache() {
        return a();
    }

    public /* synthetic */ Object onParseFirstData(String str) {
        return b(str);
    }

    public /* synthetic */ Object onParseResponse(String str) {
        return a(str);
    }

    protected /* synthetic */ boolean onResponse(Object obj) {
        return a((ResultModel) obj);
    }

    protected void setupActionBar() {
        Bundle args = getArguments();
        if ("ranks".equals(args.getString("forward_type", "index"))) {
            ActionBar actionBar = getActionBar();
            if (args != null) {
                super.setupActionBar();
                actionBar.a(args.getString("title_name", ""));
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.a = getArguments().getString("url");
        registerPagerScrollStateListener();
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterPagerScrollStateListener();
    }

    protected boolean loadData() {
        hideEmptyView();
        if (this.mbInitLoad || com.meizu.cloud.app.utils.m.b(getActivity()) || TextUtils.isEmpty(this.a)) {
            if (super.loadData()) {
                showProgress();
                return true;
            }
        } else if (super.loadData(s.a(getActivity(), this.a, 0, 50))) {
            showProgress();
            return true;
        }
        return false;
    }

    protected void onRequestData() {
        List<com.meizu.volley.b.a> paramPairs = new ArrayList();
        paramPairs.add(new com.meizu.volley.b.a("start", String.valueOf(0)));
        paramPairs.add(new com.meizu.volley.b.a("max", String.valueOf(50)));
        this.mLoadRequest = new FastJsonParseCacheRequest(this.a, paramPairs, this, new b(this), new com.meizu.cloud.base.b.f.a(this));
        this.mLoadRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(getActivity()));
        ((FastJsonParseCacheRequest) this.mLoadRequest).setCacheListener(this);
        String ifSinceModified = s.a(getActivity(), this.a);
        if (!TextUtils.isEmpty(ifSinceModified)) {
            this.mLoadRequest.addHeader(RequestConstants.IF_MODIFIED_SINCE, ifSinceModified);
        }
        com.meizu.volley.b.a(getActivity()).a().a(this.mLoadRequest);
    }

    public ResultModel<PagesResultModel<RankPageInfo>> a() {
        String json = "";
        if (this.mRunning) {
            json = s.a(getActivity(), this.a, 0, 50);
            if (TextUtils.isEmpty(json)) {
                s.a(getActivity(), this.a, "");
            }
        }
        return c(json);
    }

    public void onCacheDateReceived(String date) {
        if (this.mRunning) {
            s.a(getActivity(), this.a, date);
        }
    }

    public ResultModel<PagesResultModel<RankPageInfo>> a(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        s.a(getActivity(), this.a, json, 0, 50);
        return c(json);
    }

    public ResultModel<PagesResultModel<RankPageInfo>> b(String json) {
        return c(json);
    }

    private ResultModel<PagesResultModel<RankPageInfo>> c(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        this.i = json;
        return JSONUtils.parseResultModel(json, new TypeReference<ResultModel<PagesResultModel<RankPageInfo>>>(this) {
            final /* synthetic */ m a;

            {
                this.a = r1;
            }
        });
    }

    protected boolean a(ResultModel<PagesResultModel<RankPageInfo>> resultModel) {
        boolean loadSuccess = false;
        if (resultModel != null && resultModel.getCode() == 200) {
            List rankPageInfoList = a((PagesResultModel) resultModel.getValue());
            if (rankPageInfoList != null) {
                loadSuccess = true;
                a(rankPageInfoList);
            }
        }
        if (!loadSuccess) {
            s.a(getActivity(), this.a, "");
            showEmptyView(getString(com.meizu.cloud.b.a.i.server_error), null, new OnClickListener(this) {
                final /* synthetic */ m a;

                {
                    this.a = r1;
                }

                public void onClick(View v) {
                    this.a.loadData();
                }
            });
        }
        return loadSuccess;
    }

    private List<RankPageInfo> a(PagesResultModel<RankPageInfo> pageRsultModel) {
        List<RankPageInfo> pageInfoList = pageRsultModel.nav;
        if (pageInfoList != null) {
            for (int i = pageInfoList.size() - 1; i >= 0; i--) {
                boolean bFound = false;
                for (PageType pageType : PageType.values()) {
                    if (pageType.getType().equals(((RankPageInfo) pageInfoList.get(i)).type)) {
                        bFound = true;
                        break;
                    }
                }
                boolean isDatAvailable = (TextUtils.isEmpty(((RankPageInfo) pageInfoList.get(i)).name) || TextUtils.isEmpty(((RankPageInfo) pageInfoList.get(i)).page_type) || TextUtils.isEmpty(((RankPageInfo) pageInfoList.get(i)).type) || TextUtils.isEmpty(((RankPageInfo) pageInfoList.get(i)).url)) ? false : true;
                if (!bFound || !isDatAvailable) {
                    pageInfoList.remove(i);
                }
            }
            return pageInfoList;
        } else if (TextUtils.isEmpty(pageRsultModel.blocks)) {
            return pageInfoList;
        } else {
            pageInfoList = new ArrayList();
            RankPageInfo pageinfo = new RankPageInfo();
            pageinfo.type = PageType.RANK.getType();
            pageinfo.url = this.a;
            pageinfo.name = "";
            pageinfo.page_type = RankPageType.DEFAULT.getType();
            pageInfoList.add(pageinfo);
            return pageInfoList;
        }
    }

    protected void a(final List<RankPageInfo> pageInfoList) {
        if (this.d != null) {
            postOnPagerIdle(new Runnable(this) {
                final /* synthetic */ m b;

                public void run() {
                    if (this.b.getActivity() != null && this.b.mRunning) {
                        this.b.hideProgress();
                        this.b.hideEmptyView();
                        this.b.d.a(pageInfoList);
                    }
                }
            });
        }
    }

    protected void onErrorResponse(com.android.volley.s error) {
        showEmptyView(getEmptyTextString(), null, new OnClickListener(this) {
            final /* synthetic */ m a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                this.a.loadData();
            }
        });
    }

    protected com.meizu.cloud.base.b.i.a<RankPageInfo> b() {
        return new a(this);
    }
}
