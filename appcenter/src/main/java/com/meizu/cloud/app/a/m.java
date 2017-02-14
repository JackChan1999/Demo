package com.meizu.cloud.app.a;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
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
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo.UpdateFinishRecord;
import com.meizu.cloud.app.utils.b;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.a.e;

public abstract class m extends e<UpdateFinishRecord> {
    protected FragmentActivity a;
    protected LayoutInflater b = LayoutInflater.from(this.a);
    protected String c = "update_record";

    class a extends com.meizu.cloud.base.a.e.a {
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
        final /* synthetic */ m k;

        public a(m mVar, View itemView) {
            this.k = mVar;
            super(mVar, itemView, true);
        }
    }

    public abstract void a(View view, UpdateFinishRecord updateFinishRecord, int i);

    public m(FragmentActivity activity) {
        this.a = activity;
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
        btnInstall.a(true, false);
        b.a(btnInstall.getTextView(), c.btn_default, false);
        ImageView iconImageView = (ImageView) rootView.findViewById(f.icon);
        a itemViewHolder = new a(this, rootView);
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
        if (holder instanceof a) {
            final UpdateFinishRecord finishRecord = (UpdateFinishRecord) b(position);
            if (finishRecord != null) {
                final a itemViewHolder = (a) holder;
                h.a(this.a, finishRecord.icon, itemViewHolder.a);
                itemViewHolder.d.setText(finishRecord.name);
                itemViewHolder.e.setText(com.meizu.cloud.app.utils.g.a((double) finishRecord.size, this.a.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit)));
                itemViewHolder.f.setText(this.a.getString(i.version_format2, new Object[]{finishRecord.version_name}));
                itemViewHolder.f.setOnCheckedChangeListener(null);
                itemViewHolder.f.setChecked(finishRecord.isChecked);
                if (finishRecord.isChecked) {
                    itemViewHolder.c.setVisibility(0);
                    itemViewHolder.g.setVisibility(8);
                } else {
                    itemViewHolder.c.setVisibility(8);
                    itemViewHolder.g.setVisibility(0);
                }
                itemViewHolder.f.setOnCheckedChangeListener(new OnCheckedChangeListener(this) {
                    final /* synthetic */ m c;

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        finishRecord.isChecked = isChecked;
                        if (isChecked) {
                            itemViewHolder.c.setVisibility(0);
                            itemViewHolder.g.setVisibility(8);
                            return;
                        }
                        itemViewHolder.c.setVisibility(8);
                        itemViewHolder.g.setVisibility(0);
                    }
                });
                itemViewHolder.h.setText(TextUtils.isEmpty(finishRecord.update_description) ? this.a.getString(i.no_update_description) : finishRecord.update_description);
                itemViewHolder.i.setText(i.clear_update_record);
                itemViewHolder.i.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ m c;

                    public void onClick(View v) {
                        this.c.a(v, finishRecord, position);
                    }
                });
                itemViewHolder.j.setText(this.a.getResources().getText(i.details_title));
                itemViewHolder.j.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ m c;

                    public void onClick(View v) {
                        this.c.a(v, finishRecord, position);
                    }
                });
                itemViewHolder.b.getTextView().setText(this.a.getString(i.open));
                itemViewHolder.b.getTextView().setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ m c;

                    public void onClick(View v) {
                        finishRecord.getAppStructItem().click_pos = position + 1;
                        com.meizu.cloud.statistics.b.a().a("open", this.c.c, com.meizu.cloud.statistics.c.b(finishRecord.getAppStructItem()));
                        t.a(finishRecord.getAppStructItem().package_name, this.c.a);
                    }
                });
                itemViewHolder.itemView.setTag(finishRecord.package_name);
            }
        }
    }
}
