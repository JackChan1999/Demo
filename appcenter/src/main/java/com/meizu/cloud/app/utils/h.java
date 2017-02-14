package com.meizu.cloud.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.meizu.cloud.app.widget.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.c.b.a.b;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

public class h {
    public static int a = e.image_background;
    public static int b = e.image_background;
    public static int c = e.image_background;
    public static int d = e.image_background;

    public static void a(Context context) {
        try {
            ((Cache) b.a(Picasso.with(context.getApplicationContext()), "cache")).clear();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void a(Context context, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(context.getApplicationContext()).load(url).placeholder(a).error(b).fit().into(imageView);
        }
    }

    public static void a(Context context, File file, ImageView imageView) {
        if (file != null) {
            Picasso.with(context.getApplicationContext()).load(file).placeholder(a).error(17301651).fit().into(imageView);
        }
    }

    public static void b(Context context, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(context.getApplicationContext()).load(url).placeholder(e.ic_default_avatar).error(b).fit().into(imageView);
        }
    }

    public static void c(Context context, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(d);
        } else {
            Picasso.with(context.getApplicationContext()).load(url).placeholder(c).error(d).fit().into(imageView);
        }
    }

    public static void a(Context context, String url, ImageView imageView, int radius, int margin) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(d);
        } else {
            Picasso.with(context.getApplicationContext()).load(url).transform(new c(radius, margin)).placeholder(c).error(d).fit().into(imageView);
        }
    }

    public static void a(Context context, String url, ImageView imageView, Callback callback) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(d);
        } else {
            Picasso.with(context.getApplicationContext()).load(url).placeholder(c).error(d).fit().into(imageView, callback);
        }
    }

    public static Bitmap a(Context context, String url) {
        Bitmap bitmap = null;
        try {
            bitmap = Picasso.with(context.getApplicationContext()).load(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap a(Picasso picasso, RequestCreator requestCreator, int width, int height) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
        Method method = requestCreator.getClass().getDeclaredMethod("createRequest", new Class[]{Long.TYPE});
        method.setAccessible(true);
        Object request = method.invoke(requestCreator, new Object[]{Long.valueOf(System.nanoTime())});
        Class<?> utilsClass = Class.forName("com.squareup.picasso.Utils");
        Method methodCreateKey = utilsClass.getDeclaredMethod("createKey", new Class[]{request.getClass()});
        methodCreateKey.setAccessible(true);
        String urlKey = (String) methodCreateKey.invoke(utilsClass, new Object[]{request});
        Field cacheField = picasso.getClass().getDeclaredField("cache");
        cacheField.setAccessible(true);
        Object cache = cacheField.get(picasso);
        Method cacheGetMethod = cache.getClass().getDeclaredMethod("get", new Class[]{String.class});
        String key = String.format("%sresize:%dx%d\n", new Object[]{urlKey, Integer.valueOf(width), Integer.valueOf(height)});
        return (Bitmap) cacheGetMethod.invoke(cache, new Object[]{key});
    }

    public static Bitmap a(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Drawable a(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    public static InputStream b(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.PNG, 100, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public static boolean a(FragmentActivity context) {
        String imagePath = Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + context.getPackageName() + "/" + "TempImage/";
        Bitmap bm = null;
        try {
            View targetView = context.getWindow().getDecorView();
            targetView.setSystemUiVisibility(4);
            targetView.setDrawingCacheEnabled(true);
            if (bm != null) {
                bm.recycle();
            }
            bm = a(context, targetView.getDrawingCache());
            targetView.setSystemUiVisibility(0);
            targetView.setDrawingCacheEnabled(false);
            if (bm == null) {
                return false;
            }
            File path = new File(imagePath);
            if (!path.exists()) {
                path.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(imagePath + "share_image.jpg")));
            bm.compress(CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            bm.recycle();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Bitmap a(FragmentActivity context, Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight() - ((int) context.getResources().getDimension(d.app_snapshot_sb_height));
        if (h <= 0) {
            return null;
        }
        return Bitmap.createBitmap(bitmap, 0, (int) context.getResources().getDimension(d.app_snapshot_sb_height), w, h, null, false);
    }

    public static void a(String imgUrl, File file) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(imgUrl).openConnection();
        conn.setConnectTimeout(9000);
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("请求url失败");
        }
        a(conn.getInputStream(), file);
    }

    public static void a(InputStream inSream, File file) throws Exception {
        FileOutputStream outStream = new FileOutputStream(file);
        byte[] buffer = new byte[24576];
        while (true) {
            int len = inSream.read(buffer);
            if (len != -1) {
                outStream.write(buffer, 0, len);
            } else {
                outStream.close();
                inSream.close();
                return;
            }
        }
    }

    public static String b(Context context) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + context.getPackageName() + "/" + "TempImage/");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
}
