package com.meizu.flyme.appcenter.a;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.meizu.cloud.app.a.l;
import com.meizu.cloud.app.block.Blockable;
import com.meizu.cloud.app.block.customblock.AccountItem;
import com.meizu.cloud.app.block.customblock.DividerItem;
import com.meizu.cloud.app.block.customblock.FuctionItem;
import com.meizu.cloud.app.block.customblock.MoreItem;
import com.meizu.cloud.app.block.customblock.PartitionItem;
import com.meizu.cloud.app.block.customblock.UpdateRefreshItem;
import com.meizu.cloud.app.core.q;
import com.meizu.cloud.app.request.model.AccountInfoModel;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo;
import com.meizu.cloud.app.utils.k;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.base.app.BaseCommonActivity;
import com.meizu.flyme.appcenter.fragment.s;
import com.meizu.mstore.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class f extends l {
    private AccountInfoModel k;

    class a extends com.meizu.cloud.base.a.e.a {
        public View a;
        public View b;
        public ImageView c;
        public TextView d;
        public TextView e;
        final /* synthetic */ f f;

        public a(f fVar, View itemView) {
            this.f = fVar;
            super(fVar, itemView, true);
        }
    }

    class b extends com.meizu.cloud.base.a.e.a {
        public ImageView a;
        public TextView b;
        public View c;
        final /* synthetic */ f d;

        public b(f fVar, View itemView) {
            this.d = fVar;
            super(fVar, itemView, true);
        }
    }

    class c extends com.meizu.cloud.base.a.e.a {
        public TextView a;
        public TextView b;
        final /* synthetic */ f c;

        public c(f fVar, View itemView) {
            this.c = fVar;
            super(fVar, itemView, false);
        }
    }

    class d extends com.meizu.cloud.base.a.e.a {
        public Button a;
        final /* synthetic */ f b;

        public d(f fVar, View itemView) {
            this.b = fVar;
            super(fVar, itemView, false);
        }
    }

    class e extends com.meizu.cloud.base.a.e.a {
        public View a;
        public View b;
        public TextView c;
        public TextView d;
        final /* synthetic */ f e;

        public e(f fVar, View itemView) {
            this.e = fVar;
            super(fVar, itemView, false);
        }
    }

    public f(FragmentActivity activity, AccountInfoModel accountInfo) {
        super(activity);
        this.k = accountInfo;
        this.e = "myapp";
        this.c.a(this.e);
        this.f[1] = 11;
        this.c.a(this.f);
    }

    public void a(AccountInfoModel accountInfoModel) {
        this.k = accountInfoModel;
        notifyItemChanged(0);
    }

    public com.meizu.cloud.base.a.e.a a(ViewGroup parent, int viewType) {
        com.meizu.cloud.base.a.e.a viewHolder = super.a(parent, viewType);
        if (viewHolder != null) {
            return viewHolder;
        }
        ImageView icon;
        if (viewType == 1) {
            View accountLayout = this.b.inflate(R.layout.account_info_layout, parent, false);
            View loginView = accountLayout.findViewById(R.id.account_login);
            View unloginView = accountLayout.findViewById(R.id.account_unlogin);
            icon = (ImageView) accountLayout.findViewById(R.id.account_picture);
            TextView name = (TextView) accountLayout.findViewById(R.id.account_name);
            TextView balance = (TextView) accountLayout.findViewById(R.id.account_balance);
            com.meizu.cloud.base.a.e.a accountViewHolder = new a(this, accountLayout);
            accountViewHolder.a = loginView;
            accountViewHolder.b = unloginView;
            accountViewHolder.c = icon;
            accountViewHolder.d = name;
            accountViewHolder.e = balance;
            return accountViewHolder;
        } else if (viewType == 2) {
            View fuctionLayout = this.b.inflate(R.layout.mine_manage_item_layout, parent, false);
            icon = (ImageView) fuctionLayout.findViewById(R.id.icon);
            TextView functionView = (TextView) fuctionLayout.findViewById(R.id.fuction_item_title);
            View divider = fuctionLayout.findViewById(R.id.divider);
            com.meizu.cloud.base.a.e.a fuctionViewHolder = new b(this, fuctionLayout);
            fuctionViewHolder.a = icon;
            fuctionViewHolder.b = functionView;
            fuctionViewHolder.c = divider;
            return fuctionViewHolder;
        } else if (viewType == 3) {
            View paritionLayout = this.b.inflate(R.layout.block_item_container_v2, parent, false);
            paritionLayout.findViewById(R.id.title_title_layout).setVisibility(0);
            TextView partitionTitle = (TextView) paritionLayout.findViewById(16908308);
            TextView partitionButton = (TextView) paritionLayout.findViewById(16908309);
            r0 = new c(this, paritionLayout);
            r0.a = partitionTitle;
            r0.b = partitionButton;
            return r0;
        } else if (viewType == 4) {
            View refreshLayout = this.b.inflate(R.layout.update_refresh_layout, parent, false);
            View updateChecked = refreshLayout.findViewById(R.id.update_checked);
            View updateChecking = refreshLayout.findViewById(R.id.update_checking);
            TextView lastChecked = (TextView) refreshLayout.findViewById(R.id.refresh_last_checked);
            TextView manualCheck = (TextView) refreshLayout.findViewById(R.id.manual_check);
            r0 = new e(this, refreshLayout);
            r0.a = updateChecked;
            r0.b = updateChecking;
            r0.c = lastChecked;
            r0.d = manualCheck;
            return r0;
        } else if (viewType == 5) {
            View recordLayout = this.b.inflate(R.layout.update_record_item, parent, false);
            TextView recordView = (Button) recordLayout.findViewById(R.id.record);
            com.meizu.cloud.app.utils.b.a(recordView, R.color.theme_color, (float) (this.a.getResources().getDimensionPixelSize(R.dimen.search_hot_item_height) / 2), false);
            r0 = new d(this, recordLayout);
            r0.a = recordView;
            return r0;
        } else if (viewType != 6) {
            return viewHolder;
        } else {
            View dividerLayout = this.b.inflate(R.layout.little_title_divider, parent, false);
            dividerLayout.setEnabled(false);
            return new com.meizu.cloud.base.a.e.a(this, dividerLayout);
        }
    }

    public void a(com.meizu.cloud.base.a.e.a holder, int position) {
        super.a((com.meizu.cloud.base.a.e.a) holder, position);
        if (holder instanceof a) {
            a accountViewHolder = (a) holder;
            if (this.k == null) {
                accountViewHolder.b.setVisibility(0);
                accountViewHolder.a.setVisibility(8);
                accountViewHolder.c.setImageDrawable(this.a.getResources().getDrawable(R.drawable.ic_default_avatar));
                return;
            }
            accountViewHolder.b.setVisibility(8);
            accountViewHolder.a.setVisibility(0);
            Picasso.with(this.a.getApplicationContext()).load(this.k.icon).placeholder((int) R.drawable.ic_default_avatar).error((int) R.drawable.ic_default_avatar).fit().into(accountViewHolder.c);
            accountViewHolder.d.setText(this.k.nickname);
            if (TextUtils.isEmpty(this.k.flyme)) {
                accountViewHolder.e.setText(this.k.phone);
            } else {
                accountViewHolder.e.setText(this.k.flyme + AccountInfoModel.FLYME_SUFFIX);
            }
        } else if (holder instanceof b) {
            b fuctionViewHolder = (b) holder;
            blockable = (Blockable) b(position);
            if (blockable instanceof FuctionItem) {
                FuctionItem fuctionItem = (FuctionItem) blockable;
                fuctionViewHolder.a.setImageResource(fuctionItem.icon);
                fuctionViewHolder.b.setText(fuctionItem.fuctionText);
                if (fuctionItem.id == R.id.setting_manage) {
                    boolean isShowDivider = false;
                    for (Blockable instance : c()) {
                        if (instance instanceof UpdateRefreshItem) {
                            isShowDivider = true;
                            break;
                        }
                    }
                    if (isShowDivider) {
                        fuctionViewHolder.c.setVisibility(0);
                        return;
                    } else {
                        fuctionViewHolder.c.setVisibility(8);
                        return;
                    }
                }
                fuctionViewHolder.c.setVisibility(0);
            }
        } else if (holder instanceof d) {
            ((d) holder).a.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ f a;

                {
                    this.a = r1;
                }

                public void onClick(View view) {
                    com.meizu.cloud.base.b.d.startFragment(this.a.a, new s());
                    com.meizu.cloud.statistics.b.a().a("myapp_updatehistory", "myapp", null);
                }
            });
        } else if (holder instanceof e) {
            e refreshViewHolder = (e) holder;
            long lastCheckTime = com.meizu.cloud.app.core.m.d.a(this.a, "last_check_update_time");
            String lastCehckText = this.a.getString(R.string.never_check_update);
            if (lastCheckTime > 0) {
                String formattedString = com.meizu.common.util.a.a(this.a, lastCheckTime, 6);
                lastCehckText = String.format(this.a.getString(R.string.lastest_checked_time), new Object[]{formattedString});
            }
            refreshViewHolder.c.setText(lastCehckText);
            refreshViewHolder.a.setVisibility(0);
            refreshViewHolder.b.setVisibility(8);
            final e eVar = refreshViewHolder;
            refreshViewHolder.d.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ f b;

                public void onClick(View v) {
                    if (m.b(this.b.a)) {
                        eVar.a.setVisibility(8);
                        eVar.b.setVisibility(0);
                        k.b(this.b.a, "AppUpdateAppListAdapter", "user click to check update");
                        q.a(this.b.a).a(new com.meizu.cloud.app.core.s().a(true).d(true));
                        com.meizu.cloud.statistics.b.a().a("myapp_getupdate", "myapp", null);
                    } else if (this.b.a instanceof BaseCommonActivity) {
                        ((BaseCommonActivity) this.b.a).l();
                    }
                }
            });
        } else if (holder instanceof c) {
            c partitionViewHolder = (c) holder;
            blockable = (Blockable) b(position);
            if (blockable instanceof PartitionItem) {
                PartitionItem partitionItem = (PartitionItem) blockable;
                List<Blockable> blockables = c().subList(position, b());
                int count = 0;
                for (int i = 0; i < blockables.size(); i++) {
                    if (blockables.get(i) instanceof ServerUpdateAppInfo) {
                        count++;
                    }
                }
                if (partitionItem.tag != null) {
                    partitionViewHolder.b.setEnabled(((Boolean) partitionItem.tag).booleanValue());
                }
                partitionViewHolder.a.setVisibility(0);
                partitionViewHolder.a.setText(this.a.getString(R.string.can_update, new Object[]{Integer.valueOf(count)}));
                partitionViewHolder.b.setVisibility(0);
                partitionViewHolder.b.setText(this.a.getString(R.string.update_all));
                partitionViewHolder.b.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ f a;

                    {
                        this.a = r1;
                    }

                    public void onClick(View v) {
                        List<ServerUpdateAppInfo> infos = new ArrayList();
                        for (Blockable blockItem : this.a.c()) {
                            if (blockItem instanceof ServerUpdateAppInfo) {
                                ServerUpdateAppInfo updateAppInfo = (ServerUpdateAppInfo) blockItem;
                                if (!(com.meizu.cloud.app.downlad.d.a(this.a.a).f(updateAppInfo.package_name) || ((ServerUpdateAppInfo) blockItem).getAppStructItem() == null)) {
                                    ((ServerUpdateAppInfo) blockItem).getAppStructItem().page_info = this.a.f;
                                    ((ServerUpdateAppInfo) blockItem).getAppStructItem().install_page = this.a.e;
                                    infos.add(updateAppInfo);
                                }
                            }
                        }
                        if (infos.size() > 0) {
                            this.a.c.a(new com.meizu.cloud.app.core.k((ServerUpdateAppInfo[]) infos.toArray(new ServerUpdateAppInfo[infos.size()])));
                            com.meizu.cloud.statistics.b.a().a("myapp_updateall", "myapp", null);
                        }
                    }
                });
            }
        }
    }

    public int getItemViewType(int position) {
        if (this.i && position == 0) {
            return -1;
        }
        if (this.j && position == getItemCount() - 1) {
            return -2;
        }
        Blockable blockable = (Blockable) b(position);
        if (blockable instanceof AccountItem) {
            return 1;
        }
        if (blockable instanceof FuctionItem) {
            return 2;
        }
        if (blockable instanceof PartitionItem) {
            return 3;
        }
        if (blockable instanceof UpdateRefreshItem) {
            return 4;
        }
        if (blockable instanceof MoreItem) {
            return 5;
        }
        if (blockable instanceof DividerItem) {
            return 6;
        }
        return 0;
    }
}
