package com.google.demo.model;

import java.util.List;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/8 19:39
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class news {
    private String message;
    private ValueEntity value;
    private String redirect;
    private int code;

    public static class ValueEntity {

        private boolean more;
        private List<DataEntity> data;
        private List<CpdDataEntity> cpd_data;


        public static class DataEntity {

            public String version_name;
            public String icon;
            public String icon_webp;
            public int star;
            public String package_name;
            public int evaluate_count;
            public int download_count;
            public String recommend_desc;
            public int type;
            public String url;
            public int version_code;
            public int size;
            public String publisher;
            public int id;
            public int download7day_count;
            public double price;
            public String category_name;
            public String name;

        }

        public static class CpdDataEntity {

            public String version_name;
            public String icon;
            public String icon_webp;
            public int star;
            public String package_name;
            public int unit_id;
            public int evaluate_count;
            public String recommend_desc;
            public int download_count;
            public String url;
            public int version_code;
            public int size;
            public String publisher;
            public int id;
            public int download7day_count;
            public String request_id;
            public double price;
            public String category_name;
            public String name;
            public int package_id;

        }
    }
}
