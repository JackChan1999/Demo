package com.meizu.cloud.base.b;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import com.meizu.cloud.app.a.a;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.base.a.d;
import com.meizu.cloud.base.a.d.b;
import flyme.support.v7.widget.LinearLayoutManager;

public abstract class c<T extends AppStructItem> extends a<T> {
    private a a;

    public abstract a f();

    public d createRecyclerAdapter() {
        this.a = f();
        this.a.a(new b(this) {
            final /* synthetic */ c a;

            {
                this.a = r1;
            }

            public void onItemClick(View itemView, int position) {
                AppStructItem appStructItem = (AppStructItem) this.a.a.c(position);
                if (appStructItem != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", appStructItem.url);
                    bundle.putString("title_name", appStructItem.name);
                    bundle.putString("source_page", this.a.mPageName);
                    if (com.meizu.cloud.statistics.a.c(appStructItem)) {
                        bundle.putInt("positionId", appStructItem.position_id);
                        bundle.putInt("unitId", appStructItem.unit_id);
                        bundle.putString("requestId", appStructItem.request_id);
                        bundle.putString("version", appStructItem.version);
                    }
                    if (this.a.mPageInfo[0] == 2) {
                        bundle.putInt("source_page_id", this.a.mPageInfo[0]);
                    } else if (this.a.mPageInfo[1] != 0) {
                        bundle.putInt("source_page_id", this.a.mPageInfo[1]);
                    }
                    Fragment detailFragment = this.a.b();
                    detailFragment.setArguments(bundle);
                    d.startFragment(this.a.getActivity(), detailFragment);
                    com.meizu.cloud.statistics.a.a(this.a.getActivity()).b(appStructItem);
                    appStructItem.click_pos = position + 1;
                    com.meizu.cloud.statistics.b.a().a("item", this.a.mPageName, com.meizu.cloud.statistics.c.a(appStructItem));
                }
            }
        });
        return this.a;
    }

    protected void a(final e wrapper, boolean isProgress) {
        getActivity().runOnUiThread(new Runnable(this) {
            final /* synthetic */ c b;

            public void run() {
                this.b.a(wrapper, wrapper.g());
            }
        });
    }

    protected void c(final String pkgName) {
        getActivity().runOnUiThread(new Runnable(this) {
            final /* synthetic */ c b;

            public void run() {
                this.b.a(null, pkgName);
            }
        });
    }

    private void a(e wrapper, String pkgName) {
        if (getActivity() != null && getRecyclerView() != null && getRecyclerView().getLayoutManager() != null && this.a != null) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) getRecyclerView().getLayoutManager();
            int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
            int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
            int i = firstVisiblePosition;
            while (i <= lastVisiblePosition) {
                t.a item = (AppStructItem) this.a.c(i);
                if (item == null || TextUtils.isEmpty(item.package_name) || !item.package_name.equals(pkgName)) {
                    i++;
                } else {
                    CirProButton btn = (CirProButton) getRecyclerView().findViewWithTag(pkgName);
                    if (btn == null) {
                        return;
                    }
                    if (wrapper != null) {
                        this.a.a().a(wrapper, btn);
                        return;
                    } else if (!TextUtils.isEmpty(pkgName)) {
                        this.a.a().a(item, null, false, btn);
                        return;
                    } else {
                        return;
                    }
                }
            }
        }
    }
}
