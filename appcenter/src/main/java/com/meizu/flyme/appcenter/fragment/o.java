package com.meizu.flyme.appcenter.fragment;

import android.os.Bundle;
import android.view.View;
import com.meizu.cloud.app.block.requestitem.AppAdStructItem;
import com.meizu.cloud.app.block.requestitem.GameQualityStructItem.GameLayout;
import com.meizu.cloud.app.block.requestitem.RollingPlayStructItem;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structitem.AdAppBigItem;
import com.meizu.cloud.app.block.structitem.AdBigItem;
import com.meizu.cloud.app.block.structitem.AdvertiseItem;
import com.meizu.cloud.app.block.structitem.ChannelCol5Item;
import com.meizu.cloud.app.block.structitem.ContsRow1Col4Item;
import com.meizu.cloud.app.block.structitem.GameQualityItem;
import com.meizu.cloud.app.block.structitem.RecommendAppItem;
import com.meizu.cloud.app.block.structitem.RollMessageItem;
import com.meizu.cloud.app.block.structitem.RollingPlayItem;
import com.meizu.cloud.app.block.structitem.Row1Col2AppItem;
import com.meizu.cloud.app.block.structitem.Row1Col2AppVerItem;
import com.meizu.cloud.app.block.structitem.Row1Col3AppVerItem;
import com.meizu.cloud.app.block.structitem.Row1Col4AppVerItem;
import com.meizu.cloud.app.block.structitem.Row2Col2AppVerItem;
import com.meizu.cloud.app.block.structitem.SingleRowAppItem;
import com.meizu.cloud.app.block.structitem.TitleItem;
import com.meizu.cloud.app.core.k;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.downlad.f;
import com.meizu.cloud.app.fragment.n;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.utils.param.BlockGotoPageInfo;
import com.meizu.cloud.base.a.d;
import com.meizu.cloud.statistics.a;
import com.meizu.cloud.statistics.b;
import com.meizu.flyme.appcenter.a.c;

public class o extends n {
    public d createRecyclerAdapter() {
        return new c(this.d, this.e, this);
    }

    public void onClickApp(AppStructItem appStructItem, int position, int horPosition) {
        Bundle bundle = new Bundle();
        bundle.putString("url", appStructItem.url);
        bundle.putString("title_name", appStructItem.name);
        bundle.putInt("source_page_id", this.mPageInfo[1]);
        bundle.putParcelable("uxip_page_source_info", com.meizu.cloud.statistics.c.c((AbstractStrcutItem) appStructItem));
        if (a.c(appStructItem)) {
            bundle.putInt("positionId", appStructItem.position_id);
            bundle.putInt("unitId", appStructItem.unit_id);
            bundle.putString("requestId", appStructItem.request_id);
            bundle.putString("version", appStructItem.version);
        }
        d detailFragment = new d();
        detailFragment.setArguments(bundle);
        com.meizu.cloud.base.b.d.startFragment(getActivity(), detailFragment);
        b.a().a("click_essential", appStructItem.cur_page, com.meizu.cloud.statistics.c.c(appStructItem));
        a.a(this.d).b(appStructItem);
    }

    public void onDownload(AppStructItem appStructItem, View btn, int position, int horPosition) {
        this.mPageInfo[2] = appStructItem.block_id;
        appStructItem.click_pos = position + 1;
        appStructItem.click_hor_pos = horPosition + 1;
        appStructItem.page_info = this.mPageInfo;
        appStructItem.install_page = this.mPageName;
        appStructItem.click_pos = position + 1;
        appStructItem.click_hor_pos = horPosition + 1;
        this.e.a(new k(appStructItem));
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
        blockGotoPageInfo.o = this.mPageInfo[1];
        com.meizu.flyme.appcenter.c.a.a(getActivity(), blockGotoPageInfo);
        b.a().a("click_essential", appAdStructItem.cur_page, com.meizu.cloud.statistics.c.a(appAdStructItem));
    }

    public void onCancelDownload(AppStructItem appStructItem) {
    }

    public void onMore(TitleItem titleItem) {
    }

    public void onClickConts(AbstractStrcutItem abstractStrcutItem, String param, int position, int horPosition) {
    }

    public void onTabClick(GameLayout gameLayout, AppStructItem appStructItem) {
    }

    public void onBlockExposure(AbsBlockItem data, int position) {
        a(data, position);
    }

    public void onDownloadStateChanged(e wrapper) {
        if (wrapper != null && wrapper.f() != f.c.TASK_STARTED) {
            a(wrapper, false);
        }
    }

    public void onDownloadProgress(e wrapper) {
        a(wrapper, false);
    }

    public void onFetchStateChange(e wrapper) {
        a(wrapper, false);
    }

    public void onInstallStateChange(e wrapper) {
        a(wrapper, true);
    }

