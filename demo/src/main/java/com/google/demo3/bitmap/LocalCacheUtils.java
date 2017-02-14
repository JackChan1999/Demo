package com.google.demo3.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/9 08:03
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class LocalCacheUtils {

    private final String CACHE_PATH = Environment.getExternalStorageDirectory()+"android/data";

    public Bitmap getBitmapFromLocal(String url){
        Bitmap bitmap = null ;
        File file = null ;
        try {
            file = new File(CACHE_PATH, MD5Utils.encode(url));
            if (file != null && file.exists()){
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap ;
    }

    public void addBitmapToLocal(Bitmap bitmap, String url){
        try {
            File file = new File(CACHE_PATH, MD5Utils.encode(url));
            File parent = file.getParentFile();
            if (!parent.exists()){
                parent.mkdirs();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
