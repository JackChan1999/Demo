package com.meizu.cloud.app.request.model;

import com.meizu.cloud.app.fragment.p;

public class RankPageInfo {
    public static final int STYLE_CATEGORY_SIZE_DL = 1;
    public static final int STYLE_INDEX_SIZE_DL = 5;
    public static final int STYLE_RECOMMEND_SIZE_DL = 2;
    public static final int STYLE_SIZE_DL = 6;
    public static final int STYLE_STAR_CATEGORY_SIZE = 4;
    public static final int STYLE_STAR_SIZE_DL = 3;
    public String name;
    public String page_type;
    public String type;
    public String url;

    public enum RankPageType {
        DEFAULT("default", 4),
        RECOMMEND("recommend", 2),
        SEARCH(p.SEARCH_TAG, 3),
        APP_SEARCH("app_search", 6),
        APP_CATEGORY("app_category", 6),
        APP_SORT("app_sort", 5),
        INDEX("index", 1),
        ONLINE("online", 1),
        SOLO("solo", 1),
        REPUTATION("reputable", 3);
        
        private String pageType;
        private int style;

        private RankPageType(String pageType, int style) {
            this.pageType = pageType;
            this.style = style;
        }

        public static final RankPageType instance(String pageType) {
            for (RankPageType rankPageType : values()) {
                if (rankPageType.pageType.equals(pageType)) {
                    return rankPageType;
                }
            }
            return DEFAULT;
        }

        public String getType() {
            return this.pageType;
        }

        public int getStyle() {
            return this.style;
        }
    }
}
