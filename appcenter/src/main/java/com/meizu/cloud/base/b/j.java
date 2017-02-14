package com.meizu.cloud.base.b;

import android.os.Bundle;
import android.support.v4.app.q.a;
import android.support.v4.content.h;

public abstract class j<D> extends g implements a<D> {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().a(0, null, this);
    }

    public void a(h<D> hVar, D d) {
        getLoaderManager().a(0);
    }
}
