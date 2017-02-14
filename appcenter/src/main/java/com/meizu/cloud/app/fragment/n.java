package com.meizu.cloud.app.fragment;

import a.a.a.c;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.s;
import com.meizu.cloud.app.block.parseutil.JsonParserUtils;
import com.meizu.cloud.app.block.requestitem.SubpagePageConfigsInfo;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structitem.AdAppBigItem;
import com.meizu.cloud.app.block.structitem.AdBigItem;
import com.meizu.cloud.app.block.structitem.GameQualityItem;
import com.meizu.cloud.app.block.structitem.RecommendAppItem;
import com.meizu.cloud.app.block.structitem.Row1Col2AppItem;
import com.meizu.cloud.app.block.structitem.Row1Col2AppVerItem;
import com.meizu.cloud.app.block.structitem.Row1Col3AppVerItem;
import com.meizu.cloud.app.block.structitem.Row1Col4AppVerItem;
import com.meizu.cloud.app.block.structitem.Row2Col2AppVerItem;
import com.meizu.cloud.app.block.structitem.SingleRowAppItem;
import com.meizu.cloud.app.block.structlayout.AbsBlockLayout.OnChildClickListener;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.w;
import com.meizu.cloud.app.downlad.f.b;
import com.meizu.cloud.app.downlad.f.d;
import com.meizu.cloud.app.downlad.f.e;
import com.meizu.cloud.app.downlad.f.g;
import com.meizu.cloud.app.downlad.f.i;
import com.meizu.cloud.app.downlad.f.m;
import com.meizu.cloud.app.request.FastJsonParseRequest;
import com.meizu.cloud.app.request.ParseListener;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.BlockLoadMoreResult;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.utils.u;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.b.a;
import com.meizu.cloud.base.b.f;
import com.meizu.cloud.statistics.UxipPageSourceInfo;
import flyme.support.v7.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class n extends com.meizu.cloud.base.b.n<BlockLoadMoreResult<AbsBlockItem>> implements OnChildClickListener, b, d, e, g, i, ParseListener<BlockLoadMoreResult<AbsBlockItem>> {
    protected String a;
    protected String b;
    protected int c = 0;
    protected Context d;
    protected t e;
    protected SubpagePageConfigsInfo f;
    protected MenuItem g;
    protected Drawable h;
    final int i = 500;
    final int j = 500;
    protected Drawable k;
    protected Drawable l;
    protected int m;
    private int n;
    private String o;
    private UxipPageSourceInfo p;

    public /* synthetic */ Object onParseResponse(String str) {
        return a(str);
    }

    protected /* synthetic */ boolean onResponse(Object obj) {
        return a((BlockLoadMoreResult) obj);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.d = getActivity().getApplicationContext();
        this.n = u.a(getActivity());
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.a = bundle.getString("url", "");
            this.b = bundle.getString("title_name", "");
            if (bundle.containsKey("uxip_page_source_info")) {
                this.p = (UxipPageSourceInfo) bundle.getParcelable("uxip_page_source_info");
            } else if (bundle.containsKey("source_page")) {
                this.o = bundle.getString("source_page", "");
            }
            bundle.putInt("extra_padding_bottom", (int) getResources().getDimension(a.d.new_essential_page_padding_bottom));
        }
        this.mPageName = "Subpage_" + this.b;
        this.mPageInfo[1] = 27;
        this.e = new t(getActivity(), new com.meizu.cloud.app.core.u());
        this.e.a(this.mPageName);
        this.e.a(this.mPageInfo);
        com.meizu.cloud.app.downlad.d.a(getActivity()).a((m) this, new com.meizu.cloud.app.downlad.g());
        c.a().a((Object) this);
    }

    protected void setupActionBar() {
        super.setupActionBar();
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.a(this.b);
        }
        a(true);
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        showProgress();
    }

    public void onPause() {
        super.onPause();
        a(false);
    }

    public void onDestroy() {
        super.onDestroy();
        com.meizu.cloud.app.downlad.d.a(this.d).b((m) this);
        c.a().c(this);
    }

    protected void onRealPageStart() {
        super.onRealPageStart();
        com.meizu.cloud.statistics.b.a().a(this.mPageName);
    }

    protected void onRealPageStop() {
        super.onRealPageStop();
        com.meizu.cloud.statistics.b.a().a(this.mPageName, a());
    }

    public Map<String, String> a() {
        Map<String, String> wdmParamsMap = new HashMap();
        wdmParamsMap.put("count", String.valueOf(this.m));
        String str = "sum";
        int size = (getRecyclerViewAdapter() == null || getRecyclerViewAdapter().e() == null) ? 0 : getRecyclerViewAdapter().e().size();
        wdmParamsMap.put(str, String.valueOf(size));
        if (this.p != null) {
            wdmParamsMap.put("source_page", this.p.f);
            wdmParamsMap.put("source_block_id", String.valueOf(this.p.b));
            wdmParamsMap.put("source_block_name", this.p.c);
            wdmParamsMap.put("source_block_type", this.p.a);
            if (this.p.g > 0) {
                wdmParamsMap.put("source_block_profile_id", String.valueOf(this.p.g));
            }
            wdmParamsMap.put("source_pos", String.valueOf(this.p.d));
            if (this.p.e > 0) {
                wdmParamsMap.put("source_hor_pos", String.valueOf(this.p.e));
            }
        } else if (!TextUtils.isEmpty(this.o)) {
            wdmParamsMap.put("source_page", this.o);
        }
        return wdmParamsMap;
    }

    protected void onRequestData() {
        List requestParams = new ArrayList();
        requestParams.add(new com.meizu.volley.b.a("start", String.valueOf(this.c)));
        requestParams.add(new com.meizu.volley.b.a("max", String.valueOf(5)));
        this.mLoadRequest = new FastJsonParseRequest(RequestConstants.APP_CENTER_HOST + this.a, 0, requestParams, (ParseListener) this, new f.b(this), new f.a(this));
        this.mLoadRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.d));
        com.meizu.volley.b.a(this.d).a().a(this.mLoadRequest);
    }

    public BlockLoadMoreResult<AbsBlockItem> a(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject.containsKey("code")) {
                BlockLoadMoreResult<AbsBlockItem> result = new BlockLoadMoreResult();
                int code = jsonObject.getIntValue("code");
                if (jsonObject.containsKey("message")) {
                    result.mErrorMsg = jsonObject.getString("message");
                }
                if (code != 200) {
                    return null;
                }
                if (!jsonObject.containsKey("value")) {
                    return result;
                }
                JSONObject result_value = jsonObject.getJSONObject("value");
                if (result_value == null) {
                    return null;
                }
                if (result_value.containsKey("more")) {
                    result.mNeedLoadMore = result_value.getBooleanValue("more");
                }
                if (result_value.containsKey("blocks")) {
                    JSONArray blockJsonArray = result_value.getJSONArray("blocks");
                    ArrayList<AbsBlockItem> blocks = JsonParserUtils.parseSubPageBlockList(this.d, blockJsonArray);
                    if (blocks != null) {
                        result.mData.addAll(blocks);
                        result.mResultCount = blockJsonArray.size();
                    }
                }
                if (this.mbInitLoad || !result_value.containsKey("page_detail")) {
                    return result;
                }
                result.mSubpageConfigInfo = a(this.d, result_value.getJSONObject("page_detail"));
                if (result.mData == null || !(result.mData.get(0) instanceof AdBigItem)) {
                    return result;
                }
                AdBigItem adBigItem = (AdBigItem) result.mData.get(0);
                if (adBigItem.mAdBigStructItem == null) {
                    return result;
                }
                result.mSubpageConfigInfo.icon_url = adBigItem.mAdBigStructItem.img_url;
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected boolean a(BlockLoadMoreResult<AbsBlockItem> data) {
        if (data == null || getRecyclerViewAdapter() == null) {
            return false;
        }
        this.m++;
        this.c += data.mResultCount;
        this.mbMore = data.mNeedLoadMore;
        com.meizu.cloud.app.a.g baseNewEssentialAdapter = (com.meizu.cloud.app.a.g) getRecyclerViewAdapter();
        if (data.mSubpageConfigInfo != null) {
            this.f = data.mSubpageConfigInfo;
            b();
            c();
            this.e.a(this.f);
        }
        baseNewEssentialAdapter.b(data.mData);
        return true;
    }

    protected void onErrorResponse(s error) {
        if (!this.mbInitLoad) {
            showEmptyView(getEmptyTextString(), null, new OnClickListener(this) {
                final /* synthetic */ n a;

                {
                    this.a = r1;
                }

                public void onClick(View v) {
                    this.a.resumeLoad();
                    this.a.hideEmptyView();
                    this.a.showProgress();
                }
            });
        }
    }

    protected void onLoadFinished() {
        super.onLoadFinished();
        hideFooter();
        hideProgress();
    }

    protected void a(com.meizu.cloud.app.downlad.e wrapper, boolean isInstalled) {
        if (wrapper != null && wrapper.i() > 0 && getRecyclerView() != null && getRecyclerView().getLayoutManager() != null && getRecyclerViewAdapter() != null && getRecyclerViewAdapter().e() != null && this.e != null && !wrapper.j().d()) {
            View view;
            LinearLayoutManager layoutManager = (LinearLayoutManager) getRecyclerView().getLayoutManager();
            int firstVisiblePos = layoutManager.findFirstVisibleItemPosition();
            int lastVisiblePos = layoutManager.findLastVisibleItemPosition();
            List<View> sameTagView = null;
            String packageName = null;
            int i = firstVisiblePos;
            while (i <= lastVisiblePos) {
                if (getRecyclerViewAdapter().e().size() > i) {
                    AppStructItem appStructItem = a((AbsBlockItem) getRecyclerViewAdapter().e().get(i), wrapper.i(), "");
                    if (appStructItem != null) {
                        packageName = appStructItem.package_name;
                        view = getRecyclerView().findViewWithTag(packageName);
                        if (view != null) {
                            if (sameTagView == null) {
                                sameTagView = new ArrayList();
                            }
                            if (view instanceof LinearLayout) {
                                view.setTag(null);
                                sameTagView.add(view);
                                CirProButton btn = (CirProButton) view.findViewWithTag(Integer.valueOf(appStructItem.id));
                                if (isInstalled) {
                                    w displayConfig = new w();
                                    displayConfig.a = a.c.btn_default;
                                    displayConfig.c = 17170443;
                                    displayConfig.b = -1;
                                    displayConfig.d = a.c.btn_operation_downloading_text;
                                    displayConfig.e = 17170443;
                                    displayConfig.f = 17170443;
                                    this.e.a(displayConfig);
                                    this.e.a(wrapper, btn);
                                    this.e.a(null);
                                } else {
                                    this.e.a(wrapper, btn);
                                }
                            } else if (view instanceof CirProButton) {
                                view.setTag(null);
                                sameTagView.add(view);
                                this.e.a(wrapper, (CirProButton) view);
                            }
                        } else {
                            getRecyclerViewAdapter().notifyDataSetChanged();
                        }
                    }
                    i++;
                } else {
                    return;
                }
            }
            if (!TextUtils.isEmpty(packageName) && sameTagView != null && sameTagView.size() > 0) {
                for (View view2 : sameTagView) {
                    if (view2 != null) {
                        view2.setTag(packageName);
                    }
                }
                sameTagView.clear();
            }
        }
    }

    public void b(String apkName) {
        if (!TextUtils.isEmpty(apkName) && getRecyclerView() != null && getRecyclerView().getLayoutManager() != null && getRecyclerViewAdapter() != null && getRecyclerViewAdapter().e() != null && this.e != null) {
            View view;
            LinearLayoutManager layoutManager = (LinearLayoutManager) getRecyclerView().getLayoutManager();
            int firstVisiblePos = layoutManager.findFirstVisibleItemPosition();
            int lastVisiblePos = layoutManager.findLastVisibleItemPosition();
            List<View> sameTagView = null;
            int i = firstVisiblePos;
            while (i <= lastVisiblePos) {
                if (getRecyclerViewAdapter().e().size() > i) {
                    t.a appStructItem = a((AbsBlockItem) getRecyclerViewAdapter().e().get(i), 0, apkName);
                    if (appStructItem != null) {
                        view = getRecyclerView().findViewWithTag(appStructItem.package_name);
                        if (view != null) {
                            CirProButton btn = null;
                            if (sameTagView == null) {
                                sameTagView = new ArrayList();
                            }
                            if (view instanceof LinearLayout) {
                                view.setTag(null);
                                sameTagView.add(view);
                                btn = (CirProButton) view.findViewWithTag(Integer.valueOf(appStructItem.id));
                            } else if (view instanceof CirProButton) {
                                view.setTag(null);
                                sameTagView.add(view);
                                btn = (CirProButton) view;
                            }
                            this.e.a(appStructItem, null, true, btn);
                        } else {
                            getRecyclerViewAdapter().notifyDataSetChanged();
                        }
                    }
                    i++;
                } else {
                    return;
                }
            }
            if (sameTagView != null && sameTagView.size() > 0) {
                for (View view2 : sameTagView) {
                    if (view2 != null) {
                        view2.setTag(apkName);
                    }
                }
                sameTagView.clear();
            }
        }
    }

    private AppStructItem a(AbsBlockItem item, int id, String apkName) {
        if (item != null) {
            if (item instanceof SingleRowAppItem) {
                SingleRowAppItem singleRowAppItem = (SingleRowAppItem) item;
                if (a(singleRowAppItem.app, id, apkName)) {
                    return singleRowAppItem.app;
                }
            } else if (item instanceof RecommendAppItem) {
                RecommendAppItem recommendAppItem = (RecommendAppItem) item;
                if (a(recommendAppItem.app, id, apkName)) {
                    return recommendAppItem.app;
                }
            } else if (item instanceof Row1Col2AppItem) {
                Row1Col2AppItem row1Col2AppItem = (Row1Col2AppItem) item;
                if (a(row1Col2AppItem.app1, id, apkName)) {
                    return row1Col2AppItem.app1;
                }
                if (a(row1Col2AppItem.app2, id, apkName)) {
                    return row1Col2AppItem.app2;
                }
            } else if (item instanceof AdAppBigItem) {
                AdAppBigItem adAppBigItem = (AdAppBigItem) item;
                if (a(adAppBigItem.mAppAdBigStructItem, id, apkName)) {
                    return adAppBigItem.mAppAdBigStructItem;
                }
            } else if (item instanceof Row1Col2AppVerItem) {
                Row1Col2AppVerItem row1Col2AppVerItem = (Row1Col2AppVerItem) item;
                if (a(row1Col2AppVerItem.mAppStructItem1, id, apkName)) {
                    return row1Col2AppVerItem.mAppStructItem1;
                }
                if (a(row1Col2AppVerItem.mAppStructItem2, id, apkName)) {
                    return row1Col2AppVerItem.mAppStructItem2;
                }
            } else if (item instanceof Row1Col3AppVerItem) {
                Row1Col3AppVerItem row1Col3AppVerItem = (Row1Col3AppVerItem) item;
                if (a(row1Col3AppVerItem.mAppStructItem1, id, apkName)) {
                    return row1Col3AppVerItem.mAppStructItem1;
                }
                if (a(row1Col3AppVerItem.mAppStructItem2, id, apkName)) {
                    return row1Col3AppVerItem.mAppStructItem2;
                }
                if (a(row1Col3AppVerItem.mAppStructItem3, id, apkName)) {
                    return row1Col3AppVerItem.mAppStructItem3;
                }
            } else if (item instanceof Row1Col4AppVerItem) {
                Row1Col4AppVerItem row1Col4AppVerItem = (Row1Col4AppVerItem) item;
                if (a(row1Col4AppVerItem.mAppStructItem1, id, apkName)) {
                    return row1Col4AppVerItem.mAppStructItem1;
                }
                if (a(row1Col4AppVerItem.mAppStructItem2, id, apkName)) {
                    return row1Col4AppVerItem.mAppStructItem2;
                }
                if (a(row1Col4AppVerItem.mAppStructItem3, id, apkName)) {
                    return row1Col4AppVerItem.mAppStructItem3;
                }
                if (a(row1Col4AppVerItem.mAppStructItem4, id, apkName)) {
                    return row1Col4AppVerItem.mAppStructItem4;
                }
            } else if (item instanceof GameQualityItem) {
                GameQualityItem gameQualityItem = (GameQualityItem) item;
                if (a(gameQualityItem.mGameQualityStructItem, id, apkName)) {
                    return gameQualityItem.mGameQualityStructItem;
                }
            } else if (item instanceof Row2Col2AppVerItem) {
                Row2Col2AppVerItem row2Col2AppVerItem = (Row2Col2AppVerItem) item;
                if (a(row2Col2AppVerItem.mAppStructItem1, id, apkName)) {
                    return row2Col2AppVerItem.mAppStructItem1;
                }
                if (a(row2Col2AppVerItem.mAppStructItem2, id, apkName)) {
                    return row2Col2AppVerItem.mAppStructItem2;
                }
                if (a(row2Col2AppVerItem.mAppStructItem3, id, apkName)) {
                    return row2Col2AppVerItem.mAppStructItem3;
                }
                if (a(row2Col2AppVerItem.mAppStructItem4, id, apkName)) {
                    return row2Col2AppVerItem.mAppStructItem4;
                }
            }
        }
        return null;
    }

    private boolean a(AppStructItem item, int id, String apkName) {
        if (item == null) {
            return false;
        }
        if (id > 0) {
            if (item.id == id) {
                return true;
            }
            return false;
        } else if (TextUtils.isEmpty(apkName)) {
            return false;
        } else {
            return item.package_name.equals(apkName);
        }
    }

    public void onScrollEnd() {
        if (this.mbMore) {
            loadData();
            showFooter();
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        this.g = menu.findItem(a.f.search_menu);
        this.h = menu.findItem(a.f.search_menu).getIcon();
    }

    public void b() {
        if (this.f != null) {
            w displayConfig = new w();
            displayConfig.a = this.f.btn_bg_color;
            displayConfig.b = -1;
            displayConfig.c = this.f.btn_title_color;
            if (this.f.bg_color == 0) {
                displayConfig.d = this.f.btn_bg_color;
            } else {
                displayConfig.d = this.f.btn_title_color;
            }
            displayConfig.e = this.f.btn_bg_color;
            displayConfig.f = this.f.btn_bg_color;
            displayConfig.a(true);
            this.e.a(displayConfig);
        }
    }

    protected void c() {
        if (this.f != null) {
            if (this.f.status_bar_icon_color == -1) {
                u.a(getActivity(), false);
            } else if (this.f.status_bar_icon_color == -16777216) {
                u.a(getActivity(), true);
            } else {
                u.a(getActivity(), true);
            }
            Window window = getActivity().getWindow();
            if (window != null) {
                window.setBackgroundDrawable(u.a(getActivity(), this.f.bg_color));
            }
            int titleColor = this.f.top_bar_title_color;
            u.a(getActivity(), titleColor);
            Drawable icon = u.b(getActivity());
            if (icon != null) {
                icon.setColorFilter(titleColor, Mode.MULTIPLY);
            }
            a(true);
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                Drawable transitionDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(getResources().getColor(a.c.theme_color)), u.a(this.f.top_bar_color, 92)});
                actionBar.a(transitionDrawable);
                transitionDrawable.startTransition(500);
            }
            d();
        }
    }

    public void a(boolean isAddFilter) {
        if (getActivity() != null && this.f != null && this.h != null && this.g != null) {
            int titleColor = this.f.top_bar_title_color;
            if (isAddFilter) {
                this.h.setColorFilter(titleColor, Mode.MULTIPLY);
            } else {
                this.h.setColorFilter(this.n, Mode.MULTIPLY);
                if (this.g != null) {
                    this.g.setIcon(this.h);
                }
            }
            getActivity().invalidateOptionsMenu();
        }
    }

    public SubpagePageConfigsInfo a(Context context, JSONObject configInfo) {
        try {
            SubpagePageConfigsInfo subpagePageConfigsInfo = new SubpagePageConfigsInfo();
            if (configInfo.containsKey("bg_color")) {
                subpagePageConfigsInfo.bg_color = Color.parseColor(configInfo.getString("bg_color"));
            } else {
                subpagePageConfigsInfo.bg_color = context.getResources().getColor(a.c.activity_background_color);
            }
            if (configInfo.containsKey("btn_bg_color")) {
                subpagePageConfigsInfo.btn_bg_color = Color.parseColor(configInfo.getString("btn_bg_color"));
            } else {
                subpagePageConfigsInfo.btn_bg_color = context.getResources().getColor(a.c.theme_color);
            }
            if (configInfo.containsKey("btn_title_color")) {
                subpagePageConfigsInfo.btn_title_color = Color.parseColor(configInfo.getString("btn_title_color"));
            } else {
                subpagePageConfigsInfo.btn_title_color = context.getResources().getColor(a.c.theme_color);
            }
            if (configInfo.containsKey("des_color")) {
                subpagePageConfigsInfo.des_color = Color.parseColor(configInfo.getString("des_color"));
            } else {
                subpagePageConfigsInfo.des_color = context.getResources().getColor(17170444);
            }
            if (configInfo.containsKey("line_color")) {
                subpagePageConfigsInfo.line_color = Color.parseColor(configInfo.getString("line_color"));
            } else {
                subpagePageConfigsInfo.line_color = Color.parseColor("#4D000000");
            }
            if (configInfo.containsKey("status_bar_icon_color")) {
                subpagePageConfigsInfo.status_bar_icon_color = Color.parseColor(configInfo.getString("status_bar_icon_color"));
            } else {
                subpagePageConfigsInfo.status_bar_icon_color = subpagePageConfigsInfo.bg_color;
            }
            if (configInfo.containsKey("top_bar_color")) {
                subpagePageConfigsInfo.top_bar_color = Color.parseColor(configInfo.getString("top_bar_color"));
            } else {
                subpagePageConfigsInfo.top_bar_color = context.getResources().getColor(a.c.title_color);
            }
            if (configInfo.containsKey("top_bar_title_color")) {
                subpagePageConfigsInfo.top_bar_title_color = Color.parseColor(configInfo.getString("top_bar_title_color"));
            } else {
                subpagePageConfigsInfo.top_bar_title_color = context.getResources().getColor(a.c.title_color);
            }
            int r = Color.red(subpagePageConfigsInfo.des_color);
            int g = Color.green(subpagePageConfigsInfo.des_color);
            int b = Color.blue(subpagePageConfigsInfo.des_color);
            subpagePageConfigsInfo.recom_des_common = Color.argb(127, r, g, b);
            subpagePageConfigsInfo.divider_line_color = Color.argb(51, r, g, b);
            return subpagePageConfigsInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void d() {
        new Thread(this) {
            final /* synthetic */ n a;

            {
                this.a = r1;
            }

            public void run() {
                if (this.a.getActivity() != null && this.a.mRunning && this.a.l == null && this.a.f != null && !TextUtils.isEmpty(this.a.f.icon_url)) {
                    Drawable[] drawables = this.a.a(h.a(this.a.getActivity(), this.a.f.icon_url));
                    if (drawables != null) {
                        this.a.k = drawables[0];
                        this.a.l = drawables[1];
                    }
                    this.a.runOnUi(new Runnable(this) {
                        final /* synthetic */ AnonymousClass2 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            if (this.a.a.getActivity() != null) {
                                this.a.a.a(this.a.a.k);
                                this.a.a.b(this.a.a.l);
                            }
                        }
                    });
                }
            }
        }.start();
    }

    private Drawable[] a(Bitmap bitmap) {
        if (bitmap == null || getActivity() == null) {
            return null;
        }
        int coverColor = 0;
        int shadowColor = 0;
        if (this.f != null) {
            coverColor = this.f.top_bar_color;
            shadowColor = this.f.bg_color;
        }
        int actionBarHeight = com.meizu.cloud.app.utils.d.f(getActivity());
        int headerHeight = getResources().getDimensionPixelSize(a.d.special_header_image_layout_height);
        Bitmap blurBitmap = u.a(bitmap, coverColor);
        int actionBarBmWidth = blurBitmap.getWidth();
        int actionBarBmHeight = (blurBitmap.getHeight() * actionBarHeight) / (actionBarHeight + headerHeight);
        BitmapDrawable actionBarDrawable = new BitmapDrawable(Bitmap.createBitmap(blurBitmap, 0, 0, actionBarBmWidth, actionBarBmHeight));
        Bitmap bmHeader = Bitmap.createBitmap(blurBitmap, 0, actionBarBmHeight, actionBarBmWidth, blurBitmap.getHeight() - actionBarBmHeight);
        u.a(bmHeader, new Rect(0, 0, bmHeader.getWidth(), bmHeader.getHeight()), new int[]{16777215 & shadowColor, 184549375 & shadowColor, 872415231 & shadowColor, 1728053247 & shadowColor, -1711276033 & shadowColor, -855638017 & shadowColor, shadowColor, shadowColor}, Orientation.TOP_BOTTOM);
        BitmapDrawable headerDrawable = new BitmapDrawable(bmHeader);
        bitmap.recycle();
        blurBitmap.recycle();
        return new Drawable[]{actionBarDrawable, headerDrawable};
    }

    private void a(Drawable drawable) {
        if (drawable != null && this.mRunning) {
            int startColor = getResources().getColor(a.c.theme_color);
            if (this.f != null) {
                startColor = this.f.top_bar_color;
            }
            Drawable transitionDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(startColor), drawable});
            getActionBar().a(transitionDrawable);
            transitionDrawable.startTransition(500);
        }
    }

    private void b(Drawable drawable) {
        if (drawable != null && getRecyclerView() != null && getRecyclerView().getLayoutManager() != null) {
            com.meizu.cloud.app.a.g adapter = (com.meizu.cloud.app.a.g) getRecyclerViewAdapter();
            View firstView = getRecyclerView().getChildAt(0);
            if (firstView != null) {
                if (this.f != null) {
                    this.f.default_ad_bg = firstView.getBackground();
                }
                firstView.setBackground(drawable);
                if (this.f != null) {
                    this.f.blur_ad_bg = drawable;
                }
                this.e.a(this.f);
                if (((LinearLayoutManager) getRecyclerView().getLayoutManager()).findFirstVisibleItemPosition() == 0) {
                    TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(0), drawable});
                    View headerView = getRecyclerView().findViewWithTag("header_layout");
                    if (headerView != null) {
                        headerView.setBackground(transitionDrawable);
                    }
                    transitionDrawable.startTransition(500);
                    return;
                }
                adapter.g();
            }
        }
    }
}
