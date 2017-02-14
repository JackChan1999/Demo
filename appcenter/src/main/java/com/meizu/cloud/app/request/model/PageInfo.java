package com.meizu.cloud.app.request.model;

import com.meizu.cloud.pushsdk.constants.PushConstants;

public class PageInfo {
    public String name;
    public String page_type;
    public String type;
    public String url;

    public enum PageType {
        FEED("feed"),
        RANK("rank"),
        SPECIAL("special"),
        ACTIVITY(PushConstants.INTENT_ACTIVITY_NAME),
        CATEGORY("category"),
        MINE("mine");
        
        private String type;

        public String getType() {
            return this.type;
        }

        private PageType(String string) {
            this.type = string;
        }
    }
}
