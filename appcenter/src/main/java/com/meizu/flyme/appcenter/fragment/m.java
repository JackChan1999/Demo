package com.meizu.flyme.appcenter.fragment;

import a.a.a.c;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.h;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.meizu.cloud.app.block.Blockable;
import com.meizu.cloud.app.block.customblock.AccountItem;
import com.meizu.cloud.app.block.customblock.DividerItem;
import com.meizu.cloud.app.block.customblock.FuctionItem;
import com.meizu.cloud.app.block.customblock.MoreItem;
import com.meizu.cloud.app.block.customblock.PartitionItem;
import com.meizu.cloud.app.block.customblock.UpdateRefreshItem;
import com.meizu.cloud.app.c.i;
import com.meizu.cloud.app.core.r;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.fragment.k;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.GameEntryInfo;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.base.a.e.b;
import com.meizu.cloud.base.app.BaseMainActivity;
import com.meizu.flyme.appcenter.a.f;
import com.meizu.flyme.appcenter.activitys.AppSettingsPreferenceActivity;
import com.meizu.mstore.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class m extends k<List<Blockable>> implements com.meizu.cloud.app.a.l.a, b<Blockable> {
    public static final Comparator<ServerUpdateAppInfo> h = new Comparator<ServerUpdateAppInfo>() {
        public /* synthetic */ int compare(Object obj, Object obj2) {
            return a((ServerUpdateAppInfo) obj, (ServerUpdateAppInfo) obj2);
        }

        public int a(ServerUpdateAppInfo lhs, ServerUpdateAppInfo rhs) {
            if (lhs.version_create_time == rhs.version_create_time) {
                return 0;
            }
            return lhs.version_create_time - rhs.version_create_time < 0 ? 1 : -1;
        }
    };
    private f i;
    private List<AccountItem> j;
    private List<DividerItem> k;
    private List<FuctionItem> l;
    private List<PartitionItem> m;
    private List<UpdateRefreshItem> n;
    private List<MoreItem> o;
    private List<Blockable> p;
    private r q;
    private a r;
    private com.meizu.cloud.app.downlad.f.k s = new com.meizu.cloud.app.downlad.f.k(this) {
        final /* synthetic */ m a;

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
            if (wrapper.y() == RequestConstants.CODE_APP_NOT_FOUND) {
                this.a.b(wrapper.g());
            } else {
                this.a.a(wrapper);
            }
        }

        public void onInstallStateChange(e wrapper) {
            if (wrapper.f() == com.meizu.cloud.app.downlad.f.f.DELETE_SUCCESS) {
                this.a.b(wrapper.g());
            } else if (wrapper.f() == com.meizu.cloud.app.downlad.f.f.INSTALL_SUCCESS) {
                this.a.b(wrapper.g());
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
    private com.meizu.cloud.app.fragment.k.a t = new com.meizu.cloud.app.fragment.k.a(this) {
        final /* synthetic */ m a;

        {
            this.a = r1;
        }

        public void a() {
            if (this.a.i != null) {
                this.a.i.a(this.a.a);
            }
        }

        public void b() {
            if (this.a.i != null) {
                this.a.i.a(this.a.a);
            }
        }

        public void c() {
            if (this.a.i != null) {
                this.a.i.a(this.a.a);
            }
        }

        public void d() {
            if (this.a.i != null) {
                this.a.i.a(this.a.a);
            }
        }

        public void e() {
        }

        public void f() {
        }

        public void a(boolean isLogin) {
            if (this.a.i != null) {
                this.a.i.a(this.a.a);
            }
        }
    };

    public static class a extends android.support.v4.content.a<List<Blockable>> {
        private List<Blockable> o;
        private r p;

        public /* synthetic */ void a(Object obj) {
            b((List) obj);
        }

        public /* synthetic */ void b(Object obj) {
            a((List) obj);
        }

        public /* synthetic */ Object d() {
            return z();
        }

        public a(Context context, r updateCheckerDbHelper) {
            super(context);
            this.p = updateCheckerDbHelper;
        }

        public List<Blockable> z() {
            if (this.o == null) {
                this.o = new ArrayList();
            } else {
                this.o.clear();
            }
            List<ServerUpdateAppInfo<GameEntryInfo>> updateAppInfos = this.p.c(h());
            Collections.sort(updateAppInfos, m.h);
            this.o.addAll(updateAppInfos);
            return this.o;
        }

        public void a(List<Blockable> data) {
            if (k() && data != null) {
                c(data);
            }
            List<Blockable> oldApps = this.o;
            this.o = data;
            if (i()) {
                super.b((Object) data);
            }
            if (oldApps != null) {
                c(oldApps);
            }
        }

        protected void m() {
            if (this.o != null) {
                a(this.o);
            }
            if (v() || this.o == null) {
                o();
            }
        }

        protected void q() {
            n();
        }

        public void b(List<Blockable> data) {
            super.a(data);
            c(data);
        }

        protected void u() {
            super.u();
            q();
            if (this.o != null) {
                c(this.o);
                this.o = null;
            }
        }

        protected void c(List<Blockable> list) {
        }
    }

    protected void setupActionBar() {
    }

    public void onCreate(Bundle savedInstanceState) {
        this.mIsFirstClassPage = true;
        this.j = new ArrayList(1);
        this.j.add(new AccountItem());
        this.k = new ArrayList(1);
        this.k.add(new DividerItem());
        this.l = new ArrayList(3);
        this.l.add(new FuctionItem(R.id.download_manage, R.drawable.ic_download_manage, getString(R.string.download_manager)));
        this.l.add(new FuctionItem(R.id.install_manage, R.drawable.ic_install_record_manage, getString(R.string.install_record)));
        this.l.add(new FuctionItem(R.id.setting_manage, R.drawable.ic_setting_manage, getString(R.string.settings)));
        this.m = new ArrayList(1);
        this.m.add(new PartitionItem(getString(R.string.can_update, Integer.valueOf(0)), getString(R.string.update_all), true, -1));
        this.n = new ArrayList(1);
        this.n.add(new UpdateRefreshItem());
        this.p = new ArrayList();
        this.o = new ArrayList(1);
        this.o.add(new MoreItem());
        this.q = r.a(getActivity());
        d.a(getActivity().getApplicationContext()).a(this.s);
        c.a().a((Object) this);
        a(this.t);
        super.onCreate(savedInstanceState);
    }

    public void onDestroy() {
        if (this.d != null) {
            this.d.a();
        }
        super.onDestroy();
        d.a(getActivity().getApplicationContext()).b(this.s);
        c.a().c(this);
    }

    public void onEventMainThread(com.meizu.cloud.app.c.a appStateChangeEvent) {
        b(appStateChangeEvent.b);
    }

    public void onEventMainThread(com.meizu.cloud.app.c.b appUpdateCheckEvent) {
        if (appUpdateCheckEvent.b.e) {
            getLoaderManager().b(0, null, this);
        }
    }

    public void onEventMainThread(com.meizu.cloud.app.c.c updateDBChangeEvent) {
        ServerUpdateAppInfo updateAppInfo;
        if (updateDBChangeEvent.a == -1) {
            for (Blockable blockable : this.p) {
                if (blockable instanceof ServerUpdateAppInfo) {
                    updateAppInfo = (ServerUpdateAppInfo) blockable;
                    if (updateAppInfo.id == updateDBChangeEvent.b) {
                        b(updateAppInfo.package_name);
                        return;
                    }
                }
            }
        } else if (updateDBChangeEvent.a == 1) {
            b(updateDBChangeEvent.b);
        } else if (updateDBChangeEvent.a == 0) {
            for (Blockable blockable2 : this.p) {
                if (blockable2 instanceof ServerUpdateAppInfo) {
                    updateAppInfo = (ServerUpdateAppInfo) blockable2;
                    if (updateAppInfo.id == updateDBChangeEvent.b) {
                        a(updateAppInfo.package_name);
                        return;
                    }
                }
            }
        } else {
            getLoaderManager().b(0, null, this);
        }
    }

    public void onEventMainThread(i taskFactoryEvent) {
        boolean z = false;
        List<e> downloadWrappers = d.a(getActivity().getApplicationContext()).g(1, 3);
        int count = this.p.size();
        for (Blockable blockable : this.p) {
            if (blockable instanceof ServerUpdateAppInfo) {
                ServerUpdateAppInfo updateAppInfo = (ServerUpdateAppInfo) blockable;
                for (e downloadWrapper : downloadWrappers) {
                    if (!downloadWrapper.F() && updateAppInfo.package_name.equals(downloadWrapper.g()) && updateAppInfo.version_code == downloadWrapper.h()) {
                        count--;
                    }
                }
            }
        }
        TextView textView = (TextView) d().findViewById(16908309);
        if (textView != null) {
            if (count > 0) {
                z = true;
            }
            textView.setEnabled(z);
            return;
        }
        ((PartitionItem) this.m.get(0)).tag = Boolean.valueOf(true);
    }

    public com.meizu.cloud.base.a.e a() {
        this.i = new f(getActivity(), this.a);
        this.i.a((b) this);
        this.i.a(this);
        return this.i;
    }

    public h<List<Blockable>> a(int id, Bundle args) {
        this.r = new a(getActivity(), this.q);
        return this.r;
    }

    public void a(h<List<Blockable>> loader, List<Blockable> data) {
        super.a((h) loader, (List) data);
        final List<Blockable> list = new ArrayList();
        list.addAll(this.j);
        list.addAll(this.k);
        list.addAll(this.l);
        if (data.size() > 0) {
            this.p.clear();
            this.p.addAll(data);
            ((PartitionItem) this.m.get(0)).tag = Boolean.valueOf(g());
            list.addAll(this.m);
            list.addAll(this.p);
        } else {
            list.addAll(this.n);
        }
        list.addAll(this.o);
        postOnPagerIdle(new Runnable(this) {
            final /* synthetic */ m b;

            public void run() {
                if (this.b.getActivity() != null && this.b.mRunning) {
                    this.b.a(list);
                    this.b.hideProgress();
                }
            }
        });
    }

    public void a(h<List<Blockable>> hVar) {
    }

    public void a(View itemView, Blockable blockable) {
        boolean z = false;
        if (blockable instanceof AccountItem) {
            if (this.d == null) {
                this.d = new com.meizu.cloud.a.b(this, 0, new com.meizu.cloud.a.a(this) {
                    final /* synthetic */ m a;

                    {
                        this.a = r1;
                    }

                    public void a(String token, boolean isFromLogin) {
                        if (this.a.getActivity() != null) {
                            if (this.a.b == null) {
                                this.a.c();
                            }
                            if (!isFromLogin) {
                                try {
                                    Intent intent = new Intent();
                                    intent.setAction("com.meizu.account.ACCOUNTCENTER");
                                    intent.putExtra("Action", "for_main");
                                    this.a.getActivity().startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                    com.meizu.cloud.app.utils.a.a(this.a.getActivity(), this.a.getString(R.string.target_interface_exception));
                                }
                            }
                        }
                    }

                    public void a(int errorCode) {
                        this.a.a(errorCode);
                    }
                });
            }
            this.d.a(false);
        } else if (blockable instanceof FuctionItem) {
            FuctionItem fuctionItem = (FuctionItem) blockable;
            if (fuctionItem.id == R.id.download_manage) {
                BaseMainActivity.a(getActivity(), a(e.class, null), false);
            } else if (fuctionItem.id == R.id.install_manage) {
                com.meizu.cloud.base.b.d.startFragment(getActivity(), a(h.class, null));
            } else if (fuctionItem.id == R.id.setting_manage) {
                startActivity(new Intent(getActivity(), AppSettingsPreferenceActivity.class));
            }
        } else if (blockable instanceof ServerUpdateAppInfo) {
            View rootview = d().findViewWithTag(((ServerUpdateAppInfo) blockable).package_name);
            if (rootview != null) {
                CheckBox checkBox = (CheckBox) rootview.findViewById(R.id.publish_time);
                if (!checkBox.isChecked()) {
                    z = true;
                }
                checkBox.setChecked(z);
            }
        }
    }

    public void a(View v, ServerUpdateAppInfo positionItem, int position) {
        int id = v.getId();
        if (positionItem == null) {
            return;
        }
        if (id == R.id.tv_left) {
            a(positionItem);
        } else if (id == R.id.tv_right) {
            d detailsFragment = new d();
            Bundle bundle = new Bundle();
            bundle.putString("url", positionItem.url);
            bundle.putInt("source_page_id", 11);
            bundle.putString("source_page", this.mPageName);
            detailsFragment.setArguments(bundle);
            com.meizu.cloud.base.b.d.startFragment(getActivity(), detailsFragment);
            com.meizu.cloud.statistics.b.a().a("item", this.mPageName, com.meizu.cloud.statistics.c.a(positionItem.id, positionItem.package_name, positionItem.name, position + 1));
        }
    }

    protected void a(final ServerUpdateAppInfo updateAppInfo) {
        Builder builder = new Builder(getActivity());
        builder.setCancelable(true);
        builder.setMessage(getString(R.string.confirm_ignore_tips, updateAppInfo.name));
        builder.setPositiveButton(R.string.Continue, new OnClickListener(this) {
            final /* synthetic */ m b;

            public void onClick(DialogInterface dialogInterface, int i) {
                com.meizu.cloud.app.core.m.a.a(this.b.getActivity(), updateAppInfo.package_name, updateAppInfo.version_code, "ignore_update_apps");
                this.b.b(updateAppInfo.package_name);
                Map<String, String> dataMap = new HashMap();
                dataMap.put("appid", String.valueOf(updateAppInfo.id));
                dataMap.put("apkname", updateAppInfo.package_name);
                dataMap.put("name", updateAppInfo.name);
                com.meizu.cloud.statistics.b.a().a("ignore_update", null, dataMap);
            }
        });
        builder.setNegativeButton(R.string.cancel, new OnClickListener(this) {
            final /* synthetic */ m a;

            {
                this.a = r1;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        com.meizu.cloud.app.utils.r.a(alertDialog);
        alertDialog.show();
    }

    private void b(String packageName) {
        if (this.i != null) {
            boolean removeSuccess = false;
            e downloadWrapper = d.a(getActivity()).b(packageName);
            List<Blockable> blockableList = this.i.c();
            int removeIndex = -1;
            if (downloadWrapper == null || !com.meizu.cloud.app.core.d.a(downloadWrapper.y())) {
                for (int i = 0; i < blockableList.size(); i++) {
                    Blockable blockable = (Blockable) blockableList.get(i);
                    if (blockable instanceof ServerUpdateAppInfo) {
                        ServerUpdateAppInfo updateAppInfo = (ServerUpdateAppInfo) blockable;
                        if (updateAppInfo.package_name.equals(packageName)) {
                            removeSuccess = blockableList.remove(blockable);
                            if (removeSuccess) {
                                this.p.remove(updateAppInfo);
                                removeIndex = i;
                            }
                        }
                    }
                }
            }
            if (removeSuccess) {
                if (this.p.size() > 0) {
                    this.i.notifyItemRemoved(removeIndex);
                    int titleIndex = blockableList.indexOf(this.m.get(0));
                    if (titleIndex != -1) {
                        this.i.notifyItemChanged(titleIndex);
                    }
                } else if (blockableList.remove(this.m.get(0))) {
                    this.i.notifyItemRangeRemoved(removeIndex - 1, 1 + 1);
                    int insertIndex = blockableList.indexOf(this.o.get(0));
                    if (insertIndex != -1) {
                        blockableList.addAll(insertIndex, this.n);
                    }
                    this.i.notifyItemInserted(insertIndex);
                } else {
                    this.i.notifyItemChanged(removeIndex);
                }
                this.i.notifyItemChanged(blockableList.indexOf(this.l.get(2)));
            } else if (downloadWrapper != null) {
                a(downloadWrapper);
            }
        }
    }

    protected void b(int id) {
        if (this.i != null) {
            ServerUpdateAppInfo updateAppInfo = this.q.a((long) id);
            if (updateAppInfo != null && updateAppInfo.existUpdate()) {
                List<Blockable> blockableList = this.i.c();
                int insertIndex;
                if (this.p.size() <= 0) {
                    insertIndex = blockableList.indexOf(this.n.get(0));
                    blockableList.removeAll(this.n);
                    this.i.notifyItemRemoved(insertIndex);
                    this.p.add(updateAppInfo);
                    blockableList.add(insertIndex, updateAppInfo);
                    blockableList.addAll(insertIndex, this.m);
                    this.i.notifyItemRangeInserted(insertIndex, 2);
                    return;
                }
                insertIndex = blockableList.indexOf(this.m.get(0)) + 1;
                this.p.add(updateAppInfo);
                if (insertIndex != -1) {
                    blockableList.add(insertIndex, updateAppInfo);
                    this.i.notifyItemInserted(insertIndex);
                    this.i.notifyItemChanged(blockableList.indexOf(this.m.get(0)));
                }
            }
        }
    }

    protected void a(e wrapper) {
        if (this.i != null) {
            for (int i = 0; i < this.i.b(); i++) {
                Blockable item = (Blockable) this.i.b(i);
                if (item != null && (item instanceof ServerUpdateAppInfo)) {
                    ServerUpdateAppInfo updateAppInfo = (ServerUpdateAppInfo) item;
                    if (!TextUtils.isEmpty(updateAppInfo.package_name) && updateAppInfo.package_name.equals(wrapper.g())) {
                        View rootview = d().findViewWithTag(updateAppInfo.package_name);
                        if (rootview != null) {
                            ((f) e()).a().a(wrapper, (CirProButton) rootview.findViewById(R.id.btnInstall));
                            return;
                        }
                        return;
                    }
                }
            }
        }
    }

    protected void a(String packageName) {
        if (this.i != null) {
            for (int i = 0; i < this.i.b(); i++) {
                Blockable item = (Blockable) this.i.b(i);
                if (item != null && (item instanceof ServerUpdateAppInfo)) {
                    com.meizu.cloud.app.core.t.a updateAppInfo = (ServerUpdateAppInfo) item;
                    if (!TextUtils.isEmpty(updateAppInfo.package_name) && updateAppInfo.package_name.equals(packageName)) {
                        View rootview = d().findViewWithTag(updateAppInfo.package_name);
                        if (rootview != null) {
                            ((f) e()).a().a(updateAppInfo, null, false, (CirProButton) rootview.findViewById(R.id.btnInstall));
                            return;
                        }
                        return;
                    }
                }
            }
        }
    }

    private boolean g() {
        int count = 0;
        for (Blockable blockable : this.p) {
            if (d.a(getActivity()).f(((ServerUpdateAppInfo) blockable).package_name)) {
                count++;
            }
        }
        if (count >= this.p.size()) {
            return false;
        }
        return true;
    }
}
