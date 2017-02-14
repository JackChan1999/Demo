package com.meizu.flyme.appcenter.c;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.fragment.j;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.RankPageInfo.RankPageType;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;
import com.meizu.cloud.app.request.structitem.CategoryStructItem;
import com.meizu.cloud.app.utils.g;
import com.meizu.cloud.app.utils.param.BlockGotoPageInfo;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.statistics.c;
import com.meizu.flyme.appcenter.fragment.AppEventWebviewFragment;
import com.meizu.flyme.appcenter.fragment.d;
import com.meizu.flyme.appcenter.fragment.o;
import com.meizu.flyme.appcenter.fragment.p;
import com.meizu.flyme.appcenter.fragment.q;
import com.meizu.flyme.appcenter.fragment.r;
import java.util.ArrayList;

public class a {
    public static void a(FragmentActivity activity, BlockGotoPageInfo blockGotoPageInfo) {
        Bundle bundle;
        if (PushConstants.EXTRA_APPLICATION_PENDING_INTENT.equals(blockGotoPageInfo.a)) {
            bundle = new Bundle();
            bundle.putString("url", blockGotoPageInfo.b);
            bundle.putString("title_name", blockGotoPageInfo.c);
            if (blockGotoPageInfo.o != 0) {
                bundle.putInt("source_page_id", blockGotoPageInfo.o);
            }
            if (!TextUtils.isEmpty(blockGotoPageInfo.m) || blockGotoPageInfo.l > 0) {
                bundle.putParcelable("uxip_page_source_info", c.a(blockGotoPageInfo));
            } else {
                bundle.putString("source_page", blockGotoPageInfo.i);
            }
            bundle.putInt("pos", blockGotoPageInfo.j);
            bundle.putInt("hor_pos", blockGotoPageInfo.k);
            d detailFragment = new d();
            detailFragment.setArguments(bundle);
            com.meizu.cloud.base.b.d.startFragment(activity, detailFragment);
        } else if ("rank".equals(blockGotoPageInfo.a)) {
            bundle = new Bundle();
            bundle.putString("url", RequestConstants.APP_CENTER_HOST + blockGotoPageInfo.b);
            bundle.putString("title_name", blockGotoPageInfo.c);
            bundle.putString("forward_type", "rank");
            p rankFragment = new p();
            rankFragment.setArguments(bundle);
            com.meizu.cloud.base.b.d.startFragment(activity, rankFragment);
        } else if ("ranks".equals(blockGotoPageInfo.a)) {
            bundle = new Bundle();
            bundle.putString("title_name", blockGotoPageInfo.c);
            bundle.putInt("category_tag_id", blockGotoPageInfo.f);
            bundle.putParcelableArrayList("category_tag_struct", (ArrayList) blockGotoPageInfo.g);
            com.meizu.flyme.appcenter.fragment.c categoryPagerFragment = new com.meizu.flyme.appcenter.fragment.c();
            categoryPagerFragment.setArguments(bundle);
            com.meizu.cloud.base.b.d.startFragment(activity, categoryPagerFragment);
        } else if ("special".equals(blockGotoPageInfo.a)) {
            bundle = new Bundle();
            bundle.putString("url", RequestConstants.APP_CENTER_HOST + blockGotoPageInfo.b);
            bundle.putString("title_name", blockGotoPageInfo.c);
            if (!TextUtils.isEmpty(blockGotoPageInfo.m) || blockGotoPageInfo.l > 0) {
                bundle.putParcelable("uxip_page_source_info", c.a(blockGotoPageInfo));
            } else {
                bundle.putString("source_page", blockGotoPageInfo.i);
            }
            q specialFragment = new q();
            specialFragment.setArguments(bundle);
            com.meizu.cloud.base.b.d.startFragment(activity, specialFragment);
        } else if ("specials".equals(blockGotoPageInfo.a) || "activities".equals(blockGotoPageInfo.a)) {
            bundle = new Bundle();
            bundle.putString("url", blockGotoPageInfo.b);
            bundle.putString("title_name", blockGotoPageInfo.c);
            r specialListFragment = new r();
            specialListFragment.setArguments(bundle);
            com.meizu.cloud.base.b.d.startFragment(activity, specialListFragment);
        } else if (PushConstants.INTENT_ACTIVITY_NAME.equals(blockGotoPageInfo.a)) {
            bundle = new Bundle();
            bundle.putString("url", RequestConstants.APP_CENTER_HOST + blockGotoPageInfo.b);
            bundle.putString("title_name", blockGotoPageInfo.c);
            if (!TextUtils.isEmpty(blockGotoPageInfo.m) || blockGotoPageInfo.l > 0) {
                bundle.putParcelable("uxip_page_source_info", c.a(blockGotoPageInfo));
            } else {
                bundle.putString("source_page", blockGotoPageInfo.i);
            }
            AppEventWebviewFragment eventWebviewFragment = new AppEventWebviewFragment();
            eventWebviewFragment.setArguments(bundle);
            com.meizu.cloud.base.b.d.startFragment(activity, eventWebviewFragment);
        } else if ("h5".equals(blockGotoPageInfo.a)) {
            bundle = new Bundle();
            bundle.putString("url", blockGotoPageInfo.b);
            bundle.putString("title_name", blockGotoPageInfo.c);
            if (!TextUtils.isEmpty(blockGotoPageInfo.m) || blockGotoPageInfo.l > 0) {
                bundle.putParcelable("uxip_page_source_info", c.a(blockGotoPageInfo));
            } else {
                bundle.putString("source_page", blockGotoPageInfo.i);
            }
            j baseHtmlFragment = new j();
            baseHtmlFragment.setArguments(bundle);
            com.meizu.cloud.base.b.d.startFragment(activity, baseHtmlFragment);
        } else if ("game_gifts".equals(blockGotoPageInfo.a)) {
            if (i.a((Context) activity, "com.meizu.flyme.gamecenter") != null) {
                try {
                    Intent intent = new Intent();
                    bundle = new Bundle();
                    bundle.putString("gift_transfer_info", g.a(blockGotoPageInfo.d, blockGotoPageInfo.h));
                    intent.putExtras(bundle);
                    intent.setAction("com.meizu.flyme.gamecenter.game.packs.list");
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if ("subpage".equals(blockGotoPageInfo.a)) {
            bundle = new Bundle();
            bundle.putString("url", blockGotoPageInfo.b);
            bundle.putString("title_name", blockGotoPageInfo.c);
            if (!TextUtils.isEmpty(blockGotoPageInfo.m) || blockGotoPageInfo.l > 0) {
                bundle.putParcelable("uxip_page_source_info", c.a(blockGotoPageInfo));
            } else {
                bundle.putString("source_page", blockGotoPageInfo.i);
            }
            o appNewEssentialFragment = new o();
            appNewEssentialFragment.setArguments(bundle);
            com.meizu.cloud.base.b.d.startFragment(activity, appNewEssentialFragment);
        }
    }

    public static void a(FragmentActivity activity, AbstractStrcutItem itemData, Bundle bundle, int param1) {
        Fragment targetFrament = null;
        if (bundle == null) {
            bundle = new Bundle();
        }
        String forwardType = itemData.type;
        if ("ranks".equals(forwardType)) {
            CategoryStructItem categoryStructItem = (CategoryStructItem) itemData;
            targetFrament = new com.meizu.flyme.appcenter.fragment.c();
            bundle.putInt("category_tag_id", param1);
            bundle.putString("title_name", itemData.name);
            bundle.putParcelableArrayList("category_tag_struct", categoryStructItem.property_tags);
        } else if ("rank".equals(forwardType)) {
            targetFrament = new p();
            bundle.putString("title_name", itemData.name);
            bundle.putString("rank_page_type", RankPageType.APP_CATEGORY.getType());
        } else if (PushConstants.INTENT_ACTIVITY_NAME.equals(forwardType)) {
            targetFrament = new AppEventWebviewFragment();
            bundle.putString("title_name", itemData.name);
        } else if ("special".equals(forwardType)) {
            targetFrament = new q();
            bundle.putString("title_name", itemData.name);
        } else if ("specials".equals(forwardType) || "activities".equals(forwardType)) {
            targetFrament = new r();
            bundle.putString("title_name", itemData.name);
        } else if ("h5".equals(forwardType)) {
            targetFrament = new j();
            bundle.putString("title_name", itemData.name);
        }
        if (targetFrament != null) {
            bundle.putString("url", itemData.url);
            bundle.putString("forward_type", forwardType);
            targetFrament.setArguments(bundle);
            com.meizu.cloud.base.b.d.startFragment(activity, targetFrament);
        }
    }
}
