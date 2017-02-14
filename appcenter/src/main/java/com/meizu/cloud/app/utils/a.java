package com.meizu.cloud.app.utils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import com.meizu.cloud.b.a.i;
import com.meizu.common.app.SlideNotice;
import com.meizu.common.app.SlideNotice.b;
import com.meizu.common.widget.ContentToastLayout;

public class a {
    public static void a(Context context, String string) {
        if ((context instanceof Activity) && !((Activity) context).isDestroyed()) {
            new Builder(context).setMessage(string).setPositiveButton(i.confirm, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
    }

    public static SlideNotice a(final Context context) {
        SlideNotice notice = new SlideNotice(context);
        ContentToastLayout toastLayout = new ContentToastLayout(context);
        toastLayout.setToastType(1);
        toastLayout.setText(context.getString(i.nonetwork));
        notice.setCustomView(toastLayout);
        notice.setBelowDefaultActionBar(true);
        notice.setOnClickNoticeListener(new b() {
            public void a(SlideNotice notice) {
                notice.slideToCancel();
                context.startActivity(new Intent("android.settings.WIFI_SETTINGS"));
            }
        });
        notice.slideToShow(true);
        return notice;
    }
}
