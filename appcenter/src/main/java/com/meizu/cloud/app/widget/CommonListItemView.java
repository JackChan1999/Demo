package com.meizu.cloud.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;

public abstract class CommonListItemView extends FrameLayout {
    protected a a;

    public interface a {
        void a(AppStructItem appStructItem, View view);
    }

    public abstract void a(AppUpdateStructItem appUpdateStructItem, int i);

    public CommonListItemView(Context context) {
        super(context);
    }

    public CommonListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnInstallBtnClickListener(a onInstallBtnClickListener) {
        this.a = onInstallBtnClickListener;
    }
}
