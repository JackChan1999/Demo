package com.meizu.cloud.app.request.model;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.core.t.a;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.PatchItem;
import com.meizu.cloud.download.c.a.b;
import com.meizu.cloud.download.c.a.c;
import java.util.List;

public class ServerUpdateAppInfo<T> extends BaseServerAppInfo implements a {
    private static final int CATEGORY_TYPE_APP = 1;
    public static final int CATEGORY_TYPE_GAME = 2;
    private static final int UPDATE_FLAG_EXIST = 0;
    public static final int UPDATE_FLAG_NOT_EXIST = 1;
    public AppStructItem appStructItem;
    @com.meizu.cloud.download.c.a.a(a = "category_id", e = true)
    @com.google.gson.a.a
    public int category_id;
    public PatchItem deltaUpdateItem;
    public boolean isChecked;
    @com.meizu.cloud.download.c.a.a(a = "is_latest_version", e = true)
    @com.google.gson.a.a
    public int is_latest_version;
    public List<T> layouts;
    @com.meizu.cloud.download.c.a.a(a = "version_create_time", e = true)
    @com.google.gson.a.a
    public long version_create_time;
    @com.meizu.cloud.download.c.a.a(a = "version_patch_md5", e = true)
    @com.google.gson.a.a
    public String version_patch_md5;
    @com.meizu.cloud.download.c.a.a(a = "version_patch_size", e = true)
    @com.google.gson.a.a
    public long version_patch_size;

    public interface Columns extends BaseServerAppInfo.Columns {
        public static final String CATEGORY_ID = "category_id";
        public static final String IS_LATEST_VERSION = "is_latest_version";
        public static final String LAYOUTS = "layouts";
        public static final String UPDATE_FINISH_TIME = "update_finish_time";
        public static final String VERSION_CREATE_TIME = "version_create_time";
        public static final String VERSION_PATCH_MD5 = "version_patch_md5";
        public static final String VERSION_PATCH_SIZE = "version_patch_size";
    }

    @c(a = "update_finish_record")
    public static class UpdateFinishRecord extends ServerUpdateAppInfo {
        public static final b<UpdateFinishRecord> ENTRY_CREATOR = new b<UpdateFinishRecord>() {
            public UpdateFinishRecord create() {
                return new UpdateFinishRecord();
            }
        };
        public static final com.meizu.cloud.download.c.b UPDATE_FINISH_RECORD_SCHEMA = new com.meizu.cloud.download.c.b(UpdateFinishRecord.class);
        @com.meizu.cloud.download.c.a.a(a = "update_finish_time", e = true)
        public long update_finish_time;

        public static final UpdateFinishRecord copyValue(ServerUpdateAppInfo updateAppInfo) {
            UpdateFinishRecord updateRecord = new UpdateFinishRecord();
            updateRecord.name = updateAppInfo.name;
            updateRecord.id = updateAppInfo.id;
            updateRecord.category_name = updateAppInfo.category_name;
            updateRecord.icon = updateAppInfo.icon;
            updateRecord.evaluate_count = updateAppInfo.evaluate_count;
            updateRecord.package_name = updateAppInfo.package_name;
            updateRecord.price = updateAppInfo.price;
            updateRecord.publisher = updateAppInfo.publisher;
            updateRecord.size = updateAppInfo.size;
            updateRecord.star = updateAppInfo.star;
            updateRecord.url = updateAppInfo.url;
            updateRecord.version_code = updateAppInfo.version_code;
            updateRecord.version_name = updateAppInfo.version_name;
            updateRecord.auto_install = updateAppInfo.auto_install;
            updateRecord.category_id = updateAppInfo.category_id;
            updateRecord.is_latest_version = updateAppInfo.is_latest_version;
            updateRecord.version_create_time = updateAppInfo.version_create_time;
            updateRecord.version_patch_md5 = updateAppInfo.version_patch_md5;
            updateRecord.version_patch_size = updateAppInfo.version_patch_size;
            updateRecord.update_description = updateAppInfo.update_description;
            return updateRecord;
        }
    }

    @c(a = "update_info")
    public static class UpdateInfo extends ServerUpdateAppInfo {
        public static final b<UpdateInfo> ENTRY_CREATOR = new b<UpdateInfo>() {
            public UpdateInfo create() {
                return new UpdateInfo();
            }
        };
        public static final com.meizu.cloud.download.c.b UPDATE_INFO_SCHEMA = new com.meizu.cloud.download.c.b(UpdateInfo.class);
        @com.meizu.cloud.download.c.a.a(a = "layouts")
        public String layouts;

