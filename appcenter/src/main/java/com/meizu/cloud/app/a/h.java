package com.meizu.cloud.app.a;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.meizu.cloud.app.block.structbuilder.BlockItemBuilder;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structlayout.AbsBlockLayout;
import com.meizu.cloud.app.block.structlayout.AbsBlockLayout.OnChildClickListener;
import com.meizu.cloud.app.core.k;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.app.widget.CommonListItemView;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import java.util.ArrayList;
import java.util.List;

public abstract class h extends a<AppUpdateStructItem> implements OnChildClickListener, com.meizu.cloud.app.widget.CommonListItemView.a {
    protected AbsBlockItem e;
    private boolean j;
    private List<AppStructItem> k;
    private d l;

    protected class a extends com.meizu.cloud.base.a.d.a {
        public AbsBlockLayout<AbsBlockItem> a;
        final /* synthetic */ h b;

        public a(h hVar, View itemView) {
            this.b = hVar;
            super(hVar, itemView);
        }
    }

    public class b extends com.meizu.cloud.base.a.d.a {
        public CommonListItemView a;
        final /* synthetic */ h b;

        public b(h hVar, View itemView) {
            this.b = hVar;
            super(hVar, itemView);
        }
    }

    public class c extends com.meizu.cloud.base.a.d.a {
        public LinearLayout a;
        public ImageView b;
        final /* synthetic */ h c;

        public c(h hVar, View itemView) {
            this.c = hVar;
            super(hVar, itemView);
        }
    }

    public interface d {
        void a(AppStructItem appStructItem);
    }

    public abstract CommonListItemView a(int i);

    public abstract void a(AppStructItem appStructItem);

    public abstract void b(AbsBlockItem absBlockItem);

    public h(FragmentActivity activity) {
        super(activity);
        this.j = false;
    }

    public h(FragmentActivity activity, t viewController) {
        this(activity);
        this.b = viewController;
        this.c = viewController.a();
    }

    public void a(String banner) {
        if (this.b != null && this.b.c() != null) {
            this.b.c().a = banner;
            if (!TextUtils.isEmpty(banner)) {
                this.h = true;
            }
        }
    }

    public void a(AbsBlockItem headerBlockItem) {
        this.e = headerBlockItem;
        if (this.e != null) {
            this.h = true;
        }
    }

    public com.meizu.cloud.base.a.d.a a(ViewGroup parent) {
        if (this.e != null) {
            View view;
            AbsBlockLayout absBlockLayout = BlockItemBuilder.build(this.e.style, parent);
            if (absBlockLayout != null) {
                view = absBlockLayout.createView(this.d, null);
                absBlockLayout.setOnChildClickListener(this);
            } else {
                view = new View(this.d);
            }
            a holder = new a(this, view);
            holder.a = absBlockLayout;
            return holder;
        }
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(this.d).inflate(g.rank_header_view, parent, false);
        com.meizu.cloud.base.a.d.a headerHoder = new c(this, rootView);
        headerHoder.a = rootView;
        headerHoder.b = (ImageView) rootView.findViewById(f.image);
        return headerHoder;
    }

    public void a(com.meizu.cloud.base.a.d.a holder) {
        if (this.e != null) {
            a absBlockHolder = (a) holder;
            if (absBlockHolder.a != null) {
                absBlockHolder.a.updateView(this.d, this.e, this.b, 0);
                absBlockHolder.a.setOnChildClickListener(this);
            }
            if (this.j && !this.e.isExposured) {
                this.e.isExposured = true;
                b(this.e);
            }
        } else if (this.b != null && this.b.c() != null && this.b.c().a != null) {
            com.meizu.cloud.app.utils.h.c(this.d, this.b.c().a, ((c) holder).b);
        }
    }

    public com.meizu.cloud.base.a.d.a a(ViewGroup parent, int viewType) {
        CommonListItemView appItemView = a(viewType);
        b holder = new b(this, appItemView);
        holder.a = appItemView;
        return holder;
    }

    public void a(com.meizu.cloud.base.a.d.a holder, int position) {
        b appHolder = (b) holder;
        AppStructItem appStructItem = (AppUpdateStructItem) c(position);
        if (appStructItem != null) {
            appStructItem.click_pos = position + 1;
            appStructItem.pos_ver = position + 1;
            appHolder.a.setOnInstallBtnClickListener(this);
            appHolder.a.a(appStructItem, position);
            b(appStructItem);
        }
    }

    private void b(AppStructItem appStructItem) {
        if (!appStructItem.is_uxip_exposured) {
            appStructItem.is_uxip_exposured = true;
            if (this.j) {
                a(appStructItem);
                return;
            }
            if (this.k == null) {
                this.k = new ArrayList();
            }
            this.k.add(appStructItem);
        }
    }

    public void c() {
        if (!this.j) {
            this.j = true;
            if (this.k != null && this.k.size() > 0) {
                for (AppStructItem structItem : this.k) {
                    a(structItem);
                }
                this.k = null;
            }
            if (this.e != null && !this.e.isExposured) {
                this.e.isExposured = true;
                b(this.e);
            }
        }
    }

    public void a(boolean isShowed) {
        this.j = isShowed;
    }

    public void a(AppStructItem appStructItem, View view) {
        appStructItem.page_info = this.c;
        if (this.l != null) {
            this.l.a(appStructItem);
        }
        this.b.a(new k(appStructItem));
    }

    public void a(d onRankInstallBtnClickListener) {
        this.l = onRankInstallBtnClickListener;
    }
}
