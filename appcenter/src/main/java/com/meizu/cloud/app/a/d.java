package com.meizu.cloud.app.a;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.meizu.cloud.app.core.k;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.request.model.AppStructDetailsItem;
import com.meizu.cloud.app.request.structitem.HistoryVersions.VersionItem;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.app.widget.RankAppItemView;
import com.meizu.cloud.app.widget.VersionItemView;
import com.meizu.cloud.b.a.b;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.common.widget.FoldableTextView;

public class d extends com.meizu.cloud.base.a.d<VersionItem> {
    private Context a;
    private AppStructDetailsItem b;
    private t c;
    private int[] d;

    private class a extends com.meizu.cloud.base.a.d.a {
        public RankAppItemView a;
        public TextView b;
        final /* synthetic */ d c;

        public a(d dVar, View itemView) {
            this.c = dVar;
            super(dVar, itemView, false);
        }
    }

    public d(FragmentActivity activity, AppStructDetailsItem appStructDetailsItem, t viewController) {
        this.a = activity;
        this.b = appStructDetailsItem;
        this.c = viewController;
        this.h = true;
        this.d = viewController.a();
    }

    public com.meizu.cloud.base.a.d.a a(ViewGroup parent) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(g.latest_version_item, parent, false);
        a headerHolder = new a(this, rootView);
        headerHolder.a = (RankAppItemView) rootView.findViewById(f.latestVersionView);
        headerHolder.b = (TextView) rootView.findViewById(f.txtCount);
        headerHolder.a.getDefaultDivider().setVisibility(8);
        return headerHolder;
    }

    public void a(com.meizu.cloud.base.a.d.a holder) {
        a headerHolder = (a) holder;
        RankAppItemView headView = headerHolder.a;
        headView.setEnabled(false);
        headView.setFocusable(false);
        headView.setIconUrl(this.b.icon);
        headView.f.setText(this.b.name);
        headView.j.setText(this.a.getString(i.history_version_latest) + this.b.version_name);
        headView.j.setVisibility(0);
        headView.k.setText(this.a.getString(i.size) + com.meizu.cloud.app.utils.g.a((double) this.b.size, this.a.getResources().getStringArray(b.sizeUnit)));
        headView.h.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                this.a.b.install_page = this.a.c.b();
                this.a.b.click_pos = 1;
                this.a.b.page_info = this.a.d;
                this.a.c.a(new k(this.a.b));
            }
        });
        headView.h.setTag(Integer.valueOf(this.b.version_code));
        this.c.a(this.b, null, true, headView.h);
        headerHolder.b.setText(String.format("%s %d", new Object[]{this.a.getString(i.history_version), Integer.valueOf(d())}));
    }

    public com.meizu.cloud.base.a.d.a a(ViewGroup parent, int viewType) {
        return new com.meizu.cloud.base.a.d.a(this, new VersionItemView(parent.getContext()));
    }

    public void a(com.meizu.cloud.base.a.d.a holder, final int position) {
        final VersionItem versionItem = (VersionItem) c(position);
        VersionItemView versionItemView = holder.itemView;
        versionItemView.setVersionName(versionItem.version_name);
        versionItemView.setTags(versionItem.smartbar ? this.a.getString(i.history_version_mz) : null);
        versionItemView.setSizeText(com.meizu.cloud.app.utils.g.a((double) versionItem.size, this.a.getResources().getStringArray(b.sizeUnit)));
        versionItemView.setReleaseText(com.meizu.common.util.a.a(this.a, versionItem.sale_time, 7));
        versionItemView.setVersionDesc(versionItem.isFold, 2, versionItem.description, new com.meizu.common.widget.FoldableTextView.a(this) {
            final /* synthetic */ d b;

            public boolean a(FoldableTextView ftv, boolean folding) {
                versionItem.isFold = folding;
                return true;
            }
        });
        CirProButton btn = versionItemView.getInstallBtn();
        btn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ d c;

            public void onClick(View v) {
                this.c.b.install_page = this.c.c.b();
                this.c.b.click_pos = position + 1;
                this.c.b.page_info = this.c.d;
                this.c.c.a(new k(this.c.b, versionItem));
            }
        });
        btn.setTag(Integer.valueOf(versionItem.version_code));
        this.c.a(this.b, versionItem, true, btn);
        if (position == getItemCount() - 1) {
            versionItemView.setDividerVisible(false);
        } else {
            versionItemView.setDividerVisible(true);
        }
    }
}
