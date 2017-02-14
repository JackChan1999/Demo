package com.meizu.cloud.app.request.structitem;

import android.os.Parcelable;
import com.alibaba.fastjson.JSONObject;
import com.meizu.cloud.download.c.a;
import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public abstract class AbstractStrcutItem extends a implements Parcelable, Serializable {
    public int block_id;
    public String block_name;
    public String block_type;
    @NotJsonColumn
    public String coverUrl;
    public String cur_page;
    public boolean is_uxip_exposured;
    public boolean more;
    @a.a(a = "name", e = true)
    @com.google.gson.a.a
    public String name = "";
    public int pos_hor;
    public int pos_ver;
    public long profile_id;
    public int topic_id;
    public String topic_name;
    public String type;
    @a.a(a = "url", e = true)
    @com.google.gson.a.a
    public String url;

    public interface Columns {
        public static final String NAME = "name";
        public static final String URL = "url";
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NotJsonColumn {
    }

    public static Object createFromJson(Class<?> clsz, JSONObject jsonObject) {
        try {
            Object object = clsz.getConstructor(new Class[0]).newInstance(new Object[0]);
            for (Field field : clsz.getFields()) {
                String column = field.getName();
                Class type = field.getType();
                if (!field.isAnnotationPresent(NotJsonColumn.class) && jsonObject.containsKey(column)) {
                    if (type == String.class) {
                        field.set(object, jsonObject.getString(column));
                    } else if (type == Integer.TYPE || type == Integer.class) {
                        field.set(object, Integer.valueOf(jsonObject.getIntValue(column)));
                    } else if (type == Long.TYPE || type == Long.class) {
                        field.set(object, Long.valueOf(jsonObject.getLong(column).longValue()));
                    } else if (type == Double.TYPE || type == Double.class) {
                        field.set(object, Double.valueOf(jsonObject.getDouble(column).doubleValue()));
                    }
                }
            }
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
