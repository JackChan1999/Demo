package com.meizu.flyme.appcenter.fragment;

import android.os.Bundle;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.fragment.e;
import com.meizu.cloud.base.a.b;

public class a extends e {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected b<AbsBlockItem> d() {
        return new com.meizu.flyme.appcenter.a.e(getActivity(), this, g(), this.b);
    }

    protected void setupActionBar() {
    }
}
