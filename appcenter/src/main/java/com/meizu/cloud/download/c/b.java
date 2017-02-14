package com.meizu.cloud.download.c;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.meizu.cloud.download.c.a.c;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class b {
    private static final String[] d = new String[]{"TEXT", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "REAL", "REAL", "NONE"};
    protected final a[] a;
    protected final String[] b;
    protected final String[] c;
    private final String e;
    private final boolean f;

    public static final class a implements Comparable<a> {
        public final String a;
        public final int b;
        public final boolean c;
        public final boolean d;
        public final boolean e;
        public final String f;
        public final Field g;
        public final boolean h;
        public int i;

        public /* synthetic */ int compareTo(Object obj) {
            return a((a) obj);
        }

        public a(String name, int type, boolean indexed, boolean unique, boolean fullText, String defaultValue, boolean visible, Field field, int projectionIndex) {
            this.a = name.toLowerCase();
            this.b = type;
            this.c = indexed;
            this.d = unique;
            this.e = fullText;
            this.f = defaultValue;
            this.g = field;
            this.i = projectionIndex;
            this.h = visible;
            field.setAccessible(true);
        }

        public boolean a() {
            return "_id".equals(this.a);
        }

        public int a(a another) {
            if (this.h != another.h) {
                return this.h ? -1 : 1;
            } else {
                return this.i - another.i;
            }
        }
    }

    public <T extends com.meizu.cloud.download.c.a> List<T> a(Cursor r4, com.meizu.cloud.download.c.a.b<T> r5, boolean r6) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0003 in list [B:10:0x0025]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r3 = this;
        if (r4 != 0) goto L_0x0004;
    L_0x0002:
        r1 = 0;
    L_0x0003:
        return r1;
    L_0x0004:
        r1 = new java.util.ArrayList;
        r2 = r4.getCount();
        r1.<init>(r2);
        r2 = r4.moveToFirst();	 Catch:{ all -> 0x0029 }
        if (r2 == 0) goto L_0x0023;	 Catch:{ all -> 0x0029 }
    L_0x0013:
        r0 = r5.create();	 Catch:{ all -> 0x0029 }
        r3.a(r4, r0);	 Catch:{ all -> 0x0029 }
        r1.add(r0);	 Catch:{ all -> 0x0029 }
        r2 = r4.moveToNext();	 Catch:{ all -> 0x0029 }
        if (r2 != 0) goto L_0x0013;
    L_0x0023:
        if (r6 == 0) goto L_0x0003;
    L_0x0025:
        r4.close();
        goto L_0x0003;
    L_0x0029:
        r2 = move-exception;
        if (r6 == 0) goto L_0x002f;
    L_0x002c:
        r4.close();
    L_0x002f:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.download.c.cancel.a(android.database.Cursor, com.meizu.cloud.download.c.a$cancel, boolean):java.util.List<T>");
    }

    public b(Class<? extends a> clazz) {
        int i;
        a column;
        a[] columns = b((Class) clazz);
        this.e = a((Class) clazz);
        this.a = columns;
        String[] projection = new String[0];
        boolean hasFullTextIndex = false;
        int columnNameCount = 0;
        if (columns != null) {
            projection = new String[columns.length];
            for (i = 0; i != columns.length; i++) {
                column = columns[i];
                projection[i] = column.a;
                if (column.e) {
                    hasFullTextIndex = true;
                }
                if (column.h) {
                    columnNameCount++;
                }
            }
        }
        this.b = projection;
        this.f = hasFullTextIndex;
        this.c = new String[columnNameCount];
        a[] arr$ = columns;
        int len$ = arr$.length;
        int i$ = 0;
        int i2 = 0;
        while (i$ < len$) {
            column = arr$[i$];
            if (column.h) {
                i = i2 + 1;
                this.c[i2] = column.a;
                i$++;
                i2 = i;
            } else {
                return;
            }
        }
    }

    public String a() {
        return this.e;
    }

    private void a(SQLiteDatabase db, String sql) {
        db.execSQL(sql);
    }

    public <T extends a> T a(Cursor cursor, T object) {
        try {
            for (a column : this.a) {
                int columnIndex = column.i;
                Field field = column.g;
                switch (column.b) {
                    case 0:
                        Object obj;
                        if (cursor.isNull(columnIndex)) {
                            obj = null;
                        } else {
                            obj = cursor.getString(columnIndex);
                        }
                        field.set(object, obj);
                        break;
                    case 1:
                        field.setBoolean(object, cursor.getShort(columnIndex) == (short) 1);
                        break;
                    case 2:
                        field.setShort(object, cursor.getShort(columnIndex));
                        break;
                    case 3:
                        field.setInt(object, cursor.getInt(columnIndex));
                        break;
                    case 4:
                        field.setLong(object, cursor.getLong(columnIndex));
                        break;
                    case 5:
                        field.setFloat(object, cursor.getFloat(columnIndex));
                        break;
                    case 6:
                        field.setDouble(object, cursor.getDouble(columnIndex));
                        break;
                    case 7:
                        field.set(object, cursor.isNull(columnIndex) ? null : cursor.getBlob(columnIndex));
                        break;
                    default:
                        break;
                }
            }
            return object;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void a(a object, ContentValues values) {
        try {
            for (a column : this.a) {
                String columnName = column.a;
                Field field = column.g;
                switch (column.b) {
                    case 0:
                        values.put(columnName, (String) field.get(object));
                        break;
                    case 1:
                        values.put(columnName, Boolean.valueOf(field.getBoolean(object)));
                        break;
                    case 2:
                        values.put(columnName, Short.valueOf(field.getShort(object)));
                        break;
                    case 3:
                        values.put(columnName, Integer.valueOf(field.getInt(object)));
                        break;
                    case 4:
                        values.put(columnName, Long.valueOf(field.getLong(object)));
                        break;
                    case 5:
                        values.put(columnName, Float.valueOf(field.getFloat(object)));
                        break;
                    case 6:
                        values.put(columnName, Double.valueOf(field.getDouble(object)));
                        break;
                    case 7:
                        values.put(columnName, (byte[]) field.get(object));
                        break;
                    default:
                        break;
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Cursor a(SQLiteDatabase db, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(this.e, this.b, selection, selectionArgs, groupBy, having, orderBy);
    }

    public Cursor a(SQLiteDatabase db) {
        return db.query(this.e, this.b, null, null, null, null, null);
    }

    public <T extends a> List<T> a(SQLiteDatabase db, com.meizu.cloud.download.c.a.b<T> creator) {
        return a(a(db), (com.meizu.cloud.download.c.a.b) creator, true);
    }

    public boolean a(SQLiteDatabase db, long id, a entry) {
        SQLiteDatabase sQLiteDatabase = db;
        Cursor cursor = sQLiteDatabase.query(this.e, this.b, "_id=?", new String[]{Long.toString(id)}, null, null, null);
        boolean success = false;
        if (cursor.moveToFirst()) {
            a(cursor, entry);
            success = true;
        }
        cursor.close();
        return success;
    }

    public long a(SQLiteDatabase db, a entry) {
        ContentValues values = new ContentValues();
        a(entry, values);
        if (entry.mId == 0) {
            values.remove("_id");
        }
        long id = db.replace(this.e, "_id", values);
        entry.mId = id;
        return id;
    }

    public <T extends a> void a(SQLiteDatabase db, List<T> entries) {
        ContentValues values = new ContentValues();
        for (T entry : entries) {
            a((a) entry, values);
            if (entry.mId == 0) {
                values.remove("_id");
            }
            entry.mId = db.replace(this.e, "_id", values);
        }
    }

    public <T extends a> void a(SQLiteDatabase db, List<T> entries, boolean useTransaction) {
        if (useTransaction) {
            db.beginTransaction();
            try {
                a(db, (List) entries);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } else {
            a(db, (List) entries);
        }
    }

    public boolean a(SQLiteDatabase db, long id) {
        return db.delete(this.e, "_id=?", new String[]{Long.toString(id)}) == 1;
    }

    public void b(SQLiteDatabase db) {
        String tableName = this.e;
        h.a(tableName != null);
        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        sql.append(tableName);
        sql.append(" (_id INTEGER PRIMARY KEY AUTOINCREMENT");
        StringBuilder unique = new StringBuilder();
        for (a column : this.a) {
            if (!column.a()) {
                sql.append(',');
                sql.append(column.a);
                sql.append(' ');
                sql.append(d[column.b]);
                if (!TextUtils.isEmpty(column.f)) {
                    sql.append(" DEFAULT ");
                    sql.append(column.f);
                }
                if (column.d) {
                    if (unique.length() == 0) {
                        unique.append(column.a);
                    } else {
                        unique.append(',').append(column.a);
                    }
                }
            }
        }
        if (unique.length() > 0) {
            sql.append(",UNIQUE(").append(unique).append(')');
        }
        sql.append(");");
        a(db, sql.toString());
        sql.setLength(0);
        for (a column2 : this.a) {
            if (column2.c) {
                sql.append("CREATE INDEX ");
                sql.append(tableName);
                sql.append("_index_");
                sql.append(column2.a);
                sql.append(" ON ");
                sql.append(tableName);
                sql.append(" (");
                sql.append(column2.a);
                sql.append(");");
                a(db, sql.toString());
                sql.setLength(0);
            }
        }
        if (this.f) {
            String ftsTableName = tableName + "_fulltext";
            sql.append("CREATE VIRTUAL TABLE ");
            sql.append(ftsTableName);
            sql.append(" USING FTS3 (_id INTEGER PRIMARY KEY");
            for (a column22 : this.a) {
                if (column22.e) {
                    String columnName = column22.a;
                    sql.append(',');
                    sql.append(columnName);
                    sql.append(" TEXT");
                }
            }
            sql.append(");");
            a(db, sql.toString());
            sql.setLength(0);
            StringBuilder insertSql = new StringBuilder("INSERT OR REPLACE INTO ");
            insertSql.append(ftsTableName);
            insertSql.append(" (_id");
            for (a column222 : this.a) {
                if (column222.e) {
                    insertSql.append(',');
                    insertSql.append(column222.a);
                }
            }
            insertSql.append(") VALUES (new._id");
            for (a column2222 : this.a) {
                if (column2222.e) {
                    insertSql.append(",new.");
                    insertSql.append(column2222.a);
                }
            }
            insertSql.append(");");
            String insertSqlString = insertSql.toString();
            sql.append("CREATE TRIGGER ");
            sql.append(tableName);
            sql.append("_insert_trigger AFTER INSERT ON ");
            sql.append(tableName);
            sql.append(" FOR EACH ROW BEGIN ");
            sql.append(insertSqlString);
            sql.append("END;");
            a(db, sql.toString());
            sql.setLength(0);
            sql.append("CREATE TRIGGER ");
            sql.append(tableName);
            sql.append("_update_trigger AFTER UPDATE ON ");
            sql.append(tableName);
            sql.append(" FOR EACH ROW BEGIN ");
            sql.append(insertSqlString);
            sql.append("END;");
            a(db, sql.toString());
            sql.setLength(0);
            sql.append("CREATE TRIGGER ");
            sql.append(tableName);
            sql.append("_delete_trigger AFTER DELETE ON ");
            sql.append(tableName);
            sql.append(" FOR EACH ROW BEGIN DELETE FROM ");
            sql.append(ftsTableName);
            sql.append(" WHERE _id = old._id; END;");
            a(db, sql.toString());
            sql.setLength(0);
        }
    }

    public void c(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(this.e);
        sql.append(";");
        a(db, sql.toString());
    }

    private String a(Class<? extends Object> clazz) {
        c table = (c) clazz.getAnnotation(c.class);
        if (table == null) {
            return null;
        }
        return table.a();
    }

    private a[] b(Class<? extends Object> clazz) {
        ArrayList columns = new ArrayList();
        Class clazz2;
        while (clazz2 != null) {
            a(clazz2, columns);
            clazz2 = clazz2.getSuperclass();
        }
        Collections.sort(columns);
        int i = 0;
        Iterator i$ = columns.iterator();
        while (i$.hasNext()) {
            int i2 = i + 1;
            ((a) i$.next()).i = i;
            i = i2;
        }
        return (a[]) columns.toArray(new a[columns.size()]);
    }

    private void a(Class<? extends Object> clazz, ArrayList<a> columns) {
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i != fields.length; i++) {
            Field field = fields[i];
            com.meizu.cloud.download.c.a.a info = (com.meizu.cloud.download.c.a.a) field.getAnnotation(com.meizu.cloud.download.c.a.a.class);
            if (info != null) {
                int type;
                Class<?> fieldType = field.getType();
                if (fieldType == String.class) {
                    type = 0;
                } else if (fieldType == Boolean.TYPE) {
                    type = 1;
                } else if (fieldType == Short.TYPE) {
                    type = 2;
                } else if (fieldType == Integer.TYPE) {
                    type = 3;
                } else if (fieldType == Long.TYPE) {
                    type = 4;
                } else if (fieldType == Float.TYPE) {
                    type = 5;
                } else if (fieldType == Double.TYPE) {
                    type = 6;
                } else if (fieldType == byte[].class) {
                    type = 7;
                } else {
                    throw new IllegalArgumentException("Unsupported field type for column: " + fieldType.getName());
                }
                ArrayList<a> arrayList = columns;
                arrayList.add(new a(info.a(), type, info.b(), info.e(), info.c(), info.d(), info.f(), field, columns.size()));
            }
        }
    }
}
