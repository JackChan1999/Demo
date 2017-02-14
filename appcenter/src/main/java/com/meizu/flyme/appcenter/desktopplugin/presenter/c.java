package com.meizu.flyme.appcenter.desktopplugin.presenter;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import com.meizu.cloud.app.fragment.p;
import com.meizu.cloud.app.utils.s;
import com.meizu.cloud.app.utils.u;
import com.meizu.flyme.appcenter.desktopplugin.view.b;
import com.meizu.flyme.appcenter.fragment.AppSearchFragment;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class c {
    public static String a = "PluginSurfacePresenter";
    private b b;
    private d c;

    public void a(b view) {
        this.b = view;
        this.c = new d(this.b.a());
    }

    public void a() {
        this.b = null;
    }

    public void b() {
        Uri uri = Uri.parse("market://search?q=");
        Intent intent = new Intent();
        intent.setPackage(this.b.a().getPackageName());
        intent.setData(uri);
        intent.setAction("android.intent.action.VIEW");
        Bundle bundle = new Bundle();
        bundle.putBoolean(p.SHOW_KEYBOARD, true);
        bundle.putBoolean(p.CLEAR_TASK, true);
        bundle.putString(AppSearchFragment.EXTRA_SEARCH, "");
        intent.putExtras(bundle);
        this.b.a().startActivity(intent);
        com.meizu.cloud.statistics.b.a().a("jxcj_search", "plugin", null);
    }

    public Drawable c() {
        Drawable drawable;
        if (s.c(this.b.a()).booleanValue() || s.e(this.b.a())) {
            drawable = d();
        } else if (s.f(this.b.a())) {
            drawable = a(this.b.a().getCacheDir() + File.separator + "plugin" + File.separator + "bg.png");
            if (drawable == null) {
                drawable = d();
            }
        } else {
            drawable = d();
        }
        s.a(this.b.a(), false);
        return drawable;
    }

    private Drawable d() {
        final Bitmap blurBitmap = u.a(((BitmapDrawable) WallpaperManager.getInstance(this.b.a()).getDrawable()).getBitmap(), Color.parseColor("#33ffffff"));
        Drawable drawable = new BitmapDrawable(null, blurBitmap);
        new Thread(new Runnable(this) {
            final /* synthetic */ c b;

            public void run() {
                this.b.a(blurBitmap, "bg.png");
            }
        }).start();
        return drawable;
    }

    public void a(Bitmap bmp, String fileName) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bmp.compress(CompressFormat.PNG, 100, bytes);
            if (this.b != null && this.b.a() != null) {
                File f = new File(this.b.a().getCacheDir() + File.separator + "plugin" + File.separator + fileName);
                if (!f.getParentFile().exists()) {
                    f.getParentFile().mkdirs();
                }
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                fo.close();
                s.b(this.b.a(), true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Drawable a(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
            return new BitmapDrawable(bitmap);
        } catch (FileNotFoundException e) {
            return d();
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
