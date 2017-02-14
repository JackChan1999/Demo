package com.meizu.cloud.app.a;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.meizu.cloud.app.block.Blockable;
import com.meizu.cloud.app.block.customblock.PartitionItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.u;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.downlad.f.l;
import com.meizu.cloud.app.downlad.f.n;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;

public class c extends com.meizu.cloud.base.a.c<Blockable> {
    protected FragmentActivity a;
    protected LayoutInflater b;
    protected t c;
    protected b d;

    private class a extends com.meizu.cloud.base.a.e.a {
        int a;
        ImageView b;
        CirProButton c;
        TextView d;
        TextView e;
        TextView f;
        final /* synthetic */ c g;

        public a(c cVar, View itemView, int viewType) {
            this.g = cVar;
            super(cVar, itemView, true);
            this.a = viewType;
        }
    }

    public interface b {
        void a(View view, PartitionItem partitionItem);

        void a(View view, e eVar);
    }

    public void a(b itemChildClickListener) {
        this.d = itemChildClickListener;
    }

    public c(FragmentActivity activity) {
        this.a = activity;
        this.b = LayoutInflater.from(activity);
        u viewControllerPageInfo = new u();
        viewControllerPageInfo.b(true);
        this.c = new t(activity, viewControllerPageInfo);
    }

    public c(FragmentActivity activity, t viewController) {
        this(activity);
        this.c = viewController;
    }

    public com.meizu.cloud.base.a.e.a a(ViewGroup parent, int viewType) {
        Class<Blockable> clz = this.e.a(viewType);
        if (clz == null) {
            return null;
        }
        if (clz.equals(PartitionItem.class)) {
            View titleView = this.b.inflate(g.block_item_container_v2, parent, false);
            TextView groupTitle = (TextView) titleView.findViewById(16908308);
            TextView groupBtn = (TextView) titleView.findViewById(16908309);
            View paddingView = titleView.findViewById(f.title_title_layout);
            paddingView.setBackground(null);
            com.meizu.cloud.base.a.e.a aVar = new com.meizu.cloud.base.a.c.a(this, titleView, viewType);
            aVar.b = groupTitle;
            aVar.c = groupBtn;
            aVar.d = paddingView;
            return aVar;
        } else if (!clz.equals(e.class)) {
            return null;
        } else {
            View rootView = this.b.inflate(g.common_item_view, parent, false);
            FrameLayout middleLayout = (FrameLayout) rootView.findViewById(f.middle_layout);
            FrameLayout btnLayout = (FrameLayout) rootView.findViewById(f.btn_layout);
            this.b.inflate(g.download_middle_layout, middleLayout, true);
            TextView title = (TextView) middleLayout.findViewById(f.title);
            TextView desc = (TextView) middleLayout.findViewById(f.desc);
            TextView description = (TextView) middleLayout.findViewById(f.description);
            this.b.inflate(g.install_btn_layout, btnLayout, true);
            CirProButton btn = (CirProButton) rootView.findViewById(f.btnInstall);
            ImageView iconImageView = (ImageView) rootView.findViewById(f.icon);
            com.meizu.cloud.base.a.e.a groupItemViewHolder = new a(this, rootView, viewType);
            groupItemViewHolder.d = title;
            groupItemViewHolder.e = desc;
            groupItemViewHolder.f = description;
            groupItemViewHolder.c = btn;
            groupItemViewHolder.b = iconImageView;
            return groupItemViewHolder;
        }
    }

