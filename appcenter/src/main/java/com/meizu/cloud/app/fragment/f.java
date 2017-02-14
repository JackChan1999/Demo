package com.meizu.cloud.app.fragment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import com.meizu.cloud.app.a.b;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structlayout.AbsBlockLayout.OnChildClickListener;
import com.meizu.cloud.app.request.structitem.CategoryStructItem;
import com.meizu.cloud.base.a.d;
import com.meizu.cloud.base.b.h;
import com.meizu.cloud.base.b.h.a;
import com.meizu.cloud.statistics.c;

public abstract class f extends h<AbsBlockItem> implements OnChildClickListener {
    protected void setupActionBar() {
        ActionBar actionBar = getActionBar();
        Bundle bundle = getArguments();
        if (bundle != null) {
            CharSequence title = bundle.getString("title_name", "");
            if (!TextUtils.isEmpty(title)) {
                super.setupActionBar();
                actionBar.a(title);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPageName = "category";
        registerPagerScrollStateListener();
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterPagerScrollStateListener();
    }

    public d createRecyclerAdapter() {
        return new b(getActivity(), this);
    }

    public String a() {
        if (getArguments().containsKey("url")) {
            return getArguments().getString("url");
        }
        return "";
    }

    protected a<AbsBlockItem> a(String json) {
        return null;
    }

    protected void a(CategoryStructItem itemData, int tagName, int position, int horPosition) {
        if ("conts_row1_col2".equals(itemData.block_type) || "tags".equals(itemData.block_type)) {
            com.meizu.cloud.statistics.b.a().a("category_block", "category", c.a(itemData, position + 1, horPosition + 1));
        } else if ("category_set".equals(itemData.block_type) || "ranks".equals(itemData.block_type)) {
            com.meizu.cloud.statistics.b.a().a("category_item", "category", c.a(itemData, position + 1, horPosition + 1));
        }
    }

    protected void onRealPageStart() {
        com.meizu.cloud.statistics.b.a().a(this.mPageName);
    }

    protected void onRealPageStop() {
        com.meizu.cloud.statistics.b.a().a(this.mPageName, null);
    }
}
