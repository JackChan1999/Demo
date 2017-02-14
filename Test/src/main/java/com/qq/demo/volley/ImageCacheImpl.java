package com.qq.demo.volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2016
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * <p/>
 * Project_Name：Demo
 * Package_Name：com.qq.demo.volley
 * Version：1.0
 * time：2016/8/16 11:05
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class ImageCacheImpl implements ImageLoader.ImageCache {
    private LruCache<String,Bitmap> mLruCache;

    public ImageCacheImpl() {
        int maxmemory = (int) (Runtime.getRuntime().maxMemory()/8);
        mLruCache = new LruCache<String, Bitmap>(maxmemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String s) {
        return mLruCache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        mLruCache.put(s,bitmap);
    }
}
