package com.google.demo3.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ImageView;

import com.google.demo3.R;

import java.io.File;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/9 07:37
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class BitmapUtils {

    private NetCacheUtils mNetCacheUtils;
    private MemoryCAcheUtils mMemoryCAcheUtils;
    private LocalCacheUtils mLocalCacheUtils;
    private Context mContext;

    public BitmapUtils(Context context) {
        mMemoryCAcheUtils = new MemoryCAcheUtils();
        mLocalCacheUtils = new LocalCacheUtils();
        mNetCacheUtils = new NetCacheUtils(mMemoryCAcheUtils, mLocalCacheUtils);
        mContext = context;
    }

    public void display(ImageView iv, String url) {
        iv.setImageResource(R.mipmap.ic_launcher);
        Bitmap bitmap = null;

        bitmap = mMemoryCAcheUtils.getBitmapFromMemory(url);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
            return;
        }

        bitmap = mLocalCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
            mMemoryCAcheUtils.addBitmapToMemory(bitmap, url);
            return;
        }

        mNetCacheUtils.getBitmapFromNet(iv, url);

    }

    private String getCacheDir() {
        File dir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = new File(Environment.getExternalStorageDirectory() + "/Android/data" + mContext
                    .getPackageName());
        }else {
            dir = new File(mContext.getCacheDir(),"/icon");
        }

        if (!dir.exists()){
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }
}
