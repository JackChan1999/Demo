package com.meizu.flyme.appcenter.a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.meizu.cloud.app.utils.g;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.base.a.e;
import com.meizu.common.widget.AnimCheckBox;
import com.meizu.mstore.R;
import java.io.File;

public class b extends e<com.meizu.cloud.app.core.a> implements com.meizu.flyme.appcenter.b.a {
    protected Context a;
    protected LayoutInflater b;
    private boolean c;
    private b d;

    class a extends com.meizu.cloud.base.a.e.a {
        ImageView a;
        TextView b;
        AnimCheckBox c;
        TextView d;
        TextView e;
        TextView f;
        final /* synthetic */ b g;

        public a(b bVar, View itemView) {
            this.g = bVar;
            super(bVar, itemView, false);
        }
    }

    public interface b {
        void a(View view, com.meizu.cloud.app.core.a aVar);
    }

    public void a(b uninstallClickListener) {
        this.d = uninstallClickListener;
    }

    public b(Context context) {
        this.a = context;
        this.b = LayoutInflater.from(context);
    }

    public e.a a(ViewGroup parent, int viewType) {
        View rootView = this.b.inflate(R.layout.mulit_chioce_item_view, parent, false);
        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView size = (TextView) rootView.findViewById(R.id.size);
        TextView installTime = (TextView) rootView.findViewById(R.id.install_time);
        TextView btnDelete = (TextView) rootView.findViewById(R.id.btnInstall);
        AnimCheckBox checkBox = (AnimCheckBox) rootView.findViewById(16908289);
        ImageView iconImageView = (ImageView) rootView.findViewById(R.id.icon);
        a itemViewHolder = new a(this, rootView);
        itemViewHolder.d = title;
        itemViewHolder.e = size;
        itemViewHolder.f = installTime;
        itemViewHolder.b = btnDelete;
        itemViewHolder.c = checkBox;
        itemViewHolder.a = iconImageView;
        return itemViewHolder;
    }

    public void a(e.a holder, int position) {
        if (holder instanceof a) {
            final com.meizu.cloud.app.core.a appEntry = (com.meizu.cloud.app.core.a) b(position);
            if (appEntry != null) {
                a itemViewHolder = (a) holder;
                if (this.c) {
                    itemViewHolder.b.setVisibility(4);
                } else {
                    itemViewHolder.b.setVisibility(0);
                }
                h.a(this.a, new File(this.a.getCacheDir(), "app_icons" + File.separator + appEntry.a().packageName), itemViewHolder.a);
                itemViewHolder.d.setText(appEntry.b());
                itemViewHolder.e.setText(g.a((double) appEntry.d(), this.a.getResources().getStringArray(R.array.sizeUnit)));
                itemViewHolder.f.setText(com.meizu.common.util.a.a(this.a, appEntry.e(), 6));
                com.meizu.cloud.app.utils.b.a(itemViewHolder.b, (int) R.color.btn_default, true);
                itemViewHolder.b.setText(this.a.getString(R.string.uninstall));
                itemViewHolder.b.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ b b;

                    public void onClick(View v) {
                        if (this.b.d != null) {
                            this.b.d.a(v, appEntry);
                        }
                    }
                });
            }
        }
    }

    public void d_() {
        this.c = true;
        notifyDataSetChanged();
    }

    public void h() {
        this.c = false;
        notifyDataSetChanged();
    }
}
