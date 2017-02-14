package com.meizu.flyme.appcenter.desktopplugin.b;

import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.n;
import com.android.volley.n.b;
import com.android.volley.s;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.request.model.PluginDataResultModel;
import com.meizu.cloud.app.request.model.PluginItem;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.flyme.appcenter.desktopplugin.view.PluginActivity;
import com.meizu.mstore.R;
import com.meizu.volley.b.a;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class c {
    public static String d;
    Bitmap a;
    int b = 0;
    Boolean c = Boolean.valueOf(true);

    public static boolean a(Context context, boolean bOn) {
        d = "精选应用";
        Intent intent = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER");
        intent.setClass(context, PluginActivity.class);
        Intent shortcut;
        if (!bOn) {
            shortcut = new Intent("com.meizu.flyme.launcher.action.UNINSTALL_SHORTCUT");
            shortcut.putExtra("android.intent.extra.shortcut.NAME", d);
            shortcut.putExtra("android.intent.extra.shortcut.INTENT", intent);
            context.sendBroadcast(shortcut);
        } else if (!a(context)) {
            shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            shortcut.putExtra("android.intent.extra.shortcut.NAME", d);
            shortcut.putExtra("duplicate", false);
            shortcut.putExtra("android.intent.extra.shortcut.INTENT", intent);
            shortcut.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(context, R.drawable.plugin_default));
            context.sendBroadcast(shortcut);
        }
        return true;
    }

    public static void a(Context conetext, Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream((bitmap.getWidth() * bitmap.getHeight()) * 4);
            try {
                bitmap.compress(CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch (IOException e) {
                Log.w("Favorite", "Could not write icon");
            }
            Intent intent = new Intent("com.meizu.flyme.launcher.CHANGEICON");
            intent.putExtra("changeType", 0);
            intent.putExtra("itemType", 1);
            intent.putExtra("iconPackage", "com.meizu.mstore");
            intent.putExtra("icon", out.toByteArray());
            conetext.sendBroadcast(intent);
        }
    }

    public void a(final Context context, final int max) {
        List params = new ArrayList();
        params.add(new a("start", String.valueOf(0)));
        params.add(new a("max", String.valueOf(max + 18)));
        params.add(new a("mp", RequestManager.VALUE_APPS_FLYME5));
        l mRequest = new FastJsonRequest(new TypeReference<ResultModel<PluginDataResultModel<PluginItem>>>(this) {
            final /* synthetic */ c a;

            {
                this.a = r1;
            }
        }, "http://api-app.meizu.com/apps/public/collection/detail/desktop/5000", 0, params, new b<ResultModel<PluginDataResultModel<PluginItem>>>(this) {
            final /* synthetic */ c c;

            public void a(ResultModel<PluginDataResultModel<PluginItem>> response) {
                if (response != null && response.getCode() == 200 && response.getValue() != null && ((PluginDataResultModel) response.getValue()).data != null && ((PluginDataResultModel) response.getValue()).data.size() > 0) {
                    this.c.c = Boolean.valueOf(((PluginDataResultModel) response.getValue()).more);
                    List<PluginItem> dataList = ((PluginDataResultModel) response.getValue()).data;
                    List<PackageInfo> installed = i.b(context, 2);
                    for (int i = 0; i < installed.size(); i++) {
                        for (int j = dataList.size(); j > 0; j--) {
                            if (((PackageInfo) installed.get(i)).packageName.equals(((PluginItem) dataList.get(j - 1)).getPackage_name())) {
                                dataList.remove(j - 1);
                            }
                        }
                    }
                    if (dataList.size() >= 9) {
                        this.c.a(context, (List) dataList);
                    } else if (this.c.c.booleanValue()) {
                        this.c.a(context, max + 18);
                    } else {
                        this.c.a(context, (List) dataList);
                    }
                }
            }
        }, new n.a(this) {
            final /* synthetic */ c a;

            {
                this.a = r1;
            }

            public void a(s error) {
                a.a(error + "");
            }
        });
        mRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(context));
        com.meizu.volley.b.a(context).a().a(mRequest);
    }

    private void a(Context context, List<PluginItem> list) {
        BitmapDrawable bd;
        if (list.size() == 0) {
            bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.plugin_noapp_ic, null);
            if (bd != null) {
                this.a = bd.getBitmap();
                a(context, this.a);
                return;
            }
            return;
        }
        final float PLUGIN_DESKTOPICON_FIRSTICON_MARGINTOPORLEFT = context.getResources().getDimension(R.dimen.plugin_desktopicon_firsticon_marginleftortop);
        final float PLUGIN_DESKTOPICON_ICON_INTERVAL = context.getResources().getDimension(R.dimen.plugin_desktopicon_icon_interval);
        final float PLUGIN_DESKTOPICON_ICON_WIDTHORHEIGHT = context.getResources().getDimension(R.dimen.plugin_desktopicon_icon_widthorheight);
        final Bitmap errorBitmap = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.plugin_appdefault_ic_noflyme, null)).getBitmap();
        bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.desktop_icon_bg, null);
        if (bd != null) {
            Bitmap bitmapBg = bd.getBitmap();
            ColorFilter porterDuffColorFilter = new PorterDuffColorFilter(com.meizu.cloud.app.utils.s.d(context), Mode.SRC_ATOP);
            Paint paint = new Paint();
            paint.setColorFilter(porterDuffColorFilter);
            this.a = Bitmap.createBitmap(bitmapBg.getWidth(), bitmapBg.getHeight(), Config.ARGB_8888);
            final Canvas canvas = new Canvas(this.a);
            canvas.drawBitmap(bitmapBg, 0.0f, 0.0f, paint);
            final int iconNumber = list.size() < 9 ? list.size() : 9;
            for (int i = 0; i < iconNumber; i++) {
                final ImageView bitmapImageView = new ImageView(context);
                final int finalI = i;
                if (((PluginItem) list.get(i)).isIconError()) {
                    canvas.drawBitmap(com.meizu.flyme.appcenter.desktopplugin.presenter.b.a(context.getApplicationContext(), errorBitmap), ((((float) (finalI % 3)) * PLUGIN_DESKTOPICON_ICON_WIDTHORHEIGHT) + PLUGIN_DESKTOPICON_FIRSTICON_MARGINTOPORLEFT) + (((float) (finalI % 3)) * PLUGIN_DESKTOPICON_ICON_INTERVAL), ((((float) (finalI / 3)) * PLUGIN_DESKTOPICON_ICON_WIDTHORHEIGHT) + PLUGIN_DESKTOPICON_FIRSTICON_MARGINTOPORLEFT) + (((float) (finalI / 3)) * PLUGIN_DESKTOPICON_ICON_INTERVAL), null);
                    this.b++;
                    if (this.b == iconNumber) {
                        a(context, this.a);
                    }
                } else {
                    final Context context2 = context;
                    Picasso.with(context).load(((PluginItem) list.get(i)).getIcon()).into(bitmapImageView, new Callback(this) {
                        final /* synthetic */ c j;

                        public void onSuccess() {
                            canvas.drawBitmap(com.meizu.flyme.appcenter.desktopplugin.presenter.b.a(context2.getApplicationContext(), ((BitmapDrawable) bitmapImageView.getDrawable()).getBitmap()), (PLUGIN_DESKTOPICON_FIRSTICON_MARGINTOPORLEFT + (((float) (finalI % 3)) * PLUGIN_DESKTOPICON_ICON_WIDTHORHEIGHT)) + (((float) (finalI % 3)) * PLUGIN_DESKTOPICON_ICON_INTERVAL), (PLUGIN_DESKTOPICON_FIRSTICON_MARGINTOPORLEFT + (((float) (finalI / 3)) * PLUGIN_DESKTOPICON_ICON_WIDTHORHEIGHT)) + (((float) (finalI / 3)) * PLUGIN_DESKTOPICON_ICON_INTERVAL), null);
                            c cVar = this.j;
                            cVar.b++;
                            if (this.j.b == iconNumber) {
                                c.a(context2, this.j.a);
                            }
                        }

                        public void onError() {
                            canvas.drawBitmap(com.meizu.flyme.appcenter.desktopplugin.presenter.b.a(context2.getApplicationContext(), errorBitmap), (PLUGIN_DESKTOPICON_FIRSTICON_MARGINTOPORLEFT + ((float) ((finalI % 3) * 30))) + (((float) (finalI % 3)) * PLUGIN_DESKTOPICON_ICON_INTERVAL), (PLUGIN_DESKTOPICON_FIRSTICON_MARGINTOPORLEFT + (((float) (finalI / 3)) * PLUGIN_DESKTOPICON_ICON_WIDTHORHEIGHT)) + (((float) (finalI / 3)) * PLUGIN_DESKTOPICON_ICON_INTERVAL), null);
                            c cVar = this.j;
                            cVar.b++;
                            if (this.j.b == iconNumber) {
                                c.a(context2, this.j.a);
                            }
                        }
                    });
                }
            }
        }
    }

    public static boolean a(Context context) {
        boolean bRet = false;
        try {
            Throwable th;
            Cursor cursor = context.getContentResolver().query(Uri.parse("content://com.meizu.flyme.launcher.settings/favorites?notify=true"), null, "intent like ?", new String[]{"%component=com.meizu.mstore/com.meizu.flyme.appcenter.desktopplugin.view.PluginActivity;%"}, null);
            Throwable th2 = null;
            if (cursor != null) {
                try {
                    if (cursor.getCount() > 0) {
                        bRet = true;
                    } else {
                        bRet = false;
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                }
            }
            if (cursor == null) {
                return bRet;
            }
            if (null != null) {
                try {
                    cursor.close();
                    return bRet;
                } catch (Throwable x2) {
                    th2.addSuppressed(x2);
                    return bRet;
                }
            }
            cursor.close();
            return bRet;
            if (cursor != null) {
                if (th != null) {
                    try {
                        cursor.close();
                    } catch (Throwable x22) {
                        th.addSuppressed(x22);
                    }
                } else {
                    cursor.close();
                }
            }
            throw th2;
            throw th2;
        } catch (Exception e) {
            return false;
        }
    }
}
