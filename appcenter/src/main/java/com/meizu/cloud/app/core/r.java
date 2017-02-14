package com.meizu.cloud.app.core;

import a.a.a.c;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Pair;
import com.alibaba.fastjson.JSON;
import com.meizu.cloud.app.request.model.AppStructDetailsItem;
import com.meizu.cloud.app.request.model.BaseServerAppInfo.Columns;
import com.meizu.cloud.app.request.model.GameEntryInfo;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo.UpdateFinishRecord;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo.UpdateInfo;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.update.exclude.d;
import com.meizu.cloud.download.c.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class r extends SQLiteOpenHelper {
    private static r a;
    private CopyOnWriteArrayList<ServerUpdateAppInfo<GameEntryInfo>> b = new CopyOnWriteArrayList();

    private r(Context context) {
        super(context, "update_info.db", null, 2);
        for (ServerUpdateAppInfo updateInfo : UpdateInfo.UPDATE_INFO_SCHEMA.a(getReadableDatabase(), UpdateInfo.ENTRY_CREATOR)) {
            ServerUpdateAppInfo updateAppInfo = updateInfo;
            if (!TextUtils.isEmpty(updateInfo.layouts)) {
                updateAppInfo.layouts = JSON.parseArray(updateInfo.layouts, GameEntryInfo.class);
            }
            this.b.add(updateAppInfo);
        }
    }

    public static synchronized r a(Context context) {
        r rVar;
        synchronized (r.class) {
            if (a == null) {
                a = new r(context.getApplicationContext());
            }
            rVar = a;
        }
        return rVar;
    }

    public void onCreate(SQLiteDatabase db) {
        UpdateInfo.UPDATE_INFO_SCHEMA.b(db);
        UpdateFinishRecord.UPDATE_FINISH_RECORD_SCHEMA.b(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        UpdateInfo.UPDATE_INFO_SCHEMA.b(db);
        UpdateFinishRecord.UPDATE_FINISH_RECORD_SCHEMA.b(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        UpdateInfo.UPDATE_INFO_SCHEMA.b(db);
        UpdateFinishRecord.UPDATE_FINISH_RECORD_SCHEMA.b(db);
    }

    public synchronized long a(ServerUpdateAppInfo updateAppInfo) {
        long id;
        boolean isExist = false;
        for (int i = 0; i < this.b.size(); i++) {
            if (((ServerUpdateAppInfo) this.b.get(i)).package_name.equals(updateAppInfo.package_name)) {
                this.b.set(i, updateAppInfo);
                isExist = true;
            }
        }
        if (!isExist) {
            this.b.add(updateAppInfo);
        }
        a updateInfo = UpdateInfo.copyValue(updateAppInfo);
        updateInfo.mId = (long) updateInfo.id;
        id = UpdateInfo.UPDATE_INFO_SCHEMA.a(getWritableDatabase(), updateInfo);
        if (id != -1) {
            c.a().d(new com.meizu.cloud.app.c.c(isExist ? 0 : 1, updateAppInfo.id));
        }
        return id;
    }

    public synchronized boolean a(int id) {
        boolean success;
        Iterator i$ = this.b.iterator();
        while (i$.hasNext()) {
            ServerUpdateAppInfo<GameEntryInfo> updateAppInfo = (ServerUpdateAppInfo) i$.next();
            if (updateAppInfo.id == id) {
                this.b.remove(updateAppInfo);
            }
        }
        success = UpdateInfo.UPDATE_INFO_SCHEMA.a(getWritableDatabase(), (long) id);
        if (success) {
            c.a().d(new com.meizu.cloud.app.c.c(-1, id));
        }
        return success;
    }

    public synchronized boolean a(String packageName) {
        boolean success;
        Iterator i$ = this.b.iterator();
        while (i$.hasNext()) {
            ServerUpdateAppInfo<GameEntryInfo> updateAppInfo = (ServerUpdateAppInfo) i$.next();
            if (updateAppInfo.package_name.equals(packageName)) {
                this.b.remove(updateAppInfo);
            }
        }
        ServerUpdateAppInfo updateAppInfo2 = b(packageName);
        if (updateAppInfo2 != null) {
            success = UpdateInfo.UPDATE_INFO_SCHEMA.a(getWritableDatabase(), (long) updateAppInfo2.id);
            if (success) {
                c.a().d(new com.meizu.cloud.app.c.c(-1, updateAppInfo2.id));
            }
        } else {
            success = false;
        }
        return success;
    }

    public synchronized void a(List<ServerUpdateAppInfo<GameEntryInfo>> updateAppInfos, boolean isSendBroadcast) {
        this.b.clear();
        this.b.addAll(updateAppInfos);
        if (updateAppInfos.size() > 0) {
            List updateInfos = new ArrayList(updateAppInfos.size());
            for (ServerUpdateAppInfo updateAppInfo : updateAppInfos) {
                UpdateInfo updateInfo = UpdateInfo.copyValue(updateAppInfo);
                updateInfo.mId = (long) updateInfo.id;
                updateInfos.add(updateInfo);
            }
            UpdateInfo.UPDATE_INFO_SCHEMA.a(getWritableDatabase(), updateInfos, true);
            if (isSendBroadcast) {
                c.a().d(new com.meizu.cloud.app.c.c(11, 0));
            }
        }
    }

    public synchronized void a(boolean isSendBroadcast) {
        this.b.clear();
        UpdateInfo.UPDATE_INFO_SCHEMA.c(getWritableDatabase());
        if (isSendBroadcast) {
            c.a().d(new com.meizu.cloud.app.c.c(-11, 0));
        }
    }

    public synchronized ServerUpdateAppInfo a(long id) {
        ServerUpdateAppInfo updateAppInfo;
        Iterator i$ = this.b.iterator();
        while (i$.hasNext()) {
            updateAppInfo = (ServerUpdateAppInfo) i$.next();
            if (((long) updateAppInfo.id) == id) {
                break;
            }
        }
        a updateInfo = new UpdateInfo();
        if (UpdateInfo.UPDATE_INFO_SCHEMA.a(getReadableDatabase(), id, updateInfo)) {
            a updateAppInfo2 = updateInfo;
            if (!TextUtils.isEmpty(updateInfo.layouts)) {
                updateAppInfo2.layouts = JSON.parseArray(updateInfo.layouts, GameEntryInfo.class);
            }
            a aVar = updateAppInfo2;
        } else {
            updateAppInfo = null;
        }
        return updateAppInfo;
    }

    public synchronized UpdateFinishRecord b(long id) {
        UpdateFinishRecord updateRecordInfo;
        updateRecordInfo = new UpdateFinishRecord();
        if (!UpdateFinishRecord.UPDATE_FINISH_RECORD_SCHEMA.a(getReadableDatabase(), id, (a) updateRecordInfo)) {
            updateRecordInfo = null;
        }
        return updateRecordInfo;
    }

    public synchronized ServerUpdateAppInfo b(String packageName) {
        ServerUpdateAppInfo updateAppInfo;
        Exception e;
        Throwable th;
        Iterator i$ = this.b.iterator();
        while (i$.hasNext()) {
            updateAppInfo = (ServerUpdateAppInfo) i$.next();
            if (updateAppInfo.package_name.equals(packageName)) {
                break;
            }
        }
        ServerUpdateAppInfo serverUpdateAppInfo = null;
        Cursor cursor = null;
        try {
            cursor = UpdateInfo.UPDATE_INFO_SCHEMA.a(getReadableDatabase(), "package_name=?", new String[]{packageName}, null, null, null);
            if (cursor.moveToFirst() && cursor.isLast()) {
                ServerUpdateAppInfo updateAppInfo2 = new ServerUpdateAppInfo();
                try {
                    for (String columnName : cursor.getColumnNames()) {
                        int index = cursor.getColumnIndex(columnName);
                        if ("name".equals(columnName)) {
                            updateAppInfo2.name = cursor.getString(index);
                        } else if ("id".equals(columnName)) {
                            updateAppInfo2.id = cursor.getInt(index);
                        } else if ("category_name".equals(columnName)) {
                            updateAppInfo2.category_name = cursor.getString(index);
                        } else if ("icon".equals(columnName)) {
                            updateAppInfo2.icon = cursor.getString(index);
                        } else if ("evaluate_count".equals(columnName)) {
                            updateAppInfo2.evaluate_count = cursor.getInt(index);
                        } else if ("package_name".equals(columnName)) {
                            updateAppInfo2.package_name = cursor.getString(index);
                        } else if ("price".equals(columnName)) {
                            updateAppInfo2.price = cursor.getDouble(index);
                        } else if ("publisher".equals(columnName)) {
                            updateAppInfo2.publisher = cursor.getString(index);
                        } else if ("size".equals(columnName)) {
                            updateAppInfo2.size = cursor.getLong(index);
                        } else if ("star".equals(columnName)) {
                            updateAppInfo2.star = cursor.getInt(index);
                        } else if ("url".equals(columnName)) {
                            updateAppInfo2.url = cursor.getString(index);
                        } else if ("version_code".equals(columnName)) {
                            updateAppInfo2.version_code = cursor.getInt(index);
                        } else if ("version_name".equals(columnName)) {
                            updateAppInfo2.version_name = cursor.getString(index);
                        } else if (Columns.AUTO_INSTALL.equals(columnName)) {
                            updateAppInfo2.auto_install = cursor.getInt(index);
                        } else if (ServerUpdateAppInfo.Columns.CATEGORY_ID.equals(columnName)) {
                            updateAppInfo2.category_id = cursor.getInt(index);
                        } else if (ServerUpdateAppInfo.Columns.IS_LATEST_VERSION.equals(columnName)) {
                            updateAppInfo2.is_latest_version = cursor.getInt(index);
                        } else if (ServerUpdateAppInfo.Columns.VERSION_CREATE_TIME.equals(columnName)) {
                            updateAppInfo2.version_create_time = cursor.getLong(index);
                        } else if (ServerUpdateAppInfo.Columns.VERSION_PATCH_MD5.equals(columnName)) {
                            updateAppInfo2.version_patch_md5 = cursor.getString(index);
                        } else if (ServerUpdateAppInfo.Columns.VERSION_PATCH_SIZE.equals(columnName)) {
                            updateAppInfo2.version_patch_size = cursor.getLong(index);
                        } else if (Columns.UPDATE_DESCRIPTION.equals(columnName)) {
                            updateAppInfo2.update_description = cursor.getString(index);
                        } else if (ServerUpdateAppInfo.Columns.LAYOUTS.equals(columnName)) {
                            String layoutJson = cursor.getString(index);
                            if (!TextUtils.isEmpty(layoutJson)) {
                                updateAppInfo2.layouts = JSON.parseArray(layoutJson, GameEntryInfo.class);
                            }
                        }
                    }
                    serverUpdateAppInfo = updateAppInfo2;
                } catch (Exception e2) {
                    e = e2;
                    serverUpdateAppInfo = updateAppInfo2;
                } catch (Throwable th2) {
                    th = th2;
                    serverUpdateAppInfo = updateAppInfo2;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e3) {
            e = e3;
            try {
                e.printStackTrace();
                if (cursor != null) {
                    cursor.close();
                }
                updateAppInfo = serverUpdateAppInfo;
                return updateAppInfo;
            } catch (Throwable th3) {
                th = th3;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }
        updateAppInfo = serverUpdateAppInfo;
        return updateAppInfo;
    }

    public synchronized UpdateFinishRecord c(String packageName) {
        UpdateFinishRecord updateFinishRecord;
        Exception e;
        Throwable th;
        updateFinishRecord = null;
        Cursor cursor = null;
        try {
            cursor = UpdateFinishRecord.UPDATE_FINISH_RECORD_SCHEMA.a(getReadableDatabase(), "package_name=?", new String[]{packageName}, null, null, null);
            if (cursor.moveToFirst() && cursor.isLast()) {
                UpdateFinishRecord record = new UpdateFinishRecord();
                try {
                    for (String columnName : cursor.getColumnNames()) {
                        int index = cursor.getColumnIndex(columnName);
                        if ("name".equals(columnName)) {
                            record.name = cursor.getString(index);
                        } else if ("id".equals(columnName)) {
                            record.id = cursor.getInt(index);
                        } else if ("category_name".equals(columnName)) {
                            record.category_name = cursor.getString(index);
                        } else if ("icon".equals(columnName)) {
                            record.icon = cursor.getString(index);
                        } else if ("evaluate_count".equals(columnName)) {
                            record.evaluate_count = cursor.getInt(index);
                        } else if ("package_name".equals(columnName)) {
                            record.package_name = cursor.getString(index);
                        } else if ("price".equals(columnName)) {
                            record.price = cursor.getDouble(index);
                        } else if ("publisher".equals(columnName)) {
                            record.publisher = cursor.getString(index);
                        } else if ("size".equals(columnName)) {
                            record.size = cursor.getLong(index);
                        } else if ("star".equals(columnName)) {
                            record.star = cursor.getInt(index);
                        } else if ("url".equals(columnName)) {
                            record.url = cursor.getString(index);
                        } else if ("version_code".equals(columnName)) {
                            record.version_code = cursor.getInt(index);
                        } else if ("version_name".equals(columnName)) {
                            record.version_name = cursor.getString(index);
                        } else if (Columns.AUTO_INSTALL.equals(columnName)) {
                            record.auto_install = cursor.getInt(index);
                        } else if (ServerUpdateAppInfo.Columns.CATEGORY_ID.equals(columnName)) {
                            record.category_id = cursor.getInt(index);
                        } else if (ServerUpdateAppInfo.Columns.IS_LATEST_VERSION.equals(columnName)) {
                            record.is_latest_version = cursor.getInt(index);
                        } else if (ServerUpdateAppInfo.Columns.VERSION_CREATE_TIME.equals(columnName)) {
                            record.version_create_time = cursor.getLong(index);
                        } else if (ServerUpdateAppInfo.Columns.VERSION_PATCH_MD5.equals(columnName)) {
                            record.version_patch_md5 = cursor.getString(index);
                        } else if (ServerUpdateAppInfo.Columns.VERSION_PATCH_SIZE.equals(columnName)) {
                            record.version_patch_size = cursor.getLong(index);
                        } else if (Columns.UPDATE_DESCRIPTION.equals(columnName)) {
                            record.update_description = cursor.getString(index);
                        } else if (ServerUpdateAppInfo.Columns.LAYOUTS.equals(columnName)) {
                            String layoutJson = cursor.getString(index);
                            if (!TextUtils.isEmpty(layoutJson)) {
                                record.layouts = JSON.parseArray(layoutJson, GameEntryInfo.class);
                            }
                        } else if (ServerUpdateAppInfo.Columns.UPDATE_FINISH_TIME.equals(columnName)) {
                            record.update_finish_time = cursor.getLong(index);
                        }
                    }
                    updateFinishRecord = record;
                } catch (Exception e2) {
                    e = e2;
                    updateFinishRecord = record;
                } catch (Throwable th2) {
                    th = th2;
                    updateFinishRecord = record;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e3) {
            e = e3;
            try {
                e.printStackTrace();
                if (cursor != null) {
                    cursor.close();
                }
                return updateFinishRecord;
            } catch (Throwable th3) {
                th = th3;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }
        return updateFinishRecord;
    }

    public synchronized List<ServerUpdateAppInfo> b(Context context) {
        List<ServerUpdateAppInfo> updateAppInfos;
        updateAppInfos = new ArrayList(this.b.size());
        Iterator i$ = this.b.iterator();
        while (i$.hasNext()) {
            ServerUpdateAppInfo updateInfo = (ServerUpdateAppInfo) i$.next();
            if (updateInfo.existUpdate()) {
                updateAppInfos.add(updateInfo);
            }
        }
        b(context, updateAppInfos);
        return updateAppInfos;
    }

    public synchronized List<ServerUpdateAppInfo<GameEntryInfo>> c(Context context) {
        List<ServerUpdateAppInfo<GameEntryInfo>> updateAppInfos;
        updateAppInfos = new ArrayList(this.b.size());
        Iterator i$ = this.b.iterator();
        while (i$.hasNext()) {
            ServerUpdateAppInfo<GameEntryInfo> updateInfo = (ServerUpdateAppInfo) i$.next();
            if (updateInfo.existUpdate()) {
                updateAppInfos.add(updateInfo);
            }
        }
        List<ServerUpdateAppInfo> stayRemoveList = new ArrayList();
        for (ServerUpdateAppInfo<GameEntryInfo> updateAppInfo : updateAppInfos) {
            boolean isRemove = true;
            for (Pair<String, Integer> pair : x.d(context).a()) {
                if (updateAppInfo.package_name.equals(pair.first)) {
                    isRemove = false;
                }
            }
            if (isRemove) {
                stayRemoveList.add(updateAppInfo);
            }
        }
        updateAppInfos.removeAll(stayRemoveList);
        a(context, (List) updateAppInfos);
        return updateAppInfos;
    }

    private synchronized void b(Context context, List<ServerUpdateAppInfo> updateAppInfos) {
        List<ServerUpdateAppInfo> stayRemoveList = new ArrayList();
        for (ServerUpdateAppInfo updateAppInfo : updateAppInfos) {
            boolean isRemove = true;
            for (Pair<String, Integer> pair : x.d(context).a()) {
                if (updateAppInfo.package_name.equals(pair.first)) {
                    isRemove = false;
                }
            }
            if (isRemove) {
                stayRemoveList.add(updateAppInfo);
            }
        }
        updateAppInfos.removeAll(stayRemoveList);
    }

    public synchronized void a(Context context, List<ServerUpdateAppInfo<GameEntryInfo>> updateAppInfos) {
        List<Pair<String, Integer>> appLists = m.a.a(context, "ignore_update_apps");
        List<ServerUpdateAppInfo> stayRemove = new ArrayList();
        for (ServerUpdateAppInfo updateAppInfo : updateAppInfos) {
            for (Pair<String, Integer> pair : appLists) {
                if (updateAppInfo.package_name.equals(pair.first) && updateAppInfo.version_code == ((Integer) pair.second).intValue()) {
                    stayRemove.add(updateAppInfo);
                    break;
                }
            }
        }
        if (x.a(context)) {
            List<String> allExcludeApp = d.a(context).a();
            for (ServerUpdateAppInfo<GameEntryInfo> updateAppInfo2 : updateAppInfos) {
                for (String pkgName : allExcludeApp) {
                    if (updateAppInfo2.package_name.equals(pkgName)) {
                        stayRemove.add(updateAppInfo2);
                        break;
                    }
                }
            }
        }
        updateAppInfos.removeAll(stayRemove);
    }

    public synchronized long a(ServerUpdateAppInfo updateAppInfo, long recordTime) {
        long id;
        a finishRecord = UpdateFinishRecord.copyValue(updateAppInfo);
        finishRecord.mId = (long) finishRecord.id;
        finishRecord.update_finish_time = recordTime;
        id = UpdateFinishRecord.UPDATE_FINISH_RECORD_SCHEMA.a(getWritableDatabase(), finishRecord);
        if (id != -1) {
            c.a().d(new com.meizu.cloud.app.c.d(1, id));
        }
        return id;
    }

    public synchronized long a(AppStructItem updateAppInfo, long recordTime) {
        long id;
        a finishRecord = new UpdateFinishRecord();
        finishRecord.name = updateAppInfo.name;
        finishRecord.id = updateAppInfo.id;
        finishRecord.category_name = updateAppInfo.category_name;
        finishRecord.icon = updateAppInfo.icon;
        finishRecord.evaluate_count = updateAppInfo.evaluate_count;
        finishRecord.package_name = updateAppInfo.package_name;
        finishRecord.price = updateAppInfo.price;
        finishRecord.publisher = updateAppInfo.publisher;
        finishRecord.size = updateAppInfo.size;
        finishRecord.star = updateAppInfo.star;
        finishRecord.url = updateAppInfo.url;
        finishRecord.version_code = updateAppInfo.version_code;
        finishRecord.version_name = updateAppInfo.version_name;
        if (updateAppInfo instanceof AppStructDetailsItem) {
            AppStructDetailsItem detailsUpdateAppInfo = (AppStructDetailsItem) updateAppInfo;
            finishRecord.update_description = detailsUpdateAppInfo.update_description;
            finishRecord.version_create_time = detailsUpdateAppInfo.version_time;
        }
        finishRecord.mId = (long) finishRecord.id;
        finishRecord.update_finish_time = recordTime;
        id = UpdateFinishRecord.UPDATE_FINISH_RECORD_SCHEMA.a(getWritableDatabase(), finishRecord);
        if (id != -1) {
            c.a().d(new com.meizu.cloud.app.c.d(1, id));
        }
        return id;
    }

    public synchronized boolean b(int id) {
        boolean success;
        success = UpdateFinishRecord.UPDATE_FINISH_RECORD_SCHEMA.a(getWritableDatabase(), (long) id);
        if (success) {
            c.a().d(new com.meizu.cloud.app.c.d(-1, (long) id));
        }
        return success;
    }

    public synchronized boolean d(String packageName) {
        boolean success;
        UpdateFinishRecord record = c(packageName);
        if (record != null) {
            success = UpdateFinishRecord.UPDATE_FINISH_RECORD_SCHEMA.a(getWritableDatabase(), (long) record.id);
            if (success) {
                c.a().d(new com.meizu.cloud.app.c.d(-1, (long) record.id));
            }
        } else {
            success = false;
        }
        return success;
    }

    public synchronized List<UpdateFinishRecord> d(Context context) {
        List<UpdateFinishRecord> finishRecords;
        finishRecords = UpdateFinishRecord.UPDATE_FINISH_RECORD_SCHEMA.a(getReadableDatabase(), UpdateFinishRecord.ENTRY_CREATOR);
        List<UpdateFinishRecord> stayClean = new ArrayList();
        long currentTime = System.currentTimeMillis();
        for (UpdateFinishRecord finishRecord : finishRecords) {
            if (x.d(context).a(finishRecord.package_name, finishRecord.version_code) == c.NOT_INSTALL) {
                stayClean.add(finishRecord);
                b(finishRecord.id);
            } else if (currentTime - finishRecord.update_finish_time >= 1209600000) {
                stayClean.add(finishRecord);
                b(finishRecord.id);
            }
        }
        finishRecords.removeAll(stayClean);
        return finishRecords;
    }
}
