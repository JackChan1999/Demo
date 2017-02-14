package com.meizu.cloud.app.update.exclude;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.meizu.cloud.app.fragment.l;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.statistics.b;
import com.meizu.common.widget.Switch;
import com.meizu.common.widget.f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class c extends l {
    protected com.meizu.cloud.thread.c e;
    private List<e> f = new ArrayList();
    private List<e> g = new ArrayList();
    private List<String> h;
    private boolean i = false;

    protected class a extends f {
        final /* synthetic */ c a;

        public a(c cVar, Context context, List[] lists) {
            this.a = cVar;
            super(context, lists);
        }

        protected View a(Context context, int position, int partitionIndex, ViewGroup parent) {
            View groupHeaderView = LayoutInflater.from(context).inflate(g.block_item_container_v2, null);
            TextView groupHeaderTitle = (TextView) groupHeaderView.findViewById(16908308);
            View paddingView = groupHeaderView.findViewById(com.meizu.cloud.b.a.f.title_title_layout);
            paddingView.setBackground(null);
            groupHeaderTitle.setVisibility(0);
            if (position == 0) {
                paddingView.setVisibility(8);
            } else {
                paddingView.setVisibility(0);
            }
            if (partitionIndex == 0) {
                groupHeaderTitle.setText(context.getString(i.update_exclude_title, new Object[]{Integer.valueOf(this.a.f.size())}));
            } else if (partitionIndex == 1) {
                groupHeaderTitle.setText(context.getString(i.update_unexclude_title));
            }
            return groupHeaderView;
        }

        protected void a(View v, Context context, int position, int partitionIndex) {
            TextView groupHeaderTitle = (TextView) v.findViewById(16908308);
            if (partitionIndex == 0) {
                groupHeaderTitle.setText(context.getString(i.update_exclude_title, new Object[]{Integer.valueOf(this.a.f.size())}));
            } else if (partitionIndex == 1) {
                groupHeaderTitle.setText(context.getString(i.update_unexclude_title));
            }
        }

        protected View a(Context context, int position, int partitionIndex, Object object, int offset, int itemBgType, ViewGroup parent) {
            ViewGroup groupItem = (ViewGroup) LayoutInflater.from(context).inflate(g.mc_group_list_item_layout, parent, false);
            AppUpdateSwichItemView itemView = new AppUpdateSwichItemView(context);
            itemView.setId(16908290);
            groupItem.addView(itemView);
            return groupItem;
        }

        protected void a(View v, final Context context, int position, int partitionIndex, Object object, int offset, int itemBgType) {
            AppUpdateSwichItemView itemView = (AppUpdateSwichItemView) v.findViewById(16908290);
            final e packageInfoEx = (e) object;
            itemView.setIcon(packageInfoEx.b);
            itemView.setTitleTextView(packageInfoEx.a.applicationInfo.loadLabel(context.getPackageManager()).toString());
            itemView.setOnCheckedChangeListener(null);
            itemView.setCheck(packageInfoEx.c);
            itemView.setOnCheckedChangeListener(new OnCheckedChangeListener(this) {
                final /* synthetic */ a c;

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if ((packageInfoEx.c ^ isChecked) != 0) {
                        packageInfoEx.c = isChecked;
                        new HashMap().put("pack_name", packageInfoEx.a.packageName);
                        if (isChecked) {
                            d.a(context).a(packageInfoEx.a.packageName);
                        } else {
                            d.a(context).b(packageInfoEx.a.packageName);
                        }
                    }
                }
            });
            v.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onClick(View v) {
                    Switch itemSwitch = (Switch) v.findViewById(com.meizu.cloud.b.a.f.app_switch);
                    itemSwitch.setChecked(!itemSwitch.isChecked());
                }
            });
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().a(getString(i.update_exclude_activity_title));
    }

    protected void setupActionBar() {
        super.setupActionBar();
    }

    public void onResume() {
        super.onResume();
        if (!this.i) {
            e();
        }
    }

    public void onPause() {
        super.onPause();
    }

    public void onStart() {
        super.onStart();
        b.a().a("ingoreupdate");
    }

    public void onStop() {
        super.onStop();
        b.a().a("ingoreupdate", null);
    }

    private void e() {
        if (this.e == null || this.e.a()) {
            f();
            this.e = asyncExec(new Runnable(this) {
                final /* synthetic */ c a;

                {
                    this.a = r1;
                }

                public void run() {
                    Process.setThreadPriority(10);
                    this.a.h = d.a(this.a.getActivity()).a();
                    List<PackageInfo> allPackages = this.a.getActivity().getApplication().getPackageManager().getInstalledPackages(0);
                    final List<e> excludedList = new ArrayList();
                    final List<e> unExcludeList = new ArrayList();
                    int length = allPackages.size();
                    for (int i = 0; i < length; i++) {
                        PackageInfo info = (PackageInfo) allPackages.get(i);
                        if ((info.applicationInfo.flags & 1) == 0) {
                            e packageInfoEx = new e(info);
                            packageInfoEx.b = com.meizu.cloud.app.core.i.b(this.a.getActivity().getApplicationContext(), info.packageName);
                            if (this.a.a(info.packageName)) {
                                packageInfoEx.c = true;
                                excludedList.add(packageInfoEx);
                            } else if (!this.a.getActivity().getApplication().getPackageName().equals(info.packageName)) {
                                packageInfoEx.c = false;
                                unExcludeList.add(packageInfoEx);
                            }
                        }
                    }
                    if (!this.a.mRunning || Thread.currentThread().isInterrupted()) {
                        excludedList.clear();
                        unExcludeList.clear();
                        allPackages.clear();
                        return;
                    }
                    this.a.runOnUi(new Runnable(this) {
                        final /* synthetic */ AnonymousClass1 c;

                        public void run() {
                            if (this.c.a.mRunning) {
                                this.c.a.f.addAll(excludedList);
                                this.c.a.g.addAll(unExcludeList);
                                this.c.a.i = true;
                                this.c.a.f();
                                return;
                            }
                            excludedList.clear();
                            unExcludeList.clear();
                        }
                    });
                }
            });
        }
    }

    private boolean a(String packageName) {
        for (int i = 0; i < this.h.size(); i++) {
            if (((String) this.h.get(i)).equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public void onDestroy() {
        if (!(this.e == null || this.e.a())) {
            this.e.b();
        }
        super.onDestroy();
    }

    protected f a() {
        return new a(this, getActivity(), new List[]{this.f, this.g});
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        b().setHeaderDividersEnabled(false);
        b().setFooterDividersEnabled(false);
        f();
    }

    private void f() {
        if (!this.i) {
            showProgress();
        } else if (this.f.size() + this.g.size() == 0) {
            d();
        } else {
            c();
            this.c.a(0, this.f);
            this.c.a(1, this.g);
        }
    }

    protected String getEmptyTextString() {
        return getString(i.installed_no_data_remind_text);
    }
}
