package com.meizu.flyme.appcenter.a;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.meizu.cloud.app.a.m;
import com.meizu.cloud.app.core.r;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo.UpdateFinishRecord;
import com.meizu.cloud.statistics.b;
import com.meizu.cloud.statistics.c;
import com.meizu.flyme.appcenter.fragment.d;
import com.meizu.mstore.R;

public class g extends m {
    r d;

    public g(FragmentActivity activity) {
        super(activity);
        this.d = r.a((Context) activity);
    }

    public void a(View v, final UpdateFinishRecord finishRecord, int position) {
        int id = v.getId();
        if (finishRecord == null) {
            return;
        }
        if (id == R.id.tv_left) {
            new Thread(new Runnable(this) {
                final /* synthetic */ g b;

                public void run() {
                    this.b.d.b(finishRecord.id);
                }
            }).start();
            int index = c().indexOf(finishRecord);
            if (index != -1) {
                c().remove(index);
                notifyItemRemoved(index);
            }
        } else if (id == R.id.tv_right) {
            d detailsFragment = new d();
            Bundle bundle = new Bundle();
            bundle.putString("url", finishRecord.url);
            bundle.putString("source_page", this.c);
            detailsFragment.setArguments(bundle);
            com.meizu.cloud.base.b.d.startFragment(this.a, detailsFragment);
            finishRecord.getAppStructItem().click_pos = position + 1;
            b.a().a("item", this.c, c.a(finishRecord.getAppStructItem()));
        }
    }
}
