package com.meizu.flyme.appcenter.a;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AbsListView;
import com.meizu.cloud.app.a.k;
import com.meizu.cloud.app.block.requestitem.AppAdStructItem;
import com.meizu.cloud.app.block.requestitem.ContsRow1Col4StructItem;
import com.meizu.cloud.app.block.requestitem.GameQualityStructItem.GameLayout;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structitem.TitleItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.utils.param.BlockGotoPageInfo;
import com.meizu.cloud.base.b.d;
import com.meizu.cloud.statistics.b;
import com.meizu.cloud.statistics.c;
import com.meizu.flyme.appcenter.c.a;
import com.meizu.flyme.appcenter.fragment.p;
import java.util.ArrayList;

public class e extends k {
    public e(Context context, d fragment, AbsListView absListView, t viewController) {
        super(context, fragment, absListView, new ArrayList(), viewController);
    }

    public void onClickAd(AppAdStructItem appAdStructItem, int position, int horPosition) {
        BlockGotoPageInfo blockGotoPageInfo = new BlockGotoPageInfo();
        blockGotoPageInfo.a = appAdStructItem.type;
        blockGotoPageInfo.b = appAdStructItem.url;
        blockGotoPageInfo.c = appAdStructItem.name;
        blockGotoPageInfo.i = appAdStructItem.cur_page;
        blockGotoPageInfo.j = appAdStructItem.pos_ver;
        blockGotoPageInfo.k = appAdStructItem.pos_hor;
        blockGotoPageInfo.l = appAdStructItem.block_id;
        blockGotoPageInfo.m = appAdStructItem.block_type;
        blockGotoPageInfo.n = appAdStructItem.block_name;
        blockGotoPageInfo.o = this.c[1];
        a.a((FragmentActivity) this.e, blockGotoPageInfo);
        String controlName = "";
        if (1 == appAdStructItem.ad_wdm_type) {
            controlName = "adv";
        } else if (2 == appAdStructItem.ad_wdm_type) {
            controlName = "recommend";
        } else {
            controlName = "Featured_block";
        }
        b.a().a(controlName, appAdStructItem.cur_page, c.a(appAdStructItem));
    }

    public void onClickApp(AppStructItem appStructItem, int position, int horPosition) {
        Bundle bundle = new Bundle();
        bundle.putString("url", appStructItem.url);
        bundle.putString("title_name", appStructItem.name);
        bundle.putInt("source_page_id", this.c[1]);
        bundle.putParcelable("uxip_page_source_info", c.c((AbstractStrcutItem) appStructItem));
        if (com.meizu.cloud.statistics.a.c(appStructItem)) {
            bundle.putInt("positionId", appStructItem.position_id);
            bundle.putInt("unitId", appStructItem.unit_id);
            bundle.putString("requestId", appStructItem.request_id);
            bundle.putString("version", appStructItem.version);
        }
        com.meizu.flyme.appcenter.fragment.d detailFragment = new com.meizu.flyme.appcenter.fragment.d();
        detailFragment.setArguments(bundle);
        d.startFragment(this.a.getActivity(), detailFragment);
        b.a().a("item", appStructItem.cur_page, c.a(appStructItem));
        com.meizu.cloud.statistics.a.a(this.e).b(appStructItem);
    }

    public void onClickConts(AbstractStrcutItem abstractStrcutItem, String param, int position, int horPosition) {
        BlockGotoPageInfo blockGotoPageInfo = new BlockGotoPageInfo();
        blockGotoPageInfo.a = abstractStrcutItem.type;
        blockGotoPageInfo.b = abstractStrcutItem.url;
        blockGotoPageInfo.c = abstractStrcutItem.name;
        blockGotoPageInfo.i = abstractStrcutItem.cur_page;
        blockGotoPageInfo.j = abstractStrcutItem.pos_ver;
        blockGotoPageInfo.k = abstractStrcutItem.pos_hor;
        blockGotoPageInfo.l = abstractStrcutItem.block_id;
        blockGotoPageInfo.m = abstractStrcutItem.block_type;
        blockGotoPageInfo.n = abstractStrcutItem.block_name;
        if ("ranks".equals(abstractStrcutItem.type) && (abstractStrcutItem instanceof ContsRow1Col4StructItem)) {
            blockGotoPageInfo.f = ((ContsRow1Col4StructItem) abstractStrcutItem).id;
            blockGotoPageInfo.g = ((ContsRow1Col4StructItem) abstractStrcutItem).property_tags;
        }
        a.a((FragmentActivity) this.e, blockGotoPageInfo);
        b.a().a("Featured_block", abstractStrcutItem.cur_page, c.a(abstractStrcutItem));
    }

    public void onTabClick(GameLayout gameLayout, AppStructItem appStructItem) {
        BlockGotoPageInfo blockGotoPageInfo = new BlockGotoPageInfo();
        blockGotoPageInfo.a = gameLayout.type;
        blockGotoPageInfo.b = gameLayout.url;
        blockGotoPageInfo.c = gameLayout.name;
        blockGotoPageInfo.h = gameLayout.count;
        blockGotoPageInfo.d = appStructItem;
        a.a((FragmentActivity) this.e, blockGotoPageInfo);
    }

    public void onBlockExposure(AbsBlockItem data, int position) {
    }

    public void onDownload(AppStructItem appStructItem, View btn, int position, int horPosition) {
        this.c[2] = appStructItem.block_id;
        appStructItem.page_info = this.c;
        appStructItem.install_page = this.f.b();
        appStructItem.click_pos = position + 1;
        appStructItem.click_hor_pos = horPosition + 1;
        this.f.a(new com.meizu.cloud.app.core.k(appStructItem));
    }

    public void onCancelDownload(AppStructItem appStructItem) {
    }

    public void onMore(TitleItem titleItem) {
        Bundle bundle = new Bundle();
        bundle.putString("url", RequestConstants.APP_CENTER_HOST + titleItem.url);
        bundle.putString("title_name", titleItem.name);
        bundle.putString("forward_type", "more");
        bundle.putInt("source_page_id", this.c[1]);
        bundle.putString("wdm_page_name", titleItem.name);
        p fragment = new p();
        fragment.setArguments(bundle);
        d.startFragment((FragmentActivity) this.e, fragment);
        b.a().a("more", titleItem.cur_page, c.a(titleItem));
    }
}
