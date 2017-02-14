package com.meizu.cloud.app.fragment;

import a.a.a.c;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meizu.cloud.app.a.k;
import com.meizu.cloud.app.block.parseutil.JsonParserUtils;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structitem.AdvertiseItem;
import com.meizu.cloud.app.block.structitem.GameBacktopItem;
import com.meizu.cloud.app.block.structitem.RollingPlayItem;
import com.meizu.cloud.app.block.structitem.TitleItem;
import com.meizu.cloud.app.block.structlayout.RollingPlayLayout;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.u;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.request.FastJsonParseCacheRequest;
import com.meizu.cloud.app.request.FastJsonParseCacheRequest.CacheCallback;
import com.meizu.cloud.app.request.ParseListener;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.utils.s;
import com.meizu.cloud.app.widget.BackTopListview;
import com.meizu.cloud.base.b.b;
import com.meizu.cloud.base.b.f;
import com.meizu.cloud.download.c.h;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class e extends b implements CacheCallback, ParseListener {
    protected List<AbsBlockItem> a = new ArrayList();
    protected t b;
    public RollingPlayLayout c;
    public Boolean d;

    public static class a<T> {
        public List<T> a = new ArrayList();
        public int b = 0;
        public int c = 0;
        public boolean d = false;
        public String e;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.b = new t(getActivity(), new u());
        this.mFirstJson = getArguments().getString("json_string", "");
        this.j = getArguments().getString("url", "");
        this.mbMore = getArguments().getBoolean("more", false);
        this.mPageInfo[1] = 1;
        this.mPageName = "Featured";
        if (this.b != null) {
            this.b.a(this.mPageName);
            this.b.a(this.mPageInfo);
        }
        registerPagerScrollStateListener();
        c.a().a((Object) this);
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        showProgress();
        g().setVisibility(8);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterPagerScrollStateListener();
        if (x.b(getActivity())) {
            ((BackTopListview) g()).setBackTopView(null);
        }
        c.a().c(this);
    }

    protected void onRequestData() {
        List<com.meizu.volley.b.a> params = new ArrayList();
        params.add(new com.meizu.volley.b.a("start", String.valueOf(this.k)));
        params.add(new com.meizu.volley.b.a("max", String.valueOf(5)));
        this.mLoadRequest = new FastJsonParseCacheRequest(this.j, params, this, new f.b(this), new com.meizu.cloud.base.b.f.a(this));
        this.mLoadRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(getActivity()));
        if (this.k == 0) {
            ((FastJsonParseCacheRequest) this.mLoadRequest).setCacheListener(this);
            String ifSinceModified = s.a(getActivity(), this.j);
            if (!TextUtils.isEmpty(ifSinceModified)) {
                this.mLoadRequest.addHeader(RequestConstants.IF_MODIFIED_SINCE, ifSinceModified);
            }
        }
        com.meizu.volley.b.a(getActivity()).a().a(this.mLoadRequest);
    }

    public void onCacheDateReceived(String date) {
        if (this.k == 0 && this.mRunning) {
            s.a(getActivity(), this.j, date);
        }
    }

    public Object onParseCache() {
        String json = "";
        if (this.mRunning) {
            json = s.a(getActivity(), this.j, 0, 5);
            if (TextUtils.isEmpty(json)) {
                s.a(getActivity(), this.j, "");
            }
        }
        return a(json, true);
    }

    public Object onParseResponse(String response) {
        return a(response, false);
    }

    private Object a(String json, boolean isFromCache) {
        if (this.mRunning) {
            if (h.c(json)) {
                json = s.a(getActivity(), this.j, this.e.getCount(), 5);
            } else if (!isFromCache) {
                s.a(getActivity(), this.j, json, this.e.getCount(), 5);
            }
        }
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject.containsKey("code")) {
                Object result = new a();
                int code = jsonObject.getIntValue("code");
                if (jsonObject.containsKey("message")) {
                    result.e = jsonObject.getString("message");
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
                    result.d = result_value.getBooleanValue("more");
                }
                if (!result_value.containsKey("blocks")) {
                    return result;
                }
                JSONArray blockJsonArray = result_value.getJSONArray("blocks");
                ArrayList<AbsBlockItem> blocks = JsonParserUtils.parseBlockList(getActivity(), blockJsonArray);
                if (blocks == null) {
                    return result;
                }
                result.a.addAll(blocks);
                result.c = blockJsonArray.size();
                if (result.d || !x.b(getActivity())) {
                    return result;
                }
                result.a.add(new GameBacktopItem());
                result.c = blockJsonArray.size() + 1;
                ((BackTopListview) g()).setListViewMode(BackTopListview.a.BackTopMode);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected boolean onResponse(Object response) {
        if (response != null && !(response instanceof a)) {
            return false;
        }
        final a<AbsBlockItem> data = (a) response;
        if (data == null || this.e == null) {
            return false;
        }
        int count = this.e.getCount();
        AbsBlockItem lastItem;
        if (count == 0 && data.a.size() > 0) {
            lastItem = (AbsBlockItem) data.a.get(0);
            if ((lastItem instanceof RollingPlayItem) || (lastItem instanceof AdvertiseItem)) {
                lastItem.needExtraMarginTop = false;
            } else if (lastItem instanceof TitleItem) {
                lastItem.needExtraMarginTop = true;
            }
        } else if (count > 0 && data.a.size() > 0) {
            lastItem = (AbsBlockItem) this.e.getItem(count - 1);
            AbsBlockItem tempLastItem = (AbsBlockItem) data.a.get(data.a.size() - 1);
            if ((tempLastItem instanceof TitleItem) && ((lastItem instanceof AdvertiseItem) || (lastItem instanceof RollingPlayItem))) {
                tempLastItem.needExtraMarginTop = true;
            }
        }
        this.l++;
        this.k += data.c;
        this.a.addAll(data.a);
        this.mbMore = data.d;
        postOnPagerIdle(new Runnable(this) {
            final /* synthetic */ e b;

            public void run() {
                if (this.b.getActivity() != null && this.b.mRunning) {
                    this.b.e.a(data == null ? null : this.b.a);
                }
            }
        });
        return true;
    }

    protected void onErrorResponse(com.android.volley.s error) {
    }

    protected Object onParseFirstData(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONArray blockJsonArray = JSON.parseArray(json);
                ArrayList<AbsBlockItem> blocks = JsonParserUtils.parseBlockList(getActivity(), blockJsonArray);
                if (blocks != null) {
                    a<AbsBlockItem> result = new a();
                    result.a.addAll(blocks);
                    result.d = getArguments().getBoolean("more", false);
                    result.c = blockJsonArray.size();
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected void a() {
        super.a();
        onRequestData();
    }

    protected void onLoadFinished() {
        super.onLoadFinished();
        this.i = false;
        this.mbLoading = false;
        hideProgress();
        this.f.setVisibility(8);
        g().removeFooterView(this.f);
        g().setVisibility(0);
    }

    public Map<String, String> b() {
        Map<String, String> paramsMap = super.b();
        if (this.mPageInfo[0] > 0) {
            paramsMap.put("type_id", String.valueOf(this.mPageInfo[0]));
            paramsMap.put("type", String.valueOf(1));
            paramsMap.put("category_type", String.valueOf(1));
        }
        return paramsMap;
    }

    protected void onRealPageStart() {
        if (this.c != null) {
            this.c.resume();
        }
        com.meizu.cloud.statistics.b.a().a(this.mPageName);
    }

    protected void onRealPageStop() {
        if (this.c != null) {
            this.c.pause();
        }
        com.meizu.cloud.statistics.b.a().a(this.mPageName, b());
    }

    public void onPageScrollStateChanged(int state) {
        super.onPageScrollStateChanged(state);
        if (this.c != null) {
            this.c.onMainPageScrollStateChanged(state);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        if (this.c != null) {
            this.c.onMainPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void a(RollingPlayLayout rollingPlayLayout) {
        this.c = rollingPlayLayout;
    }

    public void a(AbsListView view, int scrollState) {
        if (view.getLastVisiblePosition() == view.getCount() - 1) {
            this.d = Boolean.valueOf(true);
            ((BackTopListview) g()).setmIsBottom(this.d);
            return;
        }
        this.d = Boolean.valueOf(false);
        ((BackTopListview) g()).setmIsBottom(this.d);
    }

    public void onEventMainThread(com.meizu.cloud.app.c.a appStateChangeEvent) {
        if (h() != null && (h() instanceof k)) {
            ((k) h()).a(appStateChangeEvent.b);
        }
    }

    public void onEventMainThread(com.meizu.cloud.app.c.b appUpdateCheckEvent) {
        if (appUpdateCheckEvent.c) {
            for (String pkg : appUpdateCheckEvent.a) {
                if (h() != null && (h() instanceof k)) {
                    ((k) h()).a(pkg);
                }
            }
        }
    }

    public void b(AbsListView view, int scrollState) {
        super.b(view, scrollState);
        if (this.c == null || scrollState != 0) {
            return;
        }
        if (view.getFirstVisiblePosition() == 0) {
            this.c.resume();
        } else {
            this.c.pause();
        }
    }
}