    public void b(e wrapper) {
        a(wrapper, false);
    }

    public void a(e wrapper) {
        a(wrapper, false);
    }

    public void onEventMainThread(com.meizu.cloud.app.c.a appStateChangeEvent) {
        if (getActivity() != null && this.mRunning) {
            b(appStateChangeEvent.b);
        }
    }

    public void onEventMainThread(com.meizu.cloud.app.c.b appUpdateCheckEvent) {
        if (getActivity() != null && this.mRunning && appUpdateCheckEvent.c) {
            for (String pkg : appUpdateCheckEvent.a) {
                b(pkg);
            }
        }
    }

    private void a(AbsBlockItem absBlockItem, int position) {
        if (absBlockItem != null) {
            a.a(this.d).a(absBlockItem);
            switch (absBlockItem.style) {
                case 0:
                    if ((absBlockItem instanceof TitleItem) && !((TitleItem) absBlockItem).is_uxip_exposured) {
                        a((TitleItem) absBlockItem, position);
                        return;
                    }
                    return;
                case 1:
                    if ((absBlockItem instanceof RollingPlayItem) && ((RollingPlayItem) absBlockItem).rollingPlayItem != null) {
                        RollingPlayStructItem rollingPlayStructItem = ((RollingPlayItem) absBlockItem).rollingPlayItem;
                        if (rollingPlayStructItem.mSubItems != null && rollingPlayStructItem.mSubItems.size() > 0) {
                            for (int i = 0; i < rollingPlayStructItem.mSubItems.size(); i++) {
                                AbstractStrcutItem abstractStrcutItem = (AbstractStrcutItem) rollingPlayStructItem.mSubItems.get(i);
                                if (abstractStrcutItem != null && (abstractStrcutItem instanceof AppAdStructItem)) {
                                    abstractStrcutItem.pos_ver = position + 1;
                                    abstractStrcutItem.cur_page = this.mPageName;
                                }
                            }
                            return;
                        }
                        return;
                    }
                    return;
                case 2:
                    if (absBlockItem instanceof AdvertiseItem) {
                        if (!(((AdvertiseItem) absBlockItem).advertise1 == null || ((AdvertiseItem) absBlockItem).advertise1.is_uxip_exposured)) {
                            a(((AdvertiseItem) absBlockItem).advertise1, position);
                        }
                        if (((AdvertiseItem) absBlockItem).advertise2 != null && !((AdvertiseItem) absBlockItem).advertise2.is_uxip_exposured) {
                            a(((AdvertiseItem) absBlockItem).advertise2, position);
                            return;
                        }
                        return;
                    }
                    return;
                case 3:
                    if (absBlockItem instanceof Row1Col2AppItem) {
                        if (!(((Row1Col2AppItem) absBlockItem).app1 == null || ((Row1Col2AppItem) absBlockItem).app1.is_uxip_exposured)) {
                            a(((Row1Col2AppItem) absBlockItem).app1, position);
                        }
                        if (((Row1Col2AppItem) absBlockItem).app2 != null && !((Row1Col2AppItem) absBlockItem).app2.is_uxip_exposured) {
                            a(((Row1Col2AppItem) absBlockItem).app2, position);
                            return;
                        }
                        return;
                    }
                    return;
                case 4:
                case 24:
                    if ((absBlockItem instanceof SingleRowAppItem) && ((SingleRowAppItem) absBlockItem).app != null && !((SingleRowAppItem) absBlockItem).app.is_uxip_exposured) {
                        a(((SingleRowAppItem) absBlockItem).app, position);
                        return;
                    }
                    return;
                case 5:
                    if ((absBlockItem instanceof RecommendAppItem) && ((RecommendAppItem) absBlockItem).app != null && !((RecommendAppItem) absBlockItem).app.is_uxip_exposured) {
                        a(((RecommendAppItem) absBlockItem).app, position);
                        return;
                    }
                    return;
                case 8:
                    if (absBlockItem instanceof ContsRow1Col4Item) {
                        if (!(((ContsRow1Col4Item) absBlockItem).item1 == null || ((ContsRow1Col4Item) absBlockItem).item1.is_uxip_exposured)) {
                            a(((ContsRow1Col4Item) absBlockItem).item1, position);
                        }
                        if (!(((ContsRow1Col4Item) absBlockItem).item2 == null || ((ContsRow1Col4Item) absBlockItem).item2.is_uxip_exposured)) {
                            a(((ContsRow1Col4Item) absBlockItem).item2, position);
                        }
                        if (!(((ContsRow1Col4Item) absBlockItem).item3 == null || ((ContsRow1Col4Item) absBlockItem).item3.is_uxip_exposured)) {
                            a(((ContsRow1Col4Item) absBlockItem).item3, position);
                        }
                        if (((ContsRow1Col4Item) absBlockItem).item4 != null && !((ContsRow1Col4Item) absBlockItem).item4.is_uxip_exposured) {
                            a(((ContsRow1Col4Item) absBlockItem).item4, position);
                            return;
                        }
                        return;
                    }
                    return;
                case 9:
                    if ((absBlockItem instanceof AdBigItem) && ((AdBigItem) absBlockItem).mAdBigStructItem != null && !((AdBigItem) absBlockItem).mAdBigStructItem.is_uxip_exposured) {
                        a(((AdBigItem) absBlockItem).mAdBigStructItem, position);
                        return;
                    }
                    return;
                case 10:
                    if ((absBlockItem instanceof AdAppBigItem) && ((AdAppBigItem) absBlockItem).mAppAdBigStructItem != null && !((AdAppBigItem) absBlockItem).mAppAdBigStructItem.is_uxip_exposured) {
                        a(((AdAppBigItem) absBlockItem).mAppAdBigStructItem, position);
                        return;
                    }
                    return;
                case 11:
                    if (absBlockItem instanceof Row1Col4AppVerItem) {
                        if (!(((Row1Col4AppVerItem) absBlockItem).mAppStructItem1 == null || ((Row1Col4AppVerItem) absBlockItem).mAppStructItem1.is_uxip_exposured)) {
                            a(((Row1Col4AppVerItem) absBlockItem).mAppStructItem1, position);
                        }
                        if (!(((Row1Col4AppVerItem) absBlockItem).mAppStructItem2 == null || ((Row1Col4AppVerItem) absBlockItem).mAppStructItem2.is_uxip_exposured)) {
                            a(((Row1Col4AppVerItem) absBlockItem).mAppStructItem2, position);
                        }
                        if (!(((Row1Col4AppVerItem) absBlockItem).mAppStructItem3 == null || ((Row1Col4AppVerItem) absBlockItem).mAppStructItem3.is_uxip_exposured)) {
                            a(((Row1Col4AppVerItem) absBlockItem).mAppStructItem3, position);
                        }
                        if (((Row1Col4AppVerItem) absBlockItem).mAppStructItem4 != null && !((Row1Col4AppVerItem) absBlockItem).mAppStructItem4.is_uxip_exposured) {
                            a(((Row1Col4AppVerItem) absBlockItem).mAppStructItem4, position);
                            return;
                        }
                        return;
                    }
                    return;
                case 12:
                    if (absBlockItem instanceof ChannelCol5Item) {
                        if (!(((ChannelCol5Item) absBlockItem).mChannelStructItem1 == null || ((ChannelCol5Item) absBlockItem).mChannelStructItem1.is_uxip_exposured)) {
                            a(((ChannelCol5Item) absBlockItem).mChannelStructItem1, position);
                        }
                        if (!(((ChannelCol5Item) absBlockItem).mChannelStructItem2 == null || ((ChannelCol5Item) absBlockItem).mChannelStructItem2.is_uxip_exposured)) {
                            a(((ChannelCol5Item) absBlockItem).mChannelStructItem2, position);
                        }
                        if (!(((ChannelCol5Item) absBlockItem).mChannelStructItem3 == null || ((ChannelCol5Item) absBlockItem).mChannelStructItem3.is_uxip_exposured)) {
                            a(((ChannelCol5Item) absBlockItem).mChannelStructItem3, position);
                        }
                        if (!(((ChannelCol5Item) absBlockItem).mChannelStructItem4 == null || ((ChannelCol5Item) absBlockItem).mChannelStructItem4.is_uxip_exposured)) {
                            a(((ChannelCol5Item) absBlockItem).mChannelStructItem4, position);
                        }
                        if (((ChannelCol5Item) absBlockItem).mChannelStructItem5 != null && !((ChannelCol5Item) absBlockItem).mChannelStructItem5.is_uxip_exposured) {
                            a(((ChannelCol5Item) absBlockItem).mChannelStructItem5, position);
                            return;
                        }
                        return;
                    }
                    return;
                case 13:
                    if ((absBlockItem instanceof RollMessageItem) && ((RollMessageItem) absBlockItem).mRollMessageStructItem != null && ((RollMessageItem) absBlockItem).mRollMessageStructItem.size() > 0) {
                        for (AbstractStrcutItem item : ((RollMessageItem) absBlockItem).mRollMessageStructItem) {
                            if (!(item == null || item.is_uxip_exposured)) {
                                a(item, position);
                            }
                        }
                        return;
                    }
                    return;
                case 14:
                    if ((absBlockItem instanceof GameQualityItem) && ((GameQualityItem) absBlockItem).mGameQualityStructItem != null && !((GameQualityItem) absBlockItem).mGameQualityStructItem.is_uxip_exposured) {
                        a(((GameQualityItem) absBlockItem).mGameQualityStructItem, position);
                        return;
                    }
                    return;
                case 15:
                case 23:
                    return;
                case 16:
                    if (absBlockItem instanceof Row1Col2AppVerItem) {
                        if (!(((Row1Col2AppVerItem) absBlockItem).mAppStructItem1 == null || ((Row1Col2AppVerItem) absBlockItem).mAppStructItem1.is_uxip_exposured)) {
                            a(((Row1Col2AppVerItem) absBlockItem).mAppStructItem1, position);
                        }
                        if (((Row1Col2AppVerItem) absBlockItem).mAppStructItem2 != null && !((Row1Col2AppVerItem) absBlockItem).mAppStructItem2.is_uxip_exposured) {
                            a(((Row1Col2AppVerItem) absBlockItem).mAppStructItem2, position);
                            return;
                        }
                        return;
                    }
                    return;
                case 17:
                    if (absBlockItem instanceof Row1Col3AppVerItem) {
                        if (!(((Row1Col3AppVerItem) absBlockItem).mAppStructItem1 == null || ((Row1Col3AppVerItem) absBlockItem).mAppStructItem1.is_uxip_exposured)) {
                            a(((Row1Col3AppVerItem) absBlockItem).mAppStructItem1, position);
                        }
                        if (!(((Row1Col3AppVerItem) absBlockItem).mAppStructItem2 == null || ((Row1Col3AppVerItem) absBlockItem).mAppStructItem2.is_uxip_exposured)) {
                            a(((Row1Col3AppVerItem) absBlockItem).mAppStructItem2, position);
                        }
                        if (((Row1Col3AppVerItem) absBlockItem).mAppStructItem3 != null && !((Row1Col3AppVerItem) absBlockItem).mAppStructItem3.is_uxip_exposured) {
                            a(((Row1Col3AppVerItem) absBlockItem).mAppStructItem3, position);
                            return;
                        }
                        return;
                    }
                    return;
                case 22:
                    if (absBlockItem instanceof Row2Col2AppVerItem) {
                        if (!(((Row2Col2AppVerItem) absBlockItem).mAppStructItem1 == null || ((Row2Col2AppVerItem) absBlockItem).mAppStructItem1.is_uxip_exposured)) {
                            a(((Row2Col2AppVerItem) absBlockItem).mAppStructItem1, position);
                        }
                        if (!(((Row2Col2AppVerItem) absBlockItem).mAppStructItem2 == null || ((Row2Col2AppVerItem) absBlockItem).mAppStructItem2.is_uxip_exposured)) {
                            a(((Row2Col2AppVerItem) absBlockItem).mAppStructItem2, position);
                        }
                        if (!(((Row2Col2AppVerItem) absBlockItem).mAppStructItem3 == null || ((Row2Col2AppVerItem) absBlockItem).mAppStructItem3.is_uxip_exposured)) {
                            a(((Row2Col2AppVerItem) absBlockItem).mAppStructItem3, position);
                        }
                        if (((Row2Col2AppVerItem) absBlockItem).mAppStructItem4 != null && !((Row2Col2AppVerItem) absBlockItem).mAppStructItem4.is_uxip_exposured) {
                            a(((Row2Col2AppVerItem) absBlockItem).mAppStructItem4, position);
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private void a(AppAdStructItem appAdStructItem, int position) {
        appAdStructItem.pos_ver = position + 1;
        appAdStructItem.cur_page = this.mPageName;
        b.a().a("exposure_essential", appAdStructItem.cur_page, com.meizu.cloud.statistics.c.a(appAdStructItem));
        appAdStructItem.is_uxip_exposured = true;
    }

    private void a(TitleItem titleItem, int position) {
        titleItem.pos_ver = position + 1;
        titleItem.cur_page = this.mPageName;
        b.a().a("exposure_essential", titleItem.cur_page, com.meizu.cloud.statistics.c.a(titleItem));
        titleItem.is_uxip_exposured = true;
    }

    private void a(AppStructItem appStructItem, int position) {
        appStructItem.pos_ver = position + 1;
        appStructItem.cur_page = this.mPageName;
        b.a().a("exposure_essential", appStructItem.cur_page, com.meizu.cloud.statistics.c.c(appStructItem));
        appStructItem.is_uxip_exposured = true;
    }

    private void a(AbstractStrcutItem abstractStrcutItem, int position) {
        abstractStrcutItem.pos_ver = position + 1;
        abstractStrcutItem.cur_page = this.mPageName;
        b.a().a("exposure_essential", abstractStrcutItem.cur_page, com.meizu.cloud.statistics.c.a(abstractStrcutItem));
        abstractStrcutItem.is_uxip_exposured = true;
    }
}
