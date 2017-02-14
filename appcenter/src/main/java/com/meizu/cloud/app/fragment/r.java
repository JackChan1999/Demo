package com.meizu.cloud.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import com.alibaba.fastjson.TypeReference;
import com.meizu.cloud.app.a.j;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.model.BlockResultModel;
import com.meizu.cloud.app.request.model.BlocksResultModel;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.SpecialStructItem;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.base.a.d.b;
import com.meizu.cloud.base.b.h;
import com.meizu.cloud.base.b.h.a;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.statistics.UxipPageSourceInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class r extends h<SpecialStructItem> {
    private int a;
    private int b;

    public abstract Fragment b();

    public abstract Fragment c();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPageName = "topics";
        this.mExtraPaddingTop = getResources().getDimensionPixelSize(d.special_list_margin_top);
    }

    protected void setupActionBar() {
        if (getArguments() != null) {
            CharSequence title = getArguments().getString("title_name", "");
            if (!TextUtils.isEmpty(title)) {
                super.setupActionBar();
                getActionBar().a(title);
            }
        }
    }

    public com.meizu.cloud.base.a.d createRecyclerAdapter() {
        final j adapter = new j(getActivity());
        adapter.a(new b(this) {
            final /* synthetic */ r b;

            public void onItemClick(View itemView, int position) {
                SpecialStructItem structItem = (SpecialStructItem) adapter.c(position);
                Map<String, String> wdmDataMap = new HashMap();
                Fragment fragment;
                Bundle bundle;
                if ("special".equals(structItem.type) || "specials".equals(structItem.type)) {
                    fragment = this.b.b();
                    bundle = new Bundle();
                    bundle.putString("url", structItem.url);
                    bundle.putString("title_name", structItem.name);
                    bundle.putString("source_page", this.b.mPageName);
                    fragment.setArguments(bundle);
                    com.meizu.cloud.base.b.d.startFragment(this.b.getActivity(), fragment);
                    wdmDataMap.put("name", structItem.name);
                } else if (PushConstants.INTENT_ACTIVITY_NAME.equals(structItem.type) || "activities".equals(structItem.type)) {
                    fragment = this.b.c();
                    bundle = new Bundle();
                    bundle.putString("url", structItem.url);
                    bundle.putString("title_name", structItem.subject);
                    bundle.putString("source_page", this.b.mPageName);
                    fragment.setArguments(bundle);
                    com.meizu.cloud.base.b.d.startFragment(this.b.getActivity(), fragment);
                    wdmDataMap.put("name", structItem.subject);
                }
                wdmDataMap.put("topicid", String.valueOf(structItem.id));
                wdmDataMap.put("pos", String.valueOf(position + 1));
                com.meizu.cloud.statistics.b.a().a("topicitem", "topics", wdmDataMap);
            }
        });
        return adapter;
    }

    protected a<SpecialStructItem> b(String json) {
        return c(json);
    }

    protected a<SpecialStructItem> a(String json) {
        return c(json);
    }

    private a<SpecialStructItem> c(String json) {
        ResultModel<BlocksResultModel<BlockResultModel<SpecialStructItem>>> resultModel = JSONUtils.parseResultModel(json, new TypeReference<ResultModel<BlocksResultModel<BlockResultModel<SpecialStructItem>>>>(this) {
            final /* synthetic */ r a;

            {
                this.a = r1;
            }
        });
        if (resultModel == null || resultModel.getCode() != 200 || resultModel.getValue() == null || ((BlocksResultModel) resultModel.getValue()).blocks == null) {
            return null;
        }
        a<SpecialStructItem> loadResult = new a();
        List<SpecialStructItem> structItemList = new ArrayList();
        for (BlockResultModel<SpecialStructItem> blockResultModel : ((BlocksResultModel) resultModel.getValue()).blocks) {
            SpecialStructItem structItem = (SpecialStructItem) blockResultModel.getData().get(0);
            structItem.type = blockResultModel.getType();
            structItemList.add(structItem);
        }
        loadResult.b = structItemList;
        loadResult.d = ((BlocksResultModel) resultModel.getValue()).more;
        loadResult.e = "";
        this.a++;
        this.b = loadResult.b.size();
        return loadResult;
    }

    public void onStart() {
        super.onStart();
        com.meizu.cloud.statistics.b.a().a(this.mPageName);
    }

    public void onStop() {
        super.onStop();
        com.meizu.cloud.statistics.b.a().a(this.mPageName, d());
    }

    public Map<String, String> d() {
        Map<String, String> wdmParamsMap = new HashMap();
        wdmParamsMap.put("count", String.valueOf(this.a));
        wdmParamsMap.put("sum", String.valueOf(this.b));
        if (getArguments() != null && getArguments().containsKey("uxip_page_source_info")) {
            UxipPageSourceInfo uxipPageSourceInfo = (UxipPageSourceInfo) getArguments().getParcelable("uxip_page_source_info");
            if (uxipPageSourceInfo != null) {
                wdmParamsMap.put("source_page", uxipPageSourceInfo.f);
                wdmParamsMap.put("source_block_id", String.valueOf(uxipPageSourceInfo.b));
                wdmParamsMap.put("source_block_name", uxipPageSourceInfo.c);
                wdmParamsMap.put("source_block_type", uxipPageSourceInfo.a);
                wdmParamsMap.put("source_pos", String.valueOf(uxipPageSourceInfo.d));
                if (uxipPageSourceInfo.e > 0) {
                    wdmParamsMap.put("source_hor_pos", String.valueOf(uxipPageSourceInfo.e));
                }
            }
        }
        return wdmParamsMap;
    }
}
