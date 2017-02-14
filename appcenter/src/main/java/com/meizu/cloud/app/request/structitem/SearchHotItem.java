package com.meizu.cloud.app.request.structitem;

import com.meizu.cloud.app.fragment.p;
import com.meizu.cloud.app.utils.param.BlockGotoPageInfo;
import com.meizu.cloud.pushsdk.constants.PushConstants;

public class SearchHotItem {
    public static final int ACTIVITY = 4;
    public static final int APP = 1;
    public static final int GIFT = 5;
    public static final int KEY = 2;
    public static final int SPECIAL = 3;
    public String bgColor;
    public BlockGotoPageInfo blockGotoPageInfo;
    public long content_id;
    public String detail_url;
    public String fontColor;
    public Tag tag;
    public String title;
    public int type = -1;

    public static class Tag {
        public String bg_color;
        public String text;
        public int type;
    }

    public void generateBlockGotoPageInfo() {
        if (this.type > 0 && this.type != 2 && this.blockGotoPageInfo == null) {
            this.blockGotoPageInfo = new BlockGotoPageInfo();
            this.blockGotoPageInfo.b = this.detail_url;
            this.blockGotoPageInfo.c = this.title;
            this.blockGotoPageInfo.i = p.SEARCH_TAG;
            switch (this.type) {
                case 1:
                    this.blockGotoPageInfo.a = PushConstants.EXTRA_APPLICATION_PENDING_INTENT;
                    return;
                case 3:
                    this.blockGotoPageInfo.a = "special";
                    return;
                case 4:
                    this.blockGotoPageInfo.a = PushConstants.INTENT_ACTIVITY_NAME;
                    return;
                case 5:
                    this.blockGotoPageInfo.a = "gift_details";
                    return;
                default:
                    return;
            }
        }
    }
}
