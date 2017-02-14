package com.meizu.flyme.appcenter.fragment;

import android.view.View;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.meizu.cloud.app.block.requestitem.AppAdStructItem;
import com.meizu.cloud.app.block.requestitem.GameQualityStructItem.GameLayout;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structitem.CategoryGridItem;
import com.meizu.cloud.app.block.structitem.CategoryTag6Item;
import com.meizu.cloud.app.block.structitem.TitleItem;
import com.meizu.cloud.app.fragment.f;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.model.BlockResultModel;
import com.meizu.cloud.app.request.model.BlocksResultModel;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.CategoryStructItem;
import com.meizu.cloud.app.request.structitem.PropertyTag;
import com.meizu.cloud.base.b.h.a;
import java.util.ArrayList;
import java.util.List;

public class b extends f {
    protected a<AbsBlockItem> b(String json) {
        ResultModel<BlocksResultModel<BlockResultModel<JSONObject>>> jsonModel = JSONUtils.parseResultModel(json, new TypeReference<ResultModel<BlocksResultModel<BlockResultModel<JSONObject>>>>(this) {
            final /* synthetic */ b a;

            {
                this.a = r1;
            }
        });
        if (jsonModel == null || jsonModel.getCode() != 200 || jsonModel.getValue() == null || ((BlocksResultModel) jsonModel.getValue()).blocks == null) {
            return null;
        }
        return a(((BlocksResultModel) jsonModel.getValue()).blocks);
    }

    protected a<AbsBlockItem> a(List<BlockResultModel<JSONObject>> blockResultModelList) {
        List<AbsBlockItem> blockItemList = new ArrayList();
        a<AbsBlockItem> result = new a();
        for (BlockResultModel<JSONObject> blockResultModel : blockResultModelList) {
            if ("category_set".equals(blockResultModel.getType())) {
                List<CategoryTag6Item> tag6ItemList = a(blockResultModel, blockResultModel.getType(), blockResultModel.id);
                if (tag6ItemList != null && tag6ItemList.size() > 0) {
                    blockItemList.addAll(tag6ItemList);
                }
            } else if ("conts_row1_col2".equals(blockResultModel.getType())) {
                blockItemList.add(b(blockResultModel, blockResultModel.getType(), blockResultModel.id));
            }
        }
        result.b = blockItemList;
        return result;
    }

    private List<CategoryTag6Item> a(BlockResultModel<JSONObject> blockResultModel, String blockType, int blockId) {
        List<CategoryTag6Item> result = new ArrayList();
        List<JSONObject> jsonObjectList = blockResultModel.getData();
        if ("category_set".equals(blockResultModel.getType())) {
            for (JSONObject jsonObject : jsonObjectList) {
                CategoryStructItem structItem = (CategoryStructItem) JSONUtils.parseJSONObject(jsonObject.toString(), new TypeReference<CategoryStructItem>(this) {
                    final /* synthetic */ b a;

                    {
                        this.a = r1;
                    }
                });
                if (structItem != null) {
                    structItem.type = "ranks";
                    ((PropertyTag) structItem.property_tags.get(0)).hide = true;
                    structItem.block_type = blockType;
                    structItem.block_id = blockId;
                    CategoryTag6Item categoryTag6Item = new CategoryTag6Item();
                    categoryTag6Item.categoryStruct = structItem;
                    result.add(categoryTag6Item);
                }
            }
        }
        return result;
    }

    private CategoryGridItem b(BlockResultModel<JSONObject> blockResultModel, String blockType, int blockId) {
        CategoryGridItem result = new CategoryGridItem();
        result.structItemList = new ArrayList();
        for (JSONObject jsonObject : blockResultModel.getData()) {
            CategoryStructItem structItem = (CategoryStructItem) JSONUtils.parseJSONObject(jsonObject.toString(), new TypeReference<CategoryStructItem>(this) {
                final /* synthetic */ b a;

                {
                    this.a = r1;
                }
            });
            structItem.bg = structItem.icon;
            structItem.block_type = blockType;
            structItem.block_id = blockId;
            result.structItemList.add(structItem);
        }
        return result;
    }

    public void b(CategoryStructItem itemData, int tagName, int position, int horPosition) {
        com.meizu.flyme.appcenter.c.a.a(getActivity(), itemData, null, tagName);
        a(itemData, tagName, position, horPosition);
    }

    public void onClickAd(AppAdStructItem appAdStructItem, int position, int horPosition) {
    }

    public void onClickApp(AppStructItem appStructItem, int position, int horPosition) {
    }

    public void onDownload(AppStructItem appStructItem, View btn, int position, int horPosition) {
    }

    public void onCancelDownload(AppStructItem appStructItem) {
    }

    public void onMore(TitleItem titleItem) {
    }

    public void onClickConts(AbstractStrcutItem abstractStrcutItem, String param, int position, int horPosition) {
        if (getActivity() != null && this.mRunning && (abstractStrcutItem instanceof CategoryStructItem)) {
            CategoryStructItem categoryStructItem = (CategoryStructItem) abstractStrcutItem;
            int tagId = 0;
            if (categoryStructItem.property_tags != null) {
                for (int i = 0; i < categoryStructItem.property_tags.size(); i++) {
                    if (((PropertyTag) categoryStructItem.property_tags.get(i)).name.equals(param)) {
                        tagId = ((PropertyTag) categoryStructItem.property_tags.get(i)).id;
                        break;
                    }
                }
            }
            b(categoryStructItem, tagId, position, horPosition);
        }
    }

    public void onTabClick(GameLayout gameLayout, AppStructItem appStructItem) {
    }

    public void onBlockExposure(AbsBlockItem data, int position) {
    }
}
