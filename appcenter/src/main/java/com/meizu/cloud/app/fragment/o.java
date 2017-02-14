package com.meizu.cloud.app.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.meizu.cloud.app.a.h;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structitem.CategoryGridItem;
import com.meizu.cloud.app.core.l;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.u;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.model.BlockResultModel;
import com.meizu.cloud.app.request.model.PageInfo.PageType;
import com.meizu.cloud.app.request.model.RankBlockResultModel;
import com.meizu.cloud.app.request.model.RankPageInfo.RankPageType;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.AdContent;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.app.request.structitem.CategoryStructItem;
import com.meizu.cloud.base.b.c;
import com.meizu.cloud.base.b.h.a;
import com.meizu.cloud.base.b.i.b;
import com.meizu.cloud.statistics.UxipPageSourceInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class o extends c<AppUpdateStructItem> implements b {
    protected boolean a = false;
    protected t b;
    protected boolean c = false;
    protected boolean d = false;
    protected AbsBlockItem e;
    private int k;
    private int l;
    private List<AdContent> m;
    private UxipPageSourceInfo n;

    protected void setupActionBar() {
        Bundle args = getArguments();
        String forwardType = args.getString("forward_type", "index");
        if ("more".equals(forwardType) || "rank".equals(forwardType)) {
            ActionBar actionBar = getActionBar();
            if (args != null) {
                super.setupActionBar();
                actionBar.a(args.getString("title_name", ""));
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mFirstJson = getArguments().getString("json_string");
            if (getArguments().containsKey("wdm_page_name")) {
                this.mPageName = "Page_" + getArguments().getString("wdm_page_name", "");
            } else if (getArguments().containsKey("title_name")) {
                this.mPageName = "Page_" + getArguments().getString("title_name", "");
            } else if (getArguments().containsKey("pager_name")) {
                this.mPageName = "Page_" + getArguments().getString("pager_name", "");
            }
            this.mPageInfo[0] = getArguments().getInt("source_page_id", 0);
        }
        this.b = new t(getActivity(), new u());
        this.mPageInfo[1] = 2;
        this.mPageInfo[2] = c();
        this.b.a(this.mPageName);
        this.b.a(this.mPageInfo);
        l resource = new l();
        resource.c = RankPageType.instance(getArguments().getString("rank_page_type", ""));
        this.b.a(resource);
        if (getArguments().containsKey("uxip_page_source_info")) {
            this.n = (UxipPageSourceInfo) getArguments().getParcelable("uxip_page_source_info");
        }
        registerPagerScrollStateListener();
    }

    protected int c() {
        String url = getArguments().getString("url", "");
        try {
            return Integer.valueOf(url.substring(url.lastIndexOf("/") + 1).trim()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            if ("新游".equals(getArguments().getString("title_name", ""))) {
                return -2;
            }
            return -1;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterPagerScrollStateListener();
    }

    protected a<AppUpdateStructItem> b(String json) {
        ResultModel<JSONObject> resultModel = JSONUtils.parseResultModel(json, new TypeReference<ResultModel<JSONObject>>(this) {
            final /* synthetic */ o a;

            {
                this.a = r1;
            }
        });
        a<AppUpdateStructItem> result = null;
        if (!(resultModel == null || resultModel.getCode() != 200 || resultModel.getValue() == null)) {
            JSONObject jsonObject = (JSONObject) resultModel.getValue();
            if (jsonObject.get("blocks") instanceof JSONArray) {
                JSONArray jsonArray = jsonObject.getJSONArray("blocks");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    if (PageType.RANK.getType().equals(object.getString("type"))) {
                        result = d(object.toJSONString());
                    } else if ("conts_row1_col2".equals(object.getString("type"))) {
                        this.e = a(object.toJSONString(), "conts_row1_col2");
                        if (getRecyclerViewAdapter() instanceof h) {
                            ((h) getRecyclerViewAdapter()).a(this.e);
                        }
                    }
                }
            } else if (jsonObject.get("blocks") instanceof JSONObject) {
                result = d(jsonObject.getJSONObject("blocks").toJSONString());
            } else if (jsonObject.get("data") instanceof JSONArray) {
                result = d(jsonObject.toJSONString());
            }
            if (jsonObject.containsKey("banner")) {
                String bannerUrl = jsonObject.getString("banner");
                if (getRecyclerViewAdapter() instanceof h) {
                    ((h) getRecyclerViewAdapter()).a(bannerUrl);
                }
            }
            if (this.b.c().b) {
                try {
                    this.b.c().d = Typeface.createFromFile("/system/fonts/DINPro-Medium.otf");
                    this.b.c().e = Typeface.createFromFile("/system/fonts/DINPro-Regular.otf");
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    private CategoryGridItem a(String jsonString, String blockType) {
        CategoryGridItem result = new CategoryGridItem();
        result.structItemList = new ArrayList();
        BlockResultModel<JSONObject> blockResultModel = (BlockResultModel) JSONUtils.parseJSONObject(jsonString, new TypeReference<BlockResultModel<JSONObject>>(this) {
            final /* synthetic */ o a;

            {
                this.a = r1;
            }
        });
        for (JSONObject jsonObject : blockResultModel.getData()) {
            CategoryStructItem structItem = (CategoryStructItem) JSONUtils.parseJSONObject(jsonObject.toString(), new TypeReference<CategoryStructItem>(this) {
                final /* synthetic */ o a;

                {
                    this.a = r1;
                }
            });
            structItem.bg = structItem.icon;
            structItem.block_name = blockResultModel.getName();
            structItem.block_type = blockType;
            structItem.block_id = blockResultModel.id;
            structItem.topic_name = structItem.name;
            if (!TextUtils.isEmpty(structItem.url)) {
                try {
                    structItem.topic_id = Integer.parseInt(structItem.url.substring(structItem.url.lastIndexOf("/") + 1));
                } catch (Exception e) {
                }
            }
            result.structItemList.add(structItem);
        }
        return result;
    }

    private a<AppUpdateStructItem> d(String jsonString) {
        RankBlockResultModel<AppUpdateStructItem> blockResultModel = (RankBlockResultModel) JSONUtils.parseJSONObject(jsonString, new TypeReference<RankBlockResultModel<AppUpdateStructItem>>(this) {
            final /* synthetic */ o a;

            {
                this.a = r1;
            }
        });
        a<AppUpdateStructItem> loadResult = new a();
        List<AppUpdateStructItem> structItems = blockResultModel.getData();
        loadResult.c = structItems.size();
        int i = structItems.size() - 1;
        while (i >= 0) {
            com.meizu.cloud.app.core.b.a(getActivity(), (AppUpdateStructItem) structItems.get(i));
            if (this.a && a((AppStructItem) structItems.get(i))) {
                structItems.remove(i);
            }
            i--;
        }
        int addCount = structItems.size();
        a(blockResultModel, structItems, 0);
        this.l += addCount;
        this.k++;
        loadResult.b = structItems;
        loadResult.d = blockResultModel.getMore();
        loadResult.e = blockResultModel.getUrl();
        return loadResult;
    }

    protected a<AppUpdateStructItem> a(String json) {
        ResultModel<RankBlockResultModel<AppUpdateStructItem>> resultModel = JSONUtils.parseResultModel(json, new TypeReference<ResultModel<RankBlockResultModel<AppUpdateStructItem>>>(this) {
            final /* synthetic */ o a;

            {
                this.a = r1;
            }
        });
        if (resultModel == null || resultModel.getCode() != 200 || resultModel.getValue() == null) {
            return null;
        }
        RankBlockResultModel<AppUpdateStructItem> blockResultModel = (RankBlockResultModel) resultModel.getValue();
        a<AppUpdateStructItem> loadResult = new a();
        List<AppUpdateStructItem> structItems = blockResultModel.getData();
        loadResult.c = structItems.size();
        int i = structItems.size() - 1;
        while (i >= 0) {
            com.meizu.cloud.app.core.b.a(getActivity(), (AppUpdateStructItem) structItems.get(i));
            if (this.a && a((AppStructItem) structItems.get(i))) {
                structItems.remove(i);
            }
            i--;
        }
        int addCount = structItems.size();
        a(blockResultModel, structItems, this.l);
        this.l += addCount;
        this.k++;
        loadResult.b = structItems;
        loadResult.d = blockResultModel.getMore();
        loadResult.e = blockResultModel.getUrl();
        return loadResult;
    }

    private void a(RankBlockResultModel<AppUpdateStructItem> blockResultModel, List<AppUpdateStructItem> structItems, int offset) {
        int index;
        int i;
        if (structItems != null) {
            index = offset;
            for (i = 0; i < structItems.size(); i++) {
                index++;
                ((AppUpdateStructItem) structItems.get(i)).index = index;
            }
        }
        if (this.m == null) {
            this.m = new ArrayList();
        }
        if (blockResultModel.ad_contents != null) {
            this.m.addAll(blockResultModel.ad_contents);
        }
        for (i = structItems.size() - 1; i >= 0; i--) {
            index = ((AppUpdateStructItem) structItems.get(i)).index;
            int j = this.m.size() - 1;
            while (j >= 0) {
                if (2 == ((AdContent) this.m.get(j)).type && index == ((AdContent) this.m.get(j)).position) {
                    AppUpdateStructItem appUpdateStructItem = new AppUpdateStructItem();
                    appUpdateStructItem.adContent = (AdContent) this.m.remove(j);
                    appUpdateStructItem.block_name = blockResultModel.getName();
                    appUpdateStructItem.block_type = blockResultModel.getType();
                    appUpdateStructItem.block_id = blockResultModel.id;
                    structItems.add(i, appUpdateStructItem);
                    if (i - 1 >= 0) {
                        ((AppUpdateStructItem) structItems.get(i - 1)).hideDivider = true;
                    }
                } else {
                    j--;
                }
            }
        }
    }

    protected boolean a(AppStructItem appStructItem) {
        if (appStructItem.price == 0.0d && x.d(getActivity()).a(appStructItem.package_name)) {
            com.meizu.cloud.app.core.c compareResult = x.d(getActivity()).a(appStructItem.package_name, appStructItem.version_code);
            if (compareResult == com.meizu.cloud.app.core.c.OPEN || compareResult == com.meizu.cloud.app.core.c.BUILD_IN || compareResult == com.meizu.cloud.app.core.c.DOWNGRADE) {
                return true;
            }
        }
        return false;
    }

    protected void onRealPageStart() {
        com.meizu.cloud.statistics.b.a().a(this.mPageName);
        g();
    }

    protected void onRealPageStop() {
        com.meizu.cloud.statistics.b.a().a(this.mPageName, d());
    }

    public Map<String, String> d() {
        Map<String, String> wdmParamsMap = new HashMap();
        wdmParamsMap.put("count", String.valueOf(this.k));
        wdmParamsMap.put("sum", String.valueOf(e()));
        if (this.n != null) {
            wdmParamsMap.put("source_page", this.n.f);
            wdmParamsMap.put("source_block_id", String.valueOf(this.n.b));
            wdmParamsMap.put("source_block_name", this.n.c);
            wdmParamsMap.put("source_block_type", this.n.a);
            wdmParamsMap.put("source_pos", String.valueOf(this.n.d));
            if (this.n.e > 0) {
                wdmParamsMap.put("source_hor_pos", String.valueOf(this.n.e));
            }
        }
        return wdmParamsMap;
    }

    public int e() {
        return getRecyclerViewAdapter() == null ? 0 : getRecyclerViewAdapter().d();
    }

    public void a(String pageName, boolean isVisibleToUser) {
        if (TextUtils.isEmpty(pageName)) {
            this.mPageName = "topall";
        } else {
            this.mPageName = "Page_topall-" + pageName;
        }
        if (this.b != null) {
            this.b.a(this.mPageName);
        }
        Log.i("usagestats", "pagename-->" + pageName + ";mPagename-->" + this.mPageName + "-->" + isVisibleToUser + ";isShowwing-->" + this.c);
        if (isVisibleToUser) {
            com.meizu.cloud.statistics.b.a().a(this.mPageName);
            this.c = true;
            g();
            return;
        }
        if (this.c) {
            com.meizu.cloud.statistics.b.a().a(this.mPageName, d());
        }
        this.c = false;
    }

    private void g() {
        this.d = true;
        if (getRecyclerViewAdapter() != null && (getRecyclerViewAdapter() instanceof h)) {
            ((h) getRecyclerViewAdapter()).c();
        }
    }
}
