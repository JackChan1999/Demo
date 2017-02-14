package com.meizu.flyme.appcenter.fragment;

import a.a.a.c;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.content.h;
import android.support.v7.widget.MultiChoiceView;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.n;
import com.android.volley.s;
import com.meizu.cloud.app.core.AppListLoader;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.service.MultiAppDeleteService;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.app.utils.g;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.app.utils.param.BlockGotoPageInfo;
import com.meizu.cloud.app.utils.r;
import com.meizu.cloud.base.a.e;
import com.meizu.cloud.base.app.BaseCommonActivity;
import com.meizu.cloud.base.b.k;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.flyme.appcenter.a.b.b;
import com.meizu.mstore.R;
import com.tencent.mm.sdk.modelbase.BaseResp.ErrCode;
import flyme.support.v7.widget.LinearLayoutManager;
import flyme.support.v7.widget.MzRecyclerView.MultiChoiceModeListener;
import flyme.support.v7.widget.MzRecyclerView.OnItemClickListener;
import flyme.support.v7.widget.RecyclerView;
import flyme.support.v7.widget.TwoStateTextView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class l extends k<List<com.meizu.cloud.app.core.a>> implements b, OnItemClickListener {
    protected MultiChoiceView a;
    protected TwoStateTextView b;
    private ProgressDialog c;
    private ProgressDialog d;
    private boolean h = false;
    private Handler i = new Handler(this) {
        final /* synthetic */ l a;

        {
            this.a = r1;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ErrCode.ERR_SENT_FAILED /*-3*/:
                case 5:
                    if (this.a.c != null) {
                        this.a.h = false;
                        this.a.c.cancel();
                        break;
                    }
                    break;
                case 3:
                    ArrayList<String> tasks = (ArrayList) msg.getData().getSerializable("stay_task");
                    List<com.meizu.cloud.app.core.a> apps = this.a.f.c();
                    if (apps != null) {
                        ArrayList<String> stayCompareList = new ArrayList();
                        for (com.meizu.cloud.app.core.a app : apps) {
                            stayCompareList.add(app.a().packageName.trim());
                        }
                        stayCompareList.removeAll(tasks);
                        if (stayCompareList.size() > 0 || this.a.h) {
                            if (!(this.a.c == null || this.a.c.isShowing())) {
                                this.a.c.show();
                            }
                        } else if (this.a.c != null) {
                            this.a.c.cancel();
                        }
                        tasks.clear();
                        stayCompareList.clear();
                        break;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private Messenger j;
    private Messenger k;
    private ServiceConnection l = new ServiceConnection(this) {
        final /* synthetic */ l a;

        {
            this.a = r1;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            this.a.a(service);
        }

        public void onServiceDisconnected(ComponentName name) {
            this.a.g();
        }
    };
    private FastJsonRequest<JSONObject> m;
    private boolean n;
    private ActionMode o;
    private List<com.meizu.cloud.app.core.a> p = new ArrayList();
    private com.meizu.flyme.appcenter.b.a q;

    private class a implements MultiChoiceModeListener {
        final /* synthetic */ l a;

        private a(l lVar) {
            this.a = lVar;
        }

        public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {
            String title;
            com.meizu.cloud.app.core.a data = this.a.f.b(position);
            if (data instanceof com.meizu.cloud.app.core.a) {
                com.meizu.cloud.app.core.a item = data;
                if (checked) {
                    if (!this.a.p.contains(item)) {
                        this.a.p.add(item);
                    }
                } else if (this.a.p.contains(item)) {
                    this.a.p.remove(item);
                }
            }
            if (this.a.o.getMenu() != null) {
                for (int i = 0; i < this.a.o.getMenu().size(); i++) {
                    MenuItem menuItem = this.a.o.getMenu().getItem(i);
                    if (menuItem != null) {
                        if (this.a.p.size() == 0) {
                            menuItem.setEnabled(false);
                        } else {
                            menuItem.setEnabled(true);
                        }
                    }
                }
            }
            this.a.b.setTotalCount(this.a.f.c().size());
            this.a.b.setSelectedCount(this.a.p.size());
            if (this.a.p.size() == 0) {
                title = this.a.getString(R.string.chioce_app);
            } else {
                title = this.a.getString(R.string.mz_action_bar_multi_choice_title, Integer.valueOf(this.a.p.size()));
            }
            this.a.a.setTitle(title);
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            this.a.a(true);
            Fragment parentFragment = this.a.getParentFragment();
            if (parentFragment instanceof h) {
                ((h) parentFragment).b(false);
            }
            this.a.a = new MultiChoiceView(this.a.getActivity());
            this.a.a.setOnSelectAllItemClickListener(new OnClickListener(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onClick(View view) {
                    this.a.a.i();
                }
            });
            this.a.a.setOnCloseItemClickListener(new OnClickListener(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onClick(View view) {
                    this.a.a.c();
                }
            });
            this.a.b = (TwoStateTextView) this.a.a.getSelectAllView();
            if (this.a.q != null) {
                this.a.q.d_();
            }
            if (this.a.o != null) {
                this.a.o.finish();
            }
            this.a.o = actionMode;
            actionMode.setCustomView(this.a.a);
            this.a.getActivity().getMenuInflater().inflate(R.menu.app_manage_menu, menu);
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
            if (this.a.q != null) {
                this.a.q.h();
            }
            this.a.p.clear();
            this.a.o = null;
        }
    }

    private void a(IBinder service) {
        if (this.j == null) {
            this.j = new Messenger(service);
        }
        Message message = this.i.obtainMessage(1);
        message.what = 1;
        if (this.k == null) {
            this.k = new Messenger(this.i);
        }
        message.replyTo = this.k;
        Bundle bundle = new Bundle();
        bundle.putString("package", h());
        message.setData(bundle);
        try {
            this.j.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void g() {
        if (this.j != null) {
            Message message = this.i.obtainMessage(-1);
            message.what = -1;
            Bundle bundle = new Bundle();
            bundle.putString("package", h());
            message.setData(bundle);
            try {
                this.j.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.i.removeCallbacksAndMessages(null);
            this.j = null;
        }
    }

    private String h() {
        return i.a(getActivity()).metaData.getString("domain");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c.a().a((Object) this);
        this.mPageName = "myapp_installed";
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && this.e != null) {
            this.e.finishMultiChoice();
        }
    }

    public e a() {
        com.meizu.flyme.appcenter.b.a adapter = new com.meizu.flyme.appcenter.a.b(getActivity());
        adapter.a((b) this);
        a(adapter);
        return adapter;
    }

    public h<List<com.meizu.cloud.app.core.a>> a(int id, Bundle args) {
        return new AppListLoader(getActivity());
    }

    public void a(h<List<com.meizu.cloud.app.core.a>> loader, List<com.meizu.cloud.app.core.a> data) {
        super.a(loader, data);
        a((List) data);
        if (data.size() <= 0) {
            showEmptyView(getString(R.string.installed_no_data_remind_text), getResources().getDrawable(R.drawable.ic_error_page, null), null);
        }
        hideProgress();
    }

    public void a(h<List<com.meizu.cloud.app.core.a>> hVar) {
        a(null);
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        this.c = new ProgressDialog(getActivity());
        this.c.setMessage(getString(R.string.mulit_app_delete_waitting));
        this.c.setCancelable(false);
        this.d = new ProgressDialog(getActivity());
        this.d.setMessage(getString(R.string.please_wait));
        this.d.setCancelable(false);
        this.e.setEnableDragSelection(true);
        this.e.setChoiceMode(4);
        this.e.setMultiChoiceModeListener(new a());
        showProgress();
        this.e.setOnItemClickListener(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intent intent = new Intent(MultiAppDeleteService.class.getName());
        intent.setPackage(getActivity().getPackageName());
        getActivity().bindService(intent, this.l, 1);
        getActivity().startService(intent);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onDestroyView() {
        c();
        this.c.cancel();
        g();
        getActivity().unbindService(this.l);
        Intent intent = new Intent(MultiAppDeleteService.class.getName());
        intent.setPackage(getActivity().getPackageName());
        getActivity().stopService(intent);
        super.onDestroyView();
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.m != null) {
            this.m.cancel();
        }
        this.k = null;
        c.a().c(this);
    }

    private synchronized void a(final com.meizu.cloud.app.core.a param) {
        if (m.b(getActivity())) {
            if (!this.n) {
                this.n = true;
                this.d.show();
                TypeReference tr = new TypeReference<ResultModel<JSONObject>>(this) {
                    final /* synthetic */ l a;

                    {
                        this.a = r1;
                    }
                };
                List paramPairs = new ArrayList();
                paramPairs.add(new com.meizu.volley.b.a("package_name", param.a().packageName));
                this.m = new FastJsonRequest(tr, RequestConstants.getRuntimeDomainUrl(getActivity(), RequestConstants.GET_EXTERNAL_APP_URL), 0, paramPairs, new n.b<ResultModel<JSONObject>>(this) {
                    final /* synthetic */ l b;

                    public void a(ResultModel<JSONObject> response) {
                        this.b.n = false;
                        this.b.d.cancel();
                        if (this.b.isAdded()) {
                            if (response != null) {
                                if (response.getCode() != 200 || response.getValue() == null) {
                                    if (response.getCode() == RequestConstants.CODE_APP_NOT_FOUND) {
                                        com.meizu.cloud.app.utils.a.a(this.b.getActivity(), this.b.getString(R.string.not_in_appcenter));
                                        return;
                                    }
                                } else if (((JSONObject) response.getValue()).containsKey("redirect_url")) {
                                    String detailUrl = ((JSONObject) response.getValue()).getString("redirect_url");
                                    if (!TextUtils.isEmpty(detailUrl)) {
                                        BlockGotoPageInfo pageInfo = new BlockGotoPageInfo();
                                        pageInfo.a = PushConstants.EXTRA_APPLICATION_PENDING_INTENT;
                                        pageInfo.b = detailUrl;
                                        pageInfo.i = this.b.mPageName;
                                        com.meizu.flyme.appcenter.c.a.a(this.b.getActivity(), pageInfo);
                                        com.meizu.cloud.statistics.b.a().a("item", this.b.mPageName, com.meizu.cloud.statistics.c.a((int) g.d(detailUrl), param.a().packageName, param.b()));
                                        return;
                                    }
                                }
                            }
                            com.meizu.cloud.app.utils.a.a(this.b.getActivity(), this.b.getString(R.string.server_error));
                        }
                    }
                }, new com.android.volley.n.a(this) {
                    final /* synthetic */ l a;

                    {
                        this.a = r1;
                    }

                    public void a(s error) {
                        this.a.n = false;
                        this.a.d.cancel();
                        if (this.a.isAdded()) {
                            com.meizu.cloud.app.utils.a.a(this.a.getActivity(), this.a.getString(R.string.server_error));
                        }
                    }
                });
                this.m.setParamProvider(com.meizu.cloud.app.utils.param.a.a(getActivity()));
                com.meizu.volley.b.a(getActivity()).a().a(this.m);
            }
        } else if (getActivity() instanceof BaseCommonActivity) {
            ((BaseCommonActivity) getActivity()).l();
        }
    }

    private boolean a(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.menu_delete || this.p.size() <= 0) {
            return false;
        }
        a(this.p, true);
        return true;
    }

    protected void c() {
        this.p.clear();
        if (this.o != null) {
            this.e.unCheckedAll();
            this.o.finish();
            this.o = null;
        }
        this.e.finishMultiChoice();
    }

    private void i() {
        String title;
        this.b.setTotalCount(this.f.c().size());
        if (this.p.size() != this.f.c().size()) {
            this.e.checkedAll();
            this.p.clear();
            this.p.addAll(this.f.c());
            this.b.setSelectedCount(this.p.size());
        } else {
            this.e.unCheckedAll();
            this.p.clear();
            this.b.setSelectedCount(0);
        }
        if (!(this.o == null || this.o.getMenu() == null)) {
            for (int i = 0; i < this.o.getMenu().size(); i++) {
                MenuItem menuItem = this.o.getMenu().getItem(i);
                if (menuItem != null) {
                    if (this.p.size() == 0) {
                        menuItem.setEnabled(false);
                    } else {
                        menuItem.setEnabled(true);
                    }
                }
            }
        }
        if (this.p.size() == 0) {
            title = getString(R.string.chioce_app);
        } else {
            title = getResources().getString(R.string.mz_action_bar_multi_choice_title, new Object[]{Integer.valueOf(this.p.size())});
        }
        this.a.setTitle(title);
    }

    private void a(final List<com.meizu.cloud.app.core.a> apps, final boolean isMulitChoice) {
        android.support.v7.app.b.a builder = new android.support.v7.app.b.a(getActivity(), R.style.Theme.Flyme.AppCompat.Light.Alert.ShowAtBottom.Color.DodgerBlue);
        CharSequence[] charSequenceArr = new CharSequence[1];
        charSequenceArr[0] = getString(R.string.remove_apps_tips, Integer.valueOf(apps.size()));
        android.support.v7.app.b alertDialog = builder.a(charSequenceArr, new DialogInterface.OnClickListener(this) {
            final /* synthetic */ l c;

            public void onClick(DialogInterface dialog, int which) {
                if (which != 0) {
                    return;
                }
                if (apps.size() != 1) {
                    boolean isAccessControl = false;
                    for (com.meizu.cloud.app.core.a app : apps) {
                        if (r.a(this.c.getActivity(), app.a().packageName).booleanValue()) {
                            isAccessControl = true;
                            break;
                        }
                    }
                    if (isAccessControl) {
                        r.a(this.c, 2048);
                    } else {
                        this.c.j();
                    }
                } else if (r.a(this.c.getActivity(), ((com.meizu.cloud.app.core.a) apps.get(0)).a().packageName).booleanValue()) {
                    if (!isMulitChoice) {
                        this.c.p.addAll(apps);
                    }
                    r.a(this.c, 1024);
                } else {
                    com.meizu.cloud.statistics.b.a().a("uninstall", this.c.mPageName, com.meizu.cloud.statistics.c.a(((com.meizu.cloud.app.core.a) apps.get(0)).a().packageName));
                    this.c.b((com.meizu.cloud.app.core.a) apps.get(0));
                }
            }
        }, true).a((int) R.string.cancel, new DialogInterface.OnClickListener(this) {
            final /* synthetic */ l a;

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

    protected void a(com.meizu.flyme.appcenter.b.a onActionModeLintener) {
        this.q = onActionModeLintener;
    }

    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        com.meizu.cloud.app.core.a appEntry = (com.meizu.cloud.app.core.a) ((com.meizu.flyme.appcenter.a.b) e()).b(position);
        if (appEntry != null) {
            a(appEntry);
        }
    }

    public void a(View v, com.meizu.cloud.app.core.a appEntry) {
        List appEntries = new ArrayList(1);
        appEntries.add(appEntry);
        a(appEntries, false);
    }

    private void a(boolean isPadding) {
        int left = this.e.getPaddingLeft();
        int top = this.e.getPaddingTop();
        int right = this.e.getPaddingRight();
        int bottom = this.e.getPaddingBottom();
        if (isPadding) {
            bottom += d.d(getActivity());
        } else {
            bottom -= d.d(getActivity());
        }
        this.e.setPadding(left, top, right, bottom);
    }

    private void b(com.meizu.cloud.app.core.a localAppInfo) {
        com.meizu.cloud.app.downlad.d.a(getActivity()).i(localAppInfo.a().packageName);
        c();
        com.meizu.cloud.statistics.b.a().a("uninstall_one", "", null);
    }

    private void j() {
        if (!(this.c == null || this.c.isShowing())) {
            this.c.show();
        }
        Message message = Message.obtain();
        message.what = 4;
        Bundle data = new Bundle();
        data.putString("package", h());
        ArrayList<String> appPackages = new ArrayList();
        for (com.meizu.cloud.app.core.a appEntry : this.p) {
            appPackages.add(appEntry.a().packageName.trim());
            com.meizu.cloud.statistics.b.a().a("uninstall", this.mPageName, com.meizu.cloud.statistics.c.a(appEntry.a().packageName.trim()));
        }
        data.putSerializable("apps", appPackages);
        message.setData(data);
        try {
            this.j.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        c();
        this.h = true;
        com.meizu.cloud.statistics.b.a().a("uninstall_more", "", null);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            return;
        }
        if (requestCode == 1024) {
            if (this.p.size() == 1) {
                com.meizu.cloud.app.core.a appEntry = (com.meizu.cloud.app.core.a) this.p.get(0);
                if (!(appEntry == null || TextUtils.isEmpty(appEntry.a().packageName))) {
                    b(appEntry);
                }
            }
            this.p.clear();
        } else if (requestCode == 2048) {
            j();
        }
    }

    public void onEventMainThread(com.meizu.cloud.app.c.a appStateChangeEvent) {
        List<com.meizu.cloud.app.core.a> appList;
        if (appStateChangeEvent.c == -1) {
            appList = ((com.meizu.flyme.appcenter.a.b) e()).c();
            boolean removeSuccess = false;
            int removeIndex = -1;
            for (int i = 0; i < appList.size(); i++) {
                com.meizu.cloud.app.core.a app = (com.meizu.cloud.app.core.a) appList.get(i);
                if (app.a().packageName.equals(appStateChangeEvent.b)) {
                    removeSuccess = appList.remove(app);
                    if (removeSuccess) {
                        removeIndex = i;
                    }
                    if (removeSuccess) {
                        a(appStateChangeEvent.b);
                    } else {
                        e().notifyItemRemoved(removeIndex);
                    }
                }
            }
            if (removeSuccess) {
                a(appStateChangeEvent.b);
            } else {
                e().notifyItemRemoved(removeIndex);
            }
        } else if (appStateChangeEvent.c != 1) {
            a(appStateChangeEvent.b);
        } else if (!i.a(appStateChangeEvent.b, getActivity())) {
            appList = ((com.meizu.flyme.appcenter.a.b) e()).c();
            PackageInfo packageInfo = i.a(getActivity(), appStateChangeEvent.b);
            com.meizu.cloud.app.core.a entry = new com.meizu.cloud.app.core.a(new AppListLoader(getActivity()), packageInfo.applicationInfo);
            entry.a(getActivity());
            String iconFileName = entry.a().packageName;
            if (!new File(getActivity().getCacheDir(), "app_icons" + File.separator + iconFileName).exists()) {
                AppListLoader.a(getActivity(), entry.c(), iconFileName);
            }
            entry.a(packageInfo.lastUpdateTime);
            appList.add(0, entry);
            e().notifyItemInserted(0);
        }
    }

    public void onEventMainThread(com.meizu.cloud.app.c.b appUpdateCheckEvent) {
        if (appUpdateCheckEvent.c) {
            for (String pkg : appUpdateCheckEvent.a) {
                a(pkg);
            }
        }
    }

    public void onEventMainThread(com.meizu.cloud.app.c.h mzConfirmPasswordEvent) {
        if (mzConfirmPasswordEvent.b != -1) {
            return;
        }
        if (mzConfirmPasswordEvent.a == 1024) {
            if (this.p.size() == 1) {
                com.meizu.cloud.app.core.a appEntry = (com.meizu.cloud.app.core.a) this.p.get(0);
                if (!(appEntry == null || TextUtils.isEmpty(appEntry.a().packageName))) {
                    b(appEntry);
                }
            }
            this.p.clear();
        } else if (mzConfirmPasswordEvent.a == 2048) {
            j();
        }
    }

    protected void a(String packageName) {
        int firstVisiblePosition = ((LinearLayoutManager) d().getLayoutManager()).findFirstVisibleItemPosition();
        int lastVisiblePosition = ((LinearLayoutManager) d().getLayoutManager()).findLastVisibleItemPosition();
        com.meizu.flyme.appcenter.a.b appListAdapter = (com.meizu.flyme.appcenter.a.b) e();
        int i = firstVisiblePosition;
        while (i < lastVisiblePosition) {
            com.meizu.cloud.app.core.a app = (com.meizu.cloud.app.core.a) appListAdapter.b(i);
            if (app == null || TextUtils.isEmpty(app.a().packageName) || !app.a().packageName.equals(packageName)) {
                i++;
            } else {
                appListAdapter.notifyItemChanged(i);
                return;
            }
        }
    }

    public void onRealPageStart() {
        com.meizu.cloud.statistics.b.a().a(this.mPageName);
    }

    public void onRealPageStop() {
        com.meizu.cloud.statistics.b.a().a(this.mPageName, null);
    }

    protected String b() {
        return getString(R.string.installed_no_data_remind_text);
    }
}
