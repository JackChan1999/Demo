package com.meizu.cloud.statistics;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.meizu.cloud.app.block.requestitem.AppAdStructItem;
import com.meizu.cloud.app.block.requestitem.ContsRow1Col4StructItem;
import com.meizu.cloud.app.block.requestitem.RollMessageStructItem;
import com.meizu.cloud.app.block.structitem.TitleItem;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.request.model.PageInfo.PageType;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.app.request.structitem.CategoryStructItem;
import com.meizu.cloud.app.request.structitem.PropertyTag;
import com.meizu.cloud.app.request.structitem.SearchHotItem;
import com.meizu.cloud.app.utils.param.BlockGotoPageInfo;
import com.meizu.cloud.base.app.BaseApplication;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class c {
    public static Map<String, String> a(AppStructItem appStructItem) {
        Map<String, String> wdmDataMap = new HashMap();
        if (appStructItem != null) {
            wdmDataMap.put("apkname", appStructItem.package_name);
            wdmDataMap.put("appid", String.valueOf(appStructItem.id));
            wdmDataMap.put("appname", appStructItem.name);
            if (appStructItem.click_pos > 0) {
                wdmDataMap.put("pos", String.valueOf(appStructItem.click_pos));
            }
            if (appStructItem.click_hor_pos > 0) {
                wdmDataMap.put("hor_pos", String.valueOf(appStructItem.click_hor_pos));
            }
            if (!TextUtils.isEmpty(appStructItem.search_id)) {
                wdmDataMap.put("search_id", appStructItem.search_id);
            }
        }
        return wdmDataMap;
    }

    public static Map<String, String> a(AppStructItem appStructItem, String searchId) {
        Map<String, String> wdmDataMap = a(appStructItem);
        wdmDataMap.put("search_id", searchId);
        return wdmDataMap;
    }

    public static Map<String, String> a(int appId, String pkgName, String appName) {
        Map<String, String> wdmDataMap = new HashMap();
        wdmDataMap.put("apkname", pkgName);
        wdmDataMap.put("appid", String.valueOf(appId));
        wdmDataMap.put("appname", appName);
        return wdmDataMap;
    }

    public static Map<String, String> a(int appId, String pkgName, String appName, int position) {
        Map<String, String> wdmDataMap = a(appId, pkgName, appName);
        if (position > 0) {
            wdmDataMap.put("pos", String.valueOf(position));
        }
        return wdmDataMap;
    }

    public static Map<String, String> a(CategoryStructItem appAdStructItem, int position, int horPosition) {
        Map<String, String> wdmDataMap = new HashMap();
        if (appAdStructItem != null) {
            wdmDataMap.put("categoryname", appAdStructItem.name);
            if (position > 0) {
                wdmDataMap.put("pos", String.valueOf(position));
            }
            if (horPosition > 0) {
                wdmDataMap.put("hor_pos", String.valueOf(horPosition));
            }
        }
        return wdmDataMap;
    }

    public static Map<String, String> b(AppStructItem appStructItem) {
        Map<String, String> wdmDataMap = a(appStructItem);
        if (appStructItem != null) {
            if (appStructItem.click_pos > 0) {
                wdmDataMap.put("pos", String.valueOf(appStructItem.click_pos));
            }
            if (appStructItem.click_hor_pos > 0) {
                wdmDataMap.put("hor_pos", String.valueOf(appStructItem.click_hor_pos));
            }
            if (!TextUtils.isEmpty(appStructItem.source_page)) {
                wdmDataMap.put("source_page", appStructItem.source_page);
            }
        }
        return wdmDataMap;
    }

    public static Map<String, String> a(String pkgName) {
        Map<String, String> wdmDataMap = new HashMap();
        wdmDataMap.put("apkname", pkgName);
        return wdmDataMap;
    }

    public static Map<String, String> a(int type, int status, String errorMsg, e downloadWrapper) {
        Map<String, String> wdmDataMap = new HashMap();
        wdmDataMap.put("type", String.valueOf(type));
        wdmDataMap.put("status", String.valueOf(status));
        wdmDataMap.put("status_reason", errorMsg);
        if (downloadWrapper != null) {
            AppStructItem appStructItem = downloadWrapper.m();
            if (appStructItem != null) {
                wdmDataMap.putAll(b(appStructItem));
                if (!TextUtils.isEmpty(appStructItem.search_id)) {
                    wdmDataMap.put("search_id", appStructItem.search_id);
                }
                if (!TextUtils.isEmpty(appStructItem.source_page)) {
                    wdmDataMap.put("source_page", appStructItem.source_page);
                }
            }
        }
        return wdmDataMap;
    }

    public static Map<String, String> a(SearchHotItem searchHotItem) {
        Map<String, String> mapData = new HashMap();
        if (searchHotItem != null) {
            mapData.put("content_id", String.valueOf(searchHotItem.content_id));
            mapData.put("name", searchHotItem.title);
            mapData.put("type", String.valueOf(searchHotItem.type));
        }
        return mapData;
    }

    public static boolean a(Context context, String pkgName, int verCode) {
        com.meizu.cloud.app.core.c compareResult = x.d(context).a(pkgName, verCode);
        return !d.a(context).f(pkgName) && (compareResult == com.meizu.cloud.app.core.c.UPGRADE || compareResult == com.meizu.cloud.app.core.c.NOT_INSTALL);
    }

    public static Map<String, String> b(int type, int status, String errorMsg, e downloadWrapper) {
        Map<String, String> downloadMap = a(type, status, errorMsg, downloadWrapper);
        if (!(downloadWrapper == null || TextUtils.isEmpty(downloadWrapper.ab()))) {
            downloadMap.put("error_type", String.valueOf(downloadWrapper.aa()));
            downloadMap.put("download_url", downloadWrapper.ab());
            downloadMap.put("dns_server", downloadWrapper.Y());
            downloadMap.put("ip_remote", downloadWrapper.Z());
            downloadMap.put("avg_down_rate", String.valueOf(downloadWrapper.W()));
            downloadMap.put("download_dur", String.valueOf(downloadWrapper.ac()));
            downloadMap.put("downloaded_size", String.valueOf(downloadWrapper.ad()));
        }
        return downloadMap;
    }

    public static Map<String, String> b(String packageName) {
        Map<String, String> mapData = new HashMap();
        mapData.put("packagename", packageName);
        return mapData;
    }

    public static Map<String, String> c(String pageNum) {
        Map<String, String> mapData = new HashMap();
        mapData.put("pageNum", pageNum);
        return mapData;
    }

    public static String d(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        return Uri.parse(url).getHost();
    }

    public static String e(String host) {
        if (!TextUtils.isEmpty(host)) {
            try {
                InetAddress inetAddress = InetAddress.getByName(host);
                if (inetAddress != null) {
                    return inetAddress.getHostAddress();
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String a() {
        return com.meizu.e.e.a(Long.toHexString(System.currentTimeMillis()) + com.meizu.cloud.app.utils.d.a(BaseApplication.a()) + "23096527").substring(8, 24);
    }

    public static Map<String, String> a(AppAdStructItem appAdStructItem) {
        Map<String, String> dataMap = new HashMap();
        if (appAdStructItem != null) {
            dataMap.put("block_id", String.valueOf(appAdStructItem.block_id));
            dataMap.put("block_type", appAdStructItem.block_type);
            dataMap.put("block_name", appAdStructItem.block_name);
            if (appAdStructItem.profile_id > 0) {
                dataMap.put("block_profile_id", String.valueOf(appAdStructItem.profile_id));
            }
            if (!TextUtils.isEmpty(appAdStructItem.type)) {
                dataMap.put("page_type", appAdStructItem.type);
            }
            dataMap.put("ad_id", String.valueOf(appAdStructItem.aid));
            dataMap.put("pos", String.valueOf(appAdStructItem.pos_ver));
            if (appAdStructItem.pos_hor > 0) {
                dataMap.put("hor_pos", String.valueOf(appAdStructItem.pos_hor));
            }
            if (PushConstants.EXTRA_APPLICATION_PENDING_INTENT.equals(appAdStructItem.type)) {
                dataMap.put("apkname", appAdStructItem.package_name);
                dataMap.put("appid", String.valueOf(appAdStructItem.content_id));
                dataMap.put("appname", appAdStructItem.name);
            } else if ("special".equals(appAdStructItem.type) || PushConstants.INTENT_ACTIVITY_NAME.equals(appAdStructItem.type)) {
                dataMap.put("topicid", String.valueOf(appAdStructItem.content_id));
                dataMap.put("topicname", String.valueOf(appAdStructItem.name));
            } else {
                dataMap.put("page_name_other", appAdStructItem.name);
                dataMap.put("page_id_other", String.valueOf(appAdStructItem.content_id));
            }
        }
        return dataMap;
    }

    public static Map<String, String> c(AppStructItem appStructItem) {
        Map<String, String> dataMap = new HashMap();
        if (appStructItem != null) {
            dataMap.put("block_id", String.valueOf(appStructItem.block_id));
            dataMap.put("block_type", appStructItem.block_type);
            dataMap.put("block_name", appStructItem.block_name);
            if (appStructItem.profile_id > 0) {
                dataMap.put("block_profile_id", String.valueOf(appStructItem.profile_id));
            }
            dataMap.put("pos", String.valueOf(appStructItem.pos_ver));
            if (appStructItem.pos_hor > 0) {
                dataMap.put("hor_pos", String.valueOf(appStructItem.pos_hor));
            }
            dataMap.put("apkname", appStructItem.package_name);
            dataMap.put("appid", String.valueOf(appStructItem.id));
            dataMap.put("appname", appStructItem.name);
            dataMap.put("appversion", appStructItem.version_name);
        }
        return dataMap;
    }

    public static Map<String, String> a(AbstractStrcutItem abstractStrcutItem) {
        Map<String, String> dataMap = new HashMap();
        if (abstractStrcutItem != null) {
            dataMap.put("block_id", String.valueOf(abstractStrcutItem.block_id));
            dataMap.put("block_type", abstractStrcutItem.block_type);
            dataMap.put("block_name", abstractStrcutItem.block_name);
            dataMap.put("pos", String.valueOf(abstractStrcutItem.pos_ver));
            if (abstractStrcutItem.pos_hor > 0) {
                dataMap.put("hor_pos", String.valueOf(abstractStrcutItem.pos_hor));
            }
            dataMap.put("page_type", abstractStrcutItem.type);
            if (abstractStrcutItem.profile_id > 0) {
                dataMap.put("block_profile_id", String.valueOf(abstractStrcutItem.profile_id));
            }
            if ((abstractStrcutItem instanceof ContsRow1Col4StructItem) && "ranks".equals(abstractStrcutItem.type)) {
                dataMap.put("page_id_other", String.valueOf(((ContsRow1Col4StructItem) abstractStrcutItem).id));
            }
            if (abstractStrcutItem instanceof RollMessageStructItem) {
                dataMap.put("page_id_other", String.valueOf(((RollMessageStructItem) abstractStrcutItem).content_id));
                dataMap.put("page_name_other", ((RollMessageStructItem) abstractStrcutItem).tag);
            } else if (!(abstractStrcutItem instanceof CategoryStructItem)) {
                dataMap.put("page_name_other", String.valueOf(abstractStrcutItem.name));
            } else if (PageType.SPECIAL.getType().equals(abstractStrcutItem.type) || PageType.ACTIVITY.getType().equals(abstractStrcutItem.type)) {
                if (!TextUtils.isEmpty(abstractStrcutItem.topic_name)) {
                    dataMap.put("topicname", abstractStrcutItem.topic_name);
                }
                if (abstractStrcutItem.topic_id > 0) {
                    dataMap.put("topicid", String.valueOf(abstractStrcutItem.topic_id));
                }
            } else {
                if (!TextUtils.isEmpty(abstractStrcutItem.topic_name)) {
                    dataMap.put("page_name_other", String.valueOf(abstractStrcutItem.topic_name));
                }
                if (abstractStrcutItem.topic_id > 0) {
                    dataMap.put("page_id_other", String.valueOf(abstractStrcutItem.topic_id));
                }
            }
        }
        return dataMap;
    }

    public static Map<String, String> a(TitleItem titleItem) {
        Map<String, String> dataMap = new HashMap();
        if (titleItem != null) {
            dataMap.put("block_id", String.valueOf(titleItem.id));
            dataMap.put("block_name", titleItem.name);
            dataMap.put("block_type", titleItem.type);
            if (titleItem.profile_id > 0) {
                dataMap.put("block_profile_id", String.valueOf(titleItem.profile_id));
            }
            dataMap.put("block_style", String.valueOf(titleItem.style));
            dataMap.put("pos", String.valueOf(titleItem.pos_ver));
        }
        return dataMap;
    }

    public static Map<String, String> a(AppUpdateStructItem updateStructItem, int lableIndex) {
        Map<String, String> dataMap = new HashMap();
        PropertyTag label = (PropertyTag) updateStructItem.adContent.data.get(lableIndex);
        dataMap.put("block_name", updateStructItem.block_name);
        dataMap.put("block_type", updateStructItem.block_type);
        dataMap.put("block_id", String.valueOf(updateStructItem.block_id));
        dataMap.put("pos", String.valueOf(updateStructItem.pos_ver));
        dataMap.put("hor_pos", String.valueOf(lableIndex + 1));
        dataMap.put("page_type", "label");
        dataMap.put("label_name", label.name);
        dataMap.put("label_id", String.valueOf(label.id));
        return dataMap;
    }

    public static Map<String, String> a(AppUpdateStructItem updateStructItem, int position, int horPosition, String labelName, int labelId) {
        Map<String, String> dataMap = new HashMap();
        if (updateStructItem.isAdStruct()) {
            dataMap.put("block_name", updateStructItem.block_name);
            dataMap.put("block_type", updateStructItem.block_type);
            dataMap.put("block_id", String.valueOf(updateStructItem.block_id));
            dataMap.put("pos", String.valueOf(position));
            dataMap.put("hor_pos", String.valueOf(horPosition));
            dataMap.put("page_type", "label");
            dataMap.put("label_name", labelName);
            dataMap.put("label_id", String.valueOf(labelId));
        }
        return dataMap;
    }

    public static UxipPageSourceInfo a(AppUpdateStructItem appUpdateStructItem, int position, int lableIndex) {
        UxipPageSourceInfo pageSourceInfo = new UxipPageSourceInfo();
        pageSourceInfo.b = appUpdateStructItem.block_id;
        pageSourceInfo.c = appUpdateStructItem.block_name;
        pageSourceInfo.a = appUpdateStructItem.block_type;
        pageSourceInfo.f = appUpdateStructItem.cur_page;
        pageSourceInfo.d = position + 1;
        if (appUpdateStructItem.adContent != null && lableIndex >= 0 && lableIndex < appUpdateStructItem.adContent.data.size()) {
            pageSourceInfo.e = lableIndex + 1;
        }
        return pageSourceInfo;
    }

    public static Map<String, String> b(AbstractStrcutItem abstractStrcutItem) {
        Map<String, String> dataMap = new HashMap();
        dataMap.put("block_id", String.valueOf(abstractStrcutItem.block_id));
        dataMap.put("block_type", abstractStrcutItem.block_type);
        dataMap.put("block_name", abstractStrcutItem.block_name);
        dataMap.put("page_type", abstractStrcutItem.type);
        dataMap.put("pos", String.valueOf(abstractStrcutItem.pos_ver));
        if (abstractStrcutItem.pos_hor > 0) {
            dataMap.put("hor_pos", String.valueOf(abstractStrcutItem.pos_hor));
        }
        if (PageType.SPECIAL.getType().equals(abstractStrcutItem.type) || PageType.ACTIVITY.getType().equals(abstractStrcutItem.type)) {
            if (!TextUtils.isEmpty(abstractStrcutItem.topic_name)) {
                dataMap.put("topicname", abstractStrcutItem.topic_name);
            }
            if (abstractStrcutItem.topic_id > 0) {
                dataMap.put("topicid", String.valueOf(abstractStrcutItem.topic_id));
            }
        } else {
            if (!TextUtils.isEmpty(abstractStrcutItem.topic_name)) {
                dataMap.put("page_name_other", String.valueOf(abstractStrcutItem.topic_name));
            }
            if (abstractStrcutItem.topic_id > 0) {
                dataMap.put("page_id_other", String.valueOf(abstractStrcutItem.topic_id));
            }
        }
        return dataMap;
    }

    public static UxipPageSourceInfo c(AbstractStrcutItem abstractStrcutItem) {
        UxipPageSourceInfo pageSourceInfo = new UxipPageSourceInfo();
        pageSourceInfo.b = abstractStrcutItem.block_id;
        pageSourceInfo.c = abstractStrcutItem.block_name;
        pageSourceInfo.a = abstractStrcutItem.block_type;
        pageSourceInfo.g = abstractStrcutItem.profile_id;
        pageSourceInfo.d = abstractStrcutItem.pos_ver;
        pageSourceInfo.e = abstractStrcutItem.pos_hor;
        pageSourceInfo.f = abstractStrcutItem.cur_page;
        return pageSourceInfo;
    }

    public static UxipPageSourceInfo a(BlockGotoPageInfo blockGotoPageInfo) {
        UxipPageSourceInfo pageSourceInfo = new UxipPageSourceInfo();
        pageSourceInfo.b = blockGotoPageInfo.l;
        pageSourceInfo.c = blockGotoPageInfo.n;
        pageSourceInfo.a = blockGotoPageInfo.m;
        pageSourceInfo.g = blockGotoPageInfo.p;
        pageSourceInfo.d = blockGotoPageInfo.j;
        pageSourceInfo.e = blockGotoPageInfo.k;
        pageSourceInfo.f = blockGotoPageInfo.i;
        return pageSourceInfo;
    }

    public static int f(String url) {
        if (TextUtils.isEmpty(url)) {
            return 0;
        }
        int specialId = 0;
        try {
            return Integer.valueOf(url.substring(url.lastIndexOf("/") + 1).trim()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return specialId;
        }
    }
}
