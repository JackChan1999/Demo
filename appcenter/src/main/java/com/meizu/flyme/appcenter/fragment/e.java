package com.meizu.flyme.appcenter.fragment;

import android.os.Bundle;
import android.view.View;
import com.meizu.cloud.app.block.Blockable;
import com.meizu.cloud.app.fragment.h;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.base.b.d;
import com.meizu.cloud.statistics.b;
import com.meizu.cloud.statistics.c;

public class e extends h {
    public /* synthetic */ void a(View view, Object obj) {
        b(view, (Blockable) obj);
    }

    public void b(View itemView, Blockable blockable) {
        if (blockable instanceof com.meizu.cloud.app.downlad.e) {
            com.meizu.cloud.app.downlad.e wrapper = (com.meizu.cloud.app.downlad.e) blockable;
            if (!a(wrapper)) {
                Bundle bundle = new Bundle();
                bundle.putString("package_name", wrapper.g());
                bundle.putString("source_page", this.mPageName);
                d appDetailFragment = new d();
                appDetailFragment.setArguments(bundle);
                d.startFragment(getActivity(), appDetailFragment);
                AppStructItem appStructItem = wrapper.m();
                appStructItem.click_pos = e().c().indexOf(blockable) + 1;
                appStructItem.install_page = this.mPageName;
                b.a().a("item", this.mPageName, c.a(appStructItem));
            }
        }
    }
}
