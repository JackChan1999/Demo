package com.meizu.flyme.appcenter.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.android.volley.s;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.share.b;
import com.meizu.cloud.app.utils.param.BlockGotoPageInfo;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.flyme.appcenter.c.a;
import com.meizu.mstore.R;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import java.util.HashMap;
import java.util.Map;

public class d extends com.meizu.cloud.app.fragment.d {
    public IWXAPI r;
    private boolean s = false;

    protected void a(View v, AppStructItem appStructItem, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("url", appStructItem.url);
        bundle.putString("title_name", appStructItem.name);
        bundle.putInt("source_page_id", appStructItem.page_info[1]);
        bundle.putString("source_page", appStructItem.install_page);
        d detailFragment = new d();
        detailFragment.setArguments(bundle);
        com.meizu.cloud.base.b.d.startFragment((FragmentActivity) this.i, detailFragment);
    }

    protected void b(String appName) {
        AppSearchFragment searchFragment = new AppSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppSearchFragment.EXTRA_SEARCH, appName);
        searchFragment.setArguments(bundle);
        com.meizu.cloud.base.b.d.startSearchFragment(getActivity(), searchFragment, true, false);
    }

    protected void a(FragmentActivity activity, BlockGotoPageInfo blockGotoPageInfo) {
        a.a(activity, blockGotoPageInfo);
    }

    protected void f() {
        if (b.a(getActivity(), "com.meizu.mstore").equals("c4aa9b9deb124fe4bae4c2ffdc05fac6")) {
            this.r = WXAPIFactory.createWXAPI(getActivity().getApplicationContext(), "wxab45df58aeffe0be", true);
            this.r.registerApp("wxab45df58aeffe0be");
            this.s = true;
            return;
        }
        this.r = WXAPIFactory.createWXAPI(getActivity().getApplicationContext(), "wxd0111b398f7aa904", true);
        this.r.registerApp("wxd0111b398f7aa904");
        this.s = true;
    }

    public IWXAPI g() {
        return this.r;
    }

    public void onClick(View v) {
        super.onClick(v);
        BlockGotoPageInfo blockGotoPageInfo;
        if (v == this.b) {
            blockGotoPageInfo = new BlockGotoPageInfo();
            if ("game_gifts".equals(this.k.notice.type)) {
                blockGotoPageInfo.h = this.k.notice.gift_count;
                blockGotoPageInfo.d = this.k;
                blockGotoPageInfo.c = this.k.name + "礼包";
            } else {
                blockGotoPageInfo.b = this.k.notice.url;
                blockGotoPageInfo.c = this.k.notice.title;
            }
            blockGotoPageInfo.a = this.k.notice.type;
            a.a((FragmentActivity) this.i, blockGotoPageInfo);
            Map<String, String> dataMap = new HashMap();
            dataMap.put(PushConstants.TITLE, blockGotoPageInfo.c);
            dataMap.put("type", this.k.notice.type);
            com.meizu.cloud.statistics.b.a().a("detail_huodong", "detail", dataMap);
        } else if (v == this.h) {
            g appVersionListFragment = new g();
            Bundle bundle = new Bundle();
            bundle.putSerializable("details_info", this.k);
            appVersionListFragment.setArguments(bundle);
            com.meizu.cloud.base.b.d.startFragment(getActivity(), appVersionListFragment);
        } else if (v == this.c) {
            blockGotoPageInfo = new BlockGotoPageInfo();
            blockGotoPageInfo.a = "rank";
            blockGotoPageInfo.b = RequestConstants.APP_BUSINESS + String.format(RequestConstants.RELATED_RECOMMEND, new Object[]{Long.valueOf(this.j)});
            blockGotoPageInfo.c = getString(R.string.appdetail_other_user_downloaded);
            a.a(getActivity(), blockGotoPageInfo);
        } else if (v == this.d && this.k != null) {
            blockGotoPageInfo = new BlockGotoPageInfo();
            blockGotoPageInfo.a = "rank";
            blockGotoPageInfo.b = RequestConstants.APP_BUSINESS + String.format(RequestConstants.APP_DEVELOPER_OTHERS, new Object[]{Integer.valueOf(this.k.developer_id)});
            blockGotoPageInfo.c = getString(R.string.appdetail_fromsamedeveloper);
            a.a(getActivity(), blockGotoPageInfo);
        }
    }

    protected void onRequestData() {
    }

    protected boolean onResponse(Object response) {
        return false;
    }

    protected void onErrorResponse(s error) {
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.s) {
            this.r.unregisterApp();
        }
    }
}
