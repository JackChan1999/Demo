package com.meizu.statsapp;

import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteFullException;
import android.net.Uri;
import android.os.RemoteException;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.statsapp.util.Utils;
import java.util.ArrayList;
import java.util.Collection;
import org.json.JSONException;
import org.json.JSONObject;

final class d {
    private static volatile d a;
    private static Object e = new Object();
    private Context b;
    private boolean c;
    private UsageStatsProvider d;

    private d(Context context, boolean onLine) {
        this.b = context;
        this.c = onLine;
        if (this.c) {
            this.d = new UsageStatsProvider(this.b);
            this.d.onCreate();
        }
    }

    public static d a(Context context, boolean onLine) {
        if (a == null) {
            synchronized (e) {
                if (a == null) {
                    a = new d(context, onLine);
                }
            }
        }
        return a;
    }

    public synchronized Cursor a(int count) {
        Cursor query;
        Uri uri;
        if (count > 0) {
            uri = Uri.parse("content://com.meizu.usagestats/event?limit=" + String.valueOf(count));
        } else {
            uri = Uri.parse("content://com.meizu.usagestats/event");
        }
        if (this.c) {
            query = this.d.query(uri, null, null, null, "time ASC");
        } else {
            query = this.b.getContentResolver().query(uri, null, null, null, "time ASC");
        }
        return query;
    }

    public synchronized Cursor a(int count, long beforeTime) {
        Cursor query;
        Uri uri;
        if (count > 0) {
            uri = Uri.parse("content://com.meizu.usagestats/event?limit=" + String.valueOf(count));
        } else {
            uri = Uri.parse("content://com.meizu.usagestats/event");
        }
        if (this.c) {
            query = this.d.query(uri, null, "time < ?", new String[]{String.valueOf(beforeTime)}, "time ASC");
        } else {
            query = this.b.getContentResolver().query(uri, null, "time < ?", new String[]{String.valueOf(beforeTime)}, "time ASC");
        }
        return query;
    }

    public synchronized int a() {
        int count;
        Cursor cursor = a(0);
        int count2 = 0;
        if (cursor == null) {
            count = count2;
        } else {
            try {
                count2 = cursor.getCount();
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
                cursor.close();
            } catch (Throwable th) {
                cursor.close();
            }
            count = count2;
        }
        return count;
    }

    public synchronized void a(Event event) {
        if (event != null) {
            Uri uri = Uri.parse("content://com.meizu.usagestats/event");
            ContentValues values = b(event);
            try {
                if (this.c) {
                    this.d.insert(uri, values);
                } else {
                    this.b.getContentResolver().insert(uri, values);
                }
            } catch (SQLiteFullException e) {
                e.printStackTrace();
                b();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public synchronized int a(Collection<Event> events) {
        int i = 0;
        synchronized (this) {
            if (events != null) {
                if (events.size() >= 1) {
                    Uri uri = Uri.parse("content://com.meizu.usagestats/event");
                    ArrayList operations = new ArrayList();
                    i = 0;
                    int count = 0;
                    for (Event event : events) {
                        count++;
                        Builder builder = ContentProviderOperation.newDelete(uri);
                        builder.withSelection("_id=?", new String[]{String.valueOf(event.i())});
                        operations.add(builder.build());
                        if (count > 50) {
                            i += a(operations);
                            count = 0;
                            operations.clear();
                        }
                    }
                    if (operations.size() > 0) {
                        i += a(operations);
                    }
                }
            }
        }
        return i;
    }

    private synchronized int a(ArrayList<ContentProviderOperation> operations) {
        int delCount;
        delCount = 0;
        ContentProviderResult[] result = null;
        if (this.c) {
            try {
                result = this.d.applyBatch(operations);
            } catch (OperationApplicationException e) {
                e.printStackTrace();
            }
        } else {
            try {
                result = this.b.getContentResolver().applyBatch("com.meizu.usagestats", operations);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            } catch (OperationApplicationException e3) {
                e3.printStackTrace();
            }
        }
        if (result != null) {
            if (result.length > 0) {
                for (ContentProviderResult res : result) {
                    delCount += res.count.intValue();
                }
            }
        }
        return delCount;
    }

    public synchronized int b() {
        int delete;
        Uri uri = Uri.parse("content://com.meizu.usagestats/clear_events");
        if (this.c) {
            delete = this.d.delete(uri, null, null);
        } else {
            delete = this.b.getContentResolver().delete(uri, null, null);
        }
        return delete;
    }

    public static ContentValues b(Event event) {
        if (event == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        values.put("name", event.a());
        values.put("type", Integer.valueOf(event.b()));
        values.put("package", event.e());
        values.put("sessionid", event.d());
        values.put(RequestManager.TIME, Long.valueOf(event.c()));
        if (!Utils.isEmpty(event.f())) {
            values.put("page", event.f());
        }
        String propertiesStr = event.h();
        if (!Utils.isEmpty(propertiesStr)) {
            values.put("properties", propertiesStr);
        }
        values.put("network", event.j());
        values.put("channel", Long.valueOf(event.k()));
        values.put("flyme_version", event.l());
        values.put("package_version", event.m());
        return values;
    }

    public static Event a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        Event event = new Event();
        event.b(cursor.getInt(cursor.getColumnIndex("_id")));
        event.a(cursor.getString(cursor.getColumnIndex("name")));
        event.a(cursor.getInt(cursor.getColumnIndex("type")));
        event.b(cursor.getString(cursor.getColumnIndex("sessionid")));
        event.c(cursor.getString(cursor.getColumnIndex("package")));
        event.d(cursor.getString(cursor.getColumnIndex("page")));
        event.a(cursor.getLong(cursor.getColumnIndex(RequestManager.TIME)));
        String propertiesStr = cursor.getString(cursor.getColumnIndex("properties"));
        if (Utils.isEmpty(propertiesStr)) {
            event.a(new JSONObject());
        } else {
            try {
                event.a(new JSONObject(propertiesStr));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        event.e(cursor.getString(cursor.getColumnIndex("network")));
        event.b(cursor.getLong(cursor.getColumnIndex("channel")));
        event.f(cursor.getString(cursor.getColumnIndex("flyme_version")));
        event.g(cursor.getString(cursor.getColumnIndex("package_version")));
        return event;
    }
}