    public void a(com.meizu.cloud.base.a.e.a holder, int position) {
        Blockable blockable;
        if (holder instanceof com.meizu.cloud.base.a.c.a) {
            com.meizu.cloud.base.a.c.a groupHeaderViewHolder = (com.meizu.cloud.base.a.c.a) holder;
            blockable = (Blockable) b(position);
            if (blockable != null && (blockable instanceof PartitionItem)) {
                final PartitionItem partitionItem = (PartitionItem) blockable;
                if (position == 0) {
                    groupHeaderViewHolder.d.setVisibility(8);
                } else {
                    groupHeaderViewHolder.d.setVisibility(0);
                }
                groupHeaderViewHolder.b.setText(partitionItem.title);
                groupHeaderViewHolder.b.setVisibility(0);
                if (partitionItem.hasBtn) {
                    boolean isShowPause;
                    if (partitionItem.tag != null) {
                        isShowPause = ((Boolean) partitionItem.tag).booleanValue();
                    } else {
                        d taskFactory = d.a(this.a.getApplicationContext());
                        isShowPause = taskFactory.d() > 0 || taskFactory.h().size() <= 0;
                    }
                    groupHeaderViewHolder.c.setTag(partitionItem.id, Boolean.valueOf(isShowPause));
                    if (partitionItem.id != f.partition_downloading) {
                        groupHeaderViewHolder.c.setText(partitionItem.btnText);
                    } else if (isShowPause) {
                        groupHeaderViewHolder.c.setText(partitionItem.btnText);
                    } else {
                        groupHeaderViewHolder.c.setText(partitionItem.btnText2);
                    }
                    groupHeaderViewHolder.c.setVisibility(0);
                    groupHeaderViewHolder.c.setOnClickListener(new OnClickListener(this) {
                        final /* synthetic */ c b;

                        public void onClick(View v) {
                            if (this.b.d != null) {
                                this.b.d.a(v, partitionItem);
                            }
                        }
                    });
                }
            }
        } else if (holder instanceof a) {
            a groupItemViewHolder = (a) holder;
            blockable = (Blockable) b(position);
            if (blockable != null && (blockable instanceof e)) {
                final e itemData = (e) blockable;
                groupItemViewHolder.itemView.setTag(itemData.g());
                groupItemViewHolder.d.setText(itemData.k());
                h.a(this.a, itemData.w(), groupItemViewHolder.b);
                groupItemViewHolder.c.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ c b;

                    public void onClick(View v) {
                        if (this.b.d != null) {
                            this.b.d.a(v, itemData);
                        }
                    }
                });
                if (itemData.f() == com.meizu.cloud.app.downlad.f.f.INSTALL_SUCCESS) {
                    groupItemViewHolder.e.setVisibility(0);
                    TextView textView = groupItemViewHolder.e;
                    FragmentActivity fragmentActivity = this.a;
                    int i = i.version_format;
                    Object[] objArr = new Object[1];
                    objArr[0] = itemData.F() ? itemData.H() : itemData.l();
                    textView.setText(fragmentActivity.getString(i, objArr));
                    groupItemViewHolder.f.setText(a(itemData));
                } else {
                    groupItemViewHolder.e.setVisibility(8);
                    groupItemViewHolder.f.setText(a(itemData));
                }
                this.c.a(itemData.m(), null, true, groupItemViewHolder.c);
            }
        }
    }

    public String a(e downloadWrapper) {
        l anEnum = downloadWrapper.f();
        if (anEnum == com.meizu.cloud.app.downlad.f.f.INSTALL_SUCCESS) {
            return com.meizu.common.util.a.a(this.a, downloadWrapper.v(), 6);
        }
        if (com.meizu.cloud.app.downlad.f.c(anEnum)) {
            return downloadWrapper.a(this.a);
        }
        String curSize;
        String talSize;
        String progress;
        if (anEnum == com.meizu.cloud.app.downlad.f.c.TASK_STARTED) {
            curSize = com.meizu.cloud.app.utils.g.a((double) downloadWrapper.t(), this.a.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit));
            talSize = com.meizu.cloud.app.utils.g.a((double) downloadWrapper.p(), this.a.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit));
            progress = String.format("%s/%s", new Object[]{curSize, talSize});
            String speed = com.meizu.cloud.app.utils.g.a((double) downloadWrapper.r(), this.a.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit)) + this.a.getResources().getStringArray(com.meizu.cloud.b.a.b.speed)[0];
            return String.format("%s   %s", new Object[]{progress, speed});
        } else if (anEnum == com.meizu.cloud.app.downlad.f.c.TASK_PAUSED) {
            descript = "";
            if (downloadWrapper.T()) {
                descript = this.a.getString(i.paused);
            } else {
                descript = this.a.getString(i.pre_install_tips);
            }
            curSize = com.meizu.cloud.app.utils.g.a((double) downloadWrapper.t(), this.a.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit));
            talSize = com.meizu.cloud.app.utils.g.a((double) downloadWrapper.p(), this.a.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit));
            progress = String.format("%s/%s", new Object[]{curSize, talSize});
            return String.format("%s   %s", new Object[]{progress, descript});
        } else if (anEnum == n.FETCHING) {
            return this.a.getString(i.fetching_url);
        } else {
            if (anEnum == com.meizu.cloud.app.downlad.f.f.INSTALL_START) {
                return this.a.getString(i.installing);
            }
            if (anEnum == com.meizu.cloud.app.downlad.f.c.TASK_WAITING) {
                descript = this.a.getString(i.waiting_download);
                curSize = com.meizu.cloud.app.utils.g.a((double) downloadWrapper.t(), this.a.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit));
                talSize = com.meizu.cloud.app.utils.g.a((double) downloadWrapper.p(), this.a.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit));
                progress = String.format("%s/%s", new Object[]{curSize, talSize});
                return String.format("%s   %s", new Object[]{progress, descript});
            } else if (anEnum == com.meizu.cloud.app.downlad.f.c.TASK_COMPLETED) {
                return this.a.getString(i.waiting_install);
            } else {
                return null;
            }
        }
    }
}
