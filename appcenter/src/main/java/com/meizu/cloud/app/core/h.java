package com.meizu.cloud.app.core;

import android.content.Context;
import android.content.Intent;

public enum h {
    PROGRESS,
    FINISH,
    DOWNLOAD_ERROR,
    INSTALL_ERROR;

    public static String a(Context context, h type) {
        String domain = i.a(context).metaData.getString("domain");
        if (x.a(context)) {
            switch (type) {
                case FINISH:
                    return "app_clean_finish_notify";
                case DOWNLOAD_ERROR:
                    return "app_clean_download_error_notify";
                case INSTALL_ERROR:
                    return "app_clean_install_error_notify";
                default:
                    return "";
            }
        } else if (!x.b(context)) {
            return "";
        } else {
            switch (type) {
                case FINISH:
                    return "game_clean_finish_notify";
                case DOWNLOAD_ERROR:
                    return "game_clean_download_error_notify";
                case INSTALL_ERROR:
                    return "game_clean_install_error_notify";
                default:
                    return "";
            }
        }
    }

    public static void a(Context context, String actionType) {
        context.sendBroadcast(new Intent(actionType));
    }
}
