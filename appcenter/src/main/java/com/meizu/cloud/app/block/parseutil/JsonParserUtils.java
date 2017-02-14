package com.meizu.cloud.app.block.parseutil;

import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.meizu.cloud.app.block.requestitem.AdBigStructItem;
import com.meizu.cloud.app.block.requestitem.AppAdBigStructItem;
import com.meizu.cloud.app.block.requestitem.AppAdStructItem;
import com.meizu.cloud.app.block.requestitem.ChannelStructItem;
import com.meizu.cloud.app.block.requestitem.ContsRow1Col4StructItem;
import com.meizu.cloud.app.block.requestitem.GameQualityStructItem;
import com.meizu.cloud.app.block.requestitem.RollMessageStructItem;
import com.meizu.cloud.app.block.requestitem.RollingPlayStructItem;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structitem.AdAppBigItem;
import com.meizu.cloud.app.block.structitem.AdBigItem;
import com.meizu.cloud.app.block.structitem.AdvertiseItem;
import com.meizu.cloud.app.block.structitem.BlockDividerViewItem;
import com.meizu.cloud.app.block.structitem.ChannelCol5Item;
import com.meizu.cloud.app.block.structitem.ContsRow1Col4Item;
import com.meizu.cloud.app.block.structitem.GameQualityItem;
import com.meizu.cloud.app.block.structitem.IconTitleItem;
import com.meizu.cloud.app.block.structitem.RecommendAppItem;
import com.meizu.cloud.app.block.structitem.RollMessageItem;
import com.meizu.cloud.app.block.structitem.RollingPlayItem;
import com.meizu.cloud.app.block.structitem.Row1Col2AppItem;
import com.meizu.cloud.app.block.structitem.Row1Col2AppVerItem;
import com.meizu.cloud.app.block.structitem.Row1Col3AppVerItem;
import com.meizu.cloud.app.block.structitem.Row1Col4AppVerItem;
import com.meizu.cloud.app.block.structitem.Row2Col2AppVerItem;
import com.meizu.cloud.app.block.structitem.SingleRowAppItem;
import com.meizu.cloud.app.block.structitem.SubpageTitleItem;
import com.meizu.cloud.app.block.structitem.TitleItem;
import com.meizu.cloud.app.core.b;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.model.BlocksResultModel;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.app.request.structitem.RecommendAppStructItem;
import java.util.ArrayList;

