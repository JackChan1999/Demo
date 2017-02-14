package com.meizu.cloud.app.a;

import android.support.v4.app.FragmentActivity;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.u;
import com.meizu.cloud.app.request.structitem.AppStructItem;

public abstract class a<T extends AppStructItem> extends f<T> {
    protected FragmentActivity a;
    protected t b;
    protected int[] c;

    public a(FragmentActivity activity) {
        super(activity);
        this.a = activity;
        this.b = new t(activity, new u());
    }

    public t a() {
        return this.b;
    }
}
