package com.meizu.flyme.appcenter.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import com.meizu.cloud.app.a.h;
import com.meizu.cloud.app.core.k;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.fragment.p;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.app.request.structitem.SearchHotItem;
import com.meizu.cloud.app.widget.RankAppItemViewV2;
import com.meizu.cloud.statistics.b;
import com.meizu.cloud.statistics.c;
import com.meizu.flyme.appcenter.a.d;
import com.meizu.mstore.R;
import java.util.ArrayList;
import java.util.List;

public class AppSearchFragment extends p {
    public static final String EXTRA_MIME = "ExtraMime";
    public static final String EXTRA_SEARCH = "ExtraSearch";

    protected class a extends a implements OnClickListener {
        final /* synthetic */ AppSearchFragment d;

        public a(AppSearchFragment appSearchFragment, Context context, ArrayList<Object> mItemData) {
            this.d = appSearchFragment;
            super(appSearchFragment, context, mItemData);
        }

        protected View a(View convertView, AppUpdateStructItem appStructItem) {
            if (convertView == null) {
                convertView = new RankAppItemViewV2(this.a, this.d.mViewControler);
            }
            RankAppItemViewV2 appItemView = (RankAppItemViewV2) convertView;
            appItemView.a(appStructItem, 0);
            appItemView.setOnInstallBtnClickListener(new com.meizu.cloud.app.widget.CommonListItemView.a(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void a(AppStructItem appStructItem, View view) {
                    appStructItem.page_info = new int[3];
                    appStructItem.page_info[1] = 9;
                    appStructItem.search_id = this.a.d.getWdmSearchId();
                    this.a.d.mViewControler.a(new k(appStructItem));
                }
            });
            return convertView;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mMimeString = getArguments().getString(EXTRA_MIME, null);
            this.mKey = getArguments().getString(EXTRA_SEARCH, null);
        }
    }

    protected String getSearchHotJson() {
        return RequestManager.getInstance(getActivity()).getSearchHot(getSearchHotUrlV2());
    }

    public void clickKeyGoto(SearchHotItem searchHotItem) {
        if (searchHotItem.blockGotoPageInfo != null) {
            searchHotItem.blockGotoPageInfo.o = 8;
            com.meizu.flyme.appcenter.c.a.a(getActivity(), searchHotItem.blockGotoPageInfo);
            b.a().a("recom_click", p.SEARCH_TAG, c.a(searchHotItem));
        }
    }

    protected void filterSearchHotItem(List<SearchHotItem> list) {
    }

    protected a createTipArrayAdapter() {
        return new a(this, getActivity(), this.mDataSearchTip);
    }

    public h createRankAdapter(FragmentActivity context, t viewController) {
        return new d(context, viewController);
    }

    public Fragment createDetailFragment() {
        return new d();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.list_tip && id >= 0) {
            Object o = this.mAdapterSearchTip.a().get((int) id);
            if (o instanceof AppStructItem) {
                Fragment appInfoPagerFragment = createDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", ((AppStructItem) o).url);
                bundle.putInt("source_page_id", 9);
                bundle.putString("search_id", getWdmSearchId());
                ((AppStructItem) o).click_pos = position + 1;
                if (this.mIsInLenovoResultPage) {
                    bundle.putString("source_page", "searchResultAuto");
                    b.a().a("item", "searchResultAuto", c.a((AppStructItem) o, getWdmSearchId()));
                }
                appInfoPagerFragment.setArguments(bundle);
                com.meizu.cloud.base.b.d.startFragment(getActivity(), appInfoPagerFragment);
                return;
            }
            this.mIsKeyboardLenove = true;
            doSearch((String) o);
        }
    }

    protected void doVioceAction(List<AppUpdateStructItem> searchResult, String action, String key) {
        if ("download".equals(action)) {
            for (AppStructItem appStructItem : searchResult) {
                if (key.equalsIgnoreCase(appStructItem.name)) {
                    Intent searchIntent = new Intent("com.meizu.flyme.appcenter.action.perform");
                    searchIntent.setData(Uri.parse("http://app.meizu.com/apps/public/detail?package_name=" + appStructItem.package_name));
                    searchIntent.putExtra("perform_internal", false);
                    searchIntent.putExtra("result_app_action", action);
                    searchIntent.setComponent(new ComponentName(getActivity().getApplicationContext().getPackageName(), "com.meizu.flyme.appcenter.action.perform.activity"));
                    getActivity().startActivity(searchIntent);
                    return;
                }
            }
        }
    }
}
