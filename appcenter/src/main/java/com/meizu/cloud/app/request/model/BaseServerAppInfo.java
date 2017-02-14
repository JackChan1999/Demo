package com.meizu.cloud.app.request.model;

import com.meizu.cloud.app.block.Blockable;
import com.meizu.cloud.download.c.a;

public class BaseServerAppInfo extends a implements Blockable {
    @a.a(a = "auto_install", e = true)
    @com.google.gson.a.a
    public int auto_install = 1;
    @a.a(a = "category_name", e = true)
    @com.google.gson.a.a
    public String category_name;
    @a.a(a = "evaluate_count", e = true)
    @com.google.gson.a.a
    public int evaluate_count;
    @a.a(a = "icon", e = true)
    @com.google.gson.a.a
    public String icon;
    @a.a(a = "id", e = true)
    @com.google.gson.a.a
    public int id;
    @a.a(a = "name", e = true)
    @com.google.gson.a.a
    public String name;
    @a.a(a = "package_name", e = true)
    @com.google.gson.a.a
    public String package_name;
    @a.a(a = "price", e = true)
    @com.google.gson.a.a
    public double price;
    @a.a(a = "publisher", e = true)
    @com.google.gson.a.a
    public String publisher;
    @a.a(a = "size", e = true)
    @com.google.gson.a.a
    public long size;
    @a.a(a = "star", e = true)
    @com.google.gson.a.a
    public int star;
    @a.a(a = "update_description", e = true)
    @com.google.gson.a.a
    public String update_description;
    @a.a(a = "url", e = true)
    @com.google.gson.a.a
    public String url;
    @a.a(a = "version_code", e = true)
    @com.google.gson.a.a
    public int version_code;
    @a.a(a = "version_name", e = true)
    @com.google.gson.a.a
    public String version_name;

    public interface Columns {
        public static final String AUTO_INSTALL = "auto_install";
        public static final String CATEGORY_NAME = "category_name";
        public static final String EVALUATE_COUNT = "evaluate_count";
        public static final String ICON = "icon";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String PACKAGE_NAME = "package_name";
        public static final String PRICE = "price";
        public static final String PUBLISHER = "publisher";
        public static final String SIZE = "size";
        public static final String STAR = "star";
        public static final String UPDATE_DESCRIPTION = "update_description";
        public static final String URL = "url";
        public static final String VERSION_CODE = "version_code";
        public static final String VERSION_NAME = "version_name";
    }

    public Class getBlockClass() {
        return getClass();
    }
}
