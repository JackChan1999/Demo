package com.meizu.cloud.app.fragment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.RecommendAppStructItem;
import com.meizu.cloud.app.request.structitem.SpecialConfig;
import com.meizu.cloud.app.utils.u;
import com.meizu.cloud.base.b.c;
import java.util.ArrayList;

public abstract class s extends c<RecommendAppStructItem> {
    protected SpecialConfig d;

    public static class a extends com.meizu.cloud.base.b.h.a<RecommendAppStructItem> {
        public SpecialConfig a;
    }

    protected /* synthetic */ com.meizu.cloud.base.b.h.a a(String str) {
        return e(str);
    }

    protected /* synthetic */ com.meizu.cloud.base.b.h.a b(String str) {
        return d(str);
    }

    protected a d(String json) {
        ResultModel<JSONObject> resultModel = JSONUtils.parseResultModel(json, new TypeReference<ResultModel<JSONObject>>(this) {
            final /* synthetic */ s a;

            {
                this.a = r1;
            }
        });
        if (resultModel != null) {
            a aVar = new a();
            aVar.b = new ArrayList();
            if (resultModel.getCode() == 200) {
                JSONObject value = (JSONObject) resultModel.getValue();
                JSONArray apps = value.getJSONArray("apps");
                if (value.containsKey("detail")) {
                    JSONObject detail = value.getJSONObject("detail");
                    if (detail != null) {
                        SpecialConfig config = new SpecialConfig();
                        if (detail.containsKey("banner")) {
                            config.banner = detail.getString("banner");
                        }
                        if (detail.containsKey("description")) {
                            config.description = detail.getString("description");
                        }
                        if (detail.containsKey("id")) {
                            config.id = (long) detail.getIntValue("id");
                        }
                        if (detail.containsKey("name")) {
                            config.title = detail.getString("name");
                        }
                        if (detail.containsKey("colors")) {
                            config.colors = u.a(getActivity(), detail.getJSONObject("colors"));
                        }
                        aVar.a = config;
                        this.d = config;
                    }
                }
                int appCount = apps.size();
                for (int i = 0; i < appCount; i++) {
                    JSONObject item = apps.getJSONObject(i);
                    RecommendAppStructItem appStructItem = (RecommendAppStructItem) JSONUtils.parseJSONObject(item.toString(), new TypeReference<RecommendAppStructItem>(this) {
                        final /* synthetic */ s a;

                        {
                            this.a = r1;
                        }
                    });
                    if (appStructItem != null) {
                        aVar.b.add(appStructItem);
                    }
                }
                return aVar;
            }
        }
        return null;
    }

    protected a e(String json) {
        return null;
    }
}