public class JsonParserUtils {
    public static ResultModel<BlocksResultModel<JSONArray>> parseResultModel(String json) {
        ResultModel<BlocksResultModel<JSONArray>> resultModel = new ResultModel();
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject.containsKey("code")) {
            resultModel.setCode(jsonObject.getIntValue("code"));
        }
        if (jsonObject.containsKey("message")) {
            resultModel.setMessage(jsonObject.getString("message"));
        }
        if (jsonObject.containsKey("redirect")) {
            resultModel.setRedirect(jsonObject.getString("redirect"));
        }
        if (jsonObject.containsKey("value")) {
            JSONObject value = jsonObject.getJSONObject("value");
            if (value != null) {
                BlocksResultModel<JSONArray> blocksResultModel = new BlocksResultModel();
                if (value.containsKey("blocks")) {
                    value.getJSONArray("blocks");
                }
                if (value.containsKey("more")) {
                    resultModel.setValue(blocksResultModel);
                } else {
                    resultModel.setValue(blocksResultModel);
                }
            }
        }
        return resultModel;
    }

    protected static RollingPlayItem parseBanner(String name, String type, String url, boolean more, JSONObject jsonObject, int id, long profileId) {
        JSONArray items = jsonObject.getJSONArray("data");
        if (items == null) {
            return null;
        }
        RollingPlayStructItem rollingPlayStructItem = new RollingPlayStructItem(name, type, url, more);
        int itemCount = items.size();
        for (int j = 0; j < itemCount; j++) {
            AppAdStructItem appAdStructItem = (AppAdStructItem) JSONUtils.parseJSONObject(items.getJSONObject(j).toString(), new TypeReference<AppAdStructItem>() {
            });
            if (appAdStructItem != null) {
                int i;
                appAdStructItem.coverUrl = appAdStructItem.img_url;
                appAdStructItem.block_type = type;
                appAdStructItem.block_name = name;
                appAdStructItem.block_id = id;
                appAdStructItem.profile_id = profileId;
                rollingPlayStructItem.addItem(appAdStructItem);
                if (rollingPlayStructItem.mSubItems == null || rollingPlayStructItem.mSubItems.size() <= 0) {
                    i = 1;
                } else {
                    i = rollingPlayStructItem.mSubItems.size();
                }
                appAdStructItem.pos_hor = i;
            }
        }
        RollingPlayItem item = new RollingPlayItem();
        item.rollingPlayItem = rollingPlayStructItem;
        return item;
    }

    protected static ArrayList<AbsBlockItem> parseMultiAppItems(Context context, String name, String type, String url, boolean more, int id, JSONObject jsonObject, String style, long profileId) {
        ArrayList<AbsBlockItem> items = new ArrayList();
        TitleItem titleItem = new TitleItem(name, type, url, more, id);
        titleItem.profile_id = profileId;
        items.add(titleItem);
        JSONArray data = jsonObject.getJSONArray("data");
        int itemCount = data.size();
        AbsBlockItem currentItem = null;
        AbsBlockItem blockDividerViewItem = null;
        for (int j = 0; j < itemCount; j++) {
            if (type.equals("game_quality")) {
                parseGameQualityBlock(data.getJSONObject(j), items, id, type, name, profileId);
                titleItem.showDivider = false;
            } else {
                AppUpdateStructItem appStructItem = (AppUpdateStructItem) JSONUtils.parseJSONObject(data.getJSONObject(j).toString(), new TypeReference<RecommendAppStructItem>() {
                });
                if (appStructItem != null) {
                    AbsBlockItem item;
                    appStructItem.block_id = id;
                    appStructItem.block_type = type;
                    appStructItem.block_name = name;
                    appStructItem.style = style;
                    appStructItem.profile_id = profileId;
                    AbsBlockItem itemToAdd = null;
                    if (!type.equals("row1_col2")) {
                        if (!type.equals("row2_col2")) {
                            if (type.equals("row3_col1")) {
                                item = new SingleRowAppItem();
                                item.app = appStructItem;
                                b.a(context, item.app);
                                itemToAdd = item;
                                if (j + 1 >= itemCount) {
                                    item.mIsLastItemInAppBlock = true;
                                    blockDividerViewItem = new BlockDividerViewItem();
                                }
                            } else {
                                if (type.equals("game_recommend")) {
                                    item = new RecommendAppItem((RecommendAppStructItem) appStructItem);
                                    itemToAdd = item;
                                    if (j + 1 >= itemCount) {
                                        item.mIsLastItemInAppBlock = true;
                                    }
                                } else if ("row1_col4".equals(type)) {
                                    if (j % 4 == 0) {
                                        appStructItem.pos_hor = 1;
                                        AbsBlockItem row1Col4AppVerItem = new Row1Col4AppVerItem();
                                        row1Col4AppVerItem.mAppStructItem1 = appStructItem;
                                        currentItem = row1Col4AppVerItem;
                                        itemToAdd = currentItem;
                                        if (j + 4 >= itemCount) {
                                            row1Col4AppVerItem.mIsLastItemInAppBlock = true;
                                        }
                                    } else if (j % 4 == 1 && currentItem != null && (currentItem instanceof Row1Col4AppVerItem)) {
                                        appStructItem.pos_hor = 2;
                                        ((Row1Col4AppVerItem) currentItem).mAppStructItem2 = appStructItem;
                                    } else if (j % 4 == 2 && currentItem != null && (currentItem instanceof Row1Col4AppVerItem)) {
                                        appStructItem.pos_hor = 3;
                                        ((Row1Col4AppVerItem) currentItem).mAppStructItem3 = appStructItem;
                                    } else if (j % 4 == 3 && currentItem != null && (currentItem instanceof Row1Col4AppVerItem)) {
                                        appStructItem.pos_hor = 4;
                                        ((Row1Col4AppVerItem) currentItem).mAppStructItem4 = appStructItem;
                                        currentItem = null;
                                    }
                                } else if ("row1_col3".equals(type)) {
                                    if (j % 3 == 0) {
                                        appStructItem.pos_hor = 1;
                                        AbsBlockItem row1Col3AppVerItem = new Row1Col3AppVerItem();
                                        row1Col3AppVerItem.mAppStructItem1 = appStructItem;
                                        currentItem = row1Col3AppVerItem;
                                        itemToAdd = currentItem;
                                        if (j + 3 >= itemCount) {
                                            row1Col3AppVerItem.mIsLastItemInAppBlock = true;
                                        }
                                    } else if (j % 3 == 1 && currentItem != null && (currentItem instanceof Row1Col3AppVerItem)) {
                                        appStructItem.pos_hor = 2;
                                        ((Row1Col3AppVerItem) currentItem).mAppStructItem2 = appStructItem;
                                    } else if (j % 3 == 2 && currentItem != null && (currentItem instanceof Row1Col3AppVerItem)) {
                                        appStructItem.pos_hor = 3;
                                        ((Row1Col3AppVerItem) currentItem).mAppStructItem3 = appStructItem;
                                        currentItem = null;
                                    }
                                }
                            }
                            if (itemToAdd == null) {
                                if (items.size() - 1 >= 0) {
                                    if (((AbsBlockItem) items.get(items.size() - 1)) instanceof TitleItem) {
                                        itemToAdd.showDivider = false;
                                    }
                                }
                                items.add(itemToAdd);
                                if (blockDividerViewItem != null) {
                                    items.add(blockDividerViewItem);
                                }
                            }
                        }
                    }
                    if (j % 2 == 0) {
                        appStructItem.pos_hor = 1;
                        item = new Row1Col2AppItem();
                        item.app1 = appStructItem;
                        currentItem = item;
                        itemToAdd = currentItem;
                        if (j + 2 >= itemCount) {
                            item.mIsLastItemInAppBlock = true;
                        }
                    } else if (currentItem != null && (currentItem instanceof Row1Col2AppItem)) {
                        appStructItem.pos_hor = 2;
                        ((Row1Col2AppItem) currentItem).app2 = appStructItem;
                        currentItem = null;
                    }
                    if (itemToAdd == null) {
                        if (items.size() - 1 >= 0) {
                            if (((AbsBlockItem) items.get(items.size() - 1)) instanceof TitleItem) {
                                itemToAdd.showDivider = false;
                            }
                        }
                        items.add(itemToAdd);
                        if (blockDividerViewItem != null) {
                            items.add(blockDividerViewItem);
                        }
                    }
                }
            }
        }
        return items;
    }

    public static ArrayList<AbsBlockItem> parseBlockList(Context context, JSONArray jsonArray) {
        ArrayList<AbsBlockItem> blocks = new ArrayList();
        if (jsonArray != null) {
            int len = jsonArray.size();
            for (int i = 0; i < len; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String type = jsonObject.getString("type");
                String url = jsonObject.getString("url");
                boolean more = jsonObject.getBoolean("more").booleanValue();
                int id = jsonObject.getIntValue("id");
                String titleColor = jsonObject.getString("title_color");
                String titleImg = jsonObject.getString("title_img");
                String style = jsonObject.getString("style");
                long profileId = 0;
                if (jsonObject.getBooleanValue("is_profile")) {
                    profileId = jsonObject.getLongValue("profile_id");
                }
                if (type != null) {
                    if (type.equals("banner")) {
                        RollingPlayItem item = parseBanner(name, type, url, more, jsonObject, id, profileId);
                        if (item != null) {
                            blocks.add(item);
                        }
                    } else if (type.equals("row1_col2") || type.equals("row2_col2") || type.equals("row3_col1") || type.equals("game_recommend") || type.equals("row1_col4") || type.equals("game_quality") || type.equals("row1_col3")) {
                        items = parseMultiAppItems(context, name, type, url, more, id, jsonObject, style, profileId);
                        if (blocks.size() > 0) {
                            AbsBlockItem lastBlock = (AbsBlockItem) blocks.get(blocks.size() - 1);
                            if ((lastBlock instanceof AdvertiseItem) || (lastBlock instanceof RollingPlayItem)) {
                                ((AbsBlockItem) items.get(0)).needExtraMarginTop = true;
                            }
                        }
                        blocks.addAll(items);
                    } else if (type.equals("advertise")) {
                        parseAdvertiseBlock(jsonObject, blocks, name, type, id, profileId);
                    } else if (type.equals("conts_row1_col4")) {
                        parseContsRow1Col4(jsonObject, blocks, name, type, id, profileId);
                    } else if (type.equals("ad_big") || type.equals("special_r1_c1")) {
                        items = jsonObject.getJSONArray("data");
                        if (items != null) {
                            itemCount = items.size();
                            for (j = 0; j < itemCount; j++) {
                                AdBigStructItem adBigStructItem = (AdBigStructItem) JSONUtils.parseJSONObject(items.get(j).toString(), new TypeReference<AdBigStructItem>() {
                                });
                                if (adBigStructItem != null) {
                                    adBigStructItem.block_id = id;
                                    adBigStructItem.block_type = type;
                                    adBigStructItem.block_name = name;
                                    adBigStructItem.profile_id = profileId;
                                    AdBigItem adBigItem = new AdBigItem();
                                    adBigItem.mAdBigStructItem = adBigStructItem;
                                    blocks.add(adBigItem);
                                }
                            }
                        }
                    } else if (type.equals("ad_app_big") || type.equals("best")) {
                        items = jsonObject.getJSONArray("data");
                        if (items != null) {
                            itemCount = items.size();
                            for (j = 0; j < itemCount; j++) {
                                AppAdBigStructItem appAdBigStructItem = (AppAdBigStructItem) JSONUtils.parseJSONObject(items.get(j).toString(), new TypeReference<AppAdBigStructItem>() {
                                });
                                if (appAdBigStructItem != null) {
                                    appAdBigStructItem.block_id = id;
                                    appAdBigStructItem.block_type = type;
                                    appAdBigStructItem.block_name = name;
                                    appAdBigStructItem.profile_id = profileId;
                                    AdAppBigItem adAppBigItem = new AdAppBigItem();
                                    adAppBigItem.mAppAdBigStructItem = appAdBigStructItem;
                                    blocks.add(adAppBigItem);
                                }
                            }
                        }
                    } else if (type.equals("channel")) {
                        parseChannelBlock(jsonObject, blocks, name, type, id, profileId);
                    } else if (type.equals("roll_message")) {
                        parseRollMessageBlock(jsonObject, blocks, name, type, id, profileId);
                    } else if (type.equals("row3_col1_f5") || type.equals("row1_col2_f5") || type.equals("row1_col3_f5") || type.equals("row2_col2_f5")) {
                        items = parseMultiIconAppItems(name, id, more, titleColor, titleImg, type, jsonObject, profileId);
                        if (items != null) {
                            blocks.addAll(items);
                        }
                    }
                }
            }
        }
        return blocks;
    }

    public static ArrayList<AbsBlockItem> parseSubPageBlockList(Context context, JSONArray jsonArray) {
        ArrayList<AbsBlockItem> blocks = new ArrayList();
        if (jsonArray != null) {
            int len = jsonArray.size();
            for (int i = 0; i < len; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String type = jsonObject.getString("type");
                String url = jsonObject.getString("url");
                boolean more = jsonObject.getBoolean("more").booleanValue();
                int id = jsonObject.getIntValue("id");
                String titleColor = jsonObject.getString("title_color");
                String titleImg = jsonObject.getString("title_img");
                String style = jsonObject.getString("style");
                long profileId = 0;
                if (jsonObject.getBooleanValue("is_profile")) {
                    profileId = jsonObject.getLongValue("profile_id");
                }
                if (type != null) {
                    if (type.equals("subpage_col1") || type.equals("subpage_col3") || type.equals("subpage_col4")) {
                        ArrayList<AbsBlockItem> items = parseMultiSubpageAppItems(context, name, id, titleColor, type, jsonObject, profileId, jsonObject.getString("recommend_desc"), style);
                        if (items != null) {
                            blocks.addAll(items);
                        }
                    } else if (type.equals("ad_big")) {
                        JSONArray items2 = jsonObject.getJSONArray("data");
                        if (items2 != null) {
                            int itemCount = items2.size();
                            for (int j = 0; j < itemCount; j++) {
                                AdBigStructItem adBigStructItem = (AdBigStructItem) JSONUtils.parseJSONObject(items2.get(j).toString(), new TypeReference<AdBigStructItem>() {
                                });
                                if (adBigStructItem != null) {
                                    adBigStructItem.block_id = id;
                                    adBigStructItem.block_type = type;
                                    adBigStructItem.block_name = name;
                                    adBigStructItem.profile_id = profileId;
                                    AdBigItem adBigItem = new AdBigItem();
                                    adBigItem.mAdBigStructItem = adBigStructItem;
                                    adBigItem.mIsNeedMarginBottom = true;
                                    blocks.add(adBigItem);
                                }
                            }
                        }
                    }
                }
            }
        }
        return blocks;
    }

    private static ArrayList<AbsBlockItem> parseMultiSubpageAppItems(Context context, String name, int id, String titleColor, String type, JSONObject jsonObject, long profileId, String recommendDes, String style) {
        ArrayList<AbsBlockItem> blocks = new ArrayList();
        blocks.add(new SubpageTitleItem(id, titleColor, type, name, recommendDes));
        JSONArray items = jsonObject.getJSONArray("data");
        if (items != null) {
            int itemCount = items.size();
            AbsBlockItem currentItem = null;
            for (int i = 0; i < itemCount; i++) {
                AppUpdateStructItem appStructItem = (AppUpdateStructItem) JSONUtils.parseJSONObject(items.getJSONObject(i).toString(), new TypeReference<AppUpdateStructItem>() {
                });
                if (appStructItem != null) {
                    appStructItem.block_id = id;
                    appStructItem.block_name = name;
                    appStructItem.block_type = type;
                    appStructItem.style = style;
                    appStructItem.profile_id = profileId;
                    b.a(context, appStructItem);
                    if (type.equals("subpage_col1")) {
                        SingleRowAppItem singleRowAppItem = new SingleRowAppItem();
                        singleRowAppItem.setItemStyle(24);
                        singleRowAppItem.app = appStructItem;
                        blocks.add(singleRowAppItem);
                        if (i + 1 >= itemCount) {
                            singleRowAppItem.mIsLastItemInAppBlock = true;
                        }
                    } else {
                        if (!type.equals("subpage_col3")) {
                            if (type.equals("subpage_col4")) {
                                if (i % 4 == 0) {
                                    appStructItem.pos_hor = 1;
                                    AbsBlockItem row1Col4AppVerItem = new Row1Col4AppVerItem(true);
                                    row1Col4AppVerItem.mAppStructItem1 = appStructItem;
                                    currentItem = row1Col4AppVerItem;
                                    blocks.add(row1Col4AppVerItem);
                                    if (i + 4 >= itemCount) {
                                        row1Col4AppVerItem.mIsLastItemInGameBlock = true;
                                        row1Col4AppVerItem.mIsLastItemInAppBlock = true;
                                    }
                                } else if (i % 4 == 1 && currentItem != null && (currentItem instanceof Row1Col4AppVerItem)) {
                                    appStructItem.pos_hor = 2;
                                    ((Row1Col4AppVerItem) currentItem).mAppStructItem2 = appStructItem;
                                } else if (i % 4 == 2 && currentItem != null && (currentItem instanceof Row1Col4AppVerItem)) {
                                    appStructItem.pos_hor = 3;
                                    ((Row1Col4AppVerItem) currentItem).mAppStructItem3 = appStructItem;
                                } else if (i % 4 == 3 && currentItem != null && (currentItem instanceof Row1Col4AppVerItem)) {
                                    appStructItem.pos_hor = 4;
                                    ((Row1Col4AppVerItem) currentItem).mAppStructItem4 = appStructItem;
                                    currentItem = null;
                                }
                            }
                        } else if (i % 3 == 0) {
                            appStructItem.pos_hor = 1;
                            AbsBlockItem row1Col3AppVerItem = new Row1Col3AppVerItem();
                            row1Col3AppVerItem.mAppStructItem1 = appStructItem;
                            currentItem = row1Col3AppVerItem;
                            blocks.add(row1Col3AppVerItem);
                            if (i + 3 >= itemCount) {
                                row1Col3AppVerItem.mIsLastItemInGameBlock = true;
                            }
                        } else if (i % 3 == 1 && currentItem != null && (currentItem instanceof Row1Col3AppVerItem)) {
                            appStructItem.pos_hor = 2;
                            ((Row1Col3AppVerItem) currentItem).mAppStructItem2 = appStructItem;
                        } else if (i % 3 == 2 && currentItem != null && (currentItem instanceof Row1Col3AppVerItem)) {
                            appStructItem.pos_hor = 3;
                            ((Row1Col3AppVerItem) currentItem).mAppStructItem3 = appStructItem;
                            currentItem = null;
                        }
                    }
                }
            }
        }
        return blocks;
    }

    public static ArrayList<AbsBlockItem> parseBlockList(Context context, String json) {
        try {
            return parseBlockList(context, JSON.parseArray(json));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void parseAdvertiseBlock(JSONObject jsonObject, ArrayList<AbsBlockItem> blocks, String name, String type, int id, long profileId) {
        JSONArray items = jsonObject.getJSONArray("data");
        int itemCount = items.size();
        AbsBlockItem currentItem = null;
        for (int j = 0; j < itemCount; j++) {
            AppAdStructItem appAdStructItem = (AppAdStructItem) JSONUtils.parseJSONObject(items.getJSONObject(j).toString(), new TypeReference<AppAdStructItem>() {
            });
            if (appAdStructItem != null) {
                appAdStructItem.block_name = name;
                appAdStructItem.block_type = type;
                appAdStructItem.block_id = id;
                appAdStructItem.profile_id = profileId;
                if (j % 2 == 0) {
                    appAdStructItem.pos_hor = 1;
                    AbsBlockItem item = new AdvertiseItem();
                    item.advertise1 = appAdStructItem;
                    currentItem = item;
                    if (blocks.size() > 0) {
                        item.needExtraMarginTop = true;
                    }
                    blocks.add(currentItem);
                } else if (currentItem != null && (currentItem instanceof AdvertiseItem)) {
                    appAdStructItem.pos_hor = 2;
                    ((AdvertiseItem) currentItem).advertise2 = appAdStructItem;
                    currentItem = null;
                }
            }
        }
    }

    private static void parseContsRow1Col4(JSONObject jsonObject, ArrayList<AbsBlockItem> blocks, String name, String type, int id, long profileId) {
        JSONArray items = jsonObject.getJSONArray("data");
        if (items != null) {
            int itemCount = items.size();
            AbsBlockItem currentItem = null;
            for (int j = 0; j < itemCount; j++) {
                ContsRow1Col4StructItem contsItem = (ContsRow1Col4StructItem) JSONUtils.parseJSONObject(items.getJSONObject(j).toString(), new TypeReference<ContsRow1Col4StructItem>() {
                });
                if (contsItem != null) {
                    contsItem.block_name = name;
                    contsItem.block_type = type;
                    contsItem.block_id = id;
                    contsItem.profile_id = profileId;
                    if (j % 4 == 0) {
                        contsItem.pos_hor = 1;
                        AbsBlockItem contsRow1Col4Item = new ContsRow1Col4Item();
                        contsRow1Col4Item.item1 = contsItem;
                        currentItem = contsRow1Col4Item;
                        if (blocks.size() > 0) {
                            contsRow1Col4Item.needExtraMarginTop = true;
                        }
                        blocks.add(contsRow1Col4Item);
                    } else if (j % 4 == 1 && currentItem != null && (currentItem instanceof ContsRow1Col4Item)) {
                        contsItem.pos_hor = 2;
                        ((ContsRow1Col4Item) currentItem).item2 = contsItem;
                    } else if (j % 4 == 2 && currentItem != null && (currentItem instanceof ContsRow1Col4Item)) {
                        contsItem.pos_hor = 3;
                        ((ContsRow1Col4Item) currentItem).item3 = contsItem;
                    } else if (j % 4 == 3 && currentItem != null && (currentItem instanceof ContsRow1Col4Item)) {
                        contsItem.pos_hor = 4;
                        ((ContsRow1Col4Item) currentItem).item4 = contsItem;
                        currentItem = null;
                    }
                }
            }
        }
    }

    private static void parseChannelBlock(JSONObject jsonObject, ArrayList<AbsBlockItem> blocks, String name, String type, int id, long profileId) {
        JSONArray items = jsonObject.getJSONArray("data");
        if (items != null) {
            int itemCount = items.size();
            AbsBlockItem currentItem = null;
            for (int i = 0; i < itemCount; i++) {
                ChannelStructItem channelstructItem = (ChannelStructItem) JSONUtils.parseJSONObject(items.getJSONObject(i).toString(), new TypeReference<ChannelStructItem>() {
                });
                if (channelstructItem != null) {
                    channelstructItem.block_name = name;
                    channelstructItem.block_type = type;
                    channelstructItem.block_id = id;
                    channelstructItem.profile_id = profileId;
                    if (i % 5 == 0) {
                        channelstructItem.pos_hor = 1;
                        AbsBlockItem channelCol5Item = new ChannelCol5Item();
                        channelCol5Item.mChannelStructItem1 = channelstructItem;
                        currentItem = channelCol5Item;
                        blocks.add(channelCol5Item);
                    } else if (i % 5 == 1 && currentItem != null && (currentItem instanceof ChannelCol5Item)) {
                        channelstructItem.pos_hor = 2;
                        ((ChannelCol5Item) currentItem).mChannelStructItem2 = channelstructItem;
                    } else if (i % 5 == 2 && currentItem != null && (currentItem instanceof ChannelCol5Item)) {
                        channelstructItem.pos_hor = 3;
                        ((ChannelCol5Item) currentItem).mChannelStructItem3 = channelstructItem;
                    } else if (i % 5 == 3 && currentItem != null && (currentItem instanceof ChannelCol5Item)) {
                        channelstructItem.pos_hor = 4;
                        ((ChannelCol5Item) currentItem).mChannelStructItem4 = channelstructItem;
                    } else if (i % 5 == 4 && currentItem != null && (currentItem instanceof ChannelCol5Item)) {
                        channelstructItem.pos_hor = 5;
                        ((ChannelCol5Item) currentItem).mChannelStructItem5 = channelstructItem;
                        currentItem = null;
                    }
                }
            }
        }
    }

    private static void parseRollMessageBlock(JSONObject jsonObject, ArrayList<AbsBlockItem> blocks, String name, String type, int id, long profileId) {
        JSONArray items = jsonObject.getJSONArray("data");
        if (items != null && items.size() > 0) {
            int itemCount = items.size();
            RollMessageItem rollMessageItem = new RollMessageItem();
            rollMessageItem.mRollMessageStructItem = new ArrayList();
            for (int i = 0; i < itemCount; i++) {
                RollMessageStructItem rollMessageStructItem = (RollMessageStructItem) JSONUtils.parseJSONObject(items.getJSONObject(i).toString(), new TypeReference<RollMessageStructItem>() {
                });
                if (rollMessageStructItem != null) {
                    rollMessageStructItem.block_name = name;
                    rollMessageStructItem.block_type = type;
                    rollMessageStructItem.block_id = id;
                    rollMessageStructItem.profile_id = profileId;
                    rollMessageStructItem.pos_hor = i + 1;
                    rollMessageItem.mRollMessageStructItem.add(rollMessageStructItem);
                }
            }
            blocks.add(rollMessageItem);
        }
    }

    private static void parseGameQualityBlock(JSONObject jsonObject, ArrayList<AbsBlockItem> blocks, int blockId, String type, String name, long profileId) {
        GameQualityStructItem gameQualityStructItem = (GameQualityStructItem) JSONUtils.parseJSONObject(jsonObject.toString(), new TypeReference<GameQualityStructItem>() {
        });
        if (gameQualityStructItem != null) {
            gameQualityStructItem.block_id = blockId;
            gameQualityStructItem.block_type = type;
            gameQualityStructItem.block_name = name;
            gameQualityStructItem.profile_id = profileId;
            GameQualityItem gameQualityItem = new GameQualityItem();
            gameQualityItem.mGameQualityStructItem = gameQualityStructItem;
            gameQualityItem.showDivider = false;
            blocks.add(gameQualityItem);
        }
    }

    private static ArrayList<AbsBlockItem> parseMultiIconAppItems(String name, int id, boolean more, String title_color, String title_img, String type, JSONObject jsonObject, long profileId) {
        ArrayList<AbsBlockItem> blocks = new ArrayList();
        blocks.add(new IconTitleItem(id, more, title_color, title_img, type, name));
        JSONArray items = jsonObject.getJSONArray("data");
        if (items != null) {
            int itemCount = items.size();
            AbsBlockItem currentItem = null;
            for (int i = 0; i < itemCount; i++) {
                AppUpdateStructItem appStructItem = (AppUpdateStructItem) JSONUtils.parseJSONObject(items.getJSONObject(i).toString(), new TypeReference<AppUpdateStructItem>() {
                });
                if (appStructItem != null) {
                    appStructItem.block_id = id;
                    appStructItem.block_name = name;
                    appStructItem.block_type = type;
                    appStructItem.profile_id = profileId;
                    if (type.equals("row3_col1_f5")) {
                        SingleRowAppItem singleRowAppItem = new SingleRowAppItem();
                        singleRowAppItem.app = appStructItem;
                        blocks.add(singleRowAppItem);
                        if (i + 1 >= itemCount) {
                            singleRowAppItem.mIsLastItemInAppBlock = true;
                        }
                    } else {
                        if (!type.equals("row1_col2_f5")) {
                            if (!type.equals("row1_col3_f5")) {
                                if (type.equals("row2_col2_f5")) {
                                    if (i % 4 == 0) {
                                        appStructItem.pos_hor = 1;
                                        AbsBlockItem row2Col2AppVerItem = new Row2Col2AppVerItem();
                                        row2Col2AppVerItem.mAppStructItem1 = appStructItem;
                                        currentItem = row2Col2AppVerItem;
                                        blocks.add(row2Col2AppVerItem);
                                        if (i + 4 >= itemCount) {
                                            row2Col2AppVerItem.mIsLastItemInGameBlock = true;
                                        }
                                    } else if (i % 4 == 1 && currentItem != null && (currentItem instanceof Row2Col2AppVerItem)) {
                                        appStructItem.pos_hor = 2;
                                        ((Row2Col2AppVerItem) currentItem).mAppStructItem2 = appStructItem;
                                    } else if (i % 4 == 2 && currentItem != null && (currentItem instanceof Row2Col2AppVerItem)) {
                                        appStructItem.pos_hor = 3;
                                        ((Row2Col2AppVerItem) currentItem).mAppStructItem3 = appStructItem;
                                    } else if (i % 4 == 3 && currentItem != null && (currentItem instanceof Row2Col2AppVerItem)) {
                                        appStructItem.pos_hor = 4;
                                        ((Row2Col2AppVerItem) currentItem).mAppStructItem4 = appStructItem;
                                        currentItem = null;
                                    }
                                }
                            } else if (i % 3 == 0) {
                                appStructItem.pos_hor = 1;
                                AbsBlockItem row1Col3AppVerItem = new Row1Col3AppVerItem();
                                row1Col3AppVerItem.mAppStructItem1 = appStructItem;
                                currentItem = row1Col3AppVerItem;
                                blocks.add(row1Col3AppVerItem);
                                if (i + 3 >= itemCount) {
                                    row1Col3AppVerItem.mIsLastItemInGameBlock = true;
                                }
                            } else if (i % 3 == 1 && currentItem != null && (currentItem instanceof Row1Col3AppVerItem)) {
                                appStructItem.pos_hor = 2;
                                ((Row1Col3AppVerItem) currentItem).mAppStructItem2 = appStructItem;
                            } else if (i % 3 == 2 && currentItem != null && (currentItem instanceof Row1Col3AppVerItem)) {
                                appStructItem.pos_hor = 3;
                                ((Row1Col3AppVerItem) currentItem).mAppStructItem3 = appStructItem;
                                currentItem = null;
                            }
                        } else if (i % 2 == 0) {
                            appStructItem.pos_hor = 1;
                            AbsBlockItem row1Col2AppVerItem = new Row1Col2AppVerItem();
                            row1Col2AppVerItem.mAppStructItem1 = appStructItem;
                            currentItem = row1Col2AppVerItem;
                            blocks.add(row1Col2AppVerItem);
                            if (i + 2 >= itemCount) {
                                row1Col2AppVerItem.mIsLastItemInGameBlock = true;
                            }
                        } else if (i % 2 == 1 && currentItem != null && (currentItem instanceof Row1Col2AppVerItem)) {
                            appStructItem.pos_hor = 2;
                            ((Row1Col2AppVerItem) currentItem).mAppStructItem2 = appStructItem;
                            currentItem = null;
                        }
                    }
                }
            }
        }
        return blocks;
    }
}
