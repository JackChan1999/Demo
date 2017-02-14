package com.meizu.flyme.appcenter.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.MultiChoiceView;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.n;
import com.android.volley.s;
import com.meizu.cloud.a.b;
import com.meizu.cloud.a.c;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.u;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.downlad.f.k;
import com.meizu.cloud.app.request.FastJsonAuthParseRequest;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.ParseListener;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.DataReultModel;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.utils.j;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.base.app.BaseCommonActivity;
import com.meizu.cloud.base.b.h;
import com.meizu.mstore.R;
import flyme.support.v7.widget.MzRecyclerView.MultiChoiceModeListener;
import flyme.support.v7.widget.MzRecyclerView.OnItemClickListener;
import flyme.support.v7.widget.RecyclerView;
import flyme.support.v7.widget.RecyclerView.AdapterDataObserver;
import flyme.support.v7.widget.TwoStateTextView;
import java.util.ArrayList;
import java.util.List;

public class f extends h<AppStructItem> implements OnAccountsUpdateListener, OnItemClickListener {
    protected b a;
    protected MultiChoiceView b;
    protected TwoStateTextView c;
    private com.meizu.volley.a.b<Object> d;
    private t e;
    private String k;
    private ActionMode l;
    private List<AppStructItem> m = new ArrayList();
    private com.meizu.flyme.appcenter.b.a n;
    private k o = new k(this) {
        final /* synthetic */ f a;

        {
            this.a = r1;
        }

        public void onDownloadStateChanged(e wrapper) {
            this.a.a(wrapper);
        }

        public void onDownloadProgress(e wrapper) {
            this.a.a(wrapper);
        }

        public void onFetchStateChange(e wrapper) {
            this.a.a(wrapper);
        }

        public void onInstallStateChange(e wrapper) {
            if (this.a.getRecyclerViewAdapter() == null) {
                return;
            }
            if (wrapper.f() == com.meizu.cloud.app.downlad.f.f.INSTALL_SUCCESS) {
                List<AppStructItem> blockableList = ((com.meizu.flyme.appcenter.a.a) this.a.getRecyclerViewAdapter()).e();
                if (blockableList != null) {
                    boolean removeSuccess = false;
                    int removeIndex = -1;
                    for (int i = 0; i < blockableList.size(); i++) {
                        AppStructItem appStructItem = (AppStructItem) blockableList.get(i);
                        if (wrapper.g().equals(appStructItem.package_name)) {
                            removeSuccess = blockableList.remove(appStructItem);
                            if (removeSuccess) {
                                removeIndex = i;
                            }
                            if (removeSuccess) {
                                this.a.a(wrapper);
                            } else {
                                this.a.getRecyclerViewAdapter().notifyItemRemoved(removeIndex);
                            }
                        }
                    }
                    if (removeSuccess) {
                        this.a.a(wrapper);
                    } else {
                        this.a.getRecyclerViewAdapter().notifyItemRemoved(removeIndex);
                    }
                }
            } else if (wrapper.f() == com.meizu.cloud.app.downlad.f.f.DELETE_SUCCESS) {
                List<AppStructItem> appList = ((com.meizu.flyme.appcenter.a.a) this.a.getRecyclerViewAdapter()).e();
                if (appList != null) {
                    appList.add(0, wrapper.m());
                    this.a.getRecyclerViewAdapter().notifyItemInserted(0);
                }
            } else {
                this.a.a(wrapper);
            }
        }

        public void b(e wrapper) {
            this.a.a(wrapper);
        }

        public void a(e wrapper) {
            this.a.a(wrapper);
        }
    };
    private AdapterDataObserver p = new AdapterDataObserver(this) {
        final /* synthetic */ f a;

        {
            this.a = r1;
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (this.a.getRecyclerViewAdapter() != null && this.a.getRecyclerViewAdapter().e().size() > 1) {
                this.a.hideEmptyView();
            }
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (this.a.getRecyclerViewAdapter() != null && this.a.getRecyclerViewAdapter().e().size() == 0) {
                Drawable icon = null;
                if (this.a.mRunning) {
                    icon = this.a.getResources().getDrawable(R.drawable.ic_error_page, null);
                }
                this.a.showEmptyView(this.a.getString(R.string.installed_no_data_remind_text), icon, null);
            }
        }
    };

