package com.google.demo3.bitmap;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/9 07:52
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class MemoryCAcheUtils {

    private LruCache<String, Bitmap> mLruCache ;

    public MemoryCAcheUtils(){
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/8);
        mLruCache = new LruCache<String, Bitmap>(maxMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public Bitmap getBitmapFromMemory(String url){
        return mLruCache.get(url);
    }

    public void addBitmapToMemory(Bitmap bitmap, String url){
        if (getBitmapFromMemory(url) == null){
            mLruCache.put(url, bitmap);
        }
    }
}
