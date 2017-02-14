package com.meizu.cloud.app.share;

import android.content.Context;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.fragment.d;
import com.meizu.cloud.app.request.model.AppStructDetailsItem;
import com.meizu.cloud.app.utils.h;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;

public class b {
    private static final char[] f = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private Context a;
    private AppStructDetailsItem b;
    private Fragment c;
    private Boolean d;
    private a e = new a(this);

    class a implements Target {
        final /* synthetic */ b a;

        a(b bVar) {
            this.a = bVar;
        }

        public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
            WXMediaMessage msg = this.a.a(this.a.a, this.a.b);
            msg.thumbData = b.a(Bitmap.createScaledBitmap(bitmap, 90, 90, true), true);
            Req req = new Req();
            req.transaction = b.a("webpage");
            req.message = msg;
            if (this.a.d.booleanValue()) {
                req.scene = 0;
            } else {
                req.scene = 1;
            }
            ((d) this.a.c).g().sendReq(req);
        }

        public void onBitmapFailed(Drawable errorDrawable) {
        }

        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    }

    public b(Context context, Fragment fragment, AppStructDetailsItem appStructDetailsItem, Boolean isToFriend) {
        this.a = context;
        this.b = appStructDetailsItem;
        this.c = fragment;
        this.d = isToFriend;
    }

    private WXMediaMessage a(Context context, AppStructDetailsItem appStructDetailsItem) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = appStructDetailsItem.h5_detail_url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = appStructDetailsItem.name;
        String description = appStructDetailsItem.recommend_desc;
        if (TextUtils.isEmpty(description)) {
            if (x.b(context)) {
                description = "这个游戏挺好玩的，你也来试试吧！";
            } else {
                description = "这个应用挺好玩的，你也来试试吧！";
            }
        }
        msg.description = description;
        return msg;
    }

    public void a() {
        Picasso picasso = Picasso.with(this.a.getApplicationContext());
        try {
            Bitmap bitmap = h.a(picasso, picasso.load(this.b.icon).fit(), this.a.getResources().getDimensionPixelSize(com.meizu.cloud.b.a.d.app_info_image_width), this.a.getResources().getDimensionPixelSize(com.meizu.cloud.b.a.d.app_info_image_width));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (ClassNotFoundException e4) {
            e4.printStackTrace();
        } catch (NoSuchFieldException e5) {
            e5.printStackTrace();
        }
        picasso.load(this.b.icon).into(this.e);
    }

    public static String a(String type) {
        return type == null ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static String a(byte[] data) {
        int l = data.length;
        char[] out = new char[(l << 1)];
        int j = 0;
        for (int i = 0; i < l; i++) {
            int i2 = j + 1;
            out[j] = f[(data[i] & 240) >>> 4];
            j = i2 + 1;
            out[i2] = f[data[i] & 15];
        }
        return new String(out);
    }

    public static String b(byte[] original) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(original);
            return a(md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] a(Bitmap bmp, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String a(Context context, String packageName) {
        try {
            Signature[] signs = context.getPackageManager().getPackageInfo(packageName, 64).signatures;
            if (signs != null && signs.length == 1) {
                return b(signs[0].toByteArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void a(Boolean mIsToFriend) {
        this.d = mIsToFriend;
    }

    public void a(AppStructDetailsItem mAppStructDetailsItem) {
        this.b = mAppStructDetailsItem;
    }
}
