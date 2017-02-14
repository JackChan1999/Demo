package com.meizu.flyme.appcenter.a;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.meizu.cloud.app.a.h;
import com.meizu.cloud.app.block.requestitem.AppAdStructItem;
import com.meizu.cloud.app.block.requestitem.GameQualityStructItem.GameLayout;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structitem.CategoryGridItem;
import com.meizu.cloud.app.block.structitem.TitleItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.request.model.RankPageInfo.RankPageType;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.app.request.structitem.CategoryStructItem;
import com.meizu.cloud.app.request.structitem.PropertyTag;
import com.meizu.cloud.app.widget.AdContentView;
import com.meizu.cloud.app.widget.CommonListItemView;
import com.meizu.cloud.app.widget.RankAppItemViewV2;
import com.meizu.cloud.base.a.d.a;
import com.meizu.cloud.statistics.b;
import com.meizu.cloud.statistics.c;
import com.meizu.flyme.appcenter.fragment.p;

public class d extends h {
    public d(FragmentActivity activity, t viewController) {
        super(activity, viewController);
    }

    public int getItemViewType(int position) {
        if (((AppUpdateStructItem) c(position)) == null || !((AppUpdateStructItem) c(position)).isAdStruct()) {
            return super.getItemViewType(position);
        }
        return Integer.MAX_VALUE;
    }

    public CommonListItemView a(int viewType) {
        if (viewType == Integer.MAX_VALUE) {
            return new AdContentView(this.d);
        }
        return new RankAppItemViewV2(this.d, this.b);
    }

    public void a(a holder, int position) {
        super.a((a) holder, position);
        if (holder.itemView instanceof AdContentView) {
            holder.itemView.setClickable(false);
            holder.itemView.setOnClickListener(null);
            holder.itemView.setOnLongClickListener(null);
            holder.itemView.setOnTagClickedListner(new AdContentView.a(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public void a(PropertyTag tag, int position, int horPosition) {
                    AppUpdateStructItem appUpdateStructItem = (AppUpdateStructItem) this.a.c(position);
                    appUpdateStructItem.cur_page = this.a.b.b();
                    p targetFrament = new p();
                    Bundle bundle = new Bundle();
                    bundle.putString("title_name", tag.name);
                    bundle.putString("rank_page_type", RankPageType.APP_CATEGORY.getType());
                    bundle.putString("url", tag.url);
                    bundle.putString("forward_type", "rank");
                    bundle.putParcelable("uxip_page_source_info", c.a(appUpdateStructItem, position, horPosition));
                    targetFrament.setArguments(bundle);
                    com.meizu.cloud.base.b.d.startFragment(this.a.a, targetFrament);
                    b.a().a("click_ranking_block", this.a.b.b(), c.a(appUpdateStructItem, position + 1, horPosition + 1, tag.name, tag.id));
                }
            });
        }
    }

    public void a(AppStructItem appStructItem) {
        if (com.meizu.cloud.statistics.a.c(appStructItem)) {
            com.meizu.cloud.statistics.a.a(this.d).a(appStructItem);
        }
        if (appStructItem instanceof AppUpdateStructItem) {
            AppUpdateStructItem appUpdateStructItem = (AppUpdateStructItem) appStructItem;
            if (appUpdateStructItem.isAdStruct() && appUpdateStructItem.adContent.data != null) {
                for (int i = 0; i < appUpdateStructItem.adContent.data.size(); i++) {
                    b.a().a("exposure_ranking_block", this.b.b(), c.a(appUpdateStructItem, i));
                }
            }
        }
    }

    public void b(AbsBlockItem absBlockItem) {
        if (absBlockItem instanceof CategoryGridItem) {
            CategoryGridItem gridItem = (CategoryGridItem) absBlockItem;
            if (gridItem.structItemList != null) {
                for (int i = 0; i < gridItem.structItemList.size(); i++) {
                    AbstractStrcutItem structItem = (CategoryStructItem) gridItem.structItemList.get(i);
                    structItem.pos_ver = 1;
                    structItem.pos_hor = i + 1;
                    b.a().a("exposure_ranking_block", this.b.b(), c.a(structItem));
                }
            }
        }
    }

    public void onClickAd(AppAdStructItem appAdStructItem, int position, int horPosition) {
    }

    public void onClickApp(AppStructItem appStructItem, int position, int horPosition) {
    }

    public void onDownload(AppStructItem appStructItem, View btn, int position, int horPosition) {
    }

    public void onCancelDownload(AppStructItem appStructItem) {
    }

    public void onMore(TitleItem titleItem) {
    }

    public void onClickConts(AbstractStrcutItem abstractStrcutItem, String param, int position, int horPosition) {
        if (abstractStrcutItem instanceof CategoryStructItem) {
            AbstractStrcutItem categoryStructItem = (CategoryStructItem) abstractStrcutItem;
            int tagId = 0;
            if (categoryStructItem.property_tags != null) {
                for (int i = 0; i < categoryStructItem.property_tags.size(); i++) {
                    if (((PropertyTag) categoryStructItem.property_tags.get(i)).name.equals(param)) {
                        tagId = ((PropertyTag) categoryStructItem.property_tags.get(i)).id;
                        break;
                    }
                }
            }
            categoryStructItem.cur_page = this.b.b();
            Bundle bundle = new Bundle();
            bundle.putParcelable("uxip_page_source_info", c.c(abstractStrcutItem));
            com.meizu.flyme.appcenter.c.a.a(this.a, categoryStructItem, bundle, tagId);
            categoryStructItem.pos_ver = position + 1;
            categoryStructItem.pos_hor = horPosition + 1;
            b.a().a("click_ranking_block", this.b.b(), c.b(categoryStructItem));
        }
    }

    public void onTabClick(GameLayout gameLayout, AppStructItem appStructItem) {
    }

    public void onBlockExposure(AbsBlockItem data, int position) {
    }
}
