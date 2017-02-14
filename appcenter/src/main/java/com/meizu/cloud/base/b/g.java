package com.meizu.cloud.base.b;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.app.widget.LoadDataView;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.app.BaseCommonActivity;

public abstract class g extends o {
    private LoadDataView mLoadDataView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected void initView(View rootView) {
        this.mLoadDataView = (LoadDataView) rootView.findViewById(f.load_data_view);
    }

    public void onDestroy() {
        if (this.mLoadDataView != null) {
            this.mLoadDataView.b();
        }
        super.onDestroy();
    }

    protected void showProgress() {
        if (this.mLoadDataView != null && this.mRunning) {
            this.mLoadDataView.c();
            this.mLoadDataView.a(getString(i.loading_text));
        }
    }

    protected void hideProgress() {
        if (this.mLoadDataView != null && this.mRunning) {
            this.mLoadDataView.b();
        }
    }

    protected void showEmptyView(String text, Drawable icon, OnClickListener listener) {
        if (this.mLoadDataView != null && this.mRunning) {
            this.mLoadDataView.b();
            if (icon == null && listener != null) {
                icon = getResources().getDrawable(e.mz_ic_empty_view_refresh, null);
            }
            this.mLoadDataView.a(text, icon, listener);
            if (getActivity() instanceof BaseCommonActivity) {
                ((BaseCommonActivity) getActivity()).m();
            }
        }
    }

    protected void hideEmptyView() {
        if (this.mLoadDataView != null && this.mRunning) {
            this.mLoadDataView.c();
        }
    }

    protected String getEmptyTextString() {
        if (m.b(getActivity())) {
            return getString(i.server_error);
        }
        return getString(i.network_error);
    }
}
