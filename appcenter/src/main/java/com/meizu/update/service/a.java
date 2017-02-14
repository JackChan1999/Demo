package com.meizu.update.service;

import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import com.meizu.update.UpdateInfo;
import com.meizu.update.c.d.c;
import com.meizu.update.c.d.d;
import com.meizu.update.h.g;

public class a {
    private Service a;
    private UpdateInfo b;
    private NotificationManager c = ((NotificationManager) this.a.getSystemService("notification"));
    private Builder d;

    public a(Service service, UpdateInfo info) {
        this.a = service;
        this.b = info;
    }

    public void a() {
        String title = a(this.b, this.a);
        String desc = String.format(this.a.getString(d.mzuc_notification_message_s), new Object[]{this.b.mVersionDesc});
        Builder builder = new Builder(this.a);
        builder.setTicker(title);
        builder.setContentTitle(title);
        builder.setContentText(desc);
        builder.setContentIntent(h());
        builder.setAutoCancel(true);
        a(builder, g());
        a(builder);
        if (com.meizu.update.h.d.c()) {
            BigTextStyle bigStyle = new BigTextStyle();
            bigStyle.bigText(desc);
            bigStyle.setBigContentTitle(title);
            builder.setStyle(bigStyle);
            builder.setContentInfo(null);
            if (!this.b.mNeedUpdate) {
                builder.addAction(0, this.a.getString(d.mzuc_skip_version), j());
            }
            builder.addAction(0, this.a.getString(d.mzuc_update_immediately), i());
        }
        this.c.notify(100, b(builder));
        com.meizu.update.g.a.a(this.a).a(com.meizu.update.g.a.a.UpdateDisplay_Notification, this.b.mVersionName, g.b(this.a, this.a.getPackageName()));
    }

    public void b() {
        String title = b(this.b, this.a);
        String desc = this.a.getString(d.mzuc_installing);
        Builder builder = new Builder(this.a);
        builder.setTicker(title);
        builder.setContentTitle(title);
        builder.setContentText(desc);
        builder.setOngoing(true);
        builder.setWhen(0);
        a(builder, g());
        a(builder);
        this.c.notify(100, b(builder));
    }

    public void c() {
        n();
        a(this.a.getString(d.mzuc_download_fail), k());
    }

    public void d() {
        a(this.a.getString(d.mzuc_install_fail), l());
    }

    public void e() {
        this.c.cancel(100);
        Intent intent = com.meizu.update.f.a.a(this.a);
        if (intent == null) {
            intent = new Intent();
        }
        a(this.a.getString(d.mzuc_update_finish), PendingIntent.getActivity(this.a, 0, intent, 134217728), 101);
    }

    public void a(String path) {
        a(this.a.getString(d.mzuc_download_finish_install), b(path));
    }

    public void a(int progress, long speed) {
        String title = b(this.b, this.a);
        String speedStr = g.a((double) speed) + "/S";
        String desc = String.format(this.a.getString(d.mzuc_download_progress_desc_s), new Object[]{speedStr, this.b.mSize});
        if (this.d == null) {
            a(title, desc, progress);
            this.a.startForeground(100, b(this.d));
            return;
        }
        this.d.setContentText(desc);
        this.d.setProgress(100, progress, false);
        this.c.notify(100, b(this.d));
    }

    private void a(String title, String desc, int progress) {
        Builder builder = new Builder(this.a);
        builder.setTicker(title);
        builder.setContentTitle(title);
        builder.setContentText(desc);
        builder.setContentIntent(m());
        builder.setOngoing(true);
        builder.setWhen(0);
        builder.setProgress(100, progress, false);
        a(builder, g());
        a(builder);
        a(this.a, builder);
        this.d = builder;
    }

    public void f() {
        n();
        this.c.cancel(100);
    }

    public static void a(Context context) {
        ((NotificationManager) context.getSystemService("notification")).cancel(101);
    }

    private void a(String desc, PendingIntent intent) {
        a(desc, intent, 100);
    }

    private void a(String desc, PendingIntent intent, int notificationId) {
        String title = b(this.b, this.a);
        Builder builder = new Builder(this.a);
        builder.setTicker(title);
        builder.setContentTitle(title);
        builder.setContentText(desc);
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        a(builder, g());
        a(builder);
        if (intent != null) {
            builder.setContentIntent(intent);
        }
        this.c.notify(notificationId, b(builder));
    }

    public static String a(UpdateInfo info, Context context) {
        String appName = g.h(context);
        return String.format(context.getString(d.mzuc_update_title_s), new Object[]{appName, info.mVersionName});
    }

    public static String b(UpdateInfo info, Context context) {
        String appName = g.h(context);
        return String.format(context.getString(d.mzuc_update_msg_title_s), new Object[]{appName, info.mVersionName});
    }

    public Bitmap g() {
        return g.a(this.a.getPackageName(), this.a);
    }

    private void n() {
        this.a.stopForeground(true);
        this.c.cancel(100);
        this.d = null;
    }

    public PendingIntent h() {
        return MzUpdateComponentService.c(this.a, this.b);
    }

    public PendingIntent i() {
        return MzUpdateComponentService.a(this.a, this.b);
    }

    public PendingIntent j() {
        return MzUpdateComponentService.d(this.a, this.b);
    }

    public PendingIntent k() {
        return MzUpdateComponentService.e(this.a, this.b);
    }

    public PendingIntent l() {
        return MzUpdateComponentService.f(this.a, this.b);
    }

    public PendingIntent m() {
        return MzUpdateComponentService.b(this.a, this.b);
    }

    public PendingIntent b(String path) {
        return MzUpdateComponentService.a(this.a, this.b, path);
    }

    public static final void a(Builder builder, Bitmap icon) {
        builder.setSmallIcon(c.mzuc_stat_sys_update);
        if (icon != null) {
            builder.setLargeIcon(icon);
        }
    }

    public static final void a(Context context, Builder builder) {
        try {
            com.meizu.f.a.a(c(builder), "setCircleProgressBar", new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(true)});
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    public static final void a(Builder builder) {
        try {
            com.meizu.f.a.a(c(builder), "setInternalApp", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(1)});
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    private static final Object c(Builder builder) throws Exception {
        return com.meizu.f.a.a((Object) builder, builder.getClass(), "mFlymeNotificationBuilder");
    }

    public Notification b(Builder builder) {
        return com.meizu.update.h.d.a() ? builder.build() : builder.getNotification();
    }
}
