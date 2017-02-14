package com.meizu.cloud.app.request;

import android.content.Context;
import com.meizu.cloud.app.core.x;

public class RequestConstants {
    public static final String ACCOUNT_SCORE = "https://jifen.flyme.cn/igrowth/oauth/account/getGrowthInfo";
    public static final String ADD_COMMENTS = "/oauth/evaluate/add";
    public static final String ADD_COMMENT_PERMISSIONS = "/oauth/evaluate/available";
    public static final String ADD_FAVORITE = "/oauth/favorite/add";
    public static final String AD_STATISTICS_CLICK = "/stats/ad/click";
    public static final String AD_STATISTICS_EXPOSURE = "/stats/ad/exposure";
    public static final String AD_STATISTICS_LAND = "/stats/ad/land";
    public static final String APP_BUSINESS = "/apps";
    public static final String APP_CENTER_HOST = "http://api-app.meizu.com";
    public static final String APP_CENTER_HTTPS_HOST = "https://api-app.meizu.com";
    public static final String APP_DETAIL_PATH_URL = "/apps/public/detail/";
    public static final String APP_DEVELOPER_OTHERS = "/public/developers/%d/apps";
    public static final String APP_LICENSE_LOAD = "http://api-app.meizu.com/apps/public/license/load";
    public static final String CENTER_HOME = "/public/index";
    public static final String CHECK_DEVICE_QUALIFICATION = "/public/order/check_device_qualification";
    public static final String CHECK_ORDER = "/public/order/check";
    public static final String CHECK_PRODUCT_ORDER = "/public/product/order/check";
    public static final String CHECK_UPDATE = "/public/history/check_update";
    public static final String CHECK_USER_QUALIFICATION = "/public/order/check_user_qualification";
    public static final int CODE_APP_NOT_FOUND = 123001;
    public static final int CODE_APP_SIGN_ERROR = 198334;
    public static final int CODE_SERVER_ERROR = 110000;
    public static final String COMMENT_ONESTAR_CATEGORY = "/public/evaluate/category/list";
    public static final String CPD_APP_AD_DETAIL = "/track/public/detail";
    public static final String CPD_APP_AD_EXPOSURE = "/track/public/exposure";
    public static final String CPD_APP_AD_INSTALL = "/track/public/install";
    public static final String CPD_APP_CENTER_HOST = "https://t-e.flyme.cn";
    public static final String CREATE_APP_PRODUCT_ORDER = "/public/product/order/add";
    public static final String CREATE_ORDER = "/public/order/add";
    public static final String DELETE_HISTORY = "/oauth/history/del";
    public static final String DETAILS_CHECK = "/public/detail/";
    public static final String DETAILS_CHECK_FOOTER = "/dynamic";
    public static final String DETAILS_COMMENT_ADD_PRAISE = "/public/evaluate/like/";
    public static final String DETAIL_URL = "/public/detail/";
    public static final String DOWNLOAD_CALLBACK = "/public/download/callback";
    public static final String EVALUATE = "/public/evaluate/list";
    public static final String FEEDBACK_PUSH_MSG = "/public/push_msg/click";
    public static final String FREE_DOWNLOAD = "/public/download";
    public static final String GAME_BUSINESS = "/games";
    public static final String GAME_CENTER_HOST = "http://api-game.meizu.com";
    public static final String GAME_CENTER_HTTPS_HOST = "https://api-game.meizu.com";
    public static final String GAME_DETAIL_PATH_URL = "/games/public/detail/";
    public static final String GAME_DEVELOPER_OTHERS = "/public/developers/%d/games";
    public static final String GAME_GIFT_CENTER = "/public/gift/list";
    public static final String GAME_GIFT_DETAIL = "/public/gift/detail/";
    public static final String GAME_GIFT_LIST = "/public/gift/%d/list";
    public static final String GAME_GIFT_MY = "/oauth/user/gift/list";
    public static final String GAME_GIFT_RECEIVE = "/oauth/user/gift/receive/%d";
    public static final String GAME_GIFT_RECOMMEND = "/public/gift/recommend/list";
    public static final String GET_APP_PRODUCT = "/public/product/ls";
    public static final String GET_DETAIL_URL = "http://api-app.meizu.com/apps/public/plugin/upgrade";
    public static final String GET_EXTERNAL_APP_URL = "/public/external/redirect";
    public static final String GET_HISTORY = "/oauth/history";
    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String LOTTERY_REPORT = "/oauth/activity/task/do/%s";
    public static final String LOTTERY_REQUEST = "/oauth/activity/zippo/do/%s";
    public static final String MAIN_CAT = "/public/category/layout";
    public static final String MEMBER_ACCOUNT_INFO = "https://member.meizu.com/uc/oauth/member/getDetail";
    public static final String MIME_SEARCH = "/public/search/mimetype";
    public static final String MINE_FAVORITE = "/oauth/favorite/list";
    public static final String PATCH_DOWNLOAD = "/public/patch/download/";
    public static final String PAY_ACCOUNT_BALANCE = "https://pay.meizu.com/pay/oauth/account/get";
    public static final String PLUGIN_DATA = "/apps/public/collection/detail/desktop/5000";
    public static final String RANK_HOT_GAME = "/games/public/game/paytop";
    public static final String RELATED_RECOMMEND = "/public/detail/%d/recommendations";
    public static final String REPORT_QUESTIONS = "/oauth/problem/add";
    public static final String SEARCH = "/public/search";
    public static final String SEARCH_HOT = "/public/search/hot";
    public static final String SEARCH_HOT_LIST = "/public/search/hotlist";
    public static final String SEARCH_HOT_V2 = "/v2/public/search/hot";
    public static final String SEARCH_SUGGEST = "/public/search/suggest";
    public static final String SENSITIVE_WORDS = "/public/evaluate/sensitive_word";
    public static final String SHARE_URL = "http://app.meizu.com/phone/apps/";
    public static final String SUBMIT_HISTORY = "/oauth/history/submit";
    public static final int SUCCESS_CODE = 200;
    public static final String TRACK_AD_CLICK_INSTALL = "https://t-flow.flyme.cn/track/public/bingo";
    public static final String TRIAL_APP = "/public/order/trial";
    public static final int TYPE_NEWS = 3;
    public static final int TYPE_REVIEW = 2;
    public static final int TYPE_STRATEGY = 1;
    public static final String VERSION_HISTORY = "/public/version/list/%d";
    public static final String VERSION_HISTORY_DOWNLOAD = "/public/version/download/%d";

    public static String getRuntimeDomainUrl(Context context, String businessUrl) {
        String host;
        if (x.b(context)) {
            if (businessUrl == null || !(businessUrl.contains("oauth") || businessUrl.contains("order"))) {
                host = GAME_CENTER_HOST;
            } else {
                host = GAME_CENTER_HTTPS_HOST;
            }
            return host + GAME_BUSINESS + businessUrl;
        } else if (!x.a(context)) {
            return null;
        } else {
            if (businessUrl == null || !(businessUrl.contains("oauth") || businessUrl.contains("order"))) {
                host = APP_CENTER_HOST;
            } else {
                host = APP_CENTER_HTTPS_HOST;
            }
            return host + APP_BUSINESS + businessUrl;
        }
    }
}
