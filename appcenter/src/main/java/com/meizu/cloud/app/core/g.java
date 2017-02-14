package com.meizu.cloud.app.core;

import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.utils.r;
import java.util.ArrayList;
import java.util.Iterator;

public class g {

    public static class a {
        public int a;
        public CharSequence b;
        public PendingIntent c;

        public a(int icon, CharSequence title, PendingIntent intent) {
            this.a = icon;
            this.b = title;
            this.c = intent;
        }
    }

    public static final Notification a(Context context, Bitmap largeIcon, int smallIconResId, String contentTitle, String contentText, String ticker) {
        Builder builder = new Builder(context);
        builder.setLargeIcon(largeIcon);
        builder.setSmallIcon(smallIconResId);
        builder.setContentTitle(contentTitle);
        builder.setContentText(contentText);
        if (ticker != null) {
            builder.setTicker(ticker);
        }
        return builder.build();
    }

    public static final Notification a(Context context, Drawable largeIcon, int smallIconResId, String contentTitle, String contentText, String ticker) {
        Builder builder = new Builder(context);
        builder.setLargeIcon(h.a(largeIcon));
        builder.setSmallIcon(smallIconResId);
        builder.setContentTitle(contentTitle);
        builder.setContentText(contentText);
        if (ticker != null) {
            builder.setTicker(ticker);
        }
        return builder.build();
    }

    public static final Notification a(Context context, String contentTitle, String contentText, Bitmap largeIcon, int smallIconResId, a[] actions) {
        Builder builder = new Builder(context);
        builder.setWhen(0);
        builder.setLargeIcon(largeIcon);
        builder.setSmallIcon(smallIconResId);
        builder.setContentTitle(contentTitle);
        builder.setContentText(contentText);
        builder.setStyle(new BigTextStyle().bigText(contentText));
        if (actions != null) {
            for (a action : actions) {
                builder.addAction(action.a, action.b, action.c);
            }
        }
        return builder.build();
    }

    public static final Notification a(Context context, String contentTitle, Bitmap largeIcon, int smallIconResId, ArrayList<String> appsName, String tickerText) {
        Builder builder = new Builder(context);
        builder.setLargeIcon(largeIcon);
        builder.setSmallIcon(smallIconResId);
        builder.setContentTitle(contentTitle);
        String separator = ", ";
        StringBuilder sbContentTitle = new StringBuilder();
        Iterator i$ = appsName.iterator();
        while (i$.hasNext()) {
            sbContentTitle.append(com.meizu.cloud.app.utils.g.a((String) i$.next())).append(separator);
        }
        String contentText = sbContentTitle.substring(0, sbContentTitle.length() - separator.length());
        builder.setContentText(contentText);
        if (!TextUtils.isEmpty(tickerText)) {
            builder.setTicker(tickerText);
        }
        builder.setStyle(new BigTextStyle().bigText(contentText));
        return builder.build();
    }

    public static final Notification a(Context context, String contentTitle, String size, String speed, String time, Bitmap largeIcon, int smallIconResId, boolean hasProgressBar, int max, int progress) {
        Builder builder = new Builder(context);
        builder.setWhen(0);
        builder.setLargeIcon(largeIcon);
        builder.setSmallIcon(smallIconResId);
        builder.setContentTitle(contentTitle);
        builder.setContentText(String.format("%s   %s   %s", new Object[]{size, speed, time}));
        if (hasProgressBar) {
            builder.setProgress(max, progress, false);
            r.a(context, builder);
        }
        return builder.build();
    }

    public static final Notification a(Context context, String contentTitle, String contentText, Bitmap largeIcon, int smallIconResId, boolean hasProgressBar, int max, int progress) {
        Builder builder = new Builder(context);
        builder.setWhen(0);
        builder.setLargeIcon(largeIcon);
        builder.setSmallIcon(smallIconResId);
        builder.setContentTitle(contentTitle);
        builder.setContentText(contentText);
        if (hasProgressBar) {
            builder.setProgress(max, progress, false);
            r.a(context, builder);
        }
        return builder.build();
    }

    public static final Notification a(Context context, String contentTitle, String contentText, Bitmap largeIcon, int smallIconResId) {
        Builder builder = new Builder(context);
        builder.setWhen(0);
        builder.setLargeIcon(largeIcon);
        builder.setSmallIcon(smallIconResId);
        builder.setContentTitle(contentTitle);
        builder.setContentText(contentText);
        builder.setProgress(100, 100, true);
        r.a(context, builder);
        return builder.build();
    }
}
