package com.meizu.cloud.app.a;

import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.meizu.cloud.app.block.Blockable;
import com.meizu.cloud.app.core.k;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.u;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.a.e;

public abstract class l extends e<Blockable> {
    protected FragmentActivity a;
    protected LayoutInflater b;
    protected t c;
    protected a d;
    protected String e;
    protected int[] f = new int[3];

    public interface a {
        void a(View view, ServerUpdateAppInfo serverUpdateAppInfo, int i);
    }

    class b extends com.meizu.cloud.base.a.e.a {
        ImageView a;
        CirProButton b;
        FrameLayout c;
        TextView d;
        TextView e;
        CheckBox f;
        View g;
        TextView h;
        TextView i;
        TextView j;
        final /* synthetic */ l k;

        public b(l lVar, View itemView, boolean itemClickable) {
            this.k = lVar;
            super(lVar, itemView, itemClickable);
        }
    }

    public void a(a onBottomItemClick) {
        this.d = onBottomItemClick;
    }

    public l(FragmentActivity activity) {
        this.a = activity;
        this.b = LayoutInflater.from(this.a);
        this.c = new t(activity, new u());
        this.c.a(new int[]{0, 11, 0});
    }

    public t a() {
        return this.c;
    }

    public com.meizu.cloud.base.a.e.a a(ViewGroup parent, int viewType) {
        if (viewType != 0) {
            return null;
        }
        View rootView = this.b.inflate(g.common_expand_appitem_view, parent, false);
        FrameLayout bottomLayout = (FrameLayout) rootView.findViewById(f.bottom_layout);
        FrameLayout middleLayout = (FrameLayout) rootView.findViewById(f.middle_layout);
        FrameLayout btnLayout = (FrameLayout) rootView.findViewById(f.btn_layout);
        this.b.inflate(g.update_bottom_layout, bottomLayout, true);
        TextView content = (TextView) bottomLayout.findViewById(f.content);
        TextView leftView = (TextView) bottomLayout.findViewById(f.tv_left);
        TextView rightView = (TextView) bottomLayout.findViewById(f.tv_right);
        this.b.inflate(g.update_middle_layout, middleLayout, true);
        TextView title = (TextView) middleLayout.findViewById(f.title);
        TextView size = (TextView) middleLayout.findViewById(f.size);
        CheckBox publishTime = (CheckBox) middleLayout.findViewById(f.publish_time);
        this.b.inflate(g.install_btn_layout, btnLayout, true);
        CirProButton btnInstall = (CirProButton) rootView.findViewById(f.btnInstall);
        ImageView iconImageView = (ImageView) rootView.findViewById(f.icon);
        b itemViewHolder = new b(this, rootView, true);
        itemViewHolder.g = rootView.findViewById(f.divider);
        itemViewHolder.c = bottomLayout;
        itemViewHolder.h = content;
        itemViewHolder.i = leftView;
        itemViewHolder.j = rightView;
        itemViewHolder.d = title;
        itemViewHolder.e = size;
        itemViewHolder.f = publishTime;
        itemViewHolder.b = btnInstall;
        itemViewHolder.a = iconImageView;
        return itemViewHolder;
    }

