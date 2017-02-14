package com.meizu.flyme.appcenter.desktopplugin.a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.meizu.cloud.app.request.model.PluginItem;
import com.meizu.cloud.statistics.c;
import com.meizu.flyme.appcenter.desktopplugin.b.b;
import com.meizu.flyme.appcenter.desktopplugin.view.PluginActivity;
import com.meizu.flyme.appcenter.desktopplugin.view.customview.IconBadgeView;
import com.meizu.flyme.appcenter.desktopplugin.view.customview.MaskIamgeview;
import com.meizu.mstore.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class a extends BaseAdapter {
    public List<View> a;
    private List<PluginItem> b = Collections.emptyList();
    private Context c;
    private int d;

    public class a {
        public View a;
        public ProgressBar b;
        public MaskIamgeview c = ((MaskIamgeview) this.a.findViewById(R.id.plugin_item_icon));
        public IconBadgeView d;
        public TextView e;
        public PluginItem f;
        final /* synthetic */ a g;

        public a(a aVar, int pos) {
            this.g = aVar;
            this.a = LayoutInflater.from(aVar.c.getApplicationContext()).inflate(R.layout.plugin_item_layout, null);
            LayoutParams params;
            if (PluginActivity.k) {
                params = this.c.getLayoutParams();
                params.height = (int) aVar.c.getResources().getDimension(R.dimen.plugin_item_maskimageview_height);
                params.width = (int) aVar.c.getResources().getDimension(R.dimen.plugin_item_maskimageview_width);
                this.c.setLayoutParams(params);
            } else {
                params = this.c.getLayoutParams();
                params.height = (int) aVar.c.getResources().getDimension(R.dimen.plugin_item_maskimageview_height_noflyme);
                params.width = (int) aVar.c.getResources().getDimension(R.dimen.plugin_item_maskimageview_width_noflyme);
                this.c.setLayoutParams(params);
            }
            this.c.setTag("IMAGEVIEW");
            this.d = (IconBadgeView) this.a.findViewById(R.id.plugin_item_badgeview);
            aVar.a.add(this.d);
            this.e = (TextView) this.a.findViewById(R.id.plugin_item_title);
            this.e.setTextColor(aVar.d);
            this.b = (ProgressBar) this.a.findViewById(R.id.plugin_progress);
        }
    }

    public a(Context context, int pattleColor) {
        this.c = context;
        this.d = pattleColor;
        this.a = new ArrayList();
    }

    public void a(List<PluginItem> pluginItemList) {
        this.b = pluginItemList;
    }

    public int getCount() {
        return this.b.size();
    }

    public Object getItem(int position) {
        return this.b.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            a viewHolder = new a(this, position);
            convertView = viewHolder.a;
            convertView.setTag(R.id.tag_first, viewHolder);
            a(viewHolder, position);
            return convertView;
        }
        a((a) convertView.getTag(R.id.tag_first), position);
        return convertView;
    }

    public void a(a viewHolder, int position) {
        PluginItem pluginItem = (PluginItem) this.b.get(position);
        if (position != 0 || viewHolder.f != pluginItem) {
            viewHolder.a.setTag(pluginItem.getPackage_name());
            viewHolder.f = pluginItem;
            if (pluginItem.getDrawabeId() != R.drawable.plugin_appdefault_ic) {
                Picasso.with(this.c.getApplicationContext()).load(pluginItem.getDrawabeId()).placeholder((int) R.drawable.plugin_appdefault_ic_flyme).error((int) R.drawable.plugin_appdefault_ic_flyme).into(viewHolder.c);
            } else if (PluginActivity.k) {
                Picasso.with(this.c.getApplicationContext()).load(viewHolder.f.getIcon()).placeholder((int) R.drawable.plugin_appdefault_ic_flyme).error((int) R.drawable.plugin_appdefault_ic_flyme).into(viewHolder.c);
            } else {
                Picasso.with(this.c.getApplicationContext()).load(viewHolder.f.getIcon()).placeholder((int) R.drawable.plugin_appdefault_ic_noflyme).error((int) R.drawable.plugin_appdefault_ic_noflyme).into(viewHolder.c);
            }
            viewHolder.c.setmProgress(pluginItem.getProgerss());
            viewHolder.e.setText(pluginItem.getName());
            viewHolder.c.setState(pluginItem.getState());
            viewHolder.d.setState(pluginItem.getState());
            if (pluginItem.getState() == 3) {
                viewHolder.b.setVisibility(0);
            } else {
                viewHolder.b.setVisibility(4);
            }
            if (!pluginItem.isExpolsure() && !pluginItem.isFromCache.booleanValue()) {
                if (pluginItem.type == PluginItem.CPD_DATA) {
                    b.b(this.c.getApplicationContext()).a(pluginItem);
                }
                com.meizu.cloud.statistics.b.a().a("jxcj_baoguangyy", "plugin", c.b(pluginItem.getPackage_name()));
                pluginItem.setExpolsure(true);
            }
        }
    }
}
