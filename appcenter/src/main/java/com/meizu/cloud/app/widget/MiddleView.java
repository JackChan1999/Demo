package com.meizu.cloud.app.widget;

import android.content.Context;
import android.view.View;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;

public abstract class MiddleView extends View {
    public abstract void a(AppUpdateStructItem appUpdateStructItem, int i, t tVar);

    public MiddleView(Context context) {
        super(context);
    }
}
