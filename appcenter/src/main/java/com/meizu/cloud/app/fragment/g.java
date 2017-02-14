package com.meizu.cloud.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.n;
import android.support.v4.view.ad;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import com.android.volley.s;
import com.meizu.cloud.app.request.model.PageInfo;
import com.meizu.cloud.app.request.model.PageInfo.PageType;
import com.meizu.cloud.app.request.model.RankPageInfo.RankPageType;
import com.meizu.cloud.app.request.structitem.PropertyTag;
import com.meizu.cloud.base.b.l;
import java.util.ArrayList;
import java.util.List;

public abstract class g extends l {
    private b<PageInfo> a;

    public class a extends n {
        final /* synthetic */ g a;

        public a(g gVar, android.support.v4.app.l fm) {
            this.a = gVar;
            super(fm);
        }

        public Fragment a(int position) {
            Fragment fragment = this.a.b(position);
            Bundle bundle = new Bundle();
            if (!(this.a.a == null || this.a.a.b == null || this.a.a.a == null)) {
                bundle.putString("url", this.a.e() + ((PageInfo) this.a.a.a.get(position)).url);
                bundle.putInt("extra_padding_top", this.a.g());
                bundle.putString("pager_name", this.a.getArguments().getString("title_name", "") + "-" + ((String) this.a.a.b.get(position)));
                bundle.putString("rank_page_type", RankPageType.RECOMMEND.getType());
                bundle.putInt("source_page_id", 22);
                if (this.a.getArguments() != null && this.a.getArguments().containsKey("uxip_page_source_info")) {
                    bundle.putParcelable("uxip_page_source_info", this.a.getArguments().getParcelable("uxip_page_source_info"));
                }
            }
            fragment.setArguments(bundle);
            return fragment;
        }

        public int b() {
            if (this.a.a == null || this.a.a.b == null) {
                return 0;
            }
            return this.a.a.b.size();
        }
    }

    private class b<T> {
        public List<T> a;
        public List<String> b;
        final /* synthetic */ g c;

        private b(g gVar) {
            this.c = gVar;
        }
    }

    public abstract Fragment b(int i);

    public abstract String e();

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

    protected boolean loadData() {
        this.a = a();
        if (this.a == null) {
            return false;
        }
        a(this.a.b);
        this.mbInitLoad = true;
        this.mbLoading = false;
        this.mbMore = false;
        return true;
    }

    protected void a(List<String> tabTitles) {
        if (tabTitles == null) {
            a(null);
        } else {
            a((String[]) tabTitles.toArray(new String[tabTitles.size()]));
        }
    }

    protected void onRequestData() {
    }

    protected boolean onResponse(Object response) {
        return false;
    }

    protected void onErrorResponse(s error) {
    }

    public b a() {
        List<String> mTitiles = null;
        List<PageInfo> mPageInfos = null;
        b<PageInfo> holder = new b();
        if (getArguments().containsKey("category_tag_struct")) {
            List<PropertyTag> property_tags = getArguments().getParcelableArrayList("category_tag_struct");
            int tagId = getArguments().getInt("category_tag_id", 0);
            if (property_tags != null) {
                mTitiles = new ArrayList();
                mPageInfos = new ArrayList();
                for (int i = 0; i < property_tags.size(); i++) {
                    PropertyTag tag = (PropertyTag) property_tags.get(i);
                    if (!(tag == null || TextUtils.isEmpty(tag.name))) {
                        mTitiles.add(tag.name);
                        PageInfo pageInfo = new PageInfo();
                        pageInfo.name = tag.name;
                        pageInfo.url = tag.url;
                        pageInfo.type = PageType.RANK.getType();
                        pageInfo.page_type = "category";
                        mPageInfos.add(pageInfo);
                        if (tagId == tag.id) {
                            this.i = i;
                        }
                    }
                }
            }
        }
        if (mTitiles == null || mPageInfos == null || mTitiles.size() != mPageInfos.size()) {
            return null;
        }
        holder.a = mPageInfos;
        holder.b = mTitiles;
        return holder;
    }

    public ad b() {
        return new a(this, getChildFragmentManager());
    }
}
