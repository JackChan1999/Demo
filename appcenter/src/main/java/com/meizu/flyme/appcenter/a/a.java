package com.meizu.flyme.appcenter.a;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.meizu.cloud.app.a.f;
import com.meizu.cloud.app.core.k;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.utils.g;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.common.widget.AnimCheckBox;
import com.meizu.mstore.R;

public class a extends f<AppStructItem> implements com.meizu.flyme.appcenter.b.a {
    protected LayoutInflater a;
    private boolean b;
    private t c;

    class a extends com.meizu.cloud.base.a.d.a {
        ImageView a;
        CirProButton b;
        AnimCheckBox c;
        TextView d;
        TextView g;
        TextView h;
        final /* synthetic */ a i;

        public a(a aVar, View itemView, boolean itemClickable) {
            this.i = aVar;
            super(aVar, itemView, itemClickable);
        }
    }

    public a(FragmentActivity activity, t viewController) {
        super(activity);
        this.c = viewController;
        this.a = LayoutInflater.from(activity);
    }

    public t a() {
        return this.c;
    }

    public com.meizu.cloud.base.a.d.a a(ViewGroup parent, int viewType) {
        View rootView = this.a.inflate(R.layout.mulit_chioce_item_view2, parent, false);
        LayoutParams layoutParams = rootView.getLayoutParams();
        layoutParams.height = parent.getContext().getResources().getDimensionPixelSize(R.dimen.common_list_item_row2_height);
        rootView.setLayoutParams(layoutParams);
        TextView title = (TextView) rootView.findViewById(R.id.txt_title);
        TextView size = (TextView) rootView.findViewById(R.id.txt_desc1);
        TextView count = (TextView) rootView.findViewById(R.id.txt_desc2);
        CirProButton btnDelete = (CirProButton) rootView.findViewById(R.id.btnInstall);
        AnimCheckBox checkBox = (AnimCheckBox) rootView.findViewById(16908289);
        ImageView iconImageView = (ImageView) rootView.findViewById(R.id.icon);
        a itemViewHolder = new a(this, rootView, false);
        itemViewHolder.d = title;
        itemViewHolder.g = size;
        itemViewHolder.h = count;
        itemViewHolder.b = btnDelete;
        itemViewHolder.c = checkBox;
        itemViewHolder.a = iconImageView;
        return itemViewHolder;
    }

    public void a(com.meizu.cloud.base.a.d.a holder, int position) {
        if (holder instanceof a) {
            final t.a appStructItem = (AppStructItem) c(position);
            if (appStructItem != null) {
                a itemViewHolder = (a) holder;
                if (this.b) {
                    itemViewHolder.b.setVisibility(4);
                } else {
                    itemViewHolder.b.setVisibility(0);
                }
                h.a(this.d, appStructItem.icon, itemViewHolder.a);
                itemViewHolder.d.setText(appStructItem.name);
                itemViewHolder.g.setText(g.a((double) appStructItem.size, this.d.getResources().getStringArray(R.array.sizeUnit)));
                itemViewHolder.h.setText(this.d.getString(R.string.install_counts_only, new Object[]{g.a(this.d, appStructItem.download_count)}));
                itemViewHolder.b.setTag(appStructItem.package_name);
                itemViewHolder.b.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ a b;

                    public void onClick(View v) {
                        appStructItem.install_page = this.b.c.b();
                        appStructItem.page_info = this.b.c.a();
                        this.b.c.a(new k(appStructItem));
                    }
                });
                this.c.a(appStructItem, null, true, itemViewHolder.b);
            }
        }
    }

    public void d_() {
        this.b = true;
        notifyDataSetChanged();
    }

    public void h() {
        this.b = false;
        notifyDataSetChanged();
    }
}
