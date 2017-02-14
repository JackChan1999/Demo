package com.meizu.cloud.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import com.alibaba.fastjson.TypeReference;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.u;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.AppStructDetailsItem;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.HistoryVersions;
import com.meizu.cloud.app.request.structitem.HistoryVersions.VersionItem;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.app.widget.LoadDataView;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.base.b.a;
import com.meizu.cloud.base.b.h;
import com.meizu.cloud.statistics.b;

public abstract class i extends a<VersionItem> {
    protected AppStructDetailsItem a;
    protected t b;

    protected /* synthetic */ boolean onResponse(Object obj) {
        return a((h.a) obj);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.a = (AppStructDetailsItem) getArguments().getSerializable("details_info");
        this.mPageName = "historyversion";
        u pageInfo = new u();
        pageInfo.a(true);
        this.b = new t(getActivity(), pageInfo);
        this.b.a(this.mPageName);
        this.mPageInfo[1] = 7;
        this.b.a(this.mPageInfo);
    }

    protected void setupActionBar() {
        super.setupActionBar();
        getActionBar().a(getString(com.meizu.cloud.b.a.i.history_version));
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        LoadDataView loadDataView = (LoadDataView) rootView.findViewById(f.load_data_view);
        LayoutParams layoutParams = (LayoutParams) loadDataView.getLayoutParams();
        layoutParams.topMargin = d.f(getActivity()) + ((int) (((float) getResources().getDimensionPixelSize(com.meizu.cloud.b.a.d.rank_item_view_height)) + getResources().getDimension(com.meizu.cloud.b.a.d.common_block_title_bar_height)));
        loadDataView.setLayoutParams(layoutParams);
    }

    public void onStart() {
        super.onStart();
        b.a().a(this.mPageName);
    }

    public void onStop() {
        super.onStop();
        b.a().a(this.mPageName, null);
    }

    protected String a() {
        return RequestConstants.getRuntimeDomainUrl(getActivity(), String.format(RequestConstants.VERSION_HISTORY, new Object[]{Integer.valueOf(this.a.id)}));
    }

    protected h.a<VersionItem> b(String json) {
        ResultModel<HistoryVersions> resultModel = JSONUtils.parseResultModel(json, new TypeReference<ResultModel<HistoryVersions>>(this) {
            final /* synthetic */ i a;

            {
                this.a = r1;
            }
        });
        if (resultModel == null || resultModel.getCode() != 200 || resultModel.getValue() == null) {
            return null;
        }
        HistoryVersions historyVersions = (HistoryVersions) resultModel.getValue();
        for (VersionItem item : historyVersions.versions) {
            item.parent = historyVersions;
        }
        h.a<VersionItem> result = new h.a();
        result.b = historyVersions.versions;
        return result;
    }

    protected h.a<VersionItem> a(String json) {
        return null;
    }

    protected boolean a(h.a resultModel) {
        boolean loadSuceess = super.a(resultModel);
        boolean bNoVersion = (resultModel == null || resultModel.b == null || resultModel.b.size() != 0) ? false : true;
        if (bNoVersion) {
            showEmptyView(getString(com.meizu.cloud.b.a.i.history_version_none), null, null);
        }
        return loadSuceess;
    }

    public com.meizu.cloud.base.a.d createRecyclerAdapter() {
        return new com.meizu.cloud.app.a.d(getActivity(), this.a, this.b);
    }

    protected void a(final e wrapper, final boolean isProgress) {
        if (wrapper.g().equals(this.a.package_name)) {
            getActivity().runOnUiThread(new Runnable(this) {
                final /* synthetic */ i c;

                public void run() {
                    if (isProgress) {
                        if (wrapper.F()) {
                            this.c.b(wrapper);
                        } else {
                            this.c.a(wrapper);
                        }
                    } else if (wrapper.f() == com.meizu.cloud.app.downlad.f.f.INSTALL_SUCCESS || wrapper.f() == com.meizu.cloud.app.downlad.f.f.INSTALL_FAILURE || (!wrapper.F() && wrapper.f() == com.meizu.cloud.app.downlad.f.f.DELETE_SUCCESS)) {
                        this.c.a(wrapper);
                        this.c.c(wrapper);
                    } else if (wrapper.F()) {
                        this.c.b(wrapper);
                    } else {
                        this.c.a(wrapper);
                    }
                }
            });
        }
    }

    protected void c(String pkgName) {
        if (this.a.package_name.equals(pkgName)) {
            e downloadWrapper = com.meizu.cloud.app.downlad.d.a(getActivity()).b(pkgName);
            if (downloadWrapper == null) {
                getRecyclerViewAdapter().notifyDataSetChanged();
            } else if (!downloadWrapper.F() && !(downloadWrapper.f() instanceof com.meizu.cloud.app.downlad.f.f)) {
                c(downloadWrapper);
            }
        }
    }

    private void a(e wrapper) {
        CirProButton btn = (CirProButton) getRecyclerView().findViewWithTag(Integer.valueOf(wrapper.h()));
        if (btn != null) {
            this.b.a(wrapper, btn);
        }
    }

    private void b(e wrapper) {
        if (getActivity() != null && getRecyclerView() != null && getRecyclerView().getLayoutManager() != null) {
            com.meizu.cloud.app.a.d versionAdapter = (com.meizu.cloud.app.a.d) getRecyclerViewAdapter();
            int i = 0;
            while (i <= versionAdapter.d()) {
                VersionItem item = (VersionItem) versionAdapter.c(i);
                if (item == null || item.version_code != wrapper.G()) {
                    i++;
                } else {
                    CirProButton btn = (CirProButton) getRecyclerView().findViewWithTag(Integer.valueOf(item.version_code));
                    if (btn != null) {
                        this.b.a((t.a) wrapper, item, false, btn);
                        return;
                    }
                    return;
                }
            }
        }
    }

    private void c(e wrapper) {
        if (getActivity() != null && getRecyclerView() != null && getRecyclerView().getLayoutManager() != null) {
            com.meizu.cloud.app.a.d versionAdapter = (com.meizu.cloud.app.a.d) getRecyclerViewAdapter();
            for (int i = 1; i < getRecyclerViewAdapter().getItemCount(); i++) {
                VersionItem item = (VersionItem) versionAdapter.c(i);
                if (item != null) {
                    CirProButton btn = (CirProButton) getRecyclerView().findViewWithTag(Integer.valueOf(item.version_code));
                    if (btn != null) {
                        this.b.a((t.a) wrapper, item, false, btn);
                    } else {
                        versionAdapter.notifyItemChanged(i);
                    }
                }
            }
        }
    }

    public Fragment b() {
        return null;
    }
}