    public void a(com.meizu.cloud.base.a.e.a holder, final int position) {
        if (holder instanceof b) {
            Blockable blockable = (Blockable) b(position);
            if (blockable != null && (blockable instanceof ServerUpdateAppInfo)) {
                final com.meizu.cloud.app.core.t.a updateAppInfo = (ServerUpdateAppInfo) blockable;
                final b itemViewHolder = (b) holder;
                h.a(this.a, updateAppInfo.icon, itemViewHolder.a);
                itemViewHolder.d.setText(updateAppInfo.name);
                if (com.meizu.cloud.app.downlad.a.b(updateAppInfo.package_name, updateAppInfo.version_code)) {
                    itemViewHolder.e.setText(a(com.meizu.cloud.app.utils.g.a((double) updateAppInfo.size, this.a.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit)), this.a.getText(i.update_downloaded)));
                } else if (updateAppInfo.existDeltaUpdate()) {
                    itemViewHolder.e.setText(a(com.meizu.cloud.app.utils.g.a((double) updateAppInfo.size, this.a.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit)), com.meizu.cloud.app.utils.g.a((double) updateAppInfo.version_patch_size, this.a.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit))));
                } else {
                    itemViewHolder.e.setText(com.meizu.cloud.app.utils.g.a((double) updateAppInfo.size, this.a.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit)));
                }
                String publish = com.meizu.common.util.a.a(this.a, updateAppInfo.version_create_time, 0);
                itemViewHolder.f.setText(this.a.getString(i.publish_date_version_format, new Object[]{publish, updateAppInfo.version_name}));
                itemViewHolder.f.setOnCheckedChangeListener(null);
                itemViewHolder.f.setChecked(updateAppInfo.isChecked);
                if (updateAppInfo.isChecked) {
                    itemViewHolder.c.setVisibility(0);
                    itemViewHolder.g.setVisibility(8);
                } else {
                    itemViewHolder.c.setVisibility(8);
                    itemViewHolder.g.setVisibility(0);
                }
                itemViewHolder.f.setOnCheckedChangeListener(new OnCheckedChangeListener(this) {
                    final /* synthetic */ l c;

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        updateAppInfo.isChecked = isChecked;
                        if (updateAppInfo.isChecked) {
                            itemViewHolder.c.setVisibility(0);
                            itemViewHolder.g.setVisibility(8);
                            return;
                        }
                        itemViewHolder.c.setVisibility(8);
                        itemViewHolder.g.setVisibility(0);
                    }
                });
                itemViewHolder.h.setText(TextUtils.isEmpty(updateAppInfo.update_description) ? this.a.getString(i.no_update_description) : updateAppInfo.update_description);
                itemViewHolder.i.setText(this.a.getResources().getText(i.ignore_the_update));
                itemViewHolder.i.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ l c;

                    public void onClick(View v) {
                        if (this.c.d != null) {
                            for (int i = 0; i < this.c.b(); i++) {
                                Blockable item = (Blockable) this.c.c().get(i);
                                if (item instanceof ServerUpdateAppInfo) {
                                    ServerUpdateAppInfo appInfo = (ServerUpdateAppInfo) item;
                                    if (updateAppInfo.package_name.equals(appInfo.package_name)) {
                                        this.c.d.a(v, appInfo, position);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                });
                itemViewHolder.j.setText(this.a.getResources().getText(i.details_title));
                itemViewHolder.j.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ l c;

                    public void onClick(View v) {
                        if (this.c.d != null) {
                            for (int i = 0; i < this.c.b(); i++) {
                                Blockable item = (Blockable) this.c.c().get(i);
                                if (item instanceof ServerUpdateAppInfo) {
                                    ServerUpdateAppInfo appInfo = (ServerUpdateAppInfo) item;
                                    if (updateAppInfo.package_name.equals(appInfo.package_name)) {
                                        this.c.d.a(v, appInfo, position);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                });
                itemViewHolder.itemView.setTag(updateAppInfo.package_name);
                itemViewHolder.b.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ l c;

                    public void onClick(View v) {
                        if (updateAppInfo.getAppStructItem() != null) {
                            updateAppInfo.getAppStructItem().page_info = this.c.f;
                            updateAppInfo.getAppStructItem().install_page = this.c.e;
                            updateAppInfo.getAppStructItem().click_pos = position + 1;
                        }
                        this.c.c.a(new k(updateAppInfo));
                    }
                });
                this.c.a(updateAppInfo, null, true, itemViewHolder.b);
            }
        }
    }

    private SpannableString a(CharSequence victimText, CharSequence survivingText) {
        SpannableString spanString = new SpannableString(String.format("%s %s", new Object[]{victimText, survivingText}));
        if (!TextUtils.isEmpty(victimText)) {
            spanString.setSpan(new StrikethroughSpan(), 0, victimText.length(), 33);
        }
        return spanString;
    }
}
