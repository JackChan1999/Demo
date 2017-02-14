package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.meizu.cloud.app.block.requestitem.AppAdStructItem;
import com.meizu.cloud.app.block.requestitem.GameQualityStructItem.GameLayout;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structitem.TitleItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.b.a.f;

public abstract class AbsBlockLayout<T> {
    protected View mDivider;
    protected OnChildClickListener mOnChildClickListener;
    protected View mView;

    public interface OnChildClickListener {
        void onBlockExposure(AbsBlockItem absBlockItem, int i);

        void onCancelDownload(AppStructItem appStructItem);

        void onClickAd(AppAdStructItem appAdStructItem, int i, int i2);

        void onClickApp(AppStructItem appStructItem, int i, int i2);

        void onClickConts(AbstractStrcutItem abstractStrcutItem, String str, int i, int i2);

        void onDownload(AppStructItem appStructItem, View view, int i, int i2);

        void onMore(TitleItem titleItem);

        void onTabClick(GameLayout gameLayout, AppStructItem appStructItem);
    }

    public abstract View createView(Context context, T t);

    protected abstract void updateLayoutMargins(Context context, T t);

    public abstract void updateView(Context context, T t, t tVar, int i);

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.mOnChildClickListener = onChildClickListener;
    }

    public AbsBlockLayout(Context context, T t) {
    }

    public View getView() {
        return this.mView;
    }

    protected View inflate(Context context, int layout) {
        this.mView = LayoutInflater.from(context).inflate(layout, null);
        this.mDivider = this.mView.findViewById(f.divider);
        return this.mView;
    }

    protected View inflate(Context context, int layout, ViewGroup root, boolean attachToRoot) {
        this.mView = LayoutInflater.from(context).inflate(layout, root, attachToRoot);
        this.mDivider = this.mView.findViewById(f.divider);
        return this.mView;
    }

    protected void setShowDivider(boolean v) {
        if (v) {
            this.mDivider.setVisibility(0);
        } else {
            this.mDivider.setVisibility(8);
        }
    }

    protected static void checkShowDivider(AbsBlockLayout layout, AbsBlockItem item) {
        layout.setShowDivider(item.showDivider);
    }
}