    class a implements MultiChoiceModeListener {
        final /* synthetic */ f a;

        a(f fVar) {
            this.a = fVar;
        }

        public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {
            String title;
            AppStructItem data = this.a.getRecyclerViewAdapter().c(position);
            if (data instanceof AppStructItem) {
                AppStructItem item = data;
                if (checked) {
                    if (!this.a.m.contains(item)) {
                        this.a.m.add(item);
                    }
                } else if (this.a.m.contains(item)) {
                    this.a.m.remove(item);
                }
            }
            if (this.a.l.getMenu() != null) {
                for (int i = 0; i < this.a.l.getMenu().size(); i++) {
                    MenuItem menuItem = this.a.l.getMenu().getItem(i);
                    if (menuItem != null) {
                        if (this.a.m.size() == 0) {
                            menuItem.setEnabled(false);
                        } else {
                            menuItem.setEnabled(true);
                        }
                    }
                }
            }
            this.a.c.setTotalCount(this.a.getRecyclerViewAdapter().e().size());
            this.a.c.setSelectedCount(this.a.m.size());
            if (this.a.m.size() == 0) {
                title = this.a.getString(R.string.chioce_app);
            } else {
                title = this.a.getString(R.string.mz_action_bar_multi_choice_title, Integer.valueOf(this.a.m.size()));
            }
            this.a.b.setTitle(title);
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            this.a.a(true);
            Fragment parentFragment = this.a.getParentFragment();
            if (parentFragment instanceof h) {
                ((h) parentFragment).b(false);
            }
            this.a.b = new MultiChoiceView(this.a.getActivity());
            this.a.c = (TwoStateTextView) this.a.b.getSelectAllView();
            this.a.b.setOnSelectAllItemClickListener(new OnClickListener(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onClick(View view) {
                    this.a.a.e();
                }
            });
            this.a.b.setOnCloseItemClickListener(new OnClickListener(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onClick(View view) {
                    this.a.a.b();
                }
            });
            if (this.a.n != null) {
                this.a.n.d_();
            }
            if (this.a.l != null) {
                this.a.l.finish();
            }
            this.a.l = actionMode;
            actionMode.setCustomView(this.a.b);
            this.a.getActivity().getMenuInflater().inflate(R.menu.history_menu, menu);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.a.a(menuItem);
        }

        public void onDestroyActionMode(ActionMode actionMode) {
            this.a.a(false);
            Fragment parentFragment = this.a.getParentFragment();
            if (parentFragment instanceof h) {
                ((h) parentFragment).b(true);
            }
            this.a.m.clear();
            this.a.l = null;
            if (this.a.n != null) {
                this.a.n.h();
            }
        }
    }

    protected /* synthetic */ boolean onResponse(Object obj) {
        return a((com.meizu.cloud.base.b.h.a) obj);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d.a(getActivity()).a(this.o);
        this.mPageName = "myapp_uninstalled";
        this.e = new t(getActivity(), new u());
        this.e.a(this.mPageName);
        this.mPageInfo[1] = 13;
        this.e.a(this.mPageInfo);
        AccountManager.get(getActivity()).addOnAccountsUpdatedListener(this, null, true);
    }

    public void onDestroy() {
        if (this.a != null) {
            this.a.a();
        }
        if (this.d != null) {
            this.d.cancel();
        }
        d.a(getActivity()).b(this.o);
        getRecyclerViewAdapter().unregisterAdapterDataObserver(this.p);
        AccountManager.get(getActivity()).removeOnAccountsUpdatedListener(this);
        super.onDestroy();
    }

