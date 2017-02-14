package com.meizu.cloud.app.fragment;

import a.a.a.c;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.h;
import android.view.View;
import android.widget.CheckBox;
import com.meizu.cloud.app.a.l;
import com.meizu.cloud.app.a.m;
import com.meizu.cloud.app.block.Blockable;
import com.meizu.cloud.app.c.d;
import com.meizu.cloud.app.core.r;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo.UpdateFinishRecord;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.a.e.b;
import com.meizu.cloud.base.b.k;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class t extends k<List<UpdateFinishRecord>> implements b<UpdateFinishRecord> {
    public static final Comparator<UpdateFinishRecord> b = new Comparator<UpdateFinishRecord>() {
        public /* synthetic */ int compare(Object obj, Object obj2) {
            return a((UpdateFinishRecord) obj, (UpdateFinishRecord) obj2);
        }

        public int a(UpdateFinishRecord lhs, UpdateFinishRecord rhs) {
            if (lhs.update_finish_time == rhs.update_finish_time) {
                return 0;
            }
            return lhs.update_finish_time - rhs.update_finish_time < 0 ? 1 : -1;
        }
    };
    protected r a;

    public static class a extends android.support.v4.content.a<List<UpdateFinishRecord>> {
        private List<UpdateFinishRecord> o;
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

        public List<UpdateFinishRecord> z() {
            if (this.o == null) {
                this.o = new ArrayList();
            } else {
                this.o.clear();
            }
            List<UpdateFinishRecord> updateAppInfos = this.p.d(h());
            Collections.sort(updateAppInfos, t.b);
            this.o.addAll(updateAppInfos);
            return this.o;
        }

        public void a(List<UpdateFinishRecord> data) {
            if (k() && data != null) {
                c(data);
            }
            List<UpdateFinishRecord> oldApps = this.o;
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

        public void b(List<UpdateFinishRecord> data) {
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

        protected void c(List<UpdateFinishRecord> list) {
        }
    }

    protected void setupActionBar() {
        super.setupActionBar();
        getActionBar().a(getString(i.update_record));
    }

    public void onCreate(Bundle savedInstanceState) {
        this.a = r.a(getActivity());
        super.onCreate(savedInstanceState);
        c.a().a((Object) this);
        this.mPageName = "update_record";
    }

    public void onDestroy() {
        super.onDestroy();
        c.a().c(this);
    }

    protected void initView(View rootView) {
        super.initView(rootView);
    }

    public h<List<UpdateFinishRecord>> a(int i, Bundle bundle) {
        return new a(getActivity(), this.a);
    }

    public void a(h<List<UpdateFinishRecord>> loader, List<UpdateFinishRecord> updateAppInfos) {
        super.a(loader, updateAppInfos);
        if (updateAppInfos.size() > 0) {
            a((List) updateAppInfos);
            hideEmptyView();
            return;
        }
        showEmptyView(getString(i.no_update_record), getResources().getDrawable(e.ic_error_page, null), null);
    }

    public void a(View itemView, UpdateFinishRecord updateAppInfo) {
        View rootview = d().findViewWithTag(updateAppInfo.package_name);
        if (rootview != null) {
            CheckBox checkBox = (CheckBox) rootview.findViewById(f.publish_time);
            checkBox.setChecked(!checkBox.isChecked());
        }
    }

    public void a(h<List<UpdateFinishRecord>> hVar) {
        a(null);
    }

    protected void onRealPageStart() {
        super.onRealPageStart();
        com.meizu.cloud.statistics.b.a().a(this.mPageName);
    }

    protected void onRealPageStop() {
        super.onRealPageStop();
        com.meizu.cloud.statistics.b.a().a(this.mPageName, null);
    }

    protected String b() {
        return getString(i.no_update_record);
    }

    public void onEventMainThread(d recordDBChangeEvent) {
        m adapter;
        if (recordDBChangeEvent.a == 1) {
            UpdateFinishRecord updateAppInfo = this.a.b(recordDBChangeEvent.b);
            if (updateAppInfo != null) {
                adapter = (m) e();
                if (adapter != null) {
                    adapter.c().add(0, updateAppInfo);
                    adapter.notifyItemInserted(0);
                    return;
                }
                getLoaderManager().b(0, null, this);
            }
        } else if (recordDBChangeEvent.a == -1) {
            adapter = (m) e();
            if (adapter != null) {
                for (Blockable blockable : adapter.c()) {
                    if (blockable instanceof UpdateFinishRecord) {
                        finishRecord = (UpdateFinishRecord) blockable;
                        if (((long) finishRecord.id) == recordDBChangeEvent.b) {
                            int removeIndex = adapter.c().indexOf(finishRecord);
                            adapter.c().remove(removeIndex);
                            adapter.notifyItemRemoved(removeIndex);
                            return;
                        }
                    }
                }
            }
        } else if (recordDBChangeEvent.a == 0) {
            l adapter2 = (l) e();
            if (adapter2 != null) {
                for (Blockable blockable2 : adapter2.c()) {
                    if (blockable2 instanceof UpdateFinishRecord) {
                        finishRecord = (UpdateFinishRecord) blockable2;
                        if (((long) finishRecord.id) == recordDBChangeEvent.b) {
                            adapter2.notifyItemChanged(adapter2.c().indexOf(finishRecord));
                            return;
                        }
                    }
                }
            }
        } else {
            getLoaderManager().b(0, null, this);
        }
    }
}
