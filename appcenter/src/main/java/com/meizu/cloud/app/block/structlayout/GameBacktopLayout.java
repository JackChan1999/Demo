package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.util.Log;
import android.view.View;
import com.meizu.cloud.app.block.structitem.GameBacktopItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.widget.BackTopView;

public class GameBacktopLayout extends AbsBlockLayout<GameBacktopItem> {
    BackTopView mBackTopView;

    public GameBacktopLayout(Context context, GameBacktopItem item) {
        super(context, item);
    }

    public View createView(Context context, GameBacktopItem item) {
        this.mBackTopView = new BackTopView(context);
        Log.i("tan", "创建了backtopview");
        return this.mBackTopView;
    }

    public void updateView(Context context, GameBacktopItem item, t viewController, int position) {
        Log.i("tan", "执行了backtopview的update");
    }

    protected void updateLayoutMargins(Context context, GameBacktopItem item) {
    }
}