    public void onAccountsUpdated(Account[] accounts) {
        if (getUserVisibleHint() && getActivity() != null && getRecyclerViewAdapter() != null) {
            String uId = c.c(getActivity());
            if (this.k == null && !TextUtils.isEmpty(uId)) {
                loadData();
            } else if (TextUtils.isEmpty(uId)) {
                getRecyclerViewAdapter().a(null);
                c();
                d();
            } else if (!uId.equals(this.k)) {
                getRecyclerViewAdapter().a(null);
                c();
                d();
            }
            this.k = uId;
        }
    }

    private void c() {
        this.f = null;
        this.g = true;
        this.mbInitLoad = false;
        this.mbLoading = false;
        this.mbMore = true;
        this.i = 0;
        if (this.mScrollToEndListener != null) {
            this.mScrollToEndListener.a();
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && getRecyclerView() != null) {
            getRecyclerView().finishMultiChoice();
        }
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        getRecyclerView().setEnableDragSelection(true);
        getRecyclerView().setChoiceMode(4);
        getRecyclerView().setMultiChoiceModeListener(new a(this));
        getRecyclerView().setOnItemClickListener(this);
    }

    protected void onRequestData() {
        List paramPairs = new ArrayList();
        paramPairs.add(new com.meizu.volley.b.a("start", String.valueOf(this.i)));
        paramPairs.add(new com.meizu.volley.b.a("max", String.valueOf(50)));
        this.mLoadRequest = new FastJsonAuthParseRequest(getActivity(), a(), 0, paramPairs, (ParseListener) this, new com.meizu.cloud.base.b.f.b(this), new com.meizu.cloud.base.b.f.a(this));
        this.mLoadRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(getActivity()));
        com.meizu.volley.b.a(getActivity()).a().a(this.mLoadRequest);
    }

    protected boolean a(com.meizu.cloud.base.b.h.a resultModel) {
        this.f = resultModel;
        boolean loadSuccess = false;
        if (!(resultModel == null || resultModel.b == null)) {
            loadSuccess = true;
        }
        if (this.g) {
            hideFooter();
            this.g = false;
        } else {
            this.mbLoading = false;
            hideProgress();
            if (this.f != null && this.f.d && this.f.b.size() < 7) {
                loadData();
            } else if (!loadSuccess) {
                showEmptyView(getString(R.string.network_error), null, new OnClickListener(this) {
                    final /* synthetic */ f a;

                    {
                        this.a = r1;
                    }

                    public void onClick(View v) {
                        this.a.loadData();
                    }
                });
            } else if (!this.f.d && getRecyclerViewAdapter().getItemCount() == 0) {
                showEmptyView(getString(R.string.installed_no_data_remind_text), getResources().getDrawable(R.drawable.ic_error_page, null), null);
            }
        }
        if (!(resultModel == null || resultModel.b == null)) {
            insertData(resultModel.b);
        }
        return loadSuccess;
    }

    protected void onErrorResponse(s error) {
        if (this.g) {
            hideFooter();
            this.g = false;
        } else if (error instanceof com.android.volley.a) {
            hideProgress();
            getRecyclerViewAdapter().a(null);
            c();
            d();
        } else {
            showEmptyView(getEmptyTextString(), null, new OnClickListener(this) {
                final /* synthetic */ f a;

                {
                    this.a = r1;
                }

                public void onClick(View v) {
                    this.a.loadData();
                }
            });
        }
    }

    private void d() {
        showEmptyView(getString(R.string.unlogin_tips) + "," + getString(R.string.tap_to_login), null, new OnClickListener(this) {
            final /* synthetic */ f a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                if (this.a.a == null) {
                    this.a.a = new b(this.a, 0, new com.meizu.cloud.a.a(this) {
                        final /* synthetic */ AnonymousClass5 a;

                        {
                            this.a = r1;
                        }

                        public void a(String token, boolean isFromLogin) {
                            if (this.a.a.getActivity() != null && !isFromLogin) {
                                this.a.a.loadData();
                            }
                        }

                        public void a(int errorCode) {
                            this.a.a.a(errorCode);
                        }
                    });
                }
                this.a.a.a(false);
            }
        });
    }

    public com.meizu.cloud.base.a.d createRecyclerAdapter() {
        com.meizu.flyme.appcenter.b.a adapter = new com.meizu.flyme.appcenter.a.a(getActivity(), this.e);
        a(adapter);
        adapter.registerAdapterDataObserver(this.p);
        return adapter;
    }

    protected String a() {
        return RequestConstants.getRuntimeDomainUrl(getActivity(), RequestConstants.GET_HISTORY);
    }

    protected com.meizu.cloud.base.b.h.a<AppStructItem> b(String json) {
        return c(json);
    }

    protected com.meizu.cloud.base.b.h.a<AppStructItem> a(String json) {
        return c(json);
    }

    private com.meizu.cloud.base.b.h.a<AppStructItem> c(String json) {
        ResultModel<DataReultModel<AppStructItem>> resultModel = JSONUtils.parseResultModel(json, new TypeReference<ResultModel<DataReultModel<AppStructItem>>>(this) {
            final /* synthetic */ f a;

            {
                this.a = r1;
            }
        });
        if (resultModel == null || resultModel.getCode() != 200 || resultModel.getValue() == null || ((DataReultModel) resultModel.getValue()).data == null) {
            return null;
        }
        com.meizu.cloud.base.b.h.a<AppStructItem> loadResult = new h.a();
        loadResult.b = ((DataReultModel) resultModel.getValue()).data;
        loadResult.d = ((DataReultModel) resultModel.getValue()).more;
        this.i += 50;
        b(loadResult.b);
        return loadResult;
    }

    protected void b() {
        this.m.clear();
        if (this.l != null) {
            if (getRecyclerViewAdapter().d() > 0) {
                getRecyclerView().unCheckedAll();
            }
            this.l.finish();
            this.l = null;
        }
    }

    private void e() {
        String title;
        this.c.setTotalCount(getRecyclerViewAdapter().e().size());
        if (this.m.size() != getRecyclerViewAdapter().e().size()) {
            getRecyclerView().checkedAll();
            this.m.clear();
            this.m.addAll(getRecyclerViewAdapter().e());
            this.c.setSelectedCount(this.m.size());
        } else {
            getRecyclerView().unCheckedAll();
            this.m.clear();
            this.c.setSelectedCount(0);
        }
        if (!(this.l == null || this.l.getMenu() == null)) {
            for (int i = 0; i < this.l.getMenu().size(); i++) {
                MenuItem menuItem = this.l.getMenu().getItem(i);
                if (menuItem != null) {
                    if (this.m.size() == 0) {
                        menuItem.setEnabled(false);
                    } else {
                        menuItem.setEnabled(true);
                    }
                }
            }
        }
        if (this.m.size() == 0) {
            title = getString(R.string.chioce_app);
        } else {
            title = getResources().getString(R.string.mz_action_bar_multi_choice_title, new Object[]{Integer.valueOf(this.m.size())});
        }
        this.b.setTitle(title);
    }

    private boolean a(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.menu_download) {
            if (this.m.size() <= 0) {
                return true;
            }
            a(this.m);
            return true;
        } else if (id != R.id.menu_delete) {
            return false;
        } else {
            d(this.m);
            return true;
        }
    }

    protected void a(List<AppStructItem> selectedApps) {
        List stayRemoveItem = new ArrayList();
        for (AppStructItem item : selectedApps) {
            if (i.a(getActivity(), item.package_name) != null) {
                stayRemoveItem.add(item);
                getRecyclerViewAdapter().e().remove(item);
            }
        }
        getRecyclerViewAdapter().notifyDataSetChanged();
        if (stayRemoveItem.size() > 0) {
            a(c(stayRemoveItem));
        }
        selectedApps.removeAll(stayRemoveItem);
        if (selectedApps.size() != 0) {
            if (m.b(getActivity())) {
                for (AppStructItem item2 : selectedApps) {
                    item2.page_info = new int[]{0, 13, 0};
                    item2.install_page = this.mPageName;
                }
                this.e.a(new com.meizu.cloud.app.core.k((AppStructItem[]) selectedApps.toArray(new AppStructItem[selectedApps.size()])));
            } else if (getActivity() instanceof BaseCommonActivity) {
                ((BaseCommonActivity) getActivity()).l();
            }
            b();
        }
    }

    public void b(List<AppStructItem> appStructItems) {
        if (appStructItems != null && appStructItems.size() > 0) {
            List<String> pkgNames = new ArrayList(appStructItems.size());
            List<AppStructItem> items = new ArrayList(appStructItems.size());
            for (AppStructItem appStructItem : appStructItems) {
                if (x.d(getActivity()).a(appStructItem.package_name, appStructItem.version_code) != com.meizu.cloud.app.core.c.NOT_INSTALL || i.a(appStructItem.package_name, getActivity())) {
                    items.add(appStructItem);
                    pkgNames.add(appStructItem.package_name);
                }
            }
            if (items.size() > 0) {
                appStructItems.removeAll(items);
            }
            if (pkgNames.size() > 0) {
                a((String[]) pkgNames.toArray(new String[pkgNames.size()]));
            }
        }
    }

    public void a(String... packageNames) {
        if (packageNames.length > 0) {
            StringBuilder sb = new StringBuilder(packageNames.length);
            for (String packageName : packageNames) {
                sb.append(packageName).append(",");
            }
            TypeReference<ResultModel<Object>> typeReference = new TypeReference<ResultModel<Object>>(this) {
                final /* synthetic */ f a;

                {
                    this.a = r1;
                }
            };
            List<com.meizu.volley.b.a> paramPairs = new ArrayList();
            paramPairs.add(new com.meizu.volley.b.a("packages", sb.toString().substring(0, sb.length() - ",".length())));
            this.d = new com.meizu.volley.a.b(getActivity(), typeReference, RequestConstants.getRuntimeDomainUrl(getActivity(), RequestConstants.DELETE_HISTORY), paramPairs, new n.b<ResultModel<Object>>(this) {
                final /* synthetic */ f a;

                {
                    this.a = r1;
                }

                public void a(ResultModel<Object> resultModel) {
                    j.a("AsyncExecuteFragment", "remote history remove success.");
                }
            }, new com.android.volley.n.a(this) {
                final /* synthetic */ f a;

                {
                    this.a = r1;
                }

                public void a(s error) {
                    j.a("AsyncExecuteFragment", "remote history remove failed.");
                }
            });
            this.d.setParamProvider(com.meizu.cloud.app.utils.param.a.a(getActivity()));
            com.meizu.volley.b.a(getActivity()).a().a(this.d);
        }
    }

    private void d(final List<AppStructItem> items) {
        android.support.v7.app.b.a builder = new android.support.v7.app.b.a(getActivity(), R.style.Theme.Flyme.AppCompat.Light.Alert.ShowAtBottom.Color.DodgerBlue);
        CharSequence[] charSequenceArr = new CharSequence[1];
        charSequenceArr[0] = getString(R.string.remove_records_tips, Integer.valueOf(items.size()));
        android.support.v7.app.b alertDialog = builder.a(charSequenceArr, new DialogInterface.OnClickListener(this) {
            final /* synthetic */ f b;

            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    this.b.e(items);
                }
            }
        }, true).a((int) R.string.cancel, new DialogInterface.OnClickListener(this) {
            final /* synthetic */ f a;

            {
                this.a = r1;
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).b();
        alertDialog.a().setOverScrollMode(2);
        alertDialog.show();
    }

    private void e(List<AppStructItem> items) {
        if (items.size() > 1) {
        }
        if (items.size() > 0) {
            a(c((List) items));
            int removeIndex = getRecyclerViewAdapter().e().indexOf(items.get(0));
            int removeCount = items.size();
            getRecyclerViewAdapter().e().removeAll(items);
            b();
            getRecyclerViewAdapter().notifyItemRangeRemoved(removeIndex, removeCount);
        }
    }

    protected String[] c(List<AppStructItem> items) {
        if (items.size() <= 0) {
            return null;
        }
        List<String> packages = new ArrayList(items.size());
        for (AppStructItem item : items) {
            packages.add(item.package_name);
        }
        return (String[]) packages.toArray(new String[packages.size()]);
    }

    protected void a(com.meizu.flyme.appcenter.b.a onActionModeLintener) {
        this.n = onActionModeLintener;
    }

    private void a(boolean isPadding) {
        int left = getRecyclerView().getPaddingLeft();
        int top = getRecyclerView().getPaddingTop();
        int right = getRecyclerView().getPaddingRight();
        int bottom = getRecyclerView().getPaddingBottom();
        if (isPadding) {
            bottom += com.meizu.cloud.app.utils.d.d(getActivity());
        } else {
            bottom -= com.meizu.cloud.app.utils.d.d(getActivity());
        }
        getRecyclerView().setPadding(left, top, right, bottom);
    }

    protected void a(e wrapper) {
        if (getRecyclerViewAdapter() != null) {
            com.meizu.flyme.appcenter.a.a appListAdapter = (com.meizu.flyme.appcenter.a.a) getRecyclerViewAdapter();
            int i = 0;
            while (i < appListAdapter.getItemCount()) {
                AppStructItem item = (AppStructItem) appListAdapter.c(i);
                if (item == null || TextUtils.isEmpty(item.package_name) || !item.package_name.equals(wrapper.g())) {
                    i++;
                } else {
                    CirProButton button = (CirProButton) getRecyclerView().findViewWithTag(item.package_name);
                    if (button != null) {
                        appListAdapter.a().a(wrapper, button);
                        return;
                    }
                    return;
                }
            }
        }
    }

    protected void a(int errorCode) {
        if (!isAdded()) {
            return;
        }
        if (errorCode == 1) {
            com.meizu.cloud.app.utils.a.a(getActivity(), getString(R.string.access_account_info_error));
        } else if (errorCode != 4) {
            com.meizu.cloud.app.utils.a.a(getActivity(), getString(R.string.access_account_info_out_date));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isAdded()) {
            switch (requestCode) {
                case 0:
                    this.a.a(requestCode, resultCode, data);
                    return;
                default:
                    return;
            }
        }
    }

    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        AppStructItem historyItem = (AppStructItem) ((com.meizu.flyme.appcenter.a.a) getRecyclerViewAdapter()).c(position);
        Bundle bundle = new Bundle();
        bundle.putString("package_name", historyItem.package_name);
        bundle.putString("source_page", this.mPageName);
        bundle.putInt("source_page_id", this.mPageInfo[1]);
        d appDetailFragment = new d();
        appDetailFragment.setArguments(bundle);
        com.meizu.cloud.base.b.d.startFragment(getActivity(), appDetailFragment);
        com.meizu.cloud.statistics.b.a().a("item", this.mPageName, com.meizu.cloud.statistics.c.a(historyItem));
    }

    public void onRealPageStart() {
        com.meizu.cloud.statistics.b.a().a(this.mPageName);
    }

    public void onRealPageStop() {
        com.meizu.cloud.statistics.b.a().a(this.mPageName, null);
    }
}
