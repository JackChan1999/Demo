package com.qq.demo.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qq.demo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/4/15 17:44
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class LoadMoreHolder extends BaseHolder {

    @Bind(R.id.item_loadmore_container_loading)
    LinearLayout mContainerLoading;

    @Bind(R.id.item_loadmore_container_retry)
    LinearLayout mContainerRetry;

    public static final int STATE_LOADING = 0;
    public static final int STATE_RETRY = 1;
    public static final int STATE_NONE = 2;

    public LoadMoreHolder(Context context, View itemView, ViewGroup parent, int position) {
        super(context, itemView, parent, position);
        ButterKnife.bind(this,itemView);
    }

    public void refreshHolderView(Integer state) {
        mContainerLoading.setVisibility(View.GONE);
        mContainerRetry.setVisibility(View.GONE);
        switch (state) {
            case STATE_LOADING:
                mContainerLoading.setVisibility(View.VISIBLE);
                break;
            case STATE_RETRY:
                mContainerRetry.setVisibility(View.VISIBLE);
                break;
            case STATE_NONE:

                break;
        }
    }
}