        public static final UpdateInfo copyValue(ServerUpdateAppInfo updateAppInfo) {
            UpdateInfo updateInfo = new UpdateInfo();
            updateInfo.name = updateAppInfo.name;
            updateInfo.id = updateAppInfo.id;
            updateInfo.category_name = updateAppInfo.category_name;
            updateInfo.icon = updateAppInfo.icon;
            updateInfo.evaluate_count = updateAppInfo.evaluate_count;
            updateInfo.package_name = updateAppInfo.package_name;
            updateInfo.price = updateAppInfo.price;
            updateInfo.publisher = updateAppInfo.publisher;
            updateInfo.size = updateAppInfo.size;
            updateInfo.star = updateAppInfo.star;
            updateInfo.url = updateAppInfo.url;
            updateInfo.version_code = updateAppInfo.version_code;
            updateInfo.version_name = updateAppInfo.version_name;
            updateInfo.auto_install = updateAppInfo.auto_install;
            updateInfo.category_id = updateAppInfo.category_id;
            updateInfo.is_latest_version = updateAppInfo.is_latest_version;
            updateInfo.version_create_time = updateAppInfo.version_create_time;
            updateInfo.version_patch_md5 = updateAppInfo.version_patch_md5;
            updateInfo.version_patch_size = updateAppInfo.version_patch_size;
            updateInfo.update_description = updateAppInfo.update_description;
            updateInfo.layouts = updateAppInfo.getLayoutsJsonString();
            return updateInfo;
        }
    }

    public boolean existUpdate() {
        return this.is_latest_version == 0;
    }

    public boolean isGame() {
        return this.category_id == 2;
    }

    public boolean existDeltaUpdate() {
        return !TextUtils.isEmpty(this.version_patch_md5);
    }

    public AppStructItem getAppStructItem() {
        if (this.appStructItem == null) {
            this.appStructItem = new AppStructItem();
            this.appStructItem.package_name = this.package_name;
            this.appStructItem.name = this.name;
            this.appStructItem.id = this.id;
            this.appStructItem.name = this.name;
            this.appStructItem.category_name = this.category_name;
            this.appStructItem.icon = this.icon;
            this.appStructItem.evaluate_count = this.evaluate_count;
            this.appStructItem.price = this.price;
            this.appStructItem.publisher = this.publisher;
            this.appStructItem.size = this.size;
            this.appStructItem.star = this.star;
            this.appStructItem.url = this.url;
            this.appStructItem.version_code = this.version_code;
            this.appStructItem.version_name = this.version_name;
            this.appStructItem.version_create_time = this.version_create_time;
        }
        return this.appStructItem;
    }

    public static ServerUpdateAppInfo toServerUpdateAppInfo(Context context, AppStructItem appStructItem) {
        if (appStructItem == null) {
            return null;
        }
        ServerUpdateAppInfo updateAppInfo = new ServerUpdateAppInfo();
        updateAppInfo.package_name = appStructItem.package_name;
        updateAppInfo.name = appStructItem.name;
        updateAppInfo.id = appStructItem.id;
        updateAppInfo.category_name = appStructItem.category_name;
        updateAppInfo.icon = appStructItem.icon;
        updateAppInfo.evaluate_count = appStructItem.evaluate_count;
        updateAppInfo.price = appStructItem.price;
        updateAppInfo.publisher = appStructItem.publisher;
        updateAppInfo.size = appStructItem.size;
        updateAppInfo.star = appStructItem.star;
        updateAppInfo.url = appStructItem.url;
        updateAppInfo.version_code = appStructItem.version_code;
        updateAppInfo.version_name = appStructItem.version_name;
        updateAppInfo.is_latest_version = 0;
        updateAppInfo.version_create_time = appStructItem.version_create_time;
        updateAppInfo.category_id = x.a(context) ? 1 : 2;
        return updateAppInfo;
    }

    public PatchItem getPatchItem(Context context) {
        if (existDeltaUpdate() && this.deltaUpdateItem == null) {
            PackageInfo packageInfo = i.a(context, this.package_name);
            if (packageInfo != null) {
                PatchItem patchItem = new PatchItem();
                patchItem.version_patch_md5 = this.version_patch_md5;
                patchItem.version_patch_size = this.version_patch_size;
                patchItem.version_code_local = packageInfo.versionCode;
                patchItem.version_origin_file_path = packageInfo.applicationInfo.sourceDir;
                this.deltaUpdateItem = patchItem;
            }
        }
        return this.deltaUpdateItem;
    }

    public void eraseDeltaUpdataInfo() {
        this.deltaUpdateItem = null;
        this.version_patch_md5 = null;
        this.version_patch_size = 0;
    }

    public Class getBlockClass() {
        return getClass();
    }

    public String getLayoutsJsonString() {
        if (this.layouts == null || this.layouts.size() <= 0) {
            return "";
        }
        return ((JSONArray) JSON.toJSON(this.layouts)).toJSONString();
    }
}
